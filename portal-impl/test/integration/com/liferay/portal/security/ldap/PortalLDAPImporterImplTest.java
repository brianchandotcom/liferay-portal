/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.naming.ldap.LdapContext;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {
	EnvironmentExecutionTestListener.class,
	MainServletExecutionTestListener.class,
	TransactionalCallbackAwareExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortalLDAPImporterImplTest {

	@Before
	public void setUp() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_ldapServerId = CounterLocalServiceUtil.increment();
	}

	@After
	public void tearDown() throws Exception {
		deleteLDAPUser();
	}

	@Test
	@Transactional
	public void testImportLDAPUsers() throws Exception {
		configureLDAPServer();

		if (!isLDAPConnected()) {
			_log.warn("Connection with LDAP not available. Test won't " +
				"execute");

			return;
		}

		createUser();

		PortalLDAPImporterUtil.importFromLDAP();

		User user = UserLocalServiceUtil.getUser(_userId);

		completeUserFields(user);

		// import again to check fields are not overriden

		PortalLDAPImporterUtil.importFromLDAP();

		User importedUser = UserLocalServiceUtil.getUser(_userId);

		Contact contact = user.getContact();

		Contact importedContact = importedUser.getContact();

		Assert.assertEquals(
			contact.getPrefixId(), importedContact.getPrefixId());
		Assert.assertEquals(user.getMiddleName(), importedUser.getMiddleName());
		Assert.assertEquals(
			contact.getSuffixId(), importedContact.getSuffixId());
		Assert.assertEquals(contact.getMale(), importedContact.getMale());
		Assert.assertEquals(user.getJobTitle(), importedUser.getJobTitle());
		Assert.assertEquals(contact.getAimSn(), importedContact.getAimSn());
		Assert.assertEquals(contact.getIcqSn(), importedContact.getIcqSn());
		Assert.assertEquals(
			contact.getJabberSn(), importedContact.getJabberSn());
		Assert.assertEquals(contact.getSkypeSn(), importedContact.getSkypeSn());
		Assert.assertEquals(contact.getMsnSn(), importedContact.getMsnSn());
		Assert.assertEquals(contact.getYmSn(), importedContact.getYmSn());
		Assert.assertEquals(
			contact.getFacebookSn(), importedContact.getFacebookSn());
		Assert.assertEquals(
			contact.getMySpaceSn(), importedContact.getMySpaceSn());
		Assert.assertEquals(
			contact.getTwitterSn(), importedContact.getTwitterSn());
		Assert.assertEquals(contact.getSmsSn(), importedContact.getSmsSn());
		Assert.assertEquals(user.getOpenId(), importedUser.getOpenId());
		Assert.assertEquals(user.getLanguageId(), importedUser.getLanguageId());
		Assert.assertEquals(user.getTimeZoneId(), importedUser.getTimeZoneId());
		Assert.assertEquals(user.getGreeting(), importedUser.getGreeting());
		Assert.assertEquals(user.getComments(), importedUser.getComments());
	}

	protected boolean isLDAPConnected() throws Exception {
		LdapContext ldapContext = PortalLDAPUtil.getContext(
			_ldapServerId, _companyId);

		if (ldapContext == null) {
			return false;
		}

		return true;
	}

	protected void completeUserFields(User user) throws Exception {
		Contact contact = user.getContact();

		contact.setPrefixId(1);

		if (Validator.isNull(user.getMiddleName())) {
			user.setMiddleName("portalMiddleName");
		}

		contact.setSuffixId(1);
		contact.setMale(false);

		if (Validator.isNull(user.getJobTitle())) {
			user.setJobTitle("portalJobTitle");
		}

		contact.setAimSn("portalAim");
		contact.setIcqSn("portalIcq");
		contact.setJabberSn("portalJabber");
		contact.setSkypeSn("portalSkype");
		contact.setMsnSn("portalMsn");
		contact.setYmSn("portalYm");

		contact.setFacebookSn("portalFacebook");
		contact.setMySpaceSn("portalMySpace");
		contact.setTwitterSn("portalTwitter");

		contact.setSmsSn("portalSms@liferay.com");

		user.setOpenId("http://liferay.openid.com");

		Locale locale = LocaleUtil.getDefault();

		user.setLanguageId(LocaleUtil.toLanguageId(locale));

		TimeZone timeZone = TimeZoneUtil.getDefault();
		user.setTimeZoneId(timeZone.getID());
		user.setGreeting("hello portal!");

		user.setComments("portal comments");

		user.setModifiedDate(new Date());

		UserLocalServiceUtil.updateUser(user);
	}

	protected void configureLDAPServer() throws Exception {
		UnicodeProperties properties = new UnicodeProperties();

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			_companyId);

		String ldapServerIds = preferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.add(
			ldapServerIds, String.valueOf(_ldapServerId));

		properties.setProperty("ldap.server.ids", ldapServerIds);

		String postfix = LDAPSettingsUtil.getPropertyPostfix(_ldapServerId);

		properties.put(PropsKeys.LDAP_AUTH_ENABLED, "true");
		properties.put(PropsKeys.LDAP_EXPORT_ENABLED, "true");
		properties.put(PropsKeys.LDAP_EXPORT_GROUP_ENABLED, "true");
		properties.put(PropsKeys.LDAP_IMPORT_ENABLED, "true");

		properties.put("ldap.server.name" + postfix, "LDAP Server Name");

		properties.put(
			PropsKeys.LDAP_BASE_PROVIDER_URL + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_BASE_PROVIDER_URL));
		properties.put(
			PropsKeys.LDAP_BASE_DN + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_BASE_DN));

		properties.put(
			PropsKeys.LDAP_SECURITY_PRINCIPAL + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_SECURITY_PRINCIPAL));
		properties.put(
			PropsKeys.LDAP_SECURITY_CREDENTIALS + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_SECURITY_CREDENTIALS));

		properties.put(
			PropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_AUTH_SEARCH_FILTER));

		properties.put(
			PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER));
		properties.put(
			PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix,
			PrefsPropsUtil.getString(
				PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER));

		properties.put(
			PropsKeys.LDAP_USERS_DN + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_USERS_DN));
		properties.put(
			PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES + postfix,
			PrefsPropsUtil.getString(
				PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES));

		properties.put(
			PropsKeys.LDAP_GROUPS_DN + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_GROUPS_DN));
		properties.put(
			PropsKeys.LDAP_GROUP_DEFAULT_OBJECT_CLASSES + postfix,
			PrefsPropsUtil.getString(
				PropsKeys.LDAP_GROUP_DEFAULT_OBJECT_CLASSES));

		properties.put(
			PropsKeys.LDAP_USER_MAPPINGS + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_USER_MAPPINGS));
		properties.put(
			PropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_USER_CUSTOM_MAPPINGS));
		properties.put(
			PropsKeys.LDAP_GROUP_MAPPINGS+ postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_GROUP_MAPPINGS));
		properties.put(
			PropsKeys.LDAP_CONTACT_MAPPINGS + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_CONTACT_MAPPINGS));
		properties.put(
			PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix,
			PrefsPropsUtil.getString(PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS));

		CompanyLocalServiceUtil.updatePreferences(_companyId, properties);
	}

	protected void createUser() throws Exception {
		User user = ServiceTestUtil.addUser(
			"PortalLDAPImporterImplTest", false,
			new long[] {TestPropsValues.getGroupId()});

		_userId = user.getUserId();
	}

	protected void deleteLDAPUser() throws Exception {
		User user = UserLocalServiceUtil.fetchUser(_userId);

		if (user != null) {
			PortalLDAPExporterUtil.deleteFromLDAP(
				_ldapServerId, _companyId, user.getUserId());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalLDAPImporterImplTest.class);

	private static long _ldapServerId;

	private long _companyId;

	private long _userId;

}