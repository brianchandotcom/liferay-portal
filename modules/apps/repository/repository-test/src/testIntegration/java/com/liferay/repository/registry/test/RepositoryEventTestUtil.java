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

package com.liferay.repository.registry.test;

import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Adolfo Pérez
 */
public class RepositoryEventTestUtil {

	protected static <S extends RepositoryEventType, T> AtomicInteger
		registerCounterRepositoryEventListener(
			RepositoryEventRegistry repositoryEventRegistry,
			Class<S> eventClass, Class<T> modelClass) {

		AtomicInteger count = new AtomicInteger();

		CounterRepositoryEventListener<S, T> counterRepositoryEventListener =
			new CounterRepositoryEventListener<>(count);

		repositoryEventRegistry.registerRepositoryEventListener(
			eventClass, modelClass, counterRepositoryEventListener);

		return count;
	}

	protected static <S extends RepositoryEventType, T> AtomicInteger
		registerCounterRepositoryEventListener(
			RepositoryEventRegistry repositoryEventRegistry,
			Class<S> eventClass, Class<T> modelClass, AtomicInteger count) {

		CounterRepositoryEventListener<S, T> counterRepositoryEventListener =
			new CounterRepositoryEventListener<>(count);

		repositoryEventRegistry.registerRepositoryEventListener(
			eventClass, modelClass, counterRepositoryEventListener);

		return count;
	}

	protected static class AlwaysFailingRepositoryEventListener
		<S extends RepositoryEventType, T>
			implements RepositoryEventListener<S, T> {

		@Override
		public void execute(T model) {
			throw new IllegalStateException();
		}

	}

	protected static class CounterRepositoryEventListener
		<S extends RepositoryEventType, T>
			implements RepositoryEventListener<S, T> {

		public CounterRepositoryEventListener(AtomicInteger count) {
			_count = count;
		}

		@Override
		public void execute(T model) {
			_count.incrementAndGet();
		}

		private final AtomicInteger _count;

	}

	protected static class NoOpRepositoryEventListener
		<S extends RepositoryEventType, T>
			implements RepositoryEventListener<S, T> {

		@Override
		public void execute(T model) {
		}

	}

}