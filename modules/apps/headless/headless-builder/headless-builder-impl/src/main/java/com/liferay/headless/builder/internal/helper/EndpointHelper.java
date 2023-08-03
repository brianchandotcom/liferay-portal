/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.odata.entity.APISchemaEntityModel;
import com.liferay.headless.builder.internal.odata.filter.expression.APISchemaTranslatorExpressionVisitor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.filter.parser.ObjectDefinitionFilterParser;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.factory.ExpressionFactory;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 * @author Carlos Correa
 * @author Alejandro Tardín
 */
@Component(service = EndpointHelper.class)
public class EndpointHelper {

	public Page<Map<String, Object>> getResponseEntityMapsPage(
			long companyId, APIApplication.Endpoint endpoint,
			String filterString, Pagination pagination, String scopeKey)
		throws Exception {

		List<Map<String, Object>> responseEntityMaps = new ArrayList<>();

		Set<String> relationshipsNames = new HashSet<>();

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		for (APIApplication.Property property :
				responseSchema.getProperties()) {

			relationshipsNames.addAll(property.getObjectRelationshipNames());
		}

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryHelper.getObjectEntriesPage(
				companyId,
				_getFilterExpression(companyId, endpoint, filterString),
				ListUtil.fromCollection(relationshipsNames), pagination,
				responseSchema.getMainObjectDefinitionExternalReferenceCode(),
				scopeKey);

		for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
			Map<String, Object> responseEntityMap = new HashMap<>();

			Map<String, Object> objectEntryProperties =
				_getObjectEntryProperties(objectEntry);

			for (APIApplication.Property property :
					responseSchema.getProperties()) {

				List<String> objectRelationshipNames =
					property.getObjectRelationshipNames();

				if (objectRelationshipNames.isEmpty()) {
					responseEntityMap.put(
						property.getName(),
						objectEntryProperties.get(
							property.getSourceFieldName()));

					continue;
				}

				responseEntityMap.put(
					property.getName(),
					_getRelatedObjectValue(
						objectEntry, property, objectRelationshipNames));
			}

			responseEntityMaps.add(responseEntityMap);
		}

		return Page.of(
			responseEntityMaps, pagination, objectEntriesPage.getTotalCount());
	}

	private Expression _getFilterExpression(
			long companyId, APIApplication.Endpoint endpoint,
			String filterString)
		throws Exception {

		APIApplication.Filter filter = endpoint.getFilter();

		if ((filter == null) && (filterString == null)) {
			return null;
		}

		APIApplication.Schema schema = endpoint.getResponseSchema();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					schema.getMainObjectDefinitionExternalReferenceCode(),
					companyId);

		EntityModel entityModel = _entityModelProvider.getEntityModel(
			objectDefinition);

		Expression endpointFilterExpression = null;

		if (filter != null) {
			endpointFilterExpression = _objectDefinitionFilterParser.parse(
				entityModel, filter.getODataFilterString(), objectDefinition);
		}

		Expression requestFilterExpression = null;

		if (filterString != null) {
			EntityModel apiSchemaEntityModel = new APISchemaEntityModel(
				entityModel, endpoint.getResponseSchema());

			Expression expression = _objectDefinitionFilterParser.parse(
				apiSchemaEntityModel, filterString, objectDefinition);

			requestFilterExpression = expression.accept(
				new APISchemaTranslatorExpressionVisitor(
					apiSchemaEntityModel, _expressionFactory));
		}

		if (endpointFilterExpression == null) {
			return requestFilterExpression;
		}
		else if (requestFilterExpression == null) {
			return endpointFilterExpression;
		}

		return _expressionFactory.createBinaryExpression(
			endpointFilterExpression, BinaryExpression.Operation.AND,
			requestFilterExpression);
	}

	private Map<String, Object> _getObjectEntryProperties(
		ObjectEntry objectEntry) {

		return HashMapBuilder.<String, Object>putAll(
			objectEntry.getProperties()
		).put(
			"createDate", objectEntry.getDateCreated()
		).put(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).put(
			"modifiedDate", objectEntry.getDateModified()
		).build();
	}

	private Object _getRelatedObjectValue(
		ObjectEntry objectEntry, APIApplication.Property property,
		List<String> relationshipsNames) {

		if (relationshipsNames.isEmpty()) {
			Map<String, Object> objectEntryProperties =
				objectEntry.getProperties();

			return objectEntryProperties.get(property.getSourceFieldName());
		}

		List<Object> values = new ArrayList<>();

		Map<String, Object> properties = objectEntry.getProperties();

		ObjectEntry[] relatedObjectEntries = (ObjectEntry[])properties.get(
			relationshipsNames.remove(0));

		for (ObjectEntry relatedObjectEntry : relatedObjectEntries) {
			Object value = _getRelatedObjectValue(
				relatedObjectEntry, property,
				new ArrayList<>(relationshipsNames));

			if (value instanceof Collection<?>) {
				values.addAll((Collection<?>)value);
			}
			else {
				values.add(value);
			}
		}

		return values;
	}

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private ExpressionFactory _expressionFactory;

	@Reference
	private ObjectDefinitionFilterParser _objectDefinitionFilterParser;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

}