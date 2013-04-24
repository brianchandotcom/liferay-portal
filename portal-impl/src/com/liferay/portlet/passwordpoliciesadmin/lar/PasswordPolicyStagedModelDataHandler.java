/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.passwordpoliciesadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
* @author Daniela Zapata Riesco
*/
public class PasswordPolicyStagedModelDataHandler
	extends BaseStagedModelDataHandler<PasswordPolicy> {

	public static final String[] CLASS_NAMES = {PasswordPolicy.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws Exception {

		Element passwordPolicyElement = portletDataContext.getExportDataElement(
			passwordPolicy);

		portletDataContext.addClassedModel(
			passwordPolicyElement,
			ExportImportPathUtil.getModelPath(passwordPolicy), passwordPolicy,
			PasswordPolicyPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws Exception {

		long userId = portletDataContext.getUserId(
			passwordPolicy.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			passwordPolicy, PasswordPolicyPortletDataHandler.NAMESPACE);

		PasswordPolicy existingPasswordPolicy =
			PasswordPolicyLocalServiceUtil.
				fetchPasswordPolicyByUuidAndCompanyId(
					passwordPolicy.getUuid(),
					portletDataContext.getCompanyId());

		if (existingPasswordPolicy == null) {
			existingPasswordPolicy =
				PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(
					portletDataContext.getCompanyId(),
					passwordPolicy.getName());
		}

		PasswordPolicy importedPasswordPolicy = null;

		if (existingPasswordPolicy == null) {
			serviceContext.setUuid(passwordPolicy.getUuid());

			importedPasswordPolicy =
				PasswordPolicyLocalServiceUtil.addPasswordPolicy(
					userId, passwordPolicy.isDefaultPolicy(),
					passwordPolicy.getName(), passwordPolicy.getDescription(),
					passwordPolicy.getChangeable(),
					passwordPolicy.isChangeRequired(),
					passwordPolicy.getMinAge(), passwordPolicy.getCheckSyntax(),
					passwordPolicy.isAllowDictionaryWords(),
					passwordPolicy.getMinAlphanumeric(),
					passwordPolicy.getMinLength(),
					passwordPolicy.getMinLowerCase(),
					passwordPolicy.getMinNumbers(),
					passwordPolicy.getMinSymbols(),
					passwordPolicy.getMinUpperCase(), passwordPolicy.getRegex(),
					passwordPolicy.isHistory(),
					passwordPolicy.getHistoryCount(),
					passwordPolicy.isExpireable(), passwordPolicy.getMaxAge(),
					passwordPolicy.getWarningTime(),
					passwordPolicy.getGraceLimit(), passwordPolicy.isLockout(),
					passwordPolicy.getMaxFailure(),
					passwordPolicy.getLockoutDuration(),
					passwordPolicy.getResetFailureCount(),
					passwordPolicy.getResetTicketMaxAge(), serviceContext);
		}
		else {
			importedPasswordPolicy =
				PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
					existingPasswordPolicy.getPasswordPolicyId(),
					passwordPolicy.getName(), passwordPolicy.getDescription(),
					passwordPolicy.isChangeable(),
					passwordPolicy.isChangeRequired(),
					passwordPolicy.getMinAge(), passwordPolicy.isCheckSyntax(),
					passwordPolicy.isAllowDictionaryWords(),
					passwordPolicy.getMinAlphanumeric(),
					passwordPolicy.getMinLength(),
					passwordPolicy.getMinLowerCase(),
					passwordPolicy.getMinNumbers(),
					passwordPolicy.getMinSymbols(),
					passwordPolicy.getMinUpperCase(), passwordPolicy.getRegex(),
					passwordPolicy.isHistory(),
					passwordPolicy.getHistoryCount(),
					passwordPolicy.isExpireable(), passwordPolicy.getMaxAge(),
					passwordPolicy.getWarningTime(),
					passwordPolicy.getGraceLimit(), passwordPolicy.isLockout(),
					passwordPolicy.getMaxFailure(),
					passwordPolicy.getLockoutDuration(),
					passwordPolicy.getResetFailureCount(),
					passwordPolicy.getResetTicketMaxAge(), serviceContext);
		}

		portletDataContext.importClassedModel(
			passwordPolicy, importedPasswordPolicy,
			PasswordPolicyPortletDataHandler.NAMESPACE);
	}

}