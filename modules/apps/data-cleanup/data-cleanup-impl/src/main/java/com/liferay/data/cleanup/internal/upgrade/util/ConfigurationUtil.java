/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade.util;

import org.apache.felix.cm.PersistenceManager;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Shuyang Zhou
 */
public class ConfigurationUtil {

	public static void deleteConfiguration(
			ConfigurationAdmin configurationAdmin,
			PersistenceManager persistenceManager, String pid)
		throws Exception {

		Configuration configuration = configurationAdmin.getConfiguration(
			pid, "?");

		configuration.delete();

		persistenceManager.delete(pid);
	}

}