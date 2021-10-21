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

import com.liferay.commerce.product.exception.NoSuchCPOptionException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Option;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.OptionDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.odata.entity.v1_0.OptionEntityModel;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionValueResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
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
	properties = "OSGI-INF/liferay/rest/v1_0/option.properties",
	scope = ServiceScope.PROTOTYPE, service = OptionResource.class
)
@CTAware
public class OptionResourceImpl
	extends BaseOptionResourceImpl implements EntityModelResource {

	@Override
	public Response deleteOption(Long id) throws Exception {
		CPOption cpOption = _cpOptionService.getCPOption(id);

		_cpOptionService.deleteCPOption(cpOption.getCPOptionId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteOptionByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CPOption cpOption = _cpOptionService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpOption == null) {
			throw new NoSuchCPOptionException(
				"Unable to find option with external reference code " +
					externalReferenceCode);
		}

		_cpOptionService.deleteCPOption(cpOption.getCPOptionId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Option getOption(Long id) throws Exception {
		return _toOption(GetterUtil.getLong(id));
	}

	@Override
	public Option getOptionByExternalReferenceCode(String externalReferenceCode)
		throws Exception {

		CPOption cpOption = _cpOptionService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		return _toOption(cpOption.getCPOptionId());
	}

	@Override
	public Page<Option> getOptionsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CPOption.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toOption(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Response patchOption(Long id, Option option) throws Exception {
		_updateOption(_cpOptionService.getCPOption(id), option);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchOptionByExternalReferenceCode(
			String externalReferenceCode, Option option)
		throws Exception {

		CPOption cpOption = _cpOptionService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpOption == null) {
			throw new NoSuchCPOptionException(
				"Unable to find option with external reference code " +
					externalReferenceCode);
		}

		_updateOption(cpOption, option);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Option postOption(Option option) throws Exception {
		return _addOrUpdateOption(option);
	}

	private Option _addOrUpdateOption(Option option) throws Exception {
		Option.FieldType fieldType = option.getFieldType();

		CPOption cpOption = _cpOptionService.addOrUpdateCPOption(
			option.getExternalReferenceCode(),
			LanguageUtils.getLocalizedMap(option.getName()),
			LanguageUtils.getLocalizedMap(option.getDescription()),
			fieldType.getValue(), GetterUtil.get(option.getFacetable(), false),
			GetterUtil.get(option.getRequired(), false),
			GetterUtil.get(option.getSkuContributor(), false), option.getKey(),
			_serviceContextHelper.getServiceContext());

		_addOrUpdateOptionValues(cpOption, option.getOptionValues());

		return _toOption(cpOption.getCPOptionId());
	}

	private void _addOrUpdateOptionValues(
			CPOption cpOption, OptionValue[] optionValues)
		throws Exception {

		if (ArrayUtil.isEmpty(optionValues)) {
			return;
		}

		_optionValueResource.setContextAcceptLanguage(contextAcceptLanguage);

		for (OptionValue optionValue : optionValues) {
			_optionValueResource.postOptionIdOptionValue(
				cpOption.getCPOptionId(), optionValue);
		}
	}

	private Map<String, Map<String, String>> _getActions(long cpOptionId) {
		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				ActionKeys.DELETE, cpOptionId, "deleteOption",
				_cpOptionModelResourcePermission)
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, cpOptionId, "getOption",
				_cpOptionModelResourcePermission)
		).put(
			"update",
			addAction(
				ActionKeys.UPDATE, cpOptionId, "patchOption",
				_cpOptionModelResourcePermission)
		).build();
	}

	private Option _toOption(Long cpOptionId) throws Exception {
		return _optionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpOptionId), _dtoConverterRegistry, cpOptionId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private Option _updateOption(CPOption cpOption, Option option)
		throws Exception {

		Option.FieldType fieldType = option.getFieldType();

		cpOption = _cpOptionService.updateCPOption(
			cpOption.getCPOptionId(),
			LanguageUtils.getLocalizedMap(option.getName()),
			LanguageUtils.getLocalizedMap(option.getDescription()),
			fieldType.getValue(),
			GetterUtil.get(option.getFacetable(), cpOption.isFacetable()),
			GetterUtil.get(option.getRequired(), cpOption.isRequired()),
			GetterUtil.get(
				option.getSkuContributor(), cpOption.isSkuContributor()),
			option.getKey(), _serviceContextHelper.getServiceContext());

		return _toOption(cpOption.getCPOptionId());
	}

	private static final EntityModel _entityModel = new OptionEntityModel();

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOption)"
	)
	private ModelResourcePermission<CPOption> _cpOptionModelResourcePermission;

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OptionDTOConverter _optionDTOConverter;

	@Reference
	private OptionValueResource _optionValueResource;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}