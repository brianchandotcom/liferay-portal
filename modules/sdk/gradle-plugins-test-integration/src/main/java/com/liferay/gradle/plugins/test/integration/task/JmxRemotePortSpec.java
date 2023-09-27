/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

/**
 * @author Andrea Di Giorgi
 */
public interface JmxRemotePortSpec {

	public int getJmxRemotePort();

	public void setJmxRemotePort(Object jmxRemotePort);

}