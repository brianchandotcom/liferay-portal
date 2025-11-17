/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.ChannelDataSource;

/**
 * @author Marcos Martins
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ChannelDataSourceDisplay {

	public ChannelDataSourceDisplay() {
	}

	public ChannelDataSourceDisplay(ChannelDataSource channelDataSource) {
		_channelId = channelDataSource.getChannelId();
		_enabled = channelDataSource.isEnabled();
		_name = channelDataSource.getName();
	}

	private String _channelId;
	private boolean _enabled;
	private String _name;

}