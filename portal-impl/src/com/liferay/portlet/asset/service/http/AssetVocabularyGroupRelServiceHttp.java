/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.http;

import com.liferay.asset.kernel.service.AssetVocabularyGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AssetVocabularyGroupRelServiceUtil</code> service
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
public class AssetVocabularyGroupRelServiceHttp {

	public static com.liferay.asset.kernel.model.AssetVocabularyGroupRel
			addAssetVocabularyGroupRel(
				HttpPrincipal httpPrincipal, long groupId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyGroupRelServiceUtil.class,
				"addAssetVocabularyGroupRel",
				_addAssetVocabularyGroupRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, vocabularyId);

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

			return (com.liferay.asset.kernel.model.AssetVocabularyGroupRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.asset.kernel.model.AssetVocabularyGroupRel>
				getAssetVocabularyGroupRelsByVocabularyId(
					HttpPrincipal httpPrincipal, long vocabularyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyGroupRelServiceUtil.class,
				"getAssetVocabularyGroupRelsByVocabularyId",
				_getAssetVocabularyGroupRelsByVocabularyIdParameterTypes1);

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

			return (java.util.List
				<com.liferay.asset.kernel.model.AssetVocabularyGroupRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void setAssetVocabularyGroupRels(
			HttpPrincipal httpPrincipal, long vocabularyId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetVocabularyGroupRelServiceUtil.class,
				"setAssetVocabularyGroupRels",
				_setAssetVocabularyGroupRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vocabularyId, groupIds);

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

	private static Log _log = LogFactoryUtil.getLog(
		AssetVocabularyGroupRelServiceHttp.class);

	private static final Class<?>[] _addAssetVocabularyGroupRelParameterTypes0 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getAssetVocabularyGroupRelsByVocabularyIdParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_setAssetVocabularyGroupRelsParameterTypes2 = new Class[] {
			long.class, long[].class
		};

}
// SB-Hash:423677408