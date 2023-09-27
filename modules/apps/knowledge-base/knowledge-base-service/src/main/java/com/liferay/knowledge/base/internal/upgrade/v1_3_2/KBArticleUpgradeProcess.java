/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_3_2;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Adolfo Pérez
 */
public class KBArticleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long kbArticleClassNameId = PortalUtil.getClassNameId(
			"com.liferay.knowledgebase.model.KBArticle");

		runSQL(
			StringBundler.concat(
				"update KBArticle set parentResourceClassNameId = ",
				kbArticleClassNameId, " where parentResourcePrimKey != ",
				KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY));

		StringBundler sb = new StringBundler(6);

		sb.append("update KBArticle set parentResourceClassNameId = ");

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			"com.liferay.knowledgebase.model.KBFolder");

		sb.append(kbFolderClassNameId);

		sb.append(", parentResourcePrimKey = ");
		sb.append(KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		sb.append(" where parentResourcePrimKey = ");
		sb.append(KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY);

		runSQL(sb.toString());
	}

}