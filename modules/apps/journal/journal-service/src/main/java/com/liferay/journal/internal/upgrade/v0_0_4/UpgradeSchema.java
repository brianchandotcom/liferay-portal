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

package com.liferay.journal.internal.upgrade.v0_0_4;

import com.liferay.journal.internal.upgrade.v0_0_4.util.JournalArticleTable;
import com.liferay.journal.internal.upgrade.v0_0_4.util.JournalFeedTable;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eduardo García
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false, false);

		upgrade(new UpgradeMVCCVersion());

		alter(
			JournalArticleTable.class,
			new AlterColumnName(
				"structureId", "DDMStructureKey VARCHAR(75) null"),
			new AlterColumnName(
				"templateId", "DDMTemplateKey VARCHAR(75) null"),
			new AlterColumnType("description", "TEXT null"));

		alter(
			JournalFeedTable.class,
			new AlterColumnName("structureId", "DDMStructureKey TEXT null"),
			new AlterColumnName("templateId", "DDMTemplateKey TEXT null"),
			new AlterColumnName(
				"rendererTemplateId", "DDMRendererTemplateKey TEXT null"),
			new AlterColumnType("targetPortletId", "VARCHAR(200) null"));
	}

}