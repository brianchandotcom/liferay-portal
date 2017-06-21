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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPDefinitionLinkServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionLinkServiceUtil} service utility. The
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
 * @see CPDefinitionLinkServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionLinkServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionLinkServiceHttp {
	public static com.liferay.commerce.product.model.CPDefinitionLink addCPDefinitionLink(
		HttpPrincipal httpPrincipal, long cpDefinitionId1,
		long cpDefinitionId2, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"addCPDefinitionLink", _addCPDefinitionLinkParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId1, cpDefinitionId2, priority, type,
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

			return (com.liferay.commerce.product.model.CPDefinitionLink)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.model.CPDefinitionLink cpDefinitionLink)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"deleteCPDefinitionLink",
					_deleteCPDefinitionLinkParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionLink);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		HttpPrincipal httpPrincipal, long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"deleteCPDefinitionLink",
					_deleteCPDefinitionLinkParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionLinkId);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink fetchCPDefinitionLink(
		HttpPrincipal httpPrincipal, long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"fetchCPDefinitionLink",
					_fetchCPDefinitionLinkParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionLinkId);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink getCPDefinitionLink(
		HttpPrincipal httpPrincipal, long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"getCPDefinitionLink", _getCPDefinitionLinkParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionLinkId);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"getCPDefinitionLinks", _getCPDefinitionLinksParameterTypes5);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionLink>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		HttpPrincipal httpPrincipal, long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionLink> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"getCPDefinitionLinks", _getCPDefinitionLinksParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionLink>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPDefinitionLinksCount(HttpPrincipal httpPrincipal,
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"getCPDefinitionLinksCount",
					_getCPDefinitionLinksCountParameterTypes7);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink updateCPDefinitionLink(
		HttpPrincipal httpPrincipal, long cpDefinitionLinkId, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"updateCPDefinitionLink",
					_updateCPDefinitionLinkParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionLinkId, priority);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateCPDefinitionLinks(HttpPrincipal httpPrincipal,
		long cpDefinitionId, long[] cpDefinitionLinkEntryIds, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionLinkServiceUtil.class,
					"updateCPDefinitionLinks",
					_updateCPDefinitionLinksParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpDefinitionLinkEntryIds, type,
					serviceContext);

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

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionLinkServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionLinkParameterTypes0 = new Class[] {
			long.class, long.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionLinkParameterTypes1 = new Class[] {
			com.liferay.commerce.product.model.CPDefinitionLink.class
		};
	private static final Class<?>[] _deleteCPDefinitionLinkParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPDefinitionLinkParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionLinkParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionLinksParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionLinksParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionLinksCountParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCPDefinitionLinkParameterTypes8 = new Class[] {
			long.class, double.class
		};
	private static final Class<?>[] _updateCPDefinitionLinksParameterTypes9 = new Class[] {
			long.class, long[].class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}