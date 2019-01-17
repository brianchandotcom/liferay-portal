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

package com.liferay.segments.internal.criteria.contributor;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true, service = SegmentsCriteriaContributorRegistry.class
)
public class SegmentsCriteriaContributorRegistryImpl
	implements SegmentsCriteriaContributorRegistry {

	@Override
	public List<SegmentsCriteriaContributor> getSegmentsCriteriaContributors(
		String className) {

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			new ArrayList<>();

		if (Validator.isNotNull(className)) {
			List<SegmentsCriteriaContributor>
				classNameSegmentsCriteriaContributors =
					_serviceTrackerMap.getService(className);

			if (!ListUtil.isEmpty(classNameSegmentsCriteriaContributors)) {
				segmentsCriteriaContributors.addAll(
					classNameSegmentsCriteriaContributors);
			}
		}

		List<SegmentsCriteriaContributor> generalSegmentsCriteriaContributors =
			_serviceTrackerMap.getService("*");

		if (!ListUtil.isEmpty(generalSegmentsCriteriaContributors)) {
			segmentsCriteriaContributors.addAll(
				generalSegmentsCriteriaContributors);
		}

		return segmentsCriteriaContributors;
	}

	@Override
	public List<SegmentsCriteriaContributor> getSegmentsCriteriaContributors(
		String className, Criteria.Type type) {

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			getSegmentsCriteriaContributors(className);

		Stream<SegmentsCriteriaContributor> stream =
			segmentsCriteriaContributors.stream();

		return stream.filter(
			segmentsCriteriaContributor -> type.equals(
				segmentsCriteriaContributor.getType())
		).collect(
			Collectors.toList()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SegmentsCriteriaContributor.class,
			"(segments.criteria.contributor.model.class.name=*)",
			new SegmentsCriteriaContributorServiceReferenceMapper(),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator(
					"segments.criteria.contributor.priority")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<SegmentsCriteriaContributor>>
		_serviceTrackerMap;

	private class SegmentsCriteriaContributorServiceReferenceMapper
		implements ServiceReferenceMapper<String, SegmentsCriteriaContributor> {

		@Override
		public void map(
			ServiceReference<SegmentsCriteriaContributor> serviceReference,
			Emitter<String> emitter) {

			String modelClassName = (String)serviceReference.getProperty(
				"segments.criteria.contributor.model.class.name");

			emitter.emit(modelClassName);
		}

	}

}