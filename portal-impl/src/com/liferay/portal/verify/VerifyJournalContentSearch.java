/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Shinn Lok
 */
public class VerifyJournalContentSearch extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<JournalContentSearch> journalContentSearches =
			JournalContentSearchLocalServiceUtil.getArticleContentSearches();

		Set<String> portletIds = new HashSet<String>();

		for (JournalContentSearch journalContentSearch :
				journalContentSearches) {

			portletIds.add(journalContentSearch.getPortletId());
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			for (String portletId : portletIds) {
				journalContentSearches =
					JournalContentSearchLocalServiceUtil.
						getArticleContentSearchesByPortletId(portletId);

				if (journalContentSearches.size() <= 1) {
					continue;
				}

				ps = con.prepareStatement(
					"select preferences from PortletPreferences where " +
						"portletId = ?");

				ps.setString(1, portletId);

				rs = ps.executeQuery();

				while (rs.next()) {
					String xml = rs.getString("preferences");

					PortletPreferences portletPreferences =
						PortletPreferencesFactoryUtil.fromDefaultXML(xml);

					String articleId = portletPreferences.getValue(
						"articleId", null);

					JournalContentSearch journalContentSearch =
						journalContentSearches.get(1);

					JournalContentSearchLocalServiceUtil.updateContentSearch(
						journalContentSearch.getGroupId(),
						journalContentSearch.isPrivateLayout(),
						journalContentSearch.getLayoutId(),
						journalContentSearch.getPortletId(), articleId, true);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

}