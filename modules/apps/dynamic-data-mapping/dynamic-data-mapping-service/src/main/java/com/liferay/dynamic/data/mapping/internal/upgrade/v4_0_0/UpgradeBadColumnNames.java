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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0;

import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMContentTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMDataProviderInstanceTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMStructureLayoutTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMStructureTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.util.DDMTemplateVersionTable;
import com.liferay.portal.kernel.upgrade.BaseUpgradeBadColumnNames;

/**
 * @author Tina Tian
 */
public class UpgradeBadColumnNames extends BaseUpgradeBadColumnNames {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBadColumnNames(DDMContentTable.class, "description");
		upgradeBadColumnNames(
			DDMDataProviderInstanceTable.class, "description", "definition");
		upgradeBadColumnNames(DDMFormInstanceTable.class, "description");
		upgradeBadColumnNames(DDMFormInstanceVersionTable.class, "description");
		upgradeBadColumnNames(
			DDMStructureTable.class, "description", "definition");
		upgradeBadColumnNames(DDMStructureLayoutTable.class, "definition");
		upgradeBadColumnNames(
			DDMStructureVersionTable.class, "description", "definition");
		upgradeBadColumnNames(DDMTemplateTable.class, "description");
		upgradeBadColumnNames(DDMTemplateVersionTable.class, "description");
	}

}