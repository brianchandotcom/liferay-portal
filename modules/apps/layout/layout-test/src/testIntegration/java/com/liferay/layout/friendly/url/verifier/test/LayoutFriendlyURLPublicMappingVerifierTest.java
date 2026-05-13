/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.friendly.url.verifier.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingConflict;
import com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingVerifier;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.service.SiteFriendlyURLLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class LayoutFriendlyURLPublicMappingVerifierTest {

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
	public void testGetConflicts() throws Exception {
		Assert.assertEquals(
			List.of(),
			_layoutFriendlyURLPublicMappingVerifier.getConflicts(
				TestPropsValues.getCompanyId()));

		Group siteGroup = GroupTestUtil.addGroup();

		_groups.add(siteGroup);

		_siteFriendlyURLLocalService.addSiteFriendlyURL(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			siteGroup.getGroupId(), _CONFLICT_DEFAULT_PATH,
			_language.getLanguageId(LocaleUtil.US), _getServiceContext());
		_siteFriendlyURLLocalService.addSiteFriendlyURL(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			siteGroup.getGroupId(), _CONFLICT_LOCALE_PATH,
			_language.getLanguageId(LocaleUtil.SPAIN), _getServiceContext());

		_layouts.add(_addLayout(_defaultGroup, _CONFLICT_DEFAULT_PATH));

		Layout localizedConflictLayout = _addLayout(
			_defaultGroup, StringPool.SLASH + RandomTestUtil.randomString());

		_layouts.add(localizedConflictLayout);

		_friendlyURLEntries.add(
			_addLayoutFriendlyURLEntry(
				_defaultGroup, localizedConflictLayout,
				_language.getLanguageId(LocaleUtil.SPAIN),
				_CONFLICT_LOCALE_PATH));

		String defaultSiteFriendlyURL = _defaultGroup.getFriendlyURL();

		Layout selfConflictLayout = _addLayout(
			_defaultGroup, StringPool.SLASH + RandomTestUtil.randomString());

		_layouts.add(selfConflictLayout);

		_friendlyURLEntries.add(
			_addLayoutFriendlyURLEntry(
				_defaultGroup, selfConflictLayout,
				_language.getLanguageId(LocaleUtil.US),
				defaultSiteFriendlyURL));

		String reservedKeyword = _findUsableReservedKeyword();

		if (reservedKeyword != null) {
			Layout reservedPathLayout = _addLayout(
				_defaultGroup,
				StringPool.SLASH + RandomTestUtil.randomString());

			_layouts.add(reservedPathLayout);

			_friendlyURLEntries.add(
				_addLayoutFriendlyURLEntry(
					_defaultGroup, reservedPathLayout,
					_language.getLanguageId(LocaleUtil.US),
					StringPool.SLASH + reservedKeyword));
		}

		Group nondefaultSiteGroup = GroupTestUtil.addGroup();
		Group nondefaultPageGroup = GroupTestUtil.addGroup();

		_groups.add(nondefaultSiteGroup);
		_groups.add(nondefaultPageGroup);

		_siteFriendlyURLLocalService.addSiteFriendlyURL(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nondefaultSiteGroup.getGroupId(), _NONDEFAULT_CONFLICT_PATH,
			_language.getLanguageId(LocaleUtil.US), _getServiceContext());

		_addLayout(nondefaultPageGroup, _NONDEFAULT_CONFLICT_PATH);

		List<LayoutFriendlyURLPublicMappingConflict> conflicts =
			_layoutFriendlyURLPublicMappingVerifier.getConflicts(
				TestPropsValues.getCompanyId());

		LayoutFriendlyURLPublicMappingConflict crossSiteDefaultConflict =
			_findByPageURL(conflicts, _CONFLICT_DEFAULT_PATH);

		Assert.assertNotNull(conflicts.toString(), crossSiteDefaultConflict);
		Assert.assertEquals(
			LayoutFriendlyURLPublicMappingConflict.Type.CROSS_SITE,
			crossSiteDefaultConflict.getType());
		Assert.assertEquals(
			Long.valueOf(siteGroup.getGroupId()),
			crossSiteDefaultConflict.getConflictingGroupId());

		LayoutFriendlyURLPublicMappingConflict crossSiteLocaleConflict =
			_findByPageURL(conflicts, _CONFLICT_LOCALE_PATH);

		Assert.assertNotNull(conflicts.toString(), crossSiteLocaleConflict);
		Assert.assertEquals(
			LayoutFriendlyURLPublicMappingConflict.Type.CROSS_SITE,
			crossSiteLocaleConflict.getType());
		Assert.assertEquals(
			Long.valueOf(siteGroup.getGroupId()),
			crossSiteLocaleConflict.getConflictingGroupId());

		LayoutFriendlyURLPublicMappingConflict selfConflict = _findByPageURL(
			conflicts, defaultSiteFriendlyURL);

		Assert.assertNotNull(conflicts.toString(), selfConflict);
		Assert.assertEquals(
			LayoutFriendlyURLPublicMappingConflict.Type.SELF,
			selfConflict.getType());
		Assert.assertNull(selfConflict.getConflictingGroupId());

		if (reservedKeyword != null) {
			LayoutFriendlyURLPublicMappingConflict reservedConflict =
				_findByPageURL(conflicts, StringPool.SLASH + reservedKeyword);

			Assert.assertNotNull(conflicts.toString(), reservedConflict);
			Assert.assertEquals(
				LayoutFriendlyURLPublicMappingConflict.Type.RESERVED_KEYWORD,
				reservedConflict.getType());
		}

		Assert.assertNull(
			conflicts.toString(),
			_findByPageURL(conflicts, _NONDEFAULT_CONFLICT_PATH));
	}

	private Layout _addLayout(Group group, String friendlyURL)
		throws Exception {

		return _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_CONTENT, false, friendlyURL,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private FriendlyURLEntry _addLayoutFriendlyURLEntry(
			Group group, Layout layout, String languageId, String urlTitle)
		throws Exception {

		return _friendlyURLEntryLocalService.addFriendlyURLEntry(
			group.getGroupId(),
			_layoutFriendlyURLEntryHelper.getClassNameId(false),
			layout.getPlid(), RandomTestUtil.randomString(),
			Collections.singletonMap(languageId, urlTitle),
			_getServiceContext());
	}

	private LayoutFriendlyURLPublicMappingConflict _findByPageURL(
		List<LayoutFriendlyURLPublicMappingConflict> conflicts,
		String pageURL) {

		for (LayoutFriendlyURLPublicMappingConflict conflict : conflicts) {
			if (pageURL.equals(conflict.getPageURL())) {
				return conflict;
			}
		}

		return null;
	}

	private String _findUsableReservedKeyword() {
		for (String keyword : PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS) {
			if (!keyword.contains(StringPool.SLASH)) {
				return keyword;
			}
		}

		return null;
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_defaultGroup.getGroupId(), TestPropsValues.getUserId());
	}

	private static final String _CONFLICT_DEFAULT_PATH = "/conflict-default";

	private static final String _CONFLICT_LOCALE_PATH = "/conflict-locale";

	private static final String _NONDEFAULT_CONFLICT_PATH =
		"/non-default-conflict";

	private Group _defaultGroup;

	@DeleteAfterTestRun
	private final List<FriendlyURLEntry> _friendlyURLEntries =
		new ArrayList<>();

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private Language _language;

	@Inject
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Inject
	private LayoutFriendlyURLPublicMappingVerifier
		_layoutFriendlyURLPublicMappingVerifier;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private final List<Layout> _layouts = new ArrayList<>();

	@Inject
	private SiteFriendlyURLLocalService _siteFriendlyURLLocalService;

}