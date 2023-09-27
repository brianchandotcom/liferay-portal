/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.xml.Document;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Tina Tina
 */
public interface TransformerListener {

	public default boolean isEnabled() {
		return true;
	}

	public String onOutput(
		String output, String languageId, Map<String, String> tokens);

	public String onScript(
		String script, Document document, String languageId,
		Map<String, String> tokens);

	public Document onXml(
		Document document, String languageId, Map<String, String> tokens);

}