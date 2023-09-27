/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.filter.parser;

import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContext;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributorRegistry;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Feliphe Marinho
 */
@Component(service = ObjectFieldFilterContributorRegistry.class)
public class ObjectFieldFilterContributorRegistryImpl
	implements ObjectFieldFilterContributorRegistry {

	@Override
	public ObjectFieldFilterContributor getObjectFieldFilterContributor(
			ObjectFieldFilterContext objectFieldFilterContext)
		throws PortalException {

		ObjectViewFilterColumn objectViewFilterColumn =
			objectFieldFilterContext.getObjectViewFilterColumn();

		String key = objectViewFilterColumn.getFilterType();

		if (Validator.isNull(key)) {
			key = ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES;
		}

		ObjectFieldFilterContributor objectFieldFilterContributor =
			_serviceTrackerMap.getService(key);

		objectFieldFilterContributor.setObjectFieldFilterStrategy(
			objectFieldFilterContext);

		return objectFieldFilterContributor;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectFieldFilterContributor.class,
			"object.field.filter.type.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectFieldFilterContributor>
		_serviceTrackerMap;

}