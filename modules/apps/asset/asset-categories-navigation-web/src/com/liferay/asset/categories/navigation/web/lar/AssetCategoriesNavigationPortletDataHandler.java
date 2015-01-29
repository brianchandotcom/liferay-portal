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

package com.liferay.asset.categories.navigation.web.lar;

import com.liferay.asset.categories.navigation.web.constants.AssetCategoriesNavigationPortletKeys;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.util.Enumeration;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" +
			AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION
	},
	service = PortletDataHandler.class
)
public class AssetCategoriesNavigationPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public AssetCategoriesNavigationPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return updateExportPortletPreferences(
			portletDataContext, portletPreferences, portletId);
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return updateImportPortletPreferences(
			portletDataContext, portletId, portletPreferences);
	}

	protected PortletPreferences updateExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String portletId)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				ExportImportHelperUtil.updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					AssetVocabulary.class.getName());
			}
		}

		return portletPreferences;
	}

	protected PortletPreferences updateImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyById(
			portletDataContext.getCompanyId());

		Group companyGroup = company.getGroup();

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				ExportImportHelperUtil.updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
		}

		return portletPreferences;
	}

}