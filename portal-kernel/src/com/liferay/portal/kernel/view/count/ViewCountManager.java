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

package com.liferay.portal.kernel.view.count;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface ViewCountManager {

	public void deleteViewCount(long companyId, long classNameId, long classPK)
		throws PortalException;

	public long getViewCount(long companyId, long classNameId, long classPK);

	public Table<?> getViewCountEntryTable();

	public void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment);

	public boolean isViewCountEnabled();

	public boolean isViewCountEnabled(long classNameId);

}