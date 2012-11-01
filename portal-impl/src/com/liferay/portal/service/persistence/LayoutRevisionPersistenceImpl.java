/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutRevisionException;
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
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutRevisionImpl;
import com.liferay.portal.model.impl.LayoutRevisionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the layout revision service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistence
 * @see LayoutRevisionUtil
 * @generated
 */
public class LayoutRevisionPersistenceImpl extends BasePersistenceImpl<LayoutRevision>
	implements LayoutRevisionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutRevisionUtil} to access the layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutRevisionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETBRANCHID =
		new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID =
		new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetBranchId", new String[] { Long.class.getName() },
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetBranchId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPlid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] { Long.class.getName() },
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_H = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_H = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_H",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_H = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_H",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_H_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByH_P",
			new String[] {
				Boolean.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_H_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByH_P",
			new String[] { Boolean.class.getName(), Long.class.getName() },
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_H_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByH_P",
			new String[] { Boolean.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_NOTS = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_P_NOTS = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByP_NotS",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_L_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_L_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.LAYOUTBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_L_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PARENTLAYOUTREVISIONID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_L_H_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByL_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_H_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the layout revision in the entity cache if it is enabled.
	 *
	 * @param layoutRevision the layout revision
	 */
	public void cacheResult(LayoutRevision layoutRevision) {
		EntityCacheUtil.putResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey(),
			layoutRevision);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_H_P,
			new Object[] {
				Long.valueOf(layoutRevision.getLayoutSetBranchId()),
				Boolean.valueOf(layoutRevision.getHead()),
				Long.valueOf(layoutRevision.getPlid())
			}, layoutRevision);

		layoutRevision.resetOriginalValues();
	}

	/**
	 * Caches the layout revisions in the entity cache if it is enabled.
	 *
	 * @param layoutRevisions the layout revisions
	 */
	public void cacheResult(List<LayoutRevision> layoutRevisions) {
		for (LayoutRevision layoutRevision : layoutRevisions) {
			if (EntityCacheUtil.getResult(
						LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutRevisionImpl.class, layoutRevision.getPrimaryKey()) == null) {
				cacheResult(layoutRevision);
			}
			else {
				layoutRevision.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout revisions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LayoutRevisionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LayoutRevisionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout revision.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutRevision layoutRevision) {
		EntityCacheUtil.removeResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(layoutRevision);
	}

	@Override
	public void clearCache(List<LayoutRevision> layoutRevisions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			EntityCacheUtil.removeResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutRevisionImpl.class, layoutRevision.getPrimaryKey());

			clearUniqueFindersCache(layoutRevision);
		}
	}

	protected void clearUniqueFindersCache(LayoutRevision layoutRevision) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_H_P,
			new Object[] {
				Long.valueOf(layoutRevision.getLayoutSetBranchId()),
				Boolean.valueOf(layoutRevision.getHead()),
				Long.valueOf(layoutRevision.getPlid())
			});
	}

	/**
	 * Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	 *
	 * @param layoutRevisionId the primary key for the new layout revision
	 * @return the new layout revision
	 */
	public LayoutRevision create(long layoutRevisionId) {
		LayoutRevision layoutRevision = new LayoutRevisionImpl();

		layoutRevision.setNew(true);
		layoutRevision.setPrimaryKey(layoutRevisionId);

		return layoutRevision;
	}

	/**
	 * Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutRevisionId the primary key of the layout revision
	 * @return the layout revision that was removed
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision remove(long layoutRevisionId)
		throws NoSuchLayoutRevisionException, SystemException {
		return remove(Long.valueOf(layoutRevisionId));
	}

	/**
	 * Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout revision
	 * @return the layout revision that was removed
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutRevision remove(Serializable primaryKey)
		throws NoSuchLayoutRevisionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutRevision layoutRevision = (LayoutRevision)session.get(LayoutRevisionImpl.class,
					primaryKey);

			if (layoutRevision == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutRevision);
		}
		catch (NoSuchLayoutRevisionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LayoutRevision removeImpl(LayoutRevision layoutRevision)
		throws SystemException {
		layoutRevision = toUnwrappedModel(layoutRevision);

		Session session = null;

		try {
			session = openSession();

			if (layoutRevision.isCachedModel()) {
				layoutRevision = (LayoutRevision)session.get(LayoutRevisionImpl.class,
						layoutRevision.getPrimaryKeyObj());
			}

			if (layoutRevision != null) {
				session.delete(layoutRevision);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutRevision != null) {
			clearCache(layoutRevision);
		}

		return layoutRevision;
	}

	@Override
	public LayoutRevision updateImpl(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws SystemException {
		layoutRevision = toUnwrappedModel(layoutRevision);

		boolean isNew = layoutRevision.isNew();

		LayoutRevisionModelImpl layoutRevisionModelImpl = (LayoutRevisionModelImpl)layoutRevision;

		Session session = null;

		try {
			session = openSession();

			if (layoutRevision.isNew()) {
				session.save(layoutRevision);

				layoutRevision.setNew(false);
			}
			else {
				session.merge(layoutRevision);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LayoutRevisionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Boolean.valueOf(layoutRevisionModelImpl.getOriginalHead())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_H, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_H,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId()),
						Boolean.valueOf(layoutRevisionModelImpl.getHead())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_H, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_H,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Integer.valueOf(layoutRevisionModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_S,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId()),
						Integer.valueOf(layoutRevisionModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_S,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_H_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Boolean.valueOf(layoutRevisionModelImpl.getOriginalHead()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_H_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_H_P,
					args);

				args = new Object[] {
						Boolean.valueOf(layoutRevisionModelImpl.getHead()),
						Long.valueOf(layoutRevisionModelImpl.getPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_H_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_H_P,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_L_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_L_P,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getLayoutBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_L_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_L_P,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalParentLayoutRevisionId()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_P,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getParentLayoutRevisionId()),
						Long.valueOf(layoutRevisionModelImpl.getPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_P,
					args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid()),
						Integer.valueOf(layoutRevisionModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_S,
					args);

				args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getLayoutSetBranchId()),
						Long.valueOf(layoutRevisionModelImpl.getPlid()),
						Integer.valueOf(layoutRevisionModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_S,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey(),
			layoutRevision);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_H_P,
				new Object[] {
					Long.valueOf(layoutRevision.getLayoutSetBranchId()),
					Boolean.valueOf(layoutRevision.getHead()),
					Long.valueOf(layoutRevision.getPlid())
				}, layoutRevision);
		}
		else {
			if ((layoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_L_H_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutRevisionModelImpl.getOriginalLayoutSetBranchId()),
						Boolean.valueOf(layoutRevisionModelImpl.getOriginalHead()),
						Long.valueOf(layoutRevisionModelImpl.getOriginalPlid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_L_H_P, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_H_P, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_H_P,
					new Object[] {
						Long.valueOf(layoutRevision.getLayoutSetBranchId()),
						Boolean.valueOf(layoutRevision.getHead()),
						Long.valueOf(layoutRevision.getPlid())
					}, layoutRevision);
			}
		}

		return layoutRevision;
	}

	protected LayoutRevision toUnwrappedModel(LayoutRevision layoutRevision) {
		if (layoutRevision instanceof LayoutRevisionImpl) {
			return layoutRevision;
		}

		LayoutRevisionImpl layoutRevisionImpl = new LayoutRevisionImpl();

		layoutRevisionImpl.setNew(layoutRevision.isNew());
		layoutRevisionImpl.setPrimaryKey(layoutRevision.getPrimaryKey());

		layoutRevisionImpl.setLayoutRevisionId(layoutRevision.getLayoutRevisionId());
		layoutRevisionImpl.setGroupId(layoutRevision.getGroupId());
		layoutRevisionImpl.setCompanyId(layoutRevision.getCompanyId());
		layoutRevisionImpl.setUserId(layoutRevision.getUserId());
		layoutRevisionImpl.setUserName(layoutRevision.getUserName());
		layoutRevisionImpl.setCreateDate(layoutRevision.getCreateDate());
		layoutRevisionImpl.setModifiedDate(layoutRevision.getModifiedDate());
		layoutRevisionImpl.setLayoutSetBranchId(layoutRevision.getLayoutSetBranchId());
		layoutRevisionImpl.setLayoutBranchId(layoutRevision.getLayoutBranchId());
		layoutRevisionImpl.setParentLayoutRevisionId(layoutRevision.getParentLayoutRevisionId());
		layoutRevisionImpl.setHead(layoutRevision.isHead());
		layoutRevisionImpl.setMajor(layoutRevision.isMajor());
		layoutRevisionImpl.setPlid(layoutRevision.getPlid());
		layoutRevisionImpl.setPrivateLayout(layoutRevision.isPrivateLayout());
		layoutRevisionImpl.setName(layoutRevision.getName());
		layoutRevisionImpl.setTitle(layoutRevision.getTitle());
		layoutRevisionImpl.setDescription(layoutRevision.getDescription());
		layoutRevisionImpl.setKeywords(layoutRevision.getKeywords());
		layoutRevisionImpl.setRobots(layoutRevision.getRobots());
		layoutRevisionImpl.setTypeSettings(layoutRevision.getTypeSettings());
		layoutRevisionImpl.setIconImage(layoutRevision.isIconImage());
		layoutRevisionImpl.setIconImageId(layoutRevision.getIconImageId());
		layoutRevisionImpl.setThemeId(layoutRevision.getThemeId());
		layoutRevisionImpl.setColorSchemeId(layoutRevision.getColorSchemeId());
		layoutRevisionImpl.setWapThemeId(layoutRevision.getWapThemeId());
		layoutRevisionImpl.setWapColorSchemeId(layoutRevision.getWapColorSchemeId());
		layoutRevisionImpl.setCss(layoutRevision.getCss());
		layoutRevisionImpl.setStatus(layoutRevision.getStatus());
		layoutRevisionImpl.setStatusByUserId(layoutRevision.getStatusByUserId());
		layoutRevisionImpl.setStatusByUserName(layoutRevision.getStatusByUserName());
		layoutRevisionImpl.setStatusDate(layoutRevision.getStatusDate());

		return layoutRevisionImpl;
	}

	/**
	 * Returns the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout revision
	 * @return the layout revision
	 * @throws com.liferay.portal.NoSuchModelException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutRevision findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	 *
	 * @param layoutRevisionId the primary key of the layout revision
	 * @return the layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPrimaryKey(long layoutRevisionId)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByPrimaryKey(layoutRevisionId);

		if (layoutRevision == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + layoutRevisionId);
			}

			throw new NoSuchLayoutRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				layoutRevisionId);
		}

		return layoutRevision;
	}

	/**
	 * Returns the layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout revision
	 * @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutRevision fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutRevisionId the primary key of the layout revision
	 * @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByPrimaryKey(long layoutRevisionId)
		throws SystemException {
		LayoutRevision layoutRevision = (LayoutRevision)EntityCacheUtil.getResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutRevisionImpl.class, layoutRevisionId);

		if (layoutRevision == _nullLayoutRevision) {
			return null;
		}

		if (layoutRevision == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				layoutRevision = (LayoutRevision)session.get(LayoutRevisionImpl.class,
						Long.valueOf(layoutRevisionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (layoutRevision != null) {
					cacheResult(layoutRevision);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutRevisionImpl.class, layoutRevisionId,
						_nullLayoutRevision);
				}

				closeSession(session);
			}
		}

		return layoutRevision;
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByLayoutSetBranchId(long layoutSetBranchId)
		throws SystemException {
		return findByLayoutSetBranchId(layoutSetBranchId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) throws SystemException {
		return findByLayoutSetBranchId(layoutSetBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID;
			finderArgs = new Object[] { layoutSetBranchId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETBRANCHID;
			finderArgs = new Object[] {
					layoutSetBranchId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByLayoutSetBranchId_First(
		long layoutSetBranchId, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByLayoutSetBranchId_First(layoutSetBranchId,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByLayoutSetBranchId_First(
		long layoutSetBranchId, OrderByComparator orderByComparator)
		throws SystemException {
		List<LayoutRevision> list = findByLayoutSetBranchId(layoutSetBranchId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByLayoutSetBranchId_Last(long layoutSetBranchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByLayoutSetBranchId_Last(layoutSetBranchId,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByLayoutSetBranchId(layoutSetBranchId);

		List<LayoutRevision> list = findByLayoutSetBranchId(layoutSetBranchId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByLayoutSetBranchId_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByLayoutSetBranchId_PrevAndNext(session,
					layoutRevision, layoutSetBranchId, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByLayoutSetBranchId_PrevAndNext(session,
					layoutRevision, layoutSetBranchId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByLayoutSetBranchId_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByPlid(long plid) throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByPlid(long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID;
			finderArgs = new Object[] { plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID;
			finderArgs = new Object[] { plid, start, end, orderByComparator };
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((plid != layoutRevision.getPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByPlid_First(plid,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByPlid_First(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutRevision> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByPlid_Last(plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByPlid_Last(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPlid(plid);

		List<LayoutRevision> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByPlid_PrevAndNext(long layoutRevisionId,
		long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByPlid_PrevAndNext(session, layoutRevision, plid,
					orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByPlid_PrevAndNext(session, layoutRevision, plid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByPlid_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_H(long layoutSetBranchId, boolean head)
		throws SystemException {
		return findByL_H(layoutSetBranchId, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_H(long layoutSetBranchId, boolean head,
		int start, int end) throws SystemException {
		return findByL_H(layoutSetBranchId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_H(long layoutSetBranchId, boolean head,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_H;
			finderArgs = new Object[] { layoutSetBranchId, head };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_H;
			finderArgs = new Object[] {
					layoutSetBranchId, head,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
						(head != layoutRevision.getHead())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_H_First(long layoutSetBranchId, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_H_First(layoutSetBranchId,
				head, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_H_First(long layoutSetBranchId,
		boolean head, OrderByComparator orderByComparator)
		throws SystemException {
		List<LayoutRevision> list = findByL_H(layoutSetBranchId, head, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_H_Last(long layoutSetBranchId, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_H_Last(layoutSetBranchId,
				head, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_H_Last(long layoutSetBranchId, boolean head,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByL_H(layoutSetBranchId, head);

		List<LayoutRevision> list = findByL_H(layoutSetBranchId, head,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByL_H_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_H_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, head, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_H_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_H_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long layoutSetBranchId, boolean head,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_H_HEAD_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P(long layoutSetBranchId, long plid)
		throws SystemException {
		return findByL_P(layoutSetBranchId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P(long layoutSetBranchId, long plid,
		int start, int end) throws SystemException {
		return findByL_P(layoutSetBranchId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P(long layoutSetBranchId, long plid,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P;
			finderArgs = new Object[] { layoutSetBranchId, plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P;
			finderArgs = new Object[] {
					layoutSetBranchId, plid,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
						(plid != layoutRevision.getPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_P_First(long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_P_First(layoutSetBranchId,
				plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_P_First(long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutRevision> list = findByL_P(layoutSetBranchId, plid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_P_Last(long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_P_Last(layoutSetBranchId,
				plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_P_Last(long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByL_P(layoutSetBranchId, plid);

		List<LayoutRevision> list = findByL_P(layoutSetBranchId, plid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByL_P_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_P_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_P_PrevAndNext(session, layoutRevision,
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

	protected LayoutRevision getByL_P_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long layoutSetBranchId, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_PLID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_S(long layoutSetBranchId, int status)
		throws SystemException {
		return findByL_S(layoutSetBranchId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_S(long layoutSetBranchId, int status,
		int start, int end) throws SystemException {
		return findByL_S(layoutSetBranchId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_S(long layoutSetBranchId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_S;
			finderArgs = new Object[] { layoutSetBranchId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_S;
			finderArgs = new Object[] {
					layoutSetBranchId, status,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
						(status != layoutRevision.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_S_First(long layoutSetBranchId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_S_First(layoutSetBranchId,
				status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_S_First(long layoutSetBranchId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutRevision> list = findByL_S(layoutSetBranchId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_S_Last(long layoutSetBranchId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_S_Last(layoutSetBranchId,
				status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_S_Last(long layoutSetBranchId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByL_S(layoutSetBranchId, status);

		List<LayoutRevision> list = findByL_S(layoutSetBranchId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByL_S_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, int status, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_S_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, status, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_S_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_S_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long layoutSetBranchId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByH_P(boolean head, long plid)
		throws SystemException {
		return findByH_P(head, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByH_P(boolean head, long plid, int start,
		int end) throws SystemException {
		return findByH_P(head, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByH_P(boolean head, long plid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_H_P;
			finderArgs = new Object[] { head, plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_H_P;
			finderArgs = new Object[] { head, plid, start, end, orderByComparator };
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((head != layoutRevision.getHead()) ||
						(plid != layoutRevision.getPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_H_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(head);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByH_P_First(boolean head, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByH_P_First(head, plid,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("head=");
		msg.append(head);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByH_P_First(boolean head, long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutRevision> list = findByH_P(head, plid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByH_P_Last(boolean head, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByH_P_Last(head, plid,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("head=");
		msg.append(head);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByH_P_Last(boolean head, long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByH_P(head, plid);

		List<LayoutRevision> list = findByH_P(head, plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByH_P_PrevAndNext(long layoutRevisionId,
		boolean head, long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByH_P_PrevAndNext(session, layoutRevision, head,
					plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByH_P_PrevAndNext(session, layoutRevision, head,
					plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByH_P_PrevAndNext(Session session,
		LayoutRevision layoutRevision, boolean head, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_H_P_HEAD_2);

		query.append(_FINDER_COLUMN_H_P_PLID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(head);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByP_NotS(long plid, int status)
		throws SystemException {
		return findByP_NotS(plid, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByP_NotS(long plid, int status, int start,
		int end) throws SystemException {
		return findByP_NotS(plid, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByP_NotS(long plid, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_NOTS;
		finderArgs = new Object[] { plid, status, start, end, orderByComparator };

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((plid != layoutRevision.getPlid()) ||
						(status != layoutRevision.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_P_NOTS_PLID_2);

			query.append(_FINDER_COLUMN_P_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByP_NotS_First(long plid, int status,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByP_NotS_First(plid, status,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByP_NotS_First(long plid, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutRevision> list = findByP_NotS(plid, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByP_NotS_Last(long plid, int status,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByP_NotS_Last(plid, status,
				orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByP_NotS_Last(long plid, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByP_NotS(plid, status);

		List<LayoutRevision> list = findByP_NotS(plid, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByP_NotS_PrevAndNext(long layoutRevisionId,
		long plid, int status, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByP_NotS_PrevAndNext(session, layoutRevision, plid,
					status, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByP_NotS_PrevAndNext(session, layoutRevision, plid,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByP_NotS_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long plid, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_P_NOTS_PLID_2);

		query.append(_FINDER_COLUMN_P_NOTS_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid) throws SystemException {
		return findByL_L_P(layoutSetBranchId, layoutBranchId, plid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end)
		throws SystemException {
		return findByL_L_P(layoutSetBranchId, layoutBranchId, plid, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_L_P;
			finderArgs = new Object[] { layoutSetBranchId, layoutBranchId, plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_L_P;
			finderArgs = new Object[] {
					layoutSetBranchId, layoutBranchId, plid,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
						(layoutBranchId != layoutRevision.getLayoutBranchId()) ||
						(plid != layoutRevision.getPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(layoutBranchId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_L_P_First(long layoutSetBranchId,
		long layoutBranchId, long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_L_P_First(layoutSetBranchId,
				layoutBranchId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", layoutBranchId=");
		msg.append(layoutBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_L_P_First(long layoutSetBranchId,
		long layoutBranchId, long plid, OrderByComparator orderByComparator)
		throws SystemException {
		List<LayoutRevision> list = findByL_L_P(layoutSetBranchId,
				layoutBranchId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_L_P_Last(long layoutSetBranchId,
		long layoutBranchId, long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_L_P_Last(layoutSetBranchId,
				layoutBranchId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", layoutBranchId=");
		msg.append(layoutBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_L_P_Last(long layoutSetBranchId,
		long layoutBranchId, long plid, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByL_L_P(layoutSetBranchId, layoutBranchId, plid);

		List<LayoutRevision> list = findByL_L_P(layoutSetBranchId,
				layoutBranchId, plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByL_L_P_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long layoutBranchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_L_P_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, layoutBranchId, plid, orderByComparator,
					true);

			array[1] = layoutRevision;

			array[2] = getByL_L_P_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, layoutBranchId, plid, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_L_P_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long layoutSetBranchId,
		long layoutBranchId, long plid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2);

		query.append(_FINDER_COLUMN_L_L_P_PLID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(layoutBranchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid) throws SystemException {
		return findByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end)
		throws SystemException {
		return findByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_P;
			finderArgs = new Object[] {
					layoutSetBranchId, parentLayoutRevisionId, plid
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P_P;
			finderArgs = new Object[] {
					layoutSetBranchId, parentLayoutRevisionId, plid,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
						(parentLayoutRevisionId != layoutRevision.getParentLayoutRevisionId()) ||
						(plid != layoutRevision.getPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2);

			query.append(_FINDER_COLUMN_L_P_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(parentLayoutRevisionId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_P_P_First(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_P_P_First(layoutSetBranchId,
				parentLayoutRevisionId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", parentLayoutRevisionId=");
		msg.append(parentLayoutRevisionId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_P_P_First(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutRevision> list = findByL_P_P(layoutSetBranchId,
				parentLayoutRevisionId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_P_P_Last(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_P_P_Last(layoutSetBranchId,
				parentLayoutRevisionId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", parentLayoutRevisionId=");
		msg.append(parentLayoutRevisionId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_P_P_Last(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);

		List<LayoutRevision> list = findByL_P_P(layoutSetBranchId,
				parentLayoutRevisionId, plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByL_P_P_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_P_P_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, parentLayoutRevisionId, plid,
					orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_P_P_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, parentLayoutRevisionId, plid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_P_P_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2);

		query.append(_FINDER_COLUMN_L_P_P_PLID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(parentLayoutRevisionId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_H_P(long layoutSetBranchId, boolean head,
		long plid) throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_H_P(layoutSetBranchId, head,
				plid);

		if (layoutRevision == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", head=");
			msg.append(head);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutRevisionException(msg.toString());
		}

		return layoutRevision;
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_H_P(long layoutSetBranchId, boolean head,
		long plid) throws SystemException {
		return fetchByL_H_P(layoutSetBranchId, head, plid, true);
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_H_P(long layoutSetBranchId, boolean head,
		long plid, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, head, plid };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_L_H_P,
					finderArgs, this);
		}

		if (result instanceof LayoutRevision) {
			LayoutRevision layoutRevision = (LayoutRevision)result;

			if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
					(head != layoutRevision.getHead()) ||
					(plid != layoutRevision.getPlid())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_P_PLID_2);

			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				qPos.add(plid);

				List<LayoutRevision> list = q.list();

				result = list;

				LayoutRevision layoutRevision = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_H_P,
						finderArgs, list);
				}
				else {
					layoutRevision = list.get(0);

					cacheResult(layoutRevision);

					if ((layoutRevision.getLayoutSetBranchId() != layoutSetBranchId) ||
							(layoutRevision.getHead() != head) ||
							(layoutRevision.getPlid() != plid)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_L_H_P,
							finderArgs, layoutRevision);
					}
				}

				return layoutRevision;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_L_H_P,
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
				return (LayoutRevision)result;
			}
		}
	}

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P_S(long layoutSetBranchId, long plid,
		int status) throws SystemException {
		return findByL_P_S(layoutSetBranchId, plid, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P_S(long layoutSetBranchId, long plid,
		int status, int start, int end) throws SystemException {
		return findByL_P_S(layoutSetBranchId, plid, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByL_P_S(long layoutSetBranchId, long plid,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_S;
			finderArgs = new Object[] { layoutSetBranchId, plid, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P_S;
			finderArgs = new Object[] {
					layoutSetBranchId, plid, status,
					
					start, end, orderByComparator
				};
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutRevision layoutRevision : list) {
				if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
						(plid != layoutRevision.getPlid()) ||
						(status != layoutRevision.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_S_PLID_2);

			query.append(_FINDER_COLUMN_L_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_P_S_First(long layoutSetBranchId, long plid,
		int status, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_P_S_First(layoutSetBranchId,
				plid, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_P_S_First(long layoutSetBranchId, long plid,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<LayoutRevision> list = findByL_P_S(layoutSetBranchId, plid,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByL_P_S_Last(long layoutSetBranchId, long plid,
		int status, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByL_P_S_Last(layoutSetBranchId,
				plid, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByL_P_S_Last(long layoutSetBranchId, long plid,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByL_P_S(layoutSetBranchId, plid, status);

		List<LayoutRevision> list = findByL_P_S(layoutSetBranchId, plid,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByL_P_S_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long plid, int status,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_P_S_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, plid, status, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_P_S_PrevAndNext(session, layoutRevision,
					layoutSetBranchId, plid, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_P_S_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long layoutSetBranchId, long plid,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_S_PLID_2);

		query.append(_FINDER_COLUMN_L_P_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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

		else {
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout revisions.
	 *
	 * @return the layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTREVISION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTREVISION.concat(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLayoutSetBranchId(long layoutSetBranchId)
		throws SystemException {
		for (LayoutRevision layoutRevision : findByLayoutSetBranchId(
				layoutSetBranchId)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPlid(long plid) throws SystemException {
		for (LayoutRevision layoutRevision : findByPlid(plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_H(long layoutSetBranchId, boolean head)
		throws SystemException {
		for (LayoutRevision layoutRevision : findByL_H(layoutSetBranchId, head)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_P(long layoutSetBranchId, long plid)
		throws SystemException {
		for (LayoutRevision layoutRevision : findByL_P(layoutSetBranchId, plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and status = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_S(long layoutSetBranchId, int status)
		throws SystemException {
		for (LayoutRevision layoutRevision : findByL_S(layoutSetBranchId, status)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where head = &#63; and plid = &#63; from the database.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByH_P(boolean head, long plid) throws SystemException {
		for (LayoutRevision layoutRevision : findByH_P(head, plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where plid = &#63; and status &ne; &#63; from the database.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_NotS(long plid, int status) throws SystemException {
		for (LayoutRevision layoutRevision : findByP_NotS(plid, status)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid) throws SystemException {
		for (LayoutRevision layoutRevision : findByL_L_P(layoutSetBranchId,
				layoutBranchId, plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid) throws SystemException {
		for (LayoutRevision layoutRevision : findByL_P_P(layoutSetBranchId,
				parentLayoutRevisionId, plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the layout revision that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision removeByL_H_P(long layoutSetBranchId, boolean head,
		long plid) throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByL_H_P(layoutSetBranchId, head,
				plid);

		return remove(layoutRevision);
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByL_P_S(long layoutSetBranchId, long plid, int status)
		throws SystemException {
		for (LayoutRevision layoutRevision : findByL_P_S(layoutSetBranchId,
				plid, status)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LayoutRevision layoutRevision : findAll()) {
			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLayoutSetBranchId(long layoutSetBranchId)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Returns the number of layout revisions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPlid(long plid) throws SystemException {
		Object[] finderArgs = new Object[] { plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PLID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PLID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_H(long layoutSetBranchId, boolean head)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, head };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_H,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_H, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_P(long layoutSetBranchId, long plid)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_S(long layoutSetBranchId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByH_P(boolean head, long plid) throws SystemException {
		Object[] finderArgs = new Object[] { head, plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_H_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(head);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_H_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_NotS(long plid, int status) throws SystemException {
		Object[] finderArgs = new Object[] { plid, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_P_NOTS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_P_NOTS_PLID_2);

			query.append(_FINDER_COLUMN_P_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_P_NOTS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid) throws SystemException {
		Object[] finderArgs = new Object[] {
				layoutSetBranchId, layoutBranchId, plid
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_L_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(layoutBranchId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_L_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid) throws SystemException {
		Object[] finderArgs = new Object[] {
				layoutSetBranchId, parentLayoutRevisionId, plid
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_P_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2);

			query.append(_FINDER_COLUMN_L_P_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(parentLayoutRevisionId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_P_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_H_P(long layoutSetBranchId, boolean head, long plid)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, head, plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_H_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_H_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByL_P_S(long layoutSetBranchId, long plid, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { layoutSetBranchId, plid, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_L_P_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_S_PLID_2);

			query.append(_FINDER_COLUMN_L_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_L_P_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout revisions.
	 *
	 * @return the number of layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTREVISION);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the layout revision persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutRevision")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutRevision>> listenersList = new ArrayList<ModelListener<LayoutRevision>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutRevision>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LayoutRevisionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
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
	private static final String _SQL_SELECT_LAYOUTREVISION = "SELECT layoutRevision FROM LayoutRevision layoutRevision";
	private static final String _SQL_SELECT_LAYOUTREVISION_WHERE = "SELECT layoutRevision FROM LayoutRevision layoutRevision WHERE ";
	private static final String _SQL_COUNT_LAYOUTREVISION = "SELECT COUNT(layoutRevision) FROM LayoutRevision layoutRevision";
	private static final String _SQL_COUNT_LAYOUTREVISION_WHERE = "SELECT COUNT(layoutRevision) FROM LayoutRevision layoutRevision WHERE ";
	private static final String _FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ?";
	private static final String _FINDER_COLUMN_PLID_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_H_HEAD_2 = "layoutRevision.head = ?";
	private static final String _FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_S_STATUS_2 = "layoutRevision.status = ?";
	private static final String _FINDER_COLUMN_H_P_HEAD_2 = "layoutRevision.head = ? AND ";
	private static final String _FINDER_COLUMN_H_P_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_P_NOTS_PLID_2 = "layoutRevision.plid = ? AND ";
	private static final String _FINDER_COLUMN_P_NOTS_STATUS_2 = "layoutRevision.status != ?";
	private static final String _FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2 = "layoutRevision.layoutBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_L_P_PLID_2 = "layoutRevision.plid = ? AND layoutRevision.status != 5";
	private static final String _FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2 = "layoutRevision.parentLayoutRevisionId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_P_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_L_H_P_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_H_P_HEAD_2 = "layoutRevision.head = ? AND ";
	private static final String _FINDER_COLUMN_L_H_P_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2 = "layoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_S_PLID_2 = "layoutRevision.plid = ? AND ";
	private static final String _FINDER_COLUMN_L_P_S_STATUS_2 = "layoutRevision.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutRevision.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutRevision exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutRevision exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(LayoutRevisionPersistenceImpl.class);
	private static LayoutRevision _nullLayoutRevision = new LayoutRevisionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LayoutRevision> toCacheModel() {
				return _nullLayoutRevisionCacheModel;
			}
		};

	private static CacheModel<LayoutRevision> _nullLayoutRevisionCacheModel = new CacheModel<LayoutRevision>() {
			public LayoutRevision toEntityModel() {
				return _nullLayoutRevision;
			}
		};
}