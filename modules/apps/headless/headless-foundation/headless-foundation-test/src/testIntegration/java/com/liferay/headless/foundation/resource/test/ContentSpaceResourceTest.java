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

package com.liferay.headless.foundation.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.ContentSpace;
import com.liferay.headless.foundation.dto.ContentSpaceCollection;
import com.liferay.headless.foundation.resource.ContentSpaceResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 * @author Sarai Díaz
 */
@RunWith(Arquillian.class)
public class ContentSpaceResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_groupLocalService.deleteGroup(_group);
	}

	@Test
	public void testGetContentSpaceCollectionWithActiveGroup()
		throws Exception {

		ContentSpaceCollection<ContentSpace> contentSpaceCollection =
			_contentSpaceResource.getContentSpaceCollection(
				new Pagination(20, 1), "");

		ContentSpace contentSpace = _getContentSpace(
			contentSpaceCollection.getItems(), _group.getGroupId());

		Assert.assertNotNull(contentSpace);
	}

	@Test
	public void testGetContentSpaceCollectionWithInactiveGroup()
		throws Exception {

		_deactivateGroup(_group);

		ContentSpaceCollection<ContentSpace> contentSpaceCollection =
			_contentSpaceResource.getContentSpaceCollection(
				new Pagination(20, 1), "");

		ContentSpace contentSpace = _getContentSpace(
			contentSpaceCollection.getItems(), _group.getGroupId());

		Assert.assertNull(contentSpace);
	}

	private Group _deactivateGroup(Group group) throws PortalException {
		return _groupLocalService.updateGroup(
			group.getGroupId(), group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), false,
			new ServiceContext());
	}

	private ContentSpace _getContentSpace(
		Collection<ContentSpace> contentSpaces, long groupId) {

		Stream<ContentSpace> stream = contentSpaces.stream();

		return stream.filter(
			contentSpace -> Objects.equals(contentSpace.getId(), groupId)
		).findAny(
		).orElse(
			null
		);
	}

	@Inject
	private ContentSpaceResource _contentSpaceResource;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}