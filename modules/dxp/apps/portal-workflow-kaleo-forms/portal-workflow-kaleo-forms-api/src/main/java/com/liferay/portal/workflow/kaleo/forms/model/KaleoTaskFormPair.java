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

package com.liferay.portal.workflow.kaleo.forms.model;

/**
 * @author Marcellus Tavares
 */
public class KaleoTaskFormPair {

	public KaleoTaskFormPair(String workflowTaskName, long ddmTemplateId) {
		_workflowTaskName = workflowTaskName;
		_ddmTemplateId = ddmTemplateId;
	}

	public long getDDMTemplateId() {
		return _ddmTemplateId;
	}

	public String getWorkflowTaskName() {
		return _workflowTaskName;
	}

	private final long _ddmTemplateId;
	private final String _workflowTaskName;

}