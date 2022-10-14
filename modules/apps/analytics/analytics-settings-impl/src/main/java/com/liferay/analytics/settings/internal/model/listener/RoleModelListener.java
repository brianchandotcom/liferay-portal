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

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class RoleModelListener
	extends BaseAnalyticsDXPEntityModelListener<Role> {

	@Override
	public Class<?> getModelClass() {
		return Role.class;
	}

	@Override
	protected Role getModel(Object classPK) {
		return _roleLocalService.fetchRole((long)classPK);
	}

	@Override
	protected boolean isTracked(Role role) {
		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return true;
		}

		return false;
	}

	@Reference
	private RoleLocalService _roleLocalService;

}