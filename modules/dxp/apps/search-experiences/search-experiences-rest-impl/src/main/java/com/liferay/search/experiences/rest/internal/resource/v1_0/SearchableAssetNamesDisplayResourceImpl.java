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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetDisplay;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNamesDisplay;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNamesDisplayResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/searchable-asset-names-display.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SearchableAssetNamesDisplayResource.class
)
public class SearchableAssetNamesDisplayResourceImpl
	extends BaseSearchableAssetNamesDisplayResourceImpl {

	@Override
	public SearchableAssetNamesDisplay getSearchableAssetNameLanguage(
			String languageId)
		throws Exception {

		SearchableAssetNamesDisplay searchableAssetNamesDisplay =
			new SearchableAssetNamesDisplay();

		String[] classNames = _searchableAssetClassNamesProvider.getClassNames(
			contextCompany.getCompanyId());

		SearchableAssetDisplay[] searchableAssetDisplays =
			new SearchableAssetDisplay[classNames.length];

		for (int i = 0; i < classNames.length; i++) {
			SearchableAssetDisplay searchableAssetDisplay =
				new SearchableAssetDisplay();

			searchableAssetDisplay.setClassName(classNames[i]);
			searchableAssetDisplay.setDisplayName(
				_getDisplayName(classNames[i], languageId));

			searchableAssetDisplays[i] = searchableAssetDisplay;
		}

		searchableAssetNamesDisplay.setSearchableAssetDisplays(
			searchableAssetDisplays);

		return searchableAssetNamesDisplay;
	}

	private String _getDisplayName(String className, String languageId) {
		String modelResource = ResourceActionsUtil.getModelResource(
			LocaleUtil.fromLanguageId(languageId), className);

		if (className.startsWith(ObjectDefinition.class.getName() + "#")) {
			String[] parts = StringUtil.split(className, "#");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					Long.valueOf(parts[1]));

			modelResource = objectDefinition.getLabel(languageId);
		}

		return modelResource;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}