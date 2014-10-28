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
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;

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

	private static class NoOpRepositoryEventListener
			<S extends RepositoryEventType, T>
		implements RepositoryEventListener<S, T> {

		@Override
		public void execute(T target) throws PortalException {
		}

	}

}