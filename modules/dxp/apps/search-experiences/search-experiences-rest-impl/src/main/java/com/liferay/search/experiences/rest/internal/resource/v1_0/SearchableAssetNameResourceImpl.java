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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetName;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameResource;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/searchable-asset-name.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchableAssetNameResource.class
)
public class SearchableAssetNameResourceImpl
	extends BaseSearchableAssetNameResourceImpl {

	@Override
	public Page<SearchableAssetName> getSearchableAssetNamesPage()
		throws Exception {

		List<SearchableAssetName> searchableAssetNames = new ArrayList<>();

		String[] classNames = _searchableAssetClassNamesProvider.getClassNames(
			contextCompany.getCompanyId());

		for (String className : classNames) {
			SearchableAssetName searchableAssetName = new SearchableAssetName();

			searchableAssetName.setClassName(className);

			searchableAssetNames.add(searchableAssetName);
		}

		return Page.of(searchableAssetNames);
	}

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}