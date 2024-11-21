/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.resource.v1_0.test.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Rafael Praxedes
 */
public class ScimTestUtil {

	public static void saveSCIMClientId(
			String className, long classPK, long companyId)
		throws Exception {

		saveSCIMClientId(
			className, classPK, companyId, RandomTestUtil.randomString());
	}

	public static void saveSCIMClientId(
			String className, long classPK, long companyId,
			String scimClientIdValue)
		throws Exception {

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.getTable(
			companyId, ClassNameLocalServiceUtil.getClassNameId(className),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(
			expandoTable.getTableId(), "scimClientId");

		ExpandoValueLocalServiceUtil.addValue(
			companyId, className, ExpandoTableConstants.DEFAULT_TABLE_NAME,
			expandoColumn.getName(), classPK, scimClientIdValue);
	}

}