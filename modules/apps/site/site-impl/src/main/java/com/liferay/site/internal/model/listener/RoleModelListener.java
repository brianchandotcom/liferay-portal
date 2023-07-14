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

package com.liferay.site.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.site.configuration.manager.MenuAccessConfigurationManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onAfterCreate(Role role) throws ModelListenerException {
		if ((role.getType() == RoleConstants.TYPE_REGULAR) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			try {
				_menuAccessConfigurationManager.addAccessRoleToControlMenu(
					role);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	@Override
	public void onAfterRemove(Role role) throws ModelListenerException {
		if ((role.getType() == RoleConstants.TYPE_REGULAR) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			try {
				_menuAccessConfigurationManager.deleteRoleAccessToControlMenu(
					role);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleModelListener.class);

	@Reference
	private MenuAccessConfigurationManager _menuAccessConfigurationManager;

}