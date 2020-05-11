/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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