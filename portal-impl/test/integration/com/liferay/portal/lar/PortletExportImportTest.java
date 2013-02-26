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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
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
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
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
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() throws Exception {

		// Create site template

		FinderCacheUtil.clearCache();

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		_layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

		_layoutSetPrototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true);

		updateLayoutTemplateId(_layoutSetPrototypeLayout, "1_column");

		_layoutSetPrototypeJournalArticle = addJournalArticle(
			_layoutSetPrototypeGroup.getGroupId(), 0, "Test Article",
			"Test Content");

		_layoutSetPrototypeJournalContentPortletId =
			addJournalContentPortletToLayout(
				TestPropsValues.getUserId(), _layoutSetPrototypeLayout,
				_layoutSetPrototypeJournalArticle, "column-1");

		// Create site from site template

		_group = GroupTestUtil.addGroup();

		SitesUtil.updateLayoutSetPrototypesLinks(
			_group, layoutSetPrototype.getLayoutSetPrototypeId(), 0, true,
			true);

		propagateChanges(_group);
	}

	@Test
	public void testExportImportPortletData() throws Exception {

		// Check data after site creation

		String content = _layoutSetPrototypeJournalArticle.getContent();

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				_group.getGroupId(),
				_layoutSetPrototypeJournalArticle.getUrlTitle());

		Assert.assertEquals(content, journalArticle.getContent());

		// Update site template data

		updateArticle(_layoutSetPrototypeJournalArticle, "New Test Content");

		// Check data after layout reset

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false,
			_layoutSetPrototypeLayout.getFriendlyURL());

		SitesUtil.resetPrototype(layout);

		Assert.assertEquals(content, journalArticle.getContent());
	}

	@Test
	public void testExportImportPortletPreferences() throws Exception {

		// Check preferences after site creation

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				_group.getGroupId(),
				_layoutSetPrototypeJournalArticle.getUrlTitle());

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false,
			_layoutSetPrototypeLayout.getFriendlyURL());

		javax.portlet.PortletPreferences jxPreferences = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(),
			_layoutSetPrototypeJournalContentPortletId);

		Assert.assertEquals(
			journalArticle.getArticleId(),
			jxPreferences.getValue("articleId", StringPool.BLANK));

		Assert.assertEquals(
			String.valueOf(journalArticle.getGroupId()),
			jxPreferences.getValue("groupId", StringPool.BLANK));

		Assert.assertEquals(
			String.valueOf(true), jxPreferences.getValue(
				"showAvailableLocales", StringPool.BLANK));

		// Update site template preferences

		javax.portlet.PortletPreferences layoutSetprototypeJxPreferences =
			getPortletPreferences(
				_layoutSetPrototypeLayout.getCompanyId(),
				_layoutSetPrototypeLayout.getPlid(),
				_layoutSetPrototypeJournalContentPortletId);

		layoutSetprototypeJxPreferences.setValue(
			"showAvailableLocales", String.valueOf(false));

		updatePortletPreferences(
			_layoutSetPrototypeLayout.getPlid(),
			_layoutSetPrototypeJournalContentPortletId,
			layoutSetprototypeJxPreferences);

		// Check preferences after layout reset

		SitesUtil.resetPrototype(layout);

		jxPreferences = getPortletPreferences(
			_group.getCompanyId(), layout.getPlid(),
			_layoutSetPrototypeJournalContentPortletId);

		Assert.assertEquals(
			journalArticle.getArticleId(),
			jxPreferences.getValue("articleId", StringPool.BLANK));
		Assert.assertEquals(
			String.valueOf(journalArticle.getGroupId()),
			jxPreferences.getValue("groupId", StringPool.BLANK));
		Assert.assertEquals(
			Boolean.FALSE.toString(),
			jxPreferences.getValue("showAvailableLocales", StringPool.BLANK));

		// Update journal content portlet with a new globally scoped journal
		// article

		Company company = CompanyUtil.fetchByPrimaryKey(
			_layoutSetPrototypeLayout.getCompanyId());

		Group companyGroup = company.getGroup();

		JournalArticle globalScopeJournalArticle = addJournalArticle(
			companyGroup.getGroupId(), 0, "Global Article", "Global Content");

		layoutSetprototypeJxPreferences.setValue(
			"articleId", globalScopeJournalArticle.getArticleId());
		layoutSetprototypeJxPreferences.setValue(
			"groupId", Long.toString(companyGroup.getGroupId()));
		layoutSetprototypeJxPreferences.setValue(
			"lfrScopeLayoutUuid", StringPool.BLANK);
		layoutSetprototypeJxPreferences.setValue("lfrScopeType", "company");

		updatePortletPreferences(
			_layoutSetPrototypeLayout.getPlid(),
			_layoutSetPrototypeJournalContentPortletId,
			layoutSetprototypeJxPreferences);

		jxPreferences = getPortletPreferences(
			_group.getCompanyId(), layout.getPlid(),
			_layoutSetPrototypeJournalContentPortletId);

		// Check preferences when journal article is from the global scope

		Assert.assertEquals(
			globalScopeJournalArticle.getArticleId(),
			jxPreferences.getValue("articleId", StringPool.BLANK));
		Assert.assertEquals(
			String.valueOf(companyGroup.getGroupId()),
			jxPreferences.getValue("groupId", StringPool.BLANK));
		Assert.assertEquals(
			StringPool.BLANK,
			jxPreferences.getValue("lfrScopeLayoutUuid", StringPool.BLANK));
		Assert.assertEquals(
			"company",
			jxPreferences.getValue("lfrScopeType", StringPool.BLANK));
	}

	protected JournalArticle addJournalArticle(
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
			long userId, Layout layout, JournalArticle journalArticle,
			String columnId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String journalPortletId = layoutTypePortlet.addPortletId(
			userId, PortletKeys.JOURNAL_CONTENT, columnId, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		javax.portlet.PortletPreferences prefs = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(), journalPortletId);

		prefs.setValue("articleId", journalArticle.getArticleId());
		prefs.setValue("groupId", String.valueOf(journalArticle.getGroupId()));
		prefs.setValue("showAvailableLocales", Boolean.TRUE.toString());

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

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	protected JournalArticle updateArticle(
			JournalArticle journalArticle, String content)
		throws Exception {

		Locale locale = LocaleUtil.getDefault();

		String localeId = locale.toString();

		String xmlContent = getArticleContent(content, localeId);

		_layoutSetPrototypeJournalArticle.setContent(xmlContent);

		return JournalArticleUtil.update(journalArticle);
	}

	protected PortletPreferences updatePortletPreferences(
			long plid, String portletId,
			javax.portlet.PortletPreferences jxPreferences)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.updatePreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
				jxPreferences);

		return portletPreferences;
	}

	private Group _group;
	private Group _layoutSetPrototypeGroup;
	private JournalArticle _layoutSetPrototypeJournalArticle;
	private String _layoutSetPrototypeJournalContentPortletId;
	private Layout _layoutSetPrototypeLayout;

}