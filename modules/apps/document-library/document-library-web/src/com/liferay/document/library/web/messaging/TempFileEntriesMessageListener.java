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

package com.liferay.document.library.web.messaging;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	property = {"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN},
	service = SchedulerEntry.class
)
public class TempFileEntriesMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = Configurable.createConfigurable(
			DLConfiguration.class, properties);

		schedulerEntryImpl.setTrigger(
			TriggerFactoryUtil.createTrigger(
				getEventListenerClass(), getEventListenerClass(),
				_dlConfiguration.temporaryFileEntriesCheckInterval(),
				TimeUnit.HOUR));
	}

	protected void deleteExpiredTemporaryFileEntries(Repository repository) {
		LocalRepository localRepository = null;

		try {
			localRepository = RepositoryProviderUtil.getLocalRepository(
				repository.getRepositoryId());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get implementation for repository " +
						repository.getRepositoryId(),
					pe);
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
		catch (Exception pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete expired temporary file entries in " +
						"repository " + repository.getRepositoryId(),
					pe);
			}
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			RepositoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					Repository repository = (Repository)object;

					deleteExpiredTemporaryFileEntries(repository);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN + ")"
	)
	protected void setPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TempFileEntriesMessageListener.class);

	private volatile DLConfiguration _dlConfiguration;

}