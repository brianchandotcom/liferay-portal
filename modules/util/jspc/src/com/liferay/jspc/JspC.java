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

package com.liferay.jspc;

import java.io.File;

import java.util.Iterator;

import org.apache.jasper.JasperException;

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
	public void scanFiles(File base) throws JasperException {
		super.scanFiles(base);

		Iterator<String> iterator = pages.iterator();

		while (iterator.hasNext()) {
			String page = iterator.next();

			if (page.contains("/docroot/META-INF/custom_jsps/")) {
				iterator.remove();
			}
		}
	}

}