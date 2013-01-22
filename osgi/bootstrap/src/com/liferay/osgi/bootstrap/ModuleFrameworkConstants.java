/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.osgi.bootstrap;

/**
 * @author Raymond Augé
 */
public interface ModuleFrameworkConstants {

	public static final String FELIX_FILEINSTALL_DIR = "felix.fileinstall.dir";

	public static final String FELIX_FILEINSTALL_LOG_LEVEL =
		"felix.fileinstall.log.level";

	public static final String FELIX_FILEINSTALL_POLL =
		"felix.fileinstall.poll";

	public static final String FELIX_FILEINSTALL_TMPDIR =
		"felix.fileinstall.tmpdir";

	public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

	public static final String SERVICE_PROPERTY_KEY_BEAN_ID = "bean.id";

	public static final String SERVICE_PROPERTY_KEY_ORIGINAL_BEAN =
		"original.bean";

	public static final String SERVICE_PROPERTY_KEY_SERVICE_VENDOR =
		"service.vendor";

}