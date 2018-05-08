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

package com.liferay.configuration.admin.web.internal.exporter;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationExporter {

	public static byte[] getPropertiesAsBytes(Dictionary properties)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, properties);

		return unsyncByteArrayOutputStream.toByteArray();
	}

}