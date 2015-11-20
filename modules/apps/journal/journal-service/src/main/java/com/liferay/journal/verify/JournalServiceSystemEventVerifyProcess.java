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

package com.liferay.journal.verify;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.SystemEventLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = JournalServiceSystemEventVerifyProcess.class)
public class JournalServiceSystemEventVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyJournalArticleDeleteSystemEvents();
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setSystemEventLocalService(
		SystemEventLocalService systemEventLocalService) {

		_systemEventLocalService = systemEventLocalService;
	}

	protected void verifyJournalArticleDeleteSystemEvents() throws Exception {
		DynamicQuery dynamicQuery = _systemEventLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(
			classNameIdProperty.eq(
				PortalUtil.getClassNameId(JournalArticle.class)));

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

		List<SystemEvent> systemEvents = _systemEventLocalService.dynamicQuery(
			dynamicQuery);

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
				_journalArticleResourceLocalService.
					fetchJournalArticleResourceByUuidAndGroupId(
						systemEvent.getClassUuid(), systemEvent.getGroupId());

			if (journalArticleResource == null) {
				continue;
			}

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchArticle(
					systemEvent.getGroupId(),
					journalArticleResource.getArticleId(),
					extraDataJSONObject.getDouble("version"));

			if ((journalArticle == null) || journalArticle.isInTrash()) {
				continue;
			}

			_systemEventLocalService.deleteSystemEvent(systemEvent);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delete system events verified for journal articles");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServiceSystemEventVerifyProcess.class);

	private volatile JournalArticleLocalService _journalArticleLocalService;
	private volatile JournalArticleResourceLocalService
		_journalArticleResourceLocalService;
	private volatile SystemEventLocalService _systemEventLocalService;

}