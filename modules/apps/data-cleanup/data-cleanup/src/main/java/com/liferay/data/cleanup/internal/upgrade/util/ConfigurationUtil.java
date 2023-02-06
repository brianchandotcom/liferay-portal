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