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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentEntryLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkLocalService
 * @generated
 */
@ProviderType
public class FragmentEntryLinkLocalServiceWrapper
	implements FragmentEntryLinkLocalService,
		ServiceWrapper<FragmentEntryLinkLocalService> {
	public FragmentEntryLinkLocalServiceWrapper(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	/**
	* Adds the fragment entry link to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was added
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink addFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {
		return _fragmentEntryLinkLocalService.addFragmentEntryLink(fragmentEntryLink);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntryLink addFragmentEntryLink(
		long userId, long groupId, long originalFragmentEntryLinkId,
		long fragmentEntryId, long classNameId, long classPK,
		java.lang.String css, java.lang.String html, java.lang.String js,
		java.lang.String editableValues, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.addFragmentEntryLink(userId,
			groupId, originalFragmentEntryLinkId, fragmentEntryId, classNameId,
			classPK, css, html, js, editableValues, position, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntryLink addFragmentEntryLink(
		long userId, long groupId, long fragmentEntryId, long classNameId,
		long classPK, java.lang.String css, java.lang.String html,
		java.lang.String js, java.lang.String editableValues, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.addFragmentEntryLink(userId,
			groupId, fragmentEntryId, classNameId, classPK, css, html, js,
			editableValues, position, serviceContext);
	}

	/**
	* Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	*
	* @param fragmentEntryLinkId the primary key for the new fragment entry link
	* @return the new fragment entry link
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink createFragmentEntryLink(
		long fragmentEntryLinkId) {
		return _fragmentEntryLinkLocalService.createFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	* Deletes the fragment entry link from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was removed
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink deleteFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {
		return _fragmentEntryLinkLocalService.deleteFragmentEntryLink(fragmentEntryLink);
	}

	/**
	* Deletes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link that was removed
	* @throws PortalException if a fragment entry link with the primary key could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink deleteFragmentEntryLink(
		long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.deleteFragmentEntryLink(fragmentEntryLinkId);
	}

	@Override
	public void deleteFragmentEntryLinks(long groupId) {
		_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(groupId);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntryLink> deleteLayoutPageTemplateEntryFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {
		return _fragmentEntryLinkLocalService.deleteLayoutPageTemplateEntryFragmentEntryLinks(groupId,
			classNameId, classPK);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fragmentEntryLinkLocalService.dynamicQuery();
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
		return _fragmentEntryLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fragmentEntryLinkLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fragmentEntryLinkLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _fragmentEntryLinkLocalService.dynamicQueryCount(dynamicQuery);
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
		return _fragmentEntryLinkLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntryLink fetchFragmentEntryLink(
		long fragmentEntryLinkId) {
		return _fragmentEntryLinkLocalService.fetchFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	* Returns the fragment entry link matching the UUID and group.
	*
	* @param uuid the fragment entry link's UUID
	* @param groupId the primary key of the group
	* @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink fetchFragmentEntryLinkByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _fragmentEntryLinkLocalService.fetchFragmentEntryLinkByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _fragmentEntryLinkLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _fragmentEntryLinkLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns the fragment entry link with the primary key.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link
	* @throws PortalException if a fragment entry link with the primary key could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink getFragmentEntryLink(
		long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.getFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	* Returns the fragment entry link matching the UUID and group.
	*
	* @param uuid the fragment entry link's UUID
	* @param groupId the primary key of the group
	* @return the matching fragment entry link
	* @throws PortalException if a matching fragment entry link could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink getFragmentEntryLinkByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the fragment entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of fragment entry links
	*/
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntryLink> getFragmentEntryLinks(
		int start, int end) {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(groupId,
			classNameId, classPK);
	}

	/**
	* Returns all the fragment entry links matching the UUID and company.
	*
	* @param uuid the UUID of the fragment entry links
	* @param companyId the primary key of the company
	* @return the matching fragment entry links, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntryLink> getFragmentEntryLinksByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinksByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of fragment entry links matching the UUID and company.
	*
	* @param uuid the UUID of the fragment entry links
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching fragment entry links, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntryLink> getFragmentEntryLinksByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntryLink> orderByComparator) {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinksByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of fragment entry links.
	*
	* @return the number of fragment entry links
	*/
	@Override
	public int getFragmentEntryLinksCount() {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinksCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _fragmentEntryLinkLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _fragmentEntryLinkLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the fragment entry link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was updated
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {
		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(fragmentEntryLink);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		long fragmentEntryLinkId, int position) {
		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(fragmentEntryLinkId,
			position);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		long userId, long fragmentEntryLinkId,
		long originalFragmentEntryLinkId, long fragmentEntryId,
		long classNameId, long classPK, java.lang.String css,
		java.lang.String html, java.lang.String js,
		java.lang.String editableValues, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(userId,
			fragmentEntryLinkId, originalFragmentEntryLinkId, fragmentEntryId,
			classNameId, classPK, css, html, js, editableValues, position,
			serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		long fragmentEntryLinkId, java.lang.String editableValues) {
		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(fragmentEntryLinkId,
			editableValues);
	}

	@Override
	public void updateFragmentEntryLinks(long userId, long groupId,
		long classNameId, long classPK, long[] fragmentEntryIds,
		java.lang.String editableValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(userId,
			groupId, classNameId, classPK, fragmentEntryIds, editableValues,
			serviceContext);
	}

	@Override
	public FragmentEntryLinkLocalService getWrappedService() {
		return _fragmentEntryLinkLocalService;
	}

	@Override
	public void setWrappedService(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
}