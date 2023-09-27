/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.ParentKnowledgeBaseFolder;
import com.liferay.knowledge.base.model.KBFolder;

/**
 * @author Javier Gamarra
 */
public class ParentKnowledgeBaseFolderUtil {

	public static ParentKnowledgeBaseFolder toParentKnowledgeBaseFolder(
		KBFolder kbFolder) {

		if (kbFolder == null) {
			return null;
		}

		return new ParentKnowledgeBaseFolder() {
			{
				folderId = kbFolder.getKbFolderId();
				folderName = kbFolder.getName();
			}
		};
	}

}