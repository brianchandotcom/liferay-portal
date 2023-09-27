/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_6_0;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_upgradeFragmentEntryCounter();
		_upgradeFragmentEntryHeadIdAndHeadStatusApproved();
		_upgradeFragmentEntryHeadIdAndHeadStatusDraft();
	}

	private void _upgradeFragmentEntryCounter() throws Exception {
		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				FragmentEntry.class.getName(),
				"', max(fragmentEntryId) from FragmentEntry"));
	}

	private void _upgradeFragmentEntryHeadIdAndHeadStatusApproved()
		throws Exception {

		try (Statement s = connection.createStatement()) {
			s.execute(
				SQLTransformer.transform(
					StringBundler.concat(
						"update FragmentEntry set headId = -1 * ",
						"fragmentEntryId, head = [$TRUE$] where status = ",
						WorkflowConstants.STATUS_APPROVED)));
		}
	}

	private void _upgradeFragmentEntryHeadIdAndHeadStatusDraft()
		throws Exception {

		try (Statement s = connection.createStatement()) {
			s.execute(
				SQLTransformer.transform(
					StringBundler.concat(
						"update FragmentEntry set headId = fragmentEntryId, ",
						"head = [$FALSE$] where status != ",
						WorkflowConstants.STATUS_APPROVED)));
		}
	}

	private void _upgradeSchema() throws Exception {
		alterTableAddColumn("FragmentEntry", "headId", "LONG");
		alterTableAddColumn("FragmentEntry", "head", "BOOLEAN");
	}

}