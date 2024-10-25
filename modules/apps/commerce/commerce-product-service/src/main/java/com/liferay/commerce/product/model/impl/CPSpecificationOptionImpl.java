/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.commerce.product.service.CPOptionCategoryLocalServiceUtil;
import com.liferay.commerce.product.service.CPSOListTypeDefinitionRelLocalServiceUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;

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
	public List<ListTypeDefinition> getListTypeDefinitions() {
		List<CPSOListTypeDefinitionRel> cpsoListTypeDefinitionRels =
			CPSOListTypeDefinitionRelLocalServiceUtil.
				getCPSOListTypeDefinitionRels(getCPSpecificationOptionId());

		DynamicQuery dynamicQuery =
			ListTypeDefinitionLocalServiceUtil.dynamicQuery();

		Property listTypeDefinitionIdProperty = PropertyFactoryUtil.forName(
			"listTypeDefinitionId");

		dynamicQuery.add(
			listTypeDefinitionIdProperty.in(
				TransformUtil.transformToLongArray(
					cpsoListTypeDefinitionRels,
					CPSOListTypeDefinitionRel::getListTypeDefinitionId)));

		return ListTypeEntryLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	@Override
	public List<ListTypeEntry> getListTypeEntries() {
		List<CPSOListTypeDefinitionRel> cpsoListTypeDefinitionRels =
			CPSOListTypeDefinitionRelLocalServiceUtil.
				getCPSOListTypeDefinitionRels(getCPSpecificationOptionId());

		DynamicQuery dynamicQuery =
			ListTypeEntryLocalServiceUtil.dynamicQuery();

		Property listTypeDefinitionIdProperty = PropertyFactoryUtil.forName(
			"listTypeDefinitionId");

		dynamicQuery.add(
			listTypeDefinitionIdProperty.in(
				TransformUtil.transformToLongArray(
					cpsoListTypeDefinitionRels,
					CPSOListTypeDefinitionRel::getListTypeDefinitionId)));

		return ListTypeEntryLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

}