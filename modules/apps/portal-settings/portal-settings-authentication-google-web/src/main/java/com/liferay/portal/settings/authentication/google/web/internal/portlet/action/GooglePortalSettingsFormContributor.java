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

package com.liferay.portal.settings.authentication.google.web.internal.portlet.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.google.constants.GoogleConstants;
import com.liferay.portal.settings.authentication.google.web.internal.constants.PortalSettingsGoogleConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;
import com.liferay.portal.settings.portlet.action.PortalSettingsParameterUtil;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
public class GooglePortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/google_delete");
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsGoogleConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/google");
	}

	@Override
	public String getSettingsId() {
		return GoogleConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		boolean googleEnabled = PortalSettingsParameterUtil.getBoolean(
			actionRequest, this, "enabled");

		if (!googleEnabled) {
			return;
		}

		String googleClientId = PortalSettingsParameterUtil.getString(
			actionRequest, this, "clientId");
		String googleClientSecret = PortalSettingsParameterUtil.getString(
			actionRequest, this, "clientSecret");

		if (Validator.isNull(googleClientId)) {
			SessionErrors.add(actionRequest, "googleClientIdInvalid");
		}

		if (Validator.isNull(googleClientSecret)) {
			SessionErrors.add(actionRequest, "googleClientSecretInvalid");
		}
	}

}