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

package com.liferay.sharing.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.sharing.service.SharingEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SharingEntryServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryServiceSoap
 * @generated
 */
public class SharingEntryServiceHttp {

	public static com.liferay.sharing.model.SharingEntry
			addOrUpdateSharingEntry(
				HttpPrincipal httpPrincipal, long toUserId, long classNameId,
				long classPK, long groupId, boolean shareable,
				java.util.Collection
					<com.liferay.sharing.security.permission.SharingEntryAction>
						sharingEntryActions,
				java.util.Date expirationDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SharingEntryServiceUtil.class, "addOrUpdateSharingEntry",
				_addOrUpdateSharingEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, toUserId, classNameId, classPK, groupId, shareable,
				sharingEntryActions, expirationDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.sharing.model.SharingEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.sharing.model.SharingEntry addSharingEntry(
			HttpPrincipal httpPrincipal, long toUserId, long classNameId,
			long classPK, long groupId, boolean shareable,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SharingEntryServiceUtil.class, "addSharingEntry",
				_addSharingEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, toUserId, classNameId, classPK, groupId, shareable,
				sharingEntryActions, expirationDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.sharing.model.SharingEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.sharing.model.SharingEntry deleteSharingEntry(
			HttpPrincipal httpPrincipal, long sharingEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SharingEntryServiceUtil.class, "deleteSharingEntry",
				_deleteSharingEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sharingEntryId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.sharing.model.SharingEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.sharing.model.SharingEntry updateSharingEntry(
			HttpPrincipal httpPrincipal, long sharingEntryId,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			boolean shareable, java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SharingEntryServiceUtil.class, "updateSharingEntry",
				_updateSharingEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sharingEntryId, sharingEntryActions, shareable,
				expirationDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.sharing.model.SharingEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SharingEntryServiceHttp.class);

	private static final Class<?>[] _addOrUpdateSharingEntryParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, long.class, boolean.class,
			java.util.Collection.class, java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addSharingEntryParameterTypes1 =
		new Class[] {
			long.class, long.class, long.class, long.class, boolean.class,
			java.util.Collection.class, java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSharingEntryParameterTypes2 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateSharingEntryParameterTypes3 =
		new Class[] {
			long.class, java.util.Collection.class, boolean.class,
			java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}