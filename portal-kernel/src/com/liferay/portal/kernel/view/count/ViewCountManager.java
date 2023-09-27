/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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