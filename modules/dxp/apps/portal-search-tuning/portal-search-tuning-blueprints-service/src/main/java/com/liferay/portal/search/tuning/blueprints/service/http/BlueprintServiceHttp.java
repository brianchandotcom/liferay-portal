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

package com.liferay.portal.search.tuning.blueprints.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>BlueprintServiceUtil</code> service
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
 * @see BlueprintServiceSoap
 * @generated
 */
public class BlueprintServiceHttp {

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addCompanyBlueprint(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "addCompanyBlueprint",
				_addCompanyBlueprintParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, titleMap, descriptionMap, configuration,
				selectedElements, type, serviceContext);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Blueprint)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addGroupBlueprint(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "addGroupBlueprint",
				_addGroupBlueprintParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, titleMap, descriptionMap, configuration,
				selectedElements, type, serviceContext);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Blueprint)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			deleteBlueprint(HttpPrincipal httpPrincipal, long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "deleteBlueprint",
				_deleteBlueprintParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, blueprintId);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Blueprint)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			getBlueprint(HttpPrincipal httpPrincipal, long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getBlueprint",
				_getBlueprintParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, blueprintId);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Blueprint)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				HttpPrincipal httpPrincipal, long companyId, int type,
				int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getGroupBlueprints",
				_getGroupBlueprintsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				HttpPrincipal httpPrincipal, long companyId, int status,
				int type, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getGroupBlueprints",
				_getGroupBlueprintsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, type, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				HttpPrincipal httpPrincipal, long companyId, int status,
				int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getGroupBlueprints",
				_getGroupBlueprintsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, type, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				HttpPrincipal httpPrincipal, long companyId, int type,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getGroupBlueprints",
				_getGroupBlueprintsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupBlueprintsCount(
		HttpPrincipal httpPrincipal, long companyId, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getGroupBlueprintsCount",
				_getGroupBlueprintsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

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

	public static int getGroupBlueprintsCount(
		HttpPrincipal httpPrincipal, long companyId, int status, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "getGroupBlueprintsCount",
				_getGroupBlueprintsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, type);

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

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			updateBlueprint(
				HttpPrincipal httpPrincipal, long blueprintId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BlueprintServiceUtil.class, "updateBlueprint",
				_updateBlueprintParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, blueprintId, titleMap, descriptionMap, configuration,
				selectedElements, serviceContext);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Blueprint)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BlueprintServiceHttp.class);

	private static final Class<?>[] _addCompanyBlueprintParameterTypes0 =
		new Class[] {
			java.util.Map.class, java.util.Map.class, String.class,
			String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addGroupBlueprintParameterTypes1 =
		new Class[] {
			java.util.Map.class, java.util.Map.class, String.class,
			String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteBlueprintParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getBlueprintParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getGroupBlueprintsParameterTypes4 =
		new Class[] {long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getGroupBlueprintsParameterTypes5 =
		new Class[] {long.class, int.class, int.class, int.class, int.class};
	private static final Class<?>[] _getGroupBlueprintsParameterTypes6 =
		new Class[] {
			long.class, int.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupBlueprintsParameterTypes7 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupBlueprintsCountParameterTypes8 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getGroupBlueprintsCountParameterTypes9 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _updateBlueprintParameterTypes10 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, String.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};

}