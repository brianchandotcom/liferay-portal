/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author João Victor Alves
 */
public class AIDecision extends Node {

	public AIDecision(String description, String name) {
		super(NodeType.AI_DECISION, name, description);
	}

}