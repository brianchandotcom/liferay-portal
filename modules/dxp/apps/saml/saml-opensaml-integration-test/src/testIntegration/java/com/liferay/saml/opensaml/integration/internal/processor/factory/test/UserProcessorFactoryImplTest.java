/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.processor.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.processor.UserProcessor;
import com.liferay.saml.opensaml.integration.processor.factory.UserProcessorFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_currentUser = _createUser(
			"John", "Doe", "john.doe@example.com", "john.doe");
	}

	@After
	public void tearDown() throws PortalException {
		_userLocalService.deleteUser(_currentUser);
	}

	@Test
	public void testAllUserFieldsAreUpdatedWhenEmailIsChanged()
		throws Exception {

		// Updated email contains capital letters

		User newUser = _processUserFromSaml(
			"John-changed", "Doe-changed", "JOHN.DOE@example.com",
			"john.doe.changed");

		_assertUserFields(newUser);

		// Updated email contains lower case letters

		newUser = _processUserFromSaml(
			"John-changed", "Doe-changed", "john.doe@example.com", "john.doe");

		_assertUserFields(newUser);

		// Updated email entirely changed

		newUser = _processUserFromSaml(
			"Jane", "Doena", "jane.doena@example.com", "jane.doena");

		_assertUserFields(newUser);
	}

	private void _assertUserFields(User newUser) {
		Assert.assertEquals(
			_currentUser.getFirstName(), newUser.getFirstName());
		Assert.assertEquals(_currentUser.getLastName(), newUser.getLastName());
		Assert.assertEquals(
			_currentUser.getScreenName(), newUser.getScreenName());
	}

	private User _createUser(
			String firstName, String lastName, String emailAddress,
			String screenName)
		throws Exception {

		User user = UserTestUtil.addUser();

		user.setScreenName(screenName);
		user.setEmailAddress(emailAddress);
		user.setFirstName(firstName);
		user.setLastName(lastName);

		return _userLocalService.updateUser(user);
	}

	private User _processUserFromSaml(
			String firstName, String lastName, String emailAddress,
			String screenName)
		throws Exception {

		UserProcessor userProcessor = _userProcessorFactory.create(
			_currentUser, _userFieldExpressionHandlerRegistry);

		userProcessor.setValueArray(
			"emailAddress", new String[] {emailAddress});
		userProcessor.setValueArray("firstName", new String[] {firstName});
		userProcessor.setValueArray("lastName", new String[] {lastName});
		userProcessor.setValueArray("screenName", new String[] {screenName});

		return userProcessor.process(
			ServiceContextTestUtil.getServiceContext());
	}

	private User _currentUser;

	@Inject
	private UserFieldExpressionHandlerRegistry
		_userFieldExpressionHandlerRegistry;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserProcessorFactory _userProcessorFactory;

}