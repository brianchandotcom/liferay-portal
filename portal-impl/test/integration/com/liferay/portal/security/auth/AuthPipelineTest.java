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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class AuthPipelineTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.authpipeline"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testAuthenticateByEmailAddress() throws AuthException {
		long companyId = 0;
		String emailAddress = RandomTestUtil.randomString();
		String key = "auth.pipeline.pre";
		String password = RandomTestUtil.randomString();

		_atomicState.reset();

		AuthPipeline.authenticateByEmailAddress(
			key, companyId, emailAddress, password, null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testAuthenticateByScreenName() throws AuthException {
		long companyId = 0;
		String key = "auth.pipeline.pre";
		String password = RandomTestUtil.randomString();
		String screenName = RandomTestUtil.randomString();

		_atomicState.reset();

		AuthPipeline.authenticateByScreenName(
			key, companyId, screenName, password, null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testAuthenticateByUserId() throws AuthException {
		long companyId = 0;
		String key = "auth.pipeline.pre";
		String password = RandomTestUtil.randomString();
		long userId = RandomTestUtil.randomLong();

		_atomicState.reset();

		AuthPipeline.authenticateByUserId(
			key, companyId, userId, password, null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnFailureByScreenName() {
		long companyId = 0;
		String key = "auth.failure";
		String screenName = RandomTestUtil.randomString();

		_atomicState.reset();

		try {
			AuthPipeline.onFailureByScreenName(
				key, companyId, screenName, null, null);
		} catch (AuthException ae) {}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnFailureByUserId() {
		long companyId = 0;
		String key = "auth.failure";
		long userId = RandomTestUtil.randomLong();

		_atomicState.reset();

		try {
			AuthPipeline.onFailureByUserId(
				key, companyId, userId, null, null);
		} catch (AuthException ae) {}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnMaxFailuresByEmailAddress() {
		long companyId = 0;
		String emailAddress = RandomTestUtil.randomString();
		String key = "auth.max.failures";
		
		_atomicState.reset();

		try {
			AuthPipeline.onMaxFailuresByEmailAddress(
				key, companyId, emailAddress, null, null);
		} catch (AuthException ae) {}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnMaxFailuresByScreenName() {
		long companyId = 0;
		String key = "auth.max.failures";
		String screenName = RandomTestUtil.randomString();

		_atomicState.reset();

		try {
			AuthPipeline.onMaxFailuresByScreenName(
				key, companyId, screenName, null, null);
		} catch (AuthException ae) {}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnMaxFailuresByUserId() {
		long companyId = 0;
		String key = "auth.max.failures";
		long userId = RandomTestUtil.randomLong();

		_atomicState.reset();

		try {
			AuthPipeline.onMaxFailuresByUserId(
				key, companyId, userId, null, null);
		} catch (AuthException ae) {}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}