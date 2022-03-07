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

package com.liferay.commerce.internal.upgrade;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService;
import com.liferay.commerce.internal.upgrade.v1_1_0.CommerceOrderItemUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v1_1_0.CommerceOrderNoteUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v1_1_0.CommerceOrderUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v1_2_0.CommerceSubscriptionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v2_0_0.CommercePaymentMethodUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v2_1_0.CPDAvailabilityEstimateUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v2_1_0.CommerceSubscriptionEntryUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v3_2_0.CommerceAvailabilityEstimateUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v3_2_0.CommerceCountryUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v3_2_0.CommerceRegionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_0_0.CommerceShipmentItemUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_1_0.CommerceAddressUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_2_1.PrintedNoteUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_3_0.CommerceOrderDateUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_4_0.CommerceOrderManuallyAdjustedUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_5_1.CommerceShippingMethodUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_6_0.ShipmentUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_6_0.SubscriptionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_8_1.CommerceOrderStatusesUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v5_0_0.CommerceAddressRestrictionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v5_0_1.CommercePermissionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v7_2_0.util.CommerceOrderTypeRelTable;
import com.liferay.commerce.internal.upgrade.v7_2_0.util.CommerceOrderTypeTable;
import com.liferay.commerce.internal.upgrade.v8_2_0.CommerceShipmentExternalReferenceCodeUpgradeProcess;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0", new CommerceOrderUpgradeProcess(),
			new CommerceOrderItemUpgradeProcess(),
			new CommerceOrderNoteUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0", new CommerceSubscriptionUpgradeProcess());

		registry.register(
			"1.2.0", "2.0.0", new CommercePaymentMethodUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.commerce.internal.upgrade.v2_1_0.
				CommerceOrderItemUpgradeProcess(
					_cpDefinitionLocalService, _cpInstanceLocalService),
			new CommerceSubscriptionEntryUpgradeProcess(
				_cpDefinitionLocalService, _cpInstanceLocalService),
			new CPDAvailabilityEstimateUpgradeProcess(
				_cpDefinitionLocalService));

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.commerce.internal.upgrade.v2_2_0.
				CommerceAccountUpgradeProcess(
					_commerceAccountLocalService,
					_commerceAccountOrganizationRelLocalService,
					_emailAddressLocalService, _organizationLocalService),
			new com.liferay.commerce.internal.upgrade.v2_2_0.
				CommerceOrderUpgradeProcess(
					_commerceAccountLocalService, _userLocalService));

		registry.register(
			"2.2.0", "3.0.0",
			new com.liferay.commerce.internal.upgrade.v3_0_0.
				CommerceSubscriptionCycleEntryUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.commerce.internal.upgrade.v3_1_0.
				CommerceOrderUpgradeProcess());

		registry.register(
			"3.1.0", "3.2.0",
			new com.liferay.commerce.internal.upgrade.v3_2_0.
				CommerceOrderUpgradeProcess(),
			new com.liferay.commerce.internal.upgrade.v3_2_0.
				CommerceOrderItemUpgradeProcess(),
			new CommerceAvailabilityEstimateUpgradeProcess(),
			new CommerceCountryUpgradeProcess(),
			new CommerceRegionUpgradeProcess(),
			new com.liferay.commerce.internal.upgrade.v3_2_0.
				CPDAvailabilityEstimateUpgradeProcess());

		registry.register(
			"3.2.0", "4.0.0",
			new com.liferay.commerce.internal.upgrade.v4_0_0.
				CommerceOrderItemUpgradeProcess(),
			new CommerceShipmentItemUpgradeProcess());

		registry.register(
			"4.0.0", "4.1.0",
			new CommerceAddressUpgradeProcess(_classNameLocalService),
			new com.liferay.commerce.internal.upgrade.v4_1_0.
				CommerceOrderItemUpgradeProcess(),
			new com.liferay.commerce.internal.upgrade.v4_1_0.
				CommerceCountryUpgradeProcess());

		registry.register(
			"4.1.0", "4.1.1",
			new com.liferay.commerce.internal.upgrade.v4_1_1.
				CommerceAddressUpgradeProcess());

		registry.register("4.1.1", "4.2.0", new DummyUpgradeProcess());

		registry.register("4.2.0", "4.2.1", new PrintedNoteUpgradeProcess());

		registry.register(
			"4.2.1", "4.3.0", new CommerceOrderDateUpgradeProcess());

		registry.register(
			"4.3.0", "4.4.0",
			new CommerceOrderManuallyAdjustedUpgradeProcess());

		registry.register(
			"4.4.0", "4.5.0",
			new com.liferay.commerce.internal.upgrade.v4_5_0.
				CommerceAddressUpgradeProcess());

		registry.register(
			"4.5.0", "4.5.1",
			new CommerceShippingMethodUpgradeProcess(
				_classNameLocalService, _groupLocalService));

		registry.register(
			"4.5.1", "4.6.0", new DummyUpgradeProcess(),
			new ShipmentUpgradeProcess(), new SubscriptionUpgradeProcess());

		registry.register("4.6.0", "4.7.0", new DummyUpgradeProcess());

		registry.register(
			"4.7.0", "4.8.1", new CommerceOrderStatusesUpgradeProcess());

		registry.register(
			"4.8.1", "4.9.0",
			new com.liferay.commerce.internal.upgrade.v4_9_0.
				CommerceOrderUpgradeProcess(),
			new com.liferay.commerce.internal.upgrade.v4_9_0.
				CommerceOrderItemUpgradeProcess());

		registry.register(
			"4.9.0", "4.9.1",
			new com.liferay.commerce.internal.upgrade.v4_9_1.
				CommerceOrderUpgradeProcess());

		registry.register(
			"4.9.1", "4.10.0",
			new com.liferay.commerce.internal.upgrade.v4_10_0.
				CommerceOrderItemUpgradeProcess());

		registry.register(
			"4.10.0", "5.0.0",
			new com.liferay.commerce.internal.upgrade.v5_0_0.
				CommerceAddressUpgradeProcess(),
			new CommerceAddressRestrictionUpgradeProcess());

		registry.register(
			"5.0.0", "5.0.1",
			new CommercePermissionUpgradeProcess(
				_resourceActionLocalService, _resourcePermissionLocalService));

		registry.register(
			"5.0.1", "6.0.0",
			new com.liferay.commerce.internal.upgrade.v6_0_0.
				CommerceCountryUpgradeProcess(_countryLocalService),
			new com.liferay.commerce.internal.upgrade.v6_0_0.
				CommerceRegionUpgradeProcess(_regionLocalService));

		registry.register(
			"6.0.0", "7.0.0",
			new com.liferay.commerce.internal.upgrade.v7_0_0.
				CommerceAddressUpgradeProcess(
					_addressLocalService, _accountEntryLocalService,
					_listTypeLocalService, _phoneLocalService));

		registry.register(
			"7.0.0", "7.1.0",
			new com.liferay.commerce.internal.upgrade.v7_1_0.
				CommerceOrderUpgradeProcess());

		registry.register(
			"7.1.0", "7.2.0", CommerceOrderTypeTable.create(),
			CommerceOrderTypeRelTable.create());

		registry.register(
			"7.2.0", "7.3.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CPDAvailabilityEstimate", "CPDefinitionInventory",
						"CommerceAddressRestriction",
						"CommerceAvailabilityEstimate", "CommerceOrder",
						"CommerceOrderItem", "CommerceOrderNote",
						"CommerceOrderPayment", "CommerceOrderType",
						"CommerceOrderTypeRel", "CommerceShipment",
						"CommerceShipmentItem", "CommerceShippingMethod",
						"CommerceSubscriptionEntry"
					};
				}

			});

		registry.register(
			"7.3.0", "8.0.0",
			new com.liferay.commerce.internal.upgrade.v8_0_0.
				CommerceOrderItemUpgradeProcess());

		registry.register("8.0.0", "8.0.1", new DummyUpgradeProcess());

		registry.register(
			"8.0.1", "8.1.0",
			new com.liferay.commerce.internal.upgrade.v8_1_0.
				CommerceOrderUpgradeProcess());

		registry.register(
			"8.1.0", "8.1.1",
			new com.liferay.commerce.internal.upgrade.v8_1_1.
				CommerceAddressUpgradeProcess());

		registry.register(
			"8.1.1", "8.2.0",
			new CommerceShipmentExternalReferenceCodeUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("Commerce upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceUpgradeStepRegistrator.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}