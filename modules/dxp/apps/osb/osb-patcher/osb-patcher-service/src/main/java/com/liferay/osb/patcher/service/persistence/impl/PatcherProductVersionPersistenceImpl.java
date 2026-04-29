/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service.persistence.impl;

import com.liferay.osb.patcher.exception.NoSuchPatcherProductVersionException;
import com.liferay.osb.patcher.model.PatcherProductVersion;
import com.liferay.osb.patcher.model.PatcherProductVersionTable;
import com.liferay.osb.patcher.model.impl.PatcherProductVersionImpl;
import com.liferay.osb.patcher.model.impl.PatcherProductVersionModelImpl;
import com.liferay.osb.patcher.service.persistence.PatcherProductVersionPersistence;
import com.liferay.osb.patcher.service.persistence.PatcherProductVersionUtil;
import com.liferay.osb.patcher.service.persistence.impl.constants.OSBPatcherPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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
 * The persistence implementation for the patcher product version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = PatcherProductVersionPersistence.class)
public class PatcherProductVersionPersistenceImpl
	extends BasePersistenceImpl
		<PatcherProductVersion, NoSuchPatcherProductVersionException>
	implements PatcherProductVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PatcherProductVersionUtil</code> to access the patcher product version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PatcherProductVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFixDeliveryMethod;
	private FinderPath _finderPathWithoutPaginationFindByFixDeliveryMethod;
	private FinderPath _finderPathCountByFixDeliveryMethod;
	private CollectionPersistenceFinder<PatcherProductVersion>
		_collectionPersistenceFinderByFixDeliveryMethod;

	/**
	 * Returns all the patcher product versions where fixDeliveryMethod = &#63;.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @return the matching patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findByFixDeliveryMethod(
		int fixDeliveryMethod) {

		return findByFixDeliveryMethod(
			fixDeliveryMethod, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher product versions where fixDeliveryMethod = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @return the range of matching patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findByFixDeliveryMethod(
		int fixDeliveryMethod, int start, int end) {

		return findByFixDeliveryMethod(fixDeliveryMethod, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher product versions where fixDeliveryMethod = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findByFixDeliveryMethod(
		int fixDeliveryMethod, int start, int end,
		OrderByComparator<PatcherProductVersion> orderByComparator) {

		return findByFixDeliveryMethod(
			fixDeliveryMethod, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher product versions where fixDeliveryMethod = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findByFixDeliveryMethod(
		int fixDeliveryMethod, int start, int end,
		OrderByComparator<PatcherProductVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByFixDeliveryMethod.find(
			finderCache, new Object[] {fixDeliveryMethod}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher product version in the ordered set where fixDeliveryMethod = &#63;.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher product version
	 * @throws NoSuchPatcherProductVersionException if a matching patcher product version could not be found
	 */
	@Override
	public PatcherProductVersion findByFixDeliveryMethod_First(
			int fixDeliveryMethod,
			OrderByComparator<PatcherProductVersion> orderByComparator)
		throws NoSuchPatcherProductVersionException {

		PatcherProductVersion patcherProductVersion =
			fetchByFixDeliveryMethod_First(
				fixDeliveryMethod, orderByComparator);

		if (patcherProductVersion != null) {
			return patcherProductVersion;
		}

		throw new NoSuchPatcherProductVersionException(
			_collectionPersistenceFinderByFixDeliveryMethod.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {fixDeliveryMethod}));
	}

	/**
	 * Returns the first patcher product version in the ordered set where fixDeliveryMethod = &#63;.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher product version, or <code>null</code> if a matching patcher product version could not be found
	 */
	@Override
	public PatcherProductVersion fetchByFixDeliveryMethod_First(
		int fixDeliveryMethod,
		OrderByComparator<PatcherProductVersion> orderByComparator) {

		return _collectionPersistenceFinderByFixDeliveryMethod.fetchFirst(
			finderCache, new Object[] {fixDeliveryMethod}, orderByComparator);
	}

	/**
	 * Returns all the patcher product versions that the user has permission to view where fixDeliveryMethod = &#63;.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @return the matching patcher product versions that the user has permission to view
	 */
	@Override
	public List<PatcherProductVersion> filterFindByFixDeliveryMethod(
		int fixDeliveryMethod) {

		return filterFindByFixDeliveryMethod(
			fixDeliveryMethod, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher product versions that the user has permission to view where fixDeliveryMethod = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @return the range of matching patcher product versions that the user has permission to view
	 */
	@Override
	public List<PatcherProductVersion> filterFindByFixDeliveryMethod(
		int fixDeliveryMethod, int start, int end) {

		return filterFindByFixDeliveryMethod(
			fixDeliveryMethod, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher product versions that the user has permissions to view where fixDeliveryMethod = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher product versions that the user has permission to view
	 */
	@Override
	public List<PatcherProductVersion> filterFindByFixDeliveryMethod(
		int fixDeliveryMethod, int start, int end,
		OrderByComparator<PatcherProductVersion> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByFixDeliveryMethod(
				fixDeliveryMethod, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByFixDeliveryMethod(
					fixDeliveryMethod, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_PATCHERPRODUCTVERSION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERPRODUCTVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_FIXDELIVERYMETHOD_FIXDELIVERYMETHOD_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERPRODUCTVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					PatcherProductVersionModelImpl.
						ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherProductVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherProductVersion.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherProductVersionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherProductVersionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(fixDeliveryMethod);

			return (List<PatcherProductVersion>)QueryUtil.list(
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
	 * Removes all the patcher product versions where fixDeliveryMethod = &#63; from the database.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 */
	@Override
	public void removeByFixDeliveryMethod(int fixDeliveryMethod) {
		_collectionPersistenceFinderByFixDeliveryMethod.remove(
			finderCache, new Object[] {fixDeliveryMethod});
	}

	/**
	 * Returns the number of patcher product versions where fixDeliveryMethod = &#63;.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @return the number of matching patcher product versions
	 */
	@Override
	public int countByFixDeliveryMethod(int fixDeliveryMethod) {
		return _collectionPersistenceFinderByFixDeliveryMethod.count(
			finderCache, new Object[] {fixDeliveryMethod});
	}

	/**
	 * Returns the number of patcher product versions that the user has permission to view where fixDeliveryMethod = &#63;.
	 *
	 * @param fixDeliveryMethod the fix delivery method
	 * @return the number of matching patcher product versions that the user has permission to view
	 */
	@Override
	public int filterCountByFixDeliveryMethod(int fixDeliveryMethod) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByFixDeliveryMethod(fixDeliveryMethod);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherProductVersion> patcherProductVersions =
				findByFixDeliveryMethod(fixDeliveryMethod);

			patcherProductVersions = InlineSQLHelperUtil.filter(
				patcherProductVersions);

			return patcherProductVersions.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_PATCHERPRODUCTVERSION_WHERE);

		sb.append(_FINDER_COLUMN_FIXDELIVERYMETHOD_FIXDELIVERYMETHOD_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherProductVersion.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(fixDeliveryMethod);

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

	private static final String
		_FINDER_COLUMN_FIXDELIVERYMETHOD_FIXDELIVERYMETHOD_2 =
			"patcherProductVersion.fixDeliveryMethod = ?";

	private FinderPath _finderPathFetchByName;
	private UniquePersistenceFinder<PatcherProductVersion>
		_uniquePersistenceFinderByName;

	/**
	 * Returns the patcher product version where name = &#63; or throws a <code>NoSuchPatcherProductVersionException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching patcher product version
	 * @throws NoSuchPatcherProductVersionException if a matching patcher product version could not be found
	 */
	@Override
	public PatcherProductVersion findByName(String name)
		throws NoSuchPatcherProductVersionException {

		PatcherProductVersion patcherProductVersion = fetchByName(name);

		if (patcherProductVersion == null) {
			String message =
				_uniquePersistenceFinderByName.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPatcherProductVersionException(message);
		}

		return patcherProductVersion;
	}

	/**
	 * Returns the patcher product version where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching patcher product version, or <code>null</code> if a matching patcher product version could not be found
	 */
	@Override
	public PatcherProductVersion fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the patcher product version where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching patcher product version, or <code>null</code> if a matching patcher product version could not be found
	 */
	@Override
	public PatcherProductVersion fetchByName(
		String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByName.fetch(
			finderCache, new Object[] {name}, useFinderCache);
	}

	/**
	 * Removes the patcher product version where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the patcher product version that was removed
	 */
	@Override
	public PatcherProductVersion removeByName(String name)
		throws NoSuchPatcherProductVersionException {

		PatcherProductVersion patcherProductVersion = findByName(name);

		return remove(patcherProductVersion);
	}

	/**
	 * Returns the number of patcher product versions where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching patcher product versions
	 */
	@Override
	public int countByName(String name) {
		return _uniquePersistenceFinderByName.count(
			finderCache, new Object[] {name});
	}

	public PatcherProductVersionPersistenceImpl() {
		setModelClass(PatcherProductVersion.class);

		setModelImplClass(PatcherProductVersionImpl.class);
		setModelPKClass(long.class);

		setTable(PatcherProductVersionTable.INSTANCE);
	}

	/**
	 * Caches the patcher product version in the entity cache if it is enabled.
	 *
	 * @param patcherProductVersion the patcher product version
	 */
	@Override
	public void cacheResult(PatcherProductVersion patcherProductVersion) {
		entityCache.putResult(
			PatcherProductVersionImpl.class,
			patcherProductVersion.getPrimaryKey(), patcherProductVersion);

		finderCache.putResult(
			_finderPathFetchByName,
			new Object[] {patcherProductVersion.getName()},
			patcherProductVersion);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the patcher product versions in the entity cache if it is enabled.
	 *
	 * @param patcherProductVersions the patcher product versions
	 */
	@Override
	public void cacheResult(
		List<PatcherProductVersion> patcherProductVersions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (patcherProductVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PatcherProductVersion patcherProductVersion :
				patcherProductVersions) {

			if (entityCache.getResult(
					PatcherProductVersionImpl.class,
					patcherProductVersion.getPrimaryKey()) == null) {

				cacheResult(patcherProductVersion);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		PatcherProductVersionModelImpl patcherProductVersionModelImpl) {

		Object[] args = new Object[] {patcherProductVersionModelImpl.getName()};

		finderCache.putResult(
			_finderPathFetchByName, args, patcherProductVersionModelImpl);
	}

	/**
	 * Creates a new patcher product version with the primary key. Does not add the patcher product version to the database.
	 *
	 * @param patcherProductVersionId the primary key for the new patcher product version
	 * @return the new patcher product version
	 */
	@Override
	public PatcherProductVersion create(long patcherProductVersionId) {
		PatcherProductVersion patcherProductVersion =
			new PatcherProductVersionImpl();

		patcherProductVersion.setNew(true);
		patcherProductVersion.setPrimaryKey(patcherProductVersionId);

		patcherProductVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return patcherProductVersion;
	}

	/**
	 * Removes the patcher product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param patcherProductVersionId the primary key of the patcher product version
	 * @return the patcher product version that was removed
	 * @throws NoSuchPatcherProductVersionException if a patcher product version with the primary key could not be found
	 */
	@Override
	public PatcherProductVersion remove(long patcherProductVersionId)
		throws NoSuchPatcherProductVersionException {

		return remove((Serializable)patcherProductVersionId);
	}

	@Override
	protected PatcherProductVersion removeImpl(
		PatcherProductVersion patcherProductVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(patcherProductVersion)) {
				patcherProductVersion = (PatcherProductVersion)session.get(
					PatcherProductVersionImpl.class,
					patcherProductVersion.getPrimaryKeyObj());
			}

			if (patcherProductVersion != null) {
				session.delete(patcherProductVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (patcherProductVersion != null) {
			clearCache(patcherProductVersion);
		}

		return patcherProductVersion;
	}

	@Override
	public PatcherProductVersion updateImpl(
		PatcherProductVersion patcherProductVersion) {

		boolean isNew = patcherProductVersion.isNew();

		if (!(patcherProductVersion instanceof
				PatcherProductVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(patcherProductVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					patcherProductVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in patcherProductVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PatcherProductVersion implementation " +
					patcherProductVersion.getClass());
		}

		PatcherProductVersionModelImpl patcherProductVersionModelImpl =
			(PatcherProductVersionModelImpl)patcherProductVersion;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (patcherProductVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				patcherProductVersion.setCreateDate(date);
			}
			else {
				patcherProductVersion.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!patcherProductVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				patcherProductVersion.setModifiedDate(date);
			}
			else {
				patcherProductVersion.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(patcherProductVersion);
			}
			else {
				patcherProductVersion = (PatcherProductVersion)session.merge(
					patcherProductVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PatcherProductVersionImpl.class, patcherProductVersionModelImpl,
			false, true);

		cacheUniqueFindersCache(patcherProductVersionModelImpl);

		if (isNew) {
			patcherProductVersion.setNew(false);
		}

		patcherProductVersion.resetOriginalValues();

		return patcherProductVersion;
	}

	/**
	 * Returns the patcher product version with the primary key or throws a <code>NoSuchPatcherProductVersionException</code> if it could not be found.
	 *
	 * @param patcherProductVersionId the primary key of the patcher product version
	 * @return the patcher product version
	 * @throws NoSuchPatcherProductVersionException if a patcher product version with the primary key could not be found
	 */
	@Override
	public PatcherProductVersion findByPrimaryKey(long patcherProductVersionId)
		throws NoSuchPatcherProductVersionException {

		return findByPrimaryKey((Serializable)patcherProductVersionId);
	}

	/**
	 * Returns the patcher product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param patcherProductVersionId the primary key of the patcher product version
	 * @return the patcher product version, or <code>null</code> if a patcher product version with the primary key could not be found
	 */
	@Override
	public PatcherProductVersion fetchByPrimaryKey(
		long patcherProductVersionId) {

		return fetchByPrimaryKey((Serializable)patcherProductVersionId);
	}

	/**
	 * Returns all the patcher product versions.
	 *
	 * @return the patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @return the range of patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findAll(
		int start, int end,
		OrderByComparator<PatcherProductVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of patcher product versions
	 * @param end the upper bound of the range of patcher product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of patcher product versions
	 */
	@Override
	public List<PatcherProductVersion> findAll(
		int start, int end,
		OrderByComparator<PatcherProductVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<PatcherProductVersion> list = null;

		if (useFinderCache) {
			list = (List<PatcherProductVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PATCHERPRODUCTVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PATCHERPRODUCTVERSION;

				sql = sql.concat(PatcherProductVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PatcherProductVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the patcher product versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PatcherProductVersion patcherProductVersion : findAll()) {
			remove(patcherProductVersion);
		}
	}

	/**
	 * Returns the number of patcher product versions.
	 *
	 * @return the number of patcher product versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_PATCHERPRODUCTVERSION);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "patcherProductVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PATCHERPRODUCTVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PatcherProductVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the patcher product version persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByFixDeliveryMethod = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFixDeliveryMethod",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fixDeliveryMethod"}, true);

		_finderPathWithoutPaginationFindByFixDeliveryMethod = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFixDeliveryMethod", new String[] {Integer.class.getName()},
			new String[] {"fixDeliveryMethod"}, true);

		_finderPathCountByFixDeliveryMethod = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFixDeliveryMethod", new String[] {Integer.class.getName()},
			new String[] {"fixDeliveryMethod"}, false);

		_collectionPersistenceFinderByFixDeliveryMethod =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFixDeliveryMethod,
				_finderPathWithoutPaginationFindByFixDeliveryMethod,
				_finderPathCountByFixDeliveryMethod,
				_SQL_SELECT_PATCHERPRODUCTVERSION_WHERE,
				_SQL_COUNT_PATCHERPRODUCTVERSION_WHERE,
				PatcherProductVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"patcherProductVersion.", "fixDeliveryMethod",
					FinderColumn.Type.INTEGER, "=", true, true,
					PatcherProductVersion::getFixDeliveryMethod));

		_finderPathFetchByName = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_uniquePersistenceFinderByName = new UniquePersistenceFinder<>(
			this, _finderPathFetchByName,
			_SQL_SELECT_PATCHERPRODUCTVERSION_WHERE,
			new FinderColumn<>(
				"patcherProductVersion.", "name", FinderColumn.Type.STRING, "=",
				true, true, PatcherProductVersion::getName));

		PatcherProductVersionUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		PatcherProductVersionUtil.setPersistence(null);

		entityCache.removeCache(PatcherProductVersionImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBPatcherPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBPatcherPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBPatcherPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_PATCHERPRODUCTVERSION =
		"SELECT patcherProductVersion FROM PatcherProductVersion patcherProductVersion";

	private static final String _SQL_SELECT_PATCHERPRODUCTVERSION_WHERE =
		"SELECT patcherProductVersion FROM PatcherProductVersion patcherProductVersion WHERE ";

	private static final String _SQL_COUNT_PATCHERPRODUCTVERSION =
		"SELECT COUNT(patcherProductVersion) FROM PatcherProductVersion patcherProductVersion";

	private static final String _SQL_COUNT_PATCHERPRODUCTVERSION_WHERE =
		"SELECT COUNT(patcherProductVersion) FROM PatcherProductVersion patcherProductVersion WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"patcherProductVersion.patcherProductVersionId";

	private static final String _FILTER_SQL_SELECT_PATCHERPRODUCTVERSION_WHERE =
		"SELECT DISTINCT {patcherProductVersion.*} FROM OSBPatcher_PProductVersion patcherProductVersion WHERE ";

	private static final String
		_FILTER_SQL_SELECT_PATCHERPRODUCTVERSION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OSBPatcher_PProductVersion.*} FROM (SELECT DISTINCT patcherProductVersion.patcherProductVersionId FROM OSBPatcher_PProductVersion patcherProductVersion WHERE ";

	private static final String
		_FILTER_SQL_SELECT_PATCHERPRODUCTVERSION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OSBPatcher_PProductVersion ON TEMP_TABLE.patcherProductVersionId = OSBPatcher_PProductVersion.patcherProductVersionId";

	private static final String _FILTER_SQL_COUNT_PATCHERPRODUCTVERSION_WHERE =
		"SELECT COUNT(DISTINCT patcherProductVersion.patcherProductVersionId) AS COUNT_VALUE FROM OSBPatcher_PProductVersion patcherProductVersion WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "patcherProductVersion";

	private static final String _FILTER_ENTITY_TABLE =
		"OSBPatcher_PProductVersion";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"patcherProductVersion.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"OSBPatcher_PProductVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PatcherProductVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PatcherProductVersionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-331289538