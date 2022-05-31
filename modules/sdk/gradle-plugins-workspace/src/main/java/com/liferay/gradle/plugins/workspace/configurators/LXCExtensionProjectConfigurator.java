package com.liferay.gradle.plugins.workspace.configurators;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
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
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.jvm.tasks.Jar;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import aQute.lib.utf8properties.UTF8Properties;
import groovy.json.JsonSlurper;
import groovy.lang.Closure;

public class LXCExtensionProjectConfigurator extends BaseProjectConfigurator {

	private static class ExtensionPoint {
		@Override
		public int hashCode() {
			return Objects.hash(_apiVersion, _extensionType);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExtensionPoint other = (ExtensionPoint) obj;
			return Objects.equals(_apiVersion, other._apiVersion)
					&& Objects.equals(_extensionType, other._extensionType);
		}

		private String _extensionType;
		private String _apiVersion;

		public ExtensionPoint(String extensionType, String apiVersion) {
			_extensionType = extensionType;
			_apiVersion = apiVersion;
		}
	}
	
//	private static class ClientExtension {
//		Map<String, Map<String, Iterable<Object>>> extensionPoints;
// 	}

	private static Function<Node, Map<String, Object>> faviconV1ConfigGenerator = node -> {
		return new HashMap<>();
	};

	private Function<FaviconV1, Map<String, Object>> faviconV1ConfigGenerator2;

	private static interface ConfigGenerator<T> {
		public Map<String, Object> toJson(T config);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ClientExtension {
		public Favicon favicon;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Favicon {
		public FaviconV1 v1;
		public FaviconV2 v2;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FaviconV1 {
		public String name;
		public String description;
		public String url;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FaviconV2 extends FaviconV1 {
		public String hdUrl;
	}
	private static final Map<ExtensionPoint, Function<Node, Map<String, Object>>> _clientExtensionConfigGenerators = new HashMap<>();
	static {
		_clientExtensionConfigGenerators.put(new ExtensionPoint("favicon", "v1"), faviconV1ConfigGenerator);
		_clientExtensionConfigGenerators.put(new ExtensionPoint("favicon", "v2"), null);
		_clientExtensionConfigGenerators.put(new ExtensionPoint("themeCss", "v1"), null);
		_clientExtensionConfigGenerators.put(new ExtensionPoint("themeJs", "v1"), null);
	}
	
	private static interface ExtentionPointConfigurer<T> {
		public void apply(Project project, ExtensionPoint extPt, Object config);
		public T loadYaml(String yamlContent);
	}
	
	private class FaviconV1Configurer implements ExtentionPointConfigurer<FaviconV1> {

		public FaviconV1 loadYaml(String yamlContent) {
			Yaml yaml = new Yaml();
			return yaml.loadAs(yamlContent, FaviconV1.class);
		}
		
		public Map<String, Object> toJson(FaviconV1 config) {
			return new HashMap<>();
		}

		@Override
		public void apply(Project project, ExtensionPoint extPt, Object config) {
			WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
					(ExtensionAware)project.getGradle(), WorkspaceExtension.class);
			
			if (isDefaultRepositoryEnabled()) {
				GradleUtil.addDefaultRepositories(project);
			}
			
			GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

			BasePluginConvention basePluginConvention = GradleUtil.getConvention(
					project, BasePluginConvention.class);

			FaviconV1 faviconV1 = (FaviconV1)config;

			basePluginConvention.setArchivesBaseName(extPt._extensionType + "-" + faviconV1.name);
			
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
							try {
								BundleExtension bundleExtension =
									BndUtil.getBundleExtension(project.getExtensions());

								bundleExtension.instruction(
									"faviconDockerFrom",
									"rotty3000/lxc-static-resources:latest");

								UTF8Properties properties = new UTF8Properties();

								properties.load(
									LXCExtensionProjectConfigurator.class.
										getResourceAsStream(
											"FaviconBundleInstructions.properties"));

								properties.forEach(
									(key, value) -> {
										String val = value.toString();

										bundleExtension.instruction(
											key.toString(),
											val.replace("\n", "\\n\\\n"));
									});
							}
							catch (IOException ioException) {
								throw new GradleException(
									"Error configuration Bundle Extension",
									ioException);
							}
						}

					});

				addTaskDockerDeploy(project, jarTaskProvider, workspaceExtension);
		}
		
	}

	private final Map<ExtensionPoint, ExtentionPointConfigurer<?>> _clientExtensionGradleConfigurers = new HashMap<>();

