/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.service;

import com.liferay.asset.link.model.AssetLink;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link AssetLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkLocalService
 * @generated
 */
public class AssetLinkLocalServiceWrapper
	implements AssetLinkLocalService, ServiceWrapper<AssetLinkLocalService> {

	public AssetLinkLocalServiceWrapper() {
		this(null);
	}

	public AssetLinkLocalServiceWrapper(
		AssetLinkLocalService assetLinkLocalService) {

		_assetLinkLocalService = assetLinkLocalService;
	}

	/**
	 * Adds the asset link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetLink the asset link
	 * @return the asset link that was added
	 */
	@Override
	public AssetLink addAssetLink(AssetLink assetLink) {
		return _assetLinkLocalService.addAssetLink(assetLink);
	}

	/**
	 * Creates a new asset link with the primary key. Does not add the asset link to the database.
	 *
	 * @param linkId the primary key for the new asset link
	 * @return the new asset link
	 */
	@Override
	public AssetLink createAssetLink(long linkId) {
		return _assetLinkLocalService.createAssetLink(linkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetLinkLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the asset link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetLink the asset link
	 * @return the asset link that was removed
	 */
	@Override
	public AssetLink deleteAssetLink(AssetLink assetLink) {
		return _assetLinkLocalService.deleteAssetLink(assetLink);
	}

	/**
	 * Deletes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link that was removed
	 * @throws PortalException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink deleteAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetLinkLocalService.deleteAssetLink(linkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _assetLinkLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _assetLinkLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetLinkLocalService.dynamicQuery();
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

		return _assetLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.link.model.impl.AssetLinkModelImpl</code>.
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

		return _assetLinkLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.link.model.impl.AssetLinkModelImpl</code>.
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

		return _assetLinkLocalService.dynamicQuery(
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

		return _assetLinkLocalService.dynamicQueryCount(dynamicQuery);
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

		return _assetLinkLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public AssetLink fetchAssetLink(long linkId) {
		return _assetLinkLocalService.fetchAssetLink(linkId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetLinkLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the asset link with the primary key.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link
	 * @throws PortalException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink getAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetLinkLocalService.getAssetLink(linkId);
	}

	/**
	 * Returns a range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.link.model.impl.AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of asset links
	 */
	@Override
	public java.util.List<AssetLink> getAssetLinks(int start, int end) {
		return _assetLinkLocalService.getAssetLinks(start, end);
	}

	/**
	 * Returns the number of asset links.
	 *
	 * @return the number of asset links
	 */
	@Override
	public int getAssetLinksCount() {
		return _assetLinkLocalService.getAssetLinksCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetLinkLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetLinkLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the asset link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetLink the asset link
	 * @return the asset link that was updated
	 */
	@Override
	public AssetLink updateAssetLink(AssetLink assetLink) {
		return _assetLinkLocalService.updateAssetLink(assetLink);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetLinkLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<AssetLink> getCTPersistence() {
		return _assetLinkLocalService.getCTPersistence();
	}

	@Override
	public Class<AssetLink> getModelClass() {
		return _assetLinkLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<AssetLink>, R, E> updateUnsafeFunction)
		throws E {

		return _assetLinkLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public AssetLinkLocalService getWrappedService() {
		return _assetLinkLocalService;
	}

	@Override
	public void setWrappedService(AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	private AssetLinkLocalService _assetLinkLocalService;

}