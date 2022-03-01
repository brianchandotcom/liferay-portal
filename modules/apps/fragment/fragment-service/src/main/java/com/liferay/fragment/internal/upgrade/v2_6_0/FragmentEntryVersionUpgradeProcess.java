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

import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryVersionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_insertIntoFragmentEntryVersion();

		_upgradeFragmentEntryVersionCounter();
	}

	private void _insertIntoFragmentEntryVersion() throws Exception {
		try (Statement s = connection.createStatement()) {
			s.execute(
				StringBundler.concat(
					"insert into FragmentEntryVersion(",
					"fragmentEntryVersionId, version, uuid_, fragmentEntryId, ",
					"groupId, companyId, userId, userName, createDate, ",
					"modifiedDate, fragmentCollectionId, fragmentEntryKey, ",
					"name, css, html, js, cacheable, configuration, ",
					"previewFileEntryId, readOnly, type_, lastPublishDate, ",
					"status, statusByUserId, statusByUserName, statusDate) ",
					"select fragmentEntryId as fragmentEntryVersionId, 1 as ",
					"version, uuid_, fragmentEntryId, groupId, companyId, ",
					"userId, userName, createDate, modifiedDate, ",
					"fragmentCollectionId, fragmentEntryKey, name, css, html, ",
					"js, cacheable, configuration, previewFileEntryId, ",
					"readOnly, type_, lastPublishDate, status, ",
					"statusByUserId, statusByUserName, statusDate from ",
					"FragmentEntry where status = ",
					WorkflowConstants.STATUS_APPROVED));
		}
	}

	private void _upgradeFragmentEntryVersionCounter() throws Exception {
		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				FragmentEntryVersion.class.getName(),
				"', max(fragmentEntryVersionId) from FragmentEntryVersion"));
	}

}