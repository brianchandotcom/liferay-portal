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

package com.liferay.analytics.reports.web.internal.data.provider;

import com.liferay.analytics.reports.web.internal.model.AcquisitionChannel;
import com.liferay.analytics.reports.web.internal.model.CountrySearchKeywords;
import com.liferay.analytics.reports.web.internal.model.HistogramMetric;
import com.liferay.analytics.reports.web.internal.model.HistoricalMetric;
import com.liferay.analytics.reports.web.internal.model.OrganicTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.PaidTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.ReferringSocialMedia;
import com.liferay.analytics.reports.web.internal.model.ReferringURL;
import com.liferay.analytics.reports.web.internal.model.SearchKeyword;
import com.liferay.analytics.reports.web.internal.model.SocialTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.analytics.reports.web.internal.model.TrafficChannel;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author David Arques
 */
public class AnalyticsReportsDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			Mockito.mock(PrefsProps.class));
	}

	@Test
	public void testGetAcquisitionChannels() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/acquisition-channels",
						JSONUtil.put(
							"direct", 5847L
						).put(
							"organic", 1732L
						).put(
							"paid", 1235L
						).put(
							"referrer", 3849L
						).put(
							"social", 735L
						).toString())));

		Map<String, AcquisitionChannel> acquisitionChannels =
			analyticsReportsDataProvider.getAcquisitionChannels(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			acquisitionChannels.toString(), 5, acquisitionChannels.size());
		Assert.assertEquals(
			String.valueOf(new AcquisitionChannel("direct", 5847L, 43.64D)),
			String.valueOf(acquisitionChannels.get("direct")));
		Assert.assertEquals(
			String.valueOf(new AcquisitionChannel("organic", 1732L, 12.93D)),
			String.valueOf(acquisitionChannels.get("organic")));
		Assert.assertEquals(
			String.valueOf(new AcquisitionChannel("paid", 1235L, 9.22)),
			String.valueOf(acquisitionChannels.get("paid")));
		Assert.assertEquals(
			String.valueOf(new AcquisitionChannel("referrer", 3849L, 28.73D)),
			String.valueOf(acquisitionChannels.get("referrer")));
		Assert.assertEquals(
			String.valueOf(new AcquisitionChannel("social", 735L, 5.49D)),
			String.valueOf(acquisitionChannels.get("social")));
	}

	@Test
	public void testGetDomainReferringURLs() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/page-referrer-hosts",
						JSONUtil.put(
							"abc.com", 3.0
						).put(
							"google.com", 6.0
						).put(
							"liferay.com", 1.0
						).toString())));

		List<ReferringURL> referringURLS =
			analyticsReportsDataProvider.getDomainReferringURLs(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(referringURLS.toString(), 3, referringURLS.size());
		Assert.assertEquals(
			String.valueOf(new ReferringURL(1, "liferay.com")),
			String.valueOf(referringURLS.get(0)));
		Assert.assertEquals(
			String.valueOf(new ReferringURL(6, "google.com")),
			String.valueOf(referringURLS.get(1)));
		Assert.assertEquals(
			String.valueOf(new ReferringURL(3, "abc.com")),
			String.valueOf(referringURLS.get(2)));
	}

	@Test
	public void testGetHistoricalReadsHistoricalMetric() throws Exception {
		LocalDate localDate = LocalDate.now();

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/read-counts",
						JSONUtil.put(
							"histogram",
							JSONUtil.put(
								JSONUtil.put(
									"key",
									localDate.format(
										DateTimeFormatter.ISO_LOCAL_DATE)
								).put(
									"value", 5
								))
						).put(
							"value", 5
						).toJSONString())));

		HistoricalMetric historicalMetric =
			analyticsReportsDataProvider.getHistoricalReadsHistoricalMetric(
				RandomTestUtil.randomLong(),
				TimeRange.of(TimeSpan.LAST_7_DAYS, 0),
				RandomTestUtil.randomString());

		Assert.assertEquals(5.0, historicalMetric.getValue(), 0.0);

		List<HistogramMetric> histogramMetrics =
			historicalMetric.getHistogramMetrics();

		Assert.assertEquals(
			histogramMetrics.toString(), 1, histogramMetrics.size());

		HistogramMetric histogramMetric = histogramMetrics.get(0);

		Assert.assertEquals(5.0, histogramMetric.getValue(), 0.0);

		Date date = histogramMetric.getKey();

		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		Assert.assertEquals(localDate, zonedDateTime.toLocalDate());
	}

	@Test
	public void testGetPageReferringURLs() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/page-referrers",
						JSONUtil.put(
							"https://slickdeals.net/computer-deals", 6.0
						).put(
							"https://slickdeals.net/credit-card-offers/", 1.0
						).put(
							"https://www.tomshardware.com/news/toms-hardware-" +
								"live-events-ces-2021",
							3.0
						).toString())));

		List<ReferringURL> referringURLS =
			analyticsReportsDataProvider.getPageReferringURLs(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(referringURLS.toString(), 3, referringURLS.size());
		Assert.assertEquals(
			String.valueOf(
				new ReferringURL(
					1, "https://slickdeals.net/credit-card-offers/")),
			String.valueOf(referringURLS.get(0)));
		Assert.assertEquals(
			String.valueOf(
				new ReferringURL(
					3,
					"https://www.tomshardware.com/news/toms-hardware-live-" +
						"events-ces-2021")),
			String.valueOf(referringURLS.get(1)));
		Assert.assertEquals(
			String.valueOf(
				new ReferringURL(6, "https://slickdeals.net/computer-deals")),
			String.valueOf(referringURLS.get(2)));
	}

	@Test
	public void testGetReferringSocialMediaList() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/social-page-referrers",
						JSONUtil.put(
							"facebook", 6.0
						).put(
							"instagram", 3.0
						).put(
							"linkedin", 1.0
						).toString())));

		List<ReferringSocialMedia> referringSocialMediaList =
			analyticsReportsDataProvider.getReferringSocialMediaList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			referringSocialMediaList.toString(), 3,
			referringSocialMediaList.size());
		Assert.assertEquals(
			String.valueOf(new ReferringSocialMedia("facebook", 6)),
			String.valueOf(referringSocialMediaList.get(0)));
		Assert.assertEquals(
			String.valueOf(new ReferringSocialMedia("instagram", 3)),
			String.valueOf(referringSocialMediaList.get(1)));
		Assert.assertEquals(
			String.valueOf(new ReferringSocialMedia("linkedin", 1)),
			String.valueOf(referringSocialMediaList.get(2)));
	}

	@Test
	public void testGetTotalReads() throws Exception {
		LocalDate localDate = LocalDate.now();

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					HashMapBuilder.put(
						"/read-count", "12345"
					).put(
						"/read-counts",
						JSONUtil.put(
							"histogram",
							JSONUtil.put(
								JSONUtil.put(
									"key",
									localDate.format(
										DateTimeFormatter.ISO_LOCAL_DATE)
								).put(
									"value", 5
								))
						).put(
							"value", 5
						).toJSONString()
					).build()));

		Long totalReads = analyticsReportsDataProvider.getTotalReads(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(Long.valueOf(12340), totalReads);
	}

	@Test(expected = PortalException.class)
	public void testGetTotalReadsWithAsahFaroBackendError() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_getHttp(new IOException()));

		analyticsReportsDataProvider.getTotalReads(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetTotalViews() throws Exception {
		LocalDate localDate = LocalDate.now();

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					HashMapBuilder.put(
						"/view-count", "12345"
					).put(
						"/view-counts",
						JSONUtil.put(
							"histogram",
							JSONUtil.put(
								JSONUtil.put(
									"key",
									localDate.format(
										DateTimeFormatter.ISO_LOCAL_DATE)
								).put(
									"value", 5
								))
						).put(
							"value", 5
						).toJSONString()
					).build()));

		Long totalViews = analyticsReportsDataProvider.getTotalViews(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(Long.valueOf(12340), totalViews);
	}

	@Test(expected = PortalException.class)
	public void testGetTotalViewsWithAsahFaroBackendError() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_getHttp(new IOException()));

		analyticsReportsDataProvider.getTotalViews(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetTrafficChannels() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					HashMapBuilder.put(
						"/acquisition-channels",
						JSONUtil.put(
							"organic", 3849L
						).put(
							"paid", 235L
						).put(
							"social", 389L
						).toString()
					).put(
						"/page-referrer-hosts", "{}"
					).put(
						"/page-referrers", "{}"
					).put(
						"/social-page-referrers", "{}"
					).put(
						"/traffic-sources",
						JSONUtil.putAll(
							JSONUtil.put(
								"name", "organic"
							).put(
								"trafficAmount", 7849L
							).put(
								"trafficShare", 97.25D
							),
							JSONUtil.put(
								"name", "paid"
							).put(
								"trafficAmount", 135L
							).put(
								"trafficShare", 56.75D
							)
						).toString()
					).build()));

		Map<String, TrafficChannel> trafficChannels =
			analyticsReportsDataProvider.getTrafficChannels(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			trafficChannels.toString(), 3, trafficChannels.size());
		Assert.assertEquals(
			String.valueOf(new OrganicTrafficChannelImpl(null, 3849L, 86.0D)),
			String.valueOf(trafficChannels.get("organic")));
		Assert.assertEquals(
			String.valueOf(new PaidTrafficChannelImpl(null, 235L, 5.3D)),
			String.valueOf(trafficChannels.get("paid")));
		Assert.assertEquals(
			String.valueOf(new SocialTrafficChannelImpl(null, 389L, 8.7D)),
			String.valueOf(trafficChannels.get("social")));
	}

	@Test(expected = PortalException.class)
	public void testGetTrafficChannelsWithAsahFaroBackendError()
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_getHttp(new IOException()));

		analyticsReportsDataProvider.getTrafficChannels(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetTrafficChannelsWithCountrySearchKeywords()
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					HashMapBuilder.put(
						"/acquisition-channels",
						JSONUtil.put(
							"organic", 3192L
						).put(
							"paid", 206L
						).toString()
					).put(
						"/page-referrer-hosts", "{}"
					).put(
						"/page-referrers", "{}"
					).put(
						"/social-page-referrers", "{}"
					).put(
						"/traffic-sources",
						JSONUtil.putAll(
							JSONUtil.put(
								"countryKeywords",
								JSONUtil.putAll(
									JSONUtil.put(
										"countryCode", "us"
									).put(
										"countryName", "United States"
									).put(
										"keywords",
										JSONUtil.putAll(
											JSONUtil.put(
												"keyword", "liferay"
											).put(
												"position", 1
											).put(
												"searchVolume", 3600
											).put(
												"traffic", 2880L
											),
											JSONUtil.put(
												"keyword", "liferay portal"
											).put(
												"position", 1
											).put(
												"searchVolume", 390
											).put(
												"traffic", 312L
											))
									))
							).put(
								"name", "organic"
							).put(
								"trafficAmount", 1192L
							).put(
								"trafficShare", 83.9D
							),
							JSONUtil.put(
								"countryKeywords",
								JSONUtil.putAll(
									JSONUtil.put(
										"countryCode", "us"
									).put(
										"countryName", "United States"
									).put(
										"keywords",
										JSONUtil.putAll(
											JSONUtil.put(
												"keyword", "dxp enterprises"
											).put(
												"position", 1
											).put(
												"searchVolume", 4400
											).put(
												"traffic", 206L
											))
									))
							).put(
								"name", "paid"
							).put(
								"trafficAmount", 2010L
							).put(
								"trafficShare", 44.1D
							)
						).toString()
					).build()));

		Map<String, TrafficChannel> trafficChannels =
			analyticsReportsDataProvider.getTrafficChannels(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			trafficChannels.toString(), 2, trafficChannels.size());
		Assert.assertEquals(
			String.valueOf(
				new OrganicTrafficChannelImpl(
					Collections.singletonList(
						new CountrySearchKeywords(
							"us",
							Arrays.asList(
								new SearchKeyword("liferay", 1, 3600, 2880L),
								new SearchKeyword(
									"liferay portal", 1, 390, 312L)))),
					3192L, 93.9D)),
			String.valueOf(trafficChannels.get("organic")));
		Assert.assertEquals(
			String.valueOf(
				new PaidTrafficChannelImpl(
					Collections.singletonList(
						new CountrySearchKeywords(
							"us",
							Collections.singletonList(
								new SearchKeyword(
									"dxp enterprises", 1, 4400, 206L)))),
					206L, 6.06D)),
			String.valueOf(trafficChannels.get("paid")));
	}

	@Test
	public void testGetTrafficChannelsWithReferringSocialMedia()
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					HashMapBuilder.put(
						"/acquisition-channels",
						JSONUtil.put(
							"organic", 3849L
						).put(
							"paid", 235L
						).put(
							"social", 389L
						).toString()
					).put(
						"/page-referrer-hosts", "{}"
					).put(
						"/page-referrers", "{}"
					).put(
						"/social-page-referrers",
						JSONUtil.put(
							"facebook", 389.0
						).toString()
					).put(
						"/traffic-sources",
						JSONUtil.putAll(
							JSONUtil.put(
								"name", "organic"
							).put(
								"trafficAmount", 7849L
							).put(
								"trafficShare", 97.25D
							),
							JSONUtil.put(
								"name", "paid"
							).put(
								"trafficAmount", 135L
							).put(
								"trafficShare", 56.75D
							)
						).toString()
					).build()));

		Map<String, TrafficChannel> trafficChannels =
			analyticsReportsDataProvider.getTrafficChannels(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			String.valueOf(
				new SocialTrafficChannelImpl(
					Collections.singletonList(
						new ReferringSocialMedia("facebook", 389)),
					389L, 8.7D)),
			String.valueOf(trafficChannels.get("social")));
	}

	@Test
	public void testGetTrafficSources() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/traffic-sources",
						JSONUtil.putAll(
							JSONUtil.put(
								"name", "organic"
							).put(
								"trafficAmount", 1192L
							).put(
								"trafficShare", 83.9D
							),
							JSONUtil.put(
								"name", "paid"
							).put(
								"trafficAmount", 2010L
							).put(
								"trafficShare", 44.1D
							)
						).toString())));

		Map<String, TrafficSource> trafficSources =
			analyticsReportsDataProvider.getTrafficSources(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			trafficSources.toString(), 2, trafficSources.size());
		Assert.assertEquals(
			String.valueOf(
				new TrafficSource(
					Collections.emptyList(), "organic", 1192L, 83.9D)),
			String.valueOf(trafficSources.get("organic")));
		Assert.assertEquals(
			String.valueOf(
				new TrafficSource(
					Collections.emptyList(), "paid", 2010L, 44.1D)),
			String.valueOf(trafficSources.get("paid")));
	}

	@Test
	public void testGetTrafficSourcesWithAsahFaroBackendError()
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_getHttp(new IOException()));

		Map<String, TrafficSource> trafficSources =
			analyticsReportsDataProvider.getTrafficSources(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertTrue(trafficSources.isEmpty());
	}

	@Test
	public void testGetTrafficSourcesWithCountrySearchKeywords()
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				_getHttp(
					Collections.singletonMap(
						"/traffic-sources",
						JSONUtil.putAll(
							JSONUtil.put(
								"countryKeywords",
								JSONUtil.putAll(
									JSONUtil.put(
										"countryCode", "us"
									).put(
										"countryName", "United States"
									).put(
										"keywords",
										JSONUtil.putAll(
											JSONUtil.put(
												"keyword", "liferay"
											).put(
												"position", 1
											).put(
												"searchVolume", 3600
											).put(
												"traffic", 2880L
											),
											JSONUtil.put(
												"keyword", "liferay portal"
											).put(
												"position", 1
											).put(
												"searchVolume", 390
											).put(
												"traffic", 312L
											))
									))
							).put(
								"name", "organic"
							).put(
								"trafficAmount", 1192L
							).put(
								"trafficShare", 83.9D
							),
							JSONUtil.put(
								"countryKeywords",
								JSONUtil.putAll(
									JSONUtil.put(
										"countryCode", "us"
									).put(
										"countryName", "United States"
									).put(
										"keywords",
										JSONUtil.putAll(
											JSONUtil.put(
												"keyword", "dxp enterprises"
											).put(
												"position", 1
											).put(
												"searchVolume", 4400
											).put(
												"traffic", 206L
											))
									))
							).put(
								"name", "paid"
							).put(
								"trafficAmount", 2010L
							).put(
								"trafficShare", 44.1D
							)
						).toString())));

		Map<String, TrafficSource> trafficSources =
			analyticsReportsDataProvider.getTrafficSources(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			trafficSources.toString(), 2, trafficSources.size());
		Assert.assertEquals(
			String.valueOf(
				new TrafficSource(
					Collections.singletonList(
						new CountrySearchKeywords(
							"us",
							Arrays.asList(
								new SearchKeyword("liferay", 1, 3600, 2880L),
								new SearchKeyword(
									"liferay portal", 1, 390, 312L)))),
					"organic", 1192L, 83.9D)),
			String.valueOf(trafficSources.get("organic")));
		Assert.assertEquals(
			String.valueOf(
				new TrafficSource(
					Collections.singletonList(
						new CountrySearchKeywords(
							"us",
							Collections.singletonList(
								new SearchKeyword(
									"dxp enterprises", 1, 4400, 206L)))),
					"paid", 2010L, 44.1D)),
			String.valueOf(trafficSources.get("paid")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewAnalyticsReportsDataProviderWithNullHttp() {
		new AnalyticsReportsDataProvider(null);
	}

	private Http _getHttp(Exception exception) throws Exception {
		Http http = Mockito.mock(Http.class);

		Mockito.when(
			http.URLtoString(Mockito.any(Http.Options.class))
		).thenThrow(
			exception
		);

		return http;
	}

	private Http _getHttp(Map<String, String> mockRequest) throws Exception {
		Http http = Mockito.mock(Http.class);

		Mockito.when(
			http.URLtoString(Mockito.any(Http.Options.class))
		).then(
			answer -> {
				Http.Options options = (Http.Options)answer.getArguments()[0];

				String location = options.getLocation();

				String endpoint = location.substring(
					location.lastIndexOf("/"), location.indexOf("?"));

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

}