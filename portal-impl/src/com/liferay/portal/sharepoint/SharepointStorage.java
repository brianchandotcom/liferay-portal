/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.xml.Element;

import java.io.InputStream;

import java.util.List;

/**
 * @author Bruno Farache
 */
public interface SharepointStorage {

	public void addDocumentElements(
			SharepointRequest sharepointRequest, Element element)
		throws Exception;

	public void createFolder(SharepointRequest sharepointRequest)
		throws Exception;

	public InputStream getDocumentInputStream(
			SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getDocumentsTree(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getDocumentTree(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getFolderTree(SharepointRequest sharepointRequest)
		throws Exception;

	public void getParentFolderIds(
			long groupId, String path, List<Long> folderIds)
		throws Exception;

	public Tree[] moveDocument(SharepointRequest sharepointRequest)
		throws Exception;

	public void putDocument(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree[] removeDocument(SharepointRequest sharepointRequest)
		throws Exception;

}