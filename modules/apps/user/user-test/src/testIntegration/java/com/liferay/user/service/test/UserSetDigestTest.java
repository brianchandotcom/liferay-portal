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

package com.liferay.user.service.test;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jesse Yeh
 */
public class UserSetDigestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_user = _userLocalService.createUser(RandomTestUtil.nextLong());
		_emailAddress =
			RandomTestUtil.randomString() + RandomTestUtil.nextLong() +
				"@liferay.com";
	}

	@Test
	public void testSetDigestAfterScreenNameAndEmailAddress() throws Exception {
		_user.setScreenName(RandomTestUtil.randomString());
		_user.setEmailAddress(_emailAddress);

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));
	}

	@Test(expected = IllegalStateException.class)
	public void testSetDigestBeforeScreenNameAndEmailAddress()
		throws Exception {

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));

		_user.setScreenName(RandomTestUtil.randomString());
		_user.setEmailAddress(_emailAddress);
	}

	@Test(expected = IllegalStateException.class)
	public void testSetEmailAndDigestBeforeScreenName() throws Exception {
		_user.setEmailAddress(_emailAddress);

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));

		_user.setScreenName(RandomTestUtil.randomString());
	}

	@Test(expected = IllegalStateException.class)
	public void testSetScreenNameAndDigestBeforeEmailAddress()
		throws Exception {

		_user.setScreenName(RandomTestUtil.randomString());

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));

		_user.setEmailAddress(_emailAddress);
	}

	@Inject
	private static UserLocalService _userLocalService;

	private String _emailAddress;
	private User _user;

}