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

package com.liferay.fragment.internal.upgrade.v2_6_0;

import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryVersionTable;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntryVersion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(FragmentEntryVersionTable.TABLE_SQL_CREATE);

		insertIntoFragmentEntryVersion();

		upgradeFragmentEntryVersionCounter();
	}

	protected void insertIntoFragmentEntryVersion() throws Exception {
		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(17);

			sb.append("insert into FragmentEntryVersion( ");
			sb.append("fragmentEntryVersionId, version, uuid_, ");
			sb.append("fragmentEntryId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, ");
			sb.append("fragmentCollectionId, fragmentEntryKey, name, css, ");
			sb.append("html, js, cacheable, configuration, ");
			sb.append("previewFileEntryId, readOnly, type_, lastPublishDate, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate) ");
			sb.append("select fragmentEntryId as fragmentEntryVersionId, 1 ");
			sb.append("as version, uuid_, fragmentEntryId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, fragmentCollectionId, fragmentEntryKey, ");
			sb.append("name, css, html, js, cacheable, configuration, ");
			sb.append("previewFileEntryId, readOnly, type_, lastPublishDate, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate ");
			sb.append("from FragmentEntry where status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(sb.toString());
		}
	}

	protected void upgradeFragmentEntryVersionCounter() throws Exception {
		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				FragmentEntryVersion.class.getName(),
				"', max(fragmentEntryVersionId) from FragmentEntryVersion"));
	}

}