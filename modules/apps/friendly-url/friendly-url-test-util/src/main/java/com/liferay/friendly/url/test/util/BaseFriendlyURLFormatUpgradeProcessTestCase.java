/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.test.util;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

/**
 * @author Joao Victor Alves
 */
public abstract class BaseFriendlyURLFormatUpgradeProcessTestCase {

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupTestUtil.deleteGroup(group);
	}

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		_persistence = FriendlyURLEntryLocalizationUtil.getPersistence();
	}

	@After
	public void tearDown() throws Exception {
		for (long friendlyURLEntryId : friendlyURLEntryIds) {
			friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
				friendlyURLEntryId, defaultLanguageId);
		}

		friendlyURLEntryIds.clear();
	}

	protected void createFriendlyURLEntry(long classPK, long classNameId) {
		long friendlyURLEntryId = RandomTestUtil.randomLong();

		friendlyURLEntryIds.add(friendlyURLEntryId);

		_friendlyURLEntry = friendlyURLEntryLocalService.createFriendlyURLEntry(
			friendlyURLEntryId);

		_friendlyURLEntry.setDefaultLanguageId(defaultLanguageId);
		_friendlyURLEntry.setGroupId(group.getGroupId());
		_friendlyURLEntry.setClassNameId(classNameId);
		_friendlyURLEntry.setClassPK(classPK);

		friendlyURLEntryLocalService.addFriendlyURLEntry(_friendlyURLEntry);
	}

	protected void createFriendlyURLEntryLocalization(
		long classNameId, long classPK, String languageId, String urlTitle) {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_persistence.create(RandomTestUtil.randomInt());

		friendlyURLEntryLocalization.setFriendlyURLEntryId(
			_friendlyURLEntry.getFriendlyURLEntryId());

		friendlyURLEntryLocalization.setLanguageId(languageId);
		friendlyURLEntryLocalization.setUrlTitle(urlTitle);
		friendlyURLEntryLocalization.setGroupId(group.getGroupId());
		friendlyURLEntryLocalization.setClassNameId(classNameId);
		friendlyURLEntryLocalization.setClassPK(classPK);

		friendlyURLEntryLocalService.updateFriendlyURLLocalization(
			friendlyURLEntryLocalization);
	}

	protected static final List<Long> friendlyURLEntryIds = new ArrayList<>();

	@Inject
	protected static FriendlyURLEntryLocalService friendlyURLEntryLocalService;

	protected static Group group;

	@Inject
	protected CounterLocalService counterLocalService;

	protected String defaultLanguageId;

	private static FriendlyURLEntryLocalizationPersistence _persistence;

	private FriendlyURLEntry _friendlyURLEntry;

}