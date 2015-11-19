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

package com.liferay.journal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link JournalArticleImageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImageLocalService
 * @generated
 */
@ProviderType
public class JournalArticleImageLocalServiceWrapper
	implements JournalArticleImageLocalService,
		ServiceWrapper<JournalArticleImageLocalService> {
	public JournalArticleImageLocalServiceWrapper(
		JournalArticleImageLocalService journalArticleImageLocalService) {
		_journalArticleImageLocalService = journalArticleImageLocalService;
	}

	@Override
	public void addArticleImageId(long articleImageId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalArticleImageLocalService.addArticleImageId(articleImageId,
			groupId, articleId, version, elInstanceId, elName, languageId);
	}

	/**
	* Adds the journal article image to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was added
	*/
	@Override
	public com.liferay.journal.model.JournalArticleImage addJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage) {
		return _journalArticleImageLocalService.addJournalArticleImage(journalArticleImage);
	}

	/**
	* Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	*
	* @param articleImageId the primary key for the new journal article image
	* @return the new journal article image
	*/
	@Override
	public com.liferay.journal.model.JournalArticleImage createJournalArticleImage(
		long articleImageId) {
		return _journalArticleImageLocalService.createJournalArticleImage(articleImageId);
	}

	@Override
	public void deleteArticleImage(
		com.liferay.journal.model.JournalArticleImage articleImage) {
		_journalArticleImageLocalService.deleteArticleImage(articleImage);
	}

	@Override
	public void deleteArticleImage(long articleImageId) {
		_journalArticleImageLocalService.deleteArticleImage(articleImageId);
	}

	@Override
	public void deleteArticleImage(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) {
		_journalArticleImageLocalService.deleteArticleImage(groupId, articleId,
			version, elInstanceId, elName, languageId);
	}

	@Override
	public void deleteImages(long groupId, java.lang.String articleId,
		double version) {
		_journalArticleImageLocalService.deleteImages(groupId, articleId,
			version);
	}

	/**
	* Deletes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image that was removed
	* @throws PortalException if a journal article image with the primary key could not be found
	*/
	@Override
	public com.liferay.journal.model.JournalArticleImage deleteJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticleImageLocalService.deleteJournalArticleImage(articleImageId);
	}

	/**
	* Deletes the journal article image from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was removed
	*/
	@Override
	public com.liferay.journal.model.JournalArticleImage deleteJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage) {
		return _journalArticleImageLocalService.deleteJournalArticleImage(journalArticleImage);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticleImageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _journalArticleImageLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _journalArticleImageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _journalArticleImageLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _journalArticleImageLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _journalArticleImageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _journalArticleImageLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.journal.model.JournalArticleImage fetchJournalArticleImage(
		long articleImageId) {
		return _journalArticleImageLocalService.fetchJournalArticleImage(articleImageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _journalArticleImageLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.journal.model.JournalArticleImage getArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticleImageLocalService.getArticleImage(articleImageId);
	}

	@Override
	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) {
		return _journalArticleImageLocalService.getArticleImageId(groupId,
			articleId, version, elInstanceId, elName, languageId);
	}

	@Override
	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean tempImage) {
		return _journalArticleImageLocalService.getArticleImageId(groupId,
			articleId, version, elInstanceId, elName, languageId, tempImage);
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalArticleImage> getArticleImages(
		long groupId) {
		return _journalArticleImageLocalService.getArticleImages(groupId);
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalArticleImage> getArticleImages(
		long groupId, java.lang.String articleId, double version) {
		return _journalArticleImageLocalService.getArticleImages(groupId,
			articleId, version);
	}

	@Override
	public int getArticleImagesCount(long groupId) {
		return _journalArticleImageLocalService.getArticleImagesCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _journalArticleImageLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the journal article image with the primary key.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image
	* @throws PortalException if a journal article image with the primary key could not be found
	*/
	@Override
	public com.liferay.journal.model.JournalArticleImage getJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticleImageLocalService.getJournalArticleImage(articleImageId);
	}

	/**
	* Returns a range of all the journal article images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of journal article images
	*/
	@Override
	public java.util.List<com.liferay.journal.model.JournalArticleImage> getJournalArticleImages(
		int start, int end) {
		return _journalArticleImageLocalService.getJournalArticleImages(start,
			end);
	}

	/**
	* Returns the number of journal article images.
	*
	* @return the number of journal article images
	*/
	@Override
	public int getJournalArticleImagesCount() {
		return _journalArticleImageLocalService.getJournalArticleImagesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _journalArticleImageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticleImageLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the journal article image in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was updated
	*/
	@Override
	public com.liferay.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage) {
		return _journalArticleImageLocalService.updateJournalArticleImage(journalArticleImage);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public JournalArticleImageLocalService getWrappedJournalArticleImageLocalService() {
		return _journalArticleImageLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedJournalArticleImageLocalService(
		JournalArticleImageLocalService journalArticleImageLocalService) {
		_journalArticleImageLocalService = journalArticleImageLocalService;
	}

	@Override
	public JournalArticleImageLocalService getWrappedService() {
		return _journalArticleImageLocalService;
	}

	@Override
	public void setWrappedService(
		JournalArticleImageLocalService journalArticleImageLocalService) {
		_journalArticleImageLocalService = journalArticleImageLocalService;
	}

	private JournalArticleImageLocalService _journalArticleImageLocalService;
}