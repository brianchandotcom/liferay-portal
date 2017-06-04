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

package com.liferay.gradle.plugins.defaults;

import aQute.bnd.osgi.Constants;
import aQute.bnd.version.Version;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.baseline.BaselinePlugin;
import com.liferay.gradle.plugins.baseline.BaselineTask;
import com.liferay.gradle.plugins.cache.CacheExtension;
import com.liferay.gradle.plugins.cache.CachePlugin;
import com.liferay.gradle.plugins.cache.task.TaskCache;
import com.liferay.gradle.plugins.defaults.internal.FindSecurityBugsPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayRelengPlugin;
import com.liferay.gradle.plugins.defaults.internal.WhipDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.BackupFilesBuildAdapter;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GitUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.IncrementVersionClosure;
import com.liferay.gradle.plugins.defaults.internal.util.copy.RenameDependencyAction;
import com.liferay.gradle.plugins.defaults.tasks.CheckOSGiBundleStateTask;
import com.liferay.gradle.plugins.defaults.tasks.InstallCacheTask;
import com.liferay.gradle.plugins.defaults.tasks.ReplaceRegexTask;
import com.liferay.gradle.plugins.defaults.tasks.WriteArtifactPublishCommandsTask;
import com.liferay.gradle.plugins.defaults.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.dependency.checker.DependencyCheckerExtension;
import com.liferay.gradle.plugins.dependency.checker.DependencyCheckerPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.patcher.PatchTask;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.tlddoc.builder.TLDDocBuilderPlugin;
import com.liferay.gradle.plugins.tlddoc.builder.tasks.TLDDocTask;
import com.liferay.gradle.plugins.upgrade.table.builder.UpgradeTableBuilderPlugin;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.plugins.whip.WhipPlugin;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.ExcludeExistingFileAction;
import com.liferay.gradle.util.copy.RenameDependencyClosure;
import com.liferay.gradle.util.copy.ReplaceLeadingPathAction;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import groovy.time.Duration;
import groovy.time.TimeCategory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLConnection;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nebula.plugin.extraconfigurations.OptionalBasePlugin;
import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.dm.gradle.plugins.bundle.BundleExtension;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.DependencySubstitutions;
import org.gradle.api.artifacts.DependencySubstitutions.Substitution;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.ExternalDependency;
import org.gradle.api.artifacts.ExternalModuleDependency;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.ResolveException;
import org.gradle.api.artifacts.component.ComponentSelector;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.maven.Conf2ScopeMapping;
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer;
import org.gradle.api.artifacts.maven.MavenDeployer;
import org.gradle.api.artifacts.repositories.AuthenticationContainer;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.PasswordCredentials;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.artifacts.publish.ArchivePublishArtifact;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.plugins.MavenPluginConvention;
import org.gradle.api.plugins.MavenRepositoryHandlerConvention;
import org.gradle.api.plugins.quality.FindBugs;
import org.gradle.api.plugins.quality.FindBugsPlugin;
import org.gradle.api.plugins.quality.FindBugsReports;
import org.gradle.api.plugins.quality.Pmd;
import org.gradle.api.plugins.quality.PmdExtension;
import org.gradle.api.plugins.quality.PmdPlugin;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.resources.ResourceHandler;
import org.gradle.api.resources.TextResourceFactory;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.StopActionException;
import org.gradle.api.tasks.TaskCollection;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.Upload;
import org.gradle.api.tasks.VerificationTask;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.CompileOptions;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.JUnitXmlReport;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestTaskReports;
import org.gradle.api.tasks.testing.logging.TestExceptionFormat;
import org.gradle.api.tasks.testing.logging.TestLogEvent;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;
import org.gradle.execution.ProjectConfigurer;
import org.gradle.external.javadoc.CoreJavadocOptions;
import org.gradle.external.javadoc.StandardJavadocDocletOptions;
import org.gradle.internal.authentication.DefaultBasicAuthentication;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.plugins.ide.api.XmlFileContentMerger;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.Classpath;
import org.gradle.plugins.ide.eclipse.model.ClasspathEntry;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.eclipse.model.SourceFolder;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;
import org.gradle.process.ExecSpec;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiDefaultsPlugin implements Plugin<Project> {

	public static final String ASPECTJ_WEAVER_CONFIGURATION_NAME =
		"aspectJWeaver";

	public static final String CHECK_OSGI_BUNDLE_STATE_TASK_NAME =
		"checkOSGiBundleState";

	public static final String COMMIT_CACHE_TASK_NAME = "commitCache";

	public static final String COPY_LIBS_TASK_NAME = "copyLibs";

	public static final String DEFAULT_REPOSITORY_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups" +
			"/public";

	public static final String DEPLOY_APP_SERVER_LIB_TASK_NAME =
		"deployAppServerLib";

	public static final String DEPLOY_DEPENDENCIES_TASK_NAME =
		"deployDependencies";

	public static final String DEPLOY_TOOL_TASK_NAME = "deployTool";

	public static final String DOWNLOAD_COMPILED_JSP_TASK_NAME =
		"downloadCompiledJSP";

	public static final String INSTALL_CACHE_TASK_NAME = "installCache";

	public static final String JAR_JAVADOC_TASK_NAME = "jarJavadoc";

	public static final String JAR_JSP_TASK_NAME = "jarJSP";

	public static final String JAR_SOURCES_TASK_NAME = "jarSources";

	public static final String JAR_TLDDOC_TASK_NAME = "jarTLDDoc";

	public static final String PORTAL_TEST_CONFIGURATION_NAME = "portalTest";

	public static final String RELEASE_PORTAL_ROOT_DIR_PROPERTY_NAME =
		"release.versions.test.other.dir";

	public static final String SNAPSHOT_IF_STALE_PROPERTY_NAME =
		"snapshotIfStale";

	public static final String SYNC_RELEASE_PROPERTY_NAME = "syncRelease";

	public static final String SYNC_VERSIONS_TASK_NAME = "syncVersions";

	public static final String UPDATE_FILE_SNAPSHOT_VERSIONS_TASK_NAME =
		"updateFileSnapshotVersions";

	public static final String UPDATE_FILE_VERSIONS_TASK_NAME =
		"updateFileVersions";

	@Override
	public void apply(final Project project) {
		final File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		final GitRepo gitRepo = _getGitRepo(project.getProjectDir());
		final boolean testProject = GradleUtil.isTestProject(project);

		File versionOverrideFile = _getVersionOverrideFile(project, gitRepo);

		boolean syncReleaseVersions = _syncReleaseVersions(
			project, portalRootDir, versionOverrideFile, testProject);

		_applyVersionOverrides(project, versionOverrideFile);

		Gradle gradle = project.getGradle();

		gradle.addBuildListener(_backupFilesBuildAdapter);

		StartParameter startParameter = gradle.getStartParameter();

		List<String> taskNames = startParameter.getTaskNames();

		final boolean publishing = _isPublishing(project);

		boolean deployToAppServerLibs = false;
		boolean deployToTools = false;

		if (FileUtil.exists(project, ".lfrbuild-app-server-lib")) {
			deployToAppServerLibs = true;
		}
		else if (FileUtil.exists(project, ".lfrbuild-tool")) {
			deployToTools = true;
		}

		_applyPlugins(project);

		// applyConfigScripts configures the "install" and "uploadArchives"
		// tasks, and this causes the conf2ScopeMappings.mappings convention
		// property to be cloned in a second map. Because we want to change
		// the default mappings, we must call configureMavenConf2ScopeMappings
		// before applyConfigScripts.

		_configureMavenConf2ScopeMappings(project);

		_applyConfigScripts(project);

		_addDependenciesPmd(project);

		if (testProject || _hasTests(project)) {
			GradleUtil.applyPlugin(project, WhipPlugin.class);

			WhipDefaultsPlugin.INSTANCE.apply(project);

			Configuration aspectJWeaverConfiguration =
				_addConfigurationAspectJWeaver(project);
			Configuration portalConfiguration = GradleUtil.getConfiguration(
				project, LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);
			Configuration portalTestConfiguration = _addConfigurationPortalTest(
				project);

			_addDependenciesPortalTest(project);
			_addDependenciesTestCompile(project);
			_configureEclipse(project, portalTestConfiguration);
			_configureIdea(project, portalTestConfiguration);
			_configureSourceSetTest(
				project, portalConfiguration, portalTestConfiguration);
			_configureSourceSetTestIntegration(
				project, portalConfiguration, portalTestConfiguration);
			_configureTaskTestAspectJWeaver(
				project, JavaPlugin.TEST_TASK_NAME, aspectJWeaverConfiguration);
			_configureTaskTestAspectJWeaver(
				project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME,
				aspectJWeaverConfiguration);
		}

		Task baselineTask = GradleUtil.getTask(
			project, BaselinePlugin.BASELINE_TASK_NAME);
		Task syncVersionsTask = _addTaskSyncVersions(project);

		baselineTask.finalizedBy(syncVersionsTask);

		if (syncReleaseVersions) {
			_configureTaskBaselineSyncReleaseVersions(
				baselineTask, versionOverrideFile);
		}

		if (!testProject) {
			_addTaskCheckOSGiBundleState(project);
		}

		InstallCacheTask installCacheTask = _addTaskInstallCache(project);

		_addTaskCommitCache(project, installCacheTask);

		_addTaskCopyLibs(project);

		Copy deployDependenciesTask = _addTaskDeployDependencies(
			project, liferayExtension);

		if (deployToAppServerLibs) {
			_addTaskAlias(
				project, DEPLOY_APP_SERVER_LIB_TASK_NAME,
				LiferayBasePlugin.DEPLOY_TASK_NAME);
		}
		else if (deployToTools) {
			_addTaskAlias(
				project, DEPLOY_TOOL_TASK_NAME,
				LiferayBasePlugin.DEPLOY_TASK_NAME);
		}

		final Jar jarJSPsTask = _addTaskJarJSP(project);
		final Jar jarJavadocTask = _addTaskJarJavadoc(project);
		final Jar jarSourcesTask = _addTaskJarSources(project, testProject);
		final Jar jarTLDDocTask = _addTaskJarTLDDoc(project);

		final ReplaceRegexTask updateFileVersionsTask =
			_addTaskUpdateFileVersions(project, gitRepo);
		final ReplaceRegexTask updateVersionTask = _addTaskUpdateVersion(
			project);

		_configureBasePlugin(project, portalRootDir);
		_configureBundleDefaultInstructions(project, portalRootDir, publishing);
		_configureConfigurations(project, gitRepo, liferayExtension);
		_configureDependencyChecker(project);
		_configureDeployDir(
			project, liferayExtension, deployToAppServerLibs, deployToTools);
		_configureEclipse(project);
		_configureJavaPlugin(project);
		_configureLocalPortalTool(
			project, portalRootDir, SourceFormatterPlugin.CONFIGURATION_NAME,
			_SOURCE_FORMATTER_PORTAL_TOOL_NAME);
		_configurePmd(project);
		_configureProject(project);
		configureRepositories(project);
		_configureSourceSetMain(project);
		_configureTaskDeploy(project, deployDependenciesTask);
		_configureTaskJar(project, testProject);
		_configureTaskJavadoc(project, portalRootDir);
		_configureTaskTest(project);
		_configureTaskTestIntegration(project);
		_configureTaskTlddoc(project, portalRootDir);
		_configureTasksBaseline(project);
		_configureTasksCheckOSGiBundleState(project, liferayExtension);
		_configureTasksFindBugs(project);
		_configureTasksJavaCompile(project);
		_configureTasksPmd(project);
		_configureTasksPublishNodeModule(project);

		_addTaskUpdateFileSnapshotVersions(project);

		if (publishing) {
			_configureTasksEnabledIfStaleSnapshot(
				project, testProject, MavenPlugin.INSTALL_TASK_NAME,
				BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);
		}

		GradleUtil.withPlugin(
			project, ServiceBuilderPlugin.class,
			new Action<ServiceBuilderPlugin>() {

				@Override
				public void execute(ServiceBuilderPlugin serviceBuilderPlugin) {
					_configureLocalPortalTool(
						project, portalRootDir,
						ServiceBuilderPlugin.CONFIGURATION_NAME,
						_SERVICE_BUILDER_PORTAL_TOOL_NAME);
					_configureTaskBuildService(project);
				}

			});

		GradleUtil.withPlugin(
			project, WSDDBuilderPlugin.class,
			new Action<WSDDBuilderPlugin>() {

				@Override
				public void execute(WSDDBuilderPlugin wsddBuilderPlugin) {
					_configureTaskBuildWSDD(project);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_checkVersion(project);

					_configureArtifacts(
						project, jarJSPsTask, jarJavadocTask, jarSourcesTask,
						jarTLDDocTask);
					_configureTaskJarSources(jarSourcesTask);
					_configureTaskUpdateFileVersions(
						updateFileVersionsTask, portalRootDir);

					GradleUtil.setProjectSnapshotVersion(
						project, _SNAPSHOT_PROPERTY_NAMES);

					if (GradleUtil.hasPlugin(project, CachePlugin.class)) {
						_configureTaskUpdateVersionForCachePlugin(
							updateVersionTask);
					}

					_configureTaskCompileJSP(
						project, jarJSPsTask, liferayExtension);

					// setProjectSnapshotVersion must be called before
					// configureTaskUploadArchives, because the latter one needs
					// to know if we are publishing a snapshot or not.

					_configureTaskUploadArchives(
						project, testProject, updateFileVersionsTask,
						updateVersionTask);

					_configureProjectBndProperties(project, liferayExtension);
				}

			});

		if (taskNames.contains("eclipse") || taskNames.contains("idea")) {
			_forceProjectDependenciesEvaluation(project);
		}

		TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

		taskExecutionGraph.whenReady(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(TaskExecutionGraph taskExecutionGraph) {
					Task jarTask = GradleUtil.getTask(
						project, JavaPlugin.JAR_TASK_NAME);

					if (taskExecutionGraph.hasTask(jarTask)) {
						_configureBundleInstructions(project, gitRepo);
					}
				}

			});
	}

	protected static void configureRepositories(Project project) {
		RepositoryHandler repositoryHandler = project.getRepositories();

		if (!Boolean.getBoolean("maven.local.ignore")) {
			repositoryHandler.mavenLocal();
		}

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					String url = System.getProperty(
						"repository.url", DEFAULT_REPOSITORY_URL);

					mavenArtifactRepository.setUrl(url);
				}

			});

		final String repositoryPrivatePassword = System.getProperty(
			"repository.private.password");
		final String repositoryPrivateUrl = System.getProperty(
			"repository.private.url");
		final String repositoryPrivateUsername = System.getProperty(
			"repository.private.username");

		if (Validator.isNotNull(repositoryPrivatePassword) &&
			Validator.isNotNull(repositoryPrivateUrl) &&
			Validator.isNotNull(repositoryPrivateUsername)) {

			MavenArtifactRepository mavenArtifactRepository =
				repositoryHandler.maven(
					new Action<MavenArtifactRepository>() {

						@Override
						public void execute(
							MavenArtifactRepository mavenArtifactRepository) {

							mavenArtifactRepository.setUrl(
								repositoryPrivateUrl);
						}

					});

			mavenArtifactRepository.authentication(
				new Action<AuthenticationContainer>() {

					@Override
					public void execute(
						AuthenticationContainer authenticationContainer) {

						authenticationContainer.add(
							new DefaultBasicAuthentication("basic"));
					}

				});

			mavenArtifactRepository.credentials(
				new Action<PasswordCredentials>() {

					@Override
					public void execute(
						PasswordCredentials passwordCredentials) {

						passwordCredentials.setPassword(
							repositoryPrivatePassword);
						passwordCredentials.setUsername(
							repositoryPrivateUsername);
					}

				});
		}
	}

	private Configuration _addConfigurationAspectJWeaver(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, ASPECTJ_WEAVER_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesAspectJWeaver(project);
				}

			});

		configuration.setDescription(
			"Configures AspectJ Weaver to apply to the test tasks.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private Configuration _addConfigurationPortalTest(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_TEST_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay portal test utility artifacts for this " +
				"project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesAspectJWeaver(Project project) {
		GradleUtil.addDependency(
			project, ASPECTJ_WEAVER_CONFIGURATION_NAME, "org.aspectj",
			"aspectjweaver", "1.8.9");
	}

	private void _addDependenciesPmd(Project project) {
		String version = PortalTools.getVersion(project, _PMD_PORTAL_TOOL_NAME);

		if (Validator.isNotNull(version)) {
			GradleUtil.addDependency(
				project, "pmd", PortalTools.GROUP, _PMD_PORTAL_TOOL_NAME,
				version);
		}
	}

	private void _addDependenciesPortalTest(Project project) {
		GradleUtil.addDependency(
			project, PORTAL_TEST_CONFIGURATION_NAME, _GROUP_PORTAL,
			"com.liferay.portal.test", "default");
		GradleUtil.addDependency(
			project, PORTAL_TEST_CONFIGURATION_NAME, _GROUP_PORTAL,
			"com.liferay.portal.test.integration", "default");
	}

	private void _addDependenciesTestCompile(Project project) {
		GradleUtil.addDependency(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME, "org.mockito",
			"mockito-core", "1.10.8");

		ModuleDependency moduleDependency =
			(ModuleDependency)GradleUtil.addDependency(
				project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME,
				"org.powermock", "powermock-api-mockito", "1.6.1");

		Map<String, String> excludeArgs = new HashMap<>();

		excludeArgs.put("group", "org.mockito");
		excludeArgs.put("module", "mockito-all");

		moduleDependency.exclude(excludeArgs);

		GradleUtil.addDependency(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME,
			"org.powermock", "powermock-module-junit4", "1.6.1");
		GradleUtil.addDependency(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME,
			"org.springframework", "spring-test", "3.2.15.RELEASE");
	}

	private Task _addTaskAlias(
		Project project, String taskName, String originalTaskName) {

		Task task = project.task(taskName);

		Task originalTask = GradleUtil.getTask(project, originalTaskName);

		task.dependsOn(originalTask);
		task.setDescription("Alias for " + originalTask);
		task.setGroup(originalTask.getGroup());

		return task;
	}

	private CheckOSGiBundleStateTask _addTaskCheckOSGiBundleState(
		final Project project) {

		CheckOSGiBundleStateTask checkOSGiBundleStateTask = GradleUtil.addTask(
			project, CHECK_OSGI_BUNDLE_STATE_TASK_NAME,
			CheckOSGiBundleStateTask.class);

		checkOSGiBundleStateTask.setBundleSymbolicName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getBundleInstruction(
						project, Constants.BUNDLE_SYMBOLICNAME);
				}

			});

		checkOSGiBundleStateTask.setDescription(
			"Checks the state of the deployed OSGi bundle.");
		checkOSGiBundleStateTask.setGroup(
			LifecycleBasePlugin.VERIFICATION_GROUP);

		return checkOSGiBundleStateTask;
	}

	private Task _addTaskCommitCache(
		Project project, final InstallCacheTask installCacheTask) {

		Task task = project.task(COMMIT_CACHE_TASK_NAME);

		task.dependsOn(installCacheTask);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					File cachedVersionDir =
						installCacheTask.getCacheDestinationDir();

					File cachedArtifactDir = cachedVersionDir.getParentFile();

					File[] cachedVersionDirs = FileUtil.getDirectories(
						cachedArtifactDir);

					if (cachedVersionDirs.length != 2) {
						throw new StopActionException(
							"Skipping old cached version deletion");
					}

					File oldCachedVersionDir = cachedVersionDirs[0];

					if (cachedVersionDir.equals(oldCachedVersionDir)) {
						oldCachedVersionDir = cachedVersionDirs[1];
					}

					Logger logger = task.getLogger();
					Project project = task.getProject();

					boolean deleted = project.delete(oldCachedVersionDir);

					if (!deleted && logger.isWarnEnabled()) {
						logger.warn(
							"Unable to delete old cached version in " +
								oldCachedVersionDir);
					}
				}

			});

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					project.exec(
						new Action<ExecSpec>() {

							@Override
							public void execute(ExecSpec execSpec) {
								execSpec.setCommandLine("git", "add", ".");

								File cachedVersionDir =
									installCacheTask.getCacheDestinationDir();

								execSpec.setWorkingDir(
									cachedVersionDir.getParentFile());
							}

						});
				}

			});

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					final String commitSubject = GitUtil.getGitResult(
						project, "log", "-1", "--pretty=%s");

					project.exec(
						new Action<ExecSpec>() {

							@Override
							public void execute(ExecSpec execSpec) {
								String message = _CACHE_COMMIT_MESSAGE;

								int index = commitSubject.indexOf(' ');

								if (index != -1) {
									message =
										commitSubject.substring(0, index + 1) +
											_CACHE_COMMIT_MESSAGE;
								}

								execSpec.setCommandLine(
									"git", "commit", "-m", message);
							}

						});
				}

			});

		task.setDescription(
			"Installs and commits the project to the local Gradle cache for " +
				"testing.");
		task.setGroup(BasePlugin.UPLOAD_GROUP);

		return task;
	}

	private Copy _addTaskCopyLibs(Project project) {
		Copy copy = GradleUtil.addTask(
			project, COPY_LIBS_TASK_NAME, Copy.class);

		File libDir = _getLibDir(project);

		copy.eachFile(new ExcludeExistingFileAction(libDir));

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		copy.from(configuration);

		copy.into(libDir);
		copy.rename(
			new RenameDependencyClosure(project, configuration.getName()));
		copy.setEnabled(false);

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(copy);

		return copy;
	}

	private Copy _addTaskDeployDependencies(
		Project project, final LiferayExtension liferayExtension) {

		final Copy copy = GradleUtil.addTask(
			project, DEPLOY_DEPENDENCIES_TASK_NAME, Copy.class);

		final boolean keepVersions = Boolean.getBoolean(
			"deploy.dependencies.keep.versions");

		GradleUtil.setProperty(
			copy, LiferayOSGiPlugin.AUTO_CLEAN_PROPERTY_NAME, false);
		GradleUtil.setProperty(copy, "keepVersions", keepVersions);

		String renameSuffix = ".jar";

		if (keepVersions) {
			renameSuffix = "-$1.jar";
		}

		GradleUtil.setProperty(copy, "renameSuffix", renameSuffix);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getDeployDir();
				}

			});

		copy.setDescription("Deploys additional dependencies.");

		TaskOutputs taskOutputs = copy.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					copy.eachFile(new RenameDependencyAction(keepVersions));
				}

			});

		return copy;
	}

	private Copy _addTaskDownloadCompiledJSP(
		JavaCompile compileJSPTask, final Jar jarJSPsTask,
		Properties artifactProperties) {

		final String artifactJspcURL = artifactProperties.getProperty(
			"artifact.jspc.url");

		if (Validator.isNull(artifactJspcURL)) {
			return null;
		}

		final Project project = compileJSPTask.getProject();

		Copy copy = GradleUtil.addTask(
			project, DOWNLOAD_COMPILED_JSP_TASK_NAME, Copy.class);

		copy.exclude("META-INF/MANIFEST.MF");

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					File file;

					try {
						file = FileUtil.get(project, artifactJspcURL);
					}
					catch (Exception e) {
						String message = e.getMessage();

						if (!message.equals("HTTP Authorization failure")) {
							throw e;
						}

						int start = artifactJspcURL.lastIndexOf('/');

						start = artifactJspcURL.indexOf('-', start) + 1;

						String classifier = jarJSPsTask.getClassifier();
						String extension = jarJSPsTask.getExtension();

						int end =
							artifactJspcURL.length() - classifier.length() -
								extension.length() - 2;

						String version = artifactJspcURL.substring(start, end);

						DependencyHandler dependencyHandler =
							project.getDependencies();

						Map<String, Object> args = new HashMap<>();

						args.put("classifier", classifier);
						args.put("ext", extension);
						args.put("group", project.getGroup());
						args.put(
							"name", GradleUtil.getArchivesBaseName(project));
						args.put("version", version);

						Dependency dependency = dependencyHandler.create(args);

						ConfigurationContainer configurationContainer =
							project.getConfigurations();

						Configuration configuration =
							configurationContainer.detachedConfiguration(
								dependency);

						file = configuration.getSingleFile();
					}

					return project.zipTree(file);
				}

			});

		copy.setDescription(
			"Downloads the latest compiled JSP classes for this project.");
		copy.setDestinationDir(compileJSPTask.getDestinationDir());
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	private InstallCacheTask _addTaskInstallCache(final Project project) {
		InstallCacheTask installCacheTask = GradleUtil.addTask(
			project, INSTALL_CACHE_TASK_NAME, InstallCacheTask.class);

		installCacheTask.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(installCacheTask.getName()),
			MavenPlugin.INSTALL_TASK_NAME);

		installCacheTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					String result = GitUtil.getGitResult(
						task.getProject(), "status", "--porcelain", ".");

					if (Validator.isNotNull(result)) {
						throw new GradleException(
							"Unable to install project to the local Gradle " +
								"cache, commit changes first");
					}
				}

			});

		installCacheTask.setArtifactGroup(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getGroup();
				}

			});

		installCacheTask.setArtifactName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return GradleUtil.getArchivesBaseName(project);
				}

			});

		installCacheTask.setArtifactVersion(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});

		installCacheTask.setDescription(
			"Installs the project to the local Gradle cache for testing.");
		installCacheTask.setGroup(BasePlugin.UPLOAD_GROUP);

		GradleUtil.setProperty(
			installCacheTask, LiferayOSGiPlugin.AUTO_CLEAN_PROPERTY_NAME,
			false);

		return installCacheTask;
	}

	private Jar _addTaskJarJavadoc(Project project) {
		Jar jar = GradleUtil.addTask(project, JAR_JAVADOC_TASK_NAME, Jar.class);

		jar.setClassifier("javadoc");
		jar.setDescription(
			"Assembles a jar archive containing the Javadoc files for this " +
				"project.");
		jar.setGroup(BasePlugin.BUILD_GROUP);

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		jar.from(javadoc);

		return jar;
	}

	private Jar _addTaskJarJSP(Project project) {
		Jar jar = GradleUtil.addTask(project, JAR_JSP_TASK_NAME, Jar.class);

		jar.setClassifier("jspc");
		jar.setDescription(
			"Assembles a jar archive containing the compiled JSP classes for " +
				"this project.");
		jar.setGroup(BasePlugin.BUILD_GROUP);
		jar.setIncludeEmptyDirs(false);

		JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
			project, JspCPlugin.COMPILE_JSP_TASK_NAME);

		jar.from(javaCompile);

		return jar;
	}

	private Jar _addTaskJarSources(Project project, boolean testProject) {
		Jar jar = GradleUtil.addTask(project, JAR_SOURCES_TASK_NAME, Jar.class);

		jar.setClassifier("sources");
		jar.setGroup(BasePlugin.BUILD_GROUP);
		jar.setDescription(
			"Assembles a jar archive containing the main source files.");
		jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);

		File docrootDir = project.file("docroot");

		if (docrootDir.exists()) {
			jar.from(docrootDir);
		}
		else {
			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, SourceSet.MAIN_SOURCE_SET_NAME);

			jar.from(sourceSet.getAllSource());

			if (testProject) {
				sourceSet = GradleUtil.getSourceSet(
					project, SourceSet.TEST_SOURCE_SET_NAME);

				jar.from(sourceSet.getAllSource());

				sourceSet = GradleUtil.getSourceSet(
					project,
					TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

				jar.from(sourceSet.getAllSource());
			}
		}

		return jar;
	}

	private Jar _addTaskJarTLDDoc(Project project) {
		Jar jar = GradleUtil.addTask(project, JAR_TLDDOC_TASK_NAME, Jar.class);

		jar.setClassifier("taglibdoc");
		jar.setDescription(
			"Assembles a jar archive containing the Tag Library " +
				"Documentation files for this project.");
		jar.setGroup(BasePlugin.BUILD_GROUP);

		TLDDocTask tlddocTask = (TLDDocTask)GradleUtil.getTask(
			project, TLDDocBuilderPlugin.TLDDOC_TASK_NAME);

		jar.from(tlddocTask);

		return jar;
	}

	private ReplaceRegexTask _addTaskSyncVersions(final Project project) {
		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, SYNC_VERSIONS_TASK_NAME, ReplaceRegexTask.class);

		_configureTaskReplaceRegexJSMatches(replaceRegexTask);

		replaceRegexTask.setDescription(
			"Updates the version in additional project files based on the " +
				"current Bundle-Version.");

		if (!FileUtil.exists(project, "bnd.bnd")) {
			replaceRegexTask.setEnabled(false);
		}

		replaceRegexTask.setReplacement(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					File bndFile = project.file("bnd.bnd");

					Properties properties = GUtil.loadProperties(bndFile);

					return properties.getProperty(Constants.BUNDLE_VERSION);
				}

			});

		return replaceRegexTask;
	}

	private ReplaceRegexTask _addTaskUpdateFileSnapshotVersions(
		final Project project) {

		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, UPDATE_FILE_SNAPSHOT_VERSIONS_TASK_NAME,
			ReplaceRegexTask.class);

		replaceRegexTask.setDescription(
			"Updates the project version in external files to the latest " +
				"snapshot.");

		String regex = _getModuleSnapshotDependencyRegex(project);

		Map<String, Object> args = new HashMap<>();

		File rootDir = project.getRootDir();

		args.put("dir", rootDir.getParentFile());

		args.put("include", "**/build.gradle");

		replaceRegexTask.setMatches(
			Collections.singletonMap(
				regex, (FileCollection)project.fileTree(args)));

		replaceRegexTask.setReplacement(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _getNexusLatestSnapshotVersion(project);
				}

			});

		return replaceRegexTask;
	}

	private ReplaceRegexTask _addTaskUpdateFileVersions(
		final Project project, final GitRepo gitRepo) {

		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, UPDATE_FILE_VERSIONS_TASK_NAME, ReplaceRegexTask.class);

		replaceRegexTask.pre(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(String content, File file) {
					String fileName = file.getName();

					if (!fileName.equals("build.gradle")) {
						return content;
					}

					String configuration =
						ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME() +
							" ";

					return content.replaceAll(
						Pattern.quote(
							configuration + _getProjectDependency(project)),
						Matcher.quoteReplacement(
							configuration +
								_getModuleDependency(project, true)));
				}

			});

		replaceRegexTask.replaceOnlyIf(
			new Closure<Boolean>(project) {

				@SuppressWarnings("unused")
				public Boolean doCall(
					String group, String replacement, String content,
					File contentFile) {

					GitRepo contentGitRepo = _getGitRepo(
						contentFile.getParentFile());

					if ((contentGitRepo != null) && contentGitRepo.readOnly &&
						((gitRepo == null) ||
						 !contentGitRepo.dir.equals(gitRepo.dir))) {

						return false;
					}

					String projectPath = project.getPath();

					if (!projectPath.startsWith(":apps:") &&
						!projectPath.startsWith(":core:") &&
						!projectPath.startsWith(":private:") &&
						(gitRepo == null)) {

						return true;
					}

					Version groupVersion = _getVersion(group);
					Version replacementVersion = _getVersion(replacement);

					if ((groupVersion == null) ||
						(replacementVersion == null) ||
						(groupVersion.getMajor() !=
							replacementVersion.getMajor())) {

						return true;
					}

					return false;
				}

			});

		replaceRegexTask.setDescription(
			"Updates the project version in external files.");

		replaceRegexTask.setReplacement(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});

		return replaceRegexTask;
	}

	private ReplaceRegexTask _addTaskUpdateVersion(Project project) {
		final ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, LiferayRelengPlugin.UPDATE_VERSION_TASK_NAME,
			ReplaceRegexTask.class);

		_configureTaskReplaceRegexJSMatches(replaceRegexTask);

		replaceRegexTask.match(_BUNDLE_VERSION_REGEX, "bnd.bnd");

		replaceRegexTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Project project = task.getProject();

					String version = String.valueOf(project.getVersion());

					if (version.contains("LIFERAY-PATCHED-")) {
						return false;
					}

					return true;
				}

			});

		replaceRegexTask.setDescription(
			"Updates the project version in the " + Constants.BUNDLE_VERSION +
				" header.");

		replaceRegexTask.setReplacement(
			IncrementVersionClosure.MICRO_INCREMENT);

		return replaceRegexTask;
	}

	private void _applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/defaults/dependencies" +
				"/config-maven.gradle",
			project);
	}

	private void _applyPlugins(Project project) {
		if (Validator.isNotNull(_getBundleInstruction(project, "Main-Class"))) {
			GradleUtil.applyPlugin(project, ApplicationPlugin.class);
		}

		GradleUtil.applyPlugin(project, BaselinePlugin.class);
		GradleUtil.applyPlugin(project, DependencyCheckerPlugin.class);
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, FindBugsPlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, MavenPlugin.class);
		GradleUtil.applyPlugin(project, OptionalBasePlugin.class);
		GradleUtil.applyPlugin(project, PmdPlugin.class);
		GradleUtil.applyPlugin(project, ProvidedBasePlugin.class);

		if (FileUtil.exists(project, "service.xml")) {
			GradleUtil.applyPlugin(project, ServiceBuilderPlugin.class);
			GradleUtil.applyPlugin(project, UpgradeTableBuilderPlugin.class);
			GradleUtil.applyPlugin(project, WSDDBuilderPlugin.class);
		}

		if (FileUtil.exists(project, "wsdl")) {
			GradleUtil.applyPlugin(project, WSDLBuilderPlugin.class);
		}

		if (FileUtil.exists(project, "xsd")) {
			GradleUtil.applyPlugin(project, XSDBuilderPlugin.class);
		}

		FindSecurityBugsPlugin.INSTANCE.apply(project);
	}

	private void _applyVersionOverrideJson(Project project, String fileName)
		throws IOException {

		File file = project.file(fileName);

		if (!file.exists()) {
			return;
		}

		Path path = file.toPath();

		_backupFilesBuildAdapter.backUp(path);

		String json = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		Matcher matcher = _jsonVersionPattern.matcher(json);

		if (!matcher.find()) {
			return;
		}

		json =
			json.substring(0, matcher.start(1)) + project.getVersion() +
				json.substring(matcher.end(1));

		Files.write(path, json.getBytes(StandardCharsets.UTF_8));
	}

	private void _applyVersionOverrides(
		Project project, File versionOverrideFile) {

		if ((versionOverrideFile == null) || !versionOverrideFile.exists()) {
			return;
		}

		final Properties versionOverrides = GUtil.loadProperties(
			versionOverrideFile);

		// Bundle-Version

		String bundleVersion = versionOverrides.getProperty(
			Constants.BUNDLE_VERSION);

		if (Validator.isNotNull(bundleVersion)) {
			Map<String, String> bundleInstructions = _getBundleInstructions(
				project);

			bundleInstructions.put(Constants.BUNDLE_VERSION, bundleVersion);

			project.setVersion(bundleVersion);

			try {
				_applyVersionOverrideJson(project, "npm-shrinkwrap.json");
				_applyVersionOverrideJson(project, "package.json");
			}
			catch (IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}

		// Dependencies

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Action<Configuration> action = new Action<Configuration>() {

			@Override
			public void execute(Configuration configuration) {
				ResolutionStrategy resolutionStrategy =
					configuration.getResolutionStrategy();

				DependencySubstitutions dependencySubstitutions =
					resolutionStrategy.getDependencySubstitution();

				for (String key : versionOverrides.stringPropertyNames()) {
					if (key.indexOf(_DEPENDENCY_KEY_SEPARATOR) == -1) {
						continue;
					}

					String dependencyNotation = key.replace(
						_DEPENDENCY_KEY_SEPARATOR, ':');

					ComponentSelector componentSelector =
						dependencySubstitutions.module(dependencyNotation);

					Substitution substitution =
						dependencySubstitutions.substitute(componentSelector);

					ComponentSelector newComponentSelector;

					String value = versionOverrides.getProperty(key);

					if (value.indexOf(':') != -1) {
						newComponentSelector = dependencySubstitutions.project(
							value);
					}
					else {
						newComponentSelector = dependencySubstitutions.module(
							dependencyNotation + ":" + value);
					}

					substitution.with(newComponentSelector);
				}
			}

		};

		configurationContainer.all(action);

		GradleInternal gradleInternal = (GradleInternal)project.getGradle();

		ServiceRegistry serviceRegistry = gradleInternal.getServices();

		ProjectConfigurer projectConfigurer = serviceRegistry.get(
			ProjectConfigurer.class);

		for (String key : versionOverrides.stringPropertyNames()) {
			if (key.indexOf(_DEPENDENCY_KEY_SEPARATOR) == -1) {
				continue;
			}

			String value = versionOverrides.getProperty(key);

			if (value.indexOf(':') == -1) {
				continue;
			}

			ProjectInternal dependencyProject =
				(ProjectInternal)project.findProject(value);

			if (dependencyProject != null) {
				projectConfigurer.configure(dependencyProject);
			}
		}

		// Package versions

		final Copy copy = (Copy)GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		copy.filesMatching(
			"**/packageinfo",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(final FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(
						new Closure<Void>(copy) {

							@SuppressWarnings("unused")
							public String doCall(String line) {
								if (Validator.isNull(line)) {
									return line;
								}

								String packagePath = fileCopyDetails.getPath();

								packagePath = packagePath.substring(
									0, packagePath.lastIndexOf('/'));

								packagePath = packagePath.replace('/', '.');

								String versionOverride =
									versionOverrides.getProperty(packagePath);

								if (Validator.isNotNull(versionOverride)) {
									return "version " + versionOverride;
								}

								return line;
							}

						});
				}

			});
	}

	private void _checkJsonVersion(Project project, String fileName) {
		File file = project.file(fileName);

		if (!file.exists()) {
			return;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> map = (Map<String, Object>)jsonSlurper.parse(file);

		String jsonVersion = (String)map.get("version");

		if (Validator.isNotNull(jsonVersion) &&
			!jsonVersion.equals(String.valueOf(project.getVersion()))) {

			throw new GradleException(
				"Version in " + fileName + " must match project version");
		}
	}

	private void _checkVersion(Project project) {
		_checkJsonVersion(project, "npm-shrinkwrap.json");
		_checkJsonVersion(project, "package.json");
	}

	private void _configureArtifacts(
		Project project, Jar jarJSPTask, Jar jarJavadocTask, Jar jarSourcesTask,
		Jar jarTLDDocTask) {

		ArtifactHandler artifactHandler = project.getArtifacts();

		if (!GradleUtil.isSnapshot(project, _SNAPSHOT_PROPERTY_NAMES)) {
			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, SourceSet.MAIN_SOURCE_SET_NAME);

			FileCollection resourcesFileCollection = sourceSet.getResources();

			FileCollection jspFileCollection = resourcesFileCollection.filter(
				new Spec<File>() {

					@Override
					public boolean isSatisfiedBy(File file) {
						String fileName = file.getName();

						if (fileName.endsWith(".jsp") ||
							fileName.endsWith(".jspf")) {

							return true;
						}

						return false;
					}

				});

			if (!jspFileCollection.isEmpty()) {
				artifactHandler.add(
					Dependency.ARCHIVES_CONFIGURATION, jarJSPTask);
			}
		}

		Spec<File> spec = new Spec<File>() {

			@Override
			public boolean isSatisfiedBy(File file) {
				String fileName = file.getName();

				if (fileName.equals("MANIFEST.MF")) {
					return false;
				}

				return true;
			}

		};

		if (FileUtil.hasSourceFiles(jarSourcesTask, spec)) {
			artifactHandler.add(
				Dependency.ARCHIVES_CONFIGURATION, jarSourcesTask);
		}

		Task javadocTask = GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		spec = new Spec<File>() {

			@Override
			public boolean isSatisfiedBy(File file) {
				String fileName = file.getName();

				if (fileName.endsWith(".java")) {
					return true;
				}

				return false;
			}

		};

		if (FileUtil.hasSourceFiles(javadocTask, spec)) {
			artifactHandler.add(
				Dependency.ARCHIVES_CONFIGURATION, jarJavadocTask);
		}

		Task tlddocTask = GradleUtil.getTask(
			project, TLDDocBuilderPlugin.TLDDOC_TASK_NAME);

		spec = new Spec<File>() {

			@Override
			public boolean isSatisfiedBy(File file) {
				String fileName = file.getName();

				if (fileName.endsWith(".tld")) {
					return true;
				}

				return false;
			}

		};

		if (FileUtil.hasSourceFiles(tlddocTask, spec)) {
			artifactHandler.add(
				Dependency.ARCHIVES_CONFIGURATION, jarTLDDocTask);
		}

		if (GradleUtil.hasPlugin(project, WSDDBuilderPlugin.class)) {
			BuildWSDDTask buildWSDDTask = (BuildWSDDTask)GradleUtil.getTask(
				project, WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME);

			if (buildWSDDTask.getEnabled()) {
				Task buildWSDDJarTask = GradleUtil.getTask(
					project, buildWSDDTask.getName() + "Jar");

				artifactHandler.add(
					Dependency.ARCHIVES_CONFIGURATION, buildWSDDJarTask,
					new Closure<Void>(project) {

						@SuppressWarnings("unused")
						public void doCall(
							ArchivePublishArtifact archivePublishArtifact) {

							archivePublishArtifact.setClassifier("wsdd");
						}

					});
			}
		}
	}

	private void _configureBasePlugin(Project project, File portalRootDir) {
		if (portalRootDir == null) {
			return;
		}

		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		File dir = new File(portalRootDir, "tools/sdk/dist");

		String dirName = FileUtil.relativize(dir, project.getBuildDir());

		basePluginConvention.setDistsDirName(dirName);
		basePluginConvention.setLibsDirName(dirName);
	}

	private void _configureBundleDefaultInstructions(
		Project project, File portalRootDir, boolean publishing) {

		LiferayOSGiExtension liferayOSGiExtension = GradleUtil.getExtension(
			project, LiferayOSGiExtension.class);

		Map<String, Object> bundleDefaultInstructions = new HashMap<>();

		bundleDefaultInstructions.put(Constants.BUNDLE_VENDOR, "Liferay, Inc.");
		bundleDefaultInstructions.put(
			Constants.DONOTCOPY,
			"(" + LiferayOSGiExtension.DONOTCOPY_DEFAULT + "|.touch)");
		bundleDefaultInstructions.put(
			Constants.FIXUPMESSAGES + ".deprecated",
			"annotations are deprecated");
		bundleDefaultInstructions.put(Constants.SOURCES, "false");

		if (publishing) {
			bundleDefaultInstructions.put(
				"Git-Descriptor",
				"${system-allow-fail;git describe --dirty --always}");
			bundleDefaultInstructions.put(
				"Git-SHA", "${system-allow-fail;git rev-list -1 HEAD}");
		}

		File appBndFile = _getAppBndFile(project, portalRootDir);

		if (appBndFile != null) {
			bundleDefaultInstructions.put(
				Constants.INCLUDE,
				FileUtil.getRelativePath(project, appBndFile));
		}

		File packageJsonFile = project.file("package.json");

		if (packageJsonFile.exists()) {
			bundleDefaultInstructions.put(
				Constants.INCLUDERESOURCE + ".packagejson",
				FileUtil.getRelativePath(project, packageJsonFile));
		}

		liferayOSGiExtension.bundleDefaultInstructions(
			bundleDefaultInstructions);
	}

	private void _configureBundleInstructions(
		Project project, GitRepo gitRepo) {

		Map<String, String> bundleInstructions = _getBundleInstructions(
			project);

		String projectPath = project.getPath();

		if (projectPath.startsWith(":apps:") ||
			projectPath.startsWith(":private:") || (gitRepo != null)) {

			String exportPackage = bundleInstructions.get(
				Constants.EXPORT_PACKAGE);

			if (Validator.isNotNull(exportPackage)) {
				exportPackage = "!com.liferay.*.kernel.*," + exportPackage;

				bundleInstructions.put(Constants.EXPORT_PACKAGE, exportPackage);
			}
		}

		if (!bundleInstructions.containsKey(Constants.EXPORT_CONTENTS) &&
			!bundleInstructions.containsKey("-check")) {

			bundleInstructions.put("-check", "exports");
		}
	}

	private void _configureConfiguration(Configuration configuration) {
		DependencySet dependencySet = configuration.getDependencies();

		dependencySet.withType(
			ExternalDependency.class,
			new Action<ExternalDependency>() {

				@Override
				public void execute(ExternalDependency externalDependency) {
					String version = externalDependency.getVersion();

					if (version.endsWith(GradleUtil.SNAPSHOT_VERSION_SUFFIX)) {
						throw new GradleException(
							"Please use a timestamp version for " +
								externalDependency);
					}
				}

			});

		dependencySet.withType(
			ModuleDependency.class,
			new Action<ModuleDependency>() {

				@Override
				public void execute(ModuleDependency moduleDependency) {
					String name = moduleDependency.getName();

					if (name.equals(
							"com.liferay.arquillian.arquillian-container-" +
								"liferay") ||
						name.equals(
							"com.liferay.arquillian.extension.junit.bridge")) {

						moduleDependency.exclude(
							Collections.singletonMap("group", _GROUP_PORTAL));
					}
				}

			});
	}

	private void _configureConfigurationDefault(Project project) {
		final Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration providedConfiguration = GradleUtil.getConfiguration(
			project, ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME());

		DependencySet dependencySet = providedConfiguration.getDependencies();

		dependencySet.withType(
			ProjectDependency.class,
			new Action<ProjectDependency>() {

				@Override
				public void execute(ProjectDependency projectDependency) {
					defaultConfiguration.exclude(
						Collections.singletonMap(
							"module", projectDependency.getName()));
				}

			});
	}

	private void _configureConfigurationJspC(
		final Project project, final LiferayExtension liferayExtension) {

		final Logger logger = project.getLogger();
		final boolean compilingJSP = GradleUtil.hasStartParameterTask(
			project, JspCPlugin.COMPILE_JSP_TASK_NAME);

		GradleInternal gradleInternal = (GradleInternal)project.getGradle();

		ServiceRegistry serviceRegistry = gradleInternal.getServices();

		final ProjectConfigurer projectConfigurer = serviceRegistry.get(
			ProjectConfigurer.class);

		final Configuration configuration = GradleUtil.getConfiguration(
			project, JspCPlugin.CONFIGURATION_NAME);

		DependencySet dependencySet = configuration.getAllDependencies();

		dependencySet.withType(
			ExternalModuleDependency.class,
			new Action<ExternalModuleDependency>() {

				@Override
				public void execute(
					ExternalModuleDependency externalModuleDependency) {

					String group = externalModuleDependency.getGroup();
					String name = externalModuleDependency.getName();

					if (_isTaglibDependency(group, name)) {
						String projectName = name.substring(12);

						projectName = projectName.replace('.', '-');

						Project taglibProject = GradleUtil.getProject(
							project.getRootProject(), projectName);

						if (taglibProject != null) {
							LogLevel logLevel = LogLevel.INFO;

							if (compilingJSP) {
								logLevel = LogLevel.LIFECYCLE;
							}

							logger.log(
								logLevel,
								"Compiling JSP files of {} with {} as " +
									"dependency in place of '{}:{}:{}'",
								project, taglibProject, group, name,
								externalModuleDependency.getVersion());

							projectConfigurer.configure(
								(ProjectInternal)taglibProject);

							GradleUtil.substituteModuleDependencyWithProject(
								configuration, externalModuleDependency,
								taglibProject);
						}
						else {
							Map<String, String> args = new HashMap<>();

							args.put("group", group);
							args.put("module", name);

							configuration.exclude(args);
						}
					}
					else if (_isUtilTaglibDependency(group, name)) {
						Map<String, String> args = new HashMap<>();

						args.put("group", group);
						args.put("module", name);

						configuration.exclude(args);
					}
				}

			});

		ResolvableDependencies resolvableDependencies =
			configuration.getIncoming();

		Action<ResolvableDependencies> action =
			new Action<ResolvableDependencies>() {

				@Override
				public void execute(
					ResolvableDependencies resolvableDependencies) {

					for (ExcludeRule excludeRule :
							configuration.getExcludeRules()) {

						String group = excludeRule.getGroup();
						String name = excludeRule.getModule();

						File file;

						if (_isTaglibDependency(group, name)) {
							File dir = new File(
								liferayExtension.getLiferayHome(), "osgi");
							String fileName = name + ".jar";

							try {
								file = FileUtil.findFile(dir, fileName);
							}
							catch (IOException ioe) {
								throw new UncheckedIOException(ioe);
							}

							if (file == null) {
								throw new GradleException(
									"Unable to find " + fileName + " in " +
										dir);
							}
						}
						else if (_isUtilTaglibDependency(group, name)) {
							file = new File(
								liferayExtension.getAppServerPortalDir(),
								"WEB-INF/lib/util-taglib.jar");

							if (!file.exists()) {
								throw new GradleException(
									"Unable to find " + file);
							}
						}
						else {
							return;
						}

						if (logger.isLifecycleEnabled()) {
							logger.lifecycle(
								"Compiling JSP files of {} with {} as " +
									"dependency in place of '{}:{}'",
								project, file.getAbsolutePath(), group, name);
						}

						GradleUtil.addDependency(
							project, configuration.getName(), file);
					}
				}

			};

		resolvableDependencies.beforeResolve(action);
	}

	private void _configureConfigurationNoCache(Configuration configuration) {
		ResolutionStrategy resolutionStrategy =
			configuration.getResolutionStrategy();

		resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS);
		resolutionStrategy.cacheDynamicVersionsFor(0, TimeUnit.SECONDS);
	}

	private void _configureConfigurations(
		Project project, GitRepo gitRepo, LiferayExtension liferayExtension) {

		_configureConfigurationDefault(project);
		_configureConfigurationJspC(project, liferayExtension);

		String projectPath = project.getPath();

		if (projectPath.startsWith(":apps:") ||
			projectPath.startsWith(":core:") ||
			projectPath.startsWith(":private:apps:") ||
			projectPath.startsWith(":private:core:") || (gitRepo != null)) {

			_configureConfigurationTransitive(
				project, JavaPlugin.COMPILE_CONFIGURATION_NAME, false);
			_configureConfigurationTransitive(
				project, JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME,
				false);
		}

		_configureDependenciesTransitive(
			project, LiferayOSGiPlugin.COMPILE_INCLUDE_CONFIGURATION_NAME,
			false);

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		configurationContainer.all(
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					_configureConfiguration(configuration);
				}

			});
	}

	private void _configureConfigurationTransitive(
		Project project, String name, boolean transitive) {

		Configuration configuration = GradleUtil.getConfiguration(
			project, name);

		configuration.setTransitive(transitive);
	}

	private void _configureDependenciesTransitive(
		Project project, String configurationName, final boolean transitive) {

		Configuration configuration = GradleUtil.getConfiguration(
			project, configurationName);

		DependencySet dependencySet = configuration.getAllDependencies();

		dependencySet.withType(
			ModuleDependency.class,
			new Action<ModuleDependency>() {

				@Override
				public void execute(ModuleDependency moduleDependency) {
					moduleDependency.setTransitive(transitive);
				}

			});
	}

	private void _configureDependencyChecker(Project project) {
		DependencyCheckerExtension dependencyCheckerExtension =
			GradleUtil.getExtension(project, DependencyCheckerExtension.class);

		Map<String, Object> args = new HashMap<>();

		args.put("configuration", SourceFormatterPlugin.CONFIGURATION_NAME);
		args.put("group", GradleUtil.PORTAL_TOOL_GROUP);
		args.put("maxAge", _PORTAL_TOOL_MAX_AGE);
		args.put("name", _SOURCE_FORMATTER_PORTAL_TOOL_NAME);
		args.put("throwError", Boolean.TRUE);

		dependencyCheckerExtension.maxAge(args);
	}

	private void _configureDeployDir(
		final Project project, final LiferayExtension liferayExtension,
		final boolean deployToAppServerLibs, final boolean deployToTools) {

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					if (deployToAppServerLibs) {
						return new File(
							liferayExtension.getAppServerPortalDir(),
							"WEB-INF/lib");
					}

					if (deployToTools) {
						return new File(
							liferayExtension.getLiferayHome(),
							"tools/" + project.getName());
					}

					if (FileUtil.exists(project, ".lfrbuild-static")) {
						return new File(
							liferayExtension.getLiferayHome(), "osgi/static");
					}

					String archivesBaseName = GradleUtil.getArchivesBaseName(
						project);

					if (archivesBaseName.startsWith("com.liferay.portal.")) {
						return new File(
							liferayExtension.getLiferayHome(), "osgi/portal");
					}

					return new File(
						liferayExtension.getLiferayHome(), "osgi/modules");
				}

			});
	}

	private void _configureEclipse(Project project) {
		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		XmlFileContentMerger xmlFileContentMerger = eclipseClasspath.getFile();

		xmlFileContentMerger.whenMerged(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Classpath classpath) {
					for (ClasspathEntry classpathEntry :
							classpath.getEntries()) {

						if (!(classpathEntry instanceof SourceFolder)) {
							continue;
						}

						SourceFolder sourceFolder =
							(SourceFolder)classpathEntry;

						File archetypeResourcesDir = new File(
							sourceFolder.getDir(), "archetype-resources");

						if (archetypeResourcesDir.isDirectory()) {
							List<String> excludes = sourceFolder.getExcludes();

							excludes.add("**/*.java");
						}
					}
				}

			});
	}

	private void _configureEclipse(
		Project project, Configuration portalTestConfiguration) {

		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		Collection<Configuration> plusConfigurations =
			eclipseClasspath.getPlusConfigurations();

		plusConfigurations.add(portalTestConfiguration);
	}

	private void _configureIdea(
		Project project, Configuration portalTestConfiguration) {

		IdeaModel ideaModel = GradleUtil.getExtension(project, IdeaModel.class);

		IdeaModule ideaModule = ideaModel.getModule();

		Map<String, Map<String, Collection<Configuration>>> scopes =
			ideaModule.getScopes();

		Map<String, Collection<Configuration>> testScope = scopes.get("TEST");

		Collection<Configuration> plusConfigurations = testScope.get("plus");

		plusConfigurations.add(portalTestConfiguration);
	}

	private void _configureJavaPlugin(Project project) {
		JavaPluginConvention javaPluginConvention = GradleUtil.getConvention(
			project, JavaPluginConvention.class);

		javaPluginConvention.setSourceCompatibility(_JAVA_VERSION);
		javaPluginConvention.setTargetCompatibility(_JAVA_VERSION);

		File testResultsDir = project.file("test-results/unit");

		javaPluginConvention.setTestResultsDirName(
			FileUtil.relativize(testResultsDir, project.getBuildDir()));
	}

	private void _configureLocalPortalTool(
		Project project, File portalRootDir, String configurationName,
		String portalToolName) {

		if ((portalRootDir == null) ||
			GradleUtil.getProperty(
				project, portalToolName + ".ignore.local", true)) {

			return;
		}

		File dir = new File(
			portalRootDir, "tools/sdk/dependencies/" + portalToolName + "/lib");

		if (!dir.exists()) {
			Logger logger = project.getLogger();

			if (logger.isWarnEnabled()) {
				logger.warn(
					"Unable to find {}, using default version of {}", dir,
					portalToolName);
			}

			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			project, configurationName);

		Map<String, String> args = new HashMap<>();

		args.put("group", PortalTools.GROUP);
		args.put("module", portalToolName);

		configuration.exclude(args);

		FileTree fileTree = FileUtil.getJarsFileTree(project, dir);

		GradleUtil.addDependency(project, configuration.getName(), fileTree);
	}

	private void _configureMavenConf2ScopeMappings(Project project) {
		MavenPluginConvention mavenPluginConvention = GradleUtil.getConvention(
			project, MavenPluginConvention.class);

		Conf2ScopeMappingContainer conf2ScopeMappingContainer =
			mavenPluginConvention.getConf2ScopeMappings();

		Map<Configuration, Conf2ScopeMapping> mappings =
			conf2ScopeMappingContainer.getMappings();

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME);

		mappings.remove(configuration);

		configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME);

		mappings.remove(configuration);
	}

	private void _configurePmd(Project project) {
		PmdExtension pmdExtension = GradleUtil.getExtension(
			project, PmdExtension.class);

		ResourceHandler resourceHandler = project.getResources();

		TextResourceFactory textResourceFactory = resourceHandler.getText();

		String ruleSet;

		try {
			ruleSet = FileUtil.read(
				"com/liferay/gradle/plugins/defaults/dependencies" +
					"/standard-rules.xml");
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		pmdExtension.setRuleSetConfig(textResourceFactory.fromString(ruleSet));

		List<String> ruleSets = Collections.emptyList();

		pmdExtension.setRuleSets(ruleSets);
	}

	private void _configureProject(Project project) {
		String group = GradleUtil.getGradlePropertiesValue(
			project, "project.group", _GROUP);

		project.setGroup(group);
	}

	private void _configureProjectBndProperties(
		Project project, LiferayExtension liferayExtension) {

		File appServerPortalDir = liferayExtension.getAppServerPortalDir();

		GradleUtil.setProperty(
			project, "app.server.portal.dir",
			project.relativePath(appServerPortalDir));

		File appServerLibPortalDir = new File(
			appServerPortalDir, "WEB-INF/lib");

		GradleUtil.setProperty(
			project, "app.server.lib.portal.dir",
			project.relativePath(appServerLibPortalDir));
	}

	private void _configureSourceSetClassesDir(
		Project project, SourceSet sourceSet, String classesDirName) {

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		if (FileUtil.isChild(
				sourceSetOutput.getClassesDir(), project.getBuildDir())) {

			sourceSetOutput.setClassesDir(classesDirName);
			sourceSetOutput.setResourcesDir(classesDirName);
		}
	}

	private void _configureSourceSetMain(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		_configureSourceSetClassesDir(project, sourceSet, "classes");
	}

	private void _configureSourceSetTest(
		Project project, Configuration portalConfiguration,
		Configuration portalTestConfiguration) {

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.TEST_SOURCE_SET_NAME);

		_configureSourceSetClassesDir(project, sourceSet, "test-classes/unit");

		Configuration compileClasspathConfiguration =
			GradleUtil.getConfiguration(
				project, JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME);

		sourceSet.setCompileClasspath(
			FileUtil.join(
				compileClasspathConfiguration, portalConfiguration,
				sourceSet.getCompileClasspath(), portalTestConfiguration));

		sourceSet.setRuntimeClasspath(
			FileUtil.join(
				compileClasspathConfiguration, portalConfiguration,
				sourceSet.getRuntimeClasspath(), portalTestConfiguration));
	}

	private void _configureSourceSetTestIntegration(
		Project project, Configuration portalConfiguration,
		Configuration portalTestConfiguration) {

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project,
			TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

		_configureSourceSetClassesDir(
			project, sourceSet, "test-classes/integration");

		sourceSet.setCompileClasspath(
			FileUtil.join(
				portalConfiguration, sourceSet.getCompileClasspath(),
				portalTestConfiguration));

		sourceSet.setRuntimeClasspath(
			FileUtil.join(
				portalConfiguration, sourceSet.getRuntimeClasspath(),
				portalTestConfiguration));
	}

	private void _configureTaskBaseline(BaselineTask baselineTask) {
		Project project = baselineTask.getProject();

		String reportLevel = GradleUtil.getProperty(
			project, "baseline.jar.report.level", "standard");

		boolean reportLevelIsDiff = reportLevel.equals("diff");
		boolean reportLevelIsPersist = reportLevel.equals("persist");

		if (reportLevelIsPersist && FileUtil.exists(project, "bnd.bnd")) {
			baselineTask.setBndFile("bnd.bnd");
		}

		boolean reportDiff = false;

		if (reportLevelIsDiff || reportLevelIsPersist) {
			reportDiff = true;
		}

		baselineTask.setReportDiff(reportDiff);

		boolean reportOnlyDirtyPackages = GradleUtil.getProperty(
			project, "baseline.jar.report.only.dirty.packages", true);

		baselineTask.setReportOnlyDirtyPackages(reportOnlyDirtyPackages);
	}

	private void _configureTaskBaselineSyncReleaseVersions(
		Task task, final File versionOverrideFile) {

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				try {
					_execute(task.getProject());
				}
				catch (IOException ioe) {
					throw new UncheckedIOException(ioe);
				}
			}

			private void _execute(Project project) throws IOException {
				boolean hasPackageInfoFiles = _hasPackageInfoFiles(project);

				if (versionOverrideFile != null) {

					// Get versions fixed by baseline

					Properties versions = _getVersions(
						project.getProjectDir(), null);

					// Reset to committed versions

					if (hasPackageInfoFiles) {
						GitUtil.executeGit(
							project, "checkout", "--", "bnd.bnd",
							"**/packageinfo");
					}
					else {
						GitUtil.executeGit(
							project, "checkout", "--", "bnd.bnd");
					}

					// Keep only the version overrides that are different
					// from the committed versions

					Properties committedVersions = _getVersions(
						project.getProjectDir(), null);

					_removeDuplicates(versions, committedVersions);

					// Re-add dependency version overrides

					if (versionOverrideFile.exists()) {
						Properties versionOverrides = GUtil.loadProperties(
							versionOverrideFile);

						for (String key :
								versionOverrides.stringPropertyNames()) {

							if (key.indexOf(_DEPENDENCY_KEY_SEPARATOR) == -1) {
								continue;
							}

							versions.setProperty(
								key, versionOverrides.getProperty(key));
						}
					}

					// Save version override file

					boolean addVersionOverrideFile = false;

					String versionOverrideRelativePath = project.relativePath(
						versionOverrideFile);

					String gitResult = GitUtil.getGitResult(
						project, "ls-files", versionOverrideRelativePath);

					if (Validator.isNotNull(gitResult)) {
						addVersionOverrideFile = true;
					}

					_saveVersions(
						project.getProjectDir(), versions, versionOverrideFile);

					if (versionOverrideFile.exists()) {
						addVersionOverrideFile = true;
					}

					if (addVersionOverrideFile) {
						GitUtil.executeGit(
							project, "add", versionOverrideRelativePath);
					}
				}
				else if (hasPackageInfoFiles) {
					GitUtil.executeGit(
						project, "add", "bnd.bnd", "**/packageinfo");
				}
				else {
					GitUtil.executeGit(project, "add", "bnd.bnd");
				}

				String message = project.getName() + " packageinfo";

				GitUtil.commit(project, message, true);
			}

			private boolean _hasPackageInfoFiles(Project project) {
				Map<String, Object> args = new HashMap<>();

				args.put("dir", project.getProjectDir());
				args.put("include", "src/main/resources/**/packageinfo");

				FileTree fileTree = project.fileTree(args);

				if (!fileTree.isEmpty()) {
					return true;
				}

				return false;
			}

			private void _removeDuplicates(
				Properties properties, Properties otherProperties) {

				Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();

				Iterator<Map.Entry<Object, Object>> iterator =
					entrySet.iterator();

				while (iterator.hasNext()) {
					Map.Entry<Object, Object> entry = iterator.next();

					String key = (String)entry.getKey();

					if (Objects.equals(
							entry.getValue(),
							otherProperties.getProperty(key))) {

						iterator.remove();
					}
				}
			}

		};

		task.doLast(action);

		if (task instanceof VerificationTask) {
			VerificationTask verificationTask = (VerificationTask)task;

			verificationTask.setIgnoreFailures(true);
		}
	}

	private void _configureTaskBuildService(Project project) {
		BuildServiceTask buildServiceTask =
			(BuildServiceTask)GradleUtil.getTask(
				project, ServiceBuilderPlugin.BUILD_SERVICE_TASK_NAME);

		buildServiceTask.setBuildNumberIncrement(false);
	}

	private void _configureTaskBuildWSDD(final Project project) {
		BuildWSDDTask buildWSDDTask = (BuildWSDDTask)GradleUtil.getTask(
			project, WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME);

		buildWSDDTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File dir = new File(project.getBuildDir(), "wsdd/output");

					dir.mkdirs();

					return dir;
				}

			});

		buildWSDDTask.setServerConfigFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						project.getBuildDir(),
						"wsdd/" + WSDDBuilderArgs.SERVER_CONFIG_FILE_NAME);
				}

			});

		boolean remoteServices = false;

		try {
			remoteServices = _hasRemoteServices(buildWSDDTask);
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to read " + buildWSDDTask.getInputFile(), e);
		}

		if (!remoteServices) {
			buildWSDDTask.setEnabled(false);
			buildWSDDTask.setFinalizedBy(Collections.emptySet());
		}
	}

	private void _configureTaskCheckOSGiBundleState(
		CheckOSGiBundleStateTask checkOSGiBundleState,
		final LiferayExtension liferayExtension) {

		checkOSGiBundleState.setJmxPort(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return liferayExtension.getJmxRemotePort();
				}

			});
	}

	private void _configureTaskCompileJSP(
		Project project, Jar jarJSPsTask, LiferayExtension liferayExtension) {

		boolean jspPrecompileEnabled = GradleUtil.getProperty(
			project, "jsp.precompile.enabled", false);

		if (!jspPrecompileEnabled) {
			return;
		}

		JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
			project, JspCPlugin.COMPILE_JSP_TASK_NAME);

		Properties artifactProperties = null;
		String dirName = null;

		TaskContainer taskContainer = project.getTasks();

		WritePropertiesTask recordArtifactTask =
			(WritePropertiesTask)taskContainer.findByName(
				LiferayRelengPlugin.RECORD_ARTIFACT_TASK_NAME);

		if (recordArtifactTask != null) {
			String artifactURL = null;

			File artifactPropertiesFile = recordArtifactTask.getOutputFile();

			if (artifactPropertiesFile.exists()) {
				artifactProperties = GUtil.loadProperties(
					artifactPropertiesFile);

				artifactURL = artifactProperties.getProperty("artifact.url");
			}

			if (Validator.isNotNull(artifactURL)) {
				int index = artifactURL.lastIndexOf('/');

				dirName = artifactURL.substring(
					index + 1, artifactURL.length() - 4);
			}
		}

		if (Validator.isNull(dirName)) {
			dirName =
				GradleUtil.getArchivesBaseName(project) + "-" +
					project.getVersion();
		}

		File dir = new File(
			liferayExtension.getLiferayHome(), "work/" + dirName);

		javaCompile.setDestinationDir(dir);

		boolean jspPrecompileFromSource = GradleUtil.getProperty(
			project, "jsp.precompile.from.source", true);

		if (!jspPrecompileFromSource && (artifactProperties != null)) {
			Copy copy = _addTaskDownloadCompiledJSP(
				javaCompile, jarJSPsTask, artifactProperties);

			if (copy != null) {
				javaCompile.deleteAllActions();
				javaCompile.setDependsOn(Collections.singleton(copy));
			}
		}
	}

	private void _configureTaskDeploy(
		Project project, Copy deployDepenciesTask) {

		Task deployTask = GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		deployTask.finalizedBy(deployDepenciesTask);
	}

	private void _configureTaskFindBugs(FindBugs findBugs) {
		Project project = findBugs.getProject();

		findBugs.setMaxHeapSize("3g");

		FindBugsReports findBugsReports = findBugs.getReports();

		SingleFileReport htmlReport = findBugsReports.getHtml();

		htmlReport.setEnabled(true);

		SingleFileReport xmlReport = findBugsReports.getXml();

		xmlReport.setEnabled(false);

		SourceSet sourceSet = null;

		String name = findBugs.getName();

		if (name.startsWith("findbugs")) {
			name = GUtil.toLowerCamelCase(name.substring(8));

			JavaPluginConvention javaPluginConvention =
				GradleUtil.getConvention(project, JavaPluginConvention.class);

			SourceSetContainer sourceSetContainer =
				javaPluginConvention.getSourceSets();

			sourceSet = sourceSetContainer.findByName(name);
		}

		if (sourceSet != null) {
			SourceSetOutput sourceSetOutput = sourceSet.getOutput();

			ConfigurableFileTree configurableFileTree = project.fileTree(
				sourceSetOutput.getClassesDir());

			configurableFileTree.setBuiltBy(
				Collections.singleton(sourceSetOutput));

			configurableFileTree.setIncludes(
				Collections.singleton("**/*.class"));

			findBugs.setClasses(configurableFileTree);
		}
	}

	private void _configureTaskJar(Project project, boolean testProject) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		if (testProject) {
			jar.dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME);

			SourceSet sourceSet = GradleUtil.getSourceSet(
				project,
				TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

			jar.dependsOn(sourceSet.getClassesTaskName());
		}

		jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	private void _configureTaskJarSources(final Jar jarSourcesTask) {
		final Project project = jarSourcesTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PatchTask.class,
			new Action<PatchTask>() {

				@Override
				public void execute(final PatchTask patchTask) {
					jarSourcesTask.from(
						new Callable<FileCollection>() {

							@Override
							public FileCollection call() throws Exception {
								return project.zipTree(
									patchTask.getOriginalLibSrcFile());
							}

						},
						new Closure<Void>(project) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								String originalLibSrcDirName =
									patchTask.getOriginalLibSrcDirName();

								if (originalLibSrcDirName.equals(".")) {
									return;
								}

								Map<Object, Object> leadingPathReplacementsMap =
									new HashMap<>();

								leadingPathReplacementsMap.put(
									originalLibSrcDirName, "");

								copySpec.eachFile(
									new ReplaceLeadingPathAction(
										leadingPathReplacementsMap));

								copySpec.include(originalLibSrcDirName + "/");
								copySpec.setIncludeEmptyDirs(false);
							}

						});

					jarSourcesTask.from(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return patchTask.getPatchesDir();
							}

						},
						new Closure<Void>(project) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.into("META-INF/patches");
							}

						});
				}

			});
	}

	private void _configureTaskJavaCompile(JavaCompile javaCompile) {
		CompileOptions compileOptions = javaCompile.getOptions();

		compileOptions.setEncoding(StandardCharsets.UTF_8.name());
		compileOptions.setWarnings(false);
	}

	private void _configureTaskJavadoc(Project project, File portalRootDir) {
		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		_configureTaskJavadocFilter(javadoc);
		_configureTaskJavadocOptions(javadoc, portalRootDir);
		_configureTaskJavadocTitle(javadoc);

		JavaVersion javaVersion = JavaVersion.current();

		if (javaVersion.isJava8Compatible()) {
			CoreJavadocOptions coreJavadocOptions =
				(CoreJavadocOptions)javadoc.getOptions();

			coreJavadocOptions.addStringOption("Xdoclint:none", "-quiet");
		}
	}

	private void _configureTaskJavadocFilter(Javadoc javadoc) {
		String exportPackage = _getBundleInstruction(
			javadoc.getProject(), Constants.EXPORT_PACKAGE);

		if (Validator.isNull(exportPackage)) {
			javadoc.exclude("**/");

			return;
		}

		String[] exportPackageArray = exportPackage.split(",");

		for (String pattern : exportPackageArray) {
			pattern = pattern.trim();

			boolean excludePattern = false;

			int start = 0;

			if (pattern.startsWith("!")) {
				excludePattern = true;

				start = 1;
			}

			int end = pattern.indexOf(';');

			if (end == -1) {
				end = pattern.length();
			}

			pattern = pattern.substring(start, end);

			pattern = "**/" + pattern.replace('.', '/');

			if (pattern.endsWith("/*")) {
				pattern = pattern.substring(0, pattern.length() - 1);
			}
			else {
				pattern += "/*";
			}

			if (excludePattern) {
				javadoc.exclude(pattern);
			}
			else {
				javadoc.include(pattern);
			}
		}
	}

	private void _configureTaskJavadocOptions(
		Javadoc javadoc, File portalRootDir) {

		StandardJavadocDocletOptions standardJavadocDocletOptions =
			(StandardJavadocDocletOptions)javadoc.getOptions();

		standardJavadocDocletOptions.setEncoding(StandardCharsets.UTF_8.name());

		Project project = javadoc.getProject();

		File overviewFile = null;

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getJava();

		for (File dir : sourceDirectorySet.getSrcDirs()) {
			File file = new File(dir, "overview.html");

			if (file.exists()) {
				overviewFile = file;

				break;
			}
		}

		if (overviewFile != null) {
			standardJavadocDocletOptions.setOverview(
				project.relativePath(overviewFile));
		}

		standardJavadocDocletOptions.tags("generated");

		if (portalRootDir != null) {
			File stylesheetFile = new File(
				portalRootDir, "tools/styles/javadoc.css");

			if (stylesheetFile.exists()) {
				standardJavadocDocletOptions.setStylesheetFile(stylesheetFile);
			}
		}
	}

	private void _configureTaskJavadocTitle(Javadoc javadoc) {
		Project project = javadoc.getProject();

		StringBuilder sb = new StringBuilder();

		sb.append("Module ");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append(' ');
		sb.append(project.getVersion());
		sb.append(" - ");

		String moduleName = project.getDescription();

		if (Validator.isNull(moduleName)) {
			moduleName = project.getName();
		}

		sb.append(moduleName);

		javadoc.setTitle(sb.toString());
	}

	private void _configureTaskPmd(Pmd pmd) {
		pmd.setClasspath(null);
	}

	private void _configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		publishNodeModuleTask.setModuleAuthor(
			"Nathan Cavanaugh <nathan.cavanaugh@liferay.com> " +
				"(https://github.com/natecavanaugh)");
		publishNodeModuleTask.setModuleBugsUrl("https://issues.liferay.com/");
		publishNodeModuleTask.setModuleLicense("LGPL");
		publishNodeModuleTask.setModuleMain("package.json");
		publishNodeModuleTask.setModuleRepository("liferay/liferay-portal");
	}

	private void _configureTaskReplaceRegexJSMatches(
		ReplaceRegexTask replaceRegexTask) {

		Project project = replaceRegexTask.getProject();

		File npmShrinkwrapJsonFile = project.file("npm-shrinkwrap.json");

		if (npmShrinkwrapJsonFile.exists()) {
			replaceRegexTask.match(
				_jsonVersionPattern.pattern(), npmShrinkwrapJsonFile);
		}

		File packageJsonFile = project.file("package.json");

		if (packageJsonFile.exists()) {
			replaceRegexTask.match(
				_jsonVersionPattern.pattern(), packageJsonFile);
		}
	}

	private void _configureTasksBaseline(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaselineTask.class,
			new Action<BaselineTask>() {

				@Override
				public void execute(BaselineTask baselineTask) {
					_configureTaskBaseline(baselineTask);
				}

			});
	}

	private void _configureTasksCheckOSGiBundleState(
		Project project, final LiferayExtension liferayExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			CheckOSGiBundleStateTask.class,
			new Action<CheckOSGiBundleStateTask>() {

				@Override
				public void execute(
					CheckOSGiBundleStateTask checkOSGiBundleState) {

					_configureTaskCheckOSGiBundleState(
						checkOSGiBundleState, liferayExtension);
				}

			});
	}

	private void _configureTasksEnabledIfStaleSnapshot(
		Project project, boolean testProject, String... taskNames) {

		boolean snapshotIfStale = false;

		if (project.hasProperty(SNAPSHOT_IF_STALE_PROPERTY_NAME)) {
			snapshotIfStale = GradleUtil.getProperty(
				project, SNAPSHOT_IF_STALE_PROPERTY_NAME, true);
		}

		if (!snapshotIfStale || (!testProject && _isSnapshotStale(project))) {
			return;
		}

		for (String taskName : taskNames) {
			Task task = GradleUtil.getTask(project, taskName);

			task.setDependsOn(Collections.emptySet());
			task.setEnabled(false);
			task.setFinalizedBy(Collections.emptySet());
		}
	}

	private void _configureTasksFindBugs(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FindBugs.class,
			new Action<FindBugs>() {

				@Override
				public void execute(FindBugs findBugs) {
					_configureTaskFindBugs(findBugs);
				}

			});
	}

	private void _configureTasksJavaCompile(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JavaCompile.class,
			new Action<JavaCompile>() {

				@Override
				public void execute(JavaCompile javaCompile) {
					_configureTaskJavaCompile(javaCompile);
				}

			});
	}

	private void _configureTasksPmd(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Pmd.class,
			new Action<Pmd>() {

				@Override
				public void execute(Pmd pmd) {
					_configureTaskPmd(pmd);
				}

			});
	}

	private void _configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					_configureTaskPublishNodeModule(publishNodeModuleTask);
				}

			});
	}

	private void _configureTaskTest(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		_configureTaskTestIgnoreFailures(test);
		_configureTaskTestJvmArgs(test, "junit.java.unit.gc");
		_configureTaskTestLogging(test);
	}

	private void _configureTaskTestAspectJWeaver(
		Project project, String taskName,
		final Configuration aspectJWeaverConfiguration) {

		Test test = (Test)GradleUtil.getTask(project, taskName);

		test.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Test test = (Test)task;

					test.jvmArgs(
						"-javaagent:" +
							FileUtil.getAbsolutePath(
								aspectJWeaverConfiguration.getSingleFile()));
				}

			});

		test.systemProperty(
			"org.aspectj.weaver.loadtime.configuration",
			"com/liferay/aspectj/modules/aop.xml");
	}

	private void _configureTaskTestIgnoreFailures(Test test) {
		test.setIgnoreFailures(true);
	}

	private void _configureTaskTestIntegration(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		_configureTaskTestIgnoreFailures(test);
		_configureTaskTestJvmArgs(test, "junit.java.integration.gc");
		_configureTaskTestLogging(test);

		test.systemProperty("org.apache.maven.offline", Boolean.TRUE);

		File resultsDir = project.file("test-results/integration");

		test.setBinResultsDir(new File(resultsDir, "binary/testIntegration"));

		TestTaskReports testTaskReports = test.getReports();

		JUnitXmlReport jUnitXmlReport = testTaskReports.getJunitXml();

		jUnitXmlReport.setDestination(resultsDir);
	}

	private void _configureTaskTestJvmArgs(Test test, String propertyName) {
		String jvmArgs = GradleUtil.getProperty(
			test.getProject(), propertyName, (String)null);

		if (Validator.isNotNull(jvmArgs)) {
			test.jvmArgs((Object[])jvmArgs.split("\\s+"));
		}
	}

	private void _configureTaskTestLogging(Test test) {
		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setEvents(EnumSet.allOf(TestLogEvent.class));
		testLoggingContainer.setExceptionFormat(TestExceptionFormat.FULL);
		testLoggingContainer.setStackTraceFilters(Collections.emptyList());
	}

	private void _configureTaskTlddoc(Project project, File portalRootDir) {
		if (portalRootDir == null) {
			return;
		}

		TLDDocTask tlddocTask = (TLDDocTask)GradleUtil.getTask(
			project, TLDDocBuilderPlugin.TLDDOC_TASK_NAME);

		File xsltDir = new File(portalRootDir, "tools/styles/taglibs");

		tlddocTask.setXsltDir(xsltDir);
	}

	private void _configureTaskUpdateFileVersions(
		ReplaceRegexTask updateFileVersionsTask, File portalRootDir) {

		Project project = updateFileVersionsTask.getProject();

		String regex = _getModuleDependencyRegex(project);

		Map<String, Object> args = new HashMap<>();

		if (portalRootDir == null) {
			portalRootDir = project.getRootDir();
		}

		args.put("dir", portalRootDir);
		args.put(
			"includes",
			Arrays.asList("**/*.gradle", "**/sdk/*/README.markdown"));

		updateFileVersionsTask.match(regex, project.fileTree(args));
	}

	private void _configureTaskUpdateVersionForCachePlugin(
		ReplaceRegexTask updateVersionTask) {

		Project project = updateVersionTask.getProject();

		CacheExtension cacheExtension = GradleUtil.getExtension(
			project, CacheExtension.class);

		for (TaskCache taskCache : cacheExtension.getTasks()) {
			String regex = "\"" + project.getName() + "@(.+?)\\/";

			Map<String, Object> args = new HashMap<>();

			args.put("dir", taskCache.getCacheDir());
			args.put("includes", Arrays.asList("config.json", "**/*.js"));

			FileTree fileTree = project.fileTree(args);

			updateVersionTask.match(regex, fileTree);

			updateVersionTask.finalizedBy(taskCache.getRefreshDigestTaskName());
		}
	}

	private void _configureTaskUploadArchives(
		Project project, boolean testProject,
		ReplaceRegexTask updateFileVersionsTask,
		ReplaceRegexTask updateVersionTask) {

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		if (testProject) {
			uploadArchivesTask.setDependsOn(Collections.emptySet());
			uploadArchivesTask.setEnabled(false);
			uploadArchivesTask.setFinalizedBy(Collections.emptySet());

			return;
		}

		if (GradleUtil.isSnapshot(project)) {
			return;
		}

		TaskContainer taskContainer = project.getTasks();

		TaskCollection<PublishNodeModuleTask> publishNodeModuleTasks =
			taskContainer.withType(PublishNodeModuleTask.class);

		uploadArchivesTask.dependsOn(publishNodeModuleTasks);

		uploadArchivesTask.finalizedBy(
			updateFileVersionsTask, updateVersionTask);
	}

	private void _forceProjectDependenciesEvaluation(Project project) {
		GradleInternal gradleInternal = (GradleInternal)project.getGradle();

		ServiceRegistry serviceRegistry = gradleInternal.getServices();

		final ProjectConfigurer projectConfigurer = serviceRegistry.get(
			ProjectConfigurer.class);

		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		for (Configuration configuration :
				eclipseClasspath.getPlusConfigurations()) {

			DependencySet dependencySet = configuration.getAllDependencies();

			dependencySet.withType(
				ProjectDependency.class,
				new Action<ProjectDependency>() {

					@Override
					public void execute(ProjectDependency projectDependency) {
						Project dependencyProject =
							projectDependency.getDependencyProject();

						projectConfigurer.configure(
							(ProjectInternal)dependencyProject);
					}

				});
		}
	}

	private File _getAppBndFile(Project project, File portalRootDir) {
		File dir = GradleUtil.getRootDir(project, _APP_BND_FILE_NAME);

		if (dir != null) {
			return new File(dir, _APP_BND_FILE_NAME);
		}

		File modulesDir = new File(portalRootDir, "modules");

		File modulesPrivateDir = new File(modulesDir, "private");

		if (!FileUtil.isChild(project.getProjectDir(), modulesPrivateDir)) {
			return null;
		}

		String path = FileUtil.relativize(
			project.getProjectDir(), modulesPrivateDir);

		if (File.pathSeparatorChar != '/') {
			path = path.replace(File.pathSeparatorChar, '/');
		}

		while (true) {
			File file = new File(modulesDir, path + "/" + _APP_BND_FILE_NAME);

			if (file.exists()) {
				return file;
			}

			int index = path.lastIndexOf('/');

			if (index == -1) {
				return null;
			}

			path = path.substring(0, index);
		}
	}

	private long _getArtifactLastModifiedTime(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		Map<String, Object> dependencyNotation = new HashMap<>();

		dependencyNotation.put("group", project.getGroup());
		dependencyNotation.put("name", GradleUtil.getArchivesBaseName(project));
		dependencyNotation.put("version", "latest.integration");

		Dependency dependency = dependencyHandler.create(dependencyNotation);

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration =
			configurationContainer.detachedConfiguration(dependency);

		_configureConfigurationNoCache(configuration);

		File file = CollectionUtils.single(configuration.resolve());

		if (GradleUtil.isFromMavenLocal(project, file)) {
			throw new GradleException(
				"Please delete " + file.getParent() + " and try again");
		}

		try (JarFile jarFile = new JarFile(file)) {
			Manifest manifest = jarFile.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			String lastModified = attributes.getValue(
				Constants.BND_LASTMODIFIED);

			return Long.valueOf(lastModified);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private String _getBundleInstruction(Project project, String key) {
		Map<String, String> bundleInstructions = _getBundleInstructions(
			project);

		return bundleInstructions.get(key);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> _getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return (Map<String, String>)bundleExtension.getInstructions();
	}

	private GitRepo _getGitRepo(File dir) {
		dir = GradleUtil.getRootDir(dir, _GIT_REPO_FILE_NAME);

		if (dir == null) {
			return null;
		}

		String content;

		try {
			File file = new File(dir, _GIT_REPO_FILE_NAME);

			content = new String(
				Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		boolean readOnly = false;

		if (content.contains("mode = pull")) {
			readOnly = true;
		}

		return new GitRepo(dir, readOnly);
	}

	private File _getLibDir(Project project) {
		File docrootDir = project.file("docroot");

		if (docrootDir.exists()) {
			return new File(docrootDir, "WEB-INF/lib");
		}

		return project.file("lib");
	}

	private String _getModuleDependency(
		Project project, boolean roundToMinorVersion) {

		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append("\", version: \"");

		String versionString = String.valueOf(project.getVersion());

		if (roundToMinorVersion) {
			Version version = _getVersion(versionString);

			if (version != null) {
				version = new Version(
					version.getMajor(), version.getMinor(), 0);

				versionString = version.toString();
			}
		}

		sb.append(versionString);

		sb.append('"');

		return sb.toString();
	}

	private String _getModuleDependencyRegex(Project project) {
		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append("\", version: \"");

		return Pattern.quote(sb.toString()) + "(\\d.+)\"";
	}

	private String _getModuleSnapshotDependencyRegex(Project project) {
		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append("\", version: \"");

		return Pattern.quote(sb.toString()) +
			"(\\d+\\.\\d+\\.\\d+-\\d{8}\\.\\d{6}-\\d+)\"";
	}

	private String _getNexusLatestSnapshotVersion(Project project)
		throws Exception {

		Upload upload = (Upload)GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		RepositoryHandler repositoryHandler = upload.getRepositories();

		MavenDeployer mavenDeployer =
			(MavenDeployer)repositoryHandler.getByName(
				MavenRepositoryHandlerConvention.DEFAULT_MAVEN_DEPLOYER_NAME);

		Object remoteRepository = mavenDeployer.getSnapshotRepository();

		Class<?> remoteRepositoryClass = remoteRepository.getClass();

		Method getUrlMethod = remoteRepositoryClass.getMethod("getUrl");

		String repositoryUrl = (String)getUrlMethod.invoke(remoteRepository);

		int start = repositoryUrl.indexOf("/content/repositories/");

		if (start == -1) {
			throw new GradleException(
				"Unable to get Nexus repository name from " + repositoryUrl);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(repositoryUrl, 0, start);
		sb.append("/service/local/artifact/maven/resolve?g=");
		sb.append(project.getGroup());
		sb.append("&a=");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append("&v=LATEST&r=");

		start += 22;

		int end = repositoryUrl.indexOf('/', start);

		if (end == -1) {
			end = repositoryUrl.length();
		}

		sb.append(repositoryUrl, start, end);

		URL url = new URL(sb.toString());

		URLConnection urlConnection = url.openConnection();

		Method getAuthenticationMethod = remoteRepositoryClass.getMethod(
			"getAuthentication");

		Object authentication = getAuthenticationMethod.invoke(
			remoteRepository);

		Class<?> authenticationClass = authentication.getClass();

		Method getUserNameMethod = authenticationClass.getMethod("getUserName");

		String userName = (String)getUserNameMethod.invoke(authentication);

		Method getPasswordMethod = authenticationClass.getMethod("getPassword");

		String password = (String)getPasswordMethod.invoke(authentication);

		String authorization = userName + ":" + password;

		authorization =
			"Basic " +
				DatatypeConverter.printBase64Binary(authorization.getBytes());

		urlConnection.setRequestProperty("Authorization", authorization);

		try (InputStream inputStream = urlConnection.getInputStream()) {
			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(inputStream);

			Element artifactResolutionElement = document.getDocumentElement();

			NodeList versionNodeList =
				artifactResolutionElement.getElementsByTagName("version");

			Element versionElement = (Element)versionNodeList.item(0);

			return versionElement.getTextContent();
		}
	}

	private String _getProjectDependency(Project project) {
		return "project(\"" + project.getPath() + "\")";
	}

	private Version _getVersion(Object version) {
		try {
			return Version.parseVersion(String.valueOf(version));
		}
		catch (IllegalArgumentException iae) {
			return null;
		}
	}

	private File _getVersionOverrideFile(Project project, GitRepo gitRepo) {
		if ((gitRepo == null) || !gitRepo.readOnly) {
			return null;
		}

		String fileName =
			".version-override-" + project.getName() + ".properties";

		return new File(gitRepo.dir.getParentFile(), fileName);
	}

	private Properties _getVersions(
			File projectDir, Properties versionOverrides)
		throws IOException {

		final Properties versions = new Properties();

		if (versionOverrides != null) {
			versions.putAll(versionOverrides);
		}

		String bundleVersion = versions.getProperty(Constants.BUNDLE_VERSION);

		if (Validator.isNull(bundleVersion)) {
			Properties bundleProperties = GUtil.loadProperties(
				new File(projectDir, "bnd.bnd"));

			bundleVersion = bundleProperties.getProperty(
				Constants.BUNDLE_VERSION);

			if (Validator.isNotNull(bundleVersion)) {
				versions.setProperty(Constants.BUNDLE_VERSION, bundleVersion);
			}
		}

		File packageInfoRootDir = new File(projectDir, "src/main/resources");

		final Path packageInfoRootDirPath = packageInfoRootDir.toPath();

		if (Files.notExists(packageInfoRootDirPath)) {
			return versions;
		}

		Files.walkFileTree(
			packageInfoRootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path packageInfoPath = dirPath.resolve("packageinfo");

					if (Files.notExists(packageInfoPath)) {
						return FileVisitResult.CONTINUE;
					}

					Path relativePath = packageInfoRootDirPath.relativize(
						dirPath);

					String packagePath = relativePath.toString();

					packagePath = packagePath.replace(File.separatorChar, '.');

					String packageVersion = versions.getProperty(packagePath);

					if (Validator.isNotNull(packageVersion)) {
						return FileVisitResult.CONTINUE;
					}

					packageVersion = new String(
						Files.readAllBytes(packageInfoPath),
						StandardCharsets.UTF_8);

					packageVersion = packageVersion.trim();
					packageVersion = packageVersion.substring(8);

					versions.setProperty(packagePath, packageVersion);

					return FileVisitResult.CONTINUE;
				}

			});

		return versions;
	}

	private boolean _hasRemoteServices(BuildWSDDTask buildWSDDTask)
		throws Exception {

		if (FileUtil.exists(buildWSDDTask.getProject(), "server-config.wsdd")) {
			return true;
		}

		File serviceXmlFile = buildWSDDTask.getInputFile();

		if (!serviceXmlFile.exists()) {
			return false;
		}

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/nonvalidating/load-external-dtd",
			false);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(serviceXmlFile);

		Element serviceBuilderElement = document.getDocumentElement();

		NodeList entityNodeList = serviceBuilderElement.getElementsByTagName(
			"entity");

		for (int i = 0; i < entityNodeList.getLength(); i++) {
			Element entityElement = (Element)entityNodeList.item(i);

			String remoteService = entityElement.getAttribute("remote-service");

			if (Validator.isNull(remoteService) ||
				Boolean.parseBoolean(remoteService)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasTests(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.TEST_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getAllSource();

		if (!sourceDirectorySet.isEmpty()) {
			return true;
		}

		sourceSet = GradleUtil.getSourceSet(
			project,
			TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

		sourceDirectorySet = sourceSet.getAllSource();

		if (!sourceDirectorySet.isEmpty()) {
			return true;
		}

		return false;
	}

	private boolean _isPublishing(Project project) {
		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		List<String> taskNames = startParameter.getTaskNames();

		if (taskNames.contains(MavenPlugin.INSTALL_TASK_NAME) ||
			taskNames.contains(BasePlugin.UPLOAD_ARCHIVES_TASK_NAME)) {

			return true;
		}

		return false;
	}

	private boolean _isSnapshotStale(Project project) {
		Logger logger = project.getLogger();

		long lastModifiedTime;

		try {
			lastModifiedTime = _getArtifactLastModifiedTime(project);
		}
		catch (ResolveException re) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"Unable to get artifact last modified time for " + project +
						", a new snapshot will be published",
					re);
			}

			return true;
		}

		// Remove milliseconds from Unix epoch

		lastModifiedTime = lastModifiedTime / 1000;

		String result = GitUtil.getGitResult(
			project, "log", "--format=%s", "--since=" + lastModifiedTime, ".");

		String[] lines = result.split("\\r?\\n");

		for (String line : lines) {
			if (logger.isInfoEnabled()) {
				logger.info(line);
			}

			if (Validator.isNull(line)) {
				continue;
			}

			if (!line.contains(
					WriteArtifactPublishCommandsTask.IGNORED_MESSAGE_PATTERN)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isTaglibDependency(String group, String name) {
		if (group.equals("com.liferay") && name.startsWith("com.liferay.") &&
			name.contains(".taglib")) {

			return true;
		}

		return false;
	}

	private boolean _isUtilTaglibDependency(String group, String name) {
		if (group.equals("com.liferay.portal") &&
			name.equals("com.liferay.util.taglib")) {

			return true;
		}

		return false;
	}

	private void _saveVersions(
			File projectDir, Properties versions, File versionOverrideFile)
		throws IOException {

		if (versionOverrideFile != null) {
			if (versions.isEmpty()) {
				versionOverrideFile.delete();
			}
			else {
				FileUtil.writeProperties(versionOverrideFile, versions);
			}
		}

		Path projectDirPath = projectDir.toPath();

		String version = versions.getProperty(Constants.BUNDLE_VERSION);

		if (Validator.isNotNull(version)) {
			FileUtil.replace(
				projectDirPath.resolve("bnd.bnd"), _BUNDLE_VERSION_REGEX,
				version);
		}

		Path packageInfoRootDirPath = projectDirPath.resolve(
			"src/main/resources");

		for (String key : versions.stringPropertyNames()) {
			if ((key.indexOf(_DEPENDENCY_KEY_SEPARATOR) != -1) ||
				key.equals(Constants.BUNDLE_VERSION)) {

				continue;
			}

			version = versions.getProperty(key);

			if (Validator.isNull(version)) {
				continue;
			}

			String packageInfo = "version " + version;

			Path packageDirPath = packageInfoRootDirPath.resolve(
				key.replace('.', '/'));

			Path packageInfoPath = packageDirPath.resolve("packageinfo");

			// Avoid unnecessary update if packageinfo has a trailing empty line

			String oldPackageInfo = new String(
				Files.readAllBytes(packageInfoPath), StandardCharsets.UTF_8);

			if (packageInfo.equals(oldPackageInfo.trim())) {
				continue;
			}

			Files.createDirectories(packageDirPath);

			Files.write(
				packageInfoPath, packageInfo.getBytes(StandardCharsets.UTF_8));
		}
	}

	private boolean _syncReleaseVersions(
		Project project, File portalRootDir, File versionOverrideFile,
		boolean testProject) {

		boolean syncRelease = false;

		if (project.hasProperty(SYNC_RELEASE_PROPERTY_NAME)) {
			syncRelease = GradleUtil.getProperty(
				project, SYNC_RELEASE_PROPERTY_NAME, true);
		}

		if ((portalRootDir == null) || !syncRelease || testProject ||
			!GradleUtil.hasStartParameterTask(
				project, BaselinePlugin.BASELINE_TASK_NAME)) {

			return false;
		}

		File releasePortalRootDir = GradleUtil.getProperty(
			project, RELEASE_PORTAL_ROOT_DIR_PROPERTY_NAME, (File)null);

		if (releasePortalRootDir == null) {
			throw new GradleException(
				"Please set the property \"" +
					RELEASE_PORTAL_ROOT_DIR_PROPERTY_NAME + "\".");
		}

		Logger logger = project.getLogger();

		String relativePath = FileUtil.relativize(
			project.getProjectDir(), portalRootDir);

		File releaseProjectDir = new File(releasePortalRootDir, relativePath);

		if (!releaseProjectDir.exists()) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"Unable to synchronize release versions of {}, {} does " +
						"not exist",
					project, releaseProjectDir);
			}

			return false;
		}

		if (logger.isLifecycleEnabled()) {
			logger.lifecycle(
				"Synchronizing release versions of {} with {}", project,
				releaseProjectDir);
		}

		Properties releaseVersions = null;
		Properties versions = null;

		if ((versionOverrideFile != null) && versionOverrideFile.exists()) {
			versions = GUtil.loadProperties(versionOverrideFile);
		}

		try {
			releaseVersions = _getVersions(releaseProjectDir, null);
			versions = _getVersions(project.getProjectDir(), versions);

			for (String key : releaseVersions.stringPropertyNames()) {
				if ((key.indexOf(_DEPENDENCY_KEY_SEPARATOR) != -1) ||
					!versions.containsKey(key)) {

					continue;
				}

				Version releaseVersion = Version.parseVersion(
					releaseVersions.getProperty(key));
				Version version = Version.parseVersion(
					versions.getProperty(key));

				if (releaseVersion.compareTo(version) > 0) {
					versions.setProperty(key, releaseVersion.toString());
				}
			}

			_saveVersions(
				project.getProjectDir(), versions, versionOverrideFile);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		// Reload Bundle-Version in case it is changed, so the project
		// configuration can proceed with the new version

		String bundleVersion = versions.getProperty(Constants.BUNDLE_VERSION);

		if (Validator.isNotNull(bundleVersion)) {
			project.setVersion(bundleVersion);
		}

		return true;
	}

	private static final String _APP_BND_FILE_NAME = "app.bnd";

	private static final String _BUNDLE_VERSION_REGEX =
		Constants.BUNDLE_VERSION + ": (.+)(?:\\s|$)";

	private static final String _CACHE_COMMIT_MESSAGE = "FAKE GRADLE CACHE";

	private static final char _DEPENDENCY_KEY_SEPARATOR = '/';

	private static final String _GIT_REPO_FILE_NAME = ".gitrepo";

	private static final String _GROUP = "com.liferay";

	private static final String _GROUP_PORTAL = "com.liferay.portal";

	private static final JavaVersion _JAVA_VERSION = JavaVersion.VERSION_1_7;

	private static final String _PMD_PORTAL_TOOL_NAME = "com.liferay.pmd";

	private static final Duration _PORTAL_TOOL_MAX_AGE = TimeCategory.getDays(
		30);

	private static final String _SERVICE_BUILDER_PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.service.builder";

	private static final String[] _SNAPSHOT_PROPERTY_NAMES = {
		SNAPSHOT_IF_STALE_PROPERTY_NAME
	};

	private static final String _SOURCE_FORMATTER_PORTAL_TOOL_NAME =
		"com.liferay.source.formatter";

	private static final BackupFilesBuildAdapter _backupFilesBuildAdapter =
		new BackupFilesBuildAdapter();
	private static final Pattern _jsonVersionPattern = Pattern.compile(
		"\\n\\t\"version\": \"(.+)\"");

	private static class GitRepo {

		public GitRepo(File dir, boolean readOnly) {
			this.dir = dir;
			this.readOnly = readOnly;
		}

		public final File dir;
		public final boolean readOnly;

	}

}