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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceCatalog. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CommerceCatalogServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceCatalogService
 * @generated
 */
public class CommerceCatalogServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CommerceCatalogServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceCatalog
			addCommerceCatalog(
				String name, String commerceCurrencyCode,
				String catalogDefaultLanguageId, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceCatalog(
			name, commerceCurrencyCode, catalogDefaultLanguageId,
			externalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			deleteCommerceCatalog(long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceCatalog(commerceCatalogId);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			fetchCommerceCatalog(long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceCatalog(commerceCatalogId);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			fetchCommerceCatalogByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceCatalogByGroupId(groupId);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			getCommerceCatalog(long commerceCatalogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCatalog(commerceCatalogId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceCatalog>
			getCommerceCatalogs(long companyId, int start, int end) {

		return getService().getCommerceCatalogs(companyId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceCatalog>
				searchCommerceCatalogs(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceCatalogs(
			companyId, keywords, start, end, sort);
	}

	public static int searchCommerceCatalogsCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceCatalogsCount(companyId, keywords);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			updateCommerceCatalog(
				long commerceCatalogId, String name,
				String commerceCurrencyCode, String catalogDefaultLanguageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceCatalog(
			commerceCatalogId, name, commerceCurrencyCode,
			catalogDefaultLanguageId);
	}

	public static com.liferay.commerce.product.model.CommerceCatalog
			updateCommerceCatalogExternalReferenceCode(
				long commerceCatalogId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceCatalogExternalReferenceCode(
			commerceCatalogId, externalReferenceCode);
	}

	public static CommerceCatalogService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceCatalogService, CommerceCatalogService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCatalogService.class);

		ServiceTracker<CommerceCatalogService, CommerceCatalogService>
			serviceTracker =
				new ServiceTracker
					<CommerceCatalogService, CommerceCatalogService>(
						bundle.getBundleContext(), CommerceCatalogService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}