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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Eduardo Garcia
 */
@PrepareForTest({PortletLocalServiceUtil.class})

@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		long userId = TestPropsValues.getUserId();

		//Create site template

		LayoutSetPrototype layoutSetPrototype =
			ServiceTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		_lspGroup = layoutSetPrototype.getGroup();

		_lspLayout = ServiceTestUtil.addLayout(
			_lspGroup.getGroupId(), ServiceTestUtil.randomString(), true);

		updateLayoutTemplateId(_lspLayout, "1_column");

		_lspArticle = addArticle(
			_lspGroup.getGroupId(), 0, "Test Article", "Test Content");

		_lspJournalContentPortletId = addJournalContentPortletToLayout(
			userId, _lspLayout, _lspArticle, "column-1");

		//Create site from site template

		_group = ServiceTestUtil.addGroup(ServiceTestUtil.randomString());

		SitesUtil.updateLayoutSetPrototypesLinks(
			_group, layoutSetPrototype.getLayoutSetPrototypeId(), 0, true,
			true);

		propagateChanges(_group);
	}

	@Test
	public void testExportImportPortletData() throws Exception {

		// Check data after site creation

		String initContent = _lspArticle.getContent();

		JournalArticle article =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				_group.getGroupId(), _lspArticle.getUrlTitle());

		Assert.assertEquals(initContent, article.getContent());

		// Update site template data

		updateArticle(_lspArticle, "New Test Content");

		// Check data after layout reset

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false, _lspLayout.getFriendlyURL());

		SitesUtil.resetPrototype(layout);

		Assert.assertEquals(initContent, article.getContent());
	}

	@Test
	public void testExportImportPortletPreferences() throws Exception {

		// Check preferences after site creation

		JournalArticle article =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				_group.getGroupId(), _lspArticle.getUrlTitle());

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false, _lspLayout.getFriendlyURL());

		javax.portlet.PortletPreferences portletPreferences =
			getPortletPreferences(
				layout.getCompanyId(), layout.getPlid(),
				_lspJournalContentPortletId);

		Assert.assertEquals(
			article.getArticleId(),
			portletPreferences.getValue("articleId", StringPool.BLANK));

		Assert.assertEquals(
			String.valueOf(article.getGroupId()),
			portletPreferences.getValue("groupId", StringPool.BLANK));

		Assert.assertEquals(
			String.valueOf(true), portletPreferences.getValue(
				"showAvailableLocales", StringPool.BLANK));

		// Update site template preferences

		javax.portlet.PortletPreferences prefs = getPortletPreferences(
			_lspLayout.getCompanyId(), _lspLayout.getPlid(),
			_lspJournalContentPortletId);

		prefs.setValue("showAvailableLocales", String.valueOf(false));

		updatePortletPreferences(
			_lspLayout.getPlid(), _lspJournalContentPortletId, prefs);

		// Check preferences after layout reset

		SitesUtil.resetPrototype(layout);

		portletPreferences = getPortletPreferences(
			_group.getCompanyId(), layout.getPlid(),
			_lspJournalContentPortletId);

		Assert.assertEquals(
			article.getArticleId(),
			portletPreferences.getValue("articleId", StringPool.BLANK));

		Assert.assertEquals(
			String.valueOf(article.getGroupId()),
			portletPreferences.getValue("groupId", StringPool.BLANK));

		Assert.assertEquals(
			String.valueOf(false), portletPreferences.getValue(
			"showAvailableLocales", StringPool.BLANK));

	}

	protected JournalArticle addArticle(
			long groupId, long folderId, String name, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getDefault();

		String localeId = locale.toString();

		titleMap.put(locale, name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		String xmlContent = getArticleContent(content, localeId);

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId, folderId, 0, 0,
			StringPool.BLANK, true, 1, titleMap, descriptionMap, xmlContent,
			"general", null, null, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true,
			0, 0, 0, 0, 0, true, false, false, null, null, null, null,
			serviceContext);
	}

	protected String addJournalContentPortletToLayout(
			long userId, Layout layout, JournalArticle article, String columnId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet) layout.getLayoutType();

		String journalPortletId = layoutTypePortlet.addPortletId(
			userId, PortletKeys.JOURNAL_CONTENT, columnId, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		javax.portlet.PortletPreferences prefs = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(), journalPortletId);

		prefs.setValue("articleId", article.getArticleId());
		prefs.setValue("groupId", String.valueOf(article.getGroupId()));
		prefs.setValue("showAvailableLocales", String.valueOf(true));

		updatePortletPreferences(layout.getPlid(), journalPortletId, prefs);

		return journalPortletId;
	}

	protected String getArticleContent(String content, String localeId) {
		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"" + localeId + "\" ");
		sb.append("default-locale=\"" + localeId + "\">");
		sb.append("<static-content language-id=\"" + localeId + "\">");
		sb.append("<![CDATA[<p>");
		sb.append(content);
		sb.append("</p>]]>");
		sb.append("</static-content></root>");

		return sb.toString();
	}

	protected javax.portlet.PortletPreferences getPortletPreferences(
			long companyId, long plid, String portletId)
		throws Exception {

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);

		return portletPreferences;
	}

	protected JournalArticle updateArticle(
		JournalArticle article, String content) throws Exception {

		Locale locale = LocaleUtil.getDefault();

		String localeId = locale.toString();

		String xmlContent = getArticleContent(content, localeId);

		_lspArticle.setContent(xmlContent);

		return JournalArticleUtil.update(article, true);
	}

	protected PortletPreferences updatePortletPreferences(
			long plid, String portletId, javax.portlet.PortletPreferences prefs)
		throws Exception {

		PortletPreferences portletPrefs =
			PortletPreferencesLocalServiceUtil.updatePreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId, prefs);

		return portletPrefs;
	}

	private Group _group;
	private JournalArticle _lspArticle;
	private Group _lspGroup;
	private String _lspJournalContentPortletId;
	private Layout _lspLayout;

}