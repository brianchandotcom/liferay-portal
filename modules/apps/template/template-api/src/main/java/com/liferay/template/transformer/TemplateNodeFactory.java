/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.transformer;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Feliphe Marinho
 */
public interface TemplateNodeFactory {

	public TemplateNode createTemplateNode(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay);

}