/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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