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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.LinkedList;
import java.util.List;
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

			exportAddresses(portletDataContext, exportedOrganization);

			exportEmailAddresses(portletDataContext, exportedOrganization);

			exportPhones(portletDataContext, exportedOrganization);

			exportOrgLabors(portletDataContext, exportedOrganization);

			exportWebsites(portletDataContext, exportedOrganization);

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

		Organization existingOrganization =
			OrganizationLocalServiceUtil.fetchOrganizationByUuidAndCompanyId(
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

		long importedOrganizationId = importedOrganization.getOrganizationId();

		List<Address> addresses = importAddresses(
			portletDataContext, organization);

		List<EmailAddress> emailAddresses = importEmailAddresses(
			portletDataContext, organization);

		List<OrgLabor> orgLabors = importOrgLabors(
			portletDataContext, organization);

		List<Phone> phones = importPhones(portletDataContext, organization);

		List<Website> websites = importWebsites(
			portletDataContext, organization);

		UsersAdminUtil.updateAddresses(
			Organization.class.getName(), importedOrganizationId, addresses);

		UsersAdminUtil.updateEmailAddresses(
			Organization.class.getName(), importedOrganizationId,
			emailAddresses);

		UsersAdminUtil.updateOrgLabors(importedOrganizationId, orgLabors);

		UsersAdminUtil.updatePhones(
			Organization.class.getName(), importedOrganizationId, phones);

		UsersAdminUtil.updateWebsites(
			Organization.class.getName(), importedOrganizationId, websites);

		portletDataContext.importClassedModel(
			organization, importedOrganization,
			UsersAdminPortletDataHandler.NAMESPACE);
	}

	protected void exportAddresses(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException, SystemException {

		List<Address> addresses = AddressLocalServiceUtil.getAddresses(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		String addressXML = portletDataContext.toXML(addresses);

		String path = getDependentModelPath(organization, Address.class);

		portletDataContext.addZipEntry(path, addressXML);
	}

	protected void exportEmailAddresses(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException, SystemException {

		List<EmailAddress> emailAddresses =
			EmailAddressLocalServiceUtil.getEmailAddresses(
				organization.getCompanyId(), organization.getModelClassName(),
				organization.getOrganizationId());

		String emailAddressXML = portletDataContext.toXML(emailAddresses);

		String path = getDependentModelPath(organization, EmailAddress.class);

		portletDataContext.addZipEntry(path, emailAddressXML);
	}

	protected void exportOrgLabors(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException, SystemException {

		List<OrgLabor> orgLabors = OrgLaborLocalServiceUtil.getOrgLabors(
			organization.getOrganizationId());

		String orgLaborsXML = portletDataContext.toXML(orgLabors);

		String path = getDependentModelPath(organization, OrgLabor.class);

		portletDataContext.addZipEntry(path, orgLaborsXML);
	}

	protected void exportPhones(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException, SystemException {

		List<Phone> phones = PhoneLocalServiceUtil.getPhones(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		String phonesXML = portletDataContext.toXML(phones);

		String path = getDependentModelPath(organization, Phone.class);

		portletDataContext.addZipEntry(path, phonesXML);

	}

	protected void exportWebsites(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException, SystemException {

		List<Website> websites = WebsiteLocalServiceUtil.getWebsites(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		String websitesXML = portletDataContext.toXML(websites);

		String path = getDependentModelPath(organization, Website.class);

		portletDataContext.addZipEntry(path, websitesXML);
	}

	protected String getDependentModelPath(
		Organization organization, Class modelClass) {

		return ExportImportPathUtil.getModelPath(
			organization, modelClass.getSimpleName().concat(".xml"));
	}

	protected List<Address> importAddresses(
		PortletDataContext portletDataContext, Organization organization) {

		String path = getDependentModelPath(organization, Address.class);

		List<Address> rawEntries =
			(List<Address>)portletDataContext.getZipEntryAsObject(path);

		for (Address address : rawEntries) {
			address.setAddressId(0);
		}

		return rawEntries;
	}

	protected List<EmailAddress> importEmailAddresses(
		PortletDataContext portletDataContext, Organization organization) {

		String path = getDependentModelPath(organization, EmailAddress.class);

		List<EmailAddress> rawEntries =
			(List<EmailAddress>)portletDataContext.getZipEntryAsObject(path);

		for (EmailAddress emailAddress : rawEntries) {
			emailAddress.setEmailAddressId(0);
		}

		return rawEntries;
	}

	protected List<OrgLabor> importOrgLabors(
		PortletDataContext portletDataContext, Organization organization) {

		String path = getDependentModelPath(organization, OrgLabor.class);

		List<OrgLabor> rawEntries =
			(List<OrgLabor>)portletDataContext.getZipEntryAsObject(path);

		for (OrgLabor orgLabor : rawEntries) {
			orgLabor.setOrgLaborId(0);
		}

		return rawEntries;
	}

	protected List<Phone> importPhones(
		PortletDataContext portletDataContext, Organization organization) {

		String path = getDependentModelPath(organization, Phone.class);

		List<Phone> rawEntries =
			(List<Phone>)portletDataContext.getZipEntryAsObject(path);

		for (Phone phone : rawEntries) {
			phone.setPhoneId(0);
		}

		return rawEntries;
	}

	protected List<Website> importWebsites(
		PortletDataContext portletDataContext, Organization organization) {

		String path = getDependentModelPath(organization, Website.class);

		List<Website> rawEntries =
			(List<Website>)portletDataContext.getZipEntryAsObject(path);

		for (Website website : rawEntries) {
			website.setWebsiteId(0);
		}

		return rawEntries;
	}

}