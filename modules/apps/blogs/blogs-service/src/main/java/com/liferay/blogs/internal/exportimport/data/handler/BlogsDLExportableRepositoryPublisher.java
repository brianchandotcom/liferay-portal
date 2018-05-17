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
@Component(immediate = true, service = DLExportableRepositoryPublisher.class)
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