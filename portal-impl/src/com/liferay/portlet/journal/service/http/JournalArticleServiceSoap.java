/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.journal.service.JournalArticleServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.journal.model.JournalArticleSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.journal.model.JournalArticle}, that is translated to a
 * {@link com.liferay.portlet.journal.model.JournalArticleSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleServiceHttp
 * @see       com.liferay.portlet.journal.model.JournalArticleSoap
 * @see       com.liferay.portlet.journal.service.JournalArticleServiceUtil
 * @generated
 */
public class JournalArticleServiceSoap {
	/**
	* Adds a web content article without any images.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param classPK the primary key of the DDM structure, if the primary key
	of the DDMStructure class is given as the
	<code>classNameId</code> parameter, the primary key of the class
	associated with the web content article, or <code>0</code>
	otherwise
	* @param articleId the primary key of the web content article
	* @param autoArticleId whether to auto generate the web content article ID
	* @param titleMap the web content article's locales and localized titles
	* @param descriptionMap the web content article's locales and localized
	descriptions
	* @param content the HTML content wrapped in XML. For more information,
	see the content example in the class description for {@link
	JournalArticleLocalServiceImpl}.
	* @param type the structure's type, if the web content article is related
	to a DDM structure. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param layoutUuid the unique string identifying the web content
	article's display page
	* @param displayDateMonth the month the web content article is set to
	display
	* @param displayDateDay the calendar day the web content article is set to
	display
	* @param displayDateYear the year the web content article is set to
	display
	* @param displayDateHour the hour the web content article is set to
	display
	* @param displayDateMinute the minute the web content article is set to
	display
	* @param expirationDateMonth the month the web content article is set to
	expire
	* @param expirationDateDay the calendar day the web content article is set
	to expire
	* @param expirationDateYear the year the web content article is set to
	expire
	* @param expirationDateHour the hour the web content article is set to
	expire
	* @param expirationDateMinute the minute the web content article is set to
	expire
	* @param neverExpire whether the web content article is not set to auto
	expire
	* @param reviewDateMonth the month the web content article is set for
	review
	* @param reviewDateDay the calendar day the web content article is set for
	review
	* @param reviewDateYear the year the web content article is set for review
	* @param reviewDateHour the hour the web content article is set for review
	* @param reviewDateMinute the minute the web content article is set for
	review
	* @param neverReview whether the web content article is not set for review
	* @param indexable whether the web content article is searchable
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, expando bridge
	attributes, guest permissions, group permissions, asset category
	IDs, asset tag names, asset link entry IDs, the "urlTitle"
	attribute, and workflow actions for the web content article. Can
	also set whether to add the default guest and group permissions.
	* @return the web content article
	* @throws PortalException if the user did not have permission to add the
	web content article or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap addArticle(
		long groupId, long folderId, long classNameId, long classPK,
		java.lang.String articleId, boolean autoArticleId,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String content,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.lang.String layoutUuid,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.addArticle(groupId,
					folderId, classNameId, classPK, articleId, autoArticleId,
					titleMap, descriptionMap, content, type, ddmStructureKey,
					ddmTemplateKey, layoutUuid, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, indexable, articleURL,
					serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Copies the web content article matching the group, article ID, and
	* version. This method creates a new article, extracting all the values
	* from the old one and updating its article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param oldArticleId the primary key of the old web content article
	* @param newArticleId the primary key of the new web content article
	* @param autoArticleId whether to auto-generate the web content article ID
	* @param version the web content article's version
	* @return the new web content article
	* @throws PortalException if the user did not have permission to add the
	copy the web content article, if a matching web content article
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap copyArticle(
		long groupId, java.lang.String oldArticleId,
		java.lang.String newArticleId, boolean autoArticleId, double version)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.copyArticle(groupId,
					oldArticleId, newArticleId, autoArticleId, version);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the web content article and its resources matching the group,
	* article ID, and version, optionally sending email notifying denial of the
	* web content article if it had not yet been approved.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	portlet preferences that include email information to notify
	recipients of the unapproved web content article's denial.
	* @throws PortalException if the user did not have permission to delete the
	web content article, if a matching web content article could not
	be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			JournalArticleServiceUtil.deleteArticle(groupId, articleId,
				version, articleURL, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes all web content articles and their resources matching the group
	* and article ID, optionally sending email notifying denial of article if
	* it had not yet been approved.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	portlet preferences that include email information to notify
	recipients of the unapproved web content article's denial.
	* @throws PortalException if the user did not have permission to delete the
	web content article or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticle(long groupId, java.lang.String articleId,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			JournalArticleServiceUtil.deleteArticle(groupId, articleId,
				articleURL, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Expires the web content article matching the group, article ID, and
	* version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	modification date, status date, portlet preferences, and can set
	whether to add the default command update for the web content
	article. With respect to social activities, by setting the
	service context's command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @return the web content article
	* @throws PortalException if the user did not have permission to expire the
	web content article, if a matching web content article could not
	be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap expireArticle(
		long groupId, java.lang.String articleId, double version,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.expireArticle(groupId,
					articleId, version, articleURL, serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Expires the web content article matching the group and article ID,
	* expiring all of its versions if the
	* <code>journal.article.expire.all.versions</code> portal property is
	* <code>true</code>, otherwise expiring only its latest approved version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	modification date, status date, portlet preferences, and can set
	whether to add the default command update for the web content
	article. With respect to social activities, by setting the
	service context's command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @throws PortalException if the user did not have permission to expire the
	web content article, if a matching web content article could not
	be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void expireArticle(long groupId, java.lang.String articleId,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			JournalArticleServiceUtil.expireArticle(groupId, articleId,
				articleURL, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the web content article with the ID.
	*
	* @param id the primary key of the web content article
	* @return the web content article with the ID
	* @throws PortalException if a matching web content article could not be
	found or if the user did not have permission to view the web
	content article
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getArticle(
		long id) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getArticle(id);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the latest approved web content article, or the latest unapproved
	* article if none are approved. Both approved and unapproved articles must
	* match the group and article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the matching web content article
	* @throws PortalException if the user did not have permission to view the
	web content article or if a matching web content article could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getArticle(
		long groupId, java.lang.String articleId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getArticle(groupId,
					articleId);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the web content article matching the group, article ID, and
	* version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @return the matching web content article
	* @throws PortalException if the user did not have permission to view the
	web content article or if a matching web content article could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getArticle(
		long groupId, java.lang.String articleId, double version)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getArticle(groupId,
					articleId, version);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the web content article matching the group, class name, and class
	* PK.
	*
	* @param groupId the primary key of the web content article's group
	* @param className the DDMStructure class name if the web content article
	is related to a DDM structure, the primary key of the class name
	associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param classPK the primary key of the DDM structure, if the the
	DDMStructure class name is given as the <code>className</code>
	parameter, the primary key of the class associated with the web
	content article, or <code>0</code> otherwise
	* @return the matching web content article
	* @throws PortalException if a matching web content article could not be
	found or if the user did not have permission to view the web
	content article
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getArticle(
		long groupId, java.lang.String className, long classPK)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getArticle(groupId,
					className, classPK);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the latest web content article that is approved, or the latest
	* unapproved article if none are approved. Both approved and unapproved
	* articles must match the group and URL title.
	*
	* @param groupId the primary key of the web content article's group
	* @param urlTitle the web content article's accessible URL title
	* @return the matching web content article
	* @throws PortalException if the user did not have permission to view the
	web content article or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getArticleByUrlTitle(
		long groupId, java.lang.String urlTitle) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getArticleByUrlTitle(groupId,
					urlTitle);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the web content articles matching the group and folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getArticles(
		long groupId, long folderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getArticles(groupId, folderId);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group and folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getArticles(groupId, folderId, start,
					end, obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group and article ID.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getArticlesByArticleId(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getArticlesByArticleId(groupId,
					articleId, start, end, obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the web content articles matching the group and layout UUID.
	*
	* @param groupId the primary key of the web content article's group
	* @param layoutUuid the unique string identifying the web content
	article's display page
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getArticlesByLayoutUuid(
		long groupId, java.lang.String layoutUuid) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getArticlesByLayoutUuid(groupId,
					layoutUuid);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group, class name ID, DDM structure key, and workflow status.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getArticlesByStructureId(
		long groupId, long classNameId, java.lang.String ddmStructureKey,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getArticlesByStructureId(groupId,
					classNameId, ddmStructureKey, status, start, end, obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group, default class name ID, and DDM structure key.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getArticlesByStructureId(
		long groupId, java.lang.String ddmStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getArticlesByStructureId(groupId,
					ddmStructureKey, start, end, obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the group and folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getArticlesCount(long groupId, long folderId)
		throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.getArticlesCount(groupId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the group and article
	* ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getArticlesCountByArticleId(long groupId,
		java.lang.String articleId) throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.getArticlesCountByArticleId(groupId,
					articleId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the group, class name
	* ID, DDM structure key, and workflow status.
	*
	* @param groupId the primary key of the web content article's group
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getArticlesCountByStructureId(long groupId,
		long classNameId, java.lang.String ddmStructureKey, int status)
		throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.getArticlesCountByStructureId(groupId,
					classNameId, ddmStructureKey, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the group, default
	* class name ID, and DDM structure key.
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getArticlesCountByStructureId(long groupId,
		java.lang.String ddmStructureKey) throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.getArticlesCountByStructureId(groupId,
					ddmStructureKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the web content article matching the URL title that is currently
	* displayed or next to be displayed if no article is currently displayed.
	*
	* @param groupId the primary key of the web content article's group
	* @param urlTitle the web content article's accessible URL title
	* @return the web content article matching the URL title that is currently
	displayed, or next one to be displayed if no version of the
	article is currently displayed
	* @throws PortalException if the user did not have permission to view the
	web content article or if no approved matching web content
	articles could be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getDisplayArticleByUrlTitle(
		long groupId, java.lang.String urlTitle) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getDisplayArticleByUrlTitle(groupId,
					urlTitle);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of folders containing web content articles belonging
	* to the group.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @return the number of matching folders containing web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getFoldersAndArticlesCount(long groupId, Long[] folderIds)
		throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.getFoldersAndArticlesCount(groupId,
					ListUtil.toList(folderIds));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group, user, the root folder or any of its subfolders.
	*
	* @param groupId the primary key of the web content article's group
	* @param userId the primary key of the user (optionally <code>0</code>)
	* @param rootFolderId the primary key of the root folder to begin the
	search
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param orderByComparator the comparator to order the web content
	articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws PortalException if the root folder could not be found, if the
	current user did not have permission to view the root folder, or
	if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] getGroupArticles(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.getGroupArticles(groupId, userId,
					rootFolderId, start, end, orderByComparator);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the group, user, and
	* the root folder or any of its subfolders.
	*
	* @param groupId the primary key of the web content article's group
	* @param userId the primary key of the user (optionally <code>0</code>)
	* @param rootFolderId the primary key of the root folder to begin the
	search
	* @return the number of matching web content articles
	* @throws PortalException if the root folder could not be found, if the
	current user did not have permission to view the root folder, or
	if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static int getGroupArticlesCount(long groupId, long userId,
		long rootFolderId) throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.getGroupArticlesCount(groupId,
					userId, rootFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the latest web content article matching the resource primary key,
	* preferring articles with approved workflow status.
	*
	* @param resourcePrimKey the primary key of the resource instance
	* @return the latest web content article matching the resource primary key,
	preferring articles with approved workflow status
	* @throws PortalException if the user did not have permission to view the
	web content article or if a matching web content article could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getLatestArticle(
		long resourcePrimKey) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getLatestArticle(resourcePrimKey);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the latest web content article matching the group, article ID,
	* and workflow status.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return the latest matching web content article
	* @throws PortalException if the user did not have permission to view the
	web content article or if a matching web content article could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getLatestArticle(
		long groupId, java.lang.String articleId, int status)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getLatestArticle(groupId,
					articleId, status);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the latest web content article matching the group, class name ID,
	* and class PK.
	*
	* @param groupId the primary key of the web content article's group
	* @param className the DDMStructure class name if the web content article
	is related to a DDM structure, the class name associated with the
	article, or {@link JournalArticleConstants#CLASSNAME_ID_DEFAULT}
	otherwise
	* @param classPK the primary key of the DDM structure, if the DDMStructure
	class name is given as the <code>className</code> parameter, the
	primary key of the class associated with the web content article,
	or <code>0</code> otherwise
	* @return the latest matching web content article
	* @throws PortalException if a matching web content article could not be
	found or if the user did not have permission to view the web
	content article
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap getLatestArticle(
		long groupId, java.lang.String className, long classPK)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getLatestArticle(groupId,
					className, classPK);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves all versions of the the web content article matching the group and
	* article ID to the folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param newFolderId the primary key of the web content article's new
	folder
	* @throws PortalException if the user did not have permission to update any
	one of the versions of the web content article or if any one of
	the versions of the web content article could not be moved to the
	folder
	* @throws SystemException if a system exception occurred
	*/
	public static void moveArticle(long groupId, java.lang.String articleId,
		long newFolderId) throws RemoteException {
		try {
			JournalArticleServiceUtil.moveArticle(groupId, articleId,
				newFolderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves the web content article from the Recycle Bin to the folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param resourcePrimKey the primary key of the resource instance
	* @param newFolderId the primary key of the web content article's new
	folder
	* @param serviceContext the service context to be applied. Can set the
	modification date, portlet preferences, and can set whether to
	add the default command update for the web content article. With
	respect to social activities, by setting the service context's
	command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @return the updated web content article, which was moved from the Recycle
	Bin to the folder
	* @throws PortalException if the user did not have permission to view or
	update the web content article, if a matching trashed web content
	article could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap moveArticleFromTrash(
		long groupId, long resourcePrimKey, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.moveArticleFromTrash(groupId,
					resourcePrimKey, newFolderId, serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves the web content article from the Recycle Bin to the folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param newFolderId the primary key of the web content article's new
	folder
	* @param serviceContext the service context to be applied. Can set the
	modification date, portlet preferences, and can set whether to
	add the default command update for the web content article. With
	respect to social activities, by setting the service context's
	command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @return the updated web content article, which was moved from the Recycle
	Bin to the folder
	* @throws PortalException if the user did not have permission to view or
	update the web content article, if a trashed web content article
	with the primary key could not be found, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap moveArticleFromTrash(
		long groupId, java.lang.String articleId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.moveArticleFromTrash(groupId,
					articleId, newFolderId, serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves the latest version of the web content article matching the group
	* and article ID to the recycle bin.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the moved web content article or <code>null</code> if no matching
	article was found
	* @throws PortalException if the user did not have permission to move the
	article to the Recycle Bin or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap moveArticleToTrash(
		long groupId, java.lang.String articleId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.moveArticleToTrash(groupId,
					articleId);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the web content of all the company's web content articles
	* matching the language.
	*
	* @param companyId the primary key of the web content article's company
	* @param languageId the primary key of the language locale to remove
	* @throws PortalException if the user did not have permission to update any
	one of the the web content articles or if web content matching
	the language could not be found for any one of the articles
	* @throws SystemException if a system exception occurred
	*/
	public static void removeArticleLocale(long companyId,
		java.lang.String languageId) throws RemoteException {
		try {
			JournalArticleServiceUtil.removeArticleLocale(companyId, languageId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the web content of the web content article matching the group,
	* article ID, and version, and language.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param languageId the primary key of the language locale to remove
	* @return the updated web content article with the locale removed
	* @throws PortalException if the user did not have permission to update the
	web content article or if a matching web content article could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.removeArticleLocale(groupId,
					articleId, version, languageId);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Restores the web content article associated with the resource primary key
	* from the Recycle Bin.
	*
	* @param resourcePrimKey the primary key of the resource instance
	* @throws PortalException if a matching web content article could not be
	found in the Recycle Bin, if the user did not have permission to
	view or restore the article, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void restoreArticleFromTrash(long resourcePrimKey)
		throws RemoteException {
		try {
			JournalArticleServiceUtil.restoreArticleFromTrash(resourcePrimKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Restores the web content article from the Recycle Bin.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @throws PortalException if the web content article with the primary key
	could not be found in the Recycle Bin, if the user did not have
	permission to restore the article, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void restoreArticleFromTrash(long groupId,
		java.lang.String articleId) throws RemoteException {
		try {
			JournalArticleServiceUtil.restoreArticleFromTrash(groupId, articleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters, including a keywords parameter for matching with the
	* article's ID, title, description, and content, a DDM structure key
	* parameter, and a DDM template key parameter.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the web content article's company
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param keywords the keywords (space separated), which may occur in the
	web content article ID, title, description, or content
	(optionally <code>null</code>). If the keywords value is not
	<code>null</code>, the search uses the OR operator in connecting
	query criteria; otherwise it uses the AND operator.
	* @param version the web content article's version (optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param displayDateGT the date after which a matching web content
	article's display date must be after (optionally
	<code>null</code>)
	* @param displayDateLT the date before which a matching web content
	article's display date must be before (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param reviewDate the web content article's scheduled review date
	(optionally <code>null</code>)
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] search(
		long companyId, long groupId, Long[] folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.search(companyId, groupId,
					ListUtil.toList(folderIds), classNameId, keywords, version,
					type, ddmStructureKey, ddmTemplateKey, displayDateGT,
					displayDateLT, status, reviewDate, start, end, obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters, including keyword parameters for article ID, title,
	* description, and content, a DDM structure key parameter, a DDM template
	* key parameter, and an AND operator switch.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the web content article's company
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param articleId the article ID keywords (space separated, optionally
	<code>null</code>)
	* @param version the web content article's version (optionally
	<code>null</code>)
	* @param title the title keywords (space separated, optionally
	<code>null</code>)
	* @param description the description keywords (space separated, optionally
	<code>null</code>)
	* @param content the content keywords (space separated, optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param displayDateGT the date after which a matching web content
	article's display date must be after (optionally
	<code>null</code>)
	* @param displayDateLT the date before which a matching web content
	article's display date must be before (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param reviewDate the web content article's scheduled review date
	(optionally <code>null</code>)
	* @param andOperator whether every field must match its value or keywords,
	or just one field must match. Company, group, folder IDs, class
	name ID, and status must all match their values.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] search(
		long companyId, long groupId, Long[] folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.search(companyId, groupId,
					ListUtil.toList(folderIds), classNameId, articleId,
					version, title, description, content, type,
					ddmStructureKey, ddmTemplateKey, displayDateGT,
					displayDateLT, status, reviewDate, andOperator, start, end,
					obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters, including keyword parameters for article ID, title,
	* description, and content, a DDM structure keys (plural) parameter, a DDM
	* template keys (plural) parameter, and an AND operator switch.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the web content article's company
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param articleId the article ID keywords (space separated, optionally
	<code>null</code>)
	* @param version the web content article's version (optionally
	<code>null</code>)
	* @param title the title keywords (space separated, optionally
	<code>null</code>)
	* @param description the description keywords (space separated, optionally
	<code>null</code>)
	* @param content the content keywords (space separated, optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param ddmStructureKeys the primary keys of the web content article's
	DDM structures, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKeys the primary keys of the web content article's DDM
	templates (originally <code>null</code>). If the articles are
	related to a DDM structure, the template's structure must match
	it.
	* @param displayDateGT the date after which a matching web content
	article's display date must be after (optionally
	<code>null</code>)
	* @param displayDateLT the date before which a matching web content
	article's display date must be before (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param reviewDate the web content article's scheduled review date
	(optionally <code>null</code>)
	* @param andOperator whether every field must match its value or keywords,
	or just one field must match.  Company, group, folder IDs, class
	name ID, and status must all match their values.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap[] search(
		long companyId, long groupId, Long[] folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalArticle> returnValue =
				JournalArticleServiceUtil.search(companyId, groupId,
					ListUtil.toList(folderIds), classNameId, articleId,
					version, title, description, content, type,
					ddmStructureKeys, ddmTemplateKeys, displayDateGT,
					displayDateLT, status, reviewDate, andOperator, start, end,
					obc);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the parameters,
	* including a keywords parameter for matching with the article's ID, title,
	* description, and content, a DDM structure key parameter, and a DDM
	* template key parameter.
	*
	* @param companyId the primary key of the web content article's company
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param keywords the keywords (space separated), which may occur in the
	web content article ID, title, description, or content
	(optionally <code>null</code>). If the keywords value is not
	<code>null</code>, the search uses the OR operator in connecting
	query criteria; otherwise it uses the AND operator.
	* @param version the web content article's version (optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param displayDateGT the date after which a matching web content
	article's display date must be after (optionally
	<code>null</code>)
	* @param displayDateLT the date before which a matching web content
	article's display date must be before (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param reviewDate the web content article's scheduled review date
	(optionally <code>null</code>)
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int searchCount(long companyId, long groupId,
		Long[] folderIds, long classNameId, java.lang.String keywords,
		java.lang.Double version, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate) throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.searchCount(companyId,
					groupId, ListUtil.toList(folderIds), classNameId, keywords,
					version, type, ddmStructureKey, ddmTemplateKey,
					displayDateGT, displayDateLT, status, reviewDate);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the parameters,
	* including keyword parameters for article ID, title, description, and
	* content, a DDM structure key parameter, a DDM template key parameter, and
	* an AND operator switch.
	*
	* @param companyId the primary key of the web content article's company
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param articleId the article ID keywords (space separated, optionally
	<code>null</code>)
	* @param version the web content article's version (optionally
	<code>null</code>)
	* @param title the title keywords (space separated, optionally
	<code>null</code>)
	* @param description the description keywords (space separated, optionally
	<code>null</code>)
	* @param content the content keywords (space separated, optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param displayDateGT the date after which a matching web content
	article's display date must be after (optionally
	<code>null</code>)
	* @param displayDateLT the date before which a matching web content
	article's display date must be before (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param reviewDate the web content article's scheduled review date
	(optionally <code>null</code>)
	* @param andOperator whether every field must match its value or keywords,
	or just one field must match. Group, folder IDs, class name ID,
	and status must all match their values.
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int searchCount(long companyId, long groupId,
		Long[] folderIds, long classNameId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator) throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.searchCount(companyId,
					groupId, ListUtil.toList(folderIds), classNameId,
					articleId, version, title, description, content, type,
					ddmStructureKey, ddmTemplateKey, displayDateGT,
					displayDateLT, status, reviewDate, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of web content articles matching the parameters,
	* including keyword parameters for article ID, title, description, and
	* content, a DDM structure keys (plural) parameter, a DDM template keys
	* (plural) parameter, and an AND operator switch.
	*
	* @param companyId the primary key of the web content article's company
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param folderIds the primary keys of the web content article folders
	(optionally {@link java.util.Collections#EMPTY_LIST})
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param articleId the article ID keywords (space separated, optionally
	<code>null</code>)
	* @param version the web content article's version (optionally
	<code>null</code>)
	* @param title the title keywords (space separated, optionally
	<code>null</code>)
	* @param description the description keywords (space separated, optionally
	<code>null</code>)
	* @param content the content keywords (space separated, optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param ddmStructureKeys the primary keys of the web content article's
	DDM structures, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKeys the primary keys of the web content article's DDM
	templates (originally <code>null</code>). If the articles are
	related to a DDM structure, the template's structure must match
	it.
	* @param displayDateGT the date after which a matching web content
	article's display date must be after (optionally
	<code>null</code>)
	* @param displayDateLT the date before which a matching web content
	article's display date must be before (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param reviewDate the web content article's scheduled review date
	(optionally <code>null</code>)
	* @param andOperator whether every field must match its value or keywords,
	or just one field must match.  Group, folder IDs, class name ID,
	and status must all match their values.
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int searchCount(long companyId, long groupId,
		Long[] folderIds, long classNameId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator) throws RemoteException {
		try {
			int returnValue = JournalArticleServiceUtil.searchCount(companyId,
					groupId, ListUtil.toList(folderIds), classNameId,
					articleId, version, title, description, content, type,
					ddmStructureKeys, ddmTemplateKeys, displayDateGT,
					displayDateLT, status, reviewDate, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Subscribes the user to notifications for the web content article matching
	* the group, notifying him the instant versions of the article are created,
	* deleted, or modified.
	*
	* @param groupId the primary key of the group
	* @throws PortalException if the user did not have permission to subscribe
	to the web content article or if a matching user or group could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void subscribe(long groupId) throws RemoteException {
		try {
			JournalArticleServiceUtil.subscribe(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Unsubscribes the user from notifications for the web content article
	* matching the group.
	*
	* @param groupId the primary key of the group
	* @throws PortalException if the user did not have permission to subscribe
	to the web content article or if a matching user or subscription
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void unsubscribe(long groupId) throws RemoteException {
		try {
			JournalArticleServiceUtil.unsubscribe(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the web content article matching the version, replacing its
	* folder, title, description, content, and layout UUID.
	*
	* @param userId the primary key of the user updating the web content
	article
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param titleMap the web content article's locales and localized titles
	* @param descriptionMap the web content article's locales and localized
	descriptions
	* @param content the HTML content wrapped in XML. For more information,
	see the content example in the class description for {@link
	JournalArticleLocalServiceImpl}.
	* @param layoutUuid the unique string identifying the web content
	article's display page
	* @param serviceContext the service context to be applied. Can set the
	modification date, expando bridge attributes, asset category IDs,
	asset tag names, asset link entry IDs, workflow actions, the
	"defaultLanguageId" and "urlTitle" attributes, and can set
	whether to add the default command update for the web content
	article. With respect to social activities, by setting the
	service context's command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @return the updated web content article
	* @throws PortalException if a user with the primary key or a matching web
	content article could not be found, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String content,
		java.lang.String layoutUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.updateArticle(userId,
					groupId, folderId, articleId, version, titleMap,
					descriptionMap, content, layoutUuid, serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the web content article matching the version, replacing its
	* folder and content.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param content the HTML content wrapped in XML. For more information,
	see the content example in the class description for {@link
	JournalArticleLocalServiceImpl}.
	* @param serviceContext the service context to be applied. Can set the
	modification date, expando bridge attributes, asset category IDs,
	asset tag names, asset link entry IDs, workflow actions, the
	"defaultLanguageId" and "urlTitle" attributes, and can set
	whether to add the default command update for the web content
	article. With respect to social activities, by setting the
	service context's command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @return the updated web content article
	* @throws PortalException if the user did not have permission to update the
	web content article, if a user with the primary key or a matching
	web content article could not be found, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap updateArticle(
		long groupId, long folderId, java.lang.String articleId,
		double version, java.lang.String content,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.updateArticle(groupId,
					folderId, articleId, version, content, serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the web content article matching the group, article ID, and
	* version, replacing its content.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param content the HTML content wrapped in XML. For more information,
	see the content example in the class description for {@link
	JournalArticleLocalServiceImpl}.
	* @return the updated web content article
	* @throws PortalException if the user did not have permission to update the
	web content article or if a matching web content article could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.updateContent(groupId,
					articleId, version, content);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the workflow status of the web content article matching the
	* group, article ID, and version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	modification date, portlet preferences, and can set whether to
	add the default command update for the web content article.
	* @return the updated web content article
	* @throws PortalException if the user did not have permission to update the
	web content article, if a matching web content article could not
	be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleSoap updateStatus(
		long groupId, java.lang.String articleId, double version, int status,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.updateStatus(groupId,
					articleId, version, status, articleURL, serviceContext);

			return com.liferay.portlet.journal.model.JournalArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticleServiceSoap.class);
}