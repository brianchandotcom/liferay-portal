/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * @author Alexander Chow
 */
public class UpgradeWorkflow extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("update WorkflowDefinitionLink set classNameId = ");

		long folderClassNameId = PortalUtil.getClassNameId(DLFolder.class);

		sb.append(folderClassNameId);

		sb.append(", typePK = ");
		sb.append(DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);
		sb.append(" where classNameId = ");

		long fileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		sb.append(fileEntryClassNameId);

		runSQL(sb.toString());
	}

}