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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.trash.service.persistence.TrashEntryPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the document library folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderPersistence
 * @see DLFolderUtil
 * @generated
 */
public class DLFolderPersistenceImpl extends BasePersistenceImpl<DLFolder>
	implements DLFolderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFolderUtil} to access the document library folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFolderModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFolderModelImpl.UUID_COLUMN_BITMASK |
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFolderModelImpl.UUID_COLUMN_BITMASK |
			DLFolderModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DLFolderModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_REPOSITORYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByRepositoryId",
			new String[] { Long.class.getName() },
			DLFolderModelImpl.REPOSITORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_REPOSITORYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N",
			new String[] { Long.class.getName(), String.class.getName() },
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_M_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_S = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_S = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_S = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M_P_H = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_H =
		new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.HIDDEN_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_M_P_H = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M_P_S = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_S =
		new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_M_P_S = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the document library folder in the entity cache if it is enabled.
	 *
	 * @param dlFolder the document library folder
	 */
	public void cacheResult(DLFolder dlFolder) {
		EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey(), dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFolder.getUuid(), Long.valueOf(
					dlFolder.getGroupId()) }, dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
			new Object[] { Long.valueOf(dlFolder.getRepositoryId()) }, dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				Long.valueOf(dlFolder.getGroupId()),
				Long.valueOf(dlFolder.getParentFolderId()),
				
			dlFolder.getName()
			}, dlFolder);

		dlFolder.resetOriginalValues();
	}

	/**
	 * Caches the document library folders in the entity cache if it is enabled.
	 *
	 * @param dlFolders the document library folders
	 */
	public void cacheResult(List<DLFolder> dlFolders) {
		for (DLFolder dlFolder : dlFolders) {
			if (EntityCacheUtil.getResult(
						DLFolderModelImpl.ENTITY_CACHE_ENABLED,
						DLFolderImpl.class, dlFolder.getPrimaryKey()) == null) {
				cacheResult(dlFolder);
			}
			else {
				dlFolder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library folders.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLFolderImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLFolderImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library folder.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFolder dlFolder) {
		EntityCacheUtil.removeResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(dlFolder);
	}

	@Override
	public void clearCache(List<DLFolder> dlFolders) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFolder dlFolder : dlFolders) {
			EntityCacheUtil.removeResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
				DLFolderImpl.class, dlFolder.getPrimaryKey());

			clearUniqueFindersCache(dlFolder);
		}
	}

	protected void clearUniqueFindersCache(DLFolder dlFolder) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFolder.getUuid(), Long.valueOf(
					dlFolder.getGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
			new Object[] { Long.valueOf(dlFolder.getRepositoryId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				Long.valueOf(dlFolder.getGroupId()),
				Long.valueOf(dlFolder.getParentFolderId()),
				
			dlFolder.getName()
			});
	}

	/**
	 * Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	 *
	 * @param folderId the primary key for the new document library folder
	 * @return the new document library folder
	 */
	public DLFolder create(long folderId) {
		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setNew(true);
		dlFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		dlFolder.setUuid(uuid);

		return dlFolder;
	}

	/**
	 * Removes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		return remove(Long.valueOf(folderId));
	}

	/**
	 * Removes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library folder
	 * @return the document library folder that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFolder remove(Serializable primaryKey)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFolder dlFolder = (DLFolder)session.get(DLFolderImpl.class,
					primaryKey);

			if (dlFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFolder);
		}
		catch (NoSuchFolderException nsee) {
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
	protected DLFolder removeImpl(DLFolder dlFolder) throws SystemException {
		dlFolder = toUnwrappedModel(dlFolder);

		try {
			clearDLFileEntryTypes.clear(dlFolder.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFolder.isCachedModel()) {
				dlFolder = (DLFolder)session.get(DLFolderImpl.class,
						dlFolder.getPrimaryKeyObj());
			}

			if (dlFolder != null) {
				session.delete(dlFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFolder != null) {
			clearCache(dlFolder);
		}

		return dlFolder;
	}

	@Override
	public DLFolder updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws SystemException {
		dlFolder = toUnwrappedModel(dlFolder);

		boolean isNew = dlFolder.isNew();

		DLFolderModelImpl dlFolderModelImpl = (DLFolderModelImpl)dlFolder;

		if (Validator.isNull(dlFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFolder.isNew()) {
				session.save(dlFolder);

				dlFolder.setNew(false);
			}
			else {
				session.merge(dlFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFolderModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { dlFolderModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFolderModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFolderModelImpl.getOriginalUuid(),
						Long.valueOf(dlFolderModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFolderModelImpl.getUuid(),
						Long.valueOf(dlFolderModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { Long.valueOf(dlFolderModelImpl.getGroupId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getGroupId()),
						Long.valueOf(dlFolderModelImpl.getParentFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId()),
						
						dlFolderModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getParentFolderId()),
						
						dlFolderModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId()),
						Boolean.valueOf(dlFolderModelImpl.getOriginalMountPoint()),
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getGroupId()),
						Boolean.valueOf(dlFolderModelImpl.getMountPoint()),
						Long.valueOf(dlFolderModelImpl.getParentFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId()),
						Integer.valueOf(dlFolderModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_S,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getGroupId()),
						Long.valueOf(dlFolderModelImpl.getParentFolderId()),
						Integer.valueOf(dlFolderModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_S,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId()),
						Boolean.valueOf(dlFolderModelImpl.getOriginalMountPoint()),
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId()),
						Boolean.valueOf(dlFolderModelImpl.getOriginalHidden())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M_P_H, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_H,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getGroupId()),
						Boolean.valueOf(dlFolderModelImpl.getMountPoint()),
						Long.valueOf(dlFolderModelImpl.getParentFolderId()),
						Boolean.valueOf(dlFolderModelImpl.getHidden())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M_P_H, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_H,
					args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId()),
						Boolean.valueOf(dlFolderModelImpl.getOriginalMountPoint()),
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId()),
						Integer.valueOf(dlFolderModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_S,
					args);

				args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getGroupId()),
						Boolean.valueOf(dlFolderModelImpl.getMountPoint()),
						Long.valueOf(dlFolderModelImpl.getParentFolderId()),
						Integer.valueOf(dlFolderModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_S,
					args);
			}
		}

		EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey(), dlFolder);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFolder.getUuid(), Long.valueOf(dlFolder.getGroupId())
				}, dlFolder);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
				new Object[] { Long.valueOf(dlFolder.getRepositoryId()) },
				dlFolder);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					Long.valueOf(dlFolder.getGroupId()),
					Long.valueOf(dlFolder.getParentFolderId()),
					
				dlFolder.getName()
				}, dlFolder);
		}
		else {
			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFolderModelImpl.getOriginalUuid(),
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						dlFolder.getUuid(), Long.valueOf(dlFolder.getGroupId())
					}, dlFolder);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_REPOSITORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalRepositoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
					new Object[] { Long.valueOf(dlFolder.getRepositoryId()) },
					dlFolder);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_P_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFolderModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFolderModelImpl.getOriginalParentFolderId()),
						
						dlFolderModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
					new Object[] {
						Long.valueOf(dlFolder.getGroupId()),
						Long.valueOf(dlFolder.getParentFolderId()),
						
					dlFolder.getName()
					}, dlFolder);
			}
		}

		return dlFolder;
	}

	protected DLFolder toUnwrappedModel(DLFolder dlFolder) {
		if (dlFolder instanceof DLFolderImpl) {
			return dlFolder;
		}

		DLFolderImpl dlFolderImpl = new DLFolderImpl();

		dlFolderImpl.setNew(dlFolder.isNew());
		dlFolderImpl.setPrimaryKey(dlFolder.getPrimaryKey());

		dlFolderImpl.setUuid(dlFolder.getUuid());
		dlFolderImpl.setFolderId(dlFolder.getFolderId());
		dlFolderImpl.setGroupId(dlFolder.getGroupId());
		dlFolderImpl.setCompanyId(dlFolder.getCompanyId());
		dlFolderImpl.setUserId(dlFolder.getUserId());
		dlFolderImpl.setUserName(dlFolder.getUserName());
		dlFolderImpl.setCreateDate(dlFolder.getCreateDate());
		dlFolderImpl.setModifiedDate(dlFolder.getModifiedDate());
		dlFolderImpl.setRepositoryId(dlFolder.getRepositoryId());
		dlFolderImpl.setMountPoint(dlFolder.isMountPoint());
		dlFolderImpl.setParentFolderId(dlFolder.getParentFolderId());
		dlFolderImpl.setName(dlFolder.getName());
		dlFolderImpl.setDescription(dlFolder.getDescription());
		dlFolderImpl.setLastPostDate(dlFolder.getLastPostDate());
		dlFolderImpl.setDefaultFileEntryTypeId(dlFolder.getDefaultFileEntryTypeId());
		dlFolderImpl.setHidden(dlFolder.isHidden());
		dlFolderImpl.setOverrideFileEntryTypes(dlFolder.isOverrideFileEntryTypes());
		dlFolderImpl.setStatus(dlFolder.getStatus());
		dlFolderImpl.setStatusByUserId(dlFolder.getStatusByUserId());
		dlFolderImpl.setStatusByUserName(dlFolder.getStatusByUserName());
		dlFolderImpl.setStatusDate(dlFolder.getStatusDate());

		return dlFolderImpl;
	}

	/**
	 * Returns the document library folder with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library folder
	 * @return the document library folder
	 * @throws com.liferay.portal.NoSuchModelException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library folder with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByPrimaryKey(folderId);

		if (dlFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + folderId);
			}

			throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				folderId);
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library folder
	 * @return the document library folder, or <code>null</code> if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFolder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder, or <code>null</code> if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByPrimaryKey(long folderId) throws SystemException {
		DLFolder dlFolder = (DLFolder)EntityCacheUtil.getResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
				DLFolderImpl.class, folderId);

		if (dlFolder == _nullDLFolder) {
			return null;
		}

		if (dlFolder == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlFolder = (DLFolder)session.get(DLFolderImpl.class,
						Long.valueOf(folderId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlFolder != null) {
					cacheResult(dlFolder);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
						DLFolderImpl.class, folderId, _nullDLFolder);
				}

				closeSession(session);
			}
		}

		return dlFolder;
	}

	/**
	 * Returns all the document library folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid(String uuid, int start, int end,
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

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if (!Validator.equals(uuid, dlFolder.getUuid())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUuid_First(uuid, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFolder> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DLFolder> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFolder, uuid,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByUuid_PrevAndNext(session, dlFolder, uuid,
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

	protected DLFolder getByUuid_PrevAndNext(Session session,
		DLFolder dlFolder, String uuid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUUID_G(uuid, groupId);

		if (dlFolder == null) {
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

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)result;

			if (!Validator.equals(uuid, dlFolder.getUuid()) ||
					(groupId != dlFolder.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

			query.append(DLFolderModelImpl.ORDER_BY_JPQL);

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

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getUuid() == null) ||
							!dlFolder.getUuid().equals(uuid) ||
							(dlFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
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
				return (DLFolder)result;
			}
		}
	}

	/**
	 * Returns all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid_C(String uuid, long companyId, int start,
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

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if (!Validator.equals(uuid, dlFolder.getUuid()) ||
						(companyId != dlFolder.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFolder> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DLFolder> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByUuid_C_PrevAndNext(long folderId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFolder, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByUuid_C_PrevAndNext(session, dlFolder, uuid,
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

	protected DLFolder getByUuid_C_PrevAndNext(Session session,
		DLFolder dlFolder, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByGroupId(long groupId, int start, int end,
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

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((groupId != dlFolder.getGroupId())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByGroupId_First(groupId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFolder> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByGroupId_Last(groupId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<DLFolder> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByGroupId_PrevAndNext(long folderId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlFolder, groupId,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByGroupId_PrevAndNext(session, dlFolder, groupId,
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

	protected DLFolder getByGroupId_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] filterFindByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(folderId, groupId,
				orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, dlFolder,
					groupId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByGroupId_PrevAndNext(session, dlFolder,
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

	protected DLFolder filterGetByGroupId_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByCompanyId(long companyId, int start, int end,
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

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((companyId != dlFolder.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByCompanyId_First(companyId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFolder> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByCompanyId_Last(companyId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<DLFolder> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFolder, companyId,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByCompanyId_PrevAndNext(session, dlFolder, companyId,
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

	protected DLFolder getByCompanyId_PrevAndNext(Session session,
		DLFolder dlFolder, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

		else {
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library folder where repositoryId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByRepositoryId(long repositoryId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByRepositoryId(repositoryId);

		if (dlFolder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repositoryId=");
			msg.append(repositoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder where repositoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByRepositoryId(long repositoryId)
		throws SystemException {
		return fetchByRepositoryId(repositoryId, true);
	}

	/**
	 * Returns the document library folder where repositoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByRepositoryId(long repositoryId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
					finderArgs, this);
		}

		if (result instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)result;

			if ((repositoryId != dlFolder.getRepositoryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			query.append(DLFolderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getRepositoryId() != repositoryId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_REPOSITORYID,
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
				return (DLFolder)result;
			}
		}
	}

	/**
	 * Returns all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return findByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, parentFolderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, parentFolderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((groupId != dlFolder.getGroupId()) ||
						(parentFolderId != dlFolder.getParentFolderId())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_First(groupId, parentFolderId,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_First(long groupId, long parentFolderId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFolder> list = findByG_P(groupId, parentFolderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_Last(groupId, parentFolderId,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<DLFolder> list = findByG_P(groupId, parentFolderId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_P_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_P_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_P_PrevAndNext(Session session, DLFolder dlFolder,
		long groupId, long parentFolderId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentFolderId, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] filterFindByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
				orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_P_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder filterGetByG_P_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, long parentFolderId,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByP_N(long parentFolderId, String name)
		throws SystemException {
		return findByP_N(parentFolderId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByP_N(long parentFolderId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentFolderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByP_N(long parentFolderId, String name,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N;
			finderArgs = new Object[] { parentFolderId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_N;
			finderArgs = new Object[] {
					parentFolderId, name,
					
					start, end, orderByComparator
				};
		}

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((parentFolderId != dlFolder.getParentFolderId()) ||
						!Validator.equals(name, dlFolder.getName())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByP_N_First(long parentFolderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByP_N_First(parentFolderId, name,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByP_N_First(long parentFolderId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFolder> list = findByP_N(parentFolderId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByP_N_Last(long parentFolderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByP_N_Last(parentFolderId, name,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByP_N_Last(long parentFolderId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByP_N(parentFolderId, name);

		List<DLFolder> list = findByP_N(parentFolderId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByP_N_PrevAndNext(long folderId, long parentFolderId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByP_N_PrevAndNext(session, dlFolder, parentFolderId,
					name, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByP_N_PrevAndNext(session, dlFolder, parentFolderId,
					name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByP_N_PrevAndNext(Session session, DLFolder dlFolder,
		long parentFolderId, String name, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_P_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_P_N_NAME_2);
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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentFolderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId) throws SystemException {
		return findByG_M_P(groupId, mountPoint, parentFolderId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId, int start, int end) throws SystemException {
		return findByG_M_P(groupId, mountPoint, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P;
			finderArgs = new Object[] { groupId, mountPoint, parentFolderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M_P;
			finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.getMountPoint()) ||
						(parentFolderId != dlFolder.getParentFolderId())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_M_P_First(long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_M_P_First(groupId, mountPoint,
				parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_M_P_First(long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFolder> list = findByG_M_P(groupId, mountPoint, parentFolderId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_M_P_Last(long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_M_P_Last(groupId, mountPoint,
				parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_M_P_Last(long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_M_P(groupId, mountPoint, parentFolderId);

		List<DLFolder> list = findByG_M_P(groupId, mountPoint, parentFolderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByG_M_P_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_P_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_P_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_M_P_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId) throws SystemException {
		return filterFindByG_M_P(groupId, mountPoint, parentFolderId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId, int start, int end) throws SystemException {
		return filterFindByG_M_P(groupId, mountPoint, parentFolderId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P(groupId, mountPoint, parentFolderId, start, end,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] filterFindByG_M_P_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_PrevAndNext(folderId, groupId, mountPoint,
				parentFolderId, orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_P_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_P_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder filterGetByG_M_P_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_N(groupId, parentFolderId, name);

		if (dlFolder == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	/**
	 * Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_N(long groupId, long parentFolderId,
		String name, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_N,
					finderArgs, this);
		}

		if (result instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)result;

			if ((groupId != dlFolder.getGroupId()) ||
					(parentFolderId != dlFolder.getParentFolderId()) ||
					!Validator.equals(name, dlFolder.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_N_NAME_2);
				}
			}

			query.append(DLFolderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getGroupId() != groupId) ||
							(dlFolder.getParentFolderId() != parentFolderId) ||
							(dlFolder.getName() == null) ||
							!dlFolder.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
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
				return (DLFolder)result;
			}
		}
	}

	/**
	 * Returns all the document library folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P_S(long groupId, long parentFolderId,
		int status) throws SystemException {
		return findByG_P_S(groupId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P_S(long groupId, long parentFolderId,
		int status, int start, int end) throws SystemException {
		return findByG_P_S(groupId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P_S(long groupId, long parentFolderId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_S;
			finderArgs = new Object[] { groupId, parentFolderId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_S;
			finderArgs = new Object[] {
					groupId, parentFolderId, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((groupId != dlFolder.getGroupId()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(status != dlFolder.getStatus())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				qPos.add(status);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_S_First(long groupId, long parentFolderId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_S_First(groupId, parentFolderId, status,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_S_First(long groupId, long parentFolderId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFolder> list = findByG_P_S(groupId, parentFolderId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_S_Last(long groupId, long parentFolderId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_S_Last(groupId, parentFolderId, status,
				orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_S_Last(long groupId, long parentFolderId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_P_S(groupId, parentFolderId, status);

		List<DLFolder> list = findByG_P_S(groupId, parentFolderId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByG_P_S_PrevAndNext(long folderId, long groupId,
		long parentFolderId, int status, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_P_S_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, status, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_P_S_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_P_S_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, long parentFolderId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_S_STATUS_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P_S(long groupId, long parentFolderId,
		int status) throws SystemException {
		return filterFindByG_P_S(groupId, parentFolderId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P_S(long groupId, long parentFolderId,
		int status, int start, int end) throws SystemException {
		return filterFindByG_P_S(groupId, parentFolderId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P_S(long groupId, long parentFolderId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_S(groupId, parentFolderId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			qPos.add(status);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] filterFindByG_P_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_S_PrevAndNext(folderId, groupId, parentFolderId,
				status, orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_P_S_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, status, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_P_S_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder filterGetByG_P_S_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, long parentFolderId, int status,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden) throws SystemException {
		return findByG_M_P_H(groupId, mountPoint, parentFolderId, hidden,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int start, int end)
		throws SystemException {
		return findByG_M_P_H(groupId, mountPoint, parentFolderId, hidden,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_H;
			finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId, hidden
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M_P_H;
			finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId, hidden,
					
					start, end, orderByComparator
				};
		}

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.getMountPoint()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(hidden != dlFolder.getHidden())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(hidden);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_M_P_H_First(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_M_P_H_First(groupId, mountPoint,
				parentFolderId, hidden, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_M_P_H_First(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFolder> list = findByG_M_P_H(groupId, mountPoint,
				parentFolderId, hidden, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_M_P_H_Last(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_M_P_H_Last(groupId, mountPoint,
				parentFolderId, hidden, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_M_P_H_Last(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_M_P_H(groupId, mountPoint, parentFolderId, hidden);

		List<DLFolder> list = findByG_M_P_H(groupId, mountPoint,
				parentFolderId, hidden, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByG_M_P_H_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_P_H_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, hidden, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_P_H_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, hidden, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_M_P_H_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden) throws SystemException {
		return filterFindByG_M_P_H(groupId, mountPoint, parentFolderId, hidden,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int start, int end)
		throws SystemException {
		return filterFindByG_M_P_H(groupId, mountPoint, parentFolderId, hidden,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_H(groupId, mountPoint, parentFolderId, hidden,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] filterFindByG_M_P_H_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_H_PrevAndNext(folderId, groupId, mountPoint,
				parentFolderId, hidden, orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_P_H_PrevAndNext(session, dlFolder,
					groupId, mountPoint, parentFolderId, hidden,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_P_H_PrevAndNext(session, dlFolder,
					groupId, mountPoint, parentFolderId, hidden,
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

	protected DLFolder filterGetByG_M_P_H_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status) throws SystemException {
		return findByG_M_P_S(groupId, mountPoint, parentFolderId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status, int start, int end)
		throws SystemException {
		return findByG_M_P_S(groupId, mountPoint, parentFolderId, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M_P_S;
			finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId, status
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M_P_S;
			finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFolder dlFolder : list) {
				if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.getMountPoint()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(status != dlFolder.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(status);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_M_P_S_First(long groupId, boolean mountPoint,
		long parentFolderId, int status, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_M_P_S_First(groupId, mountPoint,
				parentFolderId, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_M_P_S_First(long groupId, boolean mountPoint,
		long parentFolderId, int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFolder> list = findByG_M_P_S(groupId, mountPoint,
				parentFolderId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_M_P_S_Last(long groupId, boolean mountPoint,
		long parentFolderId, int status, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_M_P_S_Last(groupId, mountPoint,
				parentFolderId, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_M_P_S_Last(long groupId, boolean mountPoint,
		long parentFolderId, int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_M_P_S(groupId, mountPoint, parentFolderId, status);

		List<DLFolder> list = findByG_M_P_S(groupId, mountPoint,
				parentFolderId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByG_M_P_S_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, long parentFolderId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_P_S_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, status, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_P_S_PrevAndNext(session, dlFolder, groupId,
					mountPoint, parentFolderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_M_P_S_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_STATUS_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status) throws SystemException {
		return filterFindByG_M_P_S(groupId, mountPoint, parentFolderId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status, int start, int end)
		throws SystemException {
		return filterFindByG_M_P_S(groupId, mountPoint, parentFolderId, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_S(groupId, mountPoint, parentFolderId, status,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(status);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] filterFindByG_M_P_S_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, long parentFolderId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_S_PrevAndNext(folderId, groupId, mountPoint,
				parentFolderId, status, orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_P_S_PrevAndNext(session, dlFolder,
					groupId, mountPoint, parentFolderId, status,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_P_S_PrevAndNext(session, dlFolder,
					groupId, mountPoint, parentFolderId, status,
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

	protected DLFolder filterGetByG_M_P_S_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, int status, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders.
	 *
	 * @return the document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findAll(int start, int end,
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

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFOLDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFOLDER.concat(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFolder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFolder>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the document library folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFolder dlFolder : findByUuid(uuid)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes the document library folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByUUID_G(uuid, groupId);

		return remove(dlFolder);
	}

	/**
	 * Removes all the document library folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DLFolder dlFolder : findByUuid_C(uuid, companyId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFolder dlFolder : findByGroupId(groupId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFolder dlFolder : findByCompanyId(companyId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes the document library folder where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @return the document library folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder removeByRepositoryId(long repositoryId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByRepositoryId(repositoryId);

		return remove(dlFolder);
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (DLFolder dlFolder : findByG_P(groupId, parentFolderId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders where parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_N(long parentFolderId, String name)
		throws SystemException {
		for (DLFolder dlFolder : findByP_N(parentFolderId, name)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId) throws SystemException {
		for (DLFolder dlFolder : findByG_M_P(groupId, mountPoint, parentFolderId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the document library folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder removeByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByG_P_N(groupId, parentFolderId, name);

		return remove(dlFolder);
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P_S(long groupId, long parentFolderId, int status)
		throws SystemException {
		for (DLFolder dlFolder : findByG_P_S(groupId, parentFolderId, status)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden) throws SystemException {
		for (DLFolder dlFolder : findByG_M_P_H(groupId, mountPoint,
				parentFolderId, hidden)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status) throws SystemException {
		for (DLFolder dlFolder : findByG_M_P_S(groupId, mountPoint,
				parentFolderId, status)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the document library folders from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFolder dlFolder : findAll()) {
			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Returns the number of document library folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Returns the number of document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Returns the number of document library folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
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
	 * Returns the number of document library folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Returns the number of document library folders where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRepositoryId(long repositoryId) throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, long parentFolderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentFolderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

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
	 * Returns the number of document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_N(long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { parentFolderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, mountPoint, parentFolderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_M_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_M_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_P(groupId, mountPoint, parentFolderId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

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
	 * Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_S(long groupId, long parentFolderId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P_S(long groupId, long parentFolderId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_S(groupId, parentFolderId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			qPos.add(status);

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
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, mountPoint, parentFolderId, hidden
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_M_P_H,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(hidden);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_M_P_H,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the number of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_P_H(groupId, mountPoint, parentFolderId, hidden);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(hidden);

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
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, mountPoint, parentFolderId, status
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_M_P_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_M_P_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching document library folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_M_P_S(long groupId, boolean mountPoint,
		long parentFolderId, int status) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_P_S(groupId, mountPoint, parentFolderId, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(status);

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
	 * Returns the number of document library folders.
	 *
	 * @return the number of document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFOLDER);

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
	 * Returns all the document library file entry types associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @return the document library file entry types associated with the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk) throws SystemException {
		return getDLFileEntryTypes(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the document library file entry types associated with the document library folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the document library folder
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of document library file entry types associated with the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end) throws SystemException {
		return getDLFileEntryTypes(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_DLFILEENTRYTYPES = new FinderPath(com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS,
			com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl.class,
			DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME,
			"getDLFileEntryTypes",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_DLFILEENTRYTYPES.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types associated with the document library folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the document library folder
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry types associated with the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> list = (List<com.liferay.portlet.documentlibrary.model.DLFileEntryType>)FinderCacheUtil.getResult(FINDER_PATH_GET_DLFILEENTRYTYPES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETDLFILEENTRYTYPES.concat(ORDER_BY_CLAUSE)
												  .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETDLFILEENTRYTYPES;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("DLFileEntryType",
					com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.documentlibrary.model.DLFileEntryType>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_DLFILEENTRYTYPES,
						finderArgs);
				}
				else {
					dlFileEntryTypePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_DLFILEENTRYTYPES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_DLFILEENTRYTYPES_SIZE = new FinderPath(com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS,
			Long.class,
			DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME,
			"getDLFileEntryTypesSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_DLFILEENTRYTYPES_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of document library file entry types associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @return the number of document library file entry types associated with the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public int getDLFileEntryTypesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_DLFILEENTRYTYPES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETDLFILEENTRYTYPESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_DLFILEENTRYTYPES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_DLFILEENTRYTYPE = new FinderPath(com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS,
			Boolean.class,
			DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME,
			"containsDLFileEntryType",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the document library file entry type is associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 * @return <code>true</code> if the document library file entry type is associated with the document library folder; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDLFileEntryType(long pk, long dlFileEntryTypePK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, dlFileEntryTypePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_DLFILEENTRYTYPE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsDLFileEntryType.contains(pk,
							dlFileEntryTypePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_DLFILEENTRYTYPE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the document library folder has any document library file entry types associated with it.
	 *
	 * @param pk the primary key of the document library folder to check for associations with document library file entry types
	 * @return <code>true</code> if the document library folder has any document library file entry types associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDLFileEntryTypes(long pk) throws SystemException {
		if (getDLFileEntryTypesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFileEntryType(long pk, long dlFileEntryTypePK)
		throws SystemException {
		try {
			addDLFileEntryType.add(pk, dlFileEntryTypePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryType the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws SystemException {
		try {
			addDLFileEntryType.add(pk, dlFileEntryType.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs)
		throws SystemException {
		try {
			for (long dlFileEntryTypePK : dlFileEntryTypePKs) {
				addDLFileEntryType.add(pk, dlFileEntryTypePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypes the document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes)
		throws SystemException {
		try {
			for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
				addDLFileEntryType.add(pk, dlFileEntryType.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Clears all associations between the document library folder and its document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder to clear the associated document library file entry types from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearDLFileEntryTypes(long pk) throws SystemException {
		try {
			clearDLFileEntryTypes.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFileEntryType(long pk, long dlFileEntryTypePK)
		throws SystemException {
		try {
			removeDLFileEntryType.remove(pk, dlFileEntryTypePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryType the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws SystemException {
		try {
			removeDLFileEntryType.remove(pk, dlFileEntryType.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs)
		throws SystemException {
		try {
			for (long dlFileEntryTypePK : dlFileEntryTypePKs) {
				removeDLFileEntryType.remove(pk, dlFileEntryTypePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypes the document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes)
		throws SystemException {
		try {
			for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
				removeDLFileEntryType.remove(pk, dlFileEntryType.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types to be associated with the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public void setDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs)
		throws SystemException {
		try {
			Set<Long> dlFileEntryTypePKSet = SetUtil.fromArray(dlFileEntryTypePKs);

			List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes =
				getDLFileEntryTypes(pk);

			for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
				if (!dlFileEntryTypePKSet.remove(
							dlFileEntryType.getPrimaryKey())) {
					removeDLFileEntryType.remove(pk,
						dlFileEntryType.getPrimaryKey());
				}
			}

			for (Long dlFileEntryTypePK : dlFileEntryTypePKSet) {
				addDLFileEntryType.add(pk, dlFileEntryTypePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypes the document library file entry types to be associated with the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public void setDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes)
		throws SystemException {
		try {
			long[] dlFileEntryTypePKs = new long[dlFileEntryTypes.size()];

			for (int i = 0; i < dlFileEntryTypes.size(); i++) {
				com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType =
					dlFileEntryTypes.get(i);

				dlFileEntryTypePKs[i] = dlFileEntryType.getPrimaryKey();
			}

			setDLFileEntryTypes(pk, dlFileEntryTypePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFolderModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Initializes the document library folder persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFolder>> listenersList = new ArrayList<ModelListener<DLFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFolder>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsDLFileEntryType = new ContainsDLFileEntryType();

		addDLFileEntryType = new AddDLFileEntryType();
		clearDLFileEntryTypes = new ClearDLFileEntryTypes();
		removeDLFileEntryType = new RemoveDLFileEntryType();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLFolderImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DLContentPersistence.class)
	protected DLContentPersistence dlContentPersistence;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryMetadataPersistence.class)
	protected DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLSyncPersistence.class)
	protected DLSyncPersistence dlSyncPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	protected ContainsDLFileEntryType containsDLFileEntryType;
	protected AddDLFileEntryType addDLFileEntryType;
	protected ClearDLFileEntryTypes clearDLFileEntryTypes;
	protected RemoveDLFileEntryType removeDLFileEntryType;

	protected class ContainsDLFileEntryType {
		protected ContainsDLFileEntryType() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSDLFILEENTRYTYPE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long folderId, long fileEntryTypeId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(folderId), new Long(fileEntryTypeId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddDLFileEntryType {
		protected AddDLFileEntryType() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO DLFileEntryTypes_DLFolders (folderId, fileEntryTypeId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long folderId, long fileEntryTypeId)
			throws SystemException {
			if (!containsDLFileEntryType.contains(folderId, fileEntryTypeId)) {
				ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType>[] dlFileEntryTypeListeners =
					dlFileEntryTypePersistence.getListeners();

				for (ModelListener<DLFolder> listener : listeners) {
					listener.onBeforeAddAssociation(folderId,
						com.liferay.portlet.documentlibrary.model.DLFileEntryType.class.getName(),
						fileEntryTypeId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType> listener : dlFileEntryTypeListeners) {
					listener.onBeforeAddAssociation(fileEntryTypeId,
						DLFolder.class.getName(), folderId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(folderId), new Long(fileEntryTypeId)
					});

				for (ModelListener<DLFolder> listener : listeners) {
					listener.onAfterAddAssociation(folderId,
						com.liferay.portlet.documentlibrary.model.DLFileEntryType.class.getName(),
						fileEntryTypeId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType> listener : dlFileEntryTypeListeners) {
					listener.onAfterAddAssociation(fileEntryTypeId,
						DLFolder.class.getName(), folderId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearDLFileEntryTypes {
		protected ClearDLFileEntryTypes() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLFileEntryTypes_DLFolders WHERE folderId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long folderId) throws SystemException {
			ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType>[] dlFileEntryTypeListeners =
				dlFileEntryTypePersistence.getListeners();

			List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes =
				null;

			if ((listeners.length > 0) ||
					(dlFileEntryTypeListeners.length > 0)) {
				dlFileEntryTypes = getDLFileEntryTypes(folderId);

				for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
					for (ModelListener<DLFolder> listener : listeners) {
						listener.onBeforeRemoveAssociation(folderId,
							com.liferay.portlet.documentlibrary.model.DLFileEntryType.class.getName(),
							dlFileEntryType.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType> listener : dlFileEntryTypeListeners) {
						listener.onBeforeRemoveAssociation(dlFileEntryType.getPrimaryKey(),
							DLFolder.class.getName(), folderId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(folderId) });

			if ((listeners.length > 0) ||
					(dlFileEntryTypeListeners.length > 0)) {
				for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
					for (ModelListener<DLFolder> listener : listeners) {
						listener.onAfterRemoveAssociation(folderId,
							com.liferay.portlet.documentlibrary.model.DLFileEntryType.class.getName(),
							dlFileEntryType.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType> listener : dlFileEntryTypeListeners) {
						listener.onAfterRemoveAssociation(dlFileEntryType.getPrimaryKey(),
							DLFolder.class.getName(), folderId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveDLFileEntryType {
		protected RemoveDLFileEntryType() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLFileEntryTypes_DLFolders WHERE folderId = ? AND fileEntryTypeId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long folderId, long fileEntryTypeId)
			throws SystemException {
			if (containsDLFileEntryType.contains(folderId, fileEntryTypeId)) {
				ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType>[] dlFileEntryTypeListeners =
					dlFileEntryTypePersistence.getListeners();

				for (ModelListener<DLFolder> listener : listeners) {
					listener.onBeforeRemoveAssociation(folderId,
						com.liferay.portlet.documentlibrary.model.DLFileEntryType.class.getName(),
						fileEntryTypeId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType> listener : dlFileEntryTypeListeners) {
					listener.onBeforeRemoveAssociation(fileEntryTypeId,
						DLFolder.class.getName(), folderId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(folderId), new Long(fileEntryTypeId)
					});

				for (ModelListener<DLFolder> listener : listeners) {
					listener.onAfterRemoveAssociation(folderId,
						com.liferay.portlet.documentlibrary.model.DLFileEntryType.class.getName(),
						fileEntryTypeId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFileEntryType> listener : dlFileEntryTypeListeners) {
					listener.onAfterRemoveAssociation(fileEntryTypeId,
						DLFolder.class.getName(), folderId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_DLFOLDER = "SELECT dlFolder FROM DLFolder dlFolder";
	private static final String _SQL_SELECT_DLFOLDER_WHERE = "SELECT dlFolder FROM DLFolder dlFolder WHERE ";
	private static final String _SQL_COUNT_DLFOLDER = "SELECT COUNT(dlFolder) FROM DLFolder dlFolder";
	private static final String _SQL_COUNT_DLFOLDER_WHERE = "SELECT COUNT(dlFolder) FROM DLFolder dlFolder WHERE ";
	private static final String _SQL_GETDLFILEENTRYTYPES = "SELECT {DLFileEntryType.*} FROM DLFileEntryType INNER JOIN DLFileEntryTypes_DLFolders ON (DLFileEntryTypes_DLFolders.fileEntryTypeId = DLFileEntryType.fileEntryTypeId) WHERE (DLFileEntryTypes_DLFolders.folderId = ?)";
	private static final String _SQL_GETDLFILEENTRYTYPESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLFileEntryTypes_DLFolders WHERE folderId = ?";
	private static final String _SQL_CONTAINSDLFILEENTRYTYPE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLFileEntryTypes_DLFolders WHERE folderId = ? AND fileEntryTypeId = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFolder.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFolder.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFolder.uuid IS NULL OR dlFolder.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFolder.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFolder.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFolder.uuid IS NULL OR dlFolder.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFolder.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFolder.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFolder.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFolder.uuid IS NULL OR dlFolder.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFolder.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFolder.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFolder.companyId = ?";
	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 = "dlFolder.repositoryId = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ?";
	private static final String _FINDER_COLUMN_P_N_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_P_N_NAME_1 = "dlFolder.name IS NULL";
	private static final String _FINDER_COLUMN_P_N_NAME_2 = "dlFolder.name = ?";
	private static final String _FINDER_COLUMN_P_N_NAME_3 = "(dlFolder.name IS NULL OR dlFolder.name = ?)";
	private static final String _FINDER_COLUMN_G_M_P_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_MOUNTPOINT_2 = "dlFolder.mountPoint = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ?";
	private static final String _FINDER_COLUMN_G_P_N_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_NAME_1 = "dlFolder.name IS NULL";
	private static final String _FINDER_COLUMN_G_P_N_NAME_2 = "dlFolder.name = ?";
	private static final String _FINDER_COLUMN_G_P_N_NAME_3 = "(dlFolder.name IS NULL OR dlFolder.name = ?)";
	private static final String _FINDER_COLUMN_G_P_S_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_S_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_S_STATUS_2 = "dlFolder.status = ?";
	private static final String _FINDER_COLUMN_G_M_P_H_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2 = "dlFolder.mountPoint = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_H_HIDDEN_2 = "dlFolder.hidden = ?";
	private static final String _FINDER_COLUMN_G_M_P_S_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_S_MOUNTPOINT_2 = "dlFolder.mountPoint = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_S_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_P_S_STATUS_2 = "dlFolder.status = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "dlFolder.folderId";
	private static final String _FILTER_SQL_SELECT_DLFOLDER_WHERE = "SELECT DISTINCT {dlFolder.*} FROM DLFolder dlFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFolder.*} FROM (SELECT DISTINCT dlFolder.folderId FROM DLFolder dlFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFolder ON TEMP_TABLE.folderId = DLFolder.folderId";
	private static final String _FILTER_SQL_COUNT_DLFOLDER_WHERE = "SELECT COUNT(DISTINCT dlFolder.folderId) AS COUNT_VALUE FROM DLFolder dlFolder WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "dlFolder";
	private static final String _FILTER_ENTITY_TABLE = "DLFolder";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFolder.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFolder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFolder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFolder exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLFolderPersistenceImpl.class);
	private static DLFolder _nullDLFolder = new DLFolderImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFolder> toCacheModel() {
				return _nullDLFolderCacheModel;
			}
		};

	private static CacheModel<DLFolder> _nullDLFolderCacheModel = new CacheModel<DLFolder>() {
			public DLFolder toEntityModel() {
				return _nullDLFolder;
			}
		};
}