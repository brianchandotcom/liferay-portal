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

package com.liferay.portlet.documentlibrary.messaging;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;

/**
 * @author Ivan Zaera
 */
public class TempFileEntriesMessageListener extends BaseMessageListener {

	protected void deleteExpiredTemporaryFileEntries(Repository repository) {
		LocalRepository localRepository = null;

		try {
			localRepository = RepositoryLocalServiceUtil.getLocalRepositoryImpl(
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

	private static final Log _log = LogFactoryUtil.getLog(
		TempFileEntriesMessageListener.class);

}