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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MessageBoardSectionResourceTest
	extends BaseMessageBoardSectionResourceTestCase {

	@Override
	protected void assertValid(MessageBoardSection messageBoardSection) {
		boolean valid = false;

		if (Objects.equals(
				messageBoardSection.getContentSpaceId(),
				testGroup.getGroupId()) &&
			(messageBoardSection.getDateCreated() != null) &&
			(messageBoardSection.getDateModified() != null) &&
			(messageBoardSection.getId() != null) &&
			(messageBoardSection.getTitle() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		MessageBoardSection messageBoardSection1,
		MessageBoardSection messageBoardSection2) {

		if (Objects.equals(
				messageBoardSection1.getContentSpaceId(),
				messageBoardSection2.getContentSpaceId()) &&
			Objects.equals(
				messageBoardSection1.getTitle(),
				messageBoardSection2.getTitle())) {

			return true;
		}

		return false;
	}

	@Override
	protected MessageBoardSection randomIrrelevantMessageBoardSection() {
		MessageBoardSection messageBoardSection =
			super.randomIrrelevantMessageBoardSection();

		messageBoardSection.setContentSpaceId(irrelevantGroup.getGroupId());

		return messageBoardSection;
	}

	protected MessageBoardSection randomMessageBoardSection() {
		MessageBoardSection messageBoardSection =
			super.randomMessageBoardSection();

		messageBoardSection.setContentSpaceId(testGroup.getGroupId());

		return messageBoardSection;
	}

	@Override
	protected MessageBoardSection
			testDeleteMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Override
	protected MessageBoardSection
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				Long contentSpaceId, MessageBoardSection messageBoardSection)
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			contentSpaceId, messageBoardSection);
	}

	@Override
	protected Long
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected MessageBoardSection
			testGetMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Override
	protected MessageBoardSection
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				Long messageBoardSectionId,
				MessageBoardSection messageBoardSection)
		throws Exception {

		return invokePostMessageBoardSectionMessageBoardSection(
			messageBoardSectionId, messageBoardSection);
	}

	@Override
	protected Long
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId()
		throws Exception {

		User user = UserTestUtil.addGroupAdminUser(testGroup);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		MBCategory mbCategory = MBCategoryLocalServiceUtil.addCategory(
			user.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		return mbCategory.getCategoryId();
	}

	@Override
	protected MessageBoardSection
			testPatchMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Override
	protected MessageBoardSection
			testPostContentSpaceMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			testGroup.getGroupId(), messageBoardSection);
	}

	@Override
	protected MessageBoardSection
			testPostMessageBoardSectionMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			testGroup.getGroupId(), messageBoardSection);
	}

	@Override
	protected MessageBoardSection
			testPutMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return invokePostContentSpaceMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

}