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

package com.liferay.gradle.plugins.js.transpiler;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JSTranspilerExtension {

	public JSTranspilerExtension(Project project) {
	}

	public String getBabelVersion() {
		return GradleUtil.toString(_babelVersion);
	}

	public String getLfrAmdLoaderVersion() {
		return GradleUtil.toString(_lfrAmdLoaderVersion);
	}

	public void setBabelVersion(Object babelVersion) {
		_babelVersion = babelVersion;
	}

	public void setLfrAmdLoaderVersion(Object lfrAmdLoaderVersion) {
		_lfrAmdLoaderVersion = lfrAmdLoaderVersion;
	}

	private Object _babelVersion = "5.8.23";
	private Object _lfrAmdLoaderVersion = "1.3.5";

}