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

package com.liferay.headless.commerce.admin.site.setting.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.site.setting.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.site.setting.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.AvailabilityEstimateResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.MeasurementUnitResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.TaxCategoryResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.WarehouseResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Zoltán Takács
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAvailabilityEstimateResourceComponentServiceObjects(
			_availabilityEstimateResourceComponentServiceObjects);
		Mutation.setMeasurementUnitResourceComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects);
		Mutation.setTaxCategoryResourceComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects);
		Mutation.setWarehouseResourceComponentServiceObjects(
			_warehouseResourceComponentServiceObjects);

		Query.setAvailabilityEstimateResourceComponentServiceObjects(
			_availabilityEstimateResourceComponentServiceObjects);
		Query.setMeasurementUnitResourceComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects);
		Query.setTaxCategoryResourceComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects);
		Query.setWarehouseResourceComponentServiceObjects(
			_warehouseResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-site-setting-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AvailabilityEstimateResource>
		_availabilityEstimateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MeasurementUnitResource>
		_measurementUnitResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxCategoryResource>
		_taxCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WarehouseResource>
		_warehouseResourceComponentServiceObjects;

}