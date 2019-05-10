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
public interface MBCategoryFinder {

	public int countC_ByG_P(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int countC_ByS_G_U_P(
		long groupId, long userId, long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBCategory> queryDefinition);

	public int countC_T_ByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountC_ByG_P(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountC_ByS_G_U_P(
		long groupId, long userId, long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBCategory> queryDefinition);

	public int filterCountC_T_ByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBCategory>
		filterFindC_ByG_P(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBCategory> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBCategory>
		filterFindC_ByS_G_U_P(
			long groupId, long userId, long[] parentCategoryIds,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBCategory> queryDefinition);

	public java.util.List<Object> filterFindC_T_ByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBCategory>
		findC_ByG_P(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBCategory> queryDefinition);

	public java.util.List<com.liferay.message.boards.model.MBCategory>
		findC_ByS_G_U_P(
			long groupId, long userId, long[] parentCategoryIds,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBCategory> queryDefinition);

	public java.util.List<Object> findC_T_ByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

}