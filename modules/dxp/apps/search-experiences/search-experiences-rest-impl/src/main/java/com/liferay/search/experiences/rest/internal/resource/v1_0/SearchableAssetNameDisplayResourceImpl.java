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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNameDisplay;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameDisplayResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/searchable-asset-name-display.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SearchableAssetNameDisplayResource.class
)
public class SearchableAssetNameDisplayResourceImpl
	extends BaseSearchableAssetNameDisplayResourceImpl {

	@Override
	public Page<SearchableAssetNameDisplay> getSearchableAssetNameLanguagePage(
			String languageId)
		throws Exception {

		return Page.of(
			transformToList(
				_searchableAssetClassNamesProvider.getClassNames(
					contextCompany.getCompanyId()),
				className1 -> new SearchableAssetNameDisplay() {
					{
						className = className1;
						displayName = _getDisplayName(className1, languageId);
					}
				}));
	}

	private String _getDisplayName(String className, String languageId) {
		String modelResource = ResourceActionsUtil.getModelResource(
			LocaleUtil.fromLanguageId(languageId), className);

		if (className.startsWith(ObjectDefinition.class.getName() + "#")) {
			String[] parts = StringUtil.split(className, "#");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					GetterUtil.getLong(parts[1]));

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