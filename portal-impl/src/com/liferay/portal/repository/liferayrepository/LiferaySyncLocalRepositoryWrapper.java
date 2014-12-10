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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;

/**
 * @author Adolfo Pérez
 */
public class LiferaySyncLocalRepositoryWrapper extends LocalRepositoryWrapper {

	public LiferaySyncLocalRepositoryWrapper(
		LocalRepository localRepository, SyncCapability syncCapability) {

		super(localRepository);

		_syncCapability = syncCapability;
	}

	@Override
	public void deleteAll() throws PortalException {
		_syncCapability.destroyDocumentRepository();

		super.deleteAll();
	}

	private final SyncCapability _syncCapability;

}