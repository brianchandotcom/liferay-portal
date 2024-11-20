/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.commerce.product.service.CPOptionCategoryLocalServiceUtil;
import com.liferay.commerce.product.service.CPSpecificationOptionListTypeDefinitionRelLocalServiceUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionServiceUtil;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionImpl extends CPSpecificationOptionBaseImpl {

	@Override
	public CPOptionCategory getCPOptionCategory() throws PortalException {
		long cpOptionCategoryId = getCPOptionCategoryId();

		if (cpOptionCategoryId !=
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			return CPOptionCategoryLocalServiceUtil.getCPOptionCategory(
				cpOptionCategoryId);
		}

		return null;
	}

	@Override
	public List<ListTypeDefinition> getListTypeDefinitions()
		throws PortalException {

		List<ListTypeDefinition> listTypeDefinitions = new ArrayList<>();

		for (CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel :
					CPSpecificationOptionListTypeDefinitionRelLocalServiceUtil.
						getCPSpecificationOptionListTypeDefinitionRels(
							getCPSpecificationOptionId())) {

			ListTypeDefinition listTypeDefinition =
				ListTypeDefinitionServiceUtil.getListTypeDefinition(
					cpSpecificationOptionListTypeDefinitionRel.
						getListTypeDefinitionId());

			if (listTypeDefinitions.contains(listTypeDefinition)) {
				continue;
			}

			listTypeDefinitions.add(listTypeDefinition);
		}

		return listTypeDefinitions;
	}

	@Override
	public long getListTypeDefinitionsCount() throws PortalException {
		List<ListTypeDefinition> listTypeDefinitions = getListTypeDefinitions();

		return listTypeDefinitions.size();
	}

	@Override
	public List<ListTypeEntry> getListTypeEntries() {
		return ListTypeEntryLocalServiceUtil.getListTypeEntries(
			TransformUtil.transformToLongArray(
				CPSpecificationOptionListTypeDefinitionRelLocalServiceUtil.
					getCPSpecificationOptionListTypeDefinitionRels(
						getCPSpecificationOptionId()),
				CPSpecificationOptionListTypeDefinitionRel::
					getListTypeDefinitionId));
	}

}