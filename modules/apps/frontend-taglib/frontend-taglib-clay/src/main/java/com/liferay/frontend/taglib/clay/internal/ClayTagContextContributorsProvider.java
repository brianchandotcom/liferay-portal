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

package com.liferay.frontend.taglib.clay.internal;

import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTagContextContributor;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = {})
public class ClayTagContextContributorsProvider {

	public static List<ClayTagContextContributor> getClayTagContextContributors(
		String clayTagContextContributorKey) {

		if (_clayTagContextContributorsProvider == null) {
			_log.error(
				"Unable to get list of Clay tag context contributors for key " +
					clayTagContextContributorKey);

			return Collections.emptyList();
		}

		ServiceTrackerMap<String, List<ClayTagContextContributor>>
			clayTagContextContributors =
				_clayTagContextContributorsProvider._clayTagContextContributors;

		return clayTagContextContributors.getService(
			clayTagContextContributorKey);
	}

	public ClayTagContextContributorsProvider() {
		_clayTagContextContributorsProvider = this;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_clayTagContextContributors =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ClayTagContextContributor.class,
				"(clay.tag.context.contributor.key=*)",
				new PropertyServiceReferenceMapper<>(
					"clay.tag.context.contributor.key"),
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_clayTagContextContributors.close();

		_clayTagContextContributors = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayTagContextContributorsProvider.class);

	private static ClayTagContextContributorsProvider
		_clayTagContextContributorsProvider;

	private ServiceTrackerMap<String, List<ClayTagContextContributor>>
		_clayTagContextContributors;

}