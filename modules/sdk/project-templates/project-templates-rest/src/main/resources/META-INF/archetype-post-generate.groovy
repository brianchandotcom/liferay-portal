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

if (liferayVersion.startsWith("7.1") || liferayVersion.startsWith("7.2") || liferayVersion.startsWith("7.3")) {
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