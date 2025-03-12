/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.manifest.helper.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ReleaseInfo;

import org.apache.tools.ant.Project;

/**
 * @author Istvan Sajtos
 * @author Drew Brokke
 */
public class CPEUtil {

	public static String getName(Project project) {
		String patchVersion = "*";
		String product = "portal";
		String version =
			ReleaseInfo.getVersion() +
				project.getProperty("release.info.version.file.suffix");

		if (ReleaseInfo.isDXP()) {
			String versionDisplayName = ReleaseInfo.getVersionDisplayName();

			int index = versionDisplayName.lastIndexOf(StringPool.PERIOD);

			patchVersion = versionDisplayName.substring(index + 1);

			if (patchVersion.endsWith(" LTS")) {
				patchVersion = patchVersion.substring(
					0, patchVersion.indexOf(" LTS"));
			}

			product = "dxp";

			version = versionDisplayName.substring(0, index);

			version = version.toLowerCase();
		}

		return StringBundler.concat(
			"cpe:2.3:a:liferay:", product, ":", version, ":", patchVersion,
			":*:*:*:*:*:*");
	}

}