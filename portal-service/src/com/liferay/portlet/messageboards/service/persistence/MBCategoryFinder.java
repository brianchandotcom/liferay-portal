/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Brian Wing Shun Chan
 */
public interface MBCategoryFinder {
	public int countByS_G_U_P(long groupId, long userId,
		long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByS_G_U_P(long groupId, long userId,
		long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByS_G_U_P(
		long groupId, long userId, long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByS_G_U_P(
		long groupId, long userId, long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException;
}