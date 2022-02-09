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

package com.liferay.segments.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.segments.service.SegmentsExperimentServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SegmentsExperimentServiceUtil</code> service
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
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperimentServiceHttp {

	public static com.liferay.segments.model.SegmentsExperiment
			addSegmentsExperiment(
				HttpPrincipal httpPrincipal, long segmentsExperienceId,
				long classNameId, long classPK, String name, String description,
				String goal, String goalTarget,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "addSegmentsExperiment",
				_addSegmentsExperimentParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId, classNameId, classPK, name,
				description, goal, goalTarget, serviceContext);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(
				HttpPrincipal httpPrincipal, long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "deleteSegmentsExperiment",
				_deleteSegmentsExperimentParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(
				HttpPrincipal httpPrincipal, String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "deleteSegmentsExperiment",
				_deleteSegmentsExperimentParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentKey);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			fetchSegmentsExperiment(
				HttpPrincipal httpPrincipal, long segmentsExperienceId,
				long classNameId, long classPK, int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "fetchSegmentsExperiment",
				_fetchSegmentsExperimentParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId, classNameId, classPK,
				statuses);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			fetchSegmentsExperiment(
				HttpPrincipal httpPrincipal, long groupId,
				String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "fetchSegmentsExperiment",
				_fetchSegmentsExperimentParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, segmentsExperimentKey);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
			getSegmentsExperienceSegmentsExperiments(
				HttpPrincipal httpPrincipal, long[] segmentsExperienceIds,
				long classNameId, long classPK, int[] statuses, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class,
				"getSegmentsExperienceSegmentsExperiments",
				_getSegmentsExperienceSegmentsExperimentsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceIds, classNameId, classPK,
				statuses, start, end);

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
				<com.liferay.segments.model.SegmentsExperiment>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperiment(
				HttpPrincipal httpPrincipal, long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "getSegmentsExperiment",
				_getSegmentsExperimentParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperiment(
				HttpPrincipal httpPrincipal, String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "getSegmentsExperiment",
				_getSegmentsExperimentParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentKey);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(
			HttpPrincipal httpPrincipal, long groupId, long classNameId,
			long classPK) {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "getSegmentsExperiments",
				_getSegmentsExperimentsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.segments.model.SegmentsExperiment>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(
			HttpPrincipal httpPrincipal, long segmentsExperienceId,
			long classNameId, long classPK, int[] statuses,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsExperiment>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "getSegmentsExperiments",
				_getSegmentsExperimentsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId, classNameId, classPK, statuses,
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
				<com.liferay.segments.model.SegmentsExperiment>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			runSegmentsExperiment(
				HttpPrincipal httpPrincipal, long segmentsExperimentId,
				double confidenceLevel,
				java.util.Map<Long, Double> segmentsExperienceIdSplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "runSegmentsExperiment",
				_runSegmentsExperimentParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId, confidenceLevel,
				segmentsExperienceIdSplitMap);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			runSegmentsExperiment(
				HttpPrincipal httpPrincipal, String segmentsExperimentKey,
				double confidenceLevel,
				java.util.Map<String, Double> segmentsExperienceKeySplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "runSegmentsExperiment",
				_runSegmentsExperimentParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentKey, confidenceLevel,
				segmentsExperienceKeySplitMap);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperiment(
				HttpPrincipal httpPrincipal, long segmentsExperimentId,
				String name, String description, String goal, String goalTarget)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class, "updateSegmentsExperiment",
				_updateSegmentsExperimentParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId, name, description, goal,
				goalTarget);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				HttpPrincipal httpPrincipal, long segmentsExperimentId,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class,
				"updateSegmentsExperimentStatus",
				_updateSegmentsExperimentStatusParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId, status);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				HttpPrincipal httpPrincipal, long segmentsExperimentId,
				long winnerSegmentsExperienceId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class,
				"updateSegmentsExperimentStatus",
				_updateSegmentsExperimentStatusParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId, winnerSegmentsExperienceId,
				status);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				HttpPrincipal httpPrincipal, String segmentsExperimentKey,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class,
				"updateSegmentsExperimentStatus",
				_updateSegmentsExperimentStatusParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentKey, status);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				HttpPrincipal httpPrincipal, String segmentsExperimentKey,
				String winnerSegmentsExperienceKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentServiceUtil.class,
				"updateSegmentsExperimentStatus",
				_updateSegmentsExperimentStatusParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentKey, winnerSegmentsExperienceKey,
				status);

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

			return (com.liferay.segments.model.SegmentsExperiment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentServiceHttp.class);

	private static final Class<?>[] _addSegmentsExperimentParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSegmentsExperimentParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteSegmentsExperimentParameterTypes2 =
		new Class[] {String.class};
	private static final Class<?>[] _fetchSegmentsExperimentParameterTypes3 =
		new Class[] {long.class, long.class, long.class, int[].class};
	private static final Class<?>[] _fetchSegmentsExperimentParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getSegmentsExperienceSegmentsExperimentsParameterTypes5 = new Class[] {
			long[].class, long.class, long.class, int[].class, int.class,
			int.class
		};
	private static final Class<?>[] _getSegmentsExperimentParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getSegmentsExperimentParameterTypes7 =
		new Class[] {String.class};
	private static final Class<?>[] _getSegmentsExperimentsParameterTypes8 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getSegmentsExperimentsParameterTypes9 =
		new Class[] {
			long.class, long.class, long.class, int[].class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _runSegmentsExperimentParameterTypes10 =
		new Class[] {long.class, double.class, java.util.Map.class};
	private static final Class<?>[] _runSegmentsExperimentParameterTypes11 =
		new Class[] {String.class, double.class, java.util.Map.class};
	private static final Class<?>[] _updateSegmentsExperimentParameterTypes12 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class
		};
	private static final Class<?>[]
		_updateSegmentsExperimentStatusParameterTypes13 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[]
		_updateSegmentsExperimentStatusParameterTypes14 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_updateSegmentsExperimentStatusParameterTypes15 = new Class[] {
			String.class, int.class
		};
	private static final Class<?>[]
		_updateSegmentsExperimentStatusParameterTypes16 = new Class[] {
			String.class, String.class, int.class
		};

}