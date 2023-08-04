/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.resolver;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.SortUtil;

import javax.ws.rs.ServerErrorException;
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
		if (_sorts != null) {
			return _sorts;
		}

		APIApplication.Schema responseSchema = _endpoint.getResponseSchema();

		try {
			EntityModel entityModel = _entityModelProvider.getEntityModel(
				_objectDefinitionLocalService.
					getObjectDefinitionByExternalReferenceCode(
						responseSchema.
							getMainObjectDefinitionExternalReferenceCode(),
						_company.getCompanyId()));

			return SortUtil.getSorts(
				contextAcceptLanguage, entityModel,
				_sortParserProvider.provide(entityModel),
				_endpoint.getSort(
				).getODataSortString());
		}
		catch (Exception exception) {
			throw new ServerErrorException(
				exception.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	private Company _company;

	@Context
	private APIApplication.Endpoint _endpoint;

	private final EntityModelProvider _entityModelProvider;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final SortParserProvider _sortParserProvider;

	@Context
	private Sort[] _sorts;

}