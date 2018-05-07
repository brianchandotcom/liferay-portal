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
		long layoutPageTemplateCollectionId, String name, int type,
		long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"addLayoutPageTemplateEntry",
					_addLayoutPageTemplateEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, name, type,
					fragmentEntryIds, serviceContext);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, String name,
		long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"addLayoutPageTemplateEntry",
					_addLayoutPageTemplateEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, name, fragmentEntryIds,
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
		HttpPrincipal httpPrincipal, long[] layoutPageTemplateEntryIds) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"deleteLayoutPageTemplateEntries",
					_deleteLayoutPageTemplateEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryIds);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"deleteLayoutPageTemplateEntry",
					_deleteLayoutPageTemplateEntryParameterTypes3);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classTypeId) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"fetchDefaultLayoutPageTemplateEntry",
					_fetchDefaultLayoutPageTemplateEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classTypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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
					_fetchLayoutPageTemplateEntryParameterTypes5);

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
					_getLayoutPageTemplateCollectionsCountParameterTypes6);

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
		long layoutPageTemplateCollectionId, String name) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateCollectionsCount",
					_getLayoutPageTemplateCollectionsCountParameterTypes7);

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
		HttpPrincipal httpPrincipal, long groupId, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type, start, end, orderByComparator);

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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, start, end);

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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateCollectionId, start, end,
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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classTypeId, int type) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classTypeId, type);

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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classTypeId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classTypeId, type, start, end,
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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classTypeId, String name, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classTypeId, name, type, start, end,
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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateCollectionId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes14);

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

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		HttpPrincipal httpPrincipal, long groupId, String name, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntries",
					_getLayoutPageTemplateEntriesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, type, start, end, orderByComparator);

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
		HttpPrincipal httpPrincipal, long groupId, int type) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type);

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
		HttpPrincipal httpPrincipal, long groupId, long layoutPageTemplateFolder) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes17);

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
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classTypeId, int type) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classTypeId, type);

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
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classTypeId, String name, int type) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classTypeId, name, type);

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
		long layoutPageTemplateFolder, String name) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes20);

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

	public static int getLayoutPageTemplateEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, String name, int type) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"getLayoutPageTemplateEntriesCount",
					_getLayoutPageTemplateEntriesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, type);

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
		boolean defaultTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"updateLayoutPageTemplateEntry",
					_updateLayoutPageTemplateEntryParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId, defaultTemplate);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId,
		long classNameId, long classTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"updateLayoutPageTemplateEntry",
					_updateLayoutPageTemplateEntryParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId, classNameId, classTypeId);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId,
		long[] fragmentEntryIds, String editableValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"updateLayoutPageTemplateEntry",
					_updateLayoutPageTemplateEntryParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId, fragmentEntryIds,
					editableValues, serviceContext);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"updateLayoutPageTemplateEntry",
					_updateLayoutPageTemplateEntryParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId, name);

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

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId,
		String name, long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateEntryServiceUtil.class,
					"updateLayoutPageTemplateEntry",
					_updateLayoutPageTemplateEntryParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateEntryId, name, fragmentEntryIds,
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
			long.class, long.class, String.class, int.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addLayoutPageTemplateEntryParameterTypes1 = new Class[] {
			long.class, long.class, String.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteLayoutPageTemplateEntriesParameterTypes2 =
		new Class[] { long[].class };
	private static final Class<?>[] _deleteLayoutPageTemplateEntryParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchDefaultLayoutPageTemplateEntryParameterTypes4 =
		new Class[] { long.class, long.class, long.class };
	private static final Class<?>[] _fetchLayoutPageTemplateEntryParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _getLayoutPageTemplateCollectionsCountParameterTypes6 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getLayoutPageTemplateCollectionsCountParameterTypes7 =
		new Class[] { long.class, long.class, String.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes8 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes9 =
		new Class[] { long.class, long.class, int.class, int.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes10 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes11 =
		new Class[] { long.class, long.class, long.class, int.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes12 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes13 =
		new Class[] {
			long.class, long.class, long.class, String.class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes14 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesParameterTypes15 =
		new Class[] {
			long.class, String.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes16 =
		new Class[] { long.class, int.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes17 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes18 =
		new Class[] { long.class, long.class, long.class, int.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes19 =
		new Class[] { long.class, long.class, long.class, String.class, int.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes20 =
		new Class[] { long.class, long.class, String.class };
	private static final Class<?>[] _getLayoutPageTemplateEntriesCountParameterTypes21 =
		new Class[] { long.class, String.class, int.class };
	private static final Class<?>[] _updateLayoutPageTemplateEntryParameterTypes22 =
		new Class[] { long.class, boolean.class };
	private static final Class<?>[] _updateLayoutPageTemplateEntryParameterTypes23 =
		new Class[] { long.class, long.class, long.class };
	private static final Class<?>[] _updateLayoutPageTemplateEntryParameterTypes24 =
		new Class[] {
			long.class, long[].class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLayoutPageTemplateEntryParameterTypes25 =
		new Class[] { long.class, String.class };
	private static final Class<?>[] _updateLayoutPageTemplateEntryParameterTypes26 =
		new Class[] {
			long.class, String.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}