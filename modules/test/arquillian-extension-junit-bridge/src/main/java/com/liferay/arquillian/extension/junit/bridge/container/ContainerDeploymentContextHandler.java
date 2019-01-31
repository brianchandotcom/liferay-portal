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

package com.liferay.arquillian.extension.junit.bridge.container;

import org.jboss.arquillian.container.spi.context.ContainerContext;
import org.jboss.arquillian.container.spi.context.DeploymentContext;
import org.jboss.arquillian.container.spi.event.ContainerControlEvent;
import org.jboss.arquillian.container.spi.event.DeploymentEvent;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;

/**
 * @author Matthew Tambara
 */
public class ContainerDeploymentContextHandler {

	public void createContainerContext(
		@Observes EventContext<ContainerControlEvent> eventContext) {

		ContainerContext containerContext = _containerContextInstance.get();

		ContainerControlEvent containerControlEvent = eventContext.getEvent();

		try {
			containerContext.activate(containerControlEvent.getContainerName());

			eventContext.proceed();
		}
		finally {
			containerContext.deactivate();
		}
	}

	public void createDeploymentContext(
		@Observes EventContext<DeploymentEvent> eventContext) {

		DeploymentContext deploymentContext = _deploymentContextInstance.get();

		try {
			DeploymentEvent deploymentEvent = eventContext.getEvent();

			deploymentContext.activate(deploymentEvent.getDeployment());

			eventContext.proceed();
		}
		finally {
			deploymentContext.deactivate();
		}
	}

	@Inject
	private Instance<ContainerContext> _containerContextInstance;

	@Inject
	private Instance<DeploymentContext> _deploymentContextInstance;

}