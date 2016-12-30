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

package com.liferay.calendar.internal.recurrence;

import com.liferay.calendar.recurrence.Recurrence;

/**
 * {@link RecurrenceSplit} represents the result of trying to split a recurrence
 * into two.
 *
 * <p>
 * Many times, we need to split a {@link Recurrence} object at a specific date.
 * If the recurrence does start before the "split date" and ends after the
 * "split date," then we will have two new recurrences: one going until before
 * the "split date" and other (starting at the split date) going as far as the
 * original one went.
 * </p>
 *
 * <p>
 * If the recurrence does not include the split date, however, we just need the
 * original recurrence.
 * </p>
 *
 * <p>
 * This interface represents the result of this operation. If the represented
 * split operation was "successful" (i.e. generated two new recurrences), we
 * can get their values through the methods {@link #getFirstRecurrence()} and
 * {@link #getSecondRecurrence()}. In this case, {@link #isSplit()} will return
 * <code>true</code>.
 * </p>
 *
 * <p>
 * If the split operation "failed" (i.e. the split date was outside the
 * recurrence range) we can still get a recurrence value back via the
 * {@link #getFirstRecurrence()} method. This will be a new recurrence instance
 * whose values are idetical to the original recurrence. In this case,
 * {@link #isSplit()} returns <code>false</code>.
 * </p>
 *
 * @author Adam Brandizzi
 */
public interface RecurrenceSplit {

	/**
	 * Returns the recurrence that ends just before the split date. If the split
	 * date was outside the original recurrence range, then returns a copy of
	 * the original recurrence.
	 *
	 * @return a new {@link Recurrence} object representing either the first
	 *         part of the split recurrence, or a copy of the original
	 *         recurrence, if no split happened.
	 */
	public Recurrence getFirstRecurrence();

	/**
	 * Returns the recurrence that starts after the split date and end when the
	 * original recurrence ended. If the split
	 * date was outside the original recurrence range, this method returns
	 * <code>null</code>.
	 *
	 * @return a new {@link Recurrence} object representing the second part of
	 *         the split recurrence, or <code>null</code> if the split
	 *         date was outside the original recurrence range.
	 */
	public Recurrence getSecondRecurrence();

	/**
	 * Informs whether the recurrence could be split into two  or not, given the
	 * start and split date.
	 *
	 * @return <code>true</code> if the operation could generate two new,
	 *         different recurrences. <code>false</code> otherwise.
	 */
	public boolean isSplit();

}