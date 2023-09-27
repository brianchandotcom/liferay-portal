/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.graph;

import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;

import java.util.List;

import org.jgrapht.EdgeFactory;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class UpgradeProcessEdgeFactory
	implements EdgeFactory<String, UpgradeProcessEdge> {

	public UpgradeProcessEdgeFactory(List<UpgradeInfo> upgradeInfos) {
		_upgradeInfos = upgradeInfos;
	}

	@Override
	public UpgradeProcessEdge createEdge(
		String sourceVertex, String targetVertex) {

		for (UpgradeInfo upgradeInfo : _upgradeInfos) {
			String fromSchemaVersionString =
				upgradeInfo.getFromSchemaVersionString();
			String toSchemaVersionString =
				upgradeInfo.getToSchemaVersionString();

			if (fromSchemaVersionString.equals(sourceVertex) &&
				toSchemaVersionString.equals(targetVertex)) {

				return new UpgradeProcessEdge(upgradeInfo);
			}
		}

		return null;
	}

	private final List<UpgradeInfo> _upgradeInfos;

}