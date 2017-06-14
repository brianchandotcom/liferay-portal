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

package com.liferay.portal.tools.bundle.support.maven;

import com.liferay.portal.tools.bundle.support.internal.BundleSupportConstants;

import java.io.File;

import java.net.URL;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author David Truong
 */
public abstract class AbstractBundleMojo extends AbstractLiferayMojo {

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_BUNDLE_CACHE_DIR_NAME
	)
	protected File cacheDir;

	@Parameter(defaultValue = "configs")
	protected String configs;

	@Parameter(defaultValue = BundleSupportConstants.DEFAULT_ENVIRONMENT)
	protected String environment;

	@Parameter
	protected String password;

	@Parameter(
		defaultValue = "" + BundleSupportConstants.DEFAULT_STRIP_COMPONENTS
	)
	protected int stripComponents;

	@Parameter
	protected boolean token;

	@Parameter(
		defaultValue = BundleSupportConstants.DEFAULT_BUNDLE_URL,
		required = true
	)
	protected URL url;

	@Parameter
	protected String userName;

}