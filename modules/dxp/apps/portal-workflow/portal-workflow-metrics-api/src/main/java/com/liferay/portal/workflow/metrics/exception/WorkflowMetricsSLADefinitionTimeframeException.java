/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionTimeframeException
	extends PortalException {

	public WorkflowMetricsSLADefinitionTimeframeException(
		List<String> fieldNames) {

		super("Invalid configuration for " + fieldNames + " fields");

		_fieldNames = fieldNames;
	}

	public WorkflowMetricsSLADefinitionTimeframeException(
		String msg, List<String> fieldNames) {

		super(msg);

		_fieldNames = fieldNames;
	}

	public WorkflowMetricsSLADefinitionTimeframeException(
		String msg, Throwable throwable, List<String> fieldNames) {

		super(msg, throwable);

		_fieldNames = fieldNames;
	}

	public WorkflowMetricsSLADefinitionTimeframeException(
		Throwable throwable, List<String> fieldNames) {

		super(throwable);

		_fieldNames = fieldNames;
	}

	public List<String> getFieldNames() {
		return _fieldNames;
	}

	private final List<String> _fieldNames;

}