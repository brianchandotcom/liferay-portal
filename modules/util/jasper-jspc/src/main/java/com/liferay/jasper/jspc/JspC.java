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

import java.lang.reflect.Field;

import java.util.Iterator;
import java.util.List;

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

			if (page.contains("/docroot/META-INF/custom_jsps/") ||
				page.contains("/META-INF/resources/custom_jsps/")) {

				iterator.remove();
			}
		}
	}

	private final List<String> _pages;

}