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
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FragmentEntryContributed. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentEntryContributedLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryContributedLocalService
 * @generated
 */
public class FragmentEntryContributedLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryContributedLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static FragmentEntryContributed addFragmentEntryContributed(
		FragmentEntryContributed fragmentEntryContributed) {

		return getService().addFragmentEntryContributed(
			fragmentEntryContributed);
	}

	public static FragmentEntryContributed addOrUpdateFragmentEntryContributed(
		String fragmentEntryKey, String css, String html, String js,
		String configuration, int type) {

		return getService().addOrUpdateFragmentEntryContributed(
			fragmentEntryKey, css, html, js, configuration, type);
	}

	/**
	 * Creates a new fragment entry contributed with the primary key. Does not add the fragment entry contributed to the database.
	 *
	 * @param fragmentEntryContributedId the primary key for the new fragment entry contributed
	 * @return the new fragment entry contributed
	 */
	public static FragmentEntryContributed createFragmentEntryContributed(
		long fragmentEntryContributedId) {

		return getService().createFragmentEntryContributed(
			fragmentEntryContributedId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static FragmentEntryContributed deleteFragmentEntryContributed(
		FragmentEntryContributed fragmentEntryContributed) {

		return getService().deleteFragmentEntryContributed(
			fragmentEntryContributed);
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
	public static FragmentEntryContributed deleteFragmentEntryContributed(
			long fragmentEntryContributedId)
		throws PortalException {

		return getService().deleteFragmentEntryContributed(
			fragmentEntryContributedId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey) {

		return getService().fetchByFragmentEntryKey(fragmentEntryKey);
	}

	public static FragmentEntryContributed fetchFragmentEntryContributed(
		long fragmentEntryContributedId) {

		return getService().fetchFragmentEntryContributed(
			fragmentEntryContributedId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the fragment entry contributed with the primary key.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed
	 * @throws PortalException if a fragment entry contributed with the primary key could not be found
	 */
	public static FragmentEntryContributed getFragmentEntryContributed(
			long fragmentEntryContributedId)
		throws PortalException {

		return getService().getFragmentEntryContributed(
			fragmentEntryContributedId);
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
	public static List<FragmentEntryContributed> getFragmentEntryContributeds(
		int start, int end) {

		return getService().getFragmentEntryContributeds(start, end);
	}

	/**
	 * Returns the number of fragment entry contributeds.
	 *
	 * @return the number of fragment entry contributeds
	 */
	public static int getFragmentEntryContributedsCount() {
		return getService().getFragmentEntryContributedsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static FragmentEntryContributed updateFragmentEntryContributed(
		FragmentEntryContributed fragmentEntryContributed) {

		return getService().updateFragmentEntryContributed(
			fragmentEntryContributed);
	}

	public static FragmentEntryContributedLocalService getService() {
		return _service;
	}

	private static volatile FragmentEntryContributedLocalService _service;

}