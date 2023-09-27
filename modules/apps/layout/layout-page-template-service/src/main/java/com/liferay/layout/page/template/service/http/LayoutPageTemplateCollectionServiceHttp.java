/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.http;

import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>LayoutPageTemplateCollectionServiceUtil</code> service
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
public class LayoutPageTemplateCollectionServiceHttp {

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateCollection
				addLayoutPageTemplateCollection(
					HttpPrincipal httpPrincipal, long groupId,
					long parentLayoutPageTemplateCollection, String name,
					String description, int type,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"addLayoutPageTemplateCollection",
				_addLayoutPageTemplateCollectionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentLayoutPageTemplateCollection, name,
				description, type, serviceContext);

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

			return (com.liferay.layout.page.template.model.
				LayoutPageTemplateCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateCollection
				deleteLayoutPageTemplateCollection(
					HttpPrincipal httpPrincipal,
					long layoutPageTemplateCollectionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"deleteLayoutPageTemplateCollection",
				_deleteLayoutPageTemplateCollectionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutPageTemplateCollectionId);

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

			return (com.liferay.layout.page.template.model.
				LayoutPageTemplateCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteLayoutPageTemplateCollections(
			HttpPrincipal httpPrincipal, long[] layoutPageTemplateCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"deleteLayoutPageTemplateCollections",
				_deleteLayoutPageTemplateCollectionsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutPageTemplateCollectionIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateCollection
				fetchLayoutPageTemplateCollection(
					HttpPrincipal httpPrincipal,
					long layoutPageTemplateCollectionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"fetchLayoutPageTemplateCollection",
				_fetchLayoutPageTemplateCollectionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutPageTemplateCollectionId);

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

			return (com.liferay.layout.page.template.model.
				LayoutPageTemplateCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateCollection>
			getLayoutPageTemplateCollections(
				HttpPrincipal httpPrincipal, long groupId, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"getLayoutPageTemplateCollections",
				_getLayoutPageTemplateCollectionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.layout.page.template.model.
					LayoutPageTemplateCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateCollection>
			getLayoutPageTemplateCollections(
				HttpPrincipal httpPrincipal, long groupId, int type, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"getLayoutPageTemplateCollections",
				_getLayoutPageTemplateCollectionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, type, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.layout.page.template.model.
					LayoutPageTemplateCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateCollection>
			getLayoutPageTemplateCollections(
				HttpPrincipal httpPrincipal, long groupId, int type, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateCollection> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"getLayoutPageTemplateCollections",
				_getLayoutPageTemplateCollectionsParameterTypes6);

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
				<com.liferay.layout.page.template.model.
					LayoutPageTemplateCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateCollection>
			getLayoutPageTemplateCollections(
				HttpPrincipal httpPrincipal, long groupId, String name,
				int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateCollection> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"getLayoutPageTemplateCollections",
				_getLayoutPageTemplateCollectionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, type, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.layout.page.template.model.
					LayoutPageTemplateCollection>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getLayoutPageTemplateCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"getLayoutPageTemplateCollectionsCount",
				_getLayoutPageTemplateCollectionsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, type);

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

	public static int getLayoutPageTemplateCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId, String name, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"getLayoutPageTemplateCollectionsCount",
				_getLayoutPageTemplateCollectionsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, type);

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

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateCollection
				updateLayoutPageTemplateCollection(
					HttpPrincipal httpPrincipal,
					long layoutPageTemplateCollectionId, String name,
					String description)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutPageTemplateCollectionServiceUtil.class,
				"updateLayoutPageTemplateCollection",
				_updateLayoutPageTemplateCollectionParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutPageTemplateCollectionId, name, description);

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

			return (com.liferay.layout.page.template.model.
				LayoutPageTemplateCollection)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateCollectionServiceHttp.class);

	private static final Class<?>[]
		_addLayoutPageTemplateCollectionParameterTypes0 = new Class[] {
			long.class, long.class, String.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteLayoutPageTemplateCollectionParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteLayoutPageTemplateCollectionsParameterTypes2 = new Class[] {
			long[].class
		};
	private static final Class<?>[]
		_fetchLayoutPageTemplateCollectionParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getLayoutPageTemplateCollectionsParameterTypes4 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[]
		_getLayoutPageTemplateCollectionsParameterTypes5 = new Class[] {
			long.class, int.class, int.class, int.class
		};
	private static final Class<?>[]
		_getLayoutPageTemplateCollectionsParameterTypes6 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getLayoutPageTemplateCollectionsParameterTypes7 = new Class[] {
			long.class, String.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getLayoutPageTemplateCollectionsCountParameterTypes8 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[]
		_getLayoutPageTemplateCollectionsCountParameterTypes9 = new Class[] {
			long.class, String.class, int.class
		};
	private static final Class<?>[]
		_updateLayoutPageTemplateCollectionParameterTypes10 = new Class[] {
			long.class, String.class, String.class
		};

}