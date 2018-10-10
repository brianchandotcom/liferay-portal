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

package com.liferay.document.library.internal.repository;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.repository.util.RepositoryWrapper;

/**
 * @author Alejandro Tardín
 */
public class LiferayDynamicRepositoryWrapper extends RepositoryWrapper {

	public LiferayDynamicRepositoryWrapper(
		Repository repository,
		ServiceTrackerList<Capability, Capability> dynamicCapabilities) {

		super(repository);

		_dynamicCapabilities = dynamicCapabilities;
	}

	private final ServiceTrackerList<Capability, Capability>
		_dynamicCapabilities;

}