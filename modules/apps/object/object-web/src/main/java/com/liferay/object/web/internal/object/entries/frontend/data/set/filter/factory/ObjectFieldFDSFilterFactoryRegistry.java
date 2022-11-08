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

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ObjectFieldFDSFilterFactoryRegistry.class)
public class ObjectFieldFDSFilterFactoryRegistry {

	public ObjectFieldFDSFilterFactory getObjectFieldFDSFilterFactory(
			long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		if (Validator.isNotNull(objectViewFilterColumn.getFilterType())) {
			return _objectFieldFilterTypeKeyServiceTrackerMap.getService(
				objectViewFilterColumn.getFilterType());
		}

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), "dateCreated") ||
			Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), "dateModified")) {

			return _objectFieldBusinessTypeKeyServiceTrackerMap.getService(
				ObjectFieldConstants.BUSINESS_TYPE_DATE);
		}

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), "status")) {

			return _objectFieldBusinessTypeKeyServiceTrackerMap.getService(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST);
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinitionId, objectViewFilterColumn.getObjectFieldName());

		return _objectFieldBusinessTypeKeyServiceTrackerMap.getService(
			objectField.getBusinessType());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_objectFieldBusinessTypeKeyServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ObjectFieldFDSFilterFactory.class,
				"object.field.business.type.key");
		_objectFieldFilterTypeKeyServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ObjectFieldFDSFilterFactory.class,
				"object.field.filter.type.key");
	}

	@Deactivate
	protected void deactivate() {
		_objectFieldBusinessTypeKeyServiceTrackerMap.close();
		_objectFieldFilterTypeKeyServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectFieldFDSFilterFactory>
		_objectFieldBusinessTypeKeyServiceTrackerMap;
	private ServiceTrackerMap<String, ObjectFieldFDSFilterFactory>
		_objectFieldFilterTypeKeyServiceTrackerMap;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}