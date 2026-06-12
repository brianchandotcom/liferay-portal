/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Iliyan Peychev
 */
public class ServiceNode extends Node {

	public ServiceNode(String description, String name) {
		super(NodeType.SERVICE, name, description);
	}

}