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

package com.liferay.layout.page.template.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link LayoutPageTemplateEntryServiceUtil} service utility. The
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
 * @see LayoutPageTemplateEntryServiceSoap
 * @see HttpPrincipal
 * @see LayoutPageTemplateEntryServiceUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryServiceHttp {
	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		java.util.List<com.liferay.fragment.model.FragmentEntry> fragmentEntries,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"addLayoutPageTemplateEntry",
					_addLayoutPageTemplateEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, name, fragmentEntries,
					serviceContext);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> deleteLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long[] layoutPageTemplateEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"deleteLayoutPageTemplateEntries",
					_deleteLayoutPageTemplateEntriesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryIds);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"deleteLayoutPageTemplateEntry",
					_deleteLayoutPageTemplateEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"fetchLayoutPageTemplateEntry",
					_fetchLayoutPageTemplateEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getLayoutPageTemplateCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateCollectionsCount",
					_getLayoutPageTemplateCollectionsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId);

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

	public static int getLayoutPageTemplateCollectionsCount(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateCollectionsCount",
					_getLayoutPageTemplateCollectionsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, name);

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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, start, end);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, start, end,
					orderByComparator);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, name, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, long layoutPageTemplateFolder) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolder);

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

	public static int getLayoutPageTemplateEntriesCount(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolder, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolder, name);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId,
		java.lang.String name,
		java.util.List<com.liferay.fragment.model.FragmentEntry> fragmentEntries,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"updateLayoutPageTemplateEntry",
					_updateLayoutPageTemplateEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId, name, fragmentEntries,
					serviceContext);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPageTemplateEntryServiceHttp.class);
	private static final Class<?>[] _addLayoutPageTemplateEntryParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class, java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteLayoutPageTemplateEntriesParameterTypes1 =
		new Class[] { long[].class };
	private static final Class<?>[] _deleteLayoutPageTemplateEntryParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchLayoutPageTemplateEntryParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getLayoutPageTemplateCollectionsCountParameterTypes4 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getLayoutPageTemplateCollectionsCountParameterTypes5 =
		new Class[] { long.class, long.class, java.lang.String.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes6 =
		new Class[] { long.class, long.class, int.class, int.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes7 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes8 =
		new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes9 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes10 =
		new Class[] { long.class, long.class, java.lang.String.class };
	private static final Class<?>[] _updateLayoutPageTemplateEntryParameterTypes11 =
		new Class[] {
			long.class, java.lang.String.class, java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}