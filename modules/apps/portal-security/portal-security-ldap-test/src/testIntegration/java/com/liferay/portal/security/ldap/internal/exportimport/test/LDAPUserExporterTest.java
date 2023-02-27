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

package src.testIntegration.java.com.liferay.portal.security.ldap.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymousUserProvider;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class LDAPUserExporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAnonymousUserExport() throws Exception {
		User user1 = _uadAnonymousUserProvider.getAnonymousUser(
			TestPropsValues.getCompanyId());

		Assert.assertNotNull(user1);

		_userLocalService.updateUser(user1);

		User user2 = _ldapUserImporter.importUser(
			user1.getCompanyId(), user1.getEmailAddress(),
			user1.getScreenName());

		Assert.assertNull(user2);
	}

	@Inject
	private LDAPUserImporter _ldapUserImporter;

	@Inject
	private UADAnonymousUserProvider _uadAnonymousUserProvider;

	@Inject
	private UserLocalService _userLocalService;

}