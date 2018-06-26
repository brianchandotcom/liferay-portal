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

package com.liferay.commerce.discount.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountUsageEntryLocalService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryLocalService
 * @generated
 */
@ProviderType
public class CommerceDiscountUsageEntryLocalServiceWrapper
	implements CommerceDiscountUsageEntryLocalService,
		ServiceWrapper<CommerceDiscountUsageEntryLocalService> {
	public CommerceDiscountUsageEntryLocalServiceWrapper(
		CommerceDiscountUsageEntryLocalService commerceDiscountUsageEntryLocalService) {
		_commerceDiscountUsageEntryLocalService = commerceDiscountUsageEntryLocalService;
	}

	/**
	* Adds the commerce discount usage entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUsageEntry the commerce discount usage entry
	* @return the commerce discount usage entry that was added
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry addCommerceDiscountUsageEntry(
		com.liferay.commerce.discount.model.CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		return _commerceDiscountUsageEntryLocalService.addCommerceDiscountUsageEntry(commerceDiscountUsageEntry);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry addCommerceDiscountUsageEntry(
		long discountUserId, long discountOrganizationId, long commerceOrderId,
		long commerceDiscountId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUsageEntryLocalService.addCommerceDiscountUsageEntry(discountUserId,
			discountOrganizationId, commerceOrderId, commerceDiscountId,
			serviceContext);
	}

	/**
	* Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	*
	* @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	* @return the new commerce discount usage entry
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry createCommerceDiscountUsageEntry(
		long commerceDiscountUsageEntryId) {
		return _commerceDiscountUsageEntryLocalService.createCommerceDiscountUsageEntry(commerceDiscountUsageEntryId);
	}

	/**
	* Deletes the commerce discount usage entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUsageEntry the commerce discount usage entry
	* @return the commerce discount usage entry that was removed
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry deleteCommerceDiscountUsageEntry(
		com.liferay.commerce.discount.model.CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		return _commerceDiscountUsageEntryLocalService.deleteCommerceDiscountUsageEntry(commerceDiscountUsageEntry);
	}

	/**
	* Deletes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry that was removed
	* @throws PortalException if a commerce discount usage entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry deleteCommerceDiscountUsageEntry(
		long commerceDiscountUsageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUsageEntryLocalService.deleteCommerceDiscountUsageEntry(commerceDiscountUsageEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUsageEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceDiscountUsageEntryLocalService.dynamicQuery();
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
		return _commerceDiscountUsageEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountUsageEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountUsageEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _commerceDiscountUsageEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceDiscountUsageEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry fetchCommerceDiscountUsageEntry(
		long commerceDiscountUsageEntryId) {
		return _commerceDiscountUsageEntryLocalService.fetchCommerceDiscountUsageEntry(commerceDiscountUsageEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceDiscountUsageEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce discount usage entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @return the range of commerce discount usage entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountUsageEntry> getCommerceDiscountUsageEntries(
		int start, int end) {
		return _commerceDiscountUsageEntryLocalService.getCommerceDiscountUsageEntries(start,
			end);
	}

	/**
	* Returns the number of commerce discount usage entries.
	*
	* @return the number of commerce discount usage entries
	*/
	@Override
	public int getCommerceDiscountUsageEntriesCount() {
		return _commerceDiscountUsageEntryLocalService.getCommerceDiscountUsageEntriesCount();
	}

	/**
	* Returns the commerce discount usage entry with the primary key.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry
	* @throws PortalException if a commerce discount usage entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry getCommerceDiscountUsageEntry(
		long commerceDiscountUsageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUsageEntryLocalService.getCommerceDiscountUsageEntry(commerceDiscountUsageEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceDiscountUsageEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountUsageEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUsageEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce discount usage entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUsageEntry the commerce discount usage entry
	* @return the commerce discount usage entry that was updated
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUsageEntry updateCommerceDiscountUsageEntry(
		com.liferay.commerce.discount.model.CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		return _commerceDiscountUsageEntryLocalService.updateCommerceDiscountUsageEntry(commerceDiscountUsageEntry);
	}

	@Override
	public CommerceDiscountUsageEntryLocalService getWrappedService() {
		return _commerceDiscountUsageEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountUsageEntryLocalService commerceDiscountUsageEntryLocalService) {
		_commerceDiscountUsageEntryLocalService = commerceDiscountUsageEntryLocalService;
	}

	private CommerceDiscountUsageEntryLocalService _commerceDiscountUsageEntryLocalService;
}