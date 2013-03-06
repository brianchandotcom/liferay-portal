/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleLocalService
 * @generated
 */
public class JournalArticleLocalServiceWrapper
	implements JournalArticleLocalService,
		ServiceWrapper<JournalArticleLocalService> {
	public JournalArticleLocalServiceWrapper(
		JournalArticleLocalService journalArticleLocalService) {
		_journalArticleLocalService = journalArticleLocalService;
	}

	/**
	* Adds the journal article to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticle the journal article
	* @return the journal article that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle addJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.addJournalArticle(journalArticle);
	}

	/**
	* Creates a new journal article with the primary key. Does not add the journal article to the database.
	*
	* @param id the primary key for the new journal article
	* @return the new journal article
	*/
	public com.liferay.portlet.journal.model.JournalArticle createJournalArticle(
		long id) {
		return _journalArticleLocalService.createJournalArticle(id);
	}

	/**
	* Deletes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal article
	* @return the journal article that was removed
	* @throws PortalException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle deleteJournalArticle(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.deleteJournalArticle(id);
	}

	/**
	* Deletes the journal article from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticle the journal article
	* @return the journal article that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle deleteJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.deleteJournalArticle(journalArticle);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _journalArticleLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.journal.model.JournalArticle fetchJournalArticle(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.fetchJournalArticle(id);
	}

	/**
	* Returns the journal article with the primary key.
	*
	* @param id the primary key of the journal article
	* @return the journal article
	* @throws PortalException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle getJournalArticle(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getJournalArticle(id);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the journal article with the UUID in the group.
	*
	* @param uuid the UUID of journal article
	* @param groupId the group id of the journal article
	* @return the journal article
	* @throws PortalException if a journal article with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle getJournalArticleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getJournalArticleByUuidAndGroupId(uuid,
			groupId);
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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getJournalArticles(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getJournalArticles(start, end);
	}

	/**
	* Returns the number of journal articles.
	*
	* @return the number of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int getJournalArticlesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getJournalArticlesCount();
	}

	/**
	* Updates the journal article in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalArticle the journal article
	* @return the journal article that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle updateJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _journalArticleLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalArticleLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
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
		return _journalArticleLocalService.addArticle(userId, groupId,
			folderId, classNameId, classPK, articleId, autoArticleId, version,
			titleMap, descriptionMap, content, type, ddmStructureKey,
			ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallImageFile, images, articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, long groupId, long folderId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.addArticle(userId, groupId,
			folderId, titleMap, descriptionMap, content, ddmStructureKey,
			ddmTemplateKey, serviceContext);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.addArticleResources(article,
			addGroupPermissions, addGuestPermissions);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.addArticleResources(article,
			groupPermissions, guestPermissions);
	}

	public void addArticleResources(long groupId, java.lang.String articleId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.addArticleResources(groupId, articleId,
			addGroupPermissions, addGuestPermissions);
	}

	public void addArticleResources(long groupId, java.lang.String articleId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.addArticleResources(groupId, articleId,
			groupPermissions, guestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalArticle checkArticleResourcePrimKey(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.checkArticleResourcePrimKey(groupId,
			articleId, version);
	}

	public void checkArticles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.checkArticles();
	}

	public void checkNewLine(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.checkNewLine(groupId, articleId, version);
	}

	public void checkStructure(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.checkStructure(groupId, articleId, version);
	}

	public com.liferay.portlet.journal.model.JournalArticle copyArticle(
		long userId, long groupId, java.lang.String oldArticleId,
		java.lang.String newArticleId, boolean autoArticleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.copyArticle(userId, groupId,
			oldArticleId, newArticleId, autoArticleId, version);
	}

	public void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticle(article);
	}

	public void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticle(article, articleURL,
			serviceContext);
	}

	public void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticle(groupId, articleId, version,
			articleURL, serviceContext);
	}

	public void deleteArticle(long groupId, java.lang.String articleId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticle(groupId, articleId,
			serviceContext);
	}

	public void deleteArticles(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticles(groupId);
	}

	public void deleteArticles(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticles(groupId, folderId);
	}

	public void deleteArticles(long groupId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteArticles(groupId, folderId,
			includeTrashedEntries);
	}

	public void deleteLayoutArticleReferences(long groupId,
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.deleteLayoutArticleReferences(groupId,
			layoutUuid);
	}

	public com.liferay.portlet.journal.model.JournalArticle expireArticle(
		long userId, long groupId, java.lang.String articleId, double version,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.expireArticle(userId, groupId,
			articleId, version, articleURL, serviceContext);
	}

	public void expireArticle(long userId, long groupId,
		java.lang.String articleId, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.expireArticle(userId, groupId, articleId,
			articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle fetchArticle(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.fetchArticle(uuid, groupId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticle(id);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticle(groupId, articleId,
			version);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticle(groupId, className,
			classPK);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleByUrlTitle(groupId,
			urlTitle);
	}

	public java.lang.String getArticleContent(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleContent(article,
			ddmTemplateKey, viewMode, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version, java.lang.String viewMode,
		java.lang.String ddmTemplateKey, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, version, viewMode, ddmTemplateKey, languageId,
			themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, version, viewMode, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String viewMode,
		java.lang.String ddmTemplateKey, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, viewMode, ddmTemplateKey, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, viewMode, languageId, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(article,
			ddmTemplateKey, viewMode, languageId, page, xmlRequest, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, version, ddmTemplateKey, viewMode, languageId, page,
			xmlRequest, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, version, ddmTemplateKey, viewMode, languageId,
			themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, viewMode, languageId, page, xmlRequest, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, ddmTemplateKey, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId,
		java.lang.String ddmTemplateKey, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, ddmTemplateKey, viewMode, languageId, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, viewMode, languageId, themeDisplay);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId, folderId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId, folderId,
			start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticles(groupId, articleId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticlesBySmallImageId(smallImageId);
	}

	public int getArticlesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticlesCount(groupId);
	}

	public int getArticlesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getArticlesCount(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getCompanyArticles(
		long companyId, double version, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getCompanyArticles(companyId,
			version, status, start, end);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getCompanyArticles(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getCompanyArticles(companyId,
			status, start, end);
	}

	public int getCompanyArticlesCount(long companyId, double version,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getCompanyArticlesCount(companyId,
			version, status, start, end);
	}

	public int getCompanyArticlesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getCompanyArticlesCount(companyId,
			status);
	}

	public com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getDisplayArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getDisplayArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getDisplayArticleByUrlTitle(groupId,
			urlTitle);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticle(resourcePrimKey);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticle(resourcePrimKey,
			status);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticle(resourcePrimKey,
			status, preferApproved);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticle(groupId, articleId,
			status);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticle(groupId, className,
			classPK);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticleByUrlTitle(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestArticleByUrlTitle(groupId,
			urlTitle, status);
	}

	public double getLatestVersion(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestVersion(groupId, articleId);
	}

	public double getLatestVersion(long groupId, java.lang.String articleId,
		int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getLatestVersion(groupId, articleId,
			status);
	}

	public int getNotInTrashArticlesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getNotInTrashArticlesCount(groupId,
			folderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String ddmStructureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getStructureArticles(groupId,
			ddmStructureKey);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String ddmStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getStructureArticles(groupId,
			ddmStructureKey, start, end, obc);
	}

	public int getStructureArticlesCount(long groupId,
		java.lang.String ddmStructureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getStructureArticlesCount(groupId,
			ddmStructureKey);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String ddmTemplateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getTemplateArticles(groupId,
			ddmTemplateKey);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String ddmTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getTemplateArticles(groupId,
			ddmTemplateKey, start, end, obc);
	}

	public int getTemplateArticlesCount(long groupId,
		java.lang.String ddmTemplateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.getTemplateArticlesCount(groupId,
			ddmTemplateKey);
	}

	public boolean hasArticle(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.hasArticle(groupId, articleId);
	}

	public boolean isLatestVersion(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.isLatestVersion(groupId, articleId,
			version);
	}

	public boolean isLatestVersion(long groupId, java.lang.String articleId,
		double version, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.isLatestVersion(groupId, articleId,
			version, status);
	}

	public com.liferay.portlet.journal.model.JournalArticle moveArticle(
		long groupId, java.lang.String articleId, long newFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.moveArticle(groupId, articleId,
			newFolderId);
	}

	public com.liferay.portlet.journal.model.JournalArticle moveArticleFromTrash(
		long userId, long groupId,
		com.liferay.portlet.journal.model.JournalArticle article,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.moveArticleFromTrash(userId,
			groupId, article, newFolderId, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle moveArticleToTrash(
		long userId, com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.moveArticleToTrash(userId, article);
	}

	public com.liferay.portlet.journal.model.JournalArticle moveArticleToTrash(
		long userId, long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.moveArticleToTrash(userId, groupId,
			articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.removeArticleLocale(groupId,
			articleId, version, languageId);
	}

	public void restoreArticleFromTrash(long userId,
		com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.restoreArticleFromTrash(userId, article);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.search(companyId, groupId,
			folderIds, classNameId, keywords, version, type, ddmStructureKey,
			ddmTemplateKey, displayDateGT, displayDateLT, status, reviewDate,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.search(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, type, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, status, reviewDate, andOperator, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
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
		return _journalArticleLocalService.search(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, type, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, status, reviewDate, andOperator, start, end, obc);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.search(companyId, groupId,
			folderIds, classNameId, ddmStructureKey, ddmTemplateKey, keywords,
			params, start, end, sort);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String status,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.search(companyId, groupId,
			folderIds, classNameId, articleId, title, description, content,
			type, status, ddmStructureKey, ddmTemplateKey, params, andSearch,
			start, end, sort);
	}

	public int searchCount(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.searchCount(companyId, groupId,
			folderIds, classNameId, keywords, version, type, ddmStructureKey,
			ddmTemplateKey, displayDateGT, displayDateLT, status, reviewDate);
	}

	public int searchCount(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.searchCount(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, type, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, status, reviewDate, andOperator);
	}

	public int searchCount(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.searchCount(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, type, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, status, reviewDate, andOperator);
	}

	public void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.subscribe(userId, groupId);
	}

	public void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.unsubscribe(userId, groupId);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String layoutUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateArticle(userId, groupId,
			folderId, articleId, version, titleMap, descriptionMap, content,
			layoutUuid, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
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
		return _journalArticleLocalService.updateArticle(userId, groupId,
			folderId, articleId, version, titleMap, descriptionMap, content,
			type, ddmStructureKey, ddmTemplateKey, layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallImageFile, images, articleURL,
			serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version, java.lang.String content,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateArticle(userId, groupId,
			folderId, articleId, version, content, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#updateArticleTranslation(long, String, double, Locale,
	String, String, String, Map, ServiceContext)}
	*/
	public com.liferay.portlet.journal.model.JournalArticle updateArticleTranslation(
		long groupId, java.lang.String articleId, double version,
		java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateArticleTranslation(groupId,
			articleId, version, locale, title, description, content, images);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticleTranslation(
		long groupId, java.lang.String articleId, double version,
		java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateArticleTranslation(groupId,
			articleId, version, locale, title, description, content, images,
			serviceContext);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.journal.model.JournalArticle article,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.updateAsset(userId, article,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateContent(groupId, articleId,
			version, content);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, com.liferay.portlet.journal.model.JournalArticle article,
		int status, java.lang.String articleURL,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateStatus(userId, article,
			status, articleURL, workflowContext, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, long classPK, int status,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateStatus(userId, classPK,
			status, workflowContext, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, long groupId, java.lang.String articleId, double version,
		int status, java.lang.String articleURL,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleLocalService.updateStatus(userId, groupId,
			articleId, version, status, articleURL, workflowContext,
			serviceContext);
	}

	public void updateTemplateId(long groupId, long classNameId,
		java.lang.String oldDDMTemplateKey, java.lang.String newDDMTemplateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleLocalService.updateTemplateId(groupId, classNameId,
			oldDDMTemplateKey, newDDMTemplateKey);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public JournalArticleLocalService getWrappedJournalArticleLocalService() {
		return _journalArticleLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {
		_journalArticleLocalService = journalArticleLocalService;
	}

	public JournalArticleLocalService getWrappedService() {
		return _journalArticleLocalService;
	}

	public void setWrappedService(
		JournalArticleLocalService journalArticleLocalService) {
		_journalArticleLocalService = journalArticleLocalService;
	}

	private JournalArticleLocalService _journalArticleLocalService;
}