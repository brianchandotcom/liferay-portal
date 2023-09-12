/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.registry;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.internal.upgrade.v1_2_0.CommerceSubscriptionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v2_0_0.CommercePaymentMethodUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v2_1_0.CPDAvailabilityEstimateUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v2_1_0.CommerceSubscriptionEntryUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_1_0.CommerceAddressUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_3_0.CommerceOrderDateUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_5_1.CommerceShippingMethodUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_8_1.CommerceOrderStatusesUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v5_0_1.CommercePermissionUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v5_9_0.CommerceAccountOrganizationRelUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v5_9_0.CommerceAccountUserRelUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v6_1_0.CommerceAccountGroupRelUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v6_1_0.CommerceAccountGroupUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v7_1_2.CommerceAccountGroupCommerceAccountRelUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v7_1_2.CommerceAccountPortletUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v7_2_0.util.CommerceOrderTypeRelTable;
import com.liferay.commerce.internal.upgrade.v7_2_0.util.CommerceOrderTypeTable;
import com.liferay.commerce.internal.upgrade.v8_2_1.CommerceAccountRoleUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v8_4_0.util.CommerceShippingOptionAccountEntryRelTable;
import com.liferay.commerce.internal.upgrade.v8_4_1.AccountGroupUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v8_5_0.CommerceAddressTypeUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v8_9_1.CommerceChannelAccountEntryRelUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v8_9_4.CommerceOrderUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v9_3_0.ConfigurationUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v9_4_0.AccountRoleUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v9_6_1.SupplierRoleUpgradeProcess;
import com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl;
import com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl;
import com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl;
import com.liferay.commerce.model.impl.CommerceOrderItemModelImpl;
import com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.CommerceAccountHelper;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseUuidUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(service = UpgradeStepRegistrator.class)
public class CommerceServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "externalReferenceCode VARCHAR(75)"),
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "externalReferenceCode VARCHAR(75)"),
			UpgradeProcessFactory.addColumns(
				"CommerceOrderNote", "externalReferenceCode VARCHAR(75)"));

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
					_accountEntryLocalService,
					_accountEntryOrganizationRelLocalService,
					_accountEntryUserRelLocalService, _emailAddressLocalService,
					_organizationLocalService, _roleLocalService),
			new com.liferay.commerce.internal.upgrade.v2_2_0.
				CommerceOrderUpgradeProcess(
					_accountEntryLocalService, _accountEntryUserRelLocalService,
					_userLocalService));

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
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "printedNote VARCHAR(75)",
				"requestedDeliveryDate DATE"),
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "deliveryGroup VARCHAR(75)",
				"shippingAddressId LONG", "printedNote VARCHAR(75)",
				"requestedDeliveryDate DATE"),
			UpgradeProcessFactory.dropColumns(
				CommerceAvailabilityEstimateModelImpl.TABLE_NAME, "groupId"),
			UpgradeProcessFactory.dropColumns("CommerceCountry", "groupId"),
			UpgradeProcessFactory.dropColumns("CommerceRegion", "groupId"),
			UpgradeProcessFactory.dropColumns(
				CPDAvailabilityEstimateModelImpl.TABLE_NAME, "groupId"));

		registry.register(
			"3.2.0", "4.0.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "bookedQuantityId LONG"),
			UpgradeProcessFactory.alterColumnName(
				"CommerceShipmentItem", "commerceWarehouseId",
				"commerceInventoryWarehouseId LONG"));

		registry.register(
			"4.0.0", "4.1.0",
			new CommerceAddressUpgradeProcess(_classNameLocalService),
			new com.liferay.commerce.internal.upgrade.v4_1_0.
				CommerceOrderItemUpgradeProcess(),
			new com.liferay.commerce.internal.upgrade.v4_1_0.
				CommerceCountryUpgradeProcess());

		registry.register(
			"4.1.0", "4.1.1",
			UpgradeProcessFactory.alterColumnType(
				"CommerceAddress", "name", "VARCHAR(255) null"),
			UpgradeProcessFactory.alterColumnType(
				"CommerceAddress", "street1", "VARCHAR(255) null"),
			UpgradeProcessFactory.alterColumnType(
				"CommerceAddress", "street2", "VARCHAR(255) null"),
			UpgradeProcessFactory.alterColumnType(
				"CommerceAddress", "street3", "VARCHAR(255) null"));

		registry.register("4.1.1", "4.2.0", new DummyUpgradeProcess());

		registry.register(
			"4.2.0", "4.2.1",
			UpgradeProcessFactory.alterColumnType(
				"CommerceOrder", "printedNote", "STRING null"),
			UpgradeProcessFactory.alterColumnType(
				"CommerceOrderItem", "printedNote", "STRING null"));

		registry.register(
			"4.2.1", "4.3.0", new CommerceOrderDateUpgradeProcess());

		registry.register(
			"4.3.0", "4.4.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "manuallyAdjusted BOOLEAN"),
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "manuallyAdjusted BOOLEAN"));

		registry.register(
			"4.4.0", "4.5.0",
			UpgradeProcessFactory.addColumns(
				"CommerceAddress", "externalReferenceCode VARCHAR(75)"));

		registry.register(
			"4.5.0", "4.5.1",
			new CommerceShippingMethodUpgradeProcess(
				_classNameLocalService, _groupLocalService));

		registry.register(
			"4.5.1", "4.6.0", new DummyUpgradeProcess(),
			UpgradeProcessFactory.alterColumnType(
				"CommerceShipment", "shippingOptionName", "TEXT"),
			UpgradeProcessFactory.addColumns(
				"CommerceSubscriptionEntry",
				"deliverySubscriptionLength INTEGER",
				"deliverySubscriptionType VARCHAR(75)",
				"deliverySubTypeSettings TEXT", "deliveryCurrentCycle LONG",
				"deliveryMaxSubscriptionCycles LONG",
				"deliverySubscriptionStatus INTEGER",
				"deliveryLastIterationDate DATE",
				"deliveryNextIterationDate DATE", "deliveryStartDate DATE"));

		registry.register("4.6.0", "4.7.0", new DummyUpgradeProcess());

		registry.register(
			"4.7.0", "4.8.1", new CommerceOrderStatusesUpgradeProcess());

		registry.register(
			"4.8.1", "4.9.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "subtotalWithTaxAmount BIGDECIMAL",
				"subtotalDiscountWithTaxAmount BIGDECIMAL",
				"subtotalDiscountPctLev1WithTax BIGDECIMAL",
				"subtotalDiscountPctLev2WithTax BIGDECIMAL",
				"subtotalDiscountPctLev3WithTax BIGDECIMAL",
				"subtotalDiscountPctLev4WithTax BIGDECIMAL",
				"shippingWithTaxAmount BIGDECIMAL",
				"shippingDiscountWithTaxAmount BIGDECIMAL",
				"shippingDiscountPctLev1WithTax BIGDECIMAL",
				"shippingDiscountPctLev2WithTax BIGDECIMAL",
				"shippingDiscountPctLev3WithTax BIGDECIMAL",
				"shippingDiscountPctLev4WithTax BIGDECIMAL",
				"totalWithTaxAmount BIGDECIMAL",
				"totalDiscountWithTaxAmount BIGDECIMAL",
				"totalDiscountPctLev1WithTax BIGDECIMAL",
				"totalDiscountPctLev2WithTax BIGDECIMAL",
				"totalDiscountPctLev3WithTax BIGDECIMAL",
				"totalDiscountPctLev4WithTax BIGDECIMAL"),
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "parentCommerceOrderItemId LONG",
				"unitPriceWithTaxAmount BIGDECIMAL",
				"promoPriceWithTaxAmount BIGDECIMAL",
				"discountWithTaxAmount BIGDECIMAL",
				"finalPriceWithTaxAmount BIGDECIMAL",
				"discountPctLevel1WithTaxAmount BIGDECIMAL",
				"discountPctLevel2WithTaxAmount BIGDECIMAL",
				"discountPctLevel3WithTaxAmount BIGDECIMAL",
				"discountPctLevel4WithTaxAmount BIGDECIMAL",
				"commercePriceListId LONG"));

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
			UpgradeProcessFactory.alterColumnName(
				"CommerceAddress", "commerceCountryId", "countryId LONG"),
			UpgradeProcessFactory.alterColumnName(
				"CommerceAddress", "commerceRegionId", "regionId LONG"),
			UpgradeProcessFactory.alterColumnName(
				"CommerceAddressRestriction", "commerceCountryId",
				"countryId LONG"));

		registry.register(
			"5.0.0", "5.0.1",
			new CommercePermissionUpgradeProcess(
				_resourceActionLocalService, _resourcePermissionLocalService));

		registry.register(
			"5.0.1", "5.9.0",
			new com.liferay.commerce.internal.upgrade.v5_9_0.
				CommerceAccountUpgradeProcess(
					_accountEntryLocalService, _classNameLocalService,
					_commerceAccountHelper, _expandoTableLocalService,
					_expandoValueLocalService, _groupLocalService,
					_resourceLocalService, _workflowDefinitionLinkLocalService,
					_workflowInstanceLinkLocalService),
			new CommerceAccountOrganizationRelUpgradeProcess(
				_accountEntryOrganizationRelLocalService),
			new CommerceAccountUserRelUpgradeProcess(
				_accountEntryUserRelLocalService));

		registry.register(
			"5.9.0", "6.0.0",
			new com.liferay.commerce.internal.upgrade.v6_0_0.
				CommerceCountryUpgradeProcess(_countryLocalService),
			new com.liferay.commerce.internal.upgrade.v6_0_0.
				CommerceRegionUpgradeProcess(_regionLocalService));

		registry.register(
			"6.0.0", "6.1.0",
			new CommerceAccountGroupUpgradeProcess(
				_accountGroupLocalService, _resourceLocalService),
			new CommerceAccountGroupRelUpgradeProcess(
				_accountGroupRelLocalService));

		registry.register(
			"6.1.0", "6.2.0",
			UpgradeProcessFactory.dropTables(
				"CommerceAccount", "CommerceAccountOrganizationRel",
				"CommerceAccountUserRel"));

		registry.register(
			"6.2.0", "7.0.0",
			new com.liferay.commerce.internal.upgrade.v7_0_0.
				CommerceAddressUpgradeProcess(
					_addressLocalService, _accountEntryLocalService,
					_listTypeLocalService, _phoneLocalService));

		registry.register(
			"7.0.0", "7.1.0",
			new com.liferay.commerce.internal.upgrade.v7_1_0.
				CommerceOrderUpgradeProcess());

		registry.register(
			"7.1.0", "7.1.2",
			new CommerceAccountGroupCommerceAccountRelUpgradeProcess(
				_accountGroupRelLocalService),
			new com.liferay.commerce.internal.upgrade.v7_1_2.
				CommerceAccountRoleUpgradeProcess(
					_accountRoleLocalService, _classNameLocalService,
					_groupLocalService, _resourcePermissionLocalService,
					_roleLocalService),
			new CommerceAccountPortletUpgradeProcess());

		registry.register(
			"7.1.2", "7.2.0", CommerceOrderTypeTable.create(),
			CommerceOrderTypeRelTable.create());

		registry.register(
			"7.2.0", "7.3.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
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
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "CPMeasurementUnitId LONG",
				"decimalQuantity BIGDECIMAL null"));

		registry.register("8.0.0", "8.0.1", new DummyUpgradeProcess());

		registry.register(
			"8.0.1", "8.1.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "deliveryCommerceTermEntryId LONG",
				"deliveryCTermEntryDescription TEXT null",
				"deliveryCommerceTermEntryName VARCHAR(75) null",
				"paymentCommerceTermEntryId LONG",
				"paymentCTermEntryDescription TEXT null",
				"paymentCommerceTermEntryName VARCHAR(75) null"));

		registry.register(
			"8.1.0", "8.1.1",
			new com.liferay.commerce.internal.upgrade.v8_1_1.
				CommerceAddressUpgradeProcess());

		registry.register(
			"8.1.1", "8.1.2",
			new com.liferay.commerce.internal.upgrade.v8_1_2.
				CommerceAccountRoleUpgradeProcess(
					_companyLocalService, _resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.1.2", "8.2.0",
			UpgradeProcessFactory.addColumns(
				"CommerceShipment", "externalReferenceCode VARCHAR(75)"),
			UpgradeProcessFactory.addColumns(
				"CommerceShipmentItem", "externalReferenceCode VARCHAR(75)"));

		registry.register(
			"8.2.0", "8.2.1",
			new CommerceAccountRoleUpgradeProcess(
				_companyLocalService, _resourceActionLocalService,
				_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.2.1", "8.3.0",
			new CTModelUpgradeProcess("CPDefinitionInventory"));

		registry.register(
			"8.3.0", "8.4.0",
			CommerceShippingOptionAccountEntryRelTable.create());

		registry.register(
			"8.4.0", "8.4.1",
			new AccountGroupUpgradeProcess(_resourceLocalService),
			new com.liferay.commerce.internal.upgrade.v8_4_1.
				CommerceAccountRoleUpgradeProcess(
					_companyLocalService, _resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.4.1", "8.5.0",
			new CommerceAddressTypeUpgradeProcess(_listTypeLocalService));

		registry.register(
			"8.5.0", "8.6.0",
			new BaseUuidUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CommerceOrderItem", "commerceOrderItemId"},
						{"CommerceOrderNote", "commerceOrderNoteId"},
						{"CommerceOrderType", "commerceOrderTypeId"},
						{"CommerceOrderTypeRel", "commerceOrderTypeRelId"},
						{"CommerceShipment", "commerceShipmentId"},
						{"CommerceShipmentItem", "commerceShipmentItemId"}
					};
				}

			});

		registry.register(
			"8.6.0", "8.6.1",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CommerceOrder", "commerceOrderId"},
						{"CommerceOrderItem", "commerceOrderItemId"},
						{"CommerceOrderNote", "commerceOrderNoteId"},
						{"CommerceOrderType", "commerceOrderTypeId"},
						{"CommerceOrderTypeRel", "commerceOrderTypeRelId"}
					};
				}

			});

		registry.register(
			"8.6.1", "8.7.0",
			UpgradeProcessFactory.addColumns(
				"CommerceShipment", "trackingURL STRING null"),
			UpgradeProcessFactory.addColumns(
				"CommerceShippingMethod", "trackingURL STRING null"));

		registry.register(
			"8.7.0", "8.7.1",
			new com.liferay.commerce.internal.upgrade.v8_7_1.
				AccountEntryUpgradeProcess(
					_addressLocalService,
					_commerceChannelAccountEntryRelLocalService,
					_commerceTermEntryLocalService));

		registry.register(
			"8.7.1", "8.8.0",
			new com.liferay.commerce.internal.upgrade.v8_8_0.
				CommercePermissionUpgradeProcess(
					_resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.8.0", "8.9.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "discountManuallyAdjusted BOOLEAN",
				"priceManuallyAdjusted BOOLEAN"));

		registry.register(
			"8.9.0", "8.9.1",
			new CommerceChannelAccountEntryRelUpgradeProcess());

		registry.register(
			"8.9.1", "8.9.2",
			new com.liferay.commerce.internal.upgrade.v8_9_2.
				CommercePermissionUpgradeProcess(
					_resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.9.2", "8.9.3",
			new com.liferay.commerce.internal.upgrade.v8_9_3.
				CommerceCountryUpgradeProcess(_commerceChannelRelLocalService));

		registry.register(
			"8.9.3", "8.9.4",
			new CommerceOrderUpgradeProcess(
				_companyLocalService, _resourceActionLocalService,
				_resourceLocalService, _resourcePermissionLocalService,
				_roleLocalService));

		registry.register(
			"8.9.4", "8.9.5",
			new com.liferay.commerce.internal.upgrade.v8_9_5.
				AccountEntryUpgradeProcess(
					_commerceChannelAccountEntryRelLocalService),
			new com.liferay.commerce.internal.upgrade.v8_9_5.
				CommerceAccountRoleUpgradeProcess(
					_companyLocalService, _resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.9.5", "8.9.6",
			new com.liferay.commerce.internal.upgrade.v8_9_6.
				CommerceAccountRoleUpgradeProcess(
					_companyLocalService, _resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"8.9.6", "9.0.0",
			new com.liferay.commerce.internal.upgrade.v9_0_0.
				CommerceOrderUpgradeProcess());

		registry.register(
			"9.0.0", "9.1.0",
			new com.liferay.commerce.internal.upgrade.v9_1_0.
				CommercePermissionUpgradeProcess(
					_resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"9.1.0", "9.2.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "replacedCPInstanceId LONG",
				"replacedSku VARCHAR(75) null"));

		registry.register("9.2.0", "9.3.0", new ConfigurationUpgradeProcess());

		registry.register(
			"9.3.0", "9.4.0",
			new AccountRoleUpgradeProcess(
				_companyLocalService, _resourceActionLocalService,
				_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"9.4.0", "9.5.0",
			new com.liferay.commerce.internal.upgrade.v9_5_0.
				CommerceOrderItemUpgradeProcess());

		registry.register(
			"9.5.0", "9.5.1",
			new com.liferay.commerce.internal.upgrade.v9_5_1.
				CommercePermissionUpgradeProcess(
					_resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"9.5.1", "9.6.0",
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "customerCommerceOrderItemId LONG"));

		registry.register(
			"9.6.0", "9.6.1",
			new SupplierRoleUpgradeProcess(
				_companyLocalService, _resourcePermissionLocalService,
				_roleLocalService));

		registry.register(
			"9.6.1", "9.7.0",
			UpgradeProcessFactory.addColumns(
				CommerceOrderItemModelImpl.TABLE_NAME,
				"unitOfMeasureKey VARCHAR(75) null"));

		registry.register("9.7.0", "9.7.1", new DummyUpgradeProcess());

		registry.register(
			"9.7.1", "9.8.0",
			UpgradeProcessFactory.addColumns(
				CommerceShipmentItemModelImpl.TABLE_NAME,
				"unitOfMeasureKey VARCHAR(75) null"));

		registry.register(
			"9.8.0", "10.0.0",
			new com.liferay.commerce.internal.upgrade.v10_0_0.
				CommerceOrderItemUpgradeProcess());

		registry.register(
			"10.0.0", "10.0.1",
			new com.liferay.commerce.internal.upgrade.v10_0_1.
				CommercePermissionUpgradeProcess(
					_resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"10.0.1", "10.1.0",
			UpgradeProcessFactory.alterColumnType(
				CPDefinitionInventoryModelImpl.TABLE_NAME, "minStockQuantity",
				"BIGDECIMAL null"),
			UpgradeProcessFactory.alterColumnType(
				CPDefinitionInventoryModelImpl.TABLE_NAME, "minOrderQuantity",
				"BIGDECIMAL null"),
			UpgradeProcessFactory.alterColumnType(
				CPDefinitionInventoryModelImpl.TABLE_NAME, "maxOrderQuantity",
				"BIGDECIMAL null"),
			UpgradeProcessFactory.alterColumnType(
				CPDefinitionInventoryModelImpl.TABLE_NAME,
				"multipleOrderQuantity", "BIGDECIMAL null"));

		registry.register(
			"10.1.0", "10.2.0",
			UpgradeProcessFactory.addColumns(
				CommerceOrderItemModelImpl.TABLE_NAME,
				"UOMIncrementalOrderQuantity BIGDECIMAL"));

		registry.register(
			"10.2.0", "11.0.0",
			UpgradeProcessFactory.alterColumnName(
				CommerceOrderItemModelImpl.TABLE_NAME, "bookedQuantityId",
				"CIBookedQuantityId LONG"));

		registry.register(
			"11.0.0", "11.1.0",
			UpgradeProcessFactory.alterColumnType(
				CommerceOrderItemModelImpl.TABLE_NAME, "shippedQuantity",
				"BIGDECIMAL null"));

		registry.register(
			"11.1.0", "11.2.0",
			UpgradeProcessFactory.alterColumnType(
				CommerceShipmentItemModelImpl.TABLE_NAME, "quantity",
				"BIGDECIMAL null"));

		if (_log.isInfoEnabled()) {
			_log.info("Commerce upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceServiceUpgradeStepRegistrator.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

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
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}