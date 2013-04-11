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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
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

	@Override
	public String getClassName() {
		return Organization.class.getName();
	}

	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Organization organization)
		throws Exception {

		Queue<Organization> organizationQueue = new LinkedList<Organization>();
		organizationQueue.add(organization);

		while (!organizationQueue.isEmpty()) {

			Organization exportingOrganization = organizationQueue.remove();

			Element organizationElement =
				portletDataContext.getExportDataStagedModelElement(
					exportingOrganization);

			portletDataContext.addClassedModel(
				organizationElement,
				StagedModelPathUtil.getPath(exportingOrganization),
				exportingOrganization, UsersAdminPortletDataHandler.NAMESPACE);

			organizationQueue.addAll(
				exportingOrganization.getSuborganizations());
		}

	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Organization organization)
		throws Exception {

		long userId = portletDataContext.getUserId(organization.getUserUuid());

		long companyId = portletDataContext.getCompanyId();

		Map<Long, Long> organizationIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Organization.class);

		long parentOrganizationId = MapUtil.getLong(
			organizationIds, organization.getParentOrganizationId(),
			organization.getParentOrganizationId());

		if ((parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) &&
			(parentOrganizationId == organization.getParentOrganizationId())) {

			String parentOrganizationPath = StagedModelPathUtil.getPath(
				portletDataContext, getClassName(), parentOrganizationId);

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
				organization.getUuid(), companyId);

		if (existingOrganization == null) {
			existingOrganization =
				OrganizationLocalServiceUtil.fetchOrganization(
					companyId, organization.getName());
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
					companyId, existingOrganization.getOrganizationId(),
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