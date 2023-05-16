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

import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
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

/**
 * @author Adolfo Pérez
 */
@Component(service = SchedulerJobConfiguration.class)
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
		return TriggerConfiguration.createTriggerConfiguration(1, TimeUnit.DAY);
	}

	@Activate
	protected void activate() {
		_startOffsets = new ConcurrentHashMap<>();
	}

	private void _cleanUpDeletedStoreArea(Long companyId) {
		_startOffsets.put(
			companyId,
			_storeAreaProcessor.cleanUpDeletedStoreArea(
				companyId, Duration.ofDays(31),
				_startOffsets.getOrDefault(companyId, StringPool.BLANK)));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private Map<Long, String> _startOffsets;

	@Reference(target = "(default=true)")
	private StoreAreaProcessor _storeAreaProcessor;

}