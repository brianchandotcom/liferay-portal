/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.osb.faro.engine.client.model.LCPProject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class LCPProjectConstants {

	public static Map<String, String> getLocations() {
		return _locations;
	}

	private static final Map<String, String> _locations = HashMapBuilder.put(
		"AS1", LCPProject.Cluster.AS1.toString()
	).put(
		"DEV", LCPProject.Cluster.DEV.toString()
	).put(
		"EU2", LCPProject.Cluster.EU2.toString()
	).put(
		"EU3", LCPProject.Cluster.EU3.toString()
	).put(
		"SA", LCPProject.Cluster.SA.toString()
	).put(
		"UAT", LCPProject.Cluster.UAT.toString()
	).put(
		"US", LCPProject.Cluster.US.toString()
	).build();

}