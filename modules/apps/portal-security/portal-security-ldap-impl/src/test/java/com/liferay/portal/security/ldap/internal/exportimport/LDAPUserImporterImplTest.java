/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.exportimport;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.exportimport.LDAPUser;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;

import javax.naming.InvalidNameException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Díaz
 */
public class LDAPUserImporterImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBindingInNamespaceEscape() throws InvalidNameException {
		Assert.assertEquals(
			"cn=User\\\\,with\\\\,commas,ou=users,dc=example,dc=com",
			escapeLDAPName(
				"cn=User\\,with\\,commas,ou=users,dc=example,dc=com"));
		Assert.assertEquals(
			"cn=User\\\\2cwith\\\\2ccommas,ou=users,dc=example,dc=com",
			escapeLDAPName(
				"cn=User\\2cwith\\2ccommas,ou=users,dc=example,dc=com"));
	}

	@Test
	public void testUpdateUser() throws Exception {
		ConfigurationProvider<LDAPImportConfiguration>
			ldapImportConfigurationProvider = Mockito.mock(
				ConfigurationProvider.class);

		Mockito.when(
			ldapImportConfigurationProvider.getConfiguration(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(LDAPImportConfiguration.class)
		);

		ReflectionTestUtil.setFieldValue(
			_ldapUserImporterImpl, "_ldapImportConfigurationProvider",
			ldapImportConfigurationProvider);

		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider = Mockito.mock(
				ConfigurationProvider.class);

		LDAPServerConfiguration ldapServerConfiguration = Mockito.mock(
			LDAPServerConfiguration.class);

		String modifiedDate = "Thu Apr 2 19:18:33 GMT 2026";

		Mockito.when(
			ldapServerConfiguration.modifiedDate()
		).thenReturn(
			modifiedDate
		);

		Mockito.when(
			ldapServerConfigurationProvider.getConfiguration(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			ldapServerConfiguration
		);

		ReflectionTestUtil.setFieldValue(
			_ldapUserImporterImpl, "_ldapServerConfigurationProvider",
			ldapServerConfigurationProvider);

		ReflectionTestUtil.setFieldValue(
			_ldapUserImporterImpl, "_ldapSettings",
			Mockito.mock(LDAPSettings.class));

		LDAPImportContext ldapImportContext = Mockito.mock(
			LDAPImportContext.class);

		Mockito.when(
			ldapImportContext.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			ldapImportContext.getLdapServerId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		LDAPUser ldapUser = new LDAPUser();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setLanguageId(LocaleUtil.BRAZIL.toLanguageTag());

		ldapUser.setServiceContext(serviceContext);

		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getModifiedDate()
		).thenReturn(
			new Date()
		);

		try (MockedStatic<DateUtil> dateUtilMockedStatic = Mockito.mockStatic(
				DateUtil.class, Mockito.CALLS_REAL_METHODS)) {

			ReflectionTestUtil.invoke(
				_ldapUserImporterImpl, "_updateUser",
				new Class<?>[] {
					LDAPImportContext.class, LDAPUser.class, User.class,
					String.class, String.class, boolean.class
				},
				ldapImportContext, ldapUser, user,
				RandomTestUtil.randomString(),
				String.valueOf(RandomTestUtil.randomLong()), false);

			dateUtilMockedStatic.verify(
				() -> DateUtil.parseDate(
					Mockito.eq("EEE MMM d HH:mm:ss zzz yyyy"),
					Mockito.eq(modifiedDate), Mockito.eq(LocaleUtil.US)),
				Mockito.times(1));
		}
	}

	protected String escapeLDAPName(String query) {
		return _ldapUserImporterImpl.escapeLDAPName(query);
	}

	private static final LDAPUserImporterImpl _ldapUserImporterImpl =
		new LDAPUserImporterImpl();

}