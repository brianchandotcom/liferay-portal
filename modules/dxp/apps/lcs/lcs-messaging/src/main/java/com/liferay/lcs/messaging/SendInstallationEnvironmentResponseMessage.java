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

package com.liferay.lcs.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class SendInstallationEnvironmentResponseMessage
	extends ResponseMessage {

	public Map<String, String> getHardwareMetadata() {
		return _hardwareMetadata;
	}

	public Map<String, String> getSoftwareMetadata() {
		return _softwareMetadata;
	}

	public void setHardwareMetadata(Map<String, String> hardwareMetadata) {
		_hardwareMetadata = hardwareMetadata;
	}

	public void setSoftwareMetadata(Map<String, String> softwareMetadata) {
		_softwareMetadata = softwareMetadata;
	}

	private Map<String, String> _hardwareMetadata =
		new HashMap<String, String>();
	private Map<String, String> _softwareMetadata =
		new HashMap<String, String>();

}