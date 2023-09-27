/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.module.framework;

import org.osgi.framework.launch.Framework;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public interface ModuleFramework {

	public Framework getFramework();

	public void initFramework() throws Exception;

	public void registerContext(Object context);

	public void startFramework() throws Exception;

	public void stopFramework(long timeout) throws Exception;

	public void unregisterContext(Object context);

}