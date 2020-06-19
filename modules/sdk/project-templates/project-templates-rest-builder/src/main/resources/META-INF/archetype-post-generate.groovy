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
Path clientPath = projectPath.resolve(request.artifactId + "-client")
Path implementationPath = projectPath.resolve(request.artifactId + "-impl")
Path testPath = projectPath.resolve(request.artifactId + "-test")

Path apiBuildGradlePath = apiPath.resolve("build.gradle")
Path buildSettingsPath = projectPath.resolve("settings.gradle")
Path clientBuildGradlePath = clientPath.resolve("build.gradle")
Path implementationBuildGradlePath = implementationPath.resolve("build.gradle")
Path testBuildGradlePath = testPath.resolve("build.gradle")

Files.deleteIfExists apiBuildGradlePath
Files.deleteIfExists buildGradlePath
Files.deleteIfExists buildSettingsPath
Files.deleteIfExists clientBuildGradlePath
Files.deleteIfExists implementationBuildGradlePath
Files.deleteIfExists testBuildGradlePath