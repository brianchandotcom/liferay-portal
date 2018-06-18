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

import com.liferay.commerce.product.service.CPDefinitionOptionValueRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionOptionValueRelServiceUtil} service utility. The
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
 * @see CPDefinitionOptionValueRelServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionOptionValueRelServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelServiceHttp {
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"addCPDefinitionOptionValueRel",
					_addCPDefinitionOptionValueRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId, nameMap, priority, key,
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

			return (com.liferay.commerce.product.model.CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"deleteCPDefinitionOptionValueRel",
					_deleteCPDefinitionOptionValueRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionValueRelId);

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

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"fetchCPDefinitionOptionValueRel",
					_fetchCPDefinitionOptionValueRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"getCPDefinitionOptionValueRel",
					_getCPDefinitionOptionValueRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"getCPDefinitionOptionValueRels",
					_getCPDefinitionOptionValueRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId, start, end);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"getCPDefinitionOptionValueRels",
					_getCPDefinitionOptionValueRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		HttpPrincipal httpPrincipal, long groupId, String key, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"getCPDefinitionOptionValueRels",
					_getCPDefinitionOptionValueRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					key, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPDefinitionOptionValueRelsCount(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"getCPDefinitionOptionValueRelsCount",
					_getCPDefinitionOptionValueRelsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long cpDefinitionOptionRelId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"searchCPDefinitionOptionValueRels",
					_searchCPDefinitionOptionValueRelsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, cpDefinitionOptionRelId, keywords,
					start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionValueRelServiceUtil.class,
					"updateCPDefinitionOptionValueRel",
					_updateCPDefinitionOptionValueRelParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionValueRelId, nameMap, priority, key,
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

			return (com.liferay.commerce.product.model.CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionOptionValueRelServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionOptionValueRelParameterTypes0 =
		new Class[] {
			long.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionOptionValueRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCPDefinitionOptionValueRelParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionOptionValueRelParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionOptionValueRelsParameterTypes4 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _getCPDefinitionOptionValueRelsParameterTypes5 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionOptionValueRelsParameterTypes6 =
		new Class[] { long.class, String.class, int.class, int.class };
	private static final Class<?>[] _getCPDefinitionOptionValueRelsCountParameterTypes7 =
		new Class[] { long.class };
	private static final Class<?>[] _searchCPDefinitionOptionValueRelsParameterTypes8 =
		new Class[] {
			long.class, long.class, long.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPDefinitionOptionValueRelParameterTypes9 =
		new Class[] {
			long.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}