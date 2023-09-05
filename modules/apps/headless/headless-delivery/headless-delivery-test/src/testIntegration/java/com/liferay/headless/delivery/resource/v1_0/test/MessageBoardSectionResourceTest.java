/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSection;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MessageBoardSectionResourceTest
	extends BaseMessageBoardSectionResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected Long
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		MBCategory mbCategory = MBCategoryLocalServiceUtil.addCategory(
			UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return mbCategory.getCategoryId();
	}

	@Override
	protected MessageBoardSection
			testGraphQLMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

}