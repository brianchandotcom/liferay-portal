/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceBOMFolderApplicationRelLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRelLocalService
 * @generated
 */
public class CommerceBOMFolderApplicationRelLocalServiceWrapper
	implements CommerceBOMFolderApplicationRelLocalService,
			   ServiceWrapper<CommerceBOMFolderApplicationRelLocalService> {

	public CommerceBOMFolderApplicationRelLocalServiceWrapper(
		CommerceBOMFolderApplicationRelLocalService
			commerceBOMFolderApplicationRelLocalService) {

		_commerceBOMFolderApplicationRelLocalService =
			commerceBOMFolderApplicationRelLocalService;
	}

	/**
	 * Adds the commerce bom folder application rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceBOMFolderApplicationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceBOMFolderApplicationRel the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was added
	 */
	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
		addCommerceBOMFolderApplicationRel(
			com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
				commerceBOMFolderApplicationRel) {

		return _commerceBOMFolderApplicationRelLocalService.
			addCommerceBOMFolderApplicationRel(commerceBOMFolderApplicationRel);
	}

	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
			addCommerceBOMFolderApplicationRel(
				long userId, long commerceBOMFolderId,
				long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelLocalService.
			addCommerceBOMFolderApplicationRel(
				userId, commerceBOMFolderId, commerceApplicationModelId);
	}

	/**
	 * Creates a new commerce bom folder application rel with the primary key. Does not add the commerce bom folder application rel to the database.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key for the new commerce bom folder application rel
	 * @return the new commerce bom folder application rel
	 */
	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
		createCommerceBOMFolderApplicationRel(
			long commerceBOMFolderApplicationRelId) {

		return _commerceBOMFolderApplicationRelLocalService.
			createCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelLocalService.
			createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce bom folder application rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceBOMFolderApplicationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceBOMFolderApplicationRel the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was removed
	 */
	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
		deleteCommerceBOMFolderApplicationRel(
			com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
				commerceBOMFolderApplicationRel) {

		return _commerceBOMFolderApplicationRelLocalService.
			deleteCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRel);
	}

	/**
	 * Deletes the commerce bom folder application rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceBOMFolderApplicationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was removed
	 * @throws PortalException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
			deleteCommerceBOMFolderApplicationRel(
				long commerceBOMFolderApplicationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelLocalService.
			deleteCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRelId);
	}

	@Override
	public void deleteCommerceBOMFolderApplicationRelsByCAMId(
		long commerceApplicationModelId) {

		_commerceBOMFolderApplicationRelLocalService.
			deleteCommerceBOMFolderApplicationRelsByCAMId(
				commerceApplicationModelId);
	}

	@Override
	public void deleteCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
		long commerceBOMFolderId) {

		_commerceBOMFolderApplicationRelLocalService.
			deleteCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				commerceBOMFolderId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceBOMFolderApplicationRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceBOMFolderApplicationRelLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceBOMFolderApplicationRelLocalService.dynamicQuery();
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

		return _commerceBOMFolderApplicationRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.bom.model.impl.CommerceBOMFolderApplicationRelModelImpl</code>.
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

		return _commerceBOMFolderApplicationRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.bom.model.impl.CommerceBOMFolderApplicationRelModelImpl</code>.
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

		return _commerceBOMFolderApplicationRelLocalService.dynamicQuery(
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

		return _commerceBOMFolderApplicationRelLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _commerceBOMFolderApplicationRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
		fetchCommerceBOMFolderApplicationRel(
			long commerceBOMFolderApplicationRelId) {

		return _commerceBOMFolderApplicationRelLocalService.
			fetchCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceBOMFolderApplicationRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce bom folder application rel with the primary key.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel
	 * @throws PortalException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
			getCommerceBOMFolderApplicationRel(
				long commerceBOMFolderApplicationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRelId);
	}

	/**
	 * Returns a range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.bom.model.impl.CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of commerce bom folder application rels
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRels(int start, int end) {

		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelsByCAMId(
				long commerceApplicationModelId, int start, int end) {

		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsByCAMId(
				commerceApplicationModelId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				long commerceBOMFolderId, int start, int end) {

		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				commerceBOMFolderId, start, end);
	}

	/**
	 * Returns the number of commerce bom folder application rels.
	 *
	 * @return the number of commerce bom folder application rels
	 */
	@Override
	public int getCommerceBOMFolderApplicationRelsCount() {
		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsCount();
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCAMId(
		long commerceApplicationModelId) {

		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsCountByCAMId(
				commerceApplicationModelId);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
		long commerceBOMFolderId) {

		return _commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				commerceBOMFolderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceBOMFolderApplicationRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceBOMFolderApplicationRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce bom folder application rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceBOMFolderApplicationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceBOMFolderApplicationRel the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was updated
	 */
	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
		updateCommerceBOMFolderApplicationRel(
			com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
				commerceBOMFolderApplicationRel) {

		return _commerceBOMFolderApplicationRelLocalService.
			updateCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRel);
	}

	@Override
	public CommerceBOMFolderApplicationRelLocalService getWrappedService() {
		return _commerceBOMFolderApplicationRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceBOMFolderApplicationRelLocalService
			commerceBOMFolderApplicationRelLocalService) {

		_commerceBOMFolderApplicationRelLocalService =
			commerceBOMFolderApplicationRelLocalService;
	}

	private CommerceBOMFolderApplicationRelLocalService
		_commerceBOMFolderApplicationRelLocalService;

}