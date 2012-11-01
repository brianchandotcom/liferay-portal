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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal feed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedPersistence
 * @see JournalFeedUtil
 * @generated
 */
public class JournalFeedPersistenceImpl extends BasePersistenceImpl<JournalFeed>
	implements JournalFeedPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalFeedUtil} to access the journal feed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalFeedImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			JournalFeedModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalFeedModelImpl.UUID_COLUMN_BITMASK |
			JournalFeedModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalFeedModelImpl.UUID_COLUMN_BITMASK |
			JournalFeedModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			JournalFeedModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalFeedModelImpl.GROUPID_COLUMN_BITMASK |
			JournalFeedModelImpl.FEEDID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, JournalFeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the journal feed in the entity cache if it is enabled.
	 *
	 * @param journalFeed the journal feed
	 */
	public void cacheResult(JournalFeed journalFeed) {
		EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeed.getUuid(), Long.valueOf(journalFeed.getGroupId())
			}, journalFeed);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				Long.valueOf(journalFeed.getGroupId()),
				
			journalFeed.getFeedId()
			}, journalFeed);

		journalFeed.resetOriginalValues();
	}

	/**
	 * Caches the journal feeds in the entity cache if it is enabled.
	 *
	 * @param journalFeeds the journal feeds
	 */
	public void cacheResult(List<JournalFeed> journalFeeds) {
		for (JournalFeed journalFeed : journalFeeds) {
			if (EntityCacheUtil.getResult(
						JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
						JournalFeedImpl.class, journalFeed.getPrimaryKey()) == null) {
				cacheResult(journalFeed);
			}
			else {
				journalFeed.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal feeds.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalFeedImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalFeedImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal feed.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalFeed journalFeed) {
		EntityCacheUtil.removeResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalFeed);
	}

	@Override
	public void clearCache(List<JournalFeed> journalFeeds) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalFeed journalFeed : journalFeeds) {
			EntityCacheUtil.removeResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
				JournalFeedImpl.class, journalFeed.getPrimaryKey());

			clearUniqueFindersCache(journalFeed);
		}
	}

	protected void clearUniqueFindersCache(JournalFeed journalFeed) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeed.getUuid(), Long.valueOf(journalFeed.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				Long.valueOf(journalFeed.getGroupId()),
				
			journalFeed.getFeedId()
			});
	}

	/**
	 * Creates a new journal feed with the primary key. Does not add the journal feed to the database.
	 *
	 * @param id the primary key for the new journal feed
	 * @return the new journal feed
	 */
	public JournalFeed create(long id) {
		JournalFeed journalFeed = new JournalFeedImpl();

		journalFeed.setNew(true);
		journalFeed.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalFeed.setUuid(uuid);

		return journalFeed;
	}

	/**
	 * Removes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed that was removed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed remove(long id)
		throws NoSuchFeedException, SystemException {
		return remove(Long.valueOf(id));
	}

	/**
	 * Removes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal feed
	 * @return the journal feed that was removed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalFeed remove(Serializable primaryKey)
		throws NoSuchFeedException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalFeed journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
					primaryKey);

			if (journalFeed == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFeedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalFeed);
		}
		catch (NoSuchFeedException nsee) {
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
	protected JournalFeed removeImpl(JournalFeed journalFeed)
		throws SystemException {
		journalFeed = toUnwrappedModel(journalFeed);

		Session session = null;

		try {
			session = openSession();

			if (journalFeed.isCachedModel()) {
				journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
						journalFeed.getPrimaryKeyObj());
			}

			if (journalFeed != null) {
				session.delete(journalFeed);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalFeed != null) {
			clearCache(journalFeed);
		}

		return journalFeed;
	}

	@Override
	public JournalFeed updateImpl(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws SystemException {
		journalFeed = toUnwrappedModel(journalFeed);

		boolean isNew = journalFeed.isNew();

		JournalFeedModelImpl journalFeedModelImpl = (JournalFeedModelImpl)journalFeed;

		if (Validator.isNull(journalFeed.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalFeed.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (journalFeed.isNew()) {
				session.save(journalFeed);

				journalFeed.setNew(false);
			}
			else {
				session.merge(journalFeed);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalFeedModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalFeedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalFeedModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { journalFeedModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((journalFeedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalFeedModelImpl.getOriginalUuid(),
						Long.valueOf(journalFeedModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						journalFeedModelImpl.getUuid(),
						Long.valueOf(journalFeedModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((journalFeedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFeedModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(journalFeedModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalFeed.getUuid(),
					Long.valueOf(journalFeed.getGroupId())
				}, journalFeed);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					Long.valueOf(journalFeed.getGroupId()),
					
				journalFeed.getFeedId()
				}, journalFeed);
		}
		else {
			if ((journalFeedModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalFeedModelImpl.getOriginalUuid(),
						Long.valueOf(journalFeedModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						journalFeed.getUuid(),
						Long.valueOf(journalFeed.getGroupId())
					}, journalFeed);
			}

			if ((journalFeedModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFeedModelImpl.getOriginalGroupId()),
						
						journalFeedModelImpl.getOriginalFeedId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
					new Object[] {
						Long.valueOf(journalFeed.getGroupId()),
						
					journalFeed.getFeedId()
					}, journalFeed);
			}
		}

		return journalFeed;
	}

	protected JournalFeed toUnwrappedModel(JournalFeed journalFeed) {
		if (journalFeed instanceof JournalFeedImpl) {
			return journalFeed;
		}

		JournalFeedImpl journalFeedImpl = new JournalFeedImpl();

		journalFeedImpl.setNew(journalFeed.isNew());
		journalFeedImpl.setPrimaryKey(journalFeed.getPrimaryKey());

		journalFeedImpl.setUuid(journalFeed.getUuid());
		journalFeedImpl.setId(journalFeed.getId());
		journalFeedImpl.setGroupId(journalFeed.getGroupId());
		journalFeedImpl.setCompanyId(journalFeed.getCompanyId());
		journalFeedImpl.setUserId(journalFeed.getUserId());
		journalFeedImpl.setUserName(journalFeed.getUserName());
		journalFeedImpl.setCreateDate(journalFeed.getCreateDate());
		journalFeedImpl.setModifiedDate(journalFeed.getModifiedDate());
		journalFeedImpl.setFeedId(journalFeed.getFeedId());
		journalFeedImpl.setName(journalFeed.getName());
		journalFeedImpl.setDescription(journalFeed.getDescription());
		journalFeedImpl.setType(journalFeed.getType());
		journalFeedImpl.setStructureId(journalFeed.getStructureId());
		journalFeedImpl.setTemplateId(journalFeed.getTemplateId());
		journalFeedImpl.setRendererTemplateId(journalFeed.getRendererTemplateId());
		journalFeedImpl.setDelta(journalFeed.getDelta());
		journalFeedImpl.setOrderByCol(journalFeed.getOrderByCol());
		journalFeedImpl.setOrderByType(journalFeed.getOrderByType());
		journalFeedImpl.setTargetLayoutFriendlyUrl(journalFeed.getTargetLayoutFriendlyUrl());
		journalFeedImpl.setTargetPortletId(journalFeed.getTargetPortletId());
		journalFeedImpl.setContentField(journalFeed.getContentField());
		journalFeedImpl.setFeedType(journalFeed.getFeedType());
		journalFeedImpl.setFeedVersion(journalFeed.getFeedVersion());

		return journalFeedImpl;
	}

	/**
	 * Returns the journal feed with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal feed
	 * @return the journal feed
	 * @throws com.liferay.portal.NoSuchModelException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalFeed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal feed with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchFeedException} if it could not be found.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByPrimaryKey(long id)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByPrimaryKey(id);

		if (journalFeed == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchFeedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal feed
	 * @return the journal feed, or <code>null</code> if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalFeed fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed, or <code>null</code> if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByPrimaryKey(long id) throws SystemException {
		JournalFeed journalFeed = (JournalFeed)EntityCacheUtil.getResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
				JournalFeedImpl.class, id);

		if (journalFeed == _nullJournalFeed) {
			return null;
		}

		if (journalFeed == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
						Long.valueOf(id));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (journalFeed != null) {
					cacheResult(journalFeed);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
						JournalFeedImpl.class, id, _nullJournalFeed);
				}

				closeSession(session);
			}
		}

		return journalFeed;
	}

	/**
	 * Returns all the journal feeds where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFeed journalFeed : list) {
				if (!Validator.equals(uuid, journalFeed.getUuid())) {
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

			query.append(_SQL_SELECT_JOURNALFEED_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUuid_First(uuid, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the first journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFeed> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUuid_Last(uuid, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<JournalFeed> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalFeed, uuid,
					orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByUuid_PrevAndNext(session, journalFeed, uuid,
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

	protected JournalFeed getByUuid_PrevAndNext(Session session,
		JournalFeed journalFeed, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFEED_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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
			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFeed);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFeed> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal feed where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFeedException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUUID_G(uuid, groupId);

		if (journalFeed == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFeedException(msg.toString());
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal feed where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof JournalFeed) {
			JournalFeed journalFeed = (JournalFeed)result;

			if (!Validator.equals(uuid, journalFeed.getUuid()) ||
					(groupId != journalFeed.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALFEED_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalFeed> list = q.list();

				result = list;

				JournalFeed journalFeed = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalFeed = list.get(0);

					cacheResult(journalFeed);

					if ((journalFeed.getUuid() == null) ||
							!journalFeed.getUuid().equals(uuid) ||
							(journalFeed.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalFeed);
					}
				}

				return journalFeed;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (JournalFeed)result;
			}
		}
	}

	/**
	 * Returns all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFeed journalFeed : list) {
				if (!Validator.equals(uuid, journalFeed.getUuid()) ||
						(companyId != journalFeed.getCompanyId())) {
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

			query.append(_SQL_SELECT_JOURNALFEED_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the first journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFeed> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<JournalFeed> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed[] findByUuid_C_PrevAndNext(long id, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, journalFeed, uuid,
					companyId, orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByUuid_C_PrevAndNext(session, journalFeed, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed getByUuid_C_PrevAndNext(Session session,
		JournalFeed journalFeed, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFEED_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFeed);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFeed> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal feeds where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFeed journalFeed : list) {
				if ((groupId != journalFeed.getGroupId())) {
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

			query.append(_SQL_SELECT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the first journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFeed> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByGroupId_Last(groupId, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the last journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<JournalFeed> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalFeed, groupId,
					orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByGroupId_PrevAndNext(session, journalFeed, groupId,
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

	protected JournalFeed getByGroupId_PrevAndNext(Session session,
		JournalFeed journalFeed, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFEED_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFeed);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFeed> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal feeds that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFEED_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalFeedModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFeed.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalFeedImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalFeedImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalFeed>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set of journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a journal feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed[] filterFindByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(id, groupId, orderByComparator);
		}

		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, journalFeed,
					groupId, orderByComparator, true);

			array[1] = journalFeed;

			array[2] = filterGetByGroupId_PrevAndNext(session, journalFeed,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed filterGetByGroupId_PrevAndNext(Session session,
		JournalFeed journalFeed, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFEED_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalFeedModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFeed.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalFeedImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalFeedImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFeed);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFeed> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal feed where groupId = &#63; and feedId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFeedException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the matching journal feed
	 * @throws com.liferay.portlet.journal.NoSuchFeedException if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed findByG_F(long groupId, String feedId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByG_F(groupId, feedId);

		if (journalFeed == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", feedId=");
			msg.append(feedId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFeedException(msg.toString());
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed where groupId = &#63; and feedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByG_F(long groupId, String feedId)
		throws SystemException {
		return fetchByG_F(groupId, feedId, true);
	}

	/**
	 * Returns the journal feed where groupId = &#63; and feedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed fetchByG_F(long groupId, String feedId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, feedId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F,
					finderArgs, this);
		}

		if (result instanceof JournalFeed) {
			JournalFeed journalFeed = (JournalFeed)result;

			if ((groupId != journalFeed.getGroupId()) ||
					!Validator.equals(feedId, journalFeed.getFeedId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (feedId == null) {
				query.append(_FINDER_COLUMN_G_F_FEEDID_1);
			}
			else {
				if (feedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_FEEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_FEEDID_2);
				}
			}

			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (feedId != null) {
					qPos.add(feedId);
				}

				List<JournalFeed> list = q.list();

				result = list;

				JournalFeed journalFeed = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs, list);
				}
				else {
					journalFeed = list.get(0);

					cacheResult(journalFeed);

					if ((journalFeed.getGroupId() != groupId) ||
							(journalFeed.getFeedId() == null) ||
							!journalFeed.getFeedId().equals(feedId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
							finderArgs, journalFeed);
					}
				}

				return journalFeed;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
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
				return (JournalFeed)result;
			}
		}
	}

	/**
	 * Returns all the journal feeds.
	 *
	 * @return the journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFeed> findAll(int start, int end,
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

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALFEED);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALFEED.concat(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the journal feeds where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalFeed journalFeed : findByUuid(uuid)) {
			remove(journalFeed);
		}
	}

	/**
	 * Removes the journal feed where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal feed that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed removeByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByUUID_G(uuid, groupId);

		return remove(journalFeed);
	}

	/**
	 * Removes all the journal feeds where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (JournalFeed journalFeed : findByUuid_C(uuid, companyId)) {
			remove(journalFeed);
		}
	}

	/**
	 * Removes all the journal feeds where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalFeed journalFeed : findByGroupId(groupId)) {
			remove(journalFeed);
		}
	}

	/**
	 * Removes the journal feed where groupId = &#63; and feedId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the journal feed that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFeed removeByG_F(long groupId, String feedId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByG_F(groupId, feedId);

		return remove(journalFeed);
	}

	/**
	 * Removes all the journal feeds from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalFeed journalFeed : findAll()) {
			remove(journalFeed);
		}
	}

	/**
	 * Returns the number of journal feeds where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALFEED_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal feeds where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFEED_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFEED_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal feeds where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal feeds that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALFEED_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFeed.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of journal feeds where groupId = &#63; and feedId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the number of matching journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, String feedId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, feedId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (feedId == null) {
				query.append(_FINDER_COLUMN_G_F_FEEDID_1);
			}
			else {
				if (feedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_FEEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_FEEDID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (feedId != null) {
					qPos.add(feedId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal feeds.
	 *
	 * @return the number of journal feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALFEED);

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
	 * Initializes the journal feed persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalFeed")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalFeed>> listenersList = new ArrayList<ModelListener<JournalFeed>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalFeed>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(JournalFeedImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalFolderPersistence.class)
	protected JournalFolderPersistence journalFolderPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALFEED = "SELECT journalFeed FROM JournalFeed journalFeed";
	private static final String _SQL_SELECT_JOURNALFEED_WHERE = "SELECT journalFeed FROM JournalFeed journalFeed WHERE ";
	private static final String _SQL_COUNT_JOURNALFEED = "SELECT COUNT(journalFeed) FROM JournalFeed journalFeed";
	private static final String _SQL_COUNT_JOURNALFEED_WHERE = "SELECT COUNT(journalFeed) FROM JournalFeed journalFeed WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalFeed.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalFeed.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalFeed.uuid IS NULL OR journalFeed.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalFeed.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalFeed.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalFeed.uuid IS NULL OR journalFeed.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalFeed.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "journalFeed.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "journalFeed.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(journalFeed.uuid IS NULL OR journalFeed.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "journalFeed.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalFeed.groupId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "journalFeed.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FEEDID_1 = "journalFeed.feedId IS NULL";
	private static final String _FINDER_COLUMN_G_F_FEEDID_2 = "journalFeed.feedId = ?";
	private static final String _FINDER_COLUMN_G_F_FEEDID_3 = "(journalFeed.feedId IS NULL OR journalFeed.feedId = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "journalFeed.id_";
	private static final String _FILTER_SQL_SELECT_JOURNALFEED_WHERE = "SELECT DISTINCT {journalFeed.*} FROM JournalFeed journalFeed WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalFeed.*} FROM (SELECT DISTINCT journalFeed.id_ FROM JournalFeed journalFeed WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalFeed ON TEMP_TABLE.id_ = JournalFeed.id_";
	private static final String _FILTER_SQL_COUNT_JOURNALFEED_WHERE = "SELECT COUNT(DISTINCT journalFeed.id_) AS COUNT_VALUE FROM JournalFeed journalFeed WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "journalFeed";
	private static final String _FILTER_ENTITY_TABLE = "JournalFeed";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalFeed.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalFeed.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalFeed exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalFeed exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalFeedPersistenceImpl.class);
	private static JournalFeed _nullJournalFeed = new JournalFeedImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalFeed> toCacheModel() {
				return _nullJournalFeedCacheModel;
			}
		};

	private static CacheModel<JournalFeed> _nullJournalFeedCacheModel = new CacheModel<JournalFeed>() {
			public JournalFeed toEntityModel() {
				return _nullJournalFeed;
			}
		};
}