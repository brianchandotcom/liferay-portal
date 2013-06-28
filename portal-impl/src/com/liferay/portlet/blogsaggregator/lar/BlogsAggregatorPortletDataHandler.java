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

package com.liferay.portlet.blogsaggregator.lar;

import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class BlogsAggregatorPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public BlogsAggregatorPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, Element rootElement)
		throws Exception {

		return updateExportPortletPreferences(
			portletDataContext, portletId, portletPreferences, rootElement);
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
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, Element rootElement)
		throws Exception {

		long organizationId = GetterUtil.getLong(
			portletPreferences.getValue("organizationId", null));

		if (organizationId > 0) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			ExportImportHelperUtil.updateExportPortletPreferencesClassPKs(
				portletDataContext, portlet, portletPreferences,
				"organizationId", Organization.class.getName(), rootElement);
		}

		return portletPreferences;
	}

	protected PortletPreferences updateImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long organizationId = GetterUtil.getLong(
			portletPreferences.getValue("organizationId", null));

		if (organizationId > 0) {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				portletDataContext.getCompanyId());

			Group companyGroup = company.getGroup();

			ExportImportHelperUtil.updateImportPortletPreferencesClassPKs(
				portletDataContext, portletPreferences, "organizationId",
				Organization.class, companyGroup.getGroupId());
		}

		return portletPreferences;
	}

}