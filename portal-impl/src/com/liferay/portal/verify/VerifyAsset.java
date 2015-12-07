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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Douglas Wong
 */
public class VerifyAsset extends VerifyProcess {

	protected void deleteOrphanedAssetEntries() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryConstants.getClassName());

			StringBundler sb = new StringBundler(5);

			sb.append("select classPK, entryId from AssetEntry where ");
			sb.append("classNameId = ");
			sb.append(classNameId);
			sb.append(" and classPK not in (select fileVersionId from ");
			sb.append("DLFileVersion)");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long classPK = rs.getLong("classPK");
				long entryId = rs.getLong("entryId");

				DLFileEntry dlFileEntry =
					DLFileEntryLocalServiceUtil.fetchDLFileEntry(classPK);

				if (dlFileEntry == null) {
					AssetEntryLocalServiceUtil.deleteAssetEntry(entryId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteOrphanedAssetEntries();
		rebuildTree();
	}

	protected void rebuildTree() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select distinct groupId from AssetCategory where " +
					"(leftCategoryId is null) or (rightCategoryId is null)");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				AssetCategoryLocalServiceUtil.rebuildTree(groupId, true);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

}