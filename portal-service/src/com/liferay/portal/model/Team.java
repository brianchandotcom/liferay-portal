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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the Team service. Represents a row in the &quot;Team&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TeamModel
 * @see com.liferay.portal.model.impl.TeamImpl
 * @see com.liferay.portal.model.impl.TeamModelImpl
 * @generated
 */
@ProviderType
public interface Team extends TeamModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.TeamImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Team, Long> TEAM_ID_ACCESSOR = new Accessor<Team, Long>() {
			@Override
			public Long get(Team team) {
				return team.getTeamId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Team> getTypeClass() {
				return Team.class;
			}
		};

	public static final Accessor<Team, String> NAME_ACCESSOR = new Accessor<Team, String>() {
			@Override
			public String get(Team team) {
				return team.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Team> getTypeClass() {
				return Team.class;
			}
		};

	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException;
}