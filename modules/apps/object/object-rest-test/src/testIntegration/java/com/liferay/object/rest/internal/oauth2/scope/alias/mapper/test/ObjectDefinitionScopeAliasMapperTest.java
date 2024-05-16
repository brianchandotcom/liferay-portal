/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.oauth2.scope.alias.mapper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionScopeAliasMapperTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				"AAA" + RandomTestUtil.randomString() + "aaa",
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		String name = objectDefinition.getName();

		String lowerCaseName = StringUtil.toLowerCase(name);

		_testWithOAuth2ProviderApplicationHeadlessServerConfiguration(
			lowerCaseName + ".everything", objectDefinition,
			lowerCaseName + ".everything");

		_testWithOAuth2ProviderApplicationHeadlessServerConfiguration(
			lowerCaseName + ".everything", objectDefinition,
			name + ".everything");

		_withBundlePrefixHandlerFactoryConfiguration(
			objectDefinition,
			() -> _testWithOAuth2ProviderApplicationHeadlessServerConfiguration(
				"com.liferay.object.rest.impl/" + lowerCaseName + "/everything",
				objectDefinition,
				"com.liferay.object.rest.impl/" + lowerCaseName +
					"/everything"));

		_withBundlePrefixHandlerFactoryConfiguration(
			objectDefinition,
			() -> _testWithOAuth2ProviderApplicationHeadlessServerConfiguration(
				"com.liferay.object.rest.impl/" + lowerCaseName + "/everything",
				objectDefinition,
				"com.liferay.object.rest.impl/" + name + "/everything"));
	}

	private OAuth2Application _getOAuth2Application(
		long companyId, String name) {

		for (OAuth2Application oAuth2Application :
				_oAuth2ApplicationLocalService.getOAuth2Applications(
					companyId)) {

			if (StringUtil.equals(name, oAuth2Application.getName())) {
				return oAuth2Application;
			}
		}

		return null;
	}

	private void _testWithOAuth2ProviderApplicationHeadlessServerConfiguration(
			String expectedScope, ObjectDefinition objectDefinition,
			String scope)
		throws Exception {

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.oauth2.provider.configuration." +
				"OAuth2ProviderApplicationHeadlessServerConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"baseURL",
				"${portalURL}/o/" +
					TextFormatter.formatPlural(objectDefinition.getShortName())
			).put(
				"companyId", objectDefinition.getCompanyId()
			).put(
				"description", RandomTestUtil.randomString()
			).put(
				"homePageURL", "http://localhost:8080"
			).put(
				"name", objectDefinition.getName()
			).put(
				"projectId", objectDefinition.getName()
			).put(
				"projectName", objectDefinition.getName()
			).put(
				"properties", new String[0]
			).put(
				"scopes", new String[] {scope}
			).build());

		try {
			OAuth2Application oAuth2Application = _getOAuth2Application(
				objectDefinition.getCompanyId(), objectDefinition.getName());

			List<String> scopeAliases =
				_oAuth2ApplicationScopeAliasesLocalService.getScopeAliasesList(
					oAuth2Application.getOAuth2ApplicationScopeAliasesId());

			Assert.assertEquals(
				Collections.singleton(expectedScope),
				new HashSet<>(scopeAliases));
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	private void _withBundlePrefixHandlerFactoryConfiguration(
			ObjectDefinition objectDefinition,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.oauth2.provider.scope.internal.configuration." +
				"BundlePrefixHandlerFactoryConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"delimiter", "/"
			).put(
				"include.bundle.symbolic.name", true
			).put(
				"osgi.jaxrs.name", objectDefinition.getOSGiJaxRsName()
			).put(
				"service.properties", new String[] {"osgi.jaxrs.name"}
			).build());

		try {
			unsafeRunnable.run();
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private OAuth2ApplicationScopeAliasesLocalService
		_oAuth2ApplicationScopeAliasesLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}