/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.util.PortalLifecycle;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class HotDeployUtil {

	public static void fireDeployEvent(HotDeployEvent hotDeployEvent) {
		_hotDeploy.fireDeployEvent(hotDeployEvent);
	}

	public static void fireUndeployEvent(HotDeployEvent hotDeployEvent) {
		_hotDeploy.fireUndeployEvent(hotDeployEvent);
	}

	public static HotDeploy getHotDeploy() {
		return _hotDeploy;
	}

	public static boolean registerDependentPortalLifecycle(
		String servletContextName, PortalLifecycle portalLifecycle) {

		return _hotDeploy.registerDependentPortalLifecycle(
			servletContextName, portalLifecycle);
	}

	public static void registerListener(HotDeployListener hotDeployListener) {
		_hotDeploy.registerListener(hotDeployListener);
	}

	public static void reset() {
		_hotDeploy.reset();
	}

	public static void setCapturePrematureEvents(
		boolean capturePrematureEvents) {

		_hotDeploy.setCapturePrematureEvents(capturePrematureEvents);
	}

	public static void unregisterListener(HotDeployListener hotDeployListener) {
		_hotDeploy.unregisterListener(hotDeployListener);
	}

	public static void unregisterListeners() {
		_hotDeploy.unregisterListeners();
	}

	public void setHotDeploy(HotDeploy hotDeploy) {
		_hotDeploy = hotDeploy;
	}

	private static HotDeploy _hotDeploy;

}