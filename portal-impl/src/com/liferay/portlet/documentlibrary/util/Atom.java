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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Atoms are self-contained data units in that contain information about an MP4
 * movie file.
 *
 * @author Juan González
 * @see    JQTFastStart
 */
public class Atom {

	public static final String CMOV = "cmov";

	public static final String CO64 = "co64";

	public static final String FREE = "free";

	public static final String FTYP = "ftyp";

	public static final String JUNK = "junk";

	public static final String MDAT = "mdat";

	public static final String MOOV = "moov";

	public static final String PICT = "PICT";

	public static final String PNOT = "pnot";

	public static final String SKIP = "skip";

	public static final String STCO = "stco";

	public static final String[] TOP_LEVEL_ATOMS =
		{FREE, FTYP, JUNK, MDAT, MOOV, PICT, PNOT, SKIP, Atom.WIDE};

	public static final String WIDE = "wide";

	public Atom(RandomAccessFile randomAccessFile) throws IOException {
		_offset = randomAccessFile.getFilePointer();
		_size = randomAccessFile.readInt();

		byte[] bytes = new byte[4];

		randomAccessFile.readFully(bytes);

		_type = new String(bytes);

		if (_size == 1) {
			_size = randomAccessFile.readLong();
		}

		randomAccessFile.seek(_offset);
	}

	public void fillBuffer(RandomAccessFile randomAccessFile)
		throws IOException {

		_buffer = new byte[(int)_size];

		randomAccessFile.readFully(_buffer);
	}

	public byte[] getBuffer() {
		return _buffer;
	}

	public long getOffset() {
		return _offset;
	}

	public long getSize() {
		return _size;
	}

	public String getType() {
		return _type;
	}

	public boolean isFTYP() {
		return StringUtil.equalsIgnoreCase(_type, FTYP);
	}

	public boolean isMDAT() {
		return StringUtil.equalsIgnoreCase(_type, MDAT);
	}

	public boolean isMOOV() {
		return StringUtil.equalsIgnoreCase(_type, MOOV);
	}

	public boolean isTopLevelAtom() {
		for (String topLevelAtom : TOP_LEVEL_ATOMS) {
			if (StringUtil.equalsIgnoreCase(_type, topLevelAtom)) {
				return true;
			}
		}

		return false;
	}

	public void patchAtom() {
		for (int index = 4; index < _size - 4; index++) {
			String type = new String(
				ArrayUtil.clone(_buffer, index, index + 4));

			if (StringUtil.equalsIgnoreCase(type, Atom.STCO)) {
				index += patchStcoAtom(index) - 4;
			}
			else if (StringUtil.equalsIgnoreCase(type, Atom.CO64)) {
				index += patchCo64Atom(index) - 4;
			}
		}
	}

	public void setBuffer(byte[] buffer) {
		_buffer = buffer;
	}

	public void setOffset(long offset) {
		_offset = offset;
	}

	public void setSize(long size) {
		_size = size;
	}

	public void setType(String type) {
		_type = type;
	}

	protected long bytesToLong(byte[] buffer) {
		long value = 0;

		for (int i = 0; i < buffer.length; i++) {
			value += (buffer[i] & _BITMASK) << 8 * (buffer.length - i - 1);
		}

		return value;
	}

	protected boolean hasCompressedMoovAtom() {
		String type = new String(ArrayUtil.clone(_buffer, 12, 15));

		if (StringUtil.equalsIgnoreCase(type, Atom.CMOV)) {
			return true;
		}

		return false;
	}

	protected int patchCo64Atom(int index) {
		int size = (int)bytesToLong(ArrayUtil.clone(_buffer, index - 4, index));

		int offsetCount = (int)bytesToLong(
			ArrayUtil.clone(_buffer, index + 8, index + 12));

		for (int i = 0; i < offsetCount; i++) {
			int offsetIndex = index + 12 + i * 8;

			long offset = bytesToLong(
				ArrayUtil.clone(_buffer, offsetIndex, offsetIndex + 8));

			offset += _size;

			_buffer[offsetIndex + 0] = (byte)((offset >> 56) & 0xFF);
			_buffer[offsetIndex + 1] = (byte)((offset >> 48) & 0xFF);
			_buffer[offsetIndex + 2] = (byte)((offset >> 40) & 0xFF);
			_buffer[offsetIndex + 3] = (byte)((offset >> 32) & 0xFF);
			_buffer[offsetIndex + 4] = (byte)((offset >> 24) & 0xFF);
			_buffer[offsetIndex + 5] = (byte)((offset >> 16) & 0xFF);
			_buffer[offsetIndex + 6] = (byte)((offset >> 8) & 0xFF);
			_buffer[offsetIndex + 7] = (byte)((offset >> 0) & 0xFF);
		}

		return size;
	}

	protected int patchStcoAtom(int index) {
		int size = (int)bytesToLong(ArrayUtil.clone(_buffer, index - 4, index));

		int offsetCount = (int)bytesToLong(
			ArrayUtil.clone(_buffer, index + 8, index + 12));

		for (int i = 0; i < offsetCount; i++) {
			int offsetIndex = index + 12 + i * 4;

			int offset = (int)bytesToLong(
				ArrayUtil.clone(_buffer, offsetIndex, offsetIndex + 4));

			offset += _size;

			_buffer[offsetIndex + 0] = (byte)((offset >> 24) & 0xFF);
			_buffer[offsetIndex + 1] = (byte)((offset >> 16) & 0xFF);
			_buffer[offsetIndex + 2] = (byte)((offset >> 8) & 0xFF);
			_buffer[offsetIndex + 3] = (byte)((offset >> 0) & 0xFF);
		}

		return size;
	}

	private static final int _BITMASK = 0x00000000000000FF;

	private byte[] _buffer;
	private long _offset;
	private long _size;
	private String _type;

}