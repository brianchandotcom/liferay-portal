/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.CSSBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.EclipseDefaultsPlugin;
import com.liferay.gradle.plugins.IdeaDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.WatchOSGiPlugin;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileTree;
import org.gradle.api.java.archives.Manifest;
import org.gradle.api.java.archives.ManifestMergeDetails;
import org.gradle.api.java.archives.ManifestMergeSpec;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.util.VersionNumber;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiExtPlugin implements Plugin<Project> {

	public static final String ORIGINAL_MODULE_CONFIGURATION_NAME =
		"originalModule";

	public static final String UNZIP_ORIGINAL_MODULE_TASK_NAME =
		"unzipOriginalModule";

	@Override
	public void apply(final Project project) {

		// Plugins

		_applyPlugins(project);

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		LiferayExtension liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);

		_configureExtensionLiferay(liferayExtension);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final Configuration originalModuleConfiguration =
			configurationContainer.create(ORIGINAL_MODULE_CONFIGURATION_NAME);

		Configuration compileOnlyConfiguration =
			configurationContainer.getByName(
				JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);

		_configureConfigurationCompileOnly(
			compileOnlyConfiguration, originalModuleConfiguration);
		_configureConfigurationOriginalModule(originalModuleConfiguration);

		// Conventions

		Convention convention = project.getConvention();

		BasePluginConvention basePluginConvention = convention.getPlugin(
			BasePluginConvention.class);

		// Tasks

		TaskProvider<Sync> unzipOriginalModuleTaskProvider =
			GradleUtil.addTaskProvider(
				project, UNZIP_ORIGINAL_MODULE_TASK_NAME, Sync.class);

		TaskProvider<Copy> deployTaskProvider = GradleUtil.getTaskProvider(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME, Copy.class);
		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		_configureTaskDeployProvider(
			project, liferayExtension, deployTaskProvider, jarTaskProvider);
		_configureTaskJarProvider(
			jarTaskProvider, unzipOriginalModuleTaskProvider);
		_configureTaskUnzipOriginalModuleProvider(
			project, originalModuleConfiguration,
			unzipOriginalModuleTaskProvider);

		// Other

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					try (JarFile jarFile = new JarFile(
							originalModuleConfiguration.getSingleFile())) {

						java.util.jar.Manifest manifest = jarFile.getManifest();

						Attributes attributes = manifest.getMainAttributes();

						_configureConventionBasePluginAfterEvaluate(
							basePluginConvention, attributes);
						_configureProjectAfterEvaluate(project, attributes);
					}
					catch (IOException ioException) {
						throw new UncheckedIOException(ioException);
					}
				}

			});
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, JavaLibraryPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);

		CSSBuilderDefaultsPlugin.INSTANCE.apply(project);
		EclipseDefaultsPlugin.INSTANCE.apply(project);
		IdeaDefaultsPlugin.INSTANCE.apply(project);
		WatchOSGiPlugin.INSTANCE.apply(project);
	}

	private void _configureConfigurationCompileOnly(
		Configuration compileOnlyConfiguration,
		Configuration originalModuleConfiguration) {

		compileOnlyConfiguration.extendsFrom(originalModuleConfiguration);
	}

	private void _configureConfigurationOriginalModule(
		Configuration originalModuleConfiguration) {

		originalModuleConfiguration.setDescription(
			"Configures the original module to extend.");
		originalModuleConfiguration.setTransitive(false);
		originalModuleConfiguration.setVisible(false);
	}

	private void _configureConventionBasePluginAfterEvaluate(
		BasePluginConvention basePluginConvention, Attributes attributes) {

		basePluginConvention.setArchivesBaseName(
			attributes.getValue(Constants.BUNDLE_SYMBOLICNAME));
	}

	private void _configureExtensionLiferay(
		final LiferayExtension liferayExtension) {

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() {
					return new File(
						liferayExtension.getAppServerParentDir(),
						"osgi/marketplace/override");
				}

			});
	}

	private void _configureProjectAfterEvaluate(
		Project project, Attributes attributes) {

		String version = attributes.getValue(Constants.BUNDLE_VERSION);

		VersionNumber versionNumber = VersionNumber.parse(version);

		if (Validator.isNull(versionNumber.getQualifier())) {
			version += '.' + _VERSION_SUFFIX;
		}
		else {
			version += '-' + _VERSION_SUFFIX;
		}

		project.setVersion(version);
	}

	private void _configureTaskDeployProvider(
		final Project project, final LiferayExtension liferayExtension,
		TaskProvider<Copy> deployTaskProvider,
		final TaskProvider<Jar> jarTaskProvider) {

		deployTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy deployCopy) {
					deployCopy.from(
						jarTaskProvider,
						new Closure<Void>(project) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.rename(
									new Closure<String>(project) {

										public String doCall(String fileName) {
											Closure<String> closure =
												liferayExtension.
													getDeployedFileNameClosure();

											return closure.call(
												jarTaskProvider.get());
										}

									});
							}

						});
				}

			});
	}

	private void _configureTaskJarProvider(
		TaskProvider<Jar> jarTaskProvider,
		final TaskProvider<Sync> unzipOriginalModuleTaskProvider) {

		jarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar jar) {
					final Sync unzipOriginalModuleSync =
						unzipOriginalModuleTaskProvider.get();

					jar.dependsOn(unzipOriginalModuleSync);

					jar.from(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return unzipOriginalModuleSync.
									getDestinationDir();
							}

						});

					jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);

					Manifest manifest = jar.getManifest();

					manifest.attributes(
						Collections.singletonMap(
							Constants.BUNDLE_VERSION,
							new Object() {

								@Override
								public String toString() {
									return jar.getVersion();
								}

							}));

					manifest.from(
						new Callable<File>() {

							@Override
							public File call() {
								return new File(
									unzipOriginalModuleSync.getDestinationDir(),
									"META-INF/MANIFEST.MF");
							}

						},
						new Closure<Void>(jar) {

							@SuppressWarnings("unused")
							public void doCall(
								ManifestMergeSpec manifestMergeSpec) {

								manifestMergeSpec.eachEntry(
									new Action<ManifestMergeDetails>() {

										@Override
										public void execute(
											ManifestMergeDetails
												manifestMergeDetails) {

											if (Objects.equals(
													manifestMergeDetails.
														getKey(),
													Constants.BUNDLE_VERSION)) {

												manifestMergeDetails.setValue(
													manifestMergeDetails.
														getBaseValue());
											}
										}

									});
							}

						});
				}

			});
	}

	private void _configureTaskUnzipOriginalModuleProvider(
		final Project project, final Configuration originalModuleConfiguration,
		TaskProvider<Sync> unzipOriginalModuleTaskProvider) {

		unzipOriginalModuleTaskProvider.configure(
			new Action<Sync>() {

				@Override
				public void execute(Sync unzipOriginalModuleSync) {
					unzipOriginalModuleSync.from(
						new Callable<FileTree>() {

							@Override
							public FileTree call() {
								File file =
									originalModuleConfiguration.getSingleFile();

								return project.zipTree(file);
							}

						});

					unzipOriginalModuleSync.into(
						new Callable<File>() {

							@Override
							public File call() {
								return new File(
									project.getBuildDir(), "original-module");
							}

						});

					unzipOriginalModuleSync.setDescription(
						"Unzips the original module into a temporary " +
							"directory.");
				}

			});
	}

	private static final String _VERSION_SUFFIX = "ext";

}