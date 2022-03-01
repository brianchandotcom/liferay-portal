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

package com.liferay.commerce.notification.internal.upgrade;

import com.liferay.commerce.notification.internal.upgrade.v2_0_0.util.CommerceNotificationTemplateCommerceAccountGroupRelTable;
import com.liferay.commerce.notification.internal.upgrade.v2_1_0.CommerceNotificationQueueEntryUpgradeProcess;
import com.liferay.commerce.notification.internal.upgrade.v2_2_0.CommerceNotificationTemplateUpgradeProcess;
import com.liferay.commerce.notification.internal.upgrade.v2_2_1.CommerceNotificationTemplateGroupIdUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceNotificationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce notification upgrade step registrator started");
		}

		registry.register("1.0.0", "1.1.0", new DummyUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			CommerceNotificationTemplateCommerceAccountGroupRelTable.create());

		registry.register(
			"2.0.0", "2.1.0",
			new CommerceNotificationQueueEntryUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0", new CommerceNotificationTemplateUpgradeProcess());

		registry.register(
			"2.2.0", "2.2.1",
			new CommerceNotificationTemplateGroupIdUpgradeProcess(
				_classNameLocalService, _groupLocalService));

		registry.register(
			"2.2.1", "2.3.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CNTemplateCAccountGroupRel", "CNotificationAttachment",
						"CommerceNotificationQueueEntry",
						"CommerceNotificationTemplate"
					};
				}

			});

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce notification upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationUpgradeStepRegistrator.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}