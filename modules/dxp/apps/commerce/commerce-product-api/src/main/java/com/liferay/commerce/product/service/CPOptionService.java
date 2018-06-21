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

import com.liferay.commerce.product.model.CPOption;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
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
 * Provides the remote service interface for CPOption. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionServiceUtil
 * @see com.liferay.commerce.product.service.base.CPOptionServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPOption"}, service = CPOptionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPOptionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionServiceUtil} to access the cp option remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPOption addCPOption(Map<Locale, String> nameMap,
		Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
		boolean facetable, boolean required, boolean skuContributor,
		String key, ServiceContext serviceContext) throws PortalException;

	public void deleteCPOption(long cpOptionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption fetchCPOption(long cpOptionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption fetchCPOption(long groupId, String key)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption getCPOption(long cpOptionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOption> getCPOptions(long groupId, int start, int end,
		OrderByComparator<CPOption> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionsCount(long groupId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPOption> searchCPOptions(long companyId,
		long groupId, String keywords, int start, int end, Sort sort)
		throws PortalException;

	public CPOption updateCPOption(long cpOptionId,
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key, ServiceContext serviceContext)
		throws PortalException;
}