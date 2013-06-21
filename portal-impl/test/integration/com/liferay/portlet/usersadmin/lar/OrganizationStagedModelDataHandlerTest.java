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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.OrganizationTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author David Mendez Gonzalez
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class OrganizationStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		Organization organization =
			OrganizationTestUtil.addOrganizationWithAssetEntry(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			ServiceTestUtil.randomString(), false);

		Organization suborganization =
			OrganizationTestUtil.addOrganizationWithAssetEntry(
				organization.getOrganizationId(),
				ServiceTestUtil.randomString(), false);

		addDependentStagedModel(
			dependentStagedModelsMap, Organization.class, suborganization);

		Address address = OrganizationTestUtil.addAddress(organization);

		addDependentStagedModel(
			dependentStagedModelsMap, Address.class, address);

		EmailAddress emailAddress = OrganizationTestUtil.addEmailAddress(
			organization);

		addDependentStagedModel(
			dependentStagedModelsMap, EmailAddress.class, emailAddress);

		OrganizationTestUtil.addOrgLabor(organization);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		PasswordPolicy passwordPolicy =
			OrganizationTestUtil.addPasswordPolicyRel(
				organization, serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, PasswordPolicy.class, passwordPolicy);

		Phone phone = OrganizationTestUtil.addPhone(organization);

		addDependentStagedModel(dependentStagedModelsMap, Phone.class, phone);

		Website website = OrganizationTestUtil.addWebsite(organization);

		addDependentStagedModel(
			dependentStagedModelsMap, Website.class, website);

		return organization;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			Organization.class.getSimpleName());

		Organization suborganization = (Organization)dependentStagedModels.get(
			0);

		OrganizationLocalServiceUtil.deleteOrganization(suborganization);

		dependentStagedModels = dependentStagedModelsMap.get(
			Address.class.getSimpleName());

		Address address = (Address)dependentStagedModels.get(0);

		AddressLocalServiceUtil.deleteAddress(address);

		dependentStagedModels = dependentStagedModelsMap.get(
			EmailAddress.class.getSimpleName());

		EmailAddress emailAddress = (EmailAddress)dependentStagedModels.get(0);

		EmailAddressLocalServiceUtil.deleteEmailAddress(emailAddress);

		dependentStagedModels = dependentStagedModelsMap.get(
			Phone.class.getSimpleName());

		Phone phone = (Phone)dependentStagedModels.get(0);

		PhoneLocalServiceUtil.deletePhone(phone);

		dependentStagedModels = dependentStagedModelsMap.get(
			Website.class.getSimpleName());

		Website website = (Website)dependentStagedModels.get(0);

		WebsiteLocalServiceUtil.deleteWebsite(website);

		OrganizationLocalServiceUtil.deleteOrganization(
			(Organization)stagedModel);
}

	@Override
	protected StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		UsersAdminPortletDataHandler usersAdminPortletDataHandler =
			new UsersAdminPortletDataHandler();

		return usersAdminPortletDataHandler.
			getDeletionSystemEventStagedModelTypes();
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws SystemException {

		return OrganizationLocalServiceUtil.fetchOrganizationByUuidAndCompanyId(
			uuid, group.getCompanyId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Organization.class;
	}

	@Override
	protected void validateDeletion(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			Organization.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		Organization suborganization = (Organization)dependentStagedModels.get(
			0);

		suborganization =
			OrganizationLocalServiceUtil.fetchOrganizationByUuidAndCompanyId(
				suborganization.getUuid(), group.getCompanyId());

		Assert.assertNull(
			"Not Deleted: " + Organization.class, suborganization);

		dependentStagedModels = dependentStagedModelsMap.get(
			Address.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		Address address = (Address)dependentStagedModels.get(0);

		address = AddressLocalServiceUtil.fetchAddressByUuidAndCompanyId(
			address.getUuid(), group.getCompanyId());

		Assert.assertNull("Not Deleted: " + Address.class, address);

		dependentStagedModels = dependentStagedModelsMap.get(
			EmailAddress.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		EmailAddress emailAddress = (EmailAddress)dependentStagedModels.get(0);

		emailAddress =
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				emailAddress.getUuid(), group.getCompanyId());

		Assert.assertNull("Not Deleted: " + EmailAddress.class, emailAddress);

		dependentStagedModels = dependentStagedModelsMap.get(
			Phone.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		Phone phone = (Phone)dependentStagedModels.get(0);

		phone = PhoneLocalServiceUtil.fetchPhoneByUuidAndCompanyId(
			phone.getUuid(), group.getCompanyId());

		Assert.assertNull("Not Deleted: " + Phone.class, phone);

		dependentStagedModels = dependentStagedModelsMap.get(
			Website.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		Website website = (Website)dependentStagedModels.get(0);

		website = WebsiteLocalServiceUtil.fetchWebsiteByUuidAndCompanyId(
			website.getUuid(), group.getCompanyId());

		Assert.assertNull("Not Deleted: " + Website.class, website);
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		Organization organization =
			OrganizationLocalServiceUtil.fetchOrganizationByUuidAndCompanyId(
				stagedModel.getUuid(), group.getCompanyId());

		List<StagedModel> addressDependentStagedModels =
			dependentStagedModelsMap.get(Address.class.getSimpleName());

		Assert.assertEquals(1, addressDependentStagedModels.size());

		Address address = (Address)addressDependentStagedModels.get(0);

		Address importedAddress =
			AddressLocalServiceUtil.fetchAddressByUuidAndCompanyId(
				address.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedAddress);
		Assert.assertEquals(
			organization.getOrganizationId(), importedAddress.getClassPK());

		List<StagedModel> emailAddressDependentStagedModels =
			dependentStagedModelsMap.get(EmailAddress.class.getSimpleName());

		Assert.assertEquals(1, emailAddressDependentStagedModels.size());

		EmailAddress emailAddress =
			(EmailAddress)emailAddressDependentStagedModels.get(0);

		EmailAddress importedEmailAddress =
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				emailAddress.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedEmailAddress);
		Assert.assertEquals(
			organization.getOrganizationId(),
			importedEmailAddress.getClassPK());

		List<OrgLabor> importedOrgLabors =
			OrgLaborLocalServiceUtil.getOrgLabors(
				organization.getOrganizationId());

		Assert.assertEquals(1, importedOrgLabors.size());

		OrgLabor importedOrgLabor = importedOrgLabors.get(0);

		Assert.assertEquals(
			organization.getOrganizationId(),
			importedOrgLabor.getOrganizationId());

		PasswordPolicyRel importedPasswordPolicyRel =
			PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(
				organization.getModelClassName(),
				organization.getOrganizationId());

		Assert.assertNotNull(importedPasswordPolicyRel);

		List<StagedModel> phoneDependentStagedModels =
			dependentStagedModelsMap.get(Phone.class.getSimpleName());

		Assert.assertEquals(1, phoneDependentStagedModels.size());

		Phone phone = (Phone)phoneDependentStagedModels.get(0);

		Phone importedPhone =
			PhoneLocalServiceUtil.fetchPhoneByUuidAndCompanyId(
				phone.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedPhone);
		Assert.assertEquals(
			organization.getOrganizationId(), importedPhone.getClassPK());

		List<StagedModel> websiteDependentStagedModels =
			dependentStagedModelsMap.get(Website.class.getSimpleName());

		Assert.assertEquals(1, websiteDependentStagedModels.size());

		Website website = (Website)websiteDependentStagedModels.get(0);

		Website importedWebsite =
			WebsiteLocalServiceUtil.fetchWebsiteByUuidAndCompanyId(
				website.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedWebsite);
		Assert.assertEquals(
			organization.getOrganizationId(), importedWebsite.getClassPK());
	}

}