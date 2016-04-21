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

package com.liferay.portal.kernel.diff;

import java.io.Reader;

import java.util.List;

/**
 * @author Bruno Farache
 * @see    DiffUtil
 */
public interface Diff {

	public static final String CLOSE_DEL = "</del>";

	public static final String CLOSE_INS = "</ins>";

	public static final String CONTEXT_LINE = "#context#line#";

	public static final String OPEN_DEL = "<del>";

	public static final String OPEN_INS = "<ins>";

	public List<DiffResult>[] diff(Reader source, Reader target);

	public List<DiffResult>[] diff(
		Reader source, Reader target, String addedMarkerStart,
		String addedMarkerEnd, String deletedMarkerStart,
		String deletedMarkerEnd, int margin);

}