/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.password.policies.admin.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.password.policies.admin.uad.test.PasswordPolicyUADTestUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class PasswordPolicyUADExporterTest
	extends BaseUADExporterTestCase<PasswordPolicy> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected PasswordPolicy addBaseModel(long userId) throws Exception {
		PasswordPolicy passwordPolicy =
			PasswordPolicyUADTestUtil.addPasswordPolicy(userId);

		_passwordPolicies.add(passwordPolicy);

		return passwordPolicy;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "passwordPolicyId";
	}

	@Override
	protected UADExporter<PasswordPolicy> getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<PasswordPolicy> _passwordPolicies = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.password.policies.admin.uad.exporter.PasswordPolicyUADExporter"
	)
	private UADExporter<PasswordPolicy> _uadExporter;

}