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

package com.liferay.view.count.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ViewCountEntryLocalService}.
 *
 * @author Preston Crary
 * @see ViewCountEntryLocalService
 * @generated
 */
public class ViewCountEntryLocalServiceWrapper
	implements ServiceWrapper<ViewCountEntryLocalService>,
			   ViewCountEntryLocalService {

	public ViewCountEntryLocalServiceWrapper(
		ViewCountEntryLocalService viewCountEntryLocalService) {

		_viewCountEntryLocalService = viewCountEntryLocalService;
	}

	/**
	 * Adds the view count entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntry the view count entry
	 * @return the view count entry that was added
	 */
	@Override
	public com.liferay.view.count.model.ViewCountEntry addViewCountEntry(
		com.liferay.view.count.model.ViewCountEntry viewCountEntry) {

		return _viewCountEntryLocalService.addViewCountEntry(viewCountEntry);
	}

	/**
	 * Creates a new view count entry with the primary key. Does not add the view count entry to the database.
	 *
	 * @param viewCountEntryPK the primary key for the new view count entry
	 * @return the new view count entry
	 */
	@Override
	public com.liferay.view.count.model.ViewCountEntry createViewCountEntry(
		com.liferay.view.count.service.persistence.ViewCountEntryPK
			viewCountEntryPK) {

		return _viewCountEntryLocalService.createViewCountEntry(
			viewCountEntryPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _viewCountEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the view count entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntry the view count entry
	 * @return the view count entry that was removed
	 */
	@Override
	public com.liferay.view.count.model.ViewCountEntry deleteViewCountEntry(
		com.liferay.view.count.model.ViewCountEntry viewCountEntry) {

		return _viewCountEntryLocalService.deleteViewCountEntry(viewCountEntry);
	}

	/**
	 * Deletes the view count entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry that was removed
	 * @throws PortalException if a view count entry with the primary key could not be found
	 */
	@Override
	public com.liferay.view.count.model.ViewCountEntry deleteViewCountEntry(
			com.liferay.view.count.service.persistence.ViewCountEntryPK
				viewCountEntryPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _viewCountEntryLocalService.deleteViewCountEntry(
			viewCountEntryPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _viewCountEntryLocalService.dynamicQuery();
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

		return _viewCountEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.view.count.model.impl.ViewCountEntryModelImpl</code>.
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

		return _viewCountEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.view.count.model.impl.ViewCountEntryModelImpl</code>.
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

		return _viewCountEntryLocalService.dynamicQuery(
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

		return _viewCountEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _viewCountEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.view.count.model.ViewCountEntry fetchViewCountEntry(
		com.liferay.view.count.service.persistence.ViewCountEntryPK
			viewCountEntryPK) {

		return _viewCountEntryLocalService.fetchViewCountEntry(
			viewCountEntryPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _viewCountEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _viewCountEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _viewCountEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _viewCountEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public long getViewCount(long companyId, long classNameId, long classPK) {
		return _viewCountEntryLocalService.getViewCount(
			companyId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.view.count.model.impl.ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @return the range of view count entries
	 */
	@Override
	public java.util.List<com.liferay.view.count.model.ViewCountEntry>
		getViewCountEntries(int start, int end) {

		return _viewCountEntryLocalService.getViewCountEntries(start, end);
	}

	/**
	 * Returns the number of view count entries.
	 *
	 * @return the number of view count entries
	 */
	@Override
	public int getViewCountEntriesCount() {
		return _viewCountEntryLocalService.getViewCountEntriesCount();
	}

	/**
	 * Returns the view count entry with the primary key.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry
	 * @throws PortalException if a view count entry with the primary key could not be found
	 */
	@Override
	public com.liferay.view.count.model.ViewCountEntry getViewCountEntry(
			com.liferay.view.count.service.persistence.ViewCountEntryPK
				viewCountEntryPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _viewCountEntryLocalService.getViewCountEntry(viewCountEntryPK);
	}

	@Override
	public void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		_viewCountEntryLocalService.incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

	@Override
	public void removeViewCount(
		long companyId, long classNameId, long classPK) {

		_viewCountEntryLocalService.removeViewCount(
			companyId, classNameId, classPK);
	}

	/**
	 * Updates the view count entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntry the view count entry
	 * @return the view count entry that was updated
	 */
	@Override
	public com.liferay.view.count.model.ViewCountEntry updateViewCountEntry(
		com.liferay.view.count.model.ViewCountEntry viewCountEntry) {

		return _viewCountEntryLocalService.updateViewCountEntry(viewCountEntry);
	}

	@Override
	public ViewCountEntryLocalService getWrappedService() {
		return _viewCountEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ViewCountEntryLocalService viewCountEntryLocalService) {

		_viewCountEntryLocalService = viewCountEntryLocalService;
	}

	private ViewCountEntryLocalService _viewCountEntryLocalService;

}