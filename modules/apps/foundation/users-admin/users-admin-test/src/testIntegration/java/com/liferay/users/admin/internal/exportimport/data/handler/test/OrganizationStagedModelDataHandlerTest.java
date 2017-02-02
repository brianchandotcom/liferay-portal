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

package com.liferay.users.admin.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.PasswordPolicyRel;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author David Mendez Gonzalez
 */
@RunWith(Arquillian.class)
@Sync
public class OrganizationStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_organization != null) {
			_organization =
				OrganizationLocalServiceUtil.
					fetchOrganizationByUuidAndCompanyId(
						_organization.getUuid(), _organization.getCompanyId());

			if (_organization != null) {
				deleteOrganizations(_organization);
			}
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			_organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		addDependentStagedModel(
			dependentStagedModelsMap, Organization.class, suborganization);

		Address address = OrganizationTestUtil.addAddress(_organization);

		addDependentStagedModel(
			dependentStagedModelsMap, Address.class, address);

		EmailAddress emailAddress = OrganizationTestUtil.addEmailAddress(
			_organization);

		addDependentStagedModel(
			dependentStagedModelsMap, EmailAddress.class, emailAddress);

		OrganizationTestUtil.addOrgLabor(_organization);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		PasswordPolicy passwordPolicy =
			OrganizationTestUtil.addPasswordPolicyRel(
				_organization, serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, PasswordPolicy.class, passwordPolicy);

		Phone phone = OrganizationTestUtil.addPhone(_organization);

		addDependentStagedModel(dependentStagedModelsMap, Phone.class, phone);

		Website website = OrganizationTestUtil.addWebsite(_organization);

		addDependentStagedModel(
			dependentStagedModelsMap, Website.class, website);

		return _organization;
	}

	protected void deleteOrganizations(Organization organization)
		throws Exception {

		List<Organization> childOrganizations =
			OrganizationLocalServiceUtil.getOrganizations(
				organization.getCompanyId(), organization.getOrganizationId());

		for (Organization childOrganization : childOrganizations) {
			deleteOrganizations(childOrganization);
		}

		OrganizationLocalServiceUtil.deleteOrganization(organization);
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

		Assert.assertEquals(
			addressDependentStagedModels.toString(), 1,
			addressDependentStagedModels.size());

		Address address = (Address)addressDependentStagedModels.get(0);

		Address importedAddress =
			AddressLocalServiceUtil.fetchAddressByUuidAndCompanyId(
				address.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedAddress);
		Assert.assertEquals(
			organization.getOrganizationId(), importedAddress.getClassPK());

		List<StagedModel> emailAddressDependentStagedModels =
			dependentStagedModelsMap.get(EmailAddress.class.getSimpleName());

		Assert.assertEquals(
			emailAddressDependentStagedModels.toString(), 1,
			emailAddressDependentStagedModels.size());

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

		Assert.assertEquals(
			importedOrgLabors.toString(), 1, importedOrgLabors.size());

		OrgLabor importedOrgLabor = importedOrgLabors.get(0);

		Assert.assertEquals(
			organization.getOrganizationId(),
			importedOrgLabor.getOrganizationId());

		List<StagedModel> passwordPolicyDependentStagedModels =
			dependentStagedModelsMap.get(PasswordPolicy.class.getSimpleName());

		Assert.assertEquals(
			passwordPolicyDependentStagedModels.toString(), 1,
			passwordPolicyDependentStagedModels.size());

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

		Assert.assertEquals(
			phoneDependentStagedModels.toString(), 1,
			phoneDependentStagedModels.size());

		Phone phone = (Phone)phoneDependentStagedModels.get(0);

		Phone importedPhone =
			PhoneLocalServiceUtil.fetchPhoneByUuidAndCompanyId(
				phone.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedPhone);
		Assert.assertEquals(
			organization.getOrganizationId(), importedPhone.getClassPK());

		List<StagedModel> websiteDependentStagedModels =
			dependentStagedModelsMap.get(Website.class.getSimpleName());

		Assert.assertEquals(
			websiteDependentStagedModels.toString(), 1,
			websiteDependentStagedModels.size());

		Website website = (Website)websiteDependentStagedModels.get(0);

		Website importedWebsite =
			WebsiteLocalServiceUtil.fetchWebsiteByUuidAndCompanyId(
				website.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedWebsite);
		Assert.assertEquals(
			organization.getOrganizationId(), importedWebsite.getClassPK());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		Organization organization = (Organization)stagedModel;
		Organization importedOrganization = (Organization)importedStagedModel;

		Assert.assertEquals(
			organization.getName(), importedOrganization.getName());
		Assert.assertEquals(
			organization.getType(), importedOrganization.getType());
		Assert.assertEquals(
			organization.isRecursable(), importedOrganization.isRecursable());
		Assert.assertEquals(
			organization.getComments(), importedOrganization.getComments());
	}

	private Organization _organization;

}