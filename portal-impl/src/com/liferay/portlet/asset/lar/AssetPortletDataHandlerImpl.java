/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Jonathan Potter
 */
public class AssetPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, long companyId)
		throws Exception {

		AssetCategoryLocalServiceUtil.deleteCategoriesByCompany(companyId);
		AssetCategoryPropertyLocalServiceUtil.deleteCategoryPropertiesByCompany(
			companyId);
		AssetEntryLocalServiceUtil.deleteEntriesByCompany(companyId);
		AssetLinkLocalServiceUtil.deleteLinksByCompany(companyId);
		AssetTagLocalServiceUtil.deleteTagsByCompany(companyId);
		AssetTagPropertyLocalServiceUtil.deleteTagPropertiesByCompany(
			companyId);
		AssetVocabularyLocalServiceUtil.deleteVocabulariesByCompany(companyId);

		return null;
	}

}