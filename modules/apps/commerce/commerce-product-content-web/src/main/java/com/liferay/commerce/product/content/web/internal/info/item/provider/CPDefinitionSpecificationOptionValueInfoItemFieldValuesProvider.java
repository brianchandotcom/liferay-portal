/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.content.web.internal.info.CPDefinitionSpecificationOptionValueInfoItemFields;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class CPDefinitionSpecificationOptionValueInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider
		<CPDefinitionSpecificationOptionValue> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getCPDefinitionSpecificationOptionValueInfoFieldValues(
				cpDefinitionSpecificationOptionValue)
		).infoFieldValues(
			_expandoInfoItemFieldSetProvider.getInfoFieldValues(
				CPDefinitionSpecificationOptionValue.class.getName(),
				cpDefinitionSpecificationOptionValue)
		).infoFieldValues(
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				CPDefinitionSpecificationOptionValue.class.getName(),
				cpDefinitionSpecificationOptionValue)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				CPDefinitionSpecificationOptionValue.class.getName(),
				cpDefinitionSpecificationOptionValue)
		).infoItemReference(
			new InfoItemReference(
				CPDefinitionSpecificationOptionValue.class.getName(),
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())
		).build();
	}

	private List<InfoFieldValue<Object>>
		_getCPDefinitionSpecificationOptionValueInfoFieldValues(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue) {

		List<InfoFieldValue<Object>>
			cpDefinitionSpecificationOptionValueInfoFieldValues =
				new ArrayList<>();

		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					companyIdInfoField,
				cpDefinitionSpecificationOptionValue.getCompanyId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					cpDefinitionIdInfoField,
				cpDefinitionSpecificationOptionValue.getCPDefinitionId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					cpDefinitionSpecificationOptionValueIdInfoField,
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					cpOptionCategoryIdInfoField,
				cpDefinitionSpecificationOptionValue.getCPOptionCategoryId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					cpSpecificationOptionIdInfoField,
				cpDefinitionSpecificationOptionValue.
					getCPSpecificationOptionId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					createDateInfoField,
				cpDefinitionSpecificationOptionValue.getCreateDate()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					defaultLanguageIdInfoField,
				cpDefinitionSpecificationOptionValue.getDefaultLanguageId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					groupIdInfoField,
				cpDefinitionSpecificationOptionValue.getGroupId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					lastPublishDateInfoField,
				cpDefinitionSpecificationOptionValue.getLastPublishDate()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					modifiedDateInfoField,
				cpDefinitionSpecificationOptionValue.getModifiedDate()));

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.fetchCPSpecificationOption(
				cpDefinitionSpecificationOptionValue.
					getCPSpecificationOptionId());

		if (cpSpecificationOption != null) {
			cpDefinitionSpecificationOptionValueInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionSpecificationOptionValueInfoItemFields.
						nameInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinitionSpecificationOptionValue.
								getDefaultLanguageId())
					).values(
						cpSpecificationOption.getTitleMap()
					).build()));
		}

		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					priorityInfoField,
				cpDefinitionSpecificationOptionValue.getPriority()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					stagedModelTypeInfoField,
				cpDefinitionSpecificationOptionValue.getStagedModelType()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					userIdInfoField,
				cpDefinitionSpecificationOptionValue.getUserId()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					userNameInfoField,
				cpDefinitionSpecificationOptionValue.getUserName()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					userUuidInfoField,
				cpDefinitionSpecificationOptionValue.getUserUuid()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					uuidInfoField,
				cpDefinitionSpecificationOptionValue.getUuid()));
		cpDefinitionSpecificationOptionValueInfoFieldValues.add(
			new InfoFieldValue<>(
				CPDefinitionSpecificationOptionValueInfoItemFields.
					valueInfoField,
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.fromLanguageId(
						cpDefinitionSpecificationOptionValue.
							getDefaultLanguageId())
				).values(
					cpDefinitionSpecificationOptionValue.getValueMap()
				).build()));

		return cpDefinitionSpecificationOptionValueInfoFieldValues;
	}

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}