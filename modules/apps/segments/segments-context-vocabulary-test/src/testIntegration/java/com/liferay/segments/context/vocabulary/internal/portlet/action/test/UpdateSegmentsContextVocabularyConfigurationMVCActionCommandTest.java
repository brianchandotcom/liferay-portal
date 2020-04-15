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

import java.io.IOException;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

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

		MockActionRequest mockActionRequest = new MockActionRequest(
			RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertTrue(MultiSessionErrors.isEmpty(mockActionRequest));

		Optional<Configuration> configurationOptional =
			_getConfigurationOptional(entityField);

		Assert.assertTrue(configurationOptional.isPresent());

		Configuration configuration = configurationOptional.get();

		configuration.delete();
	}

	@Test
	public void testProcessActionWithDuplicatedEntityField() throws Exception {
		String entityField = RandomTestUtil.randomString();

		MockActionRequest mockActionRequest = new MockActionRequest(
			RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockActionRequest, new MockActionResponse());

		mockActionRequest = new MockActionRequest(
			RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertTrue(
			MultiSessionErrors.contains(
				mockActionRequest,
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
		MockActionRequest mockActionRequest = new MockActionRequest(
			null, null, null);

		_mvcActionCommand.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertTrue(
			MultiSessionErrors.contains(
				mockActionRequest,
				ConfigurationModelListenerException.class.getCanonicalName()));
	}

	@Test
	public void testProcessActionWithPid() throws Exception {
		String entityField = RandomTestUtil.randomString();

		MockActionRequest mockActionRequest = new MockActionRequest(
			RandomTestUtil.randomString(), entityField, null);

		_mvcActionCommand.processAction(
			mockActionRequest, new MockActionResponse());

		Optional<Configuration> configurationOptional =
			_getConfigurationOptional(entityField);

		Configuration configuration = configurationOptional.get();

		try {
			String assetVocabulary = RandomTestUtil.randomString();

			mockActionRequest = new MockActionRequest(
				assetVocabulary, entityField, configuration.getPid());

			_mvcActionCommand.processAction(
				mockActionRequest, new MockActionResponse());

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

	private static Locale _locale;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "mvc.command.name=/update_segments_context_vocabulary_configuration"
	)
	private MVCActionCommand _mvcActionCommand;

	private static class MockActionResponse
		extends MockLiferayPortletActionResponse {

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

	}

	private class MockActionRequest extends MockLiferayPortletActionRequest {

		public MockActionRequest(
			String assetVocabulary, String entityField, String pid) {

			_httpServletRequest = new MockHttpServletRequest() {
				{
					setParameter("assetVocabulary", assetVocabulary);
					setParameter("entityField", entityField);
					setParameter("pid", pid);
					setSession(new MockHttpSession());
				}
			};
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public HttpServletRequest getOriginalHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public String getParameter(String name) {
			return _httpServletRequest.getParameter(name);
		}

		private final HttpServletRequest _httpServletRequest;

	}

}