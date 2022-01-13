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

package com.liferay.document.library.web.internal.servlet;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.web.internal.configuration.CacheControlConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.http.header.customizer.FileEntryHttpHeaderCustomizer;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.web.internal.configuration.CacheControlConfiguration",
	property = "http.header.name=" + HttpHeaders.CACHE_CONTROL,
	service = FileEntryHttpHeaderCustomizer.class
)
public class CacheControlFileEntryHttpHeaderCustomizer
	implements FileEntryHttpHeaderCustomizer {

	@Override
	public String getHttpHeaderValue(FileEntry fileEntry, String currentValue) {
		try {
			Company company = _companyLocalService.getCompany(
				fileEntry.getCompanyId());

			if (!_dlFileEntryModelResourcePermission.contains(
					PermissionCheckerFactoryUtil.create(
						company.getDefaultUser()),
					fileEntry.getPrimaryKey(), ActionKeys.VIEW)) {

				return currentValue;
			}

			if (_cacheControlConfiguration.maxAge() <= 0) {
				return _cacheControlConfiguration.cacheControl();
			}

			return String.format(
				"%s, max-age: %d", _cacheControlConfiguration.cacheControl(),
				_cacheControlConfiguration.maxAge());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return currentValue;
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cacheControlConfiguration = ConfigurableUtil.createConfigurable(
			CacheControlConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CacheControlFileEntryHttpHeaderCustomizer.class);

	private volatile CacheControlConfiguration _cacheControlConfiguration;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission;

}