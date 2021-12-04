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

package com.liferay.remote.app.internal.data;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.remote.app.service.persistence.impl.constants.RemoteAppPersistenceConstants;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class RemoteAppDataCreator {

	@Activate
	protected void activate() throws PortalException {
		if (_createData) {
			_companyLocalService.forEachCompany(
				company -> {
					long count =
						_remoteAppEntryLocalService.getRemoteAppEntriesCount(
							company.getCompanyId());

					if (count != 0) {
						return;
					}

					RemoteAppDataUtil.addRemoteApp(
						_remoteAppEntryLocalService, _userLocalService,
						company);
				});
		}
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(release.bundle.symbolic.name=" + RemoteAppPersistenceConstants.BUNDLE_SYMBOLIC_NAME + ")",
		unbind = "-"
	)
	protected void setRelease(Release release, Map<String, Object> properties) {
		if (Objects.equals(release.getSchemaVersion(), "2.4.0") &&
			GetterUtil.getBoolean(properties.get("release.new"))) {

			_createData = true;
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private boolean _createData;

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}