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

package com.liferay.portlet.usersadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.persistence.OrganizationExportActionableDynamicQuery;
import com.liferay.portal.util.PortletKeys;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author David Gonzalez
 */
public class UsersAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "users_admin";

	public UsersAdminPortletDataHandler() {
		super();

		setDataLevel(DataLevel.PORTAL);

		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "organizations", true, true));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getOrganizations(
				portletDataContext.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID);

		for (Organization organization : organizations) {
			OrganizationLocalServiceUtil.deleteOrganization(organization);
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			PortletKeys.PORTAL, portletDataContext.getCompanyId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			new OrganizationExportActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			PortletKeys.PORTAL, portletDataContext.getSourceCompanyId(),
			portletDataContext.getCompanyId());

		Element organizationsElement =
			portletDataContext.getImportDataGroupElement(Organization.class);

		List<Element> organizationElements = organizationsElement.elements();

		for (Element organizationElement : organizationElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, organizationElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ActionableDynamicQuery actionableDynamicQuery =
			new OrganizationExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			Organization.class, actionableDynamicQuery.performCount());
	}

}