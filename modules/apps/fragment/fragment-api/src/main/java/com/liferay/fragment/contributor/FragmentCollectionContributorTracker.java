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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public interface FragmentCollectionContributorTracker {

	public default Map<String, FragmentEntry>
		getFragmentCollectionContributorEntries() {

		Map<String, FragmentEntry> fragmentEntriesMap = new HashMap<>();

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			getFragmentCollectionContributors();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			List<FragmentEntry> fragmentEntries =
				fragmentCollectionContributor.getFragmentEntries();

			for (FragmentEntry fragmentEntry : fragmentEntries) {
				fragmentEntriesMap.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);
			}
		}

		return fragmentEntriesMap;
	}

	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors();

}