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

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContext;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributorRegistry;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
		"object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES
	},
	service = ObjectFieldFDSFilterFactory.class
)
public class ListTypeEntryObjectFieldFDSFilterFactory
	implements ObjectFieldFDSFilterFactory {

	public FDSFilter create(
			Locale locale, long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		ObjectFieldFilterContributor objectFieldFilterContributor =
			_objectFieldFilterContributorRegistry.
				getObjectFieldFilterContributor(
					new ObjectFieldFilterContext(
						locale, objectDefinitionId, objectViewFilterColumn));

		return objectFieldFilterContributor.getFDSFilter();
	}

	@Reference
	private ObjectFieldFilterContributorRegistry
		_objectFieldFilterContributorRegistry;

}