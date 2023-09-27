/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.messaging.test;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.MockHttp;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class InterestTermsCheckerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		Bundle bundle = FrameworkUtil.getBundle(InterestTermsCheckerTest.class);

		_interestTermsChecker = _getService(
			bundle,
			"(component.name=com.liferay.segments.asah.connector.internal." +
				"messaging.InterestTermsChecker)");

		Assert.assertNotNull(_interestTermsChecker);

		_asahInterestTermProvider = _getService(
			bundle,
			"(component.name=com.liferay.segments.asah.connector.internal." +
				"provider.AsahInterestTermProvider)");

		Assert.assertNotNull(_asahInterestTermProvider);
	}

	@Test
	public void testCheckIndividualSegments() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId", "123456789"
						).put(
							"liferayAnalyticsEnableAllGroupIds", true
						).put(
							"liferayAnalyticsFaroBackendSecuritySignature",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsFaroBackendURL",
							"http://localhost:8080"
						).build());
			ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.segments.asah.connector.internal." +
						"configuration.SegmentsAsahConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"interestTermsCacheExpirationTime", "60"
					).build())) {

			DCLSingleton<?> asahFaroBackendClientDCLSingleton =
				ReflectionTestUtil.getFieldValue(
					_interestTermsChecker,
					"_asahFaroBackendClientDCLSingleton");

			asahFaroBackendClientDCLSingleton.destroy(null);

			ReflectionTestUtil.setFieldValue(
				_interestTermsChecker, "_http",
				new MockHttp(
					Collections.singletonMap(
						"/api/1.0/interests/terms/" + _user.getUserId(),
						() -> JSONUtil.put(
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

			ReflectionTestUtil.invoke(
				_interestTermsChecker, "checkInterestTerms",
				new Class<?>[] {long.class, String.class}, _user.getCompanyId(),
				String.valueOf(_user.getUserId()));

			String[] interestTerms = ReflectionTestUtil.invoke(
				_asahInterestTermProvider, "getInterestTerms",
				new Class<?>[] {long.class, String.class}, _user.getCompanyId(),
				String.valueOf(_user.getUserId()));

			Assert.assertArrayEquals(new String[] {"term1"}, interestTerms);
		}
	}

	private Object _getService(Bundle bundle, String filterString)
		throws Exception {

		ServiceTracker<Object, Object> serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), FrameworkUtil.createFilter(filterString),
			null);

		serviceTracker.open();

		Object service = serviceTracker.getService();

		serviceTracker.close();

		return service;
	}

	private Object _asahInterestTermProvider;
	private Object _interestTermsChecker;
	private User _user;

}