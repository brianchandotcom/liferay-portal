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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.DefaultFilterImpl;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.product.definitions.web.internal.model.Product;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITIONS,
	service = CommerceDataSetDataProvider.class
)
public class CommerceProductDefinitionDataSetDataProvider
	implements CommerceDataSetDataProvider<Product> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		BaseModelSearchResult<CPDefinition> baseModelSearchResult =
			_getBaseModelSearchResult(httpServletRequest, filter, null, null);

		return baseModelSearchResult.getLength();
	}

	@Override
	public List<Product> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Product> products = new ArrayList<>();

		try {
			Locale locale = _portal.getLocale(httpServletRequest);

			BaseModelSearchResult<CPDefinition> baseModelSearchResult =
				_getBaseModelSearchResult(
					httpServletRequest, filter, pagination, sort);

			for (CPDefinition cpDefinition :
					baseModelSearchResult.getBaseModels()) {

				CommerceCatalog commerceCatalog =
					cpDefinition.getCommerceCatalog();

				String name = cpDefinition.getName(
					LanguageUtil.getLanguageId(locale));

				CPType cpType = _getCPType(cpDefinition.getProductTypeName());

				Date modifiedDate = cpDefinition.getModifiedDate();

				String modifiedDateDescription =
					LanguageUtil.getTimeDescription(
						httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true);

				String statusDisplayStyle = StringPool.BLANK;

				if (cpDefinition.getStatus() ==
						WorkflowConstants.STATUS_APPROVED) {

					statusDisplayStyle = "success";
				}
				else if (cpDefinition.getStatus() ==
							WorkflowConstants.STATUS_DRAFT) {

					statusDisplayStyle = "secondary";
				}
				else if (cpDefinition.getStatus() ==
							WorkflowConstants.STATUS_EXPIRED) {

					statusDisplayStyle = "warning";
				}

				products.add(
					new Product(
						commerceCatalog.getName(),
						commerceCatalog.getCommerceCatalogId(),
						cpDefinition.getCPDefinitionId(),
						new ImageField(
							name, "rounded", "lg",
							cpDefinition.getDefaultImageThumbnailSrc()),
						LanguageUtil.format(
							httpServletRequest, "x-ago",
							modifiedDateDescription, false),
						HtmlUtil.escape(name), _getSku(cpDefinition, locale),
						new LabelField(
							statusDisplayStyle,
							LanguageUtil.get(
								httpServletRequest,
								WorkflowConstants.getStatusLabel(
									cpDefinition.getStatus()))),
						cpType.getLabel(locale)));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return products;
	}

	private BaseModelSearchResult<CPDefinition> _getBaseModelSearchResult(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		DefaultFilterImpl defaultFilterImpl = (DefaultFilterImpl)filter;

		int start = QueryUtil.ALL_POS;
		int end = QueryUtil.ALL_POS;

		if (pagination != null) {
			start = pagination.getStartPosition();
			end = pagination.getEndPosition();
		}

		return _cpDefinitionService.searchCPDefinitions(
			_portal.getCompanyId(httpServletRequest),
			defaultFilterImpl.getKeywords(), StringPool.BLANK, StringPool.BLANK,
			start, end, sort);
	}

	private CPType _getCPType(String name) {
		return _actionHelper.getCPType(name);
	}

	private String _getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.isEmpty()) {
			return StringPool.BLANK;
		}

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductDefinitionDataSetDataProvider.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Portal _portal;

}