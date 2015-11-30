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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for JournalArticleImage. This utility wraps
 * {@link com.liferay.journal.service.impl.JournalArticleImageLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImageLocalService
 * @see com.liferay.journal.service.base.JournalArticleImageLocalServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalArticleImageLocalServiceImpl
 * @generated
 */
@ProviderType
public class JournalArticleImageLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.journal.service.impl.JournalArticleImageLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addArticleImageId(long articleImageId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addArticleImageId(articleImageId, groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Adds the journal article image to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was added
	*/
	public static com.liferay.journal.model.JournalArticleImage addJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage) {
		return getService().addJournalArticleImage(journalArticleImage);
	}

	/**
	* Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	*
	* @param articleImageId the primary key for the new journal article image
	* @return the new journal article image
	*/
	public static com.liferay.journal.model.JournalArticleImage createJournalArticleImage(
		long articleImageId) {
		return getService().createJournalArticleImage(articleImageId);
	}

	public static void deleteArticleImage(
		com.liferay.journal.model.JournalArticleImage articleImage) {
		getService().deleteArticleImage(articleImage);
	}

	public static void deleteArticleImage(long articleImageId) {
		getService().deleteArticleImage(articleImageId);
	}

	public static void deleteArticleImage(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) {
		getService()
			.deleteArticleImage(groupId, articleId, version, elInstanceId,
			elName, languageId);
	}

	public static void deleteImages(long groupId, java.lang.String articleId,
		double version) {
		getService().deleteImages(groupId, articleId, version);
	}

	/**
	* Deletes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image that was removed
	* @throws PortalException if a journal article image with the primary key could not be found
	*/
	public static com.liferay.journal.model.JournalArticleImage deleteJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteJournalArticleImage(articleImageId);
	}

	/**
	* Deletes the journal article image from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was removed
	*/
	public static com.liferay.journal.model.JournalArticleImage deleteJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage) {
		return getService().deleteJournalArticleImage(journalArticleImage);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.journal.model.JournalArticleImage fetchJournalArticleImage(
		long articleImageId) {
		return getService().fetchJournalArticleImage(articleImageId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.journal.model.JournalArticleImage getArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getArticleImage(articleImageId);
	}

	public static long getArticleImageId(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) {
		return getService()
				   .getArticleImageId(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	public static long getArticleImageId(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean tempImage) {
		return getService()
				   .getArticleImageId(groupId, articleId, version,
			elInstanceId, elName, languageId, tempImage);
	}

	public static java.util.List<com.liferay.journal.model.JournalArticleImage> getArticleImages(
		long groupId) {
		return getService().getArticleImages(groupId);
	}

	public static java.util.List<com.liferay.journal.model.JournalArticleImage> getArticleImages(
		long groupId, java.lang.String articleId, double version) {
		return getService().getArticleImages(groupId, articleId, version);
	}

	public static int getArticleImagesCount(long groupId) {
		return getService().getArticleImagesCount(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the journal article image with the primary key.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image
	* @throws PortalException if a journal article image with the primary key could not be found
	*/
	public static com.liferay.journal.model.JournalArticleImage getJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getJournalArticleImage(articleImageId);
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
	public static java.util.List<com.liferay.journal.model.JournalArticleImage> getJournalArticleImages(
		int start, int end) {
		return getService().getJournalArticleImages(start, end);
	}

	/**
	* Returns the number of journal article images.
	*
	* @return the number of journal article images
	*/
	public static int getJournalArticleImagesCount() {
		return getService().getJournalArticleImagesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the journal article image in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalArticleImage the journal article image
	* @return the journal article image that was updated
	*/
	public static com.liferay.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.journal.model.JournalArticleImage journalArticleImage) {
		return getService().updateJournalArticleImage(journalArticleImage);
	}

	public static JournalArticleImageLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(JournalArticleImageLocalService service) {
	}

	private static ServiceTracker<JournalArticleImageLocalService, JournalArticleImageLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(JournalArticleImageLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<JournalArticleImageLocalService, JournalArticleImageLocalService>(bundle.getBundleContext(),
				JournalArticleImageLocalService.class, null);

		_serviceTracker.open();
	}
}