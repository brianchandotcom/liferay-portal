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

package com.liferay.message.boards.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface MBThreadFinder {

	public int countByG_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByG_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByG_U_LPD(
		long groupId, long userId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByG_U_A(
		long groupId, long userId, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByS_G_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByG_U_LPD_A(
		long groupId, long userId, java.util.Date lastPostDate,
		boolean includeAnonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int countByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int filterCountByG_C(long groupId, long categoryId);

	public int filterCountByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public int filterCountByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		filterFindByG_C(long groupId, long categoryId, int start, int end);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		filterFindByG_C(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		filterFindByS_G_U_C(
			long groupId, long userId, long[] categoryIds,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread> findByG_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread> findByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByG_U_C(
			long groupId, long userId, long[] categoryIds,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByG_U_LPD(
			long groupId, long userId, java.util.Date lastPostDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByG_U_A(
			long groupId, long userId, boolean anonymous,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByS_G_U(
			long groupId, long userId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByG_U_C_A(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByG_U_LPD_A(
			long groupId, long userId, java.util.Date lastPostDate,
			boolean includeAnonymous,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBThread>
		findByS_G_U_C(
			long groupId, long userId, long[] categoryIds,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition);

}