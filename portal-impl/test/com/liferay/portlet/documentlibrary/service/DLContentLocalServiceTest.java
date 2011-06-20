/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.store.Store;
import java.io.ByteArrayInputStream;
import java.util.List;
import jodd.util.StringPool;

/**
 * @author Tina Tian
 */
public class DLContentLocalServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		_service = (DLContentLocalService)PortalBeanLocatorUtil.locate(
			DLContentLocalService.class.getName());
	}

	public void testAddContentWithByteArray() throws Exception {
		DLContent dlContent = _service.addContent(
			nextLong(), randomString(), nextLong(), nextLong(), randomString(),
			Store.DEFAULT_VERSION, new byte[1024]);

		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getPortletId(), 
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertTrue(exists);
	}

	public void testAddContentWithInputStream() throws Exception {
		ByteArrayInputStream byteArrayInputStream =
			new ByteArrayInputStream(new byte[1024]);

		DLContent dlContent = _service.addContent(
			nextLong(), randomString(), nextLong(), nextLong(), randomString(),
			Store.DEFAULT_VERSION, byteArrayInputStream, 
			byteArrayInputStream.available());

		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getPortletId(), 
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertTrue(exists);
	}

	public void testDeleteContentByC_P_R_P_V() throws Exception {
		DLContent dlContent = addContent();

		_service.deleteContent(
			dlContent.getCompanyId(), dlContent.getPortletId(),
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getPortletId(), 
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertFalse(exists);
	}

	public void testDeleteContents() throws Exception {
		DLContent dlContent = addContent();

		_service.deleteContents(
			dlContent.getCompanyId(), dlContent.getPortletId(),
			dlContent.getRepositoryId(), dlContent.getPath());

		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getPortletId(), 
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertFalse(exists);
	}

	public void testGetContentByC_P_R_P_V() throws Exception {
		DLContent dlContent = addContent();

		DLContent newDlContent = _service.getContent(
			dlContent.getCompanyId(), dlContent.getPortletId(),
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertEquals(dlContent, newDlContent);
	}

	public void testGetContentByC_R_P_V() throws Exception {
		DLContent dlContent = addContent();

		DLContent newDlContent = _service.getContent(
			dlContent.getCompanyId(), dlContent.getRepositoryId(), 
			dlContent.getPath(), dlContent.getVersion());

		assertEquals(dlContent, newDlContent);
	}

	public void testGetContentReferences() throws Exception {
		DLContent dlContent = addContent(); 

		List<DLContent> results = _service.getContentReferences(
			dlContent.getCompanyId(), dlContent.getRepositoryId(),
			dlContent.getPath().split(StringPool.SLASH)[0] + StringPool.SLASH + StringPool.PERCENT);
		
		assertEquals(1, results.size());
		assertEquals(dlContent.getContentId(), results.get(0).getContentId());
		assertEquals(dlContent.getGroupId(), results.get(0).getGroupId());
		assertEquals(dlContent.getCompanyId(), results.get(0).getCompanyId());
		assertEquals(dlContent.getPortletId(), results.get(0).getPortletId());
		assertEquals(dlContent.getRepositoryId(), results.get(0).getRepositoryId());
		assertEquals(dlContent.getPath(), results.get(0).getPath());
		assertEquals(dlContent.getVersion(), results.get(0).getVersion());
		assertEquals(dlContent.getSize(), results.get(0).getSize());
	}

	public void testGetContentsByC_P_R_P() throws Exception {
		DLContent dlContent = addContent();

		List<DLContent> dlContents = _service.getContents(
			dlContent.getCompanyId(), dlContent.getPortletId(),
			dlContent.getRepositoryId(), dlContent.getPath());

		assertEquals(1, dlContents.size());
		assertEquals(dlContent, dlContents.get(0));
	}

	public void testGetContentsByC_R_P() throws Exception {
		DLContent dlContent = addContent();

		List<DLContent> dlContents = _service.getContents(
			dlContent.getCompanyId(), dlContent.getRepositoryId(), 
			dlContent.getPath());

		assertEquals(1, dlContents.size());
		assertEquals(dlContent, dlContents.get(0));
	}

	public void testHasContentByC_P_R_P_V() throws Exception {
		DLContent dlContent = addContent();

		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getPortletId(),
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertTrue(exists);
	}

	public void testHasContentByC_R_P_V() throws Exception {
		DLContent dlContent = addContent();

		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getRepositoryId(), 
			dlContent.getPath(), dlContent.getVersion());

		assertTrue(exists);
	}

	public void testUpdateContent() throws Exception {
		DLContent dlContent = addContent();

		String newPath = randomString();
		long newRepositoryId = nextLong();

		_service.updateDLContent(
			dlContent.getCompanyId(), dlContent.getRepositoryId(), 
			newRepositoryId, dlContent.getPath(), newPath);

		List<DLContent> newContents = _service.getContents(
			dlContent.getCompanyId(), newRepositoryId, newPath);

		assertFalse(dlContent.getPath().equals(newContents.get(0).getPath()));
	}

	protected DLContent addContent() throws Exception {
		String path = randomString() + StringPool.SLASH + randomString();

		DLContent dlContent = _service.addContent(
			nextLong(), randomString(), nextLong(), nextLong(), path, 
			Store.DEFAULT_VERSION, new byte[1024]);
		
		boolean exists = _service.hasContent(
			dlContent.getCompanyId(), dlContent.getPortletId(), 
			dlContent.getRepositoryId(), dlContent.getPath(), 
			dlContent.getVersion());

		assertTrue(exists);
		
		return dlContent;
	}
	
	private DLContentLocalService _service;
	
}