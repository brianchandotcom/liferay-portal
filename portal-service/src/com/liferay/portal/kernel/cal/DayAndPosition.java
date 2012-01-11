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

/*
 * Copyright (c) 2000, Columbia University.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *	  notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *	  notice, this list of conditions and the following disclaimer in the
 *	  documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the University nor the names of its contributors
 *	  may be used to endorse or promote products derived from this software
 *	  without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS
 * IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.liferay.portal.kernel.cal;

import com.liferay.portal.kernel.util.HashCode;
import com.liferay.portal.kernel.util.HashCodeFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Calendar;

/**
 * @author Jonathan Lennox
 */
public class DayAndPosition implements Cloneable, Serializable {

	/**
	 * Field NO_WEEKDAY
	 */
	public static final int NO_WEEKDAY = 0;

	/**
	 * Method isValidDayOfWeek
	 *
	 * @return boolean
	 */
	public static boolean isValidDayOfWeek(int d) {
		switch (d) {

			case NO_WEEKDAY :
			case Calendar.SUNDAY :
			case Calendar.MONDAY :
			case Calendar.TUESDAY :
			case Calendar.WEDNESDAY :
			case Calendar.THURSDAY :
			case Calendar.FRIDAY :
			case Calendar.SATURDAY :
				return true;

			default :
				return false;
		}
	}

	/**
	 * Method isValidDayPosition
	 *
	 * @return boolean
	 */
	public static boolean isValidDayPosition(int p) {
		return ((p >= -53) && (p <= 53));
	}

	/**
	 * Constructor DayAndPosition
	 */
	public DayAndPosition() {
		day = NO_WEEKDAY;
		position = 0;
	}

	/**
	 * Constructor DayAndPosition
	 */
	public DayAndPosition(int d, int p) {
		if (!isValidDayOfWeek(d)) {
			throw new IllegalArgumentException("Invalid day of week");
		}

		if (!isValidDayPosition(p)) {
			throw new IllegalArgumentException("Invalid day position");
		}

		day = d;
		position = p;
	}

	/**
	 * Method clone
	 *
	 * @return Object
	 */
	@Override
	public Object clone() {
		try {
			DayAndPosition other = (DayAndPosition)super.clone();

			other.day = day;
			other.position = position;

			return other;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/**
	 * Method equals
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DayAndPosition)) {
			return false;
		}

		DayAndPosition that = (DayAndPosition)obj;

		return (getDayOfWeek() == that.getDayOfWeek())
			   && (getDayPosition() == that.getDayPosition());
	}

	/**
	 * Method getDayOfWeek
	 *
	 * @return int
	 */
	public int getDayOfWeek() {
		return day;
	}

	/**
	 * Method getDayPosition
	 *
	 * @return int
	 */
	public int getDayPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		HashCode hashCode = HashCodeFactoryUtil.getHashCode();

		hashCode.append(day);
		hashCode.append(position);

		return hashCode.toHashCode();
	}

	/**
	 * Method setDayOfWeek
	 */
	public void setDayOfWeek(int d) {
		if (!isValidDayOfWeek(d)) {
			throw new IllegalArgumentException("Invalid day of week");
		}

		day = d;
	}

	/**
	 * Method setDayPosition
	 */
	public void setDayPosition(int p) {
		if (!isValidDayPosition(p)) {
			throw new IllegalArgumentException();
		}

		position = p;
	}

	/**
	 * Method toString
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6);

		sb.append(getClass().getName());
		sb.append("[day=");
		sb.append(day);
		sb.append(",position=");
		sb.append(position);
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Field day
	 */
	private int day;

	/**
	 * Field position
	 */
	private int position;

}