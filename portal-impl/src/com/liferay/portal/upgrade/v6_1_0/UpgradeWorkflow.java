/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
		long fileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);
		long folderClassNameId = PortalUtil.getClassNameId(DLFolder.class);

		runSQL(
			"update WorkflowDefinitionLink set classNameId = " +
				folderClassNameId + ", typePK = " +
					DLFileEntryTypeConstants.ALL_FILE_ENTRY_TYPES_ID +
						" where classNameId = " + fileEntryClassNameId);
	}

}