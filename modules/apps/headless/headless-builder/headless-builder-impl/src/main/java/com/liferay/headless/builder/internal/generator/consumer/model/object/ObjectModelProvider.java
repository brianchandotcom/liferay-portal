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

package com.liferay.headless.builder.internal.generator.consumer.model.object;

import com.liferay.asset.kernel.NoSuchClassTypeException;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiApplicationModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiEndpointModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiSchemaModel;
import com.liferay.headless.builder.internal.generator.consumer.model.BaseModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ModelProvider;
import com.liferay.headless.builder.internal.generator.consumer.model.object.constants.ObjectModelConstants;
import com.liferay.headless.builder.internal.generator.consumer.model.object.transformer.ApiApplicationModelTransformer;
import com.liferay.headless.builder.internal.generator.consumer.model.object.transformer.ApiEndpointModelTransformer;
import com.liferay.headless.builder.internal.generator.consumer.model.object.transformer.ApiSchemaModelTransformer;
import com.liferay.headless.builder.internal.generator.consumer.model.object.transformer.ObjectModelTransformer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ModelProvider.class)
public class ObjectModelProvider implements ModelProvider {

	@SuppressWarnings("unchecked")
	public <T extends BaseModel> T getModel(
			String apiApplicationERC, Class<T> clazz)
		throws Exception {

		List<T> models = getModels(apiApplicationERC, clazz);

		if (ListUtil.isEmpty(models)) {
			return null;
		}

		return models.get(0);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModel> T getModelByApiApplicationBaseURL(
			String apiApplicationBaseURL, Class<T> clazz)
		throws Exception {

		List<T> models = getModels(
			_getApiApplicationExternalReferenceCode(apiApplicationBaseURL),
			clazz);

		if (ListUtil.isEmpty(models)) {
			return null;
		}

		return models.get(0);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModel> List<T> getModels(
			String apiApplicationERC, Class<T> clazz)
		throws Exception {

		ObjectModelTransformer objectModelTransformer = null;
		UnsafeSupplier<Page<ObjectEntry>, Exception> unsafeSupplier = null;

		if (clazz.isAssignableFrom(ApiApplicationModel.class)) {
			objectModelTransformer = new ApiApplicationModelTransformer();

			unsafeSupplier = () -> _getObjectEntries(
				apiApplicationERC, "MSOD_API_APPLICATION", null, null);
		}
		else if (clazz.isAssignableFrom(ApiEndpointModel.class)) {
			objectModelTransformer = new ApiEndpointModelTransformer();

			unsafeSupplier = () -> _getObjectEntries(
				apiApplicationERC, "MSOD_API_ENDPOINT", null,
				ObjectModelConstants.
					APPLICATION_TO_ENDPOINTS_RELATIONSHIP_NAME);
		}
		else if (clazz.isAssignableFrom(ApiSchemaModel.class)) {
			objectModelTransformer = new ApiSchemaModelTransformer();

			unsafeSupplier = () -> _getObjectEntries(
				apiApplicationERC, "MSOD_API_SCHEMA",
				Arrays.asList(
					ObjectModelConstants.
						SCHEMA_TO_PROPERTIES_RELATIONSHIP_NAME),
				ObjectModelConstants.APPLICATION_TO_SCHEMAS_RELATIONSHIP_NAME);
		}
		else {
			throw new NoSuchClassTypeException(
				"Impossible to transform into class " + clazz);
		}

		Page<ObjectEntry> objectEntriesPage = unsafeSupplier.get();

		ObjectModelTransformer finalObjectModelTransformer =
			objectModelTransformer;

		return (List<T>)TransformUtil.transform(
			objectEntriesPage.getItems(),
			objectEntry -> finalObjectModelTransformer.toModel(
				CompanyThreadLocal.getCompanyId(), objectEntry));
	}

	private String _getApiApplicationExternalReferenceCode(String baseURL)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			"MSOD_API_APPLICATION");

		for (com.liferay.object.model.ObjectEntry objectEntry :
				_objectEntryLocalService.getObjectEntries(
					GroupThreadLocal.getGroupId(),
					objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			Map<String, Serializable> values = objectEntry.getValues();

			if (!Objects.equals(values.get("baseURL"), baseURL)) {
				continue;
			}

			return objectEntry.getExternalReferenceCode();
		}

		throw new NoSuchModelException(
			"API Application not found for the baseUrl: " + baseURL);
	}

	private ObjectDefinition _getObjectDefinition(String externalReferenceCode)
		throws Exception {

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				externalReferenceCode, CompanyThreadLocal.getCompanyId());
	}

	private Page<ObjectEntry> _getObjectEntries(
			String apiApplicationERC, String externalReferenceCode,
			List<String> nestedFields, String relationshipName)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			externalReferenceCode);

		User user = _getUser(apiApplicationERC);

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(user));

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, user);

		String filterString = StringBundler.concat(
			"externalReferenceCode eq '", apiApplicationERC, "'");

		if (relationshipName != null) {
			filterString = relationshipName + "/" + filterString;
		}

		String finalFilterString = filterString;

		UnsafeSupplier<Page<ObjectEntry>, Exception> unsafeSupplier =
			() -> _objectEntryManager.getObjectEntries(
				CompanyThreadLocal.getCompanyId(), objectDefinition, null, null,
				dtoConverterContext, finalFilterString,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null,
				null);

		if (nestedFields != null) {
			return _withNestedFields(nestedFields, unsafeSupplier);
		}

		return unsafeSupplier.get();
	}

	private User _getUser(String apiApplicationERC) throws Exception {
		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryLocalService.getObjectEntry(
				apiApplicationERC, CompanyThreadLocal.getCompanyId(),
				GroupThreadLocal.getGroupId());

		return _userLocalService.getUser(objectEntry.getUserId());
	}

	private <T> T _withNestedFields(
			List<String> nestedFields,
			UnsafeSupplier<T, Exception> unsafeSupplier)
		throws Exception {

		NestedFieldsContext nestedFieldsContext = new NestedFieldsContext(
			1, nestedFields);

		NestedFieldsContext oldNestedFieldsContext =
			NestedFieldsContextThreadLocal.getAndSetNestedFieldsContext(
				nestedFieldsContext);

		try {
			return unsafeSupplier.get();
		}
		finally {
			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				oldNestedFieldsContext);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}