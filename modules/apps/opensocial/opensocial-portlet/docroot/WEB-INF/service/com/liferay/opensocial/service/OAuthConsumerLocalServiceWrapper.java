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

package com.liferay.opensocial.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthConsumerLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthConsumerLocalService
 * @generated
 */
public class OAuthConsumerLocalServiceWrapper
	implements OAuthConsumerLocalService,
			   ServiceWrapper<OAuthConsumerLocalService> {

	public OAuthConsumerLocalServiceWrapper(
		OAuthConsumerLocalService oAuthConsumerLocalService) {

		_oAuthConsumerLocalService = oAuthConsumerLocalService;
	}

	@Override
	public com.liferay.opensocial.model.OAuthConsumer addOAuthConsumer(
		long companyId, String gadgetKey, String serviceName,
		String consumerKey, String consumerSecret, String keyType) {

		return _oAuthConsumerLocalService.addOAuthConsumer(
			companyId, gadgetKey, serviceName, consumerKey, consumerSecret,
			keyType);
	}

	/**
	 * Adds the o auth consumer to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was added
	 */
	@Override
	public com.liferay.opensocial.model.OAuthConsumer addOAuthConsumer(
		com.liferay.opensocial.model.OAuthConsumer oAuthConsumer) {

		return _oAuthConsumerLocalService.addOAuthConsumer(oAuthConsumer);
	}

	/**
	 * Creates a new o auth consumer with the primary key. Does not add the o auth consumer to the database.
	 *
	 * @param oAuthConsumerId the primary key for the new o auth consumer
	 * @return the new o auth consumer
	 */
	@Override
	public com.liferay.opensocial.model.OAuthConsumer createOAuthConsumer(
		long oAuthConsumerId) {

		return _oAuthConsumerLocalService.createOAuthConsumer(oAuthConsumerId);
	}

	/**
	 * Deletes the o auth consumer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer that was removed
	 * @throws PortalException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public com.liferay.opensocial.model.OAuthConsumer deleteOAuthConsumer(
			long oAuthConsumerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthConsumerLocalService.deleteOAuthConsumer(oAuthConsumerId);
	}

	/**
	 * Deletes the o auth consumer from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was removed
	 */
	@Override
	public com.liferay.opensocial.model.OAuthConsumer deleteOAuthConsumer(
		com.liferay.opensocial.model.OAuthConsumer oAuthConsumer) {

		return _oAuthConsumerLocalService.deleteOAuthConsumer(oAuthConsumer);
	}

	@Override
	public void deleteOAuthConsumers(String gadgetKey) {
		_oAuthConsumerLocalService.deleteOAuthConsumers(gadgetKey);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthConsumerLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuthConsumerLocalService.dynamicQuery();
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

		return _oAuthConsumerLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
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

		return _oAuthConsumerLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
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

		return _oAuthConsumerLocalService.dynamicQuery(
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

		return _oAuthConsumerLocalService.dynamicQueryCount(dynamicQuery);
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

		return _oAuthConsumerLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.opensocial.model.OAuthConsumer fetchOAuthConsumer(
		long oAuthConsumerId) {

		return _oAuthConsumerLocalService.fetchOAuthConsumer(oAuthConsumerId);
	}

	@Override
	public com.liferay.opensocial.model.OAuthConsumer fetchOAuthConsumer(
		String gadgetKey, String serviceName) {

		return _oAuthConsumerLocalService.fetchOAuthConsumer(
			gadgetKey, serviceName);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _oAuthConsumerLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _oAuthConsumerLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the o auth consumer with the primary key.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer
	 * @throws PortalException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public com.liferay.opensocial.model.OAuthConsumer getOAuthConsumer(
			long oAuthConsumerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthConsumerLocalService.getOAuthConsumer(oAuthConsumerId);
	}

	@Override
	public com.liferay.opensocial.model.OAuthConsumer getOAuthConsumer(
			String gadgetKey, String serviceName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthConsumerLocalService.getOAuthConsumer(
			gadgetKey, serviceName);
	}

	/**
	 * Returns a range of all the o auth consumers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @return the range of o auth consumers
	 */
	@Override
	public java.util.List<com.liferay.opensocial.model.OAuthConsumer>
		getOAuthConsumers(int start, int end) {

		return _oAuthConsumerLocalService.getOAuthConsumers(start, end);
	}

	@Override
	public java.util.List<com.liferay.opensocial.model.OAuthConsumer>
		getOAuthConsumers(String gadgetKey) {

		return _oAuthConsumerLocalService.getOAuthConsumers(gadgetKey);
	}

	@Override
	public java.util.List<com.liferay.opensocial.model.OAuthConsumer>
		getOAuthConsumers(String gadgetKey, int start, int end) {

		return _oAuthConsumerLocalService.getOAuthConsumers(
			gadgetKey, start, end);
	}

	/**
	 * Returns the number of o auth consumers.
	 *
	 * @return the number of o auth consumers
	 */
	@Override
	public int getOAuthConsumersCount() {
		return _oAuthConsumerLocalService.getOAuthConsumersCount();
	}

	@Override
	public int getOAuthConsumersCount(String gadgetKey) {
		return _oAuthConsumerLocalService.getOAuthConsumersCount(gadgetKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthConsumerLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthConsumerLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.opensocial.model.OAuthConsumer updateOAuthConsumer(
			long oAuthConsumerId, String consumerKey, String consumerSecret,
			String keyType, String keyName, String callbackURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthConsumerLocalService.updateOAuthConsumer(
			oAuthConsumerId, consumerKey, consumerSecret, keyType, keyName,
			callbackURL);
	}

	/**
	 * Updates the o auth consumer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was updated
	 */
	@Override
	public com.liferay.opensocial.model.OAuthConsumer updateOAuthConsumer(
		com.liferay.opensocial.model.OAuthConsumer oAuthConsumer) {

		return _oAuthConsumerLocalService.updateOAuthConsumer(oAuthConsumer);
	}

	@Override
	public OAuthConsumerLocalService getWrappedService() {
		return _oAuthConsumerLocalService;
	}

	@Override
	public void setWrappedService(
		OAuthConsumerLocalService oAuthConsumerLocalService) {

		_oAuthConsumerLocalService = oAuthConsumerLocalService;
	}

	private OAuthConsumerLocalService _oAuthConsumerLocalService;

}