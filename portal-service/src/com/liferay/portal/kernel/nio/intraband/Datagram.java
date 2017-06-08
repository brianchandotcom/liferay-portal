/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.EOFException;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.util.EnumSet;

/**
 * Big Endian Byte Order.<br>
 * Data format:
 * <table border="1">
 *	<tr>
 *		<td>Name</td><td>Type</td><td>Size(byte)</td><td>Offset</td>
 *	</tr>
 *	<tr>
 *		<td>Status Flag</td><td>byte</td><td>1</td><td>0</td>
 *	</tr>
 *  <tr>
 *		<td>Sequence Id</td><td>long</td><td>8</td><td>1</td>
 *	</tr>
 *	<tr>
 *		<td>Data Type</td><td>byte</td><td>1</td><td>9</td>
 *	</tr>
 *	<tr>
 *		<td>Data Size</td><td>int</td><td>4</td><td>10</td>
 *	</tr>
 *	<tr>
 *		<td>Data Chunk</td><td>byte[]</td><td>${Data Size}</td><td>14</td>
 *	</tr>
 * </table>
 *
 * @author Shuyang Zhou
 */
public class Datagram {

	public static Datagram createRequestDatagram(byte type, byte[] data) {
		return createRequestDatagram(type, ByteBuffer.wrap(data));
	}

	public static Datagram createRequestDatagram(
		byte type, ByteBuffer dataBuffer) {

		Datagram datagram = new Datagram();

		// Status flag

		datagram._headerBufferArray[_INDEX_STATUS_FLAG] = _FLAG_REQUEST;

		// Request Datagram does not set Sequence Id

		// Data type

		datagram._headerBufferArray[_INDEX_DATA_TYPE] = type;

		// Data size

		BigEndianCodec.putInt(
			datagram._headerBufferArray, _INDEX_DATA_SIZE,
			dataBuffer.remaining());

		// Data chunk

		datagram._dataBuffer = dataBuffer;

		return datagram;
	}

	public static Datagram createResponseDatagram(
		Datagram requestDatagram, byte[] data) {

		return createResponseDatagram(requestDatagram, ByteBuffer.wrap(data));
	}

	public static Datagram createResponseDatagram(
		Datagram requestDatagram, ByteBuffer dataBuffer) {

		Datagram datagram = new Datagram();

		// Status flag

		datagram._headerBufferArray[_INDEX_STATUS_FLAG] = _FLAG_RESPONSE;

		// Sequence Id

		BigEndianCodec.putLong(
			datagram._headerBufferArray, _INDEX_SEQUENCE_ID,
			requestDatagram.getSequenceId());

		// Response Datagram does not set data type

		// Data size

		BigEndianCodec.putInt(
			datagram._headerBufferArray, _INDEX_DATA_SIZE,
			dataBuffer.remaining());

		// Data chunk

		datagram._dataBuffer = dataBuffer;

		return datagram;
	}

	public ByteBuffer getData() {
		return _dataBuffer;
	}

