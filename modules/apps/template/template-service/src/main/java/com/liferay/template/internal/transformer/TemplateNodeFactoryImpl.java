/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;
import com.liferay.template.internal.info.field.transformer.TemplateNodeTransformerRegistry;
import com.liferay.template.transformer.TemplateNodeFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = TemplateNodeFactory.class)
public class TemplateNodeFactoryImpl implements TemplateNodeFactory {

	@Override
	public TemplateNode createTemplateNode(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		Class<?> infoFieldTypeClass = infoFieldType.getClass();

		TemplateNodeTransformer templateNodeTransformer =
			_templateNodeTransformerRegistry.getTemplateNodeTransformer(
				infoFieldTypeClass.getName());

		return templateNodeTransformer.transform(infoFieldValue, themeDisplay);
	}

	@Reference
	private TemplateNodeTransformerRegistry _templateNodeTransformerRegistry;

}