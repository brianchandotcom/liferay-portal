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

package com.liferay.wiki.configuration;

/**
 * @author Ivan Zaera
 */
public class WikiServiceConfigurationValues {

	public static final String FRONT_PAGE_NAME =
		WikiServiceConfigurationUtil.get("front.page.name");

	public static final String INITIAL_NODE_NAME =
		WikiServiceConfigurationUtil.get("initial.node.name");

	public static final String PAGE_TITLES_REGEXP =
		WikiServiceConfigurationUtil.get("page.titles.regexp");

	public static final String PAGE_TITLES_REMOVE_REGEXP =
		WikiServiceConfigurationUtil.get("page.titles.remove.regexp");

	public static final String[] PARSERS_CREOLE_SUPPORTED_PROTOCOLS =
		WikiServiceConfigurationUtil.getArray(
			"parsers.creole.supported.protocols");

}