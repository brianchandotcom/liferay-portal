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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Alexander Chow
 */
public class UpgradeWorkflow extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("update WorkflowDefinitionLink set classNameId = ");

		long folderClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.documentlibrary.model.DLFolder");

		sb.append(folderClassNameId);

		sb.append(", typePK = ");
		sb.append(DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);
		sb.append(" where classNameId = ");

		long fileEntryClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.documentlibrary.model.DLFileEntry");

		sb.append(fileEntryClassNameId);

		runSQL(sb.toString());
	}

}