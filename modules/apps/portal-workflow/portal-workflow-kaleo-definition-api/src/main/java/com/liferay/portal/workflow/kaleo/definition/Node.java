/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
public abstract class Node implements ActionAware, NotificationAware {

	public Node(NodeType nodeType, String name, String description) {
		_nodeType = nodeType;
		_name = name;
		_description = description;
	}

	public void addIncomingTransition(Transition transition) {
		_incomingTransitions.add(transition);
	}

	public void addOutgoingTransition(Transition transition) {
		_outgoingTransitions.put(transition.getName(), transition);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Node)) {
			return false;
		}

		Node node = (Node)object;

		if (!Objects.equals(_name, node._name)) {
			return false;
		}

		return true;
	}

	@Override
	public Set<Action> getActions() {
		if (_actions == null) {
			return Collections.emptySet();
		}

		return _actions;
	}

	public String getDefaultLabel() {
		if (_labelMap.isEmpty()) {
			return _name;
		}

		User user = UserLocalServiceUtil.fetchUser(
			PrincipalThreadLocal.getUserId());

		if (user != null) {
			String label = _labelMap.get(user.getLocale());

			if (label != null) {
				return label;
			}
		}

		String label = _labelMap.get(LocaleUtil.getSiteDefault());

		if (label != null) {
			return label;
		}

		label = _labelMap.get(LocaleUtil.getDefault());

		if (label != null) {
			return label;
		}

		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public Set<Transition> getIncomingTransitions() {
		return _incomingTransitions;
	}

	public int getIncomingTransitionsCount() {
		return _incomingTransitions.size();
	}

	public Map<Locale, String> getLabelMap() {
		return _labelMap;
	}

	public String getMetadata() {
		return _metadata;
	}

	public String getName() {
		return _name;
	}

	public NodeType getNodeType() {
		return _nodeType;
	}

	@Override
	public Set<Notification> getNotifications() {
		if (_notifications == null) {
			return Collections.emptySet();
		}

		return _notifications;
	}

	public Map<String, Transition> getOutgoingTransitions() {
		return _outgoingTransitions;
	}

	public int getOutgoingTransitionsCount() {
		return _outgoingTransitions.size();
	}

	public List<Transition> getOutgoingTransitionsList() {
		return ListUtil.fromCollection(_outgoingTransitions.values());
	}

	public Set<Timer> getTimers() {
		if (_timers == null) {
			return Collections.emptySet();
		}

		return _timers;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public void setActions(Set<Action> actions) {
		_actions = actions;
	}

	public void setLabelMap(Map<Locale, String> labelMap) {
		_labelMap = labelMap;
	}

	public void setMetadata(String metadata) {
		_metadata = metadata;
	}

	@Override
	public void setNotifications(Set<Notification> notifications) {
		_notifications = notifications;
	}

	public void setTimers(Set<Timer> timers) {
		_timers = timers;
	}

	private Set<Action> _actions;
	private final String _description;
	private final Set<Transition> _incomingTransitions = new LinkedHashSet<>();
	private Map<Locale, String> _labelMap;
	private String _metadata;
	private final String _name;
	private final NodeType _nodeType;
	private Set<Notification> _notifications;
	private final Map<String, Transition> _outgoingTransitions =
		new LinkedHashMap<>();
	private Set<Timer> _timers;

}