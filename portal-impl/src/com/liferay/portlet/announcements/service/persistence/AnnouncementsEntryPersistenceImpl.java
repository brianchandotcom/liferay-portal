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

package com.liferay.portlet.announcements.service.persistence;

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
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the announcements entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryPersistence
 * @see AnnouncementsEntryUtil
 * @generated
 */
public class AnnouncementsEntryPersistenceImpl extends BasePersistenceImpl<AnnouncementsEntry>
	implements AnnouncementsEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AnnouncementsEntryUtil} to access the announcements entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			AnnouncementsEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AnnouncementsEntryModelImpl.UUID_COLUMN_BITMASK |
			AnnouncementsEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			AnnouncementsEntryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			AnnouncementsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AnnouncementsEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			AnnouncementsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AnnouncementsEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			AnnouncementsEntryModelImpl.ALERT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the announcements entry in the entity cache if it is enabled.
	 *
	 * @param announcementsEntry the announcements entry
	 */
	public void cacheResult(AnnouncementsEntry announcementsEntry) {
		EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey(),
			announcementsEntry);

		announcementsEntry.resetOriginalValues();
	}

	/**
	 * Caches the announcements entries in the entity cache if it is enabled.
	 *
	 * @param announcementsEntries the announcements entries
	 */
	public void cacheResult(List<AnnouncementsEntry> announcementsEntries) {
		for (AnnouncementsEntry announcementsEntry : announcementsEntries) {
			if (EntityCacheUtil.getResult(
						AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsEntryImpl.class,
						announcementsEntry.getPrimaryKey()) == null) {
				cacheResult(announcementsEntry);
			}
			else {
				announcementsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all announcements entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AnnouncementsEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AnnouncementsEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the announcements entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnnouncementsEntry announcementsEntry) {
		EntityCacheUtil.removeResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AnnouncementsEntry> announcementsEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnnouncementsEntry announcementsEntry : announcementsEntries) {
			EntityCacheUtil.removeResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new announcements entry with the primary key. Does not add the announcements entry to the database.
	 *
	 * @param entryId the primary key for the new announcements entry
	 * @return the new announcements entry
	 */
	public AnnouncementsEntry create(long entryId) {
		AnnouncementsEntry announcementsEntry = new AnnouncementsEntryImpl();

		announcementsEntry.setNew(true);
		announcementsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		announcementsEntry.setUuid(uuid);

		return announcementsEntry;
	}

	/**
	 * Removes the announcements entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the announcements entry
	 * @return the announcements entry that was removed
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		return remove(Long.valueOf(entryId));
	}

	/**
	 * Removes the announcements entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the announcements entry
	 * @return the announcements entry that was removed
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
					primaryKey);

			if (announcementsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(announcementsEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected AnnouncementsEntry removeImpl(
		AnnouncementsEntry announcementsEntry) throws SystemException {
		announcementsEntry = toUnwrappedModel(announcementsEntry);

		Session session = null;

		try {
			session = openSession();

			if (announcementsEntry.isCachedModel()) {
				announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
						announcementsEntry.getPrimaryKeyObj());
			}

			if (announcementsEntry != null) {
				session.delete(announcementsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (announcementsEntry != null) {
			clearCache(announcementsEntry);
		}

		return announcementsEntry;
	}

	@Override
	public AnnouncementsEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws SystemException {
		announcementsEntry = toUnwrappedModel(announcementsEntry);

		boolean isNew = announcementsEntry.isNew();

		AnnouncementsEntryModelImpl announcementsEntryModelImpl = (AnnouncementsEntryModelImpl)announcementsEntry;

		if (Validator.isNull(announcementsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			announcementsEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (announcementsEntry.isNew()) {
				session.save(announcementsEntry);

				announcementsEntry.setNew(false);
			}
			else {
				session.merge(announcementsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AnnouncementsEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((announcementsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						announcementsEntryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { announcementsEntryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((announcementsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						announcementsEntryModelImpl.getOriginalUuid(),
						Long.valueOf(announcementsEntryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						announcementsEntryModelImpl.getUuid(),
						Long.valueOf(announcementsEntryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((announcementsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsEntryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(announcementsEntryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((announcementsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsEntryModelImpl.getOriginalClassNameId()),
						Long.valueOf(announcementsEntryModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						Long.valueOf(announcementsEntryModelImpl.getClassNameId()),
						Long.valueOf(announcementsEntryModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((announcementsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsEntryModelImpl.getOriginalClassNameId()),
						Long.valueOf(announcementsEntryModelImpl.getOriginalClassPK()),
						Boolean.valueOf(announcementsEntryModelImpl.getOriginalAlert())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_A,
					args);

				args = new Object[] {
						Long.valueOf(announcementsEntryModelImpl.getClassNameId()),
						Long.valueOf(announcementsEntryModelImpl.getClassPK()),
						Boolean.valueOf(announcementsEntryModelImpl.getAlert())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_A,
					args);
			}
		}

		EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey(),
			announcementsEntry);

		return announcementsEntry;
	}

	protected AnnouncementsEntry toUnwrappedModel(
		AnnouncementsEntry announcementsEntry) {
		if (announcementsEntry instanceof AnnouncementsEntryImpl) {
			return announcementsEntry;
		}

		AnnouncementsEntryImpl announcementsEntryImpl = new AnnouncementsEntryImpl();

		announcementsEntryImpl.setNew(announcementsEntry.isNew());
		announcementsEntryImpl.setPrimaryKey(announcementsEntry.getPrimaryKey());

		announcementsEntryImpl.setUuid(announcementsEntry.getUuid());
		announcementsEntryImpl.setEntryId(announcementsEntry.getEntryId());
		announcementsEntryImpl.setCompanyId(announcementsEntry.getCompanyId());
		announcementsEntryImpl.setUserId(announcementsEntry.getUserId());
		announcementsEntryImpl.setUserName(announcementsEntry.getUserName());
		announcementsEntryImpl.setCreateDate(announcementsEntry.getCreateDate());
		announcementsEntryImpl.setModifiedDate(announcementsEntry.getModifiedDate());
		announcementsEntryImpl.setClassNameId(announcementsEntry.getClassNameId());
		announcementsEntryImpl.setClassPK(announcementsEntry.getClassPK());
		announcementsEntryImpl.setTitle(announcementsEntry.getTitle());
		announcementsEntryImpl.setContent(announcementsEntry.getContent());
		announcementsEntryImpl.setUrl(announcementsEntry.getUrl());
		announcementsEntryImpl.setType(announcementsEntry.getType());
		announcementsEntryImpl.setDisplayDate(announcementsEntry.getDisplayDate());
		announcementsEntryImpl.setExpirationDate(announcementsEntry.getExpirationDate());
		announcementsEntryImpl.setPriority(announcementsEntry.getPriority());
		announcementsEntryImpl.setAlert(announcementsEntry.isAlert());

		return announcementsEntryImpl;
	}

	/**
	 * Returns the announcements entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements entry
	 * @return the announcements entry
	 * @throws com.liferay.portal.NoSuchModelException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the announcements entry with the primary key or throws a {@link com.liferay.portlet.announcements.NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the announcements entry
	 * @return the announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByPrimaryKey(entryId);

		if (announcementsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return announcementsEntry;
	}

	/**
	 * Returns the announcements entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements entry
	 * @return the announcements entry, or <code>null</code> if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the announcements entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the announcements entry
	 * @return the announcements entry, or <code>null</code> if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)EntityCacheUtil.getResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsEntryImpl.class, entryId);

		if (announcementsEntry == _nullAnnouncementsEntry) {
			return null;
		}

		if (announcementsEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
						Long.valueOf(entryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (announcementsEntry != null) {
					cacheResult(announcementsEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsEntryImpl.class, entryId,
						_nullAnnouncementsEntry);
				}

				closeSession(session);
			}
		}

		return announcementsEntry;
	}

	/**
	 * Returns all the announcements entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUuid(String uuid, int start, int end,
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

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsEntry announcementsEntry : list) {
				if (!Validator.equals(uuid, announcementsEntry.getUuid())) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first announcements entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first announcements entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<AnnouncementsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last announcements entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<AnnouncementsEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] findByUuid_PrevAndNext(long entryId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, announcementsEntry, uuid,
					orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = getByUuid_PrevAndNext(session, announcementsEntry, uuid,
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

	protected AnnouncementsEntry getByUuid_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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
			query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUuid(String uuid)
		throws SystemException {
		return filterFindByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUuid(String uuid, int start,
		int end) throws SystemException {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

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

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			return (List<AnnouncementsEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] filterFindByUuid_PrevAndNext(long entryId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(entryId, uuid, orderByComparator);
		}

		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(session, announcementsEntry,
					uuid, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = filterGetByUuid_PrevAndNext(session, announcementsEntry,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry filterGetByUuid_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, String uuid,
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
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

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

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUuid_C(String uuid, long companyId,
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

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsEntry announcementsEntry : list) {
				if (!Validator.equals(uuid, announcementsEntry.getUuid()) ||
						(companyId != announcementsEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AnnouncementsEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<AnnouncementsEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] findByUuid_C_PrevAndNext(long entryId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, announcementsEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = getByUuid_C_PrevAndNext(session, announcementsEntry,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry getByUuid_C_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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
			query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUuid_C(String uuid,
		long companyId) throws SystemException {
		return filterFindByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUuid_C(String uuid,
		long companyId, int start, int end) throws SystemException {
		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUuid_C(String uuid,
		long companyId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

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

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<AnnouncementsEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] filterFindByUuid_C_PrevAndNext(long entryId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C_PrevAndNext(entryId, uuid, companyId,
				orderByComparator);
		}

		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(session,
					announcementsEntry, uuid, companyId, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = filterGetByUuid_C_PrevAndNext(session,
					announcementsEntry, uuid, companyId, orderByComparator,
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

	protected AnnouncementsEntry filterGetByUuid_C_PrevAndNext(
		Session session, AnnouncementsEntry announcementsEntry, String uuid,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

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

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsEntry announcementsEntry : list) {
				if ((userId != announcementsEntry.getUserId())) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first announcements entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByUserId_First(userId,
				orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first announcements entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AnnouncementsEntry> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByUserId_Last(userId,
				orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last announcements entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsEntry> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set where userId = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] findByUserId_PrevAndNext(long entryId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(session, announcementsEntry,
					userId, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = getByUserId_PrevAndNext(session, announcementsEntry,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry getByUserId_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUserId(long userId)
		throws SystemException {
		return filterFindByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the announcements entries that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUserId(long userId, int start,
		int end) throws SystemException {
		return filterFindByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUserId(userId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			return (List<AnnouncementsEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where userId = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] filterFindByUserId_PrevAndNext(long entryId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUserId_PrevAndNext(entryId, userId, orderByComparator);
		}

		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = filterGetByUserId_PrevAndNext(session,
					announcementsEntry, userId, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = filterGetByUserId_PrevAndNext(session,
					announcementsEntry, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry filterGetByUserId_PrevAndNext(
		Session session, AnnouncementsEntry announcementsEntry, long userId,
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
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsEntry announcementsEntry : list) {
				if ((classNameId != announcementsEntry.getClassNameId()) ||
						(classPK != announcementsEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<AnnouncementsEntry> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<AnnouncementsEntry> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] findByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(session, announcementsEntry,
					classNameId, classPK, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = getByC_C_PrevAndNext(session, announcementsEntry,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry getByC_C_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, long classNameId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByC_C(long classNameId,
		long classPK) throws SystemException {
		return filterFindByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByC_C(long classNameId,
		long classPK, int start, int end) throws SystemException {
		return filterFindByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries that the user has permissions to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByC_C(long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_C(classNameId, classPK, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			return (List<AnnouncementsEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] filterFindByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_C_PrevAndNext(entryId, classNameId, classPK,
				orderByComparator);
		}

		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = filterGetByC_C_PrevAndNext(session, announcementsEntry,
					classNameId, classPK, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = filterGetByC_C_PrevAndNext(session, announcementsEntry,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry filterGetByC_C_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, long classNameId, long classPK,
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
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @return the matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert) throws SystemException {
		return findByC_C_A(classNameId, classPK, alert, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int start, int end) throws SystemException {
		return findByC_C_A(classNameId, classPK, alert, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_A;
			finderArgs = new Object[] { classNameId, classPK, alert };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_A;
			finderArgs = new Object[] {
					classNameId, classPK, alert,
					
					start, end, orderByComparator
				};
		}

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsEntry announcementsEntry : list) {
				if ((classNameId != announcementsEntry.getClassNameId()) ||
						(classPK != announcementsEntry.getClassPK()) ||
						(alert != announcementsEntry.getAlert())) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByC_C_A_First(long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByC_C_A_First(classNameId,
				classPK, alert, orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", alert=");
		msg.append(alert);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByC_C_A_First(long classNameId,
		long classPK, boolean alert, OrderByComparator orderByComparator)
		throws SystemException {
		List<AnnouncementsEntry> list = findByC_C_A(classNameId, classPK,
				alert, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry findByC_C_A_Last(long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByC_C_A_Last(classNameId,
				classPK, alert, orderByComparator);

		if (announcementsEntry != null) {
			return announcementsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", alert=");
		msg.append(alert);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry fetchByC_C_A_Last(long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_A(classNameId, classPK, alert);

		List<AnnouncementsEntry> list = findByC_C_A(classNameId, classPK,
				alert, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] findByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = getByC_C_A_PrevAndNext(session, announcementsEntry,
					classNameId, classPK, alert, orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = getByC_C_A_PrevAndNext(session, announcementsEntry,
					classNameId, classPK, alert, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsEntry getByC_C_A_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

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
			query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(alert);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @return the matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByC_C_A(long classNameId,
		long classPK, boolean alert) throws SystemException {
		return filterFindByC_C_A(classNameId, classPK, alert,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByC_C_A(long classNameId,
		long classPK, boolean alert, int start, int end)
		throws SystemException {
		return filterFindByC_C_A(classNameId, classPK, alert, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries that the user has permissions to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> filterFindByC_C_A(long classNameId,
		long classPK, boolean alert, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_C_A(classNameId, classPK, alert, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(alert);

			return (List<AnnouncementsEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param entryId the primary key of the current announcements entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements entry
	 * @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsEntry[] filterFindByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_C_A_PrevAndNext(entryId, classNameId, classPK,
				alert, orderByComparator);
		}

		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = filterGetByC_C_A_PrevAndNext(session,
					announcementsEntry, classNameId, classPK, alert,
					orderByComparator, true);

			array[1] = announcementsEntry;

			array[2] = filterGetByC_C_A_PrevAndNext(session,
					announcementsEntry, classNameId, classPK, alert,
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

	protected AnnouncementsEntry filterGetByC_C_A_PrevAndNext(Session session,
		AnnouncementsEntry announcementsEntry, long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AnnouncementsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AnnouncementsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(alert);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the announcements entries.
	 *
	 * @return the announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @return the range of announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements entries
	 * @param end the upper bound of the range of announcements entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsEntry> findAll(int start, int end,
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

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANNOUNCEMENTSENTRY.concat(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AnnouncementsEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsEntry>)QueryUtil.list(q,
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
	 * Removes all the announcements entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUuid(uuid)) {
			remove(announcementsEntry);
		}
	}

	/**
	 * Removes all the announcements entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUuid_C(uuid,
				companyId)) {
			remove(announcementsEntry);
		}
	}

	/**
	 * Removes all the announcements entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUserId(userId)) {
			remove(announcementsEntry);
		}
	}

	/**
	 * Removes all the announcements entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByC_C(classNameId,
				classPK)) {
			remove(announcementsEntry);
		}
	}

	/**
	 * Removes all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByC_C_A(classNameId,
				classPK, alert)) {
			remove(announcementsEntry);
		}
	}

	/**
	 * Removes all the announcements entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findAll()) {
			remove(announcementsEntry);
		}
	}

	/**
	 * Returns the number of announcements entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

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
	 * Returns the number of announcements entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByUuid(String uuid) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

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
	 * Returns the number of announcements entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

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
	 * Returns the number of announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByUuid_C(String uuid, long companyId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid_C(uuid, companyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

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
	 * Returns the number of announcements entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByUserId(long userId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUserId(userId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

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
	 * Returns the number of announcements entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByC_C(long classNameId, long classPK)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_C(classNameId, classPK);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

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
	 * Returns the number of announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @return the number of matching announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK, alert };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param alert the alert
	 * @return the number of matching announcements entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_C_A(classNameId, classPK, alert);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AnnouncementsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(alert);

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
	 * Returns the number of announcements entries.
	 *
	 * @return the number of announcements entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSENTRY);

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
	 * Initializes the announcements entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsEntry>> listenersList = new ArrayList<ModelListener<AnnouncementsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AnnouncementsEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(type = AnnouncementsFlagPersistence.class)
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	private static final String _SQL_SELECT_ANNOUNCEMENTSENTRY = "SELECT announcementsEntry FROM AnnouncementsEntry announcementsEntry";
	private static final String _SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE = "SELECT announcementsEntry FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSENTRY = "SELECT COUNT(announcementsEntry) FROM AnnouncementsEntry announcementsEntry";
	private static final String _SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE = "SELECT COUNT(announcementsEntry) FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "announcementsEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "announcementsEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(announcementsEntry.uuid IS NULL OR announcementsEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "announcementsEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "announcementsEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(announcementsEntry.uuid IS NULL OR announcementsEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "announcementsEntry.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "announcementsEntry.userId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "announcementsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "announcementsEntry.classPK = ?";
	private static final String _FINDER_COLUMN_C_C_A_CLASSNAMEID_2 = "announcementsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_A_CLASSPK_2 = "announcementsEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_A_ALERT_2 = "announcementsEntry.alert = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "announcementsEntry.entryId";
	private static final String _FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE = "SELECT DISTINCT {announcementsEntry.*} FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {AnnouncementsEntry.*} FROM (SELECT DISTINCT announcementsEntry.entryId FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_ANNOUNCEMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN AnnouncementsEntry ON TEMP_TABLE.entryId = AnnouncementsEntry.entryId";
	private static final String _FILTER_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE = "SELECT COUNT(DISTINCT announcementsEntry.entryId) AS COUNT_VALUE FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "announcementsEntry";
	private static final String _FILTER_ENTITY_TABLE = "AnnouncementsEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "AnnouncementsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsEntryPersistenceImpl.class);
	private static AnnouncementsEntry _nullAnnouncementsEntry = new AnnouncementsEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AnnouncementsEntry> toCacheModel() {
				return _nullAnnouncementsEntryCacheModel;
			}
		};

	private static CacheModel<AnnouncementsEntry> _nullAnnouncementsEntryCacheModel =
		new CacheModel<AnnouncementsEntry>() {
			public AnnouncementsEntry toEntityModel() {
				return _nullAnnouncementsEntry;
			}
		};
}