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

package com.liferay.portal.security.service.access.quota.impl;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.security.service.access.quota.SAQContext;
import com.liferay.portal.security.service.access.quota.SAQMetricConfig;
import com.liferay.portal.security.service.access.quota.SAQMetricProvider;
import com.liferay.portal.security.service.access.quota.ServiceAccessQuota;
import com.liferay.portal.security.service.access.quota.metric.SAQContextMatcher;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpression;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionConsumer;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionProvider;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 * @author Carlos Sierra Andrés
 */
public class SAQContextImpl implements SAQContextMatcher, SAQContext {

	public static SAQContextImpl buildContext(
		List<ServiceAccessQuota> serviceAccessQuotas,
		Map<String, SAQMetricProvider> saqMetricProviders,
		AccessControlContext accessControlContext, Method method) {

		List<ServiceAccessQuota> relevantServiceAccessQuotas =
			new LinkedList<>();

		Map<String, String> saqMetrics = new HashMap<>(
			saqMetricProviders.size());

		Set<String> requiredSaqMetrics = new HashSet<>();

		for (ServiceAccessQuota serviceAccessQuota : serviceAccessQuotas) {
			boolean metricPatternsMatched = true;

			for (SAQMetricConfig saqMetricConfig :
					serviceAccessQuota.getMetricConfigs()) {

				SAQMetricProvider saqMetricProvider = saqMetricProviders.get(
					saqMetricConfig.getMetricName());

				String metricValue;

				if (saqMetrics.containsKey(saqMetricConfig.getMetricName())) {
					metricValue = saqMetrics.get(
						saqMetricConfig.getMetricName());
				}
				else {
					metricValue = saqMetricProvider.getMetricValue(
						accessControlContext, method);

					saqMetrics.put(
						saqMetricConfig.getMetricName(), metricValue);
				}

				if (saqMetricConfig.getPattern() != null) {
					if (!saqMetricProvider.matches(
							metricValue, saqMetricConfig.getPattern())) {

						metricPatternsMatched = false;
						break;
					}
				}
			}

			if (metricPatternsMatched) {
				relevantServiceAccessQuotas.add(serviceAccessQuota);

				for (SAQMetricConfig saqMetricConfig :
						serviceAccessQuota.getMetricConfigs()) {

					requiredSaqMetrics.add(saqMetricConfig.getMetricName());
				}
			}
		}

		// Remove metrics that are not relevant
		// because of failed pattern matching

		Set<String> keySet = saqMetrics.keySet();

		keySet.retainAll(requiredSaqMetrics);

		long nowMillis = System.currentTimeMillis();

		return new SAQContextImpl(
			saqMetrics, relevantServiceAccessQuotas, saqMetricProviders,
			nowMillis);
	}

	@Override
	public Set<String> getMetricNames() {
		return _saqMetrics.keySet();
	}

	@Override
	public Map<String, String> getMetricsMap() {
		return _saqMetrics;
	}

	@Override
	public List<ServiceAccessQuota> getQuotas() {
		return _relevantServiceAccessQuotas;
	}

	public Set<ServiceAccessQuota> matches(SAQImpression saqImpression) {
		_matchedServiceAccessQuotas.clear();

		for (ServiceAccessQuota serviceAccessQuota :
				_relevantServiceAccessQuotas) {

			if ((
					saqImpression.getCreatedMillis() +
						serviceAccessQuota.getIntervalMillis()) < _nowMillis) {

				continue;
			}

			Map<String, String> saqImpressionMetrics =
				saqImpression.getMetrics();

			boolean allMetricsMatch = true;

			for (SAQMetricConfig saqMetricConfig :
					serviceAccessQuota.getMetricConfigs()) {

				String saqImpressionMetricValue = saqImpressionMetrics.get(
					saqMetricConfig.getMetricName());

				if (saqImpressionMetricValue == null) {
					allMetricsMatch = false;

					break;
				}
				else {
					if (saqMetricConfig.getPattern() != null) {
						if (!_saqMetricProviders.get(
								saqMetricConfig.getMetricName()).matches(
									saqImpressionMetricValue,
									saqMetricConfig.getPattern())) {

							allMetricsMatch = false;

							break;
						}
					}
					else {
						if (!_saqMetrics.get(
								saqMetricConfig.getMetricName()).equals(
									saqImpressionMetricValue)) {

							allMetricsMatch = false;
							break;
						}
					}
				}
			}

			if (allMetricsMatch) {
				_matchedServiceAccessQuotas.add(serviceAccessQuota);
			}
		}

		return _matchedServiceAccessQuotas;
	}

