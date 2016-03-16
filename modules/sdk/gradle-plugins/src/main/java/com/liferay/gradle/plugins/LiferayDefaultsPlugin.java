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

import com.liferay.gradle.plugins.change.log.builder.ChangeLogBuilderPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.patcher.PatchTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.tasks.BaselineTask;
import com.liferay.gradle.plugins.tasks.ReplaceRegexTask;
import com.liferay.gradle.plugins.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.tlddoc.builder.TLDDocBuilderPlugin;
import com.liferay.gradle.plugins.tlddoc.builder.tasks.TLDDocTask;
import com.liferay.gradle.plugins.upgrade.table.builder.UpgradeTableBuilderPlugin;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.ExcludeExistingFileAction;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import groovy.lang.Closure;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nebula.plugin.extraconfigurations.OptionalBasePlugin;
import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.BundlePlugin;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.maven.Conf2ScopeMapping;
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer;
import org.gradle.api.artifacts.maven.MavenDeployer;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.artifacts.publish.ArchivePublishArtifact;
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
import org.gradle.api.plugins.MavenRepositoryHandlerConvention;
import org.gradle.api.plugins.ReportingBasePlugin;
import org.gradle.api.plugins.quality.FindBugs;
import org.gradle.api.plugins.quality.FindBugsPlugin;
import org.gradle.api.plugins.quality.FindBugsReports;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskCollection;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.Upload;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
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
import org.gradle.external.javadoc.MinimalJavadocOptions;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;
import org.gradle.process.ExecSpec;
import org.gradle.util.CollectionUtils;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayDefaultsPlugin extends BaseDefaultsPlugin<LiferayPlugin> {

	public static final String BASELINE_CONFIGURATION_NAME = "baseline";

	public static final String BASELINE_TASK_NAME = "baseline";

	public static final String COPY_LIBS_TASK_NAME = "copyLibs";

	public static final String DEFAULT_REPOSITORY_URL =
		"http://cdn.repository.liferay.com/nexus/content/groups/public";

	public static final String JAR_JAVADOC_TASK_NAME = "jarJavadoc";

	public static final String JAR_SOURCES_TASK_NAME = "jarSources";

	public static final String JAR_TLDDOC_TASK_NAME = "jarTLDDoc";

	public static final String PORTAL_TEST_CONFIGURATION_NAME = "portalTest";

	public static final String PRINT_ARTIFACT_PUBLISH_COMMANDS =
		"printArtifactPublishCommands";

	public static final String PRINT_STALE_ARTIFACT_TASK_NAME =
		"printStaleArtifact";

	public static final String RECORD_ARTIFACT_TASK_NAME = "recordArtifact";

	public static final String UPDATE_BUNDLE_VERSION_TASK_NAME =
		"updateBundleVersion";

	public static final String UPDATE_FILE_VERSIONS_TASK_NAME =
		"updateFileVersions";

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
			String.valueOf(project.getGroup()), getArchivesBaseName(project),
			"(," + String.valueOf(project.getVersion()) + ")", false);
	}

	protected void addDependenciesPortalTest(Project project) {
		GradleUtil.addDependency(
			project, PORTAL_TEST_CONFIGURATION_NAME, "com.liferay.portal",
			"com.liferay.portal.test", "default");
		GradleUtil.addDependency(
			project, PORTAL_TEST_CONFIGURATION_NAME, "com.liferay.portal",
			"com.liferay.portal.test.internal", "default");
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

	protected BaselineTask addTaskBaseline(
		final Project project, final Configuration baselineConfiguration) {

		GradleUtil.applyPlugin(project, ReportingBasePlugin.class);

		BaselineTask baselineTask = GradleUtil.addTask(
			project, BASELINE_TASK_NAME, BaselineTask.class);

		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		baselineTask.dependsOn(jar);

		baselineTask.setDescription(
			"Compares the public API of this project with the public API of " +
				"the previous released version, if found.");
		baselineTask.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

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

		return baselineTask;
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

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(copy);

		return copy;
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
		final Jar jar = GradleUtil.addTask(
			project, JAR_SOURCES_TASK_NAME, Jar.class);

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

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PatchTask.class,
			new Action<PatchTask>() {

				@Override
				public void execute(final PatchTask patchTask) {
					jar.from(
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

	protected Task addTaskPrintArtifactPublishCommands(
		File gitRepoDir, final File portalRootDir,
		final WritePropertiesTask recordArtifactTask, boolean testProject) {

		final Project project = recordArtifactTask.getProject();

		Task task = project.task(PRINT_ARTIFACT_PUBLISH_COMMANDS);

		task.doLast(
			new Action<Task>() {

				private String _getGitCommitCommand(
					String message, boolean ignored) {

					return _getGitCommitCommand(message, false, ignored);
				}

				private String _getGradleCommand(
					String gradleRelativePath, String command,
					boolean gradleDaemon, String ... arguments) {

					StringBuilder sb = new StringBuilder();

					sb.append(gradleRelativePath);
					sb.append(' ');
					sb.append(command);

					if (gradleDaemon) {
						sb.append(" --daemon");
					}

					for (String argument : arguments) {
						sb.append(' ');
						sb.append(argument);
					}

					return sb.toString();
				}

				private String _getGitCommitCommand(
					String message, boolean all, boolean ignored) {

					StringBuilder sb = new StringBuilder();

					if (all) {
						sb.append("(git diff-index --quiet HEAD || ");
					}

					sb.append("git commit ");

					if (all) {
						sb.append("--all ");
					}

					sb.append("--message=\"");

					if (ignored) {
						sb.append(_IGNORED_MESSAGE_PATTERN);
						sb.append(' ');
					}

					sb.append(project.getName());
					sb.append(' ');
					sb.append(project.getVersion());
					sb.append(' ');
					sb.append(message);

					sb.append('"');

					if (all) {
						sb.append(')');
					}

					return sb.toString();
				}

				private boolean _getGradleDaemon(Task task) {
					boolean gradleDaemon = true;

					String gradleDaemonString =
						GradleUtil.getTaskPrefixedProperty(task, "daemon");

					if (Validator.isNotNull(gradleDaemonString)) {
						gradleDaemon = Boolean.parseBoolean(gradleDaemonString);
					}

					return gradleDaemon;
				}

				private String _getGradleRelativePath() {
					File rootDir = portalRootDir;

					if (portalRootDir == null) {
						rootDir = project.getRootDir();
					}

					File gradlewFile = new File(rootDir, "gradlew");

					return project.relativePath(gradlewFile);
				}

				private List<String> _getPublishCommands(
					String gradleRelativePath, boolean gradleDaemon,
					boolean excludeUpdateFileVersions) {

					List<String> commands = new ArrayList<>();

					// Publish snapshot

					commands.add(
						_getGradleCommand(
							gradleRelativePath,
							BasePlugin.UPLOAD_ARCHIVES_TASK_NAME, gradleDaemon,
							"-P" + _SNAPSHOT_PROPERTY_NAME));

					// Publish release

					String[] arguments;

					if (excludeUpdateFileVersions) {
						arguments = new String[] {
							"-x", UPDATE_FILE_VERSIONS_TASK_NAME
						};
					}
					else {
						arguments = new String[0];
					}

					commands.add(
						_getGradleCommand(
							gradleRelativePath,
							BasePlugin.UPLOAD_ARCHIVES_TASK_NAME, gradleDaemon,
							arguments));

					// Commit "prep next"

					commands.add("git add " + project.relativePath("bnd.bnd"));

					commands.add(_getGitCommitCommand("prep next", true));

					// Commit "artifact properties"

					commands.add(
						"git add " +
							project.relativePath(
								recordArtifactTask.getOutputFile()));

					commands.add(
						_getGitCommitCommand("artifact properties", true));

					// Commit other changed files

					commands.add(_getGitCommitCommand("apply", true, false));

					return commands;
				}

				@Override
				public void execute(Task task) {
					List<String> commands = new ArrayList<>();

					boolean gradleDaemon = _getGradleDaemon(task);
					String gradleRelativePath = _getGradleRelativePath();

					commands.add(
						"cd " +
							FileUtil.getAbsolutePath(project.getProjectDir()));

					// Publish if the artifact has never been published

					if (!hasBaseline(project)) {
						commands.addAll(
							_getPublishCommands(
								gradleRelativePath, gradleDaemon, true));
					}

					// Baseline

					commands.add(
						_getGradleCommand(
							gradleRelativePath, BASELINE_TASK_NAME,
							gradleDaemon));

					// Publish if there are packageinfo changes

					List<String> publishCommands = _getPublishCommands(
						gradleRelativePath, gradleDaemon, false);

					publishCommands.add(0, "git add --all");
					publishCommands.add(
						1, _getGitCommitCommand("packageinfo", false));

					commands.add(
						"(git diff-index --quiet HEAD || (" +
							CollectionUtils.join(" && ", publishCommands) +
								"))");

					System.out.println();

					for (String command : commands) {
						System.out.print(" && ");
						System.out.print(command);
					}

					if (GradleUtil.getProperty(project, "first", false)) {
						throw new GradleException();
					}
				}

			});

		if (gitRepoDir != null) {
			task.onlyIf(new LeafArtifactSpec(gitRepoDir));
		}

		task.setDescription(
			"Prints the artifact publish commands if this project has been " +
				"changed since the last publish.");

		configureTaskEnabledIfStale(task, recordArtifactTask, testProject);

		return task;
	}

	protected Task addTaskPrintStaleArtifact(
		File portalRootDir, WritePropertiesTask recordArtifactTask,
		boolean testProject) {

		Project project = recordArtifactTask.getProject();

		Task task = project.task(PRINT_STALE_ARTIFACT_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					File projectDir = project.getProjectDir();

					System.out.println(projectDir.getAbsolutePath());
				}

			});

		task.setDescription(
			"Prints the project directory if this project has been changed " +
				"since the last publish.");
		task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		configureTaskEnabledIfStale(task, recordArtifactTask, testProject);

		return task;
	}

	protected WritePropertiesTask addTaskRecordArtifact(Project project) {
		final WritePropertiesTask writePropertiesTask = GradleUtil.addTask(
			project, RECORD_ARTIFACT_TASK_NAME, WritePropertiesTask.class);

		writePropertiesTask.property(
			"artifact.git.id",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return getGitHead(writePropertiesTask.getProject());
				}

			});

		Configuration configuration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		PublishArtifactSet publishArtifactSet = configuration.getArtifacts();

		publishArtifactSet.withType(
			ArchivePublishArtifact.class,
			new Action<ArchivePublishArtifact>() {

				@Override
				public void execute(
					final ArchivePublishArtifact archivePublishArtifact) {

					String key = archivePublishArtifact.getClassifier();

					if (Validator.isNull(key)) {
						key = "artifact.url";
					}
					else {
						key = "artifact." + key + ".url";
					}

					writePropertiesTask.property(
						key,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return getArtifactRemoteURL(
									archivePublishArtifact.getArchiveTask(),
									false);
							}

						});
				}

			});

		writePropertiesTask.setDescription(
			"Records the commit ID and the artifact URLs.");
		writePropertiesTask.setOutputFile("artifact.properties");

		return writePropertiesTask;
	}

	protected Task addTaskUpdateBundleVersion(Project project) {
		Task task = project.task(UPDATE_BUNDLE_VERSION_TASK_NAME);

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				try {
					Project project = task.getProject();

					File bndFile = project.file("bnd.bnd");

					if (!bndFile.exists()) {
						if (_logger.isInfoEnabled()) {
							_logger.info("Unable to find " + bndFile);
						}

						return;
					}

					String bndContent = new String(
						Files.readAllBytes(bndFile.toPath()),
						StandardCharsets.UTF_8);

					VersionNumber versionNumber = VersionNumber.parse(
						String.valueOf(project.getVersion()));

					VersionNumber nextVersionNumber = new VersionNumber(
						versionNumber.getMajor(), versionNumber.getMinor(),
						versionNumber.getMicro() + 1,
						versionNumber.getQualifier());

					String nextBndContent = bndContent.replace(
						Constants.BUNDLE_VERSION + ": " + versionNumber,
						Constants.BUNDLE_VERSION + ": " + nextVersionNumber);

					if (bndContent.equals(nextBndContent)) {
						if (_logger.isWarnEnabled()) {
							_logger.warn(
								"Unable to update " + Constants.BUNDLE_VERSION);
						}

						return;
					}

					Files.write(
						bndFile.toPath(),
						nextBndContent.getBytes(StandardCharsets.UTF_8));

					if (_logger.isLifecycleEnabled()) {
						_logger.lifecycle(
							Constants.BUNDLE_VERSION + " of " + project +
								" updated to " + nextVersionNumber);
					}
				}
				catch (IOException ioe) {
					throw new GradleException(
						"Unable to update " + Constants.BUNDLE_VERSION, ioe);
				}
			}

		};

		task.doLast(action);

		task.setDescription(
			"Updates the project version in the " + Constants.BUNDLE_VERSION +
				" header.");

		return task;
	}

	protected ReplaceRegexTask addTaskUpdateFileVersions(
		final Project project, final File gitRepoDir) {

		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, UPDATE_FILE_VERSIONS_TASK_NAME, ReplaceRegexTask.class);

		replaceRegexTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					ReplaceRegexTask replaceRegexTask = (ReplaceRegexTask)task;

					if (!_logger.isLifecycleEnabled()) {
						return;
					}

					String replacement = replaceRegexTask.getReplacement();

					for (Object file : replaceRegexTask.getMatchedFiles()) {
						_logger.lifecycle(
							"Project version in " + project.relativePath(file) +
								" updated to " + replacement);
					}
				}

			});

		if (gitRepoDir != null) {
			replaceRegexTask.pre(
				new Closure<String>(null) {

					@SuppressWarnings("unused")
					public String doCall(String content, File file) {
						String fileName = file.getName();

						if (!fileName.equals("build.gradle")) {
							return content;
						}

						if (FileUtil.isChild(file, gitRepoDir)) {
							return content.replaceAll(
								getModuleDependencyRegex(project),
								Matcher.quoteReplacement(
									getProjectDependency(project)));
						}
						else {
							return content.replaceAll(
								Pattern.quote(getProjectDependency(project)),
								Matcher.quoteReplacement(
									getModuleDependency(project)));
						}
					}

				});
		}

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

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-maven.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, ChangeLogBuilderPlugin.class);
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
		Project project, boolean publishing) {

		LiferayOSGiExtension liferayOSGiExtension = GradleUtil.getExtension(
			project, LiferayOSGiExtension.class);

		Map<String, Object> bundleDefaultInstructions = new HashMap<>();

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

		liferayOSGiExtension.bundleDefaultInstructions(
			bundleDefaultInstructions);
	}

	protected void configureBundleInstructions(Project project) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		String includeResource = bundleInstructions.get(
			_LIFERAY_INCLUDERESOURCE);

		if (Validator.isNull(includeResource) ||
			!includeResource.contains("-*.")) {

			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

		DependencySet dependencySet = configuration.getAllDependencies();

		GradleInternal gradle = (GradleInternal)project.getGradle();

		ServiceRegistry serviceRegistry = gradle.getServices();

		ProjectConfigurer projectConfigurer = serviceRegistry.get(
			ProjectConfigurer.class);

		for (ProjectDependency projectDependency :
				dependencySet.withType(ProjectDependency.class)) {

			ProjectInternal dependencyProject =
				(ProjectInternal)projectDependency.getDependencyProject();

			projectConfigurer.configure(dependencyProject);

			if (!hasPlugin(dependencyProject, BasePlugin.class)) {
				continue;
			}

			String name = getArchivesBaseName(dependencyProject);
			String version = String.valueOf(dependencyProject.getVersion());

			includeResource = includeResource.replace(
				name + "-*.", name + "-" + version + ".");
		}

		ResolvedConfiguration resolvedConfiguration =
			configuration.getResolvedConfiguration();

		for (ResolvedDependency resolvedDependency :
				resolvedConfiguration.getFirstLevelModuleDependencies()) {

			String name = resolvedDependency.getModuleName();
			String version = resolvedDependency.getModuleVersion();

			includeResource = includeResource.replace(
				name + "-*.", name + "-" + version + ".");
		}

		bundleInstructions.put(
			Constants.INCLUDERESOURCE + ".liferay", includeResource);
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

	protected void configureConfigurations(Project project) {
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

	@Override
	protected void configureDefaults(
		final Project project, LiferayPlugin liferayPlugin) {

		final File gitRepoDir = getRootDir(project, ".gitrepo");
		final File portalRootDir = getRootDir(
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

		Configuration portalConfiguration = GradleUtil.getConfiguration(
			project, LiferayJavaPlugin.PORTAL_CONFIGURATION_NAME);
		Configuration portalTestConfiguration = addConfigurationPortalTest(
			project);

		addDependenciesPortalTest(project);
		addDependenciesTestCompile(project);

		final Jar jarJavadocTask = addTaskJarJavadoc(project);
		final Jar jarSourcesTask = addTaskJarSources(project, testProject);
		final Jar jarTLDDocTask = addTaskJarTLDDoc(project);
		final WritePropertiesTask recordArtifactTask = addTaskRecordArtifact(
			project);

		addTaskPrintArtifactPublishCommands(
			gitRepoDir, portalRootDir, recordArtifactTask, testProject);
		addTaskPrintStaleArtifact(
			portalRootDir, recordArtifactTask, testProject);

		final ReplaceRegexTask updateFileVersionsTask =
			addTaskUpdateFileVersions(project, gitRepoDir);

		configureBasePlugin(project, portalRootDir);
		configureConfigurations(project);
		configureEclipse(project, portalTestConfiguration);
		configureIdea(project, portalTestConfiguration);
		configureJavaPlugin(project);
		configureProject(project);
		configureRepositories(project);
		configureSourceSetMain(project);
		configureSourceSetTest(
			project, portalConfiguration, portalTestConfiguration);
		configureSourceSetTestIntegration(
			project, portalConfiguration, portalTestConfiguration);
		configureTaskJar(project, testProject);
		configureTaskTest(project);
		configureTaskTestIntegration(project);
		configureTasksBaseline(project);
		configureTasksFindBugs(project);
		configureTasksJavaCompile(project);
		configureTasksPublishNodeModule(project);

		withPlugin(
			project, BundlePlugin.class,
			new Action<BundlePlugin>() {

				@Override
				public void execute(BundlePlugin bundlePlugin) {
					addTaskCopyLibs(project);
					addTaskUpdateBundleVersion(project);
					configureBundleDefaultInstructions(project, publishing);
					configureTaskJavadoc(project);

					if (hasBaseline(project)) {
						Configuration baselineConfiguration =
							addConfigurationBaseline(project);

						addTaskBaseline(project, baselineConfiguration);
					}
				}

			});

		withPlugin(
			project, ServiceBuilderPlugin.class,
			new Action<ServiceBuilderPlugin>() {

				@Override
				public void execute(ServiceBuilderPlugin serviceBuilderPlugin) {
					configureLocalPortalTool(
						project, portalRootDir,
						ServiceBuilderPlugin.CONFIGURATION_NAME,
						ServiceBuilderDefaultsPlugin.PORTAL_TOOL_NAME,
						"portal-tools-service-builder");
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureArtifacts(
						project, jarJavadocTask, jarSourcesTask, jarTLDDocTask);
					configureProjectVersion(project);
					configureTaskUpdateFileVersions(
						updateFileVersionsTask, portalRootDir);

					// configureProjectVersion must be called before
					// configureTaskUploadArchives, because the latter one needs
					// to know if we are publishing a snapshot or not.

					configureTaskUploadArchives(
						project, recordArtifactTask, updateFileVersionsTask);

					if (hasPlugin(project, BundlePlugin.class)) {
						configureBundleInstructions(project);
						configureProjectBndProperties(project);
					}
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
		String portalToolName, String portalToolDirName) {

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
			portalRootDir, "tools/sdk/tmp/portal-tools/" + portalToolDirName);

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

	protected void configureProjectVersion(Project project) {
		boolean snapshot = false;

		if (project.hasProperty(_SNAPSHOT_PROPERTY_NAME)) {
			snapshot = GradleUtil.getProperty(
				project, _SNAPSHOT_PROPERTY_NAME, true);
		}

		String version = String.valueOf(project.getVersion());

		if (snapshot && !version.endsWith(_SNAPSHOT_VERSION_SUFFIX)) {
			project.setVersion(version + _SNAPSHOT_VERSION_SUFFIX);
		}
	}

	protected void configureRepositories(Project project) {
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

		boolean reportDiff = false;

		String reportLevel = GradleUtil.getProperty(
			project, "baseline.jar.report.level", "standard");

		if (reportLevel.equals("diff") || reportLevel.equals("persist")) {
			reportDiff = true;
		}

		baselineTask.setReportDiff(reportDiff);

		boolean reportOnlyDirtyPackages = GradleUtil.getProperty(
			project, "baseline.jar.report.only.dirty.packages", true);

		baselineTask.setReportOnlyDirtyPackages(reportOnlyDirtyPackages);
	}

	protected void configureTaskEnabledIfStale(
		Task task, WritePropertiesTask recordArtifactTask,
		boolean testProject) {

		if (testProject) {
			task.setEnabled(false);
		}

		task.onlyIf(new OutOfDateArtifactSpec(recordArtifactTask));
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
		MinimalJavadocOptions minimalJavadocOptions = javadoc.getOptions();
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
			minimalJavadocOptions.setOverview(
				project.relativePath(overviewFile));
		}
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
		Project project, WritePropertiesTask recordArtifactTask,
		ReplaceRegexTask updateFileVersionsTask) {

		String version = String.valueOf(project.getVersion());

		if (version.endsWith(_SNAPSHOT_VERSION_SUFFIX)) {
			return;
		}

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.dependsOn(recordArtifactTask);

		TaskContainer taskContainer = project.getTasks();

		TaskCollection<PublishNodeModuleTask> publishNodeModuleTasks =
			taskContainer.withType(PublishNodeModuleTask.class);

		uploadArchivesTask.dependsOn(publishNodeModuleTasks);

		Task updateBundleVersionTask = taskContainer.findByName(
			UPDATE_BUNDLE_VERSION_TASK_NAME);

		if (updateBundleVersionTask != null) {
			uploadArchivesTask.finalizedBy(updateBundleVersionTask);
		}

		uploadArchivesTask.finalizedBy(updateFileVersionsTask);
	}

	protected String escapeBRE(Object object) {
		String s = GradleUtil.toString(object);

		return s.replaceAll("[\\Q\\{}()[]*+?.|^$\\E]", "\\\\$0");
	}

	protected String getArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		return basePluginConvention.getArchivesBaseName();
	}

	protected String getArtifactRemoteURL(
			AbstractArchiveTask abstractArchiveTask, boolean cdn)
		throws Exception {

		Project project = abstractArchiveTask.getProject();

		Upload upload = (Upload)GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		RepositoryHandler repositoryHandler = upload.getRepositories();

		MavenDeployer mavenDeployer = (MavenDeployer)repositoryHandler.getAt(
			MavenRepositoryHandlerConvention.DEFAULT_MAVEN_DEPLOYER_NAME);

		Object repository = mavenDeployer.getRepository();

		// org.apache.maven.artifact.ant.RemoteRepository is not in the
		// classpath

		Class<?> repositoryClass = repository.getClass();

		Method getUrlMethod = repositoryClass.getMethod("getUrl");

		String url = (String)getUrlMethod.invoke(repository);

		if (cdn) {
			url = url.replace("http://", "http://cdn.");
			url = url.replace("https://", "https://cdn.");
		}

		StringBuilder sb = new StringBuilder(url);

		if (sb.charAt(sb.length() - 1) != '/') {
			sb.append('/');
		}

		String group = String.valueOf(project.getGroup());

		sb.append(group.replace('.', '/'));

		sb.append('/');
		sb.append(abstractArchiveTask.getBaseName());
		sb.append('/');
		sb.append(abstractArchiveTask.getVersion());
		sb.append('/');
		sb.append(abstractArchiveTask.getArchiveName());

		return sb.toString();
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

	protected String getGitHead(Project project) {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.commandLine("git", "rev-parse", "HEAD");
					execSpec.setStandardOutput(byteArrayOutputStream);
				}

			});

		String gitHead = byteArrayOutputStream.toString();

		return gitHead.trim();
	}

	protected File getLibDir(Project project) {
		File docrootDir = project.file("docroot");

		if (docrootDir.exists()) {
			return new File(docrootDir, "WEB-INF/lib");
		}

		return project.file("lib");
	}

	protected String getModuleDependency(Project project) {
		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(getArchivesBaseName(project));
		sb.append("\", version: \"");
		sb.append(project.getVersion());
		sb.append('"');

		return sb.toString();
	}

	protected String getModuleDependencyRegex(Project project) {
		StringBuilder sb = new StringBuilder();

		sb.append("group: \"");
		sb.append(project.getGroup());
		sb.append("\", name: \"");
		sb.append(getArchivesBaseName(project));
		sb.append("\", version: \"");

		return Pattern.quote(sb.toString()) + "(\\d.+)\"";
	}

	@Override
	protected Class<LiferayPlugin> getPluginClass() {
		return LiferayPlugin.class;
	}

	protected String getProjectDependency(Project project) {
		return "project(\"" + project.getPath() + "\")";
	}

	protected File getRootDir(Project project, String markerFileName) {
		File dir = project.getProjectDir();

		dir = dir.getParentFile();

		while (true) {
			File markerFile = new File(dir, markerFileName);

			if (markerFile.exists()) {
				return dir;
			}

			dir = dir.getParentFile();

			if (dir == null) {
				return null;
			}
		}
	}

	protected boolean hasBaseline(Project project) {
		try {
			Version version = Version.parseVersion(
				String.valueOf(project.getVersion()));

			if (version.compareTo(_LOWEST_BASELINE_VERSION) > 0) {
				return true;
			}
		}
		catch (IllegalArgumentException iae) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Unable to parse project version", iae);
			}
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

	protected boolean isTestProject(Project project) {
		String projectName = project.getName();

		if (projectName.endsWith("-test")) {
			return true;
		}

		return false;
	}

	private static final String _GROUP = "com.liferay";

	private static final String _IGNORED_MESSAGE_PATTERN = "artifact:ignore";

	private static final JavaVersion _JAVA_VERSION = JavaVersion.VERSION_1_7;

	private static final String _LIFERAY_INCLUDERESOURCE =
		"-liferay-includeresource";

	private static final Version _LOWEST_BASELINE_VERSION = new Version(
		1, 0, 0);

	private static final boolean _MAVEN_LOCAL_IGNORE = Boolean.getBoolean(
		"maven.local.ignore");

	private static final String _REPOSITORY_URL = System.getProperty(
		"repository.url", DEFAULT_REPOSITORY_URL);

	private static final String _SNAPSHOT_PROPERTY_NAME = "snapshot";

	private static final String _SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT";

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
		LiferayDefaultsPlugin.class);

	private static class LeafArtifactSpec implements Spec<Task> {

		public LeafArtifactSpec(File gitRepoDir) {
			_gitRepoDir = gitRepoDir;
		}

		@Override
		public boolean isSatisfiedBy(Task task) {
			Project project = task.getProject();

			for (Configuration configuration : project.getConfigurations()) {
				if (_hasExternalProjectDependencies(project, configuration)) {
					return false;
				}
			}

			return true;
		}

		private boolean _hasExternalProjectDependencies(
			Project project, Configuration configuration) {

			for (Dependency dependency : configuration.getDependencies()) {
				if (!(dependency instanceof ProjectDependency)) {
					continue;
				}

				ProjectDependency projectDependency =
					(ProjectDependency)dependency;

				Project dependencyProject =
					projectDependency.getDependencyProject();

				if (!FileUtil.isChild(
						dependencyProject.getProjectDir(), _gitRepoDir)) {

					return true;
				}
			}

			return false;
		}

		private final File _gitRepoDir;

	}

	private static class OutOfDateArtifactSpec implements Spec<Task> {

		public OutOfDateArtifactSpec(WritePropertiesTask recordArtifactTask) {
			_recordArtifactTask = recordArtifactTask;
		}

		@Override
		public boolean isSatisfiedBy(Task task) {
			final Project project = task.getProject();

			Properties artifactProperties;

			try {
				artifactProperties = FileUtil.readProperties(
					_recordArtifactTask.getOutputFile());
			}
			catch (IOException ioe) {
				throw new GradleException(
					"Unable to read artifact properties", ioe);
			}

			final String artifactGitId = artifactProperties.getProperty(
				"artifact.git.id");

			if (Validator.isNull(artifactGitId)) {
				if (_logger.isInfoEnabled()) {
					_logger.info(project + " has never been published");
				}

				return true;
			}

			final ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						execSpec.commandLine(
							"git", "log", "--format=%s",
							artifactGitId + "..HEAD", ".");

						execSpec.setStandardOutput(byteArrayOutputStream);
						execSpec.setWorkingDir(project.getProjectDir());
					}

				});

			String output = byteArrayOutputStream.toString();

			String[] lines = output.split("\\r?\\n");

			for (String line : lines) {
				if (_logger.isInfoEnabled()) {
					_logger.info(line);
				}

				if (Validator.isNull(line)) {
					continue;
				}

				if (!line.contains(_IGNORED_MESSAGE_PATTERN)) {
					return true;
				}
			}

			return false;
		}

		private final WritePropertiesTask _recordArtifactTask;

	}

}