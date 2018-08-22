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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.portlet.action;

import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.constants.MicrosoftCognitiveServicesAssetAutoTagProviderConstants;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.constants.PortalSettingsMicrosoftCognitiveServicesAssetAutoTagProviderConstants;
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
public class
	MicrosoftCognitiveServicesAssetAutoTagProviderPortalSettingsFormContributor
		implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.empty();
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsMicrosoftCognitiveServicesAssetAutoTagProviderConstants.
			FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of(
			"/portal_settings" +
				"/document_library_asset_auto_tagger_microsoft_cognitive_" +
					"services");
	}

	@Override
	public String getSettingsId() {
		return MicrosoftCognitiveServicesAssetAutoTagProviderConstants.
			SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {
	}

}