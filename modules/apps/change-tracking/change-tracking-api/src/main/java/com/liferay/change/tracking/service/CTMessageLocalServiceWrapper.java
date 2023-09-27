/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CTMessageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTMessageLocalService
 * @generated
 */
public class CTMessageLocalServiceWrapper
	implements CTMessageLocalService, ServiceWrapper<CTMessageLocalService> {

	public CTMessageLocalServiceWrapper() {
		this(null);
	}

	public CTMessageLocalServiceWrapper(
		CTMessageLocalService ctMessageLocalService) {

		_ctMessageLocalService = ctMessageLocalService;
	}

	/**
	 * Adds the ct message to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTMessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctMessage the ct message
	 * @return the ct message that was added
	 */
	@Override
	public com.liferay.change.tracking.model.CTMessage addCTMessage(
		com.liferay.change.tracking.model.CTMessage ctMessage) {

		return _ctMessageLocalService.addCTMessage(ctMessage);
	}

	@Override
	public com.liferay.change.tracking.model.CTMessage addCTMessage(
		long ctCollectionId,
		com.liferay.portal.kernel.messaging.Message message) {

		return _ctMessageLocalService.addCTMessage(ctCollectionId, message);
	}

	/**
	 * Creates a new ct message with the primary key. Does not add the ct message to the database.
	 *
	 * @param ctMessageId the primary key for the new ct message
	 * @return the new ct message
	 */
	@Override
	public com.liferay.change.tracking.model.CTMessage createCTMessage(
		long ctMessageId) {

		return _ctMessageLocalService.createCTMessage(ctMessageId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctMessageLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the ct message from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTMessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctMessage the ct message
	 * @return the ct message that was removed
	 */
	@Override
	public com.liferay.change.tracking.model.CTMessage deleteCTMessage(
		com.liferay.change.tracking.model.CTMessage ctMessage) {

		return _ctMessageLocalService.deleteCTMessage(ctMessage);
	}

	/**
	 * Deletes the ct message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTMessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message that was removed
	 * @throws PortalException if a ct message with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTMessage deleteCTMessage(
			long ctMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctMessageLocalService.deleteCTMessage(ctMessageId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctMessageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ctMessageLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _ctMessageLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctMessageLocalService.dynamicQuery();
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

		return _ctMessageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTMessageModelImpl</code>.
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

		return _ctMessageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTMessageModelImpl</code>.
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

		return _ctMessageLocalService.dynamicQuery(
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

		return _ctMessageLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ctMessageLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.change.tracking.model.CTMessage fetchCTMessage(
		long ctMessageId) {

		return _ctMessageLocalService.fetchCTMessage(ctMessageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ctMessageLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ct message with the primary key.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message
	 * @throws PortalException if a ct message with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTMessage getCTMessage(
			long ctMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctMessageLocalService.getCTMessage(ctMessageId);
	}

	/**
	 * Returns a range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of ct messages
	 */
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTMessage>
		getCTMessages(int start, int end) {

		return _ctMessageLocalService.getCTMessages(start, end);
	}

	/**
	 * Returns the number of ct messages.
	 *
	 * @return the number of ct messages
	 */
	@Override
	public int getCTMessagesCount() {
		return _ctMessageLocalService.getCTMessagesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ctMessageLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.messaging.Message>
		getMessages(long ctCollectionId) {

		return _ctMessageLocalService.getMessages(ctCollectionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctMessageLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctMessageLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the ct message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTMessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctMessage the ct message
	 * @return the ct message that was updated
	 */
	@Override
	public com.liferay.change.tracking.model.CTMessage updateCTMessage(
		com.liferay.change.tracking.model.CTMessage ctMessage) {

		return _ctMessageLocalService.updateCTMessage(ctMessage);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ctMessageLocalService.getBasePersistence();
	}

	@Override
	public CTMessageLocalService getWrappedService() {
		return _ctMessageLocalService;
	}

	@Override
	public void setWrappedService(CTMessageLocalService ctMessageLocalService) {
		_ctMessageLocalService = ctMessageLocalService;
	}

	private CTMessageLocalService _ctMessageLocalService;

}