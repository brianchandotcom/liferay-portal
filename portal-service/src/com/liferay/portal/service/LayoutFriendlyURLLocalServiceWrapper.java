/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides a wrapper for {@link LayoutFriendlyURLLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutFriendlyURLLocalService
 * @generated
 */
public class LayoutFriendlyURLLocalServiceWrapper
	implements LayoutFriendlyURLLocalService,
		ServiceWrapper<LayoutFriendlyURLLocalService> {
	public LayoutFriendlyURLLocalServiceWrapper(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		_layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	/**
	* Adds the layout friendly u r l to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	* @return the layout friendly u r l that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL addLayoutFriendlyURL(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURL(layoutFriendlyURL);
	}

	/**
	* Creates a new layout friendly u r l with the primary key. Does not add the layout friendly u r l to the database.
	*
	* @param layoutFriendlyURLId the primary key for the new layout friendly u r l
	* @return the new layout friendly u r l
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL createLayoutFriendlyURL(
		long layoutFriendlyURLId) {
		return _layoutFriendlyURLLocalService.createLayoutFriendlyURL(layoutFriendlyURLId);
	}

	/**
	* Deletes the layout friendly u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l that was removed
	* @throws PortalException if a layout friendly u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL deleteLayoutFriendlyURL(
		long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(layoutFriendlyURLId);
	}

	/**
	* Deletes the layout friendly u r l from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	* @return the layout friendly u r l that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL deleteLayoutFriendlyURL(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(layoutFriendlyURL);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutFriendlyURLLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURL(
		long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(layoutFriendlyURLId);
	}

	/**
	* Returns the layout friendly u r l with the primary key.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l
	* @throws PortalException if a layout friendly u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURL(
		long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURL(layoutFriendlyURLId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the layout friendly u r l matching the UUID and group.
	*
	* @param uuid the layout friendly u r l's UUID
	* @param groupId the primary key of the group
	* @return the matching layout friendly u r l
	* @throws PortalException if a matching layout friendly u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the layout friendly u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of layout friendly u r ls
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLs(start, end);
	}

	/**
	* Returns the number of layout friendly u r ls.
	*
	* @return the number of layout friendly u r ls
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getLayoutFriendlyURLsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLsCount();
	}

	/**
	* Updates the layout friendly u r l in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	* @return the layout friendly u r l that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL updateLayoutFriendlyURL(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.updateLayoutFriendlyURL(layoutFriendlyURL);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _layoutFriendlyURLLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutFriendlyURLLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL addLayoutFriendlyURL(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout, java.lang.String friendlyURL,
		java.lang.String languageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURL(userId,
			companyId, groupId, plid, privateLayout, friendlyURL, languageId,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> addLayoutFriendlyURLs(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURLs(userId,
			companyId, groupId, plid, privateLayout, friendlyURLMap,
			serviceContext);
	}

	@Override
	public void deleteLayoutFriendlyURL(long plid, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(plid, languageId);
	}

	@Override
	public void deleteLayoutFriendlyURLs(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutFriendlyURLLocalService.deleteLayoutFriendlyURLs(plid);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURL(
		long plid, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURL(plid,
			languageId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURL(
		long plid, java.lang.String languageId, boolean useDefault)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURL(plid,
			languageId, useDefault);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLs(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLs(plid);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL updateLayoutFriendlyURL(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout, java.lang.String friendlyURL,
		java.lang.String languageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.updateLayoutFriendlyURL(userId,
			companyId, groupId, plid, privateLayout, friendlyURL, languageId,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> updateLayoutFriendlyURLs(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutFriendlyURLLocalService.updateLayoutFriendlyURLs(userId,
			companyId, groupId, plid, privateLayout, friendlyURLMap,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public LayoutFriendlyURLLocalService getWrappedLayoutFriendlyURLLocalService() {
		return _layoutFriendlyURLLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedLayoutFriendlyURLLocalService(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		_layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	@Override
	public LayoutFriendlyURLLocalService getWrappedService() {
		return _layoutFriendlyURLLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		_layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;
}