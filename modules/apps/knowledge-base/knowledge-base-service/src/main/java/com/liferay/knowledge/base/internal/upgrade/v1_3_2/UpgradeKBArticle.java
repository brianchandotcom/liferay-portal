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

package com.liferay.knowledge.base.internal.upgrade.v1_3_2;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Adolfo Pérez
 */
public class UpgradeKBArticle extends UpgradeProcess {

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