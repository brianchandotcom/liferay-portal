/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.workflow.kaleo.definition.Join;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

/**
 * @author Michael C. Han
 */
public class JoinNodeBuilder extends BaseNodeBuilder<Join> {

	@Override
	public NodeType getNodeType() {
		return NodeType.JOIN;
	}

	@Override
	protected Join createNode(KaleoNode kaleoNode) {
		return new Join(kaleoNode.getName(), kaleoNode.getDescription());
	}

}