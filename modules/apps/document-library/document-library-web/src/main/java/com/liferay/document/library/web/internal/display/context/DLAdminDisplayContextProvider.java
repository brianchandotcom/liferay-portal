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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DLAdminDisplayContextProvider.class)
public class DLAdminDisplayContextProvider {

	public DLAdminDisplayContext getDLAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			httpServletRequest);

		return new DLAdminDisplayContext(
			dlRequestHelper.getLiferayPortletRequest(),
			dlRequestHelper.getLiferayPortletResponse(), _versioningStrategy);
	}

	public DLAdminManagementToolbarDisplayContext
			getDLAdminManagementToolbarDisplayContext(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
		throws PortalException {

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			httpServletRequest);

		return new DLAdminManagementToolbarDisplayContext(
			dlRequestHelper.getLiferayPortletRequest(),
			dlRequestHelper.getLiferayPortletResponse(), httpServletRequest,
			getDLAdminDisplayContext(httpServletRequest, httpServletResponse));
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile VersioningStrategy _versioningStrategy;

}