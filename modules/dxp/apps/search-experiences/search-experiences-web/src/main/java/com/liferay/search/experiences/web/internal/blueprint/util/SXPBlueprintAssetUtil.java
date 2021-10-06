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

package com.liferay.search.experiences.web.internal.blueprint.util;

import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class SXPBlueprintAssetUtil {

	public static String[] getSearchableAssetNames(long companyId) {
		return _searchableAssetClassNamesProvider.getClassNames(companyId);
	}

	public static JSONArray getSearchableAssetNamesJSONArray(
		long companyId, Locale locale) {

		JSONArray jsonArray = new JSONArrayImpl();

		String[] classNames = _searchableAssetClassNamesProvider.getClassNames(
			companyId);

		for (String className : classNames) {
			jsonArray.put(
				JSONUtil.put(
					"className", className
				).put(
					"displayName", _getDisplayName(locale, className)
				));
		}

		return jsonArray;
	}

	@Reference(unbind = "-")
	protected void setSearchableAssetClassNamesProvider(
		SearchableAssetClassNamesProvider searchableAssetClassNamesProvider) {

		_searchableAssetClassNamesProvider = searchableAssetClassNamesProvider;
	}

	private static String _getDisplayName(Locale locale, String className) {
		return ResourceActionsUtil.getModelResource(locale, className);
	}

	private static SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}