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

package com.liferay.portal.search.tuning.blueprints.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for Element. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ElementServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ElementService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.search.tuning.blueprints.service.impl.ElementServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the element remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ElementServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.portal.search.tuning.elements.service.ElementServiceUtil</code> to access the element remote service.
	 */
	public Element addCompanyElement(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, int type, ServiceContext serviceContext)
		throws PortalException;

	public Element addGroupElement(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, int type, ServiceContext serviceContext)
		throws PortalException;

	public Element deleteElement(long elementId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Element getElement(long elementId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Element> getGroupElements(
		long companyId, int type, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Element> getGroupElements(
		long companyId, int status, int type, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Element> getGroupElements(
		long companyId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Element> getGroupElements(
		long companyId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupElementsCount(long companyId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupElementsCount(long companyId, int status, int type);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public Element updateElement(
			long elementId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configuration,
			ServiceContext serviceContext)
		throws PortalException;

}