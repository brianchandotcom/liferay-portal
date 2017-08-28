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

package com.liferay.modern.site.building.fragment.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link MSBFragmentEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see MSBFragmentEntryServiceSoap
 * @see HttpPrincipal
 * @see MSBFragmentEntryServiceUtil
 * @generated
 */
@ProviderType
public class MSBFragmentEntryServiceHttp {
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		HttpPrincipal httpPrincipal, long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"addMSBFragmentEntry", _addMSBFragmentEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					msbFragmentCollectionId, name, css, html, js, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> deleteMSBFragmentEntries(
		HttpPrincipal httpPrincipal, long[] msbFragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"deleteMSBFragmentEntries",
					_deleteMSBFragmentEntriesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentEntriesIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		HttpPrincipal httpPrincipal, long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"deleteMSBFragmentEntry",
					_deleteMSBFragmentEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchMSBFragmentEntries(
		HttpPrincipal httpPrincipal, long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"fetchMSBFragmentEntries",
					_fetchMSBFragmentEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentCollectionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchMSBFragmentEntry(
		HttpPrincipal httpPrincipal, long msbFragmentEntryId) {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"fetchMSBFragmentEntry",
					_fetchMSBFragmentEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getMSBFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long msbFragmentCollectionId) {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"getMSBFragmentCollectionsCount",
					_getMSBFragmentCollectionsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentCollectionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		HttpPrincipal httpPrincipal, long msbFragmentCollectionId, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"getMSBFragmentEntries",
					_getMSBFragmentEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentCollectionId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"getMSBFragmentEntries",
					_getMSBFragmentEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					msbFragmentCollectionId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"getMSBFragmentEntries",
					_getMSBFragmentEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					msbFragmentCollectionId, name, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		HttpPrincipal httpPrincipal, long msbFragmentEntryId,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentEntryServiceUtil.class,
					"updateMSBFragmentEntry",
					_updateMSBFragmentEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentEntryId, name, css, html, js);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MSBFragmentEntryServiceHttp.class);
	private static final Class<?>[] _addMSBFragmentEntryParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteMSBFragmentEntriesParameterTypes1 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _deleteMSBFragmentEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchMSBFragmentEntriesParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchMSBFragmentEntryParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getMSBFragmentCollectionsCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _getMSBFragmentEntriesParameterTypes6 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getMSBFragmentEntriesParameterTypes7 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMSBFragmentEntriesParameterTypes8 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _updateMSBFragmentEntryParameterTypes9 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class
		};
}