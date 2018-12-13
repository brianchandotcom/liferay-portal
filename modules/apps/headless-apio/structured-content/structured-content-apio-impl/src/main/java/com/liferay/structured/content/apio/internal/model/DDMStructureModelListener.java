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

package com.liferay.structured.content.apio.internal.model;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.structured.content.apio.internal.architect.filter.StructuredContentEntityModel;
import com.liferay.structured.content.apio.internal.architect.resource.StructuredContentNestedCollectionResource;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Activate
	public void activate(BundleContext bundleContext) {
		try {
			_bundleContext = bundleContext;

			_entityFieldsMap = _getEntityFieldsMap();

			_serviceRegistration = _register(_bundleContext, _entityFieldsMap);
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
	public void onAfterCreate(DDMStructure ddmStructure)
		throws ModelListenerException {

		long classNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		if (ddmStructure.getClassNameId() != classNameId) {
			return;
		}

		try {
			Map.Entry<Long, List<EntityField>> simpleEntry = _getEntry(
				ddmStructure);

			_entityFieldsMap.put(simpleEntry.getKey(), simpleEntry.getValue());

			_serviceRegistration = _updateRegistry(
				_bundleContext, _serviceRegistration, _entityFieldsMap);
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onAfterRemove(DDMStructure ddmStructure)
		throws ModelListenerException {

		if (ddmStructure == null) {
			return;
		}

		long classNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		if (ddmStructure.getClassNameId() != classNameId) {
			return;
		}

		_entityFieldsMap.remove(ddmStructure.getStructureId());

		_serviceRegistration = _updateRegistry(
			_bundleContext, _serviceRegistration, _entityFieldsMap);
	}

	@Override
	public void onAfterUpdate(DDMStructure ddmStructure)
		throws ModelListenerException {

		onAfterCreate(ddmStructure);
	}

	protected String encodeName(
		long ddmStructureId, String fieldName, Locale locale, String type) {

		return Field.getSortableFieldName(
			StringBundler.concat(
				_ddmIndexer.encodeName(ddmStructureId, fieldName, locale),
				StringPool.UNDERLINE, type));
	}

	private Optional<EntityField> _createEntityField(
			DDMStructure ddmStructure, DDMFormField ddmFormField)
		throws PortalException {

		String indexType = ddmStructure.getFieldProperty(
			ddmFormField.getName(), "indexType");

		if (Validator.isNull(indexType)) {
			return Optional.empty();
		}

		if (Objects.equals(ddmFormField.getType(), DDMFormFieldType.CHECKBOX)) {
			return Optional.of(
				new BooleanEntityField(
					StructuredContentNestedCollectionResource.
						encodeFilterAndSortIdentifier(
							ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DATE)) {

			return Optional.of(
				new DateEntityField(
					StructuredContentNestedCollectionResource.
						encodeFilterAndSortIdentifier(
							ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String"),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String"),
					this::_getDDMDateFieldValue));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DOUBLE) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.NUMBER)) {

			return Optional.of(
				new DoubleEntityField(
					StructuredContentNestedCollectionResource.
						encodeFilterAndSortIdentifier(
							ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "Number")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.LONG) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.INTEGER)) {

			return Optional.of(
				new IntegerEntityField(
					StructuredContentNestedCollectionResource.
						encodeFilterAndSortIdentifier(
							ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "Number")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), DDMFormFieldType.RADIO) ||
				 (Objects.equals(
					 ddmFormField.getType(), DDMFormFieldType.TEXT) &&
				  Objects.equals(ddmFormField.getIndexType(), "keyword"))) {

			return Optional.of(
				new StringEntityField(
					StructuredContentNestedCollectionResource.
						encodeFilterAndSortIdentifier(
							ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String")));
		}

		return Optional.empty();
	}

	private String _getDDMDateFieldValue(Object fieldValue) {
		DateFormat indexDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		try {
			Date date = indexDateFormat.parse(String.valueOf(fieldValue));

			DateFormat searchDateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

			return searchDateFormat.format(date);
		}
		catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}

	private Map<Long, List<EntityField>> _getEntityFieldsMap()
		throws PortalException {

		Map<Long, List<EntityField>> entityFieldsMap = new HashMap<>();

		ActionableDynamicQuery actionableDynamicQuery =
			_ddmStructureLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long classNameId = _classNameLocalService.getClassNameId(
					JournalArticle.class.getName());

				dynamicQuery.add(classNameIdProperty.eq(classNameId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DDMStructure ddmStructure) -> {
				Map.Entry<Long, List<EntityField>> simpleEntry = _getEntry(
					ddmStructure);

				entityFieldsMap.put(
					simpleEntry.getKey(), simpleEntry.getValue());
			});

		actionableDynamicQuery.performActions();

		return entityFieldsMap;
	}

	private Map.Entry<Long, List<EntityField>> _getEntry(
			DDMStructure ddmStructure)
		throws PortalException {

		List<EntityField> entityFields = new ArrayList<>();

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			Optional<EntityField> entityFieldOptional = _createEntityField(
				ddmStructure, ddmFormField);

			if (entityFieldOptional.isPresent()) {
				entityFields.add(entityFieldOptional.get());
			}
		}

		return new AbstractMap.SimpleEntry<>(
			ddmStructure.getStructureId(), entityFields);
	}

	private ServiceRegistration<EntityModel> _register(
		BundleContext bundleContext,
		Map<Long, List<EntityField>> entityFieldsMap) {

		Collection<List<EntityField>> collection = entityFieldsMap.values();

		Stream<List<EntityField>> stream = collection.stream();

		List<EntityField> entityFields = stream.flatMap(
			List::stream
		).collect(
			Collectors.toList()
		);

		return bundleContext.registerService(
			EntityModel.class, new StructuredContentEntityModel(entityFields),
			new HashMapDictionary<String, Object>() {
				{
					put("entity.model.name", StructuredContentEntityModel.NAME);
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
		Map<Long, List<EntityField>> entityFieldsMap) {

		_unregister(serviceRegistration);

		return _register(bundleContext, entityFieldsMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureModelListener.class);

	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	private Map<Long, List<EntityField>> _entityFieldsMap = new HashMap<>();
	private ServiceRegistration<EntityModel> _serviceRegistration;

}