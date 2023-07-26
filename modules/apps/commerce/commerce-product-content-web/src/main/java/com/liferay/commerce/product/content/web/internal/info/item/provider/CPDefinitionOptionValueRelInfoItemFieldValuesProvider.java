/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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