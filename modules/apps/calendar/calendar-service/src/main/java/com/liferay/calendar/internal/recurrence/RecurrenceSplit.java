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
 * Represents the result of splitting a <code>Recurrence</code> (in the
 * <code>com.liferay.calendar.api</code> module) object.
 *
 * <p>
 * A <code>Recurrence</code> object is frequently split at a specific date. If
 * the recurrence starts before the split date and ends after the split date,
 * then there are two new recurrences: one going up to the split date and the
 * other starting at the split date and continuing to the original recurrence's
 * end.
 * </p>
 *
 * <p>
 * If the recurrence does not include the split date, however, only the original
 * recurrence is needed.
 * </p>
 *
 * <p>
 * This interface represents the result of this operation. If the split
 * operation is successful (i.e. generated two new recurrences), their values
 * can be acquired through the methods {@link #getFirstRecurrence()} and {@link
 * #getSecondRecurrence()}. In this case, {@link #isSplit()} returns
 * <code>true</code>.
 * </p>
 *
 * <p>
 * If the split operation failed (i.e. the split date was outside the recurrence
 * range), the recurrence value can still be acquired via the {@link
 * #getFirstRecurrence()} method. This would be a new recurrence instance whose
 * values are identical to the original recurrence. In this case, {@link
 * #isSplit()} returns <code>false</code>.
 * </p>
 *
 * @author Adam Brandizzi
 */
public interface RecurrenceSplit {

	/**
	 * Returns the recurrence that ends just before the split date. If the split
	 * date was outside the original recurrence range, this method returns a
	 * copy of the original recurrence.
	 *
	 * @return the <code>Recurrence</code> object representing either the first
	 *         part of the split recurrence or a copy of the original
	 *         recurrence, if no split occurred
	 */
	public Recurrence getFirstRecurrence();

	/**
	 * Returns the recurrence that starts after the split date and ends when the
	 * original recurrence ended. If the split date was outside the original
	 * recurrence range, this method returns <code>null</code>.
	 *
	 * @return the <code>Recurrence</code> object representing the second part
	 *         of the split recurrence, or <code>null</code> if the split date
	 *         was outside the original recurrence range
	 */
	public Recurrence getSecondRecurrence();

	/**
	 * Returns <code>true</code> if the recurrence could be split, given the
	 * start and split dates.
	 *
	 * @return <code>true</code> if the operation could generate two new,
	 *         different recurrences; <code>false</code> otherwise
	 */
	public boolean isSplit();

}