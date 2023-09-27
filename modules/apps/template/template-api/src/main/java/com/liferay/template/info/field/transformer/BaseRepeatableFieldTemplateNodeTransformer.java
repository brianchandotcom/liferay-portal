/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Collections;
import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public abstract class BaseRepeatableFieldTemplateNodeTransformer<T>
	extends BaseTemplateNodeTransformer {

	@Override
	public TemplateNode transform(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		try {
			return createTemplateNode(
				infoField.getName(), themeDisplay,
				(List<T>)infoFieldValue.getValue(themeDisplay.getLocale()),
				getTransformUnsafeFunction(infoFieldValue, themeDisplay));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return getDefaultTemplateNode(infoFieldValue, themeDisplay);
		}
	}

	protected <T> TemplateNode createTemplateNode(
			String fieldName, ThemeDisplay themeDisplay, List<T> list,
			UnsafeFunction<T, TemplateNode, Exception> unsafeFunction)
		throws Exception {

		if (list.isEmpty()) {
			return new TemplateNode(
				themeDisplay, fieldName, StringPool.BLANK, StringPool.BLANK,
				Collections.emptyMap());
		}

		T firstItem = list.get(0);

		TemplateNode templateNode = unsafeFunction.apply(firstItem);

		templateNode.appendSibling(templateNode);

		for (int i = 1; i < list.size(); i++) {
			T item = list.get(i);

			templateNode.appendSibling(unsafeFunction.apply(item));
		}

		return templateNode;
	}

	protected abstract UnsafeFunction<T, TemplateNode, Exception>
		getTransformUnsafeFunction(
			InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay);

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRepeatableFieldTemplateNodeTransformer.class);

}