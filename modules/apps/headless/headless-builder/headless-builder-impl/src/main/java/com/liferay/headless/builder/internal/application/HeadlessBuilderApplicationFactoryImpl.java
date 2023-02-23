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

package com.liferay.headless.builder.internal.application;

import com.liferay.headless.builder.application.HeadlessBuilderApplication;
import com.liferay.headless.builder.application.HeadlessBuilderApplicationFactory;
import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.headless.builder.internal.operation.OperationRegistry;
import com.liferay.headless.builder.internal.operation.handler.OperationHandler;
import com.liferay.headless.builder.internal.util.HeadlessBuilderUtil;
import com.liferay.headless.builder.internal.util.URLUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.FieldDefinition;
import com.liferay.portal.vulcan.yaml.openapi.Info;
import com.liferay.portal.vulcan.yaml.openapi.Method;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.OperationDefinition;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.ResponseCode;
import com.liferay.portal.vulcan.yaml.openapi.Schema;
import com.liferay.portal.vulcan.yaml.openapi.SchemaDefinition;

import java.io.InvalidObjectException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = HeadlessBuilderApplicationFactory.class)
public class HeadlessBuilderApplicationFactoryImpl
	implements HeadlessBuilderApplicationFactory {

	@Override
	public HeadlessBuilderApplication getHeadlessBuilderApplication(
			long companyId, OpenAPIYAML openAPIYAML)
		throws Exception {

		List<Operation> operations = new ArrayList<>();

		Components components = openAPIYAML.getComponents();

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		Info info = openAPIYAML.getInfo();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			PathItem pathItem = entry.getValue();

			_validate(pathItem);

			operations.addAll(
				_getOperations(
					companyId,
					URLUtil.getPathConfiguration(
						entry.getKey(), info.getVersion()),
					pathItem, components.getSchemas()));
		}

		return () -> {
			for (Operation operation : operations) {
				_operationRegistry.register(operation);
			}

			return () -> {
				for (Operation operation : operations) {
					_operationRegistry.unregister(operation);
				}
			};
		};
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_operationHandlerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, OperationHandler.class,
				HeadlessBuilderConstants.OPERATION_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_operationHandlerServiceTrackerMap.close();
	}

	private Set<InfoFieldType> _getCompatibleInfoFieldTypes(Schema schema) {
		String type = schema.getType();

		if (StringUtil.equals(type, "boolean")) {
			return SetUtil.fromArray(BooleanInfoFieldType.INSTANCE);
		}
		else if (StringUtil.equals(type, "integer")) {
			if (StringUtil.equals(schema.getFormat(), "int64")) {
				return SetUtil.fromArray(NumberInfoFieldType.INSTANCE);
			}
			else if (StringUtil.equals(schema.getFormat(), "int32")) {
				return SetUtil.fromArray(NumberInfoFieldType.INSTANCE);
			}
		}
		else if (StringUtil.equals(type, "string")) {
			if (StringUtil.equals(schema.getFormat(), "date-time")) {
				return SetUtil.fromArray(DateInfoFieldType.INSTANCE);
			}

			return SetUtil.fromArray(TextInfoFieldType.INSTANCE);
		}

		throw new UnsupportedOperationException(
			"Impossible to get the InfoFieldType of the type " + type);
	}

	private Map<String, InfoField> _getInfoFields(
			String entityName, Map<String, Schema> schemas)
		throws Exception {

		InfoItemFormProvider<?> infoItemFormProvider =
			HeadlessBuilderUtil.getInfoItemService(
				entityName, InfoItemFormProvider.class);

		Map<String, InfoField> infoFields = new HashMap<>();

		InfoForm infoForm = infoItemFormProvider.getInfoForm();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			Schema schema = entry.getValue();

			FieldDefinition fieldDefinition = schema.getFieldDefinition();

			if (fieldDefinition == null) {
				throw new InvalidObjectException(
					"FieldDefinition not found for the property " +
						entry.getKey());
			}

			String internalFieldName = fieldDefinition.getName();

			InfoField infoField = infoForm.getInfoField(internalFieldName);

			if (infoField == null) {
				throw new InvalidObjectException(
					StringBundler.concat(
						"There is no InfoField '", internalFieldName,
						"' registered for the class name '", entityName, "'"));
			}

			String externalFieldName = entry.getKey();

			Set<InfoFieldType> compatibleInfoFieldTypes =
				_getCompatibleInfoFieldTypes(schemas.get(externalFieldName));

			if (!compatibleInfoFieldTypes.contains(
					infoField.getInfoFieldType())) {

				throw new InvalidObjectException(
					StringBundler.concat(
						"Invalid types between the fields ", externalFieldName,
						" and ", infoField.getName()));
			}

			infoFields.put(externalFieldName, infoField);
		}

		return infoFields;
	}

	private List<Operation> _getOperations(
			long companyId, Operation.PathConfiguration pathConfiguration,
			PathItem pathItem, Map<String, Schema> schemas)
		throws Exception {

		List<Operation> operations = new ArrayList<>();

		for (Method method : Method.values()) {
			com.liferay.portal.vulcan.yaml.openapi.Operation operation =
				pathItem.get(method);

			if (operation == null) {
				continue;
			}

			OperationDefinition operationDefinition =
				operation.getOperationDefinition();

			Operation.Builder builder = new Operation.Builder(
			).withCompanyId(
				companyId
			).withMethod(
				method.name()
			).withOperationType(
				operationDefinition.getType()
			).withPathConfiguration(
				pathConfiguration
			);

			Map<ResponseCode, com.liferay.portal.vulcan.yaml.openapi.Response>
				responses = operation.getResponses();

			Map<String, InfoField> successfulInfoFields = null;

			for (Map.Entry
					<ResponseCode,
					 com.liferay.portal.vulcan.yaml.openapi.Response> entry :
						responses.entrySet()) {

				com.liferay.portal.vulcan.yaml.openapi.Response response =
					entry.getValue();

				Map<String, Content> contentMap = response.getContent();

				for (Map.Entry<String, Content> entry2 :
						contentMap.entrySet()) {

					Content content = entry2.getValue();

					Schema schema = content.getSchema();

					String schemaName = StringUtil.removeFirst(
						schema.getReference(), "#/components/schemas/");

					schema = schemas.get(schemaName);

					SchemaDefinition schemaDefinition =
						schema.getSchemaDefinition();

					Map<String, InfoField> infoFields = _getInfoFields(
						schemaDefinition.getEntityName(),
						schema.getPropertySchemas());

					ResponseCode responseCode = entry.getKey();

					int httpCode = responseCode.getHttpCode();

					if (Objects.equals(
							Response.Status.OK.getStatusCode(), httpCode)) {

						successfulInfoFields = infoFields;
					}

					builder.withResponse(
						new Operation.Response(
							schemaDefinition.getEntityName(), infoFields),
						entry2.getKey(), httpCode);
				}
			}

			if (successfulInfoFields == null) {
				throw new IllegalStateException(
					"There is no schema defined for a successful code");
			}

			operations.add(builder.build());
		}

		return operations;
	}

	private void _validate(PathItem pathItem) throws Exception {
		boolean empty = true;

		for (Method method : Method.values()) {
			com.liferay.portal.vulcan.yaml.openapi.Operation operation =
				pathItem.get(method);

			if (operation == null) {
				continue;
			}

			empty = false;

			OperationDefinition operationDefinition =
				operation.getOperationDefinition();

			if (operationDefinition == null) {
				throw new InvalidObjectException(
					"Missing operation definition");
			}

			String type = operationDefinition.getType();

			if (!_operationHandlerServiceTrackerMap.containsKey(type)) {
				throw new InvalidObjectException(
					"OperationProvider not defined for the type " + type);
			}
		}

		if (empty) {
			throw new InvalidObjectException("There is no operation defined");
		}
	}

	private ServiceTrackerMap<String, OperationHandler>
		_operationHandlerServiceTrackerMap;

	@Reference
	private OperationRegistry _operationRegistry;

}