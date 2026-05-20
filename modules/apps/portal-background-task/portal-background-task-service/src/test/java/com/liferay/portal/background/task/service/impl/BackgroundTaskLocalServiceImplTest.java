/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.service.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.background.task.internal.BackgroundTaskInExecutionUtil;
import com.liferay.portal.background.task.internal.lock.helper.BackgroundTaskLockHelper;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.model.impl.BackgroundTaskImpl;
import com.liferay.portal.background.task.service.persistence.BackgroundTaskPersistence;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author Eric Yan
 */
public class BackgroundTaskLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_backgroundTaskLocalServiceImpl = Mockito.spy(
			new BackgroundTaskLocalServiceImpl());

		ReflectionTestUtil.setFieldValue(
			_backgroundTaskLocalServiceImpl, "backgroundTaskPersistence",
			_backgroundTaskPersistence);
		ReflectionTestUtil.setFieldValue(
			_backgroundTaskLocalServiceImpl, "_backgroundTaskLockHelper",
			_backgroundTaskLockHelper);

		Mockito.doNothing(
		).when(
			_backgroundTaskLocalServiceImpl
		).cleanUpBackgroundTask(
			Mockito.anyLong(), Mockito.anyInt()
		);
	}

	@Test
	public void testCleanUpBackgroundTasksWithInProgressTask() {
		BackgroundTask backgroundTask = _createBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_IN_PROGRESS);

		Mockito.when(
			_backgroundTaskPersistence.findByCompleted(false)
		).thenReturn(
			ListUtil.fromArray(backgroundTask)
		);

		try (SafeCloseable safeCloseable =
				BackgroundTaskInExecutionUtil.setInExecutionWithSafeCloseable(
					1L)) {

			_backgroundTaskLocalServiceImpl.cleanUpBackgroundTasks();
		}

		Assert.assertFalse(backgroundTask.isCompleted());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_IN_PROGRESS,
			backgroundTask.getStatus());

		Mockito.verify(
			_backgroundTaskLocalServiceImpl, Mockito.never()
		).cleanUpBackgroundTask(
			Mockito.anyLong(), Mockito.anyInt()
		);

		Mockito.verify(
			_backgroundTaskPersistence, Mockito.never()
		).update(
			Mockito.any()
		);
	}

	@Test
	public void testCleanUpBackgroundTasksWithInProgressTaskWhenStale() {
		BackgroundTask backgroundTask = _createBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_IN_PROGRESS);

		Mockito.when(
			_backgroundTaskPersistence.findByCompleted(false)
		).thenReturn(
			ListUtil.fromArray(backgroundTask)
		);

		Mockito.when(
			_backgroundTaskPersistence.update(backgroundTask)
		).thenReturn(
			backgroundTask
		);

		_backgroundTaskLocalServiceImpl.cleanUpBackgroundTasks();

		Assert.assertTrue(backgroundTask.isCompleted());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED, backgroundTask.getStatus());

		Mockito.verify(
			_backgroundTaskLocalServiceImpl
		).cleanUpBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_FAILED
		);

		Mockito.verify(
			_backgroundTaskPersistence
		).update(
			backgroundTask
		);
	}

	@Test
	public void testCleanUpBackgroundTasksWithQueuedTask() {
		BackgroundTask backgroundTask = _createBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_QUEUED);

		Mockito.when(
			_backgroundTaskPersistence.findByCompleted(false)
		).thenReturn(
			ListUtil.fromArray(backgroundTask)
		);

		Mockito.when(
			_backgroundTaskLockHelper.isLockedBackgroundTask(Mockito.any())
		).thenReturn(
			true
		);

		_backgroundTaskLocalServiceImpl.cleanUpBackgroundTasks();

		Assert.assertFalse(backgroundTask.isCompleted());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_QUEUED, backgroundTask.getStatus());

		Mockito.verify(
			_backgroundTaskLocalServiceImpl, Mockito.never()
		).cleanUpBackgroundTask(
			Mockito.anyLong(), Mockito.anyInt()
		);

		Mockito.verify(
			_backgroundTaskPersistence, Mockito.never()
		).update(
			Mockito.any()
		);
	}

	@Test
	public void testCleanUpBackgroundTasksWithQueuedTaskWhenOrphaned() {
		BackgroundTask backgroundTask = _createBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_QUEUED);

		Mockito.when(
			_backgroundTaskPersistence.findByCompleted(false)
		).thenReturn(
			ListUtil.fromArray(backgroundTask)
		);

		Mockito.when(
			_backgroundTaskLockHelper.isLockedBackgroundTask(Mockito.any())
		).thenReturn(
			false
		);

		_backgroundTaskLocalServiceImpl.cleanUpBackgroundTasks();

		Assert.assertFalse(backgroundTask.isCompleted());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_QUEUED, backgroundTask.getStatus());

		Mockito.verify(
			_backgroundTaskLocalServiceImpl
		).cleanUpBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_QUEUED
		);

		Mockito.verify(
			_backgroundTaskPersistence, Mockito.never()
		).update(
			Mockito.any()
		);
	}

	@Test
	public void testCleanUpBackgroundTasksWithStaleInProgressTaskAndOrphanedQueuedTask() {
		BackgroundTask inProgressBackgroundTask = _createBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_IN_PROGRESS);
		BackgroundTask queuedBackgroundTask = _createBackgroundTask(
			2L, BackgroundTaskConstants.STATUS_QUEUED);

		Mockito.when(
			_backgroundTaskPersistence.findByCompleted(false)
		).thenReturn(
			ListUtil.fromArray(inProgressBackgroundTask, queuedBackgroundTask)
		);

		Mockito.when(
			_backgroundTaskPersistence.update(inProgressBackgroundTask)
		).thenReturn(
			inProgressBackgroundTask
		);

		Mockito.when(
			_backgroundTaskLockHelper.isLockedBackgroundTask(Mockito.any())
		).thenReturn(
			false
		);

		_backgroundTaskLocalServiceImpl.cleanUpBackgroundTasks();

		Assert.assertTrue(inProgressBackgroundTask.isCompleted());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED,
			inProgressBackgroundTask.getStatus());
		Assert.assertFalse(queuedBackgroundTask.isCompleted());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_QUEUED,
			queuedBackgroundTask.getStatus());

		InOrder inOrder = Mockito.inOrder(_backgroundTaskLocalServiceImpl);

		inOrder.verify(
			_backgroundTaskLocalServiceImpl
		).cleanUpBackgroundTask(
			2L, BackgroundTaskConstants.STATUS_QUEUED
		);

		inOrder.verify(
			_backgroundTaskLocalServiceImpl
		).cleanUpBackgroundTask(
			1L, BackgroundTaskConstants.STATUS_FAILED
		);

		Mockito.verify(
			_backgroundTaskPersistence
		).update(
			inProgressBackgroundTask
		);

		Mockito.verify(
			_backgroundTaskPersistence, Mockito.never()
		).update(
			queuedBackgroundTask
		);
	}

	private BackgroundTask _createBackgroundTask(long id, int status) {
		BackgroundTask backgroundTask = new BackgroundTaskImpl();

		backgroundTask.setBackgroundTaskId(id);
		backgroundTask.setStatus(status);

		return backgroundTask;
	}

	private BackgroundTaskLocalServiceImpl _backgroundTaskLocalServiceImpl;
	private final BackgroundTaskLockHelper _backgroundTaskLockHelper =
		Mockito.mock(BackgroundTaskLockHelper.class);
	private final BackgroundTaskPersistence _backgroundTaskPersistence =
		Mockito.mock(BackgroundTaskPersistence.class);

}