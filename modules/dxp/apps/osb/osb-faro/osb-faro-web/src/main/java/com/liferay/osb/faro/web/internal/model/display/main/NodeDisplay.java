/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.main;

import com.liferay.osb.faro.engine.client.model.Node;

/**
 * @author Shinn Lok
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class NodeDisplay {

	public NodeDisplay() {
	}

	public NodeDisplay(Node node) {
		_count = node.getCount();
		_name = node.getName();
		_id = node.getId();
		_parentId = node.getParentId();
	}

	private int _count;
	private String _id;
	private String _name;
	private String _parentId;

}