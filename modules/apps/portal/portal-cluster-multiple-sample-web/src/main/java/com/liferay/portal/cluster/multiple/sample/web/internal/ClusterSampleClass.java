/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.sample.web.internal;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class ClusterSampleClass implements Serializable {

	public static int getPortalLocalPort() {
		ClusterNode clusterNode = ClusterExecutorUtil.getLocalClusterNode();

		return clusterNode.getPortalPort();
	}

	public ClusterSampleClass(String name) {
		_name = name;
	}

	@Override
	public String toString() {
		return _name;
	}

	private final String _name;

}