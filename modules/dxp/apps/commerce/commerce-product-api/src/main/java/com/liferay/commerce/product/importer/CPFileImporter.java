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

package com.liferay.commerce.product.importer;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPFileImporter {

	public void cleanLayouts(ServiceContext serviceContext)
		throws PortalException;

	public void createJournalArticles(
			JSONArray journalArticleJSONArray, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext,
			ThemeDisplay themeDisplay)
		throws Exception;

	public void createLayouts(
			JSONArray jsonArray, Layout parentLayout, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext)
		throws Exception;

	public void updateLookAndFeel(String themeId, ServiceContext serviceContext)
		throws PortalException;

}