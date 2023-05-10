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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.provider;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.commerce.product.definitions.web.internal.model.ProductLink;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
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
	property = "fds.data.provider.key=" + CommerceProductFDSNames.PRODUCT_LINKS,
	service = FDSDataProvider.class
)
public class CommerceProductDefinitionLinkFDSDataProvider
	implements FDSDataProvider<ProductLink> {

	@Override
	public List<ProductLink> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<ProductLink> productLinks = new ArrayList<>();

		try {
			long cpDefinitionId = ParamUtil.getLong(
				httpServletRequest, "cpDefinitionId");

			List<CPDefinitionLink> cpDefinitionLinks =
				_cpDefinitionLinkService.getCPDefinitionLinks(
					cpDefinitionId, fdsPagination.getStartPosition(),
					fdsPagination.getEndPosition());

			for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
				CProduct cProduct = cpDefinitionLink.getCProduct();

				CPDefinition cpDefinition =
					_cpDefinitionLocalService.getCPDefinition(
						cProduct.getPublishedCPDefinitionId());

				String name = cpDefinition.getName(
					_language.getLanguageId(
						_portal.getLocale(httpServletRequest)));

				Date createDate = cpDefinitionLink.getCreateDate();

				String createDateDescription = _language.getTimeDescription(
					httpServletRequest,
					System.currentTimeMillis() - createDate.getTime(), true);

				productLinks.add(
					new ProductLink(
						cpDefinitionLink.getCPDefinitionLinkId(),
						new ImageField(
							name, "rounded", "lg",
							cpDefinition.getDefaultImageThumbnailSrc(
								AccountConstants.ACCOUNT_ENTRY_ID_ADMIN)),
						HtmlUtil.escape(name),
						_language.get(
							httpServletRequest, cpDefinitionLink.getType()),
						cpDefinitionLink.getPriority(),
						_language.format(
							httpServletRequest, "x-ago", createDateDescription,
							false)));
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return productLinks;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _cpDefinitionLinkService.getCPDefinitionLinksCount(
			cpDefinitionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductDefinitionLinkFDSDataProvider.class);

	@Reference
	private CPDefinitionLinkService _cpDefinitionLinkService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}