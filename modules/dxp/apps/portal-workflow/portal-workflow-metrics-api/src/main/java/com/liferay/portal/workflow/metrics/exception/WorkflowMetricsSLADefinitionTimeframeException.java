/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		String msg, Throwable cause, List<String> fieldNames) {

		super(msg, cause);

		_fieldNames = fieldNames;
	}

	public WorkflowMetricsSLADefinitionTimeframeException(
		Throwable cause, List<String> fieldNames) {

		super(cause);

		_fieldNames = fieldNames;
	}

	public List<String> getFieldNames() {
		return _fieldNames;
	}

	private final List<String> _fieldNames;

}