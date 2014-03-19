/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.util.List;

/**
 * <p>
 * Represents a change between one or several lines. <code>changeType</code>
 * tells if the change happened in source or target. <code>lineNumber</code>
 * holds the line number of the first modified line. This line number refers to
 * a line in source or target, depending on the <code>changeType</code> value.
 * <code>changedLines</code> is a list of strings, each string is a line that is
 * already highlighted, indicating where the changes are.
 * </p>
 *
 * @author Bruno Farache
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.portal.kernel.diff.DiffResult}
 */
@Deprecated
public class DiffResult extends com.liferay.portal.kernel.diff.DiffResult {

	public DiffResult(int linePos, List<String> changedLines) {
		super(linePos, changedLines);
	}

	public DiffResult(int linePos, String changedLine) {
		super(linePos, changedLine);
	}

}