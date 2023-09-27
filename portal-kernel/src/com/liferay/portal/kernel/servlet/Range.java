/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Juan González
 */
public class Range {

	public Range(long start, long end, long total) {
		_start = start;
		_end = end;
		_total = total;

		_length = end - start + 1;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Range)) {
			return false;
		}

		Range range = (Range)object;

		if ((_end == range._end) && (_length == range._length) &&
			(_start == range._start) && (_total == range._total)) {

			return true;
		}

		return false;
	}

	public String getContentRange() {
		return StringBundler.concat(
			"bytes ", getStart(), StringPool.DASH, getEnd(), StringPool.SLASH,
			getTotal());
	}

	public long getEnd() {
		return _end;
	}

	public long getLength() {
		return _length;
	}

	public long getStart() {
		return _start;
	}

	public long getTotal() {
		return _total;
	}

	@Override
	public int hashCode() {
		int result = 1;

		result = (_PRIME * result) + (int)(_end ^ (_end >>> 32));
		result = (_PRIME * result) + (int)(_length ^ (_length >>> 32));
		result = (_PRIME * result) + (int)(_start ^ (_start >>> 32));
		result = (_PRIME * result) + (int)(_total ^ (_total >>> 32));

		return result;
	}

	public void setEnd(long end) {
		_end = end;
	}

	public void setLength(long length) {
		_length = length;
	}

	public void setStart(long start) {
		_start = start;
	}

	public void setTotal(long total) {
		_total = total;
	}

	private static final int _PRIME = 31;

	private long _end;
	private long _length;
	private long _start;
	private long _total;

}