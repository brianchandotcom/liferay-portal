/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image.internal.upgrade.registry;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.image.internal.upgrade.v1_0_0.ImageStorageUpgradeProcess;
import com.liferay.image.upgrade.ImageCompanyIdUpgradeProcess;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = UpgradeStepRegistrator.class)
public class ImageServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "0.0.2",
			new ImageCompanyIdUpgradeProcess<>(
				_companyLocalService::getActionableDynamicQuery,
				Company::getCompanyId, null, Company::getLogoId));

		registry.register(
			"0.0.2", "0.0.3",
			new ImageCompanyIdUpgradeProcess<>(
				_ddmTemplateLocalService::getActionableDynamicQuery,
				DDMTemplate::getCompanyId, null, DDMTemplate::getSmallImageId));

		registry.register(
			"0.0.3", "0.0.4",
			new ImageCompanyIdUpgradeProcess<>(
				_layoutLocalService::getActionableDynamicQuery,
				Layout::getCompanyId, Layout::getIconImageERC, null));

		registry.register(
			"0.0.4", "0.0.5",
			new ImageCompanyIdUpgradeProcess<>(
				_layoutSetLocalService::getActionableDynamicQuery,
				LayoutSet::getCompanyId, null, LayoutSet::getLogoId));

		registry.register(
			"0.0.5", "0.0.6",
			new ImageCompanyIdUpgradeProcess<>(
				_layoutSetBranchLocalService::getActionableDynamicQuery,
				LayoutSetBranch::getCompanyId, null,
				LayoutSetBranch::getLogoId));

		registry.register(
			"0.0.6", "0.0.7",
			new ImageCompanyIdUpgradeProcess<>(
				_layoutSetBranchLocalService::getActionableDynamicQuery,
				LayoutSetBranch::getCompanyId, null,
				LayoutSetBranch::getLiveLogoId));

		registry.register(
			"0.0.7", "1.0.0",
			new ImageStorageUpgradeProcess(_imageLocalService, _store));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.journal.service)(release.schema.version>=1.1.0))"
	)
	private Release _journalServiceRelease;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.layout.service)(release.schema.version>=7.0.0))"
	)
	private Release _layoutServiceRelease;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference(target = "(default=true)")
	private Store _store;

}