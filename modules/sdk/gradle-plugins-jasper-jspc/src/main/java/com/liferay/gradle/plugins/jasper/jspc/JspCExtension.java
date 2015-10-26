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

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JspCExtension implements CompileJSPSpec {

	public JspCExtension(Project project) {
		_project = project;
	}

	public void copyTo(CompileJSPSpec compileJSPSpec) {
		compileJSPSpec.setModuleWeb(_moduleWeb);

		if (compileJSPSpec.getPortalDir() == null) {
			compileJSPSpec.setPortalDir(_portalDir);
		}

		if (compileJSPSpec.getWebAppDir() == null) {
			compileJSPSpec.setWebAppDir(_webAppDir);
		}
	}

	public String getAntVersion() {
		return _antVersion;
	}

	public String getJspCVersion() {
		return _jspCVersion;
	}

	@Override
	public File getPortalDir() {
		return GradleUtil.toFile(_project, _portalDir);
	}

	@Override
	public File getWebAppDir() {
		return GradleUtil.toFile(_project, _webAppDir);
	}

	@Override
	public boolean isModuleWeb() {
		return _moduleWeb;
	}

	public void setAntVersion(String antVersion) {
		_antVersion = antVersion;
	}

	public void setJspCVersion(String jspCVersion) {
		_jspCVersion = jspCVersion;
	}

	@Override
	public void setModuleWeb(boolean moduleWeb) {
		_moduleWeb = moduleWeb;
	}

	@Override
	public void setPortalDir(Object portalDir) {
		_portalDir = portalDir;
	}

	@Override
	public void setWebAppDir(Object webAppDir) {
		_webAppDir = webAppDir;
	}

	private String _antVersion = "1.9.4";
	private String _jspCVersion = "latest.release";
	private boolean _moduleWeb;
	private Object _portalDir;
	private final Project _project;
	private Object _webAppDir;

}