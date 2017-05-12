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

package com.liferay.commerce.product.type.virtual.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionVirtualSettingServiceUtil} service utility. The
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
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionVirtualSettingServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingServiceHttp {
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		HttpPrincipal httpPrincipal, long cpDefinitionId, long fileEntryId,
		java.lang.String url, java.lang.String activationStatus, long duration,
		int maxUsages, long sampleFileEntryId, java.lang.String sampleUrl,
		boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		long termsOfUseJournalArticleId, boolean useUrl, boolean useFileEntry,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"addCPDefinitionVirtualSetting",
					_addCPDefinitionVirtualSettingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, fileEntryId, url, activationStatus,
					duration, maxUsages, sampleFileEntryId, sampleUrl,
					termsOfUseRequired, termsOfUseContentMap,
					termsOfUseJournalArticleId, useUrl, useFileEntry,
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

			return (com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"deleteCPDefinitionVirtualSetting",
					_deleteCPDefinitionVirtualSettingParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionVirtualSetting);

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

			return (com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		HttpPrincipal httpPrincipal, long cpDefinitionVirtualSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"deleteCPDefinitionVirtualSetting",
					_deleteCPDefinitionVirtualSettingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionVirtualSettingId);

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

			return (com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
		HttpPrincipal httpPrincipal, long cpDefinitionVirtualSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"fetchCPDefinitionVirtualSetting",
					_fetchCPDefinitionVirtualSettingParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionVirtualSettingId);

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

			return (com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
		HttpPrincipal httpPrincipal, long cpDefinitionVirtualSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"getCPDefinitionVirtualSetting",
					_getCPDefinitionVirtualSettingParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionVirtualSettingId);

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

			return (com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		HttpPrincipal httpPrincipal, long cpDefinitionVirtualSettingId,
		long fileEntryId, java.lang.String url,
		java.lang.String activationStatus, long duration, int maxUsages,
		long sampleFileEntryId, java.lang.String sampleUrl,
		boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		long termsOfUseJournalArticleId, boolean useUrl, boolean useFileEntry,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"updateCPDefinitionVirtualSetting",
					_updateCPDefinitionVirtualSettingParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionVirtualSettingId, fileEntryId, url,
					activationStatus, duration, maxUsages, sampleFileEntryId,
					sampleUrl, termsOfUseRequired, termsOfUseContentMap,
					termsOfUseJournalArticleId, useUrl, useFileEntry,
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

			return (com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionVirtualSettingServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionVirtualSettingParameterTypes0 =
		new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, long.class, int.class, long.class,
			java.lang.String.class, boolean.class, java.util.Map.class,
			long.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionVirtualSettingParameterTypes1 =
		new Class[] {
			com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting.class
		};
	private static final Class<?>[] _deleteCPDefinitionVirtualSettingParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCPDefinitionVirtualSettingParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionVirtualSettingParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCPDefinitionVirtualSettingParameterTypes5 =
		new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, long.class, int.class, long.class,
			java.lang.String.class, boolean.class, java.util.Map.class,
			long.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}