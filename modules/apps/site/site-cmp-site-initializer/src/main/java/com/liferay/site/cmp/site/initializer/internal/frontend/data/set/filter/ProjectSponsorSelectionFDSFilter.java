/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author José Abelenda
 */
public class ProjectSponsorSelectionFDSFilter extends BaseSelectionFDSFilter {

	public ProjectSponsorSelectionFDSFilter(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.INTEGER;
	}

	@Override
	public String getId() {
		return "cmpProjectSponsorUserId";
	}

	@Override
	public String getLabel() {
		return "sponsor";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		for (User user :
				_userLocalService.getCompanyUsers(
					CompanyThreadLocal.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					user.getFullName(), user.getUserId()));
		}

		return selectionFDSFilterItems;
	}

	private final UserLocalService _userLocalService;

}