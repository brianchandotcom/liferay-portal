/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	protected AnalyticsEntityModel<Role> getAnalyticsEntityModel() {
		return _roleAnalyticsEntityModel;
	}

	@Reference(target = "(analytics.entity.model.type=role)")
	private AnalyticsEntityModel<Role> _roleAnalyticsEntityModel;

}