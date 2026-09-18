/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.google.places.constants.GooglePlacesWebKeys;
import com.liferay.google.places.util.GooglePlacesUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.security.key.spi.secret.SecretProvider;
import com.liferay.portal.security.key.test.util.TestSecretProvider;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Pedro Victor Silvestre
 */
@RunWith(Arquillian.class)
public class PreferenceCredentialVaultTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_fipsEnabled = PropsValues.FIPS_ENABLED;

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "FIPS_ENABLED", true);

		_secretProviderServiceRegistration = _bundleContext.registerService(
			SecretProvider.class, new TestSecretProvider(_SECRET_PROVIDER_ID),
			HashMapDictionaryBuilder.<String, Object>put(
				"secret.provider.id", _SECRET_PROVIDER_ID
			).build());

		ConfigurationTestUtil.saveConfiguration(
			_KEY_MANAGER_CUSTOM_PROFILE_CONFIGURATION_PID,
			HashMapDictionaryBuilder.<String, Object>put(
				"companySecretProviderId", _SECRET_PROVIDER_ID
			).put(
				"systemSecretProviderId", _SECRET_PROVIDER_ID
			).build());
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "FIPS_ENABLED", _fipsEnabled);

		ConfigurationTestUtil.deleteConfiguration(
			_KEY_MANAGER_CUSTOM_PROFILE_CONFIGURATION_PID);

		if (_secretProviderServiceRegistration != null) {
			_secretProviderServiceRegistration.unregister();
		}

		_companyLocalService.updatePreferences(
			TestPropsValues.getCompanyId(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, ""
			).build());
	}

	@Test
	public void testVaultCompanyPreference() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		String value = RandomTestUtil.randomString();

		_companyLocalService.updatePreferences(
			companyId,
			UnicodePropertiesBuilder.create(
				true
			).put(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, value
			).build());

		String storedValue = PrefsPropsUtil.getString(
			companyId, GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);

		Assert.assertNotNull(KeyReferenceUtil.parseKeyReference(storedValue));

		Assert.assertEquals(
			storedValue, GooglePlacesUtil.getGooglePlacesAPIKey(companyId));
		Assert.assertEquals(
			value, _secretResolver.resolve(companyId, storedValue));
	}

	@Test
	public void testVaultGroupTypeSettings() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		Group group = _groupLocalService.getGroup(TestPropsValues.getGroupId());
		String value = RandomTestUtil.randomString();

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		String typeSettings = typeSettingsUnicodeProperties.toString();

		try {
			typeSettingsUnicodeProperties.setProperty(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, value);

			group = _groupLocalService.updateGroup(
				group.getGroupId(), typeSettingsUnicodeProperties.toString());

			String storedValue = group.getTypeSettingsProperty(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);

			Assert.assertNotNull(
				KeyReferenceUtil.parseKeyReference(storedValue));

			Assert.assertEquals(
				value, _secretResolver.resolve(companyId, storedValue));
		}
		finally {
			_groupLocalService.updateGroup(group.getGroupId(), typeSettings);
		}
	}

	@Test
	public void testVaultGroupTypeSettingsInheritedFromCompany()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		Group group = _groupLocalService.getGroup(TestPropsValues.getGroupId());
		String value = RandomTestUtil.randomString();

		_companyLocalService.updatePreferences(
			companyId,
			UnicodePropertiesBuilder.create(
				true
			).put(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, value
			).build());

		String storedValue = PrefsPropsUtil.getString(
			companyId, GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		String typeSettings = typeSettingsUnicodeProperties.toString();

		try {
			typeSettingsUnicodeProperties.setProperty(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, storedValue);

			group = _groupLocalService.updateGroup(
				group.getGroupId(), typeSettingsUnicodeProperties.toString());

			Assert.assertEquals(
				storedValue,
				group.getTypeSettingsProperty(
					GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY));

			Assert.assertEquals(
				value, _secretResolver.resolve(companyId, storedValue));
		}
		finally {
			_groupLocalService.updateGroup(group.getGroupId(), typeSettings);
		}
	}

	@Test
	public void testVaultIsIdempotent() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_companyLocalService.updatePreferences(
			companyId,
			UnicodePropertiesBuilder.create(
				true
			).put(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY,
				RandomTestUtil.randomString()
			).build());

		String storedValue = PrefsPropsUtil.getString(
			companyId, GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);

		_companyLocalService.updatePreferences(
			companyId,
			UnicodePropertiesBuilder.create(
				true
			).put(
				GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY, storedValue
			).build());

		Assert.assertEquals(
			storedValue,
			PrefsPropsUtil.getString(
				companyId, GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY));
	}

	private static final String _KEY_MANAGER_CUSTOM_PROFILE_CONFIGURATION_PID =
		"com.liferay.portal.security.key.internal.profile.configuration." +
			"KeyManagerCustomProfileConfiguration";

	private static final String _SECRET_PROVIDER_ID = "test-key-secret";

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	@Inject
	private CompanyLocalService _companyLocalService;

	private boolean _fipsEnabled;

	@Inject
	private GroupLocalService _groupLocalService;

	private ServiceRegistration<SecretProvider>
		_secretProviderServiceRegistration;

	@Inject
	private SecretResolver _secretResolver;

}