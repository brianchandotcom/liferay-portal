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

package com.liferay.asset.auto.tagger.web.internal.portlet.action;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.constants.AssetAutoTaggerConstants;
import com.liferay.asset.auto.tagger.web.internal.constants.PortalSettingsAssetAutoTaggerConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;
import com.liferay.portal.settings.portlet.action.PortalSettingsParameterUtil;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
public class AssetAutoTaggerPortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.empty();
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsAssetAutoTaggerConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of(PortalSettingsAssetAutoTaggerConstants.ACTION_NAME);
	}

	@Override
	public String getSettingsId() {
		return AssetAutoTaggerConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		int maximumNumberOfTagsPerAsset = GetterUtil.getInteger(
			PortalSettingsParameterUtil.getString(
				actionRequest, this, "maximumNumberOfTagsPerAsset"));

		AssetAutoTaggerConfiguration systemAssetAutoTaggerConfiguration =
			_assetAutoTaggerConfigurationFactory.
				getSystemAssetAutoTaggerConfiguration();

		int systemMaximumNumberOfTagsPerAsset =
			systemAssetAutoTaggerConfiguration.getMaximumNumberOfTagsPerAsset();

		if ((systemMaximumNumberOfTagsPerAsset != 0) &&
			((maximumNumberOfTagsPerAsset == 0) ||
			 (systemMaximumNumberOfTagsPerAsset <
				 maximumNumberOfTagsPerAsset))) {

			SessionErrors.add(
				actionRequest, "maximumNumberOfTagsPerAssetInvalid");
		}
	}

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

}