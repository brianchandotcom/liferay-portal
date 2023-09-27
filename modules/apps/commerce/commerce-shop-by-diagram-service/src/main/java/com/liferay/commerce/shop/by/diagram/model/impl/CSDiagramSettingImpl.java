/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CSDiagramSettingImpl extends CSDiagramSettingBaseImpl {

	@Override
	public CPAttachmentFileEntry getCPAttachmentFileEntry()
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			CPAttachmentFileEntryLocalServiceUtil.getCPAttachmentFileEntry(
				getCPAttachmentFileEntryId());

		if (!cpAttachmentFileEntry.isApproved()) {
			throw new NoSuchCPAttachmentFileEntryException();
		}

		return cpAttachmentFileEntry;
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

}