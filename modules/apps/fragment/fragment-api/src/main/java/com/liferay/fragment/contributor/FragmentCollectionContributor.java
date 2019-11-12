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

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface FragmentCollectionContributor {

	public String getFragmentCollectionKey();

	public List<FragmentEntry> getFragmentEntries();

	public List<FragmentEntry> getFragmentEntries(int type);

	public default List<FragmentEntry> getFragmentEntries(
		int type, Locale locale) {

		return getFragmentEntries(type);
	}

	public default List<FragmentEntry> getFragmentEntries(Locale locale) {
		return getFragmentEntries();
	}

	public String getName();

	public default String getName(Locale locale) {
		return getName();
	}

	public Map<Locale, String> getNames();

	public default ResourceBundleLoader getResourceBundleLoader() {
		return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
	}

}