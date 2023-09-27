/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.graph;

import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class PathElement implements Serializable {

	public PathElement(
		KaleoNode startKaleoNode, KaleoNode targetKaleoNode,
		ExecutionContext executionContext) {

		_startKaleoNode = startKaleoNode;
		_targetKaleoNode = targetKaleoNode;
		_executionContext = executionContext;
	}

	public ExecutionContext getExecutionContext() {
		return _executionContext;
	}

	public KaleoNode getStartNode() {
		return _startKaleoNode;
	}

	public KaleoNode getTargetNode() {
		return _targetKaleoNode;
	}

	private final ExecutionContext _executionContext;
	private final KaleoNode _startKaleoNode;
	private final KaleoNode _targetKaleoNode;

}