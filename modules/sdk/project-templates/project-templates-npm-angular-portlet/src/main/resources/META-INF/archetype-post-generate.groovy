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

import java.nio.file.*

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path buildGradlePath = projectPath.resolve('build.gradle')

Files.deleteIfExists(buildGradlePath)

String liferayVersion = request.properties.get('liferayVersion')

if (liferayVersion != '7.1') {
	String className = properties.get('className')

	Path resourcePath = Paths.get('src', 'main', 'java', 'constants', className + 'WebKeys.java')

	Path resourceFullPath = projectPath.resolve(resourcePath)

	Files.deleteIfExists(resourceFullPath)
}