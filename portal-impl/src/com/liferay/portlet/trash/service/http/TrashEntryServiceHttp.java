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

package com.liferay.portlet.trash.service.http;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.trash.kernel.service.TrashEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link TrashEntryServiceUtil} service utility. The
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
 * @see TrashEntryServiceSoap
 * @see HttpPrincipal
 * @see TrashEntryServiceUtil
 * @deprecated As of Judson (7.1.x), replaced by {@link
           com.liferay.trash.service.impl.TrashEntryServiceImpl}
 * @generated
 */
@Deprecated
public class TrashEntryServiceHttp {
	public static void deleteEntries(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"deleteEntries", _deleteEntriesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static void deleteEntries(HttpPrincipal httpPrincipal,
		long[] entryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"deleteEntries", _deleteEntriesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryIds);

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

	public static void deleteEntry(HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"deleteEntry", _deleteEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

	public static void deleteEntry(HttpPrincipal httpPrincipal,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"deleteEntry", _deleteEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

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

	public static com.liferay.trash.kernel.model.TrashEntryList getEntries(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"getEntries", _getEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.security.auth.PrincipalException) {
					throw (com.liferay.portal.kernel.security.auth.PrincipalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.trash.kernel.model.TrashEntryList)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.trash.kernel.model.TrashEntryList getEntries(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.trash.kernel.model.TrashEntry> obc)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"getEntries", _getEntriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.security.auth.PrincipalException) {
					throw (com.liferay.portal.kernel.security.auth.PrincipalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.trash.kernel.model.TrashEntryList)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.trash.kernel.model.TrashEntry> getEntries(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"getEntries", _getEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.security.auth.PrincipalException) {
					throw (com.liferay.portal.kernel.security.auth.PrincipalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.trash.kernel.model.TrashEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.trash.kernel.model.TrashEntryList getEntries(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.trash.kernel.model.TrashEntry> obc)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"getEntries", _getEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.security.auth.PrincipalException) {
					throw (com.liferay.portal.kernel.security.auth.PrincipalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.trash.kernel.model.TrashEntryList)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void moveEntry(HttpPrincipal httpPrincipal,
		java.lang.String className, long classPK,
		long destinationContainerModelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"moveEntry", _moveEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, destinationContainerModelId,
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

	public static com.liferay.trash.kernel.model.TrashEntry restoreEntry(
		HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"restoreEntry", _restoreEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

			return (com.liferay.trash.kernel.model.TrashEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.trash.kernel.model.TrashEntry restoreEntry(
		HttpPrincipal httpPrincipal, long entryId, long overrideClassPK,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"restoreEntry", _restoreEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId,
					overrideClassPK, name);

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

			return (com.liferay.trash.kernel.model.TrashEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.trash.kernel.model.TrashEntry restoreEntry(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"restoreEntry", _restoreEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

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

			return (com.liferay.trash.kernel.model.TrashEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.trash.kernel.model.TrashEntry restoreEntry(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK,
		long overrideClassPK, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(TrashEntryServiceUtil.class,
					"restoreEntry", _restoreEntryParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, overrideClassPK, name);

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

			return (com.liferay.trash.kernel.model.TrashEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TrashEntryServiceHttp.class);
	private static final Class<?>[] _deleteEntriesParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteEntriesParameterTypes1 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _deleteEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteEntryParameterTypes3 = new Class[] {
			java.lang.String.class, long.class
		};
	private static final Class<?>[] _getEntriesParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getEntriesParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getEntriesParameterTypes6 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getEntriesParameterTypes7 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _moveEntryParameterTypes8 = new Class[] {
			java.lang.String.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _restoreEntryParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreEntryParameterTypes10 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _restoreEntryParameterTypes11 = new Class[] {
			java.lang.String.class, long.class
		};
	private static final Class<?>[] _restoreEntryParameterTypes12 = new Class[] {
			java.lang.String.class, long.class, long.class,
			java.lang.String.class
		};
}