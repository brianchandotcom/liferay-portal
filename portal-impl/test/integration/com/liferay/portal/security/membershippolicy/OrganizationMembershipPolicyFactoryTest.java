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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicyFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.security.membershippolicy.bundle.organizationmembershippolicyfactory.TestOrganizationMembershipPolicy;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class OrganizationMembershipPolicyFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.organizationmembershippolicyfactory"));

	@Test
	public void testGetOrganizationMembershipPolicy() {
		OrganizationMembershipPolicy organizationMembershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.
				getOrganizationMembershipPolicy();

		Class<?> clazz = organizationMembershipPolicy.getClass();

		Assert.assertEquals(
			TestOrganizationMembershipPolicy.class.getName(), clazz.getName());
	}

}