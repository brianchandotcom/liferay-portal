/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.constants.KaleoRuntimeDestinationNames;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;

import java.util.HashSet;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoTimerInstanceTokenTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoInstance = addKaleoInstance();

		KaleoNode kaleoNode = addKaleoNode(
			_kaleoInstance,
			new Task(RandomTestUtil.randomString(), StringPool.BLANK));

		_kaleoInstanceToken = addKaleoInstanceToken(_kaleoInstance, kaleoNode);

		Task task = new Task(RandomTestUtil.randomString(), StringPool.BLANK);

		task.setAssignments(
			new HashSet<Assignment>() {
				{
					add(
						new RoleAssignment(
							RoleConstants.ADMINISTRATOR,
							RoleConstants.TYPE_REGULAR_LABEL));
				}
			});

		KaleoTask kaleoTask = addKaleoTask(_kaleoInstance, kaleoNode, task);

		_kaleoTaskInstanceToken = addKaleoTaskInstanceToken(
			_kaleoInstance, _kaleoInstanceToken, kaleoTask);

		_kaleoTimer = addKaleoTimer(_kaleoInstance);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			_kaleoTimerInstanceTokenLocalService.addKaleoTimerInstanceToken(
				_kaleoInstanceToken.getKaleoInstanceTokenId(),
				_kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				_kaleoTimer.getKaleoTimerId(), _kaleoTimer.getName(),
				WorkflowContextUtil.convert(
					_kaleoInstance.getWorkflowContext()),
				serviceContext);

		Message message = new Message();

		message.put(
			"kaleoTimerInstanceTokenId",
			kaleoTimerInstanceToken.getKaleoTimerInstanceTokenId());

		_messageListener.receive(message);

		return _kaleoTimerInstanceTokenLocalService.
			fetchKaleoTimerInstanceToken(
				kaleoTimerInstanceToken.getKaleoTimerInstanceTokenId());
	}

	private KaleoInstance _kaleoInstance;
	private KaleoInstanceToken _kaleoInstanceToken;
	private KaleoTaskInstanceToken _kaleoTaskInstanceToken;
	private KaleoTimer _kaleoTimer;

	@Inject
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

	@Inject(
		filter = "destination.name=" + KaleoRuntimeDestinationNames.WORKFLOW_TIMER
	)
	private MessageListener _messageListener;

}