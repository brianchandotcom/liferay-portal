/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the Team service. Represents a row in the &quot;Team&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TeamModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.TeamImpl")
@ProviderType
public interface Team extends PersistedModel, TeamModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.TeamImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Team, Long> TEAM_ID_ACCESSOR =
		new Accessor<Team, Long>() {

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
	public static final Accessor<Team, String> NAME_ACCESSOR =
		new Accessor<Team, String>() {

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

	public Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException;

}