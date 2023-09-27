/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = CTDisplayRenderer.class)
public class CompanyCTDisplayRenderer extends BaseCTDisplayRenderer<Company> {

	@Override
	public Class<Company> getModelClass() {
		return Company.class;
	}

	@Override
	public String getTitle(Locale locale, Company company)
		throws PortalException {

		return company.getName();
	}

	@Override
	public String getTypeName(Locale locale) {
		return _language.get(locale, "company");
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Company> displayBuilder)
		throws PortalException {

		Company company = displayBuilder.getModel();

		displayBuilder.display(
			"company-id", company.getCompanyId()
		).display(
			"name", company.getName()
		).display(
			"virtual-host", company.getVirtualHostname()
		).display(
			"mail-domain", company.getMx()
		).display(
			"num-of-users",
			_userLocalService.searchCount(
				company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
				null)
		).display(
			"max-num-of-users",
			() -> {
				int maxUsers = company.getMaxUsers();

				if (maxUsers > 0) {
					return maxUsers;
				}

				return _language.get(displayBuilder.getLocale(), "unlimited");
			}
		).display(
			"active",
			() -> {
				if (company.isActive()) {
					return _language.get(displayBuilder.getLocale(), "yes");
				}

				return _language.get(displayBuilder.getLocale(), "no");
			}
		);
	}

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}