	@Override
	public boolean matches(String metricName, String metricValue) {
		if (metricValue == null) {
			return false;
		}

		SAQMetricProvider saqMetricProvider = _saqMetricProviders.get(
			metricName);
		List<SAQMetricConfig> saqMetricConfigs = _saqMetricConfigs.get(
			metricName);

		for (SAQMetricConfig saqMetricConfig : saqMetricConfigs) {
			if (saqMetricConfig.getPattern() != null) {
				if (saqMetricProvider.matches(
						metricValue, saqMetricConfig.getPattern())) {

					return true;
				}
			}
			else {
				if (_saqMetrics.get(metricName).equals(metricValue)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public SAQContext.ProcessingResult process(
		long companyId, SAQImpressionProvider saqImpressionProvider) {

		final ProcessingResultImpl processingResult =
			new ProcessingResultImpl();

		// Fail fast when quotas are configured with no metrics

		if (_relevantServiceAccessQuotasWithoutMetrics.size() > 0) {
			for (ServiceAccessQuota serviceAccessQuota :
					_relevantServiceAccessQuotasWithoutMetrics) {

				int saqImpressionsCount =
					saqImpressionProvider.getSAQImpressionsCount(
						companyId, serviceAccessQuota.getIntervalMillis());

				if (saqImpressionsCount >= serviceAccessQuota.getMax()) {
					processingResult.setStatus(
						ProcessingResult.Status.BREACHED_QUOTA);
					processingResult.setBreachedQuota(serviceAccessQuota);

					return processingResult;
				}
			}
		}

		final Set<SAQImpression> processedSaqImpressions = new HashSet<>();

		saqImpressionProvider.populateSAQImpressions(
			companyId, this,
			new SAQImpressionConsumer() {

				@Override
				public SAQImpressionConsumer.Status consume(
					SAQImpression saqImpression) {

					if (processedSaqImpressions.contains(saqImpression)) {
						return SAQImpressionConsumer.Status.HUNGRY;
					}

					processedSaqImpressions.add(saqImpression);

					Set<ServiceAccessQuota> matchedServiceAccessQuotas =
						matches(saqImpression);

					for (ServiceAccessQuota serviceAccessQuota :
							matchedServiceAccessQuotas) {

						int count = _serviceAccessQuotasCount.get(
							serviceAccessQuota);

						count = count + saqImpression.getWeight();

						if (count < serviceAccessQuota.getMax()) {
							_serviceAccessQuotasCount.put(
								serviceAccessQuota, count);

							continue;
						}

						// If through impression matching a quota max is hit,
						// then adding the impression for the current request
						// later will breach it, so fail fast now

						processingResult.setBreachedQuota(serviceAccessQuota);
						processingResult.setStatus(
							ProcessingResult.Status.BREACHED_QUOTA);

						return SAQImpressionConsumer.Status.SATISFIED;
					}

					return SAQImpressionConsumer.Status.HUNGRY;
				}

			});

		return processingResult;
	}

	private SAQContextImpl(
		Map<String, String> metricsMap,
		List<ServiceAccessQuota> relevantServiceAccessQuotas,
		Map<String, SAQMetricProvider> saqMetricProviders, long nowMillis) {

		_saqMetrics = metricsMap;
		_relevantServiceAccessQuotas = relevantServiceAccessQuotas;
		_saqMetricProviders = saqMetricProviders;
		_nowMillis = nowMillis;

		_serviceAccessQuotasCount = new HashMap<>(
			relevantServiceAccessQuotas.size());

		for (Iterator<ServiceAccessQuota> it =
			relevantServiceAccessQuotas.iterator();
			it.hasNext();) {

			_serviceAccessQuotasCount.put(it.next(), 0);
		}

		_saqMetricConfigs = new HashMap<>(metricsMap.size());

		_relevantServiceAccessQuotasWithoutMetrics = new LinkedList<>();

		for (ServiceAccessQuota serviceAccessQuota :
				relevantServiceAccessQuotas) {

			List<SAQMetricConfig> quotaSaqMetricConfigs =
				serviceAccessQuota.getMetricConfigs();

			if (quotaSaqMetricConfigs.isEmpty()) {
				_relevantServiceAccessQuotasWithoutMetrics.add(
					serviceAccessQuota);
			}
			else {
				for (SAQMetricConfig saqMetricConfig : quotaSaqMetricConfigs) {
					List<SAQMetricConfig> saqMetricConfigs =
						_saqMetricConfigs.get(saqMetricConfig.getMetricName());

					if (saqMetricConfigs == null) {
						saqMetricConfigs = new LinkedList<>();

						_saqMetricConfigs.put(
							saqMetricConfig.getMetricName(), saqMetricConfigs);
					}

					if (!saqMetricConfigs.contains(saqMetricConfig)) {
						saqMetricConfigs.add(saqMetricConfig);
					}
				}
			}
		}

		_matchedServiceAccessQuotas = new HashSet<>(
			relevantServiceAccessQuotas.size());

		// Sort relevantQuotasWithoutMetrics by their max property
		// Will then fail fast because quotas with lower max are
		// checked and reported first

		Collections.sort(
			_relevantServiceAccessQuotasWithoutMetrics,
			new Comparator<ServiceAccessQuota>() {

				@Override
				public int compare(
					ServiceAccessQuota o1, ServiceAccessQuota o2) {

					return o1.getMax() - o2.getMax();
				};

			});
	}

	private final Set<ServiceAccessQuota> _matchedServiceAccessQuotas;
	private final long _nowMillis;
	private final List<ServiceAccessQuota> _relevantServiceAccessQuotas;
	private final List<ServiceAccessQuota>
		_relevantServiceAccessQuotasWithoutMetrics;
	private final Map<String, List<SAQMetricConfig>> _saqMetricConfigs;
	private final Map<String, SAQMetricProvider> _saqMetricProviders;
	private final Map<String, String> _saqMetrics;
	private final HashMap<ServiceAccessQuota, Integer>
		_serviceAccessQuotasCount;

	private class ProcessingResultImpl implements ProcessingResult {

		public ProcessingResultImpl() {
			_status = ProcessingResult.Status.NO_BREACHED_QUOTA;
			_breachedQuota = null;
		}

		@Override
		public ServiceAccessQuota getBreachedQuota() {
			return _breachedQuota;
		}

		@Override
		public Status getStatus() {
			return _status;
		}

		public void setBreachedQuota(ServiceAccessQuota breachedQuota) {
			_breachedQuota = breachedQuota;
		}

		public void setStatus(ProcessingResult.Status status) {
			_status = status;
		}

		private ServiceAccessQuota _breachedQuota;
		private ProcessingResult.Status _status;

	}

}