/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.teams.web.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Team;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class TeamStagingModelListener extends BaseModelListener<Team> {

	@Override
	public void onAfterCreate(Team team) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(team);
	}

	@Override
	public void onAfterRemove(Team team) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(team);
	}

	@Override
	public void onAfterUpdate(Team originalTeam, Team team)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(team);
	}

	@Reference
	private StagingModelListener<Team> _stagingModelListener;

}