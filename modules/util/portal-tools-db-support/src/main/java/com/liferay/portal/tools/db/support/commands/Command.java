/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.support.commands;

import com.liferay.portal.tools.db.support.DBSupportArgs;

/**
 * @author Andrea Di Giorgi
 */
public interface Command {

	public void execute(DBSupportArgs dbSupportArgs) throws Exception;

}