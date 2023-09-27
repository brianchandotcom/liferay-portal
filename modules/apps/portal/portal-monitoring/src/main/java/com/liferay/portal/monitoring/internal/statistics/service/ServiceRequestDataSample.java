/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.service;

import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.monitoring.internal.BaseDataSample;
import com.liferay.portal.monitoring.internal.MonitorNames;

/**
 * @author Michael C. Han
 */
public class ServiceRequestDataSample extends BaseDataSample {

	public ServiceRequestDataSample(MethodSignature methodSignature) {
		_methodSignature = methodSignature;

		setDescription(_methodSignature.toString());

		setNamespace(MonitorNames.SERVICE);
	}

	public MethodSignature getMethodSignature() {
		return _methodSignature;
	}

	private final MethodSignature _methodSignature;

}