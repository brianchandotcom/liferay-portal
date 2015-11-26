/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.password.policies.admin.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.PasswordPolicyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PasswordPolicyStagedModelDataHandler
	extends BaseStagedModelDataHandler<PasswordPolicy> {

	public static final String[] CLASS_NAMES = {PasswordPolicy.class.getName()};

	@Override
	public void deleteStagedModel(PasswordPolicy passwordPolicy)
		throws PortalException {

		_passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		PasswordPolicy passwordPolicy =
			_passwordPolicyLocalService.fetchPasswordPolicyByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (passwordPolicy != null) {
			deleteStagedModel(passwordPolicy);
		}
	}

	@Override
	public List<PasswordPolicy> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<PasswordPolicy> passwordPolicies = new ArrayList<>();

		passwordPolicies.add(
			_passwordPolicyLocalService.fetchPasswordPolicyByUuidAndCompanyId(
				uuid, companyId));

		return passwordPolicies;
	}

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
			ExportImportPathUtil.getModelPath(passwordPolicy), passwordPolicy);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws Exception {

		long userId = portletDataContext.getUserId(
			passwordPolicy.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			passwordPolicy);

		PasswordPolicy existingPasswordPolicy =
			_passwordPolicyLocalService.fetchPasswordPolicyByUuidAndCompanyId(
				passwordPolicy.getUuid(), portletDataContext.getCompanyId());

		if (existingPasswordPolicy == null) {
			existingPasswordPolicy =
				_passwordPolicyLocalService.fetchPasswordPolicy(
					portletDataContext.getCompanyId(),
					passwordPolicy.getName());
		}

		PasswordPolicy importedPasswordPolicy = null;

		if (existingPasswordPolicy == null) {
			serviceContext.setUuid(passwordPolicy.getUuid());

			importedPasswordPolicy =
				_passwordPolicyLocalService.addPasswordPolicy(
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
				_passwordPolicyLocalService.updatePasswordPolicy(
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
			passwordPolicy, importedPasswordPolicy);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setPasswordPolicyLocalService(
		PasswordPolicyLocalService passwordPolicyLocalService) {

		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	private volatile GroupLocalService _groupLocalService;
	private volatile PasswordPolicyLocalService _passwordPolicyLocalService;

}