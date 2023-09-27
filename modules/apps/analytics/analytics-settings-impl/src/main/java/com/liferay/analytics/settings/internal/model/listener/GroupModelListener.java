/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class GroupModelListener
	extends BaseAnalyticsDXPEntityModelListener<Group> {

	@Override
	public Class<?> getModelClass() {
		return Group.class;
	}

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		super.onAfterRemove(group);

		if (!analyticsConfigurationRegistry.isActive() || !isTracked(group)) {
			return;
		}

		updateConfigurationProperties(
			group.getCompanyId(), "syncedGroupIds",
			String.valueOf(group.getGroupId()), "liferayAnalyticsGroupIds");
	}

	@Override
	protected Group getModel(Object classPK) {
		return _groupLocalService.fetchGroup((long)classPK);
	}

	@Override
	protected boolean isTracked(Group group) {
		if (group.isSite()) {
			return true;
		}

		return false;
	}

	@Reference
	private GroupLocalService _groupLocalService;

}