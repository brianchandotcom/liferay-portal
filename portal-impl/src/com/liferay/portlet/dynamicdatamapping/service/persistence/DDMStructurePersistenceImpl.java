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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

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
import com.liferay.portal.kernel.util.ArrayUtil;
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

import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypePersistence;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructurePersistence
 * @see DDMStructureUtil
 * @generated
 */
public class DDMStructurePersistenceImpl extends BasePersistenceImpl<DDMStructure>
	implements DDMStructurePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureUtil} to access the d d m structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMStructureModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassNameId",
			new String[] { Long.class.getName() },
			DDMStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_S = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureModelImpl.STRUCTUREKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.COMPANYID_COLUMN_BITMASK |
			DDMStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_D = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureModelImpl.NAME_COLUMN_BITMASK |
			DDMStructureModelImpl.DESCRIPTION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_D = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the d d m structure in the entity cache if it is enabled.
	 *
	 * @param ddmStructure the d d m structure
	 */
	public void cacheResult(DDMStructure ddmStructure) {
		EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureImpl.class, ddmStructure.getPrimaryKey(), ddmStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmStructure.getUuid(), Long.valueOf(ddmStructure.getGroupId())
			}, ddmStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				Long.valueOf(ddmStructure.getGroupId()),
				
			ddmStructure.getStructureKey()
			}, ddmStructure);

		ddmStructure.resetOriginalValues();
	}

	/**
	 * Caches the d d m structures in the entity cache if it is enabled.
	 *
	 * @param ddmStructures the d d m structures
	 */
	public void cacheResult(List<DDMStructure> ddmStructures) {
		for (DDMStructure ddmStructure : ddmStructures) {
			if (EntityCacheUtil.getResult(
						DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureImpl.class, ddmStructure.getPrimaryKey()) == null) {
				cacheResult(ddmStructure);
			}
			else {
				ddmStructure.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m structures.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMStructureImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMStructureImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m structure.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructure ddmStructure) {
		EntityCacheUtil.removeResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureImpl.class, ddmStructure.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ddmStructure);
	}

	@Override
	public void clearCache(List<DDMStructure> ddmStructures) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructure ddmStructure : ddmStructures) {
			EntityCacheUtil.removeResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureImpl.class, ddmStructure.getPrimaryKey());

			clearUniqueFindersCache(ddmStructure);
		}
	}

	protected void clearUniqueFindersCache(DDMStructure ddmStructure) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmStructure.getUuid(), Long.valueOf(ddmStructure.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				Long.valueOf(ddmStructure.getGroupId()),
				
			ddmStructure.getStructureKey()
			});
	}

	/**
	 * Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	 *
	 * @param structureId the primary key for the new d d m structure
	 * @return the new d d m structure
	 */
	public DDMStructure create(long structureId) {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setNew(true);
		ddmStructure.setPrimaryKey(structureId);

		String uuid = PortalUUIDUtil.generate();

		ddmStructure.setUuid(uuid);

		return ddmStructure;
	}

	/**
	 * Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureId the primary key of the d d m structure
	 * @return the d d m structure that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure remove(long structureId)
		throws NoSuchStructureException, SystemException {
		return remove(Long.valueOf(structureId));
	}

	/**
	 * Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure
	 * @return the d d m structure that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure remove(Serializable primaryKey)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMStructure ddmStructure = (DDMStructure)session.get(DDMStructureImpl.class,
					primaryKey);

			if (ddmStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStructure);
		}
		catch (NoSuchStructureException nsee) {
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
	protected DDMStructure removeImpl(DDMStructure ddmStructure)
		throws SystemException {
		ddmStructure = toUnwrappedModel(ddmStructure);

		Session session = null;

		try {
			session = openSession();

			if (ddmStructure.isCachedModel()) {
				ddmStructure = (DDMStructure)session.get(DDMStructureImpl.class,
						ddmStructure.getPrimaryKeyObj());
			}

			if (ddmStructure != null) {
				session.delete(ddmStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructure != null) {
			clearCache(ddmStructure);
		}

		return ddmStructure;
	}

	@Override
	public DDMStructure updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws SystemException {
		ddmStructure = toUnwrappedModel(ddmStructure);

		boolean isNew = ddmStructure.isNew();

		DDMStructureModelImpl ddmStructureModelImpl = (DDMStructureModelImpl)ddmStructure;

		if (Validator.isNull(ddmStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStructure.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmStructure.isNew()) {
				session.save(ddmStructure);

				ddmStructure.setNew(false);
			}
			else {
				session.merge(ddmStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStructureModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmStructureModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalUuid(),
						Long.valueOf(ddmStructureModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmStructureModelImpl.getUuid(),
						Long.valueOf(ddmStructureModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);

				args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getOriginalCompanyId()),
						Long.valueOf(ddmStructureModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getCompanyId()),
						Long.valueOf(ddmStructureModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getOriginalGroupId()),
						
						ddmStructureModelImpl.getOriginalName(),
						
						ddmStructureModelImpl.getOriginalDescription()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D,
					args);

				args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getGroupId()),
						
						ddmStructureModelImpl.getName(),
						
						ddmStructureModelImpl.getDescription()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D,
					args);
			}
		}

		EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureImpl.class, ddmStructure.getPrimaryKey(), ddmStructure);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					ddmStructure.getUuid(),
					Long.valueOf(ddmStructure.getGroupId())
				}, ddmStructure);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					Long.valueOf(ddmStructure.getGroupId()),
					
				ddmStructure.getStructureKey()
				}, ddmStructure);
		}
		else {
			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalUuid(),
						Long.valueOf(ddmStructureModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						ddmStructure.getUuid(),
						Long.valueOf(ddmStructure.getGroupId())
					}, ddmStructure);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStructureModelImpl.getOriginalGroupId()),
						
						ddmStructureModelImpl.getOriginalStructureKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
					new Object[] {
						Long.valueOf(ddmStructure.getGroupId()),
						
					ddmStructure.getStructureKey()
					}, ddmStructure);
			}
		}

		return ddmStructure;
	}

	protected DDMStructure toUnwrappedModel(DDMStructure ddmStructure) {
		if (ddmStructure instanceof DDMStructureImpl) {
			return ddmStructure;
		}

		DDMStructureImpl ddmStructureImpl = new DDMStructureImpl();

		ddmStructureImpl.setNew(ddmStructure.isNew());
		ddmStructureImpl.setPrimaryKey(ddmStructure.getPrimaryKey());

		ddmStructureImpl.setUuid(ddmStructure.getUuid());
		ddmStructureImpl.setStructureId(ddmStructure.getStructureId());
		ddmStructureImpl.setGroupId(ddmStructure.getGroupId());
		ddmStructureImpl.setCompanyId(ddmStructure.getCompanyId());
		ddmStructureImpl.setUserId(ddmStructure.getUserId());
		ddmStructureImpl.setUserName(ddmStructure.getUserName());
		ddmStructureImpl.setCreateDate(ddmStructure.getCreateDate());
		ddmStructureImpl.setModifiedDate(ddmStructure.getModifiedDate());
		ddmStructureImpl.setParentStructureId(ddmStructure.getParentStructureId());
		ddmStructureImpl.setClassNameId(ddmStructure.getClassNameId());
		ddmStructureImpl.setStructureKey(ddmStructure.getStructureKey());
		ddmStructureImpl.setName(ddmStructure.getName());
		ddmStructureImpl.setDescription(ddmStructure.getDescription());
		ddmStructureImpl.setXsd(ddmStructure.getXsd());
		ddmStructureImpl.setStorageType(ddmStructure.getStorageType());
		ddmStructureImpl.setType(ddmStructure.getType());

		return ddmStructureImpl;
	}

	/**
	 * Returns the d d m structure with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure
	 * @return the d d m structure
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m structure with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureException} if it could not be found.
	 *
	 * @param structureId the primary key of the d d m structure
	 * @return the d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByPrimaryKey(long structureId)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByPrimaryKey(structureId);

		if (ddmStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + structureId);
			}

			throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				structureId);
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure
	 * @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureId the primary key of the d d m structure
	 * @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByPrimaryKey(long structureId)
		throws SystemException {
		DDMStructure ddmStructure = (DDMStructure)EntityCacheUtil.getResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureImpl.class, structureId);

		if (ddmStructure == _nullDDMStructure) {
			return null;
		}

		if (ddmStructure == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ddmStructure = (DDMStructure)session.get(DDMStructureImpl.class,
						Long.valueOf(structureId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ddmStructure != null) {
					cacheResult(ddmStructure);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureImpl.class, structureId, _nullDDMStructure);
				}

				closeSession(session);
			}
		}

		return ddmStructure;
	}

	/**
	 * Returns all the d d m structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByUuid(String uuid, int start, int end,
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!Validator.equals(uuid, ddmStructure.getUuid())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByUuid_First(uuid, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStructure> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByUuid_Last(uuid, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DDMStructure> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] findByUuid_PrevAndNext(long structureId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmStructure, uuid,
					orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByUuid_PrevAndNext(session, ddmStructure, uuid,
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

	protected DDMStructure getByUuid_PrevAndNext(Session session,
		DDMStructure ddmStructure, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d d m structure where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByUUID_G(uuid, groupId);

		if (ddmStructure == null) {
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

			throw new NoSuchStructureException(msg.toString());
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMStructure) {
			DDMStructure ddmStructure = (DDMStructure)result;

			if (!Validator.equals(uuid, ddmStructure.getUuid()) ||
					(groupId != ddmStructure.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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

				List<DDMStructure> list = q.list();

				result = list;

				DDMStructure ddmStructure = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					ddmStructure = list.get(0);

					cacheResult(ddmStructure);

					if ((ddmStructure.getUuid() == null) ||
							!ddmStructure.getUuid().equals(uuid) ||
							(ddmStructure.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmStructure);
					}
				}

				return ddmStructure;
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
				return (DDMStructure)result;
			}
		}
	}

	/**
	 * Returns all the d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByUuid_C(String uuid, long companyId,
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!Validator.equals(uuid, ddmStructure.getUuid()) ||
						(companyId != ddmStructure.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStructure> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DDMStructure> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] findByUuid_C_PrevAndNext(long structureId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmStructure, uuid,
					companyId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByUuid_C_PrevAndNext(session, ddmStructure, uuid,
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

	protected DDMStructure getByUuid_C_PrevAndNext(Session session,
		DDMStructure ddmStructure, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByGroupId(long groupId, int start, int end,
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((groupId != ddmStructure.getGroupId())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByGroupId_First(groupId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStructure> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<DDMStructure> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] findByGroupId_PrevAndNext(long structureId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, ddmStructure, groupId,
					orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByGroupId_PrevAndNext(session, ddmStructure, groupId,
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

	protected DDMStructure getByGroupId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByGroupId(long[] groupIds)
		throws SystemException {
		return findByGroupId(groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByGroupId(long[] groupIds, int start, int end)
		throws SystemException {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] { StringUtil.merge(groupIds) };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!ArrayUtil.contains(groupIds, ddmStructure.getGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			boolean conjunctionable = false;

			if ((groupIds == null) || (groupIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < groupIds.length; i++) {
					query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

					if ((i + 1) < groupIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
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

				if (groupIds != null) {
					qPos.add(groupIds);
				}

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns all the d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] filterFindByGroupId_PrevAndNext(long structureId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(structureId, groupId,
				orderByComparator);
		}

		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, ddmStructure,
					groupId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = filterGetByGroupId_PrevAndNext(session, ddmStructure,
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

	protected DDMStructure filterGetByGroupId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId,
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
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByGroupId(long[] groupIds)
		throws SystemException {
		return filterFindByGroupId(groupIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByGroupId(long[] groupIds, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByGroupId(long[] groupIds, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByGroupId(groupIds, start, end, orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if ((groupIds == null) || (groupIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < groupIds.length; i++) {
				query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

				if ((i + 1) < groupIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the d d m structures where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByClassNameId(long classNameId)
		throws SystemException {
		return findByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByClassNameId(long classNameId, int start,
		int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId, start, end, orderByComparator };
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((classNameId != ddmStructure.getClassNameId())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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

				qPos.add(classNameId);

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByClassNameId_First(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByClassNameId_First(classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByClassNameId_First(long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStructure> list = findByClassNameId(classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByClassNameId_Last(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByClassNameId_Last(classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByClassNameId_Last(long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByClassNameId(classNameId);

		List<DDMStructure> list = findByClassNameId(classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] findByClassNameId_PrevAndNext(long structureId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByClassNameId_PrevAndNext(session, ddmStructure,
					classNameId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByClassNameId_PrevAndNext(session, ddmStructure,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByClassNameId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d d m structure where groupId = &#63; and structureKey = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param structureKey the structure key
	 * @return the matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByG_S(long groupId, String structureKey)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByG_S(groupId, structureKey);

		if (ddmStructure == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureKey=");
			msg.append(structureKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureException(msg.toString());
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure where groupId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param structureKey the structure key
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByG_S(long groupId, String structureKey)
		throws SystemException {
		return fetchByG_S(groupId, structureKey, true);
	}

	/**
	 * Returns the d d m structure where groupId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param structureKey the structure key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByG_S(long groupId, String structureKey,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_S,
					finderArgs, this);
		}

		if (result instanceof DDMStructure) {
			DDMStructure ddmStructure = (DDMStructure)result;

			if ((groupId != ddmStructure.getGroupId()) ||
					!Validator.equals(structureKey,
						ddmStructure.getStructureKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureKey == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREKEY_1);
			}
			else {
				if (structureKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureKey != null) {
					qPos.add(structureKey);
				}

				List<DDMStructure> list = q.list();

				result = list;

				DDMStructure ddmStructure = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
						finderArgs, list);
				}
				else {
					ddmStructure = list.get(0);

					cacheResult(ddmStructure);

					if ((ddmStructure.getGroupId() != groupId) ||
							(ddmStructure.getStructureKey() == null) ||
							!ddmStructure.getStructureKey().equals(structureKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
							finderArgs, ddmStructure);
					}
				}

				return ddmStructure;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
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
				return (DDMStructure)result;
			}
		}
	}

	/**
	 * Returns all the d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByC_C(long companyId, long classNameId)
		throws SystemException {
		return findByC_C(companyId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByC_C(long companyId, long classNameId,
		int start, int end) throws SystemException {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { companyId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					companyId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((companyId != ddmStructure.getCompanyId()) ||
						(classNameId != ddmStructure.getClassNameId())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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

				qPos.add(classNameId);

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByC_C_First(long companyId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByC_C_First(companyId, classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByC_C_First(long companyId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStructure> list = findByC_C(companyId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByC_C_Last(long companyId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByC_C_Last(companyId, classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByC_C_Last(long companyId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(companyId, classNameId);

		List<DDMStructure> list = findByC_C(companyId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] findByC_C_PrevAndNext(long structureId,
		long companyId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByC_C_PrevAndNext(session, ddmStructure, companyId,
					classNameId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByC_C_PrevAndNext(session, ddmStructure, companyId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByC_C_PrevAndNext(Session session,
		DDMStructure ddmStructure, long companyId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByG_N_D(long groupId, String name,
		String description) throws SystemException {
		return findByG_N_D(groupId, name, description, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByG_N_D(long groupId, String name,
		String description, int start, int end) throws SystemException {
		return findByG_N_D(groupId, name, description, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findByG_N_D(long groupId, String name,
		String description, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D;
			finderArgs = new Object[] { groupId, name, description };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_D;
			finderArgs = new Object[] {
					groupId, name, description,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((groupId != ddmStructure.getGroupId()) ||
						!Validator.equals(name, ddmStructure.getName()) ||
						!Validator.equals(description,
							ddmStructure.getDescription())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_NAME_2);
				}
			}

			if (description == null) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
			}
			else {
				if (description.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
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

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (description != null) {
					qPos.add(description);
				}

				list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByG_N_D_First(long groupId, String name,
		String description, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByG_N_D_First(groupId, name,
				description, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", description=");
		msg.append(description);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByG_N_D_First(long groupId, String name,
		String description, OrderByComparator orderByComparator)
		throws SystemException {
		List<DDMStructure> list = findByG_N_D(groupId, name, description, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure findByG_N_D_Last(long groupId, String name,
		String description, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = fetchByG_N_D_Last(groupId, name,
				description, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", description=");
		msg.append(description);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchByG_N_D_Last(long groupId, String name,
		String description, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_N_D(groupId, name, description);

		List<DDMStructure> list = findByG_N_D(groupId, name, description,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] findByG_N_D_PrevAndNext(long structureId,
		long groupId, String name, String description,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByG_N_D_PrevAndNext(session, ddmStructure, groupId,
					name, description, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByG_N_D_PrevAndNext(session, ddmStructure, groupId,
					name, description, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByG_N_D_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, String name,
		String description, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
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

		qPos.add(groupId);

		if (name != null) {
			qPos.add(name);
		}

		if (description != null) {
			qPos.add(description);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByG_N_D(long groupId, String name,
		String description) throws SystemException {
		return filterFindByG_N_D(groupId, name, description, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByG_N_D(long groupId, String name,
		String description, int start, int end) throws SystemException {
		return filterFindByG_N_D(groupId, name, description, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> filterFindByG_N_D(long groupId, String name,
		String description, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_D(groupId, name, description, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (name != null) {
				qPos.add(name);
			}

			if (description != null) {
				qPos.add(description);
			}

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure[] filterFindByG_N_D_PrevAndNext(long structureId,
		long groupId, String name, String description,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_D_PrevAndNext(structureId, groupId, name,
				description, orderByComparator);
		}

		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = filterGetByG_N_D_PrevAndNext(session, ddmStructure,
					groupId, name, description, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = filterGetByG_N_D_PrevAndNext(session, ddmStructure,
					groupId, name, description, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure filterGetByG_N_D_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, String name,
		String description, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (name != null) {
			qPos.add(name);
		}

		if (description != null) {
			qPos.add(description);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures.
	 *
	 * @return the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> findAll(int start, int end,
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTURE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d d m structures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDMStructure ddmStructure : findByUuid(uuid)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Removes the d d m structure where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m structure that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByUUID_G(uuid, groupId);

		return remove(ddmStructure);
	}

	/**
	 * Removes all the d d m structures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DDMStructure ddmStructure : findByUuid_C(uuid, companyId)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Removes all the d d m structures where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DDMStructure ddmStructure : findByGroupId(groupId)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Removes all the d d m structures where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByClassNameId(long classNameId) throws SystemException {
		for (DDMStructure ddmStructure : findByClassNameId(classNameId)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Removes the d d m structure where groupId = &#63; and structureKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param structureKey the structure key
	 * @return the d d m structure that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure removeByG_S(long groupId, String structureKey)
		throws NoSuchStructureException, SystemException {
		DDMStructure ddmStructure = findByG_S(groupId, structureKey);

		return remove(ddmStructure);
	}

	/**
	 * Removes all the d d m structures where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		for (DDMStructure ddmStructure : findByC_C(companyId, classNameId)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Removes all the d d m structures where groupId = &#63; and name = &#63; and description = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_N_D(long groupId, String name, String description)
		throws SystemException {
		for (DDMStructure ddmStructure : findByG_N_D(groupId, name, description)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Removes all the d d m structures from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMStructure ddmStructure : findAll()) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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
	 * Returns the number of d d m structures where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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
	 * Returns the number of d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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
	 * Returns the number of d d m structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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
	 * Returns the number of d d m structures where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long[] groupIds) throws SystemException {
		Object[] finderArgs = new Object[] { StringUtil.merge(groupIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			boolean conjunctionable = false;

			if ((groupIds == null) || (groupIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < groupIds.length; i++) {
					query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

					if ((i + 1) < groupIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (groupIds != null) {
					qPos.add(groupIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
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
	 * Returns the number of d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long[] groupIds) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByGroupId(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		boolean conjunctionable = false;

		if ((groupIds == null) || (groupIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < groupIds.length; i++) {
				query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

				if ((i + 1) < groupIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupIds != null) {
				qPos.add(groupIds);
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
	 * Returns the number of d d m structures where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassNameId(long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures where groupId = &#63; and structureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureKey the structure key
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureKey)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureKey == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREKEY_1);
			}
			else {
				if (structureKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureKey != null) {
					qPos.add(structureKey);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

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
	 * Returns the number of d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the number of matching d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N_D(long groupId, String name, String description)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name, description };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N_D,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_NAME_2);
				}
			}

			if (description == null) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
			}
			else {
				if (description.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (description != null) {
					qPos.add(description);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N_D,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the number of matching d d m structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_N_D(long groupId, String name, String description)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_D(groupId, name, description);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (name != null) {
				qPos.add(name);
			}

			if (description != null) {
				qPos.add(description);
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
	 * Returns the number of d d m structures.
	 *
	 * @return the number of d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTURE);

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
	 * Initializes the d d m structure persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMStructure")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMStructure>> listenersList = new ArrayList<ModelListener<DDMStructure>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMStructure>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDMStructureImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = DDMStorageLinkPersistence.class)
	protected DDMStorageLinkPersistence ddmStorageLinkPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = DDMTemplatePersistence.class)
	protected DDMTemplatePersistence ddmTemplatePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	private static final String _SQL_SELECT_DDMSTRUCTURE = "SELECT ddmStructure FROM DDMStructure ddmStructure";
	private static final String _SQL_SELECT_DDMSTRUCTURE_WHERE = "SELECT ddmStructure FROM DDMStructure ddmStructure WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTURE = "SELECT COUNT(ddmStructure) FROM DDMStructure ddmStructure";
	private static final String _SQL_COUNT_DDMSTRUCTURE_WHERE = "SELECT COUNT(ddmStructure) FROM DDMStructure ddmStructure WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmStructure.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmStructure.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmStructure.uuid IS NULL OR ddmStructure.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmStructure.uuid IS NULL OR ddmStructure.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmStructure.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmStructure.uuid IS NULL OR ddmStructure.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmStructure.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmStructure.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_GROUPID_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 = "ddmStructure.classNameId = ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "ddmStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREKEY_1 = "ddmStructure.structureKey IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREKEY_2 = "ddmStructure.structureKey = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREKEY_3 = "(ddmStructure.structureKey IS NULL OR ddmStructure.structureKey = ?)";
	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "ddmStructure.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ddmStructure.classNameId = ?";
	private static final String _FINDER_COLUMN_G_N_D_GROUPID_2 = "ddmStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_1 = "ddmStructure.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_2 = "ddmStructure.name = ? AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_3 = "(ddmStructure.name IS NULL OR ddmStructure.name = ?) AND ";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_1 = "ddmStructure.description IS NULL";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_2 = "ddmStructure.description = ?";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_3 = "(ddmStructure.description IS NULL OR ddmStructure.description = ?)";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "ddmStructure.structureId";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE = "SELECT DISTINCT {ddmStructure.*} FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DDMStructure.*} FROM (SELECT DISTINCT ddmStructure.structureId FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DDMStructure ON TEMP_TABLE.structureId = DDMStructure.structureId";
	private static final String _FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE = "SELECT COUNT(DISTINCT ddmStructure.structureId) AS COUNT_VALUE FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "ddmStructure";
	private static final String _FILTER_ENTITY_TABLE = "DDMStructure";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructure.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DDMStructure.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructure exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructure exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMStructurePersistenceImpl.class);
	private static DDMStructure _nullDDMStructure = new DDMStructureImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStructure> toCacheModel() {
				return _nullDDMStructureCacheModel;
			}
		};

	private static CacheModel<DDMStructure> _nullDDMStructureCacheModel = new CacheModel<DDMStructure>() {
			public DDMStructure toEntityModel() {
				return _nullDDMStructure;
			}
		};
}