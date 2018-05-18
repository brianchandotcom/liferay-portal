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

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPDefinitionHelper {

	public BaseModelSearchResult<CPDefinition> getCPDefinitions(
			long companyId, long groupId, String keywords, String filterFields,
			String filterValues, int start, int end, Sort sort)
		throws PortalException;

	public List<Facet> getFacets(
		String filterFields, String filterValues, SearchContext searchContext);

	public String getFriendlyURL(long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException;

	public Layout getProductLayout(
			long groupId, boolean privateLayout, long cpDefinitionId)
		throws PortalException;

}