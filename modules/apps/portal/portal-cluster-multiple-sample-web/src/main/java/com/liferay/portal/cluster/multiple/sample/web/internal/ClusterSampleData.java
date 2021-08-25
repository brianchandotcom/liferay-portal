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

package com.liferay.portal.cluster.multiple.sample.web.internal;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

/**
 * @author Jorge Díaz
 */
public class ClusterSampleData implements Serializable {

	public ClusterSampleData() {
		_computerName = PortalUtil.getComputerName();
		_data = StringUtil.randomString(20);
		_liferayHome = System.getProperty("liferay.home");
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