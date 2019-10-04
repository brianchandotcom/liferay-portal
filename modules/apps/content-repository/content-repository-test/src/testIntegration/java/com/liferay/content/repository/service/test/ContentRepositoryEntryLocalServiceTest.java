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

package com.liferay.content.repository.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.repository.constants.ContentRepositoryEntryConstants;
import com.liferay.content.repository.exception.ContentRepositoryEntryNameException;
import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.content.repository.service.ContentRepositoryEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class ContentRepositoryEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddContentRepositoryEntryAddsAContentRepositoryEntry()
		throws Exception {

		ContentRepositoryEntry contentRepositoryEntry =
			_addContentRepositoryEntry("name", "description");

		Group group = _groupLocalService.getGroup(
			contentRepositoryEntry.getGroupId());

		Assert.assertFalse(group.isSite());

		Assert.assertEquals(
			ContentRepositoryEntryConstants.TYPE_CONTENT_REPOSITORY,
			group.getType());

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, group.getParentGroupId());

		Assert.assertEquals(
			ContentRepositoryEntry.class.getName(), group.getClassName());

		Assert.assertEquals(
			contentRepositoryEntry.getContentRepositoryEntryId(),
			group.getClassPK());

		Assert.assertEquals("name", group.getName(LocaleUtil.getDefault()));

		Assert.assertEquals(
			"description", group.getDescription(LocaleUtil.getDefault()));
	}

	@Test(expected = ContentRepositoryEntryNameException.MustNotBeNull.class)
	public void testAddContentRepositoryEntryFailsWithAnEmptyName()
		throws Exception {

		_addContentRepositoryEntry(null, null);
	}

	@Test(expected = ContentRepositoryEntryNameException.MustNotBeNull.class)
	public void testUpdateContentRepositoryEntryFailsWithAnEmptyName()
		throws Exception {

		ContentRepositoryEntry contentRepositoryEntry =
			_addContentRepositoryEntry("name", "description");

		_contentRepositoryEntryLocalService.updateContentRepositoryEntry(
			contentRepositoryEntry.getContentRepositoryEntryId(),
			new HashMap<>(), new HashMap<>(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testUpdateContentRepositoryEntryUpdatesTheNameAndDescription()
		throws Exception {

		ContentRepositoryEntry contentRepositoryEntry =
			_addContentRepositoryEntry("name", "description");

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), "newDescription");

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), "newName");

		_contentRepositoryEntryLocalService.updateContentRepositoryEntry(
			contentRepositoryEntry.getContentRepositoryEntryId(), nameMap,
			descriptionMap, ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(
			contentRepositoryEntry.getGroupId());

		Assert.assertEquals("newName", group.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"newDescription", group.getDescription(LocaleUtil.getDefault()));
	}

	private ContentRepositoryEntry _addContentRepositoryEntry(
			String name, String description)
		throws Exception {

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		ContentRepositoryEntry contentRepositoryEntry =
			_contentRepositoryEntryLocalService.addContentRepositoryEntry(
				nameMap, descriptionMap,
				ServiceContextTestUtil.getServiceContext());

		_contentRepositoryEntries.add(contentRepositoryEntry);

		return contentRepositoryEntry;
	}

	@DeleteAfterTestRun
	private final List<ContentRepositoryEntry> _contentRepositoryEntries =
		new ArrayList<>();

	@Inject
	private ContentRepositoryEntryLocalService
		_contentRepositoryEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}