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

import com.liferay.commerce.product.content.web.internal.info.CProductInfoItemFields;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
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
public class CProductInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CProduct> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(CProduct cProduct) {
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getCProductInfoFieldValues(cProduct)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				CProduct.class.getName(), cProduct)
		).infoItemReference(
			new InfoItemReference(
				CProduct.class.getName(), cProduct.getCProductId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getCProductInfoFieldValues(
		CProduct cProduct) {

		List<InfoFieldValue<Object>> cProductInfoFieldValues =
			new ArrayList<>();

		try {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.fetchCPDefinitionByCProductId(
					cProduct.getCProductId());

			cProductInfoFieldValues.add(
				new InfoFieldValue<>(
					CProductInfoItemFields.titleInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getNameMap()
					).build()));
			cProductInfoFieldValues.add(
				new InfoFieldValue<>(
					CProductInfoItemFields.descriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getDescriptionMap()
					).build()));
			cProductInfoFieldValues.add(
				new InfoFieldValue<>(
					CProductInfoItemFields.defaultImage,
					cpDefinition.getDefaultImageFileURL()));

			ThemeDisplay themeDisplay = _getThemeDisplay();

			if (themeDisplay != null) {
				cProductInfoFieldValues.add(
					new InfoFieldValue<>(
						CProductInfoItemFields.displayPageUrlInfoField,
						_cpDefinitionHelper.getFriendlyURL(
							cProduct.getPublishedCPDefinitionId(),
							themeDisplay)));
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return cProductInfoFieldValues;
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
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}