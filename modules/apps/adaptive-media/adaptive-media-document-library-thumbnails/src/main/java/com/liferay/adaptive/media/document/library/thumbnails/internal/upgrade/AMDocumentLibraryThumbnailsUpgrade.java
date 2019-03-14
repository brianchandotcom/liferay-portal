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

package com.liferay.adaptive.media.document.library.thumbnails.internal.upgrade;

import com.liferay.adaptive.media.document.library.thumbnails.internal.upgrade.v1_0_0.UpgradeDocumentLibraryThumbnailsConfiguration;
import com.liferay.adaptive.media.document.library.thumbnails.internal.util.AMCompanyThumbnailConfigurationInitializer;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AMDocumentLibraryThumbnailsUpgrade
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.1",
			new UpgradeDocumentLibraryThumbnailsConfiguration(
				_amCompanyThumbnailConfigurationInitializer,
				_companyLocalService));

		// See LPS-86356

		registry.register(
			"1.0.0", "1.0.1",
			new UpgradeDocumentLibraryThumbnailsConfiguration(
				_amCompanyThumbnailConfigurationInitializer,
				_companyLocalService));
	}

	@Reference
	private AMCompanyThumbnailConfigurationInitializer
		_amCompanyThumbnailConfigurationInitializer;

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

}