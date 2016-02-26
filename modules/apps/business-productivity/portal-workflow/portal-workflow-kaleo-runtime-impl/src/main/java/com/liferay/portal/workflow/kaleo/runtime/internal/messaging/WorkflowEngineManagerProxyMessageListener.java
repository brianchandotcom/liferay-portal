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

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"destination.name=" + DestinationNames.WORKFLOW_ENGINE},
	service = {
		MessageListener.class, WorkflowEngineManagerProxyMessageListener.class
	}
)
public class WorkflowEngineManagerProxyMessageListener
	extends ProxyMessageListener {

	@Activate
	protected void activate() {
		setManager(_workflowEngineManager);
		setMessageBus(_messageBus);
	}

	@Reference
	private MessageBus _messageBus;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowEngineManager _workflowEngineManager;

}