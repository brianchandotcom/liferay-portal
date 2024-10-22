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
import com.liferay.portal.kernel.test.util.UserTestUtil;
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

		_user = UserTestUtil.addUser();

		_user.setScreenName("john.doe");
		_user.setEmailAddress("john.doe@example.com");
		_user.setFirstName("John");
		_user.setLastName("Doe");

		_user = _userLocalService.updateUser(_user);

		// Updated email contains capital letters

		_assertProcess(
			"John-changed", "Doe-changed", "JOHN.DOE@example.com",
			"john.doe.changed");

		// Updated email contains lower case letters

		_assertProcess(
			"John-changed", "Doe-changed", "john.doe@example.com", "john.doe");

		// Updated email entirely changed

		_assertProcess("Jane", "Doena", "jane.doena@example.com", "jane.doena");
	}

	private void _assertProcess(
			String firstName, String lastName, String emailAddress,
			String screenName)
		throws Exception {

		UserProcessor userProcessor = _userProcessorFactory.create(
			_user, _userFieldExpressionHandlerRegistry);

		userProcessor.setValueArray(
			"emailAddress", new String[] {emailAddress});
		userProcessor.setValueArray("firstName", new String[] {firstName});
		userProcessor.setValueArray("lastName", new String[] {lastName});
		userProcessor.setValueArray("screenName", new String[] {screenName});

		User user = userProcessor.process(
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(_user.getFirstName(), user.getFirstName());
		Assert.assertEquals(_user.getLastName(), user.getLastName());
		Assert.assertEquals(_user.getScreenName(), user.getScreenName());
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