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

package com.liferay.asset.search.test;

import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleFixture {

	public JournalArticle addJournalArticle() throws Exception {
		return addJournalArticle(createServiceContext());
	}

	public JournalArticle addJournalArticle(ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		String ddmStructureKey = "BASIC-WEB-CONTENT";
		String ddmTemplateKey = "BASIC-WEB-CONTENT";

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			TestPropsValues.getUserId(), _group.getGroupId(), 0, titleMap,
			descriptionMap,
			DDMStructureTestUtil.getSampleStructuredContent("content", "title"),
			ddmStructureKey, ddmTemplateKey, serviceContext);

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	public ServiceContext createServiceContext() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		return serviceContext;
	}

	public List<JournalArticle> getJournalArticles() {
		return _journalArticles;
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	private Group _group;
	private JournalArticleLocalService _journalArticleLocalService;
	private final List<JournalArticle> _journalArticles = new ArrayList<>();

}