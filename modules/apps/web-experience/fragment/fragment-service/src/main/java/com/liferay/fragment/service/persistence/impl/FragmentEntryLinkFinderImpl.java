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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.persistence.FragmentEntryLinkFinder;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryLinkFinderImpl
	extends FragmentEntryLinkFinderBaseImpl implements FragmentEntryLinkFinder {

	public static final String COUNT_BY_G_F_C_L =
		FragmentEntryLinkFinder.class.getName() + ".countByG_F_C_L";

	public static final String FIND_BY_G_F_C_L =
		FragmentEntryLinkFinder.class.getName() + ".findByG_F_C_L";

	@Override
	public int countByG_F_C_L(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateEntryType) {

		return 0;
	}

	@Override
	public List<FragmentEntryLink> findByG_F_C_L(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateEntryType, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return null;
	}

}