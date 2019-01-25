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

package com.liferay.segments.internal.odata.entity;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizerRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = EntityModelFieldMapper.class)
public class EntityModelFieldMapper {

	public List<Field> getFields(
		EntityModel entityModel, Map<String, String> idEntityFieldTypes,
		PortletRequest portletRequest) {

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		List<Field> fields = new ArrayList<>();

		entityFieldsMap.forEach(
			(entityFieldName, entityField) -> fields.addAll(
				getFields(
					entityModel, entityField, idEntityFieldTypes,
					portletRequest)));

		Collections.sort(fields);

		return fields;
	}

	public List<Field> getFields(
		EntityModel entityModel, PortletRequest portletRequest) {

		return getFields(entityModel, null, portletRequest);
	}

	protected List<Field> getFields(
		EntityModel entityModel, EntityField entityField,
		Map<String, String> idEntityFieldTypes, PortletRequest portletRequest) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(portletRequest), getClass());

		Optional<SegmentsFieldCustomizer> segmentsFieldCustomizerOptional =
			_segmentsFieldCustomizerRegistry.getSegmentFieldCustomizer(
				entityModel.getName(), entityField.getName());

		if (segmentsFieldCustomizerOptional.isPresent()) {
			SegmentsFieldCustomizer segmentsFieldCustomizer =
				segmentsFieldCustomizerOptional.get();

			return Collections.singletonList(
				new Field(
					entityField.getName(),
					segmentsFieldCustomizer.getLabel(
						entityField.getName(), resourceBundle.getLocale()),
					getType(entityField.getType()),
					segmentsFieldCustomizer.getOptions(
						resourceBundle.getLocale()),
					segmentsFieldCustomizer.getSelectEntity(portletRequest)));
		}

		EntityField.Type entityFieldType = entityField.getType();

		if (entityFieldType == EntityField.Type.COMPLEX) {
			Map<String, EntityField> entityFieldsMap =
				((ComplexEntityField)entityField).getEntityFieldsMap();

			return _getComplexFields(
				entityField.getName(), entityFieldsMap, resourceBundle);
		}

		if (entityFieldType == EntityField.Type.ID) {
			Optional<Field> idEntityFieldOptional = _getIdEntityFieldOptional(
				entityField, idEntityFieldTypes, portletRequest,
				resourceBundle);

			return idEntityFieldOptional.map(
				Collections::singletonList
			).orElse(
				Collections.emptyList()
			);
		}

		String label = LanguageUtil.get(
			resourceBundle, CamelCaseUtil.fromCamelCase(entityField.getName()));

		return Collections.singletonList(
			new Field(entityField.getName(), label, getType(entityFieldType)));
	}

	protected String getType(EntityField.Type entityFieldType) {
		if (entityFieldType == EntityField.Type.BOOLEAN) {
			return "boolean";
		}
		else if ((entityFieldType == EntityField.Type.DATE) ||
				 (entityFieldType == EntityField.Type.DATE_TIME)) {

			return "date";
		}
		else if (entityFieldType == EntityField.Type.DOUBLE) {
			return "double";
		}
		else if (entityFieldType == EntityField.Type.ID) {
			return "id";
		}
		else if (entityFieldType == EntityField.Type.INTEGER) {
			return "integer";
		}

		return "string";
	}

	private ExpandoColumn _decodeName(String entityFieldName) {
		String[] split = StringUtil.split(
			entityFieldName, StringPool.UNDERLINE);

		if (split.length < 2) {
			return null;
		}

		long columnId = Long.valueOf(split[1]);

		try {
			return _expandoColumnLocalService.getColumn(columnId);
		}
		catch (PortalException pe) {
			_log.error("Unable to find Expando Column with id " + columnId, pe);

			return null;
		}
	}

	private List<Field> _getComplexFields(
		String name, Map<String, EntityField> entityFieldsMap,
		ResourceBundle resourceBundle) {

		if (!name.equals("customField")) {
			return Collections.emptyList();
		}

		List<Field> complexFields = new ArrayList<>();

		entityFieldsMap.forEach(
			(entityFieldName, entityField) -> {
				ExpandoColumn expandoColumn = _decodeName(entityFieldName);

				if (expandoColumn == null) {
					return;
				}

				String label = expandoColumn.getDisplayName(
					resourceBundle.getLocale());

				String type = getType(entityField.getType());

				complexFields.add(
					new Field(
						"customField/" + entityFieldName, label, type,
						_getExpandoColumnFieldOptions(expandoColumn), null));
			});

		return complexFields;
	}

	private List<Field.Option> _getExpandoColumnFieldOptions(
		ExpandoColumn expandoColumn) {

		List<Field.Option> fieldOptions = new ArrayList<>();

		if (expandoColumn.getType() == ExpandoColumnConstants.DOUBLE_ARRAY) {
			for (double value : (double[])expandoColumn.getDefaultValue()) {
				fieldOptions.add(
					new Field.Option(
						String.valueOf(value), String.valueOf(value)));
			}
		}
		else if (expandoColumn.getType() ==
					ExpandoColumnConstants.INTEGER_ARRAY) {

			for (int value : (int[])expandoColumn.getDefaultValue()) {
				fieldOptions.add(
					new Field.Option(
						String.valueOf(value), String.valueOf(value)));
			}
		}
		else if (expandoColumn.getType() ==
					ExpandoColumnConstants.STRING_ARRAY) {

			for (String value : (String[])expandoColumn.getDefaultValue()) {
				fieldOptions.add(new Field.Option(value, value));
			}
		}

		return fieldOptions;
	}

	private Optional<Field> _getIdEntityFieldOptional(
		EntityField entityField, Map<String, String> idEntityFieldTypes,
		PortletRequest portletRequest, ResourceBundle resourceBundle) {

		String className = idEntityFieldTypes.get(entityField.getName());

		if (className == null) {
			return Optional.empty();
		}

		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, className, PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return Optional.empty();
			}

			portletURL.setParameter("eventName", "selectEntity");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			String label = LanguageUtil.get(
				resourceBundle,
				CamelCaseUtil.fromCamelCase(entityField.getName()));

			String title = ResourceActionsUtil.getModelResource(
				resourceBundle.getLocale(), className);

			String selectEntityTitle = LanguageUtil.format(
				resourceBundle, "select-x", title);

			Field field = new Field(
				entityField.getName(), label, "id", Collections.emptyList(),
				new Field.SelectEntity(
					"selectEntity", selectEntityTitle, portletURL.toString(),
					false));

			return Optional.of(field);
		}
		catch (Exception e) {
			_log.error("Unable to get ID entity field", e);

			return Optional.empty();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityModelFieldMapper.class);

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsFieldCustomizerRegistry _segmentsFieldCustomizerRegistry;

}