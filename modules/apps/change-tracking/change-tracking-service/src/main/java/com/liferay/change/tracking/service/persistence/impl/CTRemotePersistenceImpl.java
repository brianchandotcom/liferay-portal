/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchRemoteException;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.model.CTRemoteTable;
import com.liferay.change.tracking.model.impl.CTRemoteImpl;
import com.liferay.change.tracking.model.impl.CTRemoteModelImpl;
import com.liferay.change.tracking.service.persistence.CTRemotePersistence;
import com.liferay.change.tracking.service.persistence.CTRemoteUtil;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct remote service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = CTRemotePersistence.class)
public class CTRemotePersistenceImpl
	extends BasePersistenceImpl<CTRemote, NoSuchRemoteException>
	implements CTRemotePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTRemoteUtil</code> to access the ct remote persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTRemoteImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CTRemote>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the ct remotes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct remotes
	 */
	@Override
	public List<CTRemote> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of matching ct remotes
	 */
	@Override
	public List<CTRemote> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct remotes
	 */
	@Override
	public List<CTRemote> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct remotes
	 */
	@Override
	public List<CTRemote> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTRemote> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct remote
	 * @throws NoSuchRemoteException if a matching ct remote could not be found
	 */
	@Override
	public CTRemote findByCompanyId_First(
			long companyId, OrderByComparator<CTRemote> orderByComparator)
		throws NoSuchRemoteException {

		CTRemote ctRemote = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ctRemote != null) {
			return ctRemote;
		}

		throw new NoSuchRemoteException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct remote, or <code>null</code> if a matching ct remote could not be found
	 */
	@Override
	public CTRemote fetchByCompanyId_First(
		long companyId, OrderByComparator<CTRemote> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns all the ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct remotes that the user has permission to view
	 */
	@Override
	public List<CTRemote> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of matching ct remotes that the user has permission to view
	 */
	@Override
	public List<CTRemote> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct remotes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct remotes that the user has permission to view
	 */
	@Override
	public List<CTRemote> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_CTREMOTE_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_CTREMOTE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_CTREMOTE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CTRemoteModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CTRemoteModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CTRemote.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CTRemoteImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CTRemoteImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CTRemote>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the ct remotes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of ct remotes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct remotes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct remotes that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CTRemote> ctRemotes = findByCompanyId(companyId);

			ctRemotes = InlineSQLHelperUtil.filter(ctRemotes);

			return ctRemotes.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_CTREMOTE_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CTRemote.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"ctRemote.companyId = ?";

	public CTRemotePersistenceImpl() {
		setModelClass(CTRemote.class);

		setModelImplClass(CTRemoteImpl.class);
		setModelPKClass(long.class);

		setTable(CTRemoteTable.INSTANCE);
	}

	/**
	 * Creates a new ct remote with the primary key. Does not add the ct remote to the database.
	 *
	 * @param ctRemoteId the primary key for the new ct remote
	 * @return the new ct remote
	 */
	@Override
	public CTRemote create(long ctRemoteId) {
		CTRemote ctRemote = new CTRemoteImpl();

		ctRemote.setNew(true);
		ctRemote.setPrimaryKey(ctRemoteId);

		ctRemote.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctRemote;
	}

	/**
	 * Removes the ct remote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote that was removed
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	@Override
	public CTRemote remove(long ctRemoteId) throws NoSuchRemoteException {
		return remove((Serializable)ctRemoteId);
	}

	@Override
	protected CTRemote removeImpl(CTRemote ctRemote) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctRemote)) {
				ctRemote = (CTRemote)session.get(
					CTRemoteImpl.class, ctRemote.getPrimaryKeyObj());
			}

			if (ctRemote != null) {
				session.delete(ctRemote);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctRemote != null) {
			clearCache(ctRemote);
		}

		return ctRemote;
	}

	@Override
	public CTRemote updateImpl(CTRemote ctRemote) {
		boolean isNew = ctRemote.isNew();

		if (!(ctRemote instanceof CTRemoteModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctRemote.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctRemote);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctRemote proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTRemote implementation " +
					ctRemote.getClass());
		}

		CTRemoteModelImpl ctRemoteModelImpl = (CTRemoteModelImpl)ctRemote;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (ctRemote.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctRemote.setCreateDate(date);
			}
			else {
				ctRemote.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!ctRemoteModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctRemote.setModifiedDate(date);
			}
			else {
				ctRemote.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctRemote);
			}
			else {
				ctRemote = (CTRemote)session.merge(ctRemote);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(ctRemote, false);

		if (isNew) {
			ctRemote.setNew(false);
		}

		ctRemote.resetOriginalValues();

		return ctRemote;
	}

	/**
	 * Returns the ct remote with the primary key or throws a <code>NoSuchRemoteException</code> if it could not be found.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	@Override
	public CTRemote findByPrimaryKey(long ctRemoteId)
		throws NoSuchRemoteException {

		return findByPrimaryKey((Serializable)ctRemoteId);
	}

	/**
	 * Returns the ct remote with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote, or <code>null</code> if a ct remote with the primary key could not be found
	 */
	@Override
	public CTRemote fetchByPrimaryKey(long ctRemoteId) {
		return fetchByPrimaryKey((Serializable)ctRemoteId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctRemoteId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTREMOTE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTRemoteModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct remote persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCompanyId,
				_finderPathWithoutPaginationFindByCompanyId,
				_finderPathCountByCompanyId, _SQL_SELECT_CTREMOTE_WHERE,
				_SQL_COUNT_CTREMOTE_WHERE, CTRemoteModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"ctRemote.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, CTRemote::getCompanyId));

		CTRemoteUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CTRemoteUtil.setPersistence(null);

		entityCache.removeCache(CTRemoteImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _ENTITY_ALIAS_PREFIX =
		CTRemoteModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_CTREMOTE =
		"SELECT ctRemote FROM CTRemote ctRemote";

	private static final String _SQL_SELECT_CTREMOTE_WHERE =
		"SELECT ctRemote FROM CTRemote ctRemote WHERE ";

	private static final String _SQL_COUNT_CTREMOTE_WHERE =
		"SELECT COUNT(ctRemote) FROM CTRemote ctRemote WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"ctRemote.ctRemoteId";

	private static final String _FILTER_SQL_SELECT_CTREMOTE_WHERE =
		"SELECT DISTINCT {ctRemote.*} FROM CTRemote ctRemote WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CTREMOTE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CTRemote.*} FROM (SELECT DISTINCT ctRemote.ctRemoteId FROM CTRemote ctRemote WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CTREMOTE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CTRemote ON TEMP_TABLE.ctRemoteId = CTRemote.ctRemoteId";

	private static final String _FILTER_SQL_COUNT_CTREMOTE_WHERE =
		"SELECT COUNT(DISTINCT ctRemote.ctRemoteId) AS COUNT_VALUE FROM CTRemote ctRemote WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "ctRemote";

	private static final String _FILTER_ENTITY_TABLE = "CTRemote";

	private static final String _ORDER_BY_ENTITY_TABLE = "CTRemote.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTRemote exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTRemotePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1259690521