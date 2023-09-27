/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.ExpandoField;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.Field;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.converter.DXPEntityDTOConverter;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.io.Serializable;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "dto.class.name=com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity",
	service = DTOConverter.class
)
public class DXPEntityDTOConverterImpl implements DXPEntityDTOConverter {

	@Override
	public String getContentType() {
		return DXPEntity.class.getSimpleName();
	}

	@Override
	public DXPEntity toDTO(
			DTOConverterContext dtoConverterContext, BaseModel<?> baseModel)
		throws Exception {

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		return _toDXPEntity(
			_getExpandoFields(baseModel), _getFields(baseModel),
			String.valueOf(baseModel.getPrimaryKeyObj()),
			(Date)modelAttributes.get("modifiedDate"),
			baseModel.getModelClassName());
	}

	private void _addFieldAttributes(
		BaseModel<?> baseModel, List<Field> fields,
		List<String> includeAttributeNames) {

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			if (ListUtil.isNotEmpty(includeAttributeNames) &&
				!includeAttributeNames.contains(entry.getKey())) {

				continue;
			}

			Field field = new Field() {
				{
					name = entry.getKey();

					setValue(
						() -> {
							if (entry.getValue() instanceof Date) {
								Date date = (Date)entry.getValue();

								return String.valueOf(date.getTime());
							}

							if (Validator.isNull(entry.getValue())) {
								return StringPool.BLANK;
							}

							return String.valueOf(entry.getValue());
						});
				}
			};

			fields.add(field);
		}
	}

	private List<String> _filterAttributeNames(
		List<String> attributeNames, List<String> removeAttributeNames) {

		List<String> filteredAttributeNames = new ArrayList<>();

		for (String attributeName : attributeNames) {
			if (removeAttributeNames.contains(attributeName)) {
				continue;
			}

			filteredAttributeNames.add(attributeName);
		}

		return filteredAttributeNames;
	}

	private Map<String, Serializable> _getAttributes(
		ExpandoBridge expandoBridge, List<String> includeAttributeNames) {

		Map<String, Serializable> newAttributes = new HashMap<>();

		Map<String, Serializable> attributes = expandoBridge.getAttributes(
			false);

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			if (ListUtil.isNotEmpty(includeAttributeNames) &&
				!includeAttributeNames.contains(entry.getKey())) {

				continue;
			}

			String dataType = ExpandoColumnConstants.getDataType(
				expandoBridge.getAttributeType(entry.getKey()));

			if (Validator.isBlank(dataType)) {
				dataType = ExpandoColumnConstants.DATA_TYPE_TEXT;
			}

			newAttributes.put(
				entry.getKey() + "-" + dataType, entry.getValue());
		}

		return newAttributes;
	}

	private Field[] _getExpandoColumnFields(
		String className, String dataType, ExpandoColumn expandoColumn) {

		List<Field> fields = new ArrayList<Field>() {
			{
				add(
					new Field() {
						{
							name = "className";
							value = className;
						}
					});
				add(
					new Field() {
						{
							name = "columnId";
							value = String.valueOf(expandoColumn.getColumnId());
						}
					});
				add(
					new Field() {
						{
							name = "dataType";
							value = dataType;
						}
					});
				add(
					new Field() {
						{
							name = "modifiedDate";

							Date modifiedDate = expandoColumn.getModifiedDate();

							value = String.valueOf(modifiedDate.getTime());
						}
					});
				add(
					new Field() {
						{
							name = "name";
							value = expandoColumn.getName() + "-" + dataType;
						}
					});
			}
		};

		return fields.toArray(new Field[0]);
	}

	private ExpandoField[] _getExpandoFields(BaseModel<?> baseModel) {
		if (!StringUtil.equals(
				baseModel.getModelClassName(), Organization.class.getName()) &&
			!StringUtil.equals(
				baseModel.getModelClassName(), User.class.getName())) {

			return new ExpandoField[0];
		}

		List<ExpandoField> expandoFields = new ArrayList<>();

		List<String> includeAttributeNames = new ArrayList<>();

		ShardedModel shardedModel = (ShardedModel)baseModel;

		if (StringUtil.equals(
				baseModel.getModelClassName(), User.class.getName())) {

			AnalyticsConfiguration analyticsConfiguration =
				_analyticsConfigurationRegistry.getAnalyticsConfiguration(
					shardedModel.getCompanyId());

			includeAttributeNames = ListUtil.fromArray(
				analyticsConfiguration.syncedUserFieldNames());
		}

		Map<String, Serializable> attributes = _getAttributes(
			baseModel.getExpandoBridge(), includeAttributeNames);

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			String key = entry.getKey();

			ExpandoColumn expandoColumn =
				_expandoColumnLocalService.getDefaultTableColumn(
					shardedModel.getCompanyId(), baseModel.getModelClassName(),
					key.substring(0, key.indexOf("-")));

			if (expandoColumn == null) {
				continue;
			}

			ExpandoField expandoField = new ExpandoField() {
				{
					columnId = expandoColumn.getColumnId();
					name = key;
					value = _parseValue(entry.getValue());
				}
			};

			expandoFields.add(expandoField);
		}

		return expandoFields.toArray(new ExpandoField[0]);
	}

	private Field[] _getFields(BaseModel<?> baseModel) throws Exception {
		if (StringUtil.equals(
				baseModel.getModelClassName(), ExpandoColumn.class.getName())) {

			ExpandoColumn expandoColumn = (ExpandoColumn)baseModel;

			String className = User.class.getName();

			if (_isCustomField(
					Organization.class.getName(), expandoColumn.getTableId())) {

				className = Organization.class.getName();
			}

			String dataType = ExpandoColumnConstants.getDataType(
				expandoColumn.getType());

			if (Validator.isBlank(dataType)) {
				dataType = ExpandoColumnConstants.DATA_TYPE_TEXT;
			}

			return _getExpandoColumnFields(className, dataType, expandoColumn);
		}

		List<Field> fields = new ArrayList<>();

		List<String> includeAttributeNames = new ArrayList<>();

		if (StringUtil.equals(
				baseModel.getModelClassName(), AccountEntry.class.getName())) {

			AccountEntry accountEntry = (AccountEntry)baseModel;

			AnalyticsConfiguration analyticsConfiguration =
				_analyticsConfigurationRegistry.getAnalyticsConfiguration(
					accountEntry.getCompanyId());

			includeAttributeNames = ListUtil.fromArray(
				analyticsConfiguration.syncedAccountFieldNames());
		}

		if (StringUtil.equals(
				baseModel.getModelClassName(), User.class.getName())) {

			User user = (User)baseModel;

			AnalyticsConfiguration analyticsConfiguration =
				_analyticsConfigurationRegistry.getAnalyticsConfiguration(
					user.getCompanyId());

			includeAttributeNames = ListUtil.fromArray(
				analyticsConfiguration.syncedUserFieldNames());

			_addFieldAttributes(
				user.getContact(), fields,
				_filterAttributeNames(
					ListUtil.fromArray(
						analyticsConfiguration.syncedContactFieldNames()),
					includeAttributeNames));
		}

		_addFieldAttributes(baseModel, fields, includeAttributeNames);

		if (StringUtil.equals(
				baseModel.getModelClassName(), Group.class.getName())) {

			for (Field field : fields) {
				if (StringUtil.equals(field.getName(), "name")) {
					Group group = (Group)baseModel;

					field.setValue(group.getNameCurrentValue());

					break;
				}
			}
		}

		if (StringUtil.equals(
				baseModel.getModelClassName(), Organization.class.getName())) {

			Field field = new Field();

			field.setName("parentOrganizationName");

			Organization organization = (Organization)baseModel;

			field.setValue(organization.getParentOrganizationName());

			fields.add(field);
		}

		return fields.toArray(new Field[0]);
	}

	private boolean _isCustomField(String className, long tableId) {
		long classNameId = _classNameLocalService.getClassNameId(className);

		try {
			ExpandoTable expandoTable = _expandoTableLocalService.getTable(
				tableId);

			if (Objects.equals(
					ExpandoTableConstants.DEFAULT_TABLE_NAME,
					expandoTable.getName()) &&
				(expandoTable.getClassNameId() == classNameId)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get expando table " + tableId, exception);
			}
		}

		return false;
	}

	private String _parseValue(Object value) {
		if (value != null) {
			Class<?> clazz = value.getClass();

			if (!clazz.isArray()) {
				return String.valueOf(value);
			}

			JSONArray jsonArray = _jsonFactory.createJSONArray();

			for (int i = 0; i < Array.getLength(value); i++) {
				jsonArray.put(Array.get(value, i));
			}

			return jsonArray.toString();
		}

		return null;
	}

	private DXPEntity _toDXPEntity(
		ExpandoField[] expandoFields, Field[] fields, String id,
		Date modifiedDate, String type) {

		DXPEntity dxpEntity = new DXPEntity();

		if (expandoFields == null) {
			expandoFields = new ExpandoField[0];
		}

		dxpEntity.setExpandoFields(expandoFields);

		if (fields == null) {
			fields = new Field[0];
		}

		dxpEntity.setFields(fields);
		dxpEntity.setId(id);
		dxpEntity.setModifiedDate(modifiedDate);
		dxpEntity.setType(type);

		return dxpEntity;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DXPEntityDTOConverterImpl.class);

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}