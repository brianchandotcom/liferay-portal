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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link LayoutSetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetLocalService
 * @generated
 */
@ProviderType
public class LayoutSetLocalServiceWrapper implements LayoutSetLocalService,
	ServiceWrapper<LayoutSetLocalService> {
	public LayoutSetLocalServiceWrapper(
		LayoutSetLocalService layoutSetLocalService) {
		_layoutSetLocalService = layoutSetLocalService;
	}

	@Override
	public com.liferay.portal.model.LayoutSet addLayoutSet(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.addLayoutSet(groupId, privateLayout);
	}

	/**
	* Adds the layout set to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was added
	*/
	@Override
	public com.liferay.portal.model.LayoutSet addLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.addLayoutSet(layoutSet);
	}

	/**
	* Creates a new layout set with the primary key. Does not add the layout set to the database.
	*
	* @param layoutSetId the primary key for the new layout set
	* @return the new layout set
	*/
	@Override
	public com.liferay.portal.model.LayoutSet createLayoutSet(long layoutSetId) {
		return _layoutSetLocalService.createLayoutSet(layoutSetId);
	}

	@Override
	public void deleteLayoutSet(long groupId, boolean privateLayout,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.deleteLayoutSet(groupId, privateLayout,
			serviceContext);
	}

	/**
	* Deletes the layout set from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was removed
	*/
	@Override
	public com.liferay.portal.model.LayoutSet deleteLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.deleteLayoutSet(layoutSet);
	}

	/**
	* Deletes the layout set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set that was removed
	* @throws PortalException if a layout set with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutSet deleteLayoutSet(long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.deleteLayoutSet(layoutSetId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutSetLocalService.dynamicQuery();
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
		return _layoutSetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutSetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutSetLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _layoutSetLocalService.dynamicQueryCount(dynamicQuery);
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
		return _layoutSetLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.LayoutSet fetchLayoutSet(long layoutSetId) {
		return _layoutSetLocalService.fetchLayoutSet(layoutSetId);
	}

	@Override
	public com.liferay.portal.model.LayoutSet fetchLayoutSet(
		java.lang.String virtualHostname) {
		return _layoutSetLocalService.fetchLayoutSet(virtualHostname);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutSetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutSetLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(groupId, privateLayout);
	}

	/**
	* Returns the layout set with the primary key.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set
	* @throws PortalException if a layout set with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet(long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(layoutSetId);
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(virtualHostname);
	}

	/**
	* Returns a range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of layout sets
	*/
	@Override
	public java.util.List<com.liferay.portal.model.LayoutSet> getLayoutSets(
		int start, int end) {
		return _layoutSetLocalService.getLayoutSets(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid) {
		return _layoutSetLocalService.getLayoutSetsByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns the number of layout sets.
	*
	* @return the number of layout sets
	*/
	@Override
	public int getLayoutSetsCount() {
		return _layoutSetLocalService.getLayoutSetsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutSetLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was updated
	*/
	@Override
	public com.liferay.portal.model.LayoutSet updateLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.updateLayoutSet(layoutSet);
	}

	/**
	* Updates the state of the layout set prototype link.
	*
	* <p>
	* This method can disable the layout set prototype's link by setting
	* <code>layoutSetPrototypeLinkEnabled</code> to <code>false</code>.
	* However, this method can only enable the layout set prototype's link if
	* the layout set prototype's current uuid is not <code>null</code>. Setting
	* the <code>layoutSetPrototypeLinkEnabled</code> to <code>true</code> when
	* the layout set prototype's current uuid is <code>null</code> will have no
	* effect.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout set is private to the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set
	prototype is link enabled
	* @deprecated As of 6.1.0, replaced by {@link
	#updateLayoutSetPrototypeLinkEnabled(long, boolean, boolean,
	String)}
	*/
	@Deprecated
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(groupId,
			privateLayout, layoutSetPrototypeLinkEnabled);
	}

	/**
	* Updates the state of the layout set prototype link.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout set is private to the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	link enabled
	* @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	link with
	*/
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		java.lang.String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(groupId,
			privateLayout, layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			bytes);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			file);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			is);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.InputStream is,
		boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			is, cleanUpStream);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateLookAndFeel(long groupId,
		boolean privateLayout, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLookAndFeel(groupId, privateLayout,
			themeId, colorSchemeId, css, wapTheme);
	}

	@Override
	public void updateLookAndFeel(long groupId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLookAndFeel(groupId, themeId,
			colorSchemeId, css, wapTheme);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updatePageCount(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateSettings(long groupId,
		boolean privateLayout, java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateSettings(groupId, privateLayout,
			settings);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateVirtualHost(long groupId,
		boolean privateLayout, java.lang.String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateVirtualHost(groupId, privateLayout,
			virtualHostname);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public LayoutSetLocalService getWrappedLayoutSetLocalService() {
		return _layoutSetLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {
		_layoutSetLocalService = layoutSetLocalService;
	}

	@Override
	public LayoutSetLocalService getWrappedService() {
		return _layoutSetLocalService;
	}

	@Override
	public void setWrappedService(LayoutSetLocalService layoutSetLocalService) {
		_layoutSetLocalService = layoutSetLocalService;
	}

	private LayoutSetLocalService _layoutSetLocalService;
}