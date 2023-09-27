/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionSpecificationOptionValueInfoItemFields {

	public static final InfoField<NumberInfoFieldType> companyIdInfoField =
		BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"companyId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"company-id")
		).build();
	public static final InfoField<NumberInfoFieldType> cpDefinitionIdInfoField =
		BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpDefinitionId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"cpDefinitionId")
		).build();
	public static final InfoField<NumberInfoFieldType>
		cpDefinitionSpecificationOptionValueIdInfoField =
			BuilderHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"cpDefinitionSpecificationOptionValueId"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionSpecificationOptionValueInfoItemFields.class,
					"cpDefinitionSpecificationOptionValueId")
			).build();
	public static final InfoField<NumberInfoFieldType>
		cpOptionCategoryIdInfoField = BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpOptionCategoryId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"cpOptionCategoryId")
		).build();
	public static final InfoField<NumberInfoFieldType>
		cpSpecificationOptionIdInfoField = BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpSpecificationOptionId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"cpSpecificationOptionId")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"create-date")
		).build();
	public static final InfoField<TextInfoFieldType>
		defaultLanguageIdInfoField = BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"defaultLanguageId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"default-languageId")
		).build();
	public static final InfoField<NumberInfoFieldType> groupIdInfoField =
		BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"groupId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"group-id")
		).build();
	public static final InfoField<DateInfoFieldType> lastPublishDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"lastPublishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"last-publish-date")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"modified-date")
		).build();
	public static final InfoField<TextInfoFieldType> nameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"name"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"name")
		).build();
	public static final InfoField<NumberInfoFieldType> priorityInfoField =
		BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"priority"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"priority")
		).build();
	public static final InfoField<TextInfoFieldType> stagedModelTypeInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"stagedModelType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"staged-model-type")
		).build();
	public static final InfoField<NumberInfoFieldType> userIdInfoField =
		BuilderHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"userId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"userId")
		).build();
	public static final InfoField<TextInfoFieldType> userNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"author-name")
		).build();
	public static final InfoField<TextInfoFieldType> userUuidInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userUuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"userUuid")
		).build();
	public static final InfoField<TextInfoFieldType> uuidInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"uuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"uuid")
		).build();
	public static final InfoField<TextInfoFieldType> valueInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"value"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionSpecificationOptionValueInfoItemFields.class,
				"value")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(
				CPDefinitionSpecificationOptionValue.class.getSimpleName());

	}

}