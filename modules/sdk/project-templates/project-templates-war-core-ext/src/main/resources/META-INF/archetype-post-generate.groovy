/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path pomPath = projectPath.resolve("pom.xml")

Files.deleteIfExists pomPath

Properties properties = request.properties

String template = properties.get("template")

if (template.contains("-ext")) {
	throw new IllegalArgumentException(
		"EXT project is not supported for Maven")
}