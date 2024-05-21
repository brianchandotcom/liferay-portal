/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.release;

import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Mariano Álvaro Sáiz
 */
public abstract class BaseSchemaCreationUpgradeStep implements UpgradeStep {

	public BaseSchemaCreationUpgradeStep(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;

		DBResourceUtil.registerMiscellaneousSQLTemplate(sqlTemplate);
	}

	protected String sqlTemplate;

}