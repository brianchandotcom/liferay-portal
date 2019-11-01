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

package com.liferay.portal.properties.swapper.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.AccountLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true, service = {})
public class DefaultCompanyNameSwapper {

	@Activate
	protected void activate() {
		if (PropsHelperUtil.isCustomized(PropsKeys.COMPANY_DEFAULT_NAME)) {
			return;
		}

		String originalCompanyDefaultName = PropsValues.COMPANY_DEFAULT_NAME;

		PropsValues.COMPANY_DEFAULT_NAME = "Liferay DXP";

		try {
			Company defaultCompany = _companyLocalService.getCompany(
				PortalInstances.getDefaultCompanyId());

			if (!_hasCustomCompanyName(
					defaultCompany, originalCompanyDefaultName)) {

				_updateCompanyName(defaultCompany);
			}

			if (!Objects.equals(
					defaultCompany.getWebId(),
					PropsValues.COMPANY_DEFAULT_WEB_ID)) {

				defaultCompany = _companyLocalService.getCompanyByWebId(
					PropsValues.COMPANY_DEFAULT_WEB_ID);

				if (!_hasCustomCompanyName(
						defaultCompany, originalCompanyDefaultName)) {

					_updateCompanyName(defaultCompany);
				}
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to swap default company name", pe);
			}
		}
	}

	private boolean _hasCustomCompanyName(Company company, String defaultName)
		throws PortalException {

		String name = company.getName();

		if (Validator.isNotNull(name) && !name.equals(defaultName)) {
			return true;
		}

		return false;
	}

	private void _updateCompanyName(Company company) {
		try {
			Account account = _accountLocalService.getAccount(
				company.getAccountId());

			account.setName(PropsValues.COMPANY_DEFAULT_NAME);

			_accountLocalService.updateAccount(account);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to swap default company name for " + company, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultCompanyNameSwapper.class);

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

}