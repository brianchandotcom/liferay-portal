/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.checker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.checker.FriendlyURLPublicMappingChecker;
import com.liferay.friendly.url.checker.FriendlyURLPublicMappingConflict;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.service.SiteFriendlyURLLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class FriendlyURLPublicMappingCheckerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_defaultGroup = _groupLocalService.fetchGroup(
			TestPropsValues.getCompanyId(),
			PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);
	}

	@Test
	public void testGetConflictsForCrossSite() throws Exception {
		Group group1 = _addGroup();

		_addSiteFriendlyURL(
			group1, _DEFAULT_LOCALE_PATH,
			_language.getLanguageId(LocaleUtil.US));
		_addSiteFriendlyURL(
			group1, _NONDEFAULT_LOCALE_PATH,
			_language.getLanguageId(LocaleUtil.SPAIN));

		_addLayout(_DEFAULT_LOCALE_PATH, _defaultGroup);

		_addLayoutWithFriendlyURLEntry(
			_language.getLanguageId(LocaleUtil.SPAIN), _NONDEFAULT_LOCALE_PATH);

		Group group2 = _addGroup();

		_addSiteFriendlyURL(
			group2, _NONDEFAULT_GROUP_PATH,
			_language.getLanguageId(LocaleUtil.US));

		_addLayout(_NONDEFAULT_GROUP_PATH, group2);

		List<FriendlyURLPublicMappingConflict> conflicts = _getConflicts();

		FriendlyURLPublicMappingConflict defaultLocaleConflict = _findByURL(
			conflicts, _DEFAULT_LOCALE_PATH);

		Assert.assertNotNull(defaultLocaleConflict);
		Assert.assertEquals(
			FriendlyURLPublicMappingConflict.Type.CROSS_SITE,
			defaultLocaleConflict.getType());
		Assert.assertEquals(
			Long.valueOf(group1.getGroupId()),
			defaultLocaleConflict.getConflictingGroupId());

		FriendlyURLPublicMappingConflict nondefaultLocaleConflict = _findByURL(
			conflicts, _NONDEFAULT_LOCALE_PATH);

		Assert.assertNotNull(nondefaultLocaleConflict);
		Assert.assertEquals(
			FriendlyURLPublicMappingConflict.Type.CROSS_SITE,
			nondefaultLocaleConflict.getType());
		Assert.assertEquals(
			Long.valueOf(group1.getGroupId()),
			nondefaultLocaleConflict.getConflictingGroupId());

		Assert.assertNull(
			conflicts.toString(),
			_findByURL(conflicts, _NONDEFAULT_GROUP_PATH));
	}

	@Test
	public void testGetConflictsForReservedKeyword() throws Exception {
		_addSiteFriendlyURL(
			_addGroup(), PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			_language.getLanguageId(LocaleUtil.SPAIN));

		_addLayoutWithFriendlyURLEntry(
			_language.getLanguageId(LocaleUtil.US),
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

		List<FriendlyURLPublicMappingConflict> reservedConflicts =
			ListUtil.filter(
				_getConflicts(),
				conflict -> Objects.equals(
					PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
					conflict.getFriendlyURL()));

		boolean hasGroupConflict = false;
		boolean hasLayoutConflict = false;

		for (FriendlyURLPublicMappingConflict conflict : reservedConflicts) {
			Assert.assertEquals(
				FriendlyURLPublicMappingConflict.Type.RESERVED_KEYWORD,
				conflict.getType());

			String className = conflict.getClassName();

			if (Objects.equals(Group.class.getName(), className)) {
				hasGroupConflict = true;
			}
			else if (Objects.equals(Layout.class.getName(), className)) {
				hasLayoutConflict = true;
			}
		}

		Assert.assertTrue(reservedConflicts.toString(), hasGroupConflict);
		Assert.assertTrue(reservedConflicts.toString(), hasLayoutConflict);
	}

	@Test
	public void testGetConflictsForSelf() throws Exception {
		_addLayoutWithFriendlyURLEntry(
			_language.getLanguageId(LocaleUtil.US),
			_defaultGroup.getFriendlyURL());

		FriendlyURLPublicMappingConflict conflict = _findByURL(
			_getConflicts(), _defaultGroup.getFriendlyURL());

		Assert.assertNotNull(conflict);
		Assert.assertEquals(
			FriendlyURLPublicMappingConflict.Type.SELF, conflict.getType());
		Assert.assertNull(conflict.getConflictingGroupId());
	}

	private Group _addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	private Layout _addLayout(String friendlyURL, Group group)
		throws Exception {

		Layout layout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_CONTENT, false, friendlyURL,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		_layouts.add(layout);

		return layout;
	}

	private void _addLayoutWithFriendlyURLEntry(
			String languageId, String urlTitle)
		throws Exception {

		Layout layout = _addLayout(
			StringPool.SLASH + RandomTestUtil.randomString(), _defaultGroup);

		_friendlyURLEntries.add(
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_defaultGroup.getGroupId(),
				_layoutFriendlyURLEntryHelper.getClassNameId(false),
				layout.getPlid(), RandomTestUtil.randomString(),
				Collections.singletonMap(languageId, urlTitle),
				_getServiceContext()));
	}

	private void _addSiteFriendlyURL(
			Group group, String friendlyURL, String languageId)
		throws Exception {

		_siteFriendlyURLLocalService.addSiteFriendlyURL(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			group.getGroupId(), friendlyURL, languageId, _getServiceContext());
	}

	private FriendlyURLPublicMappingConflict _findByURL(
		List<FriendlyURLPublicMappingConflict> conflicts, String url) {

		for (FriendlyURLPublicMappingConflict conflict : conflicts) {
			if (url.equals(conflict.getFriendlyURL())) {
				return conflict;
			}
		}

		return null;
	}

	private List<FriendlyURLPublicMappingConflict> _getConflicts()
		throws Exception {

		List<FriendlyURLPublicMappingConflict> conflicts =
			_friendlyURLPublicMappingChecker.getConflicts(
				TestPropsValues.getCompanyId());

		Assert.assertNotNull(conflicts);

		return conflicts;
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_defaultGroup.getGroupId(), TestPropsValues.getUserId());
	}

	private static final String _DEFAULT_LOCALE_PATH = "/default-locale";

	private static final String _NONDEFAULT_GROUP_PATH = "/nondefault-group";

	private static final String _NONDEFAULT_LOCALE_PATH = "/nondefault-locale";

	private Group _defaultGroup;

	@DeleteAfterTestRun
	private final List<FriendlyURLEntry> _friendlyURLEntries =
		new ArrayList<>();

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private FriendlyURLPublicMappingChecker _friendlyURLPublicMappingChecker;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private Language _language;

	@Inject
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private final List<Layout> _layouts = new ArrayList<>();

	@Inject
	private SiteFriendlyURLLocalService _siteFriendlyURLLocalService;

}