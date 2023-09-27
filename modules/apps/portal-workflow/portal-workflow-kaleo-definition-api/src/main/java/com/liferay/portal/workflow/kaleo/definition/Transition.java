/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class Transition {

	public Transition(
		boolean defaultValue, Map<Locale, String> labelMap, String name,
		Node sourceNode, Node targetNode) {

		_defaultValue = defaultValue;
		_labelMap = labelMap;
		_name = name;
		_sourceNode = sourceNode;
		_targetNode = targetNode;
	}

	public Map<Locale, String> getLabelMap() {
		return _labelMap;
	}

	public String getName() {
		return _name;
	}

	public Node getSourceNode() {
		return _sourceNode;
	}

	public Node getTargetNode() {
		return _targetNode;
	}

	public Timer getTimer() {
		return _timer;
	}

	public boolean isDefault() {
		return _defaultValue;
	}

	public void setTimers(Timer timer) {
		_timer = timer;
	}

	private final boolean _defaultValue;
	private final Map<Locale, String> _labelMap;
	private final String _name;
	private final Node _sourceNode;
	private final Node _targetNode;
	private Timer _timer;

}