/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.image.service.internal.upgrade.registry;

import com.liferay.image.upgrade.ImageCompanyIdUpgradeProcess;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(enabled = false, service = UpgradeStepRegistrator.class)
public class JournalImageServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new ImageCompanyIdUpgradeProcess<>(
				_journalArticleLocalService::getActionableDynamicQuery,
				JournalArticle::getCompanyId, JournalArticle::getSmallImageId));
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}