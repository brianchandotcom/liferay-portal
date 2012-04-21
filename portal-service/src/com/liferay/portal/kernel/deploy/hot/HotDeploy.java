/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy.hot;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Raymond Augé
 */
public interface HotDeploy {

	public void fireDeployEvent(HotDeployEvent hotDeployEvent);

	public void fireUndeployEvent(HotDeployEvent hotDeployEvent);

	public boolean isMissingDependentServletContext(
		HotDeployEvent hotDeployEvent);

	public void registerDependentServletContextListener(
		HotDeployEvent hotDeployEvent, ServletContextEvent servletContextEvent,
		ServletContextListener servletContextListener);

	public void registerListener(HotDeployListener hotDeployListener);

	public void reset();

	public void setCapturePrematureEvents(boolean capturePrematureEvents);

	public void unregisterListener(HotDeployListener hotDeployListener);

	public void unregisterListeners();

}