/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service.persistence.impl;

import com.liferay.osb.patcher.exception.NoSuchPatcherFixPackException;
import com.liferay.osb.patcher.model.PatcherFix;
import com.liferay.osb.patcher.model.PatcherFixPack;
import com.liferay.osb.patcher.model.PatcherFixPackTable;
import com.liferay.osb.patcher.model.impl.PatcherFixPackImpl;
import com.liferay.osb.patcher.model.impl.PatcherFixPackModelImpl;
import com.liferay.osb.patcher.service.persistence.PatcherFixPackPersistence;
import com.liferay.osb.patcher.service.persistence.PatcherFixPackUtil;
import com.liferay.osb.patcher.service.persistence.impl.constants.OSBPatcherPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the patcher fix pack service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = PatcherFixPackPersistence.class)
public class PatcherFixPackPersistenceImpl
	extends BasePersistenceImpl<PatcherFixPack, NoSuchPatcherFixPackException>
	implements PatcherFixPackPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PatcherFixPackUtil</code> to access the patcher fix pack persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PatcherFixPackImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathFetchByPatcherBuildId;
	private UniquePersistenceFinder<PatcherFixPack>
		_uniquePersistenceFinderByPatcherBuildId;

	/**
	 * Returns the patcher fix pack where patcherBuildId = &#63; or throws a <code>NoSuchPatcherFixPackException</code> if it could not be found.
	 *
	 * @param patcherBuildId the patcher build ID
	 * @return the matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPatcherBuildId(long patcherBuildId)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPatcherBuildId(patcherBuildId);

		if (patcherFixPack == null) {
			String message =
				_uniquePersistenceFinderByPatcherBuildId.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {patcherBuildId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPatcherFixPackException(message);
		}

		return patcherFixPack;
	}

	/**
	 * Returns the patcher fix pack where patcherBuildId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param patcherBuildId the patcher build ID
	 * @return the matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPatcherBuildId(long patcherBuildId) {
		return fetchByPatcherBuildId(patcherBuildId, true);
	}

	/**
	 * Returns the patcher fix pack where patcherBuildId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param patcherBuildId the patcher build ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPatcherBuildId(
		long patcherBuildId, boolean useFinderCache) {

		return _uniquePersistenceFinderByPatcherBuildId.fetch(
			finderCache, new Object[] {patcherBuildId}, useFinderCache);
	}

	/**
	 * Removes the patcher fix pack where patcherBuildId = &#63; from the database.
	 *
	 * @param patcherBuildId the patcher build ID
	 * @return the patcher fix pack that was removed
	 */
	@Override
	public PatcherFixPack removeByPatcherBuildId(long patcherBuildId)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = findByPatcherBuildId(patcherBuildId);

		return remove(patcherFixPack);
	}

	/**
	 * Returns the number of patcher fix packs where patcherBuildId = &#63;.
	 *
	 * @param patcherBuildId the patcher build ID
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPatcherBuildId(long patcherBuildId) {
		return _uniquePersistenceFinderByPatcherBuildId.count(
			finderCache, new Object[] {patcherBuildId});
	}

	private FinderPath _finderPathWithPaginationFindByPatcherFixComponentId;
	private FinderPath _finderPathWithoutPaginationFindByPatcherFixComponentId;
	private FinderPath _finderPathCountByPatcherFixComponentId;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByPatcherFixComponentId;

	/**
	 * Returns all the patcher fix packs where patcherFixComponentId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPatcherFixComponentId(
		long patcherFixComponentId) {

		return findByPatcherFixComponentId(
			patcherFixComponentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where patcherFixComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPatcherFixComponentId(
		long patcherFixComponentId, int start, int end) {

		return findByPatcherFixComponentId(
			patcherFixComponentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPatcherFixComponentId(
		long patcherFixComponentId, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByPatcherFixComponentId(
			patcherFixComponentId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPatcherFixComponentId(
		long patcherFixComponentId, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPatcherFixComponentId.find(
			finderCache, new Object[] {patcherFixComponentId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPatcherFixComponentId_First(
			long patcherFixComponentId,
			OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPatcherFixComponentId_First(
			patcherFixComponentId, orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByPatcherFixComponentId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {patcherFixComponentId}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPatcherFixComponentId_First(
		long patcherFixComponentId,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByPatcherFixComponentId.fetchFirst(
			finderCache, new Object[] {patcherFixComponentId},
			orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPatcherFixComponentId(
		long patcherFixComponentId) {

		return filterFindByPatcherFixComponentId(
			patcherFixComponentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPatcherFixComponentId(
		long patcherFixComponentId, int start, int end) {

		return filterFindByPatcherFixComponentId(
			patcherFixComponentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where patcherFixComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPatcherFixComponentId(
		long patcherFixComponentId, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPatcherFixComponentId(
				patcherFixComponentId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByPatcherFixComponentId(
					patcherFixComponentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PATCHERFIXCOMPONENTID_PATCHERFIXCOMPONENTID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where patcherFixComponentId = &#63; from the database.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 */
	@Override
	public void removeByPatcherFixComponentId(long patcherFixComponentId) {
		_collectionPersistenceFinderByPatcherFixComponentId.remove(
			finderCache, new Object[] {patcherFixComponentId});
	}

	/**
	 * Returns the number of patcher fix packs where patcherFixComponentId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPatcherFixComponentId(long patcherFixComponentId) {
		return _collectionPersistenceFinderByPatcherFixComponentId.count(
			finderCache, new Object[] {patcherFixComponentId});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where patcherFixComponentId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByPatcherFixComponentId(long patcherFixComponentId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPatcherFixComponentId(patcherFixComponentId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByPatcherFixComponentId(
				patcherFixComponentId);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_PATCHERFIXCOMPONENTID_PATCHERFIXCOMPONENTID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

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
		_FINDER_COLUMN_PATCHERFIXCOMPONENTID_PATCHERFIXCOMPONENTID_2 =
			"patcherFixPack.patcherFixComponentId = ?";

	private FinderPath _finderPathWithPaginationFindByVersion;
	private FinderPath _finderPathWithoutPaginationFindByVersion;
	private FinderPath _finderPathCountByVersion;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByVersion;

	/**
	 * Returns all the patcher fix packs where version = &#63;.
	 *
	 * @param version the version
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByVersion(int version) {
		return findByVersion(
			version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByVersion(int version, int start, int end) {
		return findByVersion(version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByVersion(
		int version, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByVersion(version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByVersion(
		int version, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByVersion.find(
			finderCache, new Object[] {version}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where version = &#63;.
	 *
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByVersion_First(
			int version, OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByVersion_First(
			version, orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByVersion.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {version}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where version = &#63;.
	 *
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByVersion_First(
		int version, OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByVersion.fetchFirst(
			finderCache, new Object[] {version}, orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where version = &#63;.
	 *
	 * @param version the version
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByVersion(int version) {
		return filterFindByVersion(
			version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByVersion(
		int version, int start, int end) {

		return filterFindByVersion(version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByVersion(
		int version, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByVersion(version, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByVersion(
					version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_VERSION_VERSION_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(version);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where version = &#63; from the database.
	 *
	 * @param version the version
	 */
	@Override
	public void removeByVersion(int version) {
		_collectionPersistenceFinderByVersion.remove(
			finderCache, new Object[] {version});
	}

	/**
	 * Returns the number of patcher fix packs where version = &#63;.
	 *
	 * @param version the version
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByVersion(int version) {
		return _collectionPersistenceFinderByVersion.count(
			finderCache, new Object[] {version});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where version = &#63;.
	 *
	 * @param version the version
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByVersion(int version) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByVersion(version);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByVersion(version);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_VERSION_VERSION_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(version);

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

	private static final String _FINDER_COLUMN_VERSION_VERSION_2 =
		"patcherFixPack.version = ?";

	private FinderPath _finderPathWithPaginationFindByPFCI_PPVI;
	private FinderPath _finderPathWithoutPaginationFindByPFCI_PPVI;
	private FinderPath _finderPathCountByPFCI_PPVI;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByPFCI_PPVI;

	/**
	 * Returns all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId) {

		return findByPFCI_PPVI(
			patcherFixComponentId, patcherProjectVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId, int start,
		int end) {

		return findByPFCI_PPVI(
			patcherFixComponentId, patcherProjectVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId, int start,
		int end, OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByPFCI_PPVI(
			patcherFixComponentId, patcherProjectVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId, int start,
		int end, OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPFCI_PPVI.find(
			finderCache,
			new Object[] {patcherFixComponentId, patcherProjectVersionId},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_PPVI_First(
			long patcherFixComponentId, long patcherProjectVersionId,
			OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_PPVI_First(
			patcherFixComponentId, patcherProjectVersionId, orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByPFCI_PPVI.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {patcherFixComponentId, patcherProjectVersionId}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_PPVI_First(
		long patcherFixComponentId, long patcherProjectVersionId,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByPFCI_PPVI.fetchFirst(
			finderCache,
			new Object[] {patcherFixComponentId, patcherProjectVersionId},
			orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId) {

		return filterFindByPFCI_PPVI(
			patcherFixComponentId, patcherProjectVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId, int start,
		int end) {

		return filterFindByPFCI_PPVI(
			patcherFixComponentId, patcherProjectVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId, int start,
		int end, OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPFCI_PPVI(
				patcherFixComponentId, patcherProjectVersionId, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByPFCI_PPVI(
					patcherFixComponentId, patcherProjectVersionId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PFCI_PPVI_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_PATCHERPROJECTVERSIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(patcherProjectVersionId);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; from the database.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 */
	@Override
	public void removeByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId) {

		_collectionPersistenceFinderByPFCI_PPVI.remove(
			finderCache,
			new Object[] {patcherFixComponentId, patcherProjectVersionId});
	}

	/**
	 * Returns the number of patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId) {

		return _collectionPersistenceFinderByPFCI_PPVI.count(
			finderCache,
			new Object[] {patcherFixComponentId, patcherProjectVersionId});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByPFCI_PPVI(
		long patcherFixComponentId, long patcherProjectVersionId) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPFCI_PPVI(
				patcherFixComponentId, patcherProjectVersionId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByPFCI_PPVI(
				patcherFixComponentId, patcherProjectVersionId);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_PATCHERPROJECTVERSIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(patcherProjectVersionId);

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
		_FINDER_COLUMN_PFCI_PPVI_PATCHERFIXCOMPONENTID_2 =
			"patcherFixPack.patcherFixComponentId = ? AND ";

	private static final String
		_FINDER_COLUMN_PFCI_PPVI_PATCHERPROJECTVERSIONID_2 =
			"patcherFixPack.patcherProjectVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByPFCI_V;
	private FinderPath _finderPathWithoutPaginationFindByPFCI_V;
	private FinderPath _finderPathCountByPFCI_V;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByPFCI_V;

	/**
	 * Returns all the patcher fix packs where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_V(
		long patcherFixComponentId, int version) {

		return findByPFCI_V(
			patcherFixComponentId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_V(
		long patcherFixComponentId, int version, int start, int end) {

		return findByPFCI_V(patcherFixComponentId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_V(
		long patcherFixComponentId, int version, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByPFCI_V(
			patcherFixComponentId, version, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_V(
		long patcherFixComponentId, int version, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPFCI_V.find(
			finderCache, new Object[] {patcherFixComponentId, version}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_V_First(
			long patcherFixComponentId, int version,
			OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_V_First(
			patcherFixComponentId, version, orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByPFCI_V.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {patcherFixComponentId, version}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_V_First(
		long patcherFixComponentId, int version,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByPFCI_V.fetchFirst(
			finderCache, new Object[] {patcherFixComponentId, version},
			orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_V(
		long patcherFixComponentId, int version) {

		return filterFindByPFCI_V(
			patcherFixComponentId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_V(
		long patcherFixComponentId, int version, int start, int end) {

		return filterFindByPFCI_V(
			patcherFixComponentId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_V(
		long patcherFixComponentId, int version, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPFCI_V(
				patcherFixComponentId, version, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByPFCI_V(
					patcherFixComponentId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PFCI_V_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_V_VERSION_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(version);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where patcherFixComponentId = &#63; and version = &#63; from the database.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 */
	@Override
	public void removeByPFCI_V(long patcherFixComponentId, int version) {
		_collectionPersistenceFinderByPFCI_V.remove(
			finderCache, new Object[] {patcherFixComponentId, version});
	}

	/**
	 * Returns the number of patcher fix packs where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_V(long patcherFixComponentId, int version) {
		return _collectionPersistenceFinderByPFCI_V.count(
			finderCache, new Object[] {patcherFixComponentId, version});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param version the version
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByPFCI_V(long patcherFixComponentId, int version) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPFCI_V(patcherFixComponentId, version);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByPFCI_V(
				patcherFixComponentId, version);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_PFCI_V_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_V_VERSION_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(version);

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

	private static final String _FINDER_COLUMN_PFCI_V_PATCHERFIXCOMPONENTID_2 =
		"patcherFixPack.patcherFixComponentId = ? AND ";

	private static final String _FINDER_COLUMN_PFCI_V_VERSION_2 =
		"patcherFixPack.version = ?";

	private FinderPath _finderPathFetchByPFCI_N;
	private UniquePersistenceFinder<PatcherFixPack>
		_uniquePersistenceFinderByPFCI_N;

	/**
	 * Returns the patcher fix pack where patcherProjectVersionId = &#63; and name = &#63; or throws a <code>NoSuchPatcherFixPackException</code> if it could not be found.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @return the matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_N(
			long patcherProjectVersionId, String name)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_N(
			patcherProjectVersionId, name);

		if (patcherFixPack == null) {
			String message =
				_uniquePersistenceFinderByPFCI_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {patcherProjectVersionId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPatcherFixPackException(message);
		}

		return patcherFixPack;
	}

	/**
	 * Returns the patcher fix pack where patcherProjectVersionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @return the matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_N(
		long patcherProjectVersionId, String name) {

		return fetchByPFCI_N(patcherProjectVersionId, name, true);
	}

	/**
	 * Returns the patcher fix pack where patcherProjectVersionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_N(
		long patcherProjectVersionId, String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByPFCI_N.fetch(
			finderCache, new Object[] {patcherProjectVersionId, name},
			useFinderCache);
	}

	/**
	 * Removes the patcher fix pack where patcherProjectVersionId = &#63; and name = &#63; from the database.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @return the patcher fix pack that was removed
	 */
	@Override
	public PatcherFixPack removeByPFCI_N(
			long patcherProjectVersionId, String name)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = findByPFCI_N(
			patcherProjectVersionId, name);

		return remove(patcherFixPack);
	}

	/**
	 * Returns the number of patcher fix packs where patcherProjectVersionId = &#63; and name = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_N(long patcherProjectVersionId, String name) {
		return _uniquePersistenceFinderByPFCI_N.count(
			finderCache, new Object[] {patcherProjectVersionId, name});
	}

	private FinderPath _finderPathWithPaginationFindByPFCI_S;
	private FinderPath _finderPathWithoutPaginationFindByPFCI_S;
	private FinderPath _finderPathCountByPFCI_S;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByPFCI_S;

	/**
	 * Returns all the patcher fix packs where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_S(
		long patcherProjectVersionId, int status) {

		return findByPFCI_S(
			patcherProjectVersionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_S(
		long patcherProjectVersionId, int status, int start, int end) {

		return findByPFCI_S(patcherProjectVersionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_S(
		long patcherProjectVersionId, int status, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByPFCI_S(
			patcherProjectVersionId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_S(
		long patcherProjectVersionId, int status, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPFCI_S.find(
			finderCache, new Object[] {patcherProjectVersionId, status}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_S_First(
			long patcherProjectVersionId, int status,
			OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_S_First(
			patcherProjectVersionId, status, orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByPFCI_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {patcherProjectVersionId, status}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_S_First(
		long patcherProjectVersionId, int status,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByPFCI_S.fetchFirst(
			finderCache, new Object[] {patcherProjectVersionId, status},
			orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_S(
		long patcherProjectVersionId, int status) {

		return filterFindByPFCI_S(
			patcherProjectVersionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_S(
		long patcherProjectVersionId, int status, int start, int end) {

		return filterFindByPFCI_S(
			patcherProjectVersionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_S(
		long patcherProjectVersionId, int status, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPFCI_S(
				patcherProjectVersionId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByPFCI_S(
					patcherProjectVersionId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PFCI_S_PATCHERPROJECTVERSIONID_2);

		sb.append(_FINDER_COLUMN_PFCI_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherProjectVersionId);

			queryPos.add(status);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where patcherProjectVersionId = &#63; and status = &#63; from the database.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 */
	@Override
	public void removeByPFCI_S(long patcherProjectVersionId, int status) {
		_collectionPersistenceFinderByPFCI_S.remove(
			finderCache, new Object[] {patcherProjectVersionId, status});
	}

	/**
	 * Returns the number of patcher fix packs where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_S(long patcherProjectVersionId, int status) {
		return _collectionPersistenceFinderByPFCI_S.count(
			finderCache, new Object[] {patcherProjectVersionId, status});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where patcherProjectVersionId = &#63; and status = &#63;.
	 *
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param status the status
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByPFCI_S(long patcherProjectVersionId, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPFCI_S(patcherProjectVersionId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByPFCI_S(
				patcherProjectVersionId, status);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_PFCI_S_PATCHERPROJECTVERSIONID_2);

		sb.append(_FINDER_COLUMN_PFCI_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherProjectVersionId);

			queryPos.add(status);

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
		_FINDER_COLUMN_PFCI_S_PATCHERPROJECTVERSIONID_2 =
			"patcherFixPack.patcherProjectVersionId = ? AND ";

	private static final String _FINDER_COLUMN_PFCI_S_STATUS_2 =
		"patcherFixPack.status = ?";

	private FinderPath _finderPathWithPaginationFindByPFCI_PPVI_GtV;
	private FinderPath _finderPathWithPaginationCountByPFCI_PPVI_GtV;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByPFCI_PPVI_GtV;

	/**
	 * Returns all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		return findByPFCI_PPVI_GtV(
			patcherFixComponentId, patcherProjectVersionId, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end) {

		return findByPFCI_PPVI_GtV(
			patcherFixComponentId, patcherProjectVersionId, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByPFCI_PPVI_GtV(
			patcherFixComponentId, patcherProjectVersionId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end, OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPFCI_PPVI_GtV.find(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_PPVI_GtV_First(
			long patcherFixComponentId, long patcherProjectVersionId,
			int version, OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_PPVI_GtV_First(
			patcherFixComponentId, patcherProjectVersionId, version,
			orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByPFCI_PPVI_GtV.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					patcherFixComponentId, patcherProjectVersionId, version
				}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_PPVI_GtV_First(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByPFCI_PPVI_GtV.fetchFirst(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			},
			orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		return filterFindByPFCI_PPVI_GtV(
			patcherFixComponentId, patcherProjectVersionId, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end) {

		return filterFindByPFCI_PPVI_GtV(
			patcherFixComponentId, patcherProjectVersionId, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPFCI_PPVI_GtV(
				patcherFixComponentId, patcherProjectVersionId, version, start,
				end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByPFCI_PPVI_GtV(
					patcherFixComponentId, patcherProjectVersionId, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PFCI_PPVI_GTV_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_GTV_PATCHERPROJECTVERSIONID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_GTV_VERSION_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(patcherProjectVersionId);

			queryPos.add(version);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63; from the database.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 */
	@Override
	public void removeByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		_collectionPersistenceFinderByPFCI_PPVI_GtV.remove(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			});
	}

	/**
	 * Returns the number of patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		return _collectionPersistenceFinderByPFCI_PPVI_GtV.count(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &gt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByPFCI_PPVI_GtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPFCI_PPVI_GtV(
				patcherFixComponentId, patcherProjectVersionId, version);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByPFCI_PPVI_GtV(
				patcherFixComponentId, patcherProjectVersionId, version);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_GTV_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_GTV_PATCHERPROJECTVERSIONID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_GTV_VERSION_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(patcherProjectVersionId);

			queryPos.add(version);

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
		_FINDER_COLUMN_PFCI_PPVI_GTV_PATCHERFIXCOMPONENTID_2 =
			"patcherFixPack.patcherFixComponentId = ? AND ";

	private static final String
		_FINDER_COLUMN_PFCI_PPVI_GTV_PATCHERPROJECTVERSIONID_2 =
			"patcherFixPack.patcherProjectVersionId = ? AND ";

	private static final String _FINDER_COLUMN_PFCI_PPVI_GTV_VERSION_2 =
		"patcherFixPack.version > ?";

	private FinderPath _finderPathWithPaginationFindByPFCI_PPVI_LtV;
	private FinderPath _finderPathWithPaginationCountByPFCI_PPVI_LtV;
	private CollectionPersistenceFinder<PatcherFixPack>
		_collectionPersistenceFinderByPFCI_PPVI_LtV;

	/**
	 * Returns all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		return findByPFCI_PPVI_LtV(
			patcherFixComponentId, patcherProjectVersionId, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end) {

		return findByPFCI_PPVI_LtV(
			patcherFixComponentId, patcherProjectVersionId, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return findByPFCI_PPVI_LtV(
			patcherFixComponentId, patcherProjectVersionId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher fix packs
	 */
	@Override
	public List<PatcherFixPack> findByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end, OrderByComparator<PatcherFixPack> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPFCI_PPVI_LtV.find(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_PPVI_LtV_First(
			long patcherFixComponentId, long patcherProjectVersionId,
			int version, OrderByComparator<PatcherFixPack> orderByComparator)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_PPVI_LtV_First(
			patcherFixComponentId, patcherProjectVersionId, version,
			orderByComparator);

		if (patcherFixPack != null) {
			return patcherFixPack;
		}

		throw new NoSuchPatcherFixPackException(
			_collectionPersistenceFinderByPFCI_PPVI_LtV.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					patcherFixComponentId, patcherProjectVersionId, version
				}));
	}

	/**
	 * Returns the first patcher fix pack in the ordered set where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_PPVI_LtV_First(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return _collectionPersistenceFinderByPFCI_PPVI_LtV.fetchFirst(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			},
			orderByComparator);
	}

	/**
	 * Returns all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		return filterFindByPFCI_PPVI_LtV(
			patcherFixComponentId, patcherProjectVersionId, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @return the range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end) {

		return filterFindByPFCI_PPVI_LtV(
			patcherFixComponentId, patcherProjectVersionId, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the patcher fix packs that the user has permissions to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @param start the lower bound of the range of patcher fix packs
	 * @param end the upper bound of the range of patcher fix packs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public List<PatcherFixPack> filterFindByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version,
		int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPFCI_PPVI_LtV(
				patcherFixComponentId, patcherProjectVersionId, version, start,
				end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByPFCI_PPVI_LtV(
					patcherFixComponentId, patcherProjectVersionId, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PFCI_PPVI_LTV_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_LTV_PATCHERPROJECTVERSIONID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_LTV_VERSION_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(PatcherFixPackModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, PatcherFixPackImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, PatcherFixPackImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(patcherProjectVersionId);

			queryPos.add(version);

			return (List<PatcherFixPack>)QueryUtil.list(
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
	 * Removes all the patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63; from the database.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 */
	@Override
	public void removeByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		_collectionPersistenceFinderByPFCI_PPVI_LtV.remove(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			});
	}

	/**
	 * Returns the number of patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		return _collectionPersistenceFinderByPFCI_PPVI_LtV.count(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, version
			});
	}

	/**
	 * Returns the number of patcher fix packs that the user has permission to view where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and version &lt; &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param version the version
	 * @return the number of matching patcher fix packs that the user has permission to view
	 */
	@Override
	public int filterCountByPFCI_PPVI_LtV(
		long patcherFixComponentId, long patcherProjectVersionId, int version) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPFCI_PPVI_LtV(
				patcherFixComponentId, patcherProjectVersionId, version);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<PatcherFixPack> patcherFixPacks = findByPFCI_PPVI_LtV(
				patcherFixComponentId, patcherProjectVersionId, version);

			patcherFixPacks = InlineSQLHelperUtil.filter(patcherFixPacks);

			return patcherFixPacks.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_LTV_PATCHERFIXCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_LTV_PATCHERPROJECTVERSIONID_2);

		sb.append(_FINDER_COLUMN_PFCI_PPVI_LTV_VERSION_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), PatcherFixPack.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(patcherFixComponentId);

			queryPos.add(patcherProjectVersionId);

			queryPos.add(version);

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
		_FINDER_COLUMN_PFCI_PPVI_LTV_PATCHERFIXCOMPONENTID_2 =
			"patcherFixPack.patcherFixComponentId = ? AND ";

	private static final String
		_FINDER_COLUMN_PFCI_PPVI_LTV_PATCHERPROJECTVERSIONID_2 =
			"patcherFixPack.patcherProjectVersionId = ? AND ";

	private static final String _FINDER_COLUMN_PFCI_PPVI_LTV_VERSION_2 =
		"patcherFixPack.version < ?";

	private FinderPath _finderPathFetchByPFCI_PPVI_N_V;
	private UniquePersistenceFinder<PatcherFixPack>
		_uniquePersistenceFinderByPFCI_PPVI_N_V;

	/**
	 * Returns the patcher fix pack where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and name = &#63; and version = &#63; or throws a <code>NoSuchPatcherFixPackException</code> if it could not be found.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @param version the version
	 * @return the matching patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack findByPFCI_PPVI_N_V(
			long patcherFixComponentId, long patcherProjectVersionId,
			String name, int version)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = fetchByPFCI_PPVI_N_V(
			patcherFixComponentId, patcherProjectVersionId, name, version);

		if (patcherFixPack == null) {
			String message =
				_uniquePersistenceFinderByPFCI_PPVI_N_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						patcherFixComponentId, patcherProjectVersionId, name,
						version
					});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPatcherFixPackException(message);
		}

		return patcherFixPack;
	}

	/**
	 * Returns the patcher fix pack where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @param version the version
	 * @return the matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_PPVI_N_V(
		long patcherFixComponentId, long patcherProjectVersionId, String name,
		int version) {

		return fetchByPFCI_PPVI_N_V(
			patcherFixComponentId, patcherProjectVersionId, name, version,
			true);
	}

	/**
	 * Returns the patcher fix pack where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching patcher fix pack, or <code>null</code> if a matching patcher fix pack could not be found
	 */
	@Override
	public PatcherFixPack fetchByPFCI_PPVI_N_V(
		long patcherFixComponentId, long patcherProjectVersionId, String name,
		int version, boolean useFinderCache) {

		return _uniquePersistenceFinderByPFCI_PPVI_N_V.fetch(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, name, version
			},
			useFinderCache);
	}

	/**
	 * Removes the patcher fix pack where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @param version the version
	 * @return the patcher fix pack that was removed
	 */
	@Override
	public PatcherFixPack removeByPFCI_PPVI_N_V(
			long patcherFixComponentId, long patcherProjectVersionId,
			String name, int version)
		throws NoSuchPatcherFixPackException {

		PatcherFixPack patcherFixPack = findByPFCI_PPVI_N_V(
			patcherFixComponentId, patcherProjectVersionId, name, version);

		return remove(patcherFixPack);
	}

	/**
	 * Returns the number of patcher fix packs where patcherFixComponentId = &#63; and patcherProjectVersionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param patcherFixComponentId the patcher fix component ID
	 * @param patcherProjectVersionId the patcher project version ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching patcher fix packs
	 */
	@Override
	public int countByPFCI_PPVI_N_V(
		long patcherFixComponentId, long patcherProjectVersionId, String name,
		int version) {

		return _uniquePersistenceFinderByPFCI_PPVI_N_V.count(
			finderCache,
			new Object[] {
				patcherFixComponentId, patcherProjectVersionId, name, version
			});
	}

	public PatcherFixPackPersistenceImpl() {
		setModelClass(PatcherFixPack.class);

		setModelImplClass(PatcherFixPackImpl.class);
		setModelPKClass(long.class);

		setTable(PatcherFixPackTable.INSTANCE);
	}

	/**
	 * Creates a new patcher fix pack with the primary key. Does not add the patcher fix pack to the database.
	 *
	 * @param patcherFixPackId the primary key for the new patcher fix pack
	 * @return the new patcher fix pack
	 */
	@Override
	public PatcherFixPack create(long patcherFixPackId) {
		PatcherFixPack patcherFixPack = new PatcherFixPackImpl();

		patcherFixPack.setNew(true);
		patcherFixPack.setPrimaryKey(patcherFixPackId);

		patcherFixPack.setCompanyId(CompanyThreadLocal.getCompanyId());

		return patcherFixPack;
	}

	/**
	 * Removes the patcher fix pack with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param patcherFixPackId the primary key of the patcher fix pack
	 * @return the patcher fix pack that was removed
	 * @throws NoSuchPatcherFixPackException if a patcher fix pack with the primary key could not be found
	 */
	@Override
	public PatcherFixPack remove(long patcherFixPackId)
		throws NoSuchPatcherFixPackException {

		return remove((Serializable)patcherFixPackId);
	}

	@Override
	protected PatcherFixPack removeImpl(PatcherFixPack patcherFixPack) {
		patcherFixPackToPatcherFixTableMapper.deleteLeftPrimaryKeyTableMappings(
			patcherFixPack.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(patcherFixPack)) {
				patcherFixPack = (PatcherFixPack)session.get(
					PatcherFixPackImpl.class,
					patcherFixPack.getPrimaryKeyObj());
			}

			if (patcherFixPack != null) {
				session.delete(patcherFixPack);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (patcherFixPack != null) {
			clearCache(patcherFixPack);
		}

		return patcherFixPack;
	}

	@Override
	public PatcherFixPack updateImpl(PatcherFixPack patcherFixPack) {
		boolean isNew = patcherFixPack.isNew();

		if (!(patcherFixPack instanceof PatcherFixPackModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(patcherFixPack.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					patcherFixPack);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in patcherFixPack proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PatcherFixPack implementation " +
					patcherFixPack.getClass());
		}

		PatcherFixPackModelImpl patcherFixPackModelImpl =
			(PatcherFixPackModelImpl)patcherFixPack;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (patcherFixPack.getCreateDate() == null)) {
			if (serviceContext == null) {
				patcherFixPack.setCreateDate(date);
			}
			else {
				patcherFixPack.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!patcherFixPackModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				patcherFixPack.setModifiedDate(date);
			}
			else {
				patcherFixPack.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(patcherFixPack);
			}
			else {
				patcherFixPack = (PatcherFixPack)session.merge(patcherFixPack);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(patcherFixPack, false);

		if (isNew) {
			patcherFixPack.setNew(false);
		}

		patcherFixPack.resetOriginalValues();

		return patcherFixPack;
	}

	/**
	 * Returns the patcher fix pack with the primary key or throws a <code>NoSuchPatcherFixPackException</code> if it could not be found.
	 *
	 * @param patcherFixPackId the primary key of the patcher fix pack
	 * @return the patcher fix pack
	 * @throws NoSuchPatcherFixPackException if a patcher fix pack with the primary key could not be found
	 */
	@Override
	public PatcherFixPack findByPrimaryKey(long patcherFixPackId)
		throws NoSuchPatcherFixPackException {

		return findByPrimaryKey((Serializable)patcherFixPackId);
	}

	/**
	 * Returns the patcher fix pack with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param patcherFixPackId the primary key of the patcher fix pack
	 * @return the patcher fix pack, or <code>null</code> if a patcher fix pack with the primary key could not be found
	 */
	@Override
	public PatcherFixPack fetchByPrimaryKey(long patcherFixPackId) {
		return fetchByPrimaryKey((Serializable)patcherFixPackId);
	}

	/**
	 * Returns the primaryKeys of patcher fixes associated with the patcher fix pack.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @return long[] of the primaryKeys of patcher fixes associated with the patcher fix pack
	 */
	@Override
	public long[] getPatcherFixPrimaryKeys(long pk) {
		long[] pks = patcherFixPackToPatcherFixTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the patcher fix pack associated with the patcher fix.
	 *
	 * @param pk the primary key of the patcher fix
	 * @return the patcher fix packs associated with the patcher fix
	 */
	@Override
	public List<PatcherFixPack> getPatcherFixPatcherFixPacks(long pk) {
		return getPatcherFixPatcherFixPacks(
			pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all the patcher fix pack associated with the patcher fix.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the patcher fix
	 * @param start the lower bound of the range of patcher fixes
	 * @param end the upper bound of the range of patcher fixes (not inclusive)
	 * @return the range of patcher fix packs associated with the patcher fix
	 */
	@Override
	public List<PatcherFixPack> getPatcherFixPatcherFixPacks(
		long pk, int start, int end) {

		return getPatcherFixPatcherFixPacks(pk, start, end, null);
	}

	/**
	 * Returns all the patcher fix pack associated with the patcher fix.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherFixPackModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the patcher fix
	 * @param start the lower bound of the range of patcher fixes
	 * @param end the upper bound of the range of patcher fixes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of patcher fix packs associated with the patcher fix
	 */
	@Override
	public List<PatcherFixPack> getPatcherFixPatcherFixPacks(
		long pk, int start, int end,
		OrderByComparator<PatcherFixPack> orderByComparator) {

		return patcherFixPackToPatcherFixTableMapper.getLeftBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of patcher fixes associated with the patcher fix pack.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @return the number of patcher fixes associated with the patcher fix pack
	 */
	@Override
	public int getPatcherFixesSize(long pk) {
		long[] pks = patcherFixPackToPatcherFixTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the patcher fix is associated with the patcher fix pack.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixPK the primary key of the patcher fix
	 * @return <code>true</code> if the patcher fix is associated with the patcher fix pack; <code>false</code> otherwise
	 */
	@Override
	public boolean containsPatcherFix(long pk, long patcherFixPK) {
		return patcherFixPackToPatcherFixTableMapper.containsTableMapping(
			pk, patcherFixPK);
	}

	/**
	 * Returns <code>true</code> if the patcher fix pack has any patcher fixes associated with it.
	 *
	 * @param pk the primary key of the patcher fix pack to check for associations with patcher fixes
	 * @return <code>true</code> if the patcher fix pack has any patcher fixes associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsPatcherFixes(long pk) {
		if (getPatcherFixesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the patcher fix pack and the patcher fix. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixPK the primary key of the patcher fix
	 * @return <code>true</code> if an association between the patcher fix pack and the patcher fix was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addPatcherFix(long pk, long patcherFixPK) {
		PatcherFixPack patcherFixPack = fetchByPrimaryKey(pk);

		if (patcherFixPack == null) {
			return patcherFixPackToPatcherFixTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, patcherFixPK);
		}
		else {
			return patcherFixPackToPatcherFixTableMapper.addTableMapping(
				patcherFixPack.getCompanyId(), pk, patcherFixPK);
		}
	}

	/**
	 * Adds an association between the patcher fix pack and the patcher fix. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFix the patcher fix
	 * @return <code>true</code> if an association between the patcher fix pack and the patcher fix was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addPatcherFix(long pk, PatcherFix patcherFix) {
		PatcherFixPack patcherFixPack = fetchByPrimaryKey(pk);

		if (patcherFixPack == null) {
			return patcherFixPackToPatcherFixTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				patcherFix.getPrimaryKey());
		}
		else {
			return patcherFixPackToPatcherFixTableMapper.addTableMapping(
				patcherFixPack.getCompanyId(), pk, patcherFix.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the patcher fix pack and the patcher fixes. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixPKs the primary keys of the patcher fixes
	 * @return <code>true</code> if at least one association between the patcher fix pack and the patcher fixes was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addPatcherFixes(long pk, long[] patcherFixPKs) {
		long companyId = 0;

		PatcherFixPack patcherFixPack = fetchByPrimaryKey(pk);

		if (patcherFixPack == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = patcherFixPack.getCompanyId();
		}

		long[] addedKeys =
			patcherFixPackToPatcherFixTableMapper.addTableMappings(
				companyId, pk, patcherFixPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the patcher fix pack and the patcher fixes. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixes the patcher fixes
	 * @return <code>true</code> if at least one association between the patcher fix pack and the patcher fixes was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addPatcherFixes(long pk, List<PatcherFix> patcherFixes) {
		return addPatcherFixes(
			pk,
			ListUtil.toLongArray(
				patcherFixes, PatcherFix.PATCHER_FIX_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the patcher fix pack and its patcher fixes. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack to clear the associated patcher fixes from
	 */
	@Override
	public void clearPatcherFixes(long pk) {
		patcherFixPackToPatcherFixTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the patcher fix pack and the patcher fix. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixPK the primary key of the patcher fix
	 */
	@Override
	public void removePatcherFix(long pk, long patcherFixPK) {
		patcherFixPackToPatcherFixTableMapper.deleteTableMapping(
			pk, patcherFixPK);
	}

	/**
	 * Removes the association between the patcher fix pack and the patcher fix. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFix the patcher fix
	 */
	@Override
	public void removePatcherFix(long pk, PatcherFix patcherFix) {
		patcherFixPackToPatcherFixTableMapper.deleteTableMapping(
			pk, patcherFix.getPrimaryKey());
	}

	/**
	 * Removes the association between the patcher fix pack and the patcher fixes. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixPKs the primary keys of the patcher fixes
	 */
	@Override
	public void removePatcherFixes(long pk, long[] patcherFixPKs) {
		patcherFixPackToPatcherFixTableMapper.deleteTableMappings(
			pk, patcherFixPKs);
	}

	/**
	 * Removes the association between the patcher fix pack and the patcher fixes. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixes the patcher fixes
	 */
	@Override
	public void removePatcherFixes(long pk, List<PatcherFix> patcherFixes) {
		removePatcherFixes(
			pk,
			ListUtil.toLongArray(
				patcherFixes, PatcherFix.PATCHER_FIX_ID_ACCESSOR));
	}

	/**
	 * Sets the patcher fixes associated with the patcher fix pack, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixPKs the primary keys of the patcher fixes to be associated with the patcher fix pack
	 */
	@Override
	public void setPatcherFixes(long pk, long[] patcherFixPKs) {
		Set<Long> newPatcherFixPKsSet = SetUtil.fromArray(patcherFixPKs);
		Set<Long> oldPatcherFixPKsSet = SetUtil.fromArray(
			patcherFixPackToPatcherFixTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removePatcherFixPKsSet = new HashSet<Long>(
			oldPatcherFixPKsSet);

		removePatcherFixPKsSet.removeAll(newPatcherFixPKsSet);

		patcherFixPackToPatcherFixTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removePatcherFixPKsSet));

		newPatcherFixPKsSet.removeAll(oldPatcherFixPKsSet);

		long companyId = 0;

		PatcherFixPack patcherFixPack = fetchByPrimaryKey(pk);

		if (patcherFixPack == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = patcherFixPack.getCompanyId();
		}

		patcherFixPackToPatcherFixTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newPatcherFixPKsSet));
	}

	/**
	 * Sets the patcher fixes associated with the patcher fix pack, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the patcher fix pack
	 * @param patcherFixes the patcher fixes to be associated with the patcher fix pack
	 */
	@Override
	public void setPatcherFixes(long pk, List<PatcherFix> patcherFixes) {
		try {
			long[] patcherFixPKs = new long[patcherFixes.size()];

			for (int i = 0; i < patcherFixes.size(); i++) {
				PatcherFix patcherFix = patcherFixes.get(i);

				patcherFixPKs[i] = patcherFix.getPrimaryKey();
			}

			setPatcherFixes(pk, patcherFixPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "patcherFixPackId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PATCHERFIXPACK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PatcherFixPackModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the patcher fix pack persistence.
	 */
	@Activate
	public void activate() {
		patcherFixPackToPatcherFixTableMapper =
			TableMapperFactory.getTableMapper(
				"OSBPatcher_PFixes_PFixPacks#patcherFixPackId",
				"OSBPatcher_PFixes_PFixPacks", "companyId", "patcherFixPackId",
				"patcherFixId", this, PatcherFix.class);

		_finderPathFetchByPatcherBuildId = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByPatcherBuildId",
			new String[] {Long.class.getName()},
			new String[] {"patcherBuildId"}, false,
			PatcherFixPack::getPatcherBuildId);

		_uniquePersistenceFinderByPatcherBuildId =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByPatcherBuildId,
				_SQL_SELECT_PATCHERFIXPACK_WHERE, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherBuildId", FinderColumn.Type.LONG,
					"=", true, true, PatcherFixPack::getPatcherBuildId));

		_finderPathWithPaginationFindByPatcherFixComponentId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPatcherFixComponentId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"patcherFixComponentId"}, true);

		_finderPathWithoutPaginationFindByPatcherFixComponentId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByPatcherFixComponentId",
				new String[] {Long.class.getName()},
				new String[] {"patcherFixComponentId"}, true);

		_finderPathCountByPatcherFixComponentId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPatcherFixComponentId", new String[] {Long.class.getName()},
			new String[] {"patcherFixComponentId"}, false);

		_collectionPersistenceFinderByPatcherFixComponentId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPatcherFixComponentId,
				_finderPathWithoutPaginationFindByPatcherFixComponentId,
				_finderPathCountByPatcherFixComponentId,
				_SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherFixComponentId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherFixComponentId));

		_finderPathWithPaginationFindByVersion = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByVersion",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"version"}, true);

		_finderPathWithoutPaginationFindByVersion = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByVersion",
			new String[] {Integer.class.getName()}, new String[] {"version"},
			true);

		_finderPathCountByVersion = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByVersion",
			new String[] {Integer.class.getName()}, new String[] {"version"},
			false);

		_collectionPersistenceFinderByVersion =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByVersion,
				_finderPathWithoutPaginationFindByVersion,
				_finderPathCountByVersion, _SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "version", FinderColumn.Type.INTEGER,
					"=", true, true, PatcherFixPack::getVersion));

		_finderPathWithPaginationFindByPFCI_PPVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPFCI_PPVI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"patcherFixComponentId", "patcherProjectVersionId"},
			true);

		_finderPathWithoutPaginationFindByPFCI_PPVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPFCI_PPVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"patcherFixComponentId", "patcherProjectVersionId"},
			true);

		_finderPathCountByPFCI_PPVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPFCI_PPVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"patcherFixComponentId", "patcherProjectVersionId"},
			false);

		_collectionPersistenceFinderByPFCI_PPVI =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPFCI_PPVI,
				_finderPathWithoutPaginationFindByPFCI_PPVI,
				_finderPathCountByPFCI_PPVI, _SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherFixComponentId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherFixComponentId),
				new FinderColumn<>(
					"patcherFixPack.", "patcherProjectVersionId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherProjectVersionId));

		_finderPathWithPaginationFindByPFCI_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPFCI_V",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"patcherFixComponentId", "version"}, true);

		_finderPathWithoutPaginationFindByPFCI_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPFCI_V",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"patcherFixComponentId", "version"}, true);

		_finderPathCountByPFCI_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPFCI_V",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"patcherFixComponentId", "version"}, false);

		_collectionPersistenceFinderByPFCI_V =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPFCI_V,
				_finderPathWithoutPaginationFindByPFCI_V,
				_finderPathCountByPFCI_V, _SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherFixComponentId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherFixComponentId),
				new FinderColumn<>(
					"patcherFixPack.", "version", FinderColumn.Type.INTEGER,
					"=", true, true, PatcherFixPack::getVersion));

		_finderPathFetchByPFCI_N = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByPFCI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"patcherProjectVersionId", "name"}, false,
			PatcherFixPack::getPatcherProjectVersionId,
			convertNullFunction(PatcherFixPack::getName));

		_uniquePersistenceFinderByPFCI_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByPFCI_N, _SQL_SELECT_PATCHERFIXPACK_WHERE,
			"",
			new FinderColumn<>(
				"patcherFixPack.", "patcherProjectVersionId",
				FinderColumn.Type.LONG, "=", true, true,
				PatcherFixPack::getPatcherProjectVersionId),
			new FinderColumn<>(
				"patcherFixPack.", "name", FinderColumn.Type.STRING, "=", true,
				true, PatcherFixPack::getName));

		_finderPathWithPaginationFindByPFCI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPFCI_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"patcherProjectVersionId", "status"}, true);

		_finderPathWithoutPaginationFindByPFCI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPFCI_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"patcherProjectVersionId", "status"}, true);

		_finderPathCountByPFCI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPFCI_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"patcherProjectVersionId", "status"}, false);

		_collectionPersistenceFinderByPFCI_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPFCI_S,
				_finderPathWithoutPaginationFindByPFCI_S,
				_finderPathCountByPFCI_S, _SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherProjectVersionId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherProjectVersionId),
				new FinderColumn<>(
					"patcherFixPack.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, PatcherFixPack::getStatus));

		_finderPathWithPaginationFindByPFCI_PPVI_GtV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPFCI_PPVI_GtV",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"patcherFixComponentId", "patcherProjectVersionId", "version"
			},
			true);

		_finderPathWithPaginationCountByPFCI_PPVI_GtV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByPFCI_PPVI_GtV",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"patcherFixComponentId", "patcherProjectVersionId", "version"
			},
			false);

		_collectionPersistenceFinderByPFCI_PPVI_GtV =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPFCI_PPVI_GtV, null,
				_finderPathWithPaginationCountByPFCI_PPVI_GtV,
				_SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherFixComponentId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherFixComponentId),
				new FinderColumn<>(
					"patcherFixPack.", "patcherProjectVersionId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherProjectVersionId),
				new FinderColumn<>(
					"patcherFixPack.", "version", FinderColumn.Type.INTEGER,
					">", true, true, PatcherFixPack::getVersion));

		_finderPathWithPaginationFindByPFCI_PPVI_LtV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPFCI_PPVI_LtV",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"patcherFixComponentId", "patcherProjectVersionId", "version"
			},
			true);

		_finderPathWithPaginationCountByPFCI_PPVI_LtV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByPFCI_PPVI_LtV",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"patcherFixComponentId", "patcherProjectVersionId", "version"
			},
			false);

		_collectionPersistenceFinderByPFCI_PPVI_LtV =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPFCI_PPVI_LtV, null,
				_finderPathWithPaginationCountByPFCI_PPVI_LtV,
				_SQL_SELECT_PATCHERFIXPACK_WHERE,
				_SQL_COUNT_PATCHERFIXPACK_WHERE,
				PatcherFixPackModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"patcherFixPack.", "patcherFixComponentId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherFixComponentId),
				new FinderColumn<>(
					"patcherFixPack.", "patcherProjectVersionId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherFixPack::getPatcherProjectVersionId),
				new FinderColumn<>(
					"patcherFixPack.", "version", FinderColumn.Type.INTEGER,
					"<", true, true, PatcherFixPack::getVersion));

		_finderPathFetchByPFCI_PPVI_N_V = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByPFCI_PPVI_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {
				"patcherFixComponentId", "patcherProjectVersionId", "name",
				"version"
			},
			false, PatcherFixPack::getPatcherFixComponentId,
			PatcherFixPack::getPatcherProjectVersionId,
			convertNullFunction(PatcherFixPack::getName),
			PatcherFixPack::getVersion);

		_uniquePersistenceFinderByPFCI_PPVI_N_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByPFCI_PPVI_N_V,
			_SQL_SELECT_PATCHERFIXPACK_WHERE, "",
			new FinderColumn<>(
				"patcherFixPack.", "patcherFixComponentId",
				FinderColumn.Type.LONG, "=", true, true,
				PatcherFixPack::getPatcherFixComponentId),
			new FinderColumn<>(
				"patcherFixPack.", "patcherProjectVersionId",
				FinderColumn.Type.LONG, "=", true, true,
				PatcherFixPack::getPatcherProjectVersionId),
			new FinderColumn<>(
				"patcherFixPack.", "name", FinderColumn.Type.STRING, "=", true,
				true, PatcherFixPack::getName),
			new FinderColumn<>(
				"patcherFixPack.", "version", FinderColumn.Type.INTEGER, "=",
				true, true, PatcherFixPack::getVersion));

		PatcherFixPackUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		PatcherFixPackUtil.setPersistence(null);

		entityCache.removeCache(PatcherFixPackImpl.class.getName());

		TableMapperFactory.removeTableMapper(
			"OSBPatcher_PFixes_PFixPacks#patcherFixPackId");
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

	protected TableMapper<PatcherFixPack, PatcherFix>
		patcherFixPackToPatcherFixTableMapper;

	private static final String _ENTITY_ALIAS_PREFIX =
		PatcherFixPackModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_PATCHERFIXPACK =
		"SELECT patcherFixPack FROM PatcherFixPack patcherFixPack";

	private static final String _SQL_SELECT_PATCHERFIXPACK_WHERE =
		"SELECT patcherFixPack FROM PatcherFixPack patcherFixPack WHERE ";

	private static final String _SQL_COUNT_PATCHERFIXPACK_WHERE =
		"SELECT COUNT(patcherFixPack) FROM PatcherFixPack patcherFixPack WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"patcherFixPack.patcherFixPackId";

	private static final String _FILTER_SQL_SELECT_PATCHERFIXPACK_WHERE =
		"SELECT DISTINCT {patcherFixPack.*} FROM OSBPatcher_PatcherFixPack patcherFixPack WHERE ";

	private static final String
		_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OSBPatcher_PatcherFixPack.*} FROM (SELECT DISTINCT patcherFixPack.patcherFixPackId FROM OSBPatcher_PatcherFixPack patcherFixPack WHERE ";

	private static final String
		_FILTER_SQL_SELECT_PATCHERFIXPACK_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OSBPatcher_PatcherFixPack ON TEMP_TABLE.patcherFixPackId = OSBPatcher_PatcherFixPack.patcherFixPackId";

	private static final String _FILTER_SQL_COUNT_PATCHERFIXPACK_WHERE =
		"SELECT COUNT(DISTINCT patcherFixPack.patcherFixPackId) AS COUNT_VALUE FROM OSBPatcher_PatcherFixPack patcherFixPack WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "patcherFixPack";

	private static final String _FILTER_ENTITY_TABLE =
		"OSBPatcher_PatcherFixPack";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"OSBPatcher_PatcherFixPack.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PatcherFixPack exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PatcherFixPackPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:92040314