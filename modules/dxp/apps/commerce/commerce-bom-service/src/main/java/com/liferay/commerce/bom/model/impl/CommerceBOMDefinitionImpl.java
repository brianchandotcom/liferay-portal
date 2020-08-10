/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.model.impl;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMFolderLocalServiceUtil;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMDefinitionImpl extends CommerceBOMDefinitionBaseImpl {

	public CommerceBOMDefinitionImpl() {
	}

	@Override
	public CommerceBOMFolder fetchCommerceBOMFolder() {
		return CommerceBOMFolderLocalServiceUtil.fetchCommerceBOMFolder(
			getCommerceBOMFolderId());
	}

	@Override
	public CPAttachmentFileEntry fetchCPAttachmentFileEntry() {
		return CPAttachmentFileEntryLocalServiceUtil.fetchCPAttachmentFileEntry(
			getCPAttachmentFileEntryId());
	}

}