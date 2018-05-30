/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPMeasurementUnit;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CPMeasurementUnit. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitServiceUtil
 * @see com.liferay.commerce.product.service.base.CPMeasurementUnitServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPMeasurementUnit"}, service = CPMeasurementUnitService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPMeasurementUnitService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPMeasurementUnitServiceUtil} to access the cp measurement unit remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPMeasurementUnit addCPMeasurementUnit(Map<Locale, String> nameMap,
		String key, double rate, boolean primary, double priority, int type,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(long groupId,
		int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit getCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnits(long groupId,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPMeasurementUnitsCount(long groupId, int type);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPMeasurementUnit updateCPMeasurementUnit(long cpMeasurementUnitId,
		Map<Locale, String> nameMap, String key, double rate, boolean primary,
		double priority, int type, ServiceContext serviceContext)
		throws PortalException;
}