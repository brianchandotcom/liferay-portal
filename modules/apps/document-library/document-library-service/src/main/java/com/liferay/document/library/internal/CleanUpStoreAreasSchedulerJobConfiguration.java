/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal;

import com.liferay.document.library.internal.configuration.StoreAreaConfiguration;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
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
@Component(
	configurationPid = "com.liferay.document.library.internal.configuration.StoreAreaConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CleanUpStoreAreasSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return this::_cleanUpStorageAreas;
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompanyId(
			this::_cleanUpStorageAreas);
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_storeAreaConfiguration = ConfigurableUtil.createConfigurable(
			StoreAreaConfiguration.class, properties);

		_triggerConfiguration = TriggerConfiguration.createTriggerConfiguration(
			_storeAreaConfiguration.cleanUpInterval(), TimeUnit.DAY);

		_startOffsets = new ConcurrentHashMap<>();
	}

	private void _cleanUpStorageAreas(long companyId) {
		StoreAreaProcessor storeAreaProcessor =
			_storeAreaProcessorSnapshot.get();

		if (storeAreaProcessor == null) {
			return;
		}

		_startOffsets.put(
			companyId,
			storeAreaProcessor.cleanUpDeletedStoreArea(
				companyId, _storeAreaConfiguration.evictionQuota(),
				name -> !_isDLFileVersionReferenced(companyId, name),
				_startOffsets.getOrDefault(companyId, StringPool.BLANK),
				Duration.ofDays(_storeAreaConfiguration.evictionAge())));

		_startOffsets.put(
			companyId,
			storeAreaProcessor.cleanUpNewStoreArea(
				companyId, _storeAreaConfiguration.evictionQuota(),
				name -> !_isDLFileVersionReferenced(companyId, name),
				_startOffsets.getOrDefault(companyId, StringPool.BLANK),
				Duration.ofDays(_storeAreaConfiguration.evictionAge())));
	}

	private boolean _isDLFileVersionReferenced(Long companyId, String name) {
		int pos = name.lastIndexOf(StringPool.TILDE);

		if (pos == -1) {
			return true;
		}

		int fileVersionsCount = _dlFileVersionLocalService.getFileVersionsCount(
			companyId, name.substring(pos + 1));

		if (fileVersionsCount > 0) {
			return true;
		}

		return false;
	}

	private static final Snapshot<StoreAreaProcessor>
		_storeAreaProcessorSnapshot = new Snapshot<>(
			CleanUpStoreAreasSchedulerJobConfiguration.class,
			StoreAreaProcessor.class, "(default=true)", true);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	private Map<Long, String> _startOffsets;
	private StoreAreaConfiguration _storeAreaConfiguration;
	private TriggerConfiguration _triggerConfiguration;

}