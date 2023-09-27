/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eduardo García
 */
@Component(service = SegmentsCriteriaContributorRegistry.class)
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

			if (ListUtil.isNotEmpty(classNameSegmentsCriteriaContributors)) {
				segmentsCriteriaContributors.addAll(
					classNameSegmentsCriteriaContributors);
			}
		}

		List<SegmentsCriteriaContributor> generalSegmentsCriteriaContributors =
			_serviceTrackerMap.getService("*");

		if (ListUtil.isNotEmpty(generalSegmentsCriteriaContributors)) {
			segmentsCriteriaContributors.addAll(
				generalSegmentsCriteriaContributors);
		}

		return segmentsCriteriaContributors;
	}

	@Override
	public List<SegmentsCriteriaContributor> getSegmentsCriteriaContributors(
		String className, Criteria.Type type) {

		return ListUtil.filter(
			getSegmentsCriteriaContributors(className),
			segmentsCriteriaContributor -> type.equals(
				segmentsCriteriaContributor.getType()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SegmentsCriteriaContributor.class,
			"(segments.criteria.contributor.model.class.name=*)",
			new SegmentsCriteriaContributorServiceReferenceMapper(),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
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