/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Fábio Alves
 */
public class TagSelectionFDSFilter extends BaseSelectionFDSFilter {

	public TagSelectionFDSFilter(
		AssetTagLocalService assetTagLocalService, long[] groupIds) {

		_assetTagLocalService = assetTagLocalService;
		_groupIds = groupIds;
	}

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.STRING;
	}

	@Override
	public String getId() {
		return "keywords";
	}

	@Override
	public String getLabel() {
		return "tag";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		Set<String> assetTagNames = new HashSet<>();

		return TransformUtil.transform(
			_assetTagLocalService.getGroupsTags(_groupIds),
			assetTag -> {
				if (!assetTagNames.add(assetTag.getName())) {
					return null;
				}

				return new SelectionFDSFilterItem(
					assetTag.getName(), assetTag.getName());
			});
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	private final AssetTagLocalService _assetTagLocalService;
	private final long[] _groupIds;

}