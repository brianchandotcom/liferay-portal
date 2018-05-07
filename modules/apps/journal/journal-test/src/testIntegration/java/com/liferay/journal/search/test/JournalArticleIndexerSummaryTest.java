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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalArticleBuilder;
import com.liferay.journal.test.util.JournalArticleContent;
import com.liferay.journal.test.util.JournalArticleTitle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.portlet.MockPortletResponse;
import org.springframework.mock.web.portlet.MockRenderRequest;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class JournalArticleIndexerSummaryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_journalArticleBuilder = new JournalArticleBuilder();

		_journalArticleBuilder.setGroupId(_group.getGroupId());

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		_indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);
	}

	@Test
	public void testGetSummary() throws Exception {
		String content = "test content";
		String title = "test title";

		Document document = getDocument(title, content);

		assertSummary(title, content, document);
	}

	@Test
	public void testGetSummaryHighlighted() throws Exception {
		String content = "test content";
		String title = "test title";

		Document document = getDocument(title, content);

		String highlightedContent = StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, "test",
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, " content");
		String highlightedTitle = StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, "test",
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, " title");

		setSnippets(highlightedTitle, highlightedContent, document);

		assertSummary(highlightedTitle, highlightedContent, document);
	}

	@Test
	public void testStaleTitleFreshContent() throws Exception {
		String content = "test content";
		String title = "test title";

		Document document = getDocument(title, content);

		String staleContent = "stale content";
		String staleTitle = "stale title";

		setFields(staleTitle, staleContent, document);

		assertSummary(staleTitle, content, document);
	}

	@Test
	public void testStaleTitleFreshContentHighlighted() throws Exception {
		String content = "test content";
		String title = "test title";

		Document document = getDocument(title, content);

		String staleHighlightedContent = StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, "test",
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, " stale content");
		String staleHighlightedTitle = StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, "test",
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, " stale title");

		setSnippets(staleHighlightedTitle, staleHighlightedContent, document);

		String highlightedContent = StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, "test",
			HighlightUtil.HIGHLIGHT_TAG_CLOSE, " content");

		assertSummary(staleHighlightedTitle, highlightedContent, document);
	}

	protected JournalArticle addArticle() throws Exception {
		return _journalArticleBuilder.addArticle();
	}

	protected void assertSummary(
			String title, String content, Document document)
		throws Exception {

		Summary summary = getSummary(document);

		Assert.assertEquals(content, summary.getContent());
		Assert.assertEquals(title, summary.getTitle());
	}

	protected HttpServletRequest createHttpServletRequest(
		PortletRequest portletRequest) {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, portletRequest);

		return httpServletRequest;
	}

	protected HttpServletResponse createHttpServletResponse() {
		return new MockHttpServletResponse();
	}

	protected PortletRequest createPortletRequest() throws Exception {
		PortletRequest portletRequest = new MockRenderRequest();

		HttpServletRequest request = createHttpServletRequest(portletRequest);

		HttpServletResponse response = createHttpServletResponse();

		ThemeDisplay themeDisplay = createThemeDisplay(request, response);

		portletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return portletRequest;
	}

	protected PortletResponse createPortletResponse() {
		return new MockPortletResponse();
	}

	protected ThemeDisplay createThemeDisplay(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.getCompany(_group.getCompanyId()));
		themeDisplay.setLayout(LayoutTestUtil.addLayout(_group));

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);

		Theme theme = ThemeLocalServiceUtil.getTheme(
			_group.getCompanyId(), layoutSet.getThemeId());

		themeDisplay.setLookAndFeel(theme, null);

		themeDisplay.setRealUser(_user);
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(httpServletResponse);
		themeDisplay.setTimeZone(TimeZoneUtil.getDefault());
		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	protected Document getDocument(String title, String content)
		throws Exception {

		setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.US, title);
				}
			});

		setContent(
			new JournalArticleContent() {
				{
					name = "content";
					defaultLocale = LocaleUtil.US;

					put(LocaleUtil.US, content);
				}
			});

		JournalArticle journalArticle = addArticle();

		return _indexer.getDocument(journalArticle);
	}

	protected String getFieldName(String field) {
		return StringBundler.concat(
			field, StringPool.UNDERLINE, LocaleUtil.toLanguageId(Locale.US));
	}

	protected String getSnippetFieldName(String field) {
		return StringBundler.concat(
			Field.SNIPPET, StringPool.UNDERLINE, field, StringPool.UNDERLINE,
			LocaleUtil.toLanguageId(Locale.US));
	}

	protected Summary getSummary(Document document) throws Exception {
		return _indexer.getSummary(
			document, null, createPortletRequest(), createPortletResponse());
	}

	protected void setContent(JournalArticleContent journalArticleContent) {
		_journalArticleBuilder.setContent(journalArticleContent);
	}

	protected void setFields(String title, String content, Document document) {
		document.addText(getFieldName(Field.CONTENT), content);
		document.addText(getFieldName(Field.TITLE), title);
	}

	protected void setSnippets(
		String highlightedTitle, String highlightedContent, Document document) {

		document.addText(
			getSnippetFieldName(Field.CONTENT), highlightedContent);
		document.addText(getSnippetFieldName(Field.TITLE), highlightedTitle);
	}

	protected void setTitle(JournalArticleTitle journalArticleTitle) {
		_journalArticleBuilder.setTitle(journalArticleTitle);
	}

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<JournalArticle> _indexer;
	private JournalArticleBuilder _journalArticleBuilder;

	@DeleteAfterTestRun
	private User _user;

}