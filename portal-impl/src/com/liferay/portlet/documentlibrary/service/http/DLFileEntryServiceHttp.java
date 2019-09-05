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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DLFileEntryServiceUtil</code> service
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
 * @see DLFileEntryServiceSoap
 * @generated
 */
public class DLFileEntryServiceHttp {

	public static com.liferay.document.library.kernel.model.DLFileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long repositoryId,
				long folderId, String sourceFileName, String mimeType,
				String title, String description, String changeLog,
				long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, repositoryId, folderId, sourceFileName,
				mimeType, title, description, changeLog, fileEntryTypeId,
				ddmFormValuesMap, file, is, size, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileVersion
			cancelCheckOut(HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "cancelCheckOut",
				_cancelCheckOutParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

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

			return (com.liferay.document.library.kernel.model.DLFileVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, boolean major,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "checkInFileEntry",
				_checkInFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, major, changeLog, serviceContext);

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

	public static void checkInFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease
				dlVersionNumberIncrease,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "checkInFileEntry",
				_checkInFileEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);

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

	public static void checkInFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, String lockUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "checkInFileEntry",
				_checkInFileEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, lockUuid, serviceContext);

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

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "checkOutFileEntry",
				_checkOutFileEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId, String owner,
				long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "checkOutFileEntry",
				_checkOutFileEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, owner, expirationTime, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			copyFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long repositoryId,
				long fileEntryId, long destFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "copyFileEntry",
				_copyFileEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, repositoryId, fileEntryId, destFolderId,
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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "deleteFileEntry",
				_deleteFileEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

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

	public static void deleteFileEntry(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "deleteFileEntry",
				_deleteFileEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, title);

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

	public static void deleteFileVersion(
			HttpPrincipal httpPrincipal, long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "deleteFileVersion",
				_deleteFileVersionParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version);

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

	public static com.liferay.document.library.kernel.model.DLFileEntry
			fetchFileEntryByImageId(HttpPrincipal httpPrincipal, long imageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "fetchFileEntryByImageId",
				_fetchFileEntryByImageIdParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, imageId);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.InputStream getFileAsStream(
			HttpPrincipal httpPrincipal, long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileAsStream",
				_getFileAsStreamParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version);

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

			return (java.io.InputStream)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.InputStream getFileAsStream(
			HttpPrincipal httpPrincipal, long fileEntryId, String version,
			boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileAsStream",
				_getFileAsStreamParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version, incrementCounter);

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

			return (java.io.InputStream)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				long fileEntryTypeId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, fileEntryTypeId, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String[] mimeTypes, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, status, start, end,
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

			return (java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String[] mimeTypes, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

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

	public static int getFileEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status);

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

	public static int getFileEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		long fileEntryTypeId) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, fileEntryTypeId);

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

	public static int getFileEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		String[] mimeTypes) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes);

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

	public static int getFileEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		String[] mimeTypes, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, status);

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

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntry(HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntry",
				_getFileEntryParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntry",
				_getFileEntryParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, title);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntryByUuidAndGroupId(
				HttpPrincipal httpPrincipal, String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntryByUuidAndGroupId",
				_getFileEntryByUuidAndGroupIdParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock getFileEntryLock(
		HttpPrincipal httpPrincipal, long fileEntryId) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFileEntryLock",
				_getFileEntryLockParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersFileEntriesCount(
		HttpPrincipal httpPrincipal, long groupId,
		java.util.List<Long> folderIds, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getFoldersFileEntriesCount",
				_getFoldersFileEntriesCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderIds, status);

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

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long rootFolderId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.document.library.kernel.model.DLFileEntry>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long repositoryId, long rootFolderId, String[] mimeTypes,
					int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.document.library.kernel.model.DLFileEntry>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, repositoryId, rootFolderId,
				mimeTypes, status, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long rootFolderId, String[] mimeTypes, int status,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.document.library.kernel.model.DLFileEntry>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, mimeTypes, status,
				start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId);

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

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long repositoryId, long rootFolderId, String[] mimeTypes,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, repositoryId, rootFolderId,
				mimeTypes, status);

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

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId, String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, mimeTypes, status);

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

	public static boolean hasFileEntryLock(
			HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "hasFileEntryLock",
				_hasFileEntryLockParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isFileEntryCheckedOut(
			HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "isFileEntryCheckedOut",
				_isFileEntryCheckedOutParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isKeepFileVersionLabel(
			HttpPrincipal httpPrincipal, long fileEntryId, boolean majorVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "isKeepFileVersionLabel",
				_isKeepFileVersionLabelParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, majorVersion, serviceContext);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isKeepFileVersionLabel(
			HttpPrincipal httpPrincipal, long fileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "isKeepFileVersionLabel",
				_isKeepFileVersionLabelParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, serviceContext);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			moveFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "moveFileEntry",
				_moveFileEntryParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, newFolderId, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
			HttpPrincipal httpPrincipal, String lockUuid, long companyId,
			long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "refreshFileEntryLock",
				_refreshFileEntryLockParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, lockUuid, companyId, expirationTime);

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

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void revertFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "revertFileEntry",
				_revertFileEntryParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version, serviceContext);

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

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long groupId, long creatorUserId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "search",
				_searchParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, creatorUserId, status, start, end);

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

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long groupId, long creatorUserId,
			long folderId, String[] mimeTypes, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "search",
				_searchParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, creatorUserId, folderId, mimeTypes, status,
				start, end);

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

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, boolean majorVersion,
				long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, fileEntryTypeId,
				ddmFormValuesMap, file, is, size, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease,
				fileEntryTypeId, ddmFormValuesMap, file, is, size,
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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateStatus(
				HttpPrincipal httpPrincipal, long userId, long fileVersionId,
				int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "updateStatus",
				_updateStatusParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, fileVersionId, status, serviceContext,
				workflowContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyFileEntryCheckOut(
			HttpPrincipal httpPrincipal, long fileEntryId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "verifyFileEntryCheckOut",
				_verifyFileEntryCheckOutParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, lockUuid);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyFileEntryLock(
			HttpPrincipal httpPrincipal, long fileEntryId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryServiceUtil.class, "verifyFileEntryLock",
				_verifyFileEntryLockParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, lockUuid);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryServiceHttp.class);

	private static final Class<?>[] _addFileEntryParameterTypes0 = new Class[] {
		long.class, long.class, long.class, String.class, String.class,
		String.class, String.class, String.class, long.class,
		java.util.Map.class, java.io.File.class, java.io.InputStream.class,
		long.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _cancelCheckOutParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _checkInFileEntryParameterTypes2 =
		new Class[] {
			long.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes3 =
		new Class[] {
			long.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes4 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkOutFileEntryParameterTypes5 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkOutFileEntryParameterTypes6 =
		new Class[] {
			long.class, String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyFileEntryParameterTypes7 =
		new Class[] {
			long.class, long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFileEntryParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteFileEntryParameterTypes9 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _deleteFileVersionParameterTypes10 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _fetchFileEntryByImageIdParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileAsStreamParameterTypes12 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getFileAsStreamParameterTypes13 =
		new Class[] {long.class, String.class, boolean.class};
	private static final Class<?>[] _getFileEntriesParameterTypes14 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes15 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes16 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes17 =
		new Class[] {
			long.class, long.class, String[].class, int.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes18 =
		new Class[] {
			long.class, long.class, String[].class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesCountParameterTypes19 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFileEntriesCountParameterTypes20 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getFileEntriesCountParameterTypes21 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getFileEntriesCountParameterTypes22 =
		new Class[] {long.class, long.class, String[].class};
	private static final Class<?>[] _getFileEntriesCountParameterTypes23 =
		new Class[] {long.class, long.class, String[].class, int.class};
	private static final Class<?>[] _getFileEntryParameterTypes24 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileEntryParameterTypes25 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[]
		_getFileEntryByUuidAndGroupIdParameterTypes26 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getFileEntryLockParameterTypes27 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getFoldersFileEntriesCountParameterTypes28 = new Class[] {
			long.class, java.util.List.class, int.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes29 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes30 =
		new Class[] {
			long.class, long.class, long.class, long.class, String[].class,
			int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes31 =
		new Class[] {
			long.class, long.class, long.class, String[].class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes32 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes33 =
		new Class[] {
			long.class, long.class, long.class, long.class, String[].class,
			int.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes34 =
		new Class[] {
			long.class, long.class, long.class, String[].class, int.class
		};
	private static final Class<?>[] _hasFileEntryLockParameterTypes35 =
		new Class[] {long.class};
	private static final Class<?>[] _isFileEntryCheckedOutParameterTypes36 =
		new Class[] {long.class};
	private static final Class<?>[] _isKeepFileVersionLabelParameterTypes37 =
		new Class[] {
			long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _isKeepFileVersionLabelParameterTypes38 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFileEntryParameterTypes39 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _refreshFileEntryLockParameterTypes40 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _revertFileEntryParameterTypes41 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes42 = new Class[] {
		long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes43 = new Class[] {
		long.class, long.class, long.class, String[].class, int.class,
		int.class, int.class
	};
	private static final Class<?>[] _updateFileEntryParameterTypes44 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, long.class, java.util.Map.class,
			java.io.File.class, java.io.InputStream.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes45 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			long.class, java.util.Map.class, java.io.File.class,
			java.io.InputStream.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateStatusParameterTypes46 =
		new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class,
			java.util.Map.class
		};
	private static final Class<?>[] _verifyFileEntryCheckOutParameterTypes47 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _verifyFileEntryLockParameterTypes48 =
		new Class[] {long.class, String.class};

}