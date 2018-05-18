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

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPInstanceHelper {

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long cpDefinitionId, String serializedDDMFormValues, int type)
		throws Exception;

	public DDMForm getCPAttachmentFileEntryDDMForm(
			long cpDefinitionId, Locale locale)
		throws PortalException;

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
		getCPDefinitionOptionRelsMap(String json) throws PortalException;

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRel(
			long cpDefinitionId, String optionFieldName,
			Map<String, String> optionMap)
		throws Exception;

	public CPInstance getCPInstance(
			long cpDefinitionId, String serializedDDMFormValues)
		throws Exception;

	public DDMForm getCPInstanceDDMForm(
			long cpDefinitionId, Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor)
		throws PortalException;

	public List<KeyValuePair> getKeyValuePairs(String json, Locale locale)
		throws PortalException;

	public DDMForm getPublicStoreDDMForm(
			long cpDefinitionId, Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor)
		throws PortalException;

	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException;

	public String renderCPInstanceOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			boolean skuContributor, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException;

	public String renderPublicStoreOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			boolean skuContributor, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException;

}