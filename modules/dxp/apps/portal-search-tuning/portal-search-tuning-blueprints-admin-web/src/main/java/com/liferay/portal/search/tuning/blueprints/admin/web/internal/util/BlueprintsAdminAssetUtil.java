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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.util;

import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class BlueprintsAdminAssetUtil {

	public static String[] getSearchableAssetNames(long companyId) {
		return _searchableAssetClassNamesProvider.getClassNames(companyId);
	}

	@Reference(unbind = "-")
	protected void setSearchableAssetClassNamesProvider(
		SearchableAssetClassNamesProvider searchableAssetClassNamesProvider) {

		_searchableAssetClassNamesProvider = searchableAssetClassNamesProvider;
	}

	private static SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}