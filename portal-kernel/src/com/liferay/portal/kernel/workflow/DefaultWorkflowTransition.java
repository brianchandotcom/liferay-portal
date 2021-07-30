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

package com.liferay.portal.kernel.workflow;

/**
 * @author Feliphe Marinho
 */
public class DefaultWorkflowTransition implements WorkflowTransition {

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getSourceNodeName() {
		return _sourceNodeName;
	}

	@Override
	public String getTargetNodeName() {
		return _targetNodeName;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSourceNodeName(String sourceNodeName) {
		_sourceNodeName = sourceNodeName;
	}

	public void setTargetNodeName(String targetNodeName) {
		_targetNodeName = targetNodeName;
	}

	private String _name;
	private String _sourceNodeName;
	private String _targetNodeName;

}