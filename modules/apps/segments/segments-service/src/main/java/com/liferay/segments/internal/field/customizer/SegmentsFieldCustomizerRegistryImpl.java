/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.field.customizer;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizerRegistry;

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
@Component(service = SegmentsFieldCustomizerRegistry.class)
public class SegmentsFieldCustomizerRegistryImpl
	implements SegmentsFieldCustomizerRegistry {

	@Override
	public SegmentsFieldCustomizer getSegmentsFieldCustomizer(
		String entityName, String fieldName) {

		List<SegmentsFieldCustomizer> segmentsFieldCustomizers =
			_getSegmentsFieldCustomizers(entityName);

		if (ListUtil.isEmpty(segmentsFieldCustomizers)) {
			return null;
		}

		for (SegmentsFieldCustomizer segmentsFieldCustomizer :
				segmentsFieldCustomizers) {

			List<String> fieldNames = segmentsFieldCustomizer.getFieldNames();

			if (fieldNames.contains(fieldName)) {
				return segmentsFieldCustomizer;
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SegmentsFieldCustomizer.class,
			"(segments.field.customizer.entity.name=*)",
			new FieldCustomizerServiceReferenceMapper(),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"segments.field.customizer.priority")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private List<SegmentsFieldCustomizer> _getSegmentsFieldCustomizers(
		String name) {

		if (Validator.isNull(name)) {
			return null;
		}

		List<SegmentsFieldCustomizer> segmentsFieldCustomizers =
			_serviceTrackerMap.getService(name);

		if (segmentsFieldCustomizers == null) {
			return Collections.emptyList();
		}

		return segmentsFieldCustomizers;
	}

	private ServiceTrackerMap<String, List<SegmentsFieldCustomizer>>
		_serviceTrackerMap;

	private class FieldCustomizerServiceReferenceMapper
		implements ServiceReferenceMapper<String, SegmentsFieldCustomizer> {

		@Override
		public void map(
			ServiceReference<SegmentsFieldCustomizer> serviceReference,
			Emitter<String> emitter) {

			List<String> entityNames = StringPlus.asList(
				serviceReference.getProperty(
					"segments.field.customizer.entity.name"));

			for (String entityName : entityNames) {
				emitter.emit(entityName);
			}
		}

	}

}