/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		if (!analyticsConfigurationRegistry.isActive() || isExcluded(group)) {
			return;
		}

		updateConfigurationProperties(
			group.getCompanyId(), "syncedGroupIds",
			String.valueOf(group.getGroupId()), "liferayAnalyticsGroupIds");
	}

	@Override
	protected AnalyticsEntityModel<Group> getAnalyticsEntityModel() {
		return _groupAnalyticsEntityModel;
	}

	@Override
	protected Group getModel(long id) throws Exception {
		return _groupLocalService.getGroup(id);
	}

	@Override
	protected boolean isExcluded(Group group) {
		if (!group.isSite()) {
			return true;
		}

		return false;
	}

	@Reference(target = "(analytics.entity.model.type=group)")
	private AnalyticsEntityModel<Group> _groupAnalyticsEntityModel;

	@Reference
	private GroupLocalService _groupLocalService;

}