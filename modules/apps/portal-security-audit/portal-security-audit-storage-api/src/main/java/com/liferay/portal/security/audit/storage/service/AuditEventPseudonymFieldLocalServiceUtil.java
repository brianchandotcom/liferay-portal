/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AuditEventPseudonymField. This utility wraps
 * <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventPseudonymFieldLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymFieldLocalService
 * @generated
 */
public class AuditEventPseudonymFieldLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventPseudonymFieldLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the audit event pseudonym field to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventPseudonymFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventPseudonymField the audit event pseudonym field
	 * @return the audit event pseudonym field that was added
	 */
	public static AuditEventPseudonymField addAuditEventPseudonymField(
		AuditEventPseudonymField auditEventPseudonymField) {

		return getService().addAuditEventPseudonymField(
			auditEventPseudonymField);
	}

	/**
	 * Creates a new audit event pseudonym field with the primary key. Does not add the audit event pseudonym field to the database.
	 *
	 * @param auditEventPseudonymFieldId the primary key for the new audit event pseudonym field
	 * @return the new audit event pseudonym field
	 */
	public static AuditEventPseudonymField createAuditEventPseudonymField(
		long auditEventPseudonymFieldId) {

		return getService().createAuditEventPseudonymField(
			auditEventPseudonymFieldId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the audit event pseudonym field from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventPseudonymFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventPseudonymField the audit event pseudonym field
	 * @return the audit event pseudonym field that was removed
	 */
	public static AuditEventPseudonymField deleteAuditEventPseudonymField(
		AuditEventPseudonymField auditEventPseudonymField) {

		return getService().deleteAuditEventPseudonymField(
			auditEventPseudonymField);
	}

	/**
	 * Deletes the audit event pseudonym field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventPseudonymFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field that was removed
	 * @throws PortalException if a audit event pseudonym field with the primary key could not be found
	 */
	public static AuditEventPseudonymField deleteAuditEventPseudonymField(
			long auditEventPseudonymFieldId)
		throws PortalException {

		return getService().deleteAuditEventPseudonymField(
			auditEventPseudonymFieldId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldModelImpl</code>.
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

	public static AuditEventPseudonymField fetchAuditEventPseudonymField(
		long auditEventPseudonymFieldId) {

		return getService().fetchAuditEventPseudonymField(
			auditEventPseudonymFieldId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the audit event pseudonym field with the primary key.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field
	 * @throws PortalException if a audit event pseudonym field with the primary key could not be found
	 */
	public static AuditEventPseudonymField getAuditEventPseudonymField(
			long auditEventPseudonymFieldId)
		throws PortalException {

		return getService().getAuditEventPseudonymField(
			auditEventPseudonymFieldId);
	}

	/**
	 * Returns a range of all the audit event pseudonym fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit event pseudonym fields
	 * @param end the upper bound of the range of audit event pseudonym fields (not inclusive)
	 * @return the range of audit event pseudonym fields
	 */
	public static List<AuditEventPseudonymField> getAuditEventPseudonymFields(
		int start, int end) {

		return getService().getAuditEventPseudonymFields(start, end);
	}

	/**
	 * Returns the number of audit event pseudonym fields.
	 *
	 * @return the number of audit event pseudonym fields
	 */
	public static int getAuditEventPseudonymFieldsCount() {
		return getService().getAuditEventPseudonymFieldsCount();
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the audit event pseudonym field in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventPseudonymFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventPseudonymField the audit event pseudonym field
	 * @return the audit event pseudonym field that was updated
	 */
	public static AuditEventPseudonymField updateAuditEventPseudonymField(
		AuditEventPseudonymField auditEventPseudonymField) {

		return getService().updateAuditEventPseudonymField(
			auditEventPseudonymField);
	}

	public static AuditEventPseudonymFieldLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<AuditEventPseudonymFieldLocalService>
		_serviceSnapshot = new Snapshot<>(
			AuditEventPseudonymFieldLocalServiceUtil.class,
			AuditEventPseudonymFieldLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:436264485