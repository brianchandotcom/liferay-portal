/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.template.web.internal.info.item.field.reader;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.field.reader.LocalizedInfoItemFieldReader;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.template.web.internal.portlet.template.TemplateDisplayTemplateTransformer;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateInfoItemFieldReader
	implements LocalizedInfoItemFieldReader {

	public TemplateInfoItemFieldReader(
		DDMTemplate ddmTemplate, InfoItemFieldValues infoItemFieldValues) {

		_ddmTemplate = ddmTemplate;
		_infoItemFieldValues = infoItemFieldValues;
	}

	/**
	 *   @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *          #getInfoField()}
	 */
	@Deprecated
	@Override
	public InfoField getField() {
		return getInfoField();
	}

	@Override
	public InfoField getInfoField() {
		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"informationTemplate_" + _ddmTemplate.getTemplateId()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).value(
				LocaleUtil.getDefault(),
				_ddmTemplate.getName(LocaleUtil.getDefault())
			).defaultLocale(
				LocaleUtil.getDefault()
			).build()
		).build();
	}

	@Override
	public Object getValue(Object model) {
		return getValue(model, LocaleUtil.getDefault());
	}

	@Override
	public Object getValue(Object model, Locale locale) {
		TemplateDisplayTemplateTransformer templateDisplayTemplateTransformer =
			new TemplateDisplayTemplateTransformer(
				_ddmTemplate, _infoItemFieldValues);

		try {
			return templateDisplayTemplateTransformer.transform(locale);
		}
		catch (Exception exception) {
			_log.error("Unable to transform template", exception);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateInfoItemFieldReader.class);

	private final DDMTemplate _ddmTemplate;
	private final InfoItemFieldValues _infoItemFieldValues;

}