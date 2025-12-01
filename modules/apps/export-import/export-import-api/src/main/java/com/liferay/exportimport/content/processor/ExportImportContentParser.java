/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.content.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

/**
 * @author Carlos Correa
 */
public interface ExportImportContentParser {

	public String parseExportContent(
			String content, PortletDataContext portletDataContext)
		throws Exception;

	public String parseImportContent(
			String content, PortletDataContext portletDataContext)
		throws Exception;

}