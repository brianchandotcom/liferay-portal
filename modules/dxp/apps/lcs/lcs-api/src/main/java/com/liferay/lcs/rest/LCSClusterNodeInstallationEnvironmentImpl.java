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

package com.liferay.lcs.rest;

import java.util.Date;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class LCSClusterNodeInstallationEnvironmentImpl
	implements LCSClusterNodeInstallationEnvironment {

	@Override
	public Map<String, String> getHardwareMetadata() {
		return _hardwareMetadata;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public Map<String, String> getSoftwareMetadata() {
		return _softwareMetadata;
	}

	@Override
	public void setHardwareMetadata(Map<String, String> hardwareMetadata) {
		_hardwareMetadata = hardwareMetadata;
	}

	@Override
	public void setKey(String key) {
		_key = key;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public void setSoftwareMetadata(Map<String, String> softwareMetadata) {
		_softwareMetadata = softwareMetadata;
	}

	private Map<String, String> _hardwareMetadata;
	private String _key;
	private Date _modifiedDate;
	private Map<String, String> _softwareMetadata;

}