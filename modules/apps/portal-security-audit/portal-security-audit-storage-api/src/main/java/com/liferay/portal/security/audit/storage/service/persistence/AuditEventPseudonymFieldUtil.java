/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the audit event pseudonym field service. This utility wraps <code>com.liferay.portal.security.audit.storage.service.persistence.impl.AuditEventPseudonymFieldPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymFieldPersistence
 * @generated
 */
public class AuditEventPseudonymFieldUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<AuditEventPseudonymField> auditEventPseudonymFields) {

		getPersistence().cacheResult(auditEventPseudonymFields);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(
		AuditEventPseudonymField auditEventPseudonymField) {

		getPersistence().cacheResult(auditEventPseudonymField);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AuditEventPseudonymField auditEventPseudonymField) {

		getPersistence().clearCache(auditEventPseudonymField);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AuditEventPseudonymField>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AuditEventPseudonymField> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AuditEventPseudonymField> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AuditEventPseudonymField> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AuditEventPseudonymField> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AuditEventPseudonymField update(
		AuditEventPseudonymField auditEventPseudonymField) {

		return getPersistence().update(auditEventPseudonymField);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AuditEventPseudonymField update(
		AuditEventPseudonymField auditEventPseudonymField,
		ServiceContext serviceContext) {

		return getPersistence().update(
			auditEventPseudonymField, serviceContext);
	}

	/**
	 * Returns the audit event pseudonym field where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63; or throws a <code>NoSuchEventPseudonymFieldException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the matching audit event pseudonym field
	 * @throws NoSuchEventPseudonymFieldException if a matching audit event pseudonym field could not be found
	 */
	public static AuditEventPseudonymField findByC_CN_N_VH(
			long companyId, String contextName, String name, String valueHash)
		throws com.liferay.portal.security.audit.storage.exception.
			NoSuchEventPseudonymFieldException {

		return getPersistence().findByC_CN_N_VH(
			companyId, contextName, name, valueHash);
	}

	/**
	 * Returns the audit event pseudonym field where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching audit event pseudonym field, or <code>null</code> if a matching audit event pseudonym field could not be found
	 */
	public static AuditEventPseudonymField fetchByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash,
		boolean useFinderCache) {

		return getPersistence().fetchByC_CN_N_VH(
			companyId, contextName, name, valueHash, useFinderCache);
	}

	/**
	 * Removes the audit event pseudonym field where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the audit event pseudonym field that was removed
	 */
	public static AuditEventPseudonymField removeByC_CN_N_VH(
			long companyId, String contextName, String name, String valueHash)
		throws com.liferay.portal.security.audit.storage.exception.
			NoSuchEventPseudonymFieldException {

		return getPersistence().removeByC_CN_N_VH(
			companyId, contextName, name, valueHash);
	}

	/**
	 * Returns the number of audit event pseudonym fields where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63;.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the number of matching audit event pseudonym fields
	 */
	public static int countByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash) {

		return getPersistence().countByC_CN_N_VH(
			companyId, contextName, name, valueHash);
	}

	/**
	 * Creates a new audit event pseudonym field with the primary key. Does not add the audit event pseudonym field to the database.
	 *
	 * @param auditEventPseudonymFieldId the primary key for the new audit event pseudonym field
	 * @return the new audit event pseudonym field
	 */
	public static AuditEventPseudonymField create(
		long auditEventPseudonymFieldId) {

		return getPersistence().create(auditEventPseudonymFieldId);
	}

	/**
	 * Removes the audit event pseudonym field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field that was removed
	 * @throws NoSuchEventPseudonymFieldException if a audit event pseudonym field with the primary key could not be found
	 */
	public static AuditEventPseudonymField remove(
			long auditEventPseudonymFieldId)
		throws com.liferay.portal.security.audit.storage.exception.
			NoSuchEventPseudonymFieldException {

		return getPersistence().remove(auditEventPseudonymFieldId);
	}

	public static AuditEventPseudonymField updateImpl(
		AuditEventPseudonymField auditEventPseudonymField) {

		return getPersistence().updateImpl(auditEventPseudonymField);
	}

	/**
	 * Returns the audit event pseudonym field with the primary key or throws a <code>NoSuchEventPseudonymFieldException</code> if it could not be found.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field
	 * @throws NoSuchEventPseudonymFieldException if a audit event pseudonym field with the primary key could not be found
	 */
	public static AuditEventPseudonymField findByPrimaryKey(
			long auditEventPseudonymFieldId)
		throws com.liferay.portal.security.audit.storage.exception.
			NoSuchEventPseudonymFieldException {

		return getPersistence().findByPrimaryKey(auditEventPseudonymFieldId);
	}

	/**
	 * Returns the audit event pseudonym field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field, or <code>null</code> if a audit event pseudonym field with the primary key could not be found
	 */
	public static AuditEventPseudonymField fetchByPrimaryKey(
		long auditEventPseudonymFieldId) {

		return getPersistence().fetchByPrimaryKey(auditEventPseudonymFieldId);
	}

	/**
	 * Returns the audit event pseudonym field where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the matching audit event pseudonym field, or <code>null</code> if a matching audit event pseudonym field could not be found
	 */
	public static AuditEventPseudonymField fetchByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash) {

		return getPersistence().fetchByC_CN_N_VH(
			companyId, contextName, name, valueHash);
	}

	public static AuditEventPseudonymFieldPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		AuditEventPseudonymFieldPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile AuditEventPseudonymFieldPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1165430251