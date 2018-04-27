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
		String url, String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"addCPDefinitionVirtualSetting",
					_addCPDefinitionVirtualSettingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, fileEntryId, url, activationStatus,
					duration, maxUsages, useSample, sampleFileEntryId,
					sampleUrl, termsOfUseRequired, termsOfUseContentMap,
					termsOfUseJournalArticleResourcePrimKey, serviceContext);

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

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByCPDefinitionId(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"fetchCPDefinitionVirtualSettingByCPDefinitionId",
					_fetchCPDefinitionVirtualSettingByCPDefinitionIdParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

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
		long fileEntryId, String url, String activationStatus, long duration,
		int maxUsages, boolean useSample, long sampleFileEntryId,
		String sampleUrl, boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionVirtualSettingServiceUtil.class,
					"updateCPDefinitionVirtualSetting",
					_updateCPDefinitionVirtualSettingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionVirtualSettingId, fileEntryId, url,
					activationStatus, duration, maxUsages, useSample,
					sampleFileEntryId, sampleUrl, termsOfUseRequired,
					termsOfUseContentMap,
					termsOfUseJournalArticleResourcePrimKey, serviceContext);

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
			long.class, long.class, String.class, String.class, long.class,
			int.class, boolean.class, long.class, String.class, boolean.class,
			java.util.Map.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _fetchCPDefinitionVirtualSettingByCPDefinitionIdParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCPDefinitionVirtualSettingParameterTypes2 =
		new Class[] {
			long.class, long.class, String.class, String.class, long.class,
			int.class, boolean.class, long.class, String.class, boolean.class,
			java.util.Map.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}