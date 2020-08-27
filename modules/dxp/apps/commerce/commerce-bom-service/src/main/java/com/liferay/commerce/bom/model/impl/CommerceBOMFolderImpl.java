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

import com.liferay.commerce.bom.constants.CommerceBOMFolderConstants;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMFolderImpl extends CommerceBOMFolderBaseImpl {

	public CommerceBOMFolderImpl() {
	}

	@Override
	public List<Long> getAncestorCommerceBOMFolderIds() throws PortalException {
		List<Long> ancestorFolderIds = new ArrayList<>();

		CommerceBOMFolder commerceBOMFolder = this;

		while (!commerceBOMFolder.isRoot()) {
			commerceBOMFolder = commerceBOMFolder.getParentCommerceBOMFolder();

			ancestorFolderIds.add(commerceBOMFolder.getCommerceBOMFolderId());
		}

		return ancestorFolderIds;
	}

	@Override
	public List<CommerceBOMFolder> getAncestors() throws PortalException {
		List<CommerceBOMFolder> ancestors = new ArrayList<>();

		CommerceBOMFolder commerceBOMFolder = this;

		while (!commerceBOMFolder.isRoot()) {
			commerceBOMFolder = commerceBOMFolder.getParentCommerceBOMFolder();

			ancestors.add(commerceBOMFolder);
		}

		return ancestors;
	}

	@Override
	public CommerceBOMFolder getParentCommerceBOMFolder()
		throws PortalException {

		if (getParentCommerceBOMFolderId() ==
				CommerceBOMFolderConstants.DEFAULT_COMMERCE_BOM_FOLDER_ID) {

			return null;
		}

		return CommerceBOMFolderLocalServiceUtil.getCommerceBOMFolder(
			getParentCommerceBOMFolderId());
	}

	@Override
	public boolean isRoot() {
		if (getParentCommerceBOMFolderId() ==
				CommerceBOMFolderConstants.DEFAULT_COMMERCE_BOM_FOLDER_ID) {

			return true;
		}

		return false;
	}

}