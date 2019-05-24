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

package com.liferay.headless.delivery.internal.model.listener;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Javier Gamarra
 */
public class CustomFieldsModelListener
	extends BaseModelListener<ExpandoColumn> {

	public CustomFieldsModelListener(
		BundleContext bundleContext,
		ClassNameLocalService classNameLocalService, Class<?> clazz,
		Function<Map<Long, EntityField>, EntityModel> entityModelFunction,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService) {

		_bundleContext = bundleContext;
		_classNameLocalService = classNameLocalService;
		_clazz = clazz;
		_entityModelFunction = entityModelFunction;
		_expandoTableLocalService = expandoTableLocalService;

		try {
			long classNameId = classNameLocalService.getClassNameId(
				clazz.getName());

			_fields.putAll(
				EntityFieldsUtil.getEntityFields(
					classNameId, expandoColumnLocalService,
					expandoTableLocalService));

			_serviceRegistration = _register(_bundleContext, _fields);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	public void onAfterCreate(ExpandoColumn expandoColumn) {
		try {
			if (!_isContentCustomField(expandoColumn)) {
				return;
			}

			Optional<EntityField> entityFieldOptional =
				EntityFieldsUtil.getEntityFieldOptional(expandoColumn);

			entityFieldOptional.ifPresent(
				entityField -> {
					_fields.put(expandoColumn.getColumnId(), entityField);

					_serviceRegistration = _updateRegistry(
						_bundleContext, _fields);
				});
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	public void onAfterRemove(ExpandoColumn expandoColumn) {
		if (expandoColumn == null) {
			return;
		}

		if (_fields.containsKey(expandoColumn.getColumnId())) {
			_fields.remove(expandoColumn.getColumnId());

			_serviceRegistration = _updateRegistry(_bundleContext, _fields);
		}
	}

	public void onAfterUpdate(ExpandoColumn expandoColumn) {
		if (expandoColumn == null) {
			return;
		}

		_fields.remove(expandoColumn.getColumnId());
	}

	public void unregister() {
		_serviceRegistration.unregister();
	}

	private boolean _isContentCustomField(ExpandoColumn expandoColumn)
		throws PortalException {

		long classNameId = _classNameLocalService.getClassNameId(
			_clazz.getName());

		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			expandoColumn.getTableId());

		if (expandoTable.getClassNameId() != classNameId) {
			return false;
		}

		if (!ExpandoTableConstants.DEFAULT_TABLE_NAME.equals(
				expandoTable.getName())) {

			return false;
		}

		return true;
	}

	private ServiceRegistration<EntityModel> _register(
		BundleContext bundleContext, Map<Long, EntityField> entityFields) {

		EntityModel entityModel = _entityModelFunction.apply(entityFields);

		return bundleContext.registerService(
			EntityModel.class, entityModel,
			new HashMapDictionary<String, Object>() {
				{
					put("entity.model.name", entityModel.getName());
				}
			});
	}

	private ServiceRegistration<EntityModel> _updateRegistry(
		BundleContext bundleContext, Map<Long, EntityField> entityFieldsMap) {

		unregister();

		return _register(bundleContext, entityFieldsMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFieldsModelListener.class);

	private final BundleContext _bundleContext;
	private final ClassNameLocalService _classNameLocalService;
	private final Class<?> _clazz;
	private final Function<Map<Long, EntityField>, EntityModel>
		_entityModelFunction;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final Map<Long, EntityField> _fields = new HashMap<>();
	private ServiceRegistration<EntityModel> _serviceRegistration;

}