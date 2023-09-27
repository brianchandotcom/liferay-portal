/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.wiki.model.WikiPage;

/**
 * @author Norbert Kocsis
 */
public class WikiPageUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long wikiPageClassNameId = PortalUtil.getClassNameId(
				WikiPage.class.getName());

			runSQL(
				StringBundler.concat(
					"update WikiPage set createDate = (select createDate from ",
					"AssetEntry where AssetEntry.classNameId = ",
					wikiPageClassNameId,
					" and AssetEntry.classPK = WikiPage.resourcePrimKey)"));
		}
	}

}