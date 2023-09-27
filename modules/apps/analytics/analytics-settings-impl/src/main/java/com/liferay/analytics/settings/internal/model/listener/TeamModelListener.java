/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.TeamLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class TeamModelListener
	extends BaseAnalyticsDXPEntityModelListener<Team> {

	@Override
	public Class<?> getModelClass() {
		return Team.class;
	}

	@Override
	protected Team getModel(Object classPK) {
		return _teamLocalService.fetchTeam((long)classPK);
	}

	@Reference
	private TeamLocalService _teamLocalService;

}