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

package com.liferay.journal.search.test;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
public class JournalArticleDDMStructureFixture {

	public JournalArticleDDMStructureFixture(
		Group group, JournalArticleLocalService journalArticleLocalService) {

		_group = group;

		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			journalArticleLocalService);
	}

	public DDMStructure createStructureWithJournalArticle(
			String title, Locale locale)
		throws Exception, PortalException {

		JournalArticleBlueprint journalArticleBlueprint =
			_createJournalArticleBlueprint(title, null, locale);

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			new Locale[] {locale}, locale);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm,
			locale);

		_ddmStructures.add(ddmStructure);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		getTemplates().add(ddmTemplate);

		JournalArticle journalArticle = _journalArticleSearchFixture.addArticle(
			journalArticleBlueprint, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		getJournalArticles().add(journalArticle);

		return ddmStructure;
	}

	public List<JournalArticle> getJournalArticles() {
		return _journalArticleSearchFixture.getJournalArticles();
	}

	public List<DDMStructure> getStructures() {
		return _ddmStructures;
	}

	public List<DDMTemplate> getTemplates() {
		return _ddmTemplates;
	}

	private JournalArticleBlueprint _createJournalArticleBlueprint(
		String title, String content, Locale locale) {

		return new JournalArticleBlueprint() {
			{
				setGroupId(_group.getGroupId());
				setJournalArticleContent(
					new JournalArticleContent() {
						{
							setDefaultLocale(locale);
							setName("content");
							put(locale, content);
						}
					});
				setJournalArticleTitle(
					new JournalArticleTitle() {
						{
							put(locale, title);
						}
					});
			}
		};
	}

	private final List<DDMStructure> _ddmStructures = new ArrayList<>();
	private final List<DDMTemplate> _ddmTemplates = new ArrayList<>();
	private final Group _group;
	private final JournalArticleSearchFixture _journalArticleSearchFixture;

}