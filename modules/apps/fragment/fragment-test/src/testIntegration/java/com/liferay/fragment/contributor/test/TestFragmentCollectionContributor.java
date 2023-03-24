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

package com.liferay.fragment.contributor.test;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class TestFragmentCollectionContributor
	implements FragmentCollectionContributor {

	public TestFragmentCollectionContributor(
		String fragmentCollectionKey,
		Map<Integer, FragmentEntry> fragmentEntriesMap) {

		_fragmentCollectionKey = fragmentCollectionKey;
		_fragmentEntriesMap = fragmentEntriesMap;
	}

	@Override
	public String getFragmentCollectionKey() {
		return _fragmentCollectionKey;
	}

	@Override
	public List<FragmentEntry> getFragmentEntries() {
		return Collections.emptyList();
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(int type) {
		FragmentEntry fragmentEntry = _fragmentEntriesMap.get(type);

		if (fragmentEntry != null) {
			return Collections.singletonList(fragmentEntry);
		}

		return Collections.emptyList();
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(int[] types) {
		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		for (int type : types) {
			FragmentEntry fragmentEntry = _fragmentEntriesMap.get(type);

			if (fragmentEntry != null) {
				fragmentEntries.add(fragmentEntry);
			}
		}

		return fragmentEntries;
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(Locale locale) {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "Test Fragment Collection Contributor";
	}

	@Override
	public Map<Locale, String> getNames() {
		return HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), getName()
		).build();
	}

	private final String _fragmentCollectionKey;
	private final Map<Integer, FragmentEntry> _fragmentEntriesMap;

}