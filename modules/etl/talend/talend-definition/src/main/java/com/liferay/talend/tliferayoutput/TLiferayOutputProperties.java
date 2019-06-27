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

package com.liferay.talend.tliferayoutput;

import com.liferay.talend.common.log.DebugUtils;
import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.talend.resource.LiferayOutputResourceProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Zoltán Takács
 * @author Ivica Cardic
 */
public class TLiferayOutputProperties
	extends LiferayConnectionResourceBaseProperties {

	public static final List<String> rejectSchemaFieldNames = Arrays.asList(
		com.liferay.talend.common.schema.constants.SchemaConstants.
			FIELD_ERROR_MESSAGE);

	public TLiferayOutputProperties(String name) {
		super(name);
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form advancedForm = getForm(Form.ADVANCED);

		advancedForm.addRow(dieOnError);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		dieOnError.setValue(true);

		resource = new LiferayOutputResourceProperties(
			"resource", schemaFlow, schemaReject);

		resource.connection = connection;

		resource.setupProperties();

		resource.setSchemaListener(
			new ISchemaListener() {

				/**
				 * We have to reset the schema because of a Talend's internal
				 * mechanism. @see https://github.com/Talend/tdi-studio-se/blob/737243fcdf1591970536d46edad98d2992b16593/main/plugins/org.talend.designer.core.generic/src/main/java/org/talend/designer/core/generic/model/GenericElementParameter.java#L319
				 * @review
				 */
				@Override
				public void afterSchema() {
					Schema schema = resource.main.schema.getValue();

					if (_log.isTraceEnabled()) {
						_log.trace("Schema details:\n" + schema.toString());

						DebugUtils.logCurrentStackTrace(_log);
					}

					if (schema.equals(SchemaProperties.EMPTY_SCHEMA)) {
						resource.main.schema.setValue(
							SchemaProperties.EMPTY_SCHEMA);
					}
				}

			});

		_setupSchemas();
	}

	public void setValidationResult(
		ValidationResult validationResult,
		ValidationResultMutable validationResultMutable) {

		validationResultMutable.setMessage(validationResult.getMessage());
		validationResultMutable.setStatus(validationResult.getStatus());
	}

	public Property<Boolean> dieOnError = PropertyFactory.newBoolean(
		"dieOnError");
	public SchemaProperties schemaFlow = new SchemaProperties("schemaFlow");
	public SchemaProperties schemaReject = new SchemaProperties("schemaReject");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnectors) {

		Set<PropertyPathConnector> connectors = new HashSet<>();

		if (outputConnectors) {
			connectors.add(flowConnector);
			connectors.add(rejectConnector);
		}
		else {
			connectors.add(mainConnector);
		}

		return connectors;
	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			TLiferayOutputProperties.class);
	}

	protected transient PropertyPathConnector flowConnector =
		new PropertyPathConnector(Connector.MAIN_NAME, "schemaFlow");
	protected transient PropertyPathConnector rejectConnector =
		new PropertyPathConnector(Connector.REJECT_NAME, "schemaReject");

	private void _setupSchemas() {
		Schema.Field docIdField = new Schema.Field(
			"resourceId", AvroUtils._string(), null, (Object)null,
			Schema.Field.Order.ASCENDING);

		docIdField.addProp(SchemaConstants.TALEND_FIELD_GENERATED, "true");
		docIdField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		List<Schema.Field> fields = new ArrayList<>();

		fields.add(docIdField);

		Schema initialSchema = Schema.createRecord(
			"liferay", null, null, false, fields);

		resource.main.schema.setValue(initialSchema);

		_updateOutputSchemas();
	}

	private void _updateOutputSchemas() {
		if (_log.isDebugEnabled()) {
			_log.debug("Update output schemas");
		}

		Schema inputSchema = resource.main.schema.getValue();

		schemaFlow.schema.setValue(inputSchema);

		Schema rejectSchema = SchemaUtils.createRejectSchema(inputSchema);

		schemaReject.schema.setValue(rejectSchema);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		TLiferayOutputProperties.class);

}