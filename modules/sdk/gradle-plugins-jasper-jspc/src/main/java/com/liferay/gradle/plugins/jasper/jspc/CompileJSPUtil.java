/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.jasper.jspc;

import java.net.URL;

import java.util.Deque;
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

import org.gradle.api.GradleException;

/**
 * @author Drew Brokke
 */
public class CompileJSPUtil {

	public static void compileJSP(
		String compilerClassName, String[] completeArgs,
		String jspCClasspathPath) {

		JspC jspC = new JspC() {

			@Override
			public String getCompilerClassName() {
				return compilerClassName;
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
			jspC.setArgs(completeArgs);
			jspC.setClassPath(jspCClasspathPath);

			jspC.execute();
		}
		catch (Exception exception) {
			throw new GradleException(exception.getMessage(), exception);
		}
	}

}