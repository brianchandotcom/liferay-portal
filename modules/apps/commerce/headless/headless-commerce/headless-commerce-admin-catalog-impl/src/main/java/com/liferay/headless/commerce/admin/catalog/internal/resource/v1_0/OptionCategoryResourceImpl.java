/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.commerce.admin.catalog.internal.odata.entity.v1_0.OptionCategoryEntityModel;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionCategoryResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
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
 * @author Igor Beslic
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/option-category.properties",
	scope = ServiceScope.PROTOTYPE, service = OptionCategoryResource.class
)
@CTAware
public class OptionCategoryResourceImpl extends BaseOptionCategoryResourceImpl {

	@Override
	public Response deleteOptionCategory(Long id) throws Exception {
		_cpOptionCategoryService.deleteCPOptionCategory(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<OptionCategory> getOptionCategoriesPage(
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CPOptionCategory.class.getName(), StringPool.BLANK, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toOptionCategory(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public OptionCategory getOptionCategory(Long id) throws Exception {
		return _toOptionCategory(GetterUtil.getLong(id));
	}

	@Override
	public Response patchOptionCategory(Long id, OptionCategory optionCategory)
		throws Exception {

		_updateOptionCategory(id, optionCategory);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public OptionCategory postOptionCategory(OptionCategory optionCategory)
		throws Exception {

		CPOptionCategory cpOptionCategory = null;

		if (optionCategory.getId() != null) {
			cpOptionCategory = _cpOptionCategoryService.fetchCPOptionCategory(
				optionCategory.getId());
		}

		if (cpOptionCategory == null) {
			cpOptionCategory = _addOptionCategory(optionCategory);
		}
		else {
			cpOptionCategory = _updateOptionCategory(
				optionCategory.getId(), optionCategory);
		}

		return _toOptionCategory(cpOptionCategory.getCPOptionCategoryId());
	}

	private CPOptionCategory _addOptionCategory(OptionCategory optionCategory)
		throws Exception {

		return _cpOptionCategoryService.addCPOptionCategory(
			LanguageUtils.getLocalizedMap(optionCategory.getTitle()),
			LanguageUtils.getLocalizedMap(optionCategory.getDescription()),
			GetterUtil.get(optionCategory.getPriority(), 0D),
			optionCategory.getKey(),
			_serviceContextHelper.getServiceContext(contextUser));
	}

	private OptionCategory _toOptionCategory(Long cpOptionCategoryId)
		throws Exception {

		return _optionCategoryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpOptionCategoryId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private CPOptionCategory _updateOptionCategory(
			Long id, OptionCategory optionCategory)
		throws Exception {

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryService.getCPOptionCategory(id);

		return _cpOptionCategoryService.updateCPOptionCategory(
			cpOptionCategory.getCPOptionCategoryId(),
			LanguageUtils.getLocalizedMap(optionCategory.getTitle()),
			LanguageUtils.getLocalizedMap(optionCategory.getDescription()),
			GetterUtil.get(
				optionCategory.getPriority(), cpOptionCategory.getPriority()),
			optionCategory.getKey());
	}

	private static final EntityModel _entityModel =
		new OptionCategoryEntityModel();

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

	@Reference(target = DTOConverterConstants.OPTION_CATEGORY_DTO_CONVERTER)
	private DTOConverter<CPOptionCategory, OptionCategory>
		_optionCategoryDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}