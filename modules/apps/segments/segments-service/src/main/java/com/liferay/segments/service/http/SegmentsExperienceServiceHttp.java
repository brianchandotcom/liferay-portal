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
import com.liferay.segments.service.SegmentsExperienceServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SegmentsExperienceServiceUtil</code> service
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
 * @see SegmentsExperienceServiceSoap
 * @generated
 */
public class SegmentsExperienceServiceHttp {

	public static com.liferay.segments.model.SegmentsExperience
			addSegmentsExperience(
				HttpPrincipal httpPrincipal, long segmentsEntryId,
				long classNameId, long classPK,
				java.util.Map<java.util.Locale, String> nameMap, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "addSegmentsExperience",
				_addSegmentsExperienceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsEntryId, classNameId, classPK, nameMap,
				active, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.segments.model.SegmentsExperience)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsExperience
			deleteSegmentsExperience(
				HttpPrincipal httpPrincipal, long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "deleteSegmentsExperience",
				_deleteSegmentsExperienceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.segments.model.SegmentsExperience)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsExperience
			fetchSegmentsExperience(
				HttpPrincipal httpPrincipal, long groupId,
				String segmentsExperienceKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "fetchSegmentsExperience",
				_fetchSegmentsExperienceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, segmentsExperienceKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.segments.model.SegmentsExperience)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsExperience
			getSegmentsExperience(
				HttpPrincipal httpPrincipal, long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "getSegmentsExperience",
				_getSegmentsExperienceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.segments.model.SegmentsExperience)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperience>
			getSegmentsExperiences(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "getSegmentsExperiences",
				_getSegmentsExperiencesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.segments.model.SegmentsExperience>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperience>
			getSegmentsExperiences(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsExperience>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "getSegmentsExperiences",
				_getSegmentsExperiencesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK, active, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.segments.model.SegmentsExperience>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSegmentsExperiencesCount(
			HttpPrincipal httpPrincipal, long groupId, long classNameId,
			long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class,
				"getSegmentsExperiencesCount",
				_getSegmentsExperiencesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.segments.model.SegmentsExperience
			updateSegmentsExperience(
				HttpPrincipal httpPrincipal, long segmentsExperienceId,
				long segmentsEntryId,
				java.util.Map<java.util.Locale, String> nameMap, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class, "updateSegmentsExperience",
				_updateSegmentsExperienceParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId, segmentsEntryId, nameMap,
				active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.segments.model.SegmentsExperience)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateSegmentsExperiencePriority(
			HttpPrincipal httpPrincipal, long segmentsExperienceId,
			int newPriority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperienceServiceUtil.class,
				"updateSegmentsExperiencePriority",
				_updateSegmentsExperiencePriorityParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperienceId, newPriority);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceServiceHttp.class);

	private static final Class<?>[] _addSegmentsExperienceParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, java.util.Map.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSegmentsExperienceParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchSegmentsExperienceParameterTypes2 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getSegmentsExperienceParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getSegmentsExperiencesParameterTypes4 =
		new Class[] {long.class, long.class, long.class, boolean.class};
	private static final Class<?>[] _getSegmentsExperiencesParameterTypes5 =
		new Class[] {
			long.class, long.class, long.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getSegmentsExperiencesCountParameterTypes6 = new Class[] {
			long.class, long.class, long.class, boolean.class
		};
	private static final Class<?>[] _updateSegmentsExperienceParameterTypes7 =
		new Class[] {
			long.class, long.class, java.util.Map.class, boolean.class
		};
	private static final Class<?>[]
		_updateSegmentsExperiencePriorityParameterTypes8 = new Class[] {
			long.class, int.class
		};

}