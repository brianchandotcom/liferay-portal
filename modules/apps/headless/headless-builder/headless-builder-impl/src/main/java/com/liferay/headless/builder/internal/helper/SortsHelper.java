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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.SortUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = SortsHelper.class)
public class SortsHelper {

	public Sort[] getSorts(
			AcceptLanguage acceptLanguage, long companyId,
			APIApplication.Endpoint endpoint, String sortString)
		throws PortalException {

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		if (Validator.isNotNull(sortString)) {
			APISchemaEntityModel apiSchemaEntityModel =
				new APISchemaEntityModel(
					_getEntityModel(companyId, responseSchema), responseSchema);

			Map<String, EntityField> entityFieldsMap =
				apiSchemaEntityModel.getEntityFieldsMap();

			return TransformUtil.transform(
				SortUtil.getSorts(
					acceptLanguage, apiSchemaEntityModel,
					_sortParserProvider.provide(apiSchemaEntityModel),
					sortString),
				sort -> {
					APIPropertyEntityField apiPropertyEntityField =
						(APIPropertyEntityField)entityFieldsMap.get(
							sort.getFieldName());

					sort.setFieldName(apiPropertyEntityField.getInternalName());

					return sort;
				},
				Sort.class);
		}

		APIApplication.Sort sort = endpoint.getSort();

		if (sort != null) {
			return SortUtil.getSorts(
				acceptLanguage, _getEntityModel(companyId, responseSchema),
				_sortParserProvider.provide(
					_getEntityModel(companyId, responseSchema)),
				sort.getODataSortString());
		}

		return null;
	}

	private EntityModel _getEntityModel(
			long companyId, APIApplication.Schema schema)
		throws PortalException {

		return _entityModelProvider.getEntityModel(
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					schema.getMainObjectDefinitionExternalReferenceCode(),
					companyId));
	}

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SortParserProvider _sortParserProvider;

}