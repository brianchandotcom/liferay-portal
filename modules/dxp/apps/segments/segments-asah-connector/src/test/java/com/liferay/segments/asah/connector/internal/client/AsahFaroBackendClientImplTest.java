/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariant;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariants;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;
import com.liferay.segments.asah.connector.internal.client.util.OrderByField;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sarai Díaz
 */
public class AsahFaroBackendClientImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_analyticsSettingsManager = Mockito.mock(
			AnalyticsSettingsManager.class);

		Mockito.when(
			_analyticsSettingsManager.getAnalyticsConfiguration(
				Mockito.anyLong())
		).thenReturn(
			Mockito.mock(AnalyticsConfiguration.class)
		);

		_setUpPortalUtil();
	}

	@Test
	public void testAddExperiment() throws Exception {
		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/experiments",
						JSONUtil.put(
							"id", "123456"
						).toString())));

		Experiment experiment = asahFaroBackendClient.addExperiment(
			RandomTestUtil.randomLong(), new Experiment());

		Assert.assertEquals("123456", experiment.getId());
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDuration()
		throws Exception {

		String days = "14";

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/estimated-days-duration", days)));

		Assert.assertEquals(
			Long.valueOf(days),
			asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDurationWithEmptyResult()
		throws Exception {

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/estimated-days-duration", StringPool.BLANK)));

		Assert.assertNull(
			asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test(expected = NestableRuntimeException.class)
	public void testCalculateExperimentEstimatedDaysDurationWithInvalidResult()
		throws Exception {

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/estimated-days-duration",
						RandomTestUtil.randomString())));

		asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			new ExperimentSettings());
	}

	@Test
	public void testDeleteExperiment() throws Exception {
		long experimentId = RandomTestUtil.randomLong();

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/" + experimentId,
						JSONUtil.put(
							"id", "123456"
						).toString())));

		asahFaroBackendClient.deleteExperiment(
			RandomTestUtil.randomLong(), String.valueOf(experimentId));
	}

	@Test
	public void testGetIndividual() throws Exception {
		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/individuals",
						JSONUtil.put(
							"_embedded",
							JSONUtil.put(
								"individuals",
								JSONUtil.putAll(JSONUtil.put("id", "1234567")))
						).put(
							"page",
							JSONUtil.put(
								"number", 0
							).put(
								"size", 100
							).put(
								"totalElements", 1
							).put(
								"totalPages", 1
							)
						).put(
							"total", 0
						).toString())));

		Individual individual = asahFaroBackendClient.getIndividual(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals("1234567", individual.getId());
	}

	@Test
	public void testGetIndividualResults() throws Exception {
		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/individuals",
						JSONUtil.put(
							"_embedded",
							JSONUtil.put(
								"individuals",
								JSONUtil.putAll(
									JSONUtil.put(
										"dataSourceIndividualPKs",
										JSONUtil.putAll(
											JSONUtil.put(
												"dataSourceId", "123456789"
											).put(
												"individualPKs",
												JSONUtil.putAll("userUuid")
											)))))
						).put(
							"page",
							JSONUtil.put(
								"number", 0
							).put(
								"size", 100
							).put(
								"totalElements", 1
							).put(
								"totalPages", 1
							)
						).put(
							"total", 0
						).toString())));

		Results<Individual> individualResults =
			asahFaroBackendClient.getIndividualResults(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomInt(), RandomTestUtil.randomInt(),
				Collections.singletonList(OrderByField.desc("dateModified")));

		List<Individual> individuals = individualResults.getItems();

		Assert.assertEquals(individuals.toString(), 1, individuals.size());

		Individual individual = individuals.get(0);

		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Assert.assertEquals(
			dataSourceIndividualPKs.toString(), 1,
			dataSourceIndividualPKs.size());

		Individual.DataSourceIndividualPK dataSourceIndividualPK =
			dataSourceIndividualPKs.get(0);

		Assert.assertEquals(
			"123456789", dataSourceIndividualPK.getDataSourceId());
		Assert.assertEquals(
			Collections.singletonList("userUuid"),
			dataSourceIndividualPK.getIndividualPKs());
	}

	@Test
	public void testGetIndividualSegmentResults() throws Exception {
		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/individual-segments",
						JSONUtil.put(
							"_embedded",
							JSONUtil.put(
								"individual-segments",
								JSONUtil.putAll(
									JSONUtil.put(
										"id", "1234567"
									).put(
										"name", "Test segment"
									)))
						).put(
							"page",
							JSONUtil.put(
								"number", 0
							).put(
								"size", 100
							).put(
								"totalElements", 1
							).put(
								"totalPages", 1
							)
						).put(
							"total", 0
						).toString())));

		Results<IndividualSegment> individualSegmentResults =
			asahFaroBackendClient.getIndividualSegmentResults(
				RandomTestUtil.randomLong(), RandomTestUtil.randomInt(),
				RandomTestUtil.randomInt(),
				Collections.singletonList(OrderByField.desc("dateModified")));

		List<IndividualSegment> individualSegments =
			individualSegmentResults.getItems();

		Assert.assertEquals(
			individualSegments.toString(), 1, individualSegments.size());

		IndividualSegment individualSegment = individualSegments.get(0);

		Assert.assertEquals("1234567", individualSegment.getId());
		Assert.assertEquals("Test segment", individualSegment.getName());
	}

	@Test
	public void testGetInterestTermsResults() throws Exception {
		String userId = String.valueOf(RandomTestUtil.randomLong());

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/" + userId,
						JSONUtil.put(
							"_embedded",
							JSONUtil.put(
								"interest-topics",
								JSONUtil.putAll(
									JSONUtil.put(
										"terms",
										JSONUtil.putAll(
											JSONUtil.put("keyword", "term1")))))
						).put(
							"page",
							JSONUtil.put(
								"number", 0
							).put(
								"size", 100
							).put(
								"totalElements", 1
							).put(
								"totalPages", 1
							)
						).put(
							"total", 0
						).toString())));

		Results<Topic> interestTermsResults =
			asahFaroBackendClient.getInterestTermsResults(
				RandomTestUtil.randomLong(), userId);

		List<Topic> topics = interestTermsResults.getItems();

		Assert.assertEquals(topics.toString(), 1, topics.size());

		Topic topic = topics.get(0);

		List<Topic.TopicTerm> topicTerms = topic.getTerms();

		Assert.assertEquals(topicTerms.toString(), 1, topicTerms.size());

		Topic.TopicTerm topicTerm = topicTerms.get(0);

		Assert.assertEquals("term1", topicTerm.getKeyword());
	}

	@Test
	public void testUpdateExperiment() throws Exception {
		String experimentId = RandomTestUtil.randomString();

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/" + experimentId, StringPool.BLANK)));

		Experiment experiment = new Experiment();

		experiment.setId(experimentId);

		asahFaroBackendClient.updateExperiment(
			RandomTestUtil.randomLong(), experiment);
	}

	@Test
	public void testUpdateExperimentDXPVariants() throws Exception {
		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/dxp-variants", StringPool.BLANK)));

		DXPVariant dxpVariant = new DXPVariant();

		dxpVariant.setDXPVariantId(RandomTestUtil.randomString());

		DXPVariants dxpVariants = new DXPVariants(
			Collections.singletonList(dxpVariant));

		asahFaroBackendClient.updateExperimentDXPVariants(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			dxpVariants);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateExperimentDXPVariantsWithoutDXPVariants()
		throws Exception {

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/dxp-variants", StringPool.BLANK)));

		asahFaroBackendClient.updateExperimentDXPVariants(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateExperimentDXPVariantsWithoutExperimentId()
		throws Exception {

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/dxp-variants", StringPool.BLANK)));

		DXPVariant dxpVariant = new DXPVariant();

		dxpVariant.setDXPVariantId(RandomTestUtil.randomString());

		DXPVariants dxpVariants = new DXPVariants(
			Collections.singletonList(dxpVariant));

		asahFaroBackendClient.updateExperimentDXPVariants(
			RandomTestUtil.randomLong(), null, dxpVariants);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateExperimentWithoutExperimentId() throws Exception {
		String experimentId = RandomTestUtil.randomString();

		AsahFaroBackendClient asahFaroBackendClient =
			new AsahFaroBackendClientImpl(
				_analyticsSettingsManager,
				_getHttp(
					Collections.singletonMap(
						"/" + experimentId, StringPool.BLANK)));

		asahFaroBackendClient.updateExperiment(
			RandomTestUtil.randomLong(), new Experiment());
	}

	private Http _getHttp(Map<String, String> mockRequest) throws Exception {
		Http http = Mockito.mock(Http.class);

		Mockito.when(
			http.URLtoString(Mockito.any(Http.Options.class))
		).then(
			answer -> {
				Http.Options options = (Http.Options)answer.getArguments()[0];

				String location = options.getLocation();

				int endIndex = location.indexOf("?");

				if (endIndex == -1) {
					endIndex = location.length();
				}

				String endpoint = location.substring(
					location.lastIndexOf("/"), endIndex);

				if (mockRequest.containsKey(endpoint)) {
					Http.Response httpResponse = new Http.Response();

					httpResponse.setResponseCode(200);

					options.setResponse(httpResponse);

					return mockRequest.get(endpoint);
				}

				Http.Response httpResponse = new Http.Response();

				httpResponse.setResponseCode(400);

				options.setResponse(httpResponse);

				return "error, endpoint not found";
			}
		);

		return http;
	}

	private void _setUpPortalUtil() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		portalUtil.setPortal(portal);
	}

	private AnalyticsSettingsManager _analyticsSettingsManager;

}