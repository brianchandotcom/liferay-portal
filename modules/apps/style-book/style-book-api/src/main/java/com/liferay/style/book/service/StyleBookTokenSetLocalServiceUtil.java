/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.style.book.model.StyleBookTokenSet;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for StyleBookTokenSet. This utility wraps
 * <code>com.liferay.style.book.service.impl.StyleBookTokenSetLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetLocalService
 * @generated
 */
public class StyleBookTokenSetLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.style.book.service.impl.StyleBookTokenSetLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static StyleBookTokenSet addStyleBookTokenSet(
			String externalReferenceCode, String description,
			String frontendTokenCategoryName, String name,
			long styleBookEntryId, String themeId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addStyleBookTokenSet(
			externalReferenceCode, description, frontendTokenCategoryName, name,
			styleBookEntryId, themeId, serviceContext);
	}

	/**
	 * Adds the style book token set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was added
	 */
	public static StyleBookTokenSet addStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet) {

		return getService().addStyleBookTokenSet(styleBookTokenSet);
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
	 * Creates a new style book token set with the primary key. Does not add the style book token set to the database.
	 *
	 * @param styleBookTokenSetId the primary key for the new style book token set
	 * @return the new style book token set
	 */
	public static StyleBookTokenSet createStyleBookTokenSet(
		long styleBookTokenSetId) {

		return getService().createStyleBookTokenSet(styleBookTokenSetId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the style book token set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set that was removed
	 * @throws PortalException if a style book token set with the primary key could not be found
	 */
	public static StyleBookTokenSet deleteStyleBookTokenSet(
			long styleBookTokenSetId)
		throws PortalException {

		return getService().deleteStyleBookTokenSet(styleBookTokenSetId);
	}

	/**
	 * Deletes the style book token set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was removed
	 */
	public static StyleBookTokenSet deleteStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet) {

		return getService().deleteStyleBookTokenSet(styleBookTokenSet);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
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

	public static StyleBookTokenSet fetchStyleBookTokenSet(
		long styleBookTokenSetId) {

		return getService().fetchStyleBookTokenSet(styleBookTokenSetId);
	}

	public static StyleBookTokenSet
		fetchStyleBookTokenSetByExternalReferenceCode(
			String externalReferenceCode, long groupId) {

		return getService().fetchStyleBookTokenSetByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	/**
	 * Returns the style book token set matching the UUID and group.
	 *
	 * @param uuid the style book token set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public static StyleBookTokenSet fetchStyleBookTokenSetByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchStyleBookTokenSetByUuidAndGroupId(
			uuid, groupId);
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
	 * Returns the style book token set with the primary key.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set
	 * @throws PortalException if a style book token set with the primary key could not be found
	 */
	public static StyleBookTokenSet getStyleBookTokenSet(
			long styleBookTokenSetId)
		throws PortalException {

		return getService().getStyleBookTokenSet(styleBookTokenSetId);
	}

	public static StyleBookTokenSet getStyleBookTokenSetByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		return getService().getStyleBookTokenSetByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	/**
	 * Returns the style book token set matching the UUID and group.
	 *
	 * @param uuid the style book token set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching style book token set
	 * @throws PortalException if a matching style book token set could not be found
	 */
	public static StyleBookTokenSet getStyleBookTokenSetByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getStyleBookTokenSetByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the style book token sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @return the range of style book token sets
	 */
	public static List<StyleBookTokenSet> getStyleBookTokenSets(
		int start, int end) {

		return getService().getStyleBookTokenSets(start, end);
	}

	/**
	 * Returns all the style book token sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the style book token sets
	 * @param companyId the primary key of the company
	 * @return the matching style book token sets, or an empty list if no matches were found
	 */
	public static List<StyleBookTokenSet>
		getStyleBookTokenSetsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getStyleBookTokenSetsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of style book token sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the style book token sets
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching style book token sets, or an empty list if no matches were found
	 */
	public static List<StyleBookTokenSet>
		getStyleBookTokenSetsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<StyleBookTokenSet> orderByComparator) {

		return getService().getStyleBookTokenSetsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of style book token sets.
	 *
	 * @return the number of style book token sets
	 */
	public static int getStyleBookTokenSetsCount() {
		return getService().getStyleBookTokenSetsCount();
	}

	/**
	 * Updates the style book token set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was updated
	 */
	public static StyleBookTokenSet updateStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet) {

		return getService().updateStyleBookTokenSet(styleBookTokenSet);
	}

	public static StyleBookTokenSetLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<StyleBookTokenSetLocalService>
		_serviceSnapshot = new Snapshot<>(
			StyleBookTokenSetLocalServiceUtil.class,
			StyleBookTokenSetLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1201639620