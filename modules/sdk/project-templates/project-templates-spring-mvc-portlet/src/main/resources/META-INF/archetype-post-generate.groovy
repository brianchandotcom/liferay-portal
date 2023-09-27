/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

def webappDir = new File(buildDir, "src/main/webapp")

def viewsDir = new File(webappDir, "WEB-INF/views")

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

def spring4JavaPkgDir = new File(buildDir, "src/main/java/" + request.properties["package"].replaceAll("[.]", "/") + "/spring4")

if (request.properties["viewType"].equals("jsp") ||
	request.properties["framework"].equals("portletmvc4spring")) {
	spring4JavaPkgDir.deleteDir()
}

String liferayVersion = request.properties.get("liferayVersion")

char minorVersion = liferayVersion.charAt(2)

File liferayDisplayXML = new File(webappDir, "WEB-INF/liferay-display.xml");

File liferayPortletXML = new File(webappDir, "WEB-INF/liferay-portlet.xml");

def newLiferayDisplayContent = liferayDisplayXML.text.replace("7.0", "7." + minorVersion).replace("7_0", "7_" + minorVersion)

liferayDisplayXML.text = newLiferayDisplayContent

def newLiferayPortletContent = liferayPortletXML.text.replace("7.0", "7." + minorVersion).replace("7_0", "7_" + minorVersion)

liferayPortletXML.text = newLiferayPortletContent