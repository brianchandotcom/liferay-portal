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

package com.liferay.portlet.journal.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface JournalTemplateFinder {
	public int countByKeywords(long companyId, long[] groupIds,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_G_T_S_N_D(long companyId, long[] groupIds,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByKeywords(long companyId, long[] groupIds,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByC_G_T_S_N_D(long companyId, long[] groupIds,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByKeywords(
		long companyId, long[] groupIds, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByC_G_T_S_N_D(
		long companyId, long[] groupIds, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByKeywords(
		long companyId, long[] groupIds, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByC_G_T_S_N_D(
		long companyId, long[] groupIds, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;
}