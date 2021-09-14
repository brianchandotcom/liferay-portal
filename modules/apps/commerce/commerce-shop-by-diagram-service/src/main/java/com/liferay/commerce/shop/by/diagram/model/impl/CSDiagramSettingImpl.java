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

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramSettingsConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CSDiagramSettingImpl extends CSDiagramSettingBaseImpl {

	@Override
	public CPAttachmentFileEntry getCPAttachmentFileEntry()
		throws PortalException {

		CPDefinition cpDefinition =
			CPDefinitionLocalServiceUtil.getCPDefinition(getCPDefinitionId());

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpDefinition.getCPAttachmentFileEntries(
				CSDiagramSettingsConstants.TYPE_DIAGRAM,
				WorkflowConstants.STATUS_APPROVED);

		if (cpAttachmentFileEntries.isEmpty()) {
			throw new NoSuchCPAttachmentFileEntryException();
		}

		return cpAttachmentFileEntries.get(0);
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

}