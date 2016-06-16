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

package com.liferay.gradle.plugins.maven.plugin.builder;

import com.liferay.gradle.plugins.maven.plugin.builder.util.XMLUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.model.Type;

import java.io.File;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.JavaExecSpec;
import org.gradle.util.GUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Di Giorgi
 */
public class BuildPluginDescriptorTask extends DefaultTask {

	public BuildPluginDescriptorTask() {
		_configurationScopeMappings.put(
			JavaPlugin.COMPILE_CONFIGURATION_NAME,
			Conf2ScopeMappingContainer.COMPILE);
		_configurationScopeMappings.put(
			"provided", Conf2ScopeMappingContainer.PROVIDED);

		_pomRepositories.put(
			"liferay-public",
			"http://repository.liferay.com/nexus/content/groups/public");
	}

	@TaskAction
	public void buildPluginDescriptor() {
		Project project = getProject();

		File pomFile = project.file(System.currentTimeMillis() + ".xml");
		File preparedSourceDir = null;

		try {
			if (isUseSetterComments()) {
				preparedSourceDir = new File(
					getTemporaryDir(), "prepared-source");

				prepareSources(preparedSourceDir);
			}

			buildPomFile(pomFile, preparedSourceDir);

			buildPluginDescriptor(pomFile);

			readdForcedExclusions();
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
		finally {
			if (preparedSourceDir != null) {
				project.delete(preparedSourceDir);
			}

			project.delete(pomFile);
		}
	}

	public void configurationScopeMapping(
		String configurationName, String scope) {

		_configurationScopeMappings.put(configurationName, scope);
	}

	@SuppressWarnings("unchecked")
	public BuildPluginDescriptorTask forcedExclusions(
		Iterable<String> forcedExclusions) {

		GUtil.addToCollection(_forcedExclusions, forcedExclusions);

		return this;
	}

	public BuildPluginDescriptorTask forcedExclusions(
		String... forcedExclusions) {

		return forcedExclusions(Arrays.asList(forcedExclusions));
	}

	@InputDirectory
	public File getClassesDir() {
		return GradleUtil.toFile(getProject(), _classesDir);
	}

	public Map<String, String> getConfigurationScopeMappings() {
		return _configurationScopeMappings;
	}

	@Input
	public Set<String> getForcedExclusions() {
		return _forcedExclusions;
	}

	@Input
	public String getGoalPrefix() {
		return GradleUtil.toString(_goalPrefix);
	}

	@InputFiles
	public FileCollection getMavenEmbedderClasspath() {
		return _mavenEmbedderClasspath;
	}

	@Input
	public String getMavenEmbedderMainClassName() {
		return GradleUtil.toString(_mavenEmbedderMainClassName);
	}

	@Input
	public String getMavenPluginPluginVersion() {
		return GradleUtil.toString(_mavenPluginPluginVersion);
	}

	@OutputDirectory
	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	@Input
	public String getPomArtifactId() {
		return GradleUtil.toString(_pomArtifactId);
	}

	@Input
	public String getPomGroupId() {
		return GradleUtil.toString(_pomGroupId);
	}

	@Input
	public Map<String, Object> getPomRepositories() {
		return _pomRepositories;
	}

	@Input
	public String getPomVersion() {
		return GradleUtil.toString(_pomVersion);
	}

	@InputDirectory
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@Input
	public boolean isUseSetterComments() {
		return _useSetterComments;
	}

	public BuildPluginDescriptorTask pomRepositories(
		Map<String, ?> pomRepositories) {

		_pomRepositories.putAll(pomRepositories);

		return this;
	}

	public BuildPluginDescriptorTask pomRepository(String id, Object url) {
		_pomRepositories.put(id, url);

		return this;
	}

	public void setClassesDir(Object classesDir) {
		_classesDir = classesDir;
	}

	public void setForcedExclusions(Iterable<String> forcedExclusions) {
		_forcedExclusions.clear();

		forcedExclusions(forcedExclusions);
	}

	public void setForcedExclusions(String... forcedExclusions) {
		setForcedExclusions(Arrays.asList(forcedExclusions));
	}

	public void setGoalPrefix(Object goalPrefix) {
		_goalPrefix = goalPrefix;
	}

	public void setMavenEmbedderClasspath(
		FileCollection mavenEmbedderClasspath) {

		_mavenEmbedderClasspath = mavenEmbedderClasspath;
	}

	public void setMavenEmbedderMainClassName(
		Object mavenEmbedderMainClassName) {

		_mavenEmbedderMainClassName = mavenEmbedderMainClassName;
	}

	public void setMavenPluginPluginVersion(Object mavenPluginPluginVersion) {
		_mavenPluginPluginVersion = mavenPluginPluginVersion;
	}

	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	public void setPomArtifactId(Object pomArtifactId) {
		_pomArtifactId = pomArtifactId;
	}

	public void setPomGroupId(Object pomGroupId) {
		_pomGroupId = pomGroupId;
	}

	public void setPomRepositories(Map<String, ?> pomRepositories) {
		_pomRepositories.clear();

		pomRepositories(pomRepositories);
	}

	public void setPomVersion(Object pomVersion) {
		_pomVersion = pomVersion;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	public void setUseSetterComments(boolean useSetterComments) {
		_useSetterComments = useSetterComments;
	}

	protected void appendDependencyElements(
		Document document, Element dependenciesElement,
		String configurationName, String scope) {

		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration = configurationContainer.findByName(
			configurationName);

		if (configuration == null) {
			return;
		}

		Set<String> forcedExclusions = getForcedExclusions();

		ResolvedConfiguration resolvedConfiguration =
			configuration.getResolvedConfiguration();

		for (Dependency dependency : configuration.getDependencies()) {
			Element dependencyElement = document.createElement("dependency");

			dependenciesElement.appendChild(dependencyElement);

			final String dependencyGroup = dependency.getGroup();
			final String dependencyName = dependency.getName();

			XMLUtil.appendElement(
				document, dependencyElement, "groupId", dependencyGroup);
			XMLUtil.appendElement(
				document, dependencyElement, "artifactId", dependencyName);

			String dependencyVersion = dependency.getVersion();

			Set<ResolvedDependency> resolvedDependencies =
				resolvedConfiguration.getFirstLevelModuleDependencies(
					new Spec<Dependency>() {

						@Override
						public boolean isSatisfiedBy(Dependency dependency) {
							if (dependencyGroup.equals(dependency.getGroup()) &&
								dependencyName.equals(dependency.getName())) {

								return true;
							}

							return false;
						}

					});

			if (!resolvedDependencies.isEmpty()) {
				Iterator<ResolvedDependency> iterator =
					resolvedDependencies.iterator();

				ResolvedDependency resolvedDependency = iterator.next();

				dependencyVersion = resolvedDependency.getModuleVersion();
			}
			else if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to find resolved module version for " + dependency);
			}

			XMLUtil.appendElement(
				document, dependencyElement, "version", dependencyVersion);

			XMLUtil.appendElement(document, dependencyElement, "scope", scope);

			if (!forcedExclusions.isEmpty()) {
				Element exclusionsElement = document.createElement(
					"exclusions");

				dependencyElement.appendChild(exclusionsElement);

				for (String dependencyNotation : forcedExclusions) {
					appendDependencyExclusionElement(
						document, exclusionsElement, dependencyNotation);
				}
			}
		}
	}

	protected void appendDependencyExclusionElement(
		Document document, Element exclusionsElement,
		String dependencyNotation) {

		String[] tokens = parseDependencyNotation(dependencyNotation);

		String groupId = tokens[0];
		String artifactId = tokens[1];

		appendDependencyExclusionElement(
			document, exclusionsElement, groupId, artifactId);
	}

	protected void appendDependencyExclusionElement(
		Document document, Element exclusionsElement, String groupId,
		String artifactId) {

		Element exclusionElement = document.createElement("exclusion");

		exclusionsElement.appendChild(exclusionElement);

		XMLUtil.appendElement(
			document, exclusionElement, "artifactId", artifactId);
		XMLUtil.appendElement(document, exclusionElement, "groupId", groupId);
	}

	protected void appendRepositoryElement(
		Document document, Element repositoriesElement, String id, String url) {

		Element repositoryElement = document.createElement("repository");

		repositoriesElement.appendChild(repositoryElement);

		XMLUtil.appendElement(document, repositoryElement, "id", id);
		XMLUtil.appendElement(document, repositoryElement, "url", url);
	}

	protected void buildPluginDescriptor(final File pomFile) throws Exception {
		final Project project = getProject();

		project.javaexec(
			new Action<JavaExecSpec>() {

				@Override
				public void execute(JavaExecSpec javaExecSpec) {
					javaExecSpec.args("-B");

					javaExecSpec.args("-e");

					javaExecSpec.args("-f");
					javaExecSpec.args(project.relativePath(pomFile));

					javaExecSpec.args("-Dencoding=UTF-8");

					javaExecSpec.args("plugin:descriptor");

					javaExecSpec.setClasspath(getMavenEmbedderClasspath());
					javaExecSpec.setMain(getMavenEmbedderMainClassName());

					javaExecSpec.systemProperty(
						"maven.multiModuleProjectDirectory",
						project.getProjectDir());
				}

			});

		File dir = new File(getClassesDir(), "META-INF/maven");
		File outputDir = getOutputDir();

		project.delete(outputDir);

		Files.move(dir.toPath(), outputDir.toPath());
	}

	protected void buildPomFile(File pomFile, File sourceDir) throws Exception {
		Project project = getProject();

		if (sourceDir == null) {
			sourceDir = getSourceDir();
		}

		Document document = XMLUtil.createDocument();

		Element projectElement = document.createElementNS(
			"http://maven.apache.org/POM/4.0.0", "project");

		document.appendChild(projectElement);

		XMLUtil.appendElement(
			document, projectElement, "modelVersion", "4.0.0");
		XMLUtil.appendElement(
			document, projectElement, "groupId", getPomGroupId());
		XMLUtil.appendElement(
			document, projectElement, "artifactId", getPomArtifactId());
		XMLUtil.appendElement(
			document, projectElement, "version", getPomVersion());
		XMLUtil.appendElement(
			document, projectElement, "packaging", "maven-plugin");

		// Build

		Element buildElement = document.createElement("build");

		projectElement.appendChild(buildElement);

		XMLUtil.appendElement(
			document, buildElement, "outputDirectory",
			project.relativePath(getClassesDir()));
		XMLUtil.appendElement(
			document, buildElement, "sourceDirectory",
			project.relativePath(sourceDir));

		Element pluginsElement = document.createElement("plugins");

		buildElement.appendChild(pluginsElement);

		Element pluginElement = document.createElement("plugin");

		pluginsElement.appendChild(pluginElement);

		XMLUtil.appendElement(
			document, pluginElement, "groupId", "org.apache.maven.plugins");
		XMLUtil.appendElement(
			document, pluginElement, "artifactId", "maven-plugin-plugin");
		XMLUtil.appendElement(
			document, pluginElement, "version", getMavenPluginPluginVersion());

		String goalPrefix = getGoalPrefix();

		if (Validator.isNotNull(goalPrefix)) {
			Element configurationElement = document.createElement(
				"configuration");

			pluginElement.appendChild(configurationElement);

			XMLUtil.appendElement(
				document, configurationElement, "goalPrefix", goalPrefix);
		}

		// Dependencies

		Element dependenciesElement = document.createElement("dependencies");

		projectElement.appendChild(dependenciesElement);

		Map<String, String> pomConfigurationScopeMappings =
			getConfigurationScopeMappings();

		for (Map.Entry<String, String> entry :
				pomConfigurationScopeMappings.entrySet()) {

			String configurationName = entry.getKey();
			String scope = entry.getValue();

			appendDependencyElements(
				document, dependenciesElement, configurationName, scope);
		}

		// Repositories

		Map<String, Object> pomRepositories = getPomRepositories();

		if (!pomRepositories.isEmpty()) {
			Element repositoriesElement = document.createElement(
				"repositories");

			projectElement.appendChild(repositoriesElement);

			for (Map.Entry<String, Object> entry : pomRepositories.entrySet()) {
				String id = entry.getKey();
				String url = GradleUtil.toString(entry.getValue());

				appendRepositoryElement(document, repositoriesElement, id, url);
			}
		}

		XMLUtil.write(document, pomFile);
	}

	protected String getComments(JavaMethod javaMethod) {
		String code = javaMethod.getCodeBlock();

		int start = code.indexOf("/**");

		if (start < 0) {
			throw new GradleException("Unable to find comments start " + code);
		}

		int end = code.indexOf("*/", start);

		if (end < 0) {
			throw new GradleException("Unable to find comments end " + code);
		}

		return code.substring(start, end + 2);
	}

	protected String[] parseDependencyNotation(String dependencyNotation) {
		String[] tokens = dependencyNotation.split(":");

		if (tokens.length != 3) {
			throw new GradleException(
				"Unable to parse dependency notation " + dependencyNotation);
		}

		return tokens;
	}

	protected void prepareSource(JavaClass javaClass) throws Exception {
		StringBuilder sb = new StringBuilder();

		for (BeanProperty beanProperty : javaClass.getBeanProperties()) {
			JavaMethod javaMethod = beanProperty.getMutator();

			DocletTag parameterDocletTag = javaMethod.getTagByName("parameter");

			if (parameterDocletTag == null) {
				continue;
			}

			sb.append(getComments(javaMethod));
			sb.append('\n');
			sb.append("private ");

			Type type = beanProperty.getType();

			sb.append(type.getFullyQualifiedName());

			sb.append(' ');
			sb.append(beanProperty.getName());
			sb.append(';');
			sb.append('\n');
		}

		if (sb.length() == 0) {
			return;
		}

		JavaSource javaSource = javaClass.getSource();

		String code = javaSource.getCodeBlock();

		int pos = code.lastIndexOf('}');

		code = code.substring(0, pos) + sb.toString() + code.substring(pos);

		URL url = javaSource.getURL();

		Files.write(
			Paths.get(url.toURI()), code.getBytes(StandardCharsets.UTF_8));
	}

	protected void prepareSources(final File preparedSourceDir)
		throws Exception {

		Project project = getProject();

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.from(getSourceDir());
					copySpec.include("**/*Mojo.java");
					copySpec.into(preparedSourceDir);
				}

			});

		JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		javaDocBuilder.addSourceTree(preparedSourceDir);

		for (JavaClass javaClass : javaDocBuilder.getClasses()) {
			prepareSource(javaClass);
		}
	}

	protected void readdForcedExclusions() throws Exception {
		Set<String> forcedExclusions = getForcedExclusions();

		if (forcedExclusions.isEmpty()) {
			return;
		}

		File file = new File(getOutputDir(), "plugin.xml");

		Path path = file.toPath();

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		int pos = content.lastIndexOf("</dependencies>");

		if (pos == -1) {
			if (_logger.isWarnEnabled()) {
				_logger.warn("Unable to readd forced exclusions");
			}

			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(content, 0, pos - 1);

		for (String dependencyNotation : forcedExclusions) {
			String[] tokens = parseDependencyNotation(dependencyNotation);

			String groupId = tokens[0];
			String artifactId = tokens[1];
			String version = tokens[2];

			sb.append("<dependency>");

			sb.append("<groupId>");
			sb.append(groupId);
			sb.append("</groupId>");

			sb.append("<artifactId>");
			sb.append(artifactId);
			sb.append("</artifactId>");

			sb.append("<type>jar</type>");

			sb.append("<version>");
			sb.append(version);
			sb.append("</version>");

			sb.append("</dependency>");
		}

		sb.append(content, pos, content.length());

		content = sb.toString();

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	private static final Logger _logger = Logging.getLogger(
		BuildPluginDescriptorTask.class);

	private Object _classesDir;
	private final Map<String, String> _configurationScopeMappings =
		new HashMap<>();
	private final Set<String> _forcedExclusions = new HashSet<>();
	private Object _goalPrefix;
	private FileCollection _mavenEmbedderClasspath;
	private Object _mavenEmbedderMainClassName =
		"org.apache.maven.cli.MavenCli";
	private Object _mavenPluginPluginVersion = "3.4";
	private Object _outputDir;
	private Object _pomArtifactId;
	private Object _pomGroupId;
	private final Map<String, Object> _pomRepositories = new LinkedHashMap<>();
	private Object _pomVersion;
	private Object _sourceDir;
	private boolean _useSetterComments = true;

}