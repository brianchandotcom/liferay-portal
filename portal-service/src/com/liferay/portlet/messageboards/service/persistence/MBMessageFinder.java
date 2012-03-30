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
public interface MBMessageFinder {
	public int countByC_T(java.util.Date createDate, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_U_C_S(long groupId, long userId, long[] categoryIds,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_U_C_A_S(long groupId, long userId, long[] categoryIds,
		boolean anonymous, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_C(long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_C_S(long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_U_C_S(long groupId, long userId,
		long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_U_C_A_S(long groupId, long userId,
		long[] categoryIds, boolean anonymous, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Long> filterFindByG_C(long groupId,
		long[] categoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Long> filterFindByG_C_S(long groupId,
		long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Long> filterFindByG_U_C_S(long groupId,
		long userId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Long> filterFindByG_U_C_A_S(long groupId,
		long userId, long[] categoryIds, boolean anonymous, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByNoAssets()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Long> findByG_U_C_S(long groupId,
		long userId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Long> findByG_U_C_A_S(long groupId,
		long userId, long[] categoryIds, boolean anonymous, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;
}