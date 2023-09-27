/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.exporter;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationExporter {

	public static byte[] getPropertiesAsBytes(Dictionary<?, ?> properties)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, properties);

		return unsyncByteArrayOutputStream.toByteArray();
	}

}