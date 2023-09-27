/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.monitoring;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface ServiceMonitoringControl {

	public void addServiceClass(String className);

	public void addServiceClassMethod(
		String className, String methodName, String[] parameterTypes);

	public Set<String> getServiceClasses();

	public Set<MethodSignature> getServiceClassMethods();

	public boolean isInclusiveMode();

	public boolean isMonitorServiceRequest();

	public void setInclusiveMode(boolean inclusiveMode);

	public void setMonitorServiceRequest(boolean monitorServiceRequest);

}