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

package com.liferay.portal.lar;

import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;
import com.liferay.portlet.sites.util.Sites;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Julio Camarero
 * @author Eduardo Garcia
 */
@Sync
public class LayoutSetPrototypePropagationTest
	extends BasePrototypePropagationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddChildLayoutWithLinkDisabled() throws Exception {
		testAddChildLayout(false);
	}

	@Test
	public void testAddChildLayoutWithLinkEnabled() throws Exception {
		testAddChildLayout(true);
	}

	@Test
	public void testAddGroup() throws Exception {
		Assert.assertEquals(_initialPrototypeLayoutCount, _initialLayoutCount);
	}

	@Test
	public void testIsLayoutDeleteable() throws Exception {
		Assert.assertFalse(SitesUtil.isLayoutDeleteable(layout));

		setLinkEnabled(false);

		Assert.assertTrue(SitesUtil.isLayoutDeleteable(layout));
	}

	@Test
	public void testIsLayoutSortable() throws Exception {
		Assert.assertFalse(SitesUtil.isLayoutSortable(layout));

		setLinkEnabled(false);

		Assert.assertTrue(SitesUtil.isLayoutSortable(layout));
	}

	@Test
	public void testIsLayoutUpdateable() throws Exception {
		doTestIsLayoutUpdateable();
	}

	@Test
	public void testLayoutPermissionPropagationWithLinkEnabled()
		throws Exception {

		setLinkEnabled(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.POWER_USER);

		ResourcePermissionServiceUtil.setIndividualResourcePermissions(
			prototypeLayout.getGroupId(), prototypeLayout.getCompanyId(),
			Layout.class.getName(),
			String.valueOf(prototypeLayout.getPrimaryKey()), role.getRoleId(),
			new String[] {ActionKeys.CUSTOMIZE});

		prototypeLayout = updateModifiedDate(
			prototypeLayout,
			new Date(System.currentTimeMillis() + Time.MINUTE));

		CacheUtil.clearCache(prototypeLayout.getCompanyId());

		propagateChanges(group);

		Assert.assertTrue(
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				layout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layout.getPrimaryKey()), role.getRoleId(),
				ActionKeys.CUSTOMIZE));
	}

	@Test
	public void testLayoutPropagationWhenLoadingLayoutsTreeWithLinkEnabled()
		throws Exception {

		setLinkEnabled(true);

		LayoutTestUtil.addLayout(_layoutSetPrototypeGroup, true);

		Assert.assertEquals(
			_initialPrototypeLayoutCount, getGroupLayoutCount());

		LayoutServiceUtil.getLayouts(
			group.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			false, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Thread.sleep(2000);

		Assert.assertEquals(
			_initialPrototypeLayoutCount + 1, getGroupLayoutCount());
	}

	@Test
	public void testLayoutPropagationWithLayoutPrototypeLinkDisabled()
		throws Exception {

		doTestLayoutPropagationWithLayoutPrototype(false);
	}

	@Test
	public void testLayoutPropagationWithLayoutPrototypeLinkEnabled()
		throws Exception {

		doTestLayoutPropagationWithLayoutPrototype(true);
	}

	@Test
	public void testLayoutPropagationWithLinkDisabled() throws Exception {
		doTestLayoutPropagation(false);
	}

	@Test
	public void testLayoutPropagationWithLinkEnabled() throws Exception {
		doTestLayoutPropagation(true);
	}

	@Test
	public void testPortletDataPropagationWithLinkDisabled() throws Exception {
		doTestPortletDataPropagation(false);
	}

	@Test
	public void testPortletDataPropagationWithLinkEnabled() throws Exception {
		doTestPortletDataPropagation(true);
	}

	@Test
	public void testPortletPreferencesPropagationWithGlobalScopeLinkDisabled()
		throws Exception {

		doTestPortletPreferencesPropagation(false, true);
	}

	@Test
	public void testPortletPreferencesPropagationWithGlobalScopeLinkEnabled()
		throws Exception {

		doTestPortletPreferencesPropagation(true, true);
	}

	@Test
	public void testPortletPreferencesPropagationWithPreferencesUniquePerLayoutEnabled()
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), PortletKeys.NAVIGATION);

		portlet.setPreferencesUniquePerLayout(false);

		_layoutSetPrototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup, true, layoutPrototype, true);

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put("bulletStyle", new String[] {"Dots"});

		String navigationPortletId1 = LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layoutSetPrototypeLayout,
			PortletKeys.NAVIGATION, "column-1", preferenceMap);

		preferenceMap.put("bulletStyle", new String[] {"Arrows"});

		String navigationPortletId2 = LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layoutSetPrototypeLayout,
			PortletKeys.NAVIGATION, "column-2", preferenceMap);

		propagateChanges(group);

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			group.getGroupId(), false,
			_layoutSetPrototypeLayout.getFriendlyURL());

		PortletPreferences navigationPortletIdPortletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				group.getGroupId(), layout, PortletKeys.NAVIGATION, null);

		Assert.assertEquals(
			"Arrows",
			navigationPortletIdPortletPreferences.getValue(
				"bulletStyle", StringPool.BLANK));

		PortletPreferences navigationPortletId1PortletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, navigationPortletId1, null);

		Assert.assertEquals(
			"Arrows",
			navigationPortletId1PortletPreferences.getValue(
				"bulletStyle", StringPool.BLANK));

		PortletPreferences navigationPortletId2PortletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, navigationPortletId2, null);

		Assert.assertEquals(
			"Arrows",
			navigationPortletId2PortletPreferences.getValue(
				"bulletStyle", StringPool.BLANK));
	}

	@Test
	public void testResetLayoutTemplate() throws Exception {
		SitesUtil.resetPrototype(layout);
		SitesUtil.resetPrototype(_layout);

		propagateChanges(group);

		setLinkEnabled(true);

		layout = LayoutTestUtil.updateLayoutTemplateId(layout, "1_column");

		Assert.assertTrue(SitesUtil.isLayoutModifiedSinceLastMerge(layout));
		Assert.assertFalse(SitesUtil.isLayoutModifiedSinceLastMerge(_layout));

		_layout = LayoutTestUtil.updateLayoutTemplateId(_layout, "1_column");

		layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

		SitesUtil.resetPrototype(layout);

		layout = propagateChanges(layout);

		Assert.assertFalse(SitesUtil.isLayoutModifiedSinceLastMerge(layout));
		Assert.assertEquals(
			initialLayoutTemplateId,
			LayoutTestUtil.getLayoutTemplateId(layout));

		_layout = propagateChanges(_layout);

		Assert.assertTrue(SitesUtil.isLayoutModifiedSinceLastMerge(_layout));
		Assert.assertEquals(
			"1_column", LayoutTestUtil.getLayoutTemplateId(_layout));
	}

	@Test
	public void testResetPortletPreferences() throws Exception {
		LayoutTestUtil.updateLayoutPortletPreference(
			prototypeLayout, portletId, "showAvailableLocales",
			Boolean.FALSE.toString());

		SitesUtil.resetPrototype(layout);
		SitesUtil.resetPrototype(_layout);

		propagateChanges(group);

		setLinkEnabled(true);

		layout = LayoutTestUtil.updateLayoutPortletPreference(
			layout, portletId, "showAvailableLocales", Boolean.TRUE.toString());

		Assert.assertTrue(SitesUtil.isLayoutModifiedSinceLastMerge(layout));
		Assert.assertFalse(SitesUtil.isLayoutModifiedSinceLastMerge(_layout));

		_layout = LayoutTestUtil.updateLayoutPortletPreference(
			_layout, _portletId, "showAvailableLocales",
			Boolean.TRUE.toString());

		layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

		SitesUtil.resetPrototype(layout);

		layout = propagateChanges(layout);

		Assert.assertFalse(SitesUtil.isLayoutModifiedSinceLastMerge(layout));

		PortletPreferences layoutPortletPreferences =
			LayoutTestUtil.getPortletPreferences(layout, portletId);

		Assert.assertEquals(
			Boolean.FALSE.toString(),
			layoutPortletPreferences.getValue(
				"showAvailableLocales", StringPool.BLANK));

		_layout = propagateChanges(_layout);

		Assert.assertTrue(SitesUtil.isLayoutModifiedSinceLastMerge(_layout));

		layoutPortletPreferences = LayoutTestUtil.getPortletPreferences(
			_layout, _portletId);

		Assert.assertEquals(
			Boolean.TRUE.toString(),
			layoutPortletPreferences.getValue(
				"showAvailableLocales", StringPool.BLANK));
	}

	@Override
	protected void doSetUp() throws Exception {

		// Layout set prototype

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		_layoutSetPrototypeGroup = _layoutSetPrototype.getGroup();

		prototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup, true);

		LayoutTestUtil.updateLayoutTemplateId(
			prototypeLayout, initialLayoutTemplateId);

		_layoutSetPrototypeJournalArticle = JournalTestUtil.addArticle(
			_layoutSetPrototypeGroup.getGroupId(), "Test Article",
			"Test Content");

		portletId = addPortletToLayout(
			TestPropsValues.getUserId(), prototypeLayout,
			_layoutSetPrototypeJournalArticle, "column-1");

		_prototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup, true);

		LayoutTestUtil.updateLayoutTemplateId(
			_prototypeLayout, initialLayoutTemplateId);

		_portletId = addPortletToLayout(
			TestPropsValues.getUserId(), _prototypeLayout,
			_layoutSetPrototypeJournalArticle, "column-1");

		_initialPrototypeLayoutCount = LayoutLocalServiceUtil.getLayoutsCount(
			_layoutSetPrototypeGroup, true);

		// Group

		setLinkEnabled(true);

		layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			group.getGroupId(), false, prototypeLayout.getFriendlyURL());

		_layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			group.getGroupId(), false, _prototypeLayout.getFriendlyURL());

		_initialLayoutCount = getGroupLayoutCount();

		journalArticle = JournalArticleLocalServiceUtil.getArticleByUrlTitle(
			group.getGroupId(),
			_layoutSetPrototypeJournalArticle.getUrlTitle());
	}

	protected void doTestIsLayoutUpdateable() throws Exception {
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(layout));
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout));

		prototypeLayout = LayoutLocalServiceUtil.getLayout(
			prototypeLayout.getPlid());

		setLayoutUpdateable(prototypeLayout, false);

		Assert.assertFalse(SitesUtil.isLayoutUpdateable(layout));
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout));

		setLayoutsUpdateable(false);

		Assert.assertFalse(SitesUtil.isLayoutUpdateable(layout));
		Assert.assertFalse(SitesUtil.isLayoutUpdateable(_layout));

		setLinkEnabled(false);

		Assert.assertTrue(SitesUtil.isLayoutUpdateable(layout));
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout));
	}

	protected void doTestLayoutPropagation(boolean linkEnabled)
		throws Exception {

		setLinkEnabled(linkEnabled);

		Layout layout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup, true);

		Assert.assertEquals(
			_initialPrototypeLayoutCount, getGroupLayoutCount());

		propagateChanges(group);

		if (linkEnabled) {
			Assert.assertEquals(
				_initialPrototypeLayoutCount + 1, getGroupLayoutCount());
		}
		else {
			Assert.assertEquals(
				_initialPrototypeLayoutCount, getGroupLayoutCount());
		}

		LayoutLocalServiceUtil.deleteLayout(
			layout, true, ServiceContextTestUtil.getServiceContext());

		if (linkEnabled) {
			Assert.assertEquals(
				_initialPrototypeLayoutCount + 1, getGroupLayoutCount());
		}
		else {
			Assert.assertEquals(
				_initialPrototypeLayoutCount, getGroupLayoutCount());
		}

		propagateChanges(group);

		Assert.assertEquals(
			_initialPrototypeLayoutCount, getGroupLayoutCount());
	}

	protected void doTestLayoutPropagationWithLayoutPrototype(
			boolean layoutSetLayoutLinkEnabled)
		throws Exception {

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		_layoutSetPrototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup, true, layoutPrototype,
			layoutSetLayoutLinkEnabled);

		_layoutSetPrototypeLayout = propagateChanges(_layoutSetPrototypeLayout);

		propagateChanges(group);

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			group.getGroupId(), false,
			_layoutSetPrototypeLayout.getFriendlyURL());

		LayoutTestUtil.updateLayoutTemplateId(
			layoutPrototypeLayout, "1_column");

		if (layoutSetLayoutLinkEnabled) {
			Assert.assertEquals(
				initialLayoutTemplateId,
				LayoutTestUtil.getLayoutTemplateId(layout));
		}

		layout = propagateChanges(layout);

		propagateChanges(group);

		if (layoutSetLayoutLinkEnabled) {
			Assert.assertEquals(
				"1_column", LayoutTestUtil.getLayoutTemplateId(layout));
		}
		else {
			Assert.assertEquals(
				initialLayoutTemplateId,
				LayoutTestUtil.getLayoutTemplateId(layout));
		}
	}

	protected void doTestPortletDataPropagation(boolean linkEnabled)
		throws Exception {

		setLinkEnabled(linkEnabled);

		String content = _layoutSetPrototypeJournalArticle.getContent();

		for (String languageId : journalArticle.getAvailableLanguageIds()) {
			String localization = LocalizationUtil.getLocalization(
				content, languageId);

			String importedLocalization = LocalizationUtil.getLocalization(
				journalArticle.getContent(), languageId);

			Assert.assertEquals(localization, importedLocalization);
		}

		String newContent = DDMStructureTestUtil.getSampleStructuredContent(
			"New Test Content");

		JournalTestUtil.updateArticle(
			_layoutSetPrototypeJournalArticle, "New Test Title", newContent);

		propagateChanges(group);

		// Portlet data is no longer propagated once the group has been created

		for (String languageId : journalArticle.getAvailableLanguageIds()) {
			String localization = LocalizationUtil.getLocalization(
				content, languageId);

			String importedLocalization = LocalizationUtil.getLocalization(
				journalArticle.getContent(), languageId);

			Assert.assertEquals(localization, importedLocalization);
		}
	}

	@Override
	protected void doTestPortletPreferencesPropagation(boolean linkEnabled)
		throws Exception {

		doTestPortletPreferencesPropagation(linkEnabled, false);
	}

	protected int getGroupLayoutCount() throws Exception {
		return LayoutLocalServiceUtil.getLayoutsCount(group, false);
	}

	protected void propagateChanges(Group group) throws Exception {
		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), false);

		SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);

		Thread.sleep(2000);
	}

	protected void setLayoutsUpdateable(boolean layoutsUpdateable)
		throws Exception {

		_layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
				_layoutSetPrototype.getLayoutSetPrototypeId(),
				_layoutSetPrototype.getNameMap(),
				_layoutSetPrototype.getDescriptionMap(),
				_layoutSetPrototype.getActive(), layoutsUpdateable,
				ServiceContextTestUtil.getServiceContext());
	}

	protected Layout setLayoutUpdateable(
			Layout layout, boolean layoutUpdateable)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.put(
			Sites.LAYOUT_UPDATEABLE, String.valueOf(layoutUpdateable));

		layout.setTypeSettingsProperties(typeSettingsProperties);

		return LayoutLocalServiceUtil.updateLayout(layout);
	}

	@Override
	protected void setLinkEnabled(boolean linkEnabled) throws Exception {
		if ((layout != null) && (_layout != null)) {
			layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

			layout.setLayoutPrototypeLinkEnabled(linkEnabled);

			LayoutLocalServiceUtil.updateLayout(layout);

			_layout = LayoutLocalServiceUtil.getLayout(_layout.getPlid());

			_layout.setLayoutPrototypeLinkEnabled(linkEnabled);

			LayoutLocalServiceUtil.updateLayout(_layout);
		}

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, _layoutSetPrototype.getLayoutSetPrototypeId(), 0,
			linkEnabled, linkEnabled);

		Thread.sleep(2000);
	}

	protected void testAddChildLayout(boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		setLinkEnabled(layoutSetPrototypeLinkEnabled);

		try {
			LayoutTestUtil.addLayout(group, layout.getPlid());

			if (layoutSetPrototypeLinkEnabled) {
				Assert.fail(
					"Able to add a child page to a page associated to a site " +
						"template with link enabled");
			}
		}
		catch (LayoutParentLayoutIdException lplie) {
			if (!layoutSetPrototypeLinkEnabled) {
				Assert.fail(
					"Unable to add a child page to a page associated to a " +
						"template with link disabled");
			}
		}
	}

	private int _initialLayoutCount;
	private int _initialPrototypeLayoutCount;
	private Layout _layout;

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	private Group _layoutSetPrototypeGroup;

	@DeleteAfterTestRun
	private JournalArticle _layoutSetPrototypeJournalArticle;

	@DeleteAfterTestRun
	private Layout _layoutSetPrototypeLayout;

	private String _portletId;
	private Layout _prototypeLayout;

}