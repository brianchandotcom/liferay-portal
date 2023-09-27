/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Michael C. Han
 */
public class Join extends Node {

	public Join(String name, String description) {
		super(NodeType.JOIN, name, description);
	}

	protected Join(NodeType nodeType, String name, String description) {
		super(nodeType, name, description);
	}

}