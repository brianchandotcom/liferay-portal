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

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.AuthenticationContainer;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.PasswordCredentials;
import org.gradle.internal.authentication.DefaultBasicAuthentication;

/**
 * @author Andrea Di Giorgi
 */
public class GradlePluginsDefaultsUtil {

	public static final String DEFAULT_REPOSITORY_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups" +
			"/public";

	public static final String TMP_MAVEN_REPOSITORY_DIR_NAME = ".m2-tmp";

	public static void configureRepositories(
		Project project, File portalRootDir) {

		RepositoryHandler repositoryHandler = project.getRepositories();

		if (!Boolean.getBoolean("maven.local.ignore")) {
			repositoryHandler.mavenLocal();

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

}