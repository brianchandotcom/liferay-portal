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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.JspConfig;
import org.apache.jasper.compiler.TldLocation;
import org.apache.jasper.compiler.TldLocationsCache;
import org.apache.jasper.compiler.WebXml;
import org.apache.jasper.servlet.JspCServletContext;
import org.apache.tomcat.util.scan.Constants;

/**
 * @author Shuyang Zhou
 */
public class JspC extends org.apache.jasper.JspC {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(WebXml.class.getName());

		logger.setLevel(Level.SEVERE);

		JspC jspc = new JspC();

		try {
			jspc.setArgs(args);

			jspc.execute();
		}
		catch (Exception e) {
			System.err.println(e);

			if (jspc.dieLevel != NO_DIE_LEVEL) {
				System.exit(jspc.dieLevel);
			}
		}
	}

	@Override
	public void scanFiles(File baseDir) throws JasperException {
		super.scanFiles(baseDir);

		Iterator<String> iterator = pages.iterator();

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

	@Override
	protected void initServletContext() {
		super.initServletContext();

		if (Boolean.getBoolean("jspc.module.web")) {
			final String portalDir = System.getProperty("jspc.portal.dir");

			if ((portalDir == null) || portalDir.isEmpty()) {
				throw new RuntimeException(
					"The system property \"jspc.portal.dir\" is not set");
			}

			try {
				tldLocationsCache = new TldLocationsCache(
					new JspCServletContext(
						new PrintWriter(System.out),
						new URL("file://" + portalDir + "/"))) {

					@Override
					public TldLocation getLocation(String uri)
						throws JasperException {

						TldLocation tldLocation = super.getLocation(uri);

						String name = tldLocation.getName();

						if (name.startsWith("/WEB-INF/tld/")) {
							tldLocation = new TldLocation(
								"file://" + portalDir + name);
						}

						return tldLocation;
					}

				};
			}
			catch (MalformedURLException murle) {
				throw new RuntimeException(murle);
			}
		}
		else {
			try {
				WebXml webXml = new WebXml(context);

				if (webXml.getInputSource() != null) {
					return;
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}

			context.setAttribute(
				Constants.MERGED_WEB_XML, "<web-app version=\"2.4\" />");

			jspConfig = new JspConfig(context);
		}
	}

}