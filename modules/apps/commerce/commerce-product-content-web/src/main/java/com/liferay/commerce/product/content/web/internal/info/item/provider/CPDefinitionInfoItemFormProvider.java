/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.product.content.web.internal.info.CPDefinitionInfoItemFields;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Alec Sloan
 * @author Allen Ziegenfus
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CPDefinitionInfoItemFormProvider
	implements InfoItemFormProvider<CPDefinition> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName()),
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName(), StringPool.BLANK,
				CPDefinition.class.getSimpleName(), 0));
	}

	@Override
	public InfoForm getInfoForm(CPDefinition cpDefinition) {
		try {
			return _getInfoForm(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						CPDefinition.class.getName(),
						cpDefinition.getCPDefinitionId())),
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					CPDefinition.class.getName(), StringPool.BLANK,
					CPDefinition.class.getSimpleName(), 0));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get info form for commerce product definition " +
					cpDefinition.getCPDefinitionId());

			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName(), 0, groupId),
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName(), StringPool.BLANK,
				CPDefinition.class.getSimpleName(), groupId));
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.availabilityStatusInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.finalPriceInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.inventoryInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.nameInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.productTypeNameInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.shortDescriptionInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.skuInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.userNameInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getCategorizationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.categoriesInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "categorization")
		).name(
			"categorization"
		).build();
	}

	private InfoFieldSet _getDetailedInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.accountGroupFilterEnabledInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.approvedInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.availableIndividuallyInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.channelFilterEnabledInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.companyIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.cpDefinitionIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.cProductIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.ddmStructureKeyInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.defaultLanguageIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.deliveryMaxSubscriptionCyclesInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.deliverySubscriptionEnabledInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.deliverySubscriptionLengthInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.deliverySubscriptionTypeInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.deliverySubscriptionTypeSettingsInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.deniedInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.depthInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.draftInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.expiredInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.freeShippingInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.groupIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.heightInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.ignoreSKUCombinationsInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.inactiveInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.incompleteInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.pendingInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.publishedInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.scheduledInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.shippableInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.shippingExtraPriceInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.shipSeparatelyPriceInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.stagedModelTypeInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.statusInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.statusByUserIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.statusByUserNameInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.statusByUserUuidInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.statusDateInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.subscriptionEnabledInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.subscriptionLengthInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.subscriptionTypeInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.subscriptionTypeSettingsInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.taxExemptInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.telcoOrElectronicsInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.userIdInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.userUuidInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.uuidInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.versionInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.weightInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.widthInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "detailed-information")
		).name(
			"detailed-information"
		).build();
	}

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.displayPageUrlInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "display-page")
		).name(
			"displayPage"
		).build();
	}

	private InfoForm _getInfoForm(
		InfoFieldSet assetEntryInfoFieldSet,
		InfoFieldSet displayPageInfoFieldSet) {

		return InfoForm.builder(
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName())
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			assetEntryInfoFieldSet
		).infoFieldSetEntry(
			_getCategorizationInfoFieldSet()
		).infoFieldSetEntry(
			_getDetailedInformationInfoFieldSet()
		).infoFieldSetEntry(
			unsafeConsumer -> {
				if (!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(_getDisplayPageInfoFieldSet());
				}
			}
		).infoFieldSetEntry(
			unsafeConsumer -> {
				if (FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(displayPageInfoFieldSet);
				}
			}
		).infoFieldSetEntry(
			_getScheduleInfoFieldSet()
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(CPDefinition.class.getName())
		).name(
			CPDefinition.class.getName()
		).build();
	}

	private InfoFieldSet _getScheduleInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.displayDateInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.expirationDateInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "schedule")
		).name(
			"schedule"
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionInfoItemFormProvider.class);

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}