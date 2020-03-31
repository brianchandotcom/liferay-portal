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

package com.liferay.friendly.url.internal.upgrade.v3_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricardo Couso
 */
public class UpgradeFriendlyURLEntryLocalizations extends UpgradeProcess {

	protected void addMissingFriendlyURLEntryLocalizations() {
		StringBundler sb1 = new StringBundler(8);

		sb1.append("select JournalArticle.id_, ");
		sb1.append("JournalArticle.resourcePrimKey, JournalArticle.groupId, ");
		sb1.append("JournalArticle.companyId from (select resourcePrimKey, ");
		sb1.append("max(version) as latestVersion from JournalArticle group ");
		sb1.append("by resourcePrimKey) LatestVersion inner join ");
		sb1.append("JournalArticle on JournalArticle.resourcePrimKey = ");
		sb1.append("LatestVersion.resourcePrimKey and JournalArticle.version ");
		sb1.append("= LatestVersion.latestVersion");

		try (Statement statement1 = connection.createStatement();
			ResultSet rs1 = statement1.executeQuery(sb1.toString())) {

			while (rs1.next()) {
				long id = rs1.getLong(1);
				long resourcePrimKey = rs1.getLong(2);

				StringBundler sb2 = new StringBundler(13);

				sb2.append("select JAL.title, JAL.languageId from (select ");
				sb2.append("title, languageId from ");
				sb2.append("JournalArticleLocalization where articlePK = ");
				sb2.append(id);
				sb2.append(") JAL left join (select ");
				sb2.append("friendlyURLEntryLocalizationId, languageId from ");
				sb2.append("FriendlyURLEntryLocalization where classPK = ");
				sb2.append(resourcePrimKey);
				sb2.append(" and classNameId = ");
				sb2.append(_classNameIdJournalArticle);
				sb2.append(") FURLEL on JAL.languageId = FURLEL.languageId ");
				sb2.append("where FURLEL.friendlyURLEntryLocalizationId is ");
				sb2.append("null");

				Map<String, String> urlTitleMap = new HashMap<>();

				try (Statement statement2 = connection.createStatement();
					ResultSet rs2 = statement2.executeQuery(sb2.toString())) {

					while (rs2.next()) {
						String title = rs2.getString(1);
						String languageId = rs2.getString(2);

						urlTitleMap.put(languageId, title);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		setUpClassNameIds();
		addMissingFriendlyURLEntryLocalizations();
	}

	protected void setUpClassNameIds() {
		_classNameIdJournalArticle = PortalUtil.getClassNameId(
			"com.liferay.journal.model.JournalArticle");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeFriendlyURLEntryLocalizations.class);

	private long _classNameIdJournalArticle;

}