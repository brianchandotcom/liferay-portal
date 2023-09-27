/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eduardo Lundgren
 * @generated
 */
@ProviderType
public interface CalendarFinder {

	public int countByKeywords(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String keywords);

	public int countByC_G_C_N_D(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String name, String description, boolean andOperator);

	public int countByC_G_C_N_D(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String[] names, String[] descriptions, boolean andOperator);

	public int filterCountByKeywords(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String keywords);

	public int filterCountByC_G_C_N_D(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String name, String description, boolean andOperator);

	public int filterCountByC_G_C_N_D(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String[] names, String[] descriptions, boolean andOperator);

	public java.util.List<com.liferay.calendar.model.Calendar>
		filterFindByKeywords(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator);

	public java.util.List<com.liferay.calendar.model.Calendar>
		filterFindByC_G_C_N_D(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator);

	public java.util.List<com.liferay.calendar.model.Calendar>
		filterFindByC_G_C_N_D(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String[] names, String[] descriptions, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator);

	public java.util.List<com.liferay.calendar.model.Calendar> findByKeywords(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.calendar.model.Calendar> orderByComparator);

	public java.util.List<com.liferay.calendar.model.Calendar> findByC_G_C_N_D(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String name, String description, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.calendar.model.Calendar> orderByComparator);

	public java.util.List<com.liferay.calendar.model.Calendar> findByC_G_C_N_D(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		String[] names, String[] descriptions, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.calendar.model.Calendar> orderByComparator);

}