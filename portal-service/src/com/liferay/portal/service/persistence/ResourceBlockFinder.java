/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface ResourceBlockFinder {
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_G_N_R_A(
		long companyId, long groupId, java.lang.String name, long[] roleIds,
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException;
}