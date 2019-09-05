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

package com.liferay.knowledge.base.service.http;

import com.liferay.knowledge.base.service.KBCommentServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>KBCommentServiceUtil</code> service
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
 * @see KBCommentServiceSoap
 * @generated
 */
public class KBCommentServiceHttp {

	public static com.liferay.knowledge.base.model.KBComment deleteKBComment(
			HttpPrincipal httpPrincipal,
			com.liferay.knowledge.base.model.KBComment kbComment)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "deleteKBComment",
				_deleteKBCommentParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbComment);

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

			return (com.liferay.knowledge.base.model.KBComment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBComment deleteKBComment(
			HttpPrincipal httpPrincipal, long kbCommentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "deleteKBComment",
				_deleteKBCommentParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbCommentId);

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

			return (com.liferay.knowledge.base.model.KBComment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBComment getKBComment(
			HttpPrincipal httpPrincipal, long kbCommentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComment",
				_getKBCommentParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbCommentId);

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

			return (com.liferay.knowledge.base.model.KBComment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment>
			getKBComments(
				HttpPrincipal httpPrincipal, long groupId, int status,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComments",
				_getKBCommentsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, start, end);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBComment>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment>
			getKBComments(
				HttpPrincipal httpPrincipal, long groupId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComments",
				_getKBCommentsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, start, end, obc);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBComment>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment>
			getKBComments(
				HttpPrincipal httpPrincipal, long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComments",
				_getKBCommentsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, obc);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBComment>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment>
			getKBComments(
				HttpPrincipal httpPrincipal, long groupId, String className,
				long classPK, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComments",
				_getKBCommentsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, status, start, end);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBComment>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment>
			getKBComments(
				HttpPrincipal httpPrincipal, long groupId, String className,
				long classPK, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComments",
				_getKBCommentsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, status, start, end,
				obc);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBComment>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment>
			getKBComments(
				HttpPrincipal httpPrincipal, long groupId, String className,
				long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBComments",
				_getKBCommentsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, start, end, obc);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBComment>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getKBCommentsCount(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBCommentsCount",
				_getKBCommentsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static int getKBCommentsCount(
			HttpPrincipal httpPrincipal, long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBCommentsCount",
				_getKBCommentsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

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

	public static int getKBCommentsCount(
			HttpPrincipal httpPrincipal, long groupId, String className,
			long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBCommentsCount",
				_getKBCommentsCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK);

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

	public static int getKBCommentsCount(
			HttpPrincipal httpPrincipal, long groupId, String className,
			long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "getKBCommentsCount",
				_getKBCommentsCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, status);

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

	public static com.liferay.knowledge.base.model.KBComment updateKBComment(
			HttpPrincipal httpPrincipal, long kbCommentId, long classNameId,
			long classPK, String content, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "updateKBComment",
				_updateKBCommentParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbCommentId, classNameId, classPK, content, status,
				serviceContext);

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

			return (com.liferay.knowledge.base.model.KBComment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBComment updateKBComment(
			HttpPrincipal httpPrincipal, long kbCommentId, long classNameId,
			long classPK, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "updateKBComment",
				_updateKBCommentParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbCommentId, classNameId, classPK, content,
				serviceContext);

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

			return (com.liferay.knowledge.base.model.KBComment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBComment updateStatus(
			HttpPrincipal httpPrincipal, long kbCommentId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBCommentServiceUtil.class, "updateStatus",
				_updateStatusParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbCommentId, status, serviceContext);

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

			return (com.liferay.knowledge.base.model.KBComment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(KBCommentServiceHttp.class);

	private static final Class<?>[] _deleteKBCommentParameterTypes0 =
		new Class[] {com.liferay.knowledge.base.model.KBComment.class};
	private static final Class<?>[] _deleteKBCommentParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getKBCommentParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getKBCommentsParameterTypes3 =
		new Class[] {long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getKBCommentsParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBCommentsParameterTypes5 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBCommentsParameterTypes6 =
		new Class[] {
			long.class, String.class, long.class, int.class, int.class,
			int.class
		};
	private static final Class<?>[] _getKBCommentsParameterTypes7 =
		new Class[] {
			long.class, String.class, long.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBCommentsParameterTypes8 =
		new Class[] {
			long.class, String.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBCommentsCountParameterTypes9 =
		new Class[] {long.class};
	private static final Class<?>[] _getKBCommentsCountParameterTypes10 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getKBCommentsCountParameterTypes11 =
		new Class[] {long.class, String.class, long.class};
	private static final Class<?>[] _getKBCommentsCountParameterTypes12 =
		new Class[] {long.class, String.class, long.class, int.class};
	private static final Class<?>[] _updateKBCommentParameterTypes13 =
		new Class[] {
			long.class, long.class, long.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateKBCommentParameterTypes14 =
		new Class[] {
			long.class, long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateStatusParameterTypes15 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}