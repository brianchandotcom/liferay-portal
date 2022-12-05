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

package com.liferay.layout.utility.page.service.http;

import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>LayoutUtilityPageEntryServiceUtil</code> service
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
 * @generated
 */
public class LayoutUtilityPageEntryServiceHttp {

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			addLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long previewFileEntryId, String name, String type,
				long masterLayoutPlid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"addLayoutUtilityPageEntry",
				_addLayoutUtilityPageEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, previewFileEntryId,
				name, type, masterLayoutPlid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			copyLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, long groupId,
				long layoutUtilityPageEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"copyLayoutUtilityPageEntry",
				_copyLayoutUtilityPageEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, layoutUtilityPageEntryId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			deleteLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, long layoutUtilityPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"deleteLayoutUtilityPageEntry",
				_deleteLayoutUtilityPageEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutUtilityPageEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
		fetchLayoutUtilityPageEntry(
			HttpPrincipal httpPrincipal, long layoutUtilityPageEntryId) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"fetchLayoutUtilityPageEntry",
				_fetchLayoutUtilityPageEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutUtilityPageEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			getDefaultLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"getDefaultLayoutUtilityPageEntry",
				_getDefaultLayoutUtilityPageEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.utility.page.model.LayoutUtilityPageEntry>
			getLayoutUtilityPageEntries(
				HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"getLayoutUtilityPageEntries",
				_getLayoutUtilityPageEntriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.layout.utility.page.model.LayoutUtilityPageEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.utility.page.model.LayoutUtilityPageEntry>
			getLayoutUtilityPageEntries(
				HttpPrincipal httpPrincipal, long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.utility.page.model.
						LayoutUtilityPageEntry> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"getLayoutUtilityPageEntries",
				_getLayoutUtilityPageEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.layout.utility.page.model.LayoutUtilityPageEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.utility.page.model.LayoutUtilityPageEntry>
			getLayoutUtilityPageEntries(
				HttpPrincipal httpPrincipal, long groupId, String type,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.utility.page.model.
						LayoutUtilityPageEntry> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"getLayoutUtilityPageEntries",
				_getLayoutUtilityPageEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, type, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.layout.utility.page.model.LayoutUtilityPageEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getLayoutUtilityPageEntriesCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"getLayoutUtilityPageEntriesCount",
				_getLayoutUtilityPageEntriesCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			setDefaultLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, long layoutUtilityPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"setDefaultLayoutUtilityPageEntry",
				_setDefaultLayoutUtilityPageEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutUtilityPageEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			updateLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, long layoutUtilityPageEntryId,
				long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"updateLayoutUtilityPageEntry",
				_updateLayoutUtilityPageEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutUtilityPageEntryId, previewFileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.utility.page.model.LayoutUtilityPageEntry
			updateLayoutUtilityPageEntry(
				HttpPrincipal httpPrincipal, long layoutUtilityPageEntryId,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutUtilityPageEntryServiceUtil.class,
				"updateLayoutUtilityPageEntry",
				_updateLayoutUtilityPageEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutUtilityPageEntryId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.layout.utility.page.model.
				LayoutUtilityPageEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutUtilityPageEntryServiceHttp.class);

	private static final Class<?>[] _addLayoutUtilityPageEntryParameterTypes0 =
		new Class[] {
			String.class, long.class, long.class, String.class, String.class,
			long.class
		};
	private static final Class<?>[] _copyLayoutUtilityPageEntryParameterTypes1 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteLayoutUtilityPageEntryParameterTypes2 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchLayoutUtilityPageEntryParameterTypes3 = new Class[] {long.class};
	private static final Class<?>[]
		_getDefaultLayoutUtilityPageEntryParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getLayoutUtilityPageEntriesParameterTypes5 = new Class[] {long.class};
	private static final Class<?>[]
		_getLayoutUtilityPageEntriesParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getLayoutUtilityPageEntriesParameterTypes7 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getLayoutUtilityPageEntriesCountParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_setDefaultLayoutUtilityPageEntryParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateLayoutUtilityPageEntryParameterTypes10 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_updateLayoutUtilityPageEntryParameterTypes11 = new Class[] {
			long.class, String.class
		};

}