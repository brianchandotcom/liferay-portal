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

package com.liferay.portlet.social.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.social.kernel.service.SocialActivityServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SocialActivityServiceUtil</code> service
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
 * @see SocialActivityServiceSoap
 * @generated
 */
public class SocialActivityServiceHttp {

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(
				HttpPrincipal httpPrincipal, long classNameId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivities",
				_getActivitiesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(
				HttpPrincipal httpPrincipal, long mirrorActivityId,
				long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivities",
				_getActivitiesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, mirrorActivityId, classNameId, classPK, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(
				HttpPrincipal httpPrincipal, long mirrorActivityId,
				String className, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivities",
				_getActivitiesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, mirrorActivityId, className, classPK, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(
				HttpPrincipal httpPrincipal, String className, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivities",
				_getActivitiesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getActivitiesCount(
		HttpPrincipal httpPrincipal, long classNameId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivitiesCount",
				_getActivitiesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static int getActivitiesCount(
		HttpPrincipal httpPrincipal, long mirrorActivityId, long classNameId,
		long classPK) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivitiesCount",
				_getActivitiesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, mirrorActivityId, classNameId, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static int getActivitiesCount(
		HttpPrincipal httpPrincipal, long mirrorActivityId, String className,
		long classPK) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivitiesCount",
				_getActivitiesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, mirrorActivityId, className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static int getActivitiesCount(
		HttpPrincipal httpPrincipal, String className) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivitiesCount",
				_getActivitiesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static com.liferay.social.kernel.model.SocialActivity getActivity(
			HttpPrincipal httpPrincipal, long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivity",
				_getActivityParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, activityId);

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

			return (com.liferay.social.kernel.model.SocialActivity)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivitySetActivities(
				HttpPrincipal httpPrincipal, long activitySetId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getActivitySetActivities",
				_getActivitySetActivitiesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, activitySetId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getGroupActivities(
				HttpPrincipal httpPrincipal, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getGroupActivities",
				_getGroupActivitiesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupActivitiesCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getGroupActivitiesCount",
				_getGroupActivitiesCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getGroupUsersActivities(
				HttpPrincipal httpPrincipal, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getGroupUsersActivities",
				_getGroupUsersActivitiesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupUsersActivitiesCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getGroupUsersActivitiesCount",
				_getGroupUsersActivitiesCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static com.liferay.social.kernel.model.SocialActivity
			getMirrorActivity(
				HttpPrincipal httpPrincipal, long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getMirrorActivity",
				_getMirrorActivityParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, mirrorActivityId);

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

			return (com.liferay.social.kernel.model.SocialActivity)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getOrganizationActivities(
				HttpPrincipal httpPrincipal, long organizationId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getOrganizationActivities",
				_getOrganizationActivitiesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOrganizationActivitiesCount(
		HttpPrincipal httpPrincipal, long organizationId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getOrganizationActivitiesCount",
				_getOrganizationActivitiesCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getOrganizationUsersActivities(
				HttpPrincipal httpPrincipal, long organizationId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getOrganizationUsersActivities",
				_getOrganizationUsersActivitiesParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOrganizationUsersActivitiesCount(
		HttpPrincipal httpPrincipal, long organizationId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getOrganizationUsersActivitiesCount",
				_getOrganizationUsersActivitiesCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getRelationActivities(
				HttpPrincipal httpPrincipal, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getRelationActivities",
				_getRelationActivitiesParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getRelationActivities(
				HttpPrincipal httpPrincipal, long userId, int type, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getRelationActivities",
				_getRelationActivitiesParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, type, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRelationActivitiesCount(
		HttpPrincipal httpPrincipal, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getRelationActivitiesCount",
				_getRelationActivitiesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static int getRelationActivitiesCount(
		HttpPrincipal httpPrincipal, long userId, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getRelationActivitiesCount",
				_getRelationActivitiesCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserActivities(
				HttpPrincipal httpPrincipal, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getUserActivities",
				_getUserActivitiesParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getUserActivitiesCount(
		HttpPrincipal httpPrincipal, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getUserActivitiesCount",
				_getUserActivitiesCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserGroupsActivities(
				HttpPrincipal httpPrincipal, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getUserGroupsActivities",
				_getUserGroupsActivitiesParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getUserGroupsActivitiesCount(
		HttpPrincipal httpPrincipal, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class, "getUserGroupsActivitiesCount",
				_getUserGroupsActivitiesCountParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserGroupsAndOrganizationsActivities(
				HttpPrincipal httpPrincipal, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getUserGroupsAndOrganizationsActivities",
				_getUserGroupsAndOrganizationsActivitiesParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getUserGroupsAndOrganizationsActivitiesCount(
		HttpPrincipal httpPrincipal, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getUserGroupsAndOrganizationsActivitiesCount",
				_getUserGroupsAndOrganizationsActivitiesCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserOrganizationsActivities(
				HttpPrincipal httpPrincipal, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getUserOrganizationsActivities",
				_getUserOrganizationsActivitiesParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

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
				<com.liferay.social.kernel.model.SocialActivity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getUserOrganizationsActivitiesCount(
		HttpPrincipal httpPrincipal, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivityServiceUtil.class,
				"getUserOrganizationsActivitiesCount",
				_getUserOrganizationsActivitiesCountParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivityServiceHttp.class);

	private static final Class<?>[] _getActivitiesParameterTypes0 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getActivitiesParameterTypes1 =
		new Class[] {long.class, long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getActivitiesParameterTypes2 =
		new Class[] {
			long.class, String.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getActivitiesParameterTypes3 =
		new Class[] {String.class, int.class, int.class};
	private static final Class<?>[] _getActivitiesCountParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getActivitiesCountParameterTypes5 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getActivitiesCountParameterTypes6 =
		new Class[] {long.class, String.class, long.class};
	private static final Class<?>[] _getActivitiesCountParameterTypes7 =
		new Class[] {String.class};
	private static final Class<?>[] _getActivityParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getActivitySetActivitiesParameterTypes9 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getGroupActivitiesParameterTypes10 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getGroupActivitiesCountParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupUsersActivitiesParameterTypes12 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getGroupUsersActivitiesCountParameterTypes13 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getMirrorActivityParameterTypes14 =
		new Class[] {long.class};
	private static final Class<?>[] _getOrganizationActivitiesParameterTypes15 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getOrganizationActivitiesCountParameterTypes16 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getOrganizationUsersActivitiesParameterTypes17 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getOrganizationUsersActivitiesCountParameterTypes18 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getRelationActivitiesParameterTypes19 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getRelationActivitiesParameterTypes20 =
		new Class[] {long.class, int.class, int.class, int.class};
	private static final Class<?>[]
		_getRelationActivitiesCountParameterTypes21 = new Class[] {long.class};
	private static final Class<?>[]
		_getRelationActivitiesCountParameterTypes22 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getUserActivitiesParameterTypes23 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getUserActivitiesCountParameterTypes24 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserGroupsActivitiesParameterTypes25 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getUserGroupsActivitiesCountParameterTypes26 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getUserGroupsAndOrganizationsActivitiesParameterTypes27 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getUserGroupsAndOrganizationsActivitiesCountParameterTypes28 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getUserOrganizationsActivitiesParameterTypes29 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getUserOrganizationsActivitiesCountParameterTypes30 = new Class[] {
			long.class
		};

}