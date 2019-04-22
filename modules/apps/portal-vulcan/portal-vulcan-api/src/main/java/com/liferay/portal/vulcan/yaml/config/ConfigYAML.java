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

package com.liferay.portal.vulcan.yaml.config;

/**
 * @author Peter Shin
 */
public class ConfigYAML {

	public String getApiDir() {
		return _apiDir;
	}

	public String getApiPackagePath() {
		return _apiPackagePath;
	}

	public Application getApplication() {
		return _application;
	}

	public String getAuthor() {
		return _author;
	}

	public String getClientDir() {
		return _clientDir;
	}

	public String getImplDir() {
		return _implDir;
	}

	public String getTestDir() {
		return _testDir;
	}

	public boolean isForcePredictableContentApplicationXML() {
		return _forcePredictableContentApplicationXML;
	}

	public boolean isForcePredictableOperationId() {
		return _forcePredictableOperationId;
	}

	public boolean isWarningsEnabled() {
		return _warningsEnabled;
	}

	public void setApiDir(String apiDir) {
		_apiDir = apiDir;
	}

	public void setApiPackagePath(String apiPackagePath) {
		_apiPackagePath = apiPackagePath;
	}

	public void setApplication(Application application) {
		_application = application;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setClientDir(String clientDir) {
		_clientDir = clientDir;
	}

	public void setForcePredictableContentApplicationXML(
		boolean forcePredictableContentApplicationXML) {

		_forcePredictableContentApplicationXML =
			forcePredictableContentApplicationXML;
	}

	public void setForcePredictableOperationId(
		boolean forcePredictableOperationId) {

		_forcePredictableOperationId = forcePredictableOperationId;
	}

	public void setImplDir(String implDir) {
		_implDir = implDir;
	}

	public void setTestDir(String testDir) {
		_testDir = testDir;
	}

	public void setWarningsEnabled(boolean warningsEnabled) {
		_warningsEnabled = warningsEnabled;
	}

	private String _apiDir;
	private String _apiPackagePath;
	private Application _application;
	private String _author;
	private String _clientDir;
	private boolean _forcePredictableContentApplicationXML = true;
	private boolean _forcePredictableOperationId = true;
	private String _implDir = "src/main/java";
	private String _testDir;
	private boolean _warningsEnabled = true;

}