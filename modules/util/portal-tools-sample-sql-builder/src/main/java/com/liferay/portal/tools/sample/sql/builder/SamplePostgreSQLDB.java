/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Dante Wang
 */
public class SamplePostgreSQLDB extends PostgreSQLDB {

	public SamplePostgreSQLDB(int majorVersion, int minorVersion) {
		super(majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		if (template.contains("$###,##0.00")) {
			template = StringUtil.replace(template, "$###,##0.00", "$0.00");
		}

		return StringUtil.replace(super.buildSQL(template), "\\n", "\n");
	}

}