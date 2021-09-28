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

import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AssetVocabularyServiceUtil</code> service
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
 * @see AssetVocabularyServiceSoap
 * @generated
 */
public class AssetVocabularyServiceHttp {

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			HttpPrincipal httpPrincipal, long groupId, String title,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings, int visibilityType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "addVocabulary",
				_addVocabularyParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, titleMap, descriptionMap, settings,
				visibilityType, serviceContext);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			HttpPrincipal httpPrincipal, long groupId, String title,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "addVocabulary",
				_addVocabularyParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, titleMap, descriptionMap, settings,
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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			HttpPrincipal httpPrincipal, long groupId, String title,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "addVocabulary",
				_addVocabularyParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, serviceContext);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			HttpPrincipal httpPrincipal, long groupId, String name,
			String title, java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "addVocabulary",
				_addVocabularyParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, title, titleMap, descriptionMap,
				settings, serviceContext);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long groupId, String name, String title,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings, int visibilityType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "addVocabulary",
				_addVocabularyParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, name, title,
				titleMap, descriptionMap, settings, visibilityType,
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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			deleteVocabularies(
				HttpPrincipal httpPrincipal, long[] vocabularyIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "deleteVocabularies",
				_deleteVocabulariesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyIds, serviceContext);

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

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteVocabulary(
			HttpPrincipal httpPrincipal, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "deleteVocabulary",
				_deleteVocabularyParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId);

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

	public static com.liferay.asset.kernel.model.AssetVocabulary
			fetchVocabulary(HttpPrincipal httpPrincipal, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "fetchVocabulary",
				_fetchVocabularyParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupsVocabularies(HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupsVocabularies",
				_getGroupsVocabulariesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupsVocabularies(
			HttpPrincipal httpPrincipal, long[] groupIds, String className) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupsVocabularies",
				_getGroupsVocabulariesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupsVocabularies(
			HttpPrincipal httpPrincipal, long[] groupIds, String className,
			long classTypePK) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupsVocabularies",
				_getGroupsVocabulariesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, className, classTypePK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getGroupVocabularies(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getGroupVocabularies(
				HttpPrincipal httpPrincipal, long groupId,
				boolean createDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, createDefaultVocabulary);

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

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getGroupVocabularies(
				HttpPrincipal httpPrincipal, long groupId,
				boolean createDefaultVocabulary, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetVocabulary>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, createDefaultVocabulary, start, end,
				orderByComparator);

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

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(
			HttpPrincipal httpPrincipal, long groupId, int visibilityType) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, visibilityType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(
			HttpPrincipal httpPrincipal, long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetVocabulary>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes15);

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
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(
			HttpPrincipal httpPrincipal, long groupId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetVocabulary>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(
			HttpPrincipal httpPrincipal, long[] groupIds,
			int[] visibilityTypes) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabularies",
				_getGroupVocabulariesParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, visibilityTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupVocabulariesCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabulariesCount",
				_getGroupVocabulariesCountParameterTypes19);

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

	public static int getGroupVocabulariesCount(
		HttpPrincipal httpPrincipal, long groupId, String name) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabulariesCount",
				_getGroupVocabulariesCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name);

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

	public static int getGroupVocabulariesCount(
		HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabulariesCount",
				_getGroupVocabulariesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

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

	public static com.liferay.asset.kernel.model.AssetVocabularyDisplay
			getGroupVocabulariesDisplay(
				HttpPrincipal httpPrincipal, long groupId, String name,
				int start, int end, boolean addDefaultVocabulary,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetVocabulary>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabulariesDisplay",
				_getGroupVocabulariesDisplayParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, start, end, addDefaultVocabulary,
				orderByComparator);

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

			return (com.liferay.asset.kernel.model.AssetVocabularyDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabularyDisplay
			getGroupVocabulariesDisplay(
				HttpPrincipal httpPrincipal, long groupId, String name,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetVocabulary>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getGroupVocabulariesDisplay",
				_getGroupVocabulariesDisplayParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, start, end, orderByComparator);

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

			return (com.liferay.asset.kernel.model.AssetVocabularyDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary getVocabulary(
			HttpPrincipal httpPrincipal, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "getVocabulary",
				_getVocabularyParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabularyDisplay
			searchVocabulariesDisplay(
				HttpPrincipal httpPrincipal, long groupId, String title,
				boolean addDefaultVocabulary, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "searchVocabulariesDisplay",
				_searchVocabulariesDisplayParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, addDefaultVocabulary, start, end);

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

			return (com.liferay.asset.kernel.model.AssetVocabularyDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabularyDisplay
			searchVocabulariesDisplay(
				HttpPrincipal httpPrincipal, long groupId, String title,
				boolean addDefaultVocabulary, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "searchVocabulariesDisplay",
				_searchVocabulariesDisplayParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, addDefaultVocabulary, start, end,
				sort);

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

			return (com.liferay.asset.kernel.model.AssetVocabularyDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			updateVocabulary(
				HttpPrincipal httpPrincipal, long vocabularyId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String settings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "updateVocabulary",
				_updateVocabularyParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId, titleMap, descriptionMap, settings);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			updateVocabulary(
				HttpPrincipal httpPrincipal, long vocabularyId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String settings, int visibilityType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "updateVocabulary",
				_updateVocabularyParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId, titleMap, descriptionMap, settings,
				visibilityType);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			updateVocabulary(
				HttpPrincipal httpPrincipal, long vocabularyId, String title,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String settings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "updateVocabulary",
				_updateVocabularyParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId, title, titleMap, descriptionMap,
				settings, serviceContext);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			updateVocabulary(
				HttpPrincipal httpPrincipal, long vocabularyId, String name,
				String title, java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String settings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyServiceUtil.class, "updateVocabulary",
				_updateVocabularyParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId, name, title, titleMap, descriptionMap,
				settings, serviceContext);

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

			return (com.liferay.asset.kernel.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetVocabularyServiceHttp.class);

	private static final Class<?>[] _addVocabularyParameterTypes0 =
		new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addVocabularyParameterTypes1 =
		new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addVocabularyParameterTypes2 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addVocabularyParameterTypes3 =
		new Class[] {
			long.class, String.class, String.class, java.util.Map.class,
			java.util.Map.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addVocabularyParameterTypes4 =
		new Class[] {
			String.class, long.class, String.class, String.class,
			java.util.Map.class, java.util.Map.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteVocabulariesParameterTypes5 =
		new Class[] {
			long[].class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteVocabularyParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchVocabularyParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupsVocabulariesParameterTypes8 =
		new Class[] {long[].class};
	private static final Class<?>[] _getGroupsVocabulariesParameterTypes9 =
		new Class[] {long[].class, String.class};
	private static final Class<?>[] _getGroupsVocabulariesParameterTypes10 =
		new Class[] {long[].class, String.class, long.class};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes12 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes13 =
		new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes14 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes15 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes16 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes17 =
		new Class[] {long[].class};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes18 =
		new Class[] {long[].class, int[].class};
	private static final Class<?>[] _getGroupVocabulariesCountParameterTypes19 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupVocabulariesCountParameterTypes20 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getGroupVocabulariesCountParameterTypes21 =
		new Class[] {long[].class};
	private static final Class<?>[]
		_getGroupVocabulariesDisplayParameterTypes22 = new Class[] {
			long.class, String.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getGroupVocabulariesDisplayParameterTypes23 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getVocabularyParameterTypes24 =
		new Class[] {long.class};
	private static final Class<?>[] _searchVocabulariesDisplayParameterTypes25 =
		new Class[] {
			long.class, String.class, boolean.class, int.class, int.class
		};
	private static final Class<?>[] _searchVocabulariesDisplayParameterTypes26 =
		new Class[] {
			long.class, String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateVocabularyParameterTypes27 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, String.class
		};
	private static final Class<?>[] _updateVocabularyParameterTypes28 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, String.class,
			int.class
		};
	private static final Class<?>[] _updateVocabularyParameterTypes29 =
		new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateVocabularyParameterTypes30 =
		new Class[] {
			long.class, String.class, String.class, java.util.Map.class,
			java.util.Map.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}