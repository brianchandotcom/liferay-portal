/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;

/**
 * Provides a wrapper for {@link KaleoNotificationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationLocalService
 * @generated
 */
public class KaleoNotificationLocalServiceWrapper
	implements KaleoNotificationLocalService,
			   ServiceWrapper<KaleoNotificationLocalService> {

	public KaleoNotificationLocalServiceWrapper() {
		this(null);
	}

	public KaleoNotificationLocalServiceWrapper(
		KaleoNotificationLocalService kaleoNotificationLocalService) {

		_kaleoNotificationLocalService = kaleoNotificationLocalService;
	}

	/**
	 * Adds the kaleo notification to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotification the kaleo notification
	 * @return the kaleo notification that was added
	 */
	@Override
	public KaleoNotification addKaleoNotification(
		KaleoNotification kaleoNotification) {

		return _kaleoNotificationLocalService.addKaleoNotification(
			kaleoNotification);
	}

	@Override
	public KaleoNotification addKaleoNotification(
			String kaleoClassName, long kaleoClassPK, long kaleoDefinitionId,
			long kaleoDefinitionVersionId, String kaleoNodeName,
			com.liferay.portal.workflow.kaleo.definition.Notification
				notification,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationLocalService.addKaleoNotification(
			kaleoClassName, kaleoClassPK, kaleoDefinitionId,
			kaleoDefinitionVersionId, kaleoNodeName, notification,
			serviceContext);
	}

	/**
	 * Creates a new kaleo notification with the primary key. Does not add the kaleo notification to the database.
	 *
	 * @param kaleoNotificationId the primary key for the new kaleo notification
	 * @return the new kaleo notification
	 */
	@Override
	public KaleoNotification createKaleoNotification(long kaleoNotificationId) {
		return _kaleoNotificationLocalService.createKaleoNotification(
			kaleoNotificationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationLocalService.createPersistedModel(
			primaryKeyObj);
	}

	@Override
	public void deleteCompanyKaleoNotifications(long companyId) {
		_kaleoNotificationLocalService.deleteCompanyKaleoNotifications(
			companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoNotifications(
		long kaleoDefinitionVersionId) {

		_kaleoNotificationLocalService.
			deleteKaleoDefinitionVersionKaleoNotifications(
				kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo notification from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotification the kaleo notification
	 * @return the kaleo notification that was removed
	 */
	@Override
	public KaleoNotification deleteKaleoNotification(
		KaleoNotification kaleoNotification) {

		return _kaleoNotificationLocalService.deleteKaleoNotification(
			kaleoNotification);
	}

	/**
	 * Deletes the kaleo notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification that was removed
	 * @throws PortalException if a kaleo notification with the primary key could not be found
	 */
	@Override
	public KaleoNotification deleteKaleoNotification(long kaleoNotificationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationLocalService.deleteKaleoNotification(
			kaleoNotificationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoNotificationLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoNotificationLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoNotificationLocalService.dynamicQuery();
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

		return _kaleoNotificationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl</code>.
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

		return _kaleoNotificationLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl</code>.
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

		return _kaleoNotificationLocalService.dynamicQuery(
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

		return _kaleoNotificationLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoNotificationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoNotification fetchKaleoNotification(long kaleoNotificationId) {
		return _kaleoNotificationLocalService.fetchKaleoNotification(
			kaleoNotificationId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoNotificationLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoNotificationLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo notification with the primary key.
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification
	 * @throws PortalException if a kaleo notification with the primary key could not be found
	 */
	@Override
	public KaleoNotification getKaleoNotification(long kaleoNotificationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationLocalService.getKaleoNotification(
			kaleoNotificationId);
	}

	/**
	 * Returns a range of all the kaleo notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of kaleo notifications
	 */
	@Override
	public java.util.List<KaleoNotification> getKaleoNotifications(
		int start, int end) {

		return _kaleoNotificationLocalService.getKaleoNotifications(start, end);
	}

	@Override
	public java.util.List<KaleoNotification> getKaleoNotifications(
		String kaleoClassName, long kaleoClassPK) {

		return _kaleoNotificationLocalService.getKaleoNotifications(
			kaleoClassName, kaleoClassPK);
	}

	@Override
	public java.util.List<KaleoNotification> getKaleoNotifications(
		String kaleoClassName, long kaleoClassPK, String executionType) {

		return _kaleoNotificationLocalService.getKaleoNotifications(
			kaleoClassName, kaleoClassPK, executionType);
	}

	/**
	 * Returns the number of kaleo notifications.
	 *
	 * @return the number of kaleo notifications
	 */
	@Override
	public int getKaleoNotificationsCount() {
		return _kaleoNotificationLocalService.getKaleoNotificationsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoNotificationLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo notification in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotification the kaleo notification
	 * @return the kaleo notification that was updated
	 */
	@Override
	public KaleoNotification updateKaleoNotification(
		KaleoNotification kaleoNotification) {

		return _kaleoNotificationLocalService.updateKaleoNotification(
			kaleoNotification);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNotificationLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoNotification> getCTPersistence() {
		return _kaleoNotificationLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoNotification> getModelClass() {
		return _kaleoNotificationLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoNotification>, R, E>
				updateUnsafeFunction)
		throws E {

		return _kaleoNotificationLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoNotificationLocalService getWrappedService() {
		return _kaleoNotificationLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoNotificationLocalService kaleoNotificationLocalService) {

		_kaleoNotificationLocalService = kaleoNotificationLocalService;
	}

	private KaleoNotificationLocalService _kaleoNotificationLocalService;

}