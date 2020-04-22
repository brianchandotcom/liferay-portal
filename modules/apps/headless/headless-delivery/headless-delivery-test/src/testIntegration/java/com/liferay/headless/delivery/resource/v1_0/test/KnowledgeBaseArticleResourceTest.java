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
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KnowledgeBaseArticleResourceTest
	extends BaseKnowledgeBaseArticleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		_kbFolder = KBFolderLocalServiceUtil.addKBFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	@Override
	@Test
	public void testPutSiteKnowledgeBaseArticleSubscribe() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testPutSiteKnowledgeBaseArticleSubscribe_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				putSiteKnowledgeBaseArticleSubscribeHttpResponse(
					knowledgeBaseArticle.getSiteId()));
	}

	@Override
	@Test
	public void testPutSiteKnowledgeBaseArticleUnsubscribe() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testPutSiteKnowledgeBaseArticleUnsubscribe_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				putSiteKnowledgeBaseArticleUnsubscribeHttpResponse(
					knowledgeBaseArticle.getSiteId()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"articleBody", "description", "title"};
	}

	@Override
	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, serviceContext);

		return kbArticle.getResourcePrimKey();
	}

	@Override
	protected Long
		testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId() {

		return _kbFolder.getKbFolderId();
	}

	private KBFolder _kbFolder;

}