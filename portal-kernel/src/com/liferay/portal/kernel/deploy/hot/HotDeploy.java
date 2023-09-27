/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.util.PortalLifecycle;

/**
 * @author Raymond Augé
 */
public interface HotDeploy {

	public void fireDeployEvent(HotDeployEvent hotDeployEvent);

	public void fireUndeployEvent(HotDeployEvent hotDeployEvent);

	public boolean registerDependentPortalLifecycle(
		String servletContextName, PortalLifecycle portalLifecycle);

	public void registerListener(HotDeployListener hotDeployListener);

	public void reset();

	public void setCapturePrematureEvents(boolean capturePrematureEvents);

	public void unregisterListener(HotDeployListener hotDeployListener);

	public void unregisterListeners();

}