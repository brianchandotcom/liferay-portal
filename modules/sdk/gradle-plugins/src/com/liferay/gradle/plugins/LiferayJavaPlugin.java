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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.lang.builder.BuildLangTask;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.tasks.BuildCssTask;
import com.liferay.gradle.plugins.tasks.InitGradleTask;
import com.liferay.gradle.plugins.tld.formatter.TLDFormatterPlugin;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.BuildWSDLTask;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.plugins.xml.formatter.FormatXMLTask;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.plugins.xsd.builder.BuildXSDTask;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.util.ConfigObject;

import java.io.File;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayJavaPlugin implements Plugin<Project> {

	public static final String BUILD_CSS_TASK_NAME = "buildCss";

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String FORMAT_WSDL_TASK_NAME = "formatWSDL";

	public static final String FORMAT_XSD_TASK_NAME = "formatXSD";

	public static final String INIT_GRADLE_TASK_NAME = "initGradle";

	public static final String PORTAL_WEB_CONFIGURATION_NAME = "portalWeb";

	@Override
	public void apply(Project project) {
		final LiferayExtension liferayExtension = addLiferayExtension(project);

		applyPlugins(project);

		configureConfigurations(project, liferayExtension);
		configureDependencies(project, liferayExtension);
		configureProperties(project);
		configureRepositories(project);
		configureSourceSets(project);

		addConfigurations(project);
		addTasks(project, liferayExtension);

		applyConfigScripts(project);

		configureTaskBuildService(project);
		configureTaskBuildWSDD(project);
		configureTaskBuildWSDL(project);
		configureTaskBuildXSD(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureLiferayExtension(project, liferayExtension);
					configureVersion(project, liferayExtension);

					configureTasks(project, liferayExtension);
				}

			});
	}

	protected Configuration addConfigurationPortalWeb(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_WEB_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures portal-web for compiling themes and CSS files.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesPortalWeb(project);
				}

			});

		return configuration;
	}

	protected void addConfigurations(Project project) {
		addConfigurationPortalWeb(project);
	}

	protected void addDependenciesPortalWeb(Project project) {
		GradleUtil.addDependency(
			project, PORTAL_WEB_CONFIGURATION_NAME, "com.liferay.portal",
			"portal-web", "default");
	}

	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayExtension.class);
	}

	protected BuildCssTask addTaskBuildCss(Project project) {
		BuildCssTask buildCssTask = GradleUtil.addTask(
			project, BUILD_CSS_TASK_NAME, BuildCssTask.class);

		buildCssTask.setDescription("Compiles CSS files.");
		buildCssTask.setGroup(BasePlugin.BUILD_GROUP);

		return buildCssTask;
	}

	protected Copy addTaskDeploy(Project project) {
		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		copy.setDescription("Assembles the project and deploys it to Liferay.");

		return copy;
	}

	protected FormatXMLTask addTaskFormatWSDL(Project project) {
		FormatXMLTask formatXMLTask = GradleUtil.addTask(
			project, FORMAT_WSDL_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format WSDL files.");

		return formatXMLTask;
	}

	protected FormatXMLTask addTaskFormatXSD(Project project) {
		FormatXMLTask formatXMLTask = GradleUtil.addTask(
			project, FORMAT_XSD_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format XSD files.");

		return formatXMLTask;
	}

	protected InitGradleTask addTaskInitGradle(Project project) {
		InitGradleTask initGradleTask = GradleUtil.addTask(
			project, INIT_GRADLE_TASK_NAME, InitGradleTask.class);

		initGradleTask.setDescription(
			"Initializes build.gradle by migrating information from legacy " +
				"files.");

		return initGradleTask;
	}

	protected void addTasks(
		Project project, LiferayExtension liferayExtension) {

		addTaskBuildCss(project);
		addTaskDeploy(project);
		addTaskFormatWSDL(project);
		addTaskFormatXSD(project);
		addTaskInitGradle(project);
		addTaskWar(project);
	}

	protected Task addTaskWar(Project project) {
		Task task = project.task(WarPlugin.WAR_TASK_NAME);

		task.dependsOn(JavaPlugin.JAR_TASK_NAME);

		task.setDescription("Alias for 'jar'.");
		task.setGroup(BasePlugin.BUILD_GROUP);

		return task;
	}

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(project, "config-liferay.gradle", project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		GradleUtil.applyPlugin(project, JavadocFormatterPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, ServiceBuilderPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, WSDDBuilderPlugin.class);
		GradleUtil.applyPlugin(project, WSDLBuilderPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);
		GradleUtil.applyPlugin(project, XSDBuilderPlugin.class);
	}

	protected void configureConfigurations(
		Project project, final LiferayExtension liferayExtension) {

		Action<Configuration> action = new Action<Configuration>() {

			@Override
			public void execute(Configuration configuration) {
				ResolutionStrategy resolutionStrategy =
					configuration.getResolutionStrategy();

				resolutionStrategy.eachDependency(
					new Action<DependencyResolveDetails>() {

						@Override
						public void execute(
							DependencyResolveDetails dependencyResolveDetails) {
								ModuleVersionSelector moduleVersionSelector =
									dependencyResolveDetails.getRequested();

								String group = moduleVersionSelector.getGroup();
								String version =
									moduleVersionSelector.getVersion();

								if (group.equals("com.liferay.portal") &&
									version.equals("default")) {

									dependencyResolveDetails.useVersion(
										liferayExtension.getPortalVersion());
								}
						}

					});
			}

		};

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		configurationContainer.all(action);
	}

	protected void configureDependencies(
		Project project, LiferayExtension liferayExtension) {

		configureDependenciesCompile(project, liferayExtension);
	}

	protected void configureDependenciesCompile(
		Project project, LiferayExtension liferayExtension) {

		for (String dependencyNotation : COMPILE_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	protected void configureLiferayExtension(
		Project project, LiferayExtension liferayExtension) {

		File appServerParentDir = liferayExtension.getAppServerParentDir();
		String appServerType = liferayExtension.getAppServerType();

		if ((appServerParentDir == null) || Validator.isNull(appServerType)) {
			return;
		}

		File appServerDir = liferayExtension.getAppServerDir();

		if (appServerDir == null) {
			String appServerName = getAppServerProperty(
				liferayExtension, appServerType, "name");
			String appServerVersion = getAppServerProperty(
				liferayExtension, appServerType, "version");

			appServerDir = new File(
				appServerParentDir, appServerName + "-" + appServerVersion);

			liferayExtension.setAppServerDir(appServerDir);
		}

		if (liferayExtension.getAppServerDeployDir() == null) {
			File appServerDeployDir = getAppServerDir(
				liferayExtension, appServerDir, "deployDirName");

			liferayExtension.setAppServerDeployDir(appServerDeployDir);
		}

		if (liferayExtension.getAppServerLibGlobalDir() == null) {
			File appServerLibGlobalDir = getAppServerDir(
				liferayExtension, appServerDir, "libGlobalDirName");

			liferayExtension.setAppServerLibGlobalDir(appServerLibGlobalDir);
		}

		if (liferayExtension.getAppServerPortalDir() == null) {
			File appServerPortalDir = getAppServerDir(
				liferayExtension, appServerDir, "portalDirName");

			liferayExtension.setAppServerPortalDir(appServerPortalDir);
		}

		if (liferayExtension.getDeployDir() == null) {
			File deployDir = new File(appServerParentDir, "deploy");

			liferayExtension.setDeployDir(deployDir);
		}
	}

	protected void configureProperties(Project project) {
	}

	protected void configureRepositories(Project project) {
		RepositoryHandler repositoryHandler = project.getRepositories();

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(_REPOSITORY_URL);
				}

			});
	}

	protected void configureSourceSetMain(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		File srcDir = project.file("src");

		Set<File> srcDirs = Collections.singleton(srcDir);

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureSourceSets(Project project) {
		configureSourceSetMain(project);
	}

	protected void configureTaskBuildCss(
		Project project, LiferayExtension liferayExtension) {

		BuildCssTask buildCssTask = (BuildCssTask)GradleUtil.getTask(
			project, BUILD_CSS_TASK_NAME);

		configureTaskBuildCssPortalWebFile(buildCssTask);
		configureTaskBuildCssRootDirs(buildCssTask);
		configureTaskBuildCssTmpDir(buildCssTask, liferayExtension);
	}

	protected void configureTaskBuildCssPortalWebFile(
		BuildCssTask buildCssTask) {

		if (buildCssTask.getPortalWebFile() != null) {
			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			buildCssTask.getProject(), PORTAL_WEB_CONFIGURATION_NAME);

		buildCssTask.setPortalWebFile(configuration.getSingleFile());
	}

	protected void configureTaskBuildCssRootDirs(BuildCssTask buildCssTask) {
		FileCollection rootDirs = buildCssTask.getRootDirs();

		if ((rootDirs != null) && !rootDirs.isEmpty()) {
			return;
		}

		Project project = buildCssTask.getProject();

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getResources();

		buildCssTask.setRootDirs(sourceDirectorySet.getSrcDirs());
	}

	protected void configureTaskBuildCssTmpDir(
		BuildCssTask buildCssTask, LiferayExtension liferayExtension) {

		if (buildCssTask.getTmpDir() == null) {
			File tmpDir = new File(liferayExtension.getTmpDir(), "portal-web");

			buildCssTask.setTmpDir(tmpDir);
		}
	}

	protected void configureTaskBuildLang(Project project) {
		BuildLangTask buildLangTask = (BuildLangTask)GradleUtil.getTask(
			project, LangBuilderPlugin.BUILD_LANG_TASK_NAME);

		configureTaskBuildLangLangDirName(buildLangTask);
	}

	protected void configureTaskBuildLangLangDirName(
		BuildLangTask buildLangTask) {

		Project project = buildLangTask.getProject();

		File langDir = new File(getResourcesDir(project), "content");

		buildLangTask.setLangDirName(project.relativePath(langDir));
	}

	protected void configureTaskBuildService(Project project) {
		BuildServiceTask buildServiceTask =
			(BuildServiceTask)GradleUtil.getTask(
				project, ServiceBuilderPlugin.BUILD_SERVICE_TASK_NAME);

		configureTaskBuildServiceApiDirName(buildServiceTask);
		configureTaskBuildServiceAutoNamespaceTables(buildServiceTask);
		configureTaskBuildServiceBeanLocatorUtil(buildServiceTask);
		configureTaskBuildServiceHbmFileName(buildServiceTask);
		configureTaskBuildServiceImplDirName(buildServiceTask);
		configureTaskBuildServiceInputFileName(buildServiceTask);
		configureTaskBuildServiceModelHintsFileName(buildServiceTask);
		configureTaskBuildServiceOsgiModule(buildServiceTask);
		configureTaskBuildServicePluginName(buildServiceTask);
		configureTaskBuildServicePropsUtil(buildServiceTask);
		configureTaskBuildServiceRemotingFileName(buildServiceTask);
		configureTaskBuildServiceResourcesDirName(buildServiceTask);
		configureTaskBuildServiceSpringFileName(buildServiceTask);
		configureTaskBuildServiceSpringNamespaces(buildServiceTask);
		configureTaskBuildServiceSqlDirName(buildServiceTask);
		configureTaskBuildServiceSqlFileName(buildServiceTask);
		configureTaskBuildServiceTestDirName(buildServiceTask);
	}

	protected void configureTaskBuildServiceApiDirName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File apiDir = new File(getServiceBaseDir(project), "service");

		buildServiceTask.setApiDirName(project.relativePath(apiDir));
	}

	protected void configureTaskBuildServiceAutoNamespaceTables(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setAutoNamespaceTables(true);
	}

	protected void configureTaskBuildServiceBeanLocatorUtil(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setBeanLocatorUtil(
			"com.liferay.util.bean.PortletBeanLocatorUtil");
	}

	protected void configureTaskBuildServiceHbmFileName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File hbmFile = new File(
			getResourcesDir(project), "META-INF/portlet-hbm.xml");

		buildServiceTask.setHbmFileName(project.relativePath(hbmFile));
	}

	protected void configureTaskBuildServiceImplDirName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File implDir = getJavaDir(project);

		buildServiceTask.setImplDirName(project.relativePath(implDir));
	}

	protected void configureTaskBuildServiceInputFileName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File inputFile = new File(getServiceBaseDir(project), "service.xml");

		buildServiceTask.setInputFileName(project.relativePath(inputFile));
	}

	protected void configureTaskBuildServiceModelHintsFileName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File modelHintsFile = new File(
			getResourcesDir(project), "META-INF/portlet-model-hints.xml");

		buildServiceTask.setModelHintsFileName(
			project.relativePath(modelHintsFile));
	}

	protected void configureTaskBuildServiceOsgiModule(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setOsgiModule(false);
	}

	protected void configureTaskBuildServicePluginName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		buildServiceTask.setPluginName(project.getName());
	}

	protected void configureTaskBuildServicePropsUtil(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setPropsUtil("com.liferay.util.service.ServiceProps");
	}

	protected void configureTaskBuildServiceRemotingFileName(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setRemotingFileName("");
	}

	protected void configureTaskBuildServiceResourcesDirName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File resourcesDir = getResourcesDir(project);

		buildServiceTask.setResourcesDirName(
			project.relativePath(resourcesDir));
	}

	protected void configureTaskBuildServiceSpringFileName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File springFile = new File(
			getResourcesDir(project), "META-INF/portlet-spring.xml");

		buildServiceTask.setSpringFileName(project.relativePath(springFile));
	}

	protected void configureTaskBuildServiceSpringNamespaces(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setSpringNamespaces(new String[] {"beans"});
	}

	protected void configureTaskBuildServiceSqlDirName(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		File sqlDir = new File(getServiceBaseDir(project), "sql");

		buildServiceTask.setSqlDirName(project.relativePath(sqlDir));
	}

	protected void configureTaskBuildServiceSqlFileName(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setSqlFileName("tables.sql");
	}

	protected void configureTaskBuildServiceTestDirName(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setTestDirName("");
	}

	protected void configureTaskBuildWSDD(Project project) {
		BuildWSDDTask buildWSDDTask = (BuildWSDDTask)GradleUtil.getTask(
			project, WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME);

		configureTaskBuildWSDDOutputDirName(buildWSDDTask);
	}

	protected void configureTaskBuildWSDDOutputDirName(
		BuildWSDDTask buildWSDDTask) {

		Project project = buildWSDDTask.getProject();

		File outputDir = getJavaDir(project);

		buildWSDDTask.setOutputDirName(project.relativePath(outputDir));
	}

	protected void configureTaskBuildWSDL(Project project) {
		BuildWSDLTask buildWSDLTask = (BuildWSDLTask)GradleUtil.getTask(
			project, WSDLBuilderPlugin.BUILD_WSDL_TASK_NAME);

		configureTaskBuildWSDLDestinationDir(buildWSDLTask);
		configureTaskBuildWSDLInputDir(buildWSDLTask);
	}

	protected void configureTaskBuildWSDLDestinationDir(
		BuildWSDLTask buildWSDLTask) {

		File destinationDir = buildWSDLTask.getDestinationDir();

		if (!destinationDir.exists()) {
			buildWSDLTask.setDestinationDir(
				getLibDir(buildWSDLTask.getProject()));
		}
	}

	protected void configureTaskBuildWSDLInputDir(BuildWSDLTask buildWSDLTask) {
		File inputDir = buildWSDLTask.getInputDir();

		if (!inputDir.exists()) {
			buildWSDLTask.setInputDir("wsdl");
		}
	}

	protected void configureTaskBuildXSD(Project project) {
		BuildXSDTask buildXSDTask = (BuildXSDTask)GradleUtil.getTask(
			project, XSDBuilderPlugin.BUILD_XSD_TASK_NAME);

		configureTaskBuildXSDDestinationDir(buildXSDTask);
		configureTaskBuildXSDInputDir(buildXSDTask);
	}

	protected void configureTaskBuildXSDDestinationDir(
		BuildXSDTask buildXSDTask) {

		buildXSDTask.setDestinationDir(getLibDir(buildXSDTask.getProject()));
	}

	protected void configureTaskBuildXSDInputDir(BuildXSDTask buildXSDTask) {
		Project project = buildXSDTask.getProject();

		File inputDir = project.file("xsd");

		buildXSDTask.setInputDir(inputDir);
	}

	protected void configureTaskClasses(Project project) {
		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		configureTaskClassesDependsOn(classesTask);
	}

	protected void configureTaskClassesDependsOn(Task classesTask) {
		classesTask.dependsOn(BUILD_CSS_TASK_NAME);
	}

	protected void configureTaskClean(Project project) {
		Task cleanTask = GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		configureTaskCleanDependsOn(cleanTask);
	}

	protected void configureTaskCleanDependsOn(Task cleanTask) {
		Project project = cleanTask.getProject();

		for (Task task : project.getTasks()) {
			String taskName =
				BasePlugin.CLEAN_TASK_NAME +
					StringUtil.capitalize(task.getName());

			cleanTask.dependsOn(taskName);
		}

		Configuration compileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

		cleanTask.dependsOn(
			compileConfiguration.getTaskDependencyFromProjectDependency(
				true, BasePlugin.CLEAN_TASK_NAME));
	}

	protected void configureTaskDeploy(
		Project project, LiferayExtension liferayExtension) {

		Copy copy = (Copy)GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		configureTaskDeployFrom(copy);
		configureTaskDeployInto(copy, liferayExtension);
	}

	protected void configureTaskDeployFrom(Copy deployTask) {
		Jar jar = (Jar)GradleUtil.getTask(
			deployTask.getProject(), JavaPlugin.JAR_TASK_NAME);

		deployTask.from(jar.getOutputs());
	}

	protected void configureTaskDeployInto(
		Copy deployTask, LiferayExtension liferayExtension) {

		deployTask.into(liferayExtension.getDeployDir());
	}

	protected void configureTaskFormatWSDL(Project project) {
		FormatXMLTask formatXMLTask = (FormatXMLTask)GradleUtil.getTask(
			project, FORMAT_WSDL_TASK_NAME);

		configureTaskFormatWSDLSource(formatXMLTask);
	}

	protected void configureTaskFormatWSDLSource(FormatXMLTask formatXMLTask) {
		FileTree fileTree = formatXMLTask.getSource();

		if (!fileTree.isEmpty()) {
			return;
		}

		BuildWSDLTask buildWSDLTask = (BuildWSDLTask)GradleUtil.getTask(
			formatXMLTask.getProject(), WSDLBuilderPlugin.BUILD_WSDL_TASK_NAME);

		formatXMLTask.setSource(buildWSDLTask.getInputDir());
		formatXMLTask.include("**/*.wsdl");
	}

	protected void configureTaskFormatXSD(Project project) {
		FormatXMLTask formatXMLTask = (FormatXMLTask)GradleUtil.getTask(
			project, FORMAT_XSD_TASK_NAME);

		configureTaskFormatXSDSource(formatXMLTask);
	}

	protected void configureTaskFormatXSDSource(FormatXMLTask formatXMLTask) {
		FileTree fileTree = formatXMLTask.getSource();

		if (!fileTree.isEmpty()) {
			return;
		}

		BuildXSDTask buildXSDTask = (BuildXSDTask)GradleUtil.getTask(
			formatXMLTask.getProject(), XSDBuilderPlugin.BUILD_XSD_TASK_NAME);

		formatXMLTask.setSource(buildXSDTask.getInputDir());
		formatXMLTask.include("**/*.xsd");
	}

	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		configureTaskBuildCss(project, liferayExtension);
		configureTaskBuildLang(project);
		configureTaskClasses(project);
		configureTaskClean(project);
		configureTaskDeploy(project, liferayExtension);
		configureTaskFormatWSDL(project);
		configureTaskFormatXSD(project);
	}

	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		project.setVersion(
			liferayExtension.getVersionPrefix() + "." + project.getVersion());
	}

	protected File getAppServerDir(
		LiferayExtension liferayExtension, File appServerDir,
		String dirNameKey) {

		String dirName = getAppServerProperty(
			liferayExtension, liferayExtension.getAppServerType(), dirNameKey);

		return new File(appServerDir, dirName);
	}

	protected String getAppServerProperty(
		LiferayExtension liferayExtension, String appServerType, String key) {

		ConfigObject appServers = liferayExtension.getAppServers();

		Map<String, String> appServerProperties =
			(Map<String, String>)appServers.getProperty(appServerType);

		String value = appServerProperties.get(key);

		if (Validator.isNull(value)) {
			throw new GradleException(
				"Unable to get property " + key + " for " + appServerType);
		}

		return value;
	}

	protected File getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		Set<File> srcDirs = javaSourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	protected File getLibDir(Project project) {
		return project.file("lib");
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		Set<File> srcDirs = resourcesSourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	protected File getServiceBaseDir(Project project) {
		return project.getProjectDir();
	}

	protected static final String[] COMPILE_DEPENDENCY_NOTATIONS = {
		"biz.aQute.bnd:biz.aQute.bnd:2.4.1",
		"com.liferay.portal:portal-service:default",
		"com.liferay.portal:util-bridges:default",
		"com.liferay.portal:util-java:default",
		"com.liferay.portal:util-taglib:default",
		"commons-logging:commons-logging:1.1.1", "hsqldb:hsqldb:1.8.0.7",
		"javax.activation:activation:1.1", "javax.ccpp:ccpp:1.0",
		"javax.jms:jms:1.1", "javax.mail:mail:1.4",
		"javax.portlet:portlet-api:2.0", "javax.servlet.jsp:jsp-api:2.1",
		"javax.servlet:javax.servlet-api:3.0.1", "log4j:log4j:1.2.16",
		"mysql:mysql-connector-java:5.1.23", "net.sf:jargs:1.0",
		"net.sourceforge.jtds:jtds:1.2.6",
		"org.eclipse.persistence:javax.persistence:2.0.0",
		"postgresql:postgresql:9.2-1002.jdbc4"
	};

	private static final String _REPOSITORY_URL =
		"http://cdn.repository.liferay.com/nexus/content/groups/public";

}