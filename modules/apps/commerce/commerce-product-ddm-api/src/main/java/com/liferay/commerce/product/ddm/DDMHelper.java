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

package com.liferay.commerce.product.ddm;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.jsp.PageContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Igor Beslic
 */
@ProviderType
public interface DDMHelper {

	public DDMForm getCPAttachmentFileEntryDDMForm(
		Locale locale,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels);

	public DDMForm getCPInstanceDDMForm(
		Locale locale, boolean ignoreSKUCombinations,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels);

	public DDMForm getPublicStoreDDMForm(
		long groupId, long commerceAccountId, long cpDefinitionId,
		Locale locale, boolean ignoreSKUCombinations,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels,
		long companyId, long userId);

	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, PageContext pageContext,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, RenderRequest renderRequest,
			RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException;

	public String renderCPInstanceOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException;

	public String renderPublicStoreOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException;

}