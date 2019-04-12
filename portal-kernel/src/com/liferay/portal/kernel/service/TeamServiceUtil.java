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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for Team. This utility wraps
 * <code>com.liferay.portal.service.impl.TeamServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TeamService
 * @generated
 */
@ProviderType
public class TeamServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.TeamServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Team addTeam(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeam(groupId, name, description, serviceContext);
	}

	public static void deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTeam(teamId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team>
			getGroupTeams(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupTeams(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeam(teamId);
	}

	public static com.liferay.portal.kernel.model.Team getTeam(
			long groupId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeam(groupId, name);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team>
			getUserTeams(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserTeams(userId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team>
			getUserTeams(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserTeams(userId, groupId);
	}

	public static boolean hasUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasUserTeam(userId, teamId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team> search(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Team> obc) {

		return getService().search(
			groupId, name, description, params, start, end, obc);
	}

	public static int searchCount(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params) {

		return getService().searchCount(groupId, name, description, params);
	}

	public static com.liferay.portal.kernel.model.Team updateTeam(
			long teamId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTeam(teamId, name, description);
	}

	public static TeamService getService() {
		if (_service == null) {
			_service = (TeamService)PortalBeanLocatorUtil.locate(
				TeamService.class.getName());
		}

		return _service;
	}

	private static TeamService _service;

}