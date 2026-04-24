/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutBranchTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.LayoutBranchPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutBranchUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.LayoutBranchImpl;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the layout branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutBranchPersistenceImpl
	extends BasePersistenceImpl<LayoutBranch>
	implements LayoutBranchPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutBranchUtil</code> to access the layout branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutBranchImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLayoutSetBranchId;
	private FinderPath _finderPathWithoutPaginationFindByLayoutSetBranchId;
	private FinderPath _finderPathCountByLayoutSetBranchId;
	private CollectionPersistenceFinder<LayoutBranch>
		_collectionPersistenceFinderByLayoutSetBranchId;

	/**
	 * Returns all the layout branches where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId) {
		return findByLayoutSetBranchId(
			layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branches where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @return the range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) {

		return findByLayoutSetBranchId(layoutSetBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branches where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {

		return findByLayoutSetBranchId(
			layoutSetBranchId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branches where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByLayoutSetBranchId.find(
			FinderCacheUtil.getFinderCache(), new Object[] {layoutSetBranchId},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByLayoutSetBranchId_First(
			long layoutSetBranchId,
			OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = fetchByLayoutSetBranchId_First(
			layoutSetBranchId, orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutSetBranchId=");
		sb.append(layoutSetBranchId);

		sb.append("}");

		throw new NoSuchLayoutBranchException(sb.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator) {

		List<LayoutBranch> list = findByLayoutSetBranchId(
			layoutSetBranchId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the layout branches where layoutSetBranchId = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 */
	@Override
	public void removeByLayoutSetBranchId(long layoutSetBranchId) {
		for (LayoutBranch layoutBranch :
				findByLayoutSetBranchId(
					layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branches where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the number of matching layout branches
	 */
	@Override
	public int countByLayoutSetBranchId(long layoutSetBranchId) {
		return _collectionPersistenceFinderByLayoutSetBranchId.count(
			FinderCacheUtil.getFinderCache(), new Object[] {layoutSetBranchId});
	}

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;
	private CollectionPersistenceFinder<LayoutBranch>
		_collectionPersistenceFinderByPlid;

	/**
	 * Returns all the layout branches where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branches where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @return the range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branches where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branches where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByPlid.find(
			FinderCacheUtil.getFinderCache(), new Object[] {plid}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout branch in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByPlid_First(
			long plid, OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = fetchByPlid_First(plid, orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchLayoutBranchException(sb.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByPlid_First(
		long plid, OrderByComparator<LayoutBranch> orderByComparator) {

		List<LayoutBranch> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the layout branches where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (LayoutBranch layoutBranch :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branches where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout branches
	 */
	@Override
	public int countByPlid(long plid) {
		return _collectionPersistenceFinderByPlid.count(
			FinderCacheUtil.getFinderCache(), new Object[] {plid});
	}

	private FinderPath _finderPathWithPaginationFindByL_P;
	private FinderPath _finderPathWithoutPaginationFindByL_P;
	private FinderPath _finderPathCountByL_P;
	private CollectionPersistenceFinder<LayoutBranch>
		_collectionPersistenceFinderByL_P;

	/**
	 * Returns all the layout branches where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid) {
		return findByL_P(
			layoutSetBranchId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout branches where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @return the range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P(
		long layoutSetBranchId, long plid, int start, int end) {

		return findByL_P(layoutSetBranchId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branches where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P(
		long layoutSetBranchId, long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {

		return findByL_P(
			layoutSetBranchId, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branches where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P(
		long layoutSetBranchId, long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByL_P.find(
			FinderCacheUtil.getFinderCache(),
			new Object[] {layoutSetBranchId, plid}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_First(
			long layoutSetBranchId, long plid,
			OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = fetchByL_P_First(
			layoutSetBranchId, plid, orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutSetBranchId=");
		sb.append(layoutSetBranchId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchLayoutBranchException(sb.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_First(
		long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator) {

		List<LayoutBranch> list = findByL_P(
			layoutSetBranchId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the layout branches where layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 */
	@Override
	public void removeByL_P(long layoutSetBranchId, long plid) {
		for (LayoutBranch layoutBranch :
				findByL_P(
					layoutSetBranchId, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branches where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching layout branches
	 */
	@Override
	public int countByL_P(long layoutSetBranchId, long plid) {
		return _collectionPersistenceFinderByL_P.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {layoutSetBranchId, plid});
	}

	private FinderPath _finderPathFetchByL_P_N;
	private UniquePersistenceFinder<LayoutBranch>
		_uniquePersistenceFinderByL_P_N;

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or throws a <code>NoSuchLayoutBranchException</code> if it could not be found.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_N(
			long layoutSetBranchId, long plid, String name)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = fetchByL_P_N(layoutSetBranchId, plid, name);

		if (layoutBranch == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("layoutSetBranchId=");
			sb.append(layoutSetBranchId);

			sb.append(", plid=");
			sb.append(plid);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLayoutBranchException(sb.toString());
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_N(
		long layoutSetBranchId, long plid, String name) {

		return fetchByL_P_N(layoutSetBranchId, plid, name, true);
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_N(
		long layoutSetBranchId, long plid, String name,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByL_P_N.fetch(
			FinderCacheUtil.getFinderCache(),
			new Object[] {layoutSetBranchId, plid, name}, useFinderCache);
	}

	/**
	 * Removes the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the layout branch that was removed
	 */
	@Override
	public LayoutBranch removeByL_P_N(
			long layoutSetBranchId, long plid, String name)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = findByL_P_N(layoutSetBranchId, plid, name);

		return remove(layoutBranch);
	}

	/**
	 * Returns the number of layout branches where layoutSetBranchId = &#63; and plid = &#63; and name = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the number of matching layout branches
	 */
	@Override
	public int countByL_P_N(long layoutSetBranchId, long plid, String name) {
		return _uniquePersistenceFinderByL_P_N.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {layoutSetBranchId, plid, name});
	}

	private FinderPath _finderPathWithPaginationFindByL_P_M;
	private FinderPath _finderPathWithoutPaginationFindByL_P_M;
	private FinderPath _finderPathCountByL_P_M;
	private CollectionPersistenceFinder<LayoutBranch>
		_collectionPersistenceFinderByL_P_M;

	/**
	 * Returns all the layout branches where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(
		long layoutSetBranchId, long plid, boolean master) {

		return findByL_P_M(
			layoutSetBranchId, plid, master, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branches where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @return the range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(
		long layoutSetBranchId, long plid, boolean master, int start, int end) {

		return findByL_P_M(layoutSetBranchId, plid, master, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branches where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(
		long layoutSetBranchId, long plid, boolean master, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {

		return findByL_P_M(
			layoutSetBranchId, plid, master, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout branches where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout branches
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(
		long layoutSetBranchId, long plid, boolean master, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByL_P_M.find(
			FinderCacheUtil.getFinderCache(),
			new Object[] {layoutSetBranchId, plid, master}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_M_First(
			long layoutSetBranchId, long plid, boolean master,
			OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = fetchByL_P_M_First(
			layoutSetBranchId, plid, master, orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutSetBranchId=");
		sb.append(layoutSetBranchId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", master=");
		sb.append(master);

		sb.append("}");

		throw new NoSuchLayoutBranchException(sb.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_M_First(
		long layoutSetBranchId, long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator) {

		List<LayoutBranch> list = findByL_P_M(
			layoutSetBranchId, plid, master, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the layout branches where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 */
	@Override
	public void removeByL_P_M(
		long layoutSetBranchId, long plid, boolean master) {

		for (LayoutBranch layoutBranch :
				findByL_P_M(
					layoutSetBranchId, plid, master, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branches where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the number of matching layout branches
	 */
	@Override
	public int countByL_P_M(long layoutSetBranchId, long plid, boolean master) {
		return _collectionPersistenceFinderByL_P_M.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {layoutSetBranchId, plid, master});
	}

	public LayoutBranchPersistenceImpl() {
		setModelClass(LayoutBranch.class);

		setModelImplClass(LayoutBranchImpl.class);
		setModelPKClass(long.class);

		setTable(LayoutBranchTable.INSTANCE);
	}

	/**
	 * Caches the layout branch in the entity cache if it is enabled.
	 *
	 * @param layoutBranch the layout branch
	 */
	@Override
	public void cacheResult(LayoutBranch layoutBranch) {
		EntityCacheUtil.putResult(
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch);

		FinderCacheUtil.putResult(
			_finderPathFetchByL_P_N,
			new Object[] {
				layoutBranch.getLayoutSetBranchId(), layoutBranch.getPlid(),
				layoutBranch.getName()
			},
			layoutBranch);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the layout branches in the entity cache if it is enabled.
	 *
	 * @param layoutBranchs the layout branches
	 */
	@Override
	public void cacheResult(List<LayoutBranch> layoutBranchs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (layoutBranchs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LayoutBranch layoutBranch : layoutBranchs) {
			if (EntityCacheUtil.getResult(
					LayoutBranchImpl.class, layoutBranch.getPrimaryKey()) ==
						null) {

				cacheResult(layoutBranch);
			}
		}
	}

	/**
	 * Clears the cache for all layout branches.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutBranchImpl.class);

		FinderCacheUtil.clearCache(LayoutBranchImpl.class);
	}

	/**
	 * Clears the cache for the layout branch.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutBranch layoutBranch) {
		EntityCacheUtil.removeResult(LayoutBranchImpl.class, layoutBranch);
	}

	@Override
	public void clearCache(List<LayoutBranch> layoutBranchs) {
		for (LayoutBranch layoutBranch : layoutBranchs) {
			EntityCacheUtil.removeResult(LayoutBranchImpl.class, layoutBranch);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(LayoutBranchImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(LayoutBranchImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutBranchModelImpl layoutBranchModelImpl) {

		Object[] args = new Object[] {
			layoutBranchModelImpl.getLayoutSetBranchId(),
			layoutBranchModelImpl.getPlid(), layoutBranchModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathFetchByL_P_N, args, layoutBranchModelImpl);
	}

	/**
	 * Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	 *
	 * @param layoutBranchId the primary key for the new layout branch
	 * @return the new layout branch
	 */
	@Override
	public LayoutBranch create(long layoutBranchId) {
		LayoutBranch layoutBranch = new LayoutBranchImpl();

		layoutBranch.setNew(true);
		layoutBranch.setPrimaryKey(layoutBranchId);

		layoutBranch.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutBranch;
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutBranchId the primary key of the layout branch
	 * @return the layout branch that was removed
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch remove(long layoutBranchId)
		throws NoSuchLayoutBranchException {

		return remove((Serializable)layoutBranchId);
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch that was removed
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch remove(Serializable primaryKey)
		throws NoSuchLayoutBranchException {

		Session session = null;

		try {
			session = openSession();

			LayoutBranch layoutBranch = (LayoutBranch)session.get(
				LayoutBranchImpl.class, primaryKey);

			if (layoutBranch == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutBranchException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutBranch);
		}
		catch (NoSuchLayoutBranchException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LayoutBranch removeImpl(LayoutBranch layoutBranch) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutBranch)) {
				layoutBranch = (LayoutBranch)session.get(
					LayoutBranchImpl.class, layoutBranch.getPrimaryKeyObj());
			}

			if (layoutBranch != null) {
				session.delete(layoutBranch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutBranch != null) {
			clearCache(layoutBranch);
		}

		return layoutBranch;
	}

	@Override
	public LayoutBranch updateImpl(LayoutBranch layoutBranch) {
		boolean isNew = layoutBranch.isNew();

		if (!(layoutBranch instanceof LayoutBranchModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutBranch.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutBranch);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutBranch proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutBranch implementation " +
					layoutBranch.getClass());
		}

		LayoutBranchModelImpl layoutBranchModelImpl =
			(LayoutBranchModelImpl)layoutBranch;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(layoutBranch);
			}
			else {
				layoutBranch = (LayoutBranch)session.merge(layoutBranch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			LayoutBranchImpl.class, layoutBranchModelImpl, false, true);

		cacheUniqueFindersCache(layoutBranchModelImpl);

		if (isNew) {
			layoutBranch.setNew(false);
		}

		layoutBranch.resetOriginalValues();

		return layoutBranch;
	}

	/**
	 * Returns the layout branch with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutBranchException {

		LayoutBranch layoutBranch = fetchByPrimaryKey(primaryKey);

		if (layoutBranch == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutBranchException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch with the primary key or throws a <code>NoSuchLayoutBranchException</code> if it could not be found.
	 *
	 * @param layoutBranchId the primary key of the layout branch
	 * @return the layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch findByPrimaryKey(long layoutBranchId)
		throws NoSuchLayoutBranchException {

		return findByPrimaryKey((Serializable)layoutBranchId);
	}

	/**
	 * Returns the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutBranchId the primary key of the layout branch
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch fetchByPrimaryKey(long layoutBranchId) {
		return fetchByPrimaryKey((Serializable)layoutBranchId);
	}

	/**
	 * Returns all the layout branches.
	 *
	 * @return the layout branches
	 */
	@Override
	public List<LayoutBranch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @return the range of layout branches
	 */
	@Override
	public List<LayoutBranch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout branches
	 */
	@Override
	public List<LayoutBranch> findAll(
		int start, int end, OrderByComparator<LayoutBranch> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branches
	 * @param end the upper bound of the range of layout branches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout branches
	 */
	@Override
	public List<LayoutBranch> findAll(
		int start, int end, OrderByComparator<LayoutBranch> orderByComparator,
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

		List<LayoutBranch> list = null;

		if (useFinderCache) {
			list = (List<LayoutBranch>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAYOUTBRANCH);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTBRANCH;

				sql = sql.concat(LayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LayoutBranch>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the layout branches from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutBranch layoutBranch : findAll()) {
			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branches.
	 *
	 * @return the number of layout branches
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LAYOUTBRANCH);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutBranchId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTBRANCH;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutBranchModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout branch persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByLayoutSetBranchId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"layoutSetBranchId"}, true);

		_finderPathWithoutPaginationFindByLayoutSetBranchId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetBranchId", new String[] {Long.class.getName()},
			new String[] {"layoutSetBranchId"}, true);

		_finderPathCountByLayoutSetBranchId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetBranchId", new String[] {Long.class.getName()},
			new String[] {"layoutSetBranchId"}, false);

		_collectionPersistenceFinderByLayoutSetBranchId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByLayoutSetBranchId,
				_finderPathWithoutPaginationFindByLayoutSetBranchId,
				_finderPathCountByLayoutSetBranchId,
				_SQL_SELECT_LAYOUTBRANCH_WHERE, _SQL_COUNT_LAYOUTBRANCH_WHERE,
				LayoutBranchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"layoutBranch.", "layoutSetBranchId",
					FinderColumn.Type.LONG, "=", true, true,
					LayoutBranch::getLayoutSetBranchId));

		_finderPathWithPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"plid"}, true);

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, true);

		_finderPathCountByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, false);

		_collectionPersistenceFinderByPlid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByPlid,
			_finderPathWithoutPaginationFindByPlid, _finderPathCountByPlid,
			_SQL_SELECT_LAYOUTBRANCH_WHERE, _SQL_COUNT_LAYOUTBRANCH_WHERE,
			LayoutBranchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"layoutBranch.", "plid", FinderColumn.Type.LONG, "=", true,
				true, LayoutBranch::getPlid));

		_finderPathWithPaginationFindByL_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByL_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"layoutSetBranchId", "plid"}, true);

		_finderPathWithoutPaginationFindByL_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"layoutSetBranchId", "plid"}, true);

		_finderPathCountByL_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"layoutSetBranchId", "plid"}, false);

		_collectionPersistenceFinderByL_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByL_P,
			_finderPathWithoutPaginationFindByL_P, _finderPathCountByL_P,
			_SQL_SELECT_LAYOUTBRANCH_WHERE, _SQL_COUNT_LAYOUTBRANCH_WHERE,
			LayoutBranchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"layoutBranch.", "layoutSetBranchId", FinderColumn.Type.LONG,
				"=", true, false, LayoutBranch::getLayoutSetBranchId),
			new FinderColumn<>(
				"layoutBranch.", "plid", FinderColumn.Type.LONG, "=", true,
				true, LayoutBranch::getPlid));

		_finderPathFetchByL_P_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByL_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"layoutSetBranchId", "plid", "name"}, true);

		_uniquePersistenceFinderByL_P_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByL_P_N, _SQL_SELECT_LAYOUTBRANCH_WHERE,
			new FinderColumn<>(
				"layoutBranch.", "layoutSetBranchId", FinderColumn.Type.LONG,
				"=", true, false, LayoutBranch::getLayoutSetBranchId),
			new FinderColumn<>(
				"layoutBranch.", "plid", FinderColumn.Type.LONG, "=", true,
				false, LayoutBranch::getPlid),
			new FinderColumn<>(
				"layoutBranch.", "name", FinderColumn.Type.STRING, "=", true,
				true, LayoutBranch::getName));

		_finderPathWithPaginationFindByL_P_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"layoutSetBranchId", "plid", "master"}, true);

		_finderPathWithoutPaginationFindByL_P_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"layoutSetBranchId", "plid", "master"}, true);

		_finderPathCountByL_P_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"layoutSetBranchId", "plid", "master"}, false);

		_collectionPersistenceFinderByL_P_M = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByL_P_M,
			_finderPathWithoutPaginationFindByL_P_M, _finderPathCountByL_P_M,
			_SQL_SELECT_LAYOUTBRANCH_WHERE, _SQL_COUNT_LAYOUTBRANCH_WHERE,
			LayoutBranchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"layoutBranch.", "layoutSetBranchId", FinderColumn.Type.LONG,
				"=", true, false, LayoutBranch::getLayoutSetBranchId),
			new FinderColumn<>(
				"layoutBranch.", "plid", FinderColumn.Type.LONG, "=", true,
				false, LayoutBranch::getPlid),
			new FinderColumn<>(
				"layoutBranch.", "master", FinderColumn.Type.BOOLEAN, "=", true,
				true, LayoutBranch::isMaster));

		LayoutBranchUtil.setPersistence(this);
	}

	public void destroy() {
		LayoutBranchUtil.setPersistence(null);

		EntityCacheUtil.removeCache(LayoutBranchImpl.class.getName());
	}

	private static final String _SQL_SELECT_LAYOUTBRANCH =
		"SELECT layoutBranch FROM LayoutBranch layoutBranch";

	private static final String _SQL_SELECT_LAYOUTBRANCH_WHERE =
		"SELECT layoutBranch FROM LayoutBranch layoutBranch WHERE ";

	private static final String _SQL_COUNT_LAYOUTBRANCH =
		"SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch";

	private static final String _SQL_COUNT_LAYOUTBRANCH_WHERE =
		"SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutBranch.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutBranch exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutBranch exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutBranchPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1907723536