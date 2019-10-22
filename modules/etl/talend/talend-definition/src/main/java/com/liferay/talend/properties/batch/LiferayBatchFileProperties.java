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

package com.liferay.talend.properties.batch;

import static org.talend.daikon.properties.presentation.Widget.widget;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.common.daikon.DaikonUtil;
import com.liferay.talend.common.oas.OASException;
import com.liferay.talend.common.oas.OASExplorer;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.common.schema.SchemaBuilder;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.properties.ExceptionUtils;
import com.liferay.talend.resource.LiferayOutputResourceProperties;
import com.liferay.talend.source.LiferayOASSource;

import java.util.Collections;
import java.util.Set;

import javax.json.JsonObject;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Igor Beslic
 */
public class LiferayBatchFileProperties
	extends FixedConnectorsComponentProperties {

	public LiferayBatchFileProperties(String name) {
		super(name);

		batchFilePath.setRequired();
	}

	public LiferayBatchFileProperties(
		String batchFilePath, Schema entitySchema, String name,
		JsonObject oasJsonObject) {

		this(name);

		_setBatchFilePathValue(batchFilePath);

		_setEntitySchemaValue(entitySchema);

		_oasJsonObject = oasJsonObject;
	}

	public ValidationResult afterEntity() {
		_logger.info("After string property {entity}");

		SchemaBuilder schemaBuilder = new SchemaBuilder();

		Property<Schema> entitySchemaProperty = entitySchema.schema;

		entitySchemaProperty.setValue(
			schemaBuilder.getEntitySchema(
				_getEntityName(), _getOASJsonObject()));

		_logger.info("After string property {entity}");

		return null;
	}

	public ValidationResult beforeEntity() {
		_logger.info("Before string property {entity}");

		OASExplorer oasExplorer = new OASExplorer();

		try {
			Set<String> entitySchemaNames = oasExplorer.getEntitySchemaNames(
				_getOASJsonObject());

			if (entitySchemaNames.isEmpty()) {
				return new ValidationResult(
					ValidationResult.Result.ERROR,
					_i18nMessages.getMessage("error.validation.resources"));
			}

			entity.setPossibleNamedThingValues(
				DaikonUtil.toNamedThings(entitySchemaNames));
		}
		catch (Exception e) {
			return ExceptionUtils.exceptionToValidationResult(e);
		}

		return null;
	}

	public String getBatchFilePath() {
		return batchFilePath.getStringValue();
	}

	public Schema getEntitySchema() {
		Property<Schema> schemaProperty = entitySchema.schema;

		return schemaProperty.getValue();
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(connection.getForm(Form.REFERENCE));

		Widget entitySelectWidget = Widget.widget(entity);

		entitySelectWidget.setCallAfter(true);
		entitySelectWidget.setLongRunning(true);
		entitySelectWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		mainForm.addRow(entitySelectWidget);

		mainForm.addRow(entitySchema.getForm(Form.REFERENCE));

		Widget bulkFilePathWidget = widget(batchFilePath);

		bulkFilePathWidget.setWidgetType(Widget.FILE_WIDGET_TYPE);

		mainForm.addRow(bulkFilePathWidget);
	}

	public Property<String> batchFilePath = PropertyFactory.newProperty(
		"batchFilePath");
	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
	public StringProperty entity = new StringProperty("entity");
	public SchemaProperties entitySchema = new SchemaProperties("entitySchema");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnection) {

		if (outputConnection) {
			return Collections.emptySet();
		}

		return Collections.singleton(
			new PropertyPathConnector(Connector.MAIN_NAME, "entitySchema"));
	}

	private String _getEntityName() {
		return entity.getValue();
	}

	private JsonObject _getOASJsonObject() {
		if (_oasJsonObject != null) {
			return _oasJsonObject;
		}

		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				connection.getReferencedConnectionProperties());

		if (!liferayOASSource.isValid()) {
			throw new OASException("Unable to obtain OpenAPI specification");
		}

		OASSource oasSource = liferayOASSource.getOASSource();

		_oasJsonObject = oasSource.getOASJsonObject();

		return _oasJsonObject;
	}

	private void _setBatchFilePathValue(String value) {
		batchFilePath.setValue(value);
	}

	private void _setEntitySchemaValue(Schema value) {
		Property<Schema> schemaProperty = entitySchema.schema;

		schemaProperty.setValue(value);
	}

	private static Logger _logger = LoggerFactory.getLogger(
		LiferayBatchFileProperties.class);

	private static final I18nMessages _i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		_i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayOutputResourceProperties.class);
	}

	private transient JsonObject _oasJsonObject;

}