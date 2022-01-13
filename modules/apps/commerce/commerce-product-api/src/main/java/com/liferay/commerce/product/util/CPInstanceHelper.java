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

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@ProviderType
public interface CPInstanceHelper {

	public CPInstance fetchCPInstance(
			long cpDefinitionId, String serializedDDMFormValues)
		throws PortalException;

	public CPInstance fetchFirstAvailableReplacementCPInstance(
			long commerceChannelGroupId, long cpInstanceId)
		throws PortalException;

	public CPInstance fetchReplacementCPInstance(
			long cProductId, String cpInstanceUuid)
		throws PortalException;

	public List<CPDefinitionOptionValueRel> filterCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId,
			List<Long> skuCombinationCPDefinitionOptionValueRelIds)
		throws PortalException;

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedDDMFormValues, int type)
		throws Exception;

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedDDMFormValues, int type,
			int start, int end)
		throws Exception;

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
		getCPDefinitionOptionRelsMap(
			long cpDefinitionId, boolean skuContributor, boolean publicStore);

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPDefinitionOptionRelsMap(long cpDefinitionId, String json)
		throws PortalException;

	public String getCPInstanceAdaptiveMediaImageHTMLTag(
			long commerceAccountId, long companyId, long cpInstanceId)
		throws Exception;

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPInstanceCPDefinitionOptionRelsMap(long cpInstanceId)
		throws PortalException;

	public List<CPDefinitionOptionValueRel>
			getCPInstanceCPDefinitionOptionValueRels(
				long cpDefinitionId, long cpDefinitionOptionRelId)
		throws PortalException;

	public List<CPInstanceOptionValueRel>
		getCPInstanceCPInstanceOptionValueRels(long cpInstanceId);

	public FileVersion getCPInstanceImageFileVersion(
			long commerceAccountId, long companyId, long cpInstanceId)
		throws Exception;

	public String getCPInstanceThumbnailSrc(
			long commerceAccountId, long cpInstanceId)
		throws Exception;

	public CPInstance getDefaultCPInstance(long cpDefinitionId)
		throws PortalException;

	public CPSku getDefaultCPSku(CPCatalogEntry cpCatalogEntry)
		throws Exception;

	public List<KeyValuePair> getKeyValuePairs(
			long cpDefinitionId, String json, Locale locale)
		throws PortalException;

	public CPSku toCPSku(CPInstance cpInstance);

}