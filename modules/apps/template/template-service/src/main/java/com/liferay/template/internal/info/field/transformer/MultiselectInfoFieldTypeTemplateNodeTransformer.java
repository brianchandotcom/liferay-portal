/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "info.field.type.class.name=com.liferay.info.field.type.MultiselectInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class MultiselectInfoFieldTypeTemplateNodeTransformer
	extends BaseSelectInfoFieldTypeTemplateNodeTransformer {

	@Override
	protected Map<String, String> getAttributes() {
		return HashMapBuilder.put(
			"multiple", Boolean.TRUE.toString()
		).build();
	}

	@Override
	protected String getData(
		InfoFieldValue<Object> infoFieldValue, Locale locale) {

		return JSONUtil.toString(
			getSelectedOptionValuesJSONArray(infoFieldValue, locale));
	}

	@Override
	protected List<OptionInfoFieldType> getOptionInfoFieldTypes(
		InfoField infoField) {

		return (List<OptionInfoFieldType>)infoField.getAttribute(
			MultiselectInfoFieldType.OPTIONS);
	}

}