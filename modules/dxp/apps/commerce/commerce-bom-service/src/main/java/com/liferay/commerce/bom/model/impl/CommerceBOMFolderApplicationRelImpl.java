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

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.service.CommerceApplicationModelLocalServiceUtil;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMFolderApplicationRelImpl
	extends CommerceBOMFolderApplicationRelBaseImpl {

	public CommerceBOMFolderApplicationRelImpl() {
	}

	@Override
	public CommerceApplicationModel getCommerceApplicationModel()
		throws PortalException {

		return CommerceApplicationModelLocalServiceUtil.
			getCommerceApplicationModel(getCommerceApplicationModelId());
	}

	@Override
	public CommerceBOMFolder getCommerceBOMFolder() throws PortalException {
		return CommerceBOMFolderLocalServiceUtil.getCommerceBOMFolder(
			getCommerceBOMFolderId());
	}

}