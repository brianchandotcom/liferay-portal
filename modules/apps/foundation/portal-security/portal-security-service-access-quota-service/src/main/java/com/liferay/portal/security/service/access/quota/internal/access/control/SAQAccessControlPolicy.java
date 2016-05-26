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

package com.liferay.portal.security.service.access.quota.internal.access.control;

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
import com.liferay.portal.security.service.access.quota.SAQMetricProvider;
import com.liferay.portal.security.service.access.quota.internal.QuotaBreachException;
import com.liferay.portal.security.service.access.quota.internal.SAQContext;
import com.liferay.portal.security.service.access.quota.internal.SAQContext.ProcessingResult;
import com.liferay.portal.security.service.access.quota.internal.SAQMetricConfig;
import com.liferay.portal.security.service.access.quota.internal.ServiceAccessQuota;
import com.liferay.portal.security.service.access.quota.internal.impl.SAQContextImpl;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionProvider;

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
 * @author Carlos Sierra Andrés
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

		SAQContext saqContext = SAQContextImpl.buildContext(
			_serviceAccessQuotas, _saqMetricProviders, accessControlContext,
			method);

		if (saqContext.getQuotas().size() == 0) {
			return;
		}

		SAQContext.ProcessingResult processingResult = saqContext.process(
			companyId, _saqImpressionProvider);

		if (processingResult.getStatus().equals(
				SAQContext.ProcessingResult.Status.BREACHED_QUOTA)) {

			String quotaBreachedMsg = _getQuotaBreachedMsg(
				processingResult.getBreachedQuota());

			if (_log.isDebugEnabled()) {
				_log.debug(quotaBreachedMsg);
			}

			throw new QuotaBreachException(quotaBreachedMsg);
		}

		long largestQuotaIntervalMillis = 0;

		for (ServiceAccessQuota serviceAccessQuota : saqContext.getQuotas()) {
			largestQuotaIntervalMillis = Math.max(
				serviceAccessQuota.getIntervalMillis(),
				largestQuotaIntervalMillis);
		}

		_saqImpressionProvider.createSAQImpression(
			companyId, saqContext.getMetricsMap(), largestQuotaIntervalMillis);
	}

	@Reference (unbind = "-")
	public void setImpressionProvider(
		SAQImpressionProvider saqImpressionProvider) {

		_saqImpressionProvider = saqImpressionProvider;
	}

	@Reference (
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetMetricProvider"
	)
	public void setMetricProvider(SAQMetricProvider saqMetricProvider) {
		_saqMetricProviders.put(
			StringUtil.toLowerCase(saqMetricProvider.getMetricName()),
			saqMetricProvider);
	}

	@Reference (
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, unbind = "unsetQuota"
	)
	public void setQuota(ServiceAccessQuota serviceAccessQuota) {
		_serviceAccessQuotas.add(serviceAccessQuota);
	}

	public void unsetMetricProvider(SAQMetricProvider saqMetricProvider) {
		_saqMetricProviders.remove(saqMetricProvider.getMetricName());
	}

	public void unsetQuota(ServiceAccessQuota serviceAccessQuota) {
		_serviceAccessQuotas.remove(serviceAccessQuota);
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

	private String _getQuotaBreachedMsg(ServiceAccessQuota serviceAccessQuota) {
		List<SAQMetricConfig> saqMetricConfigs =
			serviceAccessQuota.getMetricConfigs();

		StringBundler sb = new StringBundler(saqMetricConfigs.size() + 4);

		sb.append("Breached quota ");

		sb.append(serviceAccessQuota.getMax());

		sb.append('/');

		sb.append(serviceAccessQuota.getIntervalMillis());

		for (SAQMetricConfig saqMetricConfig : saqMetricConfigs) {
			if (Validator.isNotNull(saqMetricConfig)) {
				sb.append('/').append(saqMetricConfig);
			}
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAQAccessControlPolicy.class);

	private SAQImpressionProvider _saqImpressionProvider;
	private final Map<String, SAQMetricProvider> _saqMetricProviders =
		new HashMap<>();
	private final List<ServiceAccessQuota> _serviceAccessQuotas =
		new LinkedList<>();

}