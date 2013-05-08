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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for AssetVocabulary. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyLocalService
 * @see com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl
 * @generated
 */
public class AssetVocabularyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset vocabulary to the database. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary
	* @return the asset vocabulary that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary addAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetVocabulary(assetVocabulary);
	}

	/**
	* Creates a new asset vocabulary with the primary key. Does not add the asset vocabulary to the database.
	*
	* @param vocabularyId the primary key for the new asset vocabulary
	* @return the new asset vocabulary
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary createAssetVocabulary(
		long vocabularyId) {
		return getService().createAssetVocabulary(vocabularyId);
	}

	/**
	* Deletes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param vocabularyId the primary key of the asset vocabulary
	* @return the asset vocabulary that was removed
	* @throws PortalException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary deleteAssetVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetVocabulary(vocabularyId);
	}

	/**
	* Deletes the asset vocabulary from the database. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary
	* @return the asset vocabulary that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary deleteAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetVocabulary(assetVocabulary);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.asset.model.AssetVocabulary fetchAssetVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchAssetVocabulary(vocabularyId);
	}

	/**
	* Returns the asset vocabulary with the primary key.
	*
	* @param vocabularyId the primary key of the asset vocabulary
	* @return the asset vocabulary
	* @throws PortalException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary getAssetVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabulary(vocabularyId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the asset vocabulary matching the UUID and group.
	*
	* @param uuid the asset vocabulary's UUID
	* @param groupId the primary key of the group
	* @return the matching asset vocabulary
	* @throws PortalException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary getAssetVocabularyByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the asset vocabularies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getAssetVocabularies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabularies(start, end);
	}

	/**
	* Returns the number of asset vocabularies.
	*
	* @return the number of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetVocabulariesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabulariesCount();
	}

	/**
	* Updates the asset vocabulary in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary
	* @return the asset vocabulary that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary updateAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetVocabulary(assetVocabulary);
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

	public static com.liferay.portlet.asset.model.AssetVocabulary addDefaultVocabulary(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addDefaultVocabulary(groupId);
	}

	/**
	* @deprecated As of 6.1.0
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		long userId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addVocabulary(userId, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		long userId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addVocabulary(userId, title, titleMap, descriptionMap,
			settings, serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		long userId, java.lang.String title,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addVocabulary(userId, title, serviceContext);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addVocabularyResources(vocabulary, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addVocabularyResources(vocabulary, groupPermissions,
			guestPermissions);
	}

	public static void deleteVocabularies(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteVocabularies(groupId);
	}

	public static void deleteVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteVocabulary(vocabulary);
	}

	public static void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteVocabulary(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupsVocabularies(groupIds);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupsVocabularies(groupIds, className);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupVocabularies(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, boolean addDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupVocabularies(groupId, addDefaultVocabulary);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupVocabularies(groupId, name, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary getGroupVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupVocabulary(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getVocabularies(
		long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getVocabularies(vocabularyIds);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getVocabulary(vocabularyId);
	}

	/**
	* @deprecated As of 6.1.0
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateVocabulary(vocabularyId, titleMap, descriptionMap,
			settings, serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateVocabulary(vocabularyId, title, titleMap,
			descriptionMap, settings, serviceContext);
	}

	public static AssetVocabularyLocalService getService() {
		if (_service == null) {
			_service = (AssetVocabularyLocalService)PortalBeanLocatorUtil.locate(AssetVocabularyLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetVocabularyLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(AssetVocabularyLocalService service) {
	}

	private static AssetVocabularyLocalService _service;
}