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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for JournalArticleImage. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImageLocalServiceUtil
 * @see com.liferay.journal.service.base.JournalArticleImageLocalServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalArticleImageLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface JournalArticleImageLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleImageLocalServiceUtil} to access the journal article image local service. Add custom service methods to {@link com.liferay.journal.service.impl.JournalArticleImageLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addArticleImageId(long articleImageId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) throws PortalException;

	/**
	* Adds the journal article image to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.journal.model.JournalArticleImage addJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage);

	/**
	* Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	*
	* @param articleImageId the primary key for the new journal article image
	* @return the new journal article image
	*/
	public com.liferay.journal.model.JournalArticleImage createJournalArticleImage(
		long articleImageId);

	public void deleteArticleImage(
		com.liferay.journal.model.JournalArticleImage articleImage);

	public void deleteArticleImage(long articleImageId);

	public void deleteArticleImage(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId);

	public void deleteImages(long groupId, java.lang.String articleId,
		double version);

	/**
	* Deletes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image that was removed
	* @throws PortalException if a journal article image with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.journal.model.JournalArticleImage deleteJournalArticleImage(
		long articleImageId) throws PortalException;

	/**
	* Deletes the journal article image from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.journal.model.JournalArticleImage deleteJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalArticleImage fetchJournalArticleImage(
		long articleImageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalArticleImage getArticleImage(
		long articleImageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean tempImage);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalArticleImage> getArticleImages(
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalArticleImage> getArticleImages(
		long groupId, java.lang.String articleId, double version);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getArticleImagesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the journal article image with the primary key.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image
	* @throws PortalException if a journal article image with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalArticleImage getJournalArticleImage(
		long articleImageId) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalArticleImage> getJournalArticleImages(
		int start, int end);

	/**
	* Returns the number of journal article images.
	*
	* @return the number of journal article images
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getJournalArticleImagesCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	/**
	* Updates the journal article image in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage);
}