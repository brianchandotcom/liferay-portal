/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link LayoutRevisionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutRevisionLocalService
 * @generated
 */
public class LayoutRevisionLocalServiceWrapper
	implements LayoutRevisionLocalService,
		ServiceWrapper<LayoutRevisionLocalService> {
	public LayoutRevisionLocalServiceWrapper(
		LayoutRevisionLocalService layoutRevisionLocalService) {
		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	/**
	* Adds the layout revision to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision addLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.addLayoutRevision(layoutRevision);
	}

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public com.liferay.portal.model.LayoutRevision createLayoutRevision(
		long layoutRevisionId) {
		return _layoutRevisionLocalService.createLayoutRevision(layoutRevisionId);
	}

	/**
	* Deletes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @throws PortalException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteLayoutRevision(long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutRevisionLocalService.deleteLayoutRevision(layoutRevisionId);
	}

	/**
	* Deletes the layout revision from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the layout revision with the primary key.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision
	* @throws PortalException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision getLayoutRevision(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(start, end);
	}

	/**
	* Returns the number of layout revisions.
	*
	* @return the number of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int getLayoutRevisionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisionsCount();
	}

	/**
	* Updates the layout revision in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @return the layout revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision updateLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.updateLayoutRevision(layoutRevision);
	}

	/**
	* Updates the layout revision in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision
	* @param merge whether to merge the layout revision with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the layout revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision updateLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.updateLayoutRevision(layoutRevision,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _layoutRevisionLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutRevisionLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.LayoutRevision addLayoutRevision(
		long userId, long layoutSetBranchId, long layoutBranchId,
		long parentLayoutRevisionId, boolean head, long plid,
		boolean privateLayout, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String keywords,
		java.lang.String robots, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.addLayoutRevision(userId,
			layoutSetBranchId, layoutBranchId, parentLayoutRevisionId, head,
			plid, privateLayout, name, title, description, keywords, robots,
			typeSettings, iconImage, iconImageId, themeId, colorSchemeId,
			wapThemeId, wapColorSchemeId, css, serviceContext);
	}

	public void deleteLayoutLayoutRevisions(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutRevisionLocalService.deleteLayoutLayoutRevisions(plid);
	}

	public void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutRevisionLocalService.deleteLayoutRevisions(layoutSetBranchId,
			plid);
	}

	public void deleteLayoutRevisions(long layoutSetBranchId,
		long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutRevisionLocalService.deleteLayoutRevisions(layoutSetBranchId,
			layoutBranchId, plid);
	}

	public void deleteLayoutSetBranchLayoutRevisions(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutRevisionLocalService.deleteLayoutSetBranchLayoutRevisions(layoutSetBranchId);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getChildLayoutRevisions(layoutSetBranchId,
			parentLayoutRevisionId, plid);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevision, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getChildLayoutRevisions(layoutSetBranchId,
			parentLayoutRevision, plid, start, end, orderByComparator);
	}

	public int getChildLayoutRevisionsCount(long layoutSetBranchId,
		long parentLayoutRevision, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getChildLayoutRevisionsCount(layoutSetBranchId,
			parentLayoutRevision, plid);
	}

	public com.liferay.portal.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevision(layoutSetBranchId,
			plid, head);
	}

	public com.liferay.portal.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevision(layoutSetBranchId,
			layoutBranchId, plid);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(plid);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			head);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			status);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			plid);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			plid, status);
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisions(layoutSetBranchId,
			layoutBranchId, plid, start, end, orderByComparator);
	}

	public int getLayoutRevisionsCount(long layoutSetBranchId,
		long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.getLayoutRevisionsCount(layoutSetBranchId,
			layoutBranchId, plid);
	}

	public com.liferay.portal.model.LayoutRevision updateLayoutRevision(
		long userId, long layoutRevisionId, long layoutBranchId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String keywords,
		java.lang.String robots, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.updateLayoutRevision(userId,
			layoutRevisionId, layoutBranchId, name, title, description,
			keywords, robots, typeSettings, iconImage, iconImageId, themeId,
			colorSchemeId, wapThemeId, wapColorSchemeId, css, serviceContext);
	}

	public com.liferay.portal.model.LayoutRevision updateStatus(long userId,
		long layoutRevisionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionLocalService.updateStatus(userId,
			layoutRevisionId, status, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LayoutRevisionLocalService getWrappedLayoutRevisionLocalService() {
		return _layoutRevisionLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {
		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	public LayoutRevisionLocalService getWrappedService() {
		return _layoutRevisionLocalService;
	}

	public void setWrappedService(
		LayoutRevisionLocalService layoutRevisionLocalService) {
		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	private LayoutRevisionLocalService _layoutRevisionLocalService;
}