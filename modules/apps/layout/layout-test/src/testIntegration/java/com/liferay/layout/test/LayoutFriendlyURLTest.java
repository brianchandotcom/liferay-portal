/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class LayoutFriendlyURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groups.add(_group);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		addLayout(group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		addLayout(group.getGroupId(), true, friendlyURLMap);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "/casa"
			).put(
				LocaleUtil.US, "/home"
			).build());
	}

	@Test
	public void testFriendlyURLWithSpecialCharacter() throws Exception {
		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/Football⚽"
			).build());

		String friendlyURL = FriendlyURLNormalizerUtil.normalizeWithEncoding(
			"/Football⚽");

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			_group.getGroupId(), false, friendlyURL);

		Assert.assertNotNull(layout);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLLanguageId() throws Exception {
		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/es"
			).build());
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLLanguageIdAndCountryId()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/es_ES"
			).build());
	}

	@Test
	public void testInvalidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/tags"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"tags", layoutFriendlyURLException.getKeywordConflict());
		}

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/home/tags"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"tags", layoutFriendlyURLException.getKeywordConflict());
		}

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/tags/home"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"tags", layoutFriendlyURLException.getKeywordConflict());
		}

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/blogs/-/home"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"/-/", layoutFriendlyURLException.getKeywordConflict());
		}
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLMapperURLInNondefaultLocale()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "/tags/two"
			).put(
				LocaleUtil.US, "/two"
			).build());
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLanguageId()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/es/home"
			).build());
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLanguageIdAndCountryId()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/es_ES/home"
			).build());
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLowerCaseLanguageIdAndCountryId()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/es_es/home"
			).build());
	}

	@Test
	public void testMissingDefaultLocaleOnGroupDefaultLocaleChange()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		Layout layout = addLayout(group.getGroupId(), false, friendlyURLMap);

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(
			"inheritLocales", Boolean.toString(false));
		typeSettingsUnicodeProperties.setProperty(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.GERMANY));
		typeSettingsUnicodeProperties.setProperty(
			PropsKeys.LOCALES,
			StringUtil.merge(
				LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales())));

		Locale originalDefaultLocale = LocaleThreadLocal.getDefaultLocale();

		try {
			LocaleThreadLocal.setDefaultLocale(LocaleUtil.GERMANY);

			GroupLocalServiceUtil.updateGroup(
				group.getGroupId(), typeSettingsUnicodeProperties.toString());
		}
		finally {
			LocaleThreadLocal.setDefaultLocale(originalDefaultLocale);
		}

		friendlyURLMap = layout.getFriendlyURLMap();

		Assert.assertNull(friendlyURLMap.get(LocaleUtil.GERMANY));
	}

	@Test
	public void testMultipleInvalidFriendlyURLMapperURL() throws Exception {
		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/tags/dos"
		).put(
			LocaleUtil.US, "/tags/two"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 2,
				layoutFriendlyURLExceptions.size());

			for (Exception exception : layoutFriendlyURLExceptions) {
				LayoutFriendlyURLException layoutFriendlyURLException =
					(LayoutFriendlyURLException)exception;

				Assert.assertEquals(
					"tags", layoutFriendlyURLException.getKeywordConflict());
			}
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/home"
		).put(
			LocaleUtil.US, "/welcome"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			if (_log.isDebugEnabled()) {
				_log.debug(layoutFriendlyURLsException);
			}
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocalePrivateLayout()
		throws Exception {

		addLayout(
			_group.getGroupId(), true,
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "/home"
			).put(
				LocaleUtil.US, "/home"
			).build());
	}

	@Test
	public void testSameFriendlyURLDifferentLocalePublicLayout()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "/home"
			).put(
				LocaleUtil.US, "/home"
			).build());
	}

	@Test
	public void testSameFriendlyURLSameLocaleDifferentLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/house"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			if (_log.isDebugEnabled()) {
				_log.debug(layoutFriendlyURLsException);
			}
		}
	}

	@Test
	public void testValidFriendlyURLEndingWithLanguageId() throws Exception {
		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/home/es"
			).build());
	}

	@Test
	public void testValidFriendlyURLEndingWithLanguageIdAndCountryId()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/home/es_ES"
			).build());
	}

	@Test
	public void testValidFriendlyURLEndingWithLowerCaseLanguageIdAndCountryId()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/home/es_es"
			).build());
	}

	@Test
	public void testValidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/blogs"
			).build());

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/home/blogs"
			).build());

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/blogs/home"
			).build());
	}

	@Test
	public void testValidFriendlyURLMapperURLInNondefaultLocale()
		throws Exception {

		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "/blogs/two"
			).put(
				LocaleUtil.US, "/two"
			).build());
	}

	@Test
	public void testValidFriendlyURLStartingWithLanguageId() throws Exception {
		addLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.US, "/eshome"
			).build());
	}

	protected Layout addLayout(
			long groupId, boolean privateLayout,
			Map<Locale, String> friendlyURLMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
			friendlyURLMap, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutFriendlyURLTest.class);

	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}