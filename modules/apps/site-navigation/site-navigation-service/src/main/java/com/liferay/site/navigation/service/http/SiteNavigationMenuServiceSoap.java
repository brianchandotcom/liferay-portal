/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.navigation.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SiteNavigationMenuServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.site.navigation.model.SiteNavigationMenuSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.site.navigation.model.SiteNavigationMenu</code>, that is translated to a
 * <code>com.liferay.site.navigation.model.SiteNavigationMenuSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SiteNavigationMenuServiceSoap {

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			addSiteNavigationMenu(
				long groupId, String name, int type, boolean auto,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.addSiteNavigationMenu(
					groupId, name, type, auto, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			addSiteNavigationMenu(
				long groupId, String name, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.addSiteNavigationMenu(
					groupId, name, type, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			addSiteNavigationMenu(
				long groupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.addSiteNavigationMenu(
					groupId, name, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			deleteSiteNavigationMenu(long siteNavigationMenuId)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.deleteSiteNavigationMenu(
					siteNavigationMenuId);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			fetchSiteNavigationMenu(long siteNavigationMenuId)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.fetchSiteNavigationMenu(
					siteNavigationMenuId);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap[]
			getSiteNavigationMenus(long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
				returnValue =
					SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
						groupId);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap[]
			getSiteNavigationMenus(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
				returnValue =
					SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
						groupId, start, end, orderByComparator);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap[]
			getSiteNavigationMenus(
				long groupId, String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
				returnValue =
					SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
						groupId, keywords, start, end, orderByComparator);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap[]
			getSiteNavigationMenus(
				long[] groupIds, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
				returnValue =
					SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
						groupIds, start, end, orderByComparator);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap[]
			getSiteNavigationMenus(
				long[] groupIds, String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
				returnValue =
					SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
						groupIds, keywords, start, end, orderByComparator);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSiteNavigationMenusCount(long groupId)
		throws RemoteException {

		try {
			int returnValue =
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSiteNavigationMenusCount(long groupId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					groupId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSiteNavigationMenusCount(long[] groupIds)
		throws RemoteException {

		try {
			int returnValue =
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					groupIds);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSiteNavigationMenusCount(
			long[] groupIds, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					groupIds, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			updateSiteNavigationMenu(
				long siteNavigationMenuId, int type, boolean auto,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.updateSiteNavigationMenu(
					siteNavigationMenuId, type, auto, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuSoap
			updateSiteNavigationMenu(
				long siteNavigationMenuId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenu returnValue =
				SiteNavigationMenuServiceUtil.updateSiteNavigationMenu(
					siteNavigationMenuId, name, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuServiceSoap.class);

}