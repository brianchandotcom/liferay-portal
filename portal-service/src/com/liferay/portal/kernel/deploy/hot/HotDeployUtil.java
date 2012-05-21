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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class HotDeployUtil {

	public static void fireDeployEvent(HotDeployEvent hotDeployEvent) {
		getHotDeploy().fireDeployEvent(hotDeployEvent);
	}

	public static void fireUndeployEvent(HotDeployEvent hotDeployEvent) {
		getHotDeploy().fireUndeployEvent(hotDeployEvent);
	}

	public static HotDeploy getHotDeploy() {
		PortalRuntimePermission.checkGetBeanProperty(HotDeployUtil.class);

		return _hotDeploy;
	}

	public static void registerListener(HotDeployListener hotDeployListener) {
		getHotDeploy().registerListener(hotDeployListener);
	}

	public static void reset() {
		getHotDeploy().reset();
	}

	public static void setCapturePrematureEvents(
		boolean capturePrematureEvents) {

		getHotDeploy().setCapturePrematureEvents(capturePrematureEvents);
	}

	public static void unregisterListener(HotDeployListener hotDeployListener) {
		getHotDeploy().unregisterListener(hotDeployListener);
	}

	public static void unregisterListeners() {
		getHotDeploy().unregisterListeners();
	}

	public void setHotDeploy(HotDeploy hotDeploy) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_hotDeploy = hotDeploy;
	}

	private static HotDeploy _hotDeploy;

}