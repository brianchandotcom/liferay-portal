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

package com.liferay.commerce.media.internal;

import com.liferay.commerce.media.CommerceMediaHttpHelper;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.media.internal.configuration.CommerceMediaDefaultImageConfiguration;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(enabled = false, service = CommerceMediaHttpHelper.class)
public class CommerceMediaHttpHelperImpl implements CommerceMediaHttpHelper {

	public FileEntry getDefaultImageFileEntry(
			long groupId, HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceMediaDefaultImageConfiguration
			commerceMediaDefaultImageConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceMediaDefaultImageConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId, CommerceMediaConstants.SERVICE_NAME));

		FileEntry fileEntry = null;

		try {
			fileEntry = _dlAppLocalService.getFileEntry(
				commerceMediaDefaultImageConfiguration.defaultFileEntryId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			Company company = _portal.getCompany(httpServletRequest);

			fileEntry =
				_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
					company.getGroupId(), PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO);
		}

		return fileEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMediaHttpHelperImpl.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

}