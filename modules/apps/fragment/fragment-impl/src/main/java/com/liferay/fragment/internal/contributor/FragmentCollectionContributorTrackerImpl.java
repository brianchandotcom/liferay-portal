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

package com.liferay.fragment.internal.contributor;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, service = FragmentCollectionContributorTracker.class
)
public class FragmentCollectionContributorTrackerImpl
	implements FragmentCollectionContributorTracker {

	@Override
	public Map<String, List<FragmentCollectionContributor>>
		getFragmentCollectionContributors() {

		return new HashMap<>(_fragmentCollectionContributor);
	}

	@Override
	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors(String key) {

		return new ArrayList<>(_fragmentCollectionContributor.get(key));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setFragmentCollectionContributor(
		FragmentCollectionContributor fragmentCollectionContributor) {

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributor.computeIfAbsent(
				fragmentCollectionContributor.getKey(),
				type -> new ArrayList<>());

		fragmentCollectionContributors.add(fragmentCollectionContributor);
	}

	protected void unsetFragmentCollectionContributor(
		FragmentCollectionContributor fragmentCollectionContributor) {

		_fragmentCollectionContributor.computeIfPresent(
			fragmentCollectionContributor.getKey(),
			(key, fragmentCollectionContributorList) -> {
				fragmentCollectionContributorList.remove(
					fragmentCollectionContributor);

				if (ListUtil.isEmpty(fragmentCollectionContributorList)) {
					return null;
				}

				return fragmentCollectionContributorList;
			});
	}

	private final Map<String, List<FragmentCollectionContributor>>
		_fragmentCollectionContributor = new ConcurrentHashMap<>();

}