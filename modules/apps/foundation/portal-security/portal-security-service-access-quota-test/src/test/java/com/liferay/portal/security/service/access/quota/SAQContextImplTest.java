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

package com.liferay.portal.security.service.access.quota;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.service.access.quota.SAQContext.ProcessingResult;
import com.liferay.portal.security.service.access.quota.impl.SAQContextImpl;
import com.liferay.portal.security.service.access.quota.impl.TestIndexedSAQImpressionPersistenceImpl;
import com.liferay.portal.security.service.access.quota.impl.TestServiceAccessQuotaImpl;
import com.liferay.portal.security.service.access.quota.impl.TestServiceAccessQuotaMetricConfigImpl;
import com.liferay.portal.security.service.access.quota.metric.impl.ServiceSAQMetricProvider;
import com.liferay.portal.security.service.access.quota.metric.impl.UserIdSAQMetricProvider;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionPersistence;
import com.liferay.portal.security.service.access.quota.sim.TestService;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Stian Sigvartsen
 */
@PrepareForTest({AccessControlContext.class})
@RunWith(PowerMockRunner.class)
public class SAQContextImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_companyId = 0;

		_registerMetricProvider(new ServiceSAQMetricProvider());
		_registerMetricProvider(new UserIdSAQMetricProvider());

		when(
			_accessControlContext.getAuthVerifierResult().getUserId()
		).thenReturn(
			123l
		);
	}

	@Test
	public void testContextHasMatchedQuotasOnly()
		throws NoSuchMethodException, QuotaBreachException {

		List<ServiceAccessQuota> quotas =
			ListUtil.fromArray(new ServiceAccessQuota[] {
				_QUOTA_3_60000_NO_METRICS, _QUOTA_2_60000_GET,
				_QUOTA_1_60000_GETMETHOD2_USER123
			});

		Method testMethod;
		testMethod = TestService.class.getMethod("getMethod1");

		SAQContext context = SAQContextImpl.buildContext(
			quotas, _metricProviders, _accessControlContext, testMethod);

		// Test for quota with no metrics + the *#get* one

		Assert.assertTrue(context.getQuotas().size() == 2);
	}

	@Test
	public void testContextHasMetricsFromMatchedQuotasOnly()
		throws NoSuchMethodException, QuotaBreachException {

		List<ServiceAccessQuota> quotas =
			ListUtil.fromArray(new ServiceAccessQuota[] {
				_QUOTA_3_60000_NO_METRICS, _QUOTA_2_60000_GET,
				_QUOTA_1_60000_GETMETHOD2_USER123
			});

		Method testMethod;
		testMethod = TestService.class.getMethod("getMethod1");

		SAQContext context = SAQContextImpl.buildContext(
			quotas, _metricProviders, _accessControlContext, testMethod);

		Assert.assertEquals(1, context.getMetrics().size());
		Assert.assertFalse(context.getMetrics().contains("user"));
	}

	@Test
	public void testImpressionsWithNoMetricsCounted()
		throws NoSuchMethodException, QuotaBreachException {

		List<ServiceAccessQuota> quotas =
			ListUtil.fromArray(new ServiceAccessQuota[] {
				_QUOTA_3_60000_NO_METRICS, _QUOTA_2_60000_GET
			});

		SAQImpressionPersistence iR =
			new TestIndexedSAQImpressionPersistenceImpl();

		// A couple of impressions left after calls to methods not matching
		// any quota except _QUOTA_3_60000_NO_METRICS

		iR.createImpression(_companyId, _metricsNone, _TEST_EXPIRY_MILLIS);
		iR.createImpression(_companyId, _metricsNone, _TEST_EXPIRY_MILLIS);

		Method testMethod;

		// Matches quota _QUOTA_2_60000_GET

		testMethod = TestService.class.getMethod("getMethod1");
		iR.createImpression(
			_companyId, _metricsGetMethod1User1, _TEST_EXPIRY_MILLIS);

		SAQContext context = SAQContextImpl.buildContext(
			quotas, _metricProviders, _accessControlContext, testMethod);

		Assert.assertEquals(2, context.getQuotas().size());

		Assert.assertEquals(
			ProcessingResult.Status.BREACHED_QUOTA,
			context.process(_companyId, iR).getStatus());
	}

	@Test
	public void testQuotaWithNoMetricsMatchesAllImpressions()
		throws NoSuchMethodException, QuotaBreachException {

		List<ServiceAccessQuota> quotas =
			ListUtil.fromArray(new ServiceAccessQuota[] {
				_QUOTA_3_60000_NO_METRICS
			});

		SAQImpressionPersistence iR =
			new TestIndexedSAQImpressionPersistenceImpl();

		Method testMethod;
		testMethod = TestService.class.getMethod("getMethod1");
		iR.createImpression(
			_companyId, _metricsGetMethod1User1, _TEST_EXPIRY_MILLIS);

		testMethod = TestService.class.getMethod("getMethod2");
		iR.createImpression(
			_companyId, _metricsGetMethod2User1, _TEST_EXPIRY_MILLIS);

		testMethod = TestService.class.getMethod("setMethod1");
		iR.createImpression(
			_companyId, _metricsSetMethod1User1, _TEST_EXPIRY_MILLIS);

		SAQContext context = SAQContextImpl.buildContext(
			quotas, _metricProviders, _accessControlContext, testMethod);

		Assert.assertEquals(
			ProcessingResult.Status.BREACHED_QUOTA,
			context.process(_companyId, iR).getStatus());
	}

	@Test
	public void testWildcardMethodMatching()
		throws NoSuchMethodException, QuotaBreachException {

		List<ServiceAccessQuota> quotas = ListUtil.fromArray(
			new ServiceAccessQuota[] {_QUOTA_2_60000_GET});

		SAQImpressionPersistence iR =
			new TestIndexedSAQImpressionPersistenceImpl();

		Method testMethod;
		testMethod = TestService.class.getMethod("getMethod1");
		iR.createImpression(
			_companyId, _metricsGetMethod1User1, _TEST_EXPIRY_MILLIS);

		testMethod = TestService.class.getMethod("getMethod2");
		iR.createImpression(
			_companyId, _metricsGetMethod2User1, _TEST_EXPIRY_MILLIS);

		SAQContext context = SAQContextImpl.buildContext(
			quotas, _metricProviders, _accessControlContext, testMethod);

		Assert.assertEquals(
			ProcessingResult.Status.BREACHED_QUOTA,
			context.process(_companyId, iR).getStatus());
	}

	private static Map<String, String> _stringArrayToMap(String[][] props) {
		Map<String, String> retMap = new HashMap<>();

		for (int i = 0; i < props.length; i++) {
			retMap.put(props[i][0], props[i][1]);
		}

		return retMap;
	}

	// ----

	private void _registerMetricProvider(SAQMetricProvider metricProvider) {
		_metricProviders.put(metricProvider.getMetricName(), metricProvider);
	}

	private static final ServiceAccessQuota _QUOTA_1_60000_GETMETHOD2_USER123 =
		new TestServiceAccessQuotaImpl(
			60000, 1,
			ListUtil.fromArray(
				new ServiceAccessQuotaMetricConfig[] {
					new TestServiceAccessQuotaMetricConfigImpl(
						"service", "*#getMethod2"),
					new TestServiceAccessQuotaMetricConfigImpl("user", "123")
				}));

	private static final ServiceAccessQuota _QUOTA_2_60000_GET =
		new TestServiceAccessQuotaImpl(
			60000, 2, ListUtil.fromArray(
				new ServiceAccessQuotaMetricConfig[] {
					new TestServiceAccessQuotaMetricConfigImpl(
						"service", "*#get*")
				}));

	private static final ServiceAccessQuota _QUOTA_3_60000_NO_METRICS =
		new TestServiceAccessQuotaImpl(
			60000, 3, ListUtil.fromArray(
				new ServiceAccessQuotaMetricConfig[0]));

	private static final long _TEST_EXPIRY_MILLIS = 10000;

	private static final Map<String, String> _metricsGetMethod1User1 =
		_stringArrayToMap(
			new String[][] {
				{"service", TestService.class.getName() + "#getMethod1"},
				{"user", "123"}
			});
	private static final Map<String, String> _metricsGetMethod2User1 =
		_stringArrayToMap(
			new String[][] {
				{"service", TestService.class.getName() + "#getMethod2"},
				{"user", "123"}
			});
	private static final Map<String, String> _metricsNone = _stringArrayToMap(
		new String[0][0]);
	private static final Map<String, String> _metricsSetMethod1User1 =
		_stringArrayToMap(
			new String[][] {
				{"service", TestService.class.getName() + "#setMethod1"},
				{"user", "123"}
			});

	@InjectMocks
	private final AccessControlContext _accessControlContext =
		new AccessControlContext();

	@Mock
	private AuthVerifierResult _authVerifierResult;

	private long _companyId;
	private final Map<String, SAQMetricProvider> _metricProviders =
		new HashMap<>();

}