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

import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Fork;
import com.liferay.portal.workflow.kaleo.definition.Join;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 * @author Norbert Kocsis
 */
@Component(
	immediate = true, property = "node.type=FORK", service = NodeValidator.class
)
public class ForkNodeValidator extends BaseNodeValidator<Fork> {

	@Override
	protected void doValidate(Definition definition, Fork fork)
		throws KaleoDefinitionValidationException {

		if (fork.getIncomingTransitionsCount() == 0) {
			throw new KaleoDefinitionValidationException.
				MustSetIncomingTransition(fork.getName());
		}

		if (fork.getOutgoingTransitionsCount() < 2) {
			throw new KaleoDefinitionValidationException.
				MustSetMultipleOutgoingTransition(fork.getName());
		}

		traverse(fork);
	}

	protected List<Node> getUnvisitedNodes(
		List<Node> nodes, Collection<Transition> transitions, boolean target) {

		List<Node> unvisitedNodes = new ArrayList<>();

		for (Transition transition : transitions) {
			Node node = transition.getSourceNode();

			if (target) {
				node = transition.getTargetNode();
			}

			if (!nodes.contains(node)) {
				unvisitedNodes.add(node);
			}
		}

		return unvisitedNodes;
	}

	protected void reverseTraverse(
			Fork fork, Join join, List<Node> targetNodes,
			Map<Join, Fork> joinForkMap)
		throws KaleoDefinitionValidationException {

		List<Node> sourceNodes = new ArrayList<>();

		sourceNodes.add(join);

		for (Transition transition : join.getIncomingTransitions()) {
			sourceNodes.add(transition.getSourceNode());
		}

		for (int i = 1; i < sourceNodes.size(); i++) {
			Node sourceNode = sourceNodes.get(i);

			NodeType nodeType = sourceNode.getNodeType();

			if (nodeType.equals(NodeType.FORK) &&
				Objects.equals(fork, sourceNode)) {

				continue;
			}
			else if (nodeType.equals(NodeType.JOIN) ||
					 nodeType.equals(NodeType.JOIN_XOR)) {

				sourceNode = joinForkMap.get(sourceNode);

				sourceNodes.set(i, sourceNode);
			}

			List<Node> unvisitedSourceNodes = getUnvisitedNodes(
				sourceNodes, sourceNode.getIncomingTransitions(), false);

			sourceNodes.addAll(unvisitedSourceNodes);
		}

		if ((sourceNodes.size() != targetNodes.size()) ||
			!sourceNodes.containsAll(targetNodes)) {

			throw new KaleoDefinitionValidationException.
				UnbalancedForkAndJoinNode(fork.getName(), join.getName());
		}
	}

	protected Join traverse(Fork fork)
		throws KaleoDefinitionValidationException {

		Join join = null;

		List<Node> targetNodes = new ArrayList<>();

		Map<Join, Fork> joinForkMap = new HashMap<>();

		targetNodes.add(fork);

		for (Transition transition : fork.getOutgoingTransitionsList()) {
			targetNodes.add(transition.getTargetNode());
		}

		for (int i = 1; i < targetNodes.size(); i++) {
			Node targetNode = targetNodes.get(i);

			NodeType nodeType = targetNode.getNodeType();

			if (nodeType.equals(NodeType.FORK)) {
				Join localJoin = traverse((Fork)targetNode);

				joinForkMap.put(localJoin, (Fork)targetNode);

				List<Node> unvisitedTargetNodes = getUnvisitedNodes(
					targetNodes, localJoin.getOutgoingTransitionsList(), true);

				targetNodes.addAll(unvisitedTargetNodes);
			}
			else if (nodeType.equals(NodeType.JOIN) ||
					 nodeType.equals(NodeType.JOIN_XOR)) {

				if (join == null) {
					join = (Join)targetNode;
				}
				else if (!Objects.equals(join, targetNode)) {
					throw new KaleoDefinitionValidationException.
						MustPairedForkAndJoinNodes(
							fork.getName(), targetNode.getName());
				}
			}
			else {
				List<Node> unvisitedTargetNodes = getUnvisitedNodes(
					targetNodes, targetNode.getOutgoingTransitionsList(), true);

				targetNodes.addAll(unvisitedTargetNodes);
			}
		}

		if (join == null) {
			throw new KaleoDefinitionValidationException.MustSetJoinNode(
				fork.getName());
		}

		reverseTraverse(fork, join, targetNodes, joinForkMap);

		return join;
	}

}