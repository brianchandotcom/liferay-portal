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

package com.liferay.osgi.bootstrap.constants;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public interface ModuleFrameworkHttpServiceConstants {
	public static enum HTTP_SERVICE_ACTION {
		ADDING, REMOVED, MODIFIED
	}

	public static final String HTTP_SERVICE_ALIAS = "alias";

	public static final String HTTP_SERVICE_CONTEXT_ID = "contextId";

	public static final String HTTP_SERVICE_KEY_BEAN_ID = "bean.id";

	public static final String HTTP_SERVICE_KEY_BUNDLE = "bundle";

	public static final String HTTP_SERVICE_KEY_BUNDLE_ID = "bundle.id";

	public static final String HTTP_SERVICE_KEY_BUNDLE_SYMBOLICNAME =
		"bundle.symbolicName";

	public static final String HTTP_SERVICE_KEY_BUNDLE_VERSION =
		"bundle.version";

	public static final String HTTP_SERVICE_KEY_COLLISION = "collision";

	public static final String HTTP_SERVICE_KEY_COLLISION_BUNDLES =
		"collision.bundles";

	public static final String HTTP_SERVICE_KEY_CONTEXT_PATH = "context.path";

	public static final String HTTP_SERVICE_KEY_EXCEPTION = "exception";

	public static final String HTTP_SERVICE_KEY_INIT_PREFIX = "init.";

	public static final String HTTP_SERVICE_KEY_NAME = "name";

	public static final String HTTP_SERVICE_KEY_ORIGINAL_BEAN = "original.bean";

	public static final String HTTP_SERVICE_KEY_SERVICE_RANKING =
		"service.ranking";

	public static final String HTTP_SERVICE_KEY_SERVICE_VENDOR =
		"service.vendor";

	public static final String HTTP_SERVICE_KEY_SERVLET_CONTEXT_NAME =
		"servlet.context.name";

	public static final String HTTP_SERVICE_KEY_TIMESTAMP = "timestamp";

	public static final String HTTP_SERVICE_KEY_WEB_CONTEXTPATH =
		"Web-ContextPath";

	public static final String HTTP_SERVICE_OSGI_BUNDLE = "osgi-bundle";

	public static final String HTTP_SERVICE_OSGI_BUNDLECONTEXT =
		"osgi-bundlecontext";

	public static final String HTTP_SERVICE_PATH_INVOKER = "/invoke";

	public static final String HTTP_SERVICE_PATTERN = "pattern";

}