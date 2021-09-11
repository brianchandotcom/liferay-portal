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

package com.liferay.commerce.product.internal.upgrade;

import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.internal.upgrade.v1_10_1.CommerceSiteTypeUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_11_0.CPAttachmentFileEntryGroupUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_11_1.CPDisplayLayoutUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_2_0.ProductSubscriptionUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CPAttachmentFileEntryUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CPDefinitionLinkUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CPDefinitionOptionRelUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CPDefinitionUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CPFriendlyURLEntryUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CPInstanceUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.CProductUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_3_0.util.CProductTable;
import com.liferay.commerce.product.internal.upgrade.v1_4_0.CPDefinitionSpecificationOptionValueUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_5_0.CProductExternalReferenceCodeUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_6_0.CPDefinitionTrashEntriesUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_6_0.CommerceCatalogUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v1_6_0.util.CommerceCatalogTable;
import com.liferay.commerce.product.internal.upgrade.v1_6_0.util.CommerceChannelRelTable;
import com.liferay.commerce.product.internal.upgrade.v1_6_0.util.CommerceChannelTable;
import com.liferay.commerce.product.internal.upgrade.v1_7_0.CPDefinitionFiltersUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_0_0.CPInstanceOptionValueRelUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_0_0.util.CPInstanceOptionValueRelTable;
import com.liferay.commerce.product.internal.upgrade.v2_1_0.CommerceCatalogSystemUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_1_0.SubscriptionUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.CPDefinitionOptionValueRelUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_3_0.CommerceChannelUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_5_0.FriendlyURLEntryUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v3_1_0.CPTaxCategoryUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v3_3_0.CPAttachmentFileEntryCDNUpgradeProcess;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ethan Bustad
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceProductUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce product upgrade step registrator started");
		}

		registry.register("1.0.0", "1.1.0", new DummyUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0", new ProductSubscriptionUpgradeProcess());

		registry.register(
			"1.2.0", "1.3.0", new CPAttachmentFileEntryUpgradeProcess(),
			new CPDefinitionLinkUpgradeProcess(),
			new CPDefinitionOptionRelUpgradeProcess(),
			new CPDefinitionUpgradeProcess(), CProductTable.create(),
			new CProductUpgradeProcess(),
			new CPFriendlyURLEntryUpgradeProcess(_classNameLocalService),
			new CPInstanceUpgradeProcess());

		registry.register(
			"1.3.0", "1.4.0",
			new CPDefinitionSpecificationOptionValueUpgradeProcess());

		registry.register(
			"1.4.0", "1.5.0",
			new CProductExternalReferenceCodeUpgradeProcess());

		registry.register(
			"1.5.0", "1.6.0", CommerceCatalogTable.create(),
			CommerceChannelRelTable.create(), CommerceChannelTable.create(),
			new CommerceCatalogUpgradeProcess(
				_classNameLocalService, _groupLocalService),
			new CPDefinitionTrashEntriesUpgradeProcess(_classNameLocalService));

		registry.register(
			"1.6.0", "1.7.0", new CPDefinitionFiltersUpgradeProcess());

		registry.register(
			"1.7.0", "1.8.0",
			new com.liferay.commerce.product.internal.upgrade.v1_8_0.
				CPAttachmentFileEntryUpgradeProcess(_classNameLocalService));

		registry.register(
			"1.8.0", "1.9.0",
			new com.liferay.commerce.product.internal.upgrade.v1_9_0.
				CPDefinitionOptionRelUpgradeProcess());

		registry.register(
			"1.9.0", "1.10.0",
			new com.liferay.commerce.product.internal.upgrade.v1_10_0.
				CPAttachmentFileEntryUpgradeProcess(_jsonFactory),
			new com.liferay.commerce.product.internal.upgrade.v1_10_0.
				CPInstanceUpgradeProcess(_jsonFactory));

		registry.register(
			"1.10.0", "1.10.1",
			new CommerceSiteTypeUpgradeProcess(
				_classNameLocalService, _groupLocalService,
				_configurationProvider, _settingsFactory));

		registry.register(
			"1.10.1", "1.11.0",
			new CPAttachmentFileEntryGroupUpgradeProcess(
				_assetCategoryLocalService, _classNameLocalService));

		registry.register(
			"1.11.0", "1.11.1",
			new CPDisplayLayoutUpgradeProcess(_layoutLocalService));

		registry.register(
			"1.11.1", "1.11.2",
			new com.liferay.commerce.product.internal.upgrade.v1_11_2.
				CPDefinitionLinkUpgradeProcess());

		registry.register(
			"1.11.2", "2.0.0", CPInstanceOptionValueRelTable.create(),
			new CPInstanceOptionValueRelUpgradeProcess(
				_jsonFactory, _portalUUID));

		registry.register(
			"2.0.0", "2.1.0", new CommerceCatalogSystemUpgradeProcess(),
			new SubscriptionUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0", new CPDefinitionOptionValueRelUpgradeProcess());

		registry.register(
			"2.2.0", "2.2.1",
			new com.liferay.commerce.product.internal.upgrade.v2_2_1.
				CPDefinitionOptionValueRelUpgradeProcess());

		registry.register("2.2.1", "2.2.2", new DummyUpgradeProcess());

		registry.register(
			"2.2.2", "2.3.0", new CommerceChannelUpgradeProcess());

		registry.register(
			"2.3.0", "2.4.0",
			new com.liferay.commerce.product.internal.upgrade.v2_4_0.
				CPDefinitionOptionValueRelUpgradeProcess());

		registry.register(
			"2.4.0", "2.5.0",
			new FriendlyURLEntryUpgradeProcess(_groupLocalService));

		registry.register(
			"2.5.0", "2.6.0",
			new com.liferay.commerce.product.internal.upgrade.v2_6_0.
				CPInstanceUpgradeProcess());

		registry.register("2.6.0", "2.6.1", new DummyUpgradeProcess());

		registry.register(
			"2.6.1", "3.0.0",
			new com.liferay.commerce.product.internal.upgrade.v3_0_0.
				CPFriendlyURLEntryUpgradeProcess());

		registry.register("3.0.0", "3.1.0", new CPTaxCategoryUpgradeProcess());

		registry.register(
			"3.1.0", "3.2.0",
			new com.liferay.commerce.product.internal.upgrade.v3_2_0.
				FriendlyURLEntryUpgradeProcess(
					_classNameLocalService, _groupLocalService));

		registry.register("3.2.0", "3.2.1", new DummyUpgradeProcess());

		registry.register(
			"3.2.1", "3.3.0", new CPAttachmentFileEntryCDNUpgradeProcess());

		registry.register(
			"3.3.0", "3.4.0",
			new com.liferay.commerce.product.internal.upgrade.v3_4_0.
				CommerceChannelUpgradeProcess(
					_accountEntryGroupSettings, _configurationProvider));

		registry.register(
			"3.4.0", "3.5.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CPAttachmentFileEntry", "CPDSpecificationOptionValue",
						"CPDefinition", "CPDefinitionLink",
						"CPDefinitionLocalization", "CPDefinitionOptionRel",
						"CPDefinitionOptionValueRel", "CPDisplayLayout",
						"CPInstance", "CPInstanceOptionValueRel",
						"CPMeasurementUnit", "CPOption", "CPOptionCategory",
						"CPOptionValue", "CPSpecificationOption",
						"CPTaxCategory", "CProduct", "CommerceCatalog",
						"CommerceChannel", "CommerceChannelRel"
					};
				}

			});

		registry.register("3.5.0", "3.5.1", new DummyUpgradeStep());

		registry.register(
			"3.5.1", "3.6.0",
			new com.liferay.commerce.product.internal.upgrade.v3_6_0.
				CPInstanceUpgradeProcess());

		registry.register(
			"3.6.0", "3.7.0",
			new CTModelUpgradeProcess(
				"CommerceCatalog", "CommerceChannel", "CommerceChannelRel",
				"CPAttachmentFileEntry", "CPDefinition", "CPDefinitionLink",
				"CPDefinitionOptionRel", "CPDefinitionOptionValueRel",
				"CPDSpecificationOptionValue", "CPDisplayLayout", "CPInstance",
				"CPInstanceOptionValueRel", "CPMeasurementUnit", "CPOption",
				"CPOptionCategory", "CPOptionValue", "CProduct",
				"CPSpecificationOption", "CPTaxCategory"));

		if (_log.isInfoEnabled()) {
			_log.info("Commerce product upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductUpgradeStepRegistrator.class);

	@Reference
	private AccountEntryGroupSettings _accountEntryGroupSettings;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private SettingsFactory _settingsFactory;

}