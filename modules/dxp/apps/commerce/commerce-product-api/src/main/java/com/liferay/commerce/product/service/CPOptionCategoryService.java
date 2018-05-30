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

import com.liferay.commerce.product.model.CPOptionCategory;

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
 * Provides the remote service interface for CPOptionCategory. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionCategoryServiceUtil
 * @see com.liferay.commerce.product.service.base.CPOptionCategoryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPOptionCategory"}, service = CPOptionCategoryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPOptionCategoryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionCategoryServiceUtil} to access the cp option category remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPOptionCategory addCPOptionCategory(Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap, double priority, String key,
		ServiceContext serviceContext) throws PortalException;

	public CPOptionCategory deleteCPOptionCategory(
		CPOptionCategory cpOptionCategory) throws PortalException;

	public CPOptionCategory deleteCPOptionCategory(long cpOptionCategoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionCategory fetchCPOptionCategory(long cpOptionCategoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionCategory> getCPOptionCategories(long groupId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionCategory> getCPOptionCategories(long groupId,
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionCategoriesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionCategory getCPOptionCategory(long cpOptionCategoryId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPOptionCategory updateCPOptionCategory(long cpOptionCategoryId,
		Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
		double priority, String key, ServiceContext serviceContext)
		throws PortalException;
}