	public byte getType() {
		return _headerBufferArray[_INDEX_DATA_TYPE];
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{statusFlag=");
		sb.append(_headerBufferArray[_INDEX_STATUS_FLAG]);
		sb.append(", sequenceId=");
		sb.append(
			BigEndianCodec.getLong(_headerBufferArray, _INDEX_SEQUENCE_ID));
		sb.append(", dataType=");
		sb.append(_headerBufferArray[_INDEX_DATA_TYPE]);
		sb.append(", dataSize=");
		sb.append(BigEndianCodec.getInt(_headerBufferArray, _INDEX_DATA_SIZE));
		sb.append(", dataChunk=");

		ByteBuffer dataBuffer = _dataBuffer;

		if (dataBuffer == null) {
			sb.append(StringPool.NULL);
		}
		else {
			sb.append(dataBuffer.toString());
		}

		sb.append("}");

		return sb.toString();
	}

	// Package level methods, do not escalate access level

	static Datagram createACKResponseDatagram(long sequenceId) {
		Datagram datagram = new Datagram();

		// Status flag

		datagram._headerBufferArray[_INDEX_STATUS_FLAG] = _FLAG_ACK_RESPONSE;

		// Sequence Id

		BigEndianCodec.putLong(
			datagram._headerBufferArray, _INDEX_SEQUENCE_ID, sequenceId);

		// ACK response does not set data type

		// Data size

		BigEndianCodec.putInt(datagram._headerBufferArray, _INDEX_DATA_SIZE, 0);

		// Empty data chunk

		datagram._dataBuffer = _EMPTY_BUFFER;

		return datagram;
	}

	static Datagram createReceiveDatagram() {
		return new Datagram();
	}

	long getSequenceId() {
		return BigEndianCodec.getLong(_headerBufferArray, _INDEX_SEQUENCE_ID);
	}

	boolean isAckRequest() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_ACK_REQUEST) != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	boolean isAckResponse() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_ACK_RESPONSE) != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	boolean isRequest() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_REQUEST) != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	boolean isResponse() {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if ((statusFlag & _FLAG_RESPONSE) != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	boolean readFrom(ScatteringByteChannel scatteringByteChannel)
		throws IOException {

		if (_headerBuffer.hasRemaining()) {
			if (scatteringByteChannel.read(_headerBuffer) == -1) {
				throw new EOFException();
			}

			if (_headerBuffer.hasRemaining()) {
				return false;
			}
			else {
				int dataSize = BigEndianCodec.getInt(
					_headerBufferArray, _INDEX_DATA_SIZE);

				if (dataSize == 0) {
					_dataBuffer = _EMPTY_BUFFER;

					return true;
				}
				else {
					_dataBuffer = ByteBuffer.allocate(dataSize);
				}
			}
		}

		if (scatteringByteChannel.read(_dataBuffer) == -1) {
			throw new EOFException();
		}

		if (_dataBuffer.hasRemaining()) {
			return false;
		}
		else {
			_dataBuffer.flip();

			return true;
		}
	}

	void setAckRequest(boolean ackRequest) {
		byte statusFlag = _headerBufferArray[_INDEX_STATUS_FLAG];

		if (ackRequest) {
			statusFlag |= _FLAG_ACK_REQUEST;
		}
		else {
			statusFlag &= ~_FLAG_ACK_REQUEST;
		}

		_headerBufferArray[_INDEX_STATUS_FLAG] = statusFlag;
	}

	void setSequenceId(long sequenceId) {
		BigEndianCodec.putLong(
			_headerBufferArray, _INDEX_SEQUENCE_ID, sequenceId);
	}

	boolean writeTo(GatheringByteChannel gatheringByteChannel)
		throws IOException {

		if (_headerBuffer.hasRemaining()) {
			ByteBuffer[] outputBuffers = new ByteBuffer[2];

			outputBuffers[0] = _headerBuffer;
			outputBuffers[1] = _dataBuffer;

			gatheringByteChannel.write(outputBuffers);
		}
		else {
			gatheringByteChannel.write(_dataBuffer);
		}

		if (_dataBuffer.hasRemaining()) {
			return false;
		}
		else {
			_dataBuffer = null;

			return true;
		}
	}

	private Datagram() {
		_headerBuffer = ByteBuffer.allocate(_HEADER_SIZE);

		// Direct reference to internal byte[] for fast encoding/decoding

		_headerBufferArray = _headerBuffer.array();
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

	// Package level fields, do not escalate access level

	Object attachment;
	CompletionHandler<Object> completionHandler;
	long expireTime;
	EnumSet<CompletionType> completionTypes;
	long timeout;

	private ByteBuffer _dataBuffer;
	private final ByteBuffer _headerBuffer;
	private final byte[] _headerBufferArray;

}