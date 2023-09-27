/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.search.test;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
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

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			PortalUtil.getSiteGroupId(_group.getGroupId()),
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		String ddmTemplateKey = "BASIC-WEB-CONTENT";

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0, titleMap,
			descriptionMap,
			DDMStructureTestUtil.getSampleStructuredContent("content", "title"),
			ddmStructure.getStructureId(), ddmTemplateKey, serviceContext);

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	public ServiceContext createServiceContext() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		return serviceContext;
	}

	public List<JournalArticle> getJournalArticles() {
		return _journalArticles;
	}

	public void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;
	private Group _group;
	private JournalArticleLocalService _journalArticleLocalService;
	private final List<JournalArticle> _journalArticles = new ArrayList<>();

}