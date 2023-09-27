/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.sample.web.internal;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.Serializable;

/**
 * @author Jorge Díaz
 */
public class ClusterSampleData implements Serializable {

	public ClusterSampleData() {
		_computerName = PortalUtil.getComputerName();
		_data = StringUtil.randomString(20);
		_liferayHome = SystemProperties.get("liferay.home");
		_timestamp = System.currentTimeMillis();
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getData() {
		return _data;
	}

	public String getLiferayHome() {
		return _liferayHome;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	private static final long serialVersionUID = 805643793521506119L;

	private final String _computerName;
	private final String _data;
	private final String _liferayHome;
	private long _timestamp = -1;

}