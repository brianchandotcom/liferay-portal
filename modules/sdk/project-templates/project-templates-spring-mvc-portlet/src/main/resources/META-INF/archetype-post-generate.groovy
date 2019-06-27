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

import groovy.io.FileType

import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path buildGradlePath = projectPath.resolve("build.gradle")

Files.deleteIfExists buildGradlePath

def buildDir = projectPath.toFile()
def viewsDir = new File(buildDir, "src/main/webapp/WEB-INF/views")
def spring4JavaPkgDir = new File(buildDir, "src/main/java/" + request.properties["package"].replaceAll("[.]", "/") + "/spring4")

if (request.properties["viewType"].equals("jsp")) {
	viewsDir.eachFileMatch FileType.FILES, ~/.*\.html/, {
		File viewFile -> viewFile.delete()
	}
}
else {
	viewsDir.eachFileMatch FileType.FILES, ~/.*\.jspx/, {
		File viewFile -> viewFile.delete()
	}
}

if (request.properties["viewType"].equals("jsp") ||
	request.properties["framework"].equals("portletmvc4spring")) {
	spring4JavaPkgDir.deleteDir()
}