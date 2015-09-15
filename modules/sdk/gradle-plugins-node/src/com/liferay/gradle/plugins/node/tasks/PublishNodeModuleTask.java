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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonOutput;

import groovy.lang.Writable;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.runtime.EncodingGroovyMethods;

import org.gradle.api.GradleException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;

/**
 * @author Andrea Di Giorgi
 */
public class PublishNodeModuleTask extends ExecuteNpmTask {

	@Override
	public void executeNode() {
		setArgs(getCompleteArgs());

		File npmrcFile = null;
		File packageJsonFile = null;

		try {
			npmrcFile = createNpmrcFile();
			packageJsonFile = createPackageJsonFile();

			super.executeNode();
		}
		catch (IOException ioe) {
			throw new GradleException(ioe.getMessage(), ioe);
		}
		finally {
			if (npmrcFile != null) {
				npmrcFile.delete();
			}

			if (packageJsonFile != null) {
				packageJsonFile.delete();
			}
		}
	}

	@Input
	@Optional
	public String getModuleAuthor() {
		return GradleUtil.toString(_moduleAuthor);
	}

	@Input
	@Optional
	public String getModuleDescription() {
		return GradleUtil.toString(_moduleDescription);
	}

	@Input
	public List<String> getModuleKeywords() {
		return GradleUtil.toStringList(_moduleKeywords);
	}

	@Input
	@Optional
	public String getModuleLicense() {
		return GradleUtil.toString(_moduleLicense);
	}

	@Input
	public String getModuleName() {
		return GradleUtil.toString(_moduleName);
	}

	@Input
	@Optional
	public String getModuleRepository() {
		return GradleUtil.toString(_moduleRepository);
	}

	@Input
	public String getModuleVersion() {
		return GradleUtil.toString(_moduleVersion);
	}

	@Input
	public String getNpmEmailAddress() {
		return GradleUtil.toString(_npmEmailAddress);
	}

	@Input
	public String getNpmPassword() {
		return GradleUtil.toString(_npmPassword);
	}

	@Input
	public String getNpmUserName() {
		return GradleUtil.toString(_npmUserName);
	}

	@InputDirectory
	@Override
	public File getWorkingDir() {
		return super.getWorkingDir();
	}

	public void setModuleAuthor(Object moduleAuthor) {
		_moduleAuthor = moduleAuthor;
	}

	public void setModuleDescription(Object moduleDescription) {
		_moduleDescription = moduleDescription;
	}

	public void setModuleKeywords(Iterable<?> moduleKeywords) {
		_moduleKeywords.clear();
	}

	public void setModuleKeywords(Object ... moduleKeywords) {
		setModuleKeywords(Arrays.asList(moduleKeywords));
	}

	public void setModuleLicense(Object moduleLicense) {
		_moduleLicense = moduleLicense;
	}

	public void setModuleName(Object moduleName) {
		_moduleName = moduleName;
	}

	public void setModuleRepository(Object moduleRepository) {
		_moduleRepository = moduleRepository;
	}

	public void setModuleVersion(Object moduleVersion) {
		_moduleVersion = moduleVersion;
	}

	public void setNpmEmailAddress(Object npmEmailAddress) {
		_npmEmailAddress = npmEmailAddress;
	}

	public void setNpmPassword(Object npmPassword) {
		_npmPassword = npmPassword;
	}

	public void setNpmUserName(Object npmUserName) {
		_npmUserName = npmUserName;
	}

	protected File createNpmrcFile() throws IOException {
		File npmrcFile = new File(getWorkingDir(), ".npmrc");

		List<String> npmrcContents = new ArrayList<>(2);

		npmrcContents.add("_auth = " + getNpmAuth());
		npmrcContents.add("email = " + getNpmEmailAddress());

		FileUtil.write(npmrcFile, npmrcContents);

		return npmrcFile;
	}

	protected File createPackageJsonFile() throws IOException {
		File packageJsonFile = new File(getWorkingDir(), "package.json");

		Map<String, Object> map = new HashMap<>();

		String author = getModuleAuthor();

		if (Validator.isNotNull(author)) {
			map.put("author", author);
		}

		String description = getModuleDescription();

		if (Validator.isNotNull(description)) {
			map.put("description", description);
		}

		List<String> keywords = getModuleKeywords();

		if (!keywords.isEmpty()) {
			map.put("keywords", keywords);
		}

		String license = getModuleLicense();

		if (Validator.isNotNull(license)) {
			map.put("license", license);
		}

		map.put("name", getModuleName());

		String repository = getModuleRepository();

		if (Validator.isNotNull(repository)) {
			map.put("repository", repository);
		}

		map.put("version", getModuleVersion());

		String json = JsonOutput.toJson(map);

		if (_logger.isInfoEnabled()) {
			_logger.info(json);
		}

		Files.write(
			packageJsonFile.toPath(), json.getBytes(StandardCharsets.UTF_8));

		return packageJsonFile;
	}

	protected List<Object> getCompleteArgs() {
		List<Object> completeArgs = new ArrayList<>();

		completeArgs.add("publish");

		completeArgs.addAll(getArgs());

		return completeArgs;
	}

	protected String getNpmAuth() {
		String auth = getNpmUserName() + ":" + getNpmPassword();

		Writable writable = EncodingGroovyMethods.encodeBase64(auth.getBytes());

		return writable.toString();
	}

	private static final Logger _logger = Logging.getLogger(
		PublishNodeModuleTask.class);

	private Object _moduleAuthor;
	private Object _moduleDescription;
	private final List<Object> _moduleKeywords = new ArrayList<>();
	private Object _moduleLicense;
	private Object _moduleName;
	private Object _moduleRepository;
	private Object _moduleVersion;
	private Object _npmEmailAddress;
	private Object _npmPassword;
	private Object _npmUserName;

}