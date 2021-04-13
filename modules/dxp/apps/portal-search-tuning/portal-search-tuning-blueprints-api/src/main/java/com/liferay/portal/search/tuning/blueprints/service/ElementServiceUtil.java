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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for Element. This utility wraps
 * <code>com.liferay.portal.search.tuning.blueprints.service.impl.ElementServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ElementService
 * @generated
 */
public class ElementServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.search.tuning.blueprints.service.impl.ElementServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.portal.search.tuning.elements.service.ElementServiceUtil</code> to access the element remote service.
	 */
	public static Element addCompanyElement(
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCompanyElement(
			titleMap, descriptionMap, configuration, type, serviceContext);
	}

	public static Element addGroupElement(
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addGroupElement(
			titleMap, descriptionMap, configuration, type, serviceContext);
	}

	public static Element deleteElement(long elementId) throws PortalException {
		return getService().deleteElement(elementId);
	}

	public static Element getElement(long elementId) throws PortalException {
		return getService().getElement(elementId);
	}

	public static List<Element> getGroupElements(
		long companyId, int type, int start, int end) {

		return getService().getGroupElements(companyId, type, start, end);
	}

	public static List<Element> getGroupElements(
		long companyId, int status, int type, int start, int end) {

		return getService().getGroupElements(
			companyId, status, type, start, end);
	}

	public static List<Element> getGroupElements(
		long companyId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getService().getGroupElements(
			companyId, status, type, start, end, orderByComparator);
	}

	public static List<Element> getGroupElements(
		long companyId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getService().getGroupElements(
			companyId, type, start, end, orderByComparator);
	}

	public static int getGroupElementsCount(long companyId, int type) {
		return getService().getGroupElementsCount(companyId, type);
	}

	public static int getGroupElementsCount(
		long companyId, int status, int type) {

		return getService().getGroupElementsCount(companyId, status, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Element updateElement(
			long elementId, Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateElement(
			elementId, titleMap, descriptionMap, configuration, serviceContext);
	}

	public static ElementService getService() {
		return _service;
	}

	private static volatile ElementService _service;

}