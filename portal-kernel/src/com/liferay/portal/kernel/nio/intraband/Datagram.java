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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.EOFException;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.util.EnumSet;

/**
 * Represents the communication unit of Intraband.
 *
 * <p>
 * Encodes/decodes data to/from big-endian byte order data format:
 * </p>
 *
 * <p>
 * <table border="1">
 *
 * <tr>
 * <td>Name</td><td>Type</td><td>Size(byte)</td><td>Offset</td>
 * </tr>
 * <tr>
 * <td>Status Flag</td><td>byte</td><td>1</td><td>0</td>
 * </tr>
 * <tr>
 * <td>Sequence ID</td><td>long</td><td>8</td><td>1</td>
 * </tr>
 * <tr>
 * <td>Data Type</td><td>byte</td><td>1</td><td>9</td>
 * </tr>
 * <tr>
 * <td>Data Size</td><td>int</td><td>4</td><td>10</td>
 * </tr>
 * <tr>
 * <td>Data Chunk</td>
 * <td>byte[]</td>
 * <td>
 * <pre>${Data Size}</pre></td> <td>14</td> </tr>
 *
 * </table>
 * </p>
 *
 * @author Shuyang Zhou
 */
public class Datagram {

	public static Datagram createRequestDatagram(byte type, byte[] data) {
		return createRequestDatagram(type, ByteBuffer.wrap(data));
	}

	public static Datagram createRequestDatagram(
		byte type, ByteBuffer dataByteBuffer) {

		Datagram datagram = new Datagram();

		// Status flag

		datagram._headerBufferArray[_INDEX_STATUS_FLAG] = _FLAG_REQUEST;

		// Request datagram does not set the sequence ID

		// Data type

		datagram._headerBufferArray[_INDEX_DATA_TYPE] = type;

		// Data size

		BigEndianCodec.putInt(
			datagram._headerBufferArray, _INDEX_DATA_SIZE,
			dataByteBuffer.remaining());

		// Data chunk

		datagram._dataByteBuffer = dataByteBuffer;

		return datagram;
	}

	public static Datagram createResponseDatagram(
		Datagram requestDatagram, byte[] data) {

		return createResponseDatagram(requestDatagram, ByteBuffer.wrap(data));
	}

	public static Datagram createResponseDatagram(
		Datagram requestDatagram, ByteBuffer byteBuffer) {

		Datagram datagram = new Datagram();

		// Status flag

		datagram._headerBufferArray[_INDEX_STATUS_FLAG] = _FLAG_RESPONSE;

		// Sequence ID

		BigEndianCodec.putLong(
			datagram._headerBufferArray, _INDEX_SEQUENCE_ID,
			requestDatagram.getSequenceId());

		// Response datagram does not set the data type

		// Data size

		BigEndianCodec.putInt(
			datagram._headerBufferArray, _INDEX_DATA_SIZE,
			byteBuffer.remaining());

		// Data chunk

		datagram._dataByteBuffer = byteBuffer;

		return datagram;
	}

	public ByteBuffer getDataByteBuffer() {
		return _dataByteBuffer;
	}

