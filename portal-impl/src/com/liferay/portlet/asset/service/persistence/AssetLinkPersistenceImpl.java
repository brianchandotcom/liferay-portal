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

package com.liferay.portlet.asset.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.impl.AssetLinkImpl;
import com.liferay.portlet.asset.model.impl.AssetLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the asset link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkPersistence
 * @see AssetLinkUtil
 * @generated
 */
public class AssetLinkPersistenceImpl extends BasePersistenceImpl<AssetLink>
	implements AssetLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetLinkUtil} to access the asset link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE1",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE1",
			new String[] { Long.class.getName() },
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE1",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE2",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE2",
			new String[] { Long.class.getName() },
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE2",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE_E",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_E",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE1_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE2_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK |
			AssetLinkModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK |
			AssetLinkModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the asset link in the entity cache if it is enabled.
	 *
	 * @param assetLink the asset link
	 */
	public void cacheResult(AssetLink assetLink) {
		EntityCacheUtil.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey(), assetLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_E_T,
			new Object[] {
				Long.valueOf(assetLink.getEntryId1()),
				Long.valueOf(assetLink.getEntryId2()),
				Integer.valueOf(assetLink.getType())
			}, assetLink);

		assetLink.resetOriginalValues();
	}

	/**
	 * Caches the asset links in the entity cache if it is enabled.
	 *
	 * @param assetLinks the asset links
	 */
	public void cacheResult(List<AssetLink> assetLinks) {
		for (AssetLink assetLink : assetLinks) {
			if (EntityCacheUtil.getResult(
						AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
						AssetLinkImpl.class, assetLink.getPrimaryKey()) == null) {
				cacheResult(assetLink);
			}
			else {
				assetLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetLinkImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetLinkImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetLink assetLink) {
		EntityCacheUtil.removeResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetLink);
	}

	@Override
	public void clearCache(List<AssetLink> assetLinks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetLink assetLink : assetLinks) {
			EntityCacheUtil.removeResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
				AssetLinkImpl.class, assetLink.getPrimaryKey());

			clearUniqueFindersCache(assetLink);
		}
	}

	protected void clearUniqueFindersCache(AssetLink assetLink) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_E_T,
			new Object[] {
				Long.valueOf(assetLink.getEntryId1()),
				Long.valueOf(assetLink.getEntryId2()),
				Integer.valueOf(assetLink.getType())
			});
	}

	/**
	 * Creates a new asset link with the primary key. Does not add the asset link to the database.
	 *
	 * @param linkId the primary key for the new asset link
	 * @return the new asset link
	 */
	public AssetLink create(long linkId) {
		AssetLink assetLink = new AssetLinkImpl();

		assetLink.setNew(true);
		assetLink.setPrimaryKey(linkId);

		return assetLink;
	}

	/**
	 * Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link that was removed
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink remove(long linkId)
		throws NoSuchLinkException, SystemException {
		return remove(Long.valueOf(linkId));
	}

	/**
	 * Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset link
	 * @return the asset link that was removed
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetLink remove(Serializable primaryKey)
		throws NoSuchLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetLink assetLink = (AssetLink)session.get(AssetLinkImpl.class,
					primaryKey);

			if (assetLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetLink);
		}
		catch (NoSuchLinkException nsee) {
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
	protected AssetLink removeImpl(AssetLink assetLink)
		throws SystemException {
		assetLink = toUnwrappedModel(assetLink);

		Session session = null;

		try {
			session = openSession();

			if (assetLink.isCachedModel()) {
				assetLink = (AssetLink)session.get(AssetLinkImpl.class,
						assetLink.getPrimaryKeyObj());
			}

			if (assetLink != null) {
				session.delete(assetLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetLink != null) {
			clearCache(assetLink);
		}

		return assetLink;
	}

	@Override
	public AssetLink updateImpl(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws SystemException {
		assetLink = toUnwrappedModel(assetLink);

		boolean isNew = assetLink.isNew();

		AssetLinkModelImpl assetLinkModelImpl = (AssetLinkModelImpl)assetLink;

		Session session = null;

		try {
			session = openSession();

			if (assetLink.isNew()) {
				session.save(assetLink);

				assetLink.setNew(false);
			}
			else {
				session.merge(assetLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId1())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E1, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1,
					args);

				args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getEntryId1())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E1, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E2, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2,
					args);

				args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getEntryId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E2, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId1()),
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E_E, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E,
					args);

				args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getEntryId1()),
						Long.valueOf(assetLinkModelImpl.getEntryId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E_E, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId1()),
						Integer.valueOf(assetLinkModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E1_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T,
					args);

				args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getEntryId1()),
						Integer.valueOf(assetLinkModelImpl.getType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E1_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId2()),
						Integer.valueOf(assetLinkModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E2_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T,
					args);

				args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getEntryId2()),
						Integer.valueOf(assetLinkModelImpl.getType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E2_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey(), assetLink);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_E_T,
				new Object[] {
					Long.valueOf(assetLink.getEntryId1()),
					Long.valueOf(assetLink.getEntryId2()),
					Integer.valueOf(assetLink.getType())
				}, assetLink);
		}
		else {
			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_E_E_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId1()),
						Long.valueOf(assetLinkModelImpl.getOriginalEntryId2()),
						Integer.valueOf(assetLinkModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_E_E_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_E_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_E_T,
					new Object[] {
						Long.valueOf(assetLink.getEntryId1()),
						Long.valueOf(assetLink.getEntryId2()),
						Integer.valueOf(assetLink.getType())
					}, assetLink);
			}
		}

		return assetLink;
	}

	protected AssetLink toUnwrappedModel(AssetLink assetLink) {
		if (assetLink instanceof AssetLinkImpl) {
			return assetLink;
		}

		AssetLinkImpl assetLinkImpl = new AssetLinkImpl();

		assetLinkImpl.setNew(assetLink.isNew());
		assetLinkImpl.setPrimaryKey(assetLink.getPrimaryKey());

		assetLinkImpl.setLinkId(assetLink.getLinkId());
		assetLinkImpl.setCompanyId(assetLink.getCompanyId());
		assetLinkImpl.setUserId(assetLink.getUserId());
		assetLinkImpl.setUserName(assetLink.getUserName());
		assetLinkImpl.setCreateDate(assetLink.getCreateDate());
		assetLinkImpl.setEntryId1(assetLink.getEntryId1());
		assetLinkImpl.setEntryId2(assetLink.getEntryId2());
		assetLinkImpl.setType(assetLink.getType());
		assetLinkImpl.setWeight(assetLink.getWeight());

		return assetLinkImpl;
	}

	/**
	 * Returns the asset link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset link
	 * @return the asset link
	 * @throws com.liferay.portal.NoSuchModelException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset link with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchLinkException} if it could not be found.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByPrimaryKey(long linkId)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByPrimaryKey(linkId);

		if (assetLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + linkId);
			}

			throw new NoSuchLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				linkId);
		}

		return assetLink;
	}

	/**
	 * Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset link
	 * @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByPrimaryKey(long linkId) throws SystemException {
		AssetLink assetLink = (AssetLink)EntityCacheUtil.getResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
				AssetLinkImpl.class, linkId);

		if (assetLink == _nullAssetLink) {
			return null;
		}

		if (assetLink == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				assetLink = (AssetLink)session.get(AssetLinkImpl.class,
						Long.valueOf(linkId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (assetLink != null) {
					cacheResult(assetLink);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
						AssetLinkImpl.class, linkId, _nullAssetLink);
				}

				closeSession(session);
			}
		}

		return assetLink;
	}

	/**
	 * Returns all the asset links where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @return the matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE1(long entryId1) throws SystemException {
		return findByE1(entryId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE1(long entryId1, int start, int end)
		throws SystemException {
		return findByE1(entryId1, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE1(long entryId1, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1;
			finderArgs = new Object[] { entryId1 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E1;
			finderArgs = new Object[] { entryId1, start, end, orderByComparator };
		}

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetLink assetLink : list) {
				if ((entryId1 != assetLink.getEntryId1())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE1_First(long entryId1,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE1_First(entryId1, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE1_First(long entryId1,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetLink> list = findByE1(entryId1, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE1_Last(long entryId1,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE1_Last(entryId1, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE1_Last(long entryId1,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByE1(entryId1);

		List<AssetLink> list = findByE1(entryId1, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink[] findByE1_PrevAndNext(long linkId, long entryId1,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE1_PrevAndNext(session, assetLink, entryId1,
					orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE1_PrevAndNext(session, assetLink, entryId1,
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

	protected AssetLink getByE1_PrevAndNext(Session session,
		AssetLink assetLink, long entryId1,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId1);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset links where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @return the matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE2(long entryId2) throws SystemException {
		return findByE2(entryId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE2(long entryId2, int start, int end)
		throws SystemException {
		return findByE2(entryId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE2(long entryId2, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2;
			finderArgs = new Object[] { entryId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E2;
			finderArgs = new Object[] { entryId2, start, end, orderByComparator };
		}

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetLink assetLink : list) {
				if ((entryId2 != assetLink.getEntryId2())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE2_First(long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE2_First(entryId2, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE2_First(long entryId2,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetLink> list = findByE2(entryId2, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE2_Last(long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE2_Last(entryId2, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE2_Last(long entryId2,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByE2(entryId2);

		List<AssetLink> list = findByE2(entryId2, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink[] findByE2_PrevAndNext(long linkId, long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE2_PrevAndNext(session, assetLink, entryId2,
					orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE2_PrevAndNext(session, assetLink, entryId2,
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

	protected AssetLink getByE2_PrevAndNext(Session session,
		AssetLink assetLink, long entryId2,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @return the matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE_E(long entryId1, long entryId2)
		throws SystemException {
		return findByE_E(entryId1, entryId2, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end) throws SystemException {
		return findByE_E(entryId1, entryId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E;
			finderArgs = new Object[] { entryId1, entryId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E_E;
			finderArgs = new Object[] {
					entryId1, entryId2,
					
					start, end, orderByComparator
				};
		}

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetLink assetLink : list) {
				if ((entryId1 != assetLink.getEntryId1()) ||
						(entryId2 != assetLink.getEntryId2())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE_E_First(long entryId1, long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE_E_First(entryId1, entryId2,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE_E_First(long entryId1, long entryId2,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetLink> list = findByE_E(entryId1, entryId2, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE_E_Last(long entryId1, long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE_E_Last(entryId1, entryId2,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE_E_Last(long entryId1, long entryId2,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByE_E(entryId1, entryId2);

		List<AssetLink> list = findByE_E(entryId1, entryId2, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink[] findByE_E_PrevAndNext(long linkId, long entryId1,
		long entryId2, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE_E_PrevAndNext(session, assetLink, entryId1,
					entryId2, orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE_E_PrevAndNext(session, assetLink, entryId1,
					entryId2, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetLink getByE_E_PrevAndNext(Session session,
		AssetLink assetLink, long entryId1, long entryId2,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

		query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId1);

		qPos.add(entryId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @return the matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE1_T(long entryId1, int type)
		throws SystemException {
		return findByE1_T(entryId1, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end) throws SystemException {
		return findByE1_T(entryId1, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T;
			finderArgs = new Object[] { entryId1, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E1_T;
			finderArgs = new Object[] {
					entryId1, type,
					
					start, end, orderByComparator
				};
		}

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetLink assetLink : list) {
				if ((entryId1 != assetLink.getEntryId1()) ||
						(type != assetLink.getType())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E1_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE1_T_First(long entryId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE1_T_First(entryId1, type,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE1_T_First(long entryId1, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetLink> list = findByE1_T(entryId1, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE1_T_Last(long entryId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE1_T_Last(entryId1, type, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE1_T_Last(long entryId1, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByE1_T(entryId1, type);

		List<AssetLink> list = findByE1_T(entryId1, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink[] findByE1_T_PrevAndNext(long linkId, long entryId1,
		int type, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE1_T_PrevAndNext(session, assetLink, entryId1,
					type, orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE1_T_PrevAndNext(session, assetLink, entryId1,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetLink getByE1_T_PrevAndNext(Session session,
		AssetLink assetLink, long entryId1, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

		query.append(_FINDER_COLUMN_E1_T_TYPE_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId1);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE2_T(long entryId2, int type)
		throws SystemException {
		return findByE2_T(entryId2, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end) throws SystemException {
		return findByE2_T(entryId2, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T;
			finderArgs = new Object[] { entryId2, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E2_T;
			finderArgs = new Object[] {
					entryId2, type,
					
					start, end, orderByComparator
				};
		}

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetLink assetLink : list) {
				if ((entryId2 != assetLink.getEntryId2()) ||
						(type != assetLink.getType())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E2_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE2_T_First(long entryId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE2_T_First(entryId2, type,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE2_T_First(long entryId2, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetLink> list = findByE2_T(entryId2, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE2_T_Last(long entryId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE2_T_Last(entryId2, type, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE2_T_Last(long entryId2, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByE2_T(entryId2, type);

		List<AssetLink> list = findByE2_T(entryId2, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink[] findByE2_T_PrevAndNext(long linkId, long entryId2,
		int type, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE2_T_PrevAndNext(session, assetLink, entryId2,
					type, orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE2_T_PrevAndNext(session, assetLink, entryId2,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetLink getByE2_T_PrevAndNext(Session session,
		AssetLink assetLink, long entryId2, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

		query.append(_FINDER_COLUMN_E2_T_TYPE_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId2);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchLinkException} if it could not be found.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset link
	 * @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink findByE_E_T(long entryId1, long entryId2, int type)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByE_E_T(entryId1, entryId2, type);

		if (assetLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", entryId2=");
			msg.append(entryId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLinkException(msg.toString());
		}

		return assetLink;
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE_E_T(long entryId1, long entryId2, int type)
		throws SystemException {
		return fetchByE_E_T(entryId1, entryId2, type, true);
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink fetchByE_E_T(long entryId1, long entryId2, int type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { entryId1, entryId2, type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_E_E_T,
					finderArgs, this);
		}

		if (result instanceof AssetLink) {
			AssetLink assetLink = (AssetLink)result;

			if ((entryId1 != assetLink.getEntryId1()) ||
					(entryId2 != assetLink.getEntryId2()) ||
					(type != assetLink.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				List<AssetLink> list = q.list();

				result = list;

				AssetLink assetLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_E_T,
						finderArgs, list);
				}
				else {
					assetLink = list.get(0);

					cacheResult(assetLink);

					if ((assetLink.getEntryId1() != entryId1) ||
							(assetLink.getEntryId2() != entryId2) ||
							(assetLink.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_E_T,
							finderArgs, assetLink);
					}
				}

				return assetLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_E_T,
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
				return (AssetLink)result;
			}
		}
	}

	/**
	 * Returns all the asset links.
	 *
	 * @return the asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset links
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> findAll(int start, int end,
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

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLINK.concat(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the asset links where entryId1 = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByE1(long entryId1) throws SystemException {
		for (AssetLink assetLink : findByE1(entryId1)) {
			remove(assetLink);
		}
	}

	/**
	 * Removes all the asset links where entryId2 = &#63; from the database.
	 *
	 * @param entryId2 the entry id2
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByE2(long entryId2) throws SystemException {
		for (AssetLink assetLink : findByE2(entryId2)) {
			remove(assetLink);
		}
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; and entryId2 = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByE_E(long entryId1, long entryId2)
		throws SystemException {
		for (AssetLink assetLink : findByE_E(entryId1, entryId2)) {
			remove(assetLink);
		}
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByE1_T(long entryId1, int type) throws SystemException {
		for (AssetLink assetLink : findByE1_T(entryId1, type)) {
			remove(assetLink);
		}
	}

	/**
	 * Removes all the asset links where entryId2 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByE2_T(long entryId2, int type) throws SystemException {
		for (AssetLink assetLink : findByE2_T(entryId2, type)) {
			remove(assetLink);
		}
	}

	/**
	 * Removes the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the asset link that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink removeByE_E_T(long entryId1, long entryId2, int type)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByE_E_T(entryId1, entryId2, type);

		return remove(assetLink);
	}

	/**
	 * Removes all the asset links from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetLink assetLink : findAll()) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @return the number of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByE1(long entryId1) throws SystemException {
		Object[] finderArgs = new Object[] { entryId1 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E1,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E1, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset links where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @return the number of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByE2(long entryId2) throws SystemException {
		Object[] finderArgs = new Object[] { entryId2 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E2,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E2, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @return the number of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByE_E(long entryId1, long entryId2)
		throws SystemException {
		Object[] finderArgs = new Object[] { entryId1, entryId2 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E_E, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @return the number of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByE1_T(long entryId1, int type) throws SystemException {
		Object[] finderArgs = new Object[] { entryId1, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E1_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E1_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E1_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the number of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByE2_T(long entryId2, int type) throws SystemException {
		Object[] finderArgs = new Object[] { entryId2, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E2_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E2_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the number of matching asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByE_E_T(long entryId1, long entryId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { entryId1, entryId2, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E_E_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E_E_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset links.
	 *
	 * @return the number of asset links
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETLINK);

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
	 * Initializes the asset link persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetLink>> listenersList = new ArrayList<ModelListener<AssetLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetLink>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AssetLinkImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETLINK = "SELECT assetLink FROM AssetLink assetLink";
	private static final String _SQL_SELECT_ASSETLINK_WHERE = "SELECT assetLink FROM AssetLink assetLink WHERE ";
	private static final String _SQL_COUNT_ASSETLINK = "SELECT COUNT(assetLink) FROM AssetLink assetLink";
	private static final String _SQL_COUNT_ASSETLINK_WHERE = "SELECT COUNT(assetLink) FROM AssetLink assetLink WHERE ";
	private static final String _FINDER_COLUMN_E1_ENTRYID1_2 = "assetLink.entryId1 = ?";
	private static final String _FINDER_COLUMN_E2_ENTRYID2_2 = "assetLink.entryId2 = ?";
	private static final String _FINDER_COLUMN_E_E_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_ENTRYID2_2 = "assetLink.entryId2 = ?";
	private static final String _FINDER_COLUMN_E1_T_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E1_T_TYPE_2 = "assetLink.type = ?";
	private static final String _FINDER_COLUMN_E2_T_ENTRYID2_2 = "assetLink.entryId2 = ? AND ";
	private static final String _FINDER_COLUMN_E2_T_TYPE_2 = "assetLink.type = ?";
	private static final String _FINDER_COLUMN_E_E_T_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_T_ENTRYID2_2 = "assetLink.entryId2 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_T_TYPE_2 = "assetLink.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetLink exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetLinkPersistenceImpl.class);
	private static AssetLink _nullAssetLink = new AssetLinkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetLink> toCacheModel() {
				return _nullAssetLinkCacheModel;
			}
		};

	private static CacheModel<AssetLink> _nullAssetLinkCacheModel = new CacheModel<AssetLink>() {
			public AssetLink toEntityModel() {
				return _nullAssetLink;
			}
		};
}