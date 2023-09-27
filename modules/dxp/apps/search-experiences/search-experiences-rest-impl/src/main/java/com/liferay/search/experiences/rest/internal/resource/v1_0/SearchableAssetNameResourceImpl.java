/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetName;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/searchable-asset-name.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchableAssetNameResource.class
)
public class SearchableAssetNameResourceImpl
	extends BaseSearchableAssetNameResourceImpl {

	@Override
	public Page<SearchableAssetName> getSearchableAssetNamesPage()
		throws Exception {

		return Page.of(
			transformToList(
				_searchableAssetClassNamesProvider.getClassNames(
					contextCompany.getCompanyId()),
				className1 -> new SearchableAssetName() {
					{
						className = className1;
					}
				}));
	}

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}