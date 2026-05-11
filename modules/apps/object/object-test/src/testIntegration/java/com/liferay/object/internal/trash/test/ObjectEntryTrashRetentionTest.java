/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.related.models.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Attila Bakay
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class ObjectEntryTrashRetentionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_deleteAllTrashEntries();

		_group = _updateTrashEntriesMaxAge(
			GroupTestUtil.addGroup(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID),
			_MAX_AGE_DAYS);

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString())),
			ObjectDefinitionConstants.SCOPE_SITE);
	}

	@After
	public void tearDown() throws Exception {
		_deleteAllTrashEntries();
	}

	@Test
	public void testCheckEntriesRemovesExpiredObjectEntry() throws Exception {
		ObjectEntry expiredObjectEntry = _addObjectEntryAndMoveToTrash(true);
		ObjectEntry freshObjectEntry = _addObjectEntryAndMoveToTrash(false);

		TrashEntryLocalServiceUtil.checkEntries();

		String className = _objectDefinition.getClassName();

		Assert.assertNull(
			TrashEntryLocalServiceUtil.fetchEntry(
				className, expiredObjectEntry.getObjectEntryId()));
		Assert.assertNotNull(
			TrashEntryLocalServiceUtil.fetchEntry(
				className, freshObjectEntry.getObjectEntryId()));
	}

	private ObjectEntry _addObjectEntryAndMoveToTrash(boolean expired)
		throws Exception {

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_group.getGroupId(), _objectDefinition.getObjectDefinitionId(),
			Collections.emptyMap());

		_objectEntryLocalService.moveObjectEntryToTrash(
			TestPropsValues.getUserId(), objectEntry,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		if (expired) {
			int maxAgeMinutes = _trashHelper.getMaxAge(_group);

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				_objectDefinition.getClassName(),
				objectEntry.getObjectEntryId());

			Date createDate = trashEntry.getCreateDate();

			trashEntry.setCreateDate(
				new Date(
					createDate.getTime() - (maxAgeMinutes * Time.MINUTE) -
						Time.DAY));

			TrashEntryLocalServiceUtil.updateTrashEntry(trashEntry);
		}

		return objectEntry;
	}

	private void _deleteAllTrashEntries() {
		List<TrashEntry> trashEntries =
			TrashEntryLocalServiceUtil.getTrashEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (TrashEntry trashEntry : trashEntries) {
			TrashEntryLocalServiceUtil.deleteEntry(trashEntry);
		}
	}

	private Group _updateTrashEntriesMaxAge(Group group, int days)
		throws Exception {

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		int companyTrashEntriesMaxAge = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		int minutes;

		if (days > 0) {
			minutes = days * 1440;
		}
		else {
			minutes = GetterUtil.getInteger(
				typeSettingsUnicodeProperties.getProperty("trashEntriesMaxAge"),
				companyTrashEntriesMaxAge);
		}

		if (minutes != companyTrashEntriesMaxAge) {
			typeSettingsUnicodeProperties.setProperty(
				"trashEntriesMaxAge", String.valueOf(minutes));
		}
		else {
			typeSettingsUnicodeProperties.remove("trashEntriesMaxAge");
		}

		group.setTypeSettingsProperties(typeSettingsUnicodeProperties);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	private static final int _MAX_AGE_DAYS = 5;

	private Group _group;
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private TrashHelper _trashHelper;

}