/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal.util;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.cache.WriteDigestTask;
import com.liferay.gradle.plugins.defaults.LiferayThemeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.task.WriteArtifactPublishCommandsTask;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.internal.artifacts.repositories.DefaultMavenArtifactRepository;
import org.gradle.api.logging.Logger;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.util.GUtil;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 * @author Peter Shin
 */
public class LiferayRelengUtil {

	public static String getArtifactRemoteURL(
		Project project, PublishArtifact publishArtifact, boolean cdn) {

		StringBuilder sb = _getArtifactRemoteBaseURL(project, cdn);

		String name = GradleUtil.getArchivesBaseName(project);

		sb.append(name);

		sb.append('/');
		sb.append(project.getVersion());
		sb.append('/');
		sb.append(name);
		sb.append('-');
		sb.append(project.getVersion());

		String classifier = publishArtifact.getClassifier();

		if (Validator.isNotNull(classifier)) {
			sb.append('-');
			sb.append(classifier);
		}

		sb.append('.');
		sb.append(publishArtifact.getExtension());

		return sb.toString();
	}

	public static File getRelengDir(File projectDir) {
		File rootDir = GradleUtil.getRootDir(projectDir, _RELENG_DIR_NAME);

		if (rootDir == null) {
			return null;
		}

		File relengDir = new File(rootDir, _RELENG_DIR_NAME);

		return new File(relengDir, FileUtil.relativize(projectDir, rootDir));
	}

	public static File getRelengDir(Project project) {
		return getRelengDir(project.getProjectDir());
	}

	public static String getUnpublishedDependencyName(Project project) {
		List<File> artifactPropertiesFiles = _getArtifactPropertiesFiles(
			project, false);

		for (File artifactPropertiesFile : artifactPropertiesFiles) {
			File artifactProjectDir = _getArtifactProjectDir(
				artifactPropertiesFile);

			if (hasUnpublishedCommits(
					project, artifactProjectDir, artifactPropertiesFile)) {

				return artifactProjectDir.getName();
			}
		}

		return null;
	}

	public static boolean hasStaleParentTheme(Project project) {
		WriteDigestTask writeDigestTask = (WriteDigestTask)GradleUtil.getTask(
			project,
			LiferayThemeDefaultsPlugin.WRITE_PARENT_THEMES_DIGEST_TASK_NAME);

		if (!Objects.equals(
				writeDigestTask.getDigest(), writeDigestTask.getOldDigest())) {

			Logger logger = project.getLogger();

			if (logger.isInfoEnabled()) {
				logger.info("The digest for {} has changed.", writeDigestTask);
			}

			return true;
		}

		return false;
	}

	public static boolean hasStalePortalDependencies(
		Project project, File artifactPropertiesFile) {

		if (!artifactPropertiesFile.exists()) {
			return false;
		}

		List<File> artifactPropertiesFiles = _getArtifactPropertiesFiles(
			project, true);

		for (File curArtifactPropertiesFile : artifactPropertiesFiles) {
			Properties artifactProperties = GUtil.loadProperties(
				curArtifactPropertiesFile);

			String artifactUrl = artifactProperties.getProperty("artifact.url");

			if (Validator.isNull(artifactUrl)) {
				File artifactProjectDir = _getArtifactProjectDir(
					curArtifactPropertiesFile);

				throw new GradleException(
					"The dependency '" + artifactProjectDir.getName() +
						"' has never been published");
			}

			String[] tokens = artifactUrl.split("/");

			String artifactName = tokens[tokens.length - 3];

			String compatVersion = GradleUtil.getProperty(
				project, "build.compat.version." + artifactName, (String)null);

			if (Validator.isNotNull(compatVersion)) {
				continue;
			}

			VersionNumber artifactVersionNumber = VersionNumber.parse(
				tokens[tokens.length - 2]);

			File artifactProjectDir = _getArtifactProjectDir(
				curArtifactPropertiesFile);

			Properties properties = GUtil.loadProperties(
				new File(artifactProjectDir, "bnd.bnd"));

			VersionNumber versionNumber = VersionNumber.parse(
				properties.getProperty(Constants.BUNDLE_VERSION));

			if (versionNumber.getMinor() == artifactVersionNumber.getMinor()) {
				continue;
			}

			Properties bndProperties = null;

			try {
				bndProperties = FileUtil.readProperties(project, "bnd.bnd");
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}

			String includeResource = bndProperties.getProperty(
				Constants.INCLUDERESOURCE);

			if (Validator.isNotNull(includeResource) &&
				includeResource.contains(artifactName)) {

				return true;
			}
		}

		return false;
	}

