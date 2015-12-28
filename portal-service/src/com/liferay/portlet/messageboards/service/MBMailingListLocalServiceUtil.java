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

package com.liferay.portlet.messageboards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for MBMailingList. This utility wraps
 * {@link com.liferay.portlet.messageboards.service.impl.MBMailingListLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingListLocalService
 * @see com.liferay.portlet.messageboards.service.base.MBMailingListLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBMailingListLocalServiceImpl
 * @generated
 */
@ProviderType
public class MBMailingListLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBMailingListLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the message boards mailing list to the database. Also notifies the appropriate model listeners.
	*
	* @param mbMailingList the message boards mailing list
	* @return the message boards mailing list that was added
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList addMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		return getService().addMBMailingList(mbMailingList);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList addMailingList(
		long userId, long groupId, long categoryId,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean allowAnonymous, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMailingList(userId, groupId, categoryId, emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			allowAnonymous, active, serviceContext);
	}

	/**
	* Creates a new message boards mailing list with the primary key. Does not add the message boards mailing list to the database.
	*
	* @param mailingListId the primary key for the new message boards mailing list
	* @return the new message boards mailing list
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList createMBMailingList(
		long mailingListId) {
		return getService().createMBMailingList(mailingListId);
	}

	public static void deleteCategoryMailingList(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategoryMailingList(groupId, categoryId);
	}

	/**
	* Deletes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list that was removed
	* @throws PortalException if a message boards mailing list with the primary key could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList deleteMBMailingList(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMBMailingList(mailingListId);
	}

	/**
	* Deletes the message boards mailing list from the database. Also notifies the appropriate model listeners.
	*
	* @param mbMailingList the message boards mailing list
	* @return the message boards mailing list that was removed
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList deleteMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		return getService().deleteMBMailingList(mbMailingList);
	}

	public static void deleteMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mailingList)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteMailingList(mailingList);
	}

	public static void deleteMailingList(long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteMailingList(mailingListId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchCategoryMailingList(
		long groupId, long categoryId) {
		return getService().fetchCategoryMailingList(groupId, categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchMBMailingList(
		long mailingListId) {
		return getService().fetchMBMailingList(mailingListId);
	}

	/**
	* Returns the message boards mailing list matching the UUID and group.
	*
	* @param uuid the message boards mailing list's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchMBMailingListByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchMBMailingListByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList getCategoryMailingList(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategoryMailingList(groupId, categoryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the message boards mailing list with the primary key.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list
	* @throws PortalException if a message boards mailing list with the primary key could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList getMBMailingList(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMBMailingList(mailingListId);
	}

	/**
	* Returns the message boards mailing list matching the UUID and group.
	*
	* @param uuid the message boards mailing list's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards mailing list
	* @throws PortalException if a matching message boards mailing list could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList getMBMailingListByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMBMailingListByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the message boards mailing lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of message boards mailing lists
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMBMailingLists(
		int start, int end) {
		return getService().getMBMailingLists(start, end);
	}

	/**
	* Returns all the message boards mailing lists matching the UUID and company.
	*
	* @param uuid the UUID of the message boards mailing lists
	* @param companyId the primary key of the company
	* @return the matching message boards mailing lists, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMBMailingListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getMBMailingListsByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMBMailingListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.messageboards.model.MBMailingList> orderByComparator) {
		return getService()
				   .getMBMailingListsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of message boards mailing lists.
	*
	* @return the number of message boards mailing lists
	*/
	public static int getMBMailingListsCount() {
		return getService().getMBMailingListsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the message boards mailing list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbMailingList the message boards mailing list
	* @return the message boards mailing list that was updated
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList updateMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		return getService().updateMBMailingList(mbMailingList);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateMailingList(
		long mailingListId, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean allowAnonymous, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMailingList(mailingListId, emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword, allowAnonymous,
			active, serviceContext);
	}

	public static MBMailingListLocalService getService() {
		if (_service == null) {
			_service = (MBMailingListLocalService)PortalBeanLocatorUtil.locate(MBMailingListLocalService.class.getName());

			ReferenceRegistry.registerReference(MBMailingListLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static MBMailingListLocalService _service;
}