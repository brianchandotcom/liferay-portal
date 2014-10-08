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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.SystemEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class VerifySystemEvent extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyJournalArticleDeleteSystemEvents();
	}

	protected void verifyJournalArticleDeleteSystemEvents() throws Exception {
		DynamicQuery dynamicQuery = SystemEventLocalServiceUtil.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(
			classNameIdProperty.eq(
				PortalUtil.getClassNameId(JournalArticle.class)));

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

		List<SystemEvent> systemEvents =
			SystemEventLocalServiceUtil.dynamicQuery(dynamicQuery);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + systemEvents.size() + " delete system events " +
					"for journal articles");
		}

		for (SystemEvent systemEvent : systemEvents) {
			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				systemEvent.getExtraData());

			if (extraDataJSONObject.has("uuid") ||
				!extraDataJSONObject.has("version")) {

				continue;
			}

			JournalArticleResource journalArticleResource =
				JournalArticleResourceLocalServiceUtil.
					fetchJournalArticleResourceByUuidAndGroupId(
						systemEvent.getClassUuid(), systemEvent.getGroupId());

			if (journalArticleResource == null) {
				continue;
			}

			JournalArticle journalArticle =
				JournalArticleLocalServiceUtil.fetchArticle(
					systemEvent.getGroupId(),
					journalArticleResource.getArticleId(),
					extraDataJSONObject.getDouble("version"));

			if ((journalArticle == null) || journalArticle.isInTrash()) {
				continue;
			}

			SystemEventLocalServiceUtil.deleteSystemEvent(systemEvent);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delete system events verified for journal articles");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifySystemEvent.class);

}