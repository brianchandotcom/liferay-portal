/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for PermissionPropagationEntry. This utility wraps
 * <code>com.liferay.portal.service.impl.PermissionPropagationEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntryLocalService
 * @generated
 */
public class PermissionPropagationEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PermissionPropagationEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static PermissionPropagationEntry addPermissionPropagationEntry(
		long companyId, long groupId, String className, long classPK,
		boolean propagation) {

		return getService().addPermissionPropagationEntry(
			companyId, groupId, className, classPK, propagation);
	}

	/**
	 * Adds the permission propagation entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 * @return the permission propagation entry that was added
	 */
	public static PermissionPropagationEntry addPermissionPropagationEntry(
		PermissionPropagationEntry permissionPropagationEntry) {

		return getService().addPermissionPropagationEntry(
			permissionPropagationEntry);
	}

	/**
	 * Creates a new permission propagation entry with the primary key. Does not add the permission propagation entry to the database.
	 *
	 * @param permissionPropagationEntryId the primary key for the new permission propagation entry
	 * @return the new permission propagation entry
	 */
	public static PermissionPropagationEntry createPermissionPropagationEntry(
		long permissionPropagationEntryId) {

		return getService().createPermissionPropagationEntry(
			permissionPropagationEntryId);
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
	 * Deletes the permission propagation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry that was removed
	 * @throws PortalException if a permission propagation entry with the primary key could not be found
	 */
	public static PermissionPropagationEntry deletePermissionPropagationEntry(
			long permissionPropagationEntryId)
		throws PortalException {

		return getService().deletePermissionPropagationEntry(
			permissionPropagationEntryId);
	}

	/**
	 * Deletes the permission propagation entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 * @return the permission propagation entry that was removed
	 */
	public static PermissionPropagationEntry deletePermissionPropagationEntry(
		PermissionPropagationEntry permissionPropagationEntry) {

		return getService().deletePermissionPropagationEntry(
			permissionPropagationEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl</code>.
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

	public static PermissionPropagationEntry fetchPermissionPropagationEntry(
		long permissionPropagationEntryId) {

		return getService().fetchPermissionPropagationEntry(
			permissionPropagationEntryId);
	}

	public static PermissionPropagationEntry fetchPermissionPropagationEntry(
		long companyId, long groupId, String className, long classPK) {

		return getService().fetchPermissionPropagationEntry(
			companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	 * Returns a range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @return the range of permission propagation entries
	 */
	public static List<PermissionPropagationEntry>
		getPermissionPropagationEntries(int start, int end) {

		return getService().getPermissionPropagationEntries(start, end);
	}

	/**
	 * Returns the number of permission propagation entries.
	 *
	 * @return the number of permission propagation entries
	 */
	public static int getPermissionPropagationEntriesCount() {
		return getService().getPermissionPropagationEntriesCount();
	}

	/**
	 * Returns the permission propagation entry with the primary key.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry
	 * @throws PortalException if a permission propagation entry with the primary key could not be found
	 */
	public static PermissionPropagationEntry getPermissionPropagationEntry(
			long permissionPropagationEntryId)
		throws PortalException {

		return getService().getPermissionPropagationEntry(
			permissionPropagationEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static PermissionPropagationEntry updatePermissionPropagationEntry(
		long companyId, long groupId, String className, long classPK,
		boolean propagation) {

		return getService().updatePermissionPropagationEntry(
			companyId, groupId, className, classPK, propagation);
	}

	/**
	 * Updates the permission propagation entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 * @return the permission propagation entry that was updated
	 */
	public static PermissionPropagationEntry updatePermissionPropagationEntry(
		PermissionPropagationEntry permissionPropagationEntry) {

		return getService().updatePermissionPropagationEntry(
			permissionPropagationEntry);
	}

	public static PermissionPropagationEntryLocalService getService() {
		return _service;
	}

	public static void setService(
		PermissionPropagationEntryLocalService service) {

		_service = service;
	}

	private static volatile PermissionPropagationEntryLocalService _service;

}