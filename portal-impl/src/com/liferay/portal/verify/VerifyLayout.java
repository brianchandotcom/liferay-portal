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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Kenneth Chang
 */
public class VerifyLayout extends VerifyProcess {

	protected void deleteOrphanedLayouts() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select plid from Layout where layoutPrototypeUuid != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutPrototype layoutPrototype =
					LayoutPrototypeLocalServiceUtil.
						fetchLayoutPrototypeByUuidAndCompanyId(
							layout.getLayoutPrototypeUuid(),
							layout.getCompanyId());

				if (layoutPrototype == null) {
					LayoutLocalServiceUtil.deleteLayout(layout);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteOrphanedLayouts();
		verifyFriendlyURL();
		verifyLayoutPrototypeLinkEnabled();
		verifyUuid();
	}

	protected void verifyFriendlyURL() throws Exception {
		List<Layout> layouts =
			LayoutLocalServiceUtil.getNullFriendlyURLLayouts();

		for (Layout layout : layouts) {
			List<LayoutFriendlyURL> layoutFriendlyURLs =
				LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
					layout.getPlid());

			for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
				String friendlyURL = StringPool.SLASH + layout.getLayoutId();

				LayoutLocalServiceUtil.updateFriendlyURL(
					layout.getUserId(), layout.getPlid(), friendlyURL,
					layoutFriendlyURL.getLanguageId());
			}
		}
	}

	protected void verifyLayoutPrototypeLinkEnabled() throws Exception {
		runSQL(
			"update Layout set layoutPrototypeLinkEnabled = [$FALSE$] where " +
				"type_ = 'link_to_layout' and layoutPrototypeLinkEnabled = " +
				"[$TRUE$]");
	}

	protected void verifyUuid() throws Exception {
		verifyUuid("AssetEntry");
		verifyUuid("JournalArticle");

		runSQL(
			"update Layout set uuid_ = sourcePrototypeLayoutUuid where " +
				"sourcePrototypeLayoutUuid != '' and uuid_ != " +
					"sourcePrototypeLayoutUuid");
	}

	protected void verifyUuid(String tableName) throws Exception {
		StringBundler sb = new StringBundler(12);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set layoutUuid = (select distinct ");
		sb.append("sourcePrototypeLayoutUuid from Layout where ");
		sb.append("Layout.uuid_ = ");
		sb.append(tableName);
		sb.append(".layoutUuid) where exists (select 1 from Layout where ");
		sb.append("Layout.uuid_ = ");
		sb.append(tableName);
		sb.append(".layoutUuid and Layout.uuid_ != ");
		sb.append("Layout.sourcePrototypeLayoutUuid and ");
		sb.append("Layout.sourcePrototypeLayoutUuid != '')");

		runSQL(sb.toString());
	}

}