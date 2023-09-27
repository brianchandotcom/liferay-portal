/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.http;

import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>BatchPlannerPlanServiceUtil</code> service
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
 * @author Igor Beslic
 * @generated
 */
public class BatchPlannerPlanServiceHttp {

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			addBatchPlannerPlan(
				HttpPrincipal httpPrincipal, boolean export,
				String externalType, String externalURL,
				String internalClassName, String name, int size,
				String taskItemDelegateName, boolean template)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "addBatchPlannerPlan",
				_addBatchPlannerPlanParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, export, externalType, externalURL, internalClassName,
				name, size, taskItemDelegateName, template);

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

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			deleteBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "deleteBatchPlannerPlan",
				_deleteBatchPlannerPlanParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

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

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			fetchBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "fetchBatchPlannerPlan",
				_fetchBatchPlannerPlanParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

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

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			getBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlan",
				_getBatchPlannerPlanParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

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

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPlan> getBatchPlannerPlans(
			HttpPrincipal httpPrincipal, long companyId, boolean export,
			boolean template, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerPlan>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlans",
				_getBatchPlannerPlansParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, template, start, end,
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
				<com.liferay.batch.planner.model.BatchPlannerPlan>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPlan> getBatchPlannerPlans(
				HttpPrincipal httpPrincipal, long companyId, boolean export,
				boolean template, String searchByKeyword, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlans",
				_getBatchPlannerPlansParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, template, searchByKeyword, start,
				end, orderByComparator);

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
				<com.liferay.batch.planner.model.BatchPlannerPlan>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPlan> getBatchPlannerPlans(
			HttpPrincipal httpPrincipal, long companyId, boolean template,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerPlan>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlans",
				_getBatchPlannerPlansParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, template, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.batch.planner.model.BatchPlannerPlan>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPlan> getBatchPlannerPlans(
				HttpPrincipal httpPrincipal, long companyId, boolean template,
				String searchByKeyword, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlans",
				_getBatchPlannerPlansParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, template, searchByKeyword, start, end,
				orderByComparator);

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
				<com.liferay.batch.planner.model.BatchPlannerPlan>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPlan> getBatchPlannerPlans(
			HttpPrincipal httpPrincipal, long companyId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlans",
				_getBatchPlannerPlansParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.batch.planner.model.BatchPlannerPlan>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPlan> getBatchPlannerPlans(
			HttpPrincipal httpPrincipal, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerPlan>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlans",
				_getBatchPlannerPlansParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.batch.planner.model.BatchPlannerPlan>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getBatchPlannerPlansCount(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlansCount",
				_getBatchPlannerPlansCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static int getBatchPlannerPlansCount(
		HttpPrincipal httpPrincipal, long companyId, boolean template) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlansCount",
				_getBatchPlannerPlansCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, template);

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

	public static int getBatchPlannerPlansCount(
		HttpPrincipal httpPrincipal, long companyId, boolean export,
		boolean template) {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlansCount",
				_getBatchPlannerPlansCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, template);

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

	public static int getBatchPlannerPlansCount(
			HttpPrincipal httpPrincipal, long companyId, boolean export,
			boolean template, String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlansCount",
				_getBatchPlannerPlansCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, template, searchByKeyword);

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

	public static int getBatchPlannerPlansCount(
			HttpPrincipal httpPrincipal, long companyId, boolean template,
			String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlansCount",
				_getBatchPlannerPlansCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, template, searchByKeyword);

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

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			updateBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String externalType, String internalClassName, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "updateBatchPlannerPlan",
				_updateBatchPlannerPlanParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, externalType, internalClassName,
				name);

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

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BatchPlannerPlanServiceHttp.class);

	private static final Class<?>[] _addBatchPlannerPlanParameterTypes0 =
		new Class[] {
			boolean.class, String.class, String.class, String.class,
			String.class, int.class, String.class, boolean.class
		};
	private static final Class<?>[] _deleteBatchPlannerPlanParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchBatchPlannerPlanParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getBatchPlannerPlanParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getBatchPlannerPlansParameterTypes4 =
		new Class[] {
			long.class, boolean.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getBatchPlannerPlansParameterTypes5 =
		new Class[] {
			long.class, boolean.class, boolean.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getBatchPlannerPlansParameterTypes6 =
		new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getBatchPlannerPlansParameterTypes7 =
		new Class[] {
			long.class, boolean.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getBatchPlannerPlansParameterTypes8 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getBatchPlannerPlansParameterTypes9 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getBatchPlannerPlansCountParameterTypes10 =
		new Class[] {long.class};
	private static final Class<?>[] _getBatchPlannerPlansCountParameterTypes11 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getBatchPlannerPlansCountParameterTypes12 =
		new Class[] {long.class, boolean.class, boolean.class};
	private static final Class<?>[] _getBatchPlannerPlansCountParameterTypes13 =
		new Class[] {long.class, boolean.class, boolean.class, String.class};
	private static final Class<?>[] _getBatchPlannerPlansCountParameterTypes14 =
		new Class[] {long.class, boolean.class, String.class};
	private static final Class<?>[] _updateBatchPlannerPlanParameterTypes15 =
		new Class[] {long.class, String.class, String.class, String.class};

}