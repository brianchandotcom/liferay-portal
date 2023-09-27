/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.dao.orm.PortalCustomSQL;
import com.liferay.util.dao.orm.CustomSQLUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalCustomSQLImpl implements PortalCustomSQL {

	@Override
	public String get(String id) {
		return CustomSQLUtil.get(id);
	}

}