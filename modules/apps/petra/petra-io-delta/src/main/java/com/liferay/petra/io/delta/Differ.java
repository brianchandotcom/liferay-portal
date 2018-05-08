/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.petra.io.delta;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import java.security.MessageDigest;

/**
 * @author Connor McKay
 */
public class Differ {

	public void delta(
			ReadableByteChannel modifiedReadableByteChannel,
			ByteChannelReader checksumsByteChannelReader,
			ByteChannelWriter deltaByteChannelWriter)
		throws IOException {

		_modifiedReadableByteChannel = modifiedReadableByteChannel;
		_checksumsByteChannelReader = checksumsByteChannelReader;
		_deltaByteChannelWriter = deltaByteChannelWriter;

		_checksumsByteChannelReader.resizeBuffer(DeltaUtil.BUFFER_FACTOR * 20);

		_checksumsByteBuffer = _checksumsByteChannelReader.getBuffer();

		readChecksumsHeader();
		readChecksums();

		_rollingChecksum = new RollingChecksum(
			_modifiedReadableByteChannel, _blockLength);

		_deltaByteChannelWriter.resizeBuffer(
			_blockLength * DeltaUtil.BUFFER_FACTOR + 5);

		_deltaByteBuffer = _deltaByteChannelWriter.getBuffer();

		if ((_dataByteBuffer == null) ||
			(_dataByteBuffer.capacity() <
				(_blockLength * DeltaUtil.BUFFER_FACTOR))) {

			_dataByteBuffer = ByteBuffer.allocate(
				_blockLength * DeltaUtil.BUFFER_FACTOR);
		}

		writeDeltaHeader();
		writeDeltaBlocks();
	}

	protected void readChecksums() throws IOException {
		if (_checksumsByteChannelReader.isSeekable()) {
			_weakChecksums = new TIntIntHashMap(_blocksCount, 0.5F, -1, -1);
		}
		else {
			_blockBytesMap = new TIntObjectHashMap<>(_blocksCount);
		}

		for (int blockNumber = 0; blockNumber < _blocksCount; blockNumber++) {
			_checksumsByteChannelReader.ensureData(20);

			int weakChecksum = _checksumsByteBuffer.getInt();

			if (_checksumsByteChannelReader.isSeekable()) {

				// If checksumsByteChannelReader is an instance of
				// SeekableByteChannel, we can significantly reduce heap memory
				// usage and maintain performance by reading the strong
				// checksums as needed

				_checksumsByteChannelReader.skip(16);

				_weakChecksums.put(weakChecksum, blockNumber);
			}
			else {
				byte[] strongChecksum = new byte[16];

				_checksumsByteBuffer.get(strongChecksum);

				// It is possible that there are two or more blocks with the
				// same weak checksum, in which case the map will only contain
				// the strong checksum of the last one. In some cases, this may
				// cause a data block to be sent when a reference block could
				// have been sent, but it doesn't really matter.

				ByteBuffer byteBuffer = ByteBuffer.allocate(20);

				byteBuffer.put(strongChecksum);
				byteBuffer.putInt(blockNumber);

				_blockBytesMap.put(weakChecksum, byteBuffer.array());
			}
		}
	}

	protected void readChecksumsHeader() throws IOException {
		_checksumsByteChannelReader.ensureData(9);

		if (DeltaUtil.PROTOCOL_VERSION != _checksumsByteBuffer.get()) {
			throw new IOException("Unknown protocol version");
		}

		_blockLength = _checksumsByteBuffer.getInt();
		_blocksCount = _checksumsByteBuffer.getInt();
	}

	protected void writeDataBlock() throws IOException {
		if (_dataByteBuffer.position() == 0) {

			// There's nothing in the data buffer

			return;
		}

		_deltaByteChannelWriter.ensureSpace(_dataByteBuffer.position() + 5);

		_deltaByteBuffer.put(DeltaUtil.DATA_KEY);
		_deltaByteBuffer.putInt(_dataByteBuffer.position());

		_dataByteBuffer.flip();

		_deltaByteBuffer.put(_dataByteBuffer);

		_dataByteBuffer.clear();
	}

