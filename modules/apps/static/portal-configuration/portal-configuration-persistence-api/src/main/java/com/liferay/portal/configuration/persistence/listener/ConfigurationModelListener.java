/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.persistence.listener;

import java.util.Dictionary;

/**
 * @author Drew Brokke
 */
public interface ConfigurationModelListener {

	public default void onAfterDelete(String pid)
		throws ConfigurationModelListenerException {
	}

	public default void onAfterSave(
			String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {
	}

	public default void onBeforeDelete(String pid)
		throws ConfigurationModelListenerException {
	}

	public default void onBeforeSave(
			String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {
	}

}