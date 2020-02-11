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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>LayoutServiceUtil</code> service
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
 * @see LayoutServiceSoap
 * @generated
 */
public class LayoutServiceHttp {

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			long masterLayoutPlid,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, classNameId,
				classPK, localeNamesMap, localeTitlesMap, descriptionMap,
				keywordsMap, robotsMap, type, typeSettings, hidden, system,
				masterLayoutPlid, friendlyURLMap, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			long masterLayoutPlid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, classNameId,
				classPK, localeNamesMap, localeTitlesMap, descriptionMap,
				keywordsMap, robotsMap, type, typeSettings, hidden, system,
				friendlyURLMap, masterLayoutPlid, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, classNameId,
				classPK, localeNamesMap, localeTitlesMap, descriptionMap,
				keywordsMap, robotsMap, type, typeSettings, hidden, system,
				friendlyURLMap, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, long masterLayoutPlid,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId,
				localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
				robotsMap, type, typeSettings, hidden, masterLayoutPlid,
				friendlyURLMap, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			long masterLayoutPlid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId,
				localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
				robotsMap, type, typeSettings, hidden, friendlyURLMap,
				masterLayoutPlid, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId,
				localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
				robotsMap, type, typeSettings, hidden, friendlyURLMap,
				serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout addLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addLayout",
				_addLayoutParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, name, title,
				description, type, hidden, friendlyURL, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				HttpPrincipal httpPrincipal, long groupId, String folderName,
				String fileName, java.io.InputStream inputStream,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "addTempFileEntry",
				_addTempFileEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderName, fileName, inputStream,
				mimeType);

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

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "deleteLayout",
				_deleteLayoutParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, serviceContext);

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

	public static void deleteLayout(
			HttpPrincipal httpPrincipal, long plid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "deleteLayout",
				_deleteLayoutParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, serviceContext);

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

	public static void deleteTempFileEntry(
			HttpPrincipal httpPrincipal, long groupId, String folderName,
			String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "deleteTempFileEntry",
				_deleteTempFileEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderName, fileName);

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

	public static com.liferay.portal.kernel.model.Layout fetchLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "fetchLayout",
				_fetchLayoutParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
			getAncestorLayouts(HttpPrincipal httpPrincipal, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getAncestorLayouts",
				_getAncestorLayoutsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, plid);

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

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getControlPanelLayoutPlid(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getControlPanelLayoutPlid",
				_getControlPanelLayoutPlidParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getDefaultPlid(
		HttpPrincipal httpPrincipal, long groupId, boolean privateLayout) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getDefaultPlid",
				_getDefaultPlidParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getDefaultPlid(
			HttpPrincipal httpPrincipal, long groupId, long scopeGroupId,
			boolean privateLayout, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getDefaultPlid",
				_getDefaultPlidParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, scopeGroupId, privateLayout, portletId);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getDefaultPlid(
			HttpPrincipal httpPrincipal, long groupId, long scopeGroupId,
			String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getDefaultPlid",
				_getDefaultPlidParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, scopeGroupId, portletId);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout
			getLayoutByUuidAndGroupId(
				HttpPrincipal httpPrincipal, String uuid, long groupId,
				boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutByUuidAndGroupId",
				_getLayoutByUuidAndGroupIdParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId, privateLayout);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String getLayoutName(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutName",
				_getLayoutNameParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, languageId);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getLayoutPlid(
			HttpPrincipal httpPrincipal, String uuid, long groupId,
			boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutPlid",
				_getLayoutPlidParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId, privateLayout);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutReference[]
		getLayoutReferences(
			HttpPrincipal httpPrincipal, long companyId, String portletId,
			String preferencesKey, String preferencesValue) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutReferences",
				_getLayoutReferencesParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, portletId, preferencesKey,
				preferencesValue);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.LayoutReference[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		getLayouts(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayouts",
				_getLayoutsParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
			getLayouts(
				HttpPrincipal httpPrincipal, long groupId,
				boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayouts",
				_getLayoutsParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId);

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

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
			getLayouts(
				HttpPrincipal httpPrincipal, long groupId,
				boolean privateLayout, long parentLayoutId, boolean incomplete,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayouts",
				_getLayoutsParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, incomplete,
				start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
			getLayouts(
				HttpPrincipal httpPrincipal, long groupId,
				boolean privateLayout, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayouts",
				_getLayoutsParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, type);

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

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		getLayouts(HttpPrincipal httpPrincipal, long groupId, String type) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayouts",
				_getLayoutsParameterTypes25);

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

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		getLayouts(
			HttpPrincipal httpPrincipal, long groupId, String type, int start,
			int end) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayouts",
				_getLayoutsParameterTypes26);

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

			return (java.util.List<com.liferay.portal.kernel.model.Layout>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getLayoutsCount(
		HttpPrincipal httpPrincipal, long groupId, boolean privateLayout) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutsCount",
				_getLayoutsCountParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout);

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

	public static int getLayoutsCount(
		HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
		long parentLayoutId) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutsCount",
				_getLayoutsCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId);

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

	public static int getLayoutsCount(
		HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
		long parentLayoutId, int priority) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutsCount",
				_getLayoutsCountParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, priority);

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

	public static int getLayoutsCount(
		HttpPrincipal httpPrincipal, long groupId, String type) {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getLayoutsCount",
				_getLayoutsCountParameterTypes30);

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

	public static String[] getTempFileNames(
			HttpPrincipal httpPrincipal, long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "getTempFileNames",
				_getTempFileNamesParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderName);

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

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean hasLayout(
			HttpPrincipal httpPrincipal, String uuid, long groupId,
			boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "hasLayout",
				_hasLayoutParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId, privateLayout);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean hasPortletId(
			HttpPrincipal httpPrincipal, long plid, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "hasPortletId",
				_hasPortletIdParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, portletId);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void schedulePublishToLive(
			HttpPrincipal httpPrincipal, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			java.util.Map<String, String[]> parameterMap, String groupName,
			String cronText, java.util.Date schedulerStartDate,
			java.util.Date schedulerEndDate, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "schedulePublishToLive",
				_schedulePublishToLiveParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceGroupId, targetGroupId, privateLayout,
				layoutIds, parameterMap, groupName, cronText,
				schedulerStartDate, schedulerEndDate, description);

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

	public static void schedulePublishToRemote(
			HttpPrincipal httpPrincipal, long sourceGroupId,
			boolean privateLayout, java.util.Map<Long, Boolean> layoutIdMap,
			java.util.Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection,
			long remoteGroupId, boolean remotePrivateLayout,
			java.util.Date startDate, java.util.Date endDate, String groupName,
			String cronText, java.util.Date schedulerStartDate,
			java.util.Date schedulerEndDate, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "schedulePublishToRemote",
				_schedulePublishToRemoteParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceGroupId, privateLayout, layoutIdMap,
				parameterMap, remoteAddress, remotePort, remotePathContext,
				secureConnection, remoteGroupId, remotePrivateLayout, startDate,
				endDate, groupName, cronText, schedulerStartDate,
				schedulerEndDate, description);

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

	public static void setLayouts(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long parentLayoutId, long[] layoutIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "setLayouts",
				_setLayoutsParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, parentLayoutId, layoutIds,
				serviceContext);

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

	public static void unschedulePublishToLive(
			HttpPrincipal httpPrincipal, long groupId, String jobName,
			String groupName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "unschedulePublishToLive",
				_unschedulePublishToLiveParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, jobName, groupName);

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

	public static void unschedulePublishToRemote(
			HttpPrincipal httpPrincipal, long groupId, String jobName,
			String groupName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "unschedulePublishToRemote",
				_unschedulePublishToRemoteParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, jobName, groupName);

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

	public static com.liferay.portal.kernel.model.Layout updateIconImage(
			HttpPrincipal httpPrincipal, long plid, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateIconImage",
				_updateIconImageParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, bytes);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			boolean hasIconImage, byte[] iconBytes, long masterLayoutPlid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateLayout",
				_updateLayoutParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, parentLayoutId,
				localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
				robotsMap, type, hidden, friendlyURLMap, hasIconImage,
				iconBytes, masterLayoutPlid, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			boolean hasIconImage, byte[] iconBytes,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateLayout",
				_updateLayoutParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, parentLayoutId,
				localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
				robotsMap, type, hidden, friendlyURLMap, hasIconImage,
				iconBytes, serviceContext);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateLayout(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateLayout",
				_updateLayoutParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, typeSettings);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateLookAndFeel(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, String themeId, String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateLookAndFeel",
				_updateLookAndFeelParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, themeId,
				colorSchemeId, css);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateName(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, String name, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateName",
				_updateNameParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, name, languageId);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateName(
			HttpPrincipal httpPrincipal, long plid, String name,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateName",
				_updateNameParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, name, languageId);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateParentLayoutId(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateParentLayoutId",
				_updateParentLayoutIdParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, parentLayoutId);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateParentLayoutId(
			HttpPrincipal httpPrincipal, long plid, long parentPlid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateParentLayoutId",
				_updateParentLayoutIdParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, parentPlid);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout
			updateParentLayoutIdAndPriority(
				HttpPrincipal httpPrincipal, long plid, long parentPlid,
				int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateParentLayoutIdAndPriority",
				_updateParentLayoutIdAndPriorityParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, parentPlid, priority);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updatePriority(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updatePriority",
				_updatePriorityParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, priority);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updatePriority(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long layoutId, long nextLayoutId, long previousLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updatePriority",
				_updatePriorityParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, layoutId, nextLayoutId,
				previousLayoutId);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updatePriority(
			HttpPrincipal httpPrincipal, long plid, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updatePriority",
				_updatePriorityParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, priority);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Layout updateType(
			HttpPrincipal httpPrincipal, long plid, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutServiceUtil.class, "updateType",
				_updateTypeParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, type);

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

			return (com.liferay.portal.kernel.model.Layout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutServiceHttp.class);

	private static final Class<?>[] _addLayoutParameterTypes0 = new Class[] {
		long.class, boolean.class, long.class, long.class, long.class,
		java.util.Map.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, java.util.Map.class, String.class, String.class,
		boolean.class, boolean.class, long.class, java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addLayoutParameterTypes1 = new Class[] {
		long.class, boolean.class, long.class, long.class, long.class,
		java.util.Map.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, java.util.Map.class, String.class, String.class,
		boolean.class, boolean.class, java.util.Map.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addLayoutParameterTypes2 = new Class[] {
		long.class, boolean.class, long.class, long.class, long.class,
		java.util.Map.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, java.util.Map.class, String.class, String.class,
		boolean.class, boolean.class, java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addLayoutParameterTypes3 = new Class[] {
		long.class, boolean.class, long.class, java.util.Map.class,
		java.util.Map.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, boolean.class,
		long.class, java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addLayoutParameterTypes4 = new Class[] {
		long.class, boolean.class, long.class, java.util.Map.class,
		java.util.Map.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, boolean.class,
		java.util.Map.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addLayoutParameterTypes5 = new Class[] {
		long.class, boolean.class, long.class, java.util.Map.class,
		java.util.Map.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, boolean.class,
		java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addLayoutParameterTypes6 = new Class[] {
		long.class, boolean.class, long.class, String.class, String.class,
		String.class, String.class, boolean.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addTempFileEntryParameterTypes7 =
		new Class[] {
			long.class, String.class, String.class, java.io.InputStream.class,
			String.class
		};
	private static final Class<?>[] _deleteLayoutParameterTypes8 = new Class[] {
		long.class, boolean.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteLayoutParameterTypes9 = new Class[] {
		long.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteTempFileEntryParameterTypes10 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _fetchLayoutParameterTypes11 = new Class[] {
		long.class, boolean.class, long.class
	};
	private static final Class<?>[] _getAncestorLayoutsParameterTypes12 =
		new Class[] {long.class};
	private static final Class<?>[] _getControlPanelLayoutPlidParameterTypes13 =
		new Class[] {};
	private static final Class<?>[] _getDefaultPlidParameterTypes14 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getDefaultPlidParameterTypes15 =
		new Class[] {long.class, long.class, boolean.class, String.class};
	private static final Class<?>[] _getDefaultPlidParameterTypes16 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _getLayoutByUuidAndGroupIdParameterTypes17 =
		new Class[] {String.class, long.class, boolean.class};
	private static final Class<?>[] _getLayoutNameParameterTypes18 =
		new Class[] {long.class, boolean.class, long.class, String.class};
	private static final Class<?>[] _getLayoutPlidParameterTypes19 =
		new Class[] {String.class, long.class, boolean.class};
	private static final Class<?>[] _getLayoutReferencesParameterTypes20 =
		new Class[] {long.class, String.class, String.class, String.class};
	private static final Class<?>[] _getLayoutsParameterTypes21 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[] _getLayoutsParameterTypes22 = new Class[] {
		long.class, boolean.class, long.class
	};
	private static final Class<?>[] _getLayoutsParameterTypes23 = new Class[] {
		long.class, boolean.class, long.class, boolean.class, int.class,
		int.class
	};
	private static final Class<?>[] _getLayoutsParameterTypes24 = new Class[] {
		long.class, boolean.class, String.class
	};
	private static final Class<?>[] _getLayoutsParameterTypes25 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getLayoutsParameterTypes26 = new Class[] {
		long.class, String.class, int.class, int.class
	};
	private static final Class<?>[] _getLayoutsCountParameterTypes27 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getLayoutsCountParameterTypes28 =
		new Class[] {long.class, boolean.class, long.class};
	private static final Class<?>[] _getLayoutsCountParameterTypes29 =
		new Class[] {long.class, boolean.class, long.class, int.class};
	private static final Class<?>[] _getLayoutsCountParameterTypes30 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getTempFileNamesParameterTypes31 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _hasLayoutParameterTypes32 = new Class[] {
		String.class, long.class, boolean.class
	};
	private static final Class<?>[] _hasPortletIdParameterTypes33 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _schedulePublishToLiveParameterTypes34 =
		new Class[] {
			long.class, long.class, boolean.class, long[].class,
			java.util.Map.class, String.class, String.class,
			java.util.Date.class, java.util.Date.class, String.class
		};
	private static final Class<?>[] _schedulePublishToRemoteParameterTypes35 =
		new Class[] {
			long.class, boolean.class, java.util.Map.class, java.util.Map.class,
			String.class, int.class, String.class, boolean.class, long.class,
			boolean.class, java.util.Date.class, java.util.Date.class,
			String.class, String.class, java.util.Date.class,
			java.util.Date.class, String.class
		};
	private static final Class<?>[] _setLayoutsParameterTypes36 = new Class[] {
		long.class, boolean.class, long.class, long[].class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _unschedulePublishToLiveParameterTypes37 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _unschedulePublishToRemoteParameterTypes38 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _updateIconImageParameterTypes39 =
		new Class[] {long.class, byte[].class};
	private static final Class<?>[] _updateLayoutParameterTypes40 =
		new Class[] {
			long.class, boolean.class, long.class, long.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, java.util.Map.class, boolean.class, byte[].class,
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLayoutParameterTypes41 =
		new Class[] {
			long.class, boolean.class, long.class, long.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, java.util.Map.class, boolean.class, byte[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLayoutParameterTypes42 =
		new Class[] {long.class, boolean.class, long.class, String.class};
	private static final Class<?>[] _updateLookAndFeelParameterTypes43 =
		new Class[] {
			long.class, boolean.class, long.class, String.class, String.class,
			String.class
		};
	private static final Class<?>[] _updateNameParameterTypes44 = new Class[] {
		long.class, boolean.class, long.class, String.class, String.class
	};
	private static final Class<?>[] _updateNameParameterTypes45 = new Class[] {
		long.class, String.class, String.class
	};
	private static final Class<?>[] _updateParentLayoutIdParameterTypes46 =
		new Class[] {long.class, boolean.class, long.class, long.class};
	private static final Class<?>[] _updateParentLayoutIdParameterTypes47 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_updateParentLayoutIdAndPriorityParameterTypes48 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _updatePriorityParameterTypes49 =
		new Class[] {long.class, boolean.class, long.class, int.class};
	private static final Class<?>[] _updatePriorityParameterTypes50 =
		new Class[] {
			long.class, boolean.class, long.class, long.class, long.class
		};
	private static final Class<?>[] _updatePriorityParameterTypes51 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _updateTypeParameterTypes52 = new Class[] {
		long.class, String.class
	};

}