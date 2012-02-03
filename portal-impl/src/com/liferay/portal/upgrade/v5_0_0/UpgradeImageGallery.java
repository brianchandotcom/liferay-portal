/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v5_0_0.util.IGFolderNameColumnImpl;
import com.liferay.portal.upgrade.v5_0_0.util.IGFolderTable;
import com.liferay.portal.upgrade.v5_0_0.util.IGImageNameColumnImpl;
import com.liferay.portal.upgrade.v5_0_0.util.IGImageTable;

/**
 * @author Alexander Chow
 */
public class UpgradeImageGallery extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// IGFolder

		UpgradeColumn groupIdColumn = new TempUpgradeColumnImpl("groupId");

		UpgradeColumn parentFolderIdColumn = new TempUpgradeColumnImpl(
			"parentFolderId");

		IGFolderNameColumnImpl igFolderNameColumn = new IGFolderNameColumnImpl(
			groupIdColumn, parentFolderIdColumn);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			IGFolderTable.TABLE_NAME, IGFolderTable.TABLE_COLUMNS,
			groupIdColumn, parentFolderIdColumn, igFolderNameColumn);

		upgradeTable.updateTable();

		// IGImage

		UpgradeColumn imageIdColumn = new TempUpgradeColumnImpl("imageId");

		IGImageNameColumnImpl imageNameColumn = new IGImageNameColumnImpl(
			imageIdColumn);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			IGImageTable.TABLE_NAME, IGImageTable.TABLE_COLUMNS, imageIdColumn,
			imageNameColumn);

		upgradeTable.updateTable();
	}

}