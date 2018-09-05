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

package com.liferay.portal.kernel.workflow.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * @author Michael C. Han
 */
public class DefaultWorkflowDestinationEventListener
	implements DestinationEventListener {

	@Override
	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Unregistering default workflow engine " + _workflowEngineName);
		}

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_COMPARATOR,
			_workflowComparatorFactoryListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_DEFINITION,
			_workflowDefinitionManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_ENGINE, _workflowEngineManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_INSTANCE,
			_workflowInstanceManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_LOG, _workflowLogManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_TASK, _workflowTaskManagerListener);
	}

	@Override
	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering default workflow engine " + _workflowEngineName);
		}

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_COMPARATOR,
			_workflowComparatorFactoryListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_DEFINITION,
			_workflowDefinitionManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_ENGINE, _workflowEngineManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE,
			_workflowInstanceManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_LOG, _workflowLogManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, _workflowTaskManagerListener);
	}

	public void setWorkflowComparatorFactoryListener(
		MessageListener workflowComparatorFactoryListener) {

		_workflowComparatorFactoryListener = workflowComparatorFactoryListener;
	}

	public void setWorkflowDefinitionManagerListener(
		MessageListener workflowDefinitionManagerListener) {

		_workflowDefinitionManagerListener = workflowDefinitionManagerListener;
	}

	public void setWorkflowEngineManagerListener(
		MessageListener workflowEngineManagerListener) {

		_workflowEngineManagerListener = workflowEngineManagerListener;
	}

	public void setWorkflowEngineName(String workflowEngineName) {
		_workflowEngineName = workflowEngineName;
	}

	public void setWorkflowInstanceManagerListener(
		MessageListener workflowInstanceManagerListener) {

		_workflowInstanceManagerListener = workflowInstanceManagerListener;
	}

	public void setWorkflowLogManagerListener(
		MessageListener workflowLogManagerListener) {

		_workflowLogManagerListener = workflowLogManagerListener;
	}

	public void setWorkflowTaskManagerListener(
		MessageListener workflowTaskManagerListener) {

		_workflowTaskManagerListener = workflowTaskManagerListener;
	}

	protected boolean isProceed(
		String destinationName, MessageListener messageListener) {

		if (messageListener.equals(_workflowEngineManagerListener)) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultWorkflowDestinationEventListener.class);

	private MessageListener _workflowComparatorFactoryListener;
	private MessageListener _workflowDefinitionManagerListener;
	private MessageListener _workflowEngineManagerListener;
	private String _workflowEngineName;
	private MessageListener _workflowInstanceManagerListener;
	private MessageListener _workflowLogManagerListener;
	private MessageListener _workflowTaskManagerListener;

}