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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.wiki.service.WikiPageServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>WikiPageServiceUtil</code> service
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
 * @see WikiPageServiceSoap
 * @generated
 */
public class WikiPageServiceHttp {

	public static com.liferay.wiki.model.WikiPage addPage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			String content, String summary, boolean minorEdit,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addPage", _addPageParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, content, summary, minorEdit,
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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage addPage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addPage", _addPageParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, content, summary, minorEdit, format,
				parentTitle, redirectTitle, serviceContext);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addPageAttachment(
				HttpPrincipal httpPrincipal, long nodeId, String title,
				String fileName, java.io.File file, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addPageAttachment",
				_addPageAttachmentParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, fileName, file, mimeType);

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

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addPageAttachment(
				HttpPrincipal httpPrincipal, long nodeId, String title,
				String fileName, java.io.InputStream inputStream,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addPageAttachment",
				_addPageAttachmentParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, fileName, inputStream, mimeType);

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

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				addPageAttachments(
					HttpPrincipal httpPrincipal, long nodeId, String title,
					java.util.List
						<com.liferay.portal.kernel.util.ObjectValuePair
							<String, java.io.InputStream>> inputStreamOVPs)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addPageAttachments",
				_addPageAttachmentsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, inputStreamOVPs);

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
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				HttpPrincipal httpPrincipal, long nodeId, String folderName,
				String fileName, java.io.InputStream inputStream,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addTempFileEntry",
				_addTempFileEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, folderName, fileName, inputStream, mimeType);

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

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addTempPageAttachment(
			HttpPrincipal httpPrincipal, long nodeId, String fileName,
			String tempFolderName, java.io.InputStream inputStream,
			String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "addTempPageAttachment",
				_addTempPageAttachmentParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, fileName, tempFolderName, inputStream,
				mimeType);

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

	public static void changeParent(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			String newParentTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "changeParent",
				_changeParentParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, newParentTitle, serviceContext);

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

	public static void copyPageAttachments(
			HttpPrincipal httpPrincipal, long templateNodeId,
			String templateTitle, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "copyPageAttachments",
				_copyPageAttachmentsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateNodeId, templateTitle, nodeId, title);

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

	public static void deletePage(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "deletePage",
				_deletePageParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

	public static void deletePageAttachment(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "deletePageAttachment",
				_deletePageAttachmentParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, fileName);

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

	public static void deletePageAttachments(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "deletePageAttachments",
				_deletePageAttachmentsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

	public static void deleteTempFileEntry(
			HttpPrincipal httpPrincipal, long nodeId, String folderName,
			String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "deleteTempFileEntry",
				_deleteTempFileEntryParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, folderName, fileName);

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

	public static void deleteTrashPageAttachments(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "deleteTrashPageAttachments",
				_deleteTrashPageAttachmentsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

	public static void discardDraft(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "discardDraft",
				_discardDraftParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, version);

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

	public static com.liferay.wiki.model.WikiPage fetchPage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "fetchPage",
				_fetchPageParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, version);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
			HttpPrincipal httpPrincipal, long groupId, long nodeId,
			boolean head, String parentTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getChildren",
				_getChildrenParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, head, parentTitle);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage getDraftPage(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getDraftPage",
				_getDraftPageParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getNodePages(
			HttpPrincipal httpPrincipal, long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getNodePages",
				_getNodePagesParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, max);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getNodePagesRSS(
			HttpPrincipal httpPrincipal, long nodeId, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getNodePagesRSS",
				_getNodePagesRSSParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, max, type, version, displayStyle, feedURL,
				entryURL, attachmentURLPrefix);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getOrphans(
			HttpPrincipal httpPrincipal, long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getOrphans",
				_getOrphansParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getOrphans(
			HttpPrincipal httpPrincipal, com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getOrphans",
				_getOrphansParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, node);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			HttpPrincipal httpPrincipal, long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPage", _getPageParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey, pageId);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			HttpPrincipal httpPrincipal, long groupId, long nodeId,
			String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPage", _getPageParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, title);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPage", _getPageParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPage", _getPageParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, head);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPage", _getPageParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, version);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getPages(
			HttpPrincipal httpPrincipal, long groupId, long nodeId,
			boolean head, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.wiki.model.WikiPage> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPages",
				_getPagesParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, head, status, start, end, obc);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getPages(
			HttpPrincipal httpPrincipal, long groupId, long nodeId,
			boolean head, long userId, boolean includeOwner, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.wiki.model.WikiPage> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPages",
				_getPagesParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, head, userId, includeOwner, status,
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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getPages(
			HttpPrincipal httpPrincipal, long groupId, long userId, long nodeId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPages",
				_getPagesParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, nodeId, status, start, end);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getPagesCount(
			HttpPrincipal httpPrincipal, long groupId, long nodeId,
			boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPagesCount",
				_getPagesCountParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, head);

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

	public static int getPagesCount(
			HttpPrincipal httpPrincipal, long groupId, long nodeId,
			boolean head, long userId, boolean includeOwner, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPagesCount",
				_getPagesCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, head, userId, includeOwner, status);

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

	public static int getPagesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId, long nodeId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPagesCount",
				_getPagesCountParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, nodeId, status);

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

	public static String getPagesRSS(
			HttpPrincipal httpPrincipal, long nodeId, String title, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL, String attachmentURLPrefix,
			java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getPagesRSS",
				_getPagesRSSParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, max, type, version, displayStyle,
				feedURL, entryURL, attachmentURLPrefix, locale);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage>
			getRecentChanges(
				HttpPrincipal httpPrincipal, long groupId, long nodeId,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getRecentChanges",
				_getRecentChangesParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId, start, end);

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

			return (java.util.List<com.liferay.wiki.model.WikiPage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRecentChangesCount(
			HttpPrincipal httpPrincipal, long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getRecentChangesCount",
				_getRecentChangesCountParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nodeId);

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

	public static String[] getTempFileNames(
			HttpPrincipal httpPrincipal, long nodeId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "getTempFileNames",
				_getTempFileNamesParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, folderName);

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

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			movePageAttachmentToTrash(
				HttpPrincipal httpPrincipal, long nodeId, String title,
				String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "movePageAttachmentToTrash",
				_movePageAttachmentToTrashParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, fileName);

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

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage movePageToTrash(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "movePageToTrash",
				_movePageToTrashParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.wiki.model.WikiPage movePageToTrash(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "movePageToTrash",
				_movePageToTrashParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, version);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void renamePage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			String newTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "renamePage",
				_renamePageParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, newTitle, serviceContext);

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

	public static void restorePageAttachmentFromTrash(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "restorePageAttachmentFromTrash",
				_restorePageAttachmentFromTrashParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, fileName);

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

	public static void restorePageFromTrash(
			HttpPrincipal httpPrincipal, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "restorePageFromTrash",
				_restorePageFromTrashParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey);

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

	public static com.liferay.wiki.model.WikiPage revertPage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			double version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "revertPage",
				_revertPageParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, version, serviceContext);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribePage(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "subscribePage",
				_subscribePageParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

	public static void unsubscribePage(
			HttpPrincipal httpPrincipal, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "unsubscribePage",
				_unsubscribePageParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title);

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

	public static com.liferay.wiki.model.WikiPage updatePage(
			HttpPrincipal httpPrincipal, long nodeId, String title,
			double version, String content, String summary, boolean minorEdit,
			String format, String parentTitle, String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WikiPageServiceUtil.class, "updatePage",
				_updatePageParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nodeId, title, version, content, summary, minorEdit,
				format, parentTitle, redirectTitle, serviceContext);

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

			return (com.liferay.wiki.model.WikiPage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageServiceHttp.class);

	private static final Class<?>[] _addPageParameterTypes0 = new Class[] {
		long.class, String.class, String.class, String.class, boolean.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addPageParameterTypes1 = new Class[] {
		long.class, String.class, String.class, String.class, boolean.class,
		String.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addPageAttachmentParameterTypes2 =
		new Class[] {
			long.class, String.class, String.class, java.io.File.class,
			String.class
		};
	private static final Class<?>[] _addPageAttachmentParameterTypes3 =
		new Class[] {
			long.class, String.class, String.class, java.io.InputStream.class,
			String.class
		};
	private static final Class<?>[] _addPageAttachmentsParameterTypes4 =
		new Class[] {long.class, String.class, java.util.List.class};
	private static final Class<?>[] _addTempFileEntryParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, java.io.InputStream.class,
			String.class
		};
	private static final Class<?>[] _addTempPageAttachmentParameterTypes6 =
		new Class[] {
			long.class, String.class, String.class, java.io.InputStream.class,
			String.class
		};
	private static final Class<?>[] _changeParentParameterTypes7 = new Class[] {
		long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _copyPageAttachmentsParameterTypes8 =
		new Class[] {long.class, String.class, long.class, String.class};
	private static final Class<?>[] _deletePageParameterTypes9 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _deletePageAttachmentParameterTypes10 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _deletePageAttachmentsParameterTypes11 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _deleteTempFileEntryParameterTypes12 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_deleteTrashPageAttachmentsParameterTypes13 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _discardDraftParameterTypes14 =
		new Class[] {long.class, String.class, double.class};
	private static final Class<?>[] _fetchPageParameterTypes15 = new Class[] {
		long.class, String.class, double.class
	};
	private static final Class<?>[] _getChildrenParameterTypes16 = new Class[] {
		long.class, long.class, boolean.class, String.class
	};
	private static final Class<?>[] _getDraftPageParameterTypes17 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getNodePagesParameterTypes18 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getNodePagesRSSParameterTypes19 =
		new Class[] {
			long.class, int.class, String.class, double.class, String.class,
			String.class, String.class, String.class
		};
	private static final Class<?>[] _getOrphansParameterTypes20 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _getOrphansParameterTypes21 = new Class[] {
		com.liferay.wiki.model.WikiNode.class
	};
	private static final Class<?>[] _getPageParameterTypes22 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getPageParameterTypes23 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getPageParameterTypes24 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getPageParameterTypes25 = new Class[] {
		long.class, String.class, Boolean.class
	};
	private static final Class<?>[] _getPageParameterTypes26 = new Class[] {
		long.class, String.class, double.class
	};
	private static final Class<?>[] _getPagesParameterTypes27 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getPagesParameterTypes28 = new Class[] {
		long.class, long.class, boolean.class, long.class, boolean.class,
		int.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getPagesParameterTypes29 = new Class[] {
		long.class, long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _getPagesCountParameterTypes30 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _getPagesCountParameterTypes31 =
		new Class[] {
			long.class, long.class, boolean.class, long.class, boolean.class,
			int.class
		};
	private static final Class<?>[] _getPagesCountParameterTypes32 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getPagesRSSParameterTypes33 = new Class[] {
		long.class, String.class, int.class, String.class, double.class,
		String.class, String.class, String.class, String.class,
		java.util.Locale.class
	};
	private static final Class<?>[] _getRecentChangesParameterTypes34 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getRecentChangesCountParameterTypes35 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getTempFileNamesParameterTypes36 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _movePageAttachmentToTrashParameterTypes37 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _movePageToTrashParameterTypes38 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _movePageToTrashParameterTypes39 =
		new Class[] {long.class, String.class, double.class};
	private static final Class<?>[] _renamePageParameterTypes40 = new Class[] {
		long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[]
		_restorePageAttachmentFromTrashParameterTypes41 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[] _restorePageFromTrashParameterTypes42 =
		new Class[] {long.class};
	private static final Class<?>[] _revertPageParameterTypes43 = new Class[] {
		long.class, String.class, double.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _subscribePageParameterTypes44 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _unsubscribePageParameterTypes45 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updatePageParameterTypes46 = new Class[] {
		long.class, String.class, double.class, String.class, String.class,
		boolean.class, String.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};

}