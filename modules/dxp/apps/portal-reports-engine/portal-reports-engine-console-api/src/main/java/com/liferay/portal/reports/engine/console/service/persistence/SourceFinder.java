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

package com.liferay.portal.reports.engine.console.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface SourceFinder {
	public int countByG_N_DU(long groupId, String name, String driverUrl,
		boolean andOperator);

	public int filterCountByG_N_DU(long groupId, String name, String driverUrl,
		boolean andOperator);

	public java.util.List<com.liferay.portal.reports.engine.console.model.Source> filterFindByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Source> orderByComparator);

	public java.util.List<com.liferay.portal.reports.engine.console.model.Source> findByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Source> orderByComparator);
}