/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.jspc.common;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Minhchau Dang
 */
public class BatchTimestampFixer {

	public static void main(String[] args) {
		if (args.length == 1) {
			new BatchTimestampFixer(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public BatchTimestampFixer(String classDir) {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(classDir);
		ds.setIncludes(new String[] {"**\\*.java"});

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		for (String fileName : fileNames) {
			File javaFile = new File(classDir, fileName);
			
			String fileNameWithoutExtension =
				fileName.substring(0, fileName.length() - 5);
			
			String classFileName =
				fileNameWithoutExtension.concat(".class");
		
			File classFile = new File(classDir, classFileName);
			
			long lastModified = javaFile.lastModified();
			
			classFile.setLastModified(lastModified);
		}
	}
}
