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

package com.liferay.portal.tools.rest.builder.internal.yaml.config;

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

	public String getClientMavenGroupId() {
		return _clientMavenGroupId;
	}

	public String getImplDir() {
		return _implDir;
	}

	public String getLicenseName() {
		return _licenseName;
	}

	public String getLicenseURL() {
		return _licenseURL;
	}

	public String getTestDir() {
		return _testDir;
	}

	public boolean isForceClientVersionDescription() {
		return _forceClientVersionDescription;
	}

	public boolean isForcePredictableContentApplicationXML() {
		return _forcePredictableContentApplicationXML;
	}

	public boolean isForcePredictableOperationId() {
		return _forcePredictableOperationId;
	}

	public boolean isForcePredictableSchemaPropertyName() {
		return _forcePredictableSchemaPropertyName;
	}

	public boolean isGenerateBatch() {
		return _generateBatch;
	}

	public boolean isGenerateGraphQL() {
		return _generateGraphQL;
	}

	public boolean isGenerateREST() {
		return _generateREST;
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

	public void setClientMavenGroupId(String clientMavenGroupId) {
		_clientMavenGroupId = clientMavenGroupId;
	}

	public void setForceClientVersionDescription(
		boolean forceClientVersionDescription) {

		_forceClientVersionDescription = forceClientVersionDescription;
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

	public void setForcePredictableSchemaPropertyName(
		boolean forcePredictableSchemaPropertyName) {

		_forcePredictableSchemaPropertyName =
			forcePredictableSchemaPropertyName;
	}

	public void setGenerateBatch(boolean generateBatch) {
		_generateBatch = generateBatch;
	}

	public void setGenerateGraphQL(boolean generateGraphQL) {
		_generateGraphQL = generateGraphQL;
	}

	public void setGenerateREST(boolean generateREST) {
		_generateREST = generateREST;
	}

	public void setImplDir(String implDir) {
		_implDir = implDir;
	}

	public void setLicenseName(String licenseName) {
		_licenseName = licenseName;
	}

	public void setLicenseURL(String licenseURL) {
		_licenseURL = licenseURL;
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
	private String _clientMavenGroupId;
	private boolean _forceClientVersionDescription = true;
	private boolean _forcePredictableContentApplicationXML = true;
	private boolean _forcePredictableOperationId = true;
	private boolean _forcePredictableSchemaPropertyName = true;
	private boolean _generateBatch = true;
	private boolean _generateGraphQL = true;
	private boolean _generateREST = true;
	private String _implDir = "src/main/java";
	private String _licenseName = "Apache 2.0";
	private String _licenseURL =
		"http://www.apache.org/licenses/LICENSE-2.0.html";
	private String _testDir;
	private boolean _warningsEnabled = true;

}