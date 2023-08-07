/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.json.JSONArray;
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

	public CPInstance fetchCPInstance(long cpDefinitionId, JSONArray jsonArray)
		throws PortalException;

	public CPInstance fetchCPInstance(
			long cpDefinitionId, String serializedFormFieldValues)
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
			long cpDefinitionId, String serializedFormFieldValues, int type)
		throws Exception;

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedFormFieldValues, int type,
			int start, int end)
		throws Exception;

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
		getCPDefinitionOptionValueRelsMap(
			long cpDefinitionId, boolean skuContributor, boolean publicStore);

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPDefinitionOptionValueRelsMap(long cpDefinitionId, String json)
		throws PortalException;

	public String getCPInstanceAdaptiveMediaImageHTMLTag(
			long commerceAccountId, long companyId, long cpInstanceId)
		throws Exception;

	public String getCPInstanceCDNURL(long commerceAccountId, long cpInstanceId)
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