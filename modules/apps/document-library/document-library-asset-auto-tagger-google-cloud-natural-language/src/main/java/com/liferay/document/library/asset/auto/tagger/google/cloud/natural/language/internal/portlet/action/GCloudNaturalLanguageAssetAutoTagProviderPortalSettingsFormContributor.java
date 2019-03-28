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

package com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.portlet.action;

import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.constants.GCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.constants.PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
public class
	GCloudNaturalLanguageAssetAutoTagProviderPortalSettingsFormContributor
		implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.empty();
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants.
			FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of(
			"/portal_settings/document_library_asset_auto_tagger_google_" +
				"cloud_natural_language");
	}

	@Override
	public String getSettingsId() {
		return GCloudNaturalLanguageAssetAutoTagProviderConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {
	}

}