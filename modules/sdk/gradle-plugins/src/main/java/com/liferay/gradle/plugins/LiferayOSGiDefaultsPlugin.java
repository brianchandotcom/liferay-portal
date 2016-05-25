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

import aQute.bnd.osgi.Constants;
import aQute.bnd.version.Version;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.js.module.config.generator.ConfigJSModulesTask;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.patcher.PatchTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.tasks.BaselineTask;
import com.liferay.gradle.plugins.tasks.InstallCacheTask;
import com.liferay.gradle.plugins.tasks.ReplaceRegexTask;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.tlddoc.builder.TLDDocBuilderPlugin;
import com.liferay.gradle.plugins.tlddoc.builder.tasks.TLDDocTask;
import com.liferay.gradle.plugins.upgrade.table.builder.UpgradeTableBuilderPlugin;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.util.IncrementVersionClosure;
import com.liferay.gradle.plugins.whip.WhipPlugin;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.ExcludeExistingFileAction;
import com.liferay.gradle.util.copy.RenameDependencyClosure;
import com.liferay.gradle.util.copy.ReplaceLeadingPathAction;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.maven.Conf2ScopeMapping;
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.plugins.MavenPluginConvention;
import org.gradle.api.plugins.ReportingBasePlugin;
import org.gradle.api.plugins.quality.FindBugs;
import org.gradle.api.plugins.quality.FindBugsPlugin;
import org.gradle.api.plugins.quality.FindBugsReports;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.StopActionException;
import org.gradle.api.tasks.TaskCollection;
import org.gradle.api.tasks.TaskContainer;
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
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiDefaultsPlugin implements Plugin<Project> {

	public static final String BASELINE_CONFIGURATION_NAME = "baseline";

	public static final String BASELINE_TASK_NAME = "baseline";

	public static final String COMMIT_CACHE_TASK_NAME = "commitCache";

	public static final String COPY_LIBS_TASK_NAME = "copyLibs";

	public static final String DEFAULT_REPOSITORY_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/" +
			"public";

	public static final String INSTALL_CACHE_TASK_NAME = "installCache";

	public static final String JAR_JAVADOC_TASK_NAME = "jarJavadoc";

	public static final String JAR_SOURCES_TASK_NAME = "jarSources";

	public static final String JAR_TLDDOC_TASK_NAME = "jarTLDDoc";

	public static final String PORTAL_TEST_CONFIGURATION_NAME = "portalTest";

	public static final String UPDATE_FILE_VERSIONS_TASK_NAME =
		"updateFileVersions";

	@Override
	public void apply(final Project project) {
		GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		List<String> taskNames = startParameter.getTaskNames();

		final File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");
		final boolean publishing = isPublishing(project);
		boolean testProject = isTestProject(project);

		applyPlugins(project);

		// applyConfigScripts configures the "install" and "uploadArchives"
		// tasks, and this causes the conf2ScopeMappings.mappings convention
		// property to be cloned in a second map. Because we want to change
		// the default mappings, we must call configureMavenConf2ScopeMappings
		// before applyConfigScripts.

		configureMavenConf2ScopeMappings(project);

		applyConfigScripts(project);

		if (testProject || hasTests(project)) {
			GradleUtil.applyPlugin(project, WhipDefaultsPlugin.class);
			GradleUtil.applyPlugin(project, WhipPlugin.class);

			Configuration portalConfiguration = GradleUtil.getConfiguration(
				project, LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);
			Configuration portalTestConfiguration = addConfigurationPortalTest(
				project);

			addDependenciesPortalTest(project);
			addDependenciesTestCompile(project);
			configureEclipse(project, portalTestConfiguration);
			configureIdea(project, portalTestConfiguration);
			configureSourceSetTest(
				project, portalConfiguration, portalTestConfiguration);
			configureSourceSetTestIntegration(
				project, portalConfiguration, portalTestConfiguration);
		}

		Configuration baselineConfiguration = null;

		if (hasBaseline(project)) {
			baselineConfiguration = addConfigurationBaseline(project);
		}

		addTaskBaseline(project, baselineConfiguration);

		InstallCacheTask installCacheTask = addTaskInstallCache(project);

		addTaskCommitCache(project, installCacheTask);

		addTaskCopyLibs(project);

		final Jar jarJavadocTask = addTaskJarJavadoc(project);
		final Jar jarSourcesTask = addTaskJarSources(project, testProject);
		final Jar jarTLDDocTask = addTaskJarTLDDoc(project);

		final ReplaceRegexTask updateFileVersionsTask =
			addTaskUpdateFileVersions(project);
		final ReplaceRegexTask updateVersionTask = addTaskUpdateVersion(
			project);

		configureBasePlugin(project, portalRootDir);
		configureBundleDefaultInstructions(project, portalRootDir, publishing);
		configureConfigurations(project);
		configureDeployDir(project);
		configureJavaPlugin(project);
		configureProject(project);
		configureRepositories(project);
		configureSourceSetMain(project);
		configureTaskJar(project, testProject);
		configureTaskJavadoc(project);
		configureTaskTest(project);
		configureTaskTestIntegration(project);
		configureTaskTlddoc(project, portalRootDir);
		configureTasksBaseline(project);
		configureTasksFindBugs(project);
		configureTasksJavaCompile(project);
		configureTasksPublishNodeModule(project);

		GradleUtil.withPlugin(
			project, ServiceBuilderPlugin.class,
			new Action<ServiceBuilderPlugin>() {

				@Override
				public void execute(ServiceBuilderPlugin serviceBuilderPlugin) {
					configureLocalPortalTool(
						project, portalRootDir,
						ServiceBuilderPlugin.CONFIGURATION_NAME,
						ServiceBuilderDefaultsPlugin.PORTAL_TOOL_NAME);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					checkVersion(project);

					configureArtifacts(
						project, jarJavadocTask, jarSourcesTask, jarTLDDocTask);
					configureTaskJarSources(jarSourcesTask);
					configureTaskUpdateFileVersions(
						updateFileVersionsTask, portalRootDir);

					GradleUtil.setProjectSnapshotVersion(project);

					// setProjectSnapshotVersion must be called before
					// configureTaskUploadArchives, because the latter one needs
					// to know if we are publishing a snapshot or not.

					configureTaskUploadArchives(
						project, updateFileVersionsTask, updateVersionTask);

					configureProjectBndProperties(project);
				}

			});

		if (taskNames.contains(EclipsePlugin.getECLIPSE_TASK_NAME()) ||
			taskNames.contains("idea")) {

			forceProjectDependenciesEvaluation(project);
		}

		TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

		taskExecutionGraph.whenReady(
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(TaskExecutionGraph taskExecutionGraph) {
					Task jarTask = GradleUtil.getTask(
						project, JavaPlugin.JAR_TASK_NAME);

					if (taskExecutionGraph.hasTask(jarTask)) {
						configureBundleInstructions(project);
					}
				}

			});
	}

	protected static void configureRepositories(Project project) {
		RepositoryHandler repositoryHandler = project.getRepositories();

		if (!_MAVEN_LOCAL_IGNORE) {
			repositoryHandler.mavenLocal();
		}

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(_REPOSITORY_URL);
				}

			});
	}

	protected static boolean isTestProject(Project project) {
		String projectName = project.getName();

		if (projectName.endsWith("-test")) {
			return true;
		}

		return false;
	}

	protected Configuration addConfigurationBaseline(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, BASELINE_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addDependenciesBaseline(project);
				}

			});

		configuration.setDescription(
			"Configures the previous released version of this project for " +
				"baselining.");
		configuration.setVisible(false);

		ResolutionStrategy resolutionStrategy =
			configuration.getResolutionStrategy();

		resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS);
		resolutionStrategy.cacheDynamicVersionsFor(0, TimeUnit.SECONDS);

		return configuration;
	}

	protected Configuration addConfigurationPortalTest(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_TEST_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay portal test utility artifacts for this " +
				"project.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addDependenciesBaseline(Project project) {
		GradleUtil.addDependency(
			project, BASELINE_CONFIGURATION_NAME,
			String.valueOf(project.getGroup()),
			GradleUtil.getArchivesBaseName(project),
			"(," + String.valueOf(project.getVersion()) + ")", false);
	}

	protected void addDependenciesPortalTest(Project project) {
		GradleUtil.addDependency(
			project, PORTAL_TEST_CONFIGURATION_NAME, "com.liferay.portal",
			"com.liferay.portal.test", "default");
		GradleUtil.addDependency(
			project, PORTAL_TEST_CONFIGURATION_NAME, "com.liferay.portal",
			"com.liferay.portal.test.integration", "default");
	}

	protected void addDependenciesTestCompile(Project project) {
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

	protected Task addTaskBaseline(
		final Project project, final Configuration baselineConfiguration) {

		Task task = null;

		if (baselineConfiguration != null) {
			GradleUtil.applyPlugin(project, ReportingBasePlugin.class);

			BaselineTask baselineTask = GradleUtil.addTask(
				project, BASELINE_TASK_NAME, BaselineTask.class);

			final Jar jar = (Jar)GradleUtil.getTask(
				project, JavaPlugin.JAR_TASK_NAME);

			baselineTask.dependsOn(jar);

			baselineTask.doFirst(
				new Action<Task>() {

					@Override
					public void execute(Task task) {
						BaselineTask baselineTask = (BaselineTask)task;

						File oldJarFile = baselineTask.getOldJarFile();

						if (GradleUtil.isFromMavenLocal(project, oldJarFile)) {
							throw new GradleException(
								"Please delete " + oldJarFile.getParent() +
									" and try again");
						}
					}

				});

			baselineTask.setNewJarFile(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return jar.getArchivePath();
					}

				});

			baselineTask.setOldJarFile(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return baselineConfiguration.getSingleFile();
					}

				});

			baselineTask.setSourceDir(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						SourceSet sourceSet = GradleUtil.getSourceSet(
							project, SourceSet.MAIN_SOURCE_SET_NAME);

						return GradleUtil.getSrcDir(sourceSet.getResources());
					}

				});

			task = baselineTask;
		}
		else {
			task = project.task(BASELINE_TASK_NAME);

			task.doLast(
				new Action<Task>() {

					@Override
					public void execute(Task task) {
						if (_logger.isLifecycleEnabled()) {
							_logger.lifecycle(
								"Unable to baseline, " + project +
									" has never been released.");
						}
					}

				});
		}

		task.setDescription(
			"Compares the public API of this project with the public API of " +
				"the previous released version, if found.");
		task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		return task;
	}

	protected Task addTaskCommitCache(
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

					Project project = task.getProject();

					boolean deleted = project.delete(oldCachedVersionDir);

					if (!deleted && _logger.isWarnEnabled()) {
						_logger.warn(
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

					final String commitSubject = getGitResult(
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

	protected Copy addTaskCopyLibs(Project project) {
		Copy copy = GradleUtil.addTask(
			project, COPY_LIBS_TASK_NAME, Copy.class);

		File libDir = getLibDir(project);

		copy.eachFile(new ExcludeExistingFileAction(libDir));

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		copy.from(configuration);
		copy.into(libDir);

		Closure<String> closure = new RenameDependencyClosure(
			project, configuration.getName());

		copy.rename(closure);

		copy.setEnabled(false);

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(copy);

		return copy;
	}

	protected InstallCacheTask addTaskInstallCache(final Project project) {
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
					String result = getGitResult(
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

	protected Jar addTaskJarJavadoc(Project project) {
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

	protected Jar addTaskJarSources(Project project, boolean testProject) {
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

	protected Jar addTaskJarTLDDoc(Project project) {
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

	protected ReplaceRegexTask addTaskUpdateFileVersions(
		final Project project) {

		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, UPDATE_FILE_VERSIONS_TASK_NAME, ReplaceRegexTask.class);

		replaceRegexTask.pre(
			new Closure<String>(null) {

				@SuppressWarnings("unused")
				public String doCall(String content, File file) {
					String fileName = file.getName();

					if (!fileName.equals("build.gradle")) {
						return content;
					}

					return content.replaceAll(
						Pattern.quote(getProjectDependency(project)),
						Matcher.quoteReplacement(
							getModuleDependency(project, true)));
				}

			});

		replaceRegexTask.replaceOnlyIf(
			new Closure<Boolean>(null) {

				@SuppressWarnings("unused")
				public Boolean doCall(
					String group, String replacement, String content) {

					String projectPath = project.getPath();

					if (!projectPath.startsWith(":apps:") &&
						!projectPath.startsWith(":core:") &&
						!projectPath.startsWith(":private:") &&
						!FileUtil.exists(
							project.getRootProject(), ".gitrepo")) {

						return true;
					}

					Version groupVersion = getVersion(group);
					Version replacementVersion = getVersion(replacement);

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
		replaceRegexTask.setIgnoreUnmatched(true);

		replaceRegexTask.setReplacement(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});

		return replaceRegexTask;
	}

	protected ReplaceRegexTask addTaskUpdateVersion(Project project) {
		final ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, LiferayRelengPlugin.UPDATE_VERSION_TASK_NAME,
			ReplaceRegexTask.class);

		replaceRegexTask.match("Bundle-Version: (.+)(?:\\s|$)", "bnd.bnd");

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

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					File moduleConfigFile = getModuleConfigFile(project);

					if ((moduleConfigFile == null) ||
						!moduleConfigFile.exists()) {

						return;
					}

					replaceRegexTask.match(
						"\\n\\t\"version\": \"(.+)\"", moduleConfigFile);
				}

			});

		return replaceRegexTask;
	}

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-maven.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, FindBugsPlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, MavenPlugin.class);
		GradleUtil.applyPlugin(project, OptionalBasePlugin.class);
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
	}

	protected void checkVersion(Project project) {
		File moduleConfigFile = getModuleConfigFile(project);

		if ((moduleConfigFile == null) || !moduleConfigFile.exists()) {
			return;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> moduleConfigMap =
			(Map<String, Object>)jsonSlurper.parse(moduleConfigFile);

		String moduleConfigVersion = (String)moduleConfigMap.get("version");

		if (Validator.isNotNull(moduleConfigVersion) &&
			!moduleConfigVersion.equals(String.valueOf(project.getVersion()))) {

			throw new GradleException(
				"Version in " + project.relativePath(moduleConfigFile) +
					" must match project version");
		}
	}

	protected void configureArtifacts(
		Project project, Jar jarJavadocTask, Jar jarSourcesTask,
		Jar jarTLDDocTask) {

		ArtifactHandler artifactHandler = project.getArtifacts();

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
	}

	protected void configureBasePlugin(Project project, File portalRootDir) {
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

	protected void configureBundleDefaultInstructions(
		Project project, File portalRootDir, boolean publishing) {

		LiferayOSGiExtension liferayOSGiExtension = GradleUtil.getExtension(
			project, LiferayOSGiExtension.class);

		Map<String, Object> bundleDefaultInstructions = new HashMap<>();

		bundleDefaultInstructions.put("-check", "all");
		bundleDefaultInstructions.put(Constants.BUNDLE_VENDOR, "Liferay, Inc.");
		bundleDefaultInstructions.put(
			Constants.DONOTCOPY,
			"(" + LiferayOSGiExtension.DONOTCOPY_DEFAULT + "|.touch" + ")");
		bundleDefaultInstructions.put(Constants.SOURCES, "false");

		if (publishing) {
			bundleDefaultInstructions.put(
				"Git-Descriptor",
				"${system-allow-fail;git describe --dirty --always}");
			bundleDefaultInstructions.put(
				"Git-SHA", "${system-allow-fail;git rev-list -1 HEAD}");
		}

		File appBndFile = getAppBndFile(project, portalRootDir);

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

	protected void configureBundleInstructions(Project project) {
		String projectPath = project.getPath();

		if (!projectPath.startsWith(":apps:") &&
			!projectPath.startsWith(":private:") &&
			!FileUtil.exists(project.getRootProject(), ".gitrepo")) {

			return;
		}

		Map<String, String> bundleInstructions = getBundleInstructions(project);

		String exportPackage = bundleInstructions.get(Constants.EXPORT_PACKAGE);

		if (Validator.isNull(exportPackage)) {
			return;
		}

		exportPackage = "!com.liferay.*.kernel.*," + exportPackage;

		bundleInstructions.put(Constants.EXPORT_PACKAGE, exportPackage);
	}

	protected void configureConfiguration(Configuration configuration) {
		DependencySet dependencySet = configuration.getDependencies();

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
							"com.liferay.arquillian.extension.junit.bridge") ||
						name.equals("com.liferay.jasper.jspc")) {

						moduleDependency.exclude(
							Collections.singletonMap(
								"group", "com.liferay.portal"));
					}
				}

			});
	}

	protected void configureConfigurationDefault(Project project) {
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

	protected void configureConfigurations(Project project) {
		configureConfigurationDefault(project);

		String projectPath = project.getPath();

		if (projectPath.startsWith(":apps:") ||
			projectPath.startsWith(":core:") ||
			projectPath.startsWith(":private:apps:") ||
			projectPath.startsWith(":private:core:") ||
			FileUtil.exists(project.getRootProject(), ".gitrepo")) {

			configureConfigurationTransitive(
				project, JavaPlugin.COMPILE_CONFIGURATION_NAME, false);
		}

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		configurationContainer.all(
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					configureConfiguration(configuration);
				}

			});
	}

	protected void configureConfigurationTransitive(
		Project project, String name, boolean transitive) {

		Configuration configuration = GradleUtil.getConfiguration(
			project, name);

		configuration.setTransitive(transitive);
	}

	protected void configureDeployDir(final Project project) {
		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
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

	protected void configureEclipse(
		Project project, Configuration portalTestConfiguration) {

		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		Collection<Configuration> plusConfigurations =
			eclipseClasspath.getPlusConfigurations();

		plusConfigurations.add(portalTestConfiguration);
	}

	protected void configureIdea(
		Project project, Configuration portalTestConfiguration) {

		IdeaModel ideaModel = GradleUtil.getExtension(project, IdeaModel.class);

		IdeaModule ideaModule = ideaModel.getModule();

		Map<String, Map<String, Collection<Configuration>>> scopes =
			ideaModule.getScopes();

		Map<String, Collection<Configuration>> testScope = scopes.get("TEST");

		Collection<Configuration> plusConfigurations = testScope.get("plus");

		plusConfigurations.add(portalTestConfiguration);
	}

	protected void configureJavaPlugin(Project project) {
		JavaPluginConvention javaPluginConvention = GradleUtil.getConvention(
			project, JavaPluginConvention.class);

		javaPluginConvention.setSourceCompatibility(_JAVA_VERSION);
		javaPluginConvention.setTargetCompatibility(_JAVA_VERSION);

		File testResultsDir = project.file("test-results/unit");

		javaPluginConvention.setTestResultsDirName(
			FileUtil.relativize(testResultsDir, project.getBuildDir()));
	}

	protected void configureLocalPortalTool(
		Project project, File portalRootDir, String configurationName,
		String portalToolName) {

		if (portalRootDir == null) {
			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			project, configurationName);

		Map<String, String> args = new HashMap<>();

		args.put("group", GradleUtil.PORTAL_TOOL_GROUP);
		args.put("module", portalToolName);

		configuration.exclude(args);

		File dir = new File(
			portalRootDir, "tools/sdk/dependencies/" + portalToolName + "/lib");

		FileTree fileTree = FileUtil.getJarsFileTree(project, dir);

		GradleUtil.addDependency(project, configuration.getName(), fileTree);
	}

	protected void configureMavenConf2ScopeMappings(Project project) {
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

	protected void configureProject(Project project) {
		project.setGroup(_GROUP);
	}

	protected void configureProjectBndProperties(Project project) {
		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		File appServerPortalDir = liferayExtension.getAppServerPortalDir();

		GradleUtil.setProperty(
			project, "app.server.portal.dir",
			project.relativePath(appServerPortalDir));

		File appServerLibPortalDir = new File(
			appServerPortalDir, "WEB-INF/lib");

		GradleUtil.setProperty(
			project, "app.server.lib.portal.dir",
			project.relativePath(appServerLibPortalDir));

		GradleUtil.setProperty(
			project, "plugin.full.version",
			String.valueOf(project.getVersion()));
	}

	protected void configureSourceSetClassesDir(
		Project project, SourceSet sourceSet, String classesDirName) {

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		if (FileUtil.isChild(
				sourceSetOutput.getClassesDir(), project.getBuildDir())) {

			sourceSetOutput.setClassesDir(classesDirName);
			sourceSetOutput.setResourcesDir(classesDirName);
		}
	}

	protected void configureSourceSetMain(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		configureSourceSetClassesDir(project, sourceSet, "classes");
	}

	protected void configureSourceSetTest(
		Project project, Configuration portalConfiguration,
		Configuration portalTestConfiguration) {

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.TEST_SOURCE_SET_NAME);

		configureSourceSetClassesDir(project, sourceSet, "test-classes/unit");

		Configuration compileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

		sourceSet.setCompileClasspath(
			FileUtil.join(
				compileConfiguration, portalConfiguration,
				sourceSet.getCompileClasspath(), portalTestConfiguration));

		sourceSet.setRuntimeClasspath(
			FileUtil.join(
				compileConfiguration, portalConfiguration,
				sourceSet.getRuntimeClasspath(), portalTestConfiguration));
	}

	protected void configureSourceSetTestIntegration(
		Project project, Configuration portalConfiguration,
		Configuration portalTestConfiguration) {

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project,
			TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

		configureSourceSetClassesDir(
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

	protected void configureTaskBaseline(BaselineTask baselineTask) {
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

	protected void configureTaskFindBugs(FindBugs findBugs) {
		findBugs.setMaxHeapSize("1g");

		FindBugsReports findBugsReports = findBugs.getReports();

		SingleFileReport htmlReport = findBugsReports.getHtml();

		htmlReport.setEnabled(true);

		SingleFileReport xmlReport = findBugsReports.getXml();

		xmlReport.setEnabled(false);
	}

	protected void configureTaskJar(Project project, boolean testProject) {
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

	protected void configureTaskJarSources(final Jar jarSourcesTask) {
		Project project = jarSourcesTask.getProject();

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
								Project project = patchTask.getProject();

								return project.zipTree(
									patchTask.getOriginalLibSrcFile());
							}

						},
						new Closure<Void>(null) {

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
						new Closure<Void>(null) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.into("META-INF/patches");
							}

						});
				}

			});
	}

	protected void configureTaskJavaCompile(JavaCompile javaCompile) {
		CompileOptions compileOptions = javaCompile.getOptions();

		compileOptions.setEncoding(StandardCharsets.UTF_8.name());
		compileOptions.setWarnings(false);
	}

	protected void configureTaskJavadoc(Project project) {
		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		configureTaskJavadocFilter(javadoc);
		configureTaskJavadocOptions(javadoc);

		JavaVersion javaVersion = JavaVersion.current();

		if (javaVersion.isJava8Compatible()) {
			CoreJavadocOptions coreJavadocOptions =
				(CoreJavadocOptions)javadoc.getOptions();

			coreJavadocOptions.addStringOption("Xdoclint:none", "-quiet");
		}
	}

	protected void configureTaskJavadocFilter(Javadoc javadoc) {
		String exportPackage = getBundleInstruction(
			javadoc.getProject(), Constants.EXPORT_PACKAGE);

		if (Validator.isNull(exportPackage)) {
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

	protected void configureTaskJavadocOptions(Javadoc javadoc) {
		StandardJavadocDocletOptions standardJavadocDocletOptions =
			(StandardJavadocDocletOptions)javadoc.getOptions();
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
	}

	protected void configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		publishNodeModuleTask.setModuleAuthor(
			"Nathan Cavanaugh <nathan.cavanaugh@liferay.com> " +
				"(https://github.com/natecavanaugh)");
		publishNodeModuleTask.setModuleBugsUrl("https://issues.liferay.com/");
		publishNodeModuleTask.setModuleLicense("LGPL");
		publishNodeModuleTask.setModuleMain("package.json");
		publishNodeModuleTask.setModuleRepository("liferay/liferay-portal");
	}

	protected void configureTasksBaseline(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaselineTask.class,
			new Action<BaselineTask>() {

				@Override
				public void execute(BaselineTask baselineTask) {
					configureTaskBaseline(baselineTask);
				}

			});
	}

	protected void configureTasksFindBugs(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FindBugs.class,
			new Action<FindBugs>() {

				@Override
				public void execute(FindBugs findBugs) {
					configureTaskFindBugs(findBugs);
				}

			});
	}

	protected void configureTasksJavaCompile(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JavaCompile.class,
			new Action<JavaCompile>() {

				@Override
				public void execute(JavaCompile javaCompile) {
					configureTaskJavaCompile(javaCompile);
				}

			});
	}

	protected void configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					configureTaskPublishNodeModule(publishNodeModuleTask);
				}

			});
	}

	protected void configureTaskTest(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		test.jvmArgs(_TEST_JVM_ARGS);

		configureTaskTestIgnoreFailures(test);
		configureTaskTestLogging(test);
	}

	protected void configureTaskTestIgnoreFailures(Test test) {
		test.setIgnoreFailures(true);
	}

	protected void configureTaskTestIntegration(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		test.jvmArgs(_TEST_INTEGRATION_JVM_ARGS);

		configureTaskTestIgnoreFailures(test);
		configureTaskTestLogging(test);

		File resultsDir = project.file("test-results/integration");

		test.setBinResultsDir(new File(resultsDir, "binary/testIntegration"));

		TestTaskReports testTaskReports = test.getReports();

		JUnitXmlReport jUnitXmlReport = testTaskReports.getJunitXml();

		jUnitXmlReport.setDestination(resultsDir);
	}

	protected void configureTaskTestLogging(Test test) {
		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setEvents(EnumSet.allOf(TestLogEvent.class));
		testLoggingContainer.setExceptionFormat(TestExceptionFormat.FULL);
		testLoggingContainer.setStackTraceFilters(Collections.emptyList());
	}

	protected void configureTaskTlddoc(Project project, File portalRootDir) {
		if (portalRootDir == null) {
			return;
		}

		TLDDocTask tlddocTask = (TLDDocTask)GradleUtil.getTask(
			project, TLDDocBuilderPlugin.TLDDOC_TASK_NAME);

		File xsltDir = new File(portalRootDir, "tools/styles/taglibs");

		tlddocTask.setXsltDir(xsltDir);
	}

	protected void configureTaskUpdateFileVersions(
		ReplaceRegexTask updateFileVersionsTask, File portalRootDir) {

		Project project = updateFileVersionsTask.getProject();

		String regex = getModuleDependencyRegex(project);

		Map<String, Object> args = new HashMap<>();

		if (portalRootDir == null) {
			portalRootDir = project.getRootDir();
		}

		args.put("dir", portalRootDir);
		args.put("include", "**/*.gradle");

		updateFileVersionsTask.match(regex, project.fileTree(args));
	}

	protected void configureTaskUploadArchives(
		Project project, ReplaceRegexTask updateFileVersionsTask,
		ReplaceRegexTask updateVersionTask) {

		if (GradleUtil.isSnapshot(project)) {
			return;
		}

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		TaskContainer taskContainer = project.getTasks();

		TaskCollection<PublishNodeModuleTask> publishNodeModuleTasks =
			taskContainer.withType(PublishNodeModuleTask.class);

		uploadArchivesTask.dependsOn(publishNodeModuleTasks);

		uploadArchivesTask.finalizedBy(
			updateFileVersionsTask, updateVersionTask);
	}

	protected void forceProjectDependenciesEvaluation(Project project) {
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

	protected File getAppBndFile(Project project, File portalRootDir) {
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

	protected String getBundleInstruction(Project project, String key) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		return bundleInstructions.get(key);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String> getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return (Map<String, String>)bundleExtension.getInstructions();
	}

	protected String getGitResult(Project project, final Object... args) {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.args(args);
					execSpec.setExecutable("git");
					execSpec.setStandardOutput(byteArrayOutputStream);
				}

			});

		String result = byteArrayOutputStream.toString();

		return result.trim();
	}

	protected File getLibDir(Project project) {
		File docrootDir = project.file("docroot");

		if (docrootDir.exists()) {
			return new File(docrootDir, "WEB-INF/lib");
		}

		return project.file("lib");
	}

	protected File getModuleConfigFile(Project project) {
		if (!GradleUtil.hasPlugin(
				project, JSModuleConfigGeneratorPlugin.class)) {

			return null;
		}

		ConfigJSModulesTask configJSModulesTask =
			(ConfigJSModulesTask)GradleUtil.getTask(
				project,
				JSModuleConfigGeneratorPlugin.CONFIG_JS_MODULES_TASK_NAME);

		return configJSModulesTask.getModuleConfigFile();
	}

	protected String getModuleDependency(
		Project project, boolean roundToMajorVersion) {

		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append("\", version: \"");

		String versionString = String.valueOf(project.getVersion());

		if (roundToMajorVersion) {
			Version version = getVersion(versionString);

			if (version != null) {
				version = new Version(version.getMajor(), 0, 0);

				versionString = version.toString();
			}
		}

		sb.append(versionString);

		sb.append('"');

		return sb.toString();
	}

	protected String getModuleDependencyRegex(Project project) {
		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(GradleUtil.getArchivesBaseName(project));
		sb.append("\", version: \"");

		return Pattern.quote(sb.toString()) + "(\\d.+)\"";
	}

	protected String getProjectDependency(Project project) {
		return "project(\"" + project.getPath() + "\")";
	}

	protected Version getVersion(Object version) {
		try {
			return Version.parseVersion(String.valueOf(version));
		}
		catch (IllegalArgumentException iae) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Unable to parse " + version, iae);
			}

			return null;
		}
	}

	protected boolean hasBaseline(Project project) {
		Version version = getVersion(project.getVersion());

		if ((version != null) &&
			(version.compareTo(_LOWEST_BASELINE_VERSION) > 0)) {

			return true;
		}

		return false;
	}

	protected boolean hasTests(Project project) {
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

	protected boolean isPublishing(Project project) {
		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		List<String> taskNames = startParameter.getTaskNames();

		if (taskNames.contains(MavenPlugin.INSTALL_TASK_NAME) ||
			taskNames.contains(BasePlugin.UPLOAD_ARCHIVES_TASK_NAME)) {

			return true;
		}

		return false;
	}

	private static final String _APP_BND_FILE_NAME = "app.bnd";

	private static final String _CACHE_COMMIT_MESSAGE = "FAKE GRADLE CACHE";

	private static final String _GROUP = "com.liferay";

	private static final JavaVersion _JAVA_VERSION = JavaVersion.VERSION_1_7;

	private static final Version _LOWEST_BASELINE_VERSION = new Version(
		1, 0, 0);

	private static final boolean _MAVEN_LOCAL_IGNORE = Boolean.getBoolean(
		"maven.local.ignore");

	private static final String _REPOSITORY_URL = System.getProperty(
		"repository.url", DEFAULT_REPOSITORY_URL);

	private static final Object[] _TEST_INTEGRATION_JVM_ARGS = {
		"-Xms512m", "-Xmx512m", "-XX:MaxNewSize=32m", "-XX:MaxPermSize=200m",
		"-XX:MaxTenuringThreshold=0", "-XX:NewSize=32m",
		"-XX:ParallelGCThreads=2", "-XX:PermSize=200m",
		"-XX:SurvivorRatio=65536", "-XX:TargetSurvivorRatio=0",
		"-XX:-UseAdaptiveSizePolicy", "-XX:+UseParallelOldGC"
	};

	private static final Object[] _TEST_JVM_ARGS = {
		"-Xms256m", "-Xmx256m", "-XX:MaxNewSize=32m", "-XX:MaxPermSize=64m",
		"-XX:MaxTenuringThreshold=0", "-XX:NewSize=32m",
		"-XX:ParallelGCThreads=2", "-XX:PermSize=64m",
		"-XX:SurvivorRatio=65536", "-XX:TargetSurvivorRatio=0",
		"-XX:-UseAdaptiveSizePolicy", "-XX:+UseParallelOldGC",
		"-XX:-UseSplitVerifier"
	};

	private static final Logger _logger = Logging.getLogger(
		LiferayOSGiDefaultsPlugin.class);

}