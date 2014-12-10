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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.repository.util.RepositoryWrapper;

/**
 * @author Adolfo Pérez
 */
public class LiferaySyncRepositoryWrapper extends RepositoryWrapper {

	public LiferaySyncRepositoryWrapper(
		Repository repository, SyncCapability syncCapability) {

		super(repository);

		_syncCapability = syncCapability;
	}

	@Override
	public void deleteAll() throws PortalException {
		_syncCapability.destroyDocumentRepository();

		super.deleteAll();
	}

	private final SyncCapability _syncCapability;

}