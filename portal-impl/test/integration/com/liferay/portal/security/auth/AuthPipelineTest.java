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

import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;
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
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule("bundle.authpipeline"));

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
		_atomicState.reset();

		AuthPipeline.authenticateByEmailAddress(
			"auth.pipeline.pre", 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testAuthenticateByScreenName() throws AuthException {
		_atomicState.reset();

		AuthPipeline.authenticateByScreenName(
			"auth.pipeline.pre", 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testAuthenticateByUserId() throws AuthException {
		_atomicState.reset();

		AuthPipeline.authenticateByUserId(
			"auth.pipeline.pre", 0, RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnFailureByScreenName() {
		_atomicState.reset();

		try {
			AuthPipeline.onFailureByScreenName(
				"auth.failure", 0, RandomTestUtil.randomString(), null, null);
		}
		catch (AuthException ae) {
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnFailureByUserId() {
		_atomicState.reset();

		try {
			AuthPipeline.onFailureByUserId(
				"auth.failure", 0, RandomTestUtil.randomLong(), null, null);
		}
		catch (AuthException ae) {
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnMaxFailuresByEmailAddress() {
		_atomicState.reset();

		try {
			AuthPipeline.onMaxFailuresByEmailAddress(
				"auth.max.failures", 0, RandomTestUtil.randomString(), null,
				null);
		}
		catch (AuthException ae) {
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnMaxFailuresByScreenName() {
		_atomicState.reset();

		try {
			AuthPipeline.onMaxFailuresByScreenName(
				"auth.max.failures", 0, RandomTestUtil.randomString(), null,
				null);
		}
		catch (AuthException ae) {
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testOnMaxFailuresByUserId() {
		_atomicState.reset();

		try {
			AuthPipeline.onMaxFailuresByUserId(
				"auth.max.failures", 0, RandomTestUtil.randomLong(), null,
				null);
		}
		catch (AuthException ae) {
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}