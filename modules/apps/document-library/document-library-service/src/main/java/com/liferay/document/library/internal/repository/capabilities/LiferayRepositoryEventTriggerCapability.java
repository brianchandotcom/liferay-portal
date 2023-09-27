/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;

/**
 * @author Adolfo Pérez
 */
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