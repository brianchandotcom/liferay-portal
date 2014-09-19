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

package com.liferay.portlet.documentlibraryadmin.messaging;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;

import java.util.List;

/**
 * @author Ivan Zaera
 */
public class TemporaryFilesMessageListener implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		List<Repository> repositories =
			RepositoryLocalServiceUtil.getRepositories(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Repository repository : repositories) {
			try {
				deleteExpiredTemporaryFileEntries(repository);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to try to cleanup temporary files in " +
							"repository " + repository.getRepositoryId(),
						e);
				}
			}
		}
	}

	protected void deleteExpiredTemporaryFileEntries(Repository repository)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryLocalServiceUtil.getLocalRepositoryImpl(
				repository.getRepositoryId());

		if (localRepository.isCapabilityProvided(
				TemporaryFileEntriesCapability.class)) {

			TemporaryFileEntriesCapability temporaryFileEntriesCapability =
				localRepository.getCapability(
					TemporaryFileEntriesCapability.class);

			temporaryFileEntriesCapability.deleteExpiredTemporaryFileEntries();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TemporaryFilesMessageListener.class);

}