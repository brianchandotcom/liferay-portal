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

import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link MSBFragmentCollectionServiceUtil} service utility. The
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
 * @see MSBFragmentCollectionServiceSoap
 * @see HttpPrincipal
 * @see MSBFragmentCollectionServiceUtil
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionServiceHttp {
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"addMSBFragmentCollection",
					_addMSBFragmentCollectionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, description, serviceContext);

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

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		HttpPrincipal httpPrincipal, long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"deleteMSBFragmentCollection",
					_deleteMSBFragmentCollectionParameterTypes1);

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

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> deleteMSBFragmentCollections(
		HttpPrincipal httpPrincipal, long[] msbFragmentCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"deleteMSBFragmentCollections",
					_deleteMSBFragmentCollectionsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentCollectionIds);

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

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection fetchMSBFragmentCollection(
		HttpPrincipal httpPrincipal, long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"fetchMSBFragmentCollection",
					_fetchMSBFragmentCollectionParameterTypes3);

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

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		HttpPrincipal httpPrincipal, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"getMSBFragmentCollections",
					_getMSBFragmentCollectionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end);

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

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"getMSBFragmentCollections",
					_getMSBFragmentCollectionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

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

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"getMSBFragmentCollections",
					_getMSBFragmentCollectionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getMSBFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"getMSBFragmentCollectionsCount",
					_getMSBFragmentCollectionsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static int getMSBFragmentCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"getMSBFragmentCollectionsCount",
					_getMSBFragmentCollectionsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name);

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

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		HttpPrincipal httpPrincipal, long msbFragmentCollectionId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(MSBFragmentCollectionServiceUtil.class,
					"updateMSBFragmentCollection",
					_updateMSBFragmentCollectionParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					msbFragmentCollectionId, name, description);

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

			return (com.liferay.modern.site.building.fragment.model.MSBFragmentCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MSBFragmentCollectionServiceHttp.class);
	private static final Class<?>[] _addMSBFragmentCollectionParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteMSBFragmentCollectionParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteMSBFragmentCollectionsParameterTypes2 =
		new Class[] { long[].class };
	private static final Class<?>[] _fetchMSBFragmentCollectionParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getMSBFragmentCollectionsParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getMSBFragmentCollectionsParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMSBFragmentCollectionsParameterTypes6 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMSBFragmentCollectionsCountParameterTypes7 =
		new Class[] { long.class };
	private static final Class<?>[] _getMSBFragmentCollectionsCountParameterTypes8 =
		new Class[] { long.class, java.lang.String.class };
	private static final Class<?>[] _updateMSBFragmentCollectionParameterTypes9 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class
		};
}