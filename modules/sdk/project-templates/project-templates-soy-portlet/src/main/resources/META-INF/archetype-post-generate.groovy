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

import java.util.List

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path buildGradlePath = projectPath.resolve("build.gradle")

Files.deleteIfExists buildGradlePath

String className = request.properties.get("className")
String liferayVersion = request.properties.get("liferayVersion")
String packageString = request.properties.get("package")

String[] packageList = packageString.split("\\.")
List<String> pathList = ["src", "main", "java"]

pathList.addAll packageList
pathList.add "portlet"

if (liferayVersion.startsWith("7.1")) {
	pathList.add "${className}Portlet.java"
}
else {
	pathList.add "${className}SoyPortletRegister.java"
}

Path resourcePath = Paths.get("", pathList as String[])

Path resourceFullPath = projectPath.resolve(resourcePath)

Files.deleteIfExists resourceFullPath