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

package com.liferay.password.policies.admin.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Daniela Zapata Riesco
 */
@RunWith(Arquillian.class)
public class PasswordPolicyStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		return PasswordPolicyTestUtil.addPasswordPolicy(serviceContext);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return PasswordPolicyLocalServiceUtil.
				fetchPasswordPolicyByUuidAndCompanyId(
					uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return PasswordPolicy.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		// super.validateImportedStagedModel(stagedModel, importedStagedModel);

		PasswordPolicy passwordPolicy = (PasswordPolicy)stagedModel;
		PasswordPolicy importedPasswordPolicy =
			(PasswordPolicy)importedStagedModel;

		Assert.assertEquals(
			passwordPolicy.isDefaultPolicy(),
			importedPasswordPolicy.isDefaultPolicy());
		Assert.assertEquals(
			passwordPolicy.getName(), importedPasswordPolicy.getName());
		Assert.assertEquals(
			passwordPolicy.getDescription(),
			importedPasswordPolicy.getDescription());
		Assert.assertEquals(
			passwordPolicy.isChangeable(),
			importedPasswordPolicy.isChangeable());
		Assert.assertEquals(
			passwordPolicy.isChangeRequired(),
			importedPasswordPolicy.isChangeRequired());
		Assert.assertEquals(
			passwordPolicy.getMinAge(), importedPasswordPolicy.getMinAge());
		Assert.assertEquals(
			passwordPolicy.isCheckSyntax(),
			importedPasswordPolicy.isCheckSyntax());
		Assert.assertEquals(
			passwordPolicy.isAllowDictionaryWords(),
			importedPasswordPolicy.isAllowDictionaryWords());
		Assert.assertEquals(
			passwordPolicy.getMinAlphanumeric(),
			importedPasswordPolicy.getMinAlphanumeric());
		Assert.assertEquals(
			passwordPolicy.getMinLength(),
			importedPasswordPolicy.getMinLength());
		Assert.assertEquals(
			passwordPolicy.getMinLowerCase(),
			importedPasswordPolicy.getMinLowerCase());
		Assert.assertEquals(
			passwordPolicy.getMinNumbers(),
			importedPasswordPolicy.getMinNumbers());
		Assert.assertEquals(
			passwordPolicy.getMinSymbols(),
			importedPasswordPolicy.getMinSymbols());
		Assert.assertEquals(
			passwordPolicy.getMinUpperCase(),
			importedPasswordPolicy.getMinUpperCase());
		Assert.assertEquals(
			passwordPolicy.getRegex(), importedPasswordPolicy.getRegex());
		Assert.assertEquals(
			passwordPolicy.isHistory(), importedPasswordPolicy.isHistory());
		Assert.assertEquals(
			passwordPolicy.getHistoryCount(),
			importedPasswordPolicy.getHistoryCount());
		Assert.assertEquals(
			passwordPolicy.isExpireable(),
			importedPasswordPolicy.isExpireable());
		Assert.assertEquals(
			passwordPolicy.getMaxAge(), importedPasswordPolicy.getMaxAge());
		Assert.assertEquals(
			passwordPolicy.getWarningTime(),
			importedPasswordPolicy.getWarningTime());
		Assert.assertEquals(
			passwordPolicy.getGraceLimit(),
			importedPasswordPolicy.getGraceLimit());
		Assert.assertEquals(
			passwordPolicy.isLockout(), importedPasswordPolicy.isLockout());
		Assert.assertEquals(
			passwordPolicy.getMaxFailure(),
			importedPasswordPolicy.getMaxFailure());
		Assert.assertEquals(
			passwordPolicy.getLockoutDuration(),
			importedPasswordPolicy.getLockoutDuration());
		Assert.assertEquals(
			passwordPolicy.isRequireUnlock(),
			importedPasswordPolicy.isRequireUnlock());
		Assert.assertEquals(
			passwordPolicy.getResetFailureCount(),
			importedPasswordPolicy.getResetFailureCount());
		Assert.assertEquals(
			passwordPolicy.getResetTicketMaxAge(),
			importedPasswordPolicy.getResetTicketMaxAge());
	}

}