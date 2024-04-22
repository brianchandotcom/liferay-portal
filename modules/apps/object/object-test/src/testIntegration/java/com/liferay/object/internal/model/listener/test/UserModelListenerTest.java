/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.test.system.TestSystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class UserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		CompanyTestUtil.resetCompanyLocales(
			_company.getCompanyId(),
			ListUtil.fromArray(LocaleUtil.SPAIN, LocaleUtil.US), LocaleUtil.US);

		Bundle bundle = FrameworkUtil.getBundle(UserModelListenerTest.class);

		_bundleContext = bundle.getBundleContext();

		_systemObjectDefinitionManagerServiceRegistration =
			_bundleContext.registerService(
				SystemObjectDefinitionManager.class,
				new TestSystemObjectDefinitionManager(
					ObjectEntry.class, _OBJECT_DEFINITION_NAME,
					StringBundler.concat(
						"/o/", RandomTestUtil.randomString(), StringPool.SLASH,
						RandomTestUtil.randomString())),
				new HashMapDictionary<>());
	}

	@After
	public void tearDown() throws Exception {
		if (_auditMessageProcessorServiceRegistration != null) {
			_auditMessageProcessorServiceRegistration.unregister();
		}

		if (_systemObjectDefinitionManagerServiceRegistration != null) {
			_systemObjectDefinitionManagerServiceRegistration.unregister();
		}

		_objectDefinitionLocalService.deleteCompanyObjectDefinitions(
			_company.getCompanyId());

		_companyLocalService.deleteCompany(_company.getCompanyId());
	}

	@Test
	public void testOnAfterRemove() throws Exception {
		BundleActivator bundleActivator =
			new UserLocalServiceTestBundleActivator();

		bundleActivator.start(_bundleContext);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.createObjectDefinition(RandomTestUtil.randomLong());

		User user = UserTestUtil.addUser();

		objectDefinition.setCompanyId(user.getCompanyId());
		objectDefinition.setUserId(user.getUserId());

		objectDefinition = _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);

		_userLocalService.deleteUser(user);

		objectDefinition = _objectDefinitionLocalService.getObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		User defaultServiceAccountUser =
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), "default-service-account");

		Assert.assertEquals(
			defaultServiceAccountUser.getUserId(),
			objectDefinition.getUserId());
	}

	@Test
	public void testOnAfterUpdate() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_company.getCompanyId(), _OBJECT_DEFINITION_NAME);

		_assertEquals(LocaleUtil.SPAIN, objectDefinition);
		_assertEquals(LocaleUtil.US, objectDefinition);
		_assertEqualsSorted(new String[] {"es_ES", "en_US"}, objectDefinition);

		CompanyTestUtil.resetCompanyLocales(
			_company.getCompanyId(),
			ListUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.BRAZIL);

		objectDefinition = _objectDefinitionLocalService.fetchObjectDefinition(
			_company.getCompanyId(), _OBJECT_DEFINITION_NAME);

		_assertEquals(LocaleUtil.BRAZIL, objectDefinition);
		_assertEqualsSorted(
			new String[] {"es_ES", "en_US", "pt_BR"}, objectDefinition);
	}

	private void _assertEquals(
		Locale locale, ObjectDefinition objectDefinition) {

		Assert.assertEquals(
			_language.get(locale, "test"), objectDefinition.getLabel(locale));
		Assert.assertEquals(
			_language.get(locale, "tests"),
			objectDefinition.getPluralLabel(locale));
	}

	private void _assertEqualsSorted(
		String[] availableLanguageIds, ObjectDefinition objectDefinition) {

		AssertUtils.assertEqualsSorted(
			availableLanguageIds,
			_localization.getAvailableLanguageIds(objectDefinition.getLabel()));
		AssertUtils.assertEqualsSorted(
			availableLanguageIds,
			_localization.getAvailableLanguageIds(
				objectDefinition.getPluralLabel()));
	}

	private static final String _OBJECT_DEFINITION_NAME =
		ObjectDefinitionTestUtil.getRandomName();

	private ServiceRegistration<AuditMessageProcessor>
		_auditMessageProcessorServiceRegistration;
	private BundleContext _bundleContext;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private Language _language;

	@Inject
	private Localization _localization;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ServiceRegistration<SystemObjectDefinitionManager>
		_systemObjectDefinitionManagerServiceRegistration;

	@Inject
	private UserLocalService _userLocalService;

	private static class TestAuditMessageProcessor
		implements AuditMessageProcessor {

		@Override
		public void process(AuditMessage auditMessage) {
			Assert.assertNotNull(auditMessage);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			String authType = String.valueOf(
				additionalInfoJSONObject.get("authType"));

			Assert.assertEquals("emailAddress", authType);

			String reason = String.valueOf(
				additionalInfoJSONObject.get("reason"));

			Assert.assertEquals("User does not exist", reason);

			Assert.assertEquals(
				EventTypes.LOGIN_DNE, auditMessage.getEventType());
		}

	}

	private class UserLocalServiceTestBundleActivator
		implements BundleActivator {

		@Override
		public void start(BundleContext bundleContext) {
			AuditMessageProcessor auditMessageProcessor =
				new TestAuditMessageProcessor();

			_auditMessageProcessorServiceRegistration =
				_bundleContext.registerService(
					AuditMessageProcessor.class, auditMessageProcessor,
					HashMapDictionaryBuilder.<String, Object>put(
						"eventTypes", EventTypes.LOGIN_DNE
					).build());
		}

		@Override
		public void stop(BundleContext bundleContext) {
			_auditMessageProcessorServiceRegistration.unregister();
		}
	}

}