	private static final String _CLIENT_EXTENSION_YAML = "client-extension.yaml";

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}
	
	protected static final String NAME = "lxc.extension";

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private final boolean _defaultRepositoryEnabled;
	
	public LXCExtensionProjectConfigurator(Settings settings) {
		super(settings);

		_clientExtensionGradleConfigurers.put(new ExtensionPoint("favicon", "v1"), new FaviconV1Configurer());
		_clientExtensionGradleConfigurers.put(new ExtensionPoint("favicon", "v2"), null);
		_clientExtensionGradleConfigurers.put(new ExtensionPoint("themeCss", "v1"), null);
		_clientExtensionGradleConfigurers.put(new ExtensionPoint("themeJs", "v1"), null);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
				settings,
				WorkspacePlugin.PROPERTY_PREFIX + NAME +
					".default.repository.enabled",
				_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public String getName() {
		return "lxc-extension";
	}

//	public static final CustomClassLoaderConstructor yamlConstructor = new CustomClassLoaderConstructor(
//			ClientExtension.class, ClientExtension.class.getClassLoader());

	
	private static class ExtConstructor extends Constructor {
		private static final TypeDescription faviconV1Type = new TypeDescription(FaviconV1.class);
		@Override
		protected Object constructObject(Node node) {
			if ("favicon".equals(node.getTag().getValue()) && node instanceof MappingNode) {
				MappingNode faviconNode = (MappingNode)node;
				return faviconNode.getValue().stream().collect(
	                    Collectors.toMap(
	                            t -> super.constructObject(t.getKeyNode()),
	                            t -> this.constructObject(t.getValueNode())
	                    )
	            );
			}
			else if ("v1".equals(node.getTag().getValue()) && node instanceof MappingNode) {
				MappingNode v1Node = (MappingNode)node;
				return v1Node.getValue().stream().collect(
						Collectors.toMap(
	                            t -> super.constructObject(t.getKeyNode()),
								t -> {
	                                Node child = t.getValueNode();
	                                child.setType(faviconV1Type.getType());
	                                return super.constructObject(child);
	                            }
						)
				);
			}
			return null;
		}

	}

	@Override
	public void apply(Project project) {
		File clientExtensionFile = project.file(_CLIENT_EXTENSION_YAML);

//		try(InputStream inputStream = new FileInputStream(clientExtensionFile)) {
//			Yaml yaml = new Yaml(yamlConstructor);
//
//			ClientExtension clientExtension = yaml.load(inputStream);
//			clientExtension.extensionPoints.entrySet().forEach(entry -> {
//				System.out.println(entry);
//			});
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		try(FileReader fileReader = new FileReader(clientExtensionFile)) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			ClientExtension clientExtension = objectMapper.readValue(fileReader, ClientExtension.class);
			String url = clientExtension.favicon.v1.url;
			String name = clientExtension.favicon.v1.name;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try(FileReader fileReader = new FileReader(clientExtensionFile)) {
			Yaml yaml = new Yaml();

			Iterable<Node> nodes = yaml.composeAll(fileReader);

			nodes.forEach(node -> {
				MappingNode mappingNode = (MappingNode)node;
				List<NodeTuple> entries = mappingNode.getValue();
				entries.stream().forEach(tuple->{
					ScalarNode typeNode = (ScalarNode)tuple.getKeyNode();
					String type = typeNode.getValue();

					MappingNode valueNode = (MappingNode) tuple.getValueNode();
					List<NodeTuple> versionNodes = valueNode.getValue();

					versionNodes.forEach(versionNode -> {
						ScalarNode versionKey = (ScalarNode)versionNode.getKeyNode();
						String version = versionKey.getValue();
						
						ExtensionPoint extpt = new ExtensionPoint(type, version);
						Function<Node, Map<String, Object>> configGenerator = _clientExtensionConfigGenerators.get(extpt);
						ExtentionPointConfigurer<?> projectConfigurer = _clientExtensionGradleConfigurers.get(extpt);

						if (configGenerator != null && projectConfigurer != null) {
							String dump = yaml.dumpAsMap(versionNode.getValueNode());
							Object config = projectConfigurer.loadYaml(dump);

							projectConfigurer.apply(project, extpt, config);
						}
					});
				});
			});
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				

		/*Map<String, Object> configJsonMap = _getConfigJsonMap(i
				configJsonFile);
		
		configJsonMap.entrySet().stream().forEach(entry -> {
			String name = _getConfigName(entry.getKey());
			String type = _getConfigType((Map<String, Object>) entry.getValue());
			String version = _getConfigVersion(entry.getKey());
			
			if ("favicon".equals(type) && "v1".equals(version)) {
				_applyFaviconV1(project, name, type);
			}
		});*/
	}

	private String _getConfigName(String key) {
		String[] segments = key.split("~");

		return segments[segments.length - 1];
	}

	private void _configureConfigurationDefault(Project project) {
		Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration archivesConfiguration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	private void _configureArtifacts(Project project, Jar jar) {
		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(Dependency.ARCHIVES_CONFIGURATION, jar);
	}
	
	private void _configureTaskDeploy(Project project, Jar jar) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);
		copy.from(jar);
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

	
	public static final String BUILD_FAVICON_TASK_NAME = "buildFavicon";
	
	private void _configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
	}

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
					CopySpec fromSrc = copySpec.from(project.file("configurator"));
					fromSrc.setIncludeEmptyDirs(false);
					fromSrc.include("*.json");
				
				}

			});
		
		copy.setDestinationDir(project.getBuildDir());

		return copy;
	}
	
	private void _configureVersion(Project project) {
		project.setVersion("1.0");
	}
	

	private String _getConfigVersion(String configEntry) {
		String[] segments = configEntry.split("\\.");
		
		return segments[segments.length - 2];
	}

	private String _getConfigType(Map<String, Object> configValue) {
		return configValue.get("type").toString();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getConfigJsonMap(File configJsonFile) {
		if (!configJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(configJsonFile);
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

					Path clientExtensionPath = dirPath.resolve(_CLIENT_EXTENSION_YAML);
					
					if (Files.exists(clientExtensionPath)) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

}
