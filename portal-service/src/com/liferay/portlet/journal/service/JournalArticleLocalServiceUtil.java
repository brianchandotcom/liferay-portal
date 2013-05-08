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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for JournalArticle. This utility wraps
 * {@link com.liferay.portlet.journal.service.impl.JournalArticleLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalService
 * @see com.liferay.portlet.journal.service.base.JournalArticleLocalServiceBaseImpl
 * @see com.liferay.portlet.journal.service.impl.JournalArticleLocalServiceImpl
 * @generated
 */
public class JournalArticleLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.journal.service.impl.JournalArticleLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the journal article to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticle the journal article
	* @return the journal article that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle addJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addJournalArticle(journalArticle);
	}

	/**
	* Creates a new journal article with the primary key. Does not add the journal article to the database.
	*
	* @param id the primary key for the new journal article
	* @return the new journal article
	*/
	public static com.liferay.portlet.journal.model.JournalArticle createJournalArticle(
		long id) {
		return getService().createJournalArticle(id);
	}

	/**
	* Deletes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal article
	* @return the journal article that was removed
	* @throws PortalException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle deleteJournalArticle(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteJournalArticle(id);
	}

	/**
	* Deletes the journal article from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticle the journal article
	* @return the journal article that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle deleteJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteJournalArticle(journalArticle);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchJournalArticle(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchJournalArticle(id);
	}

	/**
	* Returns the journal article with the primary key.
	*
	* @param id the primary key of the journal article
	* @return the journal article
	* @throws PortalException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getJournalArticle(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticle(id);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the journal article matching the UUID and group.
	*
	* @param uuid the journal article's UUID
	* @param groupId the primary key of the group
	* @return the matching journal article
	* @throws PortalException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getJournalArticleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getJournalArticles(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticles(start, end);
	}

	/**
	* Returns the number of journal articles.
	*
	* @return the number of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getJournalArticlesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticlesCount();
	}

	/**
	* Updates the journal article in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalArticle the journal article
	* @return the journal article that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateJournalArticle(journalArticle);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a web content article with additional parameters.
	*
	* @param userId the primary key of the web content article's creator/owner
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
	* @param version the web content article's version
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
	* @param smallImage whether the web content article has a small image
	* @param smallImageURL the web content article's small image URL
	* @param smallImageFile the web content article's small image file
	* @param images the web content's images
	* @param articleURL the web content article's accessible URL
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, expando bridge
	attributes, guest permissions, group permissions, asset category
	IDs, asset tag names, asset link entry IDs, the "urlTitle"
	attribute, and workflow actions for the web content article. Can
	also set whether to add the default guest and group permissions.
	* @return the web content article
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, long groupId, long folderId, long classNameId,
		long classPK, java.lang.String articleId, boolean autoArticleId,
		double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.lang.String layoutUuid, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		java.util.Map<java.lang.String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addArticle(userId, groupId, folderId, classNameId, classPK,
			articleId, autoArticleId, version, titleMap, descriptionMap,
			content, type, ddmStructureKey, ddmTemplateKey, layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallImageFile, images, articleURL,
			serviceContext);
	}

	/**
	* Adds a web content article.
	*
	* @param userId the primary key of the web content article's creator/owner
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @param titleMap the web content article's locales and localized titles
	* @param descriptionMap the web content article's locales and localized
	descriptions
	* @param content the HTML content wrapped in XML. For more information,
	see the content example in the class description for {@link
	JournalArticleLocalServiceImpl}.
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, expando bridge
	attributes, guest permissions, group permissions, asset category
	IDs, asset tag names, asset link entry IDs, the "urlTitle"
	attribute, and workflow actions for the web content article. Can
	also set whether to add the default guest and group permissions.
	* @return the web content article
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, long groupId, long folderId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addArticle(userId, groupId, folderId, titleMap,
			descriptionMap, content, ddmStructureKey, ddmTemplateKey,
			serviceContext);
	}

	/**
	* Adds the resources to the web content article.
	*
	* @param article the web content article
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	* @throws PortalException if no portal actions could be found associated
	with the web content article or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addArticleResources(article, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* Adds the model resources with the permissions to the web content article.
	*
	* @param article the web content article to add resources to
	* @param groupPermissions the group permissions to be added
	* @param guestPermissions the guest permissions to be added
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addArticleResources(article, groupPermissions, guestPermissions);
	}

	/**
	* Adds the resources to the most recently created web content article.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void addArticleResources(long groupId,
		java.lang.String articleId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addArticleResources(groupId, articleId, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* Adds the resources with the permissions to the most recently created web
	* content article.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param groupPermissions the group permissions to be added
	* @param guestPermissions the guest permissions to be added
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void addArticleResources(long groupId,
		java.lang.String articleId, java.lang.String[] groupPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addArticleResources(groupId, articleId, groupPermissions,
			guestPermissions);
	}

	/**
	* Returns the web content article with the group, article ID, and version.
	* This method checks for the article's resource primary key and, if not
	* found, creates a new one.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @return the matching web content article
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle checkArticleResourcePrimKey(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .checkArticleResourcePrimKey(groupId, articleId, version);
	}

	/**
	* Checks all web content articles by handling their expirations and sending
	* review notifications based on their current workflow.
	*
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void checkArticles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkArticles();
	}

	/**
	* Checks the web content article matching the group, article ID, and
	* version, replacing escaped newline and return characters with non-escaped
	* newline and return characters.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static void checkNewLine(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkNewLine(groupId, articleId, version);
	}

	/**
	* Checks the web content article matching the group, article ID, and
	* version for an associated structure. If no structure is associated,
	* return; otherwise check that the article and structure match.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @throws PortalException if a matching web content article could not be
	found, if the article's structure does not match it, or if a
	portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void checkStructure(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkStructure(groupId, articleId, version);
	}

	/**
	* Copies the web content article matching the group, article ID, and
	* version. This method creates a new article, extracting all the values
	* from the old one and updating its article ID.
	*
	* @param userId the primary key of the web content article's creator/owner
	* @param groupId the primary key of the web content article's group
	* @param oldArticleId the primary key of the old web content article
	* @param newArticleId the primary key of the new web content article
	* @param autoArticleId whether to auto-generate the web content article ID
	* @param version the web content article's version
	* @return the new web content article
	* @throws PortalException if a matching web content article could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle copyArticle(
		long userId, long groupId, java.lang.String oldArticleId,
		java.lang.String newArticleId, boolean autoArticleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .copyArticle(userId, groupId, oldArticleId, newArticleId,
			autoArticleId, version);
	}

	/**
	* Deletes the web content article and its resources.
	*
	* @param article the web content article
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticle(article);
	}

	/**
	* Deletes the web content article and its resources, optionally sending
	* email notifying denial of the article if it had not yet been approved.
	*
	* @param article the web content article
	* @param articleURL the web content article's accessible URL to include in
	email notifications (optionally <code>null</code>)
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the portlet preferences that include
	email information to notify recipients of the unapproved web
	content's denial.
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticle(article, articleURL, serviceContext);
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
	* @throws PortalException if a matching web content article could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteArticle(groupId, articleId, version, articleURL,
			serviceContext);
	}

	/**
	* Deletes all web content articles and their resources matching the group
	* and article ID, optionally sending email notifying denial of article if
	* it had not yet been approved.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param serviceContext the service context to be applied. Can set the
	portlet preferences that include email information to notify
	recipients of the unapproved web content article's denial.
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticle(long groupId, java.lang.String articleId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticle(groupId, articleId, serviceContext);
	}

	/**
	* Deletes all the group's web content articles and resources.
	*
	* @param groupId the primary key of the web content article's group
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticles(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticles(groupId);
	}

	/**
	* Deletes all the group's web content articles and resources in the folder,
	* including recycled articles.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticles(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticles(groupId, folderId);
	}

	/**
	* Deletes all the group's web content articles and resources in the folder,
	* optionally including recycled articles.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @param includeTrashedEntries whether to include recycled web content
	articles
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArticles(long groupId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticles(groupId, folderId, includeTrashedEntries);
	}

	/**
	* Deletes the layout's association with the web content articles for the
	* group.
	*
	* @param groupId the primary key of the web content article's group
	* @param layoutUuid the unique string identifying the web content
	article's display page
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayoutArticleReferences(long groupId,
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutArticleReferences(groupId, layoutUuid);
	}

	/**
	* Expires the web content article matching the group, article ID, and
	* version.
	*
	* @param userId the primary key of the user updating the web content
	article
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
	* @throws PortalException if a matching web content article could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle expireArticle(
		long userId, long groupId, java.lang.String articleId, double version,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .expireArticle(userId, groupId, articleId, version,
			articleURL, serviceContext);
	}

	/**
	* Expires the web content article matching the group and article ID,
	* expiring all of its versions if the
	* <code>journal.article.expire.all.versions</code> portal property is
	* <code>true</code>, otherwise expiring only its latest approved version.
	*
	* @param userId the primary key of the user updating the web content
	article
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
	* @throws PortalException if a matching web content article could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void expireArticle(long userId, long groupId,
		java.lang.String articleId, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.expireArticle(userId, groupId, articleId, articleURL,
			serviceContext);
	}

	/**
	* Returns the web content article matching the UUID and group.
	*
	* @param uuid the web content article's UUID
	* @param groupId the primary key of the web content article's group
	* @return the matching web content article
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle fetchArticle(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchArticle(uuid, groupId);
	}

	/**
	* Returns the web content article with the ID.
	*
	* @param id the primary key of the web content article
	* @return the web content article with the ID
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(id);
	}

	/**
	* Returns the latest approved web content article, or the latest unapproved
	* article if none are approved. Both approved and unapproved articles must
	* match the group and article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the matching web content article
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(groupId, articleId);
	}

	/**
	* Returns the web content article matching the group, article ID, and
	* version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @return the matching web content article
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(groupId, articleId, version);
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
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(groupId, className, classPK);
	}

	/**
	* Returns the latest web content article that is approved, or the latest
	* unapproved article if none are approved. Both approved and unapproved
	* articles must match the group and URL title.
	*
	* @param groupId the primary key of the web content article's group
	* @param urlTitle the web content article's accessible URL title
	* @return the matching web content article
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleByUrlTitle(groupId, urlTitle);
	}

	/**
	* Returns the web content associated with the web content article and DDM
	* template.
	*
	* @param article the web content article
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the web content associated with the DDM template
	* @throws PortalException if a matching DDM template could not be found or
	if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getArticleContent(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(article, ddmTemplateKey, viewMode,
			languageId, themeDisplay);
	}

	/**
	* Returns the web content matching the group, article ID, and version, and
	* associated with the DDM template.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param viewMode the mode in which the web content is being viewed
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the matching web content
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version, java.lang.String viewMode,
		java.lang.String ddmTemplateKey, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(groupId, articleId, version, viewMode,
			ddmTemplateKey, languageId, themeDisplay);
	}

	/**
	* Returns the web content matching the group, article ID, and version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the matching web content
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(groupId, articleId, version, viewMode,
			languageId, themeDisplay);
	}

	/**
	* Returns the latest web content matching the group and article ID, and
	* associated with DDM template key.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param viewMode the mode in which the web content is being viewed
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the matching web content
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String viewMode,
		java.lang.String ddmTemplateKey, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(groupId, articleId, viewMode,
			ddmTemplateKey, languageId, themeDisplay);
	}

	/**
	* Returns the latest web content matching the group and article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the matching web content
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(groupId, articleId, viewMode, languageId,
			themeDisplay);
	}

	/**
	* Returns a web content article display for the specified page of the
	* latest version of the web content article, optionally based on the DDM
	* template if the article is template driven. If the article is template
	* driven, web content transformation tokens are added from the theme
	* display (if not <code>null</code>) or the XML request otherwise.
	*
	* @param article the web content article
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param page the web content's page number. Page numbers start at
	<code>1</code>.
	* @param xmlRequest the request that serializes the web content into a
	hierarchical hash map (optionally <code>null</code>)
	* @param themeDisplay the theme display
	* @return the web content article display
	* @throws PortalException if a matching DDM template could not be found or
	if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(article, ddmTemplateKey, viewMode,
			languageId, page, xmlRequest, themeDisplay);
	}

	/**
	* Returns a web content article display for the first page of the specified
	* version of the web content article, optionally based on the DDM template
	* if the article is template driven. If the article is template driven, web
	* content transformation tokens are added from the theme display (if not
	* <code>null</code>) or the XML request otherwise.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param page the web content's page number
	* @param xmlRequest the request that serializes the web content into a
	hierarchical hash map
	* @param themeDisplay the theme display
	* @return the web content article display, or <code>null</code> if the
	article has expired or if article's display date/time is after
	the current date/time
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(groupId, articleId, version,
			ddmTemplateKey, viewMode, languageId, page, xmlRequest, themeDisplay);
	}

	/**
	* Returns a web content article display for the first page of the specified
	* version of the web content article matching the group and article ID,
	* optionally based on the DDM template if the article is template driven.
	* If the article is template driven, web content transformation tokens are
	* added from the theme display (if not <code>null</code>).
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the web content article display, or <code>null</code> if the
	article has expired or if article's display date/time is after
	the current date/time
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(groupId, articleId, version,
			ddmTemplateKey, viewMode, languageId, themeDisplay);
	}

	/**
	* Returns a web content article display for the first page of the latest
	* version of the web content article matching the group and article ID. If
	* the article is template driven, web content transformation tokens are
	* added from the theme display (if not <code>null</code>) or the XML
	* request otherwise.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param page the web content's page number
	* @param xmlRequest the request that serializes the web content into a
	hierarchical hash map
	* @param themeDisplay the theme display
	* @return the web content article display, or <code>null</code> if the
	article has expired or if article's display date/time is after
	the current date/time
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(groupId, articleId, viewMode, languageId,
			page, xmlRequest, themeDisplay);
	}

	/**
	* Returns a web content article display for the specified page of the
	* latest version of the web content article matching the group and article
	* ID, optionally based on the DDM template if the article is template
	* driven. If the article is template driven, web content transformation
	* tokens are added from the theme display (if not <code>null</code>) or the
	* XML request otherwise.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param page the web content's page number
	* @param xmlRequest the request that serializes the web content into a
	hierarchical hash map
	* @param themeDisplay the theme display
	* @return the web content article display, or <code>null</code> if the
	article has expired or if article's display date/time is after
	the current date/time
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(groupId, articleId, ddmTemplateKey,
			viewMode, languageId, page, xmlRequest, themeDisplay);
	}

	/**
	* Returns a web content article display for the first page of the latest
	* version of the web content article matching the group and article ID,
	* optionally based on the DDM template if the article is template driven.
	* If the article is template driven, web content transformation tokens are
	* added from the theme display (if not <code>null</code>).
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the web content article display, or <code>null</code> if the
	article has expired or if article's display date/time is after
	the current date/time
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(groupId, articleId, ddmTemplateKey,
			viewMode, languageId, themeDisplay);
	}

	/**
	* Returns a web content article display for the first page of the latest
	* version of the web content article matching the group and article ID. If
	* the article is template driven, web content transformation tokens are
	* added from the theme display (if not <code>null</code>).
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param viewMode the mode in which the web content is being viewed
	* @param languageId the primary key of the language translation to get
	* @param themeDisplay the theme display
	* @return the web content article display, or <code>null</code> if the
	article has expired or if article's display date/time is after
	the current date/time
	* @throws PortalException if a matching web content article or DDM template
	could not be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleDisplay(groupId, articleId, viewMode, languageId,
			themeDisplay);
	}

	/**
	* Returns all the web content articles present in the system.
	*
	* @return the web content articles present in the system
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles();
	}

	/**
	* Returns all the web content articles belonging to the group.
	*
	* @param groupId the primary key of the web content article's group
	* @return the web content articles belonging to the group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId);
	}

	/**
	* Returns a range of all the web content articles belonging to the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @return the range of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the web content articles belonging to the
	* group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, start, end, obc);
	}

	/**
	* Returns all the web content articles matching the group and folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, folderId);
	}

	/**
	* Returns a range of all the web content articles matching the group and
	* folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article's folder
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @return the range of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, folderId, start, end);
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
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article's folder
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param orderByComparator the comparator to order the web content
	articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticles(groupId, folderId, start, end, orderByComparator);
	}

	/**
	* Returns all the web content articles matching the group and article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, articleId);
	}

	/**
	* Returns all the web content articles matching the small image ID.
	*
	* @param smallImageId the primary key of the web content article's small
	image
	* @return the web content articles matching the small image ID
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesBySmallImageId(smallImageId);
	}

	/**
	* Returns the number of web content articles belonging to the group.
	*
	* @param groupId the primary key of the web content article's group
	* @return the number of web content articles belonging to the group
	* @throws SystemException if a system exception occurred
	*/
	public static int getArticlesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesCount(groupId);
	}

	/**
	* Returns the number of web content articles matching the group and folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article's folder
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getArticlesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesCount(groupId, folderId);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* company, version, and workflow status.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the web content article's company
	* @param version the web content article's version
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @return the range of matching web content articles ordered by article ID
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getCompanyArticles(
		long companyId, double version, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyArticles(companyId, version, status, start, end);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* company and workflow status.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the web content article's company
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @return the range of matching web content articles ordered by article ID
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getCompanyArticles(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyArticles(companyId, status, start, end);
	}

	/**
	* Returns the number of web content articles matching the company, version,
	* and workflow status.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the web content article's company
	* @param version the web content article's version
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getCompanyArticlesCount(long companyId, double version,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyArticlesCount(companyId, version, status, start,
			end);
	}

	/**
	* Returns the number of web content articles matching the company and
	* workflow status.
	*
	* @param companyId the primary key of the web content article's company
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getCompanyArticlesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyArticlesCount(companyId, status);
	}

	/**
	* Returns the matching web content article currently displayed or next to
	* be displayed if no article is currently displayed.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the matching web content article currently displayed, or the next
	one to be displayed if no version of the article is currently
	displayed
	* @throws PortalException if no approved matching web content articles
	could be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDisplayArticle(groupId, articleId);
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
	* @throws PortalException if no approved matching web content articles
	could be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getDisplayArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDisplayArticleByUrlTitle(groupId, urlTitle);
	}

	/**
	* Returns the latest web content article matching the resource primary key,
	* preferring articles with approved workflow status.
	*
	* @param resourcePrimKey the primary key of the resource instance
	* @return the latest web content article matching the resource primary key,
	preferring articles with approved workflow status
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(resourcePrimKey);
	}

	/**
	* Returns the latest web content article matching the resource primary key
	* and workflow status, preferring articles with approved workflow status.
	*
	* @param resourcePrimKey the primary key of the resource instance
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return the latest web content article matching the resource primary key
	and workflow status, preferring articles with approved workflow
	status
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(resourcePrimKey, status);
	}

	/**
	* Returns the latest web content article matching the resource primary key
	* and workflow status, optionally preferring articles with approved
	* workflow status.
	*
	* @param resourcePrimKey the primary key of the resource instance
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param preferApproved whether to prefer returning the latest matching
	article that has workflow status {@link
	WorkflowConstants#STATUS_APPROVED} over returning one that has a
	different status
	* @return the latest web content article matching the resource primary key
	and workflow status, optionally preferring articles with approved
	workflow status
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLatestArticle(resourcePrimKey, status, preferApproved);
	}

	/**
	* Returns the latest web content article with the group and article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the latest matching web content article
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(groupId, articleId);
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
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(groupId, articleId, status);
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
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(groupId, className, classPK);
	}

	/**
	* Returns the latest web content article matching the group, URL title, and
	* workflow status.
	*
	* @param groupId the primary key of the web content article's group
	* @param urlTitle the web content article's accessible URL title
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return the latest matching web content article
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticleByUrlTitle(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticleByUrlTitle(groupId, urlTitle, status);
	}

	/**
	* Returns the latest version number of the web content with the group and
	* article ID.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the latest version number of the matching web content
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static double getLatestVersion(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestVersion(groupId, articleId);
	}

	/**
	* Returns the latest version number of the web content with the group,
	* article ID, and workflow status.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return the latest version number of the matching web content
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static double getLatestVersion(long groupId,
		java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestVersion(groupId, articleId, status);
	}

	/**
	* Returns the number of web content articles that are not recycled.
	*
	* @param groupId the primary key of the web content article's group
	* @param folderId the primary key of the web content article folder
	* @return the number of web content articles that are not recycled
	* @throws SystemException if a system exception occurred
	*/
	public static int getNotInTrashArticlesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNotInTrashArticlesCount(groupId, folderId);
	}

	/**
	* Returns the web content articles matching the group and DDM structure
	* key.
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String ddmStructureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureArticles(groupId, ddmStructureKey);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group and DDM structure key.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String ddmStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStructureArticles(groupId, ddmStructureKey, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		java.lang.String[] ddmStructureKeys)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureArticles(ddmStructureKeys);
	}

	/**
	* Returns the number of web content articles matching the group and DDM
	* structure key.
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getStructureArticlesCount(long groupId,
		java.lang.String ddmStructureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureArticlesCount(groupId, ddmStructureKey);
	}

	/**
	* Returns the web content articles matching the group and DDM template key.
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @return the matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String ddmTemplateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplateArticles(groupId, ddmTemplateKey);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* group and DDM template key.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param obc the comparator to order the web content articles
	* @return the range of matching web content articles ordered by the
	comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String ddmTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getTemplateArticles(groupId, ddmTemplateKey, start, end, obc);
	}

	/**
	* Returns the number of web content articles matching the group and DDM
	* template key.
	*
	* @param groupId the primary key of the web content article's group
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @return the number of matching web content articles
	* @throws SystemException if a system exception occurred
	*/
	public static int getTemplateArticlesCount(long groupId,
		java.lang.String ddmTemplateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplateArticlesCount(groupId, ddmTemplateKey);
	}

	/**
	* Returns <code>true</code> if the specified web content article exists.
	*
	* @param groupId the primary key of the group
	* @param articleId the primary key of the web content article
	* @return <code>true</code> if the specified web content article exists;
	<code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasArticle(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasArticle(groupId, articleId);
	}

	/**
	* Returns <code>true</code> if the web content article, specified by group
	* and article ID, is the latest version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @return <code>true</code> if the specified web content article is the
	latest version; <code>false</code> otherwise
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean isLatestVersion(long groupId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isLatestVersion(groupId, articleId, version);
	}

	/**
	* Returns <code>true</code> if the web content article, specified by group,
	* article ID, and workflow status, is the latest version.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @return <code>true</code> if the specified web content article is the
	latest version; <code>false</code> otherwise
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean isLatestVersion(long groupId,
		java.lang.String articleId, double version, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isLatestVersion(groupId, articleId, version, status);
	}

	/**
	* Moves the web content article matching the group and article ID to a new
	* folder.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param newFolderId the primary key of the web content article's new
	folder
	* @return the updated web content article, which was moved to a new folder
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle moveArticle(
		long groupId, java.lang.String articleId, long newFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveArticle(groupId, articleId, newFolderId);
	}

	/**
	* Moves the web content article from the Recycle Bin to a new folder.
	*
	* @param userId the primary key of the user updating the web content
	article
	* @param groupId the primary key of the web content article's group
	* @param article the web content article
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
	Bin to a new folder
	* @throws PortalException if a trashed web content article with the primary
	key could not be found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle moveArticleFromTrash(
		long userId, long groupId,
		com.liferay.portlet.journal.model.JournalArticle article,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveArticleFromTrash(userId, groupId, article, newFolderId,
			serviceContext);
	}

	/**
	* Moves the latest version of the web content article matching the group
	* and article ID to the recycle bin.
	*
	* @param userId the primary key of the user updating the web content
	article
	* @param article the web content article
	* @return the updated web content article, which was moved to the Recycle
	Bin
	* @throws PortalException if the user did not have permission to move the
	article to the Recycle Bin or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle moveArticleToTrash(
		long userId, com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveArticleToTrash(userId, article);
	}

	/**
	* Moves the latest version of the web content article matching the group
	* and article ID to the recycle bin.
	*
	* @param userId the primary key of the user updating the web content
	article
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @return the moved web content article or <code>null</code> if no matching
	article was found
	* @throws PortalException if the user did not have permission to move the
	article to the Recycle Bin or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle moveArticleToTrash(
		long userId, long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveArticleToTrash(userId, groupId, articleId);
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
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .removeArticleLocale(groupId, articleId, version, languageId);
	}

	/**
	* Restores the web content article from the Recycle Bin.
	*
	* @param userId the primary key of the user restoring the web content
	article
	* @param article the web content article
	* @throws PortalException if the web content article with the primary key
	could not be found in the Recycle Bin, if the user did not have
	permission to restore the article, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void restoreArticleFromTrash(long userId,
		com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreArticleFromTrash(userId, article);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters without using the indexer, including a keywords parameter for
	* matching with the article's ID, title, description, and content, a DDM
	* structure key parameter, and a DDM template key parameter. It is
	* preferable to use the indexed version {@link #search(long, long, List,
	* long, String, String, String, LinkedHashMap, int, int, Sort)} instead of
	* this method wherever possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			keywords, version, type, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate, start, end, obc);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters without using the indexer, including keyword parameters for
	* article ID, title, description, and content, a DDM structure key
	* parameter, a DDM template key parameter, and an AND operator switch. It
	* is preferable to use the indexed version {@link #search(long, long, List,
	* long, String, String, String, String, String, String, String, String,
	* LinkedHashMap, boolean, int, int, Sort)} instead of this method wherever
	* possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, obc);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters without using the indexer, including keyword parameters for
	* article ID, title, description, and content, a DDM structure keys
	* (plural) parameter, a DDM template keys (plural) parameter, and an AND
	* operator switch.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type,
			ddmStructureKeys, ddmTemplateKeys, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, obc);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters using the indexer, including a keywords parameter for matching
	* an article's ID, title, description, or content, a DDM structure key
	* parameter, a DDM template key parameter, and a finder hash map parameter.
	* It is preferable to use this method instead of the non-indexed version
	* whenever possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param keywords the keywords (space separated), which may occur in the
	web content article ID, title, description, or content
	(optionally <code>null</code>). If the keywords value is not
	<code>null</code>, the search uses the OR operator in connecting
	query criteria; otherwise it uses the AND operator.
	* @param params the finder parameters (optionally <code>null</code>)
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param sort the field, type, and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching web content articles ordered by <code>sort</code>
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			ddmStructureKey, ddmTemplateKey, keywords, params, start, end, sort);
	}

	/**
	* Returns an ordered range of all the web content articles matching the
	* parameters using the indexer, including a keywords parameter for matching
	* an article's ID, title, description, or content, a DDM structure key
	* parameter, a DDM template key parameter, an AND operator switch, and
	* parameters for type, status, a finder hash map. It is preferable to use
	* this method instead of the non-indexed version whenever possible for
	* performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	* @param title the title keywords (space separated, optionally
	<code>null</code>)
	* @param description the description keywords (space separated, optionally
	<code>null</code>)
	* @param content the content keywords (space separated, optionally
	<code>null</code>)
	* @param type the web content article's type (optionally
	<code>null</code>)
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param ddmStructureKey the primary key of the web content article's DDM
	structure, if the article is related to a DDM structure, or
	<code>null</code> otherwise
	* @param ddmTemplateKey the primary key of the web content article's DDM
	template (optionally <code>null</code>). If the article is
	related to a DDM structure, the template's structure must match
	it.
	* @param params the finder parameters (optionally <code>null</code>). Can
	set parameter <code>"includeDiscussions"</code> to
	<code>true</code> to search for the keywords in the web content
	article discussions.
	* @param andSearch whether every field must match its value or keywords,
	or just one field must match
	* @param start the lower bound of the range of web content articles to
	return
	* @param end the upper bound of the range of web content articles to
	return (not inclusive)
	* @param sort the field, type, and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching web content articles ordered by <code>sort</code>
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String status,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			articleId, title, description, content, type, status,
			ddmStructureKey, ddmTemplateKey, params, andSearch, start, end, sort);
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
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, folderIds, classNameId,
			keywords, version, type, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate);
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
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate, andOperator);
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
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type,
			ddmStructureKeys, ddmTemplateKeys, displayDateGT, displayDateLT,
			status, reviewDate, andOperator);
	}

	/**
	* Subscribes the user to notifications for the web content article matching
	* the group, notifying him the instant versions of the article are created,
	* deleted, or modified.
	*
	* @param userId the primary key of the user to subscribe
	* @param groupId the primary key of the group
	* @throws PortalException if a matching user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribe(userId, groupId);
	}

	/**
	* Unsubscribes the user from notifications for the web content article
	* matching the group.
	*
	* @param userId the primary key of the user to unsubscribe
	* @param groupId the primary key of the group
	* @throws PortalException if a matching user or subscription could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribe(userId, groupId);
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
	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String layoutUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticle(userId, groupId, folderId, articleId,
			version, titleMap, descriptionMap, content, layoutUuid,
			serviceContext);
	}

	/**
	* Updates the web content article with additional parameters.
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
	* @param indexable whether the web content is searchable
	* @param smallImage whether to update web content article's a small image.
	A file must be passed in as <code>smallImageFile</code> value,
	otherwise the current small image is deleted.
	* @param smallImageURL the web content article's small image URL
	(optionally <code>null</code>)
	* @param smallImageFile the web content article's new small image file
	(optionally <code>null</code>). Must pass in
	<code>smallImage</code> value of <code>true</code> to replace the
	article's small image file.
	* @param images the web content's images (optionally <code>null</code>)
	* @param articleURL the web content article's accessible URL (optionally
	<code>null</code>)
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
	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.lang.String layoutUuid, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		java.util.Map<java.lang.String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticle(userId, groupId, folderId, articleId,
			version, titleMap, descriptionMap, content, type, ddmStructureKey,
			ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallImageFile, images, articleURL, serviceContext);
	}

	/**
	* Updates the web content article matching the version, replacing its
	* folder and content.
	*
	* @param userId the primary key of the user updating the web content
	article
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
	* @throws PortalException if a user with the primary key or a matching web
	content article could not be found, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version, java.lang.String content,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticle(userId, groupId, folderId, articleId,
			version, content, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#updateArticleTranslation(long, String, double, Locale,
	String, String, String, Map, ServiceContext)}
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateArticleTranslation(
		long groupId, java.lang.String articleId, double version,
		java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticleTranslation(groupId, articleId, version,
			locale, title, description, content, images);
	}

	/**
	* Updates the translation of the web content article.
	*
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param locale the locale of the web content article's display template
	* @param title the translated web content article title
	* @param description the translated web content article description
	* @param content the HTML content wrapped in XML. For more information,
	see the content example in the class description for {@link
	JournalArticleLocalServiceImpl}.
	* @param images the web content's images
	* @param serviceContext the service context to be applied. Can set the
	modification date and "urlTitle" attribute for the web content
	article.
	* @return the updated web content article
	* @throws PortalException if a user with the primary key or a matching web
	content article could not be found, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateArticleTranslation(
		long groupId, java.lang.String articleId, double version,
		java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticleTranslation(groupId, articleId, version,
			locale, title, description, content, images, serviceContext);
	}

	/**
	* Updates the web content article's asset with the new asset categories,
	* tag names, and link entries, removing and adding them as necessary.
	*
	* @param userId the primary key of the user updating the web content
	article's asset
	* @param article the web content article
	* @param assetCategoryIds the primary keys of the new asset categories
	* @param assetTagNames the new asset tag names
	* @param assetLinkEntryIds the primary keys of the new asset link entries
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void updateAsset(long userId,
		com.liferay.portlet.journal.model.JournalArticle article,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, article, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
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
	* @throws PortalException if a matching web content article could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateContent(groupId, articleId, version, content);
	}

	/**
	* Updates the workflow status of the web content article.
	*
	* @param userId the primary key of the user updating the web content
	article's status
	* @param article the web content article
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param articleURL the web content article's accessible URL
	* @param workflowContext the web content article's configured workflow
	context
	* @param serviceContext the service context to be applied. Can set the
	modification date, status date, and portlet preferences. With
	respect to social activities, by setting the service context's
	command to {@link
	com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	is considered a web content update activity; otherwise it is
	considered a web content add activity.
	* @return the updated web content article
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, com.liferay.portlet.journal.model.JournalArticle article,
		int status, java.lang.String articleURL,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, article, status, articleURL,
			workflowContext, serviceContext);
	}

	/**
	* Updates the workflow status of the web content article matching the class
	* PK.
	*
	* @param userId the primary key of the user updating the web content
	article's status
	* @param classPK the primary key of the DDM structure, if the web content
	article is related to a DDM structure, the primary key of the
	class associated with the article, or <code>0</code> otherwise
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param workflowContext the web content article's configured workflow
	* @param serviceContext the service context to be applied. Can set the
	modification date, portlet preferences, and can set whether to
	add the default command update for the web content article.
	* @return the updated web content article
	* @throws PortalException if a matching web content article could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, long classPK, int status,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, classPK, status, workflowContext,
			serviceContext);
	}

	/**
	* Updates the workflow status of the web content article matching the
	* group, article ID, and version.
	*
	* @param userId the primary key of the user updating the web content
	article's status
	* @param groupId the primary key of the web content article's group
	* @param articleId the primary key of the web content article
	* @param version the web content article's version
	* @param status the web content article's workflow status. For more
	information see {@link WorkflowConstants} for constants starting
	with the "STATUS_" prefix.
	* @param articleURL the web content article's accessible URL
	* @param workflowContext the web content article's configured workflow
	* @param serviceContext the service context to be applied. Can set the
	modification date, portlet preferences, and can set whether to
	add the default command update for the web content article.
	* @return the updated web content article
	* @throws PortalException if a matching web content article could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, long groupId, java.lang.String articleId, double version,
		int status, java.lang.String articleURL,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, groupId, articleId, version, status,
			articleURL, workflowContext, serviceContext);
	}

	/**
	* Updates the web content articles matching the group, class name ID, and
	* DDM template key, replacing the DDM template key with a new one.
	*
	* @param groupId the primary key of the web content article's group
	* @param classNameId the primary key of the DDMStructure class if the web
	content article is related to a DDM structure, the primary key of
	the class name associated with the article, or {@link
	JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	* @param oldDDMTemplateKey the primary key of the web content article's
	old DDM template
	* @param newDDMTemplateKey the primary key of the web content article's
	new DDM template
	* @throws SystemException if a system exception occurred
	*/
	public static void updateTemplateId(long groupId, long classNameId,
		java.lang.String oldDDMTemplateKey, java.lang.String newDDMTemplateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateTemplateId(groupId, classNameId, oldDDMTemplateKey,
			newDDMTemplateKey);
	}

	public static JournalArticleLocalService getService() {
		if (_service == null) {
			_service = (JournalArticleLocalService)PortalBeanLocatorUtil.locate(JournalArticleLocalService.class.getName());

			ReferenceRegistry.registerReference(JournalArticleLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(JournalArticleLocalService service) {
	}

	private static JournalArticleLocalService _service;
}