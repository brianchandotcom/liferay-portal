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

package com.liferay.fragment.service;

import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link FragmentEntryContributedLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryContributedLocalService
 * @generated
 */
public class FragmentEntryContributedLocalServiceWrapper
	implements FragmentEntryContributedLocalService,
			   ServiceWrapper<FragmentEntryContributedLocalService> {

	public FragmentEntryContributedLocalServiceWrapper() {
		this(null);
	}

	public FragmentEntryContributedLocalServiceWrapper(
		FragmentEntryContributedLocalService
			fragmentEntryContributedLocalService) {

		_fragmentEntryContributedLocalService =
			fragmentEntryContributedLocalService;
	}

	/**
	 * Adds the fragment entry contributed to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryContributedLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryContributed the fragment entry contributed
	 * @return the fragment entry contributed that was added
	 */
	@Override
	public FragmentEntryContributed addFragmentEntryContributed(
		FragmentEntryContributed fragmentEntryContributed) {

		return _fragmentEntryContributedLocalService.
			addFragmentEntryContributed(fragmentEntryContributed);
	}

	@Override
	public FragmentEntryContributed addOrUpdateFragmentEntryContributed(
		String fragmentEntryKey, String css, String html, String js,
		String configuration, int type) {

		return _fragmentEntryContributedLocalService.
			addOrUpdateFragmentEntryContributed(
				fragmentEntryKey, css, html, js, configuration, type);
	}

	/**
	 * Creates a new fragment entry contributed with the primary key. Does not add the fragment entry contributed to the database.
	 *
	 * @param fragmentEntryContributedId the primary key for the new fragment entry contributed
	 * @return the new fragment entry contributed
	 */
	@Override
	public FragmentEntryContributed createFragmentEntryContributed(
		long fragmentEntryContributedId) {

		return _fragmentEntryContributedLocalService.
			createFragmentEntryContributed(fragmentEntryContributedId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryContributedLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the fragment entry contributed from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryContributedLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryContributed the fragment entry contributed
	 * @return the fragment entry contributed that was removed
	 */
	@Override
	public FragmentEntryContributed deleteFragmentEntryContributed(
		FragmentEntryContributed fragmentEntryContributed) {

		return _fragmentEntryContributedLocalService.
			deleteFragmentEntryContributed(fragmentEntryContributed);
	}

	/**
	 * Deletes the fragment entry contributed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryContributedLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed that was removed
	 * @throws PortalException if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed deleteFragmentEntryContributed(
			long fragmentEntryContributedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryContributedLocalService.
			deleteFragmentEntryContributed(fragmentEntryContributedId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryContributedLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fragmentEntryContributedLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _fragmentEntryContributedLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fragmentEntryContributedLocalService.dynamicQuery();
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

		return _fragmentEntryContributedLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryContributedModelImpl</code>.
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

		return _fragmentEntryContributedLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryContributedModelImpl</code>.
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

		return _fragmentEntryContributedLocalService.dynamicQuery(
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

		return _fragmentEntryContributedLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _fragmentEntryContributedLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey) {

		return _fragmentEntryContributedLocalService.fetchByFragmentEntryKey(
			fragmentEntryKey);
	}

	@Override
	public FragmentEntryContributed fetchFragmentEntryContributed(
		long fragmentEntryContributedId) {

		return _fragmentEntryContributedLocalService.
			fetchFragmentEntryContributed(fragmentEntryContributedId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fragmentEntryContributedLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the fragment entry contributed with the primary key.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed
	 * @throws PortalException if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed getFragmentEntryContributed(
			long fragmentEntryContributedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryContributedLocalService.
			getFragmentEntryContributed(fragmentEntryContributedId);
	}

	/**
	 * Returns a range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @return the range of fragment entry contributeds
	 */
	@Override
	public java.util.List<FragmentEntryContributed>
		getFragmentEntryContributeds(int start, int end) {

		return _fragmentEntryContributedLocalService.
			getFragmentEntryContributeds(start, end);
	}

	/**
	 * Returns the number of fragment entry contributeds.
	 *
	 * @return the number of fragment entry contributeds
	 */
	@Override
	public int getFragmentEntryContributedsCount() {
		return _fragmentEntryContributedLocalService.
			getFragmentEntryContributedsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fragmentEntryContributedLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fragmentEntryContributedLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryContributedLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the fragment entry contributed in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryContributedLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryContributed the fragment entry contributed
	 * @return the fragment entry contributed that was updated
	 */
	@Override
	public FragmentEntryContributed updateFragmentEntryContributed(
		FragmentEntryContributed fragmentEntryContributed) {

		return _fragmentEntryContributedLocalService.
			updateFragmentEntryContributed(fragmentEntryContributed);
	}

	@Override
	public CTPersistence<FragmentEntryContributed> getCTPersistence() {
		return _fragmentEntryContributedLocalService.getCTPersistence();
	}

	@Override
	public Class<FragmentEntryContributed> getModelClass() {
		return _fragmentEntryContributedLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<FragmentEntryContributed>, R, E>
				updateUnsafeFunction)
		throws E {

		return _fragmentEntryContributedLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public FragmentEntryContributedLocalService getWrappedService() {
		return _fragmentEntryContributedLocalService;
	}

	@Override
	public void setWrappedService(
		FragmentEntryContributedLocalService
			fragmentEntryContributedLocalService) {

		_fragmentEntryContributedLocalService =
			fragmentEntryContributedLocalService;
	}

	private FragmentEntryContributedLocalService
		_fragmentEntryContributedLocalService;

}