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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Peter Shin
 */
public class CDNCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

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