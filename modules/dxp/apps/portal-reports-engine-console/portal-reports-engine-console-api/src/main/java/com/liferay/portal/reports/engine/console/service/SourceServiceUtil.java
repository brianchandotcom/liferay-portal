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

package com.liferay.portal.reports.engine.console.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Source;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for Source. This utility wraps
 * <code>com.liferay.portal.reports.engine.console.service.impl.SourceServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SourceService
 * @generated
 */
public class SourceServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.reports.engine.console.service.impl.SourceServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Source addSource(
			long groupId, Map<java.util.Locale, String> nameMap,
			String driverClassName, String driverUrl, String driverUserName,
			String driverPassword,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSource(
			groupId, nameMap, driverClassName, driverUrl, driverUserName,
			driverPassword, serviceContext);
	}

	public static Source deleteSource(long sourceId) throws PortalException {
		return getService().deleteSource(sourceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Source getSource(long sourceId) throws PortalException {
		return getService().getSource(sourceId);
	}

	public static List<Source> getSources(
			long groupId, String name, String driverUrl, boolean andSearch,
			int start, int end, OrderByComparator<Source> orderByComparator)
		throws PortalException {

		return getService().getSources(
			groupId, name, driverUrl, andSearch, start, end, orderByComparator);
	}

	public static int getSourcesCount(
		long groupId, String name, String driverUrl, boolean andSearch) {

		return getService().getSourcesCount(
			groupId, name, driverUrl, andSearch);
	}

	public static Source updateSource(
			long sourceId, Map<java.util.Locale, String> nameMap,
			String driverClassName, String driverUrl, String driverUserName,
			String driverPassword,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateSource(
			sourceId, nameMap, driverClassName, driverUrl, driverUserName,
			driverPassword, serviceContext);
	}

	public static SourceService getService() {
		return _service;
	}

	public static void setService(SourceService service) {
		_service = service;
	}

	private static volatile SourceService _service;

}