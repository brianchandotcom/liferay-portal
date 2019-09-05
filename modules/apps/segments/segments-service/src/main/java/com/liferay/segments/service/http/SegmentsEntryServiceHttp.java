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

package com.liferay.segments.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.segments.service.SegmentsEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SegmentsEntryServiceUtil</code> service
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
 * @author Eduardo Garcia
 * @see SegmentsEntryServiceSoap
 * @generated
 */
public class SegmentsEntryServiceHttp {

	public static com.liferay.segments.model.SegmentsEntry addSegmentsEntry(
			HttpPrincipal httpPrincipal, String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria, String source, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "addSegmentsEntry",
				_addSegmentsEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsEntryKey, nameMap, descriptionMap, active,
				criteria, source, type, serviceContext);

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

			return (com.liferay.segments.model.SegmentsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsEntry deleteSegmentsEntry(
			HttpPrincipal httpPrincipal, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "deleteSegmentsEntry",
				_deleteSegmentsEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsEntryId);

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

			return (com.liferay.segments.model.SegmentsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntries(
			HttpPrincipal httpPrincipal, long groupId,
			boolean includeAncestorSegmentsEntries) {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "getSegmentsEntries",
				_getSegmentsEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, includeAncestorSegmentsEntries);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.segments.model.SegmentsEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntries(
			HttpPrincipal httpPrincipal, long groupId,
			boolean includeAncestorSegmentsEntries, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntry> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "getSegmentsEntries",
				_getSegmentsEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, includeAncestorSegmentsEntries, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.segments.model.SegmentsEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSegmentsEntriesCount(
		HttpPrincipal httpPrincipal, long groupId,
		boolean includeAncestorSegmentsEntries) {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "getSegmentsEntriesCount",
				_getSegmentsEntriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, includeAncestorSegmentsEntries);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsEntry getSegmentsEntry(
			HttpPrincipal httpPrincipal, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "getSegmentsEntry",
				_getSegmentsEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsEntryId);

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

			return (com.liferay.segments.model.SegmentsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				String keywords, boolean includeAncestorSegmentsEntries,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "searchSegmentsEntries",
				_searchSegmentsEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords,
				includeAncestorSegmentsEntries, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult
				<com.liferay.segments.model.SegmentsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
			HttpPrincipal httpPrincipal, long segmentsEntryId,
			String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsEntryServiceUtil.class, "updateSegmentsEntry",
				_updateSegmentsEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsEntryId, segmentsEntryKey, nameMap,
				descriptionMap, active, criteria, serviceContext);

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

			return (com.liferay.segments.model.SegmentsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsEntryServiceHttp.class);

	private static final Class<?>[] _addSegmentsEntryParameterTypes0 =
		new Class[] {
			String.class, java.util.Map.class, java.util.Map.class,
			boolean.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSegmentsEntryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getSegmentsEntriesParameterTypes2 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getSegmentsEntriesParameterTypes3 =
		new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getSegmentsEntriesCountParameterTypes4 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getSegmentsEntryParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _searchSegmentsEntriesParameterTypes6 =
		new Class[] {
			long.class, long.class, String.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateSegmentsEntryParameterTypes7 =
		new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}