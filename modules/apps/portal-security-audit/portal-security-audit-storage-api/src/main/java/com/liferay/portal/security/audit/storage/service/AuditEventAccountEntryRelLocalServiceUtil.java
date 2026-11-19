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
import com.liferay.portal.security.audit.storage.model.AuditEventAccountEntryRel;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AuditEventAccountEntryRel. This utility wraps
 * <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventAccountEntryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventAccountEntryRelLocalService
 * @generated
 */
public class AuditEventAccountEntryRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventAccountEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the audit event account entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventAccountEntryRel the audit event account entry rel
	 * @return the audit event account entry rel that was added
	 */
	public static AuditEventAccountEntryRel addAuditEventAccountEntryRel(
		AuditEventAccountEntryRel auditEventAccountEntryRel) {

		return getService().addAuditEventAccountEntryRel(
			auditEventAccountEntryRel);
	}

	/**
	 * Creates a new audit event account entry rel with the primary key. Does not add the audit event account entry rel to the database.
	 *
	 * @param auditEventAccountEntryRelId the primary key for the new audit event account entry rel
	 * @return the new audit event account entry rel
	 */
	public static AuditEventAccountEntryRel createAuditEventAccountEntryRel(
		long auditEventAccountEntryRelId) {

		return getService().createAuditEventAccountEntryRel(
			auditEventAccountEntryRelId);
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
	 * Deletes the audit event account entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventAccountEntryRel the audit event account entry rel
	 * @return the audit event account entry rel that was removed
	 */
	public static AuditEventAccountEntryRel deleteAuditEventAccountEntryRel(
		AuditEventAccountEntryRel auditEventAccountEntryRel) {

		return getService().deleteAuditEventAccountEntryRel(
			auditEventAccountEntryRel);
	}

	/**
	 * Deletes the audit event account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventAccountEntryRelId the primary key of the audit event account entry rel
	 * @return the audit event account entry rel that was removed
	 * @throws PortalException if a audit event account entry rel with the primary key could not be found
	 */
	public static AuditEventAccountEntryRel deleteAuditEventAccountEntryRel(
			long auditEventAccountEntryRelId)
		throws PortalException {

		return getService().deleteAuditEventAccountEntryRel(
			auditEventAccountEntryRelId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventAccountEntryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventAccountEntryRelModelImpl</code>.
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

	public static AuditEventAccountEntryRel fetchAuditEventAccountEntryRel(
		long auditEventAccountEntryRelId) {

		return getService().fetchAuditEventAccountEntryRel(
			auditEventAccountEntryRelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the audit event account entry rel with the primary key.
	 *
	 * @param auditEventAccountEntryRelId the primary key of the audit event account entry rel
	 * @return the audit event account entry rel
	 * @throws PortalException if a audit event account entry rel with the primary key could not be found
	 */
	public static AuditEventAccountEntryRel getAuditEventAccountEntryRel(
			long auditEventAccountEntryRelId)
		throws PortalException {

		return getService().getAuditEventAccountEntryRel(
			auditEventAccountEntryRelId);
	}

	/**
	 * Returns a range of all the audit event account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit event account entry rels
	 * @param end the upper bound of the range of audit event account entry rels (not inclusive)
	 * @return the range of audit event account entry rels
	 */
	public static List<AuditEventAccountEntryRel> getAuditEventAccountEntryRels(
		int start, int end) {

		return getService().getAuditEventAccountEntryRels(start, end);
	}

	/**
	 * Returns the number of audit event account entry rels.
	 *
	 * @return the number of audit event account entry rels
	 */
	public static int getAuditEventAccountEntryRelsCount() {
		return getService().getAuditEventAccountEntryRelsCount();
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
	 * Updates the audit event account entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AuditEventAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param auditEventAccountEntryRel the audit event account entry rel
	 * @return the audit event account entry rel that was updated
	 */
	public static AuditEventAccountEntryRel updateAuditEventAccountEntryRel(
		AuditEventAccountEntryRel auditEventAccountEntryRel) {

		return getService().updateAuditEventAccountEntryRel(
			auditEventAccountEntryRel);
	}

	public static AuditEventAccountEntryRelLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<AuditEventAccountEntryRelLocalService>
		_serviceSnapshot = new Snapshot<>(
			AuditEventAccountEntryRelLocalServiceUtil.class,
			AuditEventAccountEntryRelLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-143381771