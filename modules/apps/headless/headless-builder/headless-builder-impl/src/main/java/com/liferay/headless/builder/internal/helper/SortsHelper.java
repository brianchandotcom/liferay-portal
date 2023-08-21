/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.odata.entity.APIPropertyEntityField;
import com.liferay.headless.builder.internal.odata.entity.APISchemaEntityModel;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.SortUtil;

import java.util.Map;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = SortsHelper.class)
public class SortsHelper {

	public Sort[] getSorts(
		AcceptLanguage acceptLanguage, long companyId,
		APIApplication.Endpoint endpoint, String sortString) {

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		try {
			EntityModel entityModel = _entityModelProvider.getEntityModel(
				_objectDefinitionLocalService.
					getObjectDefinitionByExternalReferenceCode(
						responseSchema.
							getMainObjectDefinitionExternalReferenceCode(),
						companyId));

			if (Validator.isNull(sortString)) {
				APIApplication.Sort sort = endpoint.getSort();

				String oDataSortString =
					(sort != null) ? sort.getODataSortString() : null;

				return SortUtil.getSorts(
					acceptLanguage, entityModel,
					_sortParserProvider.provide(entityModel), oDataSortString);
			}

			return _getSortsFromParamSortString(
				acceptLanguage, entityModel, responseSchema, sortString);
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
		AcceptLanguage acceptLanguage, EntityModel entityModel,
		APIApplication.Schema schema, String sortString) {

		APISchemaEntityModel apiSchemaEntityModel = new APISchemaEntityModel(
			entityModel, schema);

		Map<String, EntityField> entityFieldsMap =
			apiSchemaEntityModel.getEntityFieldsMap();

		return TransformUtil.transform(
			SortUtil.getSorts(
				acceptLanguage, apiSchemaEntityModel,
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

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SortParserProvider _sortParserProvider;

}