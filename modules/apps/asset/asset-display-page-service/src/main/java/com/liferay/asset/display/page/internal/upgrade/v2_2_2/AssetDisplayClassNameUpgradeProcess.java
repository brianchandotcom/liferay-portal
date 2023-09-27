/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class AssetDisplayClassNameUpgradeProcess extends UpgradeProcess {

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