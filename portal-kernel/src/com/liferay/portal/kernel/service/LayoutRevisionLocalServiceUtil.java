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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for LayoutRevision. This utility wraps
 * {@link com.liferay.portal.service.impl.LayoutRevisionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionLocalService
 * @see com.liferay.portal.service.base.LayoutRevisionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutRevisionLocalServiceImpl
 * @generated
 */
@ProviderType
public class LayoutRevisionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutRevisionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the layout revision to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was added
	*/
	public static com.liferay.portal.kernel.model.LayoutRevision addLayoutRevision(
		com.liferay.portal.kernel.model.LayoutRevision layoutRevision) {
		return getService().addLayoutRevision(layoutRevision);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision addLayoutRevision(
		long userId, long layoutSetBranchId, long layoutBranchId,
		long parentLayoutRevisionId, boolean head, long plid,
		long portletPreferencesPlid, boolean privateLayout, String name,
		String title, String description, String keywords, String robots,
		String typeSettings, boolean iconImage, long iconImageId,
		String themeId, String colorSchemeId, String css,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutRevision(userId, layoutSetBranchId,
			layoutBranchId, parentLayoutRevisionId, head, plid,
			portletPreferencesPlid, privateLayout, name, title, description,
			keywords, robots, typeSettings, iconImage, iconImageId, themeId,
			colorSchemeId, css, serviceContext);
	}

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public static com.liferay.portal.kernel.model.LayoutRevision createLayoutRevision(
		long layoutRevisionId) {
		return getService().createLayoutRevision(layoutRevisionId);
	}

	public static void deleteLayoutLayoutRevisions(long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayoutLayoutRevisions(plid);
	}

	/**
	* Deletes the layout revision from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.LayoutRevision deleteLayoutRevision(
		com.liferay.portal.kernel.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLayoutRevision(layoutRevision);
	}

	/**
	* Deletes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision that was removed
	* @throws PortalException if a layout revision with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.LayoutRevision deleteLayoutRevision(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLayoutRevision(layoutRevisionId);
	}

	public static void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayoutRevisions(layoutSetBranchId, plid);
	}

	public static void deleteLayoutRevisions(long layoutSetBranchId,
		long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteLayoutRevisions(layoutSetBranchId, layoutBranchId, plid);
	}

	public static void deleteLayoutSetBranchLayoutRevisions(
		long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayoutSetBranchLayoutRevisions(layoutSetBranchId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.kernel.model.LayoutRevision fetchLastLayoutRevision(
		long plid, boolean head) {
		return getService().fetchLastLayoutRevision(plid, head);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision fetchLatestLayoutRevision(
		long layoutSetBranchId, long plid) {
		return getService().fetchLatestLayoutRevision(layoutSetBranchId, plid);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision fetchLayoutRevision(
		long layoutRevisionId) {
		return getService().fetchLayoutRevision(layoutRevisionId);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.portal.kernel.model.LayoutRevision fetchLayoutRevision(
		long layoutSetBranchId, boolean head, long plid) {
		return getService().fetchLayoutRevision(layoutSetBranchId, head, plid);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision fetchLayoutRevision(
		long layoutSetBranchId, long layoutBranchId, boolean head, long plid) {
		return getService()
				   .fetchLayoutRevision(layoutSetBranchId, layoutBranchId,
			head, plid);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {
		return getService()
				   .getChildLayoutRevisions(layoutSetBranchId,
			parentLayoutRevisionId, plid);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevision, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutRevision> orderByComparator) {
		return getService()
				   .getChildLayoutRevisions(layoutSetBranchId,
			parentLayoutRevision, plid, start, end, orderByComparator);
	}

	public static int getChildLayoutRevisionsCount(long layoutSetBranchId,
		long parentLayoutRevision, long plid) {
		return getService()
				   .getChildLayoutRevisionsCount(layoutSetBranchId,
			parentLayoutRevision, plid);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout revision with the primary key.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision
	* @throws PortalException if a layout revision with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.LayoutRevision getLayoutRevision(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutRevision(layoutRevisionId);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.portal.kernel.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutRevision(layoutSetBranchId, plid, head);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutRevision(layoutSetBranchId, layoutBranchId, plid);
	}

	/**
	* Returns a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of layout revisions
	*/
	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		int start, int end) {
		return getService().getLayoutRevisions(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long plid) {
		return getService().getLayoutRevisions(plid);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, boolean head) {
		return getService().getLayoutRevisions(layoutSetBranchId, head);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, boolean head, int status) {
		return getService().getLayoutRevisions(layoutSetBranchId, head, status);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, int status) {
		return getService().getLayoutRevisions(layoutSetBranchId, status);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid) {
		return getService().getLayoutRevisions(layoutSetBranchId, plid);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, boolean head) {
		return getService().getLayoutRevisions(layoutSetBranchId, plid, head);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int status) {
		return getService().getLayoutRevisions(layoutSetBranchId, plid, status);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutRevision> orderByComparator) {
		return getService()
				   .getLayoutRevisions(layoutSetBranchId, plid, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutRevision> orderByComparator) {
		return getService()
				   .getLayoutRevisions(layoutSetBranchId, layoutBranchId, plid,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of layout revisions.
	*
	* @return the number of layout revisions
	*/
	public static int getLayoutRevisionsCount() {
		return getService().getLayoutRevisionsCount();
	}

	public static int getLayoutRevisionsCount(long layoutSetBranchId,
		long layoutBranchId, long plid) {
		return getService()
				   .getLayoutRevisionsCount(layoutSetBranchId, layoutBranchId,
			plid);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout revision in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was updated
	*/
	public static com.liferay.portal.kernel.model.LayoutRevision updateLayoutRevision(
		com.liferay.portal.kernel.model.LayoutRevision layoutRevision) {
		return getService().updateLayoutRevision(layoutRevision);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision updateLayoutRevision(
		long userId, long layoutRevisionId, long layoutBranchId, String name,
		String title, String description, String keywords, String robots,
		String typeSettings, boolean iconImage, long iconImageId,
		String themeId, String colorSchemeId, String css,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutRevision(userId, layoutRevisionId,
			layoutBranchId, name, title, description, keywords, robots,
			typeSettings, iconImage, iconImageId, themeId, colorSchemeId, css,
			serviceContext);
	}

	public static com.liferay.portal.kernel.model.LayoutRevision updateStatus(
		long userId, long layoutRevisionId, int status,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, layoutRevisionId, status,
			serviceContext);
	}

	public static LayoutRevisionLocalService getService() {
		if (_service == null) {
			_service = (LayoutRevisionLocalService)PortalBeanLocatorUtil.locate(LayoutRevisionLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutRevisionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static LayoutRevisionLocalService _service;
}