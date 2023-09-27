/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.exportimport.data.handler;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.RepositoryLocalService;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLExportableRepositoryPublisher.class)
public class BlogsDLExportableRepositoryPublisher
	implements DLExportableRepositoryPublisher {

	@Override
	public void publish(long groupId, Consumer<Long> repositoryIdConsumer) {
		Repository repository = _repositoryLocalService.fetchRepository(
			groupId, BlogsConstants.SERVICE_NAME);

		if (repository != null) {
			repositoryIdConsumer.accept(repository.getRepositoryId());
		}
	}

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}