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

package com.liferay.fragment.service.http;

import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>FragmentCollectionServiceUtil</code> service
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
 * @see FragmentCollectionServiceSoap
 * @generated
 */
public class FragmentCollectionServiceHttp {

	public static com.liferay.fragment.model.FragmentCollection
			addFragmentCollection(
				HttpPrincipal httpPrincipal, long groupId, String name,
				String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "addFragmentCollection",
				_addFragmentCollectionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, description, serviceContext);

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

			return (com.liferay.fragment.model.FragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.fragment.model.FragmentCollection
			addFragmentCollection(
				HttpPrincipal httpPrincipal, long groupId,
				String fragmentCollectionKey, String name, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "addFragmentCollection",
				_addFragmentCollectionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, fragmentCollectionKey, name, description,
				serviceContext);

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

			return (com.liferay.fragment.model.FragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.fragment.model.FragmentCollection
			deleteFragmentCollection(
				HttpPrincipal httpPrincipal, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "deleteFragmentCollection",
				_deleteFragmentCollectionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fragmentCollectionId);

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

			return (com.liferay.fragment.model.FragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFragmentCollections(
			HttpPrincipal httpPrincipal, long[] fragmentCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class,
				"deleteFragmentCollections",
				_deleteFragmentCollectionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fragmentCollectionIds);

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

	public static com.liferay.fragment.model.FragmentCollection
			fetchFragmentCollection(
				HttpPrincipal httpPrincipal, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "fetchFragmentCollection",
				_fetchFragmentCollectionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fragmentCollectionId);

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

			return (com.liferay.fragment.model.FragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			HttpPrincipal httpPrincipal, long groupId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			HttpPrincipal httpPrincipal, long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			HttpPrincipal httpPrincipal, long groupId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			HttpPrincipal httpPrincipal, long[] groupIds, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			HttpPrincipal httpPrincipal, long[] groupIds, String name,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getFragmentCollections",
				_getFragmentCollectionsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, name, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.fragment.model.FragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class,
				"getFragmentCollectionsCount",
				_getFragmentCollectionsCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static int getFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId, String name) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class,
				"getFragmentCollectionsCount",
				_getFragmentCollectionsCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name);

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

	public static int getFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class,
				"getFragmentCollectionsCount",
				_getFragmentCollectionsCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

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

	public static int getFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long[] groupIds, String name) {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class,
				"getFragmentCollectionsCount",
				_getFragmentCollectionsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, name);

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

	public static String[] getTempFileNames(
			HttpPrincipal httpPrincipal, long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "getTempFileNames",
				_getTempFileNamesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderName);

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

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.fragment.model.FragmentCollection
			updateFragmentCollection(
				HttpPrincipal httpPrincipal, long fragmentCollectionId,
				String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FragmentCollectionServiceUtil.class, "updateFragmentCollection",
				_updateFragmentCollectionParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fragmentCollectionId, name, description);

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

			return (com.liferay.fragment.model.FragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FragmentCollectionServiceHttp.class);

	private static final Class<?>[] _addFragmentCollectionParameterTypes0 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addFragmentCollectionParameterTypes1 =
		new Class[] {
			long.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFragmentCollectionParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteFragmentCollectionsParameterTypes3 =
		new Class[] {long[].class};
	private static final Class<?>[] _fetchFragmentCollectionParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes6 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes7 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes8 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes9 =
		new Class[] {long[].class};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes10 =
		new Class[] {
			long[].class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFragmentCollectionsParameterTypes11 =
		new Class[] {
			long[].class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getFragmentCollectionsCountParameterTypes12 = new Class[] {long.class};
	private static final Class<?>[]
		_getFragmentCollectionsCountParameterTypes13 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getFragmentCollectionsCountParameterTypes14 = new Class[] {
			long[].class
		};
	private static final Class<?>[]
		_getFragmentCollectionsCountParameterTypes15 = new Class[] {
			long[].class, String.class
		};
	private static final Class<?>[] _getTempFileNamesParameterTypes16 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateFragmentCollectionParameterTypes17 =
		new Class[] {long.class, String.class, String.class};

}