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

package com.liferay.portal.deploy;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DeployUtil {

	public static void copyDependencyXml(
			String fileName, String targetDir, String targetFileName,
			Map<String, String> filterMap, boolean overwrite)
		throws Exception {

		File targetFile = new File(targetDir, targetFileName);

		if (!targetFile.exists()) {
			Set<Path> tempPaths = new HashSet<>();

			File file = new File(getResourcePath(tempPaths, fileName));

			String content = FileUtil.read(file);

			FileUtil.write(
				targetFile,
				StringUtil.replace(
					content, StringPool.AT, StringPool.AT, filterMap));

			for (Path tempPath : tempPaths) {
				deletePath(tempPath);
			}
		}
	}

	public static void deletePath(Path tempPath) throws IOException {
		Files.walkFileTree(
			tempPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static String getResourcePath(
			Set<Path> tempDirPaths, String resource)
		throws Exception {

		InputStream inputStream = DeployUtil.class.getResourceAsStream(
			"dependencies/" + resource);

		if (inputStream == null) {
			return null;
		}

		Path tempDirPath = Files.createTempDirectory(
			Paths.get(SystemProperties.get(SystemProperties.TMP_DIR)), null);

		tempDirPaths.add(tempDirPath);

		File file = new File(
			tempDirPath + "/liferay/com/liferay/portal/deploy/dependencies/" +
				resource);

		File parentFile = file.getParentFile();

		if (parentFile != null) {
			FileUtil.mkdirs(parentFile);
		}

		StreamUtil.transfer(inputStream, new FileOutputStream(file));

		return FileUtil.getAbsolutePath(file);
	}

}