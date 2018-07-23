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

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path buildGradlePath = projectPath.resolve("build.gradle")

Files.deleteIfExists buildGradlePath

Properties properties = request.properties

String liferayVersion = properties.get("liferayVersion")

if (!liferayVersion.startsWith("7.1")) {
	String artifactId = properties.get("artifactId")

	List<String> fileNames = [".babelrc", ".npmbundlerrc", "package.json", "src/main/resources/META-INF/resources/${artifactId}.es.js"]

	for (fileName in fileNames) {
		Path resourcePath = Paths.get(fileName)

		Path resourceFullPath = projectPath.resolve(resourcePath)

		Files.deleteIfExists resourceFullPath
	}
}