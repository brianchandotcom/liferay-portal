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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalContent;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.LayoutParentLayoutIdException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.impl.ThemeSettingImpl;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypePropagationTest
	extends BasePrototypePropagationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
		Assert.assertEquals(_initialPrototypeLayoutsCount, _initialLayoutCount);
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
	public void testLayoutDeleteAndReadWithSameFriendlyURL() throws Exception {
		setLinkEnabled(true);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup.getGroupId(), "test", true);

		String friendlyURL = layout.getFriendlyURL();

		Assert.assertEquals(
			_initialPrototypeLayoutsCount, getGroupLayoutCount());

		propagateChanges(group);

		Assert.assertEquals(
			_initialPrototypeLayoutsCount + 1, getGroupLayoutCount());

		LayoutLocalServiceUtil.deleteLayout(
			layout, ServiceContextTestUtil.getServiceContext());

		Layout newLayout = LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup.getGroupId(), "test", true);

		Assert.assertEquals(friendlyURL, newLayout.getFriendlyURL());

		Assert.assertEquals(
			_initialPrototypeLayoutsCount + 1, getGroupLayoutCount());

		propagateChanges(group);

		Assert.assertEquals(
			_initialPrototypeLayoutsCount + 1, getGroupLayoutCount());

		Layout propagatedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				newLayout.getUuid(), group.getGroupId(), false);

		Assert.assertNotNull(
			"Deleted and readded layout could not be found on propagated site",
			propagatedLayout);

		Assert.assertEquals(
			"Friendly URLs of the source and target layouts should match",
			friendlyURL, propagatedLayout.getFriendlyURL());
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

		LayoutTestUtil.addTypePortletLayout(_layoutSetPrototypeGroup, true);

		Assert.assertEquals(
			_initialPrototypeLayoutsCount, getGroupLayoutCount());

		LayoutServiceUtil.getLayouts(
			group.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			false, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Thread.sleep(2000);

		Assert.assertEquals(
			_initialPrototypeLayoutsCount + 1, getGroupLayoutCount());
	}

	@Test
	public void testLayoutPropagationWithFriendlyURLConflict()
		throws Exception {

		LayoutSet layoutSet = group.getPublicLayoutSet();

		List<Layout> initialMergeFailFriendlyURLLayouts =
			SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);

		setLinkEnabled(true);

		LayoutTestUtil.addTypePortletLayout(group.getGroupId(), "test", false);
		LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup.getGroupId(), "test", true);

		propagateChanges(group);

		layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			layoutSet.getLayoutSetId());

		List<Layout> mergeFailFriendlyURLLayouts =
			SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);

		Assert.assertEquals(
			mergeFailFriendlyURLLayouts.toString(),
			initialMergeFailFriendlyURLLayouts.size() + 1,
			mergeFailFriendlyURLLayouts.size());
	}

	@Test
	public void testLayoutPropagationWithFriendlyURLConflictResolvedByDelete()
		throws Exception {

		LayoutSet layoutSet = group.getPublicLayoutSet();

		List<Layout> initialMergeFailFriendlyURLLayouts =
			SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);

		setLinkEnabled(true);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			group.getGroupId(), "test", false);

		LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup.getGroupId(), "test", true);

		propagateChanges(group);

		LayoutLocalServiceUtil.deleteLayout(layout);

		propagateChanges(group);

		layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			layoutSet.getLayoutSetId());

		List<Layout> mergeFailFriendlyURLLayouts =
			SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);

		Assert.assertEquals(
			mergeFailFriendlyURLLayouts.toString(),
			initialMergeFailFriendlyURLLayouts.size(),
			mergeFailFriendlyURLLayouts.size());
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
			TestPropsValues.getCompanyId(),
			JournalContentPortletKeys.JOURNAL_CONTENT);

		boolean preferencesUniquePerLayout =
			portlet.getPreferencesUniquePerLayout();

		try {
			portlet.setPreferencesUniquePerLayout(false);

			_layoutSetPrototypeLayout = LayoutTestUtil.addTypePortletLayout(
				_layoutSetPrototypeGroup, true, layoutPrototype, true);

			Map<String, String[]> preferenceMap = HashMapBuilder.put(
				"bulletStyle", new String[] {"Dots"}
			).build();

			String testPortletId1 = LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), _layoutSetPrototypeLayout,
				JournalContentPortletKeys.JOURNAL_CONTENT, "column-1",
				preferenceMap);

			preferenceMap.put("bulletStyle", new String[] {"Arrows"});

			String testPortletId2 = LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), _layoutSetPrototypeLayout,
				JournalContentPortletKeys.JOURNAL_CONTENT, "column-2",
				preferenceMap);

			propagateChanges(group);

			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				group.getGroupId(), false,
				_layoutSetPrototypeLayout.getFriendlyURL());

			PortletPreferences testPortletIdPortletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					group.getGroupId(), layout,
					JournalContentPortletKeys.JOURNAL_CONTENT, null);

			Assert.assertEquals(
				"Arrows",
				testPortletIdPortletPreferences.getValue(
					"bulletStyle", StringPool.BLANK));

			PortletPreferences testPortletId1PortletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, testPortletId1, null);

			Assert.assertEquals(
				"Arrows",
				testPortletId1PortletPreferences.getValue(
					"bulletStyle", StringPool.BLANK));

			PortletPreferences testPortletId2PortletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, testPortletId2, null);

			Assert.assertEquals(
				"Arrows",
				testPortletId2PortletPreferences.getValue(
					"bulletStyle", StringPool.BLANK));
		}
		finally {
			portlet.setPreferencesUniquePerLayout(preferencesUniquePerLayout);
		}
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

	@Test
	public void testResetPrototypeWithoutPermissions() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user1));

		Group userGroup = GroupLocalServiceUtil.getUserGroup(
			_user2.getCompanyId(), _user2.getUserId());

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			userGroup.getGroupId(), true);

		try {
			SitesUtil.resetPrototype(layoutSet);

			Assert.fail(
				"The user should not be able to reset another user's " +
					"dashboard");
		}
		catch (PrincipalException principalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(principalException);
			}
		}
	}

	@Test
	public void testResetPrototypeWithPermissions() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addUserRole(_user1.getUserId(), role);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_user1.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user1.getCompanyId()), role.getRoleId(),
			ActionKeys.UPDATE);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user1));

		Group userGroup = GroupLocalServiceUtil.getUserGroup(
			_user2.getCompanyId(), _user2.getUserId());

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			userGroup.getGroupId(), true);

		SitesUtil.resetPrototype(layoutSet);
	}

	@Test
	public void testResetUserPrototypeWithoutPermissions() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user1));

		Group userGroup = GroupLocalServiceUtil.getUserGroup(
			_user1.getCompanyId(), _user1.getUserId());

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			userGroup.getGroupId(), true);

		SitesUtil.resetPrototype(layoutSet);
	}

	@Test
	public void testThemeSettingsWithLinkEnabled() throws Exception {
		LayoutSet prototypeLayoutSet =
			_layoutSetPrototypeGroup.getPrivateLayoutSet();

		Theme prototypeTheme = prototypeLayoutSet.getTheme();

		prototypeTheme.addSetting("test", "true", true, null, null, null);

		Map<String, ThemeSetting> prototypeThemeSettings =
			prototypeTheme.getConfigurableSettings();

		UnicodeProperties settingsUnicodeProperties =
			prototypeLayoutSet.getSettingsProperties();

		String device = "regular";

		for (String propertyKey : prototypeThemeSettings.keySet()) {
			settingsUnicodeProperties.setProperty(
				ThemeSettingImpl.namespaceProperty(device, propertyKey),
				RandomTestUtil.randomString());
		}

		prototypeLayoutSet.setSettingsProperties(settingsUnicodeProperties);

		prototypeLayoutSet = LayoutSetLocalServiceUtil.updateLayoutSet(
			prototypeLayoutSet);

		setLinkEnabled(true);

		_layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
				_layoutSetPrototype.getLayoutSetPrototypeId());

		_layoutSetPrototype.setModifiedDate(new Date());

		_layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
				_layoutSetPrototype);

		propagateChanges(group);

		layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			group.getGroupId(), false, prototypeLayout.getFriendlyURL());

		_layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			group.getGroupId(), false, _prototypeLayout.getFriendlyURL());

		LayoutSet targetLayoutSet = layout.getLayoutSet();

		for (String propertyKey : prototypeThemeSettings.keySet()) {
			String prototypeValue = prototypeLayoutSet.getThemeSetting(
				propertyKey, device);
			String targetValue = targetLayoutSet.getThemeSetting(
				propertyKey, device);

			Assert.assertEquals(
				propertyKey + "=" + prototypeValue,
				propertyKey + "=" + targetValue);
		}
	}

	@Override
	protected void doSetUp() throws Exception {

		// Layout set prototype

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		_layoutSetPrototypeGroup = _layoutSetPrototype.getGroup();

		prototypeLayout = LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup, true);

		LayoutTestUtil.updateLayoutTemplateId(
			prototypeLayout, initialLayoutTemplateId);

		_layoutSetPrototypeJournalArticle = JournalTestUtil.addArticle(
			_layoutSetPrototypeGroup.getGroupId(), "Test Article",
			"Test Content");

		portletId = addPortletToLayout(
			TestPropsValues.getUserId(), prototypeLayout,
			_layoutSetPrototypeJournalArticle, "column-1");

		_prototypeLayout = LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup, true);

		LayoutTestUtil.updateLayoutTemplateId(
			_prototypeLayout, initialLayoutTemplateId);

		_portletId = addPortletToLayout(
			TestPropsValues.getUserId(), _prototypeLayout,
			_layoutSetPrototypeJournalArticle, "column-1");

		_initialPrototypeLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
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

		// Users

		_user1 = UserTestUtil.addUser();
		_user2 = UserTestUtil.addUser();
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

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototypeGroup, true);

		Assert.assertEquals(
			_initialPrototypeLayoutsCount, getGroupLayoutCount());

		propagateChanges(group);

		if (linkEnabled) {
			Assert.assertEquals(
				_initialPrototypeLayoutsCount + 1, getGroupLayoutCount());
		}
		else {
			Assert.assertEquals(
				_initialPrototypeLayoutsCount, getGroupLayoutCount());
		}

		LayoutLocalServiceUtil.deleteLayout(
			layout, ServiceContextTestUtil.getServiceContext());

		if (linkEnabled) {
			Assert.assertEquals(
				_initialPrototypeLayoutsCount + 1, getGroupLayoutCount());
		}
		else {
			Assert.assertEquals(
				_initialPrototypeLayoutsCount, getGroupLayoutCount());
		}

		propagateChanges(group);

		Assert.assertEquals(
			_initialPrototypeLayoutsCount, getGroupLayoutCount());
	}

	protected void doTestLayoutPropagationWithLayoutPrototype(
			boolean layoutSetLayoutLinkEnabled)
		throws Exception {

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		_layoutSetPrototypeLayout = LayoutTestUtil.addTypePortletLayout(
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

		Map<String, String> content = new HashMap<>();

		for (String languageId : journalArticle.getAvailableLanguageIds()) {
			String localization = _journalContent.getContent(
				_layoutSetPrototypeJournalArticle.getGroupId(),
				_layoutSetPrototypeJournalArticle.getArticleId(),
				Constants.VIEW, languageId);

			String importedLocalization = _journalContent.getContent(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				Constants.VIEW, languageId);

			Assert.assertEquals(localization, importedLocalization);

			content.put(languageId, localization);
		}

		String newContent = DDMStructureTestUtil.getSampleStructuredContent(
			"New Test Content");

		JournalTestUtil.updateArticle(
			_layoutSetPrototypeJournalArticle, "New Test Title", newContent);

		propagateChanges(group);

		// Portlet data is no longer propagated once the group has been created

		for (String languageId : journalArticle.getAvailableLanguageIds()) {
			String localization = content.get(languageId);

			String importedLocalization = _journalContent.getContent(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				Constants.VIEW, languageId);

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
				_layoutSetPrototype.isActive(), layoutsUpdateable,
				ServiceContextTestUtil.getServiceContext());
	}

	protected Layout setLayoutUpdateable(
			Layout layout, boolean layoutUpdateable)
		throws Exception {

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put(
			Sites.LAYOUT_UPDATEABLE, String.valueOf(layoutUpdateable));

		layout.setTypeSettingsProperties(typeSettingsUnicodeProperties);

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
			LayoutTestUtil.addTypePortletLayout(group, layout.getPlid());

			Assert.assertFalse(
				"Able to add a child page to a page associated to a site " +
					"template with link enabled",
				layoutSetPrototypeLinkEnabled);
		}
		catch (LayoutParentLayoutIdException layoutParentLayoutIdException) {
			if (_log.isDebugEnabled()) {
				_log.debug(layoutParentLayoutIdException);
			}

			Assert.assertTrue(
				"Unable to add a child page to a page associated to a " +
					"template with link disabled",
				layoutSetPrototypeLinkEnabled);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypePropagationTest.class);

	private int _initialLayoutCount;
	private int _initialPrototypeLayoutsCount;

	@Inject
	private JournalContent _journalContent;

	private Layout _layout;

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	private Group _layoutSetPrototypeGroup;
	private JournalArticle _layoutSetPrototypeJournalArticle;

	@DeleteAfterTestRun
	private Layout _layoutSetPrototypeLayout;

	private String _portletId;
	private Layout _prototypeLayout;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

}