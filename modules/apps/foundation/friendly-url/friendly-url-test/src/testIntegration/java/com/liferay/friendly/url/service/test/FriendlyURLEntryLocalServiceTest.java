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

package com.liferay.friendly.url.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class FriendlyURLEntryLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGetUniqueUrlTitleNormalizesUrlTitle() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);
		String urlTitle = "url title with spaces";

		String uniqueUrlTitle =
			FriendlyURLEntryLocalServiceUtil.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				urlTitle);

		Assert.assertEquals("url-title-with-spaces", uniqueUrlTitle);
	}

	@Test
	public void testGetUniqueUrlTitleResolvesConflicts() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		FriendlyURLEntryLocalServiceUtil.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		String uniqueUrlTitle =
			FriendlyURLEntryLocalServiceUtil.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, _user.getUserId(), urlTitle);

		Assert.assertEquals("existing-url-title-1", uniqueUrlTitle);
	}

	@Test
	public void testGetUniqueUrlTitleReusesOwnedUrlTitles() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		FriendlyURLEntryLocalServiceUtil.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		String uniqueUrlTitle =
			FriendlyURLEntryLocalServiceUtil.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				urlTitle);

		Assert.assertEquals(urlTitle, uniqueUrlTitle);
	}

	@Test
	public void testGetUniqueUrlTitleShortensToMaxLength() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength + 1);

		String uniqueUrlTitle =
			FriendlyURLEntryLocalServiceUtil.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				urlTitle);

		Assert.assertEquals(maxLength, uniqueUrlTitle.length());
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateDuplicateUrlTitle() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength);

		FriendlyURLEntryLocalServiceUtil.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		FriendlyURLEntryLocalServiceUtil.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateUrlTitleNotOwnedByModel() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength);

		FriendlyURLEntryLocalServiceUtil.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		FriendlyURLEntryLocalServiceUtil.validate(
			_group.getGroupId(), classNameId, _user.getUserId(), urlTitle);
	}

	@Test
	public void testValidateUrlTitleOwnedByModel() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength);

		FriendlyURLEntryLocalServiceUtil.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		FriendlyURLEntryLocalServiceUtil.validate(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle);
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testValidateUrlTitleWithInvalidLength() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength + 1);

		FriendlyURLEntryLocalServiceUtil.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	@Test
	public void testValidateUrlTitleWithMaxLength() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength);

		FriendlyURLEntryLocalServiceUtil.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	private ServiceContext _getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}