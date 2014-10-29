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

import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Enclosed.class)
public class RepositoryEventTest {

	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static final class WhenRegisteringRepositoryEvents {

		@Test
		public void shouldAcceptAnyNonNullListener() {
			_repositoryClassDefinition.registerRepositoryEventListener(
				RepositoryEventType.Add.class, FileEntry.class,
				new NoOpRepositoryEventListener
					<RepositoryEventType.Add, FileEntry>());
		}

		@Test(expected = NullPointerException.class)
		public void shouldFailOnNullListener() {
			_repositoryClassDefinition.registerRepositoryEventListener(
				RepositoryEventType.Add.class, FileEntry.class, null);
		}

		private final RepositoryClassDefinition _repositoryClassDefinition =
			new RepositoryClassDefinition(null);

	}

	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static final class WhenTriggeringEvents {

		@Test
		public void shouldExecuteAllMatchingListeners() throws Exception {
			AtomicInteger count = new AtomicInteger();

			for (int i = 0; i < 3; i++) {
				registerCounterRepositoryEventListener(
					_repositoryClassDefinition, RepositoryEventType.Add.class,
					FileEntry.class, count);
			}

			_repositoryClassDefinition.trigger(
				RepositoryEventType.Add.class, FileEntry.class, null);

			Assert.assertEquals(3, count.get());
		}

		@Test
		public void shouldExecuteListenerExactlyOncePerEvent()
			throws Exception {

			AtomicInteger count = registerCounterRepositoryEventListener(
				_repositoryClassDefinition, RepositoryEventType.Add.class,
				FileEntry.class);

			int randomInt = Math.abs(RandomTestUtil.nextInt());

			for (int i = 0; i < randomInt; i++) {
				_repositoryClassDefinition.trigger(
					RepositoryEventType.Add.class, FileEntry.class, null);
			}

			Assert.assertEquals(randomInt, count.get());
		}

		@Test
		public void shouldExecuteOnlyMatchingListeners() throws Exception {
			AtomicInteger count = registerCounterRepositoryEventListener(
				_repositoryClassDefinition, RepositoryEventType.Add.class,
				FileEntry.class);

			_repositoryClassDefinition.registerRepositoryEventListener(
				RepositoryEventType.Update.class, FileEntry.class,
				new AlwaysFailingRepositoryEventListener
					<RepositoryEventType.Update, FileEntry>());

			_repositoryClassDefinition.trigger(
				RepositoryEventType.Add.class, FileEntry.class, null);

			Assert.assertEquals(1, count.get());
		}

		private final RepositoryClassDefinition _repositoryClassDefinition =
			new RepositoryClassDefinition(null);

	}

	protected static <S extends RepositoryEventType, T>
		AtomicInteger registerCounterRepositoryEventListener(
			RepositoryEventRegistry repositoryEventRegistry,
			Class<S> eventClass, Class<T> modelClass) {

		AtomicInteger count = new AtomicInteger();

		CounterRepositoryEventListener<S, T> counterRepositoryEventListener =
			new CounterRepositoryEventListener<>(count);

		repositoryEventRegistry.registerRepositoryEventListener(
			eventClass, modelClass, counterRepositoryEventListener);

		return count;
	}

	protected static <S extends RepositoryEventType, T>
		AtomicInteger registerCounterRepositoryEventListener(
			RepositoryEventRegistry repositoryEventRegistry,
			Class<S> eventClass, Class<T> modelClass, AtomicInteger count) {

		CounterRepositoryEventListener<S, T> counterRepositoryEventListener =
			new CounterRepositoryEventListener<>(count);

		repositoryEventRegistry.registerRepositoryEventListener(
			eventClass, modelClass, counterRepositoryEventListener);

		return count;
	}

	private static class AlwaysFailingRepositoryEventListener
			<S extends RepositoryEventType, T>
		implements RepositoryEventListener<S, T> {

		@Override
		public void execute(T model) {
			throw new IllegalStateException();
		}

	}

	private static class CounterRepositoryEventListener
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

	private static class NoOpRepositoryEventListener
			<S extends RepositoryEventType, T>
		implements RepositoryEventListener<S, T> {

		@Override
		public void execute(T model) {
		}

	}

}