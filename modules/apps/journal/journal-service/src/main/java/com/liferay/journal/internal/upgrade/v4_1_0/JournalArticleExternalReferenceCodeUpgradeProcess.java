/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v4_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Javier de Arcos
 */
public class JournalArticleExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("JournalArticle", "externalReferenceCode")) {
			alterTableAddColumn(
				"JournalArticle", "externalReferenceCode", "VARCHAR(75)");

			runSQL(
				"update JournalArticle set externalReferenceCode = articleId " +
					"where externalReferenceCode is null or " +
						"externalReferenceCode = ''");
		}
	}

}