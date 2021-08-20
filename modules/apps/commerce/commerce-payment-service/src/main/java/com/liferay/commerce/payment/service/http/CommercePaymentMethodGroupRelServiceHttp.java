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

package com.liferay.commerce.payment.service.http;

import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePaymentMethodGroupRelServiceUtil</code> service
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
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelServiceSoap
 * @generated
 */
public class CommercePaymentMethodGroupRelServiceHttp {

	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				HttpPrincipal httpPrincipal, long groupId, long classPK,
				long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"addCommerceAddressRestriction",
				_addCommerceAddressRestrictionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classPK, countryId);

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

			return (com.liferay.commerce.model.CommerceAddressRestriction)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				HttpPrincipal httpPrincipal, long classPK, long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"addCommerceAddressRestriction",
				_addCommerceAddressRestrictionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classPK, countryId, serviceContext);

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

			return (com.liferay.commerce.model.CommerceAddressRestriction)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
				addCommercePaymentMethodGroupRel(
					HttpPrincipal httpPrincipal, long groupId,
					java.util.Map<java.util.Locale, String> nameMap,
					java.util.Map<java.util.Locale, String> descriptionMap,
					java.io.File imageFile, String engineKey, double priority,
					boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"addCommercePaymentMethodGroupRel",
				_addCommercePaymentMethodGroupRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, descriptionMap, imageFile,
				engineKey, priority, active);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceAddressRestriction(
			HttpPrincipal httpPrincipal, long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"deleteCommerceAddressRestriction",
				_deleteCommerceAddressRestrictionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAddressRestrictionId);

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

	public static void deleteCommerceAddressRestrictions(
			HttpPrincipal httpPrincipal, long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"deleteCommerceAddressRestrictions",
				_deleteCommerceAddressRestrictionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId);

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

	public static void deleteCommercePaymentMethodGroupRel(
			HttpPrincipal httpPrincipal, long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"deleteCommercePaymentMethodGroupRel",
				_deleteCommercePaymentMethodGroupRelParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId);

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

	public static
		com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
				fetchCommercePaymentMethodGroupRel(
					HttpPrincipal httpPrincipal, long groupId, String engineKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"fetchCommercePaymentMethodGroupRel",
				_fetchCommercePaymentMethodGroupRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, engineKey);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceAddressRestriction>
				getCommerceAddressRestrictions(
					HttpPrincipal httpPrincipal, long classPK, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.model.CommerceAddressRestriction>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommerceAddressRestrictions",
				_getCommerceAddressRestrictionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classPK, start, end, orderByComparator);

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
				<com.liferay.commerce.model.CommerceAddressRestriction>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceAddressRestrictionsCount(
			HttpPrincipal httpPrincipal, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommerceAddressRestrictionsCount",
				_getCommerceAddressRestrictionsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, classPK);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
				getCommercePaymentMethodGroupRel(
					HttpPrincipal httpPrincipal,
					long commercePaymentMethodGroupRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRel",
				_getCommercePaymentMethodGroupRelParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
				getCommercePaymentMethodGroupRel(
					HttpPrincipal httpPrincipal, long groupId, String engineKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRel",
				_getCommercePaymentMethodGroupRelParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, engineKey);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					HttpPrincipal httpPrincipal, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRels",
				_getCommercePaymentMethodGroupRelsParameterTypes11);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					HttpPrincipal httpPrincipal, long groupId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRels",
				_getCommercePaymentMethodGroupRelsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, active);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					HttpPrincipal httpPrincipal, long groupId, boolean active,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRels",
				_getCommercePaymentMethodGroupRelsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, active, start, end);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					HttpPrincipal httpPrincipal, long groupId, boolean active,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentMethodGroupRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRels",
				_getCommercePaymentMethodGroupRelsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, active, start, end, orderByComparator);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					HttpPrincipal httpPrincipal, long groupId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentMethodGroupRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRels",
				_getCommercePaymentMethodGroupRelsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					HttpPrincipal httpPrincipal, long groupId, long countryId,
					boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRels",
				_getCommercePaymentMethodGroupRelsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, countryId, active);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePaymentMethodGroupRelsCount(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRelsCount",
				_getCommercePaymentMethodGroupRelsCountParameterTypes17);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePaymentMethodGroupRelsCount(
			HttpPrincipal httpPrincipal, long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"getCommercePaymentMethodGroupRelsCount",
				_getCommercePaymentMethodGroupRelsCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, active);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
				setActive(
					HttpPrincipal httpPrincipal,
					long commercePaymentMethodGroupRelId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class, "setActive",
				_setActiveParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, active);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
				updateCommercePaymentMethodGroupRel(
					HttpPrincipal httpPrincipal,
					long commercePaymentMethodGroupRelId,
					java.util.Map<java.util.Locale, String> nameMap,
					java.util.Map<java.util.Locale, String> descriptionMap,
					java.io.File imageFile, double priority, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelServiceUtil.class,
				"updateCommercePaymentMethodGroupRel",
				_updateCommercePaymentMethodGroupRelParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, nameMap,
				descriptionMap, imageFile, priority, active);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePaymentMethodGroupRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceAddressRestrictionParameterTypes0 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[]
		_addCommerceAddressRestrictionParameterTypes1 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addCommercePaymentMethodGroupRelParameterTypes2 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.io.File.class, String.class, double.class, boolean.class
		};
	private static final Class<?>[]
		_deleteCommerceAddressRestrictionParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceAddressRestrictionsParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommercePaymentMethodGroupRelParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommercePaymentMethodGroupRelParameterTypes6 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCommerceAddressRestrictionsParameterTypes7 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceAddressRestrictionsCountParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelParameterTypes10 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsParameterTypes12 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsParameterTypes13 = new Class[] {
			long.class, boolean.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsParameterTypes14 = new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsParameterTypes15 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsParameterTypes16 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsCountParameterTypes17 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelsCountParameterTypes18 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setActiveParameterTypes19 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[]
		_updateCommercePaymentMethodGroupRelParameterTypes20 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.io.File.class, double.class, boolean.class
		};

}