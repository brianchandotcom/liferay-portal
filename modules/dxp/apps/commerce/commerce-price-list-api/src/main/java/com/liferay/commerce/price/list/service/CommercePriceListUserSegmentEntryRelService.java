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

package com.liferay.commerce.price.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;

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

/**
 * Provides the remote service interface for CommercePriceListUserSegmentEntryRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelServiceUtil
 * @see com.liferay.commerce.price.list.service.base.CommercePriceListUserSegmentEntryRelServiceBaseImpl
 * @see com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommercePriceListUserSegmentEntryRel"}, service = CommercePriceListUserSegmentEntryRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePriceListUserSegmentEntryRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListUserSegmentEntryRelServiceUtil} to access the commerce price list user segment entry rel remote service. Add custom service methods to {@link com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId, int order,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId, int order,
		ServiceContext serviceContext) throws PortalException;
}