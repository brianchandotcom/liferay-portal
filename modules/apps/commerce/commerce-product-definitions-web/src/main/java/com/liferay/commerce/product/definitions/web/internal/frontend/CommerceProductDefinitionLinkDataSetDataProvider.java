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
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.product.definitions.web.internal.model.ProductLink;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_LINKS,
	service = CommerceDataSetDataProvider.class
)
public class CommerceProductDefinitionLinkDataSetDataProvider
	implements CommerceDataSetDataProvider<ProductLink> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _cpDefinitionLinkService.getCPDefinitionLinksCount(
			cpDefinitionId);
	}

	@Override
	public List<ProductLink> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ProductLink> productLinks = new ArrayList<>();

		try {
			long cpDefinitionId = ParamUtil.getLong(
				httpServletRequest, "cpDefinitionId");

			List<CPDefinitionLink> cpDefinitionLinks =
				_cpDefinitionLinkService.getCPDefinitionLinks(
					cpDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition());

			for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
				CProduct cProduct = cpDefinitionLink.getCProduct();

				CPDefinition cpDefinition =
					_cpDefinitionLocalService.getCPDefinition(
						cProduct.getPublishedCPDefinitionId());

				String name = cpDefinition.getName(
					LanguageUtil.getLanguageId(
						_portal.getLocale(httpServletRequest)));

				Date createDate = cpDefinitionLink.getCreateDate();

				String createDateDescription = LanguageUtil.getTimeDescription(
					httpServletRequest,
					System.currentTimeMillis() - createDate.getTime(), true);

				productLinks.add(
					new ProductLink(
						cpDefinitionLink.getCPDefinitionLinkId(),
						new ImageField(
							name, "rounded", "lg",
							cpDefinition.getDefaultImageThumbnailSrc()),
						HtmlUtil.escape(name),
						LanguageUtil.get(
							httpServletRequest, cpDefinitionLink.getType()),
						cpDefinitionLink.getPriority(),
						LanguageUtil.format(
							httpServletRequest, "x-ago", createDateDescription,
							false)));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return productLinks;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductDefinitionLinkDataSetDataProvider.class);

	@Reference
	private CPDefinitionLinkService _cpDefinitionLinkService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Portal _portal;

}