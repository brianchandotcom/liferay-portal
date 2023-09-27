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
		return TriggerConfiguration.createTriggerConfiguration(
			_storeAreaConfiguration.cleanUpInterval(), TimeUnit.DAY);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_startOffsets = new ConcurrentHashMap<>();

		_storeAreaConfiguration = ConfigurableUtil.createConfigurable(
			StoreAreaConfiguration.class, properties);
	}

	private void _cleanUpDeletedStoreArea(long companyId) {
		_startOffsets.put(
			companyId,
			_storeAreaProcessor.cleanUpDeletedStoreArea(
				companyId, _storeAreaConfiguration.evictionQuota(),
				name -> !_isDLFileVersionReferenced(companyId, name),
				_startOffsets.getOrDefault(companyId, StringPool.BLANK),
				Duration.ofDays(_storeAreaConfiguration.evictionAge())));
	}

	private void _cleanUpNewStoreArea(long companyId) {
		_startOffsets.put(
			companyId,
			_storeAreaProcessor.cleanUpNewStoreArea(
				companyId, _storeAreaConfiguration.evictionQuota(),
				name -> !_isDLFileVersionReferenced(companyId, name),
				_startOffsets.getOrDefault(companyId, StringPool.BLANK),
				Duration.ofDays(_storeAreaConfiguration.evictionAge())));
	}

	private void _cleanUpStorageAreas(long companyId) {
		_cleanUpDeletedStoreArea(companyId);
		_cleanUpNewStoreArea(companyId);
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

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	private Map<Long, String> _startOffsets;
	private StoreAreaConfiguration _storeAreaConfiguration;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile StoreAreaProcessor _storeAreaProcessor;

}