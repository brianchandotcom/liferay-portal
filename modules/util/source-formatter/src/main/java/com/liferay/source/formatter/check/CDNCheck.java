/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

/**
 * @author Peter Shin
 */
public class CDNCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String rootDirName = SourceUtil.getRootDirName(absolutePath);

		if (rootDirName.equals(StringPool.BLANK) ||
			absolutePath.matches(rootDirName + "/build\\..+\\.properties")) {

			return content;
		}

		return _fixCDNURL(content);
	}

	private String _fixCDNURL(String content) {
		return StringUtil.replace(
			content,
			new String[] {
				"cdn.lfrs.sl/releases.liferay.com",
				"cdn.lfrs.sl/repository.liferay.com",
				"repository.liferay.com/nexus/content/repositories/",
				"repository.liferay.com/nexus/service/local/repo_groups" +
					"/private/content/"
			},
			new String[] {
				"releases-cdn.liferay.com", "repository-cdn.liferay.com",
				"repository-cdn.liferay.com/nexus/content/repositories/",
				"repository-cdn.liferay.com/nexus/service/local/repo_groups" +
					"/private/content/"
			});
	}

}