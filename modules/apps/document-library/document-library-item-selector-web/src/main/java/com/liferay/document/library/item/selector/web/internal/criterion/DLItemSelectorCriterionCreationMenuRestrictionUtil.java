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

package com.liferay.document.library.item.selector.web.internal.criterion;

import com.liferay.document.library.item.selector.criterion.DLItemSelectorCriterionCreationMenuRestriction;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = {})
public class DLItemSelectorCriterionCreationMenuRestrictionUtil {

	public static Set<String> getAllowedCreationMenuUIItemKeys(
		ItemSelectorCriterion itemSelectorCriterion) {

		Class<? extends ItemSelectorCriterion> clazz =
			itemSelectorCriterion.getClass();

		List<DLItemSelectorCriterionCreationMenuRestriction>
			dlItemSelectorCriterionCreationMenuRestrictions =
				_serviceTrackerMap.getService(clazz.getName());

		if (dlItemSelectorCriterionCreationMenuRestrictions == null) {
			return null;
		}

		Stream<DLItemSelectorCriterionCreationMenuRestriction> stream =
			dlItemSelectorCriterionCreationMenuRestrictions.stream();

		return stream.map(
			DLItemSelectorCriterionCreationMenuRestriction::
				getAllowedCreationMenuUIItemKeys
		).collect(
			HashSet::new, Set::addAll, Set::addAll
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, DLItemSelectorCriterionCreationMenuRestriction.class,
			null,
			(serviceReference, emitter) -> {
				Object modelClassName = serviceReference.getProperty(
					"model.class.name");

				if (modelClassName != null) {
					_propertyServiceReferenceMapper.map(
						serviceReference, emitter);

					return;
				}

				try {
					emitter.emit(
						GenericUtil.getGenericClassName(
							bundleContext.getService(serviceReference)));
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static ServiceTrackerMap
		<String, List<DLItemSelectorCriterionCreationMenuRestriction>>
			_serviceTrackerMap;

	private final PropertyServiceReferenceMapper
		<String, DLItemSelectorCriterionCreationMenuRestriction>
			_propertyServiceReferenceMapper =
				new PropertyServiceReferenceMapper<>("model.class.name");

}