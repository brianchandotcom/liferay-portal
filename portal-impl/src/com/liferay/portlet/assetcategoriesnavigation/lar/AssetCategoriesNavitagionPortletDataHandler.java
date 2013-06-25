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
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.util.Enumeration;

/**
 * @author Julio Camarero
 */
public class AssetCategoriesNavitagionPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public AssetCategoriesNavitagionPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	protected String updateExportAssetCategoriesNavigationPortletPreferences(
			String xml, long plid)
		throws Exception {

		javax.portlet.PortletPreferences jxPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(xml);

		Enumeration<String> enu = jxPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				ExportImportHelperUtil.updateExportPreferencesClassPKs(
					jxPreferences, name, AssetVocabulary.class.getName());
			}
		}

		return PortletPreferencesFactoryUtil.toXML(jxPreferences);
	}

	protected String updateImportAssetCategoriesNavigationPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long ownerId,
			int ownerType, long plid, String portletId, String xml)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		Group companyGroup = company.getGroup();

		javax.portlet.PortletPreferences jxPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		Enumeration<String> enu = jxPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				ExportImportHelperUtil.updateImportPreferencesClassPKs(
					portletDataContext, jxPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
		}

		return PortletPreferencesFactoryUtil.toXML(jxPreferences);
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetCategoriesNavitagionPortletDataHandler.class);

}