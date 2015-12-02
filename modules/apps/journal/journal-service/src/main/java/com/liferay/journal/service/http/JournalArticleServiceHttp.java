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

package com.liferay.journal.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.service.JournalArticleServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link JournalArticleServiceUtil} service utility. The
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
 * @see JournalArticleServiceSoap
 * @see HttpPrincipal
 * @see JournalArticleServiceUtil
 * @generated
 */
@ProviderType
public class JournalArticleServiceHttp {
	public static com.liferay.journal.model.JournalArticle addArticle(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		long classNameId, long classPK, java.lang.String articleId,
		boolean autoArticleId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.lang.String layoutUuid,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<java.lang.String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"addArticle", _addArticleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, classNameId, classPK, articleId, autoArticleId,
					titleMap, descriptionMap, content, ddmStructureKey,
					ddmTemplateKey, layoutUuid, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, indexable, smallImage,
					smallImageURL, smallFile, images, articleURL, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle addArticle(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		long classNameId, long classPK, java.lang.String articleId,
		boolean autoArticleId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.lang.String layoutUuid,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"addArticle", _addArticleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, classNameId, classPK, articleId, autoArticleId,
					titleMap, descriptionMap, content, ddmStructureKey,
					ddmTemplateKey, layoutUuid, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, indexable, articleURL,
					serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle copyArticle(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String oldArticleId, java.lang.String newArticleId,
		boolean autoArticleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"copyArticle", _copyArticleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					oldArticleId, newArticleId, autoArticleId, version);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteArticle(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String articleId, double version,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"deleteArticle", _deleteArticleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, articleURL, serviceContext);

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

	public static void deleteArticle(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String articleId, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"deleteArticle", _deleteArticleParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, articleURL, serviceContext);

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

	public static com.liferay.journal.model.JournalArticle expireArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"expireArticle", _expireArticleParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, articleURL, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void expireArticle(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String articleId, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"expireArticle", _expireArticleParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, articleURL, serviceContext);

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

	public static com.liferay.journal.model.JournalArticle fetchArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"fetchArticle", _fetchArticleParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
		HttpPrincipal httpPrincipal, long id)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticle", _getArticleParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, id);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticle", _getArticleParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticle", _getArticleParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticle", _getArticleParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticleByUrlTitle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticleByUrlTitle",
					_getArticleByUrlTitleParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					urlTitle);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getArticleContent(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.lang.String languageId,
		com.liferay.portal.kernel.portlet.PortletRequestModel portletRequestModel,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticleContent", _getArticleContentParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, languageId, portletRequestModel,
					themeDisplay);

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

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getArticleContent(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticleContent", _getArticleContentParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, languageId, themeDisplay);

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

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getArticleContent(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		java.lang.String languageId,
		com.liferay.portal.kernel.portlet.PortletRequestModel portletRequestModel,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticleContent", _getArticleContentParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, languageId, portletRequestModel, themeDisplay);

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

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getArticleContent(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticleContent", _getArticleContentParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, languageId, themeDisplay);

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

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticles(
		HttpPrincipal httpPrincipal, long groupId, long folderId) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticles", _getArticlesParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticles(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticles", _getArticlesParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticlesByArticleId(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesByArticleId",
					_getArticlesByArticleIdParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticlesByLayoutUuid(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String layoutUuid) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesByLayoutUuid",
					_getArticlesByLayoutUuidParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticlesByStructureId(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String ddmStructureKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesByStructureId",
					_getArticlesByStructureIdParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, ddmStructureKey, status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticlesByStructureId(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String ddmStructureKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesByStructureId",
					_getArticlesByStructureIdParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureKey, status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getArticlesByStructureId(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String ddmStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesByStructureId",
					_getArticlesByStructureIdParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureKey, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getArticlesCount(HttpPrincipal httpPrincipal,
		long groupId, long folderId) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesCount", _getArticlesCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

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

	public static int getArticlesCount(HttpPrincipal httpPrincipal,
		long groupId, long folderId, int status) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesCount", _getArticlesCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status);

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

	public static int getArticlesCountByArticleId(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String articleId) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesCountByArticleId",
					_getArticlesCountByArticleIdParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId);

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

	public static int getArticlesCountByStructureId(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String ddmStructureKey, int status) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesCountByStructureId",
					_getArticlesCountByStructureIdParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, ddmStructureKey, status);

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

	public static int getArticlesCountByStructureId(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String ddmStructureKey) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesCountByStructureId",
					_getArticlesCountByStructureIdParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureKey);

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

	public static int getArticlesCountByStructureId(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String ddmStructureKey, int status) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getArticlesCountByStructureId",
					_getArticlesCountByStructureIdParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureKey, status);

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

	public static com.liferay.journal.model.JournalArticle getDisplayArticleByUrlTitle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getDisplayArticleByUrlTitle",
					_getDisplayArticleByUrlTitleParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					urlTitle);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndArticlesCount(HttpPrincipal httpPrincipal,
		long groupId, java.util.List<java.lang.Long> folderIds) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getFoldersAndArticlesCount",
					_getFoldersAndArticlesCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderIds);

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

	public static java.util.List<com.liferay.journal.model.JournalArticle> getGroupArticles(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		long rootFolderId, int status, boolean includeOwner, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getGroupArticles", _getGroupArticlesParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, status, includeOwner, start, end,
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

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getGroupArticles(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		long rootFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getGroupArticles", _getGroupArticlesParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, status, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> getGroupArticles(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getGroupArticles", _getGroupArticlesParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupArticlesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getGroupArticlesCount",
					_getGroupArticlesCountParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupArticlesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId, long rootFolderId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getGroupArticlesCount",
					_getGroupArticlesCountParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, status);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupArticlesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId, long rootFolderId, int status,
		boolean includeOwner)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getGroupArticlesCount",
					_getGroupArticlesCountParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, status, includeOwner);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getLatestArticle(
		HttpPrincipal httpPrincipal, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getLatestArticle", _getLatestArticleParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					resourcePrimKey);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getLatestArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getLatestArticle", _getLatestArticleParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, status);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle getLatestArticle(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"getLatestArticle", _getLatestArticleParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void moveArticle(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String articleId, long newFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"moveArticle", _moveArticleParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, newFolderId);

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

	public static void moveArticle(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String articleId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"moveArticle", _moveArticleParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, newFolderId, serviceContext);

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

	public static com.liferay.journal.model.JournalArticle moveArticleFromTrash(
		HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"moveArticleFromTrash",
					_moveArticleFromTrashParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					resourcePrimKey, newFolderId, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle moveArticleFromTrash(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"moveArticleFromTrash",
					_moveArticleFromTrashParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, newFolderId, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle moveArticleToTrash(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"moveArticleToTrash", _moveArticleToTrashParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void removeArticleLocale(HttpPrincipal httpPrincipal,
		long companyId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"removeArticleLocale", _removeArticleLocaleParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, languageId);

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

	public static com.liferay.journal.model.JournalArticle removeArticleLocale(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"removeArticleLocale", _removeArticleLocaleParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, languageId);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreArticleFromTrash(HttpPrincipal httpPrincipal,
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"restoreArticleFromTrash",
					_restoreArticleFromTrashParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					resourcePrimKey);

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

	public static void restoreArticleFromTrash(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"restoreArticleFromTrash",
					_restoreArticleFromTrashParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId);

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

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal, long groupId, long creatorUserId,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"search", _searchParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					creatorUserId, status, start, end);

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

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"search", _searchParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, folderIds, classNameId, keywords,
					version, ddmStructureKey, ddmTemplateKey, displayDateGT,
					displayDateLT, status, reviewDate, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"search", _searchParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, folderIds, classNameId, articleId,
					version, title, description, content, ddmStructureKey,
					ddmTemplateKey, displayDateGT, displayDateLT, status,
					reviewDate, andOperator, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticle> obc) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"search", _searchParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, folderIds, classNameId, articleId,
					version, title, description, content, ddmStructureKeys,
					ddmTemplateKeys, displayDateGT, displayDateLT, status,
					reviewDate, andOperator, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"searchCount", _searchCountParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, folderIds, classNameId, keywords,
					version, ddmStructureKey, ddmTemplateKey, displayDateGT,
					displayDateLT, status, reviewDate);

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

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"searchCount", _searchCountParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, folderIds, classNameId, articleId,
					version, title, description, content, ddmStructureKey,
					ddmTemplateKey, displayDateGT, displayDateLT, status,
					reviewDate, andOperator);

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

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator) {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"searchCount", _searchCountParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, folderIds, classNameId, articleId,
					version, title, description, content, ddmStructureKeys,
					ddmTemplateKeys, displayDateGT, displayDateLT, status,
					reviewDate, andOperator);

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

	public static void subscribeStructure(HttpPrincipal httpPrincipal,
		long groupId, long userId, long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"subscribeStructure", _subscribeStructureParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, ddmStructureId);

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

	public static void unsubscribeStructure(HttpPrincipal httpPrincipal,
		long groupId, long userId, long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"unsubscribeStructure",
					_unsubscribeStructureParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, ddmStructureId);

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

	public static com.liferay.journal.model.JournalArticle updateArticle(
		HttpPrincipal httpPrincipal, long userId, long groupId, long folderId,
		java.lang.String articleId, double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String layoutUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateArticle", _updateArticleParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, folderId, articleId, version, titleMap,
					descriptionMap, content, layoutUuid, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticle(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String articleId, double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.lang.String layoutUuid,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<java.lang.String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateArticle", _updateArticleParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, articleId, version, titleMap, descriptionMap,
					content, ddmStructureKey, ddmTemplateKey, layoutUuid,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, indexable, smallImage,
					smallImageURL, smallFile, images, articleURL, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticle(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String articleId, double version, java.lang.String content,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateArticle", _updateArticleParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, articleId, version, content, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticleTranslation(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateArticleTranslation",
					_updateArticleTranslationParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, locale, title, description, content,
					images);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticleTranslation(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateArticleTranslation",
					_updateArticleTranslationParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, locale, title, description, content,
					images, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateContent(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, java.lang.String content)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateContent", _updateContentParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, content);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateStatus(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String articleId,
		double version, int status, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(JournalArticleServiceUtil.class,
					"updateStatus", _updateStatusParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					articleId, version, status, articleURL, serviceContext);

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

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticleServiceHttp.class);
	private static final Class<?>[] _addArticleParameterTypes0 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, boolean.class, java.util.Map.class,
			java.util.Map.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class, java.util.Map.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addArticleParameterTypes1 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, boolean.class, java.util.Map.class,
			java.util.Map.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, boolean.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyArticleParameterTypes2 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			boolean.class, double.class
		};
	private static final Class<?>[] _deleteArticleParameterTypes3 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteArticleParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _expireArticleParameterTypes5 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _expireArticleParameterTypes6 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _fetchArticleParameterTypes7 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getArticleParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getArticleParameterTypes9 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getArticleParameterTypes10 = new Class[] {
			long.class, java.lang.String.class, double.class
		};
	private static final Class<?>[] _getArticleParameterTypes11 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _getArticleByUrlTitleParameterTypes12 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getArticleContentParameterTypes13 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.lang.String.class,
			com.liferay.portal.kernel.portlet.PortletRequestModel.class,
			com.liferay.portal.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getArticleContentParameterTypes14 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.lang.String.class, com.liferay.portal.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getArticleContentParameterTypes15 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.kernel.portlet.PortletRequestModel.class,
			com.liferay.portal.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getArticleContentParameterTypes16 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getArticlesParameterTypes17 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getArticlesParameterTypes18 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByArticleIdParameterTypes19 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByLayoutUuidParameterTypes20 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes21 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes22 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes23 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesCountParameterTypes24 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getArticlesCountParameterTypes25 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _getArticlesCountByArticleIdParameterTypes26 =
		new Class[] { long.class, java.lang.String.class };
	private static final Class<?>[] _getArticlesCountByStructureIdParameterTypes27 =
		new Class[] { long.class, long.class, java.lang.String.class, int.class };
	private static final Class<?>[] _getArticlesCountByStructureIdParameterTypes28 =
		new Class[] { long.class, java.lang.String.class };
	private static final Class<?>[] _getArticlesCountByStructureIdParameterTypes29 =
		new Class[] { long.class, java.lang.String.class, int.class };
	private static final Class<?>[] _getDisplayArticleByUrlTitleParameterTypes30 =
		new Class[] { long.class, java.lang.String.class };
	private static final Class<?>[] _getFoldersAndArticlesCountParameterTypes31 = new Class[] {
			long.class, java.util.List.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes32 = new Class[] {
			long.class, long.class, long.class, int.class, boolean.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes33 = new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes34 = new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesCountParameterTypes35 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _getGroupArticlesCountParameterTypes36 = new Class[] {
			long.class, long.class, long.class, int.class
		};
	private static final Class<?>[] _getGroupArticlesCountParameterTypes37 = new Class[] {
			long.class, long.class, long.class, int.class, boolean.class
		};
	private static final Class<?>[] _getLatestArticleParameterTypes38 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getLatestArticleParameterTypes39 = new Class[] {
			long.class, java.lang.String.class, int.class
		};
	private static final Class<?>[] _getLatestArticleParameterTypes40 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _moveArticleParameterTypes41 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _moveArticleParameterTypes42 = new Class[] {
			long.class, java.lang.String.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveArticleFromTrashParameterTypes43 = new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveArticleFromTrashParameterTypes44 = new Class[] {
			long.class, java.lang.String.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveArticleToTrashParameterTypes45 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _removeArticleLocaleParameterTypes46 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _removeArticleLocaleParameterTypes47 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.lang.String.class
		};
	private static final Class<?>[] _restoreArticleFromTrashParameterTypes48 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreArticleFromTrashParameterTypes49 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _searchParameterTypes50 = new Class[] {
			long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _searchParameterTypes51 = new Class[] {
			long.class, long.class, java.util.List.class, long.class,
			java.lang.String.class, java.lang.Double.class,
			java.lang.String.class, java.lang.String.class, java.util.Date.class,
			java.util.Date.class, int.class, java.util.Date.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes52 = new Class[] {
			long.class, long.class, java.util.List.class, long.class,
			java.lang.String.class, java.lang.Double.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.util.Date.class, java.util.Date.class,
			int.class, java.util.Date.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes53 = new Class[] {
			long.class, long.class, java.util.List.class, long.class,
			java.lang.String.class, java.lang.Double.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String[].class,
			java.lang.String[].class, java.util.Date.class, java.util.Date.class,
			int.class, java.util.Date.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes54 = new Class[] {
			long.class, long.class, java.util.List.class, long.class,
			java.lang.String.class, java.lang.Double.class,
			java.lang.String.class, java.lang.String.class, java.util.Date.class,
			java.util.Date.class, int.class, java.util.Date.class
		};
	private static final Class<?>[] _searchCountParameterTypes55 = new Class[] {
			long.class, long.class, java.util.List.class, long.class,
			java.lang.String.class, java.lang.Double.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.util.Date.class, java.util.Date.class,
			int.class, java.util.Date.class, boolean.class
		};
	private static final Class<?>[] _searchCountParameterTypes56 = new Class[] {
			long.class, long.class, java.util.List.class, long.class,
			java.lang.String.class, java.lang.Double.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String[].class,
			java.lang.String[].class, java.util.Date.class, java.util.Date.class,
			int.class, java.util.Date.class, boolean.class
		};
	private static final Class<?>[] _subscribeStructureParameterTypes57 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _unsubscribeStructureParameterTypes58 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _updateArticleParameterTypes59 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			double.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleParameterTypes60 = new Class[] {
			long.class, long.class, java.lang.String.class, double.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class, boolean.class, boolean.class, java.lang.String.class,
			java.io.File.class, java.util.Map.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleParameterTypes61 = new Class[] {
			long.class, long.class, java.lang.String.class, double.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleTranslationParameterTypes62 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.util.Locale.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, java.util.Map.class
		};
	private static final Class<?>[] _updateArticleTranslationParameterTypes63 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.util.Locale.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, java.util.Map.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateContentParameterTypes64 = new Class[] {
			long.class, java.lang.String.class, double.class,
			java.lang.String.class
		};
	private static final Class<?>[] _updateStatusParameterTypes65 = new Class[] {
			long.class, java.lang.String.class, double.class, int.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
}