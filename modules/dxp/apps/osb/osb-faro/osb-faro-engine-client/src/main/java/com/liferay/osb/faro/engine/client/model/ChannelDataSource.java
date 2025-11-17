/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Marcos Martins
 */
public class ChannelDataSource {

	public String getChannelId() {
		return _channelId;
	}

	public Boolean getEnabled() {
		return _enabled;
	}

	public String getName() {
		return _name;
	}

	public Boolean isEnabled() {
		return getEnabled();
	}

	public void setChannelId(String channelId) {
		_channelId = channelId;
	}

	public void setEnabled(Boolean enabled) {
		_enabled = enabled;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _channelId;
	private Boolean _enabled;
	private String _name;

}