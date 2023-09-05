/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal;

import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MonitoringControl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Dante Wang
 */
@Component(enabled = false, service = MonitoringControl.class)
public class MonitoringControlImpl implements MonitoringControl {

	@Override
	public Level getLevel(String namespace) {
		Level level = _levels.get(namespace);

		if (level == null) {
			return Level.OFF;
		}

		return level;
	}

	@Override
	public Set<String> getNamespaces() {
		return _levels.keySet();
	}

	@Override
	public void setLevel(String namespace, Level level) {
		_levels.put(namespace, level);
	}

	private final Map<String, Level> _levels = new ConcurrentHashMap<>();

}