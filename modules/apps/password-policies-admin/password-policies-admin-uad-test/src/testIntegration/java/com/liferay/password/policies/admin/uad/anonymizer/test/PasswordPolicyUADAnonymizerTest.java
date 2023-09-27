/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.password.policies.admin.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.password.policies.admin.uad.test.PasswordPolicyUADTestUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class PasswordPolicyUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<PasswordPolicy> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected PasswordPolicy addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected PasswordPolicy addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		PasswordPolicy passwordPolicy =
			PasswordPolicyUADTestUtil.addPasswordPolicy(userId);

		if (deleteAfterTestRun) {
			_passwordPolicies.add(passwordPolicy);
		}

		return passwordPolicy;
	}

	@Override
	protected UADAnonymizer<PasswordPolicy> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		PasswordPolicy passwordPolicy =
			_passwordPolicyLocalService.getPasswordPolicy(baseModelPK);

		String userName = passwordPolicy.getUserName();

		if ((passwordPolicy.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_passwordPolicyLocalService.fetchPasswordPolicy(baseModelPK) ==
				null) {

			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<PasswordPolicy> _passwordPolicies = new ArrayList<>();

	@Inject
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Inject(
		filter = "component.name=com.liferay.password.policies.admin.uad.anonymizer.PasswordPolicyUADAnonymizer"
	)
	private UADAnonymizer<PasswordPolicy> _uadAnonymizer;

}