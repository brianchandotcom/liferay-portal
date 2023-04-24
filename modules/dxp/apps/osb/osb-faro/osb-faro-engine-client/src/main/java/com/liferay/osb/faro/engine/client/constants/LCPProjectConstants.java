/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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