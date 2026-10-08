/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link AuditEventPseudonymFieldLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymFieldLocalService
 * @generated
 */
public class AuditEventPseudonymFieldLocalServiceWrapper
	implements AuditEventPseudonymFieldLocalService,
			   ServiceWrapper<AuditEventPseudonymFieldLocalService> {

	public AuditEventPseudonymFieldLocalServiceWrapper() {
		this(null);
	}

	public AuditEventPseudonymFieldLocalServiceWrapper(
		AuditEventPseudonymFieldLocalService
			auditEventPseudonymFieldLocalService) {

		_auditEventPseudonymFieldLocalService =
			auditEventPseudonymFieldLocalService;
	}

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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
			addAuditEventPseudonymField(
				com.liferay.portal.security.audit.storage.model.
					AuditEventPseudonymField auditEventPseudonymField) {

		return _auditEventPseudonymFieldLocalService.
			addAuditEventPseudonymField(auditEventPseudonymField);
	}

	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
				addAuditEventPseudonymField(
					long companyId, String contextName, String name,
					String value)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventPseudonymFieldLocalService.
			addAuditEventPseudonymField(companyId, contextName, name, value);
	}

	/**
	 * Creates a new audit event pseudonym field with the primary key. Does not add the audit event pseudonym field to the database.
	 *
	 * @param auditEventPseudonymFieldId the primary key for the new audit event pseudonym field
	 * @return the new audit event pseudonym field
	 */
	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
			createAuditEventPseudonymField(long auditEventPseudonymFieldId) {

		return _auditEventPseudonymFieldLocalService.
			createAuditEventPseudonymField(auditEventPseudonymFieldId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventPseudonymFieldLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
			deleteAuditEventPseudonymField(
				com.liferay.portal.security.audit.storage.model.
					AuditEventPseudonymField auditEventPseudonymField) {

		return _auditEventPseudonymFieldLocalService.
			deleteAuditEventPseudonymField(auditEventPseudonymField);
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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
				deleteAuditEventPseudonymField(long auditEventPseudonymFieldId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventPseudonymFieldLocalService.
			deleteAuditEventPseudonymField(auditEventPseudonymFieldId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventPseudonymFieldLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _auditEventPseudonymFieldLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _auditEventPseudonymFieldLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _auditEventPseudonymFieldLocalService.dynamicQuery();
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

		return _auditEventPseudonymFieldLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _auditEventPseudonymFieldLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _auditEventPseudonymFieldLocalService.dynamicQuery(
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

		return _auditEventPseudonymFieldLocalService.dynamicQueryCount(
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

		return _auditEventPseudonymFieldLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
			fetchAuditEventPseudonymField(long auditEventPseudonymFieldId) {

		return _auditEventPseudonymFieldLocalService.
			fetchAuditEventPseudonymField(auditEventPseudonymFieldId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _auditEventPseudonymFieldLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the audit event pseudonym field with the primary key.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field
	 * @throws PortalException if a audit event pseudonym field with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
				getAuditEventPseudonymField(long auditEventPseudonymFieldId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventPseudonymFieldLocalService.
			getAuditEventPseudonymField(auditEventPseudonymFieldId);
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
	@Override
	public java.util.List
		<com.liferay.portal.security.audit.storage.model.
			AuditEventPseudonymField> getAuditEventPseudonymFields(
				int start, int end) {

		return _auditEventPseudonymFieldLocalService.
			getAuditEventPseudonymFields(start, end);
	}

	/**
	 * Returns the number of audit event pseudonym fields.
	 *
	 * @return the number of audit event pseudonym fields
	 */
	@Override
	public int getAuditEventPseudonymFieldsCount() {
		return _auditEventPseudonymFieldLocalService.
			getAuditEventPseudonymFieldsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _auditEventPseudonymFieldLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _auditEventPseudonymFieldLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventPseudonymFieldLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public
		com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField
			updateAuditEventPseudonymField(
				com.liferay.portal.security.audit.storage.model.
					AuditEventPseudonymField auditEventPseudonymField) {

		return _auditEventPseudonymFieldLocalService.
			updateAuditEventPseudonymField(auditEventPseudonymField);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _auditEventPseudonymFieldLocalService.getBasePersistence();
	}

	@Override
	public AuditEventPseudonymFieldLocalService getWrappedService() {
		return _auditEventPseudonymFieldLocalService;
	}

	@Override
	public void setWrappedService(
		AuditEventPseudonymFieldLocalService
			auditEventPseudonymFieldLocalService) {

		_auditEventPseudonymFieldLocalService =
			auditEventPseudonymFieldLocalService;
	}

	private AuditEventPseudonymFieldLocalService
		_auditEventPseudonymFieldLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1826015033