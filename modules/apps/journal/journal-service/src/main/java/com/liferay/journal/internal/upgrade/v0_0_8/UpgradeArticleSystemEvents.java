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

package com.liferay.journal.internal.upgrade.v0_0_8;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Daniel Kocsis
 * @author Preston Crary
 * @author Alberto Chaparro
 */
public class UpgradeArticleSystemEvents extends UpgradeProcess {

	public UpgradeArticleSystemEvents(
		SystemEventLocalService systemEventLocalService) {

		_systemEventLocalService = systemEventLocalService;
	}

	protected void deleteJournalArticleSystemEvents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			DynamicQuery dynamicQuery = _systemEventLocalService.dynamicQuery();

			Property classNameIdProperty = PropertyFactoryUtil.forName(
				"classNameId");

			dynamicQuery.add(
				classNameIdProperty.eq(PortalUtil.getClassNameId(_CLASS_NAME)));

			Property typeProperty = PropertyFactoryUtil.forName("type");

			dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

			List<SystemEvent> systemEvents =
				_systemEventLocalService.dynamicQuery(dynamicQuery);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Processing ", systemEvents.size(),
						" delete system events for journal articles"));
			}

			for (SystemEvent systemEvent : systemEvents) {
				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject(
						systemEvent.getExtraData());

				if (extraDataJSONObject.has("uuid") ||
					!extraDataJSONObject.has("version")) {

					continue;
				}

				String articleId = null;

				try (PreparedStatement ps = connection.prepareStatement(
						"select articleId from JournalArticleResource where " +
							"JournalArticleResource.uuid_ = ? and " +
								"JournalArticleResource.groupId = ?")) {

					ps.setString(1, systemEvent.getClassUuid());
					ps.setLong(2, systemEvent.getGroupId());

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							articleId = rs.getString(1);
						}
					}
				}

				if (articleId == null) {
					continue;
				}

				try (PreparedStatement ps = connection.prepareStatement(
						"select 1 from JournalArticle where groupId = ? and " +
							"articleId = ? and version = ? and status = ?")) {

					ps.setLong(1, systemEvent.getGroupId());
					ps.setString(2, articleId);
					ps.setDouble(3, extraDataJSONObject.getDouble("version"));
					ps.setInt(4, WorkflowConstants.STATUS_IN_TRASH);

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							_systemEventLocalService.deleteSystemEvent(
								systemEvent);
						}
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delete system events verified for journal articles");
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteJournalArticleSystemEvents();
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeArticleSystemEvents.class);

	private final SystemEventLocalService _systemEventLocalService;

}