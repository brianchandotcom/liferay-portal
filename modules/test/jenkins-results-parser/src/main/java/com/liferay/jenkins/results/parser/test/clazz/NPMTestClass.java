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

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class NPMTestClass extends BaseTestClass {

	public List<TestClassMethod> getJSTestClassMethods() {
		return _jsTestClassMethods;
	}

	protected NPMTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile);

		addTestClassMethod(batchTestClassGroup.getBatchName());

		_gitWorkingDirectory =
			batchTestClassGroup.getPortalGitWorkingDirectory();

		_moduleFile = testClassFile;

		initJSTestClassMethods();
	}

	protected NPMTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		addTestClassMethod(batchTestClassGroup.getBatchName());

		_gitWorkingDirectory =
			batchTestClassGroup.getPortalGitWorkingDirectory();

		_moduleFile = new File(jsonObject.getString("test_class_file"));
	}

	protected void initJSTestClassMethods() {
		List<File> jsFiles = JenkinsResultsParserUtil.findFiles(
			_moduleFile, ".*\\.(js|ts|tsx)");

		String workingDirectoryPath = JenkinsResultsParserUtil.getCanonicalPath(
			_gitWorkingDirectory.getWorkingDirectory());

		for (File jsFile : jsFiles) {
			try {
				String jsFileRelativePath =
					JenkinsResultsParserUtil.getCanonicalPath(jsFile);

				jsFileRelativePath = jsFileRelativePath.replace(
					workingDirectoryPath, "");

				String jsFileContent = JenkinsResultsParserUtil.read(jsFile);

				Matcher matcher = _itPattern.matcher(jsFileContent);

				while (matcher.find()) {
					String methodName = matcher.group("description");

					String xit = matcher.group("xit");

					boolean methodIgnored = false;

					if (xit != null) {
						methodIgnored = true;
					}

					_jsTestClassMethods.add(
						TestClassFactory.newTestClassMethod(
							methodIgnored,
							jsFileRelativePath + _TOKEN_CLASS_METHOD_SEPARATOR +
								methodName,
							this));
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	private static final String _TOKEN_CLASS_METHOD_SEPARATOR = "::";

	private static final Pattern _itPattern = Pattern.compile(
		"\\s+(?<xit>x)?it\\s*\\(\\s*\\'(?<description>[\\s\\S]*?)\\'");

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final List<TestClassMethod> _jsTestClassMethods = new ArrayList<>();
	private final File _moduleFile;

}