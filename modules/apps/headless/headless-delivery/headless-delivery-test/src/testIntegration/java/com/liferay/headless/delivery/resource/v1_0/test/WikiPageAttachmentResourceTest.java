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
import com.liferay.headless.delivery.client.dto.v1_0.WikiPageAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class WikiPageAttachmentResourceTest
	extends BaseWikiPageAttachmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCommand("update");
		serviceContext.setScopeGroupId(testGroup.getGroupId());

		WikiNode wikiNode = WikiNodeLocalServiceUtil.addNode(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		_wikiPage = WikiPageLocalServiceUtil.addPage(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			wikiNode.getNodeId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);
	}

	@Override
	protected void assertValid(
			WikiPageAttachment wikiPageAttachment,
			Map<String, File> multipartFiles)
		throws Exception {

		Assert.assertEquals(
			new String(FileUtil.getBytes(multipartFiles.get("file"))),
			_read(
				"http://localhost:8080" + wikiPageAttachment.getContentUrl()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected Map<String, File> getMultipartFiles() throws Exception {
		return HashMapBuilder.<String, File>put(
			"file",
			() -> {
				File file = new File(_tempFileName);

				FileUtil.write(file, TestDataConstants.TEST_BYTE_ARRAY);

				return file;
			}
		).build();
	}

	@Override
	protected WikiPageAttachment randomWikiPageAttachment() throws Exception {
		WikiPageAttachment wikiPageAttachment =
			super.randomWikiPageAttachment();

		_tempFileName = FileUtil.createTempFileName();

		File file = new File(_tempFileName);

		wikiPageAttachment.setTitle(file.getName());

		return wikiPageAttachment;
	}

	@Override
	protected WikiPageAttachment
			testDeleteWikiPageAttachment_addWikiPageAttachment()
		throws Exception {

		return wikiPageAttachmentResource.postWikiPageWikiPageAttachment(
			_wikiPage.getResourcePrimKey(), randomWikiPageAttachment(),
			getMultipartFiles());
	}

	@Override
	protected WikiPageAttachment
			testGetWikiPageAttachment_addWikiPageAttachment()
		throws Exception {

		return wikiPageAttachmentResource.postWikiPageWikiPageAttachment(
			_wikiPage.getResourcePrimKey(), randomWikiPageAttachment(),
			getMultipartFiles());
	}

	@Override
	protected Long testGetWikiPageWikiPageAttachmentsPage_getWikiPageId() {
		return _wikiPage.getResourcePrimKey();
	}

	@Override
	protected WikiPageAttachment
			testGraphQLWikiPageAttachment_addWikiPageAttachment()
		throws Exception {

		return testDeleteWikiPageAttachment_addWikiPageAttachment();
	}

	private String _read(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	private String _tempFileName;
	private WikiPage _wikiPage;

}