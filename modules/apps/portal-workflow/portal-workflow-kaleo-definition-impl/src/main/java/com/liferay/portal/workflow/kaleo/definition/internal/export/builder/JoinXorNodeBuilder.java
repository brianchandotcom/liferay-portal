/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.workflow.kaleo.definition.JoinXor;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

/**
 * @author Michael C. Han
 */
public class JoinXorNodeBuilder extends BaseNodeBuilder<JoinXor> {

	@Override
	public NodeType getNodeType() {
		return NodeType.JOIN_XOR;
	}

	@Override
	protected JoinXor createNode(KaleoNode kaleoNode) {
		return new JoinXor(kaleoNode.getName(), kaleoNode.getDescription());
	}

}