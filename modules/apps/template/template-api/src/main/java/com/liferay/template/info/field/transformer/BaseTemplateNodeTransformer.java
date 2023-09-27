/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;

/**
 * @author Lourdes Fernández Besada
 */
public abstract class BaseTemplateNodeTransformer
	implements TemplateNodeTransformer {

	protected TemplateNode getDefaultTemplateNode(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Object data = infoFieldValue.getValue(themeDisplay.getLocale());

		if (Validator.isNull(data)) {
			return new TemplateNode(
				themeDisplay, infoField.getName(), StringPool.BLANK,
				StringPool.BLANK, Collections.emptyMap());
		}

		return new TemplateNode(
			themeDisplay, infoField.getName(), String.valueOf(data),
			StringPool.BLANK, Collections.emptyMap());
	}

}