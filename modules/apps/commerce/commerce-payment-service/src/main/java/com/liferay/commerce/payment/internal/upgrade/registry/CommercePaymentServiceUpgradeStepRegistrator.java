/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.upgrade.registry;

import com.liferay.commerce.payment.internal.upgrade.v1_0_1.CommercePaymentMethodGroupRelUpgradeProcess;
import com.liferay.commerce.payment.internal.upgrade.v1_2_0.util.CommercePaymentMethodGroupRelQualifierTable;
import com.liferay.commerce.payment.internal.upgrade.v1_4_0.CommercePaymentEntryUpgradeProcess;
import com.liferay.commerce.payment.internal.upgrade.v1_4_0.util.CommercePaymentEntryAuditTable;
import com.liferay.commerce.payment.internal.upgrade.v1_4_0.util.CommercePaymentEntryTable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = UpgradeStepRegistrator.class)
public class CommercePaymentServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce payment upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.0.1",
			new CommercePaymentMethodGroupRelUpgradeProcess(
				_classNameLocalService, _groupLocalService));

		registry.register(
			"1.0.1", "1.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"CommercePaymentMethodGroupRel"};
				}

			});

		registry.register(
			"1.1.0", "1.2.0",
			CommercePaymentMethodGroupRelQualifierTable.create());

		registry.register(
			"1.2.0", "1.3.0",
			new com.liferay.commerce.payment.internal.upgrade.v1_3_0.
				CommercePaymentMethodGroupRelUpgradeProcess(
					_resourceActionLocalService, _resourceLocalService));

		registry.register(
			"1.3.0", "1.4.0", CommercePaymentEntryTable.create(),
			CommercePaymentEntryAuditTable.create(),
			new CommercePaymentEntryUpgradeProcess(
				_resourceActionLocalService));

		registry.register(
			"1.4.0", "1.5.0",
			UpgradeProcessFactory.alterColumnName(
				"CommercePaymentEntry", "paymentMethodName",
				"paymentIntegrationKey VARCHAR(75) null"),
			UpgradeProcessFactory.alterColumnType(
				"CommercePaymentEntry", "transactionCode", "VARCHAR(255) null"),
			UpgradeProcessFactory.addColumns(
				"CommercePaymentEntry", "callbackURL TEXT null",
				"commerceChannelId LONG", "paymentIntegrationType INTEGER",
				"redirectURL TEXT null"),
			UpgradeProcessFactory.alterColumnName(
				"CommercePaymentMethodGroupRel", "engineKey",
				"paymentIntegrationKey VARCHAR(75) null"),
			UpgradeProcessFactory.addColumns(
				"CommercePaymentMethodGroupRel", "typeSettings TEXT null"));

		if (_log.isInfoEnabled()) {
			_log.info("Commerce payment upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentServiceUpgradeStepRegistrator.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}