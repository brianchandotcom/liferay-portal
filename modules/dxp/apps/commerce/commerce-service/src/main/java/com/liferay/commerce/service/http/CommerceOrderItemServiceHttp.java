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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceOrderItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceOrderItemServiceUtil} service utility. The
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
 * @see CommerceOrderItemServiceSoap
 * @see HttpPrincipal
 * @see CommerceOrderItemServiceUtil
 * @generated
 */
@ProviderType
public class CommerceOrderItemServiceHttp {
	public static com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		HttpPrincipal httpPrincipal, long commerceOrderId, long cpInstanceId,
		int quantity, int shippedQuantity, String json,
		java.math.BigDecimal price,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"addCommerceOrderItem", _addCommerceOrderItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId, cpInstanceId, quantity, shippedQuantity,
					json, price, commerceContext, serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceOrderItem(HttpPrincipal httpPrincipal,
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"deleteCommerceOrderItem",
					_deleteCommerceOrderItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId);

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

	public static com.liferay.commerce.model.CommerceOrderItem fetchCommerceOrderItem(
		HttpPrincipal httpPrincipal, long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"fetchCommerceOrderItem",
					_fetchCommerceOrderItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId);

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

			return (com.liferay.commerce.model.CommerceOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getAvailableForShipmentCommerceOrderItems(
		HttpPrincipal httpPrincipal, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"getAvailableForShipmentCommerceOrderItems",
					_getAvailableForShipmentCommerceOrderItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrderItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem(
		HttpPrincipal httpPrincipal, long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"getCommerceOrderItem", _getCommerceOrderItemParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId);

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

			return (com.liferay.commerce.model.CommerceOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		HttpPrincipal httpPrincipal, long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"getCommerceOrderItems",
					_getCommerceOrderItemsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrderItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceOrderItemsCount(HttpPrincipal httpPrincipal,
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"getCommerceOrderItemsCount",
					_getCommerceOrderItemsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId);

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

	public static int getCommerceOrderItemsQuantity(
		HttpPrincipal httpPrincipal, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"getCommerceOrderItemsQuantity",
					_getCommerceOrderItemsQuantityParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId);

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

	public static int getCommerceWarehouseItemQuantity(
		HttpPrincipal httpPrincipal, long commerceOrderItemId,
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"getCommerceWarehouseItemQuantity",
					_getCommerceWarehouseItemQuantityParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId, commerceWarehouseId);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		HttpPrincipal httpPrincipal, long commerceOrderId, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"search", _searchParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId, keywords, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		HttpPrincipal httpPrincipal, long commerceOrderId, String sku,
		String name, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"search", _searchParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderId, sku, name, andOperator, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		HttpPrincipal httpPrincipal, long commerceOrderItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"updateCommerceOrderItem",
					_updateCommerceOrderItemParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId, quantity);

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

			return (com.liferay.commerce.model.CommerceOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		HttpPrincipal httpPrincipal, long commerceOrderItemId, int quantity,
		String json, java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceOrderItemServiceUtil.class,
					"updateCommerceOrderItem",
					_updateCommerceOrderItemParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId, quantity, json, price);

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

			return (com.liferay.commerce.model.CommerceOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceOrderItemServiceHttp.class);
	private static final Class<?>[] _addCommerceOrderItemParameterTypes0 = new Class[] {
			long.class, long.class, int.class, int.class, String.class,
			java.math.BigDecimal.class,
			com.liferay.commerce.context.CommerceContext.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceOrderItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceOrderItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getAvailableForShipmentCommerceOrderItemsParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceOrderItemParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceOrderItemsParameterTypes5 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCommerceOrderItemsCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceOrderItemsQuantityParameterTypes7 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceWarehouseItemQuantityParameterTypes8 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _searchParameterTypes9 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _searchParameterTypes10 = new Class[] {
			long.class, String.class, String.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCommerceOrderItemParameterTypes11 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _updateCommerceOrderItemParameterTypes12 = new Class[] {
			long.class, int.class, String.class, java.math.BigDecimal.class
		};
}