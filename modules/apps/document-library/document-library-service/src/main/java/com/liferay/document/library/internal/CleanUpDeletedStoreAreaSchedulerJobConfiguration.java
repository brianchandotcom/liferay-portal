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

package com.liferay.document.library.internal;

import com.liferay.document.library.internal.configuration.StoreAreaConfiguration;
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.time.Duration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.internal.configuration.StoreAreaConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CleanUpDeletedStoreAreaSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return this::_cleanUpDeletedStoreArea;
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompanyId(
			this::_cleanUpDeletedStoreArea);
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_storeAreaConfiguration.cleanUpInterval(), TimeUnit.DAY);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_startOffsets = new ConcurrentHashMap<>();

		_storeAreaConfiguration = ConfigurableUtil.createConfigurable(
			StoreAreaConfiguration.class, properties);
	}

	private void _cleanUpDeletedStoreArea(Long companyId) {
		_startOffsets.put(
			companyId,
			_storeAreaProcessor.cleanUpDeletedStoreArea(
				companyId, _storeAreaConfiguration.deletionQuota(),
				Duration.ofDays(_storeAreaConfiguration.evictionAge()),
				_startOffsets.getOrDefault(companyId, StringPool.BLANK)));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private Map<Long, String> _startOffsets;
	private StoreAreaConfiguration _storeAreaConfiguration;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile StoreAreaProcessor _storeAreaProcessor;

}