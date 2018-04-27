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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceTaxMethodServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceTaxMethodServiceUtil} service utility. The
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
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodServiceSoap
 * @see HttpPrincipal
 * @see CommerceTaxMethodServiceUtil
 * @generated
 */
@ProviderType
public class CommerceTaxMethodServiceHttp {
	public static com.liferay.commerce.model.CommerceTaxMethod addCommerceTaxMethod(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String engineKey, boolean percentage, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"addCommerceTaxMethod", _addCommerceTaxMethodParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					descriptionMap, engineKey, percentage, active,
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

			return (com.liferay.commerce.model.CommerceTaxMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceTaxMethod createCommerceTaxMethod(
		HttpPrincipal httpPrincipal, long commerceTaxMethodId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"createCommerceTaxMethod",
					_createCommerceTaxMethodParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxMethodId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceTaxMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceTaxMethod(HttpPrincipal httpPrincipal,
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"deleteCommerceTaxMethod",
					_deleteCommerceTaxMethodParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxMethodId);

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

	public static com.liferay.commerce.model.CommerceTaxMethod fetchCommerceTaxMethod(
		HttpPrincipal httpPrincipal, long groupId, String engineKey) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"fetchCommerceTaxMethod",
					_fetchCommerceTaxMethodParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					engineKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceTaxMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceTaxMethod getCommerceTaxMethod(
		HttpPrincipal httpPrincipal, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"getCommerceTaxMethod", _getCommerceTaxMethodParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxMethodId);

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

			return (com.liferay.commerce.model.CommerceTaxMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"getCommerceTaxMethods",
					_getCommerceTaxMethodsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

			return (java.util.List<com.liferay.commerce.model.CommerceTaxMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		HttpPrincipal httpPrincipal, long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"getCommerceTaxMethods",
					_getCommerceTaxMethodsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceTaxMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceTaxMethodsCount(HttpPrincipal httpPrincipal,
		long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"getCommerceTaxMethodsCount",
					_getCommerceTaxMethodsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

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

	public static com.liferay.commerce.model.CommerceTaxMethod setActive(
		HttpPrincipal httpPrincipal, long commerceTaxMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"setActive", _setActiveParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxMethodId, active);

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

			return (com.liferay.commerce.model.CommerceTaxMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceTaxMethod updateCommerceTaxMethod(
		HttpPrincipal httpPrincipal, long commerceTaxMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean percentage, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxMethodServiceUtil.class,
					"updateCommerceTaxMethod",
					_updateCommerceTaxMethodParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxMethodId, nameMap, descriptionMap, percentage,
					active);

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

			return (com.liferay.commerce.model.CommerceTaxMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceTaxMethodServiceHttp.class);
	private static final Class<?>[] _addCommerceTaxMethodParameterTypes0 = new Class[] {
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _createCommerceTaxMethodParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteCommerceTaxMethodParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceTaxMethodParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCommerceTaxMethodParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceTaxMethodsParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceTaxMethodsParameterTypes6 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCommerceTaxMethodsCountParameterTypes7 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setActiveParameterTypes8 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCommerceTaxMethodParameterTypes9 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, boolean.class,
			boolean.class
		};
}