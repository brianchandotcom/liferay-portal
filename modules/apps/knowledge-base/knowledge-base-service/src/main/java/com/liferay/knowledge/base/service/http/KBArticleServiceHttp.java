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

import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>KBArticleServiceUtil</code> service
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
 * @see KBArticleServiceSoap
 * @generated
 */
public class KBArticleServiceHttp {

	public static com.liferay.knowledge.base.model.KBArticle addKBArticle(
			HttpPrincipal httpPrincipal, String portletId,
			long parentResourceClassNameId, long parentResourcePrimKey,
			String title, String urlTitle, String content, String description,
			String sourceURL, String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "addKBArticle",
				_addKBArticleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, portletId, parentResourceClassNameId,
				parentResourcePrimKey, title, urlTitle, content, description,
				sourceURL, sections, selectedFileNames, serviceContext);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int addKBArticlesMarkdown(
			HttpPrincipal httpPrincipal, long groupId, long parentKBFolderId,
			String fileName, boolean prioritizeByNumericalPrefix,
			java.io.InputStream inputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "addKBArticlesMarkdown",
				_addKBArticlesMarkdownParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentKBFolderId, fileName,
				prioritizeByNumericalPrefix, inputStream, serviceContext);

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

	public static void addTempAttachment(
			HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
			String fileName, String tempFolderName,
			java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "addTempAttachment",
				_addTempAttachmentParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey, fileName, tempFolderName,
				inputStream, mimeType);

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

