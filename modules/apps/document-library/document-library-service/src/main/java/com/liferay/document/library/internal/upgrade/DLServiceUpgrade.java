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

package com.liferay.document.library.internal.upgrade;

import com.liferay.comment.upgrade.UpgradeDiscussionSubscriptionClassName;
import com.liferay.document.library.internal.upgrade.v1_0_0.DocumentLibraryUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v1_0_1.DLConfigurationUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v1_0_1.DLFileEntryConfigurationUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v1_0_2.DLFileShortcutUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v1_1_0.SchemaUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v1_1_2.DLFileEntryTypeUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v2_0_0.UpgradeCompanyId;
import com.liferay.document.library.internal.upgrade.v3_2_1.DDMStructureLinkUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v3_2_2.DLFileEntryUpgradeProcess;
import com.liferay.document.library.internal.upgrade.v3_2_4.UpgradeDLSizeLimitConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.ViewCountUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(service = UpgradeStepRegistrator.class)
public class DLServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0",
			new DocumentLibraryUpgradeProcess(_storeFactory.getStore()));

		registry.register("1.0.0", "1.0.1", new DLFileShortcutUpgradeProcess());

		registry.register(
			"1.0.1", "1.0.2",
			new DLConfigurationUpgradeProcess(
				_prefsPropsToConfigurationUpgradeHelper),
			new DLFileEntryConfigurationUpgradeProcess(
				_prefsPropsToConfigurationUpgradeHelper));

		registry.register("1.0.2", "1.1.0", new SchemaUpgradeProcess());

		registry.register("1.1.0", "1.1.1", new DummyUpgradeStep());

		registry.register(
			"1.1.1", "1.1.2",
			new DLFileEntryTypeUpgradeProcess(_resourceLocalService));

		registry.register("1.1.2", "2.0.0", new UpgradeCompanyId());

		registry.register(
			"2.0.0", "3.0.0",
			new ViewCountUpgradeProcess(
				"DLFileEntry", DLFileEntry.class, "fileEntryId", "readCount"));

		registry.register(
			"3.0.0", "3.0.1",
			new UpgradeDiscussionSubscriptionClassName(
				_classNameLocalService, _subscriptionLocalService,
				DLFileEntry.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.UPDATE));

		registry.register(
			"3.0.1", "3.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"DLFileVersionPreview"};
				}

			},
			new CTModelUpgradeProcess("DLFileVersionPreview"));

		registry.register("3.1.0", "3.1.1", new DummyUpgradeStep());

		registry.register(
			"3.1.1", "3.2.0",
			new com.liferay.document.library.internal.upgrade.v3_2_0.
				SchemaUpgradeProcess(),
			new com.liferay.document.library.internal.upgrade.v3_2_0.
				StorageQuotaUpgradeProcess());

		registry.register(
			"3.2.0", "3.2.1", new DDMStructureLinkUpgradeProcess(),
			new com.liferay.document.library.internal.upgrade.v3_2_1.
				UpgradeDLFileEntryType());

		registry.register("3.2.1", "3.2.2", new DummyUpgradeStep());

		registry.register(
			"3.2.2", "3.2.3",
			new DLFileEntryUpgradeProcess(_classNameLocalService));

		registry.register(
			"3.2.3", "3.2.4",
			new UpgradeDLSizeLimitConfiguration(_configurationAdmin));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}