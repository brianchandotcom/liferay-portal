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
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KnowledgeBaseFolderResourceTest
	extends BaseKnowledgeBaseFolderResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected KnowledgeBaseFolder randomKnowledgeBaseFolder() {
		KnowledgeBaseFolder knowledgeBaseFolder =
			super.randomKnowledgeBaseFolder();

		knowledgeBaseFolder.setParentKnowledgeBaseFolderId(0L);

		return knowledgeBaseFolder;
	}

	@Override
	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		KBFolder kbFolder = KBFolderLocalServiceUtil.addKBFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		return kbFolder.getKbFolderId();
	}

}