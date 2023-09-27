/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Bruno Farache
 */
public class GroupSharepointStorageImpl extends BaseSharepointStorageImpl {

	@Override
	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree foldersTree = new Tree();

		String rootPath = sharepointRequest.getRootPath();

		for (String token : SharepointUtil.getStorageTokens()) {
			String path = StringBundler.concat(
				rootPath, StringPool.SLASH, token);

			foldersTree.addChild(getFolderTree(path));
		}

		foldersTree.addChild(getFolderTree(rootPath));

		return foldersTree;
	}

}