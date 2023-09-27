/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path buildGradlePath = projectPath.resolve("build.gradle")

Files.deleteIfExists buildGradlePath

Properties properties = request.properties

String liferayVersion = properties.get("liferayVersion")

if (!liferayVersion.startsWith("7.0")) {
	Path configPath = projectPath.resolve("src/main/resources/configuration")

	String cxfConfig =
		"com.liferay.portal.remote.cxf.common.configuration." +
			"CXFEndpointPublisherConfiguration-cxf.properties";

	Path cxfConfigPath = configPath.resolve(cxfConfig)

	Files.deleteIfExists cxfConfigPath

	String restExtenderConfig =
		"com.liferay.portal.remote.rest.extender.configuration." +
			"RestExtenderConfiguration-rest.properties";

	Path restExtenderConfigPath = configPath.resolve(restExtenderConfig)

	Files.deleteIfExists restExtenderConfigPath

	Files.deleteIfExists configPath
}