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
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseAttachmentSerDes;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KnowledgeBaseAttachmentResourceTest
	extends BaseKnowledgeBaseAttachmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		_kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, serviceContext);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"contentUrl", "encodingFormat", "title"};
	}

	@Override
	protected KnowledgeBaseAttachment
			testDeleteKnowledgeBaseAttachment_addKnowledgeBaseAttachment()
		throws Exception {

		return invokePostKnowledgeBaseArticleKnowledgeBaseAttachment(
			_kbArticle.getResourcePrimKey(),
			toMultipartBody(randomKnowledgeBaseAttachment()));
	}

	@Override
	protected Long
		testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_getKnowledgeBaseArticleId() {

		return _kbArticle.getResourcePrimKey();
	}

	@Override
	protected KnowledgeBaseAttachment
			testGetKnowledgeBaseAttachment_addKnowledgeBaseAttachment()
		throws Exception {

		return invokePostKnowledgeBaseArticleKnowledgeBaseAttachment(
			_kbArticle.getResourcePrimKey(),
			toMultipartBody(randomKnowledgeBaseAttachment()));
	}

	@Override
	protected MultipartBody toMultipartBody(
		KnowledgeBaseAttachment knowledgeBaseAttachment) {

		testContentType = "multipart/form-data;boundary=PART";

		Map<String, BinaryFile> binaryFileMap = new HashMap<>();

		String randomString = RandomTestUtil.randomString();

		binaryFileMap.put(
			"file",
			new BinaryFile(
				testContentType, RandomTestUtil.randomString(),
				new ByteArrayInputStream(randomString.getBytes()), 0));

		return MultipartBody.of(
			binaryFileMap, __ -> null,
			KnowledgeBaseAttachmentSerDes.toMap(knowledgeBaseAttachment));
	}

	private KBArticle _kbArticle;

}