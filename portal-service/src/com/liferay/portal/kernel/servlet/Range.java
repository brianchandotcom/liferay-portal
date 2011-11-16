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

package com.liferay.portal.kernel.servlet;

/**
 * @author Juan González
 */
public class Range {

	public Range(long start, long end, long total) {
		this._start = start;
		this._end = end;
		this._length = end - start + 1;
		this._total = total;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof Range)) {
			return false;
		}

		Range other = (Range) obj;

		if ((_end != other._end) || (_length != other._length) ||
			(_start != other._start) || (_total != other._total)) {

			return false;
		}

		return true;
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
		int prime = 31;

		int result = 1;

		result = prime * result + (int) (_end ^ (_end >>> 32));
		result = prime * result + (int) (_length ^ (_length >>> 32));
		result = prime * result + (int) (_start ^ (_start >>> 32));
		result = prime * result + (int) (_total ^ (_total >>> 32));

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

	private long _start;

	private long _end;

	private long _length;

	private long _total;

}