/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v0_0_7;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jorge Díaz
 */
public class JournalArticleTreePathUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update JournalArticle set treePath = '/' where folderId = 0 and " +
				"treePath = '/0/'");
	}

}