/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.taglib.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.taglib.servlet.taglib.InputTag;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class InputTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_locale = _portal.getSiteDefaultLocale(_group);

		_layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				_locale, RandomTestUtil.randomString()
			).put(
				LocaleUtil.SPAIN, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				_locale,
				_friendlyURLNormalizer.normalizeWithEncoding(
					StringPool.SLASH + RandomTestUtil.randomString())
			).put(
				LocaleUtil.SPAIN,
				_friendlyURLNormalizer.normalizeWithEncoding(
					StringPool.SLASH + RandomTestUtil.randomString())
			).build());
	}

	@Test
	public void testInputTag() throws Exception {
		_assertInputTag(_layout.getFriendlyURLMap());
	}

	@Test
	public void testInputTagLocaleRemovedFromCompanySettings()
		throws Exception {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			_group.getCompanyId());

		String originalLanguageIds = portletPreferences.getValue(
			PropsKeys.LOCALES, StringPool.BLANK);

		try {
			Map<Locale, String> friendlyURLMap = _layout.getFriendlyURLMap();

			Assert.assertNotNull(friendlyURLMap.remove(LocaleUtil.SPAIN));

			String languageId = LocaleUtil.toLanguageId(LocaleUtil.SPAIN);

			Assert.assertTrue(
				StringUtil.contains(
					originalLanguageIds, languageId, StringPool.BLANK));

			_companyLocalService.updatePreferences(
				_group.getCompanyId(),
				UnicodePropertiesBuilder.put(
					PropsKeys.LOCALES,
					com.liferay.petra.string.StringUtil.merge(
						ArrayUtil.remove(
							StringUtil.split(
								originalLanguageIds, StringPool.COMMA),
							languageId),
						StringPool.COMMA)
				).build());

			portletPreferences = PrefsPropsUtil.getPreferences(
				_group.getCompanyId());

			Assert.assertFalse(
				StringUtil.contains(
					portletPreferences.getValue(PropsKeys.LOCALES, languageId),
					languageId, StringPool.BLANK));

			_assertInputTag(friendlyURLMap);
		}
		finally {
			_companyLocalService.updatePreferences(
				_group.getCompanyId(),
				UnicodePropertiesBuilder.put(
					PropsKeys.LOCALES, originalLanguageIds
				).build());
		}
	}

	private void _assertInputTag(Map<Locale, String> expectedFriendlyURLMap)
		throws Exception {

		InputTag inputTag = new InputTag();

		inputTag.setClassName(Layout.class.getName());

		inputTag.setClassPK(_layout.getPlid());

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(_layout.getCompanyId()), _group,
				_layout);

		inputTag.doTag(mockHttpServletRequest, new MockHttpServletResponse());

		Map<Locale, String> friendlyURLMap = _localization.getLocalizationMap(
			(String)mockHttpServletRequest.getAttribute(
				"liferay-friendly-url:input:value"));

		Assert.assertEquals(
			friendlyURLMap.toString(), expectedFriendlyURLMap.size(),
			friendlyURLMap.size());

		for (Map.Entry<Locale, String> entry :
				expectedFriendlyURLMap.entrySet()) {

			Assert.assertTrue(friendlyURLMap.containsKey(entry.getKey()));

			Assert.assertEquals(
				entry.getValue(), friendlyURLMap.get(entry.getKey()));
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private Locale _locale;

	@Inject
	private Localization _localization;

	@Inject
	private Portal _portal;

}