	protected void writeDeltaBlocks() throws IOException {
		_firstBlockNumber = -1;
		_lastBlockNumber = -1;

		while (_rollingChecksum.hasNext()) {
			int blockNumber = 0;
			byte[] strongChecksum = null;

			if (_checksumsByteChannelReader.isSeekable()) {
				blockNumber = _weakChecksums.get(
					_rollingChecksum.weakChecksum());

				if (blockNumber != -1) {
					int position = 13 + (blockNumber * 20);

					_checksumsByteChannelReader.position(position);

					_checksumsByteChannelReader.ensureData(16);

					strongChecksum = new byte[16];

					_checksumsByteBuffer.get(strongChecksum);
				}
			}
			else {
				byte[] blockBytes = _blockBytesMap.get(
					_rollingChecksum.weakChecksum());

				if (blockBytes != null) {
					ByteBuffer byteBuffer = ByteBuffer.wrap(blockBytes);

					strongChecksum = new byte[16];

					byteBuffer.get(strongChecksum, 0, 16);

					blockNumber = byteBuffer.getInt();
				}
			}

			if ((strongChecksum != null) &&
				MessageDigest.isEqual(
					strongChecksum, _rollingChecksum.strongChecksum())) {

				if (_firstBlockNumber == -1) {
					writeDataBlock();

					_firstBlockNumber = blockNumber;
					_lastBlockNumber = blockNumber;
				}
				else if ((_lastBlockNumber + 1) == blockNumber) {

					// The blocks must be sequential in a reference range block

					_lastBlockNumber = blockNumber;
				}
				else {
					writeReferenceBlock();

					_firstBlockNumber = blockNumber;
					_lastBlockNumber = blockNumber;
				}

				_rollingChecksum.nextBlock();
			}
			else {
				writeReferenceBlock();

				if (!_dataByteBuffer.hasRemaining()) {
					writeDataBlock();
				}

				_dataByteBuffer.put(_rollingChecksum.getFirstByte());

				_rollingChecksum.nextByte();
			}
		}

		// Only one of these should ever actually do something, but it's simpler
		// to call them both than choose between them.

		writeReferenceBlock();
		writeDataBlock();

		_deltaByteChannelWriter.ensureSpace(1);

		_deltaByteBuffer.put(DeltaUtil.EOF_KEY);
	}

	protected void writeDeltaHeader() throws IOException {
		_deltaByteChannelWriter.ensureSpace(5);

		_deltaByteBuffer.put(DeltaUtil.PROTOCOL_VERSION);
		_deltaByteBuffer.putInt(_blockLength);
	}

	protected void writeReferenceBlock() throws IOException {
		if (_firstBlockNumber == -1) {
			return;
		}

		if (_lastBlockNumber == _firstBlockNumber) {
			_deltaByteChannelWriter.ensureSpace(5);

			_deltaByteBuffer.put(DeltaUtil.REFERENCE_KEY);
			_deltaByteBuffer.putInt(_firstBlockNumber);
		}
		else {
			_deltaByteChannelWriter.ensureSpace(9);

			_deltaByteBuffer.put(DeltaUtil.REFERENCE_RANGE_KEY);
			_deltaByteBuffer.putInt(_firstBlockNumber);
			_deltaByteBuffer.putInt(_lastBlockNumber);
		}

		_firstBlockNumber = -1;
		_lastBlockNumber = -1;
	}

	private TIntObjectHashMap<byte[]> _blockBytesMap;
	private int _blockLength;
	private int _blocksCount;
	private ByteBuffer _checksumsByteBuffer;
	private ByteChannelReader _checksumsByteChannelReader;
	private ByteBuffer _dataByteBuffer;
	private ByteBuffer _deltaByteBuffer;
	private ByteChannelWriter _deltaByteChannelWriter;
	private int _firstBlockNumber;
	private int _lastBlockNumber;
	private ReadableByteChannel _modifiedReadableByteChannel;
	private RollingChecksum _rollingChecksum;
	private TIntIntHashMap _weakChecksums;

}