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

package com.liferay.segments.internal.model.listener;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexerUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.segments.internal.odata.entity.EntityModelFieldMapper;
import com.liferay.segments.internal.odata.entity.UserEntityModel;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class UserExpandoColumnModelListener
	extends BaseModelListener<ExpandoColumn> {

	@Activate
	public void activate(BundleContext bundleContext) {
		try {
			_bundleContext = bundleContext;

			_userEntityFields = _getUserEntityFields();

			_serviceRegistration = _register(_bundleContext, _userEntityFields);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	@Deactivate
	public void deactivate() {
		_unregister(_serviceRegistration);
	}

	@Override
	public void onAfterCreate(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		try {
			if (!_isUserCustomField(expandoColumn)) {
				return;
			}

			Optional<EntityField> userEntityFieldOptional =
				_getUserEntityFieldOptional(expandoColumn);

			userEntityFieldOptional.ifPresent(
				entityField -> {
					_userEntityFields.put(
						expandoColumn.getColumnId(), entityField);

					_serviceRegistration = _updateRegistry(
						_bundleContext, _serviceRegistration,
						_userEntityFields);
				});
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onAfterRemove(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		if (expandoColumn == null) {
			return;
		}

		if (_userEntityFields.containsKey(expandoColumn.getColumnId())) {
			_userEntityFields.remove(expandoColumn.getColumnId());

			_serviceRegistration = _updateRegistry(
				_bundleContext, _serviceRegistration, _userEntityFields);
		}
	}

	@Override
	public void onAfterUpdate(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		if (expandoColumn == null) {
			return;
		}

		_userEntityFields.remove(expandoColumn.getColumnId());

		onAfterCreate(expandoColumn);
	}

	private DynamicQuery _getTableDynamicQuery(long classNameId, String name) {
		DynamicQuery dynamicQuery = _expandoTableLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(classNameIdProperty.eq(classNameId));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(name));

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		return dynamicQuery;
	}

	private Optional<EntityField> _getUserEntityFieldOptional(
		ExpandoColumn expandoColumn) {

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		int indexType = GetterUtil.getInteger(
			unicodeProperties.get(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return Optional.empty();
		}

		String encodedName =
			_entityModelFieldMapper.getExpandoColumnEntityFieldName(
				expandoColumn);

		String encodedIndexedFieldName =
			ExpandoBridgeIndexerUtil.encodeFieldName(
				expandoColumn.getName(), indexType);

		EntityField entityField = null;

		if (expandoColumn.getType() == ExpandoColumnConstants.BOOLEAN) {
			entityField = new BooleanEntityField(
				encodedName, locale -> encodedIndexedFieldName);
		}
		else if (expandoColumn.getType() == ExpandoColumnConstants.DATE) {
			entityField = new DateTimeEntityField(
				encodedName,
				locale -> Field.getSortableFieldName(encodedIndexedFieldName),
				locale -> encodedIndexedFieldName);
		}
		else if (expandoColumn.getType() ==
					ExpandoColumnConstants.STRING_LOCALIZED) {

			entityField = new StringEntityField(
				encodedName,
				locale -> Field.getLocalizedName(
					locale, encodedIndexedFieldName));
		}
		else {
			entityField = new StringEntityField(
				encodedName, locale -> encodedIndexedFieldName);
		}

		return Optional.of(entityField);
	}

	private Map<Long, EntityField> _getUserEntityFields()
		throws PortalException {

		Map<Long, EntityField> userEntityFieldsMap = new HashMap<>();

		ActionableDynamicQuery columnActionableDynamicQuery =
			_expandoColumnLocalService.getActionableDynamicQuery();

		columnActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property tableProperty = PropertyFactoryUtil.forName("tableId");

				long userClassNameId = _classNameLocalService.getClassNameId(
					User.class.getName());

				dynamicQuery.add(
					tableProperty.in(
						_getTableDynamicQuery(
							userClassNameId,
							ExpandoTableConstants.DEFAULT_TABLE_NAME)));
			});
		columnActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ExpandoColumn>)
				expandoColumn -> {
					Optional<EntityField> userEntityFieldOptional =
						_getUserEntityFieldOptional(expandoColumn);

					userEntityFieldOptional.ifPresent(
						entityField -> userEntityFieldsMap.put(
							expandoColumn.getColumnId(), entityField));
				});

		columnActionableDynamicQuery.performActions();

		return userEntityFieldsMap;
	}

	private boolean _isUserCustomField(ExpandoColumn expandoColumn)
		throws PortalException {

		long userClassNameId = _classNameLocalService.getClassNameId(
			User.class.getName());

		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			expandoColumn.getTableId());

		if (expandoTable.getClassNameId() != userClassNameId) {
			return false;
		}

		if (!ExpandoTableConstants.DEFAULT_TABLE_NAME.equals(
				expandoTable.getName())) {

			return false;
		}

		return true;
	}

	private ServiceRegistration<EntityModel> _register(
		BundleContext bundleContext,
		Map<Long, EntityField> userEntityFieldsMap) {

		return bundleContext.registerService(
			EntityModel.class,
			new UserEntityModel(new ArrayList<>(userEntityFieldsMap.values())),
			new HashMapDictionary<String, Object>() {
				{
					put("entity.model.name", UserEntityModel.NAME);
				}
			});
	}

	private void _unregister(
		ServiceRegistration<EntityModel> serviceRegistration) {

		serviceRegistration.unregister();
	}

	private ServiceRegistration<EntityModel> _updateRegistry(
		BundleContext bundleContext,
		ServiceRegistration<EntityModel> serviceRegistration,
		Map<Long, EntityField> entityFieldsMap) {

		_unregister(serviceRegistration);

		return _register(bundleContext, entityFieldsMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserExpandoColumnModelListener.class);

	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private EntityModelFieldMapper _entityModelFieldMapper;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	private ServiceRegistration<EntityModel> _serviceRegistration;
	private Map<Long, EntityField> _userEntityFields = new HashMap<>();

}