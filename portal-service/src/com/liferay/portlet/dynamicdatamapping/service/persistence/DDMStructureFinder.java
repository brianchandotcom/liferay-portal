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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface DDMStructureFinder {
	public int countByKeywords(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_G_C_N_D_S_T(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_G_C_N_D_S_T(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] storageTypes,
		int type, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByKeywords(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByC_G_C_N_D_S_T(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByC_G_C_N_D_S_T(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] storageTypes,
		int type, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByKeywords(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByC_G_C_N_D_S_T(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByC_G_C_N_D_S_T(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] storageTypes, int type, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByKeywords(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByC_G_C_N_D_S_T(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByC_G_C_N_D_S_T(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] storageTypes, int type, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;
}