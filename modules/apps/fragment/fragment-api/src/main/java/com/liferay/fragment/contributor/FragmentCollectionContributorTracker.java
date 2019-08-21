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
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface FragmentCollectionContributorTracker {

	public FragmentCollectionContributor getFragmentCollectionContributor(
		String fragmentCollectionKey);

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by #getFragmentEntries
	 */
	@Deprecated
	public Map<String, FragmentEntry> getFragmentCollectionContributorEntries();

	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors();

	public Map<String, FragmentEntry> getFragmentEntries();

	public default Map<String, FragmentEntry> getFragmentEntries(
		Locale locale) {

		return getFragmentEntries();
	}

	public FragmentEntry getFragmentEntry(String fragmentEntryKey);

	public ResourceBundleLoader getResourceBundleLoader();

}