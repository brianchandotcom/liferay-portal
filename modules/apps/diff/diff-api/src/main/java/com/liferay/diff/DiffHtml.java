/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.diff;

import java.io.Reader;

/**
 * @author Julio Camarero
 */
public interface DiffHtml {

	public String diff(Reader source, Reader target) throws Exception;

	public String replaceStyles(String html);

}