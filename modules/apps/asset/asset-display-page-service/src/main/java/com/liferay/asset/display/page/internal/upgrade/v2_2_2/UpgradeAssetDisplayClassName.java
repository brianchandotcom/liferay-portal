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

package com.liferay.asset.display.page.internal.upgrade.v2_2_2;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeAssetDisplayClassName extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayClassName();
	}

	private void _upgradeAssetDisplayClassName() throws Exception {
		runSQL(
			StringBundler.concat(
				"update AssetDisplayPageEntry set value = '",
				PortalUtil.getClassNameId(FileEntry.class),
				"' where classNameId = ",
				PortalUtil.getClassNameId(DLFileEntry.class)));
	}

}