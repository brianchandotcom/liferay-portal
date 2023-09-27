/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class State extends Node {

	public State(String name, String description, boolean initial) {
		super(NodeType.STATE, name, description);

		_initial = initial;
	}

	public boolean isInitial() {
		return _initial;
	}

	public boolean isTerminal() {
		Map<String, Transition> outgoingTransitions = getOutgoingTransitions();

		return outgoingTransitions.isEmpty();
	}

	private final boolean _initial;

}