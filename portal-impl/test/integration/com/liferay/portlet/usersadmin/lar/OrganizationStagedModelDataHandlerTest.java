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

package com.liferay.portlet.usersadmin.lar;

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
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

/**
 * @author David Mendez Gonzalez
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class OrganizationStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

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

		List<StagedModel> dependentOrganizationStagedModels =
			dependentStagedModelsMap.get(Organization.class.getSimpleName());

		Organization suborganization =
			(Organization)dependentOrganizationStagedModels.get(0);

		OrganizationLocalServiceUtil.deleteOrganization(suborganization);

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
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		Organization organization =
			OrganizationLocalServiceUtil.fetchOrganizationByUuidAndCompanyId(
				stagedModel.getUuid(), group.getCompanyId());

		validateAssets(organization, stagedModelAssets, group);

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

		List<StagedModel> passwordPolicyDependentStagedModels =
			dependentStagedModelsMap.get(PasswordPolicy.class.getSimpleName());

		Assert.assertEquals(1, passwordPolicyDependentStagedModels.size());

		PasswordPolicy passwordPolicy =
			(PasswordPolicy)passwordPolicyDependentStagedModels.get(0);

		PasswordPolicyRel importedPasswordPolicyRel =
			PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(
				organization.getModelClassName(),
				organization.getOrganizationId());

		Assert.assertNotNull(importedPasswordPolicyRel);
		Assert.assertEquals(
			passwordPolicy.getPasswordPolicyId(),
			importedPasswordPolicyRel.getPasswordPolicyId());

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