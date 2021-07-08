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

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.shop.by.diagram.constants.CPDefinitionDiagramSettingsConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramSettingImpl
	extends CPDefinitionDiagramSettingBaseImpl {

	public CPDefinitionDiagramSettingImpl() {
	}

	@Override
	public CPAttachmentFileEntry getCPAttachmentFileEntry()
		throws PortalException {

		CPDefinition cpDefinition =
			CPDefinitionLocalServiceUtil.getCPDefinition(getCPDefinitionId());

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpDefinition.getCPAttachmentFileEntries(
				CPDefinitionDiagramSettingsConstants.TYPE_DIAGRAM,
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