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

package com.liferay.segments.vocabulary.field.customizer.internal.tracker;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.segments.vocabulary.field.customizer.internal.context.contributor.SegmentsVocabularyRequestContextContributor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	service = SegmentsVocabularyRequestContextContributorTracker.class
)
public class SegmentsVocabularyRequestContextContributorTracker {

	public SegmentsVocabularyRequestContextContributor getService(String key) {
		return _serviceTrackerMap.getService(key);
	}

	public List<SegmentsVocabularyRequestContextContributor> getServices() {
		Collection<SegmentsVocabularyRequestContextContributor> values =
			_serviceTrackerMap.values();

		Stream<SegmentsVocabularyRequestContextContributor> stream =
			values.stream();

		return stream.collect(Collectors.toList());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SegmentsVocabularyRequestContextContributor.class,
			"(request.context.contributor.key=*)",
			new ContextContributorServiceReferenceMapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap
		<String, SegmentsVocabularyRequestContextContributor>
			_serviceTrackerMap;

	private class ContextContributorServiceReferenceMapper
		implements ServiceReferenceMapper
			<String, SegmentsVocabularyRequestContextContributor> {

		public ContextContributorServiceReferenceMapper(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public void map(
			ServiceReference<SegmentsVocabularyRequestContextContributor>
				serviceReference,
			Emitter<String> emitter) {

			SegmentsVocabularyRequestContextContributor
				segmentsVocabularyRequestContextContributor =
					_bundleContext.getService(serviceReference);

			emitter.emit(segmentsVocabularyRequestContextContributor.getName());
		}

		private final BundleContext _bundleContext;

	}

}