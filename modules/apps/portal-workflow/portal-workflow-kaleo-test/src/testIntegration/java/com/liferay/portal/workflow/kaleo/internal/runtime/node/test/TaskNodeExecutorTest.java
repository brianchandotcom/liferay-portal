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

package com.liferay.portal.workflow.kaleo.internal.runtime.node.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.NotificationType;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.TemplateLanguage;
import com.liferay.portal.workflow.kaleo.definition.Timer;
import com.liferay.portal.workflow.kaleo.definition.UserAssignment;
import com.liferay.portal.workflow.kaleo.definition.UserRecipient;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalService;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class TaskNodeExecutorTest extends BaseNodeExecutorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExecuteTimerSendNotifications() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		Timer timer = TimerBuilder.builder(
		).notification(
		).build();

		KaleoTimer kaleoTimer = addKaleoTimer(kaleoInstance, timer);

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken(kaleoInstance, kaleoInstanceToken);

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			_kaleoTimerInstanceTokenLocalService.addKaleoTimerInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				kaleoTimer.getKaleoTimerId(), RandomTestUtil.randomString(),
				workflowContext, serviceContext);

		final ExecutionContext executionContext = new ExecutionContext(
			kaleoInstanceToken, kaleoTimerInstanceToken, workflowContext,
			serviceContext);

		_executeTimer(executionContext);

		List<KaleoNotification> kaleoNotifications =
			_kaleoNotificationLocalService.getKaleoNotifications(
				KaleoTimer.class.getName(), kaleoTimer.getKaleoTimerId(),
				ExecutionType.ON_TIMER.getValue());

		Assert.assertTrue(ListUtil.isNotEmpty(kaleoNotifications));
		Assert.assertEquals(
			kaleoNotifications.toString(), 1, kaleoNotifications.size());

		KaleoNotification kaleoNotification = kaleoNotifications.get(0);

		Assert.assertEquals(
			ExecutionType.ON_TIMER.getValue(),
			kaleoNotification.getExecutionType());
		Assert.assertEquals("Notification name", kaleoNotification.getName());
		Assert.assertEquals(
			"Subject expected", kaleoNotification.getDescription());
		Assert.assertEquals(
			NotificationType.USER_NOTIFICATION.getValue(),
			kaleoNotification.getNotificationTypes());
		Assert.assertEquals(
			TemplateLanguage.FREEMARKER.getValue(),
			kaleoNotification.getTemplateLanguage());
		Assert.assertEquals(
			"Message template expected", kaleoNotification.getTemplate());
	}

	@Test
	public void testExecuteTimerSendReassignments() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		User user = _userLocalService.getUser(TestPropsValues.getUserId());

		Timer timer = TimerBuilder.builder(
		).reassignment(
			user
		).build();

		KaleoTimer kaleoTimer = addKaleoTimer(kaleoInstance, timer);

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken(kaleoInstance, kaleoInstanceToken);

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			_kaleoTimerInstanceTokenLocalService.addKaleoTimerInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				kaleoTimer.getKaleoTimerId(), RandomTestUtil.randomString(),
				workflowContext, serviceContext);

		final ExecutionContext executionContext = new ExecutionContext(
			kaleoInstanceToken, kaleoTimerInstanceToken, workflowContext,
			serviceContext);

		executionContext.setKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		_executeTimer(executionContext);

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			_kaleoTaskAssignmentInstanceLocalService.
				getKaleoTaskAssignmentInstances(
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		Assert.assertTrue(ListUtil.isNotEmpty(kaleoTaskAssignmentInstances));
		Assert.assertEquals(
			kaleoTaskAssignmentInstances.toString(), 1,
			kaleoTaskAssignmentInstances.size());

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			kaleoTaskAssignmentInstances.get(0);

		Assert.assertEquals(
			User.class.getName(),
			kaleoTaskAssignmentInstance.getAssigneeClassName());
		Assert.assertEquals(
			user.getUserId(), kaleoTaskAssignmentInstance.getAssigneeClassPK());
	}

	protected KaleoTimer addKaleoTimer(KaleoInstance kaleoInstance, Timer timer)
		throws Exception {

		String randomString = RandomTestUtil.randomString();

		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.addKaleoDefinitionVersion(
				kaleoDefinition.getKaleoDefinitionId(), randomString,
				randomString, randomString, randomString, randomString,
				serviceContext);

		KaleoNode kaleoNode = addKaleoNode(
			kaleoInstance, new Task(randomString, StringPool.BLANK));

		return _kaleoTimerLocalService.addKaleoTimer(
			KaleoNode.class.getName(), kaleoNode.getKaleoNodeId(),
			kaleoDefinition.getKaleoDefinitionId(),
			kaleoDefinitionVersion.getKaleoDefinitionVersionId(), timer,
			serviceContext);
	}

	protected static class TimerBuilder {

		public static TimerBuilder builder() {
			TimerBuilder timerBuilder = new TimerBuilder();

			timerBuilder._timer = new Timer(
				"Timer name", RandomTestUtil.randomString(), false);
			timerBuilder._timer.setDelayDuration(
				new DelayDuration(1, DurationScale.SECOND));

			return timerBuilder;
		}

		public Timer build() {
			return _timer;
		}

		public TimerBuilder notification() throws PortalException {
			Notification notification = new Notification(
				"Notification name", "Subject expected", null,
				"Message template expected",
				TemplateLanguage.FREEMARKER.getValue());

			UserRecipient userRecipient = new UserRecipient(
				TestPropsValues.getUserId(), null, null);

			userRecipient.setNotificationReceptionType(
				NotificationReceptionType.TO);

			notification.addRecipients(userRecipient);

			notification.addNotificationType(
				NotificationType.USER_NOTIFICATION.getValue());

			Set<Notification> notifications = new HashSet<>();

			notifications.add(notification);

			_timer.setNotifications(notifications);

			return this;
		}

		public TimerBuilder reassignment(User user) {
			Assignment assignment = new UserAssignment(
				user.getUserId(), user.getScreenName(), user.getEmailAddress());

			Set<Assignment> assignments = new HashSet<>();

			assignments.add(assignment);

			_timer.setReassignments(assignments);

			return this;
		}

		private Timer _timer;

	}

	private void _executeTimer(ExecutionContext executionContext)
		throws Exception {

		Method executeTimerMethod = ReflectionUtil.getDeclaredMethod(
			_nodeExecutor.getClass(), "executeTimer", ExecutionContext.class);

		executeTimerMethod.invoke(_nodeExecutor, executionContext);
	}

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Inject
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Inject
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Inject
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

	@Inject
	private KaleoTimerLocalService _kaleoTimerLocalService;

	@Inject(filter = "node.type=TASK")
	private NodeExecutor _nodeExecutor;

	@Inject
	private UserLocalService _userLocalService;

}