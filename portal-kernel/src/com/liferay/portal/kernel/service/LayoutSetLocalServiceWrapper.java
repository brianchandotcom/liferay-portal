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

	/**
	* Adds the layout set to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was added
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutSet addLayoutSet(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.addLayoutSet(layoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet addLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.addLayoutSet(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet checkout(
		com.liferay.portal.kernel.model.LayoutSet publishedLayoutSet,
		int version) throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.checkout(publishedLayoutSet, version);
	}

	/**
	* Creates a new layout set. Does not add the layout set to the database.
	*
	* @return the new layout set
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutSet create() {
		return _layoutSetLocalService.create();
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet delete(
		com.liferay.portal.kernel.model.LayoutSet publishedLayoutSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.delete(publishedLayoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet deleteDraft(
		com.liferay.portal.kernel.model.LayoutSet draftLayoutSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.deleteDraft(draftLayoutSet);
	}

	/**
	* Deletes the layout set from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was removed
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutSet deleteLayoutSet(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
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
	public com.liferay.portal.kernel.model.LayoutSet deleteLayoutSet(
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.deleteLayoutSet(layoutSetId);
	}

	@Override
	public void deleteLayoutSet(long groupId, boolean privateLayout,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.deleteLayoutSet(groupId, privateLayout,
			serviceContext);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetVersion deleteVersion(
		com.liferay.portal.kernel.model.LayoutSetVersion layoutSetVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.deleteVersion(layoutSetVersion);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public com.liferay.portal.kernel.model.LayoutSet fetchDraft(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.fetchDraft(layoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchDraft(long primaryKey) {
		return _layoutSetLocalService.fetchDraft(primaryKey);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetVersion fetchLatestVersion(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.fetchLatestVersion(layoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		long layoutSetId) {
		return _layoutSetLocalService.fetchLayoutSet(layoutSetId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		long groupId, boolean privateLayout) {
		return _layoutSetLocalService.fetchLayoutSet(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		String virtualHostname) {
		return _layoutSetLocalService.fetchLayoutSet(virtualHostname);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSetByLogoId(
		boolean privateLayout, long logoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.fetchLayoutSetByLogoId(privateLayout,
			logoId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchPublished(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.fetchPublished(layoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchPublished(
		long primaryKey) {
		return _layoutSetLocalService.fetchPublished(primaryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutSetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getDraft(
		com.liferay.portal.kernel.model.LayoutSet layoutSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getDraft(layoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getDraft(long primaryKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getDraft(primaryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutSetLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout set with the primary key.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set
	* @throws PortalException if a layout set with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(layoutSetId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(virtualHostname);
	}

	/**
	* Returns a range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of layout sets
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutSet> getLayoutSets(
		int start, int end) {
		return _layoutSetLocalService.getLayoutSets(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
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
	public String getOSGiServiceIdentifier() {
		return _layoutSetLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetVersion getVersion(
		com.liferay.portal.kernel.model.LayoutSet layoutSet, int version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getVersion(layoutSet, version);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutSetVersion> getVersions(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.getVersions(layoutSet);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet publishDraft(
		com.liferay.portal.kernel.model.LayoutSet draftLayoutSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.publishDraft(draftLayoutSet);
	}

	@Override
	public void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener<com.liferay.portal.kernel.model.LayoutSet, com.liferay.portal.kernel.model.LayoutSetVersion> versionServiceListener) {
		_layoutSetLocalService.registerListener(versionServiceListener);
	}

	@Override
	public void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener<com.liferay.portal.kernel.model.LayoutSet, com.liferay.portal.kernel.model.LayoutSetVersion> versionServiceListener) {
		_layoutSetLocalService.unregisterListener(versionServiceListener);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateDraft(
		com.liferay.portal.kernel.model.LayoutSet draftLayoutSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateDraft(draftLayoutSet);
	}

	/**
	* Updates the layout set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was updated
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLayoutSet(
		com.liferay.portal.kernel.model.LayoutSet draftLayoutSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLayoutSet(draftLayoutSet);
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
		String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(groupId,
			privateLayout, layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			bytes);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			file);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			is);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.InputStream is,
		boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			is, cleanUpStream);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLookAndFeel(
		long groupId, boolean privateLayout, String themeId,
		String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLookAndFeel(groupId, privateLayout,
			themeId, colorSchemeId, css);
	}

	@Override
	public void updateLookAndFeel(long groupId, String themeId,
		String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLookAndFeel(groupId, themeId,
			colorSchemeId, css);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updatePageCount(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateSettings(
		long groupId, boolean privateLayout, String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateSettings(groupId, privateLayout,
			settings);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateVirtualHost(
		long groupId, boolean privateLayout, String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateVirtualHost(groupId, privateLayout,
			virtualHostname);
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