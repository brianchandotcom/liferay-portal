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

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author David Mendez Gonzalez
 */
public class OrganizationStagedModelDataHandler
	extends BaseStagedModelDataHandler<Organization> {

	public static final String[] CLASS_NAMES = {Organization.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Organization organization)
		throws Exception {

		Queue<Organization> organizations = new LinkedList<Organization>();

		organizations.add(organization);

		while (!organizations.isEmpty()) {
			Organization exportedOrganization = organizations.remove();

			Element organizationElement =
				portletDataContext.getExportDataElement(exportedOrganization);

			portletDataContext.addClassedModel(
				organizationElement,
				ExportImportPathUtil.getModelPath(exportedOrganization),
				exportedOrganization, UsersAdminPortletDataHandler.NAMESPACE);

			organizations.addAll(exportedOrganization.getSuborganizations());
		}
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Organization organization)
		throws Exception {

		long userId = portletDataContext.getUserId(organization.getUserUuid());

		Map<Long, Long> organizationIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Organization.class);

		long parentOrganizationId = MapUtil.getLong(
			organizationIds, organization.getParentOrganizationId(),
			organization.getParentOrganizationId());

		if ((parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) &&
			(parentOrganizationId == organization.getParentOrganizationId())) {

			String parentOrganizationPath = ExportImportPathUtil.getModelPath(
				portletDataContext, Organization.class.getName(),
				parentOrganizationId);

			Organization parentOrganization =
				(Organization)portletDataContext.getZipEntryAsObject(
					parentOrganizationPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, parentOrganization);

			parentOrganizationId = MapUtil.getLong(
				organizationIds, organization.getParentOrganizationId(),
				organization.getParentOrganizationId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			organization, UsersAdminPortletDataHandler.NAMESPACE);

		serviceContext.setUserId(userId);

		Organization existingOrganization = OrganizationLocalServiceUtil
			.fetchOrganizationByUuidAndCompanyId(
				organization.getUuid(), portletDataContext.getCompanyId());

		if (existingOrganization == null) {
			existingOrganization =
				OrganizationLocalServiceUtil.fetchOrganization(
					portletDataContext.getCompanyId(), organization.getName());
		}

		Organization importedOrganization = null;

		if (existingOrganization == null) {
			serviceContext.setUuid(organization.getUuid());

			importedOrganization =
				OrganizationLocalServiceUtil.addOrganization(
					userId, parentOrganizationId, organization.getName(),
					organization.getType(), organization.getRegionId(),
					organization.getCountryId(), organization.getStatusId(),
					organization.getComments(), false, serviceContext);
		}
		else {
			importedOrganization =
				OrganizationLocalServiceUtil.updateOrganization(
					portletDataContext.getCompanyId(),
					existingOrganization.getOrganizationId(),
					parentOrganizationId, organization.getName(),
					organization.getType(), organization.getRegionId(),
					organization.getCountryId(), organization.getStatusId(),
					organization.getComments(), false, serviceContext);
		}

		portletDataContext.importClassedModel(
			organization, importedOrganization,
			UsersAdminPortletDataHandler.NAMESPACE);
	}

}