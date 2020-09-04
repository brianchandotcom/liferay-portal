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

package com.liferay.commerce.product.portlet.action;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.CPType;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface ActionHelper {

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			PortletRequest portletRequest)
		throws PortalException;

	public CPAttachmentFileEntry getCPAttachmentFileEntry(
			PortletRequest portletRequest)
		throws PortalException;

	public CPDefinition getCPDefinition(PortletRequest portletRequest)
		throws PortalException;

	public CPDefinitionLink getCPDefinitionLink(PortletRequest portletRequest)
		throws PortalException;

	public List<CPDefinitionLink> getCPDefinitionLinks(
			PortletRequest portletRequest)
		throws PortalException;

	public CPDefinitionOptionRel getCPDefinitionOptionRel(
			PortletRequest portletRequest)
		throws PortalException;

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			PortletRequest portletRequest)
		throws PortalException;

	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
			PortletRequest portletRequest)
		throws PortalException;

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId)
		throws PortalException;

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			PortletRequest portletRequest)
		throws PortalException;

	public List<CPDefinition> getCPDefinitions(PortletRequest portletRequest)
		throws PortalException;

	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue(
				PortletRequest portletRequest)
		throws PortalException;

	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(
				PortletRequest portletRequest)
		throws PortalException;

	public CPInstance getCPInstance(PortletRequest portletRequest)
		throws PortalException;

	public List<CPInstance> getCPInstances(PortletRequest portletRequest)
		throws PortalException;

	public CPType getCPType(String name);

	public List<CPType> getCPTypes();

	public List<CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels(
			long cpDefinitionId)
		throws PortalException;

	public void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object object)
		throws IOException;

}