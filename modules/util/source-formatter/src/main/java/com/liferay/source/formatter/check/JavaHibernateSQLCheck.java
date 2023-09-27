/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class JavaHibernateSQLCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (content.contains("= session.createSQLQuery(") &&
			content.contains("com.liferay.portal.kernel.dao.orm.Session")) {

			content = StringUtil.replace(
				content, "= session.createSQLQuery(",
				"= session.createSynchronizedSQLQuery(");
		}

		return content;
	}

}