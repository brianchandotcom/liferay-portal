package com.liferay.gradle.plugins.workspace.configurators;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class LXCExtensionProjectConfigurator extends BaseProjectConfigurator {

	public static final String BUILD_FAVICON_TASK_NAME = "buildFavicon";

	public LXCExtensionProjectConfigurator(Settings settings) {
		super(settings);

		_clientExtensionConfigurers.put(
			new ExtensionPoint("favicon", "v1"), new FaviconV1Configurer());
		_clientExtensionConfigurers.put(
			new ExtensionPoint("favicon", "v2"), new FaviconV2Configurer());
		_clientExtensionConfigurers.put(
			new ExtensionPoint("themeCss", "v1"), null);
		_clientExtensionConfigurers.put(
			new ExtensionPoint("themeJs", "v1"), null);

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

			JsonNode node = objectMapper.readTree(clientExtensionFile);

			node.fields(
			).forEachRemaining(
				typeNode -> {
					String type = typeNode.getKey();
					typeNode.getValue(
					).fields(
					).forEachRemaining(
						versionNode -> {
							String version = versionNode.getKey();
							JsonNode extensionNode = versionNode.getValue();

							ExtensionPoint extPt = new ExtensionPoint(
								type, version);

							Class<?> extensionType = _extensionTypes.get(extPt);

							if (extensionType != null) {
								try {
									Object config = objectMapper.treeToValue(
										extensionNode, extensionType);
									ExtentionPointConfigurer
										extensionPointConfigurer =
											_clientExtensionConfigurers.get(
												extPt);

									if (extensionPointConfigurer != null) {
										extensionPointConfigurer.apply(
											project, extPt, config);
									}
								}
								catch (JsonProcessingException e) {

									// TODO Auto-generated catch block

									e.printStackTrace();
								}
								catch (IllegalArgumentException e) {

									// TODO Auto-generated catch block

									e.printStackTrace();
								}
							}
						}
					);
				}
			);
		}
		catch (FileNotFoundException e1) {

			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
		catch (IOException e1) {

			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "lxc-extension";
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FaviconV1 {

		public String toJSON() {
			Map<String, Object> config = new HashMap<>();

			config.put("description", description);

			String urlPrefix = "$[conf:host.service.address]/";
			Matcher urlMatcher = urlPrefixPattern.matcher(url);

			config.put("url", urlMatcher.matches() ? "" : urlPrefix + url);

			Map<String, Object> json = new HashMap<>();

			json.put(
				"com.liferay.client.extensions.factory.configuration.v1.FaviconConfiguration~" +
					name,
				config);

			ObjectMapper objectMapper = new ObjectMapper();

			StringWriter sw = new StringWriter();

			try {
				objectMapper.writeValue(sw, json);
			}
			catch (StreamWriteException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			catch (DatabindException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();
			}

			return sw.toString();
		}

		public String description;

		@JsonProperty(required = true)
		public String name;

		@JsonProperty(required = true)
		public String url;

		protected static final Pattern urlPrefixPattern = Pattern.compile(
			"^https?:\\/\\/");

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FaviconV2 extends FaviconV1 {

		public String toJSON() {
			Map<String, Object> config = new HashMap<>();

			config.put("description", description);

			String urlPrefix = "$[conf:host.service.address]/";
			Matcher urlMatcher = urlPrefixPattern.matcher(url);

			config.put("url", urlMatcher.find() ? url : urlPrefix + url);
			Matcher hdUrlMatcher = urlPrefixPattern.matcher(hdUrl);

			config.put(
				"hdUrl", hdUrlMatcher.find() ? hdUrl : urlPrefix + hdUrl);
			config.put("timestamp", "${tstamp}");

			Map<String, Object> json = new HashMap<>();

			json.put(
				"com.liferay.client.extensions.factory.configuration.v2.FaviconConfiguration~" +
					name,
				config);

			ObjectMapper objectMapper = new ObjectMapper();

			StringWriter sw = new StringWriter();

			try {
				objectMapper.writeValue(sw, json);
			}
			catch (StreamWriteException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			catch (DatabindException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();
			}

			return sw.toString();
		}

		public String hdUrl;

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

	protected static final String NAME = "lxc.extension";

	@SuppressWarnings("serial")
	private Copy _addTaskBuildFavicon(Project project, String configVersion) {
		Copy copy = GradleUtil.addTask(
			project, BUILD_FAVICON_TASK_NAME + configVersion, Copy.class);

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
		project.setVersion("1.0");
	}

	private static final String _CLIENT_EXTENSION_YAML =
		"client-extension.yaml";

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private static final Map<ExtensionPoint, Class<?>> _extensionTypes =
		new HashMap<>();

	private final Map<ExtensionPoint, ExtentionPointConfigurer>
		_clientExtensionConfigurers = new HashMap<>();
	private final boolean _defaultRepositoryEnabled;

	private static class ExtensionPoint {

		public ExtensionPoint(String extensionType, String apiVersion) {
			_extensionType = extensionType;
			_apiVersion = apiVersion;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)

				return true;

			if (obj == null)

				return false;

			if (getClass() != obj.getClass())

				return false;
			ExtensionPoint other = (ExtensionPoint)obj;

			if (Objects.equals(_apiVersion, other._apiVersion) &&
				Objects.equals(_extensionType, other._extensionType)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_apiVersion, _extensionType);
		}

		private final String _apiVersion;
		private final String _extensionType;

	}

	static {
		_extensionTypes.put(
			new ExtensionPoint("favicon", "v1"), FaviconV1.class);
		_extensionTypes.put(
			new ExtensionPoint("favicon", "v2"), FaviconV2.class);
	}

	private interface ExtentionPointConfigurer {

		public void apply(Project project, ExtensionPoint extPt, Object config);

	}

	private class FaviconV1Configurer implements ExtentionPointConfigurer {

		@Override
		public void apply(
			Project project, ExtensionPoint extPt, Object config) {

			WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
				(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

			if (isDefaultRepositoryEnabled()) {
				GradleUtil.addDefaultRepositories(project);
			}

			GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

			FaviconV1 faviconV1 = (FaviconV1)config;

			_configureConfigurationDefault(project);
			_configureVersion(project);

			Copy faviconTask = _addTaskBuildFavicon(
				project, getConfigVersion());

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
							"Web-ContextPath", "${project.name}");
						String configResourceName = getConfigResourceName();

						bundleExtension.instruction(
							"-includeresource." + configResourceName +
								".configjson",
							"OSGI-INF/configurator/" + configResourceName +
								".config.json;literal='${osgiConfigJsonValue" +
									getConfigVersion() + "}'");
						bundleExtension.instruction(
							"-includeresource." + configResourceName,
							"META-INF/resources/=build/;filter:=*.ico;recursive:=false");

						try {
							File lcpJsonFile = project.file("LCP.json");

							if (lcpJsonFile.exists()) {
								bundleExtension.instruction(
									"-includeresource.lcpjson", "LCP.json");
							}
							else {
								String lcpJsonValue = StringUtil.read(
									LXCExtensionProjectConfigurator.class.
										getResourceAsStream(
											"LcpJson_template"));
								bundleExtension.instruction(
									"lcpJsonValue", lcpJsonValue);
								bundleExtension.instruction(
									"-includeresource.lcpjson",
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
									LXCExtensionProjectConfigurator.class.
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

						bundleExtension.instruction(
							"osgiConfigJsonValue" + getConfigVersion(),
							faviconV1.toJSON());
					}

				});

			addTaskDockerDeploy(project, jarTaskProvider, workspaceExtension);
		}

		protected String getConfigResourceName() {
			return "faviconv1";
		}

		protected String getConfigVersion() {
			return "V1";
		}

	}

	private class FaviconV2Configurer extends FaviconV1Configurer {

		@Override
		protected String getConfigResourceName() {
			return "faviconv2";
		}

		protected String getConfigVersion() {
			return "V2";
		}

	}

}