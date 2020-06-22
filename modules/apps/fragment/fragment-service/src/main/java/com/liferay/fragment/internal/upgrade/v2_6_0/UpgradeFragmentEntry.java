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

import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryTable;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		upgradeFragmentEntryCounter();
		upgradeFragmentEntryHeadIdAndHeadStatusApproved();
		upgradeFragmentEntryHeadIdAndHeadStatusDraft();
	}

	protected void upgradeFragmentEntryCounter() throws Exception {
		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				FragmentEntry.class.getName(),
				"', max(fragmentEntryId) from FragmentEntry"));
	}

	protected void upgradeFragmentEntryHeadIdAndHeadStatusApproved()
		throws Exception {

		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(3);

			sb.append("update FragmentEntry set headId = -1 * fragmentEntryId");
			sb.append(", head = [$TRUE$] where status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(SQLTransformer.transform(sb.toString()));
		}
	}

	protected void upgradeFragmentEntryHeadIdAndHeadStatusDraft()
		throws Exception {

		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(3);

			sb.append("update FragmentEntry set headId = fragmentEntryId, ");
			sb.append("head = [$FALSE$] where status != ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(SQLTransformer.transform(sb.toString()));
		}
	}

	protected void upgradeSchema() throws Exception {
		alter(
			FragmentEntryTable.class, new AlterTableAddColumn("headId", "LONG"),
			new AlterTableAddColumn("head", "BOOLEAN"));
	}

}