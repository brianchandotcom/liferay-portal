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

package com.liferay.asset.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for AssetVocabulary. This utility wraps
 * <code>com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyLocalService
 * @generated
 */
public class AssetVocabularyLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset vocabulary to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabulary the asset vocabulary
	 * @return the asset vocabulary that was added
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
		addAssetVocabulary(
			com.liferay.asset.kernel.model.AssetVocabulary assetVocabulary) {

		return getService().addAssetVocabulary(assetVocabulary);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			addDefaultVocabulary(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDefaultVocabulary(groupId);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addVocabulary(
			userId, groupId, title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addVocabulary(
			userId, groupId, title, serviceContext);
	}

	public static void addVocabularyResources(
			com.liferay.asset.kernel.model.AssetVocabulary vocabulary,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addVocabularyResources(
			vocabulary, addGroupPermissions, addGuestPermissions);
	}

	public static void addVocabularyResources(
			com.liferay.asset.kernel.model.AssetVocabulary vocabulary,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addVocabularyResources(vocabulary, modelPermissions);
	}

	/**
	 * Creates a new asset vocabulary with the primary key. Does not add the asset vocabulary to the database.
	 *
	 * @param vocabularyId the primary key for the new asset vocabulary
	 * @return the new asset vocabulary
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
		createAssetVocabulary(long vocabularyId) {

		return getService().createAssetVocabulary(vocabularyId);
	}

	/**
	 * Deletes the asset vocabulary from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabulary the asset vocabulary
	 * @return the asset vocabulary that was removed
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
		deleteAssetVocabulary(
			com.liferay.asset.kernel.model.AssetVocabulary assetVocabulary) {

		return getService().deleteAssetVocabulary(assetVocabulary);
	}

	/**
	 * Deletes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary that was removed
	 * @throws PortalException if a asset vocabulary with the primary key could not be found
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
			deleteAssetVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetVocabulary(vocabularyId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteVocabularies(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteVocabularies(groupId);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			deleteVocabulary(
				com.liferay.asset.kernel.model.AssetVocabulary vocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteVocabulary(vocabulary);
	}

	public static void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteVocabulary(vocabularyId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.asset.kernel.model.AssetVocabulary
		fetchAssetVocabulary(long vocabularyId) {

		return getService().fetchAssetVocabulary(vocabularyId);
	}

	/**
	 * Returns the asset vocabulary with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the asset vocabulary's external reference code
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
		fetchAssetVocabularyByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchAssetVocabularyByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the asset vocabulary matching the UUID and group.
	 *
	 * @param uuid the asset vocabulary's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
		fetchAssetVocabularyByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
		fetchGroupVocabulary(long groupId, String name) {

		return getService().fetchGroupVocabulary(groupId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the asset vocabularies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of asset vocabularies
	 */
	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getAssetVocabularies(int start, int end) {

		return getService().getAssetVocabularies(start, end);
	}

	/**
	 * Returns all the asset vocabularies matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset vocabularies
	 * @param companyId the primary key of the company
	 * @return the matching asset vocabularies, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getAssetVocabulariesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getAssetVocabulariesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of asset vocabularies matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset vocabularies
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset vocabularies, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getAssetVocabulariesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetVocabulary>
					orderByComparator) {

		return getService().getAssetVocabulariesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset vocabularies.
	 *
	 * @return the number of asset vocabularies
	 */
	public static int getAssetVocabulariesCount() {
		return getService().getAssetVocabulariesCount();
	}

	/**
	 * Returns the asset vocabulary with the primary key.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary
	 * @throws PortalException if a asset vocabulary with the primary key could not be found
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
			getAssetVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetVocabulary(vocabularyId);
	}

	/**
	 * Returns the asset vocabulary matching the UUID and group.
	 *
	 * @param uuid the asset vocabulary's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset vocabulary
	 * @throws PortalException if a matching asset vocabulary could not be found
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
			getAssetVocabularyByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getCompanyVocabularies(long companyId) {

		return getService().getCompanyVocabularies(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupsVocabularies(long[] groupIds) {

		return getService().getGroupsVocabularies(groupIds);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupsVocabularies(long[] groupIds, String className) {

		return getService().getGroupsVocabularies(groupIds, className);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupsVocabularies(
			long[] groupIds, String className, long classTypePK) {

		return getService().getGroupsVocabularies(
			groupIds, className, classTypePK);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getGroupVocabularies(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupVocabularies(groupId);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getGroupVocabularies(long groupId, boolean addDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupVocabularies(groupId, addDefaultVocabulary);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(
			long groupId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetVocabulary> obc) {

		return getService().getGroupVocabularies(
			groupId, name, start, end, obc);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
		getGroupVocabularies(long[] groupIds) {

		return getService().getGroupVocabularies(groupIds);
	}

	public static int getGroupVocabulariesCount(long[] groupIds) {
		return getService().getGroupVocabulariesCount(groupIds);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			getGroupVocabulary(long groupId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupVocabulary(groupId, name);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getVocabularies(com.liferay.portal.kernel.search.Hits hits)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVocabularies(hits);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetVocabulary>
			getVocabularies(long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVocabularies(vocabularyIds);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary getVocabulary(
			long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVocabulary(vocabularyId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.asset.kernel.model.AssetVocabulary> searchVocabularies(
				long companyId, long groupId, String title, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchVocabularies(
			companyId, groupId, title, start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.asset.kernel.model.AssetVocabulary> searchVocabularies(
				long companyId, long groupId, String title, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchVocabularies(
			companyId, groupId, title, start, end, sort);
	}

	/**
	 * Updates the asset vocabulary in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabulary the asset vocabulary
	 * @return the asset vocabulary that was updated
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
		updateAssetVocabulary(
			com.liferay.asset.kernel.model.AssetVocabulary assetVocabulary) {

		return getService().updateAssetVocabulary(assetVocabulary);
	}

	public static com.liferay.asset.kernel.model.AssetVocabulary
			updateVocabulary(
				long vocabularyId, String title,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String settings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateVocabulary(
			vocabularyId, title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public static AssetVocabularyLocalService getService() {
		if (_service == null) {
			_service =
				(AssetVocabularyLocalService)PortalBeanLocatorUtil.locate(
					AssetVocabularyLocalService.class.getName());
		}

		return _service;
	}

	private static AssetVocabularyLocalService _service;

}