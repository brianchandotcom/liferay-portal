/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bnd.invoker;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Shuyang Zhou
 */
public class BndInvokerTask extends Task {

	@Override
	public void execute() throws BuildException {
		Properties properties = new Properties();

		Project project = getProject();

		properties.putAll(project.getProperties());

		try (StringWriter stringWriter = new StringWriter()) {
			properties.store(stringWriter, null);

			_invoke(stringWriter.toString(), project.getBaseDir(), _output);
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setOutput(File output) {
		_output = output;
	}

	private Set<URL> _getStaticURLs() throws MalformedURLException {
		if (_staticURLs == null) {
			_staticURLs = new LinkedHashSet<>();

			ProtectionDomain protectionDomain =
				BndInvokerTask.class.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			_staticURLs.add(codeSource.getLocation());

			String classPath = System.getProperty("java.class.path");

			for (String path : classPath.split(File.pathSeparator)) {
				File file = new File(path);

				URI uri = file.toURI();

				_staticURLs.add(uri.toURL());
			}
		}

		return _staticURLs;
	}

	private void _invoke(String properties, File baseDir, File output)
		throws Exception {

		Set<URL> urls = new LinkedHashSet<>();

		URL url = BndInvokerTask.class.getResource("/lib/bnd.jar");

		Path tempBndPath = Files.createTempFile(null, null);

		try (InputStream inputStream = url.openStream()) {
			Files.copy(
				inputStream, tempBndPath, StandardCopyOption.REPLACE_EXISTING);

			URI tempBndURI = tempBndPath.toUri();

			urls.add(tempBndURI.toURL());

			urls.addAll(_getStaticURLs());

			ClassLoader classLoader = new URLClassLoader(
				urls.toArray(new URL[0]), null);

			Class<?> clazz = classLoader.loadClass(
				BndInvokerUtil.class.getName());

			Method method = clazz.getMethod(
				"invoke", String.class, File.class, File.class);

			method.invoke(null, properties, baseDir, output);
		}
		finally {
			File tempBndFile = tempBndPath.toFile();

			if (!tempBndFile.delete()) {
				tempBndFile.deleteOnExit();
			}
		}
	}

	private static Set<URL> _staticURLs;

	private File _output;

}