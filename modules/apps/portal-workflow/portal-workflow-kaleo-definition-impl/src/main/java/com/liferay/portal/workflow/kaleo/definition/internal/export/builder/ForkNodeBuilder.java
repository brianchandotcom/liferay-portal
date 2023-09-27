/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.workflow.kaleo.definition.Fork;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = NodeBuilder.class)
public class ForkNodeBuilder
	extends BaseNodeBuilder<Fork> implements NodeBuilder {

	@Override
	public NodeType getNodeType() {
		return NodeType.FORK;
	}

	@Override
	protected Fork createNode(KaleoNode kaleoNode) {
		return new Fork(kaleoNode.getName(), kaleoNode.getDescription());
	}

}