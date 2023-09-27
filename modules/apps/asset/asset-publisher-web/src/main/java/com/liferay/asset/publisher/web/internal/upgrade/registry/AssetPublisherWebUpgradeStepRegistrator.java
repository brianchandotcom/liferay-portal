/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.upgrade.registry;

import com.liferay.asset.publisher.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.asset.publisher.web.internal.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = UpgradeStepRegistrator.class)
public class AssetPublisherWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0", new UpgradePortletId(),
			new UpgradePortletPreferences(
				_ddmStructureLocalService, _ddmStructureLinkLocalService,
				_saxReader));

		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.asset.publisher.web.internal.upgrade.v1_0_1.
				UpgradePortletPreferences(_saxReader));

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.asset.publisher.web.internal.upgrade.v1_0_2.
				UpgradePortletPreferences());

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.asset.publisher.web.internal.upgrade.v1_0_3.
				UpgradePortletPreferences());

		registry.register(
			"1.0.3", "1.0.4",
			new com.liferay.asset.publisher.web.internal.upgrade.v1_0_4.
				UpgradePortletPreferences());

		registry.register(
			"1.0.4", "1.0.5",
			new com.liferay.asset.publisher.web.internal.upgrade.v1_0_5.
				UpgradePortletPreferences());
	}

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private SAXReader _saxReader;

}