/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

import com.liferay.ai.hub.configuration.VertexAIConfiguration;
import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Iliyan Peychev
 */
public class VertexAiGeminiUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_configurationProviderUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_vertexAIConfiguration.location()
		).thenReturn(
			"us-central1"
		);

		Mockito.when(
			_vertexAIConfiguration.modelName()
		).thenReturn(
			"configured-model"
		);

		Mockito.when(
			_vertexAIConfiguration.projectId()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getCompanyConfiguration(
				Mockito.eq(VertexAIConfiguration.class), Mockito.anyLong())
		).thenReturn(
			_vertexAIConfiguration
		);
	}

	@Test
	public void testCreateVertexAiGeminiStreamingChatModel() throws Exception {
		try (VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel =
				VertexAiGeminiUtil.createVertexAiGeminiStreamingChatModel(
					_quotaManager, _serviceContext)) {

			Assert.assertEquals(
				"us-central1", _getLocation(vertexAiGeminiStreamingChatModel));
			Assert.assertEquals(
				"configured-model",
				_getModelName(vertexAiGeminiStreamingChatModel));
		}
	}

	@Test
	public void testCreateVertexAiGeminiStreamingChatModelWithModelName()
		throws Exception {

		try (VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel =
				VertexAiGeminiUtil.createVertexAiGeminiStreamingChatModel(
					VertexAiGeminiUtil.TOOL_CALLING_MODEL_NAME, _quotaManager,
					_serviceContext)) {

			Assert.assertEquals(
				VertexAiGeminiUtil.TOOL_CALLING_LOCATION,
				_getLocation(vertexAiGeminiStreamingChatModel));
			Assert.assertEquals(
				VertexAiGeminiUtil.TOOL_CALLING_MODEL_NAME,
				_getModelName(vertexAiGeminiStreamingChatModel));
		}
	}

	@Test
	public void testCreateVertexAiGeminiStreamingChatModelWithMultiRegionLocation()
		throws Exception {

		Mockito.when(
			_vertexAIConfiguration.location()
		).thenReturn(
			"eu"
		);

		try (VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel =
				VertexAiGeminiUtil.createVertexAiGeminiStreamingChatModel(
					_quotaManager, _serviceContext)) {

			Assert.assertEquals(
				"aiplatform.eu.rep.googleapis.com",
				_getAPIEndpoint(vertexAiGeminiStreamingChatModel));
			Assert.assertEquals(
				"eu", _getLocation(vertexAiGeminiStreamingChatModel));
		}
	}

	private String _getAPIEndpoint(
		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel) {

		VertexAI vertexAI = ReflectionTestUtil.getFieldValue(
			vertexAiGeminiStreamingChatModel, "vertexAI");

		return vertexAI.getApiEndpoint();
	}

	private String _getLocation(
		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel) {

		VertexAI vertexAI = ReflectionTestUtil.getFieldValue(
			vertexAiGeminiStreamingChatModel, "vertexAI");

		return vertexAI.getLocation();
	}

	private String _getModelName(
		VertexAiGeminiStreamingChatModel vertexAiGeminiStreamingChatModel) {

		GenerativeModel generativeModel = ReflectionTestUtil.getFieldValue(
			vertexAiGeminiStreamingChatModel, "generativeModel");

		String modelName = generativeModel.getModelName();

		return modelName.substring(modelName.lastIndexOf('/') + 1);
	}

	private static final MockedStatic<ConfigurationProviderUtil>
		_configurationProviderUtilMockedStatic = Mockito.mockStatic(
			ConfigurationProviderUtil.class);

	private final QuotaManager _quotaManager = Mockito.mock(QuotaManager.class);
	private final ServiceContext _serviceContext = new ServiceContext();
	private final VertexAIConfiguration _vertexAIConfiguration = Mockito.mock(
		VertexAIConfiguration.class);

}