/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Alan Huang
 */
public class BNDWebServiceTrackingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException, ParseException {

		if (!absolutePath.endsWith("/bnd.bnd")) {
			return content;
		}

		String webContextPath = BNDSourceUtil.getDefinitionValue(
			content, "Web-ContextPath");

		if (webContextPath != null) {
			return content;
		}

		String webServiceTracking = BNDSourceUtil.getDefinitionValue(
			content, "Web-ServiceTracking");

		if ((webServiceTracking != null) &&
			webServiceTracking.equals("false")) {

			return content;
		}

		int index = absolutePath.lastIndexOf(CharPool.SLASH);

		File lfrBuildPortalFile = new File(
			absolutePath.substring(0, index + 1) + ".lfrbuild-portal");

		if (!lfrBuildPortalFile.exists()) {
			return content;
		}

		File buildGradleFile = new File(
			absolutePath.substring(0, index + 1) + "build.gradle");

		if (!buildGradleFile.exists()) {
			return content;
		}

		String buildGradleContent = FileUtil.read(buildGradleFile);

		if (buildGradleContent.contains(
				"group: \"jakarta.servlet.jsp\", name: \"jakarta.servlet." +
					"jsp-api\"")) {

			return content;
		}

		List<String> jspFileNames = SourceFormatterUtil.scanForFileNames(
			absolutePath.substring(0, index + 1), new String[0],
			new String[] {
				"**/resources/META-INF/resources/**/*.{jsp,jspf,jspx}"
			},
			new SourceFormatterExcludes(), false);

		if (!jspFileNames.isEmpty()) {
			return content;
		}

		List<String> javaFileNames = SourceFormatterUtil.scanForFileNames(
			absolutePath.substring(0, index + 1), new String[0],
			new String[] {"**/src/main/java/com/liferay/**/*.java"},
			new SourceFormatterExcludes(), false);

		for (String javaFileName : javaFileNames) {
			List<String> annotationsBlocks = SourceUtil.getAnnotationsBlocks(
				FileUtil.read(new File(javaFileName)));

			for (String annotationsBlock : annotationsBlocks) {
				List<String> annotations = SourceUtil.splitAnnotations(
					annotationsBlock, SourceUtil.getIndent(annotationsBlock));

				for (String annotation : annotations) {
					if (!annotation.startsWith("@Component(")) {
						continue;
					}

					annotation = annotation.trim();

					Map<String, String> annotationMemberValuePair =
						SourceUtil.getAnnotationMemberValuePair(annotation);

					String service = annotationMemberValuePair.get("service");

					if (!service.contains("Portlet.class")) {
						continue;
					}

					String property = annotationMemberValuePair.get("property");

					if (property.contains(
							"jakarta.portlet.init-param.view-template")) {

						continue;
					}

					addMessage(
						fileName,
						"Missing \"Web-ServiceTracking: false\" in bnd.bnd, " +
							"see LPD-80556");

					return content;
				}
			}
		}

		return content;
	}

}