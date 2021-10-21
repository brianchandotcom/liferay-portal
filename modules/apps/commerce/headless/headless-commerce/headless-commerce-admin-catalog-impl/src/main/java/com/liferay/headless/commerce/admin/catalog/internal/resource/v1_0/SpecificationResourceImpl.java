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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Specification;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.SpecificationDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.odata.entity.v1_0.SpecificationEntityModel;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SpecificationResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/specification.properties",
	scope = ServiceScope.PROTOTYPE, service = SpecificationResource.class
)
@CTAware
public class SpecificationResourceImpl
	extends BaseSpecificationResourceImpl implements EntityModelResource {

	@Override
	public Response deleteSpecification(Long id) throws Exception {
		_cpSpecificationOptionService.deleteCPSpecificationOption(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Specification getSpecification(Long id) throws Exception {
		return _toSpecification(GetterUtil.getLong(id));
	}

	@Override
	public Page<Specification> getSpecificationsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CPSpecificationOption.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toSpecification(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Response patchSpecification(Long id, Specification specification)
		throws Exception {

		_updateSpecification(id, specification);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Specification postSpecification(Specification specification)
		throws Exception {

		return _addOrUpdateSpecification(specification);
	}

	private Specification _addOrUpdateSpecification(Specification specification)
		throws Exception {

		Long specificationId = specification.getId();

		if (specificationId != null) {
			try {
				CPSpecificationOption cpSpecificationOption =
					_updateSpecification(specificationId, specification);

				return _toSpecification(
					cpSpecificationOption.getCPSpecificationOptionId());
			}
			catch (NoSuchCPSpecificationOptionException
						noSuchCPSpecificationOptionException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find specification with ID: " +
							specificationId,
						noSuchCPSpecificationOptionException);
				}
			}
		}

		String specificationKey = specification.getKey();

		if (specificationKey != null) {
			try {
				CPSpecificationOption cpSpecificationOption =
					_updateSpecification(specificationKey, specification);

				return _toSpecification(
					cpSpecificationOption.getCPSpecificationOptionId());
			}
			catch (NoSuchCPSpecificationOptionException
						noSuchCPSpecificationOptionException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find specification with key: " +
							specificationKey,
						noSuchCPSpecificationOptionException);
				}
			}
		}

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionService.addCPSpecificationOption(
				_getCPOptionCategoryId(specification),
				LanguageUtils.getLocalizedMap(specification.getTitle()),
				LanguageUtils.getLocalizedMap(specification.getDescription()),
				_isFacetable(specification), specification.getKey(),
				_serviceContextHelper.getServiceContext());

		return _toSpecification(
			cpSpecificationOption.getCPSpecificationOptionId());
	}

	private long _getCPOptionCategoryId(Specification specification) {
		OptionCategory optionCategory = specification.getOptionCategory();

		if (optionCategory == null) {
			return 0;
		}

		return optionCategory.getId();
	}

	private boolean _isFacetable(Specification specification) {
		boolean facetable = false;

		if (specification.getFacetable() != null) {
			facetable = specification.getFacetable();
		}

		return facetable;
	}

	private Specification _toSpecification(Long cpSpecificationOptionId)
		throws Exception {

		return _specificationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpSpecificationOptionId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private CPSpecificationOption _updateSpecification(
			Long id, Specification specification)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionService.getCPSpecificationOption(id);

		return _cpSpecificationOptionService.updateCPSpecificationOption(
			cpSpecificationOption.getCPSpecificationOptionId(),
			_getCPOptionCategoryId(specification),
			LanguageUtils.getLocalizedMap(specification.getTitle()),
			LanguageUtils.getLocalizedMap(specification.getDescription()),
			_isFacetable(specification), specification.getKey(),
			_serviceContextHelper.getServiceContext());
	}

	private CPSpecificationOption _updateSpecification(
			String key, Specification specification)
		throws PortalException {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionService.getCPSpecificationOption(
				serviceContext.getCompanyId(), key);

		return _cpSpecificationOptionService.updateCPSpecificationOption(
			cpSpecificationOption.getCPSpecificationOptionId(),
			_getCPOptionCategoryId(specification),
			LanguageUtils.getLocalizedMap(specification.getTitle()),
			LanguageUtils.getLocalizedMap(specification.getDescription()),
			_isFacetable(specification), key, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SpecificationResourceImpl.class);

	private static final EntityModel _entityModel =
		new SpecificationEntityModel();

	@Reference
	private CPSpecificationOptionService _cpSpecificationOptionService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private SpecificationDTOConverter _specificationDTOConverter;

}