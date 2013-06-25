/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetcategoriesnavigation.lar;

import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.util.Enumeration;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class AssetCategoriesNavitagionPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public AssetCategoriesNavitagionPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	protected PortletPreferences updateExportPortletPreferences(
			PortletPreferences portletPreferences, long plid)
		throws Exception {

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				ExportImportHelperUtil.updateExportPreferencesClassPKs(
					portletPreferences, name, AssetVocabulary.class.getName());
			}
		}

		return portletPreferences;
	}

	protected PortletPreferences updateImportPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long ownerId,
			int ownerType, long plid, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		Group companyGroup = company.getGroup();

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				ExportImportHelperUtil.updateImportPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetCategoriesNavitagionPortletDataHandler.class);

}