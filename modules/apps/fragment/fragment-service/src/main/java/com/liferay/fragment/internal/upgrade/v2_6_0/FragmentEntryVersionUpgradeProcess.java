/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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