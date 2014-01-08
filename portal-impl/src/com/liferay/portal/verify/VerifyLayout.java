/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Kenneth Chang
 */
public class VerifyLayout extends VerifyProcess {

	protected void deleteOphanedLayouts() throws Exception {
		List<Layout> layoutsWithPrototype = new ArrayList<Layout>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from layout where layoutprototypeuuid != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				layoutsWithPrototype.add(layout);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		for (Layout layout : layoutsWithPrototype) {
			try {
				String uuid = layout.getLayoutPrototypeUuid();

				long companyId = layout.getCompanyId();

				LayoutPrototypeLocalServiceUtil.
					getLayoutPrototypeByUuidAndCompanyId(uuid, companyId);
			}
			catch (NoSuchLayoutPrototypeException nslpe) {
				LayoutLocalServiceUtil.deleteLayout(layout);
			}
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteOphanedLayouts();
		verifyFriendlyURL();
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
					layout.getPlid(), friendlyURL,
					layoutFriendlyURL.getLanguageId());
			}
		}
	}

	protected void verifyUuid() throws Exception {
		verifyUuid("AssetEntry");
		verifyUuid("JournalArticle");

		StringBundler sb = new StringBundler(3);

		sb.append("update Layout set uuid_ = sourcePrototypeLayoutUuid where ");
		sb.append("sourcePrototypeLayoutUuid != '' and ");
		sb.append("uuid_ != sourcePrototypeLayoutUuid");

		runSQL(sb.toString());
	}

	protected void verifyUuid(String tableName) throws Exception {
		StringBundler sb = new StringBundler(11);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set layoutUuid = (select sourcePrototypeLayoutUuid from ");
		sb.append("Layout where Layout.uuid_ = ");
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