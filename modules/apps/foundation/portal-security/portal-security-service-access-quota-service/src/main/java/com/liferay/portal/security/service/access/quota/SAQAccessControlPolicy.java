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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.access.control.BaseAccessControlPolicy;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.quota.impl.SAQContextImpl;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionPersistence;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Stian Sigvartsen
 */
@Component(service = AccessControlPolicy.class)
public class SAQAccessControlPolicy extends BaseAccessControlPolicy {

	@Override
	public void onServiceRemoteAccess(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException {

		long companyId = CompanyThreadLocal.getCompanyId();

		if (isChecked()) {
			return;
		}

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		SAQContext context = SAQContextImpl.buildContext(
			_quotas, _metricProviders, accessControlContext, method);

		if (context.getQuotas().size() == 0) {
			return;
		}

		SAQContext.ProcessingResult result = context.process(
			companyId, _impressionPersistence);

		if (result.getStatus().equals(
				SAQContext.ProcessingResult.Status.BREACHED_QUOTA)) {

			String quotaBreachedMsg = _getQuotaBreachedMsg(
				result.getBreachedQuota());

			if (_log.isDebugEnabled()) {
				_log.debug(quotaBreachedMsg);
			}

			throw new QuotaBreachException(quotaBreachedMsg);
		}

		long largestQuotaIntervalMillis = 0;

		for (ServiceAccessQuota quota : context.getQuotas()) {
			largestQuotaIntervalMillis = Math.max(
				quota.getIntervalMillis(), largestQuotaIntervalMillis);
		}

		_impressionPersistence.createImpression(
			companyId, context.getMetricsMap(), largestQuotaIntervalMillis);
	}

	@Reference (unbind = "-")
	public void setImpressionPersistence(
		SAQImpressionPersistence impressionPersistence) {

		_impressionPersistence = impressionPersistence;
	}

	@Reference (
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind ="unsetMetricProvider"
	)
	public void setMetricProvider(SAQMetricProvider metricProvider) {
		_metricProviders.put(
			StringUtil.toLowerCase(metricProvider.getMetricName()),
			metricProvider);
	}

	@Reference (
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, unbind ="unsetQuota"
	)
	public void setQuota(ServiceAccessQuota quota) {
		_quotas.add(quota);
	}

	public void unsetMetricProvider(SAQMetricProvider metricProvider) {
		_metricProviders.remove(metricProvider.getMetricName());
	}

	public void unsetQuota(ServiceAccessQuota quota) {
		_quotas.remove(quota);
	}

	protected boolean isChecked() {
		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext != null) {
			Map<String, Object> settings = accessControlContext.getSettings();

			int serviceDepth = (Integer)settings.get(
				AccessControlContext.Settings.SERVICE_DEPTH.toString());

			if (serviceDepth > 1) {
				return true;
			}
		}

		return false;
	}

	private String _getQuotaBreachedMsg(ServiceAccessQuota quota) {
		List<ServiceAccessQuotaMetricConfig> metrics = quota.getMetrics();

		StringBundler sb = new StringBundler(metrics.size() + 4);

		sb.append("Breached quota ");

		sb.append(quota.getMax());

		sb.append('/');

		sb.append(quota.getIntervalMillis());

		for (ServiceAccessQuotaMetricConfig quotaMetric : metrics) {
			if (Validator.isNotNull(quotaMetric)) {
				sb.append('/').append(quotaMetric);
			}
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAQAccessControlPolicy.class);

	private SAQImpressionPersistence _impressionPersistence;
	private final Map<String, SAQMetricProvider> _metricProviders =
		new HashMap<>();
	private final List<ServiceAccessQuota> _quotas = new LinkedList<>();

}