/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.processor.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.processor.UserProcessor;
import com.liferay.saml.opensaml.integration.processor.factory.UserProcessorFactory;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tamas Biro
 */
@RunWith(Arquillian.class)
public class UserProcessorFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAllUserFieldsAreUpdatedWhenEmailIsChanged()
		throws Exception {

		_user = _userLocalService.createUser(0);

		_user.setCompanyId(TestPropsValues.getCompanyId());

		_assertProcess("John", "john.doe", "john.doe@example.com", "john.doe");

		Assert.assertNotEquals(0, _user.getUserId());

		// Assert handling case-insensitive email addresses during update

		_assertProcess(
			"John-changed", "Doe-changed", "JOHN.DOE@example.com",
			"john.doe.changed");

		// Updated email entirely changed

		_assertProcess("Jane", "Doena", "jane.doena@example.com", "jane.doena");

		_assertProcess(
			null, null, "jane.doena@example.com", null);
	}

	private void _assertProcess(
			String firstName, String lastName, String emailAddress,
			String screenName)
		throws Exception {

		UserProcessor userProcessor = _userProcessorFactory.create(
			_user, _userFieldExpressionHandlerRegistry);

		_set(userProcessor, "emailAddress", emailAddress);
		_set(userProcessor, "firstName", firstName);
		_set(userProcessor, "lastName", lastName);
		_set(userProcessor, "screenName", screenName);

		User user2 = userProcessor.process(
			ServiceContextTestUtil.getServiceContext());

		user2 = _userLocalService.getUser(user2.getUserId());

		Assert.assertEquals(
			StringUtil.toLowerCase(_user.getEmailAddress()),
			user2.getEmailAddress());
		Assert.assertEquals(_user.getFirstName(), user2.getFirstName());
		Assert.assertEquals(_user.getLastName(), user2.getLastName());
		Assert.assertEquals(_user.getScreenName(), user2.getScreenName());

		_user = user2;
	}

	private void _set(UserProcessor userProcessor, String fieldExpression, String value) {
		if (value != null) {
			userProcessor.setValueArray(fieldExpression, new String[] {value});
		}
	}

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserFieldExpressionHandlerRegistry
		_userFieldExpressionHandlerRegistry;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserProcessorFactory _userProcessorFactory;

}