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
import com.liferay.portal.model.PasswordPolicy;
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

import java.util.ArrayList;
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
	public String getDisplayName(Organization organization) {
		return organization.getName();
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

			exportAddresses(
				organizationElement, portletDataContext, exportedOrganization);
			exportEmailAddresses(
				organizationElement, portletDataContext, exportedOrganization);
			exportOrgLabors(portletDataContext, exportedOrganization);
			exportPasswordPolicyRel(
				organizationElement, portletDataContext, exportedOrganization);
			exportPhones(
				organizationElement, portletDataContext, exportedOrganization);
			exportWebsites(
				organizationElement, portletDataContext, exportedOrganization);

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

		importAddresses(portletDataContext, importedOrganization, organization);
		importEmailAddresses(
			portletDataContext, importedOrganization, organization);
		importOrgLabors(portletDataContext, importedOrganization, organization);
		importPasswordPolicyRel(
			portletDataContext, importedOrganization, organization);
		importPhones(portletDataContext, importedOrganization, organization);
		importWebsites(portletDataContext, importedOrganization, organization);

		portletDataContext.importClassedModel(
			organization, importedOrganization,
			UsersAdminPortletDataHandler.NAMESPACE);
	}

	protected void exportAddresses(
			Element element, PortletDataContext portletDataContext,
			Organization organization)
		throws PortalException, SystemException {

		List<Address> addresses = AddressLocalServiceUtil.getAddresses(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		for (Address address : addresses) {

			portletDataContext.addReferenceElement(
				organization, element, address,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED, false);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, address);
		}
	}

	protected void exportEmailAddresses(
			Element element, PortletDataContext portletDataContext,
			Organization organization)
		throws PortalException, SystemException {

		List<EmailAddress> emailAddresses =
			EmailAddressLocalServiceUtil.getEmailAddresses(
				organization.getCompanyId(), organization.getModelClassName(),
				organization.getOrganizationId());

		for (EmailAddress emailAddress : emailAddresses) {

			portletDataContext.addReferenceElement(
				organization, element, emailAddress,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED, false);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, emailAddress);
		}
	}

	protected void exportOrgLabors(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException, SystemException {

		List<OrgLabor> orgLabors = OrgLaborLocalServiceUtil.getOrgLabors(
			organization.getOrganizationId());

		String path = ExportImportPathUtil.getModelPath(
			organization, OrgLabor.class.getSimpleName());

		portletDataContext.addZipEntry(path, orgLabors);
	}

	protected void exportPasswordPolicyRel(
			Element organizationElement, PortletDataContext portletDataContext,
			Organization organization)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy = organization.getPasswordPolicy();

		if (passwordPolicy == null) {
			return;
		}

		portletDataContext.addReferenceElement(
			organization, organizationElement, passwordPolicy,
			PortletDataContext.REFERENCE_TYPE_STRONG, false);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, passwordPolicy);
	}

	protected void exportPhones(
			Element organizationElement, PortletDataContext portletDataContext,
			Organization organization)
		throws PortalException, SystemException {

		List<Phone> phones = PhoneLocalServiceUtil.getPhones(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		for (Phone phone : phones) {

			portletDataContext.addReferenceElement(
				organization, organizationElement, phone,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED, false);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, phone);
		}
	}

	protected void exportWebsites(
			Element organizationElement, PortletDataContext portletDataContext,
			Organization organization)
		throws PortalException, SystemException {

		List<Website> websites = WebsiteLocalServiceUtil.getWebsites(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		for (Website website : websites) {

			portletDataContext.addReferenceElement(
				organization, organizationElement, website,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED, false);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, website);
		}
	}

	protected void importAddresses(
			PortletDataContext portletDataContext,
			Organization importedOrganization, Organization organization)
		throws PortalException, SystemException {

		List<Element> addressElements =
			portletDataContext.getReferenceDataElements(
				organization, Address.class);

		List<Address> addresses = new ArrayList<Address>(
			addressElements.size());

		for (Element addressElement : addressElements) {

			String path = addressElement.attributeValue("path");

			Address address = (Address)portletDataContext.getZipEntryAsObject(
				path);

			address.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, address);

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Address.class);

			long addressId = newPrimaryKeysMap.get(address.getPrimaryKey());

			address.setPrimaryKey(addressId);

			addresses.add(address);
		}

		UsersAdminUtil.updateAddresses(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), addresses);
	}

	protected void importEmailAddresses(
			PortletDataContext portletDataContext,
			Organization importedOrganization, Organization organization)
		throws PortalException, SystemException {

		List<Element> emailAddressElements =
			portletDataContext.getReferenceDataElements(
				organization, EmailAddress.class);

		List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>(
			emailAddressElements.size());

		for (Element emailAddressElement : emailAddressElements) {

			String path = emailAddressElement.attributeValue("path");

			EmailAddress emailAddress =
				(EmailAddress)portletDataContext.getZipEntryAsObject(path);

			emailAddress.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, emailAddress);

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					EmailAddress.class);

			long emailAddressId = newPrimaryKeysMap.get(
				emailAddress.getPrimaryKey());

			emailAddress.setPrimaryKey(emailAddressId);

			emailAddresses.add(emailAddress);
		}

		UsersAdminUtil.updateEmailAddresses(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), emailAddresses);
	}

	protected void importOrgLabors(
			PortletDataContext portletDataContext,
			Organization importedOrganization, Organization organization)
		throws PortalException, SystemException {

		String path = ExportImportPathUtil.getModelPath(
			organization, OrgLabor.class.getSimpleName());

		List<OrgLabor> orgLabors =
			(List<OrgLabor>)portletDataContext.getZipEntryAsObject(path);

		for (OrgLabor orgLabor : orgLabors) {
			orgLabor.setOrgLaborId(0);
		}

		UsersAdminUtil.updateOrgLabors(
			importedOrganization.getOrganizationId(), orgLabors);
	}

	protected void importPasswordPolicyRel(
			PortletDataContext portletDataContext,
			Organization importedOrganization, Organization organization)
		throws PortalException, SystemException {

		List<Element> passwordPolicyElements =
			portletDataContext.getReferenceDataElements(
				organization, PasswordPolicy.class);

		if (!passwordPolicyElements.isEmpty()) {

			Element passwordPolicyElement = passwordPolicyElements.get(0);

			String path = passwordPolicyElement.attributeValue("path");

			PasswordPolicy passwordPolicy =
				(PasswordPolicy)portletDataContext.getZipEntryAsObject(path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, passwordPolicy);

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					PasswordPolicy.class);

			long passwordPolicyId = newPrimaryKeysMap.get(
				passwordPolicy.getPrimaryKey());

			OrganizationLocalServiceUtil.addPasswordPolicyOrganizations(
				passwordPolicyId,
				new long[] { importedOrganization.getOrganizationId() });
		}
	}

	protected void importPhones(
			PortletDataContext portletDataContext,
			Organization importedOrganization, Organization organization)
		throws PortalException, SystemException {

		List<Element> phoneElements =
			portletDataContext.getReferenceDataElements(
				organization, Phone.class);

		List<Phone> phones = new ArrayList<Phone>(phoneElements.size());

		for (Element phoneElement : phoneElements) {

			String path = phoneElement.attributeValue("path");

			Phone phone = (Phone)portletDataContext.getZipEntryAsObject(path);

			phone.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, phone);

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Phone.class);

			long phoneId = newPrimaryKeysMap.get(phone.getPrimaryKey());

			phone.setPrimaryKey(phoneId);

			phones.add(phone);
		}

		UsersAdminUtil.updatePhones(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), phones);
	}

	protected void importWebsites(
			PortletDataContext portletDataContext,
			Organization importedOrganization, Organization organization)
		throws PortalException, SystemException {

		List<Element> websiteElements =
			portletDataContext.getReferenceDataElements(
				organization, Website.class);

		List<Website> websites = new ArrayList<Website>(websiteElements.size());

		for (Element websiteElement : websiteElements) {

			String path = websiteElement.attributeValue("path");

			Website website = (Website)portletDataContext.getZipEntryAsObject(
				path);

			website.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, website);

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Website.class);

			long websiteId = newPrimaryKeysMap.get(website.getPrimaryKey());

			website.setPrimaryKey(websiteId);

			websites.add(website);
		}

		UsersAdminUtil.updateWebsites(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), websites);
	}

}