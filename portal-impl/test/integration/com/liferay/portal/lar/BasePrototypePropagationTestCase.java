/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Eduardo Garcia
 */
public abstract class BasePrototypePropagationTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceTestUtil.getServiceContext());

		// Group

		_group = GroupTestUtil.addGroup();

		// Global scope article

		Company company = CompanyUtil.fetchByPrimaryKey(_group.getCompanyId());

		_globalGroupId = company.getGroup().getGroupId();

		_globalJournalArticle = JournalTestUtil.addArticle(
			_globalGroupId, "Global Article", "Global Content");

		// Layout prototype

		_layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		_layoutPrototypeLayout = _layoutPrototype.getLayout();

		LayoutTestUtil.updateLayoutTemplateId(
			_layoutPrototypeLayout, _initialLayoutTemplateId);

		doSetUp();
	}

	@Test
	public void testLayoutTypePropagationWithLinkDisabled() throws Exception {
		doTestLayoutTypePropagation(false);
	}

	@Test
	public void testLayoutTypePropagationWithLinkEnabled() throws Exception {
		doTestLayoutTypePropagation(true);
	}

	@Test
	public void testPortletPreferencesPropagationWithLinkDisabled()
		throws Exception {

		doTestPortletPreferencesPropagation(false);
	}

	@Test
	public void testPortletPreferencesPropagationWithLinkEnabled()
		throws Exception {

		doTestPortletPreferencesPropagation(true);
	}

	protected String addJournalContentPortletToLayout(
			long userId, Layout layout, JournalArticle journalArticle,
			String columnId)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(
			"articleId", new String[] {journalArticle.getArticleId()});
		parameterMap.put(
			"groupId",
			new String[] {String.valueOf(journalArticle.getGroupId())});
		parameterMap.put(
			"showAvailableLocales", new String[] {Boolean.TRUE.toString()});

		return LayoutTestUtil.addPortletToLayout(
			userId, layout, PortletKeys.JOURNAL_CONTENT, columnId,
			parameterMap);
	}

	protected abstract void doSetUp() throws Exception;

	protected void doTestLayoutTypePropagation(boolean linkEnabled)
		throws Exception {

		setLinkEnabled(linkEnabled);

		int initialPortletCount = LayoutTestUtil.getPortlets(_layout).size();

		_prototypeLayout = LayoutTestUtil.updateLayoutTemplateId(
			_prototypeLayout, "1_column");

		LayoutTestUtil.updateLayoutColumnCustomizable(
			_prototypeLayout, "column-1", true);

		addJournalContentPortletToLayout(
			TestPropsValues.getUserId(), _prototypeLayout,
			_globalJournalArticle, "column-1");

		if (linkEnabled) {
			Assert.assertEquals(
				_initialLayoutTemplateId,
				LayoutTestUtil.getLayoutTemplateId(_layout));

			Assert.assertFalse(
				LayoutTestUtil.isLayoutColumnCustomizable(_layout, "column-1"));

			Assert.assertEquals(
				initialPortletCount,
				LayoutTestUtil.getPortlets(_layout).size());
		}

		_layout = propagateChanges(_layout);

		if (linkEnabled) {
			Assert.assertEquals(
				"1_column", LayoutTestUtil.getLayoutTemplateId(_layout));

			Assert.assertTrue(
				LayoutTestUtil.isLayoutColumnCustomizable(_layout, "column-1"));

			Assert.assertEquals(
				initialPortletCount + 1,
				LayoutTestUtil.getPortlets(_layout).size());
		}
		else {
			Assert.assertEquals(
				_initialLayoutTemplateId,
				LayoutTestUtil.getLayoutTemplateId(_layout));

			Assert.assertFalse(
				LayoutTestUtil.isLayoutColumnCustomizable(_layout, "column-1"));

			Assert.assertEquals(
				initialPortletCount,
				LayoutTestUtil.getPortlets(_layout).size());
		}
	}

	protected void doTestPortletPreferencesPropagation(boolean linkEnabled)
		throws Exception {

		doTestPortletPreferencesPropagation(linkEnabled, true);
	}

	protected void doTestPortletPreferencesPropagation(
			boolean linkEnabled, boolean globalScope)
		throws Exception {

		setLinkEnabled(linkEnabled);

		PortletPreferences layoutSetPrototypePortletPreferences =
			LayoutTestUtil.getPortletPreferences(
				_prototypeLayout, _journalContentPortletId);

		layoutSetPrototypePortletPreferences.setValue(
			"articleId", StringPool.BLANK);

		layoutSetPrototypePortletPreferences.setValue(
			"showAvailableLocales", Boolean.FALSE.toString());

		if (globalScope) {
			layoutSetPrototypePortletPreferences.setValue(
				"groupId", String.valueOf(_globalGroupId));
			layoutSetPrototypePortletPreferences.setValue(
				"lfrScopeType", "company");
		}

		LayoutTestUtil.updatePortletPreferences(
			_prototypeLayout.getPlid(), _journalContentPortletId,
			layoutSetPrototypePortletPreferences);

		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(
				_layout, _journalContentPortletId);

		if (linkEnabled) {
			if (globalScope) {
				Assert.assertEquals(
					StringPool.BLANK,
					portletPreferences.getValue("articleId", StringPool.BLANK));
			}
			else {

				// Changes in preferences of local ids are not propagated

				Assert.assertEquals(
					_journalArticle.getArticleId(),
					portletPreferences.getValue("articleId", StringPool.BLANK));
			}

			Assert.assertEquals(
				Boolean.FALSE.toString(),
				portletPreferences.getValue(
					"showAvailableLocales", StringPool.BLANK));
		}
		else {
			Assert.assertEquals(
				_journalArticle.getArticleId(),
				portletPreferences.getValue("articleId", StringPool.BLANK));
		}
	}

	protected Layout propagateChanges(Layout layout) throws Exception {
		return LayoutLocalServiceUtil.getLayout(layout.getPlid());
	}

	protected abstract void setLinkEnabled(boolean linkEnabled)
		throws Exception;

	protected long _globalGroupId;
	protected JournalArticle _globalJournalArticle;
	protected Group _group;
	protected String _initialLayoutTemplateId = "2_2_columns";
	protected JournalArticle _journalArticle;
	protected String _journalContentPortletId;
	protected Layout _layout;
	protected LayoutPrototype _layoutPrototype;
	protected Layout _layoutPrototypeLayout;
	protected Layout _prototypeLayout;

}