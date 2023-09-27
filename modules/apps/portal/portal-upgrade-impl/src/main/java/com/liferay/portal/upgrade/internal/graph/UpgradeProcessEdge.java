/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.graph;

import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;

import org.jgrapht.graph.DefaultEdge;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class UpgradeProcessEdge extends DefaultEdge {

	public UpgradeProcessEdge(UpgradeInfo upgradeInfo) {
		_upgradeInfo = upgradeInfo;
	}

	public UpgradeInfo getUpgradeInfo() {
		return _upgradeInfo;
	}

	private final UpgradeInfo _upgradeInfo;

}