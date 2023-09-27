/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class TaglibUtil {

	public static List<String> getExtendedTagFileNames(
		JavaClass javaClass, String absolutePath, String utilTaglibSrcDirName) {

		List<String> extendedTagFilesNames = new ArrayList<>();

		for (String extendedClassName : javaClass.getExtendedClassNames(true)) {
			StringBundler sb = new StringBundler(5);

			if (extendedClassName.startsWith("com.liferay.taglib")) {
				sb.append(utilTaglibSrcDirName);
			}
			else if (extendedClassName.startsWith(javaClass.getPackageName())) {
				int pos = absolutePath.lastIndexOf(CharPool.SLASH);

				sb.append(absolutePath.substring(0, pos + 1));

				extendedClassName = StringUtil.removeSubstring(
					extendedClassName,
					javaClass.getPackageName() + StringPool.PERIOD);
			}
			else if (extendedClassName.startsWith(
						"com.liferay.frontend.taglib.soy.")) {

				int pos = absolutePath.indexOf("/modules/apps/");

				if (pos == -1) {
					continue;
				}

				sb.append(absolutePath.substring(0, pos));
				sb.append("/modules/apps/frontend-taglib/frontend-taglib-soy");
				sb.append("/src/main/java/");
			}
			else {
				continue;
			}

			sb.append(
				StringUtil.replace(
					extendedClassName, CharPool.PERIOD, CharPool.SLASH));
			sb.append(".java");

			extendedTagFilesNames.add(sb.toString());
		}

		return extendedTagFilesNames;
	}

	public static List<String> getTLDFileNames(
			String baseDirName, List<String> allFileNames,
			SourceFormatterExcludes sourceFormatterExcludes,
			boolean portalSource, int maxDirLevel)
		throws IOException {

		List<String> tldFileNames = SourceFormatterUtil.filterFileNames(
			allFileNames,
			new String[] {
				"**/dependencies/**", "**/util-taglib/**", "**/portal-web/**"
			},
			new String[] {"**/*.tld"}, sourceFormatterExcludes, true);

		if (!portalSource) {
			return tldFileNames;
		}

		String[] tldDirLocations = {
			"modules/apps/frontend-taglib/", "portal-web/docroot/WEB-INF/tld/",
			"util-taglib/src/META-INF/"
		};

		for (String tldDirLocation : tldDirLocations) {
			for (int i = 0; i < (maxDirLevel - 1); i++) {
				File file = new File(baseDirName + tldDirLocation);

				if (file.exists()) {
					tldFileNames.addAll(
						SourceFormatterUtil.scanForFileNames(
							baseDirName + tldDirLocation, new String[0],
							new String[] {"**/*.tld"}, sourceFormatterExcludes,
							true));

					break;
				}

				tldDirLocation = "../" + tldDirLocation;
			}
		}

		return tldFileNames;
	}

	public static String getUtilTaglibSrcDirName(
		String baseDirName, int maxDirLevel) {

		File utilTaglibDir = SourceFormatterUtil.getFile(
			baseDirName, "util-taglib/src", maxDirLevel);

		if (utilTaglibDir == null) {
			return StringPool.BLANK;
		}

		return SourceUtil.getAbsolutePath(utilTaglibDir) + StringPool.SLASH;
	}

}