/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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