	public static boolean hasStaleUnstyledTheme(
		Project project, File artifactPropertiesFile) {

		String projectName = project.getName();

		if (!projectName.startsWith("frontend-theme") ||
			!artifactPropertiesFile.exists()) {

			return false;
		}

		Properties artifactProperties = GUtil.loadProperties(
			artifactPropertiesFile);

		String artifactGitId = artifactProperties.getProperty(
			"artifact.git.id");

		if (Validator.isNull(artifactGitId)) {
			return false;
		}

		Project parentThemeUnstyledProject = GradleUtil.getProject(
			project.getRootProject(),
			GradlePluginsDefaultsUtil.PARENT_THEME_UNSTYLED_PROJECT_NAME);

		if (parentThemeUnstyledProject == null) {
			return false;
		}

		String result = GitUtil.getGitResult(
			project, parentThemeUnstyledProject.getProjectDir(), "log",
			"--format=%s", artifactGitId + "..HEAD", ":(exclude)test", ".");

		for (String line : result.split("\\r?\\n")) {
			if (Validator.isNull(line) ||
				line.contains(_IGNORED_MESSAGE_PATTERN)) {

				continue;
			}

			return true;
		}

		return false;
	}

	public static boolean hasUnpublishedCommits(
		Project project, File artifactProjectDir, File artifactPropertiesFile) {

		Logger logger = project.getLogger();

		Properties artifactProperties = new Properties();

		if (artifactPropertiesFile.exists()) {
			artifactProperties = GUtil.loadProperties(artifactPropertiesFile);
		}

		String artifactGitId = artifactProperties.getProperty(
			"artifact.git.id");

		if (Validator.isNull(artifactGitId)) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"The project '{}' has never been published.",
					artifactProjectDir.getName());
			}

			return true;
		}

		Project rootProject = project.getRootProject();

		File gitResultsDir = new File(
			rootProject.getBuildDir(), "releng/git-results");

		StringBuilder sb = new StringBuilder();

		sb.append(artifactProjectDir.getName());
		sb.append('-');
		sb.append(artifactGitId);
		sb.append('-');

		File file = new File(gitResultsDir, sb.toString() + "true");

		if (file.exists()) {
			return true;
		}

		file = new File(gitResultsDir, sb.toString() + "false");

		if (file.exists()) {
			return false;
		}

		String result = GitUtil.getGitResult(
			project, artifactProjectDir, "log", "--format=%s",
			artifactGitId + "..HEAD", ":(exclude)test", ".");

		String[] lines = result.split("\\r?\\n");

		for (String line : lines) {
			if (logger.isInfoEnabled()) {
				logger.info("Git Commit: {}", line);
			}

			if (Validator.isNull(line) ||
				line.contains(_IGNORED_MESSAGE_PATTERN)) {

				continue;
			}

			_createNewFile(new File(gitResultsDir, sb.toString() + "true"));

			return true;
		}

		_createNewFile(new File(gitResultsDir, sb.toString() + "false"));

		return false;
	}

	private static void _createNewFile(File file) {
		File dir = file.getParentFile();

		try {
			Files.createDirectories(dir.toPath());

			file.createNewFile();
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	private static File _getArtifactProjectDir(File artifactPropertiesFile) {
		String fileName = artifactPropertiesFile.getName();

		if (Objects.equals(fileName, _ARTIFACT_PROPERTIES_FILE_NAME)) {
			File artifactPropertiesDir = artifactPropertiesFile.getParentFile();

			File relengRootDir = GradleUtil.getRootDir(
				artifactPropertiesDir, _RELENG_DIR_NAME);

			while (true) {
				File rootDir = GradleUtil.getRootDir(
					relengRootDir.getParentFile(), _RELENG_DIR_NAME);

				if (rootDir == null) {
					break;
				}

				relengRootDir = rootDir;
			}

			File relengDir = new File(relengRootDir, _RELENG_DIR_NAME);

			String relativePath = FileUtil.relativize(
				artifactPropertiesDir, relengDir);

			return new File(relengRootDir, relativePath);
		}

		File portalRootDir = GradleUtil.getRootDir(
			artifactPropertiesFile.getParentFile(), "portal-impl");

		int pos = fileName.lastIndexOf('.');

		return new File(portalRootDir, fileName.substring(0, pos));
	}

	private static List<File> _getArtifactPropertiesFiles(
		Project project, boolean portalOnly) {

		List<File> artifactPropertiesFiles = new ArrayList<>();

		for (Configuration configuration : project.getConfigurations()) {
			String configurationName = configuration.getName();

			if (configurationName.startsWith("test")) {
				continue;
			}

			for (Dependency dependency : configuration.getDependencies()) {
				if (dependency instanceof ProjectDependency) {
					if (portalOnly) {
						continue;
					}

					ProjectDependency projectDependency =
						(ProjectDependency)dependency;

					File relengDir = getRelengDir(
						projectDependency.getDependencyProject());

					if (relengDir != null) {
						File propertiesFile = new File(
							relengDir, _ARTIFACT_PROPERTIES_FILE_NAME);

						artifactPropertiesFiles.add(propertiesFile);
					}
				}
				else if (configurationName.startsWith("compile")) {
					File propertiesFile = _getPortalArtifactPropertiesFile(
						project, dependency);

					if (propertiesFile != null) {
						artifactPropertiesFiles.add(propertiesFile);
					}
				}
			}
		}

		return artifactPropertiesFiles;
	}

	private static StringBuilder _getArtifactRemoteBaseURL(
		Project project, boolean cdn) {

		String url = null;

		PublishingExtension publishingExtension = GradleUtil.getExtension(
			project, PublishingExtension.class);

		RepositoryHandler repositoryHandler =
			publishingExtension.getRepositories();

		Iterator<ArtifactRepository> iterator = repositoryHandler.iterator();

		while (iterator.hasNext()) {
			ArtifactRepository artifactRepository = iterator.next();

			if (artifactRepository instanceof DefaultMavenArtifactRepository) {
				DefaultMavenArtifactRepository defaultMavenArtifactRepository =
					(DefaultMavenArtifactRepository)artifactRepository;

				String curURL = String.valueOf(
					defaultMavenArtifactRepository.getUrl());

				if (!curURL.contains("liferay.com")) {
					continue;
				}

				url = curURL;

				if (cdn) {
					url = curURL.replace(
						"repository.liferay.com", "repository-cdn.liferay.com");
				}

				break;
			}
		}

		if (url == null) {
			throw new GradleException("Unable to get Nexus repository url");
		}

		StringBuilder sb = new StringBuilder(url);

		if (sb.charAt(sb.length() - 1) != '/') {
			sb.append('/');
		}

		String group = String.valueOf(project.getGroup());

		sb.append(group.replace('.', '/'));

		sb.append('/');

		return sb;
	}

	private static File _getPortalArtifactPropertiesFile(
		Project project, Dependency dependency) {

		String dependencyGroup = dependency.getGroup();

		if (!Objects.equals(dependencyGroup, "com.liferay.portal")) {
			return null;
		}

		String dependencyVersion = dependency.getVersion();

		if (!Objects.equals(dependencyVersion, "default")) {
			return null;
		}

		String dependencyName = dependency.getName();

		if (!dependencyName.startsWith("com.liferay.")) {
			return null;
		}

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		if (portalRootDir == null) {
			return null;
		}

		String portalProjectName = dependencyName.substring(12);

		File portalProjectDir = new File(
			portalRootDir, portalProjectName.replace('.', '-'));

		if (!portalProjectDir.exists()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_MODULES_DIR_NAME);
		sb.append('/');
		sb.append(_RELENG_DIR_NAME);
		sb.append('/');
		sb.append(portalProjectDir.getName());
		sb.append(".properties");

		return new File(portalRootDir, sb.toString());
	}

	private static final String _ARTIFACT_PROPERTIES_FILE_NAME =
		"artifact.properties";

	private static final String _IGNORED_MESSAGE_PATTERN =
		WriteArtifactPublishCommandsTask.IGNORED_MESSAGE_PATTERN;

	private static final String _MODULES_DIR_NAME = "modules";

	private static final String _RELENG_DIR_NAME = ".releng";

}