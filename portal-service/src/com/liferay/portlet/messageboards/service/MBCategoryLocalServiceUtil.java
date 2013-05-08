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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for MBCategory. This utility wraps
 * {@link com.liferay.portlet.messageboards.service.impl.MBCategoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryLocalService
 * @see com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBCategoryLocalServiceImpl
 * @generated
 */
public class MBCategoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBCategoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the message boards category to the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category
	* @return the message boards category that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory addMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMBCategory(mbCategory);
	}

	/**
	* Creates a new message boards category with the primary key. Does not add the message boards category to the database.
	*
	* @param categoryId the primary key for the new message boards category
	* @return the new message boards category
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory createMBCategory(
		long categoryId) {
		return getService().createMBCategory(categoryId);
	}

	/**
	* Deletes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category that was removed
	* @throws PortalException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory deleteMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteMBCategory(categoryId);
	}

	/**
	* Deletes the message boards category from the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category
	* @return the message boards category that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory deleteMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteMBCategory(mbCategory);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory fetchMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchMBCategory(categoryId);
	}

	/**
	* Returns the message boards category with the primary key.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category
	* @throws PortalException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory getMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategory(categoryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the message boards category matching the UUID and group.
	*
	* @param uuid the message boards category's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards category
	* @throws PortalException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory getMBCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategoryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategories(start, end);
	}

	/**
	* Returns the number of message boards categories.
	*
	* @return the number of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int getMBCategoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategoriesCount();
	}

	/**
	* Updates the message boards category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category
	* @return the message boards category that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBCategory(mbCategory);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCategory(userId, parentCategoryId, name, description,
			serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean allowAnonymous,
		boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCategory(userId, parentCategoryId, name, description,
			displayStyle, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, allowAnonymous, mailingListActive, serviceContext);
	}

	public static void addCategoryResources(long categoryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(categoryId, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(categoryId, groupPermissions, guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, groupPermissions, guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategories(groupId);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(category);
	}

	public static void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(category, includeTrashedEntries);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId, parentCategoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategories(groupId, parentCategoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId, parentCategoryIds, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategories(groupId, parentCategoryIds, status, start, end);
	}

	public static java.util.List<java.lang.Object> getCategoriesAndThreads(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesAndThreads(groupId, categoryId);
	}

	public static int getCategoriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId);
	}

	public static int getCategoriesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, status);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryId, status);
	}

	public static int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryIds);
	}

	public static int getCategoriesCount(long groupId,
		long[] parentCategoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategoriesCount(groupId, parentCategoryIds, status);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCompanyCategories(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyCategories(companyId, start, end);
	}

	public static int getCompanyCategoriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyCategoriesCount(companyId);
	}

	public static java.util.List<java.lang.Long> getSubcategoryIds(
		java.util.List<java.lang.Long> categoryIds, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscribedCategories(groupId, userId, start, end);
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscribedCategoriesCount(groupId, userId);
	}

	public static void moveCategoriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().moveCategoriesToTrash(groupId, userId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory moveCategory(
		long categoryId, long parentCategoryId, boolean mergeWithParentCategory)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveCategory(categoryId, parentCategoryId,
			mergeWithParentCategory);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory moveCategoryFromTrash(
		long userId, long categoryId, long newCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveCategoryFromTrash(userId, categoryId, newCategoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory moveCategoryToTrash(
		long userId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveCategoryToTrash(userId, categoryId);
	}

	public static void restoreCategoryFromTrash(long userId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreCategoryFromTrash(userId, categoryId);
	}

	public static void subscribeCategory(long userId, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribeCategory(userId, groupId, categoryId);
	}

	public static void unsubscribeCategory(long userId, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribeCategory(userId, groupId, categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean allowAnonymous,
		boolean mailingListActive, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCategory(categoryId, parentCategoryId, name,
			description, displayStyle, emailAddress, inProtocol, inServerName,
			inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
			outEmailAddress, outCustom, outServerName, outServerPort,
			outUseSSL, outUserName, outPassword, allowAnonymous,
			mailingListActive, mergeWithParentCategory, serviceContext);
	}

	public static void updateDependentStatus(
		com.liferay.portal.model.User user,
		java.util.List<java.lang.Object> categoriesAndThreads, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateDependentStatus(user, categoriesAndThreads, status);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateStatus(
		long userId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatus(userId, categoryId, status);
	}

	public static MBCategoryLocalService getService() {
		if (_service == null) {
			_service = (MBCategoryLocalService)PortalBeanLocatorUtil.locate(MBCategoryLocalService.class.getName());

			ReferenceRegistry.registerReference(MBCategoryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(MBCategoryLocalService service) {
	}

	private static MBCategoryLocalService _service;
}