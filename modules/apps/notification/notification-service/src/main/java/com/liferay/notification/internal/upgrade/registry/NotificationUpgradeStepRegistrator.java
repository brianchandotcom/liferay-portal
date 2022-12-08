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

package com.liferay.notification.internal.upgrade.registry;

import com.liferay.notification.internal.upgrade.v1_1_0.util.NotificationQueueEntryAttachmentTable;
import com.liferay.notification.internal.upgrade.v1_1_0.util.NotificationTemplateAttachmentTable;
import com.liferay.notification.internal.upgrade.v1_2_0.NotificationQueueEntryUpgradeProcess;
import com.liferay.notification.internal.upgrade.v2_1_0.NotificationTemplateUpgradeProcess;
import com.liferay.notification.internal.upgrade.v3_4_0.NotificationRecipientUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(service = UpgradeStepRegistrator.class)
public class NotificationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0", NotificationQueueEntryAttachmentTable.create(),
			NotificationTemplateAttachmentTable.create(),
			UpgradeProcessFactory.addColumns(
				"NotificationTemplate", "objectDefinitionId LONG"));

		registry.register(
			"1.1.0", "1.2.0", new NotificationQueueEntryUpgradeProcess());

		registry.register(
			"1.2.0", "2.0.0",
			UpgradeProcessFactory.dropColumns(
				"NotificationQueueEntry", "sent"));

		registry.register(
			"2.0.0", "2.1.0", new NotificationTemplateUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.notification.internal.upgrade.v2_2_0.
				NotificationQueueEntryUpgradeProcess());

		registry.register(
			"2.2.0", "3.0.0",
			new com.liferay.notification.internal.upgrade.v3_0_0.
				NotificationRecipientUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.notification.internal.upgrade.v3_1_0.
				NotificationTemplateUpgradeProcess());

		registry.register(
			"3.1.0", "3.2.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"NotificationTemplate", "notificationTemplateId"}
					};
				}

			});

		registry.register(
			"3.2.0", "3.3.0",
			UpgradeProcessFactory.dropColumns(
				"NotificationRecipient", "className"),
			UpgradeProcessFactory.addColumns(
				"NotificationRecipient", "classNameId LONG"));

		registry.register(
			"3.3.0", "3.4.0", new NotificationRecipientUpgradeProcess());
	}

}