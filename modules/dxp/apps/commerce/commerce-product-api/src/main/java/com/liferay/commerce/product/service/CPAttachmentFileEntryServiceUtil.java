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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPAttachmentFileEntry. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryService
 * @see com.liferay.commerce.product.service.base.CPAttachmentFileEntryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPAttachmentFileEntry addCPAttachmentFileEntry(
		long classNameId, long classPK, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, String> titleMap, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPAttachmentFileEntry(classNameId, classPK, fileEntryId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, titleMap, json, priority, type, serviceContext);
	}

	public static void deleteCPAttachmentFileEntry(long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry fetchCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPAttachmentFileEntries(classNameId, classPK, type,
			status, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPAttachmentFileEntries(classNameId, classPK, type,
			status, start, end, orderByComparator);
	}

	public static int getCPAttachmentFileEntriesCount(long classNameId,
		long classPK, int type, int status) {
		return getService()
				   .getCPAttachmentFileEntriesCount(classNameId, classPK, type,
			status);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry getCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry updateCPAttachmentFileEntry(
		long cpAttachmentFileEntryId, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, String> titleMap, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPAttachmentFileEntry(cpAttachmentFileEntryId,
			fileEntryId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, titleMap, json, priority, type,
			serviceContext);
	}

	public static CPAttachmentFileEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPAttachmentFileEntryService, CPAttachmentFileEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPAttachmentFileEntryService.class);

		ServiceTracker<CPAttachmentFileEntryService, CPAttachmentFileEntryService> serviceTracker =
			new ServiceTracker<CPAttachmentFileEntryService, CPAttachmentFileEntryService>(bundle.getBundleContext(),
				CPAttachmentFileEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}