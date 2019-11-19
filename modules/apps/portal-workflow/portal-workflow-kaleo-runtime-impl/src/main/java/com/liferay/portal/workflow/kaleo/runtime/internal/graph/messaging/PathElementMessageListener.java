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

package com.liferay.portal.workflow.kaleo.runtime.internal.graph.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.workflow.kaleo.runtime.constants.KaleoRuntimeDestinationNames;
import com.liferay.portal.workflow.kaleo.runtime.graph.GraphWalker;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "destination.name=" + KaleoRuntimeDestinationNames.KALEO_GRAPH_WALKER,
	service = MessageListener.class
)
public class PathElementMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Queue<List<PathElement>> queue = new ArrayDeque<>();

		queue.add(Collections.singletonList((PathElement)message.getPayload()));

		while (!queue.isEmpty()) {
			List<PathElement> pathElements = queue.remove();

			for (PathElement pathElement : pathElements) {
				List<PathElement> remainingPathElements = new ArrayList<>();

				_graphWalker.follow(
					pathElement.getStartNode(), pathElement.getTargetNode(),
					remainingPathElements, pathElement.getExecutionContext());

				if (!remainingPathElements.isEmpty()) {
					queue.add(remainingPathElements);
				}
			}
		}
	}

	@Reference
	private GraphWalker _graphWalker;

	@Reference
	private MessageBus _messageBus;

}