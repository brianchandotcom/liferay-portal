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

import java.util.Iterator;

import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.JspConfig;
import org.apache.jasper.compiler.WebXml;
import org.apache.tomcat.util.scan.Constants;

/**
 * @author Shuyang Zhou
 */
public class JspC extends org.apache.jasper.JspC {

	public static void main(String[] args) {
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

			if (page.contains("/docroot/META-INF/custom_jsps/")) {
				iterator.remove();
			}
		}
	}

	@Override
	protected void initServletContext() {
		super.initServletContext();

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