	public byte getType() {
		return _headerBufferArray[_INDEX_DATA_TYPE];
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{dataChunk=");

		ByteBuffer byteBuffer = _dataByteBuffer;

		if (byteBuffer == null) {
			sb.append(StringPool.NULL);
		}
		else {
			sb.append(byteBuffer.toString());
		}

		sb.append(", dataSize=");
		sb.append(BigEndianCodec.getInt(_headerBufferArray, _INDEX_DATA_SIZE));
		sb.append(", dataType=");
		sb.append(_headerBufferArray[_INDEX_DATA_TYPE]);
		sb.append(", sequenceId=");
		sb.append(
			BigEndianCodec.getLong(_headerBufferArray, _INDEX_SEQUENCE_ID));
		sb.append(", statusFlag=");
		sb.append(_headerBufferArray[_INDEX_STATUS_FLAG]);
		sb.append("}");

		return sb.toString();
	}

	protected static Datagram createACKResponseDatagram(long sequenceId) {
		Datagram datagram = new Datagram();

		// Status flag

		datagram._headerBufferArray[_INDEX_STATUS_FLAG] = _FLAG_ACK_RESPONSE;

		// Sequence ID

		BigEndianCodec.putLong(
			datagram._headerBufferArray, _INDEX_SEQUENCE_ID, sequenceId);

		// ACK response datagram does not set the data type

		// Data size

		BigEndianCodec.putInt(datagram._headerBufferArray, _INDEX_DATA_SIZE, 0);

		// Data chunk

		datagram._dataByteBuffer = _EMPTY_BUFFER;

		return datagram;
	}

	protected static Datagram createReceiveDatagram() {
		return new Datagram();
	}

	protected long getSequenceId() {
		return BigEndianCodec.getLong(_headerBufferArray, _INDEX_SEQUENCE_ID);
	}

	protected boolean isAckRequest() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_ACK_REQUEST) != 0) {
			return true;
		}

		return false;
	}

	protected boolean isAckResponse() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_ACK_RESPONSE) != 0) {
			return true;
		}

		return false;
	}

	protected boolean isRequest() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_REQUEST) != 0) {
			return true;
		}

		return false;
	}

	protected boolean isResponse() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_RESPONSE) != 0) {
			return true;
		}

		return false;
	}

	protected boolean readFrom(ScatteringByteChannel scatteringByteChannel)
		throws IOException {

		if (_headerByteBuffer.hasRemaining()) {
			if (scatteringByteChannel.read(_headerByteBuffer) == -1) {
				throw new EOFException();
			}

			if (_headerByteBuffer.hasRemaining()) {
				return false;
			}

			int dataSize = BigEndianCodec.getInt(
				_headerBufferArray, _INDEX_DATA_SIZE);

			if (dataSize == 0) {
				_dataByteBuffer = _EMPTY_BUFFER;

				return true;
			}

			_dataByteBuffer = ByteBuffer.allocate(dataSize);
		}

		if (scatteringByteChannel.read(_dataByteBuffer) == -1) {
			throw new EOFException();
		}

		if (_dataByteBuffer.hasRemaining()) {
			return false;
		}

		_dataByteBuffer.flip();

		return true;
	}

	protected void setAckRequest(boolean ackRequest) {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if (ackRequest) {
			statusFlag |= _FLAG_ACK_REQUEST;
		}
		else {
			statusFlag &= ~_FLAG_ACK_REQUEST;
		}

		_headerBufferArray[_INDEX_STATUS_FLAG] = statusFlag;
	}

	protected void setSequenceId(long sequenceId) {
		BigEndianCodec.putLong(
			_headerBufferArray, _INDEX_SEQUENCE_ID, sequenceId);
	}

	protected boolean writeTo(GatheringByteChannel gatheringByteChannel)
		throws IOException {

		if (_headerByteBuffer.hasRemaining()) {
			ByteBuffer[] byteBuffers = new ByteBuffer[2];

			byteBuffers[0] = _headerByteBuffer;
			byteBuffers[1] = _dataByteBuffer;

			gatheringByteChannel.write(byteBuffers);
		}
		else {
			gatheringByteChannel.write(_dataByteBuffer);
		}

		if (_dataByteBuffer.hasRemaining()) {
			return false;
		}

		_dataByteBuffer = null;

		return true;
	}

	protected Object attachment;
	protected CompletionHandler<Object> completionHandler;
	protected EnumSet<CompletionHandler.CompletionType> completionTypes;
	protected long expireTime;
	protected long timeout;

	private Datagram() {
		_headerByteBuffer = ByteBuffer.allocate(_HEADER_SIZE);

		// Directly reference the interanl byte array for faster encoding and
		// decoding

		_headerBufferArray = _headerByteBuffer.array();
	}

	private static final ByteBuffer _EMPTY_BUFFER = ByteBuffer.allocate(0);

	private static final byte _FLAG_ACK_REQUEST = 1;

	private static final byte _FLAG_ACK_RESPONSE = 2;

	private static final byte _FLAG_REQUEST = 4;

	private static final byte _FLAG_RESPONSE = 8;

	private static final int _HEADER_SIZE = 14;

	private static final int _INDEX_DATA_SIZE = 10;

	private static final int _INDEX_DATA_TYPE = 9;

	private static final int _INDEX_SEQUENCE_ID = 1;

	private static final int _INDEX_STATUS_FLAG = 0;

	private ByteBuffer _dataByteBuffer;
	private final byte[] _headerBufferArray;
	private final ByteBuffer _headerByteBuffer;

}