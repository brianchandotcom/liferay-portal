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

import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.AppServer;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.TomcatAppServer;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.lang.builder.BuildLangTask;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.tasks.InitGradleTask;
import com.liferay.gradle.plugins.tasks.SetupTestableTomcatTask;
import com.liferay.gradle.plugins.tld.formatter.TLDFormatterPlugin;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.BuildWSDLTask;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.plugins.xml.formatter.FormatXMLTask;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.plugins.xsd.builder.BuildXSDTask;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import nebula.plugin.extraconfigurations.OptionalBasePlugin;
import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayJavaPlugin implements Plugin<Project> {

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String EXPAND_PORTAL_WEB_TASK_NAME = "expandPortalWeb";

	public static final String FORMAT_WSDL_TASK_NAME = "formatWSDL";

	public static final String FORMAT_XSD_TASK_NAME = "formatXSD";

	public static final String INIT_GRADLE_TASK_NAME = "initGradle";

	public static final String PORTAL_WEB_CONFIGURATION_NAME = "portalWeb";

	public static final String SETUP_ARQUILLIAN_TASK_NAME = "setupArquillian";

	public static final String SETUP_TESTABLE_TOMCAT_TASK_NAME =
		"setupTestableTomcat";

	public static final String TEST_INTEGRATION_SOURCE_SET_NAME =
		"testIntegration";

	public static final String TEST_INTEGRATION_TASK_NAME = "testIntegration";

	@Override
	public void apply(Project project) {
		addLiferayExtension(project);

		applyPlugins(project);

		configureConfigurations(project);
		configureDependencies(project);
		configureProperties(project);
		configureRepositories(project);
		configureSourceSets(project);

		addConfigurations(project);
		addTasks(project);

		applyConfigScripts(project);

		configureTaskBuildService(project);
		configureTaskBuildWSDD(project);
		configureTaskBuildWSDL(project);
		configureTaskBuildXSD(project);
		configureTaskTest(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					LiferayExtension liferayExtension = GradleUtil.getExtension(
						project, LiferayExtension.class);

					configureVersion(project, liferayExtension);

					configureTasks(project, liferayExtension);
				}

			});
	}

	protected Configuration addConfigurationPortalWeb(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_WEB_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures portal-web for compiling CSS files.");
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
			"portal-web", "default", false);
	}

	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayExtension.class);
	}

	protected SourceSet addSourceSetTestIntegration(Project project) {
		SourceSet testIntegrationSourceSet = GradleUtil.addSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		Configuration testIntegrationCompileConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getCompileConfigurationName());

		Configuration compileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_CONFIGURATION_NAME);
		Configuration testCompileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME);

		testIntegrationCompileConfiguration.extendsFrom(
			compileConfiguration, testCompileConfiguration);

		Configuration testIntegrationRuntimeConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getRuntimeConfigurationName());

		Configuration runtimeConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME);
		Configuration testRuntimeConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME);

		testIntegrationRuntimeConfiguration.extendsFrom(
			runtimeConfiguration, testRuntimeConfiguration,
			testIntegrationCompileConfiguration);

		SourceSet mainSourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		testIntegrationSourceSet.setCompileClasspath(
			project.files(
				mainSourceSet.getOutput(),
				testIntegrationCompileConfiguration));
		testIntegrationSourceSet.setRuntimeClasspath(
			project.files(
				testIntegrationSourceSet.getOutput(), mainSourceSet.getOutput(),
				testIntegrationRuntimeConfiguration));

		return testIntegrationSourceSet;
	}

	protected Copy addTaskDeploy(Project project) {
		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		copy.setDescription("Assembles the project and deploys it to Liferay.");

		return copy;
	}

	protected Copy addTaskExpandPortalWeb(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, EXPAND_PORTAL_WEB_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					Configuration configuration = GradleUtil.getConfiguration(
						project, PORTAL_WEB_CONFIGURATION_NAME);

					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.include("html/css/common/**/*");

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					LiferayExtension liferayExtension = GradleUtil.getExtension(
						project, LiferayExtension.class);

					return new File(liferayExtension.getTmpDir(), "portal-web");
				}

			});

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

	protected void addTasks(Project project) {
		addTaskDeploy(project);
		addTaskExpandPortalWeb(project);
		addTaskFormatWSDL(project);
		addTaskFormatXSD(project);
		addTaskInitGradle(project);
		addTaskSetupArquillian(project);
		addTaskSetupTestableTomcat(project);
		addTaskTestIntegration(project);
		addTaskWar(project);
	}

	protected Task addTaskSetupArquillian(Project project) {
		Task task = project.task(SETUP_ARQUILLIAN_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					try {
						String xml = FileUtil.read(
							"com/liferay/gradle/plugins/dependencies/" +
								"arquillian.xml");

						LiferayExtension liferayExtension =
							GradleUtil.getExtension(
								project, LiferayExtension.class);

						TomcatAppServer tomcatAppServer =
							(TomcatAppServer)liferayExtension.getAppServer(
								"tomcat");

						xml = xml.replace(
							"${app.server.tomcat.manager.password}",
							tomcatAppServer.getManagerPassword());
						xml = xml.replace(
							"${app.server.tomcat.manager.user}",
							tomcatAppServer.getManagerUserName());
						xml = xml.replace(
							"${jmx.remote.port}",
							String.valueOf(
								liferayExtension.getJmxRemotePort()));

						SourceSet sourceSet = GradleUtil.getSourceSet(
							project, TEST_INTEGRATION_SOURCE_SET_NAME);

						File testIntegrationDir = getSrcDir(
							sourceSet.getResources());

						File arquillianXmlFile = new File(
							testIntegrationDir, "arquillian.xml");

						Files.write(
							arquillianXmlFile.toPath(),
							xml.getBytes(StandardCharsets.UTF_8));
					}
					catch (Exception e) {
						throw new GradleException(e.getMessage(), e);
					}
				}

			});

		task.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						task.getProject(), TEST_INTEGRATION_SOURCE_SET_NAME);

					File testIntegrationDir = getSrcDir(
						sourceSet.getResources());

					File arquillianXmlFile = new File(
						testIntegrationDir, "arquillian.xml");

					if (arquillianXmlFile.exists()) {
						return false;
					}

					SourceDirectorySet sourceDirectorySet =
						sourceSet.getAllJava();

					if (sourceDirectorySet.isEmpty()) {
						return false;
					}

					return true;
				}

			});

		return task;
	}

	protected SetupTestableTomcatTask addTaskSetupTestableTomcat(
		Project project) {

		SetupTestableTomcatTask setupTestableTomcatTask = GradleUtil.addTask(
			project, SETUP_TESTABLE_TOMCAT_TASK_NAME,
			SetupTestableTomcatTask.class);

		setupTestableTomcatTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Project project = task.getProject();

					LiferayExtension liferayExtension = GradleUtil.getExtension(
						project, LiferayExtension.class);

					String appServerType = liferayExtension.getAppServerType();

					if (appServerType.equals("tomcat")) {
						return true;
					}

					return false;
				}

			});

		return setupTestableTomcatTask;
	}

	protected Test addTaskTestIntegration(Project project) {
		Test test = GradleUtil.addTask(
			project, TEST_INTEGRATION_TASK_NAME, Test.class);

		test.dependsOn(SETUP_ARQUILLIAN_TASK_NAME);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		test.setClasspath(sourceSet.getRuntimeClasspath());
		test.setDescription("Runs the integration tests.");
		test.setGroup("verification");
		test.setTestClassesDir(sourceSetOutput.getClassesDir());

		return test;
	}

	protected Task addTaskWar(Project project) {
		Task task = project.task(WarPlugin.WAR_TASK_NAME);

		task.dependsOn(JavaPlugin.JAR_TASK_NAME);

		task.setDescription("Alias for 'jar'.");
		task.setGroup(BasePlugin.BUILD_GROUP);

		return task;
	}

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-liferay.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		GradleUtil.applyPlugin(project, OptionalBasePlugin.class);
		GradleUtil.applyPlugin(project, ProvidedBasePlugin.class);

		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
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

	protected void configureConfigurations(final Project project) {
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

									LiferayExtension liferayExtension =
										GradleUtil.getExtension(
											project, LiferayExtension.class);

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

	protected void configureDependencies(Project project) {
		configureDependenciesProvided(project);
		configureDependenciesTestCompile(project);
	}

	protected void configureDependenciesProvided(Project project) {
		if (!isAddDefaultDependencies(project)) {
			return;
		}

		for (String dependencyNotation : DEFAULT_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME(),
				dependencyNotation);
		}

		for (String dependencyNotation : _DEFAULT_EXT_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME(),
				dependencyNotation);
		}
	}

	protected void configureDependenciesTestCompile(Project project) {
		if (!isAddTestDefaultDependencies(project)) {
			return;
		}

		for (String dependencyNotation : _DEFAULT_TEST_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	protected void configureProperties(Project project) {
		configureTestResultsDir(project);
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

	protected void configureSourceSet(
		Project project, String name, File classesDir, File srcDir) {

		SourceSet sourceSet = GradleUtil.getSourceSet(project, name);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		Set<File> srcDirs = Collections.singleton(srcDir);

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		sourceSetOutput.setClassesDir(classesDir);
		sourceSetOutput.setResourcesDir(classesDir);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureSourceSetMain(Project project) {
		File classesDir = project.file("classes");
		File srcDir = project.file("src");

		configureSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME, classesDir, srcDir);
	}

	protected void configureSourceSets(Project project) {
		addSourceSetTestIntegration(project);

		configureSourceSetMain(project);
		configureSourceSetTest(project);
		configureSourceSetTestIntegration(project);
	}

	protected void configureSourceSetTest(Project project) {
		File classesDir = project.file("test-classes/unit");
		File srcDir = project.file("test/unit");

		configureSourceSet(
			project, SourceSet.TEST_SOURCE_SET_NAME, classesDir, srcDir);
	}

	protected void configureSourceSetTestIntegration(Project project) {
		File classesDir = project.file("test-classes/integration");
		File srcDir = project.file("test/integration");

		configureSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME, classesDir, srcDir);
	}

	protected void configureTaskBuildCSS(
		Project project, LiferayExtension liferayExtension) {

		BuildCSSTask buildCSSTask = (BuildCSSTask)GradleUtil.getTask(
			project, CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

		configureTaskBuildCSSDocrootDirName(buildCSSTask);
		configureTaskBuildCSSPortalCommonDirName(buildCSSTask);
	}

	protected void configureTaskBuildCSSDocrootDirName(
		BuildCSSTask buildCSSTask) {

		Project project = buildCSSTask.getProject();

		String docrootDirName = buildCSSTask.getDocrootDirName();

		if (Validator.isNotNull(docrootDirName) &&
			FileUtil.exists(project, docrootDirName)) {

			return;
		}

		File resourcesDir = getResourcesDir(project);

		buildCSSTask.setDocrootDirName(project.relativePath(resourcesDir));
	}

	protected void configureTaskBuildCSSPortalCommonDirName(
		BuildCSSTask buildCSSTask) {

		Project project = buildCSSTask.getProject();

		String portalCommonDirName = buildCSSTask.getPortalCommonDirName();

		if (Validator.isNotNull(portalCommonDirName) &&
			FileUtil.exists(project, portalCommonDirName)) {

			return;
		}

		Task expandPortalWebTask = GradleUtil.getTask(
			project, EXPAND_PORTAL_WEB_TASK_NAME);

		buildCSSTask.dependsOn(expandPortalWebTask);

		TaskOutputs taskOutputs = expandPortalWebTask.getOutputs();

		FileCollection fileCollection = taskOutputs.getFiles();

		File portalCommonDir = new File(
			fileCollection.getSingleFile(), "html/css/common");

		buildCSSTask.setPortalCommonDirName(
			project.relativePath(portalCommonDir));
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
		classesTask.dependsOn(CSSBuilderPlugin.BUILD_CSS_TASK_NAME);
	}

	protected void configureTaskClean(Project project) {
		Task cleanTask = GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		configureTaskCleanDependsOn(cleanTask);
	}

	protected void configureTaskCleanDependsOn(Task cleanTask) {
		Project project = cleanTask.getProject();

		for (Task task : project.getTasks()) {
			String taskName = task.getName();

			if (taskName.equals(DEPLOY_TASK_NAME)) {
				continue;
			}

			TaskOutputs taskOutputs = task.getOutputs();

			if (taskOutputs.getHasOutput()) {
				taskName =
					BasePlugin.CLEAN_TASK_NAME +
						StringUtil.capitalize(taskName);

				cleanTask.dependsOn(taskName);
			}
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

	protected void configureTaskDirectDeployAppServerLibGlobalDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerLibGlobalDir() == null) {
			directDeployTask.setAppServerLibGlobalDir(
				liferayExtension.getAppServerLibGlobalDir());
		}
	}

	protected void configureTaskDirectDeployAppServerPortalDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerPortalDir() == null) {
			directDeployTask.setAppServerPortalDir(
				liferayExtension.getAppServerPortalDir());
		}
	}

	protected void configureTaskDirectDeployAppServerType(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (Validator.isNull(directDeployTask.getAppServerType())) {
			directDeployTask.setAppServerType(
				liferayExtension.getAppServerType());
		}
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

	protected void configureTaskJar(Project project) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		configureTaskJarDependsOn(jar);
		configureTaskJarDuplicatesStrategy(jar);
	}

	protected void configureTaskJarDependsOn(Jar jar) {
		Project project = jar.getProject();

		if (isTestProject(project)) {
			jar.dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME);

			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, TEST_INTEGRATION_SOURCE_SET_NAME);

			jar.dependsOn(sourceSet.getClassesTaskName());
		}
	}

	protected void configureTaskJarDuplicatesStrategy(Jar jar) {
		jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		configureTaskBuildCSS(project, liferayExtension);
		configureTaskBuildLang(project);
		configureTaskClasses(project);
		configureTaskClean(project);
		configureTaskDeploy(project, liferayExtension);
		configureTaskFormatWSDL(project);
		configureTaskFormatXSD(project);
		configureTaskJar(project);
		configureTaskSetupTestableTomcat(project, liferayExtension);
		configureTasksDirectDeploy(project);
	}

	protected void configureTasksDirectDeploy(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DirectDeployTask.class,
			new Action<DirectDeployTask>() {

				@Override
				public void execute(DirectDeployTask directDeployTask) {
					LiferayExtension liferayExtension = GradleUtil.getExtension(
						directDeployTask.getProject(), LiferayExtension.class);

					configureTaskDirectDeployAppServerLibGlobalDir(
						directDeployTask, liferayExtension);
					configureTaskDirectDeployAppServerPortalDir(
						directDeployTask, liferayExtension);
					configureTaskDirectDeployAppServerType(
						directDeployTask, liferayExtension);
				}

			});
	}

	protected void configureTaskSetupTestableTomcat(
		Project project, LiferayExtension liferayExtension) {

		SetupTestableTomcatTask setupTestableTomcatTask =
			(SetupTestableTomcatTask)GradleUtil.getTask(
				project, SETUP_TESTABLE_TOMCAT_TASK_NAME);

		configureTaskSetupTestableTomcatDir(
			setupTestableTomcatTask, liferayExtension);
		configureTaskSetupTestableTomcatJmx(
			setupTestableTomcatTask, liferayExtension);
		configureTaskSetupTestableTomcatManagerUser(
			setupTestableTomcatTask, liferayExtension);
		configureTaskSetupTestableTomcatModuleFrameworkBaseDir(
			setupTestableTomcatTask, liferayExtension);
		configureTaskSetupTestableTomcatZipUrl(
			setupTestableTomcatTask, liferayExtension);
	}

	protected void configureTaskSetupTestableTomcatDir(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		if (setupTestableTomcatTask.getTomcatDir() != null) {
			return;
		}

		AppServer appServer = liferayExtension.getAppServer("tomcat");

		setupTestableTomcatTask.setTomcatDir(appServer.getDir());
	}

	protected void configureTaskSetupTestableTomcatJmx(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		if (setupTestableTomcatTask.getJmxRemotePort() > 0) {
			return;
		}

		setupTestableTomcatTask.setJmxRemotePort(
			liferayExtension.getJmxRemotePort());
	}

	protected void configureTaskSetupTestableTomcatManagerUser(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		if (Validator.isNull(
				setupTestableTomcatTask.getTomcatManagerPassword())) {

			setupTestableTomcatTask.setTomcatManagerPassword(
				tomcatAppServer.getManagerPassword());
		}

		if (Validator.isNull(
				setupTestableTomcatTask.getTomcatManagerUserName())) {

			setupTestableTomcatTask.setTomcatManagerUserName(
				tomcatAppServer.getManagerUserName());
		}
	}

	protected void configureTaskSetupTestableTomcatModuleFrameworkBaseDir(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		if (setupTestableTomcatTask.getModuleFrameworkBaseDir() != null) {
			return;
		}

		File moduleFrameworkBaseDir = new File(
			liferayExtension.getLiferayHome(), "osgi");

		setupTestableTomcatTask.setModuleFrameworkBaseDir(
			moduleFrameworkBaseDir);
	}

	protected void configureTaskSetupTestableTomcatZipUrl(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		if (Validator.isNotNull(setupTestableTomcatTask.getTomcatZipUrl())) {
			return;
		}

		AppServer appServer = liferayExtension.getAppServer("tomcat");

		setupTestableTomcatTask.setTomcatZipUrl(appServer.getZipUrl());
	}

	protected void configureTaskTest(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		configureTaskTestDefaultCharacterEncoding(test);
		configureTaskTestForkEvery(test);
		configureTaskTestLogging(test);
	}

	protected void configureTaskTestDefaultCharacterEncoding(Test test) {
		test.setDefaultCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	protected void configureTaskTestForkEvery(Test test) {
		test.setForkEvery(1L);
	}

	protected void configureTaskTestLogging(Test test) {
		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setShowStandardStreams(true);
	}

	protected void configureTestResultsDir(Project project) {
		JavaPluginConvention javaPluginConvention = GradleUtil.getConvention(
			project, JavaPluginConvention.class);

		File testResultsDir = project.file("test-results/unit");

		javaPluginConvention.setTestResultsDirName(
			FileUtil.relativize(testResultsDir, project.getBuildDir()));
	}

	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		project.setVersion(
			liferayExtension.getVersionPrefix() + "." + project.getVersion());
	}

	protected File getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getJava());
	}

	protected File getLibDir(Project project) {
		return project.file("lib");
	}

	protected boolean getProperty(
		Project project, String name, boolean defaultValue) {

		if (!project.hasProperty(name)) {
			return defaultValue;
		}

		Object value = project.property(name);

		if (value instanceof Boolean) {
			return (Boolean)value;
		}

		if (value instanceof String) {
			return Boolean.parseBoolean((String)value);
		}

		return defaultValue;
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getResources());
	}

	protected File getServiceBaseDir(Project project) {
		return project.getProjectDir();
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	protected boolean isAddDefaultDependencies(Project project) {
		return getProperty(
			project, _ADD_DEFAULT_DEPENDENCIES_PROPERTY_NAME, true);
	}

	protected boolean isAddTestDefaultDependencies(Project project) {
		return getProperty(
			project, _ADD_TEST_DEFAULT_DEPENDENCIES_PROPERTY_NAME, true);
	}

	protected boolean isTestProject(Project project) {
		String projectName = project.getName();

		if (projectName.endsWith("-test")) {
			return true;
		}

		return false;
	}

	protected static final String[] DEFAULT_DEPENDENCY_NOTATIONS = {
		"biz.aQute.bnd:biz.aQute.bnd:2.4.1",
		"com.liferay.portal:portal-service:default",
		"com.liferay.portal:util-bridges:default",
		"com.liferay.portal:util-java:default",
		"com.liferay.portal:util-taglib:default",
		"commons-logging:commons-logging:1.1.3",
		"javax.activation:activation:1.1", "javax.annotation:jsr250-api:1.0",
		"javax.mail:mail:1.4", "javax.servlet.jsp:jsp-api:2.1",
		"javax.servlet:javax.servlet-api:3.0.1", "log4j:log4j:1.2.17"
	};

	protected static class RenameDependencyClosure extends Closure<String> {

		public RenameDependencyClosure(
			Project project, String ... configurationNames) {

			super(null);

			_project = project;
			_configurationNames = configurationNames;
		}

		public String doCall(String name) {
			Map<String, String> newDependencyNames = _getNewDependencyNames();

			String newDependencyName = newDependencyNames.get(name);

			if (Validator.isNotNull(newDependencyName)) {
				return newDependencyName;
			}

			return name;
		}

		private Map<String, String> _getNewDependencyNames() {
			if (_newDependencyNames != null) {
				return _newDependencyNames;
			}

			_newDependencyNames = new HashMap<>();

			for (String configurationName : _configurationNames) {
				Configuration configuration = GradleUtil.getConfiguration(
					_project, configurationName);

				ResolvedConfiguration resolvedConfiguration =
					configuration.getResolvedConfiguration();

				for (ResolvedArtifact resolvedArtifact :
						resolvedConfiguration.getResolvedArtifacts()) {

					ResolvedModuleVersion resolvedModuleVersion =
						resolvedArtifact.getModuleVersion();

					ModuleVersionIdentifier moduleVersionIdentifier =
						resolvedModuleVersion.getId();

					File file = resolvedArtifact.getFile();

					String oldDependencyName = file.getName();

					String newDependencyName;

					String suffix =
						"-" + moduleVersionIdentifier.getVersion() + ".jar";

					if (oldDependencyName.endsWith(suffix)) {
						newDependencyName = oldDependencyName.substring(
							0, oldDependencyName.length() - suffix.length());

						newDependencyName += ".jar";
					}
					else {
						newDependencyName =
							moduleVersionIdentifier.getName() + ".jar";
					}

					_newDependencyNames.put(
						oldDependencyName, newDependencyName);
				}
			}

			return _newDependencyNames;
		}

		private final String[] _configurationNames;
		private Map<String, String> _newDependencyNames;
		private final Project _project;

	}

	private static final String _ADD_DEFAULT_DEPENDENCIES_PROPERTY_NAME =
		"com.liferay.adddefaultdependencies";

	private static final String _ADD_TEST_DEFAULT_DEPENDENCIES_PROPERTY_NAME =
		"com.liferay.addtestdefaultdependencies";

	private static final String[] _DEFAULT_EXT_DEPENDENCY_NOTATIONS = {
		"hsqldb:hsqldb:1.8.0.7", "javax.ccpp:ccpp:1.0", "javax.jms:jms:1.1",
		"javax.portlet:portlet-api:2.0", "mysql:mysql-connector-java:5.1.23",
		"net.sourceforge.jtds:jtds:1.2.6",
		"org.eclipse.persistence:javax.persistence:2.0.0",
		"postgresql:postgresql:9.2-1002.jdbc4"
	};

	private static final String[] _DEFAULT_TEST_DEPENDENCY_NOTATIONS = {
		"junit:junit:4.12", "org.mockito:mockito-all:1.9.5",
		"org.powermock:powermock-api-mockito:1.6.1",
		"org.powermock:powermock-api-support:1.6.1",
		"org.powermock:powermock-core:1.6.1",
		"org.powermock:powermock-module-junit4:1.6.1",
		"org.powermock:powermock-module-junit4-common:1.6.1",
		"org.powermock:powermock-reflect:1.6.1",
		"org.springframework:spring-test:3.0.7.RELEASE"
	};

	private static final String _REPOSITORY_URL =
		"http://cdn.repository.liferay.com/nexus/content/groups/public";

}