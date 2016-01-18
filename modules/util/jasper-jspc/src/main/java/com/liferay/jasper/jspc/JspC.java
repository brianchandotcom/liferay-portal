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

package com.liferay.jasper.jspc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.Iterator;
import java.util.List;

import org.apache.jasper.JasperException;

/**
 * @author Shuyang Zhou
 */
public class JspC extends org.apache.jasper.JspC {

	public static void main(String[] args) {
		try {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));

			String classPath = bufferedReader.readLine();

			if ((classPath != null) && !classPath.isEmpty()) {
				_runWithClassPath(classPath, args);

				return;
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		JspC jspc = new JspC();

		try {
			jspc.setArgs(args);

			jspc.execute();
		}
		catch (Exception e1) {
			e1.printStackTrace();

			try {
				Field noDieLevelField =
					org.apache.jasper.JspC.class.getDeclaredField(
						"NO_DIE_LEVEL");

				noDieLevelField.setAccessible(true);

				int dieLevel = jspc.getDieLevel();

				if (dieLevel != (Integer)noDieLevelField.get(null)) {
					System.exit(dieLevel);
				}
			}
			catch (Exception e2) {
				e2.addSuppressed(e1);

				throw new RuntimeException(e2);
			}
		}
	}

	public JspC() {
		List<String> pages = null;

		try {
			Field pagesField = org.apache.jasper.JspC.class.getDeclaredField(
				"pages");

			pagesField.setAccessible(true);

			pages = (List<String>)pagesField.get(this);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_pages = pages;
	}

	@Override
	public void scanFiles(File baseDir) throws JasperException {
		super.scanFiles(baseDir);

		Iterator<String> iterator = _pages.iterator();

		while (iterator.hasNext()) {
			String page = iterator.next();

			if (File.separatorChar != '/') {
				page = page.replace(File.separatorChar, '/');
			}

			if (page.contains("/docroot/META-INF/custom_jsps/")) {
				iterator.remove();
			}
		}
	}

	private static void _runWithClassPath(String classpath, String[] args)
		throws Exception {

		classpath =
			System.getProperty("java.class.path") + File.pathSeparator +
				classpath;

		String[] files = classpath.split(File.pathSeparator);

		URL[] urls = new URL[files.length];

		for (int i = 0; i < files.length; i++) {
			File file = new File(files[i]);

			URI uri = file.toURI();

			urls[i] = uri.toURL();
		}

		ClassLoader classLoader = new URLClassLoader(urls, null);

		Class<?> jspcClass = classLoader.loadClass(JspC.class.getName());

		Object jspc = jspcClass.newInstance();

		Method setArgsMethod = jspcClass.getMethod("setArgs", String[].class);

		setArgsMethod.invoke(jspc, new Object[] {args});

		Method executeMethod = jspcClass.getMethod("execute");

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);

		try {
			executeMethod.invoke(jspc);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final List<String> _pages;

}