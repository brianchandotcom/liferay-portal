/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.ldiff;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import java.security.MessageDigest;

import java.util.HashMap;

/**
 * @author Connor McKay
 */
public class Differ {

	public void delta(ReadableByteChannel modified, ByteChannelReader checksums,
			ByteChannelWriter delta)
		throws IOException {

		_checksums = checksums;
		_modified = modified;
		_delta = delta;

		_checksums.resizeBuffer(LDiff.BUFFER_FACTOR * 20);
		_checksumsBuffer = _checksums.getBuffer();

		readChecksumsHeader();

		readChecksums();

		_reader = new RollingChecksum(_modified, _blockLength);

		_delta.resizeBuffer(_blockLength * LDiff.BUFFER_FACTOR + 5);
		_deltaBuffer = _delta.getBuffer();

		if (_dataBuffer == null ||
			_dataBuffer.capacity() < _blockLength * LDiff.BUFFER_FACTOR) {

			_dataBuffer =
				ByteBuffer.allocate(_blockLength * LDiff.BUFFER_FACTOR);
		}

		writeDeltaHeader();

		writeDeltaBlocks();
	}

	protected void readChecksums() throws IOException {
		_checksumsMap = new HashMap<Integer, BlockData>(_numBlocks);

		for (int blockNumber = 0; blockNumber < _numBlocks; blockNumber++) {
			_checksums.ensureData(20);

			int weakChecksum = _checksumsBuffer.getInt();
			byte[] strongChecksum = new byte[16];

			_checksumsBuffer.get(strongChecksum);

			// It is possible that there are two or more blocks with the same
			// weak checksum, in which case the map will only contain the strong
			// checksum of the last one. In some cases, this may cause a data
			// block to be sent when a reference block could have been sent,
			// but it doesn't really matter.

			_checksumsMap.put(
				Integer.valueOf(weakChecksum),
				new BlockData(blockNumber, strongChecksum));
		}
	}

	protected void readChecksumsHeader() throws IOException {
		_checksums.ensureData(9);

		if (LDiff.PROTOCOL_VERSION != _checksumsBuffer.get()) {
			throw new IOException("Unknown protocol version");
		}

		_blockLength = _checksumsBuffer.getInt();
		_numBlocks = _checksumsBuffer.getInt();
	}

	protected void writeDeltaBlocks() throws IOException {
		_firstBlockNumber = -1;
		_lastBlockNumber = -1;

		while (_reader.hasNext()) {
			BlockData blockData =
				_checksumsMap.get(Integer.valueOf(_reader.weakChecksum()));

			if (blockData != null && MessageDigest.isEqual(
				blockData.strongChecksum(), _reader.strongChecksum())) {

				int blockNumber = blockData.blockNumber();

				if (_firstBlockNumber == -1) {
					writeDataBlock();

					_firstBlockNumber = blockNumber;
					_lastBlockNumber = blockNumber;
				}
				else if (_lastBlockNumber + 1 == blockNumber) {

					// The blocks must be sequential in a reference range block

					_lastBlockNumber = blockNumber;
				}
				else {
					writeReferenceBlock();

					_firstBlockNumber = blockNumber;
					_lastBlockNumber = blockNumber;
				}

				_reader.nextBlock();
			}
			else {
				writeReferenceBlock();

				if (!_dataBuffer.hasRemaining()) {
					writeDataBlock();
				}

				_dataBuffer.put(_reader.getFirstByte());

				_reader.nextByte();
			}
		}

		// Only one of these should ever actually do something, but it's simpler
		// to call them both than choose between them.

		writeReferenceBlock();
		writeDataBlock();

		_delta.ensureSpace(1);
		_deltaBuffer.put(LDiff.EOF_KEY);
	}

	protected void writeDataBlock() throws IOException {
		if (_dataBuffer.position() == 0) {

			// There's nothing in the data buffer

			return;
		}

		_delta.ensureSpace(_dataBuffer.position() + 5);
		_deltaBuffer.put(LDiff.DATA_KEY);
		_deltaBuffer.putInt(_dataBuffer.position());

		_dataBuffer.flip();
		_deltaBuffer.put(_dataBuffer);
		_dataBuffer.clear();
	}

	protected void writeDeltaHeader() throws IOException {
		_delta.ensureSpace(5);
		_deltaBuffer.put(LDiff.PROTOCOL_VERSION);
		_deltaBuffer.putInt(_blockLength);
	}

	protected void writeReferenceBlock() throws IOException {
		if (_firstBlockNumber == -1) {
			return;
		}

		if (_lastBlockNumber == _firstBlockNumber) {
			_delta.ensureSpace(5);
			_deltaBuffer.put(LDiff.REFERENCE_KEY);
			_deltaBuffer.putInt(_firstBlockNumber);
		}
		else {
			_delta.ensureSpace(9);
			_deltaBuffer.put(LDiff.REFERENCE_RANGE_KEY);
			_deltaBuffer.putInt(_firstBlockNumber);
			_deltaBuffer.putInt(_lastBlockNumber);
		}

		_firstBlockNumber = -1;
		_lastBlockNumber = -1;
	}

	protected static class BlockData {

		public BlockData(int blockNumber, byte[] strongChecksum) {
			_blockNumber = blockNumber;
			_strongChecksum = strongChecksum;
		}

		public int blockNumber() {
			return _blockNumber;
		}

		public byte[] strongChecksum() {
			return _strongChecksum;
		}

		private int _blockNumber;
		private byte[] _strongChecksum;

	}

	private int _blockLength;

	private HashMap<Integer, BlockData> _checksumsMap;
	private ByteChannelReader _checksums;
	private ByteBuffer _checksumsBuffer;

	private ByteBuffer _dataBuffer;
	private ByteChannelWriter _delta;
	private ByteBuffer _deltaBuffer;

	private int _firstBlockNumber;

	private int _lastBlockNumber;
	private ReadableByteChannel _modified;

	private int _numBlocks;
	private RollingChecksum _reader;

}