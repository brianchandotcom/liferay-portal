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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.DefaultScreenNameValidator;
import com.liferay.portal.kernel.security.auth.ScreenNameValidator;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.security.auth.ScreenNameValidatorFactory;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserServiceWhenAddingUserWithSpecialCharactersScreenNameTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_screenNameValidator = ScreenNameValidatorFactory.getInstance();

		if (_screenNameValidator instanceof DefaultScreenNameValidator) {
			_originalSpecialChars = ReflectionTestUtil.getAndSetFieldValue(
				_screenNameValidator, _FIELD_KEY_SPECIAL_CHARS,
				_SPECIAL_CHARACTERS);
			_originalSpecialCharsRegex = ReflectionTestUtil.getAndSetFieldValue(
				_screenNameValidator, _FIELD_KEY_SPECIAL_CHARS_REGEX, null);
		}
	}

	@After
	public void tearDown() {
		if (_screenNameValidator instanceof DefaultScreenNameValidator) {
			ReflectionTestUtil.setFieldValue(
				_screenNameValidator, _FIELD_KEY_SPECIAL_CHARS,
				_originalSpecialChars);
			ReflectionTestUtil.setFieldValue(
				_screenNameValidator, _FIELD_KEY_SPECIAL_CHARS_REGEX,
				_originalSpecialCharsRegex);
		}
	}

	@Test
	public void testShouldNormalizeTheFriendlyURL() throws Exception {
		User user1 = UserTestUtil.addUser("contains-hyphens");

		_users.add(user1);

		Assert.assertEquals("/contains-hyphens", _getFriendlyURL(user1));

		User user2 = UserTestUtil.addUser("contains.periods");

		_users.add(user2);

		Assert.assertEquals("/contains.periods", _getFriendlyURL(user2));

		User user3 = UserTestUtil.addUser("contains_underscores");

		_users.add(user3);

		Assert.assertEquals("/contains_underscores", _getFriendlyURL(user3));

		User user4 = UserTestUtil.addUser("contains'apostrophes");

		_users.add(user4);

		Assert.assertEquals("/contains-apostrophes", _getFriendlyURL(user4));

		User user5 = UserTestUtil.addUser("contains#pounds");

		_users.add(user5);

		Assert.assertEquals("/contains-pounds", _getFriendlyURL(user5));
	}

	private String _getFriendlyURL(User user) {
		Group group = user.getGroup();

		return group.getFriendlyURL();
	}

	private static final String _FIELD_KEY_SPECIAL_CHARS = "_specialChars";

	private static final String _FIELD_KEY_SPECIAL_CHARS_REGEX =
		"_specialCharsRegex";

	private static final String _SPECIAL_CHARACTERS = "-._\\'#";

	private String _originalSpecialChars;
	private String _originalSpecialCharsRegex;
	private ScreenNameValidator _screenNameValidator;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}