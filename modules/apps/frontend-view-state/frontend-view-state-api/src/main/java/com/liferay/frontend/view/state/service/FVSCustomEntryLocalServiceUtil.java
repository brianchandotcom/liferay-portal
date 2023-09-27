/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service;

import com.liferay.frontend.view.state.model.FVSCustomEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FVSCustomEntry. This utility wraps
 * <code>com.liferay.frontend.view.state.service.impl.FVSCustomEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FVSCustomEntryLocalService
 * @generated
 */
public class FVSCustomEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.frontend.view.state.service.impl.FVSCustomEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the fvs custom entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 * @return the fvs custom entry that was added
	 */
	public static FVSCustomEntry addFVSCustomEntry(
		FVSCustomEntry fvsCustomEntry) {

		return getService().addFVSCustomEntry(fvsCustomEntry);
	}

	/**
	 * Creates a new fvs custom entry with the primary key. Does not add the fvs custom entry to the database.
	 *
	 * @param fvsCustomEntryId the primary key for the new fvs custom entry
	 * @return the new fvs custom entry
	 */
	public static FVSCustomEntry createFVSCustomEntry(long fvsCustomEntryId) {
		return getService().createFVSCustomEntry(fvsCustomEntryId);
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
	 * Deletes the fvs custom entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 * @return the fvs custom entry that was removed
	 */
	public static FVSCustomEntry deleteFVSCustomEntry(
		FVSCustomEntry fvsCustomEntry) {

		return getService().deleteFVSCustomEntry(fvsCustomEntry);
	}

	/**
	 * Deletes the fvs custom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry that was removed
	 * @throws PortalException if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry deleteFVSCustomEntry(long fvsCustomEntryId)
		throws PortalException {

		return getService().deleteFVSCustomEntry(fvsCustomEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl</code>.
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

	public static FVSCustomEntry fetchFVSCustomEntry(long fvsCustomEntryId) {
		return getService().fetchFVSCustomEntry(fvsCustomEntryId);
	}

	/**
	 * Returns the fvs custom entry with the matching UUID and company.
	 *
	 * @param uuid the fvs custom entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry fetchFVSCustomEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchFVSCustomEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns a range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of fvs custom entries
	 */
	public static List<FVSCustomEntry> getFVSCustomEntries(int start, int end) {
		return getService().getFVSCustomEntries(start, end);
	}

	/**
	 * Returns the number of fvs custom entries.
	 *
	 * @return the number of fvs custom entries
	 */
	public static int getFVSCustomEntriesCount() {
		return getService().getFVSCustomEntriesCount();
	}

	/**
	 * Returns the fvs custom entry with the primary key.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry
	 * @throws PortalException if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry getFVSCustomEntry(long fvsCustomEntryId)
		throws PortalException {

		return getService().getFVSCustomEntry(fvsCustomEntryId);
	}

	/**
	 * Returns the fvs custom entry with the matching UUID and company.
	 *
	 * @param uuid the fvs custom entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs custom entry
	 * @throws PortalException if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry getFVSCustomEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getFVSCustomEntryByUuidAndCompanyId(
			uuid, companyId);
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
	 * Updates the fvs custom entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 * @return the fvs custom entry that was updated
	 */
	public static FVSCustomEntry updateFVSCustomEntry(
		FVSCustomEntry fvsCustomEntry) {

		return getService().updateFVSCustomEntry(fvsCustomEntry);
	}

	public static FVSCustomEntryLocalService getService() {
		return _service;
	}

	public static void setService(FVSCustomEntryLocalService service) {
		_service = service;
	}

	private static volatile FVSCustomEntryLocalService _service;

}