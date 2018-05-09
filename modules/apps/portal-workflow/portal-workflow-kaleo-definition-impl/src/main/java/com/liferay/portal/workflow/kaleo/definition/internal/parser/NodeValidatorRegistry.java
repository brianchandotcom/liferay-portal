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

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.NodeTypeDependentObjectRegistry;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = NodeValidatorRegistry.class)
public class NodeValidatorRegistry {

	public NodeValidator<Node> getNodeValidator(NodeType nodeType) {
		return _nodeValidators.getNodeTypeDependentObjects(nodeType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addNodeValidator(
		NodeValidator<Node> nodeValidator, Map<String, Object> properties) {

		String nodeType = (String)properties.get("node.type");

		if (nodeType == null) {
			throw new IllegalArgumentException(
				"The property \"node.type\" is null");
		}

		_nodeValidators.addNodeTypeDependentObject(nodeType, nodeValidator);
	}

	protected void removeNodeValidator(
		NodeValidator<Node> nodeValidator, Map<String, Object> properties) {

		String nodeType = (String)properties.get("node.type");

		if (nodeType == null) {
			throw new IllegalArgumentException(
				"The property \"node.type\" is null");
		}

		_nodeValidators.removeNodeTypeDependentObjects(nodeType);
	}

	private final NodeTypeDependentObjectRegistry<NodeValidator<Node>>
		_nodeValidators = new NodeTypeDependentObjectRegistry<>();

}