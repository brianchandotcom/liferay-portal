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
public interface CPDefinitionInfoItemFields {

	public static final InfoField<BooleanInfoFieldType>
		accountGroupFilterEnabledInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"accountGroupFilterEnabled"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class,
				"account-group-filter-enabled")
		).build();
	public static final InfoField<BooleanInfoFieldType> approvedInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"approved"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "approved")
		).build();
	public static final InfoField<TextInfoFieldType>
		availabilityStatusInfoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"availabilityStatus"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "availability-status")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		availableIndividuallyInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"availableIndividually"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "available-individually")
		).build();
	public static final InfoField<TextInfoFieldType> categoriesInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"categories"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "all-categories")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		channelFilterEnabledInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"channelFilterEnabled"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "channel-filter-enabled")
		).build();
	public static final InfoField<NumberInfoFieldType> companyIdInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"companyId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "company-id")
		).build();
	public static final InfoField<NumberInfoFieldType> cpDefinitionIdInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpDefinitionId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "cpDefinitionId")
		).build();
	public static final InfoField<NumberInfoFieldType> cProductIdInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cProductId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "cProductId")
		).build();
	public static final InfoField<NumberInfoFieldType>
		cpTaxCategoryIdInfoField = InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"cpTaxCategoryId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "cpTaxCategoryId")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> ddmStructureKeyInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"ddmStructureKey"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "ddm-structure-key")
		).build();
	public static final InfoField<TextInfoFieldType>
		defaultLanguageIdInfoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"defaultLanguageId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "default-languageId")
		).build();
	public static final InfoField<NumberInfoFieldType>
		deliveryMaxSubscriptionCyclesInfoField = InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"deliveryMaxSubscriptionCycles"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class,
				"delivery-max-subscription-cycles")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		deliverySubscriptionEnabledInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"deliverySubscriptionEnabled"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class,
				"delivery-subscription-enabled")
		).build();
	public static final InfoField<NumberInfoFieldType>
		deliverySubscriptionLengthInfoField = InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"deliverySubscriptionLength"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class,
				"delivery-subscription-length")
		).build();
	public static final InfoField<TextInfoFieldType>
		deliverySubscriptionTypeInfoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"deliverySubscriptionType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "delivery-subscription-type")
		).build();
	public static final InfoField<TextInfoFieldType>
		deliverySubscriptionTypeSettingsInfoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"deliverySubscriptionTypeSettings"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class,
				"delivery-subscription-type-settings")
		).build();
	public static final InfoField<BooleanInfoFieldType> deniedInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"denied"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "denied")
		).build();
	public static final InfoField<NumberInfoFieldType> depthInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"depth"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "depth")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "description")
		).build();
	public static final InfoField<DateInfoFieldType> displayDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"displayDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "display-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageUrlInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<BooleanInfoFieldType> draftInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"draft"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "draft")
		).build();
	public static final InfoField<DateInfoFieldType> expirationDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<BooleanInfoFieldType> expiredInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"expired"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "expired")
		).build();
	public static final InfoField<TextInfoFieldType> finalPriceInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"finalPrice"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "final-price")
		).build();
	public static final InfoField<BooleanInfoFieldType> freeShippingInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"freeShipping"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "free-shipping")
		).build();
	public static final InfoField<NumberInfoFieldType> groupIdInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"groupId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "group-id")
		).build();
	public static final InfoField<NumberInfoFieldType> heightInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"height"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "height")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		ignoreSKUCombinationsInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"ignoreSKUCombinations"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "ignore-sku-combinations")
		).build();
	public static final InfoField<BooleanInfoFieldType> inactiveInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"inactive"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "inactive")
		).build();
	public static final InfoField<BooleanInfoFieldType> incompleteInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"incomplete"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "incomplete")
		).build();
	public static final InfoField<NumberInfoFieldType> inventoryInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"inventory"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "inventory")
		).build();
	public static final InfoField<DateInfoFieldType> lastPublishDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"lastPublishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "last-publish-date")
		).build();
	public static final InfoField<NumberInfoFieldType>
		maxSubscriptionCyclesInfoField = InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"maxSubscriptionCycles"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "max-subscription-cycles")
		).build();
	public static final InfoField<TextInfoFieldType> metaDescriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"metaDescription"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "meta-description")
		).build();
	public static final InfoField<TextInfoFieldType> metaKeywordsInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"metaKeywords"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "meta-keywords")
		).build();
	public static final InfoField<TextInfoFieldType> metaTitleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"metaTitle"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "meta-title")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<TextInfoFieldType> nameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"name"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "name")
		).build();
	public static final InfoField<BooleanInfoFieldType> pendingInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"pending"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "pending")
		).build();
	public static final InfoField<TextInfoFieldType> productTypeNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"productTypeName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "product-type")
		).build();
	public static final InfoField<BooleanInfoFieldType> publishedInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"published"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "published")
		).build();
	public static final InfoField<BooleanInfoFieldType> scheduledInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"scheduled"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "scheduled")
		).build();
	public static final InfoField<BooleanInfoFieldType> shippableInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"shippable"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "shippable")
		).build();
	public static final InfoField<NumberInfoFieldType>
		shippingExtraPriceInfoField = InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"shippingExtraPrice"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "shipping-extra-price")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		shipSeparatelyPriceInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"shipSeperately"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "ship-separately")
		).build();
	public static final InfoField<TextInfoFieldType> shortDescriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"shortDescription"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "short-description")
		).build();
	public static final InfoField<TextInfoFieldType> skuInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"sku"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(CPDefinitionInfoItemFields.class, "sku")
		).build();
	public static final InfoField<TextInfoFieldType> stagedModelTypeInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"stagedModelType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "staged-model-type")
		).build();
	public static final InfoField<TextInfoFieldType> statusByUserIdInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"statusByUserId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-by-userId")
		).build();
	public static final InfoField<TextInfoFieldType> statusByUserNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"statusByUserName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-by-userName")
		).build();
	public static final InfoField<TextInfoFieldType> statusByUserUuidInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"statusByUserUuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-by-userUuid")
		).build();
	public static final InfoField<DateInfoFieldType> statusDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"statusDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status-date")
		).build();
	public static final InfoField<TextInfoFieldType> statusInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"status"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "status")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		subscriptionEnabledInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"subscriptionEnabled"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "subscription-enabled")
		).build();
	public static final InfoField<NumberInfoFieldType>
		subscriptionLengthInfoField = InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"subscriptionLength"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "subscription-length")
		).build();
	public static final InfoField<TextInfoFieldType> subscriptionTypeInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"subscriptionType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "subscription-type")
		).build();
	public static final InfoField<TextInfoFieldType>
		subscriptionTypeSettingsInfoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"subscriptionTypeSettings"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "subscription-type-settings")
		).build();
	public static final InfoField<BooleanInfoFieldType> taxExemptInfoField =
		InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"taxExempt"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "tax-exempt")
		).build();
	public static final InfoField<BooleanInfoFieldType>
		telcoOrElectronicsInfoField = InfoField.builder(
		).infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"telcoOrElectronics"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "telco-or-electronics")
		).build();
	public static final InfoField<NumberInfoFieldType> userIdInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"userId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "userId")
		).build();
	public static final InfoField<TextInfoFieldType> userNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<TextInfoFieldType> userUuidInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userUuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "userUuid")
		).build();
	public static final InfoField<TextInfoFieldType> uuidInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"uuid"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "uuid")
		).build();
	public static final InfoField<NumberInfoFieldType> versionInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"version"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "version")
		).build();
	public static final InfoField<NumberInfoFieldType> weightInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"weight"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "weight")
		).build();
	public static final InfoField<NumberInfoFieldType> widthInfoField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).name(
			"width"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionInfoItemFields.class, "width")
		).build();

}