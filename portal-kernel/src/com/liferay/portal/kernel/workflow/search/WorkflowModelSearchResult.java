/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow.search;

import com.liferay.portal.kernel.workflow.WorkflowModel;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class WorkflowModelSearchResult<T extends WorkflowModel>
	implements Serializable {

	public WorkflowModelSearchResult(List<T> workflowModels, int length) {
		if (workflowModels == null) {
			_workflowModels = Collections.emptyList();
		}
		else {
			_workflowModels = workflowModels;
		}

		_length = length;
	}

	public int getLength() {
		return _length;
	}

	public List<T> getWorkflowModels() {
		return _workflowModels;
	}

	private final int _length;
	private final List<T> _workflowModels;

}