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

package com.liferay.portal.settings.authentication.cas.web.internal.portlet.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.cas.constants.CASConstants;
import com.liferay.portal.settings.authentication.cas.web.internal.constants.PortalSettingsCASConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;
import com.liferay.portal.settings.portlet.action.PortalSettingsParameterUtil;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
public class CASPortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/cas_delete");
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/cas");
	}

	@Override
	public String getSettingsId() {
		return CASConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		boolean casEnabled = PortalSettingsParameterUtil.getBoolean(
			actionRequest, this, "enabled");

		if (!casEnabled) {
			return;
		}

		String casLoginURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "loginURL");
		String casLogoutURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "logoutURL");
		String casServerName = PortalSettingsParameterUtil.getString(
			actionRequest, this, "serverName");
		String casServerURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "serverURL");
		String casServiceURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "serviceURL");
		String casNoSuchUserRedirectURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "noSuchUserRedirectURL");

		if (!Validator.isUrl(casLoginURL)) {
			SessionErrors.add(actionRequest, "casLoginURLInvalid");
		}

		if (!Validator.isUrl(casLogoutURL)) {
			SessionErrors.add(actionRequest, "casLogoutURLInvalid");
		}

		if (Validator.isNull(casServerName)) {
			SessionErrors.add(actionRequest, "casServerNameInvalid");
		}

		if (!Validator.isUrl(casServerURL)) {
			SessionErrors.add(actionRequest, "casServerURLInvalid");
		}

		if (Validator.isNotNull(casServiceURL) &&
			!Validator.isUrl(casServiceURL)) {

			SessionErrors.add(actionRequest, "casServiceURLInvalid");
		}

		if (Validator.isNotNull(casNoSuchUserRedirectURL) &&
			!Validator.isUrl(casNoSuchUserRedirectURL)) {

			SessionErrors.add(actionRequest, "casNoSuchUserURLInvalid");
		}
	}

}