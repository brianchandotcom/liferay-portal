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

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.content.web.internal.info.CPDefinitionOptionValueRelInfoItemFields;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Danny Situ
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class CPDefinitionOptionValueRelInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CPDefinitionOptionValueRel> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getCPDefinitionOptionValueRelInfoFieldValues(
					cpDefinitionOptionValueRel)
			).infoItemReference(
				new InfoItemReference(
					CPDefinitionOptionValueRel.class.getName(),
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId())
			).build();
		}
		catch (Exception exception) {
			throw new RuntimeException("Unexpected exception", exception);
		}
	}

	private List<InfoFieldValue<Object>>
		_getCPDefinitionOptionValueRelInfoFieldValues(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		return ListUtil.fromArray(
			new InfoFieldValue<>(
				CPDefinitionOptionValueRelInfoItemFields.
					cpDefinitionOptionValueRelIdInfoField,
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId()),
			new InfoFieldValue<>(
				CPDefinitionOptionValueRelInfoItemFields.keyInfoField,
				cpDefinitionOptionValueRel.getKey()),
			new InfoFieldValue<>(
				CPDefinitionOptionValueRelInfoItemFields.nameInfoField,
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.fromLanguageId(
						cpDefinitionOptionValueRel.getDefaultLanguageId())
				).values(
					cpDefinitionOptionValueRel.getNameMap()
				).build()));
	}

}