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

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import java.util.List;

/**
 * @author Bruno Farache
 */
public class CompanySharepointStorageImpl extends BaseSharepointStorageImpl {

	@Override
	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree foldersTree = new Tree();

		List<Group> groups = WebDAVUtil.getGroups(sharepointRequest.getUser());

		for (Group group : groups) {
			String name = group.getFriendlyURL();

			name = name.substring(1);

			foldersTree.addChild(getFolderTree(name));
		}

		foldersTree.addChild(getFolderTree(StringPool.BLANK));

		return foldersTree;
	}

}