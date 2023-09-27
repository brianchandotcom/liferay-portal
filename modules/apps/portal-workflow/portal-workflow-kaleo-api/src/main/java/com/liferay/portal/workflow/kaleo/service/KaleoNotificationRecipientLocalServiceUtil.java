/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for KaleoNotificationRecipient. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNotificationRecipientLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationRecipientLocalService
 * @generated
 */
public class KaleoNotificationRecipientLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNotificationRecipientLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo notification recipient to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was added
	 */
	public static KaleoNotificationRecipient addKaleoNotificationRecipient(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return getService().addKaleoNotificationRecipient(
			kaleoNotificationRecipient);
	}

	public static KaleoNotificationRecipient addKaleoNotificationRecipient(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNotificationId,
			com.liferay.portal.workflow.kaleo.definition.Recipient recipient,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKaleoNotificationRecipient(
			kaleoDefinitionId, kaleoDefinitionVersionId, kaleoNotificationId,
			recipient, serviceContext);
	}

	/**
	 * Creates a new kaleo notification recipient with the primary key. Does not add the kaleo notification recipient to the database.
	 *
	 * @param kaleoNotificationRecipientId the primary key for the new kaleo notification recipient
	 * @return the new kaleo notification recipient
	 */
	public static KaleoNotificationRecipient createKaleoNotificationRecipient(
		long kaleoNotificationRecipientId) {

		return getService().createKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteCompanyKaleoNotificationRecipients(
		long companyId) {

		getService().deleteCompanyKaleoNotificationRecipients(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoNotificationRecipients(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoNotificationRecipients(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo notification recipient from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 */
	public static KaleoNotificationRecipient deleteKaleoNotificationRecipient(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return getService().deleteKaleoNotificationRecipient(
			kaleoNotificationRecipient);
	}

	/**
	 * Deletes the kaleo notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 * @throws PortalException if a kaleo notification recipient with the primary key could not be found
	 */
	public static KaleoNotificationRecipient deleteKaleoNotificationRecipient(
			long kaleoNotificationRecipientId)
		throws PortalException {

		return getService().deleteKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static KaleoNotificationRecipient fetchKaleoNotificationRecipient(
		long kaleoNotificationRecipientId) {

		return getService().fetchKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo notification recipient with the primary key.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient
	 * @throws PortalException if a kaleo notification recipient with the primary key could not be found
	 */
	public static KaleoNotificationRecipient getKaleoNotificationRecipient(
			long kaleoNotificationRecipientId)
		throws PortalException {

		return getService().getKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
	}

	/**
	 * Returns a range of all the kaleo notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of kaleo notification recipients
	 */
	public static List<KaleoNotificationRecipient>
		getKaleoNotificationRecipients(int start, int end) {

		return getService().getKaleoNotificationRecipients(start, end);
	}

	public static List<KaleoNotificationRecipient>
		getKaleoNotificationRecipients(long kaleoNotificationId) {

		return getService().getKaleoNotificationRecipients(kaleoNotificationId);
	}

	/**
	 * Returns the number of kaleo notification recipients.
	 *
	 * @return the number of kaleo notification recipients
	 */
	public static int getKaleoNotificationRecipientsCount() {
		return getService().getKaleoNotificationRecipientsCount();
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo notification recipient in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was updated
	 */
	public static KaleoNotificationRecipient updateKaleoNotificationRecipient(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return getService().updateKaleoNotificationRecipient(
			kaleoNotificationRecipient);
	}

	public static KaleoNotificationRecipientLocalService getService() {
		return _service;
	}

	public static void setService(
		KaleoNotificationRecipientLocalService service) {

		_service = service;
	}

	private static volatile KaleoNotificationRecipientLocalService _service;

}