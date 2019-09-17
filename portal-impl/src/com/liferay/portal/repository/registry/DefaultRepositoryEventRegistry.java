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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Adolfo Pérez
 */
public class DefaultRepositoryEventRegistry
	implements RepositoryEventRegistry, RepositoryEventTrigger {

	public DefaultRepositoryEventRegistry(
		RepositoryEventTrigger parentRepositoryEventTrigger) {

		_parentRepositoryEventTrigger = parentRepositoryEventTrigger;
	}

	@Override
	public <S extends RepositoryEventType, T> void
		registerRepositoryEventListener(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

		if (repositoryEventListener == null) {
			throw new NullPointerException("Repository event listener is null");
		}

		Tuple tuple = new Tuple(repositoryEventTypeClass, modelClass);

		Collection<RepositoryEventListener<?, ?>> repositoryEventListeners =
			_repositoryEventListeners.computeIfAbsent(
				tuple, keyTuple -> new CopyOnWriteArrayList<>());

		repositoryEventListeners.add(repositoryEventListener);
	}

	@Override
	public <S extends RepositoryEventType, T> void trigger(
			Class<S> repositoryEventTypeClass, Class<T> modelClass, T model)
		throws PortalException {

		if (_parentRepositoryEventTrigger != null) {
			_parentRepositoryEventTrigger.trigger(
				repositoryEventTypeClass, modelClass, model);
		}

		Tuple tuple = new Tuple(repositoryEventTypeClass, modelClass);

		@SuppressWarnings("rawtypes")
		Collection<RepositoryEventListener<S, T>> repositoryEventListeners =
			(Collection)_repositoryEventListeners.get(tuple);

		if (repositoryEventListeners != null) {
			for (RepositoryEventListener<S, T> repositoryEventListener :
					repositoryEventListeners) {

				repositoryEventListener.execute(model);
			}
		}
	}

	@Override
	public <S extends RepositoryEventType, T> void
		unregisterRepositoryEventListener(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

		if (repositoryEventListener == null) {
			throw new NullPointerException("Repository event listener is null");
		}

		Tuple tuple = new Tuple(repositoryEventTypeClass, modelClass);

		Collection<RepositoryEventListener<?, ?>> repositoryEventListeners =
			_repositoryEventListeners.get(tuple);

		if (repositoryEventListeners != null) {
			repositoryEventListeners.remove(repositoryEventListener);
		}
	}

	private final RepositoryEventTrigger _parentRepositoryEventTrigger;
	private final Map<Tuple, Collection<RepositoryEventListener<?, ?>>>
		_repositoryEventListeners = new ConcurrentHashMap<>();

}