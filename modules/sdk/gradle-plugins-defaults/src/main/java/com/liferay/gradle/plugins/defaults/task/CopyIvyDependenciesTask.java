/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.task;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.StringUtil;
import com.liferay.gradle.plugins.defaults.internal.util.XMLUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.CopySpec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class CopyIvyDependenciesTask extends Copy {

	@SuppressWarnings("serial")
	public CopyIvyDependenciesTask() {
		_configuration = _createConfiguration();

		doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					CopyIvyDependenciesTask copyIvyDependenciesTask =
						(CopyIvyDependenciesTask)task;

					copyIvyDependenciesTask.writeChecksumFile();
				}

			});

		from(
			_configuration,
			new Closure<Void>(getProject()) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.rename(
						new RenameDependencyClosure(
							getProject(), _configuration.getName()));
				}

			});
	}

	@InputFiles
	public Configuration getConfiguration() {
		return _configuration;
	}

	@Internal
	public Closure<Map<String, Object>> getDependencyTransformClosure() {
		return _dependencyTransformClosure;
	}

	@InputFile
	public File getInputFile() {
		return GradleUtil.toFile(getProject(), _inputFile);
	}

	public void setDependencyTransformClosure(
		Closure<Map<String, Object>> dependencyVersionClosure) {

		_dependencyTransformClosure = dependencyVersionClosure;
	}

	public void setInputFile(Object inputFile) {
		_inputFile = inputFile;
	}

	@SuppressWarnings("serial")
	public void writeChecksumFile() {
		Project project = getProject();

		project.ant(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(AntBuilder antBuilder) {
					antBuilder.invokeMethod(
						"checksum",
						Collections.singletonMap("file", getInputFile()));
				}

			});
	}

	private void _addDependencies(DependencySet dependencySet)
		throws Exception {

		Project project = getProject();

		DependencyHandler dependencyHandler = project.getDependencies();

		DocumentBuilder documentBuilder = XMLUtil.getDocumentBuilder();

		Document document = documentBuilder.parse(getInputFile());

		NodeList dependencyNodeList = document.getElementsByTagName(
			"dependency");

		for (int i = 0; i < dependencyNodeList.getLength(); i++) {
			Element dependencyElement = (Element)dependencyNodeList.item(i);

			String conf = dependencyElement.getAttribute("conf");

			if (Validator.isNotNull(conf)) {
				continue;
			}

			Map<String, Object> dependencyNotation = new HashMap<>();

			dependencyNotation.put(
				"group", dependencyElement.getAttribute("org"));
			dependencyNotation.put(
				"name", dependencyElement.getAttribute("name"));
			dependencyNotation.put(
				"version", dependencyElement.getAttribute("rev"));

			String transitive = dependencyElement.getAttribute("transitive");

			if (Validator.isNotNull(transitive)) {
				dependencyNotation.put(
					"transitive", Boolean.parseBoolean(transitive));
			}

			dependencyNotation = _dependencyTransformClosure.call(
				dependencyNotation);

			ModuleDependency moduleDependency =
				(ModuleDependency)dependencyHandler.create(dependencyNotation);

			NodeList excludeNodeList = dependencyElement.getElementsByTagName(
				"exclude");

			for (int j = 0; j < excludeNodeList.getLength(); j++) {
				Element excludeElement = (Element)excludeNodeList.item(j);

				Map<String, String> args = new HashMap<>();

				String group = excludeElement.getAttribute("org");

				if (Validator.isNotNull(group)) {
					args.put("group", group);
				}

				String module = excludeElement.getAttribute("module");

				if (Validator.isNotNull(module)) {
					args.put("module", module);
				}

				moduleDependency.exclude(args);
			}

			dependencySet.add(moduleDependency);
		}
	}

	private Configuration _createConfiguration() {
		Configuration configuration = GradleUtil.addConfiguration(
			getProject(), "ivy" + StringUtil.capitalize(getName()));

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					try {
						_addDependencies(dependencySet);
					}
					catch (IOException ioException) {
						throw new UncheckedIOException(ioException);
					}
					catch (Exception exception) {
						throw new GradleException(
							exception.getMessage(), exception);
					}
				}

			});

		configuration.setDescription("Configures Ivy dependencies for " + this);
		configuration.setVisible(false);

		return configuration;
	}

	private final Configuration _configuration;

	@SuppressWarnings("unchecked")
	private Closure<Map<String, Object>> _dependencyTransformClosure =
		Closure.IDENTITY;

	private Object _inputFile;

}