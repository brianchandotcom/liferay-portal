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

package com.liferay.fragment.contributor;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public interface FragmentCollectionContributor {

	public String getFragmentCollectionKey();

	public List<FragmentEntry> getFragmentEntries();

	public default List<FragmentEntry> getFragmentEntries(int type) {
		List<FragmentEntry> fragmentEntries = getFragmentEntries();

		return ListUtil.filter(
			fragmentEntries, fragmentEntry -> fragmentEntry.getType() == type);
	}

	public String getName();

}