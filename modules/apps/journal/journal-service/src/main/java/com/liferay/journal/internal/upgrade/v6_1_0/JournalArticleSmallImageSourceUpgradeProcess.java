/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_0;

import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleSmallImageSourceUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update JournalArticle set smallImageSource = " +
				JournalArticleConstants.SMALL_IMAGE_SOURCE_NONE +
					" where smallImage = [$FALSE$]");
		runSQL(
			"update JournalArticle set smallImageSource = " +
				JournalArticleConstants.SMALL_IMAGE_SOURCE_USER_COMPUTER +
					" where smallImage = [$TRUE$] and smallImageId > 0");

		runSQL(
			StringBundler.concat(
				"update JournalArticle set smallImageSource = ",
				JournalArticleConstants.SMALL_IMAGE_SOURCE_USER_COMPUTER,
				" where smallImage = [$TRUE$] and (smallImageURL is null or ",
				"smallImageURL = '')"));
	}

}