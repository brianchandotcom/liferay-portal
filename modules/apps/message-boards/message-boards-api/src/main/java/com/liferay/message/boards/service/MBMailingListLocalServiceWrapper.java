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

package com.liferay.message.boards.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBMailingListLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingListLocalService
 * @generated
 */
public class MBMailingListLocalServiceWrapper
	implements MBMailingListLocalService,
			   ServiceWrapper<MBMailingListLocalService> {

	public MBMailingListLocalServiceWrapper(
		MBMailingListLocalService mbMailingListLocalService) {

		_mbMailingListLocalService = mbMailingListLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBMailingListLocalServiceUtil} to access the message boards mailing list local service. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBMailingListLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList addMailingList(
			long userId, long groupId, long categoryId, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean allowAnonymous,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.addMailingList(
			userId, groupId, categoryId, emailAddress, inProtocol, inServerName,
			inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
			outEmailAddress, outCustom, outServerName, outServerPort, outUseSSL,
			outUserName, outPassword, allowAnonymous, active, serviceContext);
	}

	/**
	 * Adds the message boards mailing list to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMailingList the message boards mailing list
	 * @return the message boards mailing list that was added
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList addMBMailingList(
		com.liferay.message.boards.model.MBMailingList mbMailingList) {

		return _mbMailingListLocalService.addMBMailingList(mbMailingList);
	}

	/**
	 * Creates a new message boards mailing list with the primary key. Does not add the message boards mailing list to the database.
	 *
	 * @param mailingListId the primary key for the new message boards mailing list
	 * @return the new message boards mailing list
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList createMBMailingList(
		long mailingListId) {

		return _mbMailingListLocalService.createMBMailingList(mailingListId);
	}

	@Override
	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbMailingListLocalService.deleteCategoryMailingList(
			groupId, categoryId);
	}

	@Override
	public void deleteMailingList(long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbMailingListLocalService.deleteMailingList(mailingListId);
	}

	@Override
	public void deleteMailingList(
			com.liferay.message.boards.model.MBMailingList mailingList)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbMailingListLocalService.deleteMailingList(mailingList);
	}

	/**
	 * Deletes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list that was removed
	 * @throws PortalException if a message boards mailing list with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList deleteMBMailingList(
			long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.deleteMBMailingList(mailingListId);
	}

	/**
	 * Deletes the message boards mailing list from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMailingList the message boards mailing list
	 * @return the message boards mailing list that was removed
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList deleteMBMailingList(
		com.liferay.message.boards.model.MBMailingList mbMailingList) {

		return _mbMailingListLocalService.deleteMBMailingList(mbMailingList);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbMailingListLocalService.dynamicQuery();
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

		return _mbMailingListLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBMailingListModelImpl</code>.
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

		return _mbMailingListLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBMailingListModelImpl</code>.
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

		return _mbMailingListLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _mbMailingListLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mbMailingListLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.message.boards.model.MBMailingList
		fetchCategoryMailingList(long groupId, long categoryId) {

		return _mbMailingListLocalService.fetchCategoryMailingList(
			groupId, categoryId);
	}

	@Override
	public com.liferay.message.boards.model.MBMailingList fetchMBMailingList(
		long mailingListId) {

		return _mbMailingListLocalService.fetchMBMailingList(mailingListId);
	}

	/**
	 * Returns the message boards mailing list matching the UUID and group.
	 *
	 * @param uuid the message boards mailing list's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList
		fetchMBMailingListByUuidAndGroupId(String uuid, long groupId) {

		return _mbMailingListLocalService.fetchMBMailingListByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mbMailingListLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.message.boards.model.MBMailingList
			getCategoryMailingList(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.getCategoryMailingList(
			groupId, categoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _mbMailingListLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbMailingListLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the message boards mailing list with the primary key.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list
	 * @throws PortalException if a message boards mailing list with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList getMBMailingList(
			long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.getMBMailingList(mailingListId);
	}

	/**
	 * Returns the message boards mailing list matching the UUID and group.
	 *
	 * @param uuid the message boards mailing list's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards mailing list
	 * @throws PortalException if a matching message boards mailing list could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList
			getMBMailingListByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.getMBMailingListByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the message boards mailing lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBMailingListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @return the range of message boards mailing lists
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBMailingList>
		getMBMailingLists(int start, int end) {

		return _mbMailingListLocalService.getMBMailingLists(start, end);
	}

	/**
	 * Returns all the message boards mailing lists matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards mailing lists
	 * @param companyId the primary key of the company
	 * @return the matching message boards mailing lists, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBMailingList>
		getMBMailingListsByUuidAndCompanyId(String uuid, long companyId) {

		return _mbMailingListLocalService.getMBMailingListsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of message boards mailing lists matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards mailing lists
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching message boards mailing lists, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBMailingList>
		getMBMailingListsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMailingList>
					orderByComparator) {

		return _mbMailingListLocalService.getMBMailingListsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of message boards mailing lists.
	 *
	 * @return the number of message boards mailing lists
	 */
	@Override
	public int getMBMailingListsCount() {
		return _mbMailingListLocalService.getMBMailingListsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbMailingListLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.message.boards.model.MBMailingList updateMailingList(
			long mailingListId, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean allowAnonymous, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbMailingListLocalService.updateMailingList(
			mailingListId, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, allowAnonymous, active, serviceContext);
	}

	/**
	 * Updates the message boards mailing list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbMailingList the message boards mailing list
	 * @return the message boards mailing list that was updated
	 */
	@Override
	public com.liferay.message.boards.model.MBMailingList updateMBMailingList(
		com.liferay.message.boards.model.MBMailingList mbMailingList) {

		return _mbMailingListLocalService.updateMBMailingList(mbMailingList);
	}

	@Override
	public MBMailingListLocalService getWrappedService() {
		return _mbMailingListLocalService;
	}

	@Override
	public void setWrappedService(
		MBMailingListLocalService mbMailingListLocalService) {

		_mbMailingListLocalService = mbMailingListLocalService;
	}

	private MBMailingListLocalService _mbMailingListLocalService;

}