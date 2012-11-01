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

package com.liferay.portlet.dynamicdatalists.service.persistence;

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
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d l record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordPersistence
 * @see DDLRecordUtil
 * @generated
 */
public class DDLRecordPersistenceImpl extends BasePersistenceImpl<DDLRecord>
	implements DDLRecordPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDLRecordUtil} to access the d d l record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDLRecordImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDLRecordModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDLRecordModelImpl.UUID_COLUMN_BITMASK |
			DDLRecordModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDLRecordModelImpl.UUID_COLUMN_BITMASK |
			DDLRecordModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DDLRecordModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RECORDSETID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRecordSetId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordSetId",
			new String[] { Long.class.getName() },
			DDLRecordModelImpl.RECORDSETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RECORDSETID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordSetId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_U = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDLRecordModelImpl.RECORDSETID_COLUMN_BITMASK |
			DDLRecordModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_U = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the d d l record in the entity cache if it is enabled.
	 *
	 * @param ddlRecord the d d l record
	 */
	public void cacheResult(DDLRecord ddlRecord) {
		EntityCacheUtil.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordImpl.class, ddlRecord.getPrimaryKey(), ddlRecord);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddlRecord.getUuid(), Long.valueOf(ddlRecord.getGroupId())
			}, ddlRecord);

		ddlRecord.resetOriginalValues();
	}

	/**
	 * Caches the d d l records in the entity cache if it is enabled.
	 *
	 * @param ddlRecords the d d l records
	 */
	public void cacheResult(List<DDLRecord> ddlRecords) {
		for (DDLRecord ddlRecord : ddlRecords) {
			if (EntityCacheUtil.getResult(
						DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
						DDLRecordImpl.class, ddlRecord.getPrimaryKey()) == null) {
				cacheResult(ddlRecord);
			}
			else {
				ddlRecord.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d l records.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDLRecordImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDLRecordImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d l record.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDLRecord ddlRecord) {
		EntityCacheUtil.removeResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordImpl.class, ddlRecord.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ddlRecord);
	}

	@Override
	public void clearCache(List<DDLRecord> ddlRecords) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDLRecord ddlRecord : ddlRecords) {
			EntityCacheUtil.removeResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
				DDLRecordImpl.class, ddlRecord.getPrimaryKey());

			clearUniqueFindersCache(ddlRecord);
		}
	}

	protected void clearUniqueFindersCache(DDLRecord ddlRecord) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddlRecord.getUuid(), Long.valueOf(ddlRecord.getGroupId())
			});
	}

	/**
	 * Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	 *
	 * @param recordId the primary key for the new d d l record
	 * @return the new d d l record
	 */
	public DDLRecord create(long recordId) {
		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setNew(true);
		ddlRecord.setPrimaryKey(recordId);

		String uuid = PortalUUIDUtil.generate();

		ddlRecord.setUuid(uuid);

		return ddlRecord;
	}

	/**
	 * Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the d d l record
	 * @return the d d l record that was removed
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord remove(long recordId)
		throws NoSuchRecordException, SystemException {
		return remove(Long.valueOf(recordId));
	}

	/**
	 * Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d l record
	 * @return the d d l record that was removed
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDLRecord remove(Serializable primaryKey)
		throws NoSuchRecordException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDLRecord ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class,
					primaryKey);

			if (ddlRecord == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecordException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddlRecord);
		}
		catch (NoSuchRecordException nsee) {
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
	protected DDLRecord removeImpl(DDLRecord ddlRecord)
		throws SystemException {
		ddlRecord = toUnwrappedModel(ddlRecord);

		Session session = null;

		try {
			session = openSession();

			if (ddlRecord.isCachedModel()) {
				ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class,
						ddlRecord.getPrimaryKeyObj());
			}

			if (ddlRecord != null) {
				session.delete(ddlRecord);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddlRecord != null) {
			clearCache(ddlRecord);
		}

		return ddlRecord;
	}

	@Override
	public DDLRecord updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws SystemException {
		ddlRecord = toUnwrappedModel(ddlRecord);

		boolean isNew = ddlRecord.isNew();

		DDLRecordModelImpl ddlRecordModelImpl = (DDLRecordModelImpl)ddlRecord;

		if (Validator.isNull(ddlRecord.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddlRecord.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ddlRecord.isNew()) {
				session.save(ddlRecord);

				ddlRecord.setNew(false);
			}
			else {
				session.merge(ddlRecord);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDLRecordModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddlRecordModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalUuid(),
						Long.valueOf(ddlRecordModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddlRecordModelImpl.getUuid(),
						Long.valueOf(ddlRecordModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddlRecordModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(ddlRecordModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddlRecordModelImpl.getOriginalRecordSetId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RECORDSETID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID,
					args);

				args = new Object[] {
						Long.valueOf(ddlRecordModelImpl.getRecordSetId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RECORDSETID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddlRecordModelImpl.getOriginalRecordSetId()),
						Long.valueOf(ddlRecordModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U,
					args);

				args = new Object[] {
						Long.valueOf(ddlRecordModelImpl.getRecordSetId()),
						Long.valueOf(ddlRecordModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U,
					args);
			}
		}

		EntityCacheUtil.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordImpl.class, ddlRecord.getPrimaryKey(), ddlRecord);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					ddlRecord.getUuid(), Long.valueOf(ddlRecord.getGroupId())
				}, ddlRecord);
		}
		else {
			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalUuid(),
						Long.valueOf(ddlRecordModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						ddlRecord.getUuid(),
						Long.valueOf(ddlRecord.getGroupId())
					}, ddlRecord);
			}
		}

		return ddlRecord;
	}

	protected DDLRecord toUnwrappedModel(DDLRecord ddlRecord) {
		if (ddlRecord instanceof DDLRecordImpl) {
			return ddlRecord;
		}

		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		ddlRecordImpl.setNew(ddlRecord.isNew());
		ddlRecordImpl.setPrimaryKey(ddlRecord.getPrimaryKey());

		ddlRecordImpl.setUuid(ddlRecord.getUuid());
		ddlRecordImpl.setRecordId(ddlRecord.getRecordId());
		ddlRecordImpl.setGroupId(ddlRecord.getGroupId());
		ddlRecordImpl.setCompanyId(ddlRecord.getCompanyId());
		ddlRecordImpl.setUserId(ddlRecord.getUserId());
		ddlRecordImpl.setUserName(ddlRecord.getUserName());
		ddlRecordImpl.setVersionUserId(ddlRecord.getVersionUserId());
		ddlRecordImpl.setVersionUserName(ddlRecord.getVersionUserName());
		ddlRecordImpl.setCreateDate(ddlRecord.getCreateDate());
		ddlRecordImpl.setModifiedDate(ddlRecord.getModifiedDate());
		ddlRecordImpl.setDDMStorageId(ddlRecord.getDDMStorageId());
		ddlRecordImpl.setRecordSetId(ddlRecord.getRecordSetId());
		ddlRecordImpl.setVersion(ddlRecord.getVersion());
		ddlRecordImpl.setDisplayIndex(ddlRecord.getDisplayIndex());

		return ddlRecordImpl;
	}

	/**
	 * Returns the d d l record with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d l record
	 * @return the d d l record
	 * @throws com.liferay.portal.NoSuchModelException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDLRecord findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d l record with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordException} if it could not be found.
	 *
	 * @param recordId the primary key of the d d l record
	 * @return the d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByPrimaryKey(long recordId)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByPrimaryKey(recordId);

		if (ddlRecord == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + recordId);
			}

			throw new NoSuchRecordException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				recordId);
		}

		return ddlRecord;
	}

	/**
	 * Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d l record
	 * @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDLRecord fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordId the primary key of the d d l record
	 * @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByPrimaryKey(long recordId) throws SystemException {
		DDLRecord ddlRecord = (DDLRecord)EntityCacheUtil.getResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
				DDLRecordImpl.class, recordId);

		if (ddlRecord == _nullDDLRecord) {
			return null;
		}

		if (ddlRecord == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class,
						Long.valueOf(recordId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ddlRecord != null) {
					cacheResult(ddlRecord);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
						DDLRecordImpl.class, recordId, _nullDDLRecord);
				}

				closeSession(session);
			}
		}

		return ddlRecord;
	}

	/**
	 * Returns all the d d l records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByUuid(String uuid, int start, int end,
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

		List<DDLRecord> list = (List<DDLRecord>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDLRecord ddlRecord : list) {
				if (!Validator.equals(uuid, ddlRecord.getUuid())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

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

				list = (List<DDLRecord>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByUuid_First(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDLRecord> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByUuid_Last(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DDLRecord> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord[] findByUuid_PrevAndNext(long recordId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddlRecord, uuid,
					orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByUuid_PrevAndNext(session, ddlRecord, uuid,
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

	protected DDLRecord getByUuid_PrevAndNext(Session session,
		DDLRecord ddlRecord, String uuid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d d l record where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByUUID_G(uuid, groupId);

		if (ddlRecord == null) {
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

			throw new NoSuchRecordException(msg.toString());
		}

		return ddlRecord;
	}

	/**
	 * Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDLRecord) {
			DDLRecord ddlRecord = (DDLRecord)result;

			if (!Validator.equals(uuid, ddlRecord.getUuid()) ||
					(groupId != ddlRecord.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

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

				List<DDLRecord> list = q.list();

				result = list;

				DDLRecord ddlRecord = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					ddlRecord = list.get(0);

					cacheResult(ddlRecord);

					if ((ddlRecord.getUuid() == null) ||
							!ddlRecord.getUuid().equals(uuid) ||
							(ddlRecord.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddlRecord);
					}
				}

				return ddlRecord;
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
				return (DDLRecord)result;
			}
		}
	}

	/**
	 * Returns all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<DDLRecord> list = (List<DDLRecord>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDLRecord ddlRecord : list) {
				if (!Validator.equals(uuid, ddlRecord.getUuid()) ||
						(companyId != ddlRecord.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

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

				list = (List<DDLRecord>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDLRecord> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DDLRecord> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord[] findByUuid_C_PrevAndNext(long recordId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddlRecord, uuid,
					companyId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByUuid_C_PrevAndNext(session, ddlRecord, uuid,
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

	protected DDLRecord getByUuid_C_PrevAndNext(Session session,
		DDLRecord ddlRecord, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d l records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d l records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<DDLRecord> list = (List<DDLRecord>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDLRecord ddlRecord : list) {
				if ((companyId != ddlRecord.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

				qPos.add(companyId);

				list = (List<DDLRecord>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDLRecord> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByCompanyId_Last(companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<DDLRecord> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord[] findByCompanyId_PrevAndNext(long recordId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, ddlRecord,
					companyId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByCompanyId_PrevAndNext(session, ddlRecord,
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

	protected DDLRecord getByCompanyId_PrevAndNext(Session session,
		DDLRecord ddlRecord, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d l records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByRecordSetId(long recordSetId)
		throws SystemException {
		return findByRecordSetId(recordSetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByRecordSetId(long recordSetId, int start,
		int end) throws SystemException {
		return findByRecordSetId(recordSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByRecordSetId(long recordSetId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID;
			finderArgs = new Object[] { recordSetId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RECORDSETID;
			finderArgs = new Object[] { recordSetId, start, end, orderByComparator };
		}

		List<DDLRecord> list = (List<DDLRecord>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDLRecord ddlRecord : list) {
				if ((recordSetId != ddlRecord.getRecordSetId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

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

				qPos.add(recordSetId);

				list = (List<DDLRecord>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByRecordSetId_First(long recordSetId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByRecordSetId_First(recordSetId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByRecordSetId_First(long recordSetId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDLRecord> list = findByRecordSetId(recordSetId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByRecordSetId_Last(long recordSetId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByRecordSetId_Last(recordSetId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByRecordSetId_Last(long recordSetId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByRecordSetId(recordSetId);

		List<DDLRecord> list = findByRecordSetId(recordSetId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord[] findByRecordSetId_PrevAndNext(long recordId,
		long recordSetId, OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByRecordSetId_PrevAndNext(session, ddlRecord,
					recordSetId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByRecordSetId_PrevAndNext(session, ddlRecord,
					recordSetId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByRecordSetId_PrevAndNext(Session session,
		DDLRecord ddlRecord, long recordSetId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

		query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

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

		qPos.add(recordSetId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByR_U(long recordSetId, long userId)
		throws SystemException {
		return findByR_U(recordSetId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByR_U(long recordSetId, long userId, int start,
		int end) throws SystemException {
		return findByR_U(recordSetId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findByR_U(long recordSetId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U;
			finderArgs = new Object[] { recordSetId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_U;
			finderArgs = new Object[] {
					recordSetId, userId,
					
					start, end, orderByComparator
				};
		}

		List<DDLRecord> list = (List<DDLRecord>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDLRecord ddlRecord : list) {
				if ((recordSetId != ddlRecord.getRecordSetId()) ||
						(userId != ddlRecord.getUserId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

			query.append(_FINDER_COLUMN_R_U_USERID_2);

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

				qPos.add(recordSetId);

				qPos.add(userId);

				list = (List<DDLRecord>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByR_U_First(long recordSetId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByR_U_First(recordSetId, userId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByR_U_First(long recordSetId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDLRecord> list = findByR_U(recordSetId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord findByR_U_Last(long recordSetId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = fetchByR_U_Last(recordSetId, userId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord fetchByR_U_Last(long recordSetId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByR_U(recordSetId, userId);

		List<DDLRecord> list = findByR_U(recordSetId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord[] findByR_U_PrevAndNext(long recordId, long recordSetId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByR_U_PrevAndNext(session, ddlRecord, recordSetId,
					userId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByR_U_PrevAndNext(session, ddlRecord, recordSetId,
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

	protected DDLRecord getByR_U_PrevAndNext(Session session,
		DDLRecord ddlRecord, long recordSetId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

		query.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

		query.append(_FINDER_COLUMN_R_U_USERID_2);

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

		qPos.add(recordSetId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d l records.
	 *
	 * @return the d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLRecord> findAll(int start, int end,
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

		List<DDLRecord> list = (List<DDLRecord>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDLRECORD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDLRECORD;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d d l records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDLRecord ddlRecord : findByUuid(uuid)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Removes the d d l record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d l record that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDLRecord removeByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException, SystemException {
		DDLRecord ddlRecord = findByUUID_G(uuid, groupId);

		return remove(ddlRecord);
	}

	/**
	 * Removes all the d d l records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DDLRecord ddlRecord : findByUuid_C(uuid, companyId)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Removes all the d d l records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (DDLRecord ddlRecord : findByCompanyId(companyId)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Removes all the d d l records where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRecordSetId(long recordSetId) throws SystemException {
		for (DDLRecord ddlRecord : findByRecordSetId(recordSetId)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Removes all the d d l records where recordSetId = &#63; and userId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByR_U(long recordSetId, long userId)
		throws SystemException {
		for (DDLRecord ddlRecord : findByR_U(recordSetId, userId)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Removes all the d d l records from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDLRecord ddlRecord : findAll()) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

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
	 * Returns the number of d d l records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

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
	 * Returns the number of d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

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
	 * Returns the number of d d l records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d l records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRecordSetId(long recordSetId) throws SystemException {
		Object[] finderArgs = new Object[] { recordSetId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RECORDSETID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RECORDSETID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the number of matching d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_U(long recordSetId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { recordSetId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

			query.append(_FINDER_COLUMN_R_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d l records.
	 *
	 * @return the number of d d l records
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDLRECORD);

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
	 * Initializes the d d l record persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatalists.model.DDLRecord")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDLRecord>> listenersList = new ArrayList<ModelListener<DDLRecord>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDLRecord>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDLRecordImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DDLRecordPersistence.class)
	protected DDLRecordPersistence ddlRecordPersistence;
	@BeanReference(type = DDLRecordSetPersistence.class)
	protected DDLRecordSetPersistence ddlRecordSetPersistence;
	@BeanReference(type = DDLRecordVersionPersistence.class)
	protected DDLRecordVersionPersistence ddlRecordVersionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	private static final String _SQL_SELECT_DDLRECORD = "SELECT ddlRecord FROM DDLRecord ddlRecord";
	private static final String _SQL_SELECT_DDLRECORD_WHERE = "SELECT ddlRecord FROM DDLRecord ddlRecord WHERE ";
	private static final String _SQL_COUNT_DDLRECORD = "SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord";
	private static final String _SQL_COUNT_DDLRECORD_WHERE = "SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddlRecord.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddlRecord.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddlRecord.uuid IS NULL OR ddlRecord.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddlRecord.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddlRecord.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddlRecord.uuid IS NULL OR ddlRecord.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddlRecord.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddlRecord.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddlRecord.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddlRecord.uuid IS NULL OR ddlRecord.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddlRecord.companyId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "ddlRecord.companyId = ?";
	private static final String _FINDER_COLUMN_RECORDSETID_RECORDSETID_2 = "ddlRecord.recordSetId = ?";
	private static final String _FINDER_COLUMN_R_U_RECORDSETID_2 = "ddlRecord.recordSetId = ? AND ";
	private static final String _FINDER_COLUMN_R_U_USERID_2 = "ddlRecord.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlRecord.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDLRecord exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDLRecord exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDLRecordPersistenceImpl.class);
	private static DDLRecord _nullDDLRecord = new DDLRecordImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDLRecord> toCacheModel() {
				return _nullDDLRecordCacheModel;
			}
		};

	private static CacheModel<DDLRecord> _nullDDLRecordCacheModel = new CacheModel<DDLRecord>() {
			public DDLRecord toEntityModel() {
				return _nullDDLRecord;
			}
		};
}