/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.resolver;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.odata.entity.APIPropertyEntityField;
import com.liferay.headless.builder.internal.odata.entity.APISchemaEntityModel;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.SortUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class APIApplicationSortContextResolver
	implements ContextResolver<Sort[]> {

	public APIApplicationSortContextResolver(
		EntityModelProvider entityModelProvider,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		SortParserProvider sortParserProvider) {

		_entityModelProvider = entityModelProvider;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_sortParserProvider = sortParserProvider;
	}

	@Override
	public Sort[] getContext(Class<?> aClass) {
		APIApplication.Schema responseSchema = _endpoint.getResponseSchema();

		try {
			EntityModel entityModel = _entityModelProvider.getEntityModel(
				_objectDefinitionLocalService.
					getObjectDefinitionByExternalReferenceCode(
						responseSchema.
							getMainObjectDefinitionExternalReferenceCode(),
						_company.getCompanyId()));

			String sortString = ParamUtil.getString(
				_httpServletRequest, "sort");

			if (Validator.isNull(sortString)) {
				APIApplication.Sort sort = _endpoint.getSort();

				String oDataSortString =
					(sort != null) ? sort.getODataSortString() : null;

				return SortUtil.getSorts(
					_contextAcceptLanguage, entityModel,
					_sortParserProvider.provide(entityModel), oDataSortString);
			}

			return _getSortsFromParamSortString(
				entityModel, responseSchema, sortString);
		}
		catch (WebApplicationException webApplicationException) {
			throw webApplicationException;
		}
		catch (Exception exception) {
			throw new ServerErrorException(
				exception.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	private Sort[] _getSortsFromParamSortString(
		EntityModel entityModel, APIApplication.Schema schema,
		String sortString) {

		APISchemaEntityModel apiSchemaEntityModel = new APISchemaEntityModel(
			entityModel, schema);

		Map<String, EntityField> entityFieldsMap =
			apiSchemaEntityModel.getEntityFieldsMap();

		return TransformUtil.transform(
			SortUtil.getSorts(
				_contextAcceptLanguage, apiSchemaEntityModel,
				_sortParserProvider.provide(apiSchemaEntityModel), sortString),
			sort -> {
				APIPropertyEntityField apiPropertyEntityField =
					(APIPropertyEntityField)entityFieldsMap.get(
						sort.getFieldName());

				sort.setFieldName(apiPropertyEntityField.getInternalName());

				return sort;
			},
			Sort.class);
	}

	@Context
	private Company _company;

	@Context
	private AcceptLanguage _contextAcceptLanguage;

	@Context
	private APIApplication.Endpoint _endpoint;

	private final EntityModelProvider _entityModelProvider;

	@Context
	private HttpServletRequest _httpServletRequest;

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final SortParserProvider _sortParserProvider;

	@Context
	private Sort[] _sorts;

}