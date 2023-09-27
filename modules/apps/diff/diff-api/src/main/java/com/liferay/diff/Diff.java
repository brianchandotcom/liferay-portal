/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.diff;

import java.io.Reader;

import java.util.List;

/**
 * @author Bruno Farache
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