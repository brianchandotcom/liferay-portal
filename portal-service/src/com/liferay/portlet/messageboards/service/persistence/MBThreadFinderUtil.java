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

package com.liferay.portlet.messageboards.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class MBThreadFinderUtil {
	public static int countByG_U(long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().countByG_U(groupId, userId, queryDefinition);
	}

	public static int countByG_C(long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().countByG_C(groupId, categoryId, queryDefinition);
	}

	public static int countByG_U_C(long groupId, long userId,
		long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .countByG_U_C(groupId, userId, categoryIds, queryDefinition);
	}

	public static int countByG_U_LPD(long groupId, long userId,
		java.util.Date lastPostDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .countByG_U_LPD(groupId, userId, lastPostDate,
			queryDefinition);
	}

	public static int countByG_U_A(long groupId, long userId,
		boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .countByG_U_A(groupId, userId, anonymous, queryDefinition);
	}

	public static int countByS_G_U(long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().countByS_G_U(groupId, userId, queryDefinition);
	}

	public static int countByG_U_C_A(long groupId, long userId,
		long[] categoryIds, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .countByG_U_C_A(groupId, userId, categoryIds, anonymous,
			queryDefinition);
	}

	public static int countByS_G_U_C(long groupId, long userId,
		long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .countByS_G_U_C(groupId, userId, categoryIds, queryDefinition);
	}

	public static int filterCountByG_C(long groupId, long categoryId) {
		return getFinder().filterCountByG_C(groupId, categoryId);
	}

	public static int filterCountByG_C(long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().filterCountByG_C(groupId, categoryId, queryDefinition);
	}

	public static int filterCountByS_G_U_C(long groupId, long userId,
		long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .filterCountByS_G_U_C(groupId, userId, categoryIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end) {
		return getFinder().filterFindByG_C(groupId, categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().filterFindByG_C(groupId, categoryId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .filterFindByS_G_U_C(groupId, userId, categoryIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().findByG_U(groupId, userId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().findByG_C(groupId, categoryId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .findByG_U_C(groupId, userId, categoryIds, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_LPD(
		long groupId, long userId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .findByG_U_LPD(groupId, userId, lastPostDate, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_A(
		long groupId, long userId, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .findByG_U_A(groupId, userId, anonymous, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder().findByS_G_U(groupId, userId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .findByG_U_C_A(groupId, userId, categoryIds, anonymous,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getFinder()
				   .findByS_G_U_C(groupId, userId, categoryIds, queryDefinition);
	}

	public static MBThreadFinder getFinder() {
		if (_finder == null) {
			_finder = (MBThreadFinder)PortalBeanLocatorUtil.locate(MBThreadFinder.class.getName());

			ReferenceRegistry.registerReference(MBThreadFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(MBThreadFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(MBThreadFinderUtil.class, "_finder");
	}

	private static MBThreadFinder _finder;
}