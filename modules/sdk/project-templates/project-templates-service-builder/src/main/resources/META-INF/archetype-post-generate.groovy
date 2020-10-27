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

Path apiPath = projectPath.resolve(request.artifactId + "-api")
Path buildGradlePath = projectPath.resolve("build.gradle")
Path servicePath = projectPath.resolve(request.artifactId + "-service")

Path apiBuildGradlePath = apiPath.resolve("build.gradle")
Path serviceBuildGradlePath = servicePath.resolve("build.gradle")
Path serviceBuildSettingsPath = projectPath.resolve("settings.gradle")

Files.deleteIfExists apiBuildGradlePath
Files.deleteIfExists buildGradlePath
Files.deleteIfExists serviceBuildGradlePath
Files.deleteIfExists serviceBuildSettingsPath

Properties properties = request.properties

String addOnOptions = properties.get("addOnOptions")

String liferayVersion = properties.get("liferayVersion")

if (addOnOptions.equals("true") && (liferayVersion.startsWith("7.0") || (liferayVersion.startsWith("7.1")))) {
	throw new IllegalArgumentException(
		"Add On Options is not supported in 7.0 or 7.1")
}

Path uadPath = projectPath.resolve(request.artifactId + "-uad")
Path uadBuildGradlePath = uadPath.resolve("build.gradle")
Path uadBndPath = uadPath.resolve("bnd.bnd")
Path uadPomPath = uadPath.resolve("pom.xml")

if (addOnOptions.equals("false")) {
	Files.deleteIfExists uadBuildGradlePath
	Files.deleteIfExists uadBndPath
	Files.deleteIfExists uadPomPath
	Files.deleteIfExists uadPath
}
else {
	Files.deleteIfExists uadBuildGradlePath
}