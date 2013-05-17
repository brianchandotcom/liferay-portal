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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
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
import com.liferay.portal.util.TestPropsValues;

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

		Organization organization = OrganizationTestUtil.addOrganization();

		Organization childOrganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), ServiceTestUtil.randomString(),
			false);
		addDependentStagedModel(
			dependentStagedModelsMap, Organization.class, childOrganization);

		Address address = OrganizationTestUtil.addAddress(organization);
		addDependentStagedModel(
			dependentStagedModelsMap, Address.class, address);

		EmailAddress emailAddress = OrganizationTestUtil.addEmailAddress(
			organization);
		addDependentStagedModel(
			dependentStagedModelsMap, EmailAddress.class, emailAddress);

		OrganizationTestUtil.addOrgLabor(organization);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId(), TestPropsValues.getUserId());
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
	protected void clearStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentOrganizationStagedModels =
			dependentStagedModelsMap.get(Organization.class.getSimpleName());
		Organization childOrganization =
			(Organization)dependentOrganizationStagedModels.get(0);
		OrganizationLocalServiceUtil.deleteOrganization(childOrganization);

		OrganizationLocalServiceUtil.deleteOrganization(
			(Organization)stagedModel);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return OrganizationLocalServiceUtil.
				fetchOrganizationByUuidAndCompanyId(uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Organization.class;
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

		List<StagedModel> dependentAddressStagedModels =
			dependentStagedModelsMap.get(Address.class.getSimpleName());
		Assert.assertEquals(1, dependentAddressStagedModels.size());
		Address address = (Address)dependentAddressStagedModels.get(0);
		Address importedAddress =
			AddressLocalServiceUtil.fetchAddressByUuidAndCompanyId(
				address.getUuid(), group.getCompanyId());
		Assert.assertNotNull(importedAddress);
		Assert.assertEquals(
			organization.getPrimaryKey(), importedAddress.getClassPK());

		List<StagedModel> dependentEmailAddressStagedModels =
			dependentStagedModelsMap.get(EmailAddress.class.getSimpleName());
		Assert.assertEquals(1, dependentEmailAddressStagedModels.size());
		EmailAddress emailAddress =
			(EmailAddress)dependentEmailAddressStagedModels.get(0);
		EmailAddress importedEmailAddress =
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				emailAddress.getUuid(), group.getCompanyId());
		Assert.assertNotNull(importedEmailAddress);
		Assert.assertEquals(
			organization.getPrimaryKey(), importedEmailAddress.getClassPK());

		List<OrgLabor> orgLabors = OrgLaborLocalServiceUtil.getOrgLabors(
			organization.getOrganizationId());
		Assert.assertEquals(1, orgLabors.size());
		Assert.assertEquals(
			organization.getPrimaryKey(), orgLabors.get(0).getOrganizationId());

		List<StagedModel> dependentPasswordPolicyStagedModels =
			dependentStagedModelsMap.get(PasswordPolicy.class.getSimpleName());
		Assert.assertEquals(1, dependentPasswordPolicyStagedModels.size());
		PasswordPolicy passwordPolicy =
			(PasswordPolicy)dependentPasswordPolicyStagedModels.get(0);
		PasswordPolicyRel passwordPolicyRel =
			PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(
				organization.getModelClassName(), organization.getPrimaryKey());
		Assert.assertNotNull(passwordPolicyRel);
		Assert.assertEquals(
			passwordPolicy.getPasswordPolicyId(),
			passwordPolicyRel.getPasswordPolicyId());

		List<StagedModel> dependentPhoneStagedModels =
			dependentStagedModelsMap.get(Phone.class.getSimpleName());
		Assert.assertEquals(1, dependentPhoneStagedModels.size());
		Phone phone = (Phone)dependentPhoneStagedModels.get(0);
		Phone importedPhone =
			PhoneLocalServiceUtil.fetchPhoneByUuidAndCompanyId(
				phone.getUuid(), group.getCompanyId());
		Assert.assertNotNull(importedPhone);
		Assert.assertEquals(
			organization.getPrimaryKey(), importedPhone.getClassPK());

		List<StagedModel> dependentWebsiteStagedModels =
			dependentStagedModelsMap.get(Website.class.getSimpleName());
		Assert.assertEquals(1, dependentWebsiteStagedModels.size());
		Website website = (Website)dependentWebsiteStagedModels.get(0);
		Website importedWebsite =
			WebsiteLocalServiceUtil.fetchWebsiteByUuidAndCompanyId(
				website.getUuid(), group.getCompanyId());
		Assert.assertNotNull(importedWebsite);
		Assert.assertEquals(
			organization.getPrimaryKey(), importedWebsite.getClassPK());
	}

}