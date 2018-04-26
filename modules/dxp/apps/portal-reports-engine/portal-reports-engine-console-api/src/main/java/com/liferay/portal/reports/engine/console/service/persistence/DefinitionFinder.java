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
public interface DefinitionFinder {
	public int countByG_S_N_D_RN(long groupId, String name, String description,
		long sourceId, String reportName, boolean andOperator);

	public int filterCountByG_S_N_D_RN(long groupId, String name,
		String description, long sourceId, String reportName,
		boolean andOperator);

	public java.util.List<com.liferay.portal.reports.engine.console.model.Definition> filterFindByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Definition> orderByComparator);

	public java.util.List<com.liferay.portal.reports.engine.console.model.Definition> findByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Definition> orderByComparator);
}