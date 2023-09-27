/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.template.info.field.transformer.BaseRepeatableFieldTemplateNodeTransformer;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "info.field.type.class.name=com.liferay.info.field.type.TagsInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class DefaultRepeatableFieldTemplateNodeTransformer
	extends BaseRepeatableFieldTemplateNodeTransformer<Object> {

	@Override
	protected UnsafeFunction<Object, TemplateNode, Exception>
		getTransformUnsafeFunction(
			InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		return object -> new TemplateNode(
			themeDisplay, infoField.getName(), String.valueOf(object),
			infoFieldType.getName(), Collections.emptyMap());
	}

}