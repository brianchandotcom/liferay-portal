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
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<PasswordPolicy> _passwordPolicies = new ArrayList<>();

	@Inject(filter = "component.name=*.PasswordPolicyUADExporter")
	private UADExporter _uadExporter;

}