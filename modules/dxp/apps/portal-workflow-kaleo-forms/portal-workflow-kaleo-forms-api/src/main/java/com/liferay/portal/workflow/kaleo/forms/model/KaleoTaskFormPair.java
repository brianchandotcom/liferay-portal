/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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