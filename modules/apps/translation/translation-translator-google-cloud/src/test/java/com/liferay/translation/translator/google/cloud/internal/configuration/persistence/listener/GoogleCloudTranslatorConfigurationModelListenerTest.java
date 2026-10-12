/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.translator.google.cloud.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class GoogleCloudTranslatorConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_googleCloudTranslatorConfigurationModelListener, "_jsonFactory",
			new JSONFactoryImpl());

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(Mockito.<Locale>any(), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1)
		);

		ReflectionTestUtil.setFieldValue(
			_googleCloudTranslatorConfigurationModelListener, "_language",
			language);

		SecretResolver secretResolver = Mockito.mock(SecretResolver.class);

		Mockito.when(
			secretResolver.resolve(Mockito.anyLong(), Mockito.any())
		).thenAnswer(
			invocation -> invocation.getArgument(1)
		);

		ReflectionTestUtil.setFieldValue(
			_googleCloudTranslatorConfigurationModelListener, "_secretResolver",
			secretResolver);
	}

	@Test
	public void testOnBeforeSaveDisabledWithInvalidServiceAccountPrivateKey()
		throws Exception {

		_onBeforeSave(false, "");
		_onBeforeSave(false, "invalid");
	}

	@Test
	public void testOnBeforeSaveEnabledWithInvalidServiceAccountPrivateKey() {
		_assertInvalidServiceAccountPrivateKey("");
		_assertInvalidServiceAccountPrivateKey("{}");
		_assertInvalidServiceAccountPrivateKey("invalid");
	}

	@Test
	public void testOnBeforeSaveEnabledWithValidServiceAccountPrivateKey()
		throws Exception {

		_onBeforeSave(true, "{\"type\": \"service_account\"}");
	}

	private void _assertInvalidServiceAccountPrivateKey(
		String serviceAccountPrivateKey) {

		ConfigurationModelListenerException
			configurationModelListenerException = Assert.assertThrows(
				ConfigurationModelListenerException.class,
				() -> _onBeforeSave(true, serviceAccountPrivateKey));

		Assert.assertEquals(
			"the-service-account-private-key-must-be-in-json-format",
			configurationModelListenerException.causeMessage);
	}

	private void _onBeforeSave(boolean enabled, String serviceAccountPrivateKey)
		throws Exception {

		_googleCloudTranslatorConfigurationModelListener.onBeforeSave(
			null,
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).put(
				"serviceAccountPrivateKey", serviceAccountPrivateKey
			).build());
	}

	private final GoogleCloudTranslatorConfigurationModelListener
		_googleCloudTranslatorConfigurationModelListener =
			new GoogleCloudTranslatorConfigurationModelListener();

}