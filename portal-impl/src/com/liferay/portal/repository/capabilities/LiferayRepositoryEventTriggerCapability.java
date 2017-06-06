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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;

/**
 * @author Adolfo Pérez
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.document.library.internal.capabilities.LiferayRepositoryEventTriggerCapability}
 */
@Deprecated
public class LiferayRepositoryEventTriggerCapability
	implements RepositoryEventTriggerCapability {

	public LiferayRepositoryEventTriggerCapability(
		RepositoryEventTrigger repositoryEventTrigger) {

		_repositoryEventTrigger = repositoryEventTrigger;
	}

	@Override
	public <S extends RepositoryEventType, T> void trigger(
			Class<S> eventTypeClass, Class<T> modelClass, T model)
		throws PortalException {

		_repositoryEventTrigger.trigger(eventTypeClass, modelClass, model);
	}

	private final RepositoryEventTrigger _repositoryEventTrigger;

}