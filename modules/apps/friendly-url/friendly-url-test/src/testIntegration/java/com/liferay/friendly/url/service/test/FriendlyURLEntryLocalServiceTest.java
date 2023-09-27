/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class FriendlyURLEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class));
		_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class));
	}

	@Test
	public void testAddFriendlyURLEntryKeepsOldLocalizedValues()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.US), "url-title-en"),
			_getServiceContext());

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.CHINA), "url-title-zh"),
			_getServiceContext());

		FriendlyURLEntry finalFriendlyURL =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				"url-title-en", _getServiceContext());

		Assert.assertEquals(
			"url-title-en",
			finalFriendlyURL.getUrlTitle(
				_language.getLanguageId(LocaleUtil.US)));
		Assert.assertEquals(
			"url-title-zh",
			finalFriendlyURL.getUrlTitle(
				_language.getLanguageId(LocaleUtil.CHINA)));
	}

	@Test
	public void testAddFriendlyURLEntryReusesOwnedUrlTitles() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			"existing-url-title-2", _getServiceContext());

		FriendlyURLEntry finalFriendlyURL =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				urlTitle, _getServiceContext());

		Assert.assertEquals(urlTitle, finalFriendlyURL.getUrlTitle());
	}

	@Test
	public void testDeleteFriendlyURLLocalizationEntry() throws Exception {
		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(),
				HashMapBuilder.put(
					_language.getLanguageId(LocaleUtil.CHINA), "url-title-zh"
				).put(
					_language.getLanguageId(LocaleUtil.US), "url-title-en"
				).build(),
				_getServiceContext());

		_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
			friendlyURLEntry.getFriendlyURLEntryId(),
			_language.getLanguageId(LocaleUtil.CHINA));

		Assert.assertNotNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId()));

		_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
			friendlyURLEntry.getFriendlyURLEntryId(),
			_language.getLanguageId(LocaleUtil.US));

		Assert.assertNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId()));
	}

	@Test
	public void testGetUniqueUrlTitleNormalizesUrlTitle() throws Exception {
		String urlTitle = "url title with spaces";

		Assert.assertEquals(
			"url-title-with-spaces",
			_friendlyURLEntryLocalService.getUniqueUrlTitle(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(), urlTitle, null));
	}

	@Test
	public void testGetUniqueUrlTitleResolvesConflicts() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		Assert.assertEquals(
			"existing-url-title-1",
			_friendlyURLEntryLocalService.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, _user.getUserId(), urlTitle,
				null));
	}

	@Test
	public void testGetUniqueUrlTitleReusesOwnedUrlTitles() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, null);

		Assert.assertEquals(urlTitle, uniqueUrlTitle);
	}

	@Test
	public void testGetUniqueUrlTitleShortensToMaxLength() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength + 1);

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, null);

		Assert.assertEquals(maxLength, uniqueUrlTitle.length());
	}

	@Test
	public void testGetUniqueUrlTitleWithNonasciiCharsShortensToMaxLength()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength - 1);

		urlTitle += "あ";

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, null);

		Assert.assertEquals(maxLength - 1, uniqueUrlTitle.length());
	}

	@Test
	public void testValidateAllowsDuplicatesInDifferentLanguagesForDifferentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.getDefault()), urlTitle
			).build(),
			_getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, _user.getUserId(),
			_language.getLanguageId(LocaleUtil.BRAZIL), urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateDuplicateLocalizedUrlTitleForDifferentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.getDefault()), urlTitle
			).build(),
			_getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, _user.getUserId(),
			_language.getLanguageId(LocaleUtil.getDefault()), urlTitle);
	}

	@Test
	public void testValidateDuplicateLocalizedUrlTitleForSameClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.getDefault()), urlTitle
			).build(),
			_getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			_language.getLanguageId(LocaleUtil.BRAZIL), urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateDuplicateUrlTitle() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateUrlTitleNotOwnedByModel() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, _user.getUserId(), urlTitle);
	}

	@Test
	public void testValidateUrlTitleOwnedByModel() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle);
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testValidateUrlTitleWithInvalidLength() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength + 1);

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	@Test
	public void testValidateUrlTitleWithMaxLength() throws Exception {
		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class),
			_getRandomURLTitle());
	}

	private String _getRandomURLTitle() {
		return StringUtil.randomString(
			ModelHintsUtil.getMaxLength(
				FriendlyURLEntryLocalization.class.getName(), "urlTitle"));
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	@DeleteAfterTestRun
	private User _user;

}