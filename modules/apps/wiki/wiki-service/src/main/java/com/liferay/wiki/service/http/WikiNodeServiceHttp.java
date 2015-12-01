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

package com.liferay.wiki.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.wiki.service.WikiNodeServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link WikiNodeServiceUtil} service utility. The
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
 * @author Brian Wing Shun Chan
 * @see WikiNodeServiceSoap
 * @see HttpPrincipal
 * @see WikiNodeServiceUtil
 * @generated
 */
@ProviderType
public class WikiNodeServiceHttp {
	public static com.liferay.wiki.model.WikiNode addNode(
		HttpPrincipal httpPrincipal, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"addNode", _addNodeParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, name,
					description, serviceContext);

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

			return (com.liferay.wiki.model.WikiNode)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteNode(HttpPrincipal httpPrincipal, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"deleteNode", _deleteNodeParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId);

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

	public static com.liferay.wiki.model.WikiNode getNode(
		HttpPrincipal httpPrincipal, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNode", _getNodeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId);

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

			return (com.liferay.wiki.model.WikiNode)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiNode getNode(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNode", _getNodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name);

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

			return (com.liferay.wiki.model.WikiNode)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodes", _getNodesParameterTypes4);

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

			return (java.util.List<com.liferay.wiki.model.WikiNode>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		HttpPrincipal httpPrincipal, long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodes", _getNodesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status);

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

			return (java.util.List<com.liferay.wiki.model.WikiNode>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		HttpPrincipal httpPrincipal, long groupId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodes", _getNodesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.wiki.model.WikiNode>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		HttpPrincipal httpPrincipal, long groupId, int status, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodes", _getNodesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.wiki.model.WikiNode>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		HttpPrincipal httpPrincipal, long groupId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> obc) {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodes", _getNodesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.wiki.model.WikiNode>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getNodesCount(HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodesCount", _getNodesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static int getNodesCount(HttpPrincipal httpPrincipal, long groupId,
		int status) {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"getNodesCount", _getNodesCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status);

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

	public static void importPages(HttpPrincipal httpPrincipal, long nodeId,
		java.lang.String importer, java.io.InputStream[] inputStreams,
		java.util.Map<java.lang.String, java.lang.String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"importPages", _importPagesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId,
					importer, inputStreams, options);

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

	public static com.liferay.wiki.model.WikiNode moveNodeToTrash(
		HttpPrincipal httpPrincipal, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"moveNodeToTrash", _moveNodeToTrashParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId);

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

			return (com.liferay.wiki.model.WikiNode)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreNodeFromTrash(HttpPrincipal httpPrincipal,
		long nodeId) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"restoreNodeFromTrash",
					_restoreNodeFromTrashParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId);

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

	public static void subscribeNode(HttpPrincipal httpPrincipal, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"subscribeNode", _subscribeNodeParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId);

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

	public static void unsubscribeNode(HttpPrincipal httpPrincipal, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"unsubscribeNode", _unsubscribeNodeParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId);

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

	public static com.liferay.wiki.model.WikiNode updateNode(
		HttpPrincipal httpPrincipal, long nodeId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(WikiNodeServiceUtil.class,
					"updateNode", _updateNodeParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, nodeId,
					name, description, serviceContext);

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

			return (com.liferay.wiki.model.WikiNode)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WikiNodeServiceHttp.class);
	private static final Class<?>[] _addNodeParameterTypes0 = new Class[] {
			java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteNodeParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getNodeParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getNodeParameterTypes3 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getNodesParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getNodesParameterTypes5 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getNodesParameterTypes6 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getNodesParameterTypes7 = new Class[] {
			long.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _getNodesParameterTypes8 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getNodesCountParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getNodesCountParameterTypes10 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _importPagesParameterTypes11 = new Class[] {
			long.class, java.lang.String.class, java.io.InputStream[].class,
			java.util.Map.class
		};
	private static final Class<?>[] _moveNodeToTrashParameterTypes12 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreNodeFromTrashParameterTypes13 = new Class[] {
			long.class
		};
	private static final Class<?>[] _subscribeNodeParameterTypes14 = new Class[] {
			long.class
		};
	private static final Class<?>[] _unsubscribeNodeParameterTypes15 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateNodeParameterTypes16 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
}