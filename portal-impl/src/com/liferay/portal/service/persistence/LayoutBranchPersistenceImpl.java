/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutBranchImpl;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the layout branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchPersistence
 * @see LayoutBranchUtil
 * @generated
 */
public class LayoutBranchPersistenceImpl extends BasePersistenceImpl<LayoutBranch>
	implements LayoutBranchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutBranchUtil} to access the layout branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutBranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_LAYOUTSETBRANCHID = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST, "findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByLayoutSetBranchId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_L_P = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST, "findByL_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByL_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_L_P_M = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P_M = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the layout branch in the entity cache if it is enabled.
	 *
	 * @param layoutBranch the layout branch
	 */
	public void cacheResult(LayoutBranch layoutBranch) {
		EntityCacheUtil.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_P_M,
			new Object[] {
				Long.valueOf(layoutBranch.getLayoutSetBranchId()),
				Long.valueOf(layoutBranch.getPlid()),
				Boolean.valueOf(layoutBranch.getMaster())
			}, layoutBranch);

		layoutBranch.resetOriginalValues();
	}

	/**
	 * Caches the layout branchs in the entity cache if it is enabled.
	 *
	 * @param layoutBranchs the layout branchs
	 */
	public void cacheResult(List<LayoutBranch> layoutBranchs) {
		for (LayoutBranch layoutBranch : layoutBranchs) {
			if (EntityCacheUtil.getResult(
						LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						LayoutBranchImpl.class, layoutBranch.getPrimaryKey(),
						this) == null) {
				cacheResult(layoutBranch);
			}
		}
	}

	/**
	 * Clears the cache for all layout branchs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LayoutBranchImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LayoutBranchImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the layout branch.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutBranch layoutBranch) {
		EntityCacheUtil.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_P_M,
			new Object[] {
				Long.valueOf(layoutBranch.getLayoutSetBranchId()),
				Long.valueOf(layoutBranch.getPlid()),
				Boolean.valueOf(layoutBranch.getMaster())
			});
	}

	/**
	 * Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	 *
	 * @param LayoutBranchId the primary key for the new layout branch
	 * @return the new layout branch
	 */
	public LayoutBranch create(long LayoutBranchId) {
		LayoutBranch layoutBranch = new LayoutBranchImpl();

		layoutBranch.setNew(true);
		layoutBranch.setPrimaryKey(LayoutBranchId);

		return layoutBranch;
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutBranch remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param LayoutBranchId the primary key of the layout branch
	 * @return the layout branch that was removed
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch remove(long LayoutBranchId)
		throws NoSuchLayoutBranchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutBranch layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
					Long.valueOf(LayoutBranchId));

			if (layoutBranch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						LayoutBranchId);
				}

				throw new NoSuchLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					LayoutBranchId);
			}

			return layoutBranchPersistence.remove(layoutBranch);
		}
		catch (NoSuchLayoutBranchException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the layout branch from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutBranch the layout branch
	 * @return the layout branch that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutBranch remove(LayoutBranch layoutBranch)
		throws SystemException {
		return super.remove(layoutBranch);
	}

	@Override
	protected LayoutBranch removeImpl(LayoutBranch layoutBranch)
		throws SystemException {
		layoutBranch = toUnwrappedModel(layoutBranch);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, layoutBranch);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LayoutBranchModelImpl layoutBranchModelImpl = (LayoutBranchModelImpl)layoutBranch;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_P_M,
			new Object[] {
				Long.valueOf(layoutBranchModelImpl.getLayoutSetBranchId()),
				Long.valueOf(layoutBranchModelImpl.getPlid()),
				Boolean.valueOf(layoutBranchModelImpl.getMaster())
			});

		EntityCacheUtil.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey());

		return layoutBranch;
	}

	@Override
	public LayoutBranch updateImpl(
		com.liferay.portal.model.LayoutBranch layoutBranch, boolean merge)
		throws SystemException {
		layoutBranch = toUnwrappedModel(layoutBranch);

		boolean isNew = layoutBranch.isNew();

		LayoutBranchModelImpl layoutBranchModelImpl = (LayoutBranchModelImpl)layoutBranch;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutBranch, merge);

			layoutBranch.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch);

		if (!isNew &&
				((layoutBranch.getLayoutSetBranchId() != layoutBranchModelImpl.getOriginalLayoutSetBranchId()) ||
				(layoutBranch.getPlid() != layoutBranchModelImpl.getOriginalPlid()) ||
				(layoutBranch.getMaster() != layoutBranchModelImpl.getOriginalMaster()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_P_M,
				new Object[] {
					Long.valueOf(
						layoutBranchModelImpl.getOriginalLayoutSetBranchId()),
					Long.valueOf(layoutBranchModelImpl.getOriginalPlid()),
					Boolean.valueOf(layoutBranchModelImpl.getOriginalMaster())
				});
		}

		if (isNew ||
				((layoutBranch.getLayoutSetBranchId() != layoutBranchModelImpl.getOriginalLayoutSetBranchId()) ||
				(layoutBranch.getPlid() != layoutBranchModelImpl.getOriginalPlid()) ||
				(layoutBranch.getMaster() != layoutBranchModelImpl.getOriginalMaster()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_P_M,
				new Object[] {
					Long.valueOf(layoutBranch.getLayoutSetBranchId()),
					Long.valueOf(layoutBranch.getPlid()),
					Boolean.valueOf(layoutBranch.getMaster())
				}, layoutBranch);
		}

		return layoutBranch;
	}

	protected LayoutBranch toUnwrappedModel(LayoutBranch layoutBranch) {
		if (layoutBranch instanceof LayoutBranchImpl) {
			return layoutBranch;
		}

		LayoutBranchImpl layoutBranchImpl = new LayoutBranchImpl();

		layoutBranchImpl.setNew(layoutBranch.isNew());
		layoutBranchImpl.setPrimaryKey(layoutBranch.getPrimaryKey());

		layoutBranchImpl.setLayoutBranchId(layoutBranch.getLayoutBranchId());
		layoutBranchImpl.setGroupId(layoutBranch.getGroupId());
		layoutBranchImpl.setCompanyId(layoutBranch.getCompanyId());
		layoutBranchImpl.setUserId(layoutBranch.getUserId());
		layoutBranchImpl.setUserName(layoutBranch.getUserName());
		layoutBranchImpl.setLayoutSetBranchId(layoutBranch.getLayoutSetBranchId());
		layoutBranchImpl.setPlid(layoutBranch.getPlid());
		layoutBranchImpl.setName(layoutBranch.getName());
		layoutBranchImpl.setDescription(layoutBranch.getDescription());
		layoutBranchImpl.setMaster(layoutBranch.isMaster());

		return layoutBranchImpl;
	}

	/**
	 * Returns the layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch
	 * @throws com.liferay.portal.NoSuchModelException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutBranchException} if it could not be found.
	 *
	 * @param LayoutBranchId the primary key of the layout branch
	 * @return the layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByPrimaryKey(long LayoutBranchId)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = fetchByPrimaryKey(LayoutBranchId);

		if (layoutBranch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + LayoutBranchId);
			}

			throw new NoSuchLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				LayoutBranchId);
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutBranch fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param LayoutBranchId the primary key of the layout branch
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByPrimaryKey(long LayoutBranchId)
		throws SystemException {
		LayoutBranch layoutBranch = (LayoutBranch)EntityCacheUtil.getResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutBranchImpl.class, LayoutBranchId, this);

		if (layoutBranch == _nullLayoutBranch) {
			return null;
		}

		if (layoutBranch == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
						Long.valueOf(LayoutBranchId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (layoutBranch != null) {
					cacheResult(layoutBranch);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						LayoutBranchImpl.class, LayoutBranchId,
						_nullLayoutBranch);
				}

				closeSession(session);
			}
		}

		return layoutBranch;
	}

	/**
	 * Returns all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId)
		throws SystemException {
		return findByLayoutSetBranchId(layoutSetBranchId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId,
		int start, int end) throws SystemException {
		return findByLayoutSetBranchId(layoutSetBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				layoutSetBranchId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutBranch> list = (List<LayoutBranch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_LAYOUTSETBRANCHID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_LAYOUTSETBRANCHID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_LAYOUTSETBRANCHID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByLayoutSetBranchId_First(long layoutSetBranchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		List<LayoutBranch> list = findByLayoutSetBranchId(layoutSetBranchId, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByLayoutSetBranchId_Last(long layoutSetBranchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		int count = countByLayoutSetBranchId(layoutSetBranchId);

		List<LayoutBranch> list = findByLayoutSetBranchId(layoutSetBranchId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param LayoutBranchId the primary key of the current layout branch
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch[] findByLayoutSetBranchId_PrevAndNext(
		long LayoutBranchId, long layoutSetBranchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = findByPrimaryKey(LayoutBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutBranch[] array = new LayoutBranchImpl[3];

			array[0] = getByLayoutSetBranchId_PrevAndNext(session,
					layoutBranch, layoutSetBranchId, orderByComparator, true);

			array[1] = layoutBranch;

			array[2] = getByLayoutSetBranchId_PrevAndNext(session,
					layoutBranch, layoutSetBranchId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutBranch getByLayoutSetBranchId_PrevAndNext(Session session,
		LayoutBranch layoutBranch, long layoutSetBranchId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid)
		throws SystemException {
		return findByL_P(layoutSetBranchId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid,
		int start, int end) throws SystemException {
		return findByL_P(layoutSetBranchId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				layoutSetBranchId, plid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutBranch> list = (List<LayoutBranch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_L_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_L_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_L_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByL_P_First(long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		List<LayoutBranch> list = findByL_P(layoutSetBranchId, plid, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByL_P_Last(long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		int count = countByL_P(layoutSetBranchId, plid);

		List<LayoutBranch> list = findByL_P(layoutSetBranchId, plid, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param LayoutBranchId the primary key of the current layout branch
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch[] findByL_P_PrevAndNext(long LayoutBranchId,
		long layoutSetBranchId, long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = findByPrimaryKey(LayoutBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutBranch[] array = new LayoutBranchImpl[3];

			array[0] = getByL_P_PrevAndNext(session, layoutBranch,
					layoutSetBranchId, plid, orderByComparator, true);

			array[1] = layoutBranch;

			array[2] = getByL_P_PrevAndNext(session, layoutBranch,
					layoutSetBranchId, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutBranch getByL_P_PrevAndNext(Session session,
		LayoutBranch layoutBranch, long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_PLID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutBranchException} if it could not be found.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByL_P_M(long layoutSetBranchId, long plid,
		boolean master) throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = fetchByL_P_M(layoutSetBranchId, plid, master);

		if (layoutBranch == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", master=");
			msg.append(master);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutBranchException(msg.toString());
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByL_P_M(long layoutSetBranchId, long plid,
		boolean master) throws SystemException {
		return fetchByL_P_M(layoutSetBranchId, plid, master, true);
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByL_P_M(long layoutSetBranchId, long plid,
		boolean master, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, plid, master };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_L_P_M,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_M_PLID_2);

			query.append(_FINDER_COLUMN_L_P_M_MASTER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(master);

				List<LayoutBranch> list = q.list();

				result = list;

				LayoutBranch layoutBranch = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_P_M,
						finderArgs, list);
				}
				else {
					layoutBranch = list.get(0);

					cacheResult(layoutBranch);

					if ((layoutBranch.getLayoutSetBranchId() != layoutSetBranchId) ||
							(layoutBranch.getPlid() != plid) ||
							(layoutBranch.getMaster() != master)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_P_M,
							finderArgs, layoutBranch);
					}
				}

				return layoutBranch;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_P_M,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (LayoutBranch)result;
			}
		}
	}

	/**
	 * Returns all the layout branchs.
	 *
	 * @return the layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutBranch> list = (List<LayoutBranch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTBRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTBRANCH;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout branchs where layoutSetBranchId = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLayoutSetBranchId(long layoutSetBranchId)
		throws SystemException {
		for (LayoutBranch layoutBranch : findByLayoutSetBranchId(
				layoutSetBranchId)) {
			layoutBranchPersistence.remove(layoutBranch);
		}
	}

	/**
	 * Removes all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_P(long layoutSetBranchId, long plid)
		throws SystemException {
		for (LayoutBranch layoutBranch : findByL_P(layoutSetBranchId, plid)) {
			layoutBranchPersistence.remove(layoutBranch);
		}
	}

	/**
	 * Removes the layout branch where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_P_M(long layoutSetBranchId, long plid, boolean master)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = findByL_P_M(layoutSetBranchId, plid, master);

		layoutBranchPersistence.remove(layoutBranch);
	}

	/**
	 * Removes all the layout branchs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LayoutBranch layoutBranch : findAll()) {
			layoutBranchPersistence.remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the number of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLayoutSetBranchId(long layoutSetBranchId)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_P(long layoutSetBranchId, long plid)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the number of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_P_M(long layoutSetBranchId, long plid, boolean master)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, plid, master };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_P_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_M_PLID_2);

			query.append(_FINDER_COLUMN_L_P_M_MASTER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(master);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_P_M,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout branchs.
	 *
	 * @return the number of layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTBRANCH);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the layout branch persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutBranch")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutBranch>> listenersList = new ArrayList<ModelListener<LayoutBranch>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutBranch>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutBranchImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_LAYOUTBRANCH = "SELECT layoutBranch FROM LayoutBranch layoutBranch";
	private static final String _SQL_SELECT_LAYOUTBRANCH_WHERE = "SELECT layoutBranch FROM LayoutBranch layoutBranch WHERE ";
	private static final String _SQL_COUNT_LAYOUTBRANCH = "SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch";
	private static final String _SQL_COUNT_LAYOUTBRANCH_WHERE = "SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch WHERE ";
	private static final String _FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2 =
		"layoutBranch.layoutSetBranchId = ?";
	private static final String _FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2 = "layoutBranch.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_PLID_2 = "layoutBranch.plid = ?";
	private static final String _FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2 = "layoutBranch.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_M_PLID_2 = "layoutBranch.plid = ? AND ";
	private static final String _FINDER_COLUMN_L_P_M_MASTER_2 = "layoutBranch.master = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutBranch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutBranch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutBranch exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(LayoutBranchPersistenceImpl.class);
	private static LayoutBranch _nullLayoutBranch = new LayoutBranchImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LayoutBranch> toCacheModel() {
				return _nullLayoutBranchCacheModel;
			}
		};

	private static CacheModel<LayoutBranch> _nullLayoutBranchCacheModel = new CacheModel<LayoutBranch>() {
			public LayoutBranch toEntityModel() {
				return _nullLayoutBranch;
			}
		};
}