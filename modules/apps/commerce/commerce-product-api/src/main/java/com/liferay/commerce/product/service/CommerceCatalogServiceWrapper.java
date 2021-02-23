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

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCatalogService}.
 *
 * @author Marco Leo
 * @see CommerceCatalogService
 * @generated
 */
public class CommerceCatalogServiceWrapper
	implements CommerceCatalogService, ServiceWrapper<CommerceCatalogService> {

	public CommerceCatalogServiceWrapper(
		CommerceCatalogService commerceCatalogService) {

		_commerceCatalogService = commerceCatalogService;
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			addCommerceCatalog(
				String externalReferenceCode, String name,
				String commerceCurrencyCode, String catalogDefaultLanguageId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.addCommerceCatalog(
			externalReferenceCode, name, commerceCurrencyCode,
			catalogDefaultLanguageId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			deleteCommerceCatalog(long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.deleteCommerceCatalog(commerceCatalogId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			fetchCommerceCatalog(long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.fetchCommerceCatalog(commerceCatalogId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			fetchCommerceCatalogByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.fetchCommerceCatalogByGroupId(groupId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			getCommerceCatalog(long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.getCommerceCatalog(commerceCatalogId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceCatalog>
		getCommerceCatalogs(long companyId, int start, int end) {

		return _commerceCatalogService.getCommerceCatalogs(
			companyId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceCatalogService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceCatalog>
			searchCommerceCatalogs(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.searchCommerceCatalogs(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceCatalogsCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.searchCommerceCatalogsCount(
			companyId, keywords);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			updateCommerceCatalog(
				long commerceCatalogId, String name,
				String commerceCurrencyCode, String catalogDefaultLanguageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.updateCommerceCatalog(
			commerceCatalogId, name, commerceCurrencyCode,
			catalogDefaultLanguageId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceCatalog
			updateCommerceCatalogExternalReferenceCode(
				String externalReferenceCode, long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCatalogService.
			updateCommerceCatalogExternalReferenceCode(
				externalReferenceCode, commerceCatalogId);
	}

	@Override
	public CommerceCatalogService getWrappedService() {
		return _commerceCatalogService;
	}

	@Override
	public void setWrappedService(
		CommerceCatalogService commerceCatalogService) {

		_commerceCatalogService = commerceCatalogService;
	}

	private CommerceCatalogService _commerceCatalogService;

}