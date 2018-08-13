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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.portlet.action;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.constants.PortalSettingsTensorflowAssetAutoTagProviderConstants;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.constants.TensorflowAssetAutoTagProviderConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
public class TensorflowAssetAutoTagProviderPortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.empty();
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsTensorflowAssetAutoTagProviderConstants.
			FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of(
			"/portal_settings/document_library_asset_auto_tagger_tensorflow");
	}

	@Override
	public String getSettingsId() {
		return TensorflowAssetAutoTagProviderConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {
	}

}