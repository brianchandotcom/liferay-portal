/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder.maven;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.css.builder.CSSBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.component.repository.ComponentDependency;
import org.codehaus.plexus.util.Scanner;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * Compiles CSS files.
 *
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 * @goal build
 */
public class BuildCSSMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			boolean artifactPresent = false;

			if (_cssBuilderArgs.getImportPaths() == null) {
				for (Dependency dependency : _project.getDependencies()) {
					String artifactId = dependency.getArtifactId();

					if (artifactId.equals("com.liferay.frontend.css.common")) {
						Artifact artifact = _resolveArtifact(dependency);

						if (artifact != null) {
							_cssBuilderArgs.setImportPaths(
								Arrays.asList(artifact.getFile()));
						}

						artifactPresent = true;

						break;
					}
				}
			}

			if (!artifactPresent &&
				(_cssBuilderArgs.getImportPaths() == null)) {

				for (ComponentDependency componentDependency :
						_pluginDescriptor.getDependencies()) {

					String artifactId = componentDependency.getArtifactId();

					if (artifactId.equals("com.liferay.frontend.css.common")) {
						Artifact artifact = _resolveArtifact(
							componentDependency);

						_cssBuilderArgs.setImportPaths(
							Arrays.asList(artifact.getFile()));

						break;
					}
				}
			}

			if (_buildContext.isIncremental()) {
				Scanner scanner = _buildContext.newScanner(_projectBaseDir);

				scanner.setIncludes(new String[] {"", "**/*.scss"});

				scanner.scan();

				String[] includedFiles = scanner.getIncludedFiles();

				if ((includedFiles != null) && (includedFiles.length > 0)) {
					_execute();
				}
			}
			else {
				_execute();
			}
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
		}
	}

	/**
	 * @parameter default-value="true"
	 */
	public void setAppendCssImportTimestamps(
		boolean appendCssImportTimestamps) {

		_cssBuilderArgs.setAppendCssImportTimestamps(appendCssImportTimestamps);
	}

	/**
	 * @parameter default-value="${project.build.directory}/${project.build.finalName}"
	 */
	public void setBaseDir(File baseDir) {
		_cssBuilderArgs.setBaseDir(baseDir);
	}

	/**
	 * @parameter
	 */
	public void setDirNames(String dirNames) {
		_cssBuilderArgs.setDirNames(dirNames);
	}

	/**
	 * @parameter
	 */
	public void setExcludes(String excludes) {
		_cssBuilderArgs.setExcludes(excludes);
	}

	/**
	 * @parameter
	 */
	public void setGenerateSourceMap(boolean generateSourceMap) {
		_cssBuilderArgs.setGenerateSourceMap(generateSourceMap);
	}

	/**
	 * @parameter
	 */
	public void setImportDir(File importDir) {
		_cssBuilderArgs.setImportPaths(Arrays.asList(importDir));
	}

	/**
	 * @parameter default-value=".sass-cache/"
	 */
	public void setOutputDirName(String outputDirName) {
		_cssBuilderArgs.setOutputDirName(outputDirName);
	}

	/**
	 * @parameter
	 */
	public void setPrecision(int precision) {
		_cssBuilderArgs.setPrecision(precision);
	}

	/**
	 * @parameter
	 */
	public void setRtlExcludedPathRegexps(String rtlExcludedPathRegexps) {
		_cssBuilderArgs.setRtlExcludedPathRegexps(rtlExcludedPathRegexps);
	}

	/**
	 * @parameter
	 */
	public void setSassCompilerClassName(String sassCompilerClassName) {
		_cssBuilderArgs.setSassCompilerClassName(sassCompilerClassName);
	}

	private void _execute() throws Exception {
		try (CSSBuilder cssBuilder = new CSSBuilder(_cssBuilderArgs)) {
			cssBuilder.execute();
		}
	}

	private Artifact _resolveArtifact(Artifact artifact)
		throws ArtifactResolutionException {

		ArtifactRequest artifactRequest = new ArtifactRequest();

		artifactRequest.setArtifact(artifact);

		List<RemoteRepository> repositories = new ArrayList<>();

		repositories.addAll(_project.getRemotePluginRepositories());
		repositories.addAll(_project.getRemoteProjectRepositories());

		artifactRequest.setRepositories(repositories);

		ArtifactResult artifactResult = _repositorySystem.resolveArtifact(
			_repositorySystemSession, artifactRequest);

		return artifactResult.getArtifact();
	}

	private Artifact _resolveArtifact(ComponentDependency componentDependency)
		throws ArtifactResolutionException {

		Artifact artifact = new DefaultArtifact(
			componentDependency.getGroupId(),
			componentDependency.getArtifactId(), componentDependency.getType(),
			componentDependency.getVersion());

		return _resolveArtifact(artifact);
	}

	private Artifact _resolveArtifact(Dependency dependency)
		throws ArtifactResolutionException {

		Artifact artifact = new DefaultArtifact(
			dependency.getGroupId(), dependency.getArtifactId(),
			dependency.getType(), dependency.getVersion());

		return _resolveArtifact(artifact);
	}

	/**
	 * @component
	 */
	private BuildContext _buildContext;

	private final CSSBuilderArgs _cssBuilderArgs = new CSSBuilderArgs();

	/**
	 * @parameter default-value="${plugin}"
	 * @readonly
	 * @required
	 */
	private PluginDescriptor _pluginDescriptor;

	/**
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	private MavenProject _project;

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	private File _projectBaseDir;

	/**
	 * @component
	 */
	private RepositorySystem _repositorySystem;

	/**
	 * @parameter property="repositorySystemSession"
	 * @readonly
	 * @required
	 */
	private RepositorySystemSession _repositorySystemSession;

}