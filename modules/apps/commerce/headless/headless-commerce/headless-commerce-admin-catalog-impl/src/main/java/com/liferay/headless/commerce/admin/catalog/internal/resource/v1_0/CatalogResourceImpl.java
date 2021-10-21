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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.exception.NoSuchCatalogException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.CatalogDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.odata.entity.v1_0.CatalogEntityModel;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/catalog.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CatalogResource.class, NestedFieldSupport.class}
)
@CTAware
public class CatalogResourceImpl
	extends BaseCatalogResourceImpl
	implements EntityModelResource, NestedFieldSupport {

	@Override
	public Response deleteCatalog(Long id) throws Exception {
		_commerceCatalogService.deleteCommerceCatalog(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteCatalogByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceCatalog == null) {
			throw new NoSuchCatalogException(
				"Unable to find catalog with external reference code " +
					externalReferenceCode);
		}

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Catalog getCatalog(Long id) throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchCommerceCatalog(id);

		if (commerceCatalog == null) {
			throw new NoSuchCatalogException(
				"Unable to find Catalog with ID: " + id);
		}

		return _toCatalog(commerceCatalog);
	}

	@Override
	public Catalog getCatalogByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceCatalog == null) {
			throw new NoSuchCatalogException(
				"Unable to find catalog with external reference code " +
					externalReferenceCode);
		}

		return _toCatalog(commerceCatalog);
	}

	@Override
	public Page<Catalog> getCatalogsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommerceCatalog.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toCatalog(
				_commerceCatalogService.getCommerceCatalog(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Catalog getProductByExternalReferenceCodeCatalog(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _toCatalog(cpDefinition.getCommerceCatalog());
	}

	@NestedField(parentClass = Product.class, value = "catalog")
	@Override
	public Catalog getProductIdCatalog(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _toCatalog(cpDefinition.getCommerceCatalog());
	}

	@Override
	public Response patchCatalog(Long id, Catalog catalog) throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogService.getCommerceCatalog(id);

		_commerceCatalogService.updateCommerceCatalog(
			commerceCatalog.getCommerceCatalogId(), catalog.getName(),
			GetterUtil.get(
				catalog.getCurrencyCode(),
				commerceCatalog.getCommerceCurrencyCode()),
			GetterUtil.get(
				catalog.getDefaultLanguageId(),
				commerceCatalog.getCatalogDefaultLanguageId()));

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchCatalogByExternalReferenceCode(
			String externalReferenceCode, Catalog catalog)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceCatalog == null) {
			throw new NoSuchCatalogException(
				"Unable to find catalog with external reference code " +
					externalReferenceCode);
		}

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Catalog postCatalog(Catalog catalog) throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchByExternalReferenceCode(
				catalog.getExternalReferenceCode(),
				contextCompany.getCompanyId());

		if (commerceCatalog == null) {
			commerceCatalog = _commerceCatalogService.addCommerceCatalog(
				catalog.getExternalReferenceCode(), catalog.getName(),
				catalog.getCurrencyCode(), catalog.getDefaultLanguageId(),
				_serviceContextHelper.getServiceContext());
		}
		else {
			commerceCatalog = _commerceCatalogService.updateCommerceCatalog(
				commerceCatalog.getCommerceCatalogId(), catalog.getName(),
				GetterUtil.get(
					catalog.getCurrencyCode(),
					commerceCatalog.getCommerceCurrencyCode()),
				GetterUtil.get(
					catalog.getDefaultLanguageId(),
					commerceCatalog.getCatalogDefaultLanguageId()));
		}

		return _toCatalog(commerceCatalog);
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceCatalog commerceCatalog) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"DELETE", commerceCatalog.getCommerceCatalogId(),
				"deleteCatalog", commerceCatalog.getUserId(),
				"com.liferay.commerce.product.model.CommerceCatalog",
				commerceCatalog.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", commerceCatalog.getCommerceCatalogId(), "getCatalog",
				commerceCatalog.getUserId(),
				"com.liferay.commerce.product.model.CommerceCatalog",
				commerceCatalog.getGroupId())
		).put(
			"update",
			addAction(
				"UPDATE", commerceCatalog.getCommerceCatalogId(),
				"patchCatalog", commerceCatalog.getUserId(),
				"com.liferay.commerce.product.model.CommerceCatalog",
				commerceCatalog.getGroupId())
		).build();
	}

	private Catalog _toCatalog(CommerceCatalog commerceCatalog)
		throws Exception {

		return _catalogDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceCatalog), _dtoConverterRegistry,
				commerceCatalog.getCommerceCatalogId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private static final EntityModel _entityModel = new CatalogEntityModel();

	@Reference
	private CatalogDTOConverter _catalogDTOConverter;

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}