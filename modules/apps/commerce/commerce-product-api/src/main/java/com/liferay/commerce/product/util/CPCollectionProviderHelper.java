/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.info.pagination.Pagination;

import java.util.List;

/**
 * @author Danny Situ
 */
@ProviderType
public interface CPCollectionProviderHelper {

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords,
		Pagination pagination);

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long companyId, long groupId,
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords,
		Pagination pagination);

	public int getCPDefinitionOptionValueRelsCount(
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords);

	public int getCPDefinitionOptionValueRelsCount(
		long companyId, long groupId,
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords);

}