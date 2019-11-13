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

package com.liferay.item.selector.provider;

import com.liferay.portal.kernel.model.Group;

import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public interface GroupItemSelectorProvider {

	public int getGroupCount(long companyId, long groupId, String keywords);

	public List<Group> getGroups(
		long companyId, long groupId, String keywords, int start, int end);

	public String getIcon();

	public String getLabel(Locale locale);

}