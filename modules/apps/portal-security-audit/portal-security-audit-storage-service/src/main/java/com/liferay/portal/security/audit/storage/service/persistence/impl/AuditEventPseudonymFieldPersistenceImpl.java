/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service.persistence.impl;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.audit.storage.exception.NoSuchEventPseudonymFieldException;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymFieldTable;
import com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldImpl;
import com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldModelImpl;
import com.liferay.portal.security.audit.storage.service.persistence.AuditEventPseudonymFieldPersistence;
import com.liferay.portal.security.audit.storage.service.persistence.AuditEventPseudonymFieldUtil;
import com.liferay.portal.security.audit.storage.service.persistence.impl.constants.AuditPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the audit event pseudonym field service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AuditEventPseudonymFieldPersistence.class)
public class AuditEventPseudonymFieldPersistenceImpl
	extends BasePersistenceImpl
		<AuditEventPseudonymField, NoSuchEventPseudonymFieldException>
	implements AuditEventPseudonymFieldPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AuditEventPseudonymFieldUtil</code> to access the audit event pseudonym field persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AuditEventPseudonymFieldImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private UniquePersistenceFinder
		<AuditEventPseudonymField, NoSuchEventPseudonymFieldException>
			_uniquePersistenceFinderByC_CN_N_VH;

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
	@Override
	public AuditEventPseudonymField findByC_CN_N_VH(
			long companyId, String contextName, String name, String valueHash)
		throws NoSuchEventPseudonymFieldException {

		return _uniquePersistenceFinderByC_CN_N_VH.find(
			finderCache,
			new Object[] {companyId, contextName, name, valueHash});
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
	@Override
	public AuditEventPseudonymField fetchByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByC_CN_N_VH.fetch(
			finderCache, new Object[] {companyId, contextName, name, valueHash},
			useFinderCache);
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
	@Override
	public AuditEventPseudonymField removeByC_CN_N_VH(
			long companyId, String contextName, String name, String valueHash)
		throws NoSuchEventPseudonymFieldException {

		AuditEventPseudonymField auditEventPseudonymField = findByC_CN_N_VH(
			companyId, contextName, name, valueHash);

		return remove(auditEventPseudonymField);
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
	@Override
	public int countByC_CN_N_VH(
		long companyId, String contextName, String name, String valueHash) {

		return _uniquePersistenceFinderByC_CN_N_VH.count(
			finderCache,
			new Object[] {companyId, contextName, name, valueHash});
	}

	public AuditEventPseudonymFieldPersistenceImpl() {
		setModelClass(AuditEventPseudonymField.class);

		setModelImplClass(AuditEventPseudonymFieldImpl.class);
		setModelPKClass(long.class);

		setTable(AuditEventPseudonymFieldTable.INSTANCE);
	}

	/**
	 * Creates a new audit event pseudonym field with the primary key. Does not add the audit event pseudonym field to the database.
	 *
	 * @param auditEventPseudonymFieldId the primary key for the new audit event pseudonym field
	 * @return the new audit event pseudonym field
	 */
	@Override
	public AuditEventPseudonymField create(long auditEventPseudonymFieldId) {
		AuditEventPseudonymField auditEventPseudonymField =
			new AuditEventPseudonymFieldImpl();

		auditEventPseudonymField.setNew(true);
		auditEventPseudonymField.setPrimaryKey(auditEventPseudonymFieldId);

		auditEventPseudonymField.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return auditEventPseudonymField;
	}

	/**
	 * Removes the audit event pseudonym field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field that was removed
	 * @throws NoSuchEventPseudonymFieldException if a audit event pseudonym field with the primary key could not be found
	 */
	@Override
	public AuditEventPseudonymField remove(long auditEventPseudonymFieldId)
		throws NoSuchEventPseudonymFieldException {

		return remove((Serializable)auditEventPseudonymFieldId);
	}

	@Override
	protected AuditEventPseudonymField removeImpl(
		AuditEventPseudonymField auditEventPseudonymField) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(auditEventPseudonymField)) {
				auditEventPseudonymField =
					(AuditEventPseudonymField)session.get(
						AuditEventPseudonymFieldImpl.class,
						auditEventPseudonymField.getPrimaryKeyObj());
			}

			if (auditEventPseudonymField != null) {
				session.delete(auditEventPseudonymField);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (auditEventPseudonymField != null) {
			clearCache(auditEventPseudonymField);
		}

		return auditEventPseudonymField;
	}

	@Override
	public AuditEventPseudonymField updateImpl(
		AuditEventPseudonymField auditEventPseudonymField) {

		boolean isNew = auditEventPseudonymField.isNew();

		if (!(auditEventPseudonymField instanceof
				AuditEventPseudonymFieldModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(auditEventPseudonymField.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					auditEventPseudonymField);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in auditEventPseudonymField proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AuditEventPseudonymField implementation " +
					auditEventPseudonymField.getClass());
		}

		AuditEventPseudonymFieldModelImpl auditEventPseudonymFieldModelImpl =
			(AuditEventPseudonymFieldModelImpl)auditEventPseudonymField;

		if (isNew && (auditEventPseudonymField.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				auditEventPseudonymField.setCreateDate(date);
			}
			else {
				auditEventPseudonymField.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(auditEventPseudonymField);
			}
			else {
				auditEventPseudonymField =
					(AuditEventPseudonymField)session.merge(
						auditEventPseudonymField);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(auditEventPseudonymField, false);

		if (isNew) {
			auditEventPseudonymField.setNew(false);
		}

		auditEventPseudonymField.resetOriginalValues();

		return auditEventPseudonymField;
	}

	/**
	 * Returns the audit event pseudonym field with the primary key or throws a <code>NoSuchEventPseudonymFieldException</code> if it could not be found.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field
	 * @throws NoSuchEventPseudonymFieldException if a audit event pseudonym field with the primary key could not be found
	 */
	@Override
	public AuditEventPseudonymField findByPrimaryKey(
			long auditEventPseudonymFieldId)
		throws NoSuchEventPseudonymFieldException {

		return findByPrimaryKey((Serializable)auditEventPseudonymFieldId);
	}

	/**
	 * Returns the audit event pseudonym field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param auditEventPseudonymFieldId the primary key of the audit event pseudonym field
	 * @return the audit event pseudonym field, or <code>null</code> if a audit event pseudonym field with the primary key could not be found
	 */
	@Override
	public AuditEventPseudonymField fetchByPrimaryKey(
		long auditEventPseudonymFieldId) {

		return fetchByPrimaryKey((Serializable)auditEventPseudonymFieldId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "auditEventPseudonymFieldId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_AUDITEVENTPSEUDONYMFIELD;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AuditEventPseudonymFieldModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the audit event pseudonym field persistence.
	 */
	@Activate
	public void activate() {
		_uniquePersistenceFinderByC_CN_N_VH = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByC_CN_N_VH",
				new String[] {
					Long.class.getName(), String.class.getName(),
					String.class.getName(), String.class.getName()
				},
				new String[] {"companyId", "contextName", "name", "valueHash"},
				0, 14, false, AuditEventPseudonymField::getCompanyId,
				convertNullFunction(AuditEventPseudonymField::getContextName),
				convertNullFunction(AuditEventPseudonymField::getName),
				convertNullFunction(AuditEventPseudonymField::getValueHash)),
			_SQL_SELECT_AUDITEVENTPSEUDONYMFIELD_WHERE, "",
			new FinderColumn<>(
				"auditEventPseudonymField.", "companyId",
				FinderColumn.Type.LONG, "=", true, true,
				AuditEventPseudonymField::getCompanyId),
			new FinderColumn<>(
				"auditEventPseudonymField.", "contextName",
				FinderColumn.Type.STRING, "=", true, true,
				AuditEventPseudonymField::getContextName),
			new FinderColumn<>(
				"auditEventPseudonymField.", "name", FinderColumn.Type.STRING,
				"=", true, true, AuditEventPseudonymField::getName),
			new FinderColumn<>(
				"auditEventPseudonymField.", "valueHash",
				FinderColumn.Type.STRING, "=", true, true,
				AuditEventPseudonymField::getValueHash));

		AuditEventPseudonymFieldUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AuditEventPseudonymFieldUtil.setPersistence(null);

		entityCache.removeCache(AuditEventPseudonymFieldImpl.class.getName());
	}

	@Override
	@Reference(
		target = AuditPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AuditPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AuditPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_AUDITEVENTPSEUDONYMFIELD =
		"SELECT auditEventPseudonymField FROM AuditEventPseudonymField auditEventPseudonymField";

	private static final String _SQL_SELECT_AUDITEVENTPSEUDONYMFIELD_WHERE =
		"SELECT auditEventPseudonymField FROM AuditEventPseudonymField auditEventPseudonymField WHERE ";

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1479114239