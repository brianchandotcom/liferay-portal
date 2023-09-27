/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Adolfo Pérez
 */
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

		Set<String> allowedCreationMenuUIItemKeys = new HashSet<>();

		for (DLItemSelectorCriterionCreationMenuRestriction
				dlItemSelectorCriterionCreationMenuRestriction :
					dlItemSelectorCriterionCreationMenuRestrictions) {

			allowedCreationMenuUIItemKeys.addAll(
				dlItemSelectorCriterionCreationMenuRestriction.
					getAllowedCreationMenuUIItemKeys());
		}

		return allowedCreationMenuUIItemKeys;
	}

	private static final ServiceTrackerMap
		<String, List<DLItemSelectorCriterionCreationMenuRestriction>>
			_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DLItemSelectorCriterionCreationMenuRestrictionUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		PropertyServiceReferenceMapper
			<String, DLItemSelectorCriterionCreationMenuRestriction>
				propertyServiceReferenceMapper =
					new PropertyServiceReferenceMapper<>("model.class.name");

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, DLItemSelectorCriterionCreationMenuRestriction.class,
			null,
			(serviceReference, emitter) -> {
				Object modelClassName = serviceReference.getProperty(
					"model.class.name");

				if (modelClassName != null) {
					propertyServiceReferenceMapper.map(
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

}