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

package com.liferay.gradle.plugins.defaults.internal.util;

import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.AuthenticationContainer;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.PasswordCredentials;
import org.gradle.internal.authentication.DefaultBasicAuthentication;
import org.gradle.util.GradleVersion;

/**
 * @author Andrea Di Giorgi
 */
public class GradlePluginsDefaultsUtil {

	public static final String DEFAULT_REPOSITORY_URL =
		"https://repository-cdn.liferay.com/nexus/content/groups/public";

	public static final String[] JSON_VERSION_FILE_NAMES = {
		"npm-shrinkwrap.json", "package-lock.json", "package.json"
	};

	public static final String[] PARENT_THEME_PROJECT_NAMES = {
		"frontend-theme-styled", "frontend-theme-unstyled"
	};

	public static final String SNAPSHOT_PROPERTY_NAME = "snapshot";

	public static final String SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT";

	public static final String TMP_MAVEN_REPOSITORY_DIR_NAME = ".m2-tmp";

	public static final Pattern jsonVersionPattern = Pattern.compile(
		"\\n(\\t|  )\"version\": \"(.+)\"");

	public static void configureRepositories(
		Project project, File portalRootDir) {

		RepositoryHandler repositoryHandler = project.getRepositories();

		if (!Boolean.getBoolean("maven.local.ignore")) {
			GradleVersion gradleVersion = GradleVersion.current();

			if (gradleVersion.compareTo(GradleVersion.version("6.0")) > 0) {
				repositoryHandler.mavenLocal(
					new Action<MavenArtifactRepository>() {

						@Override
						public void execute(
							MavenArtifactRepository mavenArtifactRepository) {

							MavenArtifactRepository.MetadataSources
								metadataSources =
									mavenArtifactRepository.
										getMetadataSources();

							metadataSources.mavenPom();

							metadataSources.artifact();
						}

					});
			}

			File tmpMavenRepositoryDir = null;

			if (portalRootDir != null) {
				tmpMavenRepositoryDir = new File(
					portalRootDir, TMP_MAVEN_REPOSITORY_DIR_NAME);
			}

			if ((tmpMavenRepositoryDir != null) &&
				tmpMavenRepositoryDir.exists()) {

				GradleUtil.addMavenArtifactRepository(
					repositoryHandler, tmpMavenRepositoryDir);
			}
		}

		String url = System.getProperty(
			"repository.url", DEFAULT_REPOSITORY_URL);

		GradleUtil.addMavenArtifactRepository(repositoryHandler, url);

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

	public static Set<String> getBuildProfileFileNames(
		String buildProfile, boolean publicBranch) {

		if (Validator.isNull(buildProfile)) {
			return null;
		}

		String suffix = "private";

		if (publicBranch) {
			suffix = "public";
		}

		Set<String> fileNames = new HashSet<>();

		if (Objects.equals(buildProfile, "dxp")) {
			buildProfile = "portal";
		}

		fileNames.add(_BUILD_PROFILE_FILE_NAME_PREFIX + buildProfile);
		fileNames.add(
			_BUILD_PROFILE_FILE_NAME_PREFIX + buildProfile + "-" + suffix);

		if (buildProfile.equals("portal-deprecated")) {
			fileNames.add(_BUILD_PROFILE_FILE_NAME_PREFIX + "portal");
			fileNames.add(_BUILD_PROFILE_FILE_NAME_PREFIX + "portal-" + suffix);
		}

		return fileNames;
	}

	@SuppressWarnings("unchecked")
	public static boolean hasNPMParentThemesDependencies(Project project) {
		if (!isSubrepository(project)) {
			return false;
		}

		File packageJSONFile = project.file("package.json");

		if (!packageJSONFile.exists()) {
			return false;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> packageJSONMap =
			(Map<String, Object>)jsonSlurper.parse(packageJSONFile);

		Map<String, Object> devDependencies =
			(Map<String, Object>)packageJSONMap.get("devDependencies");

		if (devDependencies == null) {
			return false;
		}

		for (String key : devDependencies.keySet()) {
			if (key.startsWith("liferay-theme-deps-")) {
				return true;
			}
		}

		for (String parentThemeProjectName : PARENT_THEME_PROJECT_NAMES) {
			String name = "liferay-" + parentThemeProjectName;

			if (!devDependencies.containsKey(name)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isPrivateProject(Project project) {
		String path = project.getPath();

		if (path.startsWith(":dxp:") || path.startsWith(":private:")) {
			return true;
		}

		return false;
	}

	public static boolean isSnapshot(Project project) {
		String version = String.valueOf(project.getVersion());

		if (version.endsWith(SNAPSHOT_VERSION_SUFFIX)) {
			return true;
		}

		return false;
	}

	public static boolean isSnapshot(Project project, String... propertyNames) {
		boolean snapshot = false;

		if (project.hasProperty(SNAPSHOT_PROPERTY_NAME)) {
			snapshot = GradleUtil.getProperty(
				project, SNAPSHOT_PROPERTY_NAME, true);
		}

		if (!snapshot) {
			for (String propertyName : propertyNames) {
				if (project.hasProperty(propertyName) &&
					GradleUtil.getProperty(project, propertyName, true)) {

					snapshot = true;

					break;
				}
			}
		}

		return snapshot;
	}

	public static boolean isSubrepository(Project project) {
		File gitRepoDir = GradleUtil.getRootDir(
			project, GitRepo.GIT_REPO_FILE_NAME);

		if (gitRepoDir != null) {
			return true;
		}

		String[] dirNames = {"build-working-dir.xml", "portal-impl"};

		for (String dirName : dirNames) {
			if (GradleUtil.getRootDir(project, dirName) != null) {
				return false;
			}
		}

		return true;
	}

	public static boolean isTestProject(File dir) {
		String dirName = dir.getName();

		if (dirName.endsWith(_TEST_PROJECT_SUFFIX)) {
			return true;
		}

		return false;
	}

	public static boolean isTestProject(Project project) {
		String projectName = project.getName();

		if (projectName.endsWith(_TEST_PROJECT_SUFFIX)) {
			return true;
		}

		return false;
	}

	public static void setProjectSnapshotVersion(
		Project project, String... propertyNames) {

		String version = String.valueOf(project.getVersion());

		if (isSnapshot(project, propertyNames) &&
			!version.endsWith(SNAPSHOT_VERSION_SUFFIX)) {

			project.setVersion(version + SNAPSHOT_VERSION_SUFFIX);
		}
	}

	private static final String _BUILD_PROFILE_FILE_NAME_PREFIX = ".lfrbuild-";

	private static final String _TEST_PROJECT_SUFFIX = "-test";

}