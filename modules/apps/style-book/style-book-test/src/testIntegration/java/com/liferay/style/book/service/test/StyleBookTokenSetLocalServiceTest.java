/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.exception.DuplicateStyleBookTokenSetNameException;
import com.liferay.style.book.exception.NoSuchFrontendTokenCategoryException;
import com.liferay.style.book.exception.StyleBookTokenSetNameException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.model.StyleBookTokenSet;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.service.StyleBookTokenSetLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Lima
 */
@RunWith(Arquillian.class)
public class StyleBookTokenSetLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		_styleBookEntry = _styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), false, null, RandomTestUtil.randomString(),
			null, RandomTestUtil.randomString(), _serviceContext);
	}

	@Test
	public void testAddStyleBookTokenSet() throws Exception {
		String description = RandomTestUtil.randomString();
		String externalReferenceCode = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();

		StyleBookTokenSet styleBookTokenSet =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				externalReferenceCode, description,
				_FRONTEND_TOKEN_CATEGORY_NAME, name,
				_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
				_serviceContext);

		Assert.assertEquals(description, styleBookTokenSet.getDescription());
		Assert.assertEquals(
			externalReferenceCode,
			styleBookTokenSet.getExternalReferenceCode());
		Assert.assertEquals(
			_FRONTEND_TOKEN_CATEGORY_NAME,
			styleBookTokenSet.getFrontendTokenCategoryName());
		Assert.assertEquals(
			_group.getGroupId(), styleBookTokenSet.getGroupId());
		Assert.assertEquals(name, styleBookTokenSet.getName());
		Assert.assertEquals(
			_styleBookEntry.getStyleBookEntryId(),
			styleBookTokenSet.getStyleBookEntryId());
		Assert.assertEquals(_THEME_ID_CLASSIC, styleBookTokenSet.getThemeId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), styleBookTokenSet.getUserId());
	}

	@Test(expected = NoSuchFrontendTokenCategoryException.class)
	public void testAddStyleBookTokenSetWhenFrontendTokenCategoryDoesNotExist()
		throws Exception {

		_styleBookTokenSetLocalService.addStyleBookTokenSet(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
			_serviceContext);
	}

	@Test
	public void testAddStyleBookTokenSetWhenFrontendTokenCategoryNameIsDifferent()
		throws Exception {

		String name = RandomTestUtil.randomString();

		StyleBookTokenSet styleBookTokenSet1 =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_FRONTEND_TOKEN_CATEGORY_NAME, name,
				_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
				_serviceContext);

		StyleBookTokenSet styleBookTokenSet2 =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"spacing", name, _styleBookEntry.getStyleBookEntryId(),
				_THEME_ID_CLASSIC, _serviceContext);

		Assert.assertNotEquals(
			styleBookTokenSet1.getStyleBookTokenSetId(),
			styleBookTokenSet2.getStyleBookTokenSetId());
	}

	@Test(expected = DuplicateStyleBookTokenSetNameException.class)
	public void testAddStyleBookTokenSetWhenNameIsBaseFrontendTokenSetName()
		throws Exception {

		_styleBookTokenSetLocalService.addStyleBookTokenSet(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_FRONTEND_TOKEN_CATEGORY_NAME, "brandColors",
			_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
			_serviceContext);
	}

	@Test(expected = DuplicateStyleBookTokenSetNameException.class)
	public void testAddStyleBookTokenSetWhenNameIsDuplicate() throws Exception {
		String name = RandomTestUtil.randomString();

		_styleBookTokenSetLocalService.addStyleBookTokenSet(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_FRONTEND_TOKEN_CATEGORY_NAME, name,
			_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
			_serviceContext);

		_styleBookTokenSetLocalService.addStyleBookTokenSet(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_FRONTEND_TOKEN_CATEGORY_NAME, name,
			_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
			_serviceContext);
	}

	@Test
	public void testAddStyleBookTokenSetWhenNameIsInvalid() throws Exception {
		Assert.assertThrows(
			StyleBookTokenSetNameException.class,
			() -> _styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_styleBookEntry.getStyleBookEntryId(),
				RandomTestUtil.randomString(), _serviceContext));
		Assert.assertThrows(
			StyleBookTokenSetNameException.class,
			() -> _styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				_styleBookEntry.getStyleBookEntryId(),
				RandomTestUtil.randomString(), _serviceContext));
		Assert.assertThrows(
			StyleBookTokenSetNameException.class,
			() -> _styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(76),
				_styleBookEntry.getStyleBookEntryId(),
				RandomTestUtil.randomString(), _serviceContext));
	}

	@Test
	public void testAddStyleBookTokenSetWhenStyleBookEntryIdIsDifferent()
		throws Exception {

		String name = RandomTestUtil.randomString();

		StyleBookTokenSet styleBookTokenSet1 =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_FRONTEND_TOKEN_CATEGORY_NAME, name,
				_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
				_serviceContext);

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, RandomTestUtil.randomString(), _serviceContext);

		StyleBookTokenSet styleBookTokenSet2 =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_FRONTEND_TOKEN_CATEGORY_NAME, name,
				styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
				_serviceContext);

		Assert.assertNotEquals(
			styleBookTokenSet1.getStyleBookTokenSetId(),
			styleBookTokenSet2.getStyleBookTokenSetId());
	}

	@Test
	public void testAddStyleBookTokenSetWhenThemeIdIsDifferent()
		throws Exception {

		String name = RandomTestUtil.randomString();

		StyleBookTokenSet styleBookTokenSet1 =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_FRONTEND_TOKEN_CATEGORY_NAME, name,
				_styleBookEntry.getStyleBookEntryId(), _THEME_ID_CLASSIC,
				_serviceContext);

		StyleBookTokenSet styleBookTokenSet2 =
			_styleBookTokenSetLocalService.addStyleBookTokenSet(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_FRONTEND_TOKEN_CATEGORY_NAME, name,
				_styleBookEntry.getStyleBookEntryId(), "cms_WAR_cmstheme",
				_serviceContext);

		Assert.assertNotEquals(
			styleBookTokenSet1.getStyleBookTokenSetId(),
			styleBookTokenSet2.getStyleBookTokenSetId());
	}

	private static final String _FRONTEND_TOKEN_CATEGORY_NAME = "colorSystem";

	private static final String _THEME_ID_CLASSIC = "classic_WAR_classictheme";

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;
	private StyleBookEntry _styleBookEntry;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private StyleBookTokenSetLocalService _styleBookTokenSetLocalService;

}