/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.net.URL;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jasper.JspC;
import org.apache.jasper.servlet.JspCServletContext;
import org.apache.jasper.servlet.TldScanner;
import org.apache.tomcat.JarScanType;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.JarScannerCallback;
import org.apache.tomcat.util.scan.StandardJarScanner;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class CompileJSPTask extends DefaultTask {

	@TaskAction
	public void compileJSP() {
		FileCollection jspCClasspath = getJspCClasspath();

		JspC jspC = new JspC() {

			@Override
			public String getCompilerClassName() {
				return _getCompilerClassName();
			}

			@Override
			protected TldScanner newTldScanner(
				JspCServletContext jspCServletContext, boolean namespaceAware,
				boolean validate, boolean blockExternal) {

				return new TldScanner(
					jspCServletContext, namespaceAware, validate,
					blockExternal) {

					@Override
					public void scanJars() {
						jspCServletContext.setAttribute(
							JarScanner.class.getName(),
							new StandardJarScanner() {

								protected void processURLs(
									JarScanType scanType,
									JarScannerCallback callback,
									Set<URL> processedURLs, boolean webApp,
									Deque<URL> classPathUrlsToProcess) {

									if (!webApp) {
										classPathUrlsToProcess.clear();

										return;
									}

									super.processURLs(
										scanType, callback, processedURLs,
										webApp, classPathUrlsToProcess);
								}

							});

						super.scanJars();
					}

				};
			}

		};

		Logger logger = Logger.getLogger("org.apache.tomcat");

		logger.setLevel(Level.INFO);

		try {
			jspC.setArgs(_getCompleteArgs());
			jspC.setClassPath(jspCClasspath.getAsPath());

			jspC.execute();
		}
		catch (Exception exception) {
			throw new GradleException(exception.getMessage(), exception);
		}
	}

	@Classpath
	@Optional
	public FileCollection getCompileJspClasspath() {
		return _compileJspClasspath;
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@Classpath
	public FileCollection getJspCClasspath() {
		return _jspCClasspath;
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	@SkipWhenEmpty
	public FileCollection getJSPFiles() {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", getWebAppDir());

		List<String> excludes = new ArrayList<>(2);

		excludes.add("**/custom_jsps/**/*");
		excludes.add("**/dependencies/**/*");

		args.put("excludes", excludes);

		args.put("include", "**/*.jsp");

		return project.fileTree(args);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getWebAppDir() {
		return GradleUtil.toFile(getProject(), _webAppDir);
	}

	public void setCompileJspClasspath(FileCollection compileJspClasspath) {
		_compileJspClasspath = compileJspClasspath;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setJspCClasspath(FileCollection jspCClasspath) {
		_jspCClasspath = jspCClasspath;
	}

	public void setWebAppDir(Object webAppDir) {
		_webAppDir = webAppDir;
	}

	private String _getCompilerClassName() {
		JavaVersion javaVersion = JavaVersion.current();

		if (!Boolean.getBoolean("build.jakarta.transformer.enabled") ||
			(javaVersion.compareTo(JavaVersion.VERSION_17) < 0)) {

			return null;
		}

		String dirNames = System.getProperty(
			"build.jakarta.transformer.include.dirs");

		if (Validator.isNull(dirNames)) {
			return null;
		}

		Project project = getProject();

		File portalModulesDir = project.getRootDir();

		Path portalModulesPath = portalModulesDir.toPath();

		File projectDir = project.getProjectDir();

		Path projectPath = projectDir.toPath();

		for (String dirName : dirNames.split(",")) {
			if (projectPath.startsWith(portalModulesPath.resolve(dirName))) {
				return JakartaTransformerJDTCompiler.class.getName();
			}
		}

		return null;
	}

	private String[] _getCompleteArgs() {
		return new String[] {
			"-d", FileUtil.getAbsolutePath(getDestinationDir()),
			"-no-strictQuoteEscaping", "-webapp",
			FileUtil.getAbsolutePath(getWebAppDir())
		};
	}

	private FileCollection _compileJspClasspath;
	private Object _destinationDir;
	private FileCollection _jspCClasspath;
	private Object _webAppDir;

}