/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			JSONArray jsonArray, boolean privateLayout, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext)
		throws Exception;

	public void createRoles(JSONArray jsonArray, ServiceContext serviceContext)
		throws PortalException;

	public DDMTemplate getDDMTemplate(
			File file, long classNameId, long classPK, long resourceClassNameId,
			String name, String type, String mode, String language,
			ServiceContext serviceContext)
		throws Exception;

	public void updateLookAndFeel(String themeId, ServiceContext serviceContext)
		throws PortalException;

}