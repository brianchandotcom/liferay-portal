/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPRuleAssetCategoryRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPRuleAssetCategoryRelServiceUtil} service utility. The
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
 * @see CPRuleAssetCategoryRelServiceSoap
 * @see HttpPrincipal
 * @see CPRuleAssetCategoryRelServiceUtil
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelServiceHttp {
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		HttpPrincipal httpPrincipal, long cpRuleId, long assetCategoryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPRuleAssetCategoryRelServiceUtil.class,
					"addCPRuleAssetCategoryRel",
					_addCPRuleAssetCategoryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpRuleId, assetCategoryId, serviceContext);

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

			return (com.liferay.commerce.product.model.CPRuleAssetCategoryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPRuleAssetCategoryRel(
		HttpPrincipal httpPrincipal, long cpRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPRuleAssetCategoryRelServiceUtil.class,
					"deleteCPRuleAssetCategoryRel",
					_deleteCPRuleAssetCategoryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpRuleAssetCategoryRelId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getAssetCategoryIds(HttpPrincipal httpPrincipal,
		long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPRuleAssetCategoryRelServiceUtil.class,
					"getAssetCategoryIds", _getAssetCategoryIdsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, cpRuleId);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		HttpPrincipal httpPrincipal, long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPRuleAssetCategoryRelServiceUtil.class,
					"getCPRuleAssetCategoryRels",
					_getCPRuleAssetCategoryRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, cpRuleId);

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

			return (java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPRuleAssetCategoryRelServiceHttp.class);
	private static final Class<?>[] _addCPRuleAssetCategoryRelParameterTypes0 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPRuleAssetCategoryRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getAssetCategoryIdsParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPRuleAssetCategoryRelsParameterTypes3 = new Class[] {
			long.class
		};
}