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
import com.liferay.headless.commerce.admin.site.setting.internal.resource.v1_0.AvailabilityEstimateResourceImpl;
import com.liferay.headless.commerce.admin.site.setting.internal.resource.v1_0.MeasurementUnitResourceImpl;
import com.liferay.headless.commerce.admin.site.setting.internal.resource.v1_0.TaxCategoryResourceImpl;
import com.liferay.headless.commerce.admin.site.setting.internal.resource.v1_0.WarehouseResourceImpl;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.AvailabilityEstimateResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.MeasurementUnitResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.TaxCategoryResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.WarehouseResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

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
@Component(service = ServletData.class)
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

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Site.Setting";
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

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#deleteAvailabilityEstimate",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"deleteAvailabilityEstimate"));
		_resourceMethodPairs.put(
			"mutation#deleteAvailabilityEstimateBatch",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"deleteAvailabilityEstimateBatch"));
		_resourceMethodPairs.put(
			"mutation#updateAvailabilityEstimate",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"putAvailabilityEstimate"));
		_resourceMethodPairs.put(
			"mutation#updateAvailabilityEstimateBatch",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"putAvailabilityEstimateBatch"));
		_resourceMethodPairs.put(
			"mutation#createCommerceAdminSiteSettingGroupAvailabilityEstimate",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"postCommerceAdminSiteSettingGroupAvailabilityEstimate"));
		_resourceMethodPairs.put(
			"mutation#createMeasurementUnit",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "postMeasurementUnit"));
		_resourceMethodPairs.put(
			"mutation#createMeasurementUnitBatch",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "postMeasurementUnitBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteMeasurementUnitByExternalReferenceCode",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"deleteMeasurementUnitByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchMeasurementUnitByExternalReferenceCode",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"patchMeasurementUnitByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteMeasurementUnitByKey",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"deleteMeasurementUnitByKey"));
		_resourceMethodPairs.put(
			"mutation#patchMeasurementUnitByKey",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"patchMeasurementUnitByKey"));
		_resourceMethodPairs.put(
			"mutation#deleteMeasurementUnit",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "deleteMeasurementUnit"));
		_resourceMethodPairs.put(
			"mutation#deleteMeasurementUnitBatch",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"deleteMeasurementUnitBatch"));
		_resourceMethodPairs.put(
			"mutation#patchMeasurementUnit",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "patchMeasurementUnit"));
		_resourceMethodPairs.put(
			"mutation#createCommerceAdminSiteSettingGroupTaxCategory",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class,
				"postCommerceAdminSiteSettingGroupTaxCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxCategory",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "deleteTaxCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxCategoryBatch",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "deleteTaxCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#updateTaxCategory",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "putTaxCategory"));
		_resourceMethodPairs.put(
			"mutation#updateTaxCategoryBatch",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "putTaxCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#createCommerceAdminSiteSettingGroupWarehouse",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class,
				"postCommerceAdminSiteSettingGroupWarehouse"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouse",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "deleteWarehouse"));
		_resourceMethodPairs.put(
			"mutation#deleteWarehouseBatch",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "deleteWarehouseBatch"));
		_resourceMethodPairs.put(
			"mutation#updateWarehouse",
			new ObjectValuePair<>(WarehouseResourceImpl.class, "putWarehouse"));
		_resourceMethodPairs.put(
			"mutation#updateWarehouseBatch",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class, "putWarehouseBatch"));
		_resourceMethodPairs.put(
			"query#availabilityEstimate",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"getAvailabilityEstimate"));
		_resourceMethodPairs.put(
			"query#commerceAdminSettingGroupAvailabilityEstimate",
			new ObjectValuePair<>(
				AvailabilityEstimateResourceImpl.class,
				"getCommerceAdminSiteSettingGroupAvailabilityEstimatePage"));
		_resourceMethodPairs.put(
			"query#measurementUnits",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "getMeasurementUnitsPage"));
		_resourceMethodPairs.put(
			"query#measurementUnitByExternalReferenceCode",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"getMeasurementUnitByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#measurementUnitByKey",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "getMeasurementUnitByKey"));
		_resourceMethodPairs.put(
			"query#measurementUnitsByType",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class,
				"getMeasurementUnitsByType"));
		_resourceMethodPairs.put(
			"query#measurementUnit",
			new ObjectValuePair<>(
				MeasurementUnitResourceImpl.class, "getMeasurementUnit"));
		_resourceMethodPairs.put(
			"query#commerceAdminSettingGroupTaxCategory",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class,
				"getCommerceAdminSiteSettingGroupTaxCategoryPage"));
		_resourceMethodPairs.put(
			"query#taxCategory",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "getTaxCategory"));
		_resourceMethodPairs.put(
			"query#commerceAdminSettingGroupWarehouse",
			new ObjectValuePair<>(
				WarehouseResourceImpl.class,
				"getCommerceAdminSiteSettingGroupWarehousePage"));
		_resourceMethodPairs.put(
			"query#warehouse",
			new ObjectValuePair<>(WarehouseResourceImpl.class, "getWarehouse"));
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