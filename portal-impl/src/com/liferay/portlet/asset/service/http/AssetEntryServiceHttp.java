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

package com.liferay.portlet.asset.service.http;

import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AssetEntryServiceUtil</code> service
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
 * @see AssetEntryServiceSoap
 * @generated
 */
public class AssetEntryServiceHttp {

	public static com.liferay.asset.kernel.model.AssetEntry fetchEntry(
			HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "fetchEntry",
				_fetchEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getCompanyEntries(
			HttpPrincipal httpPrincipal, long companyId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "getCompanyEntries",
				_getCompanyEntriesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.asset.kernel.model.AssetEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCompanyEntriesCount(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "getCompanyEntriesCount",
				_getCompanyEntriesCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static java.util.List<com.liferay.asset.kernel.model.AssetEntry>
			getEntries(
				HttpPrincipal httpPrincipal,
				com.liferay.asset.kernel.service.persistence.AssetEntryQuery
					entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "getEntries",
				_getEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryQuery);

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

			return (java.util.List<com.liferay.asset.kernel.model.AssetEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getEntriesCount(
			HttpPrincipal httpPrincipal,
			com.liferay.asset.kernel.service.persistence.AssetEntryQuery
				entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "getEntriesCount",
				_getEntriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryQuery);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.kernel.model.AssetEntry getEntry(
			HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "getEntry",
				_getEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.kernel.model.AssetEntry getEntry(
			HttpPrincipal httpPrincipal, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "getEntry",
				_getEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void incrementViewCounter(
			HttpPrincipal httpPrincipal,
			com.liferay.asset.kernel.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "incrementViewCounter",
				_incrementViewCounterParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetEntry);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.kernel.model.AssetEntry
			incrementViewCounter(
				HttpPrincipal httpPrincipal, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "incrementViewCounter",
				_incrementViewCounterParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.kernel.model.AssetEntry updateEntry(
			HttpPrincipal httpPrincipal, long groupId,
			java.util.Date createDate, java.util.Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean listable,
			boolean visible, java.util.Date startDate, java.util.Date endDate,
			java.util.Date publishDate, java.util.Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, String layoutUuid, int height, int width,
			Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "updateEntry",
				_updateEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, createDate, modifiedDate, className,
				classPK, classUuid, classTypeId, categoryIds, tagNames,
				listable, visible, startDate, endDate, publishDate,
				expirationDate, mimeType, title, description, summary, url,
				layoutUuid, height, width, priority);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.kernel.model.AssetEntry updateEntry(
			HttpPrincipal httpPrincipal, long groupId,
			java.util.Date createDate, java.util.Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean listable,
			boolean visible, java.util.Date startDate, java.util.Date endDate,
			java.util.Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "updateEntry",
				_updateEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, createDate, modifiedDate, className,
				classPK, classUuid, classTypeId, categoryIds, tagNames,
				listable, visible, startDate, endDate, expirationDate, mimeType,
				title, description, summary, url, layoutUuid, height, width,
				priority);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.kernel.model.AssetEntry updateEntry(
			HttpPrincipal httpPrincipal, long groupId,
			java.util.Date createDate, java.util.Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean visible,
			java.util.Date startDate, java.util.Date endDate,
			java.util.Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetEntryServiceUtil.class, "updateEntry",
				_updateEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, createDate, modifiedDate, className,
				classPK, classUuid, classTypeId, categoryIds, tagNames, visible,
				startDate, endDate, expirationDate, mimeType, title,
				description, summary, url, layoutUuid, height, width, priority,
				sync);

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

			return (com.liferay.asset.kernel.model.AssetEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetEntryServiceHttp.class);

	private static final Class<?>[] _fetchEntryParameterTypes0 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getCompanyEntriesParameterTypes1 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getCompanyEntriesCountParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getEntriesParameterTypes3 = new Class[] {
		com.liferay.asset.kernel.service.persistence.AssetEntryQuery.class
	};
	private static final Class<?>[] _getEntriesCountParameterTypes4 =
		new Class[] {
			com.liferay.asset.kernel.service.persistence.AssetEntryQuery.class
		};
	private static final Class<?>[] _getEntryParameterTypes5 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getEntryParameterTypes6 = new Class[] {
		String.class, long.class
	};
	private static final Class<?>[] _incrementViewCounterParameterTypes7 =
		new Class[] {com.liferay.asset.kernel.model.AssetEntry.class};
	private static final Class<?>[] _incrementViewCounterParameterTypes8 =
		new Class[] {String.class, long.class};
	private static final Class<?>[] _updateEntryParameterTypes9 = new Class[] {
		long.class, java.util.Date.class, java.util.Date.class, String.class,
		long.class, String.class, long.class, long[].class, String[].class,
		boolean.class, boolean.class, java.util.Date.class,
		java.util.Date.class, java.util.Date.class, java.util.Date.class,
		String.class, String.class, String.class, String.class, String.class,
		String.class, int.class, int.class, Double.class
	};
	private static final Class<?>[] _updateEntryParameterTypes10 = new Class[] {
		long.class, java.util.Date.class, java.util.Date.class, String.class,
		long.class, String.class, long.class, long[].class, String[].class,
		boolean.class, boolean.class, java.util.Date.class,
		java.util.Date.class, java.util.Date.class, String.class, String.class,
		String.class, String.class, String.class, String.class, int.class,
		int.class, Double.class
	};
	private static final Class<?>[] _updateEntryParameterTypes11 = new Class[] {
		long.class, java.util.Date.class, java.util.Date.class, String.class,
		long.class, String.class, long.class, long[].class, String[].class,
		boolean.class, java.util.Date.class, java.util.Date.class,
		java.util.Date.class, String.class, String.class, String.class,
		String.class, String.class, String.class, int.class, int.class,
		Integer.class, boolean.class
	};

}