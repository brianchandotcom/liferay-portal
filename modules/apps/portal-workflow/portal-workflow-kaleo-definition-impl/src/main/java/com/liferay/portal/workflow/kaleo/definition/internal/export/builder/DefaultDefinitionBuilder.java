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

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.export.builder.DefinitionBuilder;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DefinitionBuilder.class)
public class DefaultDefinitionBuilder implements DefinitionBuilder {

	@Override
	public Definition buildDefinition(long kaleoDefinitionId)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.getKaleoDefinition(kaleoDefinitionId);

		return _doBuildDefinition(
			_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
				StringBundler.concat(
					kaleoDefinition.getVersion(), CharPool.PERIOD, 0)));
	}

	@Override
	public Definition buildDefinition(long companyId, String name, int version)
		throws PortalException {

		return _doBuildDefinition(
			_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				companyId, name,
				StringBundler.concat(version, CharPool.PERIOD, 0)));
	}

	private Definition _doBuildDefinition(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		Definition definition = new Definition(
			kaleoDefinitionVersion.getName(),
			kaleoDefinitionVersion.getDescription(),
			kaleoDefinitionVersion.getContent(),
			_getVersion(kaleoDefinitionVersion.getVersion()));

		List<KaleoNode> kaleoNodes =
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		for (KaleoNode kaleoNode : kaleoNodes) {
			NodeBuilder nodeBuilder = _nodeBuilderRegistry.getNodeBuilder(
				kaleoNode.getType());

			Node node = nodeBuilder.buildNode(kaleoNode);

			definition.addNode(node);
		}

		List<KaleoTransition> kaleoTransitions =
			_kaleoTransitionLocalService.
				getKaleoDefinitionVersionKaleoTransitions(
					kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		for (KaleoTransition kaleoTransition : kaleoTransitions) {
			String sourceNodeName = kaleoTransition.getSourceKaleoNodeName();

			Node sourceNode = definition.getNode(sourceNodeName);

			String targetNodeName = kaleoTransition.getTargetKaleoNodeName();

			Node targetNode = definition.getNode(targetNodeName);

			Transition transition = new Transition(
				kaleoTransition.isDefaultTransition(),
				kaleoTransition.getLabelMap(), kaleoTransition.getName(),
				sourceNode, targetNode);

			sourceNode.addOutgoingTransition(transition);

			targetNode.addIncomingTransition(transition);
		}

		return definition;
	}

	private int _getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTransitionLocalService _kaleoTransitionLocalService;

	@Reference
	private NodeBuilderRegistry _nodeBuilderRegistry;

}