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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public class UpgradeAssetEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(_CLASS_NAMES.length * 2);

		for (String className : _CLASS_NAMES) {
			long classNameId = _getClassNameId(
				_CLASS_NAME_MBDISCUSSION + StringPool.UNDERLINE + className);

			if (classNameId != 0) {
				sb.append(classNameId);
				sb.append(StringPool.COMMA);
			}
		}

		if (sb.length() == 0) {
			return;
		}

		sb.setIndex(sb.index() - 1);

		runSQL(
			"delete from AssetEntry where classNameId in (" + sb.toString() +
				")");
	}

	private long _getClassNameId(String className) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			ps.setString(1, className);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("classNameId");
				}

				return 0;
			}
		}
	}

	private static final String _CLASS_NAME_MBDISCUSSION =
		"com.liferay.message.boards.model.MBDiscussion";

	private static final String[] _CLASS_NAMES = {
		"com.liferay.blogs.model.BlogsEntry",
		"com.liferay.calendar.model.CalendarBooking",
		"com.liferay.portal.kernel.model.Layout",
		"com.liferay.document.library.kernel.model.DLFileEntry",
		"com.liferay.journal.model.JournalArticle",
		"com.liferay.wiki.model.WikiPage"
	};

}