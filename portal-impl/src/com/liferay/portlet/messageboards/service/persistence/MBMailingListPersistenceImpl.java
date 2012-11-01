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

package com.liferay.portlet.messageboards.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.impl.MBMailingListImpl;
import com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message boards mailing list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingListPersistence
 * @see MBMailingListUtil
 * @generated
 */
public class MBMailingListPersistenceImpl extends BasePersistenceImpl<MBMailingList>
	implements MBMailingListPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBMailingListUtil} to access the message boards mailing list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBMailingListImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			MBMailingListModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MBMailingListModelImpl.UUID_COLUMN_BITMASK |
			MBMailingListModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MBMailingListModelImpl.UUID_COLUMN_BITMASK |
			MBMailingListModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByActive",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE =
		new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByActive", new String[] { Boolean.class.getName() },
			MBMailingListModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBMailingListModelImpl.GROUPID_COLUMN_BITMASK |
			MBMailingListModelImpl.CATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			MBMailingListImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the message boards mailing list in the entity cache if it is enabled.
	 *
	 * @param mbMailingList the message boards mailing list
	 */
	public void cacheResult(MBMailingList mbMailingList) {
		EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
			mbMailingList);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMailingList.getUuid(),
				Long.valueOf(mbMailingList.getGroupId())
			}, mbMailingList);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
			new Object[] {
				Long.valueOf(mbMailingList.getGroupId()),
				Long.valueOf(mbMailingList.getCategoryId())
			}, mbMailingList);

		mbMailingList.resetOriginalValues();
	}

	/**
	 * Caches the message boards mailing lists in the entity cache if it is enabled.
	 *
	 * @param mbMailingLists the message boards mailing lists
	 */
	public void cacheResult(List<MBMailingList> mbMailingLists) {
		for (MBMailingList mbMailingList : mbMailingLists) {
			if (EntityCacheUtil.getResult(
						MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
						MBMailingListImpl.class, mbMailingList.getPrimaryKey()) == null) {
				cacheResult(mbMailingList);
			}
			else {
				mbMailingList.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards mailing lists.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBMailingListImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBMailingListImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards mailing list.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBMailingList mbMailingList) {
		EntityCacheUtil.removeResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mbMailingList);
	}

	@Override
	public void clearCache(List<MBMailingList> mbMailingLists) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBMailingList mbMailingList : mbMailingLists) {
			EntityCacheUtil.removeResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
				MBMailingListImpl.class, mbMailingList.getPrimaryKey());

			clearUniqueFindersCache(mbMailingList);
		}
	}

	protected void clearUniqueFindersCache(MBMailingList mbMailingList) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMailingList.getUuid(),
				Long.valueOf(mbMailingList.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C,
			new Object[] {
				Long.valueOf(mbMailingList.getGroupId()),
				Long.valueOf(mbMailingList.getCategoryId())
			});
	}

	/**
	 * Creates a new message boards mailing list with the primary key. Does not add the message boards mailing list to the database.
	 *
	 * @param mailingListId the primary key for the new message boards mailing list
	 * @return the new message boards mailing list
	 */
	public MBMailingList create(long mailingListId) {
		MBMailingList mbMailingList = new MBMailingListImpl();

		mbMailingList.setNew(true);
		mbMailingList.setPrimaryKey(mailingListId);

		String uuid = PortalUUIDUtil.generate();

		mbMailingList.setUuid(uuid);

		return mbMailingList;
	}

	/**
	 * Removes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList remove(long mailingListId)
		throws NoSuchMailingListException, SystemException {
		return remove(Long.valueOf(mailingListId));
	}

	/**
	 * Removes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards mailing list
	 * @return the message boards mailing list that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMailingList remove(Serializable primaryKey)
		throws NoSuchMailingListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMailingList mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
					primaryKey);

			if (mbMailingList == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMailingListException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbMailingList);
		}
		catch (NoSuchMailingListException nsee) {
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
	protected MBMailingList removeImpl(MBMailingList mbMailingList)
		throws SystemException {
		mbMailingList = toUnwrappedModel(mbMailingList);

		Session session = null;

		try {
			session = openSession();

			if (mbMailingList.isCachedModel()) {
				mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
						mbMailingList.getPrimaryKeyObj());
			}

			if (mbMailingList != null) {
				session.delete(mbMailingList);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbMailingList != null) {
			clearCache(mbMailingList);
		}

		return mbMailingList;
	}

	@Override
	public MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws SystemException {
		mbMailingList = toUnwrappedModel(mbMailingList);

		boolean isNew = mbMailingList.isNew();

		MBMailingListModelImpl mbMailingListModelImpl = (MBMailingListModelImpl)mbMailingList;

		if (Validator.isNull(mbMailingList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbMailingList.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (mbMailingList.isNew()) {
				session.save(mbMailingList);

				mbMailingList.setNew(false);
			}
			else {
				session.merge(mbMailingList);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBMailingListModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbMailingListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbMailingListModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mbMailingListModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mbMailingListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbMailingListModelImpl.getOriginalUuid(),
						Long.valueOf(mbMailingListModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mbMailingListModelImpl.getUuid(),
						Long.valueOf(mbMailingListModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mbMailingListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Boolean.valueOf(mbMailingListModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIVE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE,
					args);

				args = new Object[] {
						Boolean.valueOf(mbMailingListModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIVE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE,
					args);
			}
		}

		EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
			mbMailingList);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMailingList.getUuid(),
					Long.valueOf(mbMailingList.getGroupId())
				}, mbMailingList);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
				new Object[] {
					Long.valueOf(mbMailingList.getGroupId()),
					Long.valueOf(mbMailingList.getCategoryId())
				}, mbMailingList);
		}
		else {
			if ((mbMailingListModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbMailingListModelImpl.getOriginalUuid(),
						Long.valueOf(mbMailingListModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						mbMailingList.getUuid(),
						Long.valueOf(mbMailingList.getGroupId())
					}, mbMailingList);
			}

			if ((mbMailingListModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMailingListModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMailingListModelImpl.getOriginalCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
					new Object[] {
						Long.valueOf(mbMailingList.getGroupId()),
						Long.valueOf(mbMailingList.getCategoryId())
					}, mbMailingList);
			}
		}

		return mbMailingList;
	}

	protected MBMailingList toUnwrappedModel(MBMailingList mbMailingList) {
		if (mbMailingList instanceof MBMailingListImpl) {
			return mbMailingList;
		}

		MBMailingListImpl mbMailingListImpl = new MBMailingListImpl();

		mbMailingListImpl.setNew(mbMailingList.isNew());
		mbMailingListImpl.setPrimaryKey(mbMailingList.getPrimaryKey());

		mbMailingListImpl.setUuid(mbMailingList.getUuid());
		mbMailingListImpl.setMailingListId(mbMailingList.getMailingListId());
		mbMailingListImpl.setGroupId(mbMailingList.getGroupId());
		mbMailingListImpl.setCompanyId(mbMailingList.getCompanyId());
		mbMailingListImpl.setUserId(mbMailingList.getUserId());
		mbMailingListImpl.setUserName(mbMailingList.getUserName());
		mbMailingListImpl.setCreateDate(mbMailingList.getCreateDate());
		mbMailingListImpl.setModifiedDate(mbMailingList.getModifiedDate());
		mbMailingListImpl.setCategoryId(mbMailingList.getCategoryId());
		mbMailingListImpl.setEmailAddress(mbMailingList.getEmailAddress());
		mbMailingListImpl.setInProtocol(mbMailingList.getInProtocol());
		mbMailingListImpl.setInServerName(mbMailingList.getInServerName());
		mbMailingListImpl.setInServerPort(mbMailingList.getInServerPort());
		mbMailingListImpl.setInUseSSL(mbMailingList.isInUseSSL());
		mbMailingListImpl.setInUserName(mbMailingList.getInUserName());
		mbMailingListImpl.setInPassword(mbMailingList.getInPassword());
		mbMailingListImpl.setInReadInterval(mbMailingList.getInReadInterval());
		mbMailingListImpl.setOutEmailAddress(mbMailingList.getOutEmailAddress());
		mbMailingListImpl.setOutCustom(mbMailingList.isOutCustom());
		mbMailingListImpl.setOutServerName(mbMailingList.getOutServerName());
		mbMailingListImpl.setOutServerPort(mbMailingList.getOutServerPort());
		mbMailingListImpl.setOutUseSSL(mbMailingList.isOutUseSSL());
		mbMailingListImpl.setOutUserName(mbMailingList.getOutUserName());
		mbMailingListImpl.setOutPassword(mbMailingList.getOutPassword());
		mbMailingListImpl.setAllowAnonymous(mbMailingList.isAllowAnonymous());
		mbMailingListImpl.setActive(mbMailingList.isActive());

		return mbMailingListImpl;
	}

	/**
	 * Returns the message boards mailing list with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards mailing list
	 * @return the message boards mailing list
	 * @throws com.liferay.portal.NoSuchModelException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMailingList findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards mailing list with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByPrimaryKey(long mailingListId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByPrimaryKey(mailingListId);

		if (mbMailingList == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + mailingListId);
			}

			throw new NoSuchMailingListException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				mailingListId);
		}

		return mbMailingList;
	}

	/**
	 * Returns the message boards mailing list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards mailing list
	 * @return the message boards mailing list, or <code>null</code> if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMailingList fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards mailing list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list, or <code>null</code> if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByPrimaryKey(long mailingListId)
		throws SystemException {
		MBMailingList mbMailingList = (MBMailingList)EntityCacheUtil.getResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
				MBMailingListImpl.class, mailingListId);

		if (mbMailingList == _nullMBMailingList) {
			return null;
		}

		if (mbMailingList == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
						Long.valueOf(mailingListId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mbMailingList != null) {
					cacheResult(mbMailingList);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
						MBMailingListImpl.class, mailingListId,
						_nullMBMailingList);
				}

				closeSession(session);
			}
		}

		return mbMailingList;
	}

	/**
	 * Returns all the message boards mailing lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards mailing lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @return the range of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards mailing lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByUuid(String uuid, int start, int end,
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

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMailingList mbMailingList : list) {
				if (!Validator.equals(uuid, mbMailingList.getUuid())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards mailing list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUuid_First(uuid, orderByComparator);

		if (mbMailingList != null) {
			return mbMailingList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMailingListException(msg.toString());
	}

	/**
	 * Returns the first message boards mailing list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMailingList> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards mailing list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUuid_Last(uuid, orderByComparator);

		if (mbMailingList != null) {
			return mbMailingList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMailingListException(msg.toString());
	}

	/**
	 * Returns the last message boards mailing list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<MBMailingList> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where uuid = &#63;.
	 *
	 * @param mailingListId the primary key of the current message boards mailing list
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList[] findByUuid_PrevAndNext(long mailingListId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		Session session = null;

		try {
			session = openSession();

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mbMailingList, uuid,
					orderByComparator, true);

			array[1] = mbMailingList;

			array[2] = getByUuid_PrevAndNext(session, mbMailingList, uuid,
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

	protected MBMailingList getByUuid_PrevAndNext(Session session,
		MBMailingList mbMailingList, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMailingList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMailingList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByUUID_G(String uuid, long groupId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUUID_G(uuid, groupId);

		if (mbMailingList == null) {
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

			throw new NoSuchMailingListException(msg.toString());
		}

		return mbMailingList;
	}

	/**
	 * Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MBMailingList) {
			MBMailingList mbMailingList = (MBMailingList)result;

			if (!Validator.equals(uuid, mbMailingList.getUuid()) ||
					(groupId != mbMailingList.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

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

				List<MBMailingList> list = q.list();

				result = list;

				MBMailingList mbMailingList = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbMailingList = list.get(0);

					cacheResult(mbMailingList);

					if ((mbMailingList.getUuid() == null) ||
							!mbMailingList.getUuid().equals(uuid) ||
							(mbMailingList.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbMailingList);
					}
				}

				return mbMailingList;
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
				return (MBMailingList)result;
			}
		}
	}

	/**
	 * Returns all the message boards mailing lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards mailing lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @return the range of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards mailing lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByUuid_C(String uuid, long companyId,
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

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMailingList mbMailingList : list) {
				if (!Validator.equals(uuid, mbMailingList.getUuid()) ||
						(companyId != mbMailingList.getCompanyId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

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

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mbMailingList != null) {
			return mbMailingList;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMailingListException(msg.toString());
	}

	/**
	 * Returns the first message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMailingList> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mbMailingList != null) {
			return mbMailingList;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMailingListException(msg.toString());
	}

	/**
	 * Returns the last message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<MBMailingList> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param mailingListId the primary key of the current message boards mailing list
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList[] findByUuid_C_PrevAndNext(long mailingListId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		Session session = null;

		try {
			session = openSession();

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mbMailingList, uuid,
					companyId, orderByComparator, true);

			array[1] = mbMailingList;

			array[2] = getByUuid_C_PrevAndNext(session, mbMailingList, uuid,
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

	protected MBMailingList getByUuid_C_PrevAndNext(Session session,
		MBMailingList mbMailingList, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMailingList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMailingList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards mailing lists where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByActive(boolean active)
		throws SystemException {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards mailing lists where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @return the range of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards mailing lists where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findByActive(boolean active, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE;
			finderArgs = new Object[] { active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIVE;
			finderArgs = new Object[] { active, start, end, orderByComparator };
		}

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMailingList mbMailingList : list) {
				if ((active != mbMailingList.getActive())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

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

				qPos.add(active);

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards mailing list in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByActive_First(boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByActive_First(active,
				orderByComparator);

		if (mbMailingList != null) {
			return mbMailingList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMailingListException(msg.toString());
	}

	/**
	 * Returns the first message boards mailing list in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByActive_First(boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMailingList> list = findByActive(active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards mailing list in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByActive_Last(boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByActive_Last(active,
				orderByComparator);

		if (mbMailingList != null) {
			return mbMailingList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMailingListException(msg.toString());
	}

	/**
	 * Returns the last message boards mailing list in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByActive_Last(boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByActive(active);

		List<MBMailingList> list = findByActive(active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where active = &#63;.
	 *
	 * @param mailingListId the primary key of the current message boards mailing list
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList[] findByActive_PrevAndNext(long mailingListId,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		Session session = null;

		try {
			session = openSession();

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = getByActive_PrevAndNext(session, mbMailingList, active,
					orderByComparator, true);

			array[1] = mbMailingList;

			array[2] = getByActive_PrevAndNext(session, mbMailingList, active,
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

	protected MBMailingList getByActive_PrevAndNext(Session session,
		MBMailingList mbMailingList, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMailingList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMailingList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards mailing list
	 * @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList findByG_C(long groupId, long categoryId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByG_C(groupId, categoryId);

		if (mbMailingList == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMailingListException(msg.toString());
		}

		return mbMailingList;
	}

	/**
	 * Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByG_C(long groupId, long categoryId)
		throws SystemException {
		return fetchByG_C(groupId, categoryId, true);
	}

	/**
	 * Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList fetchByG_C(long groupId, long categoryId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C,
					finderArgs, this);
		}

		if (result instanceof MBMailingList) {
			MBMailingList mbMailingList = (MBMailingList)result;

			if ((groupId != mbMailingList.getGroupId()) ||
					(categoryId != mbMailingList.getCategoryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				List<MBMailingList> list = q.list();

				result = list;

				MBMailingList mbMailingList = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
						finderArgs, list);
				}
				else {
					mbMailingList = list.get(0);

					cacheResult(mbMailingList);

					if ((mbMailingList.getGroupId() != groupId) ||
							(mbMailingList.getCategoryId() != categoryId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
							finderArgs, mbMailingList);
					}
				}

				return mbMailingList;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C,
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
				return (MBMailingList)result;
			}
		}
	}

	/**
	 * Returns all the message boards mailing lists.
	 *
	 * @return the message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards mailing lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @return the range of message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards mailing lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMailingList> findAll(int start, int end,
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

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBMAILINGLIST);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBMAILINGLIST;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the message boards mailing lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (MBMailingList mbMailingList : findByUuid(uuid)) {
			remove(mbMailingList);
		}
	}

	/**
	 * Removes the message boards mailing list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards mailing list that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList removeByUUID_G(String uuid, long groupId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByUUID_G(uuid, groupId);

		return remove(mbMailingList);
	}

	/**
	 * Removes all the message boards mailing lists where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (MBMailingList mbMailingList : findByUuid_C(uuid, companyId)) {
			remove(mbMailingList);
		}
	}

	/**
	 * Removes all the message boards mailing lists where active = &#63; from the database.
	 *
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByActive(boolean active) throws SystemException {
		for (MBMailingList mbMailingList : findByActive(active)) {
			remove(mbMailingList);
		}
	}

	/**
	 * Removes the message boards mailing list where groupId = &#63; and categoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the message boards mailing list that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBMailingList removeByG_C(long groupId, long categoryId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByG_C(groupId, categoryId);

		return remove(mbMailingList);
	}

	/**
	 * Removes all the message boards mailing lists from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBMailingList mbMailingList : findAll()) {
			remove(mbMailingList);
		}
	}

	/**
	 * Returns the number of message boards mailing lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

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
	 * Returns the number of message boards mailing lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

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
	 * Returns the number of message boards mailing lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

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
	 * Returns the number of message boards mailing lists where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public int countByActive(boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIVE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIVE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards mailing lists where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards mailing lists.
	 *
	 * @return the number of message boards mailing lists
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBMAILINGLIST);

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
	 * Initializes the message boards mailing list persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMailingList")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMailingList>> listenersList = new ArrayList<ModelListener<MBMailingList>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMailingList>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBMailingListImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBMAILINGLIST = "SELECT mbMailingList FROM MBMailingList mbMailingList";
	private static final String _SQL_SELECT_MBMAILINGLIST_WHERE = "SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ";
	private static final String _SQL_COUNT_MBMAILINGLIST = "SELECT COUNT(mbMailingList) FROM MBMailingList mbMailingList";
	private static final String _SQL_COUNT_MBMAILINGLIST_WHERE = "SELECT COUNT(mbMailingList) FROM MBMailingList mbMailingList WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mbMailingList.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mbMailingList.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mbMailingList.uuid IS NULL OR mbMailingList.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mbMailingList.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mbMailingList.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mbMailingList.uuid IS NULL OR mbMailingList.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mbMailingList.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mbMailingList.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mbMailingList.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mbMailingList.uuid IS NULL OR mbMailingList.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mbMailingList.companyId = ?";
	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 = "mbMailingList.active = ?";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "mbMailingList.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 = "mbMailingList.categoryId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbMailingList.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBMailingList exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBMailingList exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MBMailingListPersistenceImpl.class);
	private static MBMailingList _nullMBMailingList = new MBMailingListImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBMailingList> toCacheModel() {
				return _nullMBMailingListCacheModel;
			}
		};

	private static CacheModel<MBMailingList> _nullMBMailingListCacheModel = new CacheModel<MBMailingList>() {
			public MBMailingList toEntityModel() {
				return _nullMBMailingList;
			}
		};
}