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

package com.liferay.html.preview.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link HtmlPreviewEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryLocalService
 * @generated
 */
public class HtmlPreviewEntryLocalServiceWrapper
	implements HtmlPreviewEntryLocalService,
			   ServiceWrapper<HtmlPreviewEntryLocalService> {

	public HtmlPreviewEntryLocalServiceWrapper(
		HtmlPreviewEntryLocalService htmlPreviewEntryLocalService) {

		_htmlPreviewEntryLocalService = htmlPreviewEntryLocalService;
	}

	/**
	 * Adds the html preview entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 * @return the html preview entry that was added
	 */
	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry addHtmlPreviewEntry(
		com.liferay.html.preview.model.HtmlPreviewEntry htmlPreviewEntry) {

		return _htmlPreviewEntryLocalService.addHtmlPreviewEntry(
			htmlPreviewEntry);
	}

	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry addHtmlPreviewEntry(
			long userId, long groupId, long classNameId, long classPK,
			String content, String mimeType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.addHtmlPreviewEntry(
			userId, groupId, classNameId, classPK, content, mimeType,
			serviceContext);
	}

	/**
	 * Creates a new html preview entry with the primary key. Does not add the html preview entry to the database.
	 *
	 * @param htmlPreviewEntryId the primary key for the new html preview entry
	 * @return the new html preview entry
	 */
	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry
		createHtmlPreviewEntry(long htmlPreviewEntryId) {

		return _htmlPreviewEntryLocalService.createHtmlPreviewEntry(
			htmlPreviewEntryId);
	}

	/**
	 * Deletes the html preview entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 * @return the html preview entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry
			deleteHtmlPreviewEntry(
				com.liferay.html.preview.model.HtmlPreviewEntry
					htmlPreviewEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.deleteHtmlPreviewEntry(
			htmlPreviewEntry);
	}

	/**
	 * Deletes the html preview entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry that was removed
	 * @throws PortalException if a html preview entry with the primary key could not be found
	 */
	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry
			deleteHtmlPreviewEntry(long htmlPreviewEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.deleteHtmlPreviewEntry(
			htmlPreviewEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _htmlPreviewEntryLocalService.dynamicQuery();
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

		return _htmlPreviewEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl</code>.
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

		return _htmlPreviewEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl</code>.
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

		return _htmlPreviewEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _htmlPreviewEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _htmlPreviewEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry
		fetchHtmlPreviewEntry(long htmlPreviewEntryId) {

		return _htmlPreviewEntryLocalService.fetchHtmlPreviewEntry(
			htmlPreviewEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _htmlPreviewEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @return the range of html preview entries
	 */
	@Override
	public java.util.List<com.liferay.html.preview.model.HtmlPreviewEntry>
		getHtmlPreviewEntries(int start, int end) {

		return _htmlPreviewEntryLocalService.getHtmlPreviewEntries(start, end);
	}

	/**
	 * Returns the number of html preview entries.
	 *
	 * @return the number of html preview entries
	 */
	@Override
	public int getHtmlPreviewEntriesCount() {
		return _htmlPreviewEntryLocalService.getHtmlPreviewEntriesCount();
	}

	/**
	 * Returns the html preview entry with the primary key.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry
	 * @throws PortalException if a html preview entry with the primary key could not be found
	 */
	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry getHtmlPreviewEntry(
			long htmlPreviewEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.getHtmlPreviewEntry(
			htmlPreviewEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _htmlPreviewEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _htmlPreviewEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the html preview entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 * @return the html preview entry that was updated
	 */
	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry
		updateHtmlPreviewEntry(
			com.liferay.html.preview.model.HtmlPreviewEntry htmlPreviewEntry) {

		return _htmlPreviewEntryLocalService.updateHtmlPreviewEntry(
			htmlPreviewEntry);
	}

	@Override
	public com.liferay.html.preview.model.HtmlPreviewEntry
			updateHtmlPreviewEntry(
				long htmlPreviewEntryId, String content, String mimeType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _htmlPreviewEntryLocalService.updateHtmlPreviewEntry(
			htmlPreviewEntryId, content, mimeType, serviceContext);
	}

	@Override
	public HtmlPreviewEntryLocalService getWrappedService() {
		return _htmlPreviewEntryLocalService;
	}

	@Override
	public void setWrappedService(
		HtmlPreviewEntryLocalService htmlPreviewEntryLocalService) {

		_htmlPreviewEntryLocalService = htmlPreviewEntryLocalService;
	}

	private HtmlPreviewEntryLocalService _htmlPreviewEntryLocalService;

}