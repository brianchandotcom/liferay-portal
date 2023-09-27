/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.legacy.groupby;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.search.groupby.GroupByRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface GroupByRequestFactory {

	/**
	 * Provides a GroupByRequest object based off a legacy GroupBy object.
	 *
	 * @param  groupBy the legacy GroupBy object to be converted
	 * @return the converted GroupByRequest object
	 * @review
	 */
	public GroupByRequest getGroupByRequest(GroupBy groupBy);

}