/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service;

import com.liferay.commerce.tax.model.CommerceTaxCategoryMapping;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceTaxCategoryMapping. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMappingServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceTaxCategoryMappingService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.tax.service.impl.CommerceTaxCategoryMappingServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce tax category mapping remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceTaxCategoryMappingServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceTaxCategoryMapping addCommerceTaxCategoryMapping(
			String externalReferenceCode, long groupId,
			long commerceTaxMethodId, long cpTaxCategoryId)
		throws PortalException;

	public void deleteCommerceTaxCategoryMapping(
			long commerceTaxCategoryMappingId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTaxCategoryMapping fetchCommerceTaxCategoryMapping(
			long commerceTaxCategoryMappingId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTaxCategoryMappingCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxCategoryMapping> getCommerceTaxCategoryMappings(
			long groupId, long commerceTaxMethodId, int start, int end,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceTaxCategoryMapping updateExternalReferenceCode(
			long commerceTaxCategoryMappingId, String externalReferenceCode)
		throws PortalException;

}