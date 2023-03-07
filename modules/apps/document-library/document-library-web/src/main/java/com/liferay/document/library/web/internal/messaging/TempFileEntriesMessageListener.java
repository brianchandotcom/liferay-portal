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

package com.liferay.document.library.web.internal.messaging;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.UndeployedExternalRepositoryException;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.RepositoryLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = SchedulerJobConfiguration.class
)
public class TempFileEntriesMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			ActionableDynamicQuery actionableDynamicQuery =
				_repositoryLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(Repository repository) -> _deleteExpiredTemporaryFileEntries(
					repository));

			actionableDynamicQuery.performActions();
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_dlConfiguration.temporaryFileEntriesCheckInterval(),
			TimeUnit.HOUR);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	private void _deleteExpiredTemporaryFileEntries(Repository repository) {
		LocalRepository localRepository = null;

		try {
			localRepository = _repositoryProvider.getLocalRepository(
				repository.getRepositoryId());
		}
		catch (PortalException | UndeployedExternalRepositoryException
					exception) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get implementation for repository " +
						repository.getRepositoryId(),
					exception);
			}

			return;
		}

		try {
			if (localRepository.isCapabilityProvided(
					TemporaryFileEntriesCapability.class)) {

				TemporaryFileEntriesCapability temporaryFileEntriesCapability =
					localRepository.getCapability(
						TemporaryFileEntriesCapability.class);

				temporaryFileEntriesCapability.
					deleteExpiredTemporaryFileEntries();
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete expired temporary file entries in " +
						"repository " + repository.getRepositoryId(),
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TempFileEntriesMessageListener.class);

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference
	private RepositoryProvider _repositoryProvider;

}