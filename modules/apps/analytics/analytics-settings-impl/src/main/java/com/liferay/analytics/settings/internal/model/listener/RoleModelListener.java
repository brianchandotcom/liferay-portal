/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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