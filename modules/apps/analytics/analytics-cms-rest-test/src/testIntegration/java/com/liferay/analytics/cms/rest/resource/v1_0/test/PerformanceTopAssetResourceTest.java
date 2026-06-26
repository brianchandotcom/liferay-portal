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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import java.io.ByteArrayOutputStream;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.Collections;

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

			_testGetPerformanceTopAssetResponse();
			_testGetPerformanceTopAssetURL(dataSourceId);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_performanceTopAssetResource, "_http", _http);
		}
	}

	@Override
	@Test
	public void testGetPerformanceTopAssetExport() throws Exception {
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

			_testGetPerformanceTopAssetExportResponse();
			_testGetPerformanceTopAssetExportURL(dataSourceId);
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

	private RecordingMockHttp _setUpRecordingMockHttp(
		String json, String path) {

		RecordingMockHttp recordingMockHttp = new RecordingMockHttp(
			Collections.singletonMap(path, () -> json));

		ReflectionTestUtil.setFieldValue(
			_performanceTopAssetResource, "_http", recordingMockHttp);

		return recordingMockHttp;
	}

	private void _testGetPerformanceTopAssetExportResponse() throws Exception {
		String value = RandomTestUtil.randomString();

		_setUpRecordingMockHttp(
			value, "/api/1.0/asset-metric/objectEntry/summaries/export");

		Response response =
			_performanceTopAssetResource.getPerformanceTopAssetExport(
				RandomTestUtil.randomString(), null, RandomTestUtil.nextInt(),
				null);

		Assert.assertEquals(
			"attachment; filename=top-assets-" + LocalDate.now() + ".csv",
			response.getHeaderString("Content-Disposition"));

		StreamingOutput streamingOutput = (StreamingOutput)response.getEntity();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		streamingOutput.write(byteArrayOutputStream);

		Assert.assertEquals(value, byteArrayOutputStream.toString());
	}

	private void _testGetPerformanceTopAssetExportURL(String dataSourceId)
		throws Exception {

		RecordingMockHttp recordingMockHttp = _setUpRecordingMockHttp(
			RandomTestUtil.randomString(),
			"/api/1.0/asset-metric/objectEntry/summaries/export");

		String assetFilterString = RandomTestUtil.randomString();
		int rangeKey = RandomTestUtil.nextInt();
		Sort[] sorts = {
			new Sort(RandomTestUtil.randomString(), true),
			new Sort(RandomTestUtil.randomString(), false)
		};

		_performanceTopAssetResource.getPerformanceTopAssetExport(
			assetFilterString, null, rangeKey, sorts);

		String location = recordingMockHttp.getLocation();

		_assertParameter(dataSourceId, "dataSourceId", location);
		_assertParameter(assetFilterString, "filter", location);
		_assertParameter(String.valueOf(rangeKey), "rangeKey", location);

		StringBundler sb = new StringBundler();

		for (int i = 0; i < sorts.length; i++) {
			if (i > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(sorts[i].getFieldName());
			sb.append(StringPool.COMMA);
			sb.append(sorts[i].isReverse() ? "desc" : "asc");
		}

		_assertParameter(sb.toString(), "sort", location);
	}

	private void _testGetPerformanceTopAssetResponse() throws Exception {
		String assetTitle = RandomTestUtil.randomString();
		String assetType = RandomTestUtil.randomString();
		int downloads = RandomTestUtil.nextInt();
		double engagement = RandomTestUtil.nextDouble();
		double engagementTrend = RandomTestUtil.nextDouble();
		int impressions = RandomTestUtil.nextInt();
		int lastPage = RandomTestUtil.nextInt();
		int page = RandomTestUtil.nextInt();
		int pageSize = RandomTestUtil.nextInt();
		int totalCount = RandomTestUtil.nextInt();
		int views = RandomTestUtil.nextInt();

		_setUpRecordingMockHttp(
			JSONUtil.put(
				"_embedded",
				JSONUtil.put(
					"assetSummaryMetrics",
					JSONUtil.putAll(
						JSONUtil.put(
							"assetTitle", assetTitle
						).put(
							"assetType", assetType
						).put(
							"downloadsMetric", JSONUtil.put("value", downloads)
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
			).toString(),
			"/api/1.0/asset-metric/objectEntry/summaries");

		PerformanceTopAsset performanceTopAsset =
			_performanceTopAssetResource.getPerformanceTopAsset(
				RandomTestUtil.randomString(), null, RandomTestUtil.nextInt(),
				Pagination.of(page, pageSize), null);

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

		Trend trend = performanceTopAssetItem.getTrend();

		Assert.assertEquals(
			Trend.Classification.POSITIVE.toString(),
			String.valueOf(trend.getClassification()));
		Assert.assertEquals(engagementTrend, trend.getPercentage(), 0);

		Assert.assertEquals(views, performanceTopAssetItem.getViews(), 0);
	}

	private void _testGetPerformanceTopAssetURL(String dataSourceId)
		throws Exception {

		RecordingMockHttp recordingMockHttp = _setUpRecordingMockHttp(
			"{}", "/api/1.0/asset-metric/objectEntry/summaries");

		String assetFilterString = RandomTestUtil.randomString();
		int page = RandomTestUtil.nextInt();
		int pageSize = RandomTestUtil.nextInt();
		int rangeKey = RandomTestUtil.nextInt();

		Sort[] sorts = {
			new Sort(RandomTestUtil.randomString(), true),
			new Sort(RandomTestUtil.randomString(), false)
		};

		_performanceTopAssetResource.getPerformanceTopAsset(
			assetFilterString, null, rangeKey, Pagination.of(page, pageSize),
			sorts);

		String location = recordingMockHttp.getLocation();

		_assertParameter(dataSourceId, "dataSourceId", location);
		_assertParameter(assetFilterString, "filter", location);
		_assertParameter(String.valueOf(page - 1), "page", location);
		_assertParameter(String.valueOf(rangeKey), "rangeKey", location);
		_assertParameter(String.valueOf(pageSize), "size", location);

		StringBundler sb = new StringBundler();

		for (int i = 0; i < sorts.length; i++) {
			if (i > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(sorts[i].getFieldName());
			sb.append(StringPool.COMMA);
			sb.append(sorts[i].isReverse() ? "desc" : "asc");
		}

		_assertParameter(sb.toString(), "sort", location);
	}

	@Inject
	private Http _http;

	@Inject
	private PerformanceTopAssetResource _performanceTopAssetResource;

}