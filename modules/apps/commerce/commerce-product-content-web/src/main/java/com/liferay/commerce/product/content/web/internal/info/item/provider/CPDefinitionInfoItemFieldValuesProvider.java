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

import com.liferay.commerce.product.content.web.internal.info.CPDefinitionInfoItemFields;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Marco Leo
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	service = InfoItemFieldValuesProvider.class
)
public class CPDefinitionInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CPDefinition> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CPDefinition cpDefinition) {

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getCPDefinitionInfoFieldValues(cpDefinition)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				CPDefinition.class.getName(), cpDefinition)
		).infoItemReference(
			new InfoItemReference(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getCPDefinitionInfoFieldValues(
		CPDefinition cpDefinition) {

		List<InfoFieldValue<Object>> cpDefinitionInfoFieldValues =
			new ArrayList<>();

		try {
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.titleInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getNameMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.descriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getDescriptionMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.defaultImage,
					cpDefinition.getDefaultImageFileURL()));

			ThemeDisplay themeDisplay = _getThemeDisplay();

			if (themeDisplay != null) {
				cpDefinitionInfoFieldValues.add(
					new InfoFieldValue<>(
						CPDefinitionInfoItemFields.displayPageUrlInfoField,
						_cpDefinitionHelper.getFriendlyURL(
							cpDefinition.getCPDefinitionId(), themeDisplay)));
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return cpDefinitionInfoFieldValues;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}