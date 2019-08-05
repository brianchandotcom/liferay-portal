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

package com.liferay.portal.security.ldap.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;
import com.liferay.portal.security.ldap.constants.LegacyLDAPPropsKeys;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.io.IOException;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class LDAPPropertiesVerifyProcessTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		Bundle bundle = FrameworkUtil.getBundle(
			LDAPPropertiesVerifyProcessTest.class);

		_bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin>
			configurationAdminServiceReference =
				_bundleContext.getServiceReference(ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(
			configurationAdminServiceReference);

		ServiceReference<PortalLDAP> serviceReference =
			_bundleContext.getServiceReference(PortalLDAP.class);

		bundle = serviceReference.getBundle();

		_componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				bundle,
				"com.liferay.portal.security.ldap.internal.configuration." +
					"LDAPConfigurationListener");

		_enabled = _serviceComponentRuntime.isComponentEnabled(
			_componentDescriptionDTO);

		if (_enabled) {
			_serviceComponentRuntime.disableComponent(_componentDescriptionDTO);
		}
	}

	@AfterClass
	public static void tearDownClass() {
		if (_enabled) {
			_serviceComponentRuntime.enableComponent(_componentDescriptionDTO);
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			deleteConfigurations(company, LDAPAuthConfiguration.class);
			deleteConfigurations(company, LDAPExportConfiguration.class);
			deleteConfigurations(company, LDAPImportConfiguration.class);
			deleteConfigurations(company, LDAPServerConfiguration.class);
			deleteConfigurations(company, SystemLDAPConfiguration.class);
		}
	}

	@Test
	public void testVerifyConfigurationsNoServers() throws Exception {
		_configureProperties = false;

		super.testVerify();
	}

	protected void addLDAPServer(
		UnicodeProperties properties, long ldapServerId) {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		properties.put(
			LegacyLDAPPropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix,
			"(mail=@email_address@)");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix, "dc=liferay,dc=com");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_BASE_PROVIDER_URL + postfix,
			"ldap://liferay.com:10389");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix, "");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_CONTACT_MAPPINGS + postfix,
			"birthday=\r\ncountry=\r\n");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_GROUP_DEFAULT_OBJECT_CLASSES + postfix,
			"top,groupOfUniqueNames");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_GROUPS_DN + postfix,
			"ou=groups,dc=example,dc=com");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_GROUP_MAPPINGS + postfix,
			"description=description\ngroupName=cn\nuser=uniqueMember");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix,
			"(objectClass=groupOfUniqueNames)");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER + postfix,
			"(objectClass=inetOrgPerson)");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_SECURITY_CREDENTIALS + postfix, "secret");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_SECURITY_PRINCIPAL + postfix,
			"uid=admin,ou=system");
		properties.put(LegacyLDAPPropsKeys.LDAP_SERVER_NAME + postfix, "test");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix, "");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES + postfix,
			"top,person,inetOrgPerson,organizationalPerson");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_USER_MAPPINGS + postfix,
			"emailAddress=mail\nfirstName=givenName\ngroup=groupMembership\n" +
				"jobTitle=title\nlastName=sn\npassword=userPassword\n" +
					"screenName=cn\nuuid=uuid\n");
		properties.put(
			LegacyLDAPPropsKeys.LDAP_USERS_DN + postfix,
			"ou=users,dc=example,dc=com");
	}

	protected void assertArrayLength(Object[] array, int length) {
		Assert.assertEquals(Arrays.toString(array), length, array.length);
	}

	protected void assertContainsAll(Object[] array1, Object[] array2) {
		Assert.assertTrue(ArrayUtil.containsAll(array1, array2));
	}

	protected void deleteConfigurations(
			Company company, Class<?> configurationClass)
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=" + configurationClass.getName() + ")");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties != null) {
				Long companyId = (Long)properties.get(LDAPConstants.COMPANY_ID);

				if ((companyId != null) &&
					(companyId == company.getCompanyId())) {

					configuration.delete();
				}
			}
		}
	}

	@Override
	protected void doVerify() throws VerifyException {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		if (_configureProperties) {
			setUpProperties();
		}

		super.doVerify();

		if (_configureProperties) {
			verifyConfigurationsWithServers(companies);
		}
		else {
			verifyConfigurationsNoServers(companies);
		}
	}

	protected Dictionary<String, Object> getConfigurationProperties(
		Company company, Class<?> configurationClass) {

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					"(service.factoryPid=" + configurationClass.getName() +
						")");

			if (ArrayUtil.isEmpty(configurations)) {
				return null;
			}

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				if (properties == null) {
					continue;
				}

				Long companyId = (Long)properties.get(LDAPConstants.COMPANY_ID);

				if ((companyId == null) ||
					(companyId != company.getCompanyId())) {

					continue;
				}

				return properties;
			}

			return null;
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected Dictionary<String, Object> getConfigurationProperties(
		Company company, long ldapServerId, Class<?> configurationClass) {

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					"(service.factoryPid=" + configurationClass.getName() +
						")");

			if (ArrayUtil.isEmpty(configurations)) {
				return null;
			}

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				if (properties == null) {
					continue;
				}

				Long companyId = (Long)properties.get(LDAPConstants.COMPANY_ID);
				Long configuredLDAPServerId = (Long)properties.get(
					LDAPConstants.LDAP_SERVER_ID);

				if ((companyId == null) || (configuredLDAPServerId == null) ||
					(companyId != company.getCompanyId()) ||
					(configuredLDAPServerId != ldapServerId)) {

					continue;
				}

				return properties;
			}

			return null;
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getAllServiceReferences(
					VerifyProcess.class.getName(),
					StringBundler.concat(
						"(&(objectClass=", VerifyProcess.class.getName(),
						")(verify.process.name=",
						"com.liferay.portal.security.ldap))"));

			if (ArrayUtil.isEmpty(serviceReferences)) {
				throw new IllegalStateException("Unable to get verify process");
			}

			return (VerifyProcess)_bundleContext.getService(
				serviceReferences[0]);
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalStateException("Unable to get verify process");
		}
	}

	protected void setUpProperties() {
		try {
			UnicodeProperties properties = new UnicodeProperties();

			properties.put(LegacyLDAPPropsKeys.LDAP_AUTH_ENABLED, "true");
			properties.put(LegacyLDAPPropsKeys.LDAP_AUTH_REQUIRED, "true");
			properties.put(LegacyLDAPPropsKeys.LDAP_EXPORT_ENABLED, "true");
			properties.put(
				LegacyLDAPPropsKeys.LDAP_FACTORY_INITIAL,
				"com.sun.jndi.ldap.LdapCtxFactory");
			properties.put(LegacyLDAPPropsKeys.LDAP_IMPORT_ENABLED, "true");
			properties.put(LegacyLDAPPropsKeys.LDAP_IMPORT_ON_STARTUP, "true");
			properties.put(
				LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED,
				"true");
			properties.put(
				LegacyLDAPPropsKeys.LDAP_PASSWORD_POLICY_ENABLED, "true");

			addLDAPServer(properties, 0L);
			addLDAPServer(properties, 1L);

			properties.put("ldap.server.ids", "0,1");

			List<Company> companies = CompanyLocalServiceUtil.getCompanies(
				false);

			for (Company company : companies) {
				CompanyLocalServiceUtil.updatePreferences(
					company.getCompanyId(), properties);
			}
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected void verifyConfigurationsNoServers(List<Company> companies) {
		for (Company company : companies) {
			Dictionary<String, Object> ldapAuthProperties =
				getConfigurationProperties(
					company, LDAPAuthConfiguration.class);

			Assert.assertNull(ldapAuthProperties);

			Dictionary<String, Object> ldapExportProperties =
				getConfigurationProperties(
					company, LDAPExportConfiguration.class);

			Assert.assertNull(ldapExportProperties);

			Dictionary<String, Object> ldapImportProperties =
				getConfigurationProperties(
					company, LDAPImportConfiguration.class);

			Assert.assertNull(ldapImportProperties);

			Dictionary<String, Object> ldapServerProperties0 =
				getConfigurationProperties(
					company, 0L, LDAPServerConfiguration.class);

			Assert.assertNull(ldapServerProperties0);

			Dictionary<String, Object> ldapServerProperties1 =
				getConfigurationProperties(
					company, 1L, LDAPServerConfiguration.class);

			Assert.assertNull(ldapServerProperties1);

			Dictionary<String, Object> systemLdapProperties =
				getConfigurationProperties(
					company, SystemLDAPConfiguration.class);

			Assert.assertNull(systemLdapProperties);
		}
	}

	protected void verifyConfigurationsWithServers(List<Company> companies) {
		for (Company company : companies) {
			PortletPreferences portletPreferences =
				PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(
						"ldap.server.ids", StringPool.BLANK)));

			for (String key : LegacyLDAPPropsKeys.LDAP_KEYS_NONPOSTFIXED) {
				Assert.assertTrue(
					Validator.isNull(
						portletPreferences.getValue(key, StringPool.BLANK)));
			}

			Dictionary<String, Object> ldapAuthProperties =
				getConfigurationProperties(
					company, LDAPAuthConfiguration.class);

			Assert.assertNotNull(ldapAuthProperties);
			Assert.assertEquals(
				true, ldapAuthProperties.get(LDAPConstants.AUTH_ENABLED));
			Assert.assertEquals(
				true, ldapAuthProperties.get(LDAPConstants.AUTH_REQUIRED));
			Assert.assertEquals(
				true,
				ldapAuthProperties.get(LDAPConstants.PASSWORD_POLICY_ENABLED));

			Dictionary<String, Object> ldapExportProperties =
				getConfigurationProperties(
					company, LDAPExportConfiguration.class);

			Assert.assertNotNull(ldapExportProperties);

			Assert.assertEquals(
				true, ldapExportProperties.get(LDAPConstants.EXPORT_ENABLED));

			Dictionary<String, Object> ldapImportProperties =
				getConfigurationProperties(
					company, LDAPImportConfiguration.class);

			Assert.assertNotNull(ldapImportProperties);
			Assert.assertEquals(
				true, ldapImportProperties.get(LDAPConstants.IMPORT_ENABLED));
			Assert.assertEquals(
				true,
				ldapImportProperties.get(LDAPConstants.IMPORT_ON_STARTUP));
			Assert.assertEquals(
				true,
				ldapImportProperties.get(
					LDAPConstants.IMPORT_USER_PASSWORD_AUTOGENERATED));

			Dictionary<String, Object> ldapServerProperties0 =
				getConfigurationProperties(
					company, 0L, LDAPServerConfiguration.class);

			_validateLDAPServerProperties(
				company.getCompanyId(), 0L, ldapServerProperties0);

			Dictionary<String, Object> ldapServerProperties1 =
				getConfigurationProperties(
					company, 1L, LDAPServerConfiguration.class);

			_validateLDAPServerProperties(
				company.getCompanyId(), 1L, ldapServerProperties1);

			Dictionary<String, Object> systemLdapProperties =
				getConfigurationProperties(
					company, SystemLDAPConfiguration.class);

			Assert.assertNotNull(systemLdapProperties);
			Assert.assertEquals(
				"com.sun.jndi.ldap.LdapCtxFactory",
				systemLdapProperties.get(LDAPConstants.FACTORY_INITIAL));
		}
	}

	private void _validateLDAPServerProperties(
		long companyId, long ldapServerId,
		Dictionary<String, Object> properties) {

		Assert.assertNotNull(properties);
		Assert.assertNotNull(properties.get(LDAPConstants.AUTH_SEARCH_FILTER));
		Assert.assertEquals(
			"(mail=@email_address@)",
			properties.get(LDAPConstants.AUTH_SEARCH_FILTER));
		Assert.assertNotNull(properties.get(LDAPConstants.BASE_DN));
		Assert.assertEquals(
			"dc=liferay,dc=com", properties.get(LDAPConstants.BASE_DN));
		Assert.assertNotNull(properties.get(LDAPConstants.BASE_PROVIDER_URL));
		Assert.assertEquals(
			"ldap://liferay.com:10389",
			properties.get(LDAPConstants.BASE_PROVIDER_URL));
		Assert.assertNotNull(properties.get(LDAPConstants.COMPANY_ID));
		Assert.assertEquals(
			companyId, properties.get(LDAPConstants.COMPANY_ID));
		Assert.assertNotNull(
			properties.get(LDAPConstants.CONTACT_CUSTOM_MAPPINGS));
		assertArrayLength(
			(String[])properties.get(LDAPConstants.CONTACT_CUSTOM_MAPPINGS), 0);
		assertContainsAll(
			new String[] {"birthday=", "country="},
			(String[])properties.get(LDAPConstants.CONTACT_MAPPINGS));
		Assert.assertNotNull(
			properties.get(LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES));
		assertContainsAll(
			new String[] {"top", "groupOfUniqueNames"},
			(String[])properties.get(
				LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES));
		Assert.assertNotNull(properties.get(LDAPConstants.GROUP_MAPPINGS));
		assertContainsAll(
			new String[] {
				"description=description", "groupName=cn", "user=uniqueMember"
			},
			(String[])properties.get(LDAPConstants.GROUP_MAPPINGS));
		Assert.assertNotNull(properties.get(LDAPConstants.GROUP_SEARCH_FILTER));
		Assert.assertEquals(
			"(objectClass=groupOfUniqueNames)",
			properties.get(LDAPConstants.GROUP_SEARCH_FILTER));
		Assert.assertNotNull(properties.get(LDAPConstants.GROUPS_DN));
		Assert.assertEquals(
			"ou=groups,dc=example,dc=com",
			properties.get(LDAPConstants.GROUPS_DN));
		Assert.assertNotNull(properties.get(LDAPConstants.LDAP_SERVER_ID));
		Assert.assertEquals(
			ldapServerId, properties.get(LDAPConstants.LDAP_SERVER_ID));
		Assert.assertNotNull(properties.get(LDAPConstants.USER_SEARCH_FILTER));
		Assert.assertEquals(
			"(objectClass=inetOrgPerson)",
			properties.get(LDAPConstants.USER_SEARCH_FILTER));
		Assert.assertNotNull(properties.get(LDAPConstants.SECURITY_CREDENTIAL));
		Assert.assertEquals(
			"secret", properties.get(LDAPConstants.SECURITY_CREDENTIAL));
		Assert.assertNotNull(properties.get(LDAPConstants.SECURITY_PRINCIPAL));
		Assert.assertEquals(
			"uid=admin,ou=system",
			properties.get(LDAPConstants.SECURITY_PRINCIPAL));
		Assert.assertNotNull(properties.get(LDAPConstants.SERVER_NAME));
		Assert.assertEquals("test", properties.get(LDAPConstants.SERVER_NAME));
		Assert.assertNotNull(
			properties.get(LDAPConstants.USER_CUSTOM_MAPPINGS));
		assertArrayLength(
			(String[])properties.get(LDAPConstants.USER_CUSTOM_MAPPINGS), 0);
		Assert.assertNotNull(
			properties.get(LDAPConstants.USER_DEFAULT_OBJECT_CLASSES));
		assertContainsAll(
			new String[] {
				"top", "person", "inetOrgPerson", "organizationalPerson"
			},
			(String[])properties.get(
				LDAPConstants.USER_DEFAULT_OBJECT_CLASSES));
		Assert.assertNotNull(properties.get(LDAPConstants.USER_MAPPINGS));
		assertContainsAll(
			new String[] {
				"emailAddress=mail", "firstName=givenName",
				"group=groupMembership", "jobTitle=title", "lastName=sn",
				"password=userPassword", "screenName=cn", "uuid=uuid"
			},
			(String[])properties.get(LDAPConstants.USER_MAPPINGS));
		Assert.assertNotNull(properties.get(LDAPConstants.USERS_DN));
		Assert.assertEquals(
			"ou=users,dc=example,dc=com",
			properties.get(LDAPConstants.USERS_DN));
	}

	private static BundleContext _bundleContext;
	private static ComponentDescriptionDTO _componentDescriptionDTO;
	private static ConfigurationAdmin _configurationAdmin;
	private static boolean _enabled;

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	private boolean _configureProperties = true;

}