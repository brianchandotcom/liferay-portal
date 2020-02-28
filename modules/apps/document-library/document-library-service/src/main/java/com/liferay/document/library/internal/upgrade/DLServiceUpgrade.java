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
import com.liferay.document.library.internal.upgrade.v1_0_0.UpgradeDocumentLibrary;
import com.liferay.document.library.internal.upgrade.v1_0_1.UpgradeDLConfiguration;
import com.liferay.document.library.internal.upgrade.v1_0_1.UpgradeDLFileEntryConfiguration;
import com.liferay.document.library.internal.upgrade.v1_0_2.UpgradeDLFileShortcut;
import com.liferay.document.library.internal.upgrade.v1_1_0.UpgradeSchema;
import com.liferay.document.library.internal.upgrade.v2_0_0.UpgradeCompanyId;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeViewCount;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(service = UpgradeStepRegistrator.class)
public class DLServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new UpgradeDocumentLibrary(_store));

		registry.register("1.0.0", "1.0.1", new UpgradeDLFileShortcut());

		registry.register(
			"1.0.1", "1.0.2",
			new UpgradeDLConfiguration(_prefsPropsToConfigurationUpgradeHelper),
			new UpgradeDLFileEntryConfiguration(
				_prefsPropsToConfigurationUpgradeHelper));

		registry.register("1.0.2", "1.1.0", new UpgradeSchema());

		registry.register("1.1.0", "1.1.1", new DummyUpgradeStep());

		registry.register("1.1.1", "2.0.0", new UpgradeCompanyId());

		registry.register(
			"2.0.0", "3.0.0",
			new UpgradeViewCount(
				"DlFileEntry", DLFileEntry.class, "fileEntryId", "readCount"));

		registry.register(
			"3.0.0", "3.0.1",
			new UpgradeDiscussionSubscriptionClassName(
				_subscriptionLocalService, DLFileEntry.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.
					DELETE_OLD));
	}

	@Reference
	private PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

	@Reference(target = "(dl.store.upgrade=true)")
	private Store _store;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}