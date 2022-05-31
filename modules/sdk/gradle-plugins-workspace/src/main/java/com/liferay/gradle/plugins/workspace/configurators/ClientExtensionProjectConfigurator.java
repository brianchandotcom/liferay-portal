package com.liferay.gradle.plugins.workspace.configurators;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.jvm.tasks.Jar;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;

import groovy.lang.Closure;

public class ClientExtensionProjectConfigurator extends BaseProjectConfigurator {

	public static final String BUILD_FAVICON_TASK_NAME = "buildFavicon";

	public ClientExtensionProjectConfigurator(Settings settings) {
		super(settings);

		_clientExtensionConfigurers.put("favicon", new FaviconConfigurer());
		_clientExtensionConfigurers.put("themeCss", new ThemeCssConfigurer());

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		File clientExtensionFile = project.file(_CLIENT_EXTENSION_YAML);

		try (FileReader fileReader = new FileReader(clientExtensionFile)) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

			JsonNode rootNode = objectMapper.readTree(clientExtensionFile);

			rootNode.fields().forEachRemaining(
				nameNode -> {
					String name = nameNode.getKey();
					JsonNode clientExtensionNode = nameNode.getValue();
					
					try {
						ClientExtension clientExtension = objectMapper.treeToValue(clientExtensionNode, ClientExtension.class);
						clientExtension.name = name;
						
						ClientExtensionConfigurer clientExtensionConfigurer = _clientExtensionConfigurers.get(clientExtension.type);
						if (clientExtensionConfigurer != null) {
							clientExtensionConfigurer.apply(project, clientExtension);
						}
					} 
					catch (JsonProcessingException | IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "client-extension";
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ClientExtension {
		public String name;
		public String type;
		public String description;
		public String sourceCodeUrl;
		public Map<String, Object> typeSettings = new HashMap<>();

		@JsonAnySetter
		public void ignored(String name, Object value) {
		    typeSettings.put(name, value);
		}

		public Object toJSON() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (isExcludedDirName(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path clientExtensionPath = dirPath.resolve(
						_CLIENT_EXTENSION_YAML);

					if (Files.exists(clientExtensionPath)) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "client.extension";

	@SuppressWarnings("serial")
	private Copy _addTaskBuildFavicon(Project project) {
		Copy copy = GradleUtil.addTask(
			project, BUILD_FAVICON_TASK_NAME, Copy.class);

		copy.setDescription("Assembles favicon.");
		copy.setGroup(BasePlugin.BUILD_GROUP);
		copy.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "";
				}

			},
			new Closure<Void>(copy) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					CopySpec fromSrc = copySpec.from(project.file("src"));

					fromSrc.setIncludeEmptyDirs(false);
					fromSrc.include("*.ico");
				}

			});

		copy.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "configurator";
				}

			},
			new Closure<Void>(copy) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					CopySpec fromSrc = copySpec.from(
						project.file("configurator"));

					fromSrc.setIncludeEmptyDirs(false);
					fromSrc.include("*.json");
				}

			});

		copy.setDestinationDir(project.getBuildDir());

		return copy;
	}

	private void _configureArtifacts(Project project, Jar jar) {
		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(Dependency.ARCHIVES_CONFIGURATION, jar);
	}

	private void _configureConfigurationDefault(Project project) {
		Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration archivesConfiguration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	@SuppressWarnings({"serial", "unused"})
	private void _configureRootTaskDistBundle(Project project, Jar jar) {
		Task assembleTask = GradleUtil.getTask(
			project, BasePlugin.ASSEMBLE_TASK_NAME);

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		assembleTask.dependsOn(jar);

		copy.dependsOn(assembleTask);

		copy.into(
			"deploy",
			new Closure<Void>(project) {

				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					Provider<RegularFile> fileProvider = jar.getArchiveFile();

					ConfigurableFileCollection configurableFileCollection =
						project.files(fileProvider);

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(fileProvider);
				}

			});
	}

	private void _configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
	}

	private void _configureTaskDeploy(Project project, Jar jar) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);
		copy.from(jar);
	}

	private void _configureVersion(Project project) {
		project.setVersion("1.0.0");
	}

	private static final String _CLIENT_EXTENSION_YAML =
		"client-extension.yaml";

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private final Map<String, ClientExtensionConfigurer> _clientExtensionConfigurers = new HashMap<>();
	private final boolean _defaultRepositoryEnabled;

	private interface ClientExtensionConfigurer {

		public void apply(Project project, ClientExtension clientExtension);

	}

	private class ThemeCssConfigurer extends FaviconConfigurer {
	}

	private class FaviconConfigurer implements ClientExtensionConfigurer {

		@Override
		public void apply(
			Project project, ClientExtension clientExtension) {

			WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
				(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

			if (isDefaultRepositoryEnabled()) {
				GradleUtil.addDefaultRepositories(project);
			}

			GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

			_configureConfigurationDefault(project);
			_configureVersion(project);

			Copy faviconTask = _addTaskBuildFavicon(project);

			TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
				project, JavaPlugin.JAR_TASK_NAME, Jar.class);

			jarTaskProvider.configure(
				new Action<Jar>() {

					@Override
					public void execute(Jar jar) {
						jar.dependsOn(faviconTask);

						DirectoryProperty destinationDirectoryProperty =
							jar.getDestinationDirectory();

						destinationDirectoryProperty.set(
							new File(project.getProjectDir(), "dist"));

						Property<String> archiveExtensionProperty =
							jar.getArchiveExtension();

						archiveExtensionProperty.set("zip");

						_configureArtifacts(project, jar);
						_configureTaskDeploy(project, jar);
						_configureRootTaskDistBundle(project, jar);
					}

				});

			_configureTaskClean(project);

			project.afterEvaluate(
				new Action<Project>() {

					@Override
					public void execute(Project project) {
						BundleExtension bundleExtension =
							BndUtil.getBundleExtension(project.getExtensions());

						bundleExtension.instruction(
							"Bundle-SymbolicName", "${project.name}");
						bundleExtension.instruction(
							"Bundle-Version", "${project.version}");
						bundleExtension.instruction(
							"Require-Capability",
							"osgi.extender;filter:=\"(&(osgi.extender=osgi.configurator)(version>=1.0)(!(version>=2.0)))\"");
						bundleExtension.instruction(
							"Web-ContextPath", "/${project.name}");

						bundleExtension.instruction("-includeresource.favicon.osgi.config.json", "OSGI-INF/configurator/osgi.config.json;literal='${osgiConfigJsonValue}'");
						bundleExtension.instruction("-includeresource.favicon", "META-INF/resources/=build/;filter:=*.ico;recursive:=false");

						try {
							File lcpJsonFile = project.file("LCP.json");

							if (lcpJsonFile.exists()) {
								bundleExtension.instruction(
									"-includeresource.lcp.json", "LCP.json");
							}
							else {
								String lcpJsonValue = StringUtil.read(
									ClientExtensionProjectConfigurator.class.
										getResourceAsStream(
											"LcpJson_template"));
								bundleExtension.instruction(
									"lcpJsonValue", lcpJsonValue);
								bundleExtension.instruction(
									"-includeresource.lcp.json",
									"LCP.json;literal='${lcpJsonValue}'");
							}

							File dockerfile = project.file("Dockerfile");

							if (dockerfile.exists()) {
								bundleExtension.instruction(
									"-includeresource.dockerfile",
									"Dockerfile");
							}
							else {
								String dockerfileValue = StringUtil.read(
									ClientExtensionProjectConfigurator.class.
										getResourceAsStream(
											"Dockerfile_template"));
								bundleExtension.instruction(
									"dockerfileValue", dockerfileValue);
								bundleExtension.instruction(
									"-includeresource.dockerfile",
									"Dockerfile;literal='${dockerfileValue}'");
							}
						}
						catch (IOException e) {
						}

						bundleExtension.instruction("osgiConfigJsonValue", clientExtension.toJSON());
					}

				});

			addTaskDockerDeploy(project, jarTaskProvider, workspaceExtension);
		}
	}

}