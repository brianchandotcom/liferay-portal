/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.provider;

import com.liferay.portal.kernel.model.Group;

import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public interface GroupItemSelectorProvider {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getEmptyResultsMessage();

	public default String getEmptyResultsMessage(Locale locale) {
		return getEmptyResultsMessage();
	}

	public List<Group> getGroups(
		long companyId, long groupId, String keywords, int start, int end);

	public int getGroupsCount(long companyId, long groupId, String keywords);

	public String getGroupType();

	public String getIcon();

	public String getLabel(Locale locale);

	public default boolean isEnabled() {
		return true;
	}

}