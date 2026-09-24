/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.control.panel.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mario Leandro
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL,
		"panel.category.order:Integer=20"
	},
	service = PanelCategory.class
)
public class InstancePanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return PanelCategoryKeys.CONTROL_PANEL_INSTANCE;
	}

	@Override
	public String getLabel(Locale locale) {
		Company company = _companyLocalService.fetchCompany(
			CompanyThreadLocal.getCompanyId());

		if (company == null) {
			return _language.get(locale, "instance");
		}

		return _language.format(locale, "instance-x", company.getName(), false);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

}