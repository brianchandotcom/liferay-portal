/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.openapi.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Magdalena Jedraszak
 */
@Component(service = OpenAPIContributor.class)
public class FilterableFieldsOpenAPIContributor implements OpenAPIContributor {

	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception {

		if ((openAPIContext == null) || (openAPI.getComponents() == null)) {
			return;
		}

		Map<String, Schema> schemas = openAPI.getComponents(
		).getSchemas();

		if (schemas.isEmpty()) {
			return;
		}

		String jaxRsApplicationName = _getJaxRsApplicationName(openAPIContext);

		if (Validator.isBlank(jaxRsApplicationName)) {
			return;
		}

		for (Schema schema : schemas.values()) {
			Map<String, EntityField> entityFieldsMap = _getEntityFieldsMap(
				jaxRsApplicationName, openAPIContext, schema);

			if (MapUtil.isEmpty(entityFieldsMap)) {
				continue;
			}

			Map<EntityField, Integer> entityFieldsDepth = new HashMap<>();
			Map<EntityField, String> filterableFields = new HashMap<>();

			for (Map.Entry<String, EntityField> entry :
					entityFieldsMap.entrySet()) {

				_contributeToFilterableFields(
					0, entry.getValue(), entityFieldsDepth, entry.getKey(),
					filterableFields);
			}

			schema.addExtension(
				"x-filterable",
				ListUtil.sort(new ArrayList<>(filterableFields.values())));
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;
	}

	private void _contributeToFilterableFields(
		int depth, EntityField entityField,
		Map<EntityField, Integer> entityFieldsDepth, String fieldName,
		Map<EntityField, String> filterableFiels) {

		if (!(entityField instanceof ComplexEntityField)) {
			entityFieldsDepth.compute(
				entityField,
				(key, previousDepth) -> {
					if ((previousDepth == null) || (previousDepth > depth)) {
						filterableFiels.put(entityField, fieldName);

						return depth;
					}

					return previousDepth;
				});

			return;
		}

		ComplexEntityField complexEntityField = (ComplexEntityField)entityField;

		if (entityFieldsDepth.containsKey(entityField)) {
			return;
		}

		entityFieldsDepth.put(entityField, depth);

		Map<String, EntityField> entityFieldsMap =
			complexEntityField.getEntityFieldsMap();

		for (Map.Entry<String, EntityField> entry :
				entityFieldsMap.entrySet()) {

			_contributeToFilterableFields(
				depth + 1, entry.getValue(), entityFieldsDepth,
				fieldName + "/" + entry.getKey(), filterableFiels);
		}
	}

	private Map<String, EntityField> _getEntityFieldsMap(
			String jaxRsApplicationName, OpenAPIContext openAPIContext,
			Schema schema)
		throws Exception {

		Map<String, Schema> properties = schema.getProperties();

		if (properties == null) {
			return null;
		}

		Schema xClassNameSchema = properties.get("x-class-name");

		if (xClassNameSchema == null) {
			return null;
		}

		String xClassNameDefault = (String)xClassNameSchema.getDefault();

		if (Validator.isBlank(xClassNameDefault)) {
			return null;
		}

		Schema xSchemaNameSchema = properties.get("x-schema-name");

		String xSchemaName = null;

		if (xSchemaNameSchema != null) {
			xSchemaName = (String)xSchemaNameSchema.getDefault();
		}

		List<EntityModelResource> entityModelResources =
			_getEntityModelResources(
				xClassNameDefault, jaxRsApplicationName, openAPIContext,
				xSchemaName);

		if (ListUtil.isEmpty(entityModelResources)) {
			return null;
		}

		Map<String, EntityField> entityFieldsMap = new HashMap<>();

		for (EntityModelResource entityModelResource : entityModelResources) {
			MultivaluedHashMap<String, Object> params =
				new MultivaluedHashMap<>();

			params.putSingle("companyId", openAPIContext.getCompanyId());

			EntityModel entityModel = entityModelResource.getEntityModel(
				params);

			if (entityModel == null) {
				continue;
			}

			Map<String, EntityField> currentEntityFieldsMap =
				entityModel.getEntityFieldsMap();

			if (currentEntityFieldsMap != null) {
				entityFieldsMap.putAll(currentEntityFieldsMap);
			}
		}

		return entityFieldsMap;
	}

	private List<EntityModelResource> _getEntityModelResources(
			String className, String jaxRsApplicationName,
			OpenAPIContext openAPIContext, String schemaName)
		throws Exception {

		String filterString = null;

		if (schemaName != null) {
			filterString = StringBundler.concat(
				"(entity.class.name=", className, "#",
				StringUtil.toLowerCase(schemaName), ")");
		}
		else {
			filterString = "(entity.class.name=" + className + ")";
		}

		ServiceReference<?>[] resourceServiceReferences =
			_bundleContext.getServiceReferences(
				(String)null,
				StringBundler.concat(
					"(&(api.version=",
					GetterUtil.get(openAPIContext.getVersion(), "v1.0"),
					")(osgi.jaxrs.resource=true)",
					"(osgi.jaxrs.application.select=\\(osgi.jaxrs.name=",
					jaxRsApplicationName, "\\))", filterString, ")"));

		if (resourceServiceReferences == null) {
			return null;
		}

		List<EntityModelResource> resources = new ArrayList<>();

		for (ServiceReference<?> serviceReference : resourceServiceReferences) {
			Object service = _bundleContext.getService(serviceReference);

			if (service instanceof EntityModelResource) {
				resources.add((EntityModelResource)service);
			}
		}

		return resources;
	}

	private String _getJaxRsApplicationName(OpenAPIContext openAPIContext)
		throws Exception {

		String trimmedPath = StringUtil.removeFirst(
			openAPIContext.getPath(), "/o");

		String applicationBase = StringUtil.replaceLast(trimmedPath, '/', "");

		Collection<ServiceReference<Application>> serviceReferences =
			_bundleContext.getServiceReferences(
				Application.class,
				String.format(
					"(osgi.jaxrs.application.base=%s)", applicationBase));

		if (serviceReferences.isEmpty()) {
			return null;
		}

		ServiceReference<Application> serviceReference =
			serviceReferences.iterator(
			).next();

		return (String)serviceReference.getProperty("osgi.jaxrs.name");
	}

	private BundleContext _bundleContext;

}