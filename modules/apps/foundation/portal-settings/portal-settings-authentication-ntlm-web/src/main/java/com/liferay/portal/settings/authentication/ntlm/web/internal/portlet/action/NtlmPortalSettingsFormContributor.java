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

package com.liferay.portal.settings.authentication.ntlm.web.internal.portlet.action;

import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;
import com.liferay.portal.settings.authentication.ntlm.web.internal.constants.PortalSettingsNtlmConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
public class NtlmPortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/ntlm_delete");
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsNtlmConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/ntlm");
	}

	@Override
	public String getSettingsId() {
		return NtlmConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {
	}

}