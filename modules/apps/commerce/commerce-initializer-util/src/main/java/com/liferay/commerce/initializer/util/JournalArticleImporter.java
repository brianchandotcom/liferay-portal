/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Steven Smith
 */
@Component(service = JournalArticleImporter.class)
public class JournalArticleImporter {

	public void importJournalArticles(
			JSONArray jsonArray, ClassLoader classLoader,
			String imageDependenciesPath, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setTimeZone(TimeZoneUtil.getDefault());
		serviceContext.setUserId(userId);

		_cpFileImporter.createJournalArticles(
			jsonArray, classLoader, imageDependenciesPath, serviceContext);
	}

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private UserLocalService _userLocalService;

}