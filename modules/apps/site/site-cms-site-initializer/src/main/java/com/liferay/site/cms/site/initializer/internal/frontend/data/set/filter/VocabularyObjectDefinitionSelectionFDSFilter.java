/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionServiceUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Noor Najjar
 */
public class VocabularyObjectDefinitionSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "assetTypes";
	}

	@Override
	public String getItemKey() {
		return "assetTypes.type";
	}

	@Override
	public String getLabel() {
		return "asset-types";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		List<ObjectDefinition> objectDefinitions =
			ObjectDefinitionServiceUtil.getCMSObjectDefinitions(
				CompanyThreadLocal.getCompanyId(),
				new String[] {
					ObjectFolderConstants.
						EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
					ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
				});

		if (objectDefinitions.isEmpty()) {
			return selectionFDSFilterItems;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					objectDefinition.getLabel(locale),
					objectDefinition.getObjectDefinitionId()));
		}

		return selectionFDSFilterItems;
	}

}