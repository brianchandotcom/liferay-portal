/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.collection.filter;

import aQute.bnd.annotation.ProviderType;

import java.util.List;

/**
 * @author Pablo Molina
 */
@ProviderType
public interface FragmentCollectionFilterRegistry {

	public FragmentCollectionFilter getFragmentCollectionFilter(String key);

	public List<FragmentCollectionFilter> getFragmentCollectionFilters();

}