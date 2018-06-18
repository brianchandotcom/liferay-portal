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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPAttachmentFileEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPAttachmentFileEntryServiceUtil} service utility. The
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
 * @author Marco Leo
 * @see CPAttachmentFileEntryServiceSoap
 * @see HttpPrincipal
 * @see CPAttachmentFileEntryServiceUtil
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryServiceHttp {
	public static com.liferay.commerce.product.model.CPAttachmentFileEntry addCPAttachmentFileEntry(
		HttpPrincipal httpPrincipal, long classNameId, long classPK,
		long fileEntryId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, String> titleMap, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"addCPAttachmentFileEntry",
					_addCPAttachmentFileEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameId, classPK, fileEntryId, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, titleMap, json,
					priority, type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPAttachmentFileEntry(
		HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"deleteCPAttachmentFileEntry",
					_deleteCPAttachmentFileEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpAttachmentFileEntryId);

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

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry fetchCPAttachmentFileEntry(
		HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"fetchCPAttachmentFileEntry",
					_fetchCPAttachmentFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpAttachmentFileEntryId);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		HttpPrincipal httpPrincipal, long classNameId, long classPK, int type,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"getCPAttachmentFileEntries",
					_getCPAttachmentFileEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameId, classPK, type, status, start, end);

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

			return (java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		HttpPrincipal httpPrincipal, long classNameId, long classPK, int type,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"getCPAttachmentFileEntries",
					_getCPAttachmentFileEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameId, classPK, type, status, start, end,
					orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPAttachmentFileEntriesCount(
		HttpPrincipal httpPrincipal, long classNameId, long classPK, int type,
		int status) {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"getCPAttachmentFileEntriesCount",
					_getCPAttachmentFileEntriesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameId, classPK, type, status);

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

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry getCPAttachmentFileEntry(
		HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"getCPAttachmentFileEntry",
					_getCPAttachmentFileEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpAttachmentFileEntryId);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry updateCPAttachmentFileEntry(
		HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId,
		long fileEntryId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, String> titleMap, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPAttachmentFileEntryServiceUtil.class,
					"updateCPAttachmentFileEntry",
					_updateCPAttachmentFileEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpAttachmentFileEntryId, fileEntryId, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, titleMap, json,
					priority, type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPAttachmentFileEntryServiceHttp.class);
	private static final Class<?>[] _addCPAttachmentFileEntryParameterTypes0 = new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class, java.util.Map.class, String.class,
			double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPAttachmentFileEntryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPAttachmentFileEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPAttachmentFileEntriesParameterTypes3 = new Class[] {
			long.class, long.class, int.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _getCPAttachmentFileEntriesParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPAttachmentFileEntriesCountParameterTypes5 =
		new Class[] { long.class, long.class, int.class, int.class };
	private static final Class<?>[] _getCPAttachmentFileEntryParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCPAttachmentFileEntryParameterTypes7 = new Class[] {
			long.class, long.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class, java.util.Map.class, String.class, double.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
}