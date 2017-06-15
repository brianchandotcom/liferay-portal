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

package com.liferay.vulcan.liferay.internal.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.vulcan.liferay.context.CurrentGroup;
import com.liferay.vulcan.provider.Provider;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CurrentGroupProvider implements Provider<CurrentGroup> {

	@Override
	public CurrentGroup createContext(HttpServletRequest httpServletRequest) {
		Long groupId = GroupThreadLocal.getGroupId();

		return () -> {
			try {
				return _groupLocalService.getGroup(groupId);
			}
			catch (PortalException pe) {
				throw new NotFoundException(pe);
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

}