/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.resource.v1_0.test;

import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceAssetConsumption;
import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceAssetConsumptionItem;
import com.liferay.analytics.cms.rest.resource.v1_0.PerformanceAssetConsumptionResource;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.MockHttp;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import jakarta.ws.rs.BadRequestException;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rachael Koestartyo
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-34594")}
)
@RunWith(Arquillian.class)
public class PerformanceAssetConsumptionResourceTest
	extends BasePerformanceAssetConsumptionResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testGetPerformanceAssetConsumption() throws Exception {
		_performanceAssetConsumptionResource.setContextAcceptLanguage(
			new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return Arrays.asList(LocaleUtil.getDefault());
				}

				@Override
				public String getPreferredLanguageId() {
					return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.getDefault();
				}

			});

		long dataSourceId = RandomTestUtil.nextLong();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						testCompany.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId", dataSourceId
						).put(
							"liferayAnalyticsEnableAllGroupIds", true
						).put(
							"liferayAnalyticsFaroBackendSecuritySignature",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsFaroBackendURL",
							"http://" + RandomTestUtil.randomString()
						).build())) {

			_assertGetPerformanceAssetConsumptionGroupByStructure();

			_assertGetPerformanceAssetConsumptionResponse();

			_assertGetPerformanceAssetConsumptionURL(dataSourceId);

			_assertGetPerformanceAssetConsumptionWithInvalidGroupBy();
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_performanceAssetConsumptionResource, "_http", _http);
		}
	}

	private void _assertGetPerformanceAssetConsumptionGroupByStructure()
		throws Exception {

		CMSTestUtil.getOrAddGroup(
			PerformanceAssetConsumptionResourceTest.class);

		ObjectDefinition basicWebContentObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", testCompany.getCompanyId());
		ObjectDefinition blogObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BLOG", testCompany.getCompanyId());

		_setUpMockHttp(
			JSONUtil.put(
				"metrics",
				JSONUtil.putAll(
					JSONUtil.put(
						"count", 30
					).put(
						"key", RandomTestUtil.randomString()
					).put(
						"title", basicWebContentObjectDefinition.getName()
					),
					JSONUtil.put(
						"count", 20
					).put(
						"key", RandomTestUtil.randomString()
					).put(
						"title", blogObjectDefinition.getName()
					))
			).put(
				"total", 2
			).put(
				"totalCount", 50
			).toString());

		PerformanceAssetConsumption performanceAssetConsumption =
			_performanceAssetConsumptionResource.getPerformanceAssetConsumption(
				null, null, "structure", 30, null, null, null,
				Pagination.of(1, 10));

		PerformanceAssetConsumptionItem[] performanceAssetConsumptionItems =
			performanceAssetConsumption.getPerformanceAssetConsumptionItems();

		Assert.assertEquals(
			Arrays.toString(performanceAssetConsumptionItems), 2,
			performanceAssetConsumptionItems.length);
		Assert.assertEquals(
			30L, (long)performanceAssetConsumptionItems[0].getCount());
		Assert.assertEquals(
			basicWebContentObjectDefinition.getExternalReferenceCode(),
			performanceAssetConsumptionItems[0].getKey());
		Assert.assertEquals(
			basicWebContentObjectDefinition.getLabel(LocaleUtil.getDefault()),
			performanceAssetConsumptionItems[0].getTitle());
		Assert.assertEquals(
			20L, (long)performanceAssetConsumptionItems[1].getCount());
		Assert.assertEquals(
			blogObjectDefinition.getExternalReferenceCode(),
			performanceAssetConsumptionItems[1].getKey());
		Assert.assertEquals(
			blogObjectDefinition.getLabel(LocaleUtil.getDefault()),
			performanceAssetConsumptionItems[1].getTitle());
	}

	private void _assertGetPerformanceAssetConsumptionResponse()
		throws Exception {

		String key1 = RandomTestUtil.randomString();
		String key2 = RandomTestUtil.randomString();
		String title1 = RandomTestUtil.randomString();
		String title2 = RandomTestUtil.randomString();

		_setUpMockHttp(
			JSONUtil.put(
				"metrics",
				JSONUtil.putAll(
					JSONUtil.put(
						"count", 10
					).put(
						"key", key1
					).put(
						"title", title1
					),
					JSONUtil.put(
						"count", 20
					).put(
						"key", key2
					).put(
						"title", title2
					))
			).put(
				"total", 2
			).put(
				"totalCount", 30
			).toString());

		PerformanceAssetConsumption performanceAssetConsumption =
			_performanceAssetConsumptionResource.getPerformanceAssetConsumption(
				null, null, "category", RandomTestUtil.nextInt(), null, null,
				null, Pagination.of(1, 10));

		PerformanceAssetConsumptionItem[] performanceAssetConsumptionItems =
			performanceAssetConsumption.getPerformanceAssetConsumptionItems();

		Assert.assertEquals(
			Arrays.toString(performanceAssetConsumptionItems), 2,
			performanceAssetConsumptionItems.length);
		Assert.assertEquals(
			10L, (long)performanceAssetConsumptionItems[0].getCount());
		Assert.assertEquals(key1, performanceAssetConsumptionItems[0].getKey());
		Assert.assertEquals(
			title1, performanceAssetConsumptionItems[0].getTitle());
		Assert.assertEquals(
			20L, (long)performanceAssetConsumptionItems[1].getCount());
		Assert.assertEquals(key2, performanceAssetConsumptionItems[1].getKey());
		Assert.assertEquals(
			title2, performanceAssetConsumptionItems[1].getTitle());

		Assert.assertEquals(
			2L,
			(long)
				performanceAssetConsumption.
					getPerformanceAssetConsumptionItemsCount());
		Assert.assertEquals(
			30L, (long)performanceAssetConsumption.getTotalCount());
	}

	private void _assertGetPerformanceAssetConsumptionURL(long dataSourceId)
		throws Exception {

		RecordingMockHttp recordingMockHttp = _setUpMockHttp(
			JSONUtil.put(
				"metrics", JSONUtil.putAll()
			).put(
				"total", 0
			).put(
				"totalCount", 0
			).toString());

		int rangeKey = RandomTestUtil.nextInt();

		_performanceAssetConsumptionResource.getPerformanceAssetConsumption(
			11111L, null, "tag", rangeKey, null, 22222L, 33333L,
			Pagination.of(2, 5));

		String location = recordingMockHttp._getLocation();

		Assert.assertTrue(
			location,
			location.contains("assetSummaryMetricTypeString=viewsMetric"));
		Assert.assertTrue(location, location.contains("categoryId=11111"));
		Assert.assertTrue(
			location, location.contains("dataSourceId=" + dataSourceId));
		Assert.assertTrue(location, location.contains("groupBy=tag"));
		Assert.assertTrue(location, location.contains("page=2"));
		Assert.assertTrue(location, location.contains("rangeKey=" + rangeKey));
		Assert.assertTrue(location, location.contains("size=5"));
		Assert.assertTrue(location, location.contains("tagId=22222"));
		Assert.assertTrue(location, location.contains("vocabularyId=33333"));
	}

	private void _assertGetPerformanceAssetConsumptionWithInvalidGroupBy() {
		Assert.assertThrows(
			BadRequestException.class,
			() ->
				_performanceAssetConsumptionResource.
					getPerformanceAssetConsumption(
						null, null, RandomTestUtil.randomString(), 30, null,
						null, null, Pagination.of(1, 10)));
	}

	private RecordingMockHttp _setUpMockHttp(String json) {
		RecordingMockHttp recordingMockHttp = new RecordingMockHttp(
			Collections.singletonMap(
				"/api/1.0/asset-metric/objectEntry/asset-consumption",
				() -> json));

		ReflectionTestUtil.setFieldValue(
			_performanceAssetConsumptionResource, "_http", recordingMockHttp);

		return recordingMockHttp;
	}

	@Inject
	private Http _http;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private PerformanceAssetConsumptionResource
		_performanceAssetConsumptionResource;

	private static class RecordingMockHttp extends MockHttp {

		public RecordingMockHttp(
			Map<String, UnsafeSupplier<String, Exception>> unsafeSuppliers) {

			super(unsafeSuppliers);
		}

		@Override
		public String URLtoString(Http.Options options) throws IOException {
			_location = options.getLocation();

			return super.URLtoString(options);
		}

		private String _getLocation() {
			return _location;
		}

		private String _location;

	}

}