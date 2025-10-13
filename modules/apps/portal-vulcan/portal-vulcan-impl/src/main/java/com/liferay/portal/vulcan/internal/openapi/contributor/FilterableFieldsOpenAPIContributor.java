/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.openapi.contributor;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import jakarta.ws.rs.core.MultivaluedHashMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Magdalena Jedraszak
 */
@Component(service = OpenAPIContributor.class)
public class FilterableFieldsOpenAPIContributor implements OpenAPIContributor {

	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception {

		if (openAPIContext == null) {
			return;
		}

		Components components = openAPI.getComponents();

		if (components == null) {
			return;
		}

		Map<String, Schema> schemas = components.getSchemas();

		if (MapUtil.isEmpty(schemas)) {
			return;
		}

		Map<String, Map<String, Object>> schemaNameFilterableFieldMapping =
			new HashMap<>();

		for (Schema schema : schemas.values()) {
			Map<String, Object> filterableFieldMapping =
				_getFilterableFieldMapping(openAPIContext, schema);

			schema.addExtension("x-filterable", filterableFieldMapping);

			schemaNameFilterableFieldMapping.put(
				schema.getName(), filterableFieldMapping);
		}

		Paths paths = openAPI.getPaths();

		if (MapUtil.isEmpty(paths)) {
			return;
		}

		for (PathItem pathItem : paths.values()) {
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getDelete());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getGet());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getHead());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getOptions());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getPatch());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getPost());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getPut());
			_setXFilterable(
				schemaNameFilterableFieldMapping, pathItem.getTrace());
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, null, "(osgi.jaxrs.resource=true)",
			(serviceReference, emitter) -> {
				try {
					if (!(_bundleContext.getService(serviceReference) instanceof
							EntityModelResource)) {

						return;
					}

					String apiVersion = (String)serviceReference.getProperty(
						"api.version");

					Object companyIdObject = serviceReference.getProperty(
						"companyId");

					String entityClassName =
						(String)serviceReference.getProperty(
							"entity.class.name");

					if (companyIdObject instanceof List) {
						for (Object object : (List<?>)companyIdObject) {
							emitter.emit(
								_encodeKey(
									entityClassName, GetterUtil.getLong(object),
									apiVersion));
						}

						return;
					}

					emitter.emit(
						_encodeKey(
							entityClassName,
							GetterUtil.getLong(companyIdObject), apiVersion));
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _encodeKey(
		String className, Long companyId, String version) {

		String key = StringBundler.concat(
			className, StringPool.POUND, GetterUtil.getString(version, "v1.0"));

		if (Validator.isNull(companyId)) {
			return key;
		}

		return key + StringPool.POUND + companyId;
	}

	private String _getClassName(String className, String schemaName) {
		if (Validator.isNull(schemaName)) {
			return className;
		}

		return className + "#" + StringUtil.toLowerCase(schemaName);
	}

	private Map<String, EntityField> _getEntityFieldsMap(
			OpenAPIContext openAPIContext, Schema schema)
		throws Exception {

		Map<String, Schema> properties = schema.getProperties();

		if (properties == null) {
			return Collections.emptyMap();
		}

		Schema xClassNameSchema = properties.get("x-class-name");

		if (xClassNameSchema == null) {
			return Collections.emptyMap();
		}

		String xClassNameDefault = (String)xClassNameSchema.getDefault();

		if (Validator.isBlank(xClassNameDefault)) {
			return Collections.emptyMap();
		}

		Schema xSchemaNameSchema = properties.get("x-schema-name");

		String xSchemaName = null;

		if (xSchemaNameSchema != null) {
			xSchemaName = (String)xSchemaNameSchema.getDefault();
		}

		String entityClassName = _getClassName(xClassNameDefault, xSchemaName);

		EntityModelResource entityModelResource = _getEntityModelResource(
			CompanyThreadLocal.getCompanyId(), entityClassName,
			openAPIContext.getVersion());

		if (entityModelResource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No EntityModelResource found for entityClassName: " +
						entityClassName);
			}

			return Collections.emptyMap();
		}

		Company company = _companyLocalService.fetchCompany(
			CompanyThreadLocal.getCompanyId());

		if (company == null) {
			return Collections.emptyMap();
		}

		entityModelResource.setContextCompany(company);

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyMap();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		if (entityFieldsMap == null) {
			return Collections.emptyMap();
		}

		return entityFieldsMap;
	}

	private EntityModelResource _getEntityModelResource(
		long companyId, String className, String version) {

		String companyIdKey = _encodeKey(className, companyId, version);

		if (_serviceTrackerMap.containsKey(companyIdKey)) {
			return _serviceTrackerMap.getService(companyIdKey);
		}

		String key = _encodeKey(className, null, version);

		if (_serviceTrackerMap.containsKey(key)) {
			return _serviceTrackerMap.getService(key);
		}

		return null;
	}

	private Map<String, Object> _getFilterableFieldMapping(
			OpenAPIContext openAPIContext, Schema schema)
		throws Exception {

		Map<String, EntityField> entityFieldsMap = _getEntityFieldsMap(
			openAPIContext, schema);

		if (MapUtil.isEmpty(entityFieldsMap)) {
			return Collections.emptyMap();
		}

		Map<String, Object> filterableFieldMapping = new LinkedHashMap<>();

		_populateFilterableFieldMappingItems(
			0, entityFieldsMap, filterableFieldMapping, null);

		return filterableFieldMapping;
	}

	private void _populateFilterableFieldMappingItems(
		long currentLevel, Map<String, EntityField> entityFieldsMap,
		Map<String, Object> filterableFieldMapping, String parentFieldName) {

		Map<String, Map<String, EntityField>> entityChildList =
			new LinkedHashMap<>();

		for (Map.Entry<String, EntityField> entry :
				entityFieldsMap.entrySet()) {

			String fieldName = entry.getKey();

			if (!Validator.isBlank(parentFieldName)) {
				if (StringUtil.equalsIgnoreCase(fieldName, parentFieldName)) {
					continue;
				}

				fieldName = parentFieldName + "/" + fieldName;
			}

			EntityField entityField = entry.getValue();

			if (entityField instanceof
					CollectionEntityField collectionEntityField) {

				EntityField internalEntityField =
					collectionEntityField.getEntityField();

				filterableFieldMapping.put(
					fieldName,
					HashMapBuilder.put(
						"items",
						StringUtil.toLowerCase(
							String.valueOf(internalEntityField.getType()))
					).put(
						"type", "array"
					).build());
			}
			else if ((entityField instanceof
						ComplexEntityField complexEntityField) &&
					 (currentLevel < 5)) {

				entityChildList.put(
					fieldName, complexEntityField.getEntityFieldsMap());
			}
			else {
				filterableFieldMapping.put(
					fieldName,
					HashMapBuilder.put(
						"type",
						StringUtil.toLowerCase(
							String.valueOf(entityField.getType()))
					).build());
			}
		}

		for (Map.Entry<String, Map<String, EntityField>> child :
				entityChildList.entrySet()) {

			_populateFilterableFieldMappingItems(
				currentLevel + 1, child.getValue(), filterableFieldMapping,
				child.getKey());
		}
	}

	private void _setXFilterable(
		Map<String, Map<String, Object>> filterableFieldMapping,
		Operation operation) {

		if (operation == null) {
			return;
		}

		List<String> tags = operation.getTags();

		if (ListUtil.isEmpty(tags)) {
			return;
		}

		List<Parameter> parameters = operation.getParameters();

		if (ListUtil.isEmpty(parameters)) {
			return;
		}

		Parameter filterParameter = null;

		for (Parameter parameter : parameters) {
			if (StringUtil.equals(parameter.getName(), "filter")) {
				filterParameter = parameter;

				break;
			}
		}

		if (filterParameter == null) {
			return;
		}

		Schema schema = filterParameter.getSchema();

		if (schema == null) {
			return;
		}

		schema.addExtension(
			"x-filterable", filterableFieldMapping.get(tags.get(0)));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FilterableFieldsOpenAPIContributor.class);

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ServiceTrackerMap<String, EntityModelResource> _serviceTrackerMap;

}