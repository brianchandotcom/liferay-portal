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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 * @author Brian Wing Shun Chan
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateAssetClassTypeId();
		updateIGImageClassName();
	}

	protected long getJournalStructureId(String structureId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select id_ from JournalStructure where structureId = ?")) {

			ps.setString(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("id_");
				}

				return 0;
			}
		}
	}

	protected void updateAssetClassTypeId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.journal.model.JournalArticle");

			try (PreparedStatement ps = connection.prepareStatement(
					"select resourcePrimKey, structureId from JournalArticle " +
						"where structureId != ''");
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long resourcePrimKey = rs.getLong("resourcePrimKey");
					String structureId = rs.getString("structureId");

					long journalStructureId = getJournalStructureId(
						structureId);

					StringBundler sb = new StringBundler(6);

					sb.append("update AssetEntry set classTypeId = ");
					sb.append(journalStructureId);
					sb.append(" where classNameId = ");
					sb.append(classNameId);
					sb.append(" and classPK = ");
					sb.append(resourcePrimKey);

					runSQL(sb.toString());
				}
			}
		}
	}

	protected void updateIGImageClassName() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long dlFileEntryClassNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry");
			long igImageClassNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.imagegallery.model.IGImage");

			if (PropsValues.
					DL_FILE_ENTRY_TYPE_IG_IMAGE_AUTO_CREATE_ON_UPGRADE) {

				UpgradeProcessUtil.setCreateIGImageDocumentType(true);

				updateIGImageClassNameWithClassTypeId(
					dlFileEntryClassNameId, igImageClassNameId);
			}
			else {
				updateIGImageClassNameWithoutClassTypeId(
					dlFileEntryClassNameId, igImageClassNameId);
			}
		}
	}

	protected void updateIGImageClassNameWithClassTypeId(
			long dlFileEntryClassNameId, long igImageClassNameId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select fileEntryTypeId, companyId from DLFileEntryType " +
					"where name = ?")) {

			ps.setString(1, DLFileEntryTypeConstants.NAME_IG_IMAGE);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long fileEntryTypeId = rs.getLong("fileEntryTypeId");
					long companyId = rs.getLong("companyId");

					StringBundler sb = new StringBundler(8);

					sb.append("update AssetEntry set classNameId = ");
					sb.append(dlFileEntryClassNameId);
					sb.append(", classTypeId = ");
					sb.append(fileEntryTypeId);
					sb.append(" where classNameId = ");
					sb.append(igImageClassNameId);
					sb.append(" AND companyId = ");
					sb.append(companyId);

					runSQL(sb.toString());
				}
			}
		}
	}

	protected void updateIGImageClassNameWithoutClassTypeId(
			long dlFileEntryClassNameId, long igImageClassNameId)
		throws Exception {

		runSQL(
			"update AssetEntry set classNameId = " + dlFileEntryClassNameId +
				" where classNameId = " + igImageClassNameId);
	}

}