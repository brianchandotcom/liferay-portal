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

/**
 * Provides a wrapper for {@link LayoutRevisionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionLocalService
 * @generated
 */
@ProviderType
public class LayoutRevisionLocalServiceWrapper
	implements LayoutRevisionLocalService,
		ServiceWrapper<LayoutRevisionLocalService> {
	public LayoutRevisionLocalServiceWrapper(
		LayoutRevisionLocalService layoutRevisionLocalService) {
		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutRevisionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutRevisionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutRevisionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Adds the layout revision to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was added
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision addLayoutRevision(
		com.liferay.portal.kernel.model.LayoutRevision layoutRevision) {
		return _layoutRevisionLocalService.addLayoutRevision(layoutRevision);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision addLayoutRevision(
		long userId, long layoutSetBranchId, long layoutBranchId,
		long parentLayoutRevisionId, boolean head, long plid,
		long portletPreferencesPlid, boolean privateLayout,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String keywords,
		java.lang.String robots, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.addLayoutRevision(userId,
			layoutSetBranchId, layoutBranchId, parentLayoutRevisionId, head,
			plid, portletPreferencesPlid, privateLayout, name, title,
			description, keywords, robots, typeSettings, iconImage,
			iconImageId, themeId, colorSchemeId, css, serviceContext);
	}

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision createLayoutRevision(
		long layoutRevisionId) {
		return _layoutRevisionLocalService.createLayoutRevision(layoutRevisionId);
	}

	/**
	* Deletes the layout revision from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision deleteLayoutRevision(
		com.liferay.portal.kernel.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
	}

	/**
	* Deletes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision that was removed
	* @throws PortalException if a layout revision with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision deleteLayoutRevision(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.deleteLayoutRevision(layoutRevisionId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision fetchLastLayoutRevision(
		long plid, boolean head) {
		return _layoutRevisionLocalService.fetchLastLayoutRevision(plid, head);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision fetchLatestLayoutRevision(
		long layoutSetBranchId, long plid) {
		return _layoutRevisionLocalService.fetchLatestLayoutRevision(layoutSetBranchId,
			plid);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision fetchLayoutRevision(
		long layoutRevisionId) {
		return _layoutRevisionLocalService.fetchLayoutRevision(layoutRevisionId);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision fetchLayoutRevision(
		long layoutSetBranchId, boolean head, long plid) {
		return _layoutRevisionLocalService.fetchLayoutRevision(layoutSetBranchId,
			head, plid);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision fetchLayoutRevision(
		long layoutSetBranchId, long layoutBranchId, boolean head, long plid) {
		return _layoutRevisionLocalService.fetchLayoutRevision(layoutSetBranchId,
			layoutBranchId, head, plid);
	}

	/**
	* Returns the layout revision with the primary key.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision
	* @throws PortalException if a layout revision with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision getLayoutRevision(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.getLayoutRevision(layoutSetBranchId,
			layoutBranchId, plid);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.getLayoutRevision(layoutSetBranchId,
			plid, head);
	}

	/**
	* Updates the layout revision in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was updated
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision updateLayoutRevision(
		com.liferay.portal.kernel.model.LayoutRevision layoutRevision) {
		return _layoutRevisionLocalService.updateLayoutRevision(layoutRevision);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision updateLayoutRevision(
		long userId, long layoutRevisionId, long layoutBranchId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String keywords,
		java.lang.String robots, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.updateLayoutRevision(userId,
			layoutRevisionId, layoutBranchId, name, title, description,
			keywords, robots, typeSettings, iconImage, iconImageId, themeId,
			colorSchemeId, css, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutRevision updateStatus(
		long userId, long layoutRevisionId, int status,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.updateStatus(userId,
			layoutRevisionId, status, serviceContext);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutRevisionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public int getChildLayoutRevisionsCount(long layoutSetBranchId,
		long parentLayoutRevision, long plid) {
		return _layoutRevisionLocalService.getChildLayoutRevisionsCount(layoutSetBranchId,
			parentLayoutRevision, plid);
	}

	/**
	* Returns the number of layout revisions.
	*
	* @return the number of layout revisions
	*/
	@Override
	public int getLayoutRevisionsCount() {
		return _layoutRevisionLocalService.getLayoutRevisionsCount();
	}

	@Override
	public int getLayoutRevisionsCount(long layoutSetBranchId,
		long layoutBranchId, long plid) {
		return _layoutRevisionLocalService.getLayoutRevisionsCount(layoutSetBranchId,
			layoutBranchId, plid);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutRevisionLocalService.getOSGiServiceIdentifier();
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
		return _layoutRevisionLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _layoutRevisionLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _layoutRevisionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevision, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutRevision> orderByComparator) {
		return _layoutRevisionLocalService.getChildLayoutRevisions(layoutSetBranchId,
			parentLayoutRevision, plid, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {
		return _layoutRevisionLocalService.getChildLayoutRevisions(layoutSetBranchId,
			parentLayoutRevisionId, plid);
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
	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		int start, int end) {
		return _layoutRevisionLocalService.getLayoutRevisions(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, boolean head) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			head);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, int status) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			status);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutRevision> orderByComparator) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			layoutBranchId, plid, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			plid);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, boolean head) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			plid, head);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutRevision> orderByComparator) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			plid, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int status) {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			plid, status);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutRevision> getLayoutRevisions(
		long plid) {
		return _layoutRevisionLocalService.getLayoutRevisions(plid);
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
		return _layoutRevisionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _layoutRevisionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteLayoutLayoutRevisions(long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutRevisionLocalService.deleteLayoutLayoutRevisions(plid);
	}

	@Override
	public void deleteLayoutRevisions(long layoutSetBranchId,
		long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutRevisionLocalService.deleteLayoutRevisions(layoutSetBranchId,
			layoutBranchId, plid);
	}

	@Override
	public void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutRevisionLocalService.deleteLayoutRevisions(layoutSetBranchId,
			plid);
	}

	@Override
	public void deleteLayoutSetBranchLayoutRevisions(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutRevisionLocalService.deleteLayoutSetBranchLayoutRevisions(layoutSetBranchId);
	}

	@Override
	public LayoutRevisionLocalService getWrappedService() {
		return _layoutRevisionLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutRevisionLocalService layoutRevisionLocalService) {
		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	private LayoutRevisionLocalService _layoutRevisionLocalService;
}