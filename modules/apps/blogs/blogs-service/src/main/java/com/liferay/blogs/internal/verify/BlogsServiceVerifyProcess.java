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

package com.liferay.blogs.internal.verify;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.blogs.service",
	service = VerifyProcess.class
)
public class BlogsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	/**
	* @deprecated As of Judson (7.1.x), replaced by {@link
	* com.liferay.portal.kernel.upgrade.UpgradeStagingGroupTypeSettings}
	*/
	@Deprecated
	protected void updateStagedPortletNames() throws PortalException {
		throw new UnsupportedOperationException();
	}

	private GroupLocalService _groupLocalService;

}