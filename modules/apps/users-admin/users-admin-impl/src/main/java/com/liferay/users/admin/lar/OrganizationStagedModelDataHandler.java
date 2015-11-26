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

package com.liferay.users.admin.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressLocalService;
import com.liferay.portal.service.EmailAddressLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.OrgLaborLocalService;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.PasswordPolicyLocalService;
import com.liferay.portal.service.PasswordPolicyRelLocalService;
import com.liferay.portal.service.PhoneLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WebsiteLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Mendez Gonzalez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class OrganizationStagedModelDataHandler
	extends BaseStagedModelDataHandler<Organization> {

	public static final String[] CLASS_NAMES = {Organization.class.getName()};

	@Override
	public void deleteStagedModel(Organization organization)
		throws PortalException {

		_organizationLocalService.deleteOrganization(organization);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Organization organization =
			_organizationLocalService.fetchOrganizationByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (organization != null) {
			deleteStagedModel(organization);
		}
	}

	@Override
	public List<Organization> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Organization> organizations = new ArrayList<>();

		organizations.add(
			_organizationLocalService.fetchOrganizationByUuidAndCompanyId(
				uuid, companyId));

		return organizations;
	}

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

		Queue<Organization> organizations = new LinkedList<>();

		organizations.add(organization);

		while (!organizations.isEmpty()) {
			Organization exportedOrganization = organizations.remove();

			if (organization.getParentOrganizationId() !=
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, organization,
					organization.getParentOrganization(),
					PortletDataContext.REFERENCE_TYPE_PARENT);
			}

			exportAddresses(portletDataContext, exportedOrganization);
			exportEmailAddresses(portletDataContext, exportedOrganization);
			exportOrgLabors(portletDataContext, exportedOrganization);
			exportPasswordPolicyRel(portletDataContext, exportedOrganization);
			exportPhones(portletDataContext, exportedOrganization);
			exportWebsites(portletDataContext, exportedOrganization);

			Element organizationElement =
				portletDataContext.getExportDataElement(exportedOrganization);

			portletDataContext.addClassedModel(
				organizationElement,
				ExportImportPathUtil.getModelPath(exportedOrganization),
				exportedOrganization);

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

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			organization);

		serviceContext.setUserId(userId);

		Organization existingOrganization =
			_organizationLocalService.fetchOrganizationByUuidAndCompanyId(
				organization.getUuid(), portletDataContext.getGroupId());

		if (existingOrganization == null) {
			existingOrganization = _organizationLocalService.fetchOrganization(
				portletDataContext.getCompanyId(), organization.getName());
		}

		Organization importedOrganization = null;

		if (existingOrganization == null) {
			serviceContext.setUuid(organization.getUuid());

			importedOrganization = _organizationLocalService.addOrganization(
				userId, parentOrganizationId, organization.getName(),
				organization.getType(), organization.getRegionId(),
				organization.getCountryId(), organization.getStatusId(),
				organization.getComments(), false, serviceContext);
		}
		else {
			importedOrganization = _organizationLocalService.updateOrganization(
				portletDataContext.getCompanyId(),
				existingOrganization.getOrganizationId(), parentOrganizationId,
				organization.getName(), organization.getType(),
				organization.getRegionId(), organization.getCountryId(),
				organization.getStatusId(), organization.getComments(), true,
				null, false, serviceContext);
		}

		importAddresses(portletDataContext, organization, importedOrganization);
		importEmailAddresses(
			portletDataContext, organization, importedOrganization);
		importOrgLabors(portletDataContext, organization, importedOrganization);
		importPasswordPolicyRel(
			portletDataContext, organization, importedOrganization);
		importPhones(portletDataContext, organization, importedOrganization);
		importWebsites(portletDataContext, organization, importedOrganization);

		portletDataContext.importClassedModel(
			organization, importedOrganization);
	}

	protected void exportAddresses(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException {

		List<Address> addresses = _addressLocalService.getAddresses(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		for (Address address : addresses) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, organization, address,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED);
		}
	}

	protected void exportEmailAddresses(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException {

		List<EmailAddress> emailAddresses =
			_emailAddressLocalService.getEmailAddresses(
				organization.getCompanyId(), organization.getModelClassName(),
				organization.getOrganizationId());

		for (EmailAddress emailAddress : emailAddresses) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, organization, emailAddress,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED);
		}
	}

	protected void exportOrgLabors(
		PortletDataContext portletDataContext, Organization organization) {

		List<OrgLabor> orgLabors = _orgLaborLocalService.getOrgLabors(
			organization.getOrganizationId());

		String path = ExportImportPathUtil.getModelPath(
			organization, OrgLabor.class.getSimpleName());

		portletDataContext.addZipEntry(path, orgLabors);
	}

	protected void exportPasswordPolicyRel(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException {

		PasswordPolicyRel passwordPolicyRel =
			_passwordPolicyRelLocalService.fetchPasswordPolicyRel(
				Organization.class.getName(), organization.getOrganizationId());

		if (passwordPolicyRel == null) {
			return;
		}

		PasswordPolicy passwordPolicy =
			_passwordPolicyLocalService.getPasswordPolicy(
				passwordPolicyRel.getPasswordPolicyId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, organization, passwordPolicy,
			PortletDataContext.REFERENCE_TYPE_STRONG);
	}

	protected void exportPhones(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException {

		List<Phone> phones = _phoneLocalService.getPhones(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		for (Phone phone : phones) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, organization, phone,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED);
		}
	}

	protected void exportWebsites(
			PortletDataContext portletDataContext, Organization organization)
		throws PortalException {

		List<Website> websites = _websiteLocalService.getWebsites(
			organization.getCompanyId(), organization.getModelClassName(),
			organization.getOrganizationId());

		for (Website website : websites) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, organization, website,
				PortletDataContext.REFERENCE_TYPE_EMBEDDED);
		}
	}

	protected void importAddresses(
			PortletDataContext portletDataContext, Organization organization,
			Organization importedOrganization)
		throws PortalException {

		List<Element> addressElements =
			portletDataContext.getReferenceDataElements(
				organization, Address.class);

		List<Address> addresses = new ArrayList<>(addressElements.size());

		for (Element addressElement : addressElements) {
			String addressPath = addressElement.attributeValue("path");

			Address address = (Address)portletDataContext.getZipEntryAsObject(
				addressPath);

			address.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, address);

			Map<Long, Long> addressIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Address.class);

			long addressId = addressIds.get(address.getPrimaryKey());

			address.setAddressId(addressId);

			addresses.add(address);
		}

		UsersAdminUtil.updateAddresses(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), addresses);
	}

	protected void importEmailAddresses(
			PortletDataContext portletDataContext, Organization organization,
			Organization importedOrganization)
		throws PortalException {

		List<Element> emailAddressElements =
			portletDataContext.getReferenceDataElements(
				organization, EmailAddress.class);

		List<EmailAddress> emailAddresses = new ArrayList<>(
			emailAddressElements.size());

		for (Element emailAddressElement : emailAddressElements) {
			String emailAddressPath = emailAddressElement.attributeValue(
				"path");

			EmailAddress emailAddress =
				(EmailAddress)portletDataContext.getZipEntryAsObject(
					emailAddressPath);

			emailAddress.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, emailAddress);

			Map<Long, Long> emailAddressIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					EmailAddress.class);

			long emailAddressId = emailAddressIds.get(
				emailAddress.getPrimaryKey());

			emailAddress.setEmailAddressId(emailAddressId);

			emailAddresses.add(emailAddress);
		}

		UsersAdminUtil.updateEmailAddresses(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), emailAddresses);
	}

	protected void importOrgLabors(
			PortletDataContext portletDataContext, Organization organization,
			Organization importedOrganization)
		throws PortalException {

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
			PortletDataContext portletDataContext, Organization organization,
			Organization importedOrganization)
		throws PortalException {

		List<Element> passwordPolicyElements =
			portletDataContext.getReferenceDataElements(
				organization, PasswordPolicy.class);

		if (passwordPolicyElements.isEmpty()) {
			return;
		}

		Element passwordPolicyElement = passwordPolicyElements.get(0);

		String passwordPolicyPath = passwordPolicyElement.attributeValue(
			"path");

		PasswordPolicy passwordPolicy =
			(PasswordPolicy)portletDataContext.getZipEntryAsObject(
				passwordPolicyPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, passwordPolicy);

		Map<Long, Long> passwordPolicyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PasswordPolicy.class);

		long passwordPolicyId = passwordPolicyIds.get(
			passwordPolicy.getPrimaryKey());

		_organizationLocalService.addPasswordPolicyOrganizations(
			passwordPolicyId,
			new long[] {importedOrganization.getOrganizationId()});
	}

	protected void importPhones(
			PortletDataContext portletDataContext, Organization organization,
			Organization importedOrganization)
		throws PortalException {

		List<Element> phoneElements =
			portletDataContext.getReferenceDataElements(
				organization, Phone.class);

		List<Phone> phones = new ArrayList<>(phoneElements.size());

		for (Element phoneElement : phoneElements) {
			String phonePath = phoneElement.attributeValue("path");

			Phone phone = (Phone)portletDataContext.getZipEntryAsObject
				(phonePath);

			phone.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, phone);

			Map<Long, Long> phoneIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Phone.class);

			long phoneId = phoneIds.get(phone.getPrimaryKey());

			phone.setPhoneId(phoneId);

			phones.add(phone);
		}

		UsersAdminUtil.updatePhones(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), phones);
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext, Organization organization) {
	}

	protected void importWebsites(
			PortletDataContext portletDataContext, Organization organization,
			Organization importedOrganization)
		throws PortalException {

		List<Element> websiteElements =
			portletDataContext.getReferenceDataElements(
				organization, Website.class);

		List<Website> websites = new ArrayList<>(websiteElements.size());

		for (Element websiteElement : websiteElements) {
			String websitePath = websiteElement.attributeValue("path");

			Website website = (Website)portletDataContext.getZipEntryAsObject(
				websitePath);

			website.setClassPK(importedOrganization.getOrganizationId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, website);

			Map<Long, Long> websiteIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Website.class);

			long websiteId = websiteIds.get(website.getPrimaryKey());

			website.setWebsiteId(websiteId);

			websites.add(website);
		}

		UsersAdminUtil.updateWebsites(
			Organization.class.getName(),
			importedOrganization.getOrganizationId(), websites);
	}

	@Reference(unbind = "-")
	protected void setAddressLocalService(
		AddressLocalService addressLocalService) {

		_addressLocalService = addressLocalService;
	}

	@Reference(unbind = "-")
	protected void setEmailAddressLocalService(
		EmailAddressLocalService emailAddressLocalService) {

		_emailAddressLocalService = emailAddressLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Reference(unbind = "-")
	protected void setOrgLaborLocalService(
		OrgLaborLocalService orgLaborLocalService) {

		_orgLaborLocalService = orgLaborLocalService;
	}

	@Reference(unbind = "-")
	protected void setPasswordPolicyLocalService(
		PasswordPolicyLocalService passwordPolicyLocalService) {

		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	@Reference(unbind = "-")
	protected void setPasswordPolicyRelLocalService(
		PasswordPolicyRelLocalService passwordPolicyRelLocalService) {

		_passwordPolicyRelLocalService = passwordPolicyRelLocalService;
	}

	@Reference(unbind = "-")
	protected void setPhoneLocalService(PhoneLocalService phoneLocalService) {
		_phoneLocalService = phoneLocalService;
	}

	@Reference(unbind = "-")
	protected void setWebsiteLocalService(
		WebsiteLocalService websiteLocalService) {

		_websiteLocalService = websiteLocalService;
	}

	private volatile AddressLocalService _addressLocalService;
	private volatile EmailAddressLocalService _emailAddressLocalService;
	private volatile GroupLocalService _groupLocalService;
	private volatile OrganizationLocalService _organizationLocalService;
	private volatile OrgLaborLocalService _orgLaborLocalService;
	private volatile PasswordPolicyLocalService _passwordPolicyLocalService;
	private volatile PasswordPolicyRelLocalService
		_passwordPolicyRelLocalService;
	private volatile PhoneLocalService _phoneLocalService;
	private volatile WebsiteLocalService _websiteLocalService;

}