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

import com.liferay.commerce.price.list.model.CommercePriceEntry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

/**
 * Provides the remote service interface for CommercePriceEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryServiceUtil
 * @see com.liferay.commerce.price.list.service.base.CommercePriceEntryServiceBaseImpl
 * @see com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommercePriceEntry"}, service = CommercePriceEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePriceEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceEntryServiceUtil} to access the commerce price entry remote service. Add custom service methods to {@link com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommercePriceEntry addCommercePriceEntry(long cpInstanceId,
		long commercePriceListId, BigDecimal price, BigDecimal promoPrice,
		ServiceContext serviceContext) throws PortalException;

	public CommercePriceEntry addCommercePriceEntry(long cpInstanceId,
		long commercePriceListId, String externalReferenceCode,
		BigDecimal price, BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchByExternalReferenceCode(
		String externalReferenceCode) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> fetchCommercePriceEntries(long groupId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(long commercePriceEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCount(long commercePriceListId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCountByGroupId(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
		long companyId, long groupId, long commercePriceListId,
		String keywords, int start, int end, Sort sort)
		throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
		ServiceContext serviceContext) throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, String externalReferenceCode,
		BigDecimal price, BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException;
}