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
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.trash.service.persistence.TrashEntryPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the document library file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPersistence
 * @see DLFileEntryUtil
 * @generated
 */
public class DLFileEntryPersistenceImpl extends BasePersistenceImpl<DLFileEntry>
	implements DLFileEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryUtil} to access the document library file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFileEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MIMETYPE = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMimeType",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMimeType",
			new String[] { String.class.getName() },
			DLFileEntryModelImpl.MIMETYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MIMETYPE = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMimeType",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_N",
			new String[] { Long.class.getName(), String.class.getName() },
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.USERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FILEENTRYTYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the document library file entry in the entity cache if it is enabled.
	 *
	 * @param dlFileEntry the document library file entry
	 */
	public void cacheResult(DLFileEntry dlFileEntry) {
		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntry.getUuid(), Long.valueOf(dlFileEntry.getGroupId())
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				Long.valueOf(dlFileEntry.getGroupId()),
				Long.valueOf(dlFileEntry.getFolderId()),
				
			dlFileEntry.getName()
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				Long.valueOf(dlFileEntry.getGroupId()),
				Long.valueOf(dlFileEntry.getFolderId()),
				
			dlFileEntry.getTitle()
			}, dlFileEntry);

		dlFileEntry.resetOriginalValues();
	}

	/**
	 * Caches the document library file entries in the entity cache if it is enabled.
	 *
	 * @param dlFileEntries the document library file entries
	 */
	public void cacheResult(List<DLFileEntry> dlFileEntries) {
		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (EntityCacheUtil.getResult(
						DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, dlFileEntry.getPrimaryKey()) == null) {
				cacheResult(dlFileEntry);
			}
			else {
				dlFileEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLFileEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLFileEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntry dlFileEntry) {
		EntityCacheUtil.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(dlFileEntry);
	}

	@Override
	public void clearCache(List<DLFileEntry> dlFileEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			EntityCacheUtil.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

			clearUniqueFindersCache(dlFileEntry);
		}
	}

	protected void clearUniqueFindersCache(DLFileEntry dlFileEntry) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntry.getUuid(), Long.valueOf(dlFileEntry.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				Long.valueOf(dlFileEntry.getGroupId()),
				Long.valueOf(dlFileEntry.getFolderId()),
				
			dlFileEntry.getName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				Long.valueOf(dlFileEntry.getGroupId()),
				Long.valueOf(dlFileEntry.getFolderId()),
				
			dlFileEntry.getTitle()
			});
	}

	/**
	 * Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	 *
	 * @param fileEntryId the primary key for the new document library file entry
	 * @return the new document library file entry
	 */
	public DLFileEntry create(long fileEntryId) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(fileEntryId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntry.setUuid(uuid);

		return dlFileEntry;
	}

	/**
	 * Removes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry remove(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		return remove(Long.valueOf(fileEntryId));
	}

	/**
	 * Removes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file entry
	 * @return the document library file entry that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntry remove(Serializable primaryKey)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
					primaryKey);

			if (dlFileEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileEntry);
		}
		catch (NoSuchFileEntryException nsee) {
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
	protected DLFileEntry removeImpl(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntry.isCachedModel()) {
				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						dlFileEntry.getPrimaryKeyObj());
			}

			if (dlFileEntry != null) {
				session.delete(dlFileEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntry != null) {
			clearCache(dlFileEntry);
		}

		return dlFileEntry;
	}

	@Override
	public DLFileEntry updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		boolean isNew = dlFileEntry.isNew();

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		if (Validator.isNull(dlFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntry.isNew()) {
				session.save(dlFileEntry);

				dlFileEntry.setNew(false);
			}
			else {
				session.merge(dlFileEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalUuid(),
						Long.valueOf(dlFileEntryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getUuid(),
						Long.valueOf(dlFileEntryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalMimeType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
					args);

				args = new Object[] { dlFileEntryModelImpl.getMimeType() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalFolderId()),
						
						dlFileEntryModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getFolderId()),
						
						dlFileEntryModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalUserId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getUserId()),
						Long.valueOf(dlFileEntryModelImpl.getFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalFolderId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalFileEntryTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getFolderId()),
						Long.valueOf(dlFileEntryModelImpl.getFileEntryTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F,
					args);
			}
		}

		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntry.getUuid(),
					Long.valueOf(dlFileEntry.getGroupId())
				}, dlFileEntry);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					Long.valueOf(dlFileEntry.getGroupId()),
					Long.valueOf(dlFileEntry.getFolderId()),
					
				dlFileEntry.getName()
				}, dlFileEntry);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
				new Object[] {
					Long.valueOf(dlFileEntry.getGroupId()),
					Long.valueOf(dlFileEntry.getFolderId()),
					
				dlFileEntry.getTitle()
				}, dlFileEntry);
		}
		else {
			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalUuid(),
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						dlFileEntry.getUuid(),
						Long.valueOf(dlFileEntry.getGroupId())
					}, dlFileEntry);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_F_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalFolderId()),
						
						dlFileEntryModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
					new Object[] {
						Long.valueOf(dlFileEntry.getGroupId()),
						Long.valueOf(dlFileEntry.getFolderId()),
						
					dlFileEntry.getName()
					}, dlFileEntry);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_F_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileEntryModelImpl.getOriginalFolderId()),
						
						dlFileEntryModelImpl.getOriginalTitle()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
					new Object[] {
						Long.valueOf(dlFileEntry.getGroupId()),
						Long.valueOf(dlFileEntry.getFolderId()),
						
					dlFileEntry.getTitle()
					}, dlFileEntry);
			}
		}

		return dlFileEntry;
	}

	protected DLFileEntry toUnwrappedModel(DLFileEntry dlFileEntry) {
		if (dlFileEntry instanceof DLFileEntryImpl) {
			return dlFileEntry;
		}

		DLFileEntryImpl dlFileEntryImpl = new DLFileEntryImpl();

		dlFileEntryImpl.setNew(dlFileEntry.isNew());
		dlFileEntryImpl.setPrimaryKey(dlFileEntry.getPrimaryKey());

		dlFileEntryImpl.setUuid(dlFileEntry.getUuid());
		dlFileEntryImpl.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileEntryImpl.setGroupId(dlFileEntry.getGroupId());
		dlFileEntryImpl.setCompanyId(dlFileEntry.getCompanyId());
		dlFileEntryImpl.setUserId(dlFileEntry.getUserId());
		dlFileEntryImpl.setUserName(dlFileEntry.getUserName());
		dlFileEntryImpl.setVersionUserId(dlFileEntry.getVersionUserId());
		dlFileEntryImpl.setVersionUserName(dlFileEntry.getVersionUserName());
		dlFileEntryImpl.setCreateDate(dlFileEntry.getCreateDate());
		dlFileEntryImpl.setModifiedDate(dlFileEntry.getModifiedDate());
		dlFileEntryImpl.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileEntryImpl.setFolderId(dlFileEntry.getFolderId());
		dlFileEntryImpl.setName(dlFileEntry.getName());
		dlFileEntryImpl.setExtension(dlFileEntry.getExtension());
		dlFileEntryImpl.setMimeType(dlFileEntry.getMimeType());
		dlFileEntryImpl.setTitle(dlFileEntry.getTitle());
		dlFileEntryImpl.setDescription(dlFileEntry.getDescription());
		dlFileEntryImpl.setExtraSettings(dlFileEntry.getExtraSettings());
		dlFileEntryImpl.setFileEntryTypeId(dlFileEntry.getFileEntryTypeId());
		dlFileEntryImpl.setVersion(dlFileEntry.getVersion());
		dlFileEntryImpl.setSize(dlFileEntry.getSize());
		dlFileEntryImpl.setReadCount(dlFileEntry.getReadCount());
		dlFileEntryImpl.setSmallImageId(dlFileEntry.getSmallImageId());
		dlFileEntryImpl.setLargeImageId(dlFileEntry.getLargeImageId());
		dlFileEntryImpl.setCustom1ImageId(dlFileEntry.getCustom1ImageId());
		dlFileEntryImpl.setCustom2ImageId(dlFileEntry.getCustom2ImageId());
		dlFileEntryImpl.setManualCheckInRequired(dlFileEntry.isManualCheckInRequired());

		return dlFileEntryImpl;
	}

	/**
	 * Returns the document library file entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry
	 * @return the document library file entry
	 * @throws com.liferay.portal.NoSuchModelException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file entry with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByPrimaryKey(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByPrimaryKey(fileEntryId);

		if (dlFileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileEntryId);
			}

			throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileEntryId);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry
	 * @return the document library file entry, or <code>null</code> if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry, or <code>null</code> if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByPrimaryKey(long fileEntryId)
		throws SystemException {
		DLFileEntry dlFileEntry = (DLFileEntry)EntityCacheUtil.getResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, fileEntryId);

		if (dlFileEntry == _nullDLFileEntry) {
			return null;
		}

		if (dlFileEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						Long.valueOf(fileEntryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlFileEntry != null) {
					cacheResult(dlFileEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, fileEntryId, _nullDLFileEntry);
				}

				closeSession(session);
			}
		}

		return dlFileEntry;
	}

	/**
	 * Returns all the document library file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid(String uuid, int start, int end,
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

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if (!Validator.equals(uuid, dlFileEntry.getUuid())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUuid_First(uuid, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DLFileEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByUuid_PrevAndNext(long fileEntryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileEntry, uuid,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByUuid_PrevAndNext(session, dlFileEntry, uuid,
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

	protected DLFileEntry getByUuid_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUUID_G(uuid, groupId);

		if (dlFileEntry == null) {
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

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if (!Validator.equals(uuid, dlFileEntry.getUuid()) ||
					(groupId != dlFileEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getUuid() == null) ||
							!dlFileEntry.getUuid().equals(uuid) ||
							(dlFileEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
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
				return (DLFileEntry)result;
			}
		}
	}

	/**
	 * Returns all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId,
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

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if (!Validator.equals(uuid, dlFileEntry.getUuid()) ||
						(companyId != dlFileEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DLFileEntry> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByUuid_C_PrevAndNext(long fileEntryId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileEntry, uuid,
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

	protected DLFileEntry getByUuid_C_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end,
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

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<DLFileEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlFileEntry, groupId,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByGroupId_PrevAndNext(session, dlFileEntry, groupId,
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

	protected DLFileEntry getByGroupId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] filterFindByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(fileEntryId, groupId,
				orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, dlFileEntry,
					groupId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session, dlFileEntry,
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

	protected DLFileEntry filterGetByGroupId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((companyId != dlFileEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<DLFileEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByCompanyId_PrevAndNext(long fileEntryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFileEntry,
					companyId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByCompanyId_PrevAndNext(session, dlFileEntry,
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

	protected DLFileEntry getByCompanyId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByMimeType(String mimeType)
		throws SystemException {
		return findByMimeType(mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByMimeType(String mimeType, int start, int end)
		throws SystemException {
		return findByMimeType(mimeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByMimeType(String mimeType, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE;
			finderArgs = new Object[] { mimeType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MIMETYPE;
			finderArgs = new Object[] { mimeType, start, end, orderByComparator };
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if (!Validator.equals(mimeType, dlFileEntry.getMimeType())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			if (mimeType == null) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
			}
			else {
				if (mimeType.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (mimeType != null) {
					qPos.add(mimeType);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByMimeType_First(String mimeType,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByMimeType_First(mimeType,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByMimeType_First(String mimeType,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByMimeType(mimeType, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByMimeType_Last(String mimeType,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByMimeType_Last(mimeType,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByMimeType_Last(String mimeType,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByMimeType(mimeType);

		List<DLFileEntry> list = findByMimeType(mimeType, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByMimeType_PrevAndNext(long fileEntryId,
		String mimeType, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByMimeType_PrevAndNext(session, dlFileEntry,
					mimeType, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByMimeType_PrevAndNext(session, dlFileEntry,
					mimeType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByMimeType_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String mimeType,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		if (mimeType == null) {
			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
		}
		else {
			if (mimeType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (mimeType != null) {
			qPos.add(mimeType);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByF_N(long folderId, String name)
		throws SystemException {
		return findByF_N(folderId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByF_N(long folderId, String name, int start,
		int end) throws SystemException {
		return findByF_N(folderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N;
			finderArgs = new Object[] { folderId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_F_N;
			finderArgs = new Object[] {
					folderId, name,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((folderId != dlFileEntry.getFolderId()) ||
						!Validator.equals(name, dlFileEntry.getName())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByF_N_First(long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByF_N_First(folderId, name,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId=");
		msg.append(folderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByF_N_First(long folderId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByF_N(folderId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByF_N_Last(long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByF_N_Last(folderId, name,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId=");
		msg.append(folderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByF_N_Last(long folderId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByF_N(folderId, name);

		List<DLFileEntry> list = findByF_N(folderId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByF_N_PrevAndNext(long fileEntryId, long folderId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByF_N_PrevAndNext(session, dlFileEntry, folderId,
					name, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByF_N_PrevAndNext(session, dlFileEntry, folderId,
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

	protected DLFileEntry getByF_N_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long folderId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_F_N_NAME_2);
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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(folderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						(userId != dlFileEntry.getUserId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_U_First(groupId, userId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_U_Last(groupId, userId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_U(groupId, userId);

		List<DLFileEntry> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_U_PrevAndNext(long fileEntryId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_U_PrevAndNext(session, dlFileEntry, groupId,
					userId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_U_PrevAndNext(session, dlFileEntry, groupId,
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

	protected DLFileEntry getByG_U_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId,
		int start, int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] filterFindByG_U_PrevAndNext(long fileEntryId,
		long groupId, long userId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PrevAndNext(fileEntryId, groupId, userId,
				orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_U_PrevAndNext(session, dlFileEntry,
					groupId, userId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_U_PrevAndNext(session, dlFileEntry,
					groupId, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_U_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long folderId)
		throws SystemException {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						(folderId != dlFileEntry.getFolderId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_First(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_First(groupId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_First(long groupId, long folderId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntry> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_Last(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_Last(groupId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_Last(long groupId, long folderId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_F(groupId, folderId);

		List<DLFileEntry> list = findByG_F(groupId, folderId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_F_PrevAndNext(long fileEntryId, long groupId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds)
		throws SystemException {
		return findByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end) throws SystemException {
		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds),
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						!ArrayUtil.contains(folderIds, dlFileEntry.getFolderId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
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

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId)
		throws SystemException {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] filterFindByG_F_PrevAndNext(long fileEntryId,
		long groupId, long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_PrevAndNext(fileEntryId, groupId, folderId,
				orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds)
		throws SystemException {
		return filterFindByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId) throws SystemException {
		return findByG_U_F(groupId, userId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end) throws SystemException {
		return findByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F;
			finderArgs = new Object[] { groupId, userId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F;
			finderArgs = new Object[] {
					groupId, userId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						(userId != dlFileEntry.getUserId()) ||
						(folderId != dlFileEntry.getFolderId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(folderId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_F_First(long groupId, long userId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_U_F_First(groupId, userId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_U_F_First(long groupId, long userId,
		long folderId, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileEntry> list = findByG_U_F(groupId, userId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_F_Last(long groupId, long userId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_U_F_Last(groupId, userId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_U_F_Last(long groupId, long userId,
		long folderId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_U_F(groupId, userId, folderId);

		List<DLFileEntry> list = findByG_U_F(groupId, userId, folderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_U_F_PrevAndNext(long fileEntryId,
		long groupId, long userId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_U_F_PrevAndNext(session, dlFileEntry, groupId,
					userId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_U_F_PrevAndNext(session, dlFileEntry, groupId,
					userId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_U_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId, long folderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds) throws SystemException {
		return findByG_U_F(groupId, userId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end) throws SystemException {
		return findByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] {
					groupId, userId, StringUtil.merge(folderIds)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, userId, StringUtil.merge(folderIds),
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						(userId != dlFileEntry.getUserId()) ||
						!ArrayUtil.contains(folderIds, dlFileEntry.getFolderId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

			conjunctionable = true;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_USERID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
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

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId, int start, int end) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(groupId, userId, folderId, start, end,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] filterFindByG_U_F_PrevAndNext(long fileEntryId,
		long groupId, long userId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F_PrevAndNext(fileEntryId, groupId, userId,
				folderId, orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_U_F_PrevAndNext(session, dlFileEntry,
					groupId, userId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_U_F_PrevAndNext(session, dlFileEntry,
					groupId, userId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_U_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId, long folderId,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(groupId, userId, folderIds, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

		conjunctionable = true;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_USERID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_N(groupId, folderId, name);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		return fetchByG_F_N(groupId, folderId, name, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_N,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if ((groupId != dlFileEntry.getGroupId()) ||
					(folderId != dlFileEntry.getFolderId()) ||
					!Validator.equals(name, dlFileEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_NAME_2);
				}
			}

			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getName() == null) ||
							!dlFileEntry.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
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
				return (DLFileEntry)result;
			}
		}
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_T(groupId, folderId, title);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		return fetchByG_F_T(groupId, folderId, title, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, title };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_T,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if ((groupId != dlFileEntry.getGroupId()) ||
					(folderId != dlFileEntry.getFolderId()) ||
					!Validator.equals(title, dlFileEntry.getTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
				}
			}

			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getTitle() == null) ||
							!dlFileEntry.getTitle().equals(title)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
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
				return (DLFileEntry)result;
			}
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId) throws SystemException {
		return findByG_F_F(groupId, folderId, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end) throws SystemException {
		return findByG_F_F(groupId, folderId, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F;
			finderArgs = new Object[] { groupId, folderId, fileEntryTypeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F;
			finderArgs = new Object[] {
					groupId, folderId, fileEntryTypeId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						(folderId != dlFileEntry.getFolderId()) ||
						(fileEntryTypeId != dlFileEntry.getFileEntryTypeId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(fileEntryTypeId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_F_First(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_F_First(groupId, folderId,
				fileEntryTypeId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_F_First(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileEntry> list = findByG_F_F(groupId, folderId,
				fileEntryTypeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_F_Last(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_F_Last(groupId, folderId,
				fileEntryTypeId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_F_Last(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_F_F(groupId, folderId, fileEntryTypeId);

		List<DLFileEntry> list = findByG_F_F(groupId, folderId,
				fileEntryTypeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_F_F_PrevAndNext(long fileEntryId,
		long groupId, long folderId, long fileEntryTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_F_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, fileEntryTypeId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_F_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, fileEntryTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_F_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(fileEntryTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId) throws SystemException {
		return findByG_F_F(groupId, folderIds, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end) throws SystemException {
		return findByG_F_F(groupId, folderIds, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), fileEntryTypeId
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), fileEntryTypeId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntry dlFileEntry : list) {
				if ((groupId != dlFileEntry.getGroupId()) ||
						!ArrayUtil.contains(folderIds, dlFileEntry.getFolderId()) ||
						(fileEntryTypeId != dlFileEntry.getFileEntryTypeId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_5);

			conjunctionable = true;

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				qPos.add(fileEntryTypeId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long folderId,
		long fileEntryTypeId) throws SystemException {
		return filterFindByG_F_F(groupId, folderId, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end) throws SystemException {
		return filterFindByG_F_F(groupId, folderId, fileEntryTypeId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F(groupId, folderId, fileEntryTypeId, start, end,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(fileEntryTypeId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] filterFindByG_F_F_PrevAndNext(long fileEntryId,
		long groupId, long folderId, long fileEntryTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F_PrevAndNext(fileEntryId, groupId, folderId,
				fileEntryTypeId, orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_F_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, fileEntryTypeId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_F_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, fileEntryTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_F_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(fileEntryTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId) throws SystemException {
		return filterFindByG_F_F(groupId, folderIds, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end) throws SystemException {
		return filterFindByG_F_F(groupId, folderIds, fileEntryTypeId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F(groupId, folderIds, fileEntryTypeId, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_5);

		conjunctionable = true;

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			qPos.add(fileEntryTypeId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the document library file entries.
	 *
	 * @return the document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findAll(int start, int end,
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

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRY.concat(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the document library file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileEntry dlFileEntry : findByUuid(uuid)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes the document library file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByUUID_G(uuid, groupId);

		return remove(dlFileEntry);
	}

	/**
	 * Removes all the document library file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByUuid_C(uuid, companyId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByGroupId(groupId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByCompanyId(companyId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where mimeType = &#63; from the database.
	 *
	 * @param mimeType the mime type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByMimeType(String mimeType) throws SystemException {
		for (DLFileEntry dlFileEntry : findByMimeType(mimeType)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where folderId = &#63; and name = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByF_N(long folderId, String name)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByF_N(folderId, name)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_U(groupId, userId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_F(groupId, folderId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_F(long groupId, long userId, long folderId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_U_F(groupId, userId, folderId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the document library file entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry removeByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_N(groupId, folderId, name);

		return remove(dlFileEntry);
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the document library file entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry removeByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_T(groupId, folderId, title);

		return remove(dlFileEntry);
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_F(long groupId, long folderId, long fileEntryTypeId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_F_F(groupId, folderId,
				fileEntryTypeId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the document library file entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileEntry dlFileEntry : findAll()) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Returns the number of document library file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Returns the number of document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Returns the number of document library file entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
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
	 * Returns the number of document library file entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Returns the number of document library file entries where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByMimeType(String mimeType) throws SystemException {
		Object[] finderArgs = new Object[] { mimeType };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MIMETYPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			if (mimeType == null) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
			}
			else {
				if (mimeType.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (mimeType != null) {
					qPos.add(mimeType);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MIMETYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByF_N(long folderId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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
	 * Returns the number of document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long[] folderIds)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
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

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long folderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long[] folderIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
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
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_F(long groupId, long userId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_F(long groupId, long userId, long[] folderIds)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, StringUtil.merge(folderIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

			conjunctionable = true;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_USERID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
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

				qPos.add(groupId);

				qPos.add(userId);

				if (folderIds != null) {
					qPos.add(folderIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_F(long groupId, long userId, long folderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(folderId);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_F(long groupId, long userId, long[] folderIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

		conjunctionable = true;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_USERID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (folderIds != null) {
				qPos.add(folderIds);
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
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and title = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, title };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_F(long groupId, long folderId, long fileEntryTypeId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, fileEntryTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(fileEntryTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_F(long groupId, long[] folderIds, long fileEntryTypeId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds), fileEntryTypeId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_5);

			conjunctionable = true;

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				qPos.add(fileEntryTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F_F(long groupId, long folderId,
		long fileEntryTypeId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_F(groupId, folderId, fileEntryTypeId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(fileEntryTypeId);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_F(groupId, folderIds, fileEntryTypeId);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_5);

		conjunctionable = true;

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			qPos.add(fileEntryTypeId);

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
	 * Returns the number of document library file entries.
	 *
	 * @return the number of document library file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRY);

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
	 * Initializes the document library file entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileEntry>> listenersList = new ArrayList<ModelListener<DLFileEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileEntryImpl.class.getName());
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
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(type = ExpandoTablePersistence.class)
	protected ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	private static final String _SQL_SELECT_DLFILEENTRY = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry";
	private static final String _SQL_SELECT_DLFILEENTRY_WHERE = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRY = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry";
	private static final String _SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileEntry.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileEntry.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFileEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFileEntry.companyId = ?";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_1 = "dlFileEntry.mimeType IS NULL";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_2 = "dlFileEntry.mimeType = ?";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_3 = "(dlFileEntry.mimeType IS NULL OR dlFileEntry.mimeType = ?)";
	private static final String _FINDER_COLUMN_F_N_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_F_N_NAME_1 = "dlFileEntry.name IS NULL";
	private static final String _FINDER_COLUMN_F_N_NAME_2 = "dlFileEntry.name = ?";
	private static final String _FINDER_COLUMN_F_N_NAME_3 = "(dlFileEntry.name IS NULL OR dlFileEntry.name = ?)";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileEntry.userId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_FOLDERID_2) + ")";
	private static final String _FINDER_COLUMN_G_U_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_F_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_U_F_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_U_F_USERID_2 = "dlFileEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_F_USERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_U_F_USERID_2) + ")";
	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_U_F_FOLDERID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_N_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_NAME_1 = "dlFileEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_NAME_2 = "dlFileEntry.name = ?";
	private static final String _FINDER_COLUMN_G_F_N_NAME_3 = "(dlFileEntry.name IS NULL OR dlFileEntry.name = ?)";
	private static final String _FINDER_COLUMN_G_F_T_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_1 = "dlFileEntry.title IS NULL";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_2 = "dlFileEntry.title = ?";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_3 = "(dlFileEntry.title IS NULL OR dlFileEntry.title = ?)";
	private static final String _FINDER_COLUMN_G_F_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_F_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_F_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_F_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_F_FOLDERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_F_FOLDERID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2 = "dlFileEntry.fileEntryTypeId = ?";
	private static final String _FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2) + ")";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "dlFileEntry.fileEntryId";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_WHERE = "SELECT DISTINCT {dlFileEntry.*} FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFileEntry.*} FROM (SELECT DISTINCT dlFileEntry.fileEntryId FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFileEntry ON TEMP_TABLE.fileEntryId = DLFileEntry.fileEntryId";
	private static final String _FILTER_SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(DISTINCT dlFileEntry.fileEntryId) AS COUNT_VALUE FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "dlFileEntry";
	private static final String _FILTER_ENTITY_TABLE = "DLFileEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceImpl.class);
	private static DLFileEntry _nullDLFileEntry = new DLFileEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileEntry> toCacheModel() {
				return _nullDLFileEntryCacheModel;
			}
		};

	private static CacheModel<DLFileEntry> _nullDLFileEntryCacheModel = new CacheModel<DLFileEntry>() {
			public DLFileEntry toEntityModel() {
				return _nullDLFileEntry;
			}
		};
}