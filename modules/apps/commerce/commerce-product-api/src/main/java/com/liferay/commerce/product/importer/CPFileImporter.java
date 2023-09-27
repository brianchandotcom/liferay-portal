/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.importer;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CPFileImporter {

	public void cleanLayouts(ServiceContext serviceContext)
		throws PortalException;

	public void createJournalArticles(
			JSONArray journalArticleJSONArray, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext)
		throws Exception;

	public void createLayouts(
			JSONArray jsonArray, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext)
		throws Exception;

	public void createRoles(JSONArray jsonArray, ServiceContext serviceContext)
		throws PortalException;

	public DDMTemplate getDDMTemplate(
			File file, long classNameId, long classPK, long resourceClassNameId,
			String name, String type, String mode, String language,
			ServiceContext serviceContext)
		throws Exception;

	public void updateLogo(
			File file, boolean privateLayout, boolean logo,
			ServiceContext serviceContext)
		throws PortalException;

	public void updateLookAndFeel(
			String themeId, boolean privateLayout,
			ServiceContext serviceContext)
		throws PortalException;

}