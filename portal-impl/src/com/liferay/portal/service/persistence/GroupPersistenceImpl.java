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

import com.liferay.portal.NoSuchGroupException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence;
import com.liferay.portlet.calendar.service.persistence.CalEventPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalStructurePersistence;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBBanPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;
import com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivitySettingPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;
import com.liferay.portlet.wiki.service.persistence.WikiNodePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupPersistence
 * @see GroupUtil
 * @generated
 */
public class GroupPersistenceImpl extends BasePersistenceImpl<Group>
	implements GroupPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link GroupUtil} to access the group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = GroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_LIVEGROUPID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByLiveGroupId",
			new String[] { Long.class.getName() },
			GroupModelImpl.LIVEGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LIVEGROUPID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLiveGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.PARENTGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] { Long.class.getName(), String.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.FRIENDLYURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_A",
			new String[] {
				Integer.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_A",
			new String[] { Integer.class.getName(), Boolean.class.getName() },
			GroupModelImpl.TYPE_COLUMN_BITMASK |
			GroupModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_A",
			new String[] { Integer.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			GroupModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.PARENTGROUPID_COLUMN_BITMASK |
			GroupModelImpl.SITE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.LIVEGROUPID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			GroupModelImpl.LIVEGROUPID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the group in the entity cache if it is enabled.
	 *
	 * @param group the group
	 */
	public void cacheResult(Group group) {
		EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey(), group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
			new Object[] { Long.valueOf(group.getLiveGroupId()) }, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { Long.valueOf(group.getCompanyId()), group.getName() },
			group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				
			group.getFriendlyURL()
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				Long.valueOf(group.getClassNameId()),
				Long.valueOf(group.getClassPK())
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				Long.valueOf(group.getLiveGroupId()),
				
			group.getName()
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				Long.valueOf(group.getClassNameId()),
				Long.valueOf(group.getLiveGroupId()),
				
			group.getName()
			}, group);

		group.resetOriginalValues();
	}

	/**
	 * Caches the groups in the entity cache if it is enabled.
	 *
	 * @param groups the groups
	 */
	public void cacheResult(List<Group> groups) {
		for (Group group : groups) {
			if (EntityCacheUtil.getResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
						GroupImpl.class, group.getPrimaryKey()) == null) {
				cacheResult(group);
			}
			else {
				group.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all groups.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(GroupImpl.class.getName());
		}

		EntityCacheUtil.clearCache(GroupImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the group.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Group group) {
		EntityCacheUtil.removeResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(group);
	}

	@Override
	public void clearCache(List<Group> groups) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Group group : groups) {
			EntityCacheUtil.removeResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
				GroupImpl.class, group.getPrimaryKey());

			clearUniqueFindersCache(group);
		}
	}

	protected void clearUniqueFindersCache(Group group) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
			new Object[] { Long.valueOf(group.getLiveGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { Long.valueOf(group.getCompanyId()), group.getName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				
			group.getFriendlyURL()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				Long.valueOf(group.getClassNameId()),
				Long.valueOf(group.getClassPK())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				Long.valueOf(group.getLiveGroupId()),
				
			group.getName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N,
			new Object[] {
				Long.valueOf(group.getCompanyId()),
				Long.valueOf(group.getClassNameId()),
				Long.valueOf(group.getLiveGroupId()),
				
			group.getName()
			});
	}

	/**
	 * Creates a new group with the primary key. Does not add the group to the database.
	 *
	 * @param groupId the primary key for the new group
	 * @return the new group
	 */
	public Group create(long groupId) {
		Group group = new GroupImpl();

		group.setNew(true);
		group.setPrimaryKey(groupId);

		return group;
	}

	/**
	 * Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param groupId the primary key of the group
	 * @return the group that was removed
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group remove(long groupId)
		throws NoSuchGroupException, SystemException {
		return remove(Long.valueOf(groupId));
	}

	/**
	 * Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the group
	 * @return the group that was removed
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group remove(Serializable primaryKey)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Group group = (Group)session.get(GroupImpl.class, primaryKey);

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(group);
		}
		catch (NoSuchGroupException nsee) {
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
	protected Group removeImpl(Group group) throws SystemException {
		group = toUnwrappedModel(group);

		try {
			clearOrganizations.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}

		try {
			clearRoles.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}

		try {
			clearUserGroups.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}

		try {
			clearUsers.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (group.isCachedModel()) {
				group = (Group)session.get(GroupImpl.class,
						group.getPrimaryKeyObj());
			}

			if (group != null) {
				session.delete(group);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (group != null) {
			clearCache(group);
		}

		return group;
	}

	@Override
	public Group updateImpl(com.liferay.portal.model.Group group)
		throws SystemException {
		group = toUnwrappedModel(group);

		boolean isNew = group.isNew();

		GroupModelImpl groupModelImpl = (GroupModelImpl)group;

		Session session = null;

		try {
			session = openSession();

			if (group.isNew()) {
				session.save(group);

				group.setNew(false);
			}
			else {
				session.merge(group);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !GroupModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { Long.valueOf(groupModelImpl.getCompanyId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						Long.valueOf(groupModelImpl.getOriginalParentGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);

				args = new Object[] {
						Long.valueOf(groupModelImpl.getCompanyId()),
						Long.valueOf(groupModelImpl.getParentGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Integer.valueOf(groupModelImpl.getOriginalType()),
						Boolean.valueOf(groupModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A,
					args);

				args = new Object[] {
						Integer.valueOf(groupModelImpl.getType()),
						Boolean.valueOf(groupModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						Long.valueOf(groupModelImpl.getOriginalParentGroupId()),
						Boolean.valueOf(groupModelImpl.getOriginalSite())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S,
					args);

				args = new Object[] {
						Long.valueOf(groupModelImpl.getCompanyId()),
						Long.valueOf(groupModelImpl.getParentGroupId()),
						Boolean.valueOf(groupModelImpl.getSite())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S,
					args);
			}
		}

		EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey(), group);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
				new Object[] { Long.valueOf(group.getLiveGroupId()) }, group);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] { Long.valueOf(group.getCompanyId()), group.getName() },
				group);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
				new Object[] {
					Long.valueOf(group.getCompanyId()),
					
				group.getFriendlyURL()
				}, group);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
				new Object[] {
					Long.valueOf(group.getCompanyId()),
					Long.valueOf(group.getClassNameId()),
					Long.valueOf(group.getClassPK())
				}, group);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
				new Object[] {
					Long.valueOf(group.getCompanyId()),
					Long.valueOf(group.getLiveGroupId()),
					
				group.getName()
				}, group);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
				new Object[] {
					Long.valueOf(group.getCompanyId()),
					Long.valueOf(group.getClassNameId()),
					Long.valueOf(group.getLiveGroupId()),
					
				group.getName()
				}, group);
		}
		else {
			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_LIVEGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalLiveGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LIVEGROUPID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
					new Object[] { Long.valueOf(group.getLiveGroupId()) }, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						
						groupModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
					new Object[] {
						Long.valueOf(group.getCompanyId()),
						
					group.getName()
					}, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						
						groupModelImpl.getOriginalFriendlyURL()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_F, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
					new Object[] {
						Long.valueOf(group.getCompanyId()),
						
					group.getFriendlyURL()
					}, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						Long.valueOf(groupModelImpl.getOriginalClassNameId()),
						Long.valueOf(groupModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
					new Object[] {
						Long.valueOf(group.getCompanyId()),
						Long.valueOf(group.getClassNameId()),
						Long.valueOf(group.getClassPK())
					}, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_L_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						Long.valueOf(groupModelImpl.getOriginalLiveGroupId()),
						
						groupModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_L_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
					new Object[] {
						Long.valueOf(group.getCompanyId()),
						Long.valueOf(group.getLiveGroupId()),
						
					group.getName()
					}, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C_L_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(groupModelImpl.getOriginalCompanyId()),
						Long.valueOf(groupModelImpl.getOriginalClassNameId()),
						Long.valueOf(groupModelImpl.getOriginalLiveGroupId()),
						
						groupModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_L_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
					new Object[] {
						Long.valueOf(group.getCompanyId()),
						Long.valueOf(group.getClassNameId()),
						Long.valueOf(group.getLiveGroupId()),
						
					group.getName()
					}, group);
			}
		}

		return group;
	}

	protected Group toUnwrappedModel(Group group) {
		if (group instanceof GroupImpl) {
			return group;
		}

		GroupImpl groupImpl = new GroupImpl();

		groupImpl.setNew(group.isNew());
		groupImpl.setPrimaryKey(group.getPrimaryKey());

		groupImpl.setGroupId(group.getGroupId());
		groupImpl.setCompanyId(group.getCompanyId());
		groupImpl.setCreatorUserId(group.getCreatorUserId());
		groupImpl.setClassNameId(group.getClassNameId());
		groupImpl.setClassPK(group.getClassPK());
		groupImpl.setParentGroupId(group.getParentGroupId());
		groupImpl.setLiveGroupId(group.getLiveGroupId());
		groupImpl.setName(group.getName());
		groupImpl.setDescription(group.getDescription());
		groupImpl.setType(group.getType());
		groupImpl.setTypeSettings(group.getTypeSettings());
		groupImpl.setFriendlyURL(group.getFriendlyURL());
		groupImpl.setSite(group.isSite());
		groupImpl.setActive(group.isActive());

		return groupImpl;
	}

	/**
	 * Returns the group with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the group
	 * @return the group
	 * @throws com.liferay.portal.NoSuchModelException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the group with the primary key or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param groupId the primary key of the group
	 * @return the group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByPrimaryKey(long groupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByPrimaryKey(groupId);

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + groupId);
			}

			throw new NoSuchGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				groupId);
		}

		return group;
	}

	/**
	 * Returns the group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the group
	 * @return the group, or <code>null</code> if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param groupId the primary key of the group
	 * @return the group, or <code>null</code> if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByPrimaryKey(long groupId) throws SystemException {
		Group group = (Group)EntityCacheUtil.getResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
				GroupImpl.class, groupId);

		if (group == _nullGroup) {
			return null;
		}

		if (group == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				group = (Group)session.get(GroupImpl.class,
						Long.valueOf(groupId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (group != null) {
					cacheResult(group);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
						GroupImpl.class, groupId, _nullGroup);
				}

				closeSession(session);
			}
		}

		return group;
	}

	/**
	 * Returns all the groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByCompanyId(long companyId, int start, int end,
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Group>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByCompanyId_First(companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByCompanyId_Last(companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<Group> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group[] findByCompanyId_PrevAndNext(long groupId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, group, companyId,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByCompanyId_PrevAndNext(session, group, companyId,
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

	protected Group getByCompanyId_PrevAndNext(Session session, Group group,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the group where liveGroupId = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param liveGroupId the live group ID
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByLiveGroupId(liveGroupId);

		if (group == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("liveGroupId=");
			msg.append(liveGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param liveGroupId the live group ID
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByLiveGroupId(long liveGroupId) throws SystemException {
		return fetchByLiveGroupId(liveGroupId, true);
	}

	/**
	 * Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param liveGroupId the live group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByLiveGroupId(long liveGroupId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { liveGroupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((liveGroupId != group.getLiveGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_LIVEGROUPID_LIVEGROUPID_2);

			query.append(GroupModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(liveGroupId);

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getLiveGroupId() != liveGroupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
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
				return (Group)result;
			}
		}
	}

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByC_P(long companyId, long parentGroupId)
		throws SystemException {
		return findByC_P(companyId, parentGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByC_P(long companyId, long parentGroupId, int start,
		int end) throws SystemException {
		return findByC_P(companyId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByC_P(long companyId, long parentGroupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] { companyId, parentGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] {
					companyId, parentGroupId,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(parentGroupId != group.getParentGroupId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				list = (List<Group>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_P_First(long companyId, long parentGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_First(companyId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_P_First(long companyId, long parentGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByC_P(companyId, parentGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_P_Last(long companyId, long parentGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_Last(companyId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_P_Last(long companyId, long parentGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_P(companyId, parentGroupId);

		List<Group> list = findByC_P(companyId, parentGroupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group[] findByC_P_PrevAndNext(long groupId, long companyId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_P_PrevAndNext(session, group, companyId,
					parentGroupId, orderByComparator, true);

			array[1] = group;

			array[2] = getByC_P_PrevAndNext(session, group, companyId,
					parentGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Group getByC_P_PrevAndNext(Session session, Group group,
		long companyId, long parentGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTGROUPID_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the group where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_N(companyId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					!Validator.equals(name, group.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			query.append(GroupModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
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
				return (Group)result;
			}
		}
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_F(companyId, friendlyURL);

		if (group == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_F(long companyId, String friendlyURL)
		throws SystemException {
		return fetchByC_F(companyId, friendlyURL, true);
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_F(long companyId, String friendlyURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					!Validator.equals(friendlyURL, group.getFriendlyURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else {
				if (friendlyURL.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
				}
			}

			query.append(GroupModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (friendlyURL != null) {
					qPos.add(friendlyURL);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getFriendlyURL() == null) ||
							!group.getFriendlyURL().equals(friendlyURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F,
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
				return (Group)result;
			}
		}
	}

	/**
	 * Returns all the groups where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByT_A(int type, boolean active)
		throws SystemException {
		return findByT_A(type, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByT_A(int type, boolean active, int start, int end)
		throws SystemException {
		return findByT_A(type, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByT_A(int type, boolean active, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A;
			finderArgs = new Object[] { type, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_A;
			finderArgs = new Object[] {
					type, active,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((type != group.getType()) || (active != group.getActive())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_T_A_TYPE_2);

			query.append(_FINDER_COLUMN_T_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(active);

				list = (List<Group>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByT_A_First(int type, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByT_A_First(type, active, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByT_A_First(int type, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByT_A(type, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByT_A_Last(int type, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByT_A_Last(type, active, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByT_A_Last(int type, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_A(type, active);

		List<Group> list = findByT_A(type, active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group[] findByT_A_PrevAndNext(long groupId, int type,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByT_A_PrevAndNext(session, group, type, active,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByT_A_PrevAndNext(session, group, type, active,
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

	protected Group getByT_A_PrevAndNext(Session session, Group group,
		int type, boolean active, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_T_A_TYPE_2);

		query.append(_FINDER_COLUMN_T_A_ACTIVE_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_C(companyId, classNameId, classPK);

		if (group == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_C(companyId, classNameId, classPK, true);
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					(classNameId != group.getClassNameId()) ||
					(classPK != group.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			query.append(GroupModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getClassNameId() != classNameId) ||
							(group.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
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
				return (Group)result;
			}
		}
	}

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site) throws SystemException {
		return findByC_P_S(companyId, parentGroupId, site, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end) throws SystemException {
		return findByC_P_S(companyId, parentGroupId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S;
			finderArgs = new Object[] { companyId, parentGroupId, site };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P_S;
			finderArgs = new Object[] {
					companyId, parentGroupId, site,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(parentGroupId != group.getParentGroupId()) ||
						(site != group.getSite())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_S_PARENTGROUPID_2);

			query.append(_FINDER_COLUMN_C_P_S_SITE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				qPos.add(site);

				list = (List<Group>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_P_S_First(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_S_First(companyId, parentGroupId, site,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(", site=");
		msg.append(site);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_P_S_First(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws SystemException {
		List<Group> list = findByC_P_S(companyId, parentGroupId, site, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_P_S_Last(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_S_Last(companyId, parentGroupId, site,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(", site=");
		msg.append(site);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_P_S_Last(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_P_S(companyId, parentGroupId, site);

		List<Group> list = findByC_P_S(companyId, parentGroupId, site,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group[] findByC_P_S_PrevAndNext(long groupId, long companyId,
		long parentGroupId, boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_P_S_PrevAndNext(session, group, companyId,
					parentGroupId, site, orderByComparator, true);

			array[1] = group;

			array[2] = getByC_P_S_PrevAndNext(session, group, companyId,
					parentGroupId, site, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Group getByC_P_S_PrevAndNext(Session session, Group group,
		long companyId, long parentGroupId, boolean site,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_S_PARENTGROUPID_2);

		query.append(_FINDER_COLUMN_C_P_S_SITE_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentGroupId);

		qPos.add(site);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_L_N(long companyId, long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_L_N(companyId, liveGroupId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", liveGroupId=");
			msg.append(liveGroupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_L_N(long companyId, long liveGroupId, String name)
		throws SystemException {
		return fetchByC_L_N(companyId, liveGroupId, name, true);
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_L_N(long companyId, long liveGroupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, liveGroupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_L_N,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					(liveGroupId != group.getLiveGroupId()) ||
					!Validator.equals(name, group.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_L_N_LIVEGROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_L_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_L_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_L_N_NAME_2);
				}
			}

			query.append(GroupModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(liveGroupId);

				if (name != null) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getLiveGroupId() != liveGroupId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N,
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
				return (Group)result;
			}
		}
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group findByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_L_N(companyId, classNameId, liveGroupId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", liveGroupId=");
			msg.append(liveGroupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name) throws SystemException {
		return fetchByC_C_L_N(companyId, classNameId, liveGroupId, name, true);
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Group fetchByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, classNameId, liveGroupId, name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_L_N,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					(classNameId != group.getClassNameId()) ||
					(liveGroupId != group.getLiveGroupId()) ||
					!Validator.equals(name, group.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_LIVEGROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_C_L_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_L_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_L_N_NAME_2);
				}
			}

			query.append(GroupModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(liveGroupId);

				if (name != null) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getClassNameId() != classNameId) ||
							(group.getLiveGroupId() != liveGroupId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N,
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
				return (Group)result;
			}
		}
	}

	/**
	 * Returns all the groups.
	 *
	 * @return the groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Group> findAll(int start, int end,
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_GROUP_);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_GROUP_.concat(GroupModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Group group : findByCompanyId(companyId)) {
			remove(group);
		}
	}

	/**
	 * Removes the group where liveGroupId = &#63; from the database.
	 *
	 * @param liveGroupId the live group ID
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Group removeByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException, SystemException {
		Group group = findByLiveGroupId(liveGroupId);

		return remove(group);
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P(long companyId, long parentGroupId)
		throws SystemException {
		for (Group group : findByC_P(companyId, parentGroupId)) {
			remove(group);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Group removeByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_N(companyId, name);

		return remove(group);
	}

	/**
	 * Removes the group where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Group removeByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_F(companyId, friendlyURL);

		return remove(group);
	}

	/**
	 * Removes all the groups where type = &#63; and active = &#63; from the database.
	 *
	 * @param type the type
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_A(int type, boolean active) throws SystemException {
		for (Group group : findByT_A(type, active)) {
			remove(group);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Group removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_C(companyId, classNameId, classPK);

		return remove(group);
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P_S(long companyId, long parentGroupId, boolean site)
		throws SystemException {
		for (Group group : findByC_P_S(companyId, parentGroupId, site)) {
			remove(group);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Group removeByC_L_N(long companyId, long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_L_N(companyId, liveGroupId, name);

		return remove(group);
	}

	/**
	 * Removes the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Group removeByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_L_N(companyId, classNameId, liveGroupId, name);

		return remove(group);
	}

	/**
	 * Removes all the groups from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Group group : findAll()) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_GROUP__WHERE);

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
	 * Returns the number of groups where liveGroupId = &#63;.
	 *
	 * @param liveGroupId the live group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLiveGroupId(long liveGroupId) throws SystemException {
		Object[] finderArgs = new Object[] { liveGroupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LIVEGROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_LIVEGROUPID_LIVEGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(liveGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LIVEGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P(long companyId, long parentGroupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, parentGroupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_F(long companyId, String friendlyURL)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else {
				if (friendlyURL.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (friendlyURL != null) {
					qPos.add(friendlyURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_A(int type, boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { type, active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_T_A_TYPE_2);

			query.append(_FINDER_COLUMN_T_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P_S(long companyId, long parentGroupId, boolean site)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, parentGroupId, site };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_S_PARENTGROUPID_2);

			query.append(_FINDER_COLUMN_C_P_S_SITE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				qPos.add(site);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and liveGroupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_L_N(long companyId, long liveGroupId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, liveGroupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_L_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_L_N_LIVEGROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_L_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_L_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_L_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(liveGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_L_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, classNameId, liveGroupId, name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_L_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_LIVEGROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_C_L_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_L_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_L_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(liveGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_L_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of groups.
	 *
	 * @return the number of groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_GROUP_);

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
	 * Returns all the organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS,
			com.liferay.portal.model.impl.OrganizationImpl.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME, "getOrganizations",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_ORGANIZATIONS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the organizations associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETORGANIZATIONS.concat(ORDER_BY_CLAUSE)
											   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETORGANIZATIONS.concat(com.liferay.portal.model.impl.OrganizationModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ORGANIZATIONS,
						finderArgs);
				}
				else {
					organizationPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS, Long.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME,
			"getOrganizationsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_ORGANIZATIONS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getOrganizationsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ORGANIZATION = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS, Boolean.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME,
			"containsOrganization",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the organization is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, organizationPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ORGANIZATION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsOrganization.contains(pk,
							organizationPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ORGANIZATION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the group has any organizations associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with organizations
	 * @return <code>true</code> if the group has any organizations associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Clears all associations between the group and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated organizations from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			Set<Long> organizationPKSet = SetUtil.fromArray(organizationPKs);

			List<com.liferay.portal.model.Organization> organizations = getOrganizations(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				if (!organizationPKSet.remove(organization.getPrimaryKey())) {
					removeOrganization.remove(pk, organization.getPrimaryKey());
				}
			}

			for (Long organizationPK : organizationPKSet) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			long[] organizationPKs = new long[organizations.size()];

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = organizations.get(i);

				organizationPKs[i] = organization.getPrimaryKey();
			}

			setOrganizations(pk, organizationPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Returns all the roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES,
			com.liferay.portal.model.impl.RoleImpl.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "getRoles",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_ROLES.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the roles associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETROLES.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETROLES.concat(com.liferay.portal.model.impl.RoleModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ROLES,
						finderArgs);
				}
				else {
					rolePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, Long.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "getRolesSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_ROLES_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, Boolean.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the role is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, rolePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the group has any roles associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with roles
	 * @return <code>true</code> if the group has any roles associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Clears all associations between the group and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated roles from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			Set<Long> rolePKSet = SetUtil.fromArray(rolePKs);

			List<com.liferay.portal.model.Role> roles = getRoles(pk);

			for (com.liferay.portal.model.Role role : roles) {
				if (!rolePKSet.remove(role.getPrimaryKey())) {
					removeRole.remove(pk, role.getPrimaryKey());
				}
			}

			for (Long rolePK : rolePKSet) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Returns all the user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			com.liferay.portal.model.impl.UserGroupImpl.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME,
			"getUserGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_USERGROUPS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERGROUPS.concat(ORDER_BY_CLAUSE)
											.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERGROUPS.concat(com.liferay.portal.model.impl.UserGroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERGROUPS,
						finderArgs);
				}
				else {
					userGroupPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS, Long.class,
			GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME,
			"getUserGroupsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERGROUPS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getUserGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USERGROUP = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			Boolean.class, GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME,
			"containsUserGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user group is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, userGroupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USERGROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the group has any user groups associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with user groups
	 * @return <code>true</code> if the group has any user groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the group and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated user groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			Set<Long> userGroupPKSet = SetUtil.fromArray(userGroupPKs);

			List<com.liferay.portal.model.UserGroup> userGroups = getUserGroups(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				if (!userGroupPKSet.remove(userGroup.getPrimaryKey())) {
					removeUserGroup.remove(pk, userGroup.getPrimaryKey());
				}
			}

			for (Long userGroupPK : userGroupPKSet) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Returns all the users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS,
			com.liferay.portal.model.impl.UserImpl.class,
			GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_USERS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the users associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERS,
						finderArgs);
				}
				else {
					userPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, Long.class,
			GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "getUsersSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, Boolean.class,
			GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, userPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the group has any users associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with users
	 * @return <code>true</code> if the group has any users associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated users from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.remove(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Initializes the group persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Group")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Group>> listenersList = new ArrayList<ModelListener<Group>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Group>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsOrganization = new ContainsOrganization();

		addOrganization = new AddOrganization();
		clearOrganizations = new ClearOrganizations();
		removeOrganization = new RemoveOrganization();

		containsRole = new ContainsRole();

		addRole = new AddRole();
		clearRoles = new ClearRoles();
		removeRole = new RemoveRole();

		containsUserGroup = new ContainsUserGroup();

		addUserGroup = new AddUserGroup();
		clearUserGroups = new ClearUserGroups();
		removeUserGroup = new RemoveUserGroup();

		containsUser = new ContainsUser();

		addUser = new AddUser();
		clearUsers = new ClearUsers();
		removeUser = new RemoveUser();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(GroupImpl.class.getName());
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
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = BookmarksFolderPersistence.class)
	protected BookmarksFolderPersistence bookmarksFolderPersistence;
	@BeanReference(type = CalEventPersistence.class)
	protected CalEventPersistence calEventPersistence;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(type = ShoppingCartPersistence.class)
	protected ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(type = ShoppingCategoryPersistence.class)
	protected ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(type = ShoppingCouponPersistence.class)
	protected ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(type = ShoppingOrderPersistence.class)
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = WikiNodePersistence.class)
	protected WikiNodePersistence wikiNodePersistence;
	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsOrganization {
		protected ContainsOrganization() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSORGANIZATION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long organizationId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(organizationId)
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

	protected class AddOrganization {
		protected AddOrganization() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Orgs (groupId, organizationId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long groupId, long organizationId)
			throws SystemException {
			if (!containsOrganization.contains(groupId, organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeAddAssociation(organizationId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(organizationId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterAddAssociation(organizationId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearOrganizations {
		protected ClearOrganizations() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE groupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
				organizationPersistence.getListeners();

			List<com.liferay.portal.model.Organization> organizations = null;

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				organizations = getOrganizations(groupId);

				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onAfterRemoveAssociation(organization.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveOrganization {
		protected RemoveOrganization() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long groupId, long organizationId)
			throws SystemException {
			if (containsOrganization.contains(groupId, organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(organizationId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterRemoveAssociation(organizationId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsRole {
		protected ContainsRole() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(roleId)
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

	protected class AddRole {
		protected AddRole() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Roles (groupId, roleId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long groupId, long roleId) throws SystemException {
			if (!containsRole.contains(groupId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(roleId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearRoles {
		protected ClearRoles() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE groupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(groupId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onAfterRemoveAssociation(role.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE groupId = ? AND roleId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long groupId, long roleId)
			throws SystemException {
			if (containsRole.contains(groupId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(roleId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(userGroupId)
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

	protected class AddUserGroup {
		protected AddUserGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_UserGroups (groupId, userGroupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long groupId, long userGroupId)
			throws SystemException {
			if (!containsUserGroup.contains(groupId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userGroupId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_UserGroups WHERE groupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(groupId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onAfterRemoveAssociation(userGroup.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long groupId, long userGroupId)
			throws SystemException {
			if (containsUserGroup.contains(groupId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userGroupId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsUser {
		protected ContainsUser() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(userId)
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

	protected class AddUser {
		protected AddUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Groups (groupId, userId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long groupId, long userId) throws SystemException {
			if (!containsUser.contains(groupId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUsers {
		protected ClearUsers() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE groupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(groupId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE groupId = ? AND userId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long groupId, long userId)
			throws SystemException {
			if (containsUser.contains(groupId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_GROUP_ = "SELECT group_ FROM Group group_";
	private static final String _SQL_SELECT_GROUP__WHERE = "SELECT group_ FROM Group group_ WHERE ";
	private static final String _SQL_COUNT_GROUP_ = "SELECT COUNT(group_) FROM Group group_";
	private static final String _SQL_COUNT_GROUP__WHERE = "SELECT COUNT(group_) FROM Group group_ WHERE ";
	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Groups_Orgs ON (Groups_Orgs.organizationId = Organization_.organizationId) WHERE (Groups_Orgs.groupId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Groups_Roles ON (Groups_Roles.roleId = Role_.roleId) WHERE (Groups_Roles.groupId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ? AND roleId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Groups_UserGroups ON (Groups_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Groups_UserGroups.groupId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Groups ON (Users_Groups.userId = User_.userId) WHERE (Users_Groups.groupId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "group_.companyId = ?";
	private static final String _FINDER_COLUMN_LIVEGROUPID_LIVEGROUPID_2 = "group_.liveGroupId = ?";
	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PARENTGROUPID_2 = "group_.parentGroupId = ?";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "group_.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "group_.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(group_.name IS NULL OR group_.name = ?)";
	private static final String _FINDER_COLUMN_C_F_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_1 = "group_.friendlyURL IS NULL";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_2 = "group_.friendlyURL = ?";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_3 = "(group_.friendlyURL IS NULL OR group_.friendlyURL = ?)";
	private static final String _FINDER_COLUMN_T_A_TYPE_2 = "group_.type = ? AND ";
	private static final String _FINDER_COLUMN_T_A_ACTIVE_2 = "group_.active = ?";
	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "group_.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "group_.classPK = ?";
	private static final String _FINDER_COLUMN_C_P_S_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_S_PARENTGROUPID_2 = "group_.parentGroupId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_S_SITE_2 = "group_.site = ?";
	private static final String _FINDER_COLUMN_C_L_N_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_N_LIVEGROUPID_2 = "group_.liveGroupId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_N_NAME_1 = "group_.name IS NULL";
	private static final String _FINDER_COLUMN_C_L_N_NAME_2 = "group_.name = ?";
	private static final String _FINDER_COLUMN_C_L_N_NAME_3 = "(group_.name IS NULL OR group_.name = ?)";
	private static final String _FINDER_COLUMN_C_C_L_N_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_L_N_CLASSNAMEID_2 = "group_.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_L_N_LIVEGROUPID_2 = "group_.liveGroupId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_L_N_NAME_1 = "group_.name IS NULL";
	private static final String _FINDER_COLUMN_C_C_L_N_NAME_2 = "group_.name = ?";
	private static final String _FINDER_COLUMN_C_C_L_N_NAME_3 = "(group_.name IS NULL OR group_.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "group_.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Group exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Group exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(GroupPersistenceImpl.class);
	private static Group _nullGroup = new GroupImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Group> toCacheModel() {
				return _nullGroupCacheModel;
			}
		};

	private static CacheModel<Group> _nullGroupCacheModel = new CacheModel<Group>() {
			public Group toEntityModel() {
				return _nullGroup;
			}
		};
}