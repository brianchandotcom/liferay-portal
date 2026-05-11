/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.Base64;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "model.class.name=com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration",
	service = ConfigurationModelListener.class
)
public class AIHubCellConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(
		String pid, Dictionary<String, Object> properties) {

		byte[] bytes = new byte[64];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = SecureRandomUtil.nextByte();
		}

		properties.put("secret", Base64.encode(bytes));
	}

}