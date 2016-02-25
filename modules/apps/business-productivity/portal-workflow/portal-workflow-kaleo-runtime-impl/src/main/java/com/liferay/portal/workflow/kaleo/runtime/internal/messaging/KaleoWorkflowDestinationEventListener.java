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

package com.liferay.portal.workflow.kaleo.runtime.internal.messaging;

import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.workflow.messaging.DefaultWorkflowDestinationEventListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"destination.name=" + DestinationNames.WORKFLOW_ENGINE},
	service = DestinationEventListener.class
)
public class KaleoWorkflowDestinationEventListener
	implements DestinationEventListener {

	@Override
	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		_defaultWorkflowDestinationEventListener.messageListenerRegistered(
			destinationName, messageListener);
	}

	@Override
	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		_defaultWorkflowDestinationEventListener.messageListenerUnregistered(
			destinationName, messageListener);
	}

	@Activate
	protected void activate() {
		_defaultWorkflowDestinationEventListener =
			new DefaultWorkflowDestinationEventListener();

		_defaultWorkflowDestinationEventListener.
			setWorkflowComparatorFactoryListener(
				_workflowComparatorFactoryProxyMessageListener);
		_defaultWorkflowDestinationEventListener.
			setWorkflowDefinitionManagerListener(
				_workflowDefinitionManagerProxyMessageListener);
		_defaultWorkflowDestinationEventListener.
			setWorkflowEngineManagerListener(
				_workflowEngineManagerProxyMessageListener);
		_defaultWorkflowDestinationEventListener.setWorkflowEngineName(
			"Liferay Kaleo Workflow Engine");
		_defaultWorkflowDestinationEventListener.
			setWorkflowInstanceManagerListener(
				_workflowInstanceManagerProxyMessageListener);
		_defaultWorkflowDestinationEventListener.setWorkflowLogManagerListener(
			_workflowLogManagerProxyMessageListener);
		_defaultWorkflowDestinationEventListener.setWorkflowTaskManagerListener(
			_workflowTaskManagerProxyMessageListener);
	}

	private DefaultWorkflowDestinationEventListener
		_defaultWorkflowDestinationEventListener;

	@Reference
	private WorkflowComparatorFactoryProxyMessageListener
		_workflowComparatorFactoryProxyMessageListener;

	@Reference
	private WorkflowDefinitionManagerProxyMessageListener
		_workflowDefinitionManagerProxyMessageListener;

	@Reference
	private WorkflowEngineManagerProxyMessageListener
		_workflowEngineManagerProxyMessageListener;

	@Reference
	private WorkflowInstanceManagerProxyMessageListener
		_workflowInstanceManagerProxyMessageListener;

	@Reference
	private WorkflowLogManagerProxyMessageListener
		_workflowLogManagerProxyMessageListener;

	@Reference
	private WorkflowTaskManagerProxyMessageListener
		_workflowTaskManagerProxyMessageListener;

}