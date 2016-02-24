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

package com.liferay.portal.workflow.kaleo.runtime.node;

import com.liferay.portal.workflow.kaleo.util.NodeTypeDependentObjectRegistry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = NodeExecutorFactory.class)
public class NodeExecutorFactory {

	public NodeExecutor getNodeExecutor(String nodeTypeString) {
		return _nodeExecutors.getNodeTypeDependentObjects(nodeTypeString);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeNodeExporter"
	)
	protected void addNodeExporter(
		NodeExecutor nodeExecutor, Map<String, Object> properties) {

		String nodeType = (String)properties.get("node.type");

		if (nodeType == null) {
			throw new IllegalArgumentException("Must specify a node type");
		}

		_nodeExecutors.addNodeTypeDependentObject(nodeType, nodeExecutor);
	}

	protected void removeNodeExporter(
		NodeExecutor nodeExecutor, Map<String, Object> properties) {

		String nodeType = (String)properties.get("node.type");

		if (nodeType == null) {
			throw new IllegalArgumentException("Must specify a node type");
		}

		_nodeExecutors.removeNodeTypeDependentObjects(nodeType);
	}

	private final NodeTypeDependentObjectRegistry<NodeExecutor>
		_nodeExecutors = new NodeTypeDependentObjectRegistry<>();

}