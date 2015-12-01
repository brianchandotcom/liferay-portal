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

import com.liferay.portal.upgrade.registry.UpgradeInfo;

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