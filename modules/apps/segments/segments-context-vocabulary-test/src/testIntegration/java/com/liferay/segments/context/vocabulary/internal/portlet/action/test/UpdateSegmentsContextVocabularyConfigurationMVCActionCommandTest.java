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

package com.liferay.segments.context.vocabulary.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.MultiSessionErrors;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletSession;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class UpdateSegmentsContextVocabularyConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_locale = LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleThreadLocal.setThemeDisplayLocale(_locale);
	}

	@Test
	public void testProcessAction() throws Exception {
		String entityField = RandomTestUtil.randomString();

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertTrue(
			MultiSessionErrors.isEmpty(mockLiferayPortletActionRequest));

		Optional<Configuration> configurationOptional =
			_getConfigurationOptional(entityField);

		Assert.assertTrue(configurationOptional.isPresent());

		Configuration configuration = configurationOptional.get();

		configuration.delete();
	}

	@Test
	public void testProcessActionWithDuplicatedEntityField() throws Exception {
		String entityField = RandomTestUtil.randomString();

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertTrue(
			MultiSessionErrors.contains(
				mockLiferayPortletActionRequest,
				StringBundler.concat(
					"com.liferay.segments.context.vocabulary.internal.",
					"configuration.persistence.listener.",
					"DuplicatedSegmentsContextVocabularyConfiguration",
					"ModelListenerException")));

		Optional<Configuration> configurationOptional =
			_getConfigurationOptional(entityField);

		Configuration configuration = configurationOptional.get();

		configuration.delete();
	}

	@Test
	public void testProcessActionWithoutEntityField() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(null, null, null);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertTrue(
			MultiSessionErrors.contains(
				mockLiferayPortletActionRequest,
				ConfigurationModelListenerException.class.getCanonicalName()));
	}

	@Test
	public void testProcessActionWithPid() throws Exception {
		String entityField = RandomTestUtil.randomString();

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Optional<Configuration> configurationOptional =
			_getConfigurationOptional(entityField);

		Configuration configuration = configurationOptional.get();

		try {
			String assetVocabulary = RandomTestUtil.randomString();

			mockLiferayPortletActionRequest =
				_getMockLiferayPortletActionRequest(
					assetVocabulary, entityField, configuration.getPid());

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			configurationOptional = _getConfigurationOptional(entityField);

			configuration = configurationOptional.get();

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertEquals(
				assetVocabulary, properties.get("assetVocabulary"));
		}
		finally {
			configuration.delete();
		}
	}

	private Optional<Configuration> _getConfigurationOptional(
			String entityField)
		throws InvalidSyntaxException, IOException {

		return Stream.of(
			_configurationAdmin.listConfigurations(
				StringBundler.concat(
					"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
					"com.liferay.segments.context.vocabulary.internal.",
					"configuration.SegmentsContextVocabularyConfiguration",
					")"))
		).filter(
			configuration -> {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				return Objects.equals(
					properties.get("entityField"), entityField);
			}
		).findAny();
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
		String assetVocabulary, String entityField, String pid) {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"assetVocabulary", assetVocabulary);
		mockLiferayPortletActionRequest.setParameter(
			"entityField", entityField);
		mockLiferayPortletActionRequest.setParameter("pid", pid);
		mockLiferayPortletActionRequest.setSession(new MockPortletSession());

		return mockLiferayPortletActionRequest;
	}

	private static Locale _locale;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "mvc.command.name=/update_segments_context_vocabulary_configuration"
	)
	private MVCActionCommand _mvcActionCommand;

}