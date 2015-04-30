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
import com.liferay.gradle.plugins.tasks.BuildCssTask;
import com.liferay.gradle.plugins.tasks.BuildXsdTask;
import com.liferay.gradle.plugins.tasks.FormatSourceTask;
import com.liferay.gradle.plugins.tasks.InitGradleTask;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderExtension;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.util.ConfigObject;

import java.io.File;

import java.util.Collections;
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
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayJavaPlugin implements Plugin<Project> {

	public static final String BUILD_CSS_TASK_NAME = "buildCss";

	public static final String BUILD_WSDL_TASK_NAME = "buildWsdl";

	public static final String BUILD_XSD_TASK_NAME = "buildXsd";

	public static final String FORMAT_SOURCE_TASK_NAME = "formatSource";

	public static final String INIT_GRADLE_TASK_NAME = "initGradle";

	public static final String PORTAL_WEB_CONFIGURATION_NAME = "portalWeb";

	@Override
	public void apply(Project project) {
		final LiferayExtension liferayExtension = addLiferayExtension(project);

		applyPlugins(project);

		configureConfigurations(project, liferayExtension);
		configureDependencies(project, liferayExtension);
		configureRepositories(project);
		configureSourceSets(project);
		configureWSDLBuilderExtension(project);

		addConfigurations(project);
		addTasks(project, liferayExtension);

		applyConfigScripts(project);

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

	protected BuildXsdTask addTaskBuildXsd(Project project) {
		BuildXsdTask buildXsdTask = GradleUtil.addTask(
			project, BUILD_XSD_TASK_NAME, BuildXsdTask.class);

		buildXsdTask.setDescription("Generates XMLBeans bindings.");
		buildXsdTask.setGroup(BasePlugin.BUILD_GROUP);

		return buildXsdTask;
	}

	protected FormatSourceTask addTaskFormatSource(Project project) {
		FormatSourceTask formatSourceTask = GradleUtil.addTask(
			project, FORMAT_SOURCE_TASK_NAME, FormatSourceTask.class);

		formatSourceTask.setDescription(
			"Runs Liferay Source Formatter to format files.");

		return formatSourceTask;
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
		addTaskBuildXsd(project);
		addTaskFormatSource(project);
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
		GradleUtil.applyPlugin(project, WSDLBuilderPlugin.class);
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

	protected void configureTaskBuildXsd(Project project) {
		BuildXsdTask buildXsdTask = (BuildXsdTask)GradleUtil.getTask(
			project, BUILD_XSD_TASK_NAME);

		configureTaskBuildXsdDestinationDir(buildXsdTask);
		configureTaskBuildXsdRootDir(buildXsdTask);

		buildXsdTask.addTasks();

		TaskOutputs taskOutputs = buildXsdTask.getOutputs();

		GradleUtil.addDependency(
			buildXsdTask.getProject(), JavaPlugin.COMPILE_CONFIGURATION_NAME,
			taskOutputs.getFiles());
	}

	protected void configureTaskBuildXsdDestinationDir(
		BuildXsdTask buildXsdTask) {

		File destinationDir = buildXsdTask.getDestinationDir();

		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			buildXsdTask.getProject(), BasePluginConvention.class);

		if ((destinationDir == null) ||
			destinationDir.equals(basePluginConvention.getDistsDir())) {

			buildXsdTask.setDestinationDir(
				getLibDir(buildXsdTask.getProject()));
		}
	}

	protected void configureTaskBuildXsdRootDir(BuildXsdTask buildXsdTask) {
		if (buildXsdTask.getRootDir() != null) {
			return;
		}

		Project project = buildXsdTask.getProject();

		File rootDir = project.file("xsd");

		buildXsdTask.setRootDir(rootDir);
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

	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		configureTaskBuildCss(project, liferayExtension);
		configureTaskBuildXsd(project);
		configureTaskClasses(project);
		configureTaskClean(project);
	}

	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		project.setVersion(
			liferayExtension.getVersionPrefix() + "." + project.getVersion());
	}

	protected void configureWSDLBuilderExtension(Project project) {
		WSDLBuilderExtension wsdlBuilderExtension = GradleUtil.getExtension(
			project, WSDLBuilderExtension.class);

		configureWSDLBuilderExtensionDestinationDir(
			project, wsdlBuilderExtension);
		configureWSDLBuilderExtensionWSDLDir(project, wsdlBuilderExtension);
	}

	protected void configureWSDLBuilderExtensionDestinationDir(
		Project project, WSDLBuilderExtension wsdlBuilderExtension) {

		File destinationDir = wsdlBuilderExtension.getDestinationDir();

		if (!destinationDir.exists()) {
			wsdlBuilderExtension.setDestinationDir("lib");
		}
	}

	protected void configureWSDLBuilderExtensionWSDLDir(
		Project project, WSDLBuilderExtension wsdlBuilderExtension) {

		File wsdlDir = wsdlBuilderExtension.getWSDLDir();

		if (!wsdlDir.exists()) {
			wsdlBuilderExtension.setWSDLDir("wsdl");
		}
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

	protected File getLibDir(Project project) {
		return project.file("lib");
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