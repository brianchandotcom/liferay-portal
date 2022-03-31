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

package com.liferay.commerce.product.content.web.internal.info;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Eudaldo Alonso
 */
public class CPDefinitionInfoItemFields {

	public static final InfoField<BooleanInfoFieldType>
		accountGroupFilterEnabledInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"accountGroupFilterEnabled"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"account-group-filter-enabled")
			).build();
	public static final InfoField<BooleanInfoFieldType> approvedInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"approved"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "approved")
		).build();
	public static final InfoField<TextInfoFieldType>
		availabilityStatusInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				TextInfoFieldType.INSTANCE
			).name(
				"availabilityStatus"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "availability-status")
			).build();
	public static final InfoField<BooleanInfoFieldType>
		availableIndividuallyInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"availableIndividually"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "available-individually")
			).build();
	public static final InfoField<TextInfoFieldType> categoriesInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"categories"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "all-categories")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		channelFilterEnabledInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"channelFilterEnabled"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "channel-filter-enabled")
			).build();
	public static final InfoField<NumberInfoFieldType> companyIdInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"companyId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "company-id")
		).build();
	public static final InfoField<NumberInfoFieldType> cpDefinitionIdInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpDefinitionId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "cpDefinitionId")
		).build();
	public static final InfoField<NumberInfoFieldType> cProductIdInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cProductId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "cProductId")
		).build();
	public static final InfoField<NumberInfoFieldType>
		cpTaxCategoryIdInfoField = BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpTaxCategoryId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "cpTaxCategoryId")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> ddmStructureKeyInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"ddmStructureKey"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "ddm-structure-key")
		).build();
	public static final InfoField<TextInfoFieldType>
		defaultLanguageIdInfoField = BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"defaultLanguageId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "default-languageId")
		).build();
	public static final InfoField<NumberInfoFieldType>
		deliveryMaxSubscriptionCyclesInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"deliveryMaxSubscriptionCycles"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"delivery-max-subscription-cycles")
			).build();
	public static final InfoField<BooleanInfoFieldType>
		deliverySubscriptionEnabledInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"deliverySubscriptionEnabled"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"delivery-subscription-enabled")
			).build();
	public static final InfoField<NumberInfoFieldType>
		deliverySubscriptionLengthInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"deliverySubscriptionLength"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"delivery-subscription-length")
			).build();
	public static final InfoField<TextInfoFieldType>
		deliverySubscriptionTypeInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				TextInfoFieldType.INSTANCE
			).name(
				"deliverySubscriptionType"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"delivery-subscription-type")
			).build();
	public static final InfoField<TextInfoFieldType>
		deliverySubscriptionTypeSettingsInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				TextInfoFieldType.INSTANCE
			).name(
				"deliverySubscriptionTypeSettings"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"delivery-subscription-type-settings")
			).build();
	public static final InfoField<BooleanInfoFieldType> deniedInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"denied"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "denied")
		).build();
	public static final InfoField<NumberInfoFieldType> depthInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"depth"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "depth")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "description")
		).build();
	public static final InfoField<DateInfoFieldType> displayDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"displayDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "display-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageUrlInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<BooleanInfoFieldType> draftInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"draft"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "draft")
		).build();
	public static final InfoField<DateInfoFieldType> expirationDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<BooleanInfoFieldType> expiredInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"expired"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "expired")
		).build();
	public static final InfoField<TextInfoFieldType> finalPriceInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"finalPrice"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "final-price")
		).build();
	public static final InfoField<BooleanInfoFieldType> freeShippingInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"freeShipping"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "free-shipping")
		).build();
	public static final InfoField<NumberInfoFieldType> groupIdInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"groupId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "group-id")
		).build();
	public static final InfoField<NumberInfoFieldType> heightInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"height"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "height")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		ignoreSKUCombinationsInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"ignoreSKUCombinations"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "ignore-sku-combinations")
			).build();
	public static final InfoField<BooleanInfoFieldType> inactiveInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"inactive"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "inactive")
		).build();
	public static final InfoField<BooleanInfoFieldType> incompleteInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"incomplete"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "incomplete")
		).build();
	public static final InfoField<NumberInfoFieldType> inventoryInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"inventory"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "inventory")
		).build();
	public static final InfoField<DateInfoFieldType> lastPublishDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"lastPublishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "last-publish-date")
		).build();
	public static final InfoField<NumberInfoFieldType>
		maxSubscriptionCyclesInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"maxSubscriptionCycles"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "max-subscription-cycles")
			).build();
	public static final InfoField<TextInfoFieldType> metaDescriptionInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"metaDescription"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "meta-description")
		).build();
	public static final InfoField<TextInfoFieldType> metaKeywordsInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"metaKeywords"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "meta-keywords")
		).build();
	public static final InfoField<TextInfoFieldType> metaTitleInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"metaTitle"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "meta-title")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<TextInfoFieldType> nameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"name"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "name")
		).build();
	public static final InfoField<BooleanInfoFieldType> pendingInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"pending"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "pending")
		).build();
	public static final InfoField<TextInfoFieldType> productTypeNameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"productTypeName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "product-type")
		).build();
	public static final InfoField<BooleanInfoFieldType> publishedInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"published"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "published")
		).build();
	public static final InfoField<BooleanInfoFieldType> scheduledInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"scheduled"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "scheduled")
		).build();
	public static final InfoField<BooleanInfoFieldType> shippableInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"shippable"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "shippable")
		).build();
	public static final InfoField<NumberInfoFieldType>
		shippingExtraPriceInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"shippingExtraPrice"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "shipping-extra-price")
			).build();
	public static final InfoField<BooleanInfoFieldType>
		shipSeparatelyPriceInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"shipSeperately"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "ship-separately")
			).build();
	public static final InfoField<TextInfoFieldType> shortDescriptionInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"shortDescription"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "short-description")
		).build();
	public static final InfoField<TextInfoFieldType> skuInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"sku"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(CPDefinitionInfoItemFields.class, "sku")
		).build();
	public static final InfoField<TextInfoFieldType> stagedModelTypeInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"stagedModelType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "staged-model-type")
		).build();
	public static final InfoField<TextInfoFieldType> statusByUserIdInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"statusByUserId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-by-userId")
		).build();
	public static final InfoField<TextInfoFieldType> statusByUserNameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"statusByUserName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-by-userName")
		).build();
	public static final InfoField<TextInfoFieldType> statusByUserUuidInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"statusByUserUuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-by-userUuid")
		).build();
	public static final InfoField<DateInfoFieldType> statusDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"statusDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-date")
		).build();
	public static final InfoField<TextInfoFieldType> statusInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"status"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		subscriptionEnabledInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"subscriptionEnabled"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "subscription-enabled")
			).build();
	public static final InfoField<NumberInfoFieldType>
		subscriptionLengthInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"subscriptionLength"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "subscription-length")
			).build();
	public static final InfoField<TextInfoFieldType> subscriptionTypeInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"subscriptionType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "subscription-type")
		).build();
	public static final InfoField<TextInfoFieldType>
		subscriptionTypeSettingsInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				TextInfoFieldType.INSTANCE
			).name(
				"subscriptionTypeSettings"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class,
					"subscription-type-settings")
			).build();
	public static final InfoField<BooleanInfoFieldType> taxExemptInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"taxExempt"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "tax-exempt")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		telcoOrElectronicsInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				BooleanInfoFieldType.INSTANCE
			).name(
				"telcoOrElectronics"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionInfoItemFields.class, "telco-or-electronics")
			).build();
	public static final InfoField<NumberInfoFieldType> userIdInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"userId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "userId")
		).build();
	public static final InfoField<TextInfoFieldType> userNameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<TextInfoFieldType> userUuidInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userUuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "userUuid")
		).build();
	public static final InfoField<TextInfoFieldType> uuidInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"uuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "uuid")
		).build();
	public static final InfoField<NumberInfoFieldType> versionInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"version"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "version")
		).build();
	public static final InfoField<NumberInfoFieldType> weightInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"weight"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "weight")
		).build();
	public static final InfoField<NumberInfoFieldType> widthInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"width"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "width")
		).build();

	private static class BuilderStaticHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(CPDefinition.class.getSimpleName());

	}

}