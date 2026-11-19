/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link AuditEventAccountEntryRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventAccountEntryRelLocalService
 * @generated
 */
public class AuditEventAccountEntryRelLocalServiceWrapper
	implements AuditEventAccountEntryRelLocalService,
			   ServiceWrapper<AuditEventAccountEntryRelLocalService> {

	public AuditEventAccountEntryRelLocalServiceWrapper() {
		this(null);
	}

	public AuditEventAccountEntryRelLocalServiceWrapper(
		AuditEventAccountEntryRelLocalService
			auditEventAccountEntryRelLocalService) {

		_auditEventAccountEntryRelLocalService =
			auditEventAccountEntryRelLocalService;
	}

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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel addAuditEventAccountEntryRel(
				com.liferay.portal.security.audit.storage.model.
					AuditEventAccountEntryRel auditEventAccountEntryRel) {

		return _auditEventAccountEntryRelLocalService.
			addAuditEventAccountEntryRel(auditEventAccountEntryRel);
	}

	/**
	 * Creates a new audit event account entry rel with the primary key. Does not add the audit event account entry rel to the database.
	 *
	 * @param auditEventAccountEntryRelId the primary key for the new audit event account entry rel
	 * @return the new audit event account entry rel
	 */
	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel createAuditEventAccountEntryRel(
				long auditEventAccountEntryRelId) {

		return _auditEventAccountEntryRelLocalService.
			createAuditEventAccountEntryRel(auditEventAccountEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventAccountEntryRelLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel deleteAuditEventAccountEntryRel(
				com.liferay.portal.security.audit.storage.model.
					AuditEventAccountEntryRel auditEventAccountEntryRel) {

		return _auditEventAccountEntryRelLocalService.
			deleteAuditEventAccountEntryRel(auditEventAccountEntryRel);
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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel deleteAuditEventAccountEntryRel(
					long auditEventAccountEntryRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventAccountEntryRelLocalService.
			deleteAuditEventAccountEntryRel(auditEventAccountEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventAccountEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _auditEventAccountEntryRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _auditEventAccountEntryRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _auditEventAccountEntryRelLocalService.dynamicQuery();
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

		return _auditEventAccountEntryRelLocalService.dynamicQuery(
			dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _auditEventAccountEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _auditEventAccountEntryRelLocalService.dynamicQuery(
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

		return _auditEventAccountEntryRelLocalService.dynamicQueryCount(
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

		return _auditEventAccountEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel fetchAuditEventAccountEntryRel(
				long auditEventAccountEntryRelId) {

		return _auditEventAccountEntryRelLocalService.
			fetchAuditEventAccountEntryRel(auditEventAccountEntryRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _auditEventAccountEntryRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the audit event account entry rel with the primary key.
	 *
	 * @param auditEventAccountEntryRelId the primary key of the audit event account entry rel
	 * @return the audit event account entry rel
	 * @throws PortalException if a audit event account entry rel with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel getAuditEventAccountEntryRel(
					long auditEventAccountEntryRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventAccountEntryRelLocalService.
			getAuditEventAccountEntryRel(auditEventAccountEntryRelId);
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
	@Override
	public java.util.List
		<com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel> getAuditEventAccountEntryRels(
				int start, int end) {

		return _auditEventAccountEntryRelLocalService.
			getAuditEventAccountEntryRels(start, end);
	}

	/**
	 * Returns the number of audit event account entry rels.
	 *
	 * @return the number of audit event account entry rels
	 */
	@Override
	public int getAuditEventAccountEntryRelsCount() {
		return _auditEventAccountEntryRelLocalService.
			getAuditEventAccountEntryRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _auditEventAccountEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _auditEventAccountEntryRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventAccountEntryRelLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.
			AuditEventAccountEntryRel updateAuditEventAccountEntryRel(
				com.liferay.portal.security.audit.storage.model.
					AuditEventAccountEntryRel auditEventAccountEntryRel) {

		return _auditEventAccountEntryRelLocalService.
			updateAuditEventAccountEntryRel(auditEventAccountEntryRel);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _auditEventAccountEntryRelLocalService.getBasePersistence();
	}

	@Override
	public AuditEventAccountEntryRelLocalService getWrappedService() {
		return _auditEventAccountEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		AuditEventAccountEntryRelLocalService
			auditEventAccountEntryRelLocalService) {

		_auditEventAccountEntryRelLocalService =
			auditEventAccountEntryRelLocalService;
	}

	private AuditEventAccountEntryRelLocalService
		_auditEventAccountEntryRelLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:65259640