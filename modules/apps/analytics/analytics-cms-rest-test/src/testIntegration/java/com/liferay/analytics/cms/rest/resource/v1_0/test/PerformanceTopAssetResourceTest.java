/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.resource.v1_0.test;

import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceTopAsset;
import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceTopAssetItem;
import com.liferay.analytics.cms.rest.dto.v1_0.Trend;
import com.liferay.analytics.cms.rest.resource.v1_0.PerformanceTopAssetResource;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.MockHttp;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rachael Koestartyo
 */
@RunWith(Arquillian.class)
public class PerformanceTopAssetResourceTest
	extends BasePerformanceTopAssetResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testGetPerformanceTopAsset() throws Exception {
		String dataSourceId = RandomTestUtil.randomString();

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

			String assetTitle = RandomTestUtil.randomString();
			String assetType = "text/html";
			int downloads = RandomTestUtil.nextInt();
			double engagement = RandomTestUtil.nextDouble();
			double engagementTrend = RandomTestUtil.nextDouble();
			int impressions = RandomTestUtil.nextInt();
			int lastPage = RandomTestUtil.nextInt();
			int page = RandomTestUtil.nextInt();
			int pageSize = RandomTestUtil.nextInt();
			int rangeKey = RandomTestUtil.nextInt();
			int totalCount = RandomTestUtil.nextInt();
			int views = RandomTestUtil.nextInt();

			RecordingMockHttp recordingMockHttp = new RecordingMockHttp(
				Collections.singletonMap(
					"/api/1.0/asset-metric/objectEntry/summaries",
					() -> JSONUtil.put(
						"_embedded",
						JSONUtil.put(
							"assetSummaryMetrics",
							JSONUtil.putAll(
								JSONUtil.put(
									"assetTitle", assetTitle
								).put(
									"assetType", assetType
								).put(
									"downloadsMetric",
									JSONUtil.put("value", downloads)
								).put(
									"engagementMetric",
									JSONUtil.put(
										"trend",
										JSONUtil.put(
											"percentage", engagementTrend
										).put(
											"trendClassification", "POSITIVE"
										)
									).put(
										"value", engagement
									)
								).put(
									"impressionsMetric",
									JSONUtil.put("value", impressions)
								).put(
									"viewsMetric", JSONUtil.put("value", views)
								)))
					).put(
						"page",
						JSONUtil.put(
							"number", page
						).put(
							"size", pageSize
						).put(
							"totalElements", totalCount
						).put(
							"totalPages", lastPage
						)
					).toString()));

			ReflectionTestUtil.setFieldValue(
				_performanceTopAssetResource, "_http", recordingMockHttp);

			String assetFilter = RandomTestUtil.randomString();
			Sort[] sorts = {
				new Sort("title", true), new Sort("viewCount", false)
			};

			PerformanceTopAsset performanceTopAsset =
				_performanceTopAssetResource.getPerformanceTopAsset(
					assetFilter, null, rangeKey, Pagination.of(page, pageSize),
					sorts);

			_assertPerformanceTopAssetResponse(
				assetTitle, assetType, downloads, engagement, engagementTrend,
				impressions, lastPage, page, pageSize, performanceTopAsset,
				totalCount, views);

			_assertPerformanceTopAssetURL(
				assetFilter, dataSourceId, page, pageSize, rangeKey,
				recordingMockHttp, sorts);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_performanceTopAssetResource, "_http", _http);
		}
	}

	private void _assertParameter(
		String expectedValue, String name, String url) {

		Assert.assertEquals(
			expectedValue,
			URLCodec.decodeURL(
				HttpComponentsUtil.getParameter(url, name, false)));
	}

	private void _assertPerformanceTopAssetResponse(
		String assetTitle, String assetType, int downloads, double engagement,
		double engagementTrend, int impressions, int lastPage, int page,
		int pageSize, PerformanceTopAsset performanceTopAsset, int totalCount,
		int views) {

		Assert.assertEquals(lastPage, (long)performanceTopAsset.getLastPage());
		Assert.assertEquals(page, (long)performanceTopAsset.getPage());
		Assert.assertEquals(pageSize, (long)performanceTopAsset.getPageSize());
		Assert.assertEquals(
			totalCount, (long)performanceTopAsset.getTotalCount());

		PerformanceTopAssetItem[] performanceTopAssetItems =
			performanceTopAsset.getPerformanceTopAssetItems();

		Assert.assertEquals(
			Arrays.toString(performanceTopAssetItems), 1,
			performanceTopAssetItems.length);

		PerformanceTopAssetItem performanceTopAssetItem =
			performanceTopAssetItems[0];

		Assert.assertEquals(
			downloads, performanceTopAssetItem.getDownloads(), 0);
		Assert.assertEquals(
			engagement, performanceTopAssetItem.getEngagement(), 0);
		Assert.assertEquals(
			impressions, performanceTopAssetItem.getImpressions(), 0);
		Assert.assertEquals(assetType, performanceTopAssetItem.getMimeType());
		Assert.assertEquals(assetTitle, performanceTopAssetItem.getTitle());
		Assert.assertEquals(views, performanceTopAssetItem.getViews(), 0);

		Trend trend = performanceTopAssetItem.getTrend();

		Assert.assertEquals(
			Trend.Classification.POSITIVE, trend.getClassification());
		Assert.assertEquals(engagementTrend, trend.getPercentage(), 0);
	}

	private void _assertPerformanceTopAssetURL(
		String assetFilter, String dataSourceId, int page, int pageSize,
		int rangeKey, RecordingMockHttp recordingMockHttp, Sort[] sorts) {

		String location = recordingMockHttp._getLocation();

		_assertParameter(dataSourceId, "dataSourceId", location);
		_assertParameter(assetFilter, "filter", location);
		_assertParameter(String.valueOf(page), "page", location);
		_assertParameter(String.valueOf(rangeKey), "rangeKey", location);
		_assertParameter(String.valueOf(pageSize), "size", location);

		StringBundler sb = new StringBundler();

		for (int i = 0; i < sorts.length; i++) {
			if (i > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(sorts[i].getFieldName());
			sb.append(StringPool.COLON);
			sb.append(sorts[i].isReverse() ? "desc" : "asc");
		}

		_assertParameter(sb.toString(), "sort", location);
	}

	@Inject
	private Http _http;

	@Inject
	private PerformanceTopAssetResource _performanceTopAssetResource;

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