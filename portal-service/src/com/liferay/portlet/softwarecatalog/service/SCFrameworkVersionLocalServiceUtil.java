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

package com.liferay.portlet.softwarecatalog.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for SCFrameworkVersion. This utility wraps
 * {@link com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionLocalService
 * @see com.liferay.portlet.softwarecatalog.service.base.SCFrameworkVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl
 * @generated
 */
public class SCFrameworkVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the s c framework version to the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSCFrameworkVersion(scFrameworkVersion);
	}

	/**
	* Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	*
	* @param frameworkVersionId the primary key for the new s c framework version
	* @return the new s c framework version
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion createSCFrameworkVersion(
		long frameworkVersionId) {
		return getService().createSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Deletes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws PortalException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Deletes the s c framework version from the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSCFrameworkVersion(scFrameworkVersion);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Returns the s c framework version with the primary key.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws PortalException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCFrameworkVersion(frameworkVersionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCFrameworkVersions(start, end);
	}

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCFrameworkVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCFrameworkVersionsCount();
	}

	/**
	* Updates the s c framework version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSCFrameworkVersion(scFrameworkVersion);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearSCProductVersionSCFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCProductVersionSCFrameworkVersionsCount(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCProductVersionSCFrameworkVersionsCount(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasSCProductVersionSCFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFrameworkVersion(userId, name, url, active, priority,
			serviceContext);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersionId,
			addGroupPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersionId, groupPermissions,
			guestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersion,
			addGroupPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersion, groupPermissions,
			guestPermissions);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFrameworkVersion(frameworkVersionId);
	}

	public static void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFrameworkVersion(frameworkVersion);
	}

	public static void deleteFrameworkVersions(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFrameworkVersions(groupId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFrameworkVersions(groupId, active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFrameworkVersions(groupId, active, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFrameworkVersions(groupId, start, end);
	}

	public static int getFrameworkVersionsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFrameworkVersionsCount(groupId);
	}

	public static int getFrameworkVersionsCount(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFrameworkVersionsCount(groupId, active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductVersionFrameworkVersions(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFrameworkVersion(frameworkVersionId, name, url,
			active, priority);
	}

	public static SCFrameworkVersionLocalService getService() {
		if (_service == null) {
			_service = (SCFrameworkVersionLocalService)PortalBeanLocatorUtil.locate(SCFrameworkVersionLocalService.class.getName());

			ReferenceRegistry.registerReference(SCFrameworkVersionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(SCFrameworkVersionLocalService service) {
	}

	private static SCFrameworkVersionLocalService _service;
}