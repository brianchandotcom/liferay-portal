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

	public static enum Action {
		ADDING, REMOVED, MODIFIED
	}

	public static final String ALIAS = "alias";

	public static final String BEAN_ID = "bean.id";

	public static final String BUNDLE = "bundle";

	public static final String BUNDLE_ID = "bundle.id";

	public static final String BUNDLE_SYMBOLICNAME = "bundle.symbolicName";

	public static final String BUNDLE_VERSION = "bundle.version";

	public static final String COLLISION = "collision";

	public static final String COLLISION_BUNDLES = "collision.bundles";

	public static final String CONTEXT_ID = "contextId";

	public static final String CONTEXT_PATH = "context.path";

	public static final String EXCEPTION = "exception";

	public static final String EXTENDER_BUNDLE = "extender.bundle";

	public static final String EXTENDER_BUNDLE_ID = "extender.bundle.id";

	public static final String EXTENDER_BUNDLE_SYMBOLICNAME =
		"extender.bundle.symbolicName";

	public static final String EXTENDER_BUNDLE_VERSION =
		"extender.bundle.version";

	public static final String FELIX_FILEINSTALL_DIR = "felix.fileinstall.dir";

	public static final String FELIX_FILEINSTALL_LOG_LEVEL =
		"felix.fileinstall.log.level";

	public static final String FELIX_FILEINSTALL_POLL =
		"felix.fileinstall.poll";

	public static final String FELIX_FILEINSTALL_TMPDIR =
		"felix.fileinstall.tmpdir";

	public static final String INVOKER_PATH = "/invoke";

	public static final String INIT_PREFIX = "init.";

	public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

	public static final String MODULE = "o";

	public static final String NAME = "name";

	public static final String ORIGINAL_BEAN = "original.bean";

	public static final String OSGI_BUNDLE = "osgi-bundle";

	public static final String OSGI_BUNDLECONTEXT = "osgi-bundlecontext";

	public static final String PATTERN = "pattern";

	public static final String SERVICE_RANKING = "service.ranking";

	public static final String SERVICE_VENDOR = "service.vendor";

	public static final String SERVLET_CONTEXT_NAME = "servlet.context.name";

	public static final String SYSTEM_GENERATED_FRAGMENT_PREFIX = "sfg_";

	public static final String TIMESTAMP = "timestamp";

	public static final String WEB_CONTEXTPATH = "Web-ContextPath";

}