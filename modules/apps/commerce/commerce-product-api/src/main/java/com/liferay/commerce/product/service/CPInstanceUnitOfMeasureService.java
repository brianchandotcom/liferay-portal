/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CPInstanceUnitOfMeasure. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CPInstanceUnitOfMeasureService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPInstanceUnitOfMeasureServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the cp instance unit of measure remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CPInstanceUnitOfMeasureServiceUtil} if injection and service tracking are not available.
	 */
	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException;

	public CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException;

	public CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchPrimaryCPInstanceUnitOfMeasure(
			long cpInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstanceUnitOfMeasure> getActiveCPInstanceUnitOfMeasures(
			long cpInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getActiveCPInstanceUnitOfMeasuresCount(long cpInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
			long cpInstanceId, int start, int end,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstanceUnitOfMeasuresCount(long cpInstanceId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException;

}