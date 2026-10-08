/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.security.audit.storage.exception.NoSuchEventPseudonymFieldException;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the audit event pseudonym field service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymFieldUtil
 * @generated
 */
@ProviderType
public interface AuditEventPseudonymFieldPersistence
	extends BasePersistence<AuditEventPseudonymField> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AuditEventPseudonymFieldUtil} to access the audit event pseudonym field persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

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
	public AuditEventPseudonymField findByC_CN_N_VH(
			long companyId, String contextName, String name, String valueHash)
		throws NoSuchEventPseudonymFieldException;

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
	public AuditEventPseudonymField fetchByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash,
		boolean useFinderCache);

	/**
	 * Removes the audit event pseudonym field where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the audit event pseudonym field that was removed
	 */
	public AuditEventPseudonymField removeByC_CN_N_VH(
			long companyId, String contextName, String name, String valueHash)
		throws NoSuchEventPseudonymFieldException;

	/**
	 * Returns the number of audit event pseudonym fields where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63;.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the number of matching audit event pseudonym fields
	 */
	public int countByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash);

	/**
	 * Creates a new audit event pseudonym field with the primary key. Does not add the audit event pseudonym field to the database.
	 *
	 * @param auditEventPseudonymFieldId the primary key for the new audit event pseudonym field
	 * @return the new audit event pseudonym field
	 */
	public AuditEventPseudonymField create(long auditEventPseudonymFieldId);

	/**
	 * Removes the audit event pseudonym field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field that was removed
	 * @throws NoSuchEventPseudonymFieldException if a audit event pseudonym field with the primary key could not be found
	 */
	public AuditEventPseudonymField remove(long auditEventPseudonymFieldId)
		throws NoSuchEventPseudonymFieldException;

	public AuditEventPseudonymField updateImpl(
		AuditEventPseudonymField auditEventPseudonymField);

	/**
	 * Returns the audit event pseudonym field with the primary key or throws a <code>NoSuchEventPseudonymFieldException</code> if it could not be found.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field
	 * @throws NoSuchEventPseudonymFieldException if a audit event pseudonym field with the primary key could not be found
	 */
	public AuditEventPseudonymField findByPrimaryKey(
			long auditEventPseudonymFieldId)
		throws NoSuchEventPseudonymFieldException;

	/**
	 * Returns the audit event pseudonym field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field, or <code>null</code> if a audit event pseudonym field with the primary key could not be found
	 */
	public AuditEventPseudonymField fetchByPrimaryKey(
		long auditEventPseudonymFieldId);

	/**
	 * Returns the audit event pseudonym field where companyId = &#63; and contextName = &#63; and name = &#63; and valueHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param contextName the context name
	 * @param name the name
	 * @param valueHash the value hash
	 * @return the matching audit event pseudonym field, or <code>null</code> if a matching audit event pseudonym field could not be found
	 */
	public default AuditEventPseudonymField fetchByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash) {

		return fetchByC_CN_N_VH(companyId, contextName, name, valueHash, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1685585664