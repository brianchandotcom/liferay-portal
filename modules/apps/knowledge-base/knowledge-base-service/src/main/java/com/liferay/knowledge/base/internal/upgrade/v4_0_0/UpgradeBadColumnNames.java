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

package com.liferay.knowledge.base.internal.upgrade.v4_0_0;

import com.liferay.knowledge.base.internal.upgrade.v4_0_0.util.KBArticleTable;
import com.liferay.knowledge.base.internal.upgrade.v4_0_0.util.KBFolderTable;
import com.liferay.portal.kernel.upgrade.BaseUpgradeBadColumnNames;

/**
 * @author Tina Tian
 */
public class UpgradeBadColumnNames extends BaseUpgradeBadColumnNames {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBadColumnNames(KBArticleTable.class, "description");
		upgradeBadColumnNames(KBFolderTable.class, "description");
	}

}