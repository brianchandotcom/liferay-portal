/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.upgrade.registry;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.document.library.internal.upgrade.v1_0_17.AssetVocabularyUpgradeProcess;
import com.liferay.content.dashboard.document.library.internal.upgrade.v1_0_18.AssetVocabularyERCUpgradeProcess;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = UpgradeStepRegistrator.class)
public class ContentDashboardDocumentLibraryImplUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.1", new DummyUpgradeStep());

		registry.register("1.0.1", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"1.0.2", "1.0.17",
			new AssetVocabularyUpgradeProcess(
				_assetVocabularyLocalService, _classNameLocalService,
				_companyLocalService));

		registry.register(
			"1.0.17", "1.0.18",
			new AssetVocabularyERCUpgradeProcess(
				_assetVocabularyLocalService, _companyLocalService,
				_groupLocalService, _portletPreferencesLocalService));
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}