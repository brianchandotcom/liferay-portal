/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.repository.registry.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.registry.DefaultRepositoryEventRegistry;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class RepositoryEventWhenRegisteringRepositoryEventsTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldAcceptAnyNonnullListener() {
		_repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Add.class, FileEntry.class,
			new RepositoryEventTestUtil.NoOpRepositoryEventListener
				<RepositoryEventType.Add, FileEntry>());
	}

	@Test(expected = NullPointerException.class)
	public void testShouldFailOnNullListener() {
		_repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Add.class, FileEntry.class, null);
	}

	private final RepositoryEventRegistry _repositoryEventRegistry =
		new DefaultRepositoryEventRegistry(null);

}