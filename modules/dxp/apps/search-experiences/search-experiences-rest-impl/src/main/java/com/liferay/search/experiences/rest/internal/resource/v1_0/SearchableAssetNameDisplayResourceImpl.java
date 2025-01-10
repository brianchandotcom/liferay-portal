/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNameDisplay;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameDisplayResource;

import java.util.List;

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
						setClassName(() -> className1);
						setDisplayName(
							() -> _getDisplayName(className1, languageId));
						setHasSubtype(() -> _gethasSubtype(className1));
					}
				}));
	}

	private String _getDisplayName(String className, String languageId) {
		String modelResource = ResourceActionsUtil.getModelResource(
			LocaleUtil.fromLanguageId(languageId), className);

		if (className.startsWith(
				ObjectDefinitionConstants.
					CLASS_NAME_PREFIX_CUSTOM_OBJECT_DEFINITION)) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					contextCompany.getCompanyId(), className);

			if (objectDefinition != null) {
				modelResource = objectDefinition.getLabel(languageId);
			}
		}

		return modelResource;
	}

	private Boolean _gethasSubtype(String className) {
		String lookupClassName = className;

		if (lookupClassName.equals(DLFileEntry.class.getName())) {
			lookupClassName = DLFileEntryMetadata.class.getName();
		}

		List<DDMStructure> classStructures =
			_ddmStructureLocalService.getClassStructures(
				contextCompany.getCompanyId(),
				_portal.getClassNameId(lookupClassName));

		return !classStructures.isEmpty();
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}