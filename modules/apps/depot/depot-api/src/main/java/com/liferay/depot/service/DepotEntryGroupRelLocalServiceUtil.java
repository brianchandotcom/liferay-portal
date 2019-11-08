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

package com.liferay.depot.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DepotEntryGroupRel. This utility wraps
 * <code>com.liferay.depot.service.impl.DepotEntryGroupRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelLocalService
 * @generated
 */
public class DepotEntryGroupRelLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryGroupRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the depot entry group rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 * @return the depot entry group rel that was added
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
		addDepotEntryGroupRel(
			com.liferay.depot.model.DepotEntryGroupRel depotEntryGroupRel) {

		return getService().addDepotEntryGroupRel(depotEntryGroupRel);
	}

	public static com.liferay.depot.model.DepotEntryGroupRel
		addDepotEntryGroupRel(long depotEntryId, long toGroupId) {

		return getService().addDepotEntryGroupRel(depotEntryId, toGroupId);
	}

	/**
	 * Creates a new depot entry group rel with the primary key. Does not add the depot entry group rel to the database.
	 *
	 * @param depotEntryGroupRelId the primary key for the new depot entry group rel
	 * @return the new depot entry group rel
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
		createDepotEntryGroupRel(long depotEntryGroupRelId) {

		return getService().createDepotEntryGroupRel(depotEntryGroupRelId);
	}

	/**
	 * Deletes the depot entry group rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 * @return the depot entry group rel that was removed
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
		deleteDepotEntryGroupRel(
			com.liferay.depot.model.DepotEntryGroupRel depotEntryGroupRel) {

		return getService().deleteDepotEntryGroupRel(depotEntryGroupRel);
	}

	/**
	 * Deletes the depot entry group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel that was removed
	 * @throws PortalException if a depot entry group rel with the primary key could not be found
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
			deleteDepotEntryGroupRel(long depotEntryGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDepotEntryGroupRel(depotEntryGroupRelId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteToGroupDepotEntryGroupRels(long toGroupId) {
		getService().deleteToGroupDepotEntryGroupRels(toGroupId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.depot.model.DepotEntryGroupRel
		fetchDepotEntryGroupRel(long depotEntryGroupRelId) {

		return getService().fetchDepotEntryGroupRel(depotEntryGroupRelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the depot entry group rel with the primary key.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel
	 * @throws PortalException if a depot entry group rel with the primary key could not be found
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
			getDepotEntryGroupRel(long depotEntryGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntryGroupRel(depotEntryGroupRelId);
	}

	public static java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
		getDepotEntryGroupRels(com.liferay.depot.model.DepotEntry depotEntry) {

		return getService().getDepotEntryGroupRels(depotEntry);
	}

	/**
	 * Returns a range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of depot entry group rels
	 */
	public static java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
		getDepotEntryGroupRels(int start, int end) {

		return getService().getDepotEntryGroupRels(start, end);
	}

	public static java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
		getDepotEntryGroupRels(long groupId, int start, int end) {

		return getService().getDepotEntryGroupRels(groupId, start, end);
	}

	/**
	 * Returns the number of depot entry group rels.
	 *
	 * @return the number of depot entry group rels
	 */
	public static int getDepotEntryGroupRelsCount() {
		return getService().getDepotEntryGroupRelsCount();
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the depot entry group rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 * @return the depot entry group rel that was updated
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
		updateDepotEntryGroupRel(
			com.liferay.depot.model.DepotEntryGroupRel depotEntryGroupRel) {

		return getService().updateDepotEntryGroupRel(depotEntryGroupRel);
	}

	public static DepotEntryGroupRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DepotEntryGroupRelLocalService, DepotEntryGroupRelLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DepotEntryGroupRelLocalService.class);

		ServiceTracker
			<DepotEntryGroupRelLocalService, DepotEntryGroupRelLocalService>
				serviceTracker =
					new ServiceTracker
						<DepotEntryGroupRelLocalService,
						 DepotEntryGroupRelLocalService>(
							 bundle.getBundleContext(),
							 DepotEntryGroupRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}