	public static com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "deleteKBArticle",
				_deleteKBArticleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteKBArticles(
			HttpPrincipal httpPrincipal, long groupId, long[] resourcePrimKeys)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "deleteKBArticles",
				_deleteKBArticlesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKeys);

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

	public static void deleteTempAttachment(
			HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
			String fileName, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "deleteTempAttachment",
				_deleteTempAttachmentParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey, fileName, tempFolderName);

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

	public static com.liferay.knowledge.base.model.KBArticle
		fetchFirstChildKBArticle(
			HttpPrincipal httpPrincipal, long groupId,
			long parentResourcePrimKey) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "fetchFirstChildKBArticle",
				_fetchFirstChildKBArticleParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentResourcePrimKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchFirstChildKBArticle(
			HttpPrincipal httpPrincipal, long groupId,
			long parentResourcePrimKey, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "fetchFirstChildKBArticle",
				_fetchFirstChildKBArticleParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentResourcePrimKey, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBArticle
			fetchKBArticleByUrlTitle(
				HttpPrincipal httpPrincipal, long groupId, long kbFolderId,
				String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "fetchKBArticleByUrlTitle",
				_fetchKBArticleByUrlTitleParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, kbFolderId, urlTitle);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBArticle
			fetchLatestKBArticle(
				HttpPrincipal httpPrincipal, long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "fetchLatestKBArticle",
				_fetchLatestKBArticleParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, status);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBArticle
			fetchLatestKBArticleByUrlTitle(
				HttpPrincipal httpPrincipal, long groupId, long kbFolderId,
				String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "fetchLatestKBArticleByUrlTitle",
				_fetchLatestKBArticleByUrlTitleParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, kbFolderId, urlTitle, status);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
			getAllDescendantKBArticles(
				HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
				int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getAllDescendantKBArticles",
				_getAllDescendantKBArticlesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey, status, orderByComparator);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getGroupKBArticles(
			HttpPrincipal httpPrincipal, long groupId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getGroupKBArticles",
				_getGroupKBArticlesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupKBArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getGroupKBArticlesCount",
				_getGroupKBArticlesCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

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

	public static String getGroupKBArticlesRSS(
			HttpPrincipal httpPrincipal, int status, int rssDelta,
			String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getGroupKBArticlesRSS",
				_getGroupKBArticlesRSSParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, status, rssDelta, rssDisplayStyle, rssFormat,
				themeDisplay);

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

	public static com.liferay.knowledge.base.model.KBArticle getKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey, int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticle",
				_getKBArticleParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, version);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
			getKBArticleAndAllDescendantKBArticles(
				HttpPrincipal httpPrincipal, long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class,
				"getKBArticleAndAllDescendantKBArticles",
				_getKBArticleAndAllDescendantKBArticlesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, status, orderByComparator);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getKBArticleRSS(
			HttpPrincipal httpPrincipal, long resourcePrimKey, int status,
			int rssDelta, String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticleRSS",
				_getKBArticleRSSParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, status, rssDelta, rssDisplayStyle,
				rssFormat, themeDisplay);

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

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			HttpPrincipal httpPrincipal, long groupId,
			long parentResourcePrimKey, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticles",
				_getKBArticlesParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentResourcePrimKey, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			HttpPrincipal httpPrincipal, long groupId, long[] resourcePrimKeys,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticles",
				_getKBArticlesParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKeys, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			HttpPrincipal httpPrincipal, long groupId, long[] resourcePrimKeys,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticles",
				_getKBArticlesParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKeys, status,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getKBArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, long parentResourcePrimKey,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticlesCount",
				_getKBArticlesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentResourcePrimKey, status);

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

	public static int getKBArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, long[] resourcePrimKeys,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticlesCount",
				_getKBArticlesCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKeys, status);

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

	public static com.liferay.knowledge.base.model.KBArticleSearchDisplay
			getKBArticleSearchDisplay(
				HttpPrincipal httpPrincipal, long groupId, String title,
				String content, int status, java.util.Date startDate,
				java.util.Date endDate, boolean andOperator,
				int[] curStartValues, int cur, int delta,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticleSearchDisplay",
				_getKBArticleSearchDisplayParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, content, status, startDate, endDate,
				andOperator, curStartValues, cur, delta, orderByComparator);

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

			return (com.liferay.knowledge.base.model.KBArticleSearchDisplay)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleVersions(
			HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticleVersions",
				_getKBArticleVersionsParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getKBArticleVersionsCount(
		HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getKBArticleVersionsCount",
				_getKBArticleVersionsCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey, status);

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

	public static com.liferay.knowledge.base.model.KBArticle getLatestKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getLatestKBArticle",
				_getLatestKBArticleParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, status);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.knowledge.base.model.KBArticle[]
			getPreviousAndNextKBArticles(
				HttpPrincipal httpPrincipal, long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getPreviousAndNextKBArticles",
				_getPreviousAndNextKBArticlesParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbArticleId);

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

			return (com.liferay.knowledge.base.model.KBArticle[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getSectionsKBArticles(
			HttpPrincipal httpPrincipal, long groupId, String[] sections,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getSectionsKBArticles",
				_getSectionsKBArticlesParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, sections, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.knowledge.base.model.KBArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSectionsKBArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, String[] sections,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getSectionsKBArticlesCount",
				_getSectionsKBArticlesCountParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, sections, status);

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

	public static String[] getTempAttachmentNames(
			HttpPrincipal httpPrincipal, long groupId, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "getTempAttachmentNames",
				_getTempAttachmentNamesParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, tempFolderName);

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

	public static void moveKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey,
			long parentResourceClassNameId, long parentResourcePrimKey,
			double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "moveKBArticle",
				_moveKBArticleParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, parentResourceClassNameId,
				parentResourcePrimKey, priority);

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

	public static com.liferay.knowledge.base.model.KBArticle revertKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "revertKBArticle",
				_revertKBArticleParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, version, serviceContext);

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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeGroupKBArticles(
			HttpPrincipal httpPrincipal, long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "subscribeGroupKBArticles",
				_subscribeGroupKBArticlesParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, portletId);

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

	public static void subscribeKBArticle(
			HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "subscribeKBArticle",
				_subscribeKBArticleParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey);

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

	public static void unsubscribeGroupKBArticles(
			HttpPrincipal httpPrincipal, long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "unsubscribeGroupKBArticles",
				_unsubscribeGroupKBArticlesParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, portletId);

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

	public static void unsubscribeKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "unsubscribeKBArticle",
				_unsubscribeKBArticleParameterTypes36);

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

	public static com.liferay.knowledge.base.model.KBArticle updateKBArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey, String title,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			long[] removeFileEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "updateKBArticle",
				_updateKBArticleParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey, title, content, description,
				sourceURL, sections, selectedFileNames, removeFileEntryIds,
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

			return (com.liferay.knowledge.base.model.KBArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateKBArticlesPriorities(
			HttpPrincipal httpPrincipal, long groupId,
			java.util.Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBArticleServiceUtil.class, "updateKBArticlesPriorities",
				_updateKBArticlesPrioritiesParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKeyToPriorityMap);

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

	private static Log _log = LogFactoryUtil.getLog(KBArticleServiceHttp.class);

	private static final Class<?>[] _addKBArticleParameterTypes0 = new Class[] {
		String.class, long.class, long.class, String.class, String.class,
		String.class, String.class, String.class, String[].class,
		String[].class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addKBArticlesMarkdownParameterTypes1 =
		new Class[] {
			long.class, long.class, String.class, boolean.class,
			java.io.InputStream.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addTempAttachmentParameterTypes2 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.io.InputStream.class, String.class
		};
	private static final Class<?>[] _deleteKBArticleParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteKBArticlesParameterTypes4 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _deleteTempAttachmentParameterTypes5 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _fetchFirstChildKBArticleParameterTypes6 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _fetchFirstChildKBArticleParameterTypes7 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _fetchKBArticleByUrlTitleParameterTypes8 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _fetchLatestKBArticleParameterTypes9 =
		new Class[] {long.class, int.class};
	private static final Class<?>[]
		_fetchLatestKBArticleByUrlTitleParameterTypes10 = new Class[] {
			long.class, long.class, String.class, int.class
		};
	private static final Class<?>[]
		_getAllDescendantKBArticlesParameterTypes11 = new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupKBArticlesParameterTypes12 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupKBArticlesCountParameterTypes13 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getGroupKBArticlesRSSParameterTypes14 =
		new Class[] {
			int.class, int.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getKBArticleParameterTypes15 =
		new Class[] {long.class, int.class};
	private static final Class<?>[]
		_getKBArticleAndAllDescendantKBArticlesParameterTypes16 = new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBArticleRSSParameterTypes17 =
		new Class[] {
			long.class, int.class, int.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getKBArticlesParameterTypes18 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBArticlesParameterTypes19 =
		new Class[] {
			long.class, long[].class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBArticlesParameterTypes20 =
		new Class[] {
			long.class, long[].class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBArticlesCountParameterTypes21 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getKBArticlesCountParameterTypes22 =
		new Class[] {long.class, long[].class, int.class};
	private static final Class<?>[] _getKBArticleSearchDisplayParameterTypes23 =
		new Class[] {
			long.class, String.class, String.class, int.class,
			java.util.Date.class, java.util.Date.class, boolean.class,
			int[].class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBArticleVersionsParameterTypes24 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getKBArticleVersionsCountParameterTypes25 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getLatestKBArticleParameterTypes26 =
		new Class[] {long.class, int.class};
	private static final Class<?>[]
		_getPreviousAndNextKBArticlesParameterTypes27 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSectionsKBArticlesParameterTypes28 =
		new Class[] {
			long.class, String[].class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getSectionsKBArticlesCountParameterTypes29 = new Class[] {
			long.class, String[].class, int.class
		};
	private static final Class<?>[] _getTempAttachmentNamesParameterTypes30 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _moveKBArticleParameterTypes31 =
		new Class[] {long.class, long.class, long.class, double.class};
	private static final Class<?>[] _revertKBArticleParameterTypes32 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _subscribeGroupKBArticlesParameterTypes33 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _subscribeKBArticleParameterTypes34 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_unsubscribeGroupKBArticlesParameterTypes35 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _unsubscribeKBArticleParameterTypes36 =
		new Class[] {long.class};
	private static final Class<?>[] _updateKBArticleParameterTypes37 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String[].class, String[].class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateKBArticlesPrioritiesParameterTypes38 = new Class[] {
			long.class, java.util.Map